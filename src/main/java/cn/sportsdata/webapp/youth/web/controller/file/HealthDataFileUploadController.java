package cn.sportsdata.webapp.youth.web.controller.file;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.sportsdata.webapp.youth.common.constants.Constants;
import cn.sportsdata.webapp.youth.common.exceptions.SoccerProException;
import cn.sportsdata.webapp.youth.common.utils.HAFileUtils;
import cn.sportsdata.webapp.youth.common.utils.SecurityUtils;
import cn.sportsdata.webapp.youth.common.vo.AssetVO;
import cn.sportsdata.webapp.youth.common.vo.OrgVO;
import cn.sportsdata.webapp.youth.common.vo.Response;
import cn.sportsdata.webapp.youth.common.vo.login.LoginVO;
import cn.sportsdata.webapp.youth.service.account.AccountService;
import cn.sportsdata.webapp.youth.service.asset.AssetService;
import cn.sportsdata.webapp.youth.web.controller.BaseController;
import net.sf.json.JSONArray;

@Controller
@RequestMapping("/api/v1")
public class HealthDataFileUploadController extends BaseController {
	private static Logger logger = Logger.getLogger(HealthDataFileUploadController.class);
	private static Rectangle clip;
	
	@Autowired
	AssetService assetservice;
	
    @Autowired
    private AccountService accountService;
	
	@ResponseBody
	@RequestMapping(value="/tempUpload", method = RequestMethod.POST)
	public Object tempUpload(HttpServletRequest request, @RequestParam(required=false) MultipartFile uploadedFile) {
		try {
			if(uploadedFile == null) {
				return "";
			}
			
			String originFileName =  java.util.UUID.randomUUID() + "photo.png";
			LoginVO loginVO = getCurrentUser(request);
			OrgVO orgVO = getCurrentOrg(request);
			
			File createdFile = HAFileUtils.createNewAttachmentFile(originFileName, loginVO.getId(), orgVO.getId(), HAFileUtils.TEMP_FILE_TYPE);
			uploadedFile.transferTo(createdFile);
			
			return SecurityUtils.encryptByAES(createdFile.getAbsolutePath());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	@ResponseBody
	@RequestMapping(value="/upload", method = RequestMethod.POST)
	public Object upload(HttpServletRequest request, @RequestBody FileUploadBO fileUploadBO) {
		try {
			String tempFilePath = fileUploadBO.getFileName();
			int cropStartX = Integer.parseInt(fileUploadBO.getCropStartX());
			int cropStartY = Integer.parseInt(fileUploadBO.getCropStartY());
			int cropWidth = Integer.parseInt(fileUploadBO.getCropWidth());
			int cropHeight = Integer.parseInt(fileUploadBO.getCropHeight());
			
			return processCropAndSave(tempFilePath, cropStartX, cropStartY, cropWidth, cropHeight, request);
		} catch (Exception e) {
			//
		}
		
		return "";
	}
	
	private String processCropAndSave(String tempFilePath,int cropStartX, int cropStartY, int cropWidth, int cropHeight, HttpServletRequest request) throws SoccerProException {
		String originalFileName = SecurityUtils.decryptByAES(tempFilePath);
			
		BufferedImage originalImage = readImage(originalFileName);
			
		BufferedImage processedImage = cropMyImage(originalImage, cropStartX, cropStartY, cropWidth, cropHeight);
		LoginVO loginVO = getCurrentUser(request);
		OrgVO orgVO = getCurrentOrg(request);
		
		return writeImage(processedImage, "gif", loginVO, orgVO);
	}
	
	private String writeImage(BufferedImage img, String extension, LoginVO loginVO, OrgVO orgVO) throws SoccerProException {
		File createdFile = HAFileUtils.createNewAttachmentFile(java.util.UUID.randomUUID() + "photo.png",
				loginVO.getId(), orgVO.getId(), HAFileUtils.PRODUCT_FILE_TYPE);
		
		try {
			BufferedImage bi = img;
			ImageIO.write(bi, extension, createdFile);
		} catch (IOException ex) {
			//
		}
		
		return SecurityUtils.encryptByAES(createdFile.getAbsolutePath());
	}
	
	private BufferedImage readImage(String tempFilePathAndName) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(tempFilePathAndName));
		} catch (IOException ex) {
			//
		}
		return img;
	}
	
	private BufferedImage cropMyImage(BufferedImage img, int cropStartX, int cropStartY, int cropWidth, int cropHeight) {
		
		BufferedImage clipped = null;
		Dimension size = new Dimension(cropWidth, cropHeight);

		createClip(img, size, cropStartX, cropStartY);
		try {
			int w = clip.width;
			int h = clip.height;
			clipped = img.getSubimage(clip.x, clip.y, w, h);
		} catch (RasterFormatException rfe) {
			//
		}
		
		return clipped;
	}
	
	private void createClip(BufferedImage img, Dimension size, int clipX, int clipY) {
		//Checking for negative X Co-ordinate
		if (clipX < 0) {
			clipX = 0;
		}
		
		//Checking for negative Y Co-ordinate
		if (clipY < 0) {
			clipY = 0;
		}

		//Checking if the clip area lies outside the rectangle.
		if ((size.width + clipX) <= img.getWidth() && (size.height + clipY) <= img.getHeight()) {
			//Setting up a clip rectangle when clip area lies within the image.
			clip = new Rectangle(size);
			clip.x = clipX;
			clip.y = clipY;
		} else {
			//Checking if the width of the clip area lies outside the image. If so, making the image width boundary as the clip width.
			if ((size.width + clipX) > img.getWidth()){
				size.width = img.getWidth() - clipX;
			}

			//Checking if the height of the clip area lies outside the image. If so, making the image height boundary as the clip height.
			if ((size.height + clipY) > img.getHeight()){
				size.height = img.getHeight() - clipY;
			}

			//Setting up the clip are based on our clip area size adjustment.
			clip = new Rectangle(size);
			clip.x = clipX;
			clip.y = clipY;
		}
	}
	
	// below upload/download methods are used by pure PlUpload plugin
	@ResponseBody
	@RequestMapping(value="/fileUpload", method = RequestMethod.POST, produces = "text/html; charset=utf-8")
	public String fileUpload(HttpServletRequest request, @RequestParam MultipartFile uploadedFile, String hospitalId, String hospitalRecordId, String type, String typeId) {
		try {
//			LoginVO loginVO = getCurrentUser(request);
//			OrgVO orgVO = getCurrentOrg(request);
			LoginVO loginVO = (LoginVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		    String accountID = loginVO.getId();
		        
			
			String originFileName = uploadedFile.getOriginalFilename();

			String fileExt = HAFileUtils.getFileExtension(originFileName);

			String createdFileName =  UUID.randomUUID().toString();
			if(fileExt != null){
				createdFileName += ("." + fileExt);
			}

			File createdFile = HAFileUtils.createNewAttachmentFile(createdFileName, loginVO.getId(), null, HAFileUtils.PRODUCT_FILE_TYPE);
			uploadedFile.transferTo(createdFile);
			
			JSONObject json = new JSONObject();
//			json.put("encryptedFileName", SecurityUtils.encryptByAES(createdFile.getAbsolutePath()));
//			json.put("originalFileName", originFileName);
//			json.put("extName", fileExt);
//			json.put("size", uploadedFile.getSize());
			
			
			 // add a record to asset
	        AssetVO vo = new AssetVO();
	      
	        vo.setCreator_id(accountID);
	        vo.setOrg_id("");
	        vo.setDisplay_name(originFileName);
	        vo.setFile_extension(fileExt);
	        vo.setSize(uploadedFile.getSize());
	        vo.setPrivacy("protected");
	        vo.setStatus("active");
	        vo.setStorage_name(SecurityUtils.encryptByAES(createdFile.getAbsolutePath()));

	        String id = assetservice.insertAsset(vo, hospitalId, hospitalRecordId, type, typeId);
			vo.setId(id);
			json.put("asset_id", id);
			return json.toString();
		} catch(Exception e) {
			logger.error("Error occurs while uploading file", e);
		}
		
		return "";
	}
	
	@ResponseBody
	@RequestMapping(value = "/download")
	public Object downloadFormAttachment(HttpServletRequest request, HttpServletResponse response, @RequestParam(required=true) String fileName) throws SoccerProException {
		if(StringUtils.isBlank(fileName)) {
			return null;
		}
		
		String encryptedFileName = fileName;
		String originalFileName = SecurityUtils.decryptByAES(encryptedFileName);
		File targetFile = new File(originalFileName);
		
		if(!targetFile.exists()) {
			return null;
		}
		
		InputStream is = null;
		OutputStream os = null;
		String targetFileName = targetFile.getName();
		String extName = targetFileName.substring(targetFileName.lastIndexOf(".") + 1);
		
		try {
			if(Arrays.asList(Constants.VALID_ATTACHMENTS_PIC_TYPES).contains(extName.toLowerCase())) {
				response.setContentType("image/*");
			} else {
				response.setContentType("application/octet-stream");
				
				String downloadedFileName = "比赛附件." + extName;
				String ua = request.getHeader("User-Agent");
				if(ua != null && (ua.toLowerCase().indexOf("msie") > -1 || (ua.toLowerCase().indexOf("trident") > -1 && ua.toLowerCase().indexOf("rv") > -1))) {
					downloadedFileName = URLEncoder.encode(downloadedFileName, "UTF8");
				} else {
					downloadedFileName = new String(downloadedFileName.getBytes("UTF-8"), "ISO8859-1");
				}
				
				response.setHeader("Content-disposition", "attachment; filename=" + downloadedFileName);
			}
			
			is = new FileInputStream(targetFile);
			os = response.getOutputStream();

			IOUtils.copy(is, os);
		} catch(Exception e) {
			//
		} finally {
			IOUtils.closeQuietly(is);
			IOUtils.closeQuietly(os);
		}
		
		return null;
	}
	
	@ResponseBody
	@RequestMapping(value = "/fileDelete", method = RequestMethod.POST)
	public Response deleteFile(HttpServletRequest request, @RequestParam String fileNameArray) {
		JSONArray files = JSONArray.fromObject(fileNameArray);
		
		if(files != null) {
			for(int i = 0; i < files.size(); i++) {
				String encryptedFileName = files.getString(i);
				String originalFileName = null;
				
				try {
					originalFileName = SecurityUtils.decryptByAES(encryptedFileName);
				} catch (SoccerProException e) {
					continue;
				}
				
				File targetFile = new File(originalFileName);
				if(!targetFile.exists()) {
					continue;
				} else {
					FileUtils.deleteQuietly(targetFile);
				}
			}
		}
		
		return Response.toSussess(null);
	}
	
	@ResponseBody
	@RequestMapping(value = "/downloadTemplate")
	public Object downloadTemplate(HttpServletRequest request, HttpServletResponse response, @RequestParam String fileName) {
		if(StringUtils.isBlank(fileName)) {
			return null;
		}
		
		InputStream is = this.getClass().getResourceAsStream("/templates/" + fileName);
		if(is == null) {
			return null;
		}
		
		OutputStream os = null;
		try {
			response.setContentType("application/octet-stream");
			String ua = request.getHeader("User-Agent");
			if(ua != null && (ua.toLowerCase().indexOf("msie") > -1 || (ua.toLowerCase().indexOf("trident") > -1 && ua.toLowerCase().indexOf("rv") > -1))) {
				fileName = URLEncoder.encode(fileName, "UTF8");
			} else {
				fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
			}
			
			response.setHeader("Content-disposition", "attachment; filename=" + fileName);
			os = response.getOutputStream();
			
			IOUtils.copy(is, os);
		} catch(Exception e) {
			logger.error("Error occurs while downloading template file: " + fileName, e);
		} finally {
			IOUtils.closeQuietly(is);
			IOUtils.closeQuietly(os);
		}
		
		return null;
	}
	
	@ResponseBody
	@RequestMapping(value = "/asset")
	public Object downloadAsset(HttpServletRequest request, HttpServletResponse response, @RequestParam String id) throws SoccerProException {
		return assetDownload(request, response, id, true);
	}
	
	@ResponseBody
	@RequestMapping(value = "/asset_pub")
	public Object downloadAssetWithNoPrivilege(HttpServletRequest request, HttpServletResponse response, @RequestParam String id) throws SoccerProException {
		return assetDownload(request, response, id, false);
	}
	
	private Object assetDownload(HttpServletRequest request, HttpServletResponse response, String id, boolean checkPrivilege) throws SoccerProException {
		if(StringUtils.isBlank(id)) {
			logger.error("Asset ID is blank");
			return null;
		}
		
		AssetVO asset = assetservice.getAssetByID(id);
		if(asset == null || StringUtils.isBlank(asset.getStorage_name())) {
			logger.error("Asset ID: " + id + " is not found");
			return null;
		}
		
//		if(checkPrivilege) {
//			String currentUserId = getCurrentUser(request).getId();
//			if (!canDownloadAsset(asset, currentUserId, getUserOrgs(currentUserId))) {
//				logger.error("User: " + currentUserId + " has no privilege for handling asset: " + id);
//				return null;
//			}
//		}
		
		String encryptedFileName = asset.getStorage_name();
        String filePath = SecurityUtils.decryptByAES(encryptedFileName);
        File targetFile = new File(filePath);
        
        if(!targetFile.exists()) {
        	logger.error("Asset ID: " + id + " of related file path is not found");
			return null;
        }
        
        InputStream is = null;
		OutputStream os = null;
		
		try {
			if(Arrays.asList(Constants.VALID_ATTACHMENTS_PIC_TYPES).contains(asset.getFile_extension())) {
				response.setContentType("image/*");
			} else {
				response.setContentType("application/octet-stream");
				
				String downloadedFileName = asset.getDisplay_name();
				if(StringUtils.isBlank(downloadedFileName)) {
					downloadedFileName = Constants.DEFAULT_DOWNLOAD_FILE_NAME;
				}
				
				String ua = request.getHeader("User-Agent");
				if(ua != null && (ua.toLowerCase().indexOf("msie") > -1 || (ua.toLowerCase().indexOf("trident") > -1 && ua.toLowerCase().indexOf("rv") > -1))) {
					downloadedFileName = URLEncoder.encode(downloadedFileName, "UTF8");
				} else {
					downloadedFileName = new String(downloadedFileName.getBytes("UTF-8"), "ISO8859-1");
				}
				
				response.setHeader("Content-disposition", "attachment; filename=" + downloadedFileName);
			}
			
			is = new FileInputStream(targetFile);
			os = response.getOutputStream();

			IOUtils.copy(is, os);
		} catch(Exception e) {
			logger.error("Error occurs while downloading asset: " + id, e);
		} finally {
			IOUtils.closeQuietly(is);
			IOUtils.closeQuietly(os);
		}
		
		return null;
	}
	
	private boolean canDownloadAsset(AssetVO asset, String userID, List<String> orgList){
        if (asset.getCreator_id().equals(userID)){
            return true;
        }

        if(orgList.contains(asset.getOrg_id()) && !asset.getPrivacy().equals("private")) {
            return true;
        }

        return false;
    }
	
	private List<String> getUserOrgs(String accountID) {
        List<String> orgIdList = new ArrayList<String>();
        
        List<OrgVO> voList = accountService.getOrgsByAccount(accountID);
        for (int i=0; i<voList.size(); i++){
            orgIdList.add( voList.get(i).getId());
        }
        
        return orgIdList;
    }
}
