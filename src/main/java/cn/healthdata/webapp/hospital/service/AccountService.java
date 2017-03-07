package cn.healthdata.webapp.hospital.service;

import java.util.List;

import cn.healthdata.webapp.hospital.vo.LoginVO;

public interface AccountService {
	
	public LoginVO getLoginInfo(LoginVO loginVO);
	
	public void upsertLoginInfo(LoginVO loginVO);
	
	public LoginVO getLoginInfo(String phone);
	
	public List<String> getInviteCode(String inviteCode);
}
