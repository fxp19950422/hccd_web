package cn.sportsdata.webapp.youth.dao.match.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import cn.sportsdata.webapp.youth.common.vo.match.MatchAssetVO;
import cn.sportsdata.webapp.youth.common.vo.match.MatchDataVO;
import cn.sportsdata.webapp.youth.common.vo.match.MatchFoulVO;
import cn.sportsdata.webapp.youth.common.vo.match.MatchGoalVO;
import cn.sportsdata.webapp.youth.common.vo.match.MatchResultVO;
import cn.sportsdata.webapp.youth.common.vo.match.MatchSubVO;
import cn.sportsdata.webapp.youth.common.vo.match.MatchTacticsVO;
import cn.sportsdata.webapp.youth.common.vo.match.MatchVO;
import cn.sportsdata.webapp.youth.common.vo.starters.StartersPlayerAssociationVO;
import cn.sportsdata.webapp.youth.dao.BaseDAO;
import cn.sportsdata.webapp.youth.dao.match.MatchDAO;

@Repository
public class MatchDAOImpl extends BaseDAO implements MatchDAO {
	private static final String INSERT_MATCH_BASIC = "insertMatchBasic";
	private static final String UPDATE_MATCH_BASIC = "updateMatchBasic";
	private static final String INSERT_MATCH_RESULT_BASIC = "insertMatchResultBasic";
	private static final String UPDATE_MATCH_RESULT_BASIC = "updateMatchResultBasic";
	private static final String GET_MATCH_LIST_BY_ORG_ID = "getMatchesByOrgId";
	private static final String GET_MATCH_BY_ID = "getMatchById";
	private static final String DELETE_MATCH_BY_ID = "deleteMatchById";
	private static final String GET_MATCH_RESULT_BASIC_BY_MATCH_ID = "getMatchResultBasicByMatchId";
	private static final String GET_MATCH_TACTICS_BY_MATCH_ID = "getMatchTacticsByMatchId";
	private static final String INSERT_MATCH_TACTICS = "insertMatchTactics";
	private static final String DELETE_MATCH_TACTICS_BY_MATCH_ID = "deleteMatchTacticsByMatchId";
	private static final String getMatchStartPlayerList = "getMatchStartPlayerList";
	private static final String saveMatchStartPlayerList = "saveMatchStartPlayerList";
	private static final String clearMatchStartPlayerList = "clearMatchStartPlayerList";
	private static final String GET_MATCH_GOALS_BY_MATCH_ID = "getMatchGoalsByMatchId";
	private static final String GET_MATCH_FOULS_BY_MATCH_ID = "getMatchFoulsByMatchId";
	private static final String GET_MATCH_SUBS_BY_MATCH_ID = "getMatchSubsByMatchId";
	private static final String GET_MATCH_DATA_BY_MATCH_ID = "getMatchDataByMatchId";
	private static final String GET_MATCH_DATA_LOG_BY_MATCH_ID = "getMatchDataLogByMatchId";
	private static final String GET_MATCH_ASSETS_BY_MATCH_ID = "getMatchAssetsByMatchId";
	private static final String DELETE_MATCH_GOALS_BY_MATCH_ID = "deleteMatchGoalsByMatchId";
	private static final String DELETE_MATCH_FOULS_BY_MATCH_ID = "deleteMatchFoulsByMatchId";
	private static final String DELETE_MATCH_SUBS_BY_MATCH_ID = "deleteMatchSubsByMatchId";
	private static final String DELETE_MATCH_DATA_BY_MATCH_ID = "deleteMatchDataByMatchId";
	private static final String DELETE_MATCH_DATA_LOG_BY_MATCH_ID = "deleteMatchDataLogByMatchId";
	private static final String DELETE_MATCH_ASSETS_RELATIONS_BY_MATCH_ID = "deleteMatchAssetRelationsByMatchId";
	private static final String DELETE_MATCH_ASSETS = "deleteMatchAssets";
	private static final String INSERT_MATCH_GOALS = "insertMatchGoals";
	private static final String INSERT_MATCH_FOULS = "insertMatchFouls";
	private static final String INSERT_MATCH_SUBS = "insertMatchSubs";
	private static final String INSERT_MATCH_DATA = "insertMatchData";
	private static final String INSERT_MATCH_DATA_LOG = "insertMatchDataLog";
	private static final String INSERT_MATCH_ASSETS = "insertMatchAssets";
	private static final String INSERT_MATCH_ASSET_RELATIONS = "insertMatchAssetRelations";
	private static final String UPDATE_MATCH_END_STATUS = "updateMatchEndStatus";
	private static final String GET_LATEST_ENDED_MATCH_BY_ORG_ID = "getLatestEndedMatchByOrgId";
	private static final String GET_COMING_MATCHES_BY_ORG_ID = "getComingMatchesByOrgId";
	
	@Override
	public boolean handleMatchBasic(MatchVO matchVO, boolean isCreate) {
		int affectedNum = sqlSessionTemplate.insert(getSqlNameSpace(isCreate ? INSERT_MATCH_BASIC : UPDATE_MATCH_BASIC), matchVO);
		return affectedNum == 1;
	}

	@Override
	public boolean handleMatchResultBasic(MatchResultVO matchResultVO, boolean isCreate) {
		int affectedNum = sqlSessionTemplate.insert(getSqlNameSpace(isCreate ? INSERT_MATCH_RESULT_BASIC : UPDATE_MATCH_RESULT_BASIC), matchResultVO);
		return affectedNum == 1;
	}

	@Override
	public List<MatchVO> getMatchListByOrgId(String orgId) {
		return sqlSessionTemplate.selectList(getSqlNameSpace(GET_MATCH_LIST_BY_ORG_ID), orgId);
	}

	@Override
	public MatchVO getMatchById(String matchId) {
		return sqlSessionTemplate.selectOne(getSqlNameSpace(GET_MATCH_BY_ID), matchId);
	}

	@Override
	public boolean deleteMatch(String matchId) {
		int affectedRowNum = sqlSessionTemplate.insert(getSqlNameSpace(DELETE_MATCH_BY_ID), matchId);
		return affectedRowNum == 1;
	}

	@Override
	public MatchResultVO getMatchResultBasicByMatchId(String matchId) {
		return sqlSessionTemplate.selectOne(getSqlNameSpace(GET_MATCH_RESULT_BASIC_BY_MATCH_ID), matchId);
	}

	@Override
	public MatchTacticsVO getMatchTacticsByMatchId(String matchId) {
		return sqlSessionTemplate.selectOne(getSqlNameSpace(GET_MATCH_TACTICS_BY_MATCH_ID), matchId);
	}

	@Override
	public boolean handleMatchTactics(MatchTacticsVO matchTacticsVO) {
		String matchId = matchTacticsVO.getMatchId();
		
		MatchTacticsVO matchTactics = getMatchTacticsByMatchId(matchId);
		if(matchTactics != null) {
			sqlSessionTemplate.insert(getSqlNameSpace(DELETE_MATCH_TACTICS_BY_MATCH_ID), matchId);
		}
		
		Map<String, Object> paramList = new HashMap<String, Object>();
		paramList.put("matchId", matchId);
		paramList.put("taticsIdList", matchTacticsVO.getTacticsIds());
		
		int affectedNum = sqlSessionTemplate.insert(getSqlNameSpace(INSERT_MATCH_TACTICS), paramList);
		return affectedNum == matchTacticsVO.getTacticsIds().size();
	}
	
	@Override
	public long saveMatchStartPlayerList(String matchId, MatchTacticsVO matchTacticsVO) {
		try {
			List<String> tacticsIdList = matchTacticsVO.getTacticsIds();
			List<StartersPlayerAssociationVO> playerList = sqlSessionTemplate.selectList(getSqlNameSpace(getMatchStartPlayerList), tacticsIdList);
			
			if (playerList.size() > 0) {
				Map<String, Object> paramList = new HashMap<String, Object>();
				paramList.put("matchId", matchId);
				paramList.put("playerList", playerList);
				sqlSessionTemplate.update(getSqlNameSpace(clearMatchStartPlayerList), matchId);
				return sqlSessionTemplate.insert(getSqlNameSpace(saveMatchStartPlayerList), paramList);
			} else {
				sqlSessionTemplate.update(getSqlNameSpace(clearMatchStartPlayerList), matchId);
				return 0;
			}

		} catch (Exception e) {
			return 0;
		}
	}

	@Override

	public long clearMatchStartPlayerList(String matchId) {
		return sqlSessionTemplate.update(getSqlNameSpace(clearMatchStartPlayerList), matchId);
	}

	@Override
	public List<MatchGoalVO> getMatchGoalsByMatchId(String matchId) {
		return sqlSessionTemplate.selectList(getSqlNameSpace(GET_MATCH_GOALS_BY_MATCH_ID), matchId);
	}

	@Override
	public boolean handleMatchGoal(String matchId, List<MatchGoalVO> goalList) {
		List<MatchGoalVO> existedGoals = getMatchGoalsByMatchId(matchId);
		boolean needDelete = (existedGoals != null && existedGoals.size() > 0);
		
		if(needDelete) {
			sqlSessionTemplate.insert(getSqlNameSpace(DELETE_MATCH_GOALS_BY_MATCH_ID), matchId);
		}
		
		if(goalList.size() == 0)  return true;
		
		int affectedNum = sqlSessionTemplate.insert(getSqlNameSpace(INSERT_MATCH_GOALS), goalList);
		return affectedNum == goalList.size();
	}

	@Override
	public List<MatchFoulVO> getMatchFoulsByMatchId(String matchId) {
		return sqlSessionTemplate.selectList(getSqlNameSpace(GET_MATCH_FOULS_BY_MATCH_ID), matchId);
	}

	@Override
	public boolean handleMatchFoul(String matchId, List<MatchFoulVO> foulList) {
		List<MatchFoulVO> existedFouls = getMatchFoulsByMatchId(matchId);
		boolean needDelete = (existedFouls != null && existedFouls.size() > 0);
		
		if(needDelete) {
			sqlSessionTemplate.insert(getSqlNameSpace(DELETE_MATCH_FOULS_BY_MATCH_ID), matchId);
		}
		
		if(foulList.size() == 0)  return true;
		
		int affectedNum = sqlSessionTemplate.insert(getSqlNameSpace(INSERT_MATCH_FOULS), foulList);
		return affectedNum == foulList.size();
	}

	@Override
	public List<MatchSubVO> getMatchSubsByMatchId(String matchId) {
		return sqlSessionTemplate.selectList(getSqlNameSpace(GET_MATCH_SUBS_BY_MATCH_ID), matchId);
	}

	@Override
	public boolean handleMatchSub(String matchId, List<MatchSubVO> subList) {
		List<MatchSubVO> existedSubs = getMatchSubsByMatchId(matchId);
		boolean needDelete = (existedSubs != null && existedSubs.size() > 0);
		
		if(needDelete) {
			sqlSessionTemplate.insert(getSqlNameSpace(DELETE_MATCH_SUBS_BY_MATCH_ID), matchId);
		}
		
		if(subList.size() == 0)  return true;
		
		int affectedNum = sqlSessionTemplate.insert(getSqlNameSpace(INSERT_MATCH_SUBS), subList);
		return affectedNum == subList.size();
	}

	@Override
	public List<MatchDataVO> getMatchDataByMatchId(String matchId) {
		return sqlSessionTemplate.selectList(getSqlNameSpace(GET_MATCH_DATA_BY_MATCH_ID), matchId);
	}

	@Override
	public boolean handleMatchData(String matchId, List<MatchDataVO> dataList) {
		List<MatchDataVO> existedDatas = getMatchDataByMatchId(matchId);
		boolean needDelete = (existedDatas != null && existedDatas.size() > 0);
		
		if(needDelete) {
			sqlSessionTemplate.insert(getSqlNameSpace(DELETE_MATCH_DATA_BY_MATCH_ID), matchId);
		}
		
		if(dataList.size() == 0)  return true;
		
		int affectedNum = sqlSessionTemplate.insert(getSqlNameSpace(INSERT_MATCH_DATA), dataList);
		return affectedNum == dataList.size();
	}
	
	@Override
	public List<MatchDataVO> getMatchDataLogByMatchId(String matchId) {
		return sqlSessionTemplate.selectList(getSqlNameSpace(GET_MATCH_DATA_LOG_BY_MATCH_ID), matchId);
	}
	
	@Override
	public boolean handleMatchDataLog(String matchId, List<MatchDataVO> dataList) {
		List<MatchDataVO> existedDatas = getMatchDataLogByMatchId(matchId);
		boolean needDelete = (existedDatas != null && existedDatas.size() > 0);
		
		if(needDelete) {
			sqlSessionTemplate.insert(getSqlNameSpace(DELETE_MATCH_DATA_LOG_BY_MATCH_ID), matchId);
		}
		
		if(dataList.size() == 0)  return true;
		
		int affectedNum = sqlSessionTemplate.insert(getSqlNameSpace(INSERT_MATCH_DATA_LOG), dataList);
		return affectedNum == dataList.size();
	}

	@Override
	public List<MatchAssetVO> getMatchAssetsByMatchId(String matchId) {
		return sqlSessionTemplate.selectList(getSqlNameSpace(GET_MATCH_ASSETS_BY_MATCH_ID), matchId);
	}

	@Override
	public boolean handleMatchAsset(String matchId, List<MatchAssetVO> assetList) {
		List<MatchAssetVO> existedAssets = getMatchAssetsByMatchId(matchId);
		boolean needDelete = (existedAssets != null && existedAssets.size() > 0);
		
		if(needDelete) {
			sqlSessionTemplate.insert(getSqlNameSpace(DELETE_MATCH_ASSETS_RELATIONS_BY_MATCH_ID), matchId);
			sqlSessionTemplate.insert(getSqlNameSpace(DELETE_MATCH_ASSETS), existedAssets);
		}
		
		if(assetList.size() == 0)  return true;
		
		// Generate UUID from Tomcat server, not MySQL itself
		for(MatchAssetVO asset : assetList) {
			asset.setAssetId(UUID.randomUUID().toString());
		}
		
		sqlSessionTemplate.insert(getSqlNameSpace(INSERT_MATCH_ASSETS), assetList);
		sqlSessionTemplate.insert(getSqlNameSpace(INSERT_MATCH_ASSET_RELATIONS), assetList);
		return true;
	}

	@Override
	public boolean updateMatchEndStatus(String matchId, int isEnd) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("matchId", matchId);
		params.put("isEnd", isEnd);
		
		int affectedNum = sqlSessionTemplate.update(getSqlNameSpace(UPDATE_MATCH_END_STATUS), params);
		return affectedNum == 1;
	}

	@Override
	public MatchVO getLatestEndedMatchByOrgId(String orgId) {
		return sqlSessionTemplate.selectOne(getSqlNameSpace(GET_LATEST_ENDED_MATCH_BY_ORG_ID), orgId);
	}

	@Override
	public List<MatchVO> getComingMatchesByOrgId(String orgId) {
		return sqlSessionTemplate.selectList(GET_COMING_MATCHES_BY_ORG_ID, orgId);
	}

	@Override
	public boolean deleteMatchTacticsByMatchId(String matchId) {
		sqlSessionTemplate.insert(getSqlNameSpace(DELETE_MATCH_TACTICS_BY_MATCH_ID), matchId);
		return true;
	}
}
