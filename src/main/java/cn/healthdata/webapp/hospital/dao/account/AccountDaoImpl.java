/**
 * 
 */
package cn.healthdata.webapp.hospital.dao.account;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.healthdata.webapp.hospital.dao.BaseDAO;
import cn.healthdata.webapp.hospital.vo.LoginVO;

/**
 * @author king
 *
 */
@Repository
public class AccountDaoImpl extends BaseDAO implements AccountDao {
	
	private static final String SELECT_ACCOUNTS_BY_ORG = "getLoginUserInfo";
	
	private static final String INSERT_USER_INFO = "insertLoginInfo";
	
	private static final String UPDATE_USER_INFO = "updateLoginInfo";
	
	private static final String GET_INVITE_CODE = "getInviteCode";

	@Override
	public LoginVO getLoginInfo(String phone) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("user_phone", phone);
		return this.sqlSessionTemplate.selectOne(SELECT_ACCOUNTS_BY_ORG, map);
	}

	@Override
	public int insertLoginInfo(LoginVO loginVO) {
		return this.sqlSessionTemplate.insert(INSERT_USER_INFO, loginVO);
	}

	@Override
	public int updateLoginInfo(LoginVO loginVO) {
		return this.sqlSessionTemplate.update(UPDATE_USER_INFO, loginVO);
	}

	@Override
	public List<String> getInviteCode(String inviteCode) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("invite_code", inviteCode);
		return this.sqlSessionTemplate.selectList(GET_INVITE_CODE, map);
	}

	

	
}
