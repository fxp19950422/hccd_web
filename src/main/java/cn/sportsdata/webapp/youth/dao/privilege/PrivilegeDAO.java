package cn.sportsdata.webapp.youth.dao.privilege;

import java.util.List;

import cn.sportsdata.webapp.youth.common.vo.privilege.PrivilegeVO;
import cn.sportsdata.webapp.youth.common.vo.privilege.UserPrivilegeTypeAssociationVO;

public interface PrivilegeDAO {
	List<PrivilegeVO> getUserPrivileges(String userID, String orgID);

	int setUsePrivilege(UserPrivilegeTypeAssociationVO userPrivilegeVO);

	int deleteUserPrivilegeType(long userID);

	UserPrivilegeTypeAssociationVO getUserPrivilegeType(long userID);
}
