package cn.sportsdata.webapp.youth.service.tactics.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import cn.sportsdata.webapp.youth.dao.asset.AssetDAO;
import cn.sportsdata.webapp.youth.dao.placeKick.PlaceKickDAO;
import cn.sportsdata.webapp.youth.dao.starters.StartersDAO;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.sportsdata.webapp.youth.common.constants.Constants;
import cn.sportsdata.webapp.youth.common.exceptions.SoccerProException;
import cn.sportsdata.webapp.youth.common.utils.HAFileUtils;
import cn.sportsdata.webapp.youth.common.utils.SecurityUtils;
import cn.sportsdata.webapp.youth.common.utils.StringUtil;
import cn.sportsdata.webapp.youth.common.vo.AssetVO;
import cn.sportsdata.webapp.youth.common.vo.PlaceKickVO;
import cn.sportsdata.webapp.youth.common.vo.TacticsVO;
import cn.sportsdata.webapp.youth.common.vo.UserVO;
import cn.sportsdata.webapp.youth.common.vo.starters.StartersPlayerAssociationVO;
import cn.sportsdata.webapp.youth.common.vo.starters.StartersVO;
import cn.sportsdata.webapp.youth.dao.tactics.TacticsDAO;
import cn.sportsdata.webapp.youth.service.tactics.TacticsService;

@Service
public class TacticsServiceImpl implements TacticsService {
	private static Logger logger = Logger.getLogger(TacticsServiceImpl.class);
	@Autowired
	private TacticsDAO tacticsDAO;

	@Autowired
	private AssetDAO assetDAO;
	
	@Autowired
	private StartersDAO startersDAO;
	
	@Autowired
	private PlaceKickDAO placeKickDAO;
	
	@Override
	public TacticsVO getTacticsByID(String id) {
		// TODO Auto-generated method stub
		return tacticsDAO.getTacticsByID(id);
	}

	@Transactional
	@Override
	public String createTactics(TacticsVO tactics) {
		try {
			assetDAO.insertAsset(tactics.getImgAssetVo());
			assetDAO.insertAsset(tactics.getDataAssetVo());
			tactics.setImgName(tactics.getImgAssetVo().getId());
			tactics.setTacticsdata(tactics.getDataAssetVo().getId());
			String surfix = "";
			try {
				Timestamp tmp = Timestamp.valueOf(tactics.getImgAssetVo().getLast_update());
				surfix = "&_lastupdate="+tmp.getTime();
			} catch(Exception e) {
				surfix = "";
			}
			tactics.setImgUrl(tactics.getImgUrl()+"hagkFile/asset?id="+tactics.getImgAssetVo().getId()+surfix);
			tacticsDAO.createTactics(tactics);
			
			if (tactics.getTactics_type_id() == Constants.starters_tactics_type) {
				StartersVO startersVo = new StartersVO();
				startersVo.setTacticsId(tactics.getId());
				startersVo.setFormation_id(Constants.default_starters_formation_type_id);
				startersDAO.createStarters(startersVo);
			}
			
			if (tactics.getTactics_type_id() == Constants.place_kick_tactics_type) {
				PlaceKickVO placekickVo = new PlaceKickVO();
				placekickVo.setPlaceKickTypeId(Constants.default_place_kick_type_id);
				placekickVo.setId(tactics.getId());
				placeKickDAO.createPlaceKick(placekickVo);
			}
		}
		catch (Exception e) {
			return null;
		}
		return tactics.getId();
	}
	private String fileName = null;
	private String fileNameImg = null;
	private File filedata = null;
	private File fileImg = null;
	
	@Override
	public boolean saveTacticsFiles(TacticsVO tacticsVo, String userId, String orgId) {
		logger.info("TacticsController.saveTactics");
		UUID uuid = UUID.randomUUID();
		AssetVO imgAssetVo = null;
		AssetVO dataAssetVo = null;
		if(StringUtil.isBlank(tacticsVo.getId()) == false) {
			// old tactics
			imgAssetVo = assetDAO.getAssetByID(tacticsVo.getImgName());
			dataAssetVo = assetDAO.getAssetByID(tacticsVo.getTacticsdata());
		}

 		if(imgAssetVo == null) {
 			imgAssetVo = new AssetVO();
 			//imgAssetVo.setId(DatabaseUtils.Short_UUID(orgId, userId)); 
 			imgAssetVo.setCreator_id(userId);
 			imgAssetVo.setOrg_id(orgId);
			imgAssetVo.setFile_extension(AssetVO.TYPE_png);
 			imgAssetVo.setPrivacy(AssetVO.PRIVACY_protected);
			imgAssetVo.setStorage_name(null);	
			fileNameImg = null;
 		} else {
 			imgAssetVo.setNew(false);
			try {
				fileNameImg = SecurityUtils.decryptByAES(imgAssetVo.getStorage_name());
			} catch (SoccerProException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				fileNameImg = null;
			}
 		}

		if(dataAssetVo == null) {
			dataAssetVo = new AssetVO();
			//dataAssetVo.setId(DatabaseUtils.Short_UUID(orgId, userId));
			dataAssetVo.setCreator_id(userId);
			dataAssetVo.setOrg_id(orgId);
			dataAssetVo.setFile_extension(AssetVO.TYPE_txt);
			dataAssetVo.setPrivacy(AssetVO.PRIVACY_protected);
			dataAssetVo.setStorage_name(null);	
			fileName = null;
		} else {
			dataAssetVo.setNew(false);
			try {
					fileName = SecurityUtils.decryptByAES(dataAssetVo.getStorage_name());
				}
			catch (SoccerProException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				fileName = null;
			}
		}
		
		boolean bSuccess = false;
		bSuccess = false;
		dataAssetVo.setStorage_name(null);
		String originTacticsDataFileName = uuid.toString()+"_web_tacticsdata.txt";
		try {
			if(StringUtils.isBlank(fileName) == false) {
				filedata = HAFileUtils.createNewAttachmentFile(fileName);
				if(filedata != null) {
					filedata.deleteOnExit();
				}
	 		}
			filedata = HAFileUtils.createNewTacticsFile(originTacticsDataFileName, userId, orgId, HAFileUtils.PRODUCT_FILE_TYPE);

			if(filedata != null) {
				bSuccess = writeStrToFile(tacticsVo.getTacticsFrames(), filedata.getAbsolutePath());
				String encryptedFileName = SecurityUtils.encryptByAES(filedata.getAbsolutePath());
				dataAssetVo.setStorage_name(encryptedFileName);
				dataAssetVo.setSize(tacticsVo.getTacticsFrames().length());
			}
		} catch (Exception e) {
			logger.error("Error occurs while writing tactics data", e);
		}

		String originTacticsPicFileName = uuid.toString()+"_web_tacticsdata.png";
		bSuccess = false;
		imgAssetVo.setStorage_name(null);
		try {
			if(StringUtils.isBlank(fileNameImg) == false) {
				fileImg = HAFileUtils.createNewAttachmentFile(fileNameImg);
				if(fileImg != null) {
					fileImg.deleteOnExit();
				}
			}
			fileImg = HAFileUtils.createNewTacticsFile(originTacticsPicFileName, userId, orgId, HAFileUtils.PRODUCT_FILE_TYPE);
			if(fileImg!=null) {
				bSuccess = GenerateImage(tacticsVo.getImgData(), fileImg);
				String encryptedFileName = SecurityUtils.encryptByAES(fileImg.getAbsolutePath());
				imgAssetVo.setStorage_name(encryptedFileName);
				imgAssetVo.setSize(tacticsVo.getImgData().length());
			}
		} catch (Exception e) {
			logger.error("Error occurs while writing tactics pic", e);
		}
		
		dataAssetVo.setDisplay_name("战术板数据(web)");
		imgAssetVo.setDisplay_name("战术板图片(web)");
		tacticsVo.setImgAssetVo(imgAssetVo);
		tacticsVo.setDataAssetVo(dataAssetVo);

		return bSuccess;
	}
	
	@Override
	public List<TacticsVO> getTacticsByOrgId(String org_id, String category) {
		List<TacticsVO> tacticsList = null;
		try {
			tacticsList = tacticsDAO.getTacticsByOrgId(org_id, category);
			if(tacticsList == null) {
				tacticsList = new ArrayList<TacticsVO>();
			}	
		}
		catch (Exception e) {
			tacticsList = new ArrayList<TacticsVO>();
		}
		return tacticsList;
	}

	@Override
	public List<TacticsVO> getTacticsByUserId(String creator_id, String category) {
		List<TacticsVO> tacticsList = null;
		try {
			tacticsList = tacticsDAO.getTacticsByUserId(creator_id, category);
			if(tacticsList == null) {
				tacticsList = new ArrayList<TacticsVO>();
			}	
		}
		catch (Exception e) {
			tacticsList = new ArrayList<TacticsVO>();
		}
		return tacticsList;
	}

	@Override
	public List<TacticsVO> getTacticsByTypeAndOrgId(String org_id, long tactics_type_id) {
		List<TacticsVO> tacticsList = null;
		try {
			tacticsList = tacticsDAO.getTacticsByTypeAndOrgId(org_id, tactics_type_id);
			if(tacticsList == null) {
				tacticsList = new ArrayList<TacticsVO>();
			}	
		}
		catch (Exception e) {
			tacticsList = new ArrayList<TacticsVO>();
		}
		return tacticsList;
	}

	@Override
	public List<TacticsVO> getTacticsByTypeAndUserId(String creator_id, long tactics_type_id) {
		List<TacticsVO> tacticsList = null;
		try {
			tacticsList = tacticsDAO.getTacticsByTypeAndUserId(creator_id, tactics_type_id);
			if(tacticsList == null) {
				tacticsList = new ArrayList<TacticsVO>();
			}	
		}
		catch (Exception e) {
			tacticsList = new ArrayList<TacticsVO>();
		}
		return tacticsList;
	}

	@Override
	public boolean updateTactics(TacticsVO tactics) {
		boolean success = false;
		try {
			TacticsVO org_tactics = tacticsDAO.getTacticsByID(tactics.getId());
			if(tactics.getImgAssetVo().isNew()) {
				assetDAO.insertAsset(tactics.getImgAssetVo());
			} else {
				assetDAO.updateAsset(tactics.getImgAssetVo());
			}
			if(tactics.getDataAssetVo().isNew()) {
				assetDAO.insertAsset(tactics.getDataAssetVo());
			} else {
				assetDAO.updateAsset(tactics.getDataAssetVo());
			}
			tactics.setImgName(tactics.getImgAssetVo().getId());
			tactics.setTacticsdata(tactics.getDataAssetVo().getId());
			String surfix = "";
			try {
				Timestamp tmp = Timestamp.valueOf(tactics.getImgAssetVo().getLast_update());
				surfix = "&_lastupdate="+tmp.getTime();
			} catch(Exception e) {
				surfix = "";
			}
			tactics.setImgUrl(tactics.getImgUrl()+"hagkFile/asset?id="+tactics.getImgAssetVo().getId()+surfix);
			success = tacticsDAO.updateTactics(tactics);
			
			if (org_tactics.getTactics_type_id() != tactics.getTactics_type_id()) {
				if (org_tactics.getTactics_type_id() == Constants.starters_tactics_type) {
					startersDAO.deleteStarters(tactics.getId());
				}
				
				if (org_tactics.getTactics_type_id() == Constants.place_kick_tactics_type) {
					placeKickDAO.deletePlaceKick(tactics.getId());
				}
				
				if (tactics.getTactics_type_id() == Constants.starters_tactics_type) {
					StartersVO startersVo = new StartersVO();
					startersVo.setTacticsId(tactics.getId());
					startersVo.setFormation_id(Constants.default_starters_formation_type_id);
					startersDAO.createStarters(startersVo);
				}
				
				if (tactics.getTactics_type_id() == Constants.place_kick_tactics_type) {
					PlaceKickVO placekickVo = new PlaceKickVO();
					placekickVo.setPlaceKickTypeId(Constants.default_place_kick_type_id);
					placekickVo.setId(tactics.getId());
					placeKickDAO.createPlaceKick(placekickVo);
				}
			}
		}
		catch (Exception e) {
			success = false;
		}
		return success; 
	}
	
	@Override
	@Transactional
	public long saveTacticsPlayer(TacticsVO tactics) {
		if (tactics.getTactics_type_id() != Constants.starters_tactics_type) {
			return 0;
		}
		try {
			String startersId = tactics.getId();
			List<StartersPlayerAssociationVO> playerList = new ArrayList<StartersPlayerAssociationVO>();
			List<UserVO> userList = tactics.getPlayerList();
			for (UserVO user : userList) {
				StartersPlayerAssociationVO player = new StartersPlayerAssociationVO();
				player.setStartersId(startersId);
				player.setPlayerId(user.getId());
				playerList.add(player);
			}
			
			startersDAO.clearStartersPlayer(startersId);
			return startersDAO.saveStartersPlayer(playerList);
		}
		catch (Exception e) {
			return 0;
		}
	}

	@Override
	public boolean deleteTactics(TacticsVO tactics) {
		boolean success = false;
		try {
			success = tacticsDAO.deleteTactics(tactics);	
		}
		catch (Exception e) {
			success = false;
		}
		return success; 
	}
	
	// 图片转化成base64字符串
	public static String GetImageStr(String imgFile) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
		// 待处理的图片
		if(imgFile==null) {
			return null;
		}
		String output = null;
		InputStream in = null;
		byte[] data = null;
		// 读取图片字节数组
		try {
			output = "data:image/png;base64,";
			in = new FileInputStream(imgFile);
			data = new byte[in.available()];
			in.read(data);
			in.close();
			// 对字节数组Base64编码
	        String t = new String(Base64.encodeBase64(data));
	        output = output + t;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			output = null;
		} catch (IOException e) {
			e.printStackTrace();
			output = null;
		} catch (Exception e) {
			e.printStackTrace();
			output = null;
		} 

		return output;// 返回Base64编码过的字节数组字符串
	}

	// base64字符串转化成图片
	public static boolean GenerateImage(String imgData, File createFile) { // 对字节数组字符串进行Base64解码并生成图片
		String imgStr = null;
		if(imgData.contains(",")) {
			imgStr = imgData.split(",")[1];
		} else {
			imgStr = imgData;
		}

		if (imgStr == null) // 图像数据为空
			return false;
		try {
				// Base64解码
				byte[] b = Base64.decodeBase64(imgStr);
				for (int i = 0; i < b.length; ++i) {
					if (b[i] < 0) {// 调整异常数据
						b[i] += 256;
					}
				}			
	        	
//		        	File f = new File(path);
//		        	// 创建文件夹
//		        	if (!f.exists()) {
//		        		f.mkdirs();
//		        	}
//		        		
				OutputStream out = new FileOutputStream(createFile.getAbsolutePath());
				out.write(b);
				out.flush();
				out.close();
				return true;
		} catch (Exception e) {
			return false;
		}
	}
	
    public static boolean writeStrToFile(String in, String createdFileName){  
        try {    
        	
//	        	File f = new File(path);
//	        	// 创建文件夹
//	        	if (!f.exists()) {
//	        		f.mkdirs();
//	        	}
            FileOutputStream fos = new FileOutputStream(new File(createdFileName));  
            Writer os = new OutputStreamWriter(fos, "utf-8");  
            os.write(in);  
            os.flush();  
            fos.close();  
            return true;
        } catch (FileNotFoundException e) {    
            // TODO Auto-generated catch block     
            e.printStackTrace();    
        } catch (IOException e) {    
            // TODO Auto-generated catch block     
            e.printStackTrace();    
        }    
        return false;
    }  
	
	public static String readFileToStr(String fileName) {// 
		// 待处理的图片
		String t = null;
		InputStream in = null;
		byte[] data = null;
		// 读取图片字节数组
		try {
			in = new FileInputStream(fileName);
			data = new byte[in.available()];
			in.read(data);
			in.close();
			t = new String(data);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			t = null;
		} catch (IOException e) {
			e.printStackTrace();
			t = null;
		}
		catch (Exception e) {
			e.printStackTrace();
			t = null;
		}
		return t;
	}

	@Override
	public void cleanOldFiles() {
		try {
			File old = new File(fileName);
			old.deleteOnExit();
		} 
		catch(Exception e){
			
		}
		
		try {
			File old = new File(fileNameImg);
			old.deleteOnExit();
		} 
		catch(Exception e){
			
		}
		fileName = null;
		fileNameImg = null;
	}

	@Override
	public boolean loadTacticsFiles(TacticsVO tacticsVo) {
		String originalTacticsDataFileName;
		boolean bSuccess = false;
		try {
			AssetVO tacticsAssetVo = assetDAO.getAssetByID(tacticsVo.getTacticsdata());
			originalTacticsDataFileName = SecurityUtils.decryptByAES(tacticsAssetVo.getStorage_name());
			tacticsVo.setTacticsFrames(readFileToStr(originalTacticsDataFileName));
			bSuccess = true;
		} catch (Exception e1) {
			e1.printStackTrace();
			tacticsVo.setTacticsFrames("");
		}		
		return bSuccess;
	}
	
	@Override
	public boolean copyTacticsFiles(TacticsVO dstTacticsVo, TacticsVO srcTacticsVo) {
		String originalTacticsDataFileName;
		try {
			AssetVO tacticsAssetVo = assetDAO.getAssetByID(srcTacticsVo.getTacticsdata());
			originalTacticsDataFileName = SecurityUtils.decryptByAES(tacticsAssetVo.getStorage_name());
			dstTacticsVo.setTacticsFrames(readFileToStr(originalTacticsDataFileName));
			dstTacticsVo.setDescription(srcTacticsVo.getDescription());
			dstTacticsVo.setName(srcTacticsVo.getName());
			return true;
		} catch (Exception e1) {
			e1.printStackTrace();
			dstTacticsVo.setTacticsFrames("");
		}
		return false;
	}

	@Override
	public List<TacticsVO> getTacticsByUserAndOrgId(String creator_id, String org_id, String category) {
		List<TacticsVO> tacticsList = null;
		try {
			tacticsList = tacticsDAO.getTacticsByOrgId(org_id, category);
			if(tacticsList == null) {
				tacticsList = new ArrayList<TacticsVO>();
			}	
		}
		catch (Exception e) {
			tacticsList = new ArrayList<TacticsVO>();
		}
		return tacticsList;
	}

	@Override
	public List<TacticsVO> getTacticsByTypeUserAndOrgId(String creator_id, String org_id, long tactics_type_id) {
		List<TacticsVO> tacticsList = null;
		try {
			tacticsList = tacticsDAO.getTacticsByTypeAndOrgId(org_id, tactics_type_id);
			if(tacticsList == null) {
				tacticsList = new ArrayList<TacticsVO>();
			}	
		}
		catch (Exception e) {
			tacticsList = new ArrayList<TacticsVO>();
		}
		return tacticsList;
	}

	@Override
	public List<TacticsVO> getTacticsByMatchId(String matchId) {
		List<TacticsVO> tacticsList = tacticsDAO.getTacticsByMatchId(matchId);
		if(tacticsList == null) {
			tacticsList = new ArrayList<TacticsVO>();
		}
		
		return tacticsList;
	}
}
