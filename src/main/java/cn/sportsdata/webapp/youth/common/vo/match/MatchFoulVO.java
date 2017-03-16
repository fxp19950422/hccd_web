package cn.sportsdata.webapp.youth.common.vo.match;

import java.io.Serializable;

import cn.sportsdata.webapp.youth.common.constants.Constants;

public class MatchFoulVO implements Serializable {
	private static final long serialVersionUID = -7304308318579791882L;
	
	private String matchId;
	private String orgId;
	private String playerId;
	private String playerLabel;
	private int time;
	private String type;
	
	private String homePlayerName;
	private String homePlayerJerseyNumber;
	
	private String translatedType;
	
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
	
	public String getPlayerId() {
		return playerId;
	}
	
	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}
	
	public String getPlayerLabel() {
		return playerLabel;
	}
	
	public void setPlayerLabel(String playerLabel) {
		this.playerLabel = playerLabel;
	}
	
	public int getTime() {
		return time;
	}
	
	public void setTime(int time) {
		this.time = time;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}

	public String getHomePlayerName() {
		return homePlayerName;
	}

	public void setHomePlayerName(String homePlayerName) {
		this.homePlayerName = homePlayerName;
	}

	public String getHomePlayerJerseyNumber() {
		return homePlayerJerseyNumber;
	}

	public void setHomePlayerJerseyNumber(String homePlayerJerseyNumber) {
		this.homePlayerJerseyNumber = homePlayerJerseyNumber;
	}

	public String getTranslatedType() {
		translatedType = Constants.MATCH_FOUL_TYPE_MAPPING.get(type);
		
		return translatedType;
	}
}
