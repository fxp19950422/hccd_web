package cn.sportsdata.webapp.youth.common.vo.match;

import java.io.Serializable;

public class MatchStartersResultVO implements Serializable {
	private static final long serialVersionUID = 9001635638090879629L;
	private String matchId;
	private String startersId;
	private String result;
	private int goalFor;
	private int goalAgainst;
	
	public String getStartersId() {
		return startersId;
	}

	public void setStartersId(String startersId) {
		this.startersId = startersId;
	}
	
	public String getMatchId() {
		return matchId;
	}
	
	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}
	
	public String getResult() {
		return result;
	}
	
	public void setResult(String result) {
		this.result = result;
	}
	
	public int getGoalFor() {
		return goalFor;
	}
	
	public void setGoalFor(int goalFor) {
		this.goalFor = goalFor;
	}
	
	public int getGoalAgainst() {
		return goalAgainst;
	}
	
	public void setGoalAgainst(int goalAgainst) {
		this.goalAgainst = goalAgainst;
	}
}
