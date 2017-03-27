package cn.sportsdata.webapp.youth.service.user.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.sportsdata.webapp.youth.common.vo.AssetVO;
import cn.sportsdata.webapp.youth.common.vo.UserExtVO;
import cn.sportsdata.webapp.youth.common.vo.UserHospitalDepartmentVO;
import cn.sportsdata.webapp.youth.common.vo.UserOrgRoleVO;
import cn.sportsdata.webapp.youth.common.vo.UserVO;
import cn.sportsdata.webapp.youth.common.vo.match.PlayerMatchStatisticsVO;
import cn.sportsdata.webapp.youth.common.vo.utraining.UtrainingTaskEvaluationVO;
import cn.sportsdata.webapp.youth.dao.asset.AssetDAO;
import cn.sportsdata.webapp.youth.dao.user.UserDAO;
import cn.sportsdata.webapp.youth.dao.utraining.UtrainingDAO;
import cn.sportsdata.webapp.youth.service.user.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDAO userDAO;

	@Autowired
	private UtrainingDAO utrainingDAO;
	
	@Autowired
	private AssetDAO assetDao;
	
	@Override
	public UserVO getUserByID(String userId) {
		return userDAO.getUserByID(userId);
	}

	@Override
	public List<UserVO> getPlayersByOrgId(String orgId) {
		List<UserVO> playerList = userDAO.getPlayersByOrgId(orgId);
		if(playerList == null) {
			playerList = new ArrayList<UserVO>();
		}
		
		return playerList;
	}

	@Override
	public List<UtrainingTaskEvaluationVO> getPlayersLatestEvaluation(String orgId) {
		List<UtrainingTaskEvaluationVO> resultList = utrainingDAO.getPlayersLatestEvaluation(orgId);
		if(resultList == null) {
			resultList = new ArrayList<UtrainingTaskEvaluationVO>();
		}
		return resultList;
	}
	
	@Override
	@Transactional
	public boolean createUser(UserVO basicData, List<UserExtVO> userExtList, UserOrgRoleVO uorVO, AssetVO asset) {
		if (asset != null){
			String assetID = assetDao.insertAsset(asset);
			basicData.setAvatarId(assetID);
		}
		boolean isStep1Success = userDAO.handleUser(basicData, userExtList, true);
		if(!isStep1Success)  return false;
		
		uorVO.setUserId(basicData.getId());
		boolean b = userDAO.insertUserOrgRole(uorVO);
		UserHospitalDepartmentVO uhdVO = new UserHospitalDepartmentVO();
		uhdVO.setUserId(basicData.getId());
		uhdVO.setHospitalId( uorVO.getHospitalId());
		uhdVO.setDepartmentId("1");
		userDAO.insertUserHospitalDepartment(uhdVO);
		
		return true;
	}

	@Override
	@Transactional
	public boolean updateUser(UserVO basicData, List<UserExtVO> userExtList,UserOrgRoleVO uorVO, AssetVO asset) {
		 if (asset != null ){
			 String avatarId = null;
			 if (!StringUtils.isEmpty(asset.getId())){
				 assetDao.updateAsset(asset);
			 } else {
				 avatarId = assetDao.insertAsset(asset);
				 basicData.setAvatarId(avatarId);
			 }

		 }
		 userDAO.handleUser(basicData, userExtList, false);
		 
//		 UserOrgRoleVO uorVO = new UserOrgRoleVO();
//		 uorVO.setUserId(basicData.getId());
		 
		 userDAO.updateUserOrgRole(uorVO);
		 return true;
	}

	@Override
	public long getRoleIdByRoleName(String roleName) {
		return userDAO.getRoleIdByRoleName(roleName);
	}

	
	@Override
	public List<UserVO> getCoachsByOrgId(String orgId) {
		List<UserVO> playerList = userDAO.getCoachsByOrgId(orgId);
		if(playerList == null) {
			playerList = new ArrayList<UserVO>();
		}
		
		return playerList;
	}

	@Override
	public boolean deleteUser(String userId) {
		return userDAO.deleteUser(userId);
	}

	@Override
	public List<PlayerMatchStatisticsVO> getPlayersGameRecord(String orgId, List<UserVO> playerList) {
		List<PlayerMatchStatisticsVO> playerGoalList = userDAO.getPlayersMatchGogls(orgId);
		List<PlayerMatchStatisticsVO> playerAssistList = userDAO.getPlayersMatchAssistCount(orgId);
		List<PlayerMatchStatisticsVO> playerStarterList = userDAO.getPlayersMatchStarterCount(orgId);
		List<PlayerMatchStatisticsVO> playerSubstituteList = userDAO.getPlayersMatchSubstitution(orgId);
		
		List<PlayerMatchStatisticsVO> resultList = new ArrayList<PlayerMatchStatisticsVO>();
		for (UserVO user: playerList) {
			PlayerMatchStatisticsVO tempRecord = new PlayerMatchStatisticsVO();
			tempRecord.setUserId(user.getId());
			tempRecord.setAssistCount(0);
			tempRecord.setGoalCount(0);
			tempRecord.setStarterCount(0);
			tempRecord.setSubstituteCount(0);
			for (PlayerMatchStatisticsVO record: playerGoalList) {
				if (record.getUserId().equals(tempRecord.getUserId())){
					tempRecord.setGoalCount(record.getGoalCount());
				}
			}
			for (PlayerMatchStatisticsVO record: playerAssistList) {
				if (record.getUserId().equals(tempRecord.getUserId())){
					tempRecord.setAssistCount(record.getAssistCount());
				}
			}
			for (PlayerMatchStatisticsVO record: playerStarterList) {
				if (record.getUserId().equals(tempRecord.getUserId())){
					tempRecord.setStarterCount(record.getStarterCount());
				}
			}
			for (PlayerMatchStatisticsVO record: playerSubstituteList) {
				if (record.getUserId().equals(tempRecord.getUserId())){
					tempRecord.setSubstituteCount(record.getSubstituteCount());
				}
			}
			resultList.add(tempRecord);
		}
		return resultList;
	}

	@Override
	public String getPlayerIdByJerseyNumberAndNameAndOrgID(String jerseyNumber, String playerName, String orgId) {
		if(StringUtils.isBlank(jerseyNumber) || StringUtils.isBlank(playerName)) {
			return null;
		}
		
		return userDAO.getPlayerIdByJerseyNumberAndNameAndOrgID(jerseyNumber, playerName, orgId);
	}

	public List<UserOrgRoleVO> getUserOrgAssociationByUserId(String userId){return userDAO.getUserOrgAssociationByUserId(userId);}
}
