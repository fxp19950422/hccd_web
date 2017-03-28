package cn.sportsdata.webapp.youth.service.user;

import java.util.List;

import cn.sportsdata.webapp.youth.common.vo.AssetVO;
import cn.sportsdata.webapp.youth.common.vo.UserExtVO;
import cn.sportsdata.webapp.youth.common.vo.UserOrgRoleVO;
import cn.sportsdata.webapp.youth.common.vo.UserVO;
import cn.sportsdata.webapp.youth.common.vo.match.PlayerMatchStatisticsVO;
import cn.sportsdata.webapp.youth.common.vo.utraining.UtrainingTaskEvaluationVO;

public interface UserService {
	UserVO getUserByID(String userId);
	List<UserVO> getPlayersByOrgId(String orgId);

	boolean createUser(UserVO basicData, List<UserExtVO> userExtList, UserOrgRoleVO uorVO , AssetVO asset);
	boolean updateUser(UserVO basicData, List<UserExtVO> userExtList,UserOrgRoleVO uorVO, AssetVO asset);
	long getRoleIdByRoleName(String roleName);
	
	/**
	 * get all coachs of Org
	 * @param orgId long
	 * @return List<UserVO>
	 */
	List<UserVO> getCoachsByOrgId(String orgId);

	boolean deleteUser(String userId);

	List<UtrainingTaskEvaluationVO> getPlayersLatestEvaluation(String orgId);

	List<PlayerMatchStatisticsVO> getPlayersGameRecord(String orgId, List<UserVO> playerList);
	
	String getPlayerIdByJerseyNumberAndNameAndOrgID(String jerseyNumber, String playerName, String orgId);

	List<UserOrgRoleVO> getUserOrgAssociationByUserId(String userId);

}
