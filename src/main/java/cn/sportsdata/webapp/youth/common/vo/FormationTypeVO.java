package cn.sportsdata.webapp.youth.common.vo;

import java.io.Serializable;

public class FormationTypeVO extends BaseVO implements Serializable {
	private static final long serialVersionUID = -482088777003793626L;
	private long id;
	private String name;
	private String category;
	private String creator_id;	
	private String org_id;
	private long player_count;
	private boolean checked;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
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
	public long getPlayer_count() {
		return player_count;
	}
	public void setPlayer_count(long player_count) {
		this.player_count = player_count;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
