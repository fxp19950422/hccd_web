package cn.sportsdata.webapp.youth.common.vo.match;

import java.io.Serializable;

public class HomeFoulVO implements Serializable {
	private static final long serialVersionUID = 8704902352419863666L;
	
	private String playerId;
	private int time;
	private String type;
	
	public String getPlayerId() {
		return playerId;
	}
	
	public void setPlayerId(String playerId) {
		this.playerId = playerId;
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
