package cn.sportsdata.webapp.youth.common.vo.match;

import java.io.Serializable;

public class HomeScoreVO implements Serializable {
	private static final long serialVersionUID = 4618512489565082107L;
	
	private String playerId;
	private String type;
	private String mode;
	private String assistPlayerId;
	private int time;
	
	public String getPlayerId() {
		return playerId;
	}
	
	public void setPlayerId(String playerId) {
		this.playerId = playerId;
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
	
	public String getAssistPlayerId() {
		return assistPlayerId;
	}
	
	public void setAssistPlayerId(String assistPlayerId) {
		this.assistPlayerId = assistPlayerId;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}
}
