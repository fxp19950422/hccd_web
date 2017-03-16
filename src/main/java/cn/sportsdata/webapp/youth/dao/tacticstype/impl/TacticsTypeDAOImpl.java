package cn.sportsdata.webapp.youth.dao.tacticstype.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.sportsdata.webapp.youth.dao.BaseDAO;
import org.springframework.stereotype.Repository;

import cn.sportsdata.webapp.youth.common.vo.TacticsTypeVO;
import cn.sportsdata.webapp.youth.dao.tacticstype.TacticsTypeDAO;

@Repository
public class TacticsTypeDAOImpl extends BaseDAO implements TacticsTypeDAO {
	private static final String getTacticsTypeByID = "getTacticsTypeByID";
	private static final String getTacticsTypeCommon = "getTacticsTypeCommon";
	private static final String getTacticsTypeByOrgId = "getTacticsTypeByOrgId";
	
	@Override
	public TacticsTypeVO getTacticsTypeByID(long id) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne(getSqlNameSpace(getTacticsTypeByID), id);
	}

	@Override
	public List<TacticsTypeVO> getTacticsTypeCommon(String category) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(getSqlNameSpace(getTacticsTypeCommon), category);
	}

	@Override
	public List<TacticsTypeVO> getTacticsTypeByOrgId(String org_id, String category) {
		// TODO Auto-generated method stub
		Map<String, String> paramList = new HashMap<String, String>();
		paramList.put("org_id", org_id);
		paramList.put("category", category);
		return sqlSessionTemplate.selectList(getSqlNameSpace(getTacticsTypeByOrgId), paramList);
	}

}
