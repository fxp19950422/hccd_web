package cn.sportsdata.webapp.youth.service.starters.impl;

import cn.sportsdata.webapp.youth.common.vo.TacticsVO;
import cn.sportsdata.webapp.youth.common.vo.UserVO;
import cn.sportsdata.webapp.youth.common.vo.match.MatchStartersResultVO;
import cn.sportsdata.webapp.youth.common.vo.starters.StartersPlayerAssociationVO;
import cn.sportsdata.webapp.youth.common.vo.starters.StartersPreviewsVO;
import cn.sportsdata.webapp.youth.common.vo.starters.StartersVO;
import cn.sportsdata.webapp.youth.dao.asset.AssetDAO;
import cn.sportsdata.webapp.youth.dao.starters.StartersDAO;
import cn.sportsdata.webapp.youth.dao.tactics.TacticsDAO;
import cn.sportsdata.webapp.youth.service.starters.StartersService;
import cn.sportsdata.webapp.youth.common.constants.Constants;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class StartersServiceImpl implements StartersService {
	private static Logger logger = Logger.getLogger(StartersServiceImpl.class);
	@Autowired
	private StartersDAO startersDAO;
	
	@Autowired
	private TacticsDAO tacticsDAO;
	@Autowired
	private AssetDAO assetDAO;
	
	@Override
	@Transactional
	public String createStarters(StartersVO startersVo, TacticsVO tacticsVo) {
		String tacticsId = null;
		try {
			assetDAO.insertAsset(tacticsVo.getImgAssetVo());
			assetDAO.insertAsset(tacticsVo.getDataAssetVo());
			
			tacticsVo.setImgName(tacticsVo.getImgAssetVo().getId());
			tacticsVo.setTacticsdata(tacticsVo.getDataAssetVo().getId());
			String surfix = "";
			try {
				Timestamp tmp = Timestamp.valueOf(tacticsVo.getImgAssetVo().getLast_update());
				surfix = "&_lastupdate="+tmp.getTime();
			} catch(Exception e) {
				surfix = "";
			}
			tacticsVo.setImgUrl(tacticsVo.getImgUrl()+"hagkFile/asset?id="+tacticsVo.getImgAssetVo().getId()+surfix);
			tacticsVo.setTactics_type_id(Constants.starters_tactics_type);
			tacticsDAO.createTactics(tacticsVo);
			tacticsId = tacticsVo.getId();
			
			startersVo.setTacticsId(tacticsId);
			startersDAO.createStarters(startersVo);
		}
		catch (Exception e) {
			return null;
		}

		return tacticsId;
	}
	
	private void updateStartersMatchInfo(StartersPreviewsVO startersObj, MatchStartersResultVO matchResult) {
		startersObj.setUsedCount(startersObj.getUsedCount() + 1);
		if (startersObj.getRecentRecordList().size() < 5)
		{
			startersObj.getRecentRecordList().add(matchResult);
		}
		if (matchResult.getResult() != null) {
			if (matchResult.getResult().equals(Constants.MATCH_RESULT_WON)) {
				startersObj.setWinCount(startersObj.getWinCount() + 1);
			}
			if (matchResult.getResult().equals(Constants.MATCH_RESULT_LOST)) {
				startersObj.setLoseCount(startersObj.getLoseCount() + 1);
			}
			if (matchResult.getResult().equals(Constants.MATCH_RESULT_DRAWN)) {
				startersObj.setDrawCount(startersObj.getDrawCount() + 1);
			}
		}
	}

	@Override
	public List<StartersPreviewsVO> getStartersByUserId(String userId, String orgId, String category) {
		List<StartersPreviewsVO> startersList = null;
		try {
			startersList = startersDAO.getStartersByOrg(orgId, category);
			if(startersList == null) {
				startersList = new ArrayList<StartersPreviewsVO>();
			} else {
				List<MatchStartersResultVO> matchList = startersDAO.getMatchStartersByOrgId(orgId, category);
				for (MatchStartersResultVO matchResult: matchList) {
					for (StartersPreviewsVO startersObj: startersList) {
						if (matchResult.getStartersId().equals(startersObj.getTacticsId())) {
							updateStartersMatchInfo(startersObj, matchResult);
						}
					}
				}
			}
		}
		catch (Exception e) {
			startersList = new ArrayList<StartersPreviewsVO>();
		}
		return startersList;
	}
	
	@Override
	public StartersPreviewsVO getStartersMatchInfo(TacticsVO tacticsVo) {
		try {
			StartersPreviewsVO startersVo = new StartersPreviewsVO();
			BeanUtils.copyProperties(startersVo, tacticsVo);
			startersVo.setTacticsId(tacticsVo.getId());
			List<MatchStartersResultVO> matchList = startersDAO.getStartersMatchInfoById(tacticsVo.getId());

			for (MatchStartersResultVO matchResult: matchList) {
				updateStartersMatchInfo(startersVo, matchResult);
			}
			return startersVo;
		}
		catch (Exception e) {
			
		}
		return null;
	}

	@Override
	public List<StartersPreviewsVO> getStartersByTypeAndUserId(String userId, String orgId, long formationId) {
		List<StartersPreviewsVO> startersList = null;
		try {
			startersList = startersDAO.getStartersByOrgAndFormation(orgId, formationId);
			if(startersList == null) {
				startersList = new ArrayList<StartersPreviewsVO>();
			} else {
				List<MatchStartersResultVO> matchList = startersDAO.getMatchStartersByOrgId(orgId, "soccer");
				for (MatchStartersResultVO matchResult: matchList) {
					for (StartersPreviewsVO startersObj: startersList) {
						if (matchResult.getStartersId().equals(startersObj.getTacticsId())) {
							updateStartersMatchInfo(startersObj, matchResult);
						}
					}
				}
			}
		}
		catch (Exception e) {
			startersList = new ArrayList<StartersPreviewsVO>();
		}
		return startersList;
	}

	@Override
	public StartersVO getStartersById(String starters_id) {
		return startersDAO.getStartersByID(starters_id);
	}

	@Override
	public boolean deleteStarters(StartersVO startersVo) {
		boolean success = false;
		try {
			TacticsVO tactics = tacticsDAO.getTacticsByID(startersVo.getTacticsId());
			if (tactics != null) {
				success = tacticsDAO.deleteTactics(tactics);
			}
		}
		catch (Exception e) {
			success = false;
		}
		return success; 
	}

	@Override
	@Transactional
	public long saveStartersPlayer(StartersVO startersVo) {
		try {
			String startersId = startersVo.getTacticsId();
			List<StartersPlayerAssociationVO> playerList = new ArrayList<StartersPlayerAssociationVO>();
			List<UserVO> userList = startersVo.getPlayerList();
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
	public boolean updateStarters(StartersVO startersVo, TacticsVO tacticsVo) {
		try {
			tacticsVo.setId(startersVo.getTacticsId());
			if(tacticsVo.getImgAssetVo().isNew()) {
				assetDAO.insertAsset(tacticsVo.getImgAssetVo());
			} else {
				assetDAO.updateAsset(tacticsVo.getImgAssetVo());
			}
			if(tacticsVo.getDataAssetVo().isNew()) {
				assetDAO.insertAsset(tacticsVo.getDataAssetVo());
			} else {
				assetDAO.updateAsset(tacticsVo.getDataAssetVo());
			}
			
			tacticsVo.setImgName(tacticsVo.getImgAssetVo().getId());
			tacticsVo.setTacticsdata(tacticsVo.getDataAssetVo().getId());
			String surfix = "";
			try {
				Timestamp tmp = Timestamp.valueOf(tacticsVo.getImgAssetVo().getLast_update());
				surfix = "&_lastupdate="+tmp.getTime();
			} catch(Exception e) {
				surfix = "";
			}
			tacticsVo.setImgUrl(tacticsVo.getImgUrl()+"hagkFile/asset?id="+tacticsVo.getImgAssetVo().getId()+surfix);
			if (tacticsDAO.updateTactics(tacticsVo)) {
				if (startersDAO.updateStarters(startersVo.getTacticsId(), startersVo.getFormation_id()) > 0) {
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public TacticsVO getTacticsVOData(StartersVO startersVo) {
		TacticsVO tactics = new TacticsVO();
		try {
			BeanUtils.copyProperties(tactics, startersVo);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return tactics;
	}
}
