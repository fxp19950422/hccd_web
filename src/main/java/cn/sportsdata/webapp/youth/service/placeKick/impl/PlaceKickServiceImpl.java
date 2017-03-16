package cn.sportsdata.webapp.youth.service.placeKick.impl;

import cn.sportsdata.webapp.youth.common.constants.Constants;
import cn.sportsdata.webapp.youth.common.vo.PlaceKickVO;
import cn.sportsdata.webapp.youth.common.vo.TacticsVO;
import cn.sportsdata.webapp.youth.dao.asset.AssetDAO;
import cn.sportsdata.webapp.youth.dao.placeKick.PlaceKickDAO;
import cn.sportsdata.webapp.youth.dao.tactics.TacticsDAO;
import cn.sportsdata.webapp.youth.service.placeKick.PlaceKickService;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class PlaceKickServiceImpl implements PlaceKickService {
	private static Logger logger = Logger.getLogger(PlaceKickServiceImpl.class);
	@Autowired
	private PlaceKickDAO placeKickDAO;
	
	@Autowired
	private TacticsDAO tacticsDAO;
	
	@Autowired
	private AssetDAO assetDAO;
	
	@Override
	@Transactional
	public String createPlaceKick(PlaceKickVO placeKickVo) {
		String tacticsId = null;
		try {
			assetDAO.insertAsset(placeKickVo.getImgAssetVo());
			assetDAO.insertAsset(placeKickVo.getDataAssetVo());
			
			placeKickVo.setImgName(placeKickVo.getImgAssetVo().getId());
			placeKickVo.setTacticsdata(placeKickVo.getDataAssetVo().getId());
			String surfix = "";
			try {
				Timestamp tmp = Timestamp.valueOf(placeKickVo.getImgAssetVo().getLast_update());
				surfix = "&_lastupdate="+tmp.getTime();
			} catch(Exception e) {
				surfix = "";
			}
			placeKickVo.setImgUrl(placeKickVo.getImgUrl()+"hagkFile/asset?id="+placeKickVo.getImgAssetVo().getId()+surfix);
			placeKickVo.setTactics_type_id(Constants.place_kick_tactics_type);
			tacticsDAO.createTactics(placeKickVo);
			tacticsId = placeKickVo.getId();
			
			placeKickVo.setId(tacticsId);
			placeKickDAO.createPlaceKick(placeKickVo);
		}
		catch (Exception e) {
			return null;
		}
		return tacticsId;
	}

	@Override
	public List<PlaceKickVO> getPlaceKickByUserId(String userId, String orgId, String category) {
		List<PlaceKickVO> PlaceKickList = null;
		try {
			PlaceKickList = placeKickDAO.getPlaceKickByOrg(orgId, category);
			if(PlaceKickList == null) {
				PlaceKickList = new ArrayList<PlaceKickVO>();
			}	
		}
		catch (Exception e) {
			PlaceKickList = new ArrayList<PlaceKickVO>();
		}
		return PlaceKickList;
	}

	@Override
	public List<PlaceKickVO> getPlaceKickByTypeAndUserId(String userId, String orgId, String placeKickTypeId) {
		List<PlaceKickVO> PlaceKickList = null;
		try {
			PlaceKickList = placeKickDAO.getPlaceKickByOrgAndType(orgId, placeKickTypeId);
			if(PlaceKickList == null) {
				PlaceKickList = new ArrayList<PlaceKickVO>();
			}	
		}
		catch (Exception e) {
			PlaceKickList = new ArrayList<PlaceKickVO>();
		}
		return PlaceKickList;
	}

	@Override
	public PlaceKickVO getPlaceKickById(String place_kick_id) {
		return placeKickDAO.getPlaceKickByID(place_kick_id);
	}

	@Override
	public boolean deletePlaceKick(PlaceKickVO placeKickVo) {
		boolean success = false;
		try {
			success = tacticsDAO.deleteTactics(placeKickVo);
		}
		catch (Exception e) {
			success = false;
		}
		return success; 
	}

	@Override
	public TacticsVO getTacticsVOData(PlaceKickVO placeKickVo) {
		TacticsVO tactics = new TacticsVO();
		try {
			BeanUtils.copyProperties(tactics, placeKickVo);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return tactics;
	}

	@Override
	public boolean updatePlaceKickType(PlaceKickVO placeKickVo) {
		try {
			placeKickVo.setId(placeKickVo.getId());
			if(placeKickVo.getImgAssetVo().isNew()) {
				assetDAO.insertAsset(placeKickVo.getImgAssetVo());
			} else {
				assetDAO.updateAsset(placeKickVo.getImgAssetVo());
			}
			if(placeKickVo.getDataAssetVo().isNew()) {
				assetDAO.insertAsset(placeKickVo.getDataAssetVo());
			} else {
				assetDAO.updateAsset(placeKickVo.getDataAssetVo());
			}
			
			placeKickVo.setImgName(placeKickVo.getImgAssetVo().getId());
			placeKickVo.setTacticsdata(placeKickVo.getDataAssetVo().getId());
			String surfix = "";
			try {
				Timestamp tmp = Timestamp.valueOf(placeKickVo.getImgAssetVo().getLast_update());
				surfix = "&_lastupdate="+tmp.getTime();
			} catch(Exception e) {
				surfix = "";
			}
			placeKickVo.setImgUrl(placeKickVo.getImgUrl()+"hagkFile/asset?id="+placeKickVo.getImgAssetVo().getId()+surfix);
			if (tacticsDAO.updateTactics(placeKickVo)) {
				if (placeKickDAO.updatePlaceKick(placeKickVo.getId(), placeKickVo.getPlaceKickTypeId()) > 0) {
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
