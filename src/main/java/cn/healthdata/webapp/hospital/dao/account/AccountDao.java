package cn.healthdata.webapp.hospital.dao.account;

import java.util.List;

import cn.healthdata.webapp.hospital.vo.LoginVO;

public interface AccountDao{
	
	
	public LoginVO getLoginInfo(String phone);
	
	public int insertLoginInfo(LoginVO loginVO);
	
	public int updateLoginInfo(LoginVO loginVO);
	
	public List<String> getInviteCode(String inviteCode);
}
