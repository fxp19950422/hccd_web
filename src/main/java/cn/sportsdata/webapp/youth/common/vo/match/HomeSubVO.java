package cn.sportsdata.webapp.youth.common.vo.match;

import java.io.Serializable;

public class HomeSubVO implements Serializable {
	private static final long serialVersionUID = 4422109844872657052L;
	
	private String offPlayerId;
	private String onPlayerId;
	private int time;
	
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
	
	public int getTime() {
		return time;
	}
	
	public void setTime(int time) {
		this.time = time;
	}
}
