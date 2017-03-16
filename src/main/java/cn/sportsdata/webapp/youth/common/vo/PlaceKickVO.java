package cn.sportsdata.webapp.youth.common.vo;

import java.io.Serializable;

public class PlaceKickVO extends TacticsVO implements Serializable {
	private static final long serialVersionUID = -6455705052751545711L;
	private String placeKickTypeId;
	private String placeKickTypeName;
	
	public String getPlaceKickTypeName() {
		return placeKickTypeName;
	}
	public void setPlaceKickTypeName(String placeKickTypeName) {
		this.placeKickTypeName = placeKickTypeName;
	}
	public String getPlaceKickTypeId() {
		return placeKickTypeId;
	}
	public void setPlaceKickTypeId(String placeKickTypeId) {
		this.placeKickTypeId = placeKickTypeId;
	}	
}
