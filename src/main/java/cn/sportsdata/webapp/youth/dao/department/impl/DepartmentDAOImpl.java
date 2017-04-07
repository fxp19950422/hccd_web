package cn.sportsdata.webapp.youth.dao.department.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.sportsdata.webapp.youth.common.vo.DepartmentVO;
import cn.sportsdata.webapp.youth.dao.BaseDAO;
import cn.sportsdata.webapp.youth.dao.department.DepartmentDAO;

@Repository
public class DepartmentDAOImpl extends BaseDAO implements DepartmentDAO{

	private static final String GET_DEPARTMENT_LIST = "getDepartmentList";
	
	private static final String GET_DEPARTMENT_FROM_LOGIN_ID = "getDepartmentFromLoginId";
	
	@Override
	public List<DepartmentVO> getDepartmentList(String hospitalId, String departmentId) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("hospitalId", hospitalId);
		param.put("departmentId", departmentId);
		return sqlSessionTemplate.selectList(getSqlNameSpace(GET_DEPARTMENT_LIST),param);
	}

	@Override
	public DepartmentVO getDepartmentFromLoginId(String userId) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("userId", userId);
		return sqlSessionTemplate.selectOne(getSqlNameSpace(GET_DEPARTMENT_FROM_LOGIN_ID),param);
	}
	

}

