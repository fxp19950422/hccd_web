package cn.sportsdata.webapp.youth.common.bo;

import java.io.Serializable;

public class MatchBO implements Serializable {
	private static final long serialVersionUID = -8338982744921057345L;
	
	private String id;
	private String opponent;
	private String dress;
	private String date;
	private String time;
	private String location;
	private String fieldType;
	private int goalFor;
	private int goalAgainst;
	private String type;
	private String tacticIds;  // separated by ','
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOpponent() {
		return opponent;
	}
	
	public void setOpponent(String opponent) {
		this.opponent = opponent;
	}
	
	public String getDress() {
		return dress;
	}
	
	public void setDress(String dress) {
		this.dress = dress;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public String getTime() {
		return time;
	}
	
	public void setTime(String time) {
		this.time = time;
	}
	
	public String getLocation() {
		return location;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	public String getFieldType() {
		return fieldType;
	}
	
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTacticIds() {
		return tacticIds;
	}

	public void setTacticIds(String tacticIds) {
		this.tacticIds = tacticIds;
	}
}
