package cn.sportsdata.webapp.youth.dao.login;

import java.util.List;

import cn.sportsdata.webapp.youth.common.vo.login.LoginVO;

public interface LoginDao {
	
	
	/**
     * get LoginVO by user ID
     * @param userName String
     * @return LoginVO
     */
    LoginVO getUserByID(long userID);
    
    /**
     * get LoginVO by user name
     * @param userName String
     * @return LoginVO
     */
    LoginVO getUserByUserName(String userName);
    
    /**
     * get LoginVO by email
     * @param email String
     * @return LoginVO
     */   
    LoginVO getUserByEmail(String userEmail);
    
    /**
     * update user info
     * @param user LoginVO
     * @return boolean
     */
    int updateUser(LoginVO user);
    
    int createUser(LoginVO user);
    
    boolean checkUserNameExist(String userName);

	int deleteUserById(Long id);

	
	int addLoginCount(long userID);
}
