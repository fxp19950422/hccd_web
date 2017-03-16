package cn.sportsdata.webapp.youth.common.vo;

import cn.sportsdata.webapp.youth.common.vo.BaseVO;

/**
 * Created by binzhu on 4/28/16.
 */
public class EquipmentVO extends BaseVO {
	private static final long serialVersionUID = 7922477308103836118L;

	private String id;
	private String name;
	private String description;
	private String avatar;
	private String orgId;
	
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

}
