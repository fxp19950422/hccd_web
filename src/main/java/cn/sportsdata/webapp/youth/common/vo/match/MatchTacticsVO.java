package cn.sportsdata.webapp.youth.common.vo.match;

import java.io.Serializable;
import java.util.List;

public class MatchTacticsVO implements Serializable {
	private static final long serialVersionUID = -7715796839405400235L;
	
	private String matchId;
	private List<String> tacticsIds;
	
	public String getMatchId() {
		return matchId;
	}
	
	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}
	
	public List<String> getTacticsIds() {
		return tacticsIds;
	}
	
	public void setTacticsIds(List<String> tacticsIds) {
		this.tacticsIds = tacticsIds;
	}
}
