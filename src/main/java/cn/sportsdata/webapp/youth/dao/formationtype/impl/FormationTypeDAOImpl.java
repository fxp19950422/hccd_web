package cn.sportsdata.webapp.youth.dao.formationtype.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.sportsdata.webapp.youth.common.vo.FormationTypeVO;
import cn.sportsdata.webapp.youth.dao.BaseDAO;
import cn.sportsdata.webapp.youth.dao.formationtype.FormationTypeDAO;

@Repository
public class FormationTypeDAOImpl extends BaseDAO implements FormationTypeDAO {
	private static final String getFormationTypeByID = "getFormationTypeByID";
	private static final String getFormationTypeCommon = "getFormationTypeCommon";
	private static final String getFormationTypeByOrgId = "getFormationTypeByOrgId";
	
	@Override
	public FormationTypeVO getFormationTypeByID(long id) {
		return sqlSessionTemplate.selectOne(getSqlNameSpace(getFormationTypeByID), id);
	}

	@Override
	public List<FormationTypeVO> getFormationTypeCommon(String category) {
		return sqlSessionTemplate.selectList(getSqlNameSpace(getFormationTypeCommon), category);
	}

	@Override
	public List<FormationTypeVO> getFormationTypeByOrgId(String orgId, String category) {
		Map<String, String> paramList = new HashMap<String, String>();
		paramList.put("org_id", orgId);
		paramList.put("category", category);
		return sqlSessionTemplate.selectList(getSqlNameSpace(getFormationTypeByOrgId), paramList);
	}

}
