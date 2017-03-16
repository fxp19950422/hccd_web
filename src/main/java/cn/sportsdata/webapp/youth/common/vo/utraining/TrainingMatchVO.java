/**
 * 
 */
package cn.sportsdata.webapp.youth.common.vo.utraining;

import java.sql.Timestamp;

/**
 * @author king
 * <result column="id" property="trainingID" jdbcType="INTEGER" />
 * <result column="name" property="trainingName" jdbcType="VARCHAR" />
 * <result column="date" property="matchDate" javaType="java.sql.Timestamp" jdbcType="TIMESTAMP >
 * <result column="opponent" property="opponent" jdbcType="VARCHAR" />
 */
public class TrainingMatchVO {
	private String trainingID;
	private String opponent;
	private Timestamp matchDate;
	private String trainingName;
	/**
	 * @return the trainingID
	 */
	public String getTrainingID() {
		return trainingID;
	}
	/**
	 * @param trainingID the trainingID to set
	 */
	public void setTrainingID(String trainingID) {
		this.trainingID = trainingID;
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
	 * @return the matchDate
	 */
	public Timestamp getMatchDate() {
		return matchDate;
	}
	/**
	 * @param matchDate the matchDate to set
	 */
	public void setMatchDate(Timestamp matchDate) {
		this.matchDate = matchDate;
	}
	/**
	 * @return the trainingName
	 */
	public String getTrainingName() {
		return trainingName;
	}
	/**
	 * @param trainingName the trainingName to set
	 */
	public void setTrainingName(String trainingName) {
		this.trainingName = trainingName;
	}
}
