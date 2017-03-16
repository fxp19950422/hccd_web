package cn.sportsdata.webapp.youth.common.vo.utraining;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class UtrainingTaskVO implements Serializable {
	private String trainingId;
	private String id;
	private String title;
	private String location;
	private Date taskDate;
	private String taskTime;
	private String type;
	
	private String tempId;
	
	private List<TrainingExtVO> extList ;
	
	private List<UtrainingTaskEvaluationVO> evaList;
	
	private List<String> singleTrainingList;
	
	
	public String getTrainingId() {
		return trainingId;
	}

	public void setTrainingId(String trainingId) {
		this.trainingId = trainingId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Date getTaskDate() {
		return taskDate;
	}

	public void setTaskDate(Date taskDate) {
		this.taskDate = taskDate;
	}

	public String getTaskTime() {
		return taskTime;
	}

	public void setTaskTime(String taskTime) {
		this.taskTime = taskTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<TrainingExtVO> getExtList() {
		return extList;
	}

	public void setExtList(List<TrainingExtVO> extList) {
		this.extList = extList;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTempId() {
		return tempId;
	}

	public void setTempId(String tempId) {
		this.tempId = tempId;
	}

	public List<UtrainingTaskEvaluationVO> getEvaList() {
		return evaList;
	}

	public void setEvaList(List<UtrainingTaskEvaluationVO> evaList) {
		this.evaList = evaList;
	}

	/**
	 * @return the singleTrainingList
	 */
	public List<String> getSingleTrainingList() {
		return singleTrainingList;
	}

	/**
	 * @param singleTrainingList the singleTrainingList to set
	 */
	public void setSingleTrainingList(List<String> singleTrainingList) {
		this.singleTrainingList = singleTrainingList;
	}
	
}
