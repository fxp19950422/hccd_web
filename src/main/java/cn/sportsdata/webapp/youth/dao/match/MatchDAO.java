package cn.sportsdata.webapp.youth.dao.match;

import java.util.List;

import cn.sportsdata.webapp.youth.common.vo.match.MatchAssetVO;
import cn.sportsdata.webapp.youth.common.vo.match.MatchDataVO;
import cn.sportsdata.webapp.youth.common.vo.match.MatchFoulVO;
import cn.sportsdata.webapp.youth.common.vo.match.MatchGoalVO;
import cn.sportsdata.webapp.youth.common.vo.match.MatchResultVO;
import cn.sportsdata.webapp.youth.common.vo.match.MatchSubVO;
import cn.sportsdata.webapp.youth.common.vo.match.MatchTacticsVO;
import cn.sportsdata.webapp.youth.common.vo.match.MatchVO;

public interface MatchDAO {
	boolean handleMatchBasic(MatchVO matchVO, boolean isCreate);
	boolean handleMatchResultBasic(MatchResultVO matchResultVO, boolean isCreate);
	List<MatchVO> getMatchListByOrgId(String orgId);
	MatchVO getMatchById(String matchId);
	MatchResultVO getMatchResultBasicByMatchId(String matchId);
	MatchTacticsVO getMatchTacticsByMatchId(String matchId);
	boolean deleteMatch(String matchId);
	boolean handleMatchTactics(MatchTacticsVO matchTacticsVO);
	long saveMatchStartPlayerList(String matchId, MatchTacticsVO matchTacticsVO);
	long clearMatchStartPlayerList(String matchId);
	List<MatchGoalVO> getMatchGoalsByMatchId(String matchId);
	boolean handleMatchGoal(String matchId, List<MatchGoalVO> goalList);
	List<MatchFoulVO> getMatchFoulsByMatchId(String matchId);
	boolean handleMatchFoul(String matchId, List<MatchFoulVO> foulList);
	List<MatchSubVO> getMatchSubsByMatchId(String matchId);
	boolean handleMatchSub(String matchId, List<MatchSubVO> subList);
	List<MatchDataVO> getMatchDataByMatchId(String matchId);
	boolean handleMatchData(String matchId, List<MatchDataVO> dataList);
	List<MatchAssetVO> getMatchAssetsByMatchId(String matchId);
	boolean handleMatchAsset(String matchId, List<MatchAssetVO> assetList);
	boolean updateMatchEndStatus(String matchId, int isEnd);
	MatchVO getLatestEndedMatchByOrgId(String orgId);
	List<MatchVO> getComingMatchesByOrgId(String orgId);
	boolean deleteMatchTacticsByMatchId(String matchId);
	boolean handleMatchDataLog(String matchId, List<MatchDataVO> dataList);
	List<MatchDataVO> getMatchDataLogByMatchId(String matchId);
}
