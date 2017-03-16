package cn.sportsdata.webapp.youth.common.vo;

import java.io.Serializable;

public class UserOrgRoleVO extends BaseVO implements Serializable {
	private static final long serialVersionUID = -6103938737922583642L;
	
	private String userId;
	private String orgId;

	private long roleId;
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getOrgId() {
		return orgId;
	}
	
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public long getRoleId() {
		return roleId;
	}
	
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}
}
