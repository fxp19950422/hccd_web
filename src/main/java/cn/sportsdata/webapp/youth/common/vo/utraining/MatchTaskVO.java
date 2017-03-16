package cn.sportsdata.webapp.youth.common.vo.utraining;

import java.io.Serializable;
import java.sql.Timestamp;

public class MatchTaskVO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8149857659772773446L;
	private String trainingTaskId;
	private String matchID;
	private Timestamp date;
	private String time;
	private String location;
	private String opponent;
	private String dress;
	
	/**
	 * @return the trainingTaskId
	 */
	public String getTrainingTaskId() {
		return trainingTaskId;
	}
	/**
	 * @param trainingTaskId the trainingTaskId to set
	 */
	public void setTrainingTaskId(String trainingTaskId) {
		this.trainingTaskId = trainingTaskId;
	}
	/**
	 * @return the matchID
	 */
	public String getMatchID() {
		return matchID;
	}
	/**
	 * @param matchID the matchID to set
	 */
	public void setMatchID(String matchID) {
		this.matchID = matchID;
	}
	/**
	 * @return the date
	 */
	public Timestamp getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(Timestamp date) {
		this.date = date;
	}
	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}
	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}
	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}
	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	/**
	 * @return the opponent
	 */
	public String getOpponent() {
		return opponent;
	}
	/**
	 * @param opponent the opponent to set
	 */
	public void setOpponent(String opponent) {
		this.opponent = opponent;
	}
	/**
	 * @return the dress
	 */
	public String getDress() {
		return dress;
	}
	/**
	 * @param dress the dress to set
	 */
	public void setDress(String dress) {
		this.dress = dress;
	}
	
	
}
