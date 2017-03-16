/**
 * 
 */
package cn.sportsdata.webapp.youth.dao.login;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.sportsdata.webapp.youth.common.vo.login.LoginVO;
import cn.sportsdata.webapp.youth.dao.BaseDAO;

/**
 * @author king
 *
 */
@Repository
public class LoginDaoImpl extends BaseDAO implements LoginDao {
	private static final String SELECT_USER_BY_USERNAME_PASSWORD = "selectUserByUserName";
	private static final String SELECT_USER_BY_ID = "selectUserByID";
	private static final String UPDATE_USER_BY_ID = "updateUserByID";
	private static final String INSERT_USER_BY_ID = "createUser";
	private static final String GET_USER_BY_NAME = "selectUserByName";
	private static final String SELECT_USER_BY_EMAIL = "selectUserByEmail";
	private static final String DELETE_USER_BY_ID = "deleteUserById";
	private static final String GET_EXPERT_LIST = "getExpertList";
	private static final String ADD_LOGIN_COUNT = "addLoginCount";

	/* (non-Javadoc)
	 * @see cn.sportsdata.webapp.soccerda.dao.UserDAO#getUserByUserNamePassword(java.lang.String, java.lang.String)
	 */
	@Override
	public LoginVO getUserByUserName(String userName) {
		try {
			Map paraMap = new HashMap();
			paraMap.put("userName", userName);
    		return sqlSessionTemplate.selectOne(getSqlNameSpace(SELECT_USER_BY_USERNAME_PASSWORD), paraMap);
    	} catch(Exception e) {
    		return null;
    	}
	}

	@Override
	public LoginVO getUserByEmail(String userEmail) {
		try {
			Map paraMap = new HashMap();
			paraMap.put("email", userEmail);
    		return sqlSessionTemplate.selectOne(getSqlNameSpace(SELECT_USER_BY_EMAIL), paraMap);
    	} catch(Exception e) {
    		return null;
    	}
	}
	
	@Override
	public LoginVO getUserByID(long userID) {
		try {
			Map paraMap = new HashMap();
			paraMap.put("id", new Long(userID));
    		return sqlSessionTemplate.selectOne(getSqlNameSpace(SELECT_USER_BY_ID), paraMap);
    	} catch(Exception e) {
    		e.printStackTrace();
    		return null;
    	}
	}

	@Override
    public int updateUser(LoginVO user) {
		try {
			return sqlSessionTemplate.update(getSqlNameSpace(UPDATE_USER_BY_ID), user);
    	} catch(Exception e) {
    		e.printStackTrace();
    		return -1;
    	}
	}
	
	@Override
    public int createUser(LoginVO user) {
		try {
			if(user.getUserName() == null || user.getUserName().length() < 6) {
				return -2;
			}
			return sqlSessionTemplate.insert(getSqlNameSpace(INSERT_USER_BY_ID), user);
    	} catch(Exception e) {
    		e.printStackTrace();
    		return -1;
    	}
	}
	
	@Override
    public int deleteUserById(Long id) {
		try {
			return sqlSessionTemplate.delete(getSqlNameSpace(DELETE_USER_BY_ID), id);
    	} catch(Exception e) {
    		e.printStackTrace();
    		return -1;
    	}
	}
	
	@Override
	public boolean checkUserNameExist(String userName) {
		try {
			List<LoginVO> userList = sqlSessionTemplate.selectList(getSqlNameSpace(GET_USER_BY_NAME), userName);
			if (userList.size() > 0) {
				return true;
			} else {
				return false;
			}
    	} catch(Exception e) {
    		e.printStackTrace();
    		return false;
    	}
	}
	
	@Override
	public int addLoginCount(long userID) {
		try {
			Map paraMap = new HashMap();
			paraMap.put("id", new Long(userID));
    		return sqlSessionTemplate.update(getSqlNameSpace(ADD_LOGIN_COUNT), paraMap);
    	} catch(Exception e) {
    		e.printStackTrace();
    		return 0;
    	}
	}
}
