package cn.sportsdata.webapp.youth.service.privilege;

import java.util.List;

import cn.sportsdata.webapp.youth.common.vo.privilege.PrivilegeVO;

public interface PrivilegeService {
	List<PrivilegeVO> getUserPrivileges(String userID, String orgID);
}
