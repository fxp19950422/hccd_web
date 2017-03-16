package cn.sportsdata.webapp.youth.common.vo.match;

import java.io.Serializable;

public class MatchDataItemVO implements Serializable {
	private static final long serialVersionUID = -4362642529920177495L;
	
	private String itemName;
	private String itemValue;
	
	public String getItemName() {
		return itemName;
	}
	
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	public String getItemValue() {
		return itemValue;
	}
	
	public void setItemValue(String itemValue) {
		this.itemValue = itemValue;
	}
}
