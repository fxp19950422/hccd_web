package cn.sportsdata.webapp.youth.dao.match.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.sportsdata.webapp.youth.common.vo.match.MatchDataVO;
import cn.sportsdata.webapp.youth.dao.BaseDAO;
import cn.sportsdata.webapp.youth.dao.match.MatchDataDAO;

@Repository
public class MatchDataDAOImpl extends BaseDAO implements MatchDataDAO {
	private static final String SELECT_MATCH_DATA = "getMatchDataByMatchIds";
	private static final String SELECT_MATCH_TIME_DATA = "getMatchTimeDataByMatchIds";

	@Override
	public List<MatchDataVO> getMatchDataInfoByMatchList(List<String> matchIdList) {
		
		return sqlSessionTemplate.selectList(getSqlNameSpace(SELECT_MATCH_DATA), matchIdList);
	}

	@Override
	public List<MatchDataVO> getMatchTimeDataInfoByMatchList(List<String> matchIdList) {
		return sqlSessionTemplate.selectList(getSqlNameSpace(SELECT_MATCH_TIME_DATA), matchIdList);
	}
	
}
