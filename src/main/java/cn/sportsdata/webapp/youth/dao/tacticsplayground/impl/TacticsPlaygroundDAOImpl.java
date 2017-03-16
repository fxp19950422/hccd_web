package cn.sportsdata.webapp.youth.dao.tacticsplayground.impl;

import java.util.List;

import cn.sportsdata.webapp.youth.dao.BaseDAO;
import org.springframework.stereotype.Repository;

import cn.sportsdata.webapp.youth.common.vo.TacticsPlaygroundVO;
import cn.sportsdata.webapp.youth.dao.tacticsplayground.TacticsPlaygroundDAO;

@Repository
public class TacticsPlaygroundDAOImpl extends BaseDAO implements TacticsPlaygroundDAO {
	private static final String get_Tactics_Playground_By_ID = "getTacticsPlaygroundByID";
	private static final String get_Tactics_Playground_Common = "getTacticsPlaygroundCommon";

	
	@Override
	public TacticsPlaygroundVO getTacticsPlaygroundByID(long id) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne(getSqlNameSpace(get_Tactics_Playground_By_ID), id);
	}

	@Override
	public List<TacticsPlaygroundVO> getTacticsPlaygroundCommon(String category) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(getSqlNameSpace(get_Tactics_Playground_Common), category);
	}

}
