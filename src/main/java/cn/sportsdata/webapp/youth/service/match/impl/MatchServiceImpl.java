package cn.sportsdata.webapp.youth.service.match.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.sportsdata.webapp.youth.common.bo.MatchResultBO;
import cn.sportsdata.webapp.youth.common.constants.Constants;
import cn.sportsdata.webapp.youth.common.vo.match.GuestFoulVO;
import cn.sportsdata.webapp.youth.common.vo.match.GuestScoreVO;
import cn.sportsdata.webapp.youth.common.vo.match.GuestSubVO;
import cn.sportsdata.webapp.youth.common.vo.match.HomeFoulVO;
import cn.sportsdata.webapp.youth.common.vo.match.HomeScoreVO;
import cn.sportsdata.webapp.youth.common.vo.match.HomeSubVO;
import cn.sportsdata.webapp.youth.common.vo.match.MatchAssetVO;
import cn.sportsdata.webapp.youth.common.vo.match.MatchDataItemVO;
import cn.sportsdata.webapp.youth.common.vo.match.MatchDataVO;
import cn.sportsdata.webapp.youth.common.vo.match.MatchFoulVO;
import cn.sportsdata.webapp.youth.common.vo.match.MatchGoalVO;
import cn.sportsdata.webapp.youth.common.vo.match.MatchResultVO;
import cn.sportsdata.webapp.youth.common.vo.match.MatchSubVO;
import cn.sportsdata.webapp.youth.common.vo.match.MatchTacticsVO;
import cn.sportsdata.webapp.youth.common.vo.match.MatchVO;
import cn.sportsdata.webapp.youth.dao.match.MatchDAO;
import cn.sportsdata.webapp.youth.service.match.MatchService;

@Service
public class MatchServiceImpl implements MatchService {
	@Autowired
	private MatchDAO matchDAO;
	
	@Override
	@Transactional
	public boolean handleMatch(MatchVO matchVO, MatchResultVO matchResultVO, MatchTacticsVO matchTacticsVO, boolean isCreate) {
		matchDAO.handleMatchBasic(matchVO, isCreate);
		String matchId = matchVO.getId();
		
		if(matchResultVO != null) {
			MatchResultVO matchResult = matchDAO.getMatchResultBasicByMatchId(matchId);
			boolean isMatchResultCreate = (matchResult == null);
			matchResultVO.setMatchId(matchId);
			matchDAO.handleMatchResultBasic(matchResultVO, isMatchResultCreate);
		}
		
		if(matchTacticsVO != null) {
			matchTacticsVO.setMatchId(matchId);
			matchDAO.handleMatchTactics(matchTacticsVO);
			
			matchDAO.saveMatchStartPlayerList(matchId, matchTacticsVO);
		} else {
			matchDAO.deleteMatchTacticsByMatchId(matchId);
			matchDAO.clearMatchStartPlayerList(matchId);
		}
		
		return true;
	}

	@Override
	public List<MatchVO> getMatchListByOrgId(String orgId) {
		List<MatchVO> matchList = matchDAO.getMatchListByOrgId(orgId);
		if(matchList == null) {
			matchList = new ArrayList<MatchVO>();
		}
		
		return matchList;
	}

	@Override
	public MatchVO getMatchById(String matchId) {
		return matchDAO.getMatchById(matchId);
	}

	@Override
	public boolean deleteMatch(String matchId) {
		return matchDAO.deleteMatch(matchId);
	}

	@Override
	@Transactional
	public boolean saveMatchResult(MatchResultBO matchResult) {
		String matchId = matchResult.getMatchId();
		
		// step 1: update match result
		MatchResultVO matchResultBasic = matchDAO.getMatchResultBasicByMatchId(matchId);
		boolean isMatchResultCreate = (matchResultBasic == null);
		
		int goalFor = matchResult.getHomeScore(), goalAgainst = matchResult.getGuestScore();
		MatchResultVO newMatchResultBasic = new MatchResultVO();
		newMatchResultBasic.setMatchId(matchId);
		newMatchResultBasic.setGoalFor(goalFor);
		newMatchResultBasic.setGoalAgainst(goalAgainst);
		newMatchResultBasic.setResult(goalFor > goalAgainst ? Constants.MATCH_RESULT_WON : (goalFor < goalAgainst ? Constants.MATCH_RESULT_LOST : Constants.MATCH_RESULT_DRAWN));
		matchDAO.handleMatchResultBasic(newMatchResultBasic, isMatchResultCreate);
		
		// step 2: update match goal result
		List<MatchGoalVO> goalList = new ArrayList<MatchGoalVO>();
		if(matchResult.getHomeScoreData() != null) {
			for(HomeScoreVO score : matchResult.getHomeScoreData()) {
				MatchGoalVO goal = new MatchGoalVO();
				
				BeanUtils.copyProperties(score, goal);
				goal.setMatchId(matchId);
				goal.setOrgId(matchResult.getOrgId());
				goalList.add(goal);
			}
		}
		
		if(matchResult.getGuestScoreData() != null) {
			for(GuestScoreVO score : matchResult.getGuestScoreData()) {
				MatchGoalVO goal = new MatchGoalVO();
				
				BeanUtils.copyProperties(score, goal);
				goal.setMatchId(matchId);
				goalList.add(goal);
			}
		}
		
		matchDAO.handleMatchGoal(matchId, goalList);
		
		// step 3: update match foul result
		List<MatchFoulVO> foulList = new ArrayList<MatchFoulVO>();
		if(matchResult.getHomeFoulData() != null) {
			for(HomeFoulVO homeFoul : matchResult.getHomeFoulData()) {
				MatchFoulVO foul = new MatchFoulVO();
				
				BeanUtils.copyProperties(homeFoul, foul);
				foul.setMatchId(matchId);
				foul.setOrgId(matchResult.getOrgId());
				foulList.add(foul);
			}
		}
		
		if(matchResult.getGuestFoulData() != null) {
			for(GuestFoulVO guestFoul : matchResult.getGuestFoulData()) {
				MatchFoulVO foul = new MatchFoulVO();
				
				BeanUtils.copyProperties(guestFoul, foul);
				foul.setMatchId(matchId);
				foulList.add(foul);
			}
		}
		
		matchDAO.handleMatchFoul(matchId, foulList);
		
		// step 4: update match sub result
		List<MatchSubVO> subList = new ArrayList<MatchSubVO>();
		if(matchResult.getHomeSubData() != null) {
			for(HomeSubVO homeSub : matchResult.getHomeSubData()) {
				MatchSubVO sub = new MatchSubVO();
				
				BeanUtils.copyProperties(homeSub, sub);
				sub.setMatchId(matchId);
				sub.setOrgId(matchResult.getOrgId());
				subList.add(sub);
			}
		}
		
		if(matchResult.getGuestSubData() != null) {
			for(GuestSubVO guestSub : matchResult.getGuestSubData()) {
				MatchSubVO sub = new MatchSubVO();
				
				BeanUtils.copyProperties(guestSub, sub);
				sub.setMatchId(matchId);
				subList.add(sub);
			}
		}
		
		matchDAO.handleMatchSub(matchId, subList);
		
		// step 5: update match data
		if(matchResult.getMatchData() != null) {
			List<MatchDataVO> dataList = matchResult.getMatchData();
			for(MatchDataVO matchData : dataList) {
				matchData.setMatchId(matchId);
			}
			
			matchDAO.handleMatchData(matchId, dataList);
			
			// per pad request, also insert match data into match data log table
			matchDAO.handleMatchDataLog(matchId, dataList);
		}
		
		// step 6: update match assets
		List<MatchAssetVO> assetList = new ArrayList<MatchAssetVO>();
		if(matchResult.getMatchPhotoAssets() != null) {
			for(MatchAssetVO photo : matchResult.getMatchPhotoAssets()) {
				photo.setMatchId(matchId);
				photo.setType(Constants.MATCH_ASSET_PHOTO_TYPE);
				photo.setCreatorId(matchResult.getCreatorId());
				photo.setOrgId(matchResult.getOrgId());
				
				assetList.add(photo);
			}
		}
		
		if(matchResult.getMatchAttachAssets() != null) {
			for(MatchAssetVO attach : matchResult.getMatchAttachAssets()) {
				attach.setMatchId(matchId);
				attach.setType(Constants.MATCH_ASSET_ATTACH_TYPE);
				attach.setCreatorId(matchResult.getCreatorId());
				attach.setOrgId(matchResult.getOrgId());
				
				assetList.add(attach);
			}
		}
		
		matchDAO.handleMatchAsset(matchId, assetList);
		
		// step 7: update match status
		matchDAO.updateMatchEndStatus(matchId, 1);
		
		return true;
	}

	@Override
	public List<MatchGoalVO> getMatchGoalsByMatchId(String matchId) {
		List<MatchGoalVO> goalList = matchDAO.getMatchGoalsByMatchId(matchId);
		
		if(goalList == null) {
			goalList = new ArrayList<MatchGoalVO>();
		}
		
		return goalList;
	}

	@Override
	public List<MatchFoulVO> getMatchFoulsByMatchId(String matchId) {
		List<MatchFoulVO> foulList = matchDAO.getMatchFoulsByMatchId(matchId);
		
		if(foulList == null) {
			foulList = new ArrayList<MatchFoulVO>();
		}
		
		return foulList;
	}

	@Override
	public List<MatchSubVO> getMatchSubsByMatchId(String matchId) {
		List<MatchSubVO> subList = matchDAO.getMatchSubsByMatchId(matchId);
		
		if(subList == null) {
			subList = new ArrayList<MatchSubVO>();
		}
		
		return subList;
	}

	@Override
	public List<MatchDataVO> getMatchDataByMatchId(String matchId) {
		List<MatchDataVO> dataList = matchDAO.getMatchDataByMatchId(matchId);
		
		if(dataList == null) {
			dataList = new ArrayList<MatchDataVO>();
		}
		
		for(MatchDataVO data : dataList) {
			convertMatchDataList(data);
		}
		
		return dataList;
	}

	@Override
	public List<MatchAssetVO> getMatchAssetsByMatchId(String matchId) {
		List<MatchAssetVO> assetList = matchDAO.getMatchAssetsByMatchId(matchId);
		
		if(assetList == null) {
			assetList = new ArrayList<MatchAssetVO>();
		}
		
		return assetList;
	}
	
	private void convertMatchDataList(MatchDataVO matchData) {
		Map<String, String> itemMapping = new HashMap<String, String>();
		List<MatchDataItemVO> matchDataItemList = matchData.getItemList();
		
		if(matchDataItemList != null && matchDataItemList.size() > 0) {
			for(MatchDataItemVO item : matchDataItemList) {
				itemMapping.put(item.getItemName(), item.getItemValue());
			}
		}
		
		matchData.setItemMapping(itemMapping);
	}

	@Override
	public MatchVO getLatestEndedMatchByOrgId(String orgId) {
		return matchDAO.getLatestEndedMatchByOrgId(orgId);
	}

	@Override
	public List<MatchVO> getComingMatchesByOrgId(String orgId) {
		List<MatchVO> matches = matchDAO.getComingMatchesByOrgId(orgId);
		if(matches == null) {
			matches = new ArrayList<MatchVO>();
		}
		
		return matches;
	}
}
