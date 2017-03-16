package cn.sportsdata.webapp.youth.common.vo;

import java.io.Serializable;

public class SingleTrainingExtVO implements Serializable {
	private static final long serialVersionUID = 5904153012178678924L;
	
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
