package cn.sportsdata.webapp.youth.common.vo.match;

import java.io.Serializable;
import java.sql.Timestamp;

import cn.sportsdata.webapp.youth.common.constants.Constants;

public class MatchVO implements Serializable {
	private static final long serialVersionUID = 8628757275474528838L;
	
	private String id;
	private String opponent;
	private String dress;
	private Timestamp date;
	private String time;
	private String location;
	private String fieldType;
	private int isEnd;
	private String orgId;
	private String creatorId;
	private String type;
	
	private int homeScore;
	private int guestScore;
	
	private String translatedType;
	private String translatedFieldType;
	
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
	
	public Timestamp getDate() {
		return date;
	}
	
	public void setDate(Timestamp date) {
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

	public int getIsEnd() {
		return isEnd;
	}

	public void setIsEnd(int isEnd) {
		this.isEnd = isEnd;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getHomeScore() {
		return homeScore;
	}

	public void setHomeScore(int homeScore) {
		this.homeScore = homeScore;
	}

	public int getGuestScore() {
		return guestScore;
	}

	public void setGuestScore(int guestScore) {
		this.guestScore = guestScore;
	}

	public String getTranslatedType() {
		translatedType = Constants.MATCH_TYPE_MAPPING.get(type);
		return translatedType;
	}

	public String getTranslatedFieldType() {
		translatedFieldType = Constants.MATCH_FIELD_TYPE_MAPPING.get(fieldType);
		
		return translatedFieldType;
	}
}
