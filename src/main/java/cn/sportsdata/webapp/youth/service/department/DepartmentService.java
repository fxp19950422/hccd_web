package cn.sportsdata.webapp.youth.service.department;

import java.util.List;
import java.util.Map;

import cn.sportsdata.webapp.youth.common.vo.DepartmentVO;

public interface DepartmentService {
	List<DepartmentVO> getDepartmentList(String hospitalId, String departmentId);
	
	public DepartmentVO getDepartmentFromLoginId(String userId) ;
	
	 Map<String,String> getUserRoleByLoginId(String userId);

}
