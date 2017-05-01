package cn.sportsdata.webapp.youth.dao.department;
import java.util.List;
import java.util.Map;

import cn.sportsdata.webapp.youth.common.vo.DepartmentVO;

public interface DepartmentDAO{
	List<DepartmentVO> getDepartmentList(String hospitalId, String departmentId);
	DepartmentVO getDepartmentFromLoginId(String userId);
	public Map<String, String> getUserRoleByLoginId(String userId);
}

