package cn.sportsdata.webapp.youth.dao.department;
import java.util.List;

import cn.sportsdata.webapp.youth.common.vo.DepartmentVO;

public interface DepartmentDAO{
	List<DepartmentVO> getDepartmentList(String hospitalId, String departmentId);

}

