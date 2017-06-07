package cn.sportsdata.webapp.youth.web.controller.file;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import cn.sportsdata.webapp.youth.common.exceptions.SoccerProException;
import cn.sportsdata.webapp.youth.common.utils.ConfigProps;
import cn.sportsdata.webapp.youth.common.utils.HAFileUtils;
import cn.sportsdata.webapp.youth.common.utils.SecurityUtils;
import cn.sportsdata.webapp.youth.common.utils.barcode.ZXingCode;
import cn.sportsdata.webapp.youth.common.vo.AssetVO;
import cn.sportsdata.webapp.youth.common.vo.OrgVO;
import cn.sportsdata.webapp.youth.common.vo.login.LoginVO;
import cn.sportsdata.webapp.youth.service.asset.AssetService;
import cn.sportsdata.webapp.youth.web.controller.BaseController;

@Controller
@RequestMapping("/file")
public class FileController extends BaseController {
	
	@Autowired
	AssetService assetservice;
	
	private static Logger logger = Logger.getLogger(FileController.class);
	private static final long PHOTO_SIZE_LIMIT_5M = 5 * 1024 * 1024;
	static Rectangle clip;
	
	@ResponseBody
	@RequestMapping(value = "/flashUploadFile", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	public Object uploadFormAttachment(HttpServletRequest request) throws SoccerProException {
		String originFileName = java.util.UUID.randomUUID()+"photo.png";
		LoginVO loginVO = getCurrentUser(request);
		OrgVO orgVO = getCurrentOrg(request);
		
		File createdFile = HAFileUtils.createNewAttachmentFile(originFileName, loginVO.getId(), orgVO.getId(), HAFileUtils.TEMP_FILE_TYPE);
		try {
			uploadFileInternal(createdFile.getParentFile().getAbsolutePath(), request.getInputStream(), createdFile.getAbsolutePath());
		} catch (Exception e) {
			logger.error("Error occurs while uploading form attachments", e);
			return printResult("failed", e.getMessage());
		}
		String encryptedFileName = SecurityUtils.encryptByAES(createdFile.getAbsolutePath());
		return printResult("success", encryptedFileName);
	}
	
	@ResponseBody
	@RequestMapping(value="/upload", method = RequestMethod.POST)
	  public Object upload(HttpServletRequest request, HttpServletResponse response) throws SoccerProException {
		String aciton = request.getParameter("action");
		
		if ("tempUpload".equals(aciton)) {
			MultipartHttpServletRequest tempRequest = (MultipartHttpServletRequest) request;
			//1. build an iterator
		     Iterator<String> itr =  tempRequest.getFileNames();
		     MultipartFile mpf = null;

		     //2. get each file
		     while(itr.hasNext()){
		       
		       //2.1 get next MultipartFile
		       mpf = tempRequest.getFile(itr.next());
		       
		       String filename = mpf.getOriginalFilename();
		       if (!checkFile(filename)) {
		    	   return printResult("failed", "invalidImg");
		       }
		       
		       String originFileName = java.util.UUID.randomUUID()+"photo.png";
				LoginVO loginVO = getCurrentUser(request);
				OrgVO orgVO = getCurrentOrg(request);
				
				File createdFile = HAFileUtils.createNewAttachmentFile(originFileName, loginVO.getId(), orgVO.getId(), HAFileUtils.TEMP_FILE_TYPE);
				try {
					uploadFileInternal(createdFile.getParentFile().getAbsolutePath(), mpf.getInputStream(), createdFile.getAbsolutePath());
				} catch (ExceedLimitSizeException e) {
					return printResult("failed", "exceedFileLimitSize");
				}  catch (Exception e) {
					logger.error("Error occurs while uploading form attachments", e);
					return printResult("failed", e.getMessage());
				}
				String encryptedFileName = SecurityUtils.encryptByAES(createdFile.getAbsolutePath());
				return printResult("success", encryptedFileName);
		     }
		} else {
			if ("cropAndSave".equals(aciton)) {
				String tempFilePath = request.getParameter("fileName");
				String cropStartXStr = request.getParameter("cropStartX");
				int cropStartX = Integer.parseInt(cropStartXStr);
				String cropStartYStr = request.getParameter("cropStartY");
				int cropStartY = Integer.parseInt(cropStartYStr);
				String cropWidthStr = request.getParameter("cropWidth");
				int cropWidth = Integer.parseInt(cropWidthStr);
				String cropHeightStr = request.getParameter("cropHeight");
				int cropHeight = Integer.parseInt(cropHeightStr);
				
				String noNeedCrop = request.getParameter("noNeedCrop");
				return processCropAndSave(tempFilePath, cropStartX, cropStartY, cropWidth, cropHeight, request, noNeedCrop);
			}
		}
		
		
	     return "";
	}
	
	private  boolean checkFile(String fileName){
		boolean flag=false;
		String suffixList="jpg,gif,png,jpeg";
		String suffix=fileName.substring(fileName.lastIndexOf(".")+1, fileName.length());
		
		if(suffixList.contains(suffix.trim().toLowerCase())){
			flag=true;
		}
		return flag;
	}
	
	public String processCropAndSave(String tempFilePath,int cropStartX, int cropStartY, int cropWidth, int cropHeight, HttpServletRequest request, String noNeedCrop) throws SoccerProException {
		String originalFileName = SecurityUtils.decryptByAES(tempFilePath);
			
		BufferedImage originalImage = readImage(originalFileName);
		LoginVO loginVO = getCurrentUser(request);
		OrgVO orgVO = getCurrentOrg(request);
		if ("true".equals(noNeedCrop)){
			return writeImage(originalImage, "jpg", loginVO, orgVO);
		}
		BufferedImage processedImage = cropMyImage(originalImage, cropStartX, cropStartY, cropWidth, cropHeight);
		
		
		BufferedImage scaledImage = scaleMyImage(processedImage, 320);
		return writeImage(scaledImage, "jpg", loginVO, orgVO);
	}
	
	/**
	 * This method reads an image from the file.
	 * 
	 * @param fileLocation
	 * @return BufferedImage of the file read
	 */
	private BufferedImage readImage(String tempFilePathAndName) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(tempFilePathAndName));
		} catch (IOException ex) {
			logger.error("ImageIO reand file failed and throw exception", ex);
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
			logger.error(rfe);
		}
		
		return clipped;
	}

	private BufferedImage scaleMyImage(BufferedImage prevImage, int size) {
		try {
		    double width = prevImage.getWidth();  
		    double height = prevImage.getHeight();  
		    double percent = size/width;  
		    int newWidth = (int)(width * percent);  
		    int newHeight = (int)(height * percent);  
		    BufferedImage image = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_BGR);  
		    Graphics graphics = image.createGraphics();  
		    graphics.drawImage(prevImage, 0, 0, newWidth, newHeight, null);  
		    return image;
		} catch (RasterFormatException rfe) {
			logger.error(rfe);
		}
	    return prevImage;
	}
	
	/**
	 * This method crops an original image according to the parameters provided.
	 * 
	 * If the crop rectangle lies outside the rectangle (even if partially),
	 * this program will adjust the crop area to fit within the original image.
	 * 
	 * @param img, Original Image To Be Cropped
	 * @param size, Crop area rectangle
	 * @param clipX, Starting X-position of crop area rectangle
	 * @param clipY, Strating Y-position of crop area rectangle
	 * @return void
	 * @throws WbxException
	 */
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
	
	protected static void uploadFileInternal(String filePath, InputStream fileData, String fileName)
			throws Exception {
		long readTotal = 0L;
		File path = new File(filePath);
		if (!path.exists()) {
			path.mkdirs();
		}
		OutputStream outStream = new BufferedOutputStream(new FileOutputStream(fileName));
		if (fileData != null && outStream != null) {
			try {
				byte[] buffer = new byte[8192];
				int bytesRead = fileData.read(buffer, 0, 8192);
				while (bytesRead != -1) {
					readTotal = bytesRead + readTotal;
					if (readTotal > PHOTO_SIZE_LIMIT_5M) {
						throw new ExceedLimitSizeException("file size > 5M");
					}
					outStream.write(buffer, 0, bytesRead);
					bytesRead = fileData.read(buffer, 0, 8192);
				}
			} finally {
				try {
					outStream.close();
					fileData.close();
				} catch (IOException ex) {
					outStream = null;
					fileData = null;
				}
			}

		}
		
	}
	
	/**
	 * This method writes a buffered image to a file.
	 * 
	 * @param img BufferedImage
	 * @param fileLocation
	 * @param extension
	 * @throws SoccerProException 
	 */
	private String writeImage(BufferedImage img, String extension, LoginVO loginVO, OrgVO orgVO) throws SoccerProException {
		File createdFile = HAFileUtils.createNewAttachmentFile(java.util.UUID.randomUUID()+"photo.gif", loginVO.getId(), orgVO.getId(), HAFileUtils.PRODUCT_FILE_TYPE);
		
		try {
			BufferedImage bi = img;
			ImageIO.write(bi, extension, createdFile);
		} catch (IOException ex) {
			logger.error("file write failed", ex);
		}
		
		String encryptedFileName = SecurityUtils.encryptByAES(createdFile.getAbsolutePath());
		String status = "success";
	    return printResult(status, encryptedFileName);
	}
	
	private String printResult(String status, String value) {
			JSONObject obj = new JSONObject();
			obj.put("status", status);
			obj.put("value", value);
			String jsonStr = obj.toString();
			return jsonStr;
	
	}
	
	@ResponseBody
	@RequestMapping(value = "/downloadFile")
	public Object downloadFormAttachment(HttpServletRequest request, HttpServletResponse response, @RequestParam(required=true) String fileName) throws SoccerProException {
		if(StringUtils.isBlank(fileName)) {
			logger.error("File name is blank");
			return null;
		}
		String encryptedFileName = fileName;
		String originalFileName = SecurityUtils.decryptByAES(encryptedFileName);
		
		File targetFile = new File(originalFileName);
		
		if(!targetFile.exists()) {
			logger.error(fileName + " is not existed");
			return null;
		}
		
		InputStream is = null;
		OutputStream os = null;
		
		try {
			response.setContentType("image/*");
			is = new FileInputStream(targetFile);
			os = response.getOutputStream();
			
			IOUtils.copy(is, os);
		} catch(Exception e) {
			logger.error("Error occurs while downloading form attachment: " + targetFile.getAbsolutePath(), e);
		} finally {
			IOUtils.closeQuietly(is);
			IOUtils.closeQuietly(os);
		}
		
		return null;
	}

	@ResponseBody
	@RequestMapping(value = "/asset")
	public Object downloadFormAsset(HttpServletRequest request, HttpServletResponse response, @RequestParam(required=true) String id) throws SoccerProException {
		if(StringUtils.isBlank(id)) {
			logger.error("File name is blank");
			return null;
		}
		
		File targetFile = null;
		String originalFileName = null;
		try{
			AssetVO fileAssetVo= assetservice.getAssetByID(id);
			if(fileAssetVo!=null && fileAssetVo.getStorage_name() !=null && fileAssetVo.getStorage_name().isEmpty() == false) {	
				String encryptedFileName = fileAssetVo.getStorage_name();
				originalFileName = SecurityUtils.decryptByAES(encryptedFileName);
				targetFile = new File(originalFileName);
			}
			
		} catch(Exception e) {
			targetFile = null;
		}

		if(targetFile==null || !targetFile.exists()) {
			logger.error(id + " is not existed");
			return null;
		}
		
		InputStream is = null;
		OutputStream os = null;
		
		try {
			response.setContentType("image/*");
			is = new FileInputStream(targetFile);
			os = response.getOutputStream();
			
			IOUtils.copy(is, os);
		} catch(Exception e) {
			logger.error("Error occurs while downloading form attachment: " + targetFile.getAbsolutePath(), e);
		} finally {
			IOUtils.closeQuietly(is);
			IOUtils.closeQuietly(os);
		}
		
		return null;
	}
	
	@ResponseBody
	@RequestMapping(value = "/getBarcode")
	public Object getBarcode(HttpServletRequest request, HttpServletResponse response, @RequestParam(required=true) String content) throws SoccerProException {
		if(StringUtils.isBlank(content)) {
			logger.error("File name is blank");
			return null;
		}
		
		InputStream is = null;
		ByteArrayOutputStream os = null;
		OutputStream resuestOS = null;
		
		try {
			String originalContent = SecurityUtils.decryptByAES(content);
			
			BufferedImage image = ZXingCode.getBarCode(originalContent);
			
			os = new ByteArrayOutputStream();
			ImageIO.write(image, "gif", os);
			is = new ByteArrayInputStream(os.toByteArray());
			response.setContentType("image/*");
			resuestOS = response.getOutputStream();
			
			IOUtils.copy(is, resuestOS);
		} catch(Exception e) {
		} finally {
			IOUtils.closeQuietly(is);
			IOUtils.closeQuietly(os);
		}
		
		return null;
	}
}
