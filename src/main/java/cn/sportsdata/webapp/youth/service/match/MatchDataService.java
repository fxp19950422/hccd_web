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

public interface MatchDataService {
	
	public List<MatchDataVO> getMatchDataInfoByMatchList(List<String> matchIdList);
}
