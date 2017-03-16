package cn.sportsdata.webapp.youth.common.vo.match;

import java.io.Serializable;

public class PlayerMatchStatisticsVO implements Serializable {
	private static final long serialVersionUID = -5583786335683982361L;
	private String userId;
	private String orgId;
	private long goalCount;
	private long assistCount;
	private long starterCount;
	private long substituteCount;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public long getGoalCount() {
		return goalCount;
	}
	public void setGoalCount(long goalCount) {
		this.goalCount = goalCount;
	}
	public long getAssistCount() {
		return assistCount;
	}
	public void setAssistCount(long assistCount) {
		this.assistCount = assistCount;
	}
	public long getStarterCount() {
		return starterCount;
	}
	public void setStarterCount(long starterCount) {
		this.starterCount = starterCount;
	}
	public long getSubstituteCount() {
		return substituteCount;
	}
	public void setSubstituteCount(long substituteCount) {
		this.substituteCount = substituteCount;
	}

}
