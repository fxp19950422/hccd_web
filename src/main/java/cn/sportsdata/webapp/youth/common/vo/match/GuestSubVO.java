package cn.sportsdata.webapp.youth.common.vo.match;

import java.io.Serializable;

public class GuestSubVO implements Serializable {
	private static final long serialVersionUID = -884465641615022293L;
	
	private String offPlayerLabel;
	private String onPlayerLabel;
	private int time;
	
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
}
