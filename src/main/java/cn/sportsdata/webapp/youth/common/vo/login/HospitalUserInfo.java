package cn.sportsdata.webapp.youth.common.vo.login;

import cn.sportsdata.webapp.youth.common.vo.BaseVO;

public class HospitalUserInfo extends BaseVO {
	private static final long serialVersionUID = 7922477308103836118L;
	private String id;
	private String userName;
	private String userIdinHospital;
	private String hospitalId;
	private String deptId;
	private String type;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUserIdinHospital() {
		return userIdinHospital;
	}

	public void setUserIdinHospital(String userIdinHospital) {
		this.userIdinHospital = userIdinHospital;
	}


}
