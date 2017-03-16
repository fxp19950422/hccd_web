package cn.sportsdata.webapp.youth.dao.utraining;

import java.util.Date;
import java.util.List;

import cn.sportsdata.webapp.youth.common.vo.utraining.MatchTaskVO;
import cn.sportsdata.webapp.youth.common.vo.utraining.TrainingMatchVO;
import cn.sportsdata.webapp.youth.common.vo.utraining.UtrainingTaskEvaluationVO;
import cn.sportsdata.webapp.youth.common.vo.utraining.UtrainingTaskVO;
import cn.sportsdata.webapp.youth.common.vo.utraining.UtrainingVO;

public interface UtrainingDAO {

	public List<String> selectUtrainingYears(String orgId);
	
	public String addUtraining(UtrainingVO vo);
	
	public List<UtrainingVO> getUtrainingInYear(String year,String orgId);
	
	List<UtrainingVO> getUtrainingsByUserId(String userId);
	
	public UtrainingVO getUtrainingById(String utrainingId);
	
	public boolean deleteUtrainingById(String utrainingId, String orgID);
	
	boolean updateUtrainingById(UtrainingVO vo);
	
	List<String> getPlayersInTraining(String utrainingId);
	
	public int buildUserTrainingRelation(UtrainingVO vo);
	
	public String handleTask(UtrainingTaskVO task);
	
	public int buildTaskTrainingRelation(String utrainingId,List<String> taskIds);
	
	List<String> getTasksInTraining(String utrainingId);
	
	List<String> getEvasInTask(String taskId);
	
	List<UtrainingTaskVO> getTaskInfoById(List<String> taskIdList);
	
	void deleteTaskByTrainingIdAndTaskId(List<String> taskIdList,String utrainingId);
	
	void deleteEvaListByTaskIdAndEvaIds(List<String> evaIdList,String taskId);
	
	public String handleTaskEvaluation(String taskID,UtrainingTaskEvaluationVO eva);
	
	List<UtrainingTaskEvaluationVO> getEvaListByTask(String taskId);
	
	boolean deleteEvaListByTaskId(List<String> taskIds);
	
	boolean checkUtrainingDate(String utrainingId,Date startDate,Date endDate,String orgId);
	
	int deleteUserInTraining(String utrainingId);

	List<UtrainingTaskEvaluationVO> getPlayersLatestEvaluation(String orgId);
	
	int buildTaskSingleTrainingRelation(String taskID, List<String> singleTrainingIDs);
	
	List<String> getSingleTrainingIDListByTaskID(String utrainingId);

	UtrainingTaskVO getLatestTrainingTask(String orgId);

	List<UtrainingTaskEvaluationVO> getTrainingTaskEvaResults(String taskId);

	List<UtrainingTaskVO> getComingTrainingTask(String orgId);
	
	int insertTrainingTaskMatchAssociation(String trainingTaskId, String matchID);
	
	List<MatchTaskVO> getMatchTaskInfoByIds(List<String> taskIdList);
	
	List<TrainingMatchVO> getUtrainingMatchInOneYear(String year, String orgId);
}
