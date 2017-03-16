package cn.sportsdata.webapp.youth.common.vo.match;

import java.io.Serializable;

public class GuestFoulVO implements Serializable {
	private static final long serialVersionUID = 6835466747274783948L;
	
	private String playerLabel;
	private int time;
	private String type;
	
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
}
