package cn.sportsdata.webapp.youth.dao.user;

import java.util.List;

import cn.sportsdata.webapp.youth.common.vo.UserExtVO;
import cn.sportsdata.webapp.youth.common.vo.UserHospitalDepartmentVO;
import cn.sportsdata.webapp.youth.common.vo.UserOrgRoleVO;
import cn.sportsdata.webapp.youth.common.vo.UserVO;
import cn.sportsdata.webapp.youth.common.vo.match.PlayerMatchStatisticsVO;

public interface UserDAO {
	UserVO getUserByID(String userId);
	List<UserVO> getPlayersByOrgId(String orgId);
	boolean handleUser(UserVO user, List<UserExtVO> userExtList, boolean isCreate);
	
	boolean updateUserOrgRole(UserOrgRoleVO uorVO);
	long getRoleIdByRoleName(String roleName);
	boolean insertUserOrgRole(UserOrgRoleVO uorVO);
	
	boolean insertUserHospitalDepartment(UserHospitalDepartmentVO uorVO);
	/**
	 * get all coachs of Org
	 * @param orgId long
	 * @return List<UserVO>
	 */
	List<UserVO> getCoachsByOrgId(String orgId);
	boolean deleteUser(String userId);

	List<PlayerMatchStatisticsVO> getPlayersMatchGogls(String orgId);
	List<PlayerMatchStatisticsVO> getPlayersMatchAssistCount(String orgId);
	List<PlayerMatchStatisticsVO> getPlayersMatchStarterCount(String orgId);
	List<PlayerMatchStatisticsVO> getPlayersMatchSubstitution(String orgId);
	
	String getPlayerIdByJerseyNumberAndNameAndOrgID(String jerseyNumber, String playerName, String orgId);

	List<UserOrgRoleVO> getUserOrgAssociationByUserId(String userId);

}
