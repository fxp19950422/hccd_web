package cn.sportsdata.webapp.youth.dao.user.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.sportsdata.webapp.youth.common.vo.UserExtVO;
import cn.sportsdata.webapp.youth.common.vo.UserOrgRoleVO;
import cn.sportsdata.webapp.youth.common.vo.UserVO;
import cn.sportsdata.webapp.youth.common.vo.match.PlayerMatchStatisticsVO;
import cn.sportsdata.webapp.youth.dao.BaseDAO;
import cn.sportsdata.webapp.youth.dao.user.UserDAO;

@Repository
public class UserDAOImpl extends BaseDAO implements UserDAO {
	private static final String GET_BY_ID = "getUserByID";
	private static final String GET_PLAYERS_BY_ORG_ID = "getPlayersByOrgId";
	private static final String INSERT_USER_BASIC_DATA = "insertUserBasicData";
	private static final String INSERT_USER_EXT_DATA = "insertUserExtData";
	private static final String UPDATE_USER_BASIC_DATA = "updateUserBasicData";
	private static final String UPDATE_USER_EXT_DATA = "updateUserExtData";
	private static final String GET_ROLE_ID_BY_ROLE_NAME = "getRoleIdByRoleName";
	private static final String INSERT_USER_ORG_ROLE = "insertUserOrgRole";
	private static final String DELETE_USER_BY_ID = "deleteUserById";
	private static final String getPlayersMatchGoalCount = "getPlayersMatchGoalCount";
	private static final String getPlayersMatchAssistCount = "getPlayersMatchAssistCount";
	private static final String getPlayersMatchStarterCount = "getPlayersMatchStarterCount";
	private static final String getPlayersMatchSubstituteCount = "getPlayersMatchSubstituteCount";
	private static final String GET_COACHS_BY_ORG_ID = "getCoachsByOrgId";

	private static final String GET_PLAYER_BY_JERSEY_NUMBER_AND_PLAYER_NAME = "getPlayerIdByJerseyNumberAndNameAndOrgID";

	private static final String GET_USER_ORG_ASSOCIATION_BY_USERID = "getUserOrgAssociationByUserId";
	
	@Override
	public UserVO getUserByID(String userId) {
		return sqlSessionTemplate.selectOne(getSqlNameSpace(GET_BY_ID), userId);
	}

	@Override
	public List<UserVO> getPlayersByOrgId(String orgId) {
		return sqlSessionTemplate.selectList(getSqlNameSpace(GET_PLAYERS_BY_ORG_ID), orgId);
	}

	@Override
	public boolean handleUser(UserVO user, List<UserExtVO> userExtList, boolean isCreate) {
		int affectedFormNum = sqlSessionTemplate.insert(getSqlNameSpace(isCreate ? INSERT_USER_BASIC_DATA : UPDATE_USER_BASIC_DATA), user);
		
		if(affectedFormNum != 1) {
			return false;
		}
		
		if(userExtList == null || userExtList.size() == 0) {
			return true;
		}
		
		Map<String, Object> paramList = new HashMap<String, Object>();
		paramList.put("userId", user.getId());
		paramList.put("userExtList", userExtList);
		
		sqlSessionTemplate.insert(getSqlNameSpace(isCreate ? INSERT_USER_EXT_DATA : UPDATE_USER_EXT_DATA), paramList);
		return true;
	}

	@Override
	public long getRoleIdByRoleName(String roleName) {
		Long roleId =  sqlSessionTemplate.selectOne(getSqlNameSpace(GET_ROLE_ID_BY_ROLE_NAME), roleName);
		return roleId == null ? 0 : roleId.longValue();
	}

	@Override
	public boolean insertUserOrgRole(UserOrgRoleVO uorVO) {
		int affectedRowNum = sqlSessionTemplate.insert(getSqlNameSpace(INSERT_USER_ORG_ROLE), uorVO);
		return affectedRowNum == 1;
	}

	
	@Override
	public List<UserVO> getCoachsByOrgId(String orgId) {
		return sqlSessionTemplate.selectList(getSqlNameSpace(GET_COACHS_BY_ORG_ID), orgId);
	}


	@Override
	public boolean deleteUser(String userId) {
		int affectedRowNum = sqlSessionTemplate.insert(getSqlNameSpace(DELETE_USER_BY_ID), userId);
		return affectedRowNum == 1;
	}

	@Override
	public List<PlayerMatchStatisticsVO> getPlayersMatchGogls(String orgId) {
		return sqlSessionTemplate.selectList(getSqlNameSpace(getPlayersMatchGoalCount), orgId);
	}
	@Override
	public List<PlayerMatchStatisticsVO> getPlayersMatchAssistCount(String orgId) {
		return sqlSessionTemplate.selectList(getSqlNameSpace(getPlayersMatchAssistCount), orgId);
	}
	@Override
	public List<PlayerMatchStatisticsVO> getPlayersMatchStarterCount(String orgId) {
		return sqlSessionTemplate.selectList(getSqlNameSpace(getPlayersMatchStarterCount), orgId);
	}
	@Override
	public List<PlayerMatchStatisticsVO> getPlayersMatchSubstitution(String orgId) {
		return sqlSessionTemplate.selectList(getSqlNameSpace(getPlayersMatchSubstituteCount), orgId);
	}

	@Override
	public String getPlayerIdByJerseyNumberAndNameAndOrgID(String jerseyNumber, String playerName, String orgId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("jersey_number", jerseyNumber);
		params.put("player_name", playerName);
		params.put("org_id", orgId);

		return sqlSessionTemplate.selectOne(getSqlNameSpace(GET_PLAYER_BY_JERSEY_NUMBER_AND_PLAYER_NAME), params);
	}

	@Override
	public List<UserOrgRoleVO> getUserOrgAssociationByUserId(String userId) {
		return sqlSessionTemplate.selectList(getSqlNameSpace(GET_USER_ORG_ASSOCIATION_BY_USERID), userId );
	}
}
