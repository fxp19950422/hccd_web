package cn.sportsdata.webapp.youth.service.login;

import cn.sportsdata.webapp.youth.common.vo.login.LoginVO;

public interface LoginService {
	public LoginVO getUserByUserName(String userName);

	public LoginVO getUserByEmail(String userMail);
	
	public LoginVO getUserByID(long userID);

	public int updateUser(LoginVO user);
	
	public boolean checkUserNameExist(String userName);
    public int setUserPrivilege(LoginVO user, long privilegeId);


	public int addLoginCount(long userID);
}
