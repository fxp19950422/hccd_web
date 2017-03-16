/**
 * 
 */
package cn.sportsdata.webapp.youth.common.vo.privilege;

import cn.sportsdata.webapp.youth.common.vo.BaseVO;

import java.io.Serializable;

/**
 * @author king
 *
 */
public class PrivilegeVO extends BaseVO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1332212307676291326L;
	private long id;
	private long parentID;
	private String itemName;
	private String itemValue;
	private String type;
	private String action;
	private String icon;
	
	
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @return the parentID
	 */
	public long getParentID() {
		return parentID;
	}
	/**
	 * @param parentID the parentID to set
	 */
	public void setParentID(long parentID) {
		this.parentID = parentID;
	}
	/**
	 * @return the itemName
	 */
	public String getItemName() {
		return itemName;
	}
	/**
	 * @param itemName the itemName to set
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	/**
	 * @return the itemValue
	 */
	public String getItemValue() {
		return itemValue;
	}
	/**
	 * @param itemValue the itemValue to set
	 */
	public void setItemValue(String itemValue) {
		this.itemValue = itemValue;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

}
