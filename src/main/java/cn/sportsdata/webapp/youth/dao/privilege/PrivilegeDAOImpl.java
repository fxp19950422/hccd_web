package cn.sportsdata.webapp.youth.dao.privilege;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.sportsdata.webapp.youth.dao.BaseDAO;
import org.springframework.stereotype.Repository;

import cn.sportsdata.webapp.youth.common.vo.privilege.PrivilegeVO;
import cn.sportsdata.webapp.youth.common.vo.privilege.UserPrivilegeTypeAssociationVO;

@Repository
public class PrivilegeDAOImpl extends BaseDAO implements PrivilegeDAO {
	private static final String GET_USER_PRIVILEGES = "getUserPrivileges";
	private static final String GET_USER_PRIVILEGE_TYPE = "getUserPrivilegeType";
	private static final String SET_USER_PRIVILEGE_TYPE = "setUserPrivilegeType";
	private static final String UPDATE_USER_PRIVILEGE_TYPE = "updateUserPrivilegeType";
	private static final String DELETE_USER_PRIVILEGE_TYPE = "deleteUserPrivilegeType";
	
	@Override
	public List<PrivilegeVO> getUserPrivileges(String userID, String orgID) {
		
		Map<String, String> param = new HashMap<String, String>();
		param.put("userID", userID);
		param.put("orgID", orgID);
		
		return sqlSessionTemplate.selectList(getSqlNameSpace(GET_USER_PRIVILEGES), param);
	}
	
	@Override
	public UserPrivilegeTypeAssociationVO getUserPrivilegeType(long userID) {
		try {
			return sqlSessionTemplate.selectOne(getSqlNameSpace(GET_USER_PRIVILEGE_TYPE), userID);
		} catch (Exception e) {
    		e.printStackTrace();
    		return null;
		}
	}
	
	@Override
	public int deleteUserPrivilegeType(long userID) {
		try {
			return sqlSessionTemplate.delete(getSqlNameSpace(DELETE_USER_PRIVILEGE_TYPE), userID);
		} catch (Exception e) {
    		e.printStackTrace();
    		return -1;
		}
	}

	@Override
	public int setUsePrivilege(UserPrivilegeTypeAssociationVO userPrivilegeTypeVO) {
		try {
			UserPrivilegeTypeAssociationVO userPrivilegeType = sqlSessionTemplate.selectOne(getSqlNameSpace(GET_USER_PRIVILEGE_TYPE), userPrivilegeTypeVO.getUserId());
			if (userPrivilegeType == null) {
				return sqlSessionTemplate.insert(getSqlNameSpace(SET_USER_PRIVILEGE_TYPE), userPrivilegeTypeVO);
			} else {
				return sqlSessionTemplate.update(getSqlNameSpace(UPDATE_USER_PRIVILEGE_TYPE), userPrivilegeTypeVO);
			}
    	} catch(Exception e) {
    		e.printStackTrace();
    		return -1;
    	}
	}
}
