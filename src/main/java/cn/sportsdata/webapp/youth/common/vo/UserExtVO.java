package cn.sportsdata.webapp.youth.common.vo;

import java.io.Serializable;

public class UserExtVO extends BaseVO implements Serializable {
	private static final long serialVersionUID = 8945430781393235958L;
	
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
