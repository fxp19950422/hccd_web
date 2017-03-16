package cn.sportsdata.webapp.youth.common.bo;

import java.io.Serializable;
import java.util.List;
import cn.sportsdata.webapp.youth.common.vo.UserVO;

public class UtrainingTaskEvaBO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3344243669578598015L;
	private long taskId;
	private double score;
	private List<UserVO> playerList;
	private List<String> playerInfoList;
	private List<String> topFourPlayerInfoList;
	private String allPlayersInfo;
	
	public String getAllPlayersInfo() {
		return allPlayersInfo;
	}
	public void setAllPlayersInfo(String allPlayersInfo) {
		this.allPlayersInfo = allPlayersInfo;
	}
	public long getTaskId() {
		return taskId;
	}
	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public List<UserVO> getPlayerList() {
		return playerList;
	}
	public void setPlayerList(List<UserVO> playerList) {
		this.playerList = playerList;
	}
	public List<String> getPlayerInfoList() {
		return playerInfoList;
	}
	public void setPlayerInfoList(List<String> playerInfoList) {
		this.playerInfoList = playerInfoList;
	}
	public List<String> getTopFourPlayerInfoList() {
		return topFourPlayerInfoList;
	}
	public void setTopFourPlayerInfoList(List<String> topFourPlayerInfoList) {
		this.topFourPlayerInfoList = topFourPlayerInfoList;
	}

	
}
