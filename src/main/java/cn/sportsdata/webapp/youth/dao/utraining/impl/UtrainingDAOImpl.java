package cn.sportsdata.webapp.youth.dao.utraining.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.sportsdata.webapp.youth.common.auth.share.ShareLink;
import cn.sportsdata.webapp.youth.common.exceptions.SoccerProException;
import cn.sportsdata.webapp.youth.common.utils.StringUtil;
import cn.sportsdata.webapp.youth.common.vo.utraining.MatchTaskVO;
import cn.sportsdata.webapp.youth.common.vo.utraining.TrainingMatchVO;
import cn.sportsdata.webapp.youth.common.vo.utraining.UtrainingTaskEvaluationVO;
import cn.sportsdata.webapp.youth.common.vo.utraining.UtrainingTaskVO;
import cn.sportsdata.webapp.youth.common.vo.utraining.UtrainingVO;
import cn.sportsdata.webapp.youth.dao.BaseDAO;
import cn.sportsdata.webapp.youth.dao.share.ShareLinkDAO;
import cn.sportsdata.webapp.youth.dao.utraining.UtrainingDAO;
import cn.sportsdata.webapp.youth.service.utraining.UtrainingService;

@Repository
public class UtrainingDAOImpl extends BaseDAO implements UtrainingDAO {
	
	@Autowired
	private UtrainingService utrainingService;
	
	@Autowired
	private ShareLinkDAO shareLinkDAO;

	// get years contain utraining
	public static final String GET_UTRAINING_YEARS = "selectUtrainingYears";
	// add utraining
	public static final String ADD_NEW_UTRAINING = "addNewUtraining";
	// get utrainings in a year
	public static final String GET_UTRAININGS_IN_ONE_YEAR = "getUtrainingsInOneYear";

	public static final String GET_UTRAININGS_BY_USER_ID = "getUtrainingsByUserId";

	public static final String GET_UTRAININGS_BY_ID = "getUtrainingById";

	public static final String DELETE_UTRAININGS_BY_ID = "deleteUtrainingById";

	public static final String UPDATE_UTRAININGS_BY_ID = "updateUtrainingById";

	public static final String GET_PLAYER_IN_UTRAINING = "getPlayerInTraining";

	public static final String DELETE_PLAYER_UTRAINING_RELATION = "deletePlayerUtriningRelation";

	public static final String INSERT_PLAYER_UTRAINING_RELATION = "insertPlayerUtriningRelation";

	public static final String INSERT_TASK_BASIC_DATA = "insertTaskBasicData";
	public static final String UPDATE_TASK_BASIC_DATA = "updateTaskBasicData";
	public static final String INSERT_TASK_EXT_DATA = "insertTaskExtData";
	public static final String UPDATE_TASK_EXT_DATA = "updateTaskExtData";

	public static final String DELETE_TASK_UTRAINING_RELATION = "deleteTaskUtriningRelation";
	
	public static final String SOFT_DELETE_TASK_SINGLE_TRINING_RELATION = "softDeleteTaskSingleTriningRelation";

	public static final String INSERT_TASK_UTRAINING_RELATION = "insertTaskUtriningRelation";
	
	public static final String INSERT_TASK_SINGLE_TRINING_RELATION = "insertTaskSingleTriningRelation";

	public static final String GET_TASK_IN_UTRAINING = "getTaskInTraining";
	public static final String GET_TASK_INFO_BY_IDS = "getTaskInfoByIds";

	public static final String DELETE_UTRAINING_TASK_BY_TASK_ID = "deleteUtrainingTaskByTaskId";
	public static final String DELETE_UTRAINING_TASK_RELATION_BY_TRAINING_ID = "deleteUtrainingTaskRalationByTrainingId";

	public static final String DELETE_UTRAINING_TASK_RELATION_BY_TRAINING_ID_AND_TASK_ID = "deleteUtrainingTaskRalationByTrainingIdAndTaskId";

	public static final String INSERT_TASK_EVA_BASIC_DATA = "insertTaskEvaBasicData";
	public static final String UPDATE_TASK_EVA_BASIC_DATA = "updateTaskEvaBasicData";
	public static final String INSERT_TASK_EVA_EXT_DATA = "insertTaskEvaExtData";
	public static final String UPDATE_TASK_EVA_EXT_DATA = "updateTaskEvaExtData";

	public static final String GET_TASK_EVA_LIST_BY_TASK_ID = "getTaskEvaListByTaskId";
	public static final String DELETE_EVA_LIST_BY_TASK_ID = "deleteEvaListByTaskId";
	
	
	public static final String CHECK_UTRAINING_DATE = "checkUtrainingDate";
	
	public static final String DELETE_UTRAINING_TASK_EXT_BY_TASK_ID = "deleteUtrainingTaskExtByTaskId";
	public static final String DELETE_UTRAINING_TASK_EVA_EXT_BY_TASK_ID = "deleteUtrainingTaskEvaluationExtByTaskId";
	
	public static final String GET_EVA_IN_TASK = "getEvaInTask";
	public static final String DELETE_EVA_LIST_BY_TASKID_AND_EVAID = "deleteEvaListByTaskIdAndEvaId";
	public static final String DELETE_EVA_EXT_LIST_BY_EVAID = "deleteEvaExtListByEvaId";

	private static final String GET_PLAYERS_LATEST_EVALUATION = "getPlayersLatestEvaluation";
	private static final String getLatestTrainingTask = "getLatestTrainingTask";
	private static final String getTrainingTaskEvaResults = "getTrainingTaskEvaResults";
	private static final String getComingTrainingTask = "getComingTrainingTask";
	
	public static final String GET_SINGLE_TRAINING_ID_LIST_BY_TASK_ID = "getSingleTrainingIDListByTaskID";
	
	public static final String INSERT_TRAINING_TASK_MATCH_ASSOCIATION = "insertTrainingTaskMatchAssociation";
	
	public static final String GET_MATCH_TASK_INFO_BY_IDS = "getMatchTaskInfoByIds";
	
	public static final String GET_UTRAINING_MATCH_IN_ONE_YEAR = "getUtrainingMatchInOneYear";
	
	private static final Logger logger = Logger.getLogger(UtrainingDAOImpl.class);
	
	@Override
	public List<UtrainingTaskEvaluationVO> getPlayersLatestEvaluation(String orgId) {
		return sqlSessionTemplate.selectList(getSqlNameSpace(GET_PLAYERS_LATEST_EVALUATION), orgId);
	}
	
	@Override
	public List<String> selectUtrainingYears(String orgId) {
		return sqlSessionTemplate.selectList(getSqlNameSpace(GET_UTRAINING_YEARS), orgId);
	}

	@Override
	public String addUtraining(UtrainingVO vo) {
		sqlSessionTemplate.insert(getSqlNameSpace(ADD_NEW_UTRAINING), vo);
		return vo.getId();
	}

	@Override
	public List<UtrainingVO> getUtrainingInYear(String year, String orgId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("year", year);
		map.put("orgId", orgId);
		return sqlSessionTemplate.selectList(getSqlNameSpace(GET_UTRAININGS_IN_ONE_YEAR), map);
	}

	@Override
	public List<UtrainingVO> getUtrainingsByUserId(String userId) {
		return sqlSessionTemplate.selectList(getSqlNameSpace(GET_UTRAININGS_BY_USER_ID), userId);
	}

	@Override
	public UtrainingVO getUtrainingById(String utrainingId) {
		return sqlSessionTemplate.selectOne(getSqlNameSpace(GET_UTRAININGS_BY_ID), utrainingId);
	}

	@Override
	public boolean deleteUtrainingById(String utrainingId, String orgID) {
		
		ShareLink shareLink = utrainingService.getShareLink(orgID, utrainingId);
		try {
			shareLinkDAO.deleteShareLinkBysha256(shareLink.sha256());
		} catch (SoccerProException e) {
			logger.error("delete share link by sha256 failed");
		}
		
		
		sqlSessionTemplate.delete(getSqlNameSpace(DELETE_PLAYER_UTRAINING_RELATION), utrainingId);

		List<String> taskIds = getTasksInTraining(utrainingId);

		sqlSessionTemplate.delete(getSqlNameSpace(DELETE_UTRAINING_TASK_RELATION_BY_TRAINING_ID), utrainingId);
		if (taskIds != null && taskIds.size() > 0) {
//			sqlSessionTemplate.delete(getSqlNameSpace(DELETE_UTRAINING_TASK_BY_TASK_ID), taskIds);
//			sqlSessionTemplate.delete(getSqlNameSpace(DELETE_UTRAINING_TASK_EXT_BY_TASK_ID), taskIds);
			deleteTaskListByTaskId(taskIds);
			deleteEvaListByTaskId(taskIds);
		}
		int i = sqlSessionTemplate.delete(getSqlNameSpace(DELETE_UTRAININGS_BY_ID), utrainingId);
		return i > 0;
	}

	private boolean deleteTaskListByTaskId(List<String> taskIds){
		sqlSessionTemplate.delete(getSqlNameSpace(DELETE_UTRAINING_TASK_BY_TASK_ID), taskIds);
		sqlSessionTemplate.delete(getSqlNameSpace(DELETE_UTRAINING_TASK_EXT_BY_TASK_ID), taskIds);
		return true;
	}
	
	@Override
	public boolean updateUtrainingById(UtrainingVO vo) {
		int i = sqlSessionTemplate.update(getSqlNameSpace(UPDATE_UTRAININGS_BY_ID), vo);
		return i > 0;
	}

	@Override
	public List<String> getPlayersInTraining(String utrainingId) {
		return sqlSessionTemplate.selectList(getSqlNameSpace(GET_PLAYER_IN_UTRAINING), utrainingId);
	}

	@Override
	public int buildUserTrainingRelation(UtrainingVO vo) {

		sqlSessionTemplate.delete(getSqlNameSpace(DELETE_PLAYER_UTRAINING_RELATION), vo.getId());

		Map<String, Object> paramList = new HashMap<String, Object>();
		paramList.put("utrainingId", vo.getId());
		paramList.put("playerList", vo.getTrainingPlayIdList());
		return sqlSessionTemplate.insert(getSqlNameSpace(INSERT_PLAYER_UTRAINING_RELATION), paramList);
	}

	@Override
	public String handleTask(UtrainingTaskVO task) {
		int affectedFormNum = sqlSessionTemplate
				.insert(getSqlNameSpace(StringUtil.isBlank(task.getId()) ? INSERT_TASK_BASIC_DATA : UPDATE_TASK_BASIC_DATA), task);

		if (affectedFormNum != 1) {
			return "";
		}

		// extlist
		if (task.getExtList() != null && task.getExtList().size() > 0) {
			Map<String, Object> paramList = new HashMap<String, Object>();
			paramList.put("taskId", task.getId());
			paramList.put("taskExtList", task.getExtList());

			sqlSessionTemplate.insert(getSqlNameSpace(StringUtil.isBlank(task.getId()) ? INSERT_TASK_EXT_DATA : UPDATE_TASK_EXT_DATA),
					paramList);
		}

		return task.getId();
	}

	@Override
	public int buildTaskTrainingRelation(String utrainingId, List<String> taskIds) {
		sqlSessionTemplate.delete(getSqlNameSpace(DELETE_TASK_UTRAINING_RELATION), utrainingId);

		Map<String, Object> paramList = new HashMap<String, Object>();
		paramList.put("utrainingId", utrainingId);
		paramList.put("taskList", taskIds);
		return sqlSessionTemplate.insert(getSqlNameSpace(INSERT_TASK_UTRAINING_RELATION), paramList);
	}
	
	@Override
	public int buildTaskSingleTrainingRelation(String taskID, List<String> singleTrainingIDs) {
		sqlSessionTemplate.update(getSqlNameSpace(SOFT_DELETE_TASK_SINGLE_TRINING_RELATION), taskID);

		Map<String, Object> paramList = new HashMap<String, Object>();
		paramList.put("taskID", taskID);
		paramList.put("singleTrainingIDs", singleTrainingIDs);
		return sqlSessionTemplate.insert(getSqlNameSpace(INSERT_TASK_SINGLE_TRINING_RELATION), paramList);
	}

	@Override
	public List<String> getTasksInTraining(String utrainingId) {
		return sqlSessionTemplate.selectList(getSqlNameSpace(GET_TASK_IN_UTRAINING), utrainingId);
	}

	@Override
	public List<UtrainingTaskVO> getTaskInfoById(List<String> taskIdList) {
		return sqlSessionTemplate.selectList(getSqlNameSpace(GET_TASK_INFO_BY_IDS), taskIdList);
	}

	@Override
	public void deleteTaskByTrainingIdAndTaskId(List<String> taskIdList, String utrainingId) {
		Map<String, Object> paramList = new HashMap<String, Object>();
		paramList.put("utrainingId", utrainingId);
		paramList.put("taskList", taskIdList);

		sqlSessionTemplate.delete(getSqlNameSpace(DELETE_UTRAINING_TASK_RELATION_BY_TRAINING_ID_AND_TASK_ID),
				paramList);
		
		if (taskIdList != null && taskIdList.size() > 0) {
			deleteTaskListByTaskId(taskIdList);
			deleteEvaListByTaskId(taskIdList);
		}
		
	}

	@Override
	public String handleTaskEvaluation(String taskID, UtrainingTaskEvaluationVO eva) {
		eva.setTaskId(taskID);
		int affectedFormNum = sqlSessionTemplate.insert(getSqlNameSpace(StringUtil.isBlank(eva.getId())? INSERT_TASK_EVA_BASIC_DATA : UPDATE_TASK_EVA_BASIC_DATA), eva);

		if (affectedFormNum != 1) {
			return "";
		}

		// extlist
		if (eva.getEvaExtList() != null && eva.getEvaExtList().size() > 0) {
			Map<String, Object> paramList = new HashMap<String, Object>();
			paramList.put("taskEvaId", eva.getId());
			paramList.put("taskEvaExtList", eva.getEvaExtList());

			sqlSessionTemplate.insert(
					getSqlNameSpace(StringUtil.isBlank(eva.getId())? INSERT_TASK_EVA_EXT_DATA : UPDATE_TASK_EVA_EXT_DATA), paramList);
		}

		return eva.getId();
	}

	@Override
	public List<UtrainingTaskEvaluationVO> getEvaListByTask(String taskId) {
		return sqlSessionTemplate.selectList(getSqlNameSpace(GET_TASK_EVA_LIST_BY_TASK_ID), taskId);
	}

	@Override
	public boolean deleteEvaListByTaskId(List<String> taskIds) {
		int count = sqlSessionTemplate.delete(getSqlNameSpace(DELETE_EVA_LIST_BY_TASK_ID), taskIds);
		sqlSessionTemplate.delete(getSqlNameSpace(DELETE_UTRAINING_TASK_EVA_EXT_BY_TASK_ID), taskIds);
		return false ;
	}

	@Override
	public boolean checkUtrainingDate(String utrainingId,Date startDate, Date endDate,String orgId) {
		Map<String, Object> paramList = new HashMap<String, Object>();
		paramList.put("startDate", startDate);
		paramList.put("endDate", endDate);
		paramList.put("orgId", orgId);
		paramList.put("utrainingId", utrainingId);
		int count = sqlSessionTemplate.selectOne(getSqlNameSpace(CHECK_UTRAINING_DATE), paramList);
		return count == 0;
	}

	@Override
	public int deleteUserInTraining(String utrainingId) {
		return sqlSessionTemplate.delete(getSqlNameSpace(DELETE_PLAYER_UTRAINING_RELATION), utrainingId);
	}

	@Override
	public List<String> getEvasInTask(String taskId) {
		return sqlSessionTemplate.selectList(getSqlNameSpace(GET_EVA_IN_TASK), taskId);
	}

	@Override
	public void deleteEvaListByTaskIdAndEvaIds(List<String> evaIdList, String taskId) {
		Map<String, Object> paramList = new HashMap<String, Object>();
		paramList.put("taskId", taskId);
		paramList.put("evaIdList", evaIdList);
		sqlSessionTemplate.delete(getSqlNameSpace(DELETE_EVA_LIST_BY_TASKID_AND_EVAID), paramList);
		sqlSessionTemplate.delete(getSqlNameSpace(DELETE_EVA_EXT_LIST_BY_EVAID), evaIdList);
	}
	
	@Override
	public List<String> getSingleTrainingIDListByTaskID(String utrainingId) {
		return sqlSessionTemplate.selectList(getSqlNameSpace(GET_SINGLE_TRAINING_ID_LIST_BY_TASK_ID), utrainingId);
	}
	
	@Override
	public int insertTrainingTaskMatchAssociation(String trainingTaskID, String matchID) {
		Map<String, Object> paramList = new HashMap<String, Object>();
		paramList.put("trainingTaskID", trainingTaskID);
		paramList.put("matchID", matchID);
		return sqlSessionTemplate.insert(getSqlNameSpace(INSERT_TRAINING_TASK_MATCH_ASSOCIATION), paramList);
	}
	
	@Override
	public List<MatchTaskVO> getMatchTaskInfoByIds(List<String> taskIdList) {
		 return sqlSessionTemplate.selectList(getSqlNameSpace(GET_MATCH_TASK_INFO_BY_IDS),taskIdList);
	}
	
	@Override
	public List<TrainingMatchVO> getUtrainingMatchInOneYear(String year, String orgId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("year", year);
		map.put("orgID", orgId);
		return sqlSessionTemplate.selectList(getSqlNameSpace(GET_UTRAINING_MATCH_IN_ONE_YEAR), map);
	}

	@Override
	public UtrainingTaskVO getLatestTrainingTask(String orgId) {
		return sqlSessionTemplate.selectOne(getSqlNameSpace(getLatestTrainingTask), orgId);
	}

	@Override
	public List<UtrainingTaskEvaluationVO> getTrainingTaskEvaResults(String taskId) {
		return sqlSessionTemplate.selectList(getSqlNameSpace(getTrainingTaskEvaResults), taskId);
	}
	
	@Override
	public List<UtrainingTaskVO> getComingTrainingTask(String orgId) {
		return sqlSessionTemplate.selectList(getSqlNameSpace(getComingTrainingTask), orgId);
	}
}
