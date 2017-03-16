package cn.sportsdata.webapp.youth.common.vo.match;

import java.io.Serializable;

public class MatchSubVO implements Serializable {
	private static final long serialVersionUID = 8698066287819382903L;
	
	private String matchId;
	private String orgId;
	private String offPlayerId;
	private String onPlayerId;
	private String offPlayerLabel;
	private String onPlayerLabel;
	private int time;
	
	private String homeOnPlayerName;
	private String homeOnPlayerJerseyNumber;
	private String homeOffPlayerName;
	private String homeOffPlayerJerseyNumber;
	
	public String getMatchId() {
		return matchId;
	}
	
	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}
	
	public String getOrgId() {
		return orgId;
	}
	
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
	public String getOffPlayerId() {
		return offPlayerId;
	}
	
	public void setOffPlayerId(String offPlayerId) {
		this.offPlayerId = offPlayerId;
	}
	
	public String getOnPlayerId() {
		return onPlayerId;
	}
	
	public void setOnPlayerId(String onPlayerId) {
		this.onPlayerId = onPlayerId;
	}
	
	public String getOffPlayerLabel() {
		return offPlayerLabel;
	}
	
	public void setOffPlayerLabel(String offPlayerLabel) {
		this.offPlayerLabel = offPlayerLabel;
	}
	
	public String getOnPlayerLabel() {
		return onPlayerLabel;
	}

	public void setOnPlayerLabel(String onPlayerLabel) {
		this.onPlayerLabel = onPlayerLabel;
	}

	public int getTime() {
		return time;
	}
	
	public void setTime(int time) {
		this.time = time;
	}

	public String getHomeOnPlayerName() {
		return homeOnPlayerName;
	}

	public void setHomeOnPlayerName(String homeOnPlayerName) {
		this.homeOnPlayerName = homeOnPlayerName;
	}

	public String getHomeOnPlayerJerseyNumber() {
		return homeOnPlayerJerseyNumber;
	}

	public void setHomeOnPlayerJerseyNumber(String homeOnPlayerJerseyNumber) {
		this.homeOnPlayerJerseyNumber = homeOnPlayerJerseyNumber;
	}

	public String getHomeOffPlayerName() {
		return homeOffPlayerName;
	}

	public void setHomeOffPlayerName(String homeOffPlayerName) {
		this.homeOffPlayerName = homeOffPlayerName;
	}

	public String getHomeOffPlayerJerseyNumber() {
		return homeOffPlayerJerseyNumber;
	}

	public void setHomeOffPlayerJerseyNumber(String homeOffPlayerJerseyNumber) {
		this.homeOffPlayerJerseyNumber = homeOffPlayerJerseyNumber;
	}
}
