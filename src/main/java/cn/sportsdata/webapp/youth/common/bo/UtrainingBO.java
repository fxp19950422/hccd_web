package cn.sportsdata.webapp.youth.common.bo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import cn.sportsdata.webapp.youth.common.vo.UserVO;
import cn.sportsdata.webapp.youth.common.vo.utraining.UtrainingTaskVO;

public class UtrainingBO implements Serializable {

	private String name;
	private String id;
	private Date startDate;
	private Date endDate;
	private String location;
	private String goal;
	private String evaluation;
	private String orgId;
	private String color;
	private String barCodeLink;
	
	
	private List<UserVO> forwardList;
	private List<UserVO> centerList;
	private List<UserVO> defenderList;
	private List<UserVO> goalKeeperList;
	
	private List<UserVO> playerList;
	
	private List<UserVO> allPlayerList;
	
	private List<String> trainingPlayIdList;
	
	private List<UtrainingTaskVO> taskList;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getGoal() {
		return goal;
	}
	public void setGoal(String goal) {
		this.goal = goal;
	}
	public String getEvaluation() {
		return evaluation;
	}
	public void setEvaluation(String evaluation) {
		this.evaluation = evaluation;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public List<UserVO> getForwardList() {
		return forwardList;
	}
	public void setForwardList(List<UserVO> forwardList) {
		this.forwardList = forwardList;
	}
	public List<UserVO> getCenterList() {
		return centerList;
	}
	public void setCenterList(List<UserVO> centerList) {
		this.centerList = centerList;
	}
	public List<UserVO> getDefenderList() {
		return defenderList;
	}
	public void setDefenderList(List<UserVO> defenderList) {
		this.defenderList = defenderList;
	}
	public List<UserVO> getGoalKeeperList() {
		return goalKeeperList;
	}
	public void setGoalKeeperList(List<UserVO> goalKeeperList) {
		this.goalKeeperList = goalKeeperList;
	}
	public List<String> getTrainingPlayIdList() {
		return trainingPlayIdList;
	}
	public void setTrainingPlayIdList(List<String> trainingPlayIdList) {
		this.trainingPlayIdList = trainingPlayIdList;
	}
	public List<UtrainingTaskVO> getTaskList() {
		return taskList;
	}
	public void setTaskList(List<UtrainingTaskVO> taskList) {
		this.taskList = taskList;
	}
	public List<UserVO> getPlayerList() {
		return playerList;
	}
	public void setPlayerList(List<UserVO> playerList) {
		this.playerList = playerList;
	}
	public List<UserVO> getAllPlayerList() {
		return allPlayerList;
	}
	public void setAllPlayerList(List<UserVO> allPlayerList) {
		this.allPlayerList = allPlayerList;
	}
	/**
	 * @return the barCodeLink
	 */
	public String getBarCodeLink() {
		return barCodeLink;
	}
	/**
	 * @param barCodeLink the barCodeLink to set
	 */
	public void setBarCodeLink(String barCodeLink) {
		this.barCodeLink = barCodeLink;
	}
	
	
	
}
