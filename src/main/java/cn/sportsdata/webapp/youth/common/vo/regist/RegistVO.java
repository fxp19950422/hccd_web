package cn.sportsdata.webapp.youth.common.vo.regist;

import java.io.Serializable;

public class RegistVO implements Serializable {
	
	private static final long serialVersionUID = 6687419490823072447L;

	private String id;
	private String userName;
	private String mobile;
	private String password;
	private String verifyCode;
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
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getVerifyCode() {
		return verifyCode;
	}
	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}
	
	
	
}
