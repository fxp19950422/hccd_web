package cn.sportsdata.webapp.youth.common.vo.match;

import java.io.Serializable;

public class GuestScoreVO implements Serializable {
	private static final long serialVersionUID = -1933490087201845084L;
	
	private String playerLabel;
	private String type;
	private String mode;
	private String assistPlayerLabel;
	private int time;
	
	public String getPlayerLabel() {
		return playerLabel;
	}
	
	public void setPlayerLabel(String playerLabel) {
		this.playerLabel = playerLabel;
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
	
	public String getAssistPlayerLabel() {
		return assistPlayerLabel;
	}
	
	public void setAssistPlayerLabel(String assistPlayerLabel) {
		this.assistPlayerLabel = assistPlayerLabel;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}
}
