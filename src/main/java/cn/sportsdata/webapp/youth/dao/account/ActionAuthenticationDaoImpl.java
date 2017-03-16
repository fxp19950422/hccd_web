/**
 * 
 */
package cn.sportsdata.webapp.youth.dao.account;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.sportsdata.webapp.youth.common.vo.account.ActionAuthenticationVO;
import cn.sportsdata.webapp.youth.dao.BaseDAO;

/**
 * @author king
 *
 */
@Repository
public class ActionAuthenticationDaoImpl extends BaseDAO implements ActionAuthenticationDAO {
	
	private static final String SELECT_ACTION_AUTHENTICATION_BY_ID = "selectActionAuthenticationBYID";
	private static final String SELECT_ACTION_AUTHENTICATION_BY_USER_ID = "selectActionAuthenticationBYUserID";
	private static final String UPDATE_ACTION_AUTHENTICATION_BY_ID = "updateActionAuthenticationByID";
	private static final String INSERT_ACTION_AUTHENTICATION = "insertActionAuthentication";
	private static final String SELECT_ACTION_AUTHENTICATION_BY_RANMD5 = "selectActionAuthenticationByRanMd5";

	@Override
	public ActionAuthenticationVO getActionAuthenticationByID(long action_id, String action_type) {
		try {
			Map paraMap = new HashMap();
			paraMap.put("action_id", action_id);
			paraMap.put("action_type", action_type);
    		return  (ActionAuthenticationVO) sqlSessionTemplate.selectList(getSqlNameSpace(SELECT_ACTION_AUTHENTICATION_BY_ID), paraMap).get(0);
    	} catch(Exception e) {
    		return null;
    	}
	}
	
	@Override
	public ActionAuthenticationVO getActionAuthenticationByUserId(String userId, String action_type) {
		try {
			Map paraMap = new HashMap();
			paraMap.put("user_id", userId);
			paraMap.put("action_type", action_type);
    		return (ActionAuthenticationVO) sqlSessionTemplate.selectList(getSqlNameSpace(SELECT_ACTION_AUTHENTICATION_BY_USER_ID), paraMap).get(0);
    	} catch(Exception e) {
    		return null;
    	}
	}
	
	@Override
	public ActionAuthenticationVO getActionAuthenticationByRanMd5(String random_md5, String action_type) {
		try {
			Map paraMap = new HashMap();
			paraMap.put("random_md5", random_md5);
			paraMap.put("action_type", action_type);
    		return  (ActionAuthenticationVO) sqlSessionTemplate.selectList(getSqlNameSpace(SELECT_ACTION_AUTHENTICATION_BY_RANMD5), paraMap).get(0);
    	} catch(Exception e) {
    		return null;
    	}
	}
	
	@Override
	public long updateActionAuthentication(ActionAuthenticationVO actionAuthentication) {
		// TODO Auto-generated method stub
		try {
			return sqlSessionTemplate.update(getSqlNameSpace(UPDATE_ACTION_AUTHENTICATION_BY_ID), actionAuthentication);
    	} catch(Exception e) {
    		e.printStackTrace();
    		return -1;
    	}
	}
	
	@Override
	public int insertActionAuthentication(ActionAuthenticationVO actionAuthentication) {
		// TODO Auto-generated method stub
		try {
			return sqlSessionTemplate.insert(getSqlNameSpace(INSERT_ACTION_AUTHENTICATION), actionAuthentication);
    	} catch(Exception e) {
    		e.printStackTrace();
    		return -1;
    	}
	}
}
