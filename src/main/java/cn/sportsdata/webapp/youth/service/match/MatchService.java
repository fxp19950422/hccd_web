package cn.sportsdata.webapp.youth.service.match;

import java.util.List;

import cn.sportsdata.webapp.youth.common.bo.MatchResultBO;
import cn.sportsdata.webapp.youth.common.vo.match.MatchAssetVO;
import cn.sportsdata.webapp.youth.common.vo.match.MatchDataVO;
import cn.sportsdata.webapp.youth.common.vo.match.MatchFoulVO;
import cn.sportsdata.webapp.youth.common.vo.match.MatchGoalVO;
import cn.sportsdata.webapp.youth.common.vo.match.MatchResultVO;
import cn.sportsdata.webapp.youth.common.vo.match.MatchSubVO;
import cn.sportsdata.webapp.youth.common.vo.match.MatchTacticsVO;
import cn.sportsdata.webapp.youth.common.vo.match.MatchVO;

public interface MatchService {
	boolean handleMatch(MatchVO matchVO, MatchResultVO matchResultVO, MatchTacticsVO matchTacticsVO, boolean isCreate);
	List<MatchVO> getMatchListByOrgId(String orgId);
	MatchVO getMatchById(String matchId);
	boolean deleteMatch(String matchId);
	boolean saveMatchResult(MatchResultBO matchResult);
	List<MatchGoalVO> getMatchGoalsByMatchId(String matchId);
	List<MatchFoulVO> getMatchFoulsByMatchId(String matchId);
	List<MatchSubVO> getMatchSubsByMatchId(String matchId);
	List<MatchDataVO> getMatchDataByMatchId(String matchId);
	List<MatchAssetVO> getMatchAssetsByMatchId(String matchId);
	MatchVO getLatestEndedMatchByOrgId(String orgId);
	List<MatchVO> getComingMatchesByOrgId(String orgId);
}
