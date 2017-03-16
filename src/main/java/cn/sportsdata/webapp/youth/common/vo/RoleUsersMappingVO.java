package cn.sportsdata.webapp.youth.common.vo;

import java.util.List;

public class RoleUsersMappingVO {
	private String roleName;
	
	private String roleNameStr;
	
	public String getRoleNameStr() {
		return roleNameStr;
	}

	public void setRoleNameStr(String roleNameStr) {
		this.roleNameStr = roleNameStr;
	}

	private List<UserVO> users;

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public List<UserVO> getUsers() {
		return users;
	}

	public void setUsers(List<UserVO> users) {
		this.users = users;
	}
	
}
