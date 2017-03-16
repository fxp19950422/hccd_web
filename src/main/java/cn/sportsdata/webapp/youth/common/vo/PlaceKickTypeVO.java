package cn.sportsdata.webapp.youth.common.vo;

import java.io.Serializable;

public class PlaceKickTypeVO  implements Serializable {
	private static final long serialVersionUID = -5846677159475609045L;
	private String id;
	private String name;
	private String category;
	private String creator_id;	
	private String  org_id;
	private boolean checked;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getCreator_id() {
		return creator_id;
	}
	public void setCreator_id(String creator_id) {
		this.creator_id = creator_id;
	}
	public String getOrg_id() {
		return org_id;
	}
	public void setOrg_id(String org_id) {
		this.org_id = org_id;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
}
