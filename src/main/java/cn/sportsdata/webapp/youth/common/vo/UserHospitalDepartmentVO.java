package cn.sportsdata.webapp.youth.common.vo;

import java.io.Serializable;

public class UserHospitalDepartmentVO extends BaseVO implements Serializable {
	private static final long serialVersionUID = -6103938737922583642L;
	
	private String id;
	
	private String userId;
	private String hospitalId;

	private String departmentId;

	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	
	
}
