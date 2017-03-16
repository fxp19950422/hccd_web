package cn.sportsdata.webapp.youth.common.vo.match;

import java.io.Serializable;

import cn.sportsdata.webapp.youth.common.constants.Constants;

public class MatchGoalVO implements Serializable {
	private static final long serialVersionUID = -8652632732291922428L;
	
	private String matchId;
	private String orgId;
	private String playerId;
	private String assistPlayerId;
	private String playerLabel;
	private String assistPlayerLabel;
	private String type;
	private String mode;
	private int time;
	
	private String homePlayerName;
	private String homeAssistPlayerName;
	private String homePlayerJerseyNumber;
	private String homeAssistPlayerJerseyNumber;
	
	private String translatedGoalType;
	private String translatedGoalMode;
	
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
	
	public String getAssistPlayerId() {
		return assistPlayerId;
	}
	
	public void setAssistPlayerId(String assistPlayerId) {
		this.assistPlayerId = assistPlayerId;
	}
	
	public String getPlayerLabel() {
		return playerLabel;
	}
	
	public void setPlayerLabel(String playerLabel) {
		this.playerLabel = playerLabel;
	}
	
	public String getAssistPlayerLabel() {
		return assistPlayerLabel;
	}
	
	public void setAssistPlayerLabel(String assistPlayerLabel) {
		this.assistPlayerLabel = assistPlayerLabel;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getMode() {
		return mode;
	}
	
	public void setMode(String mode) {
		this.mode = mode;
	}
	
	public int getTime() {
		return time;
	}
	
	public void setTime(int time) {
		this.time = time;
	}

	public String getHomePlayerName() {
		return homePlayerName;
	}

	public void setHomePlayerName(String homePlayerName) {
		this.homePlayerName = homePlayerName;
	}

	public String getHomeAssistPlayerName() {
		return homeAssistPlayerName;
	}

	public void setHomeAssistPlayerName(String homeAssistPlayerName) {
		this.homeAssistPlayerName = homeAssistPlayerName;
	}

	public String getHomePlayerJerseyNumber() {
		return homePlayerJerseyNumber;
	}

	public void setHomePlayerJerseyNumber(String homePlayerJerseyNumber) {
		this.homePlayerJerseyNumber = homePlayerJerseyNumber;
	}

	public String getHomeAssistPlayerJerseyNumber() {
		return homeAssistPlayerJerseyNumber;
	}

	public void setHomeAssistPlayerJerseyNumber(String homeAssistPlayerJerseyNumber) {
		this.homeAssistPlayerJerseyNumber = homeAssistPlayerJerseyNumber;
	}

	public String getTranslatedGoalType() {
		translatedGoalType = Constants.MATCH_GOAL_TYPE_MAPPING.get(type);
		
		return translatedGoalType;
	}

	public String getTranslatedGoalMode() {
		translatedGoalMode = Constants.MATCH_GOAL_MODE_MAPPING.get(mode);
		
		return translatedGoalMode;
	}
}
