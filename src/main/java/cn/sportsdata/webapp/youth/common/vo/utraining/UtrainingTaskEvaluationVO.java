package cn.sportsdata.webapp.youth.common.vo.utraining;

import java.io.Serializable;
import java.util.List;

public class UtrainingTaskEvaluationVO implements Serializable {

	private String id;
	private String taskId;
	private String userId;
	private String userName;
	private String score;
	private String evaluation;
	private List<UtrainingTaskEvaluationExtVO> evaExtList;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getEvaluation() {
		return evaluation;
	}
	public void setEvaluation(String evaluation) {
		this.evaluation = evaluation;
	}
	public List<UtrainingTaskEvaluationExtVO> getEvaExtList() {
		return evaExtList;
	}
	public void setEvaExtList(List<UtrainingTaskEvaluationExtVO> evaExtList) {
		this.evaExtList = evaExtList;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
}
