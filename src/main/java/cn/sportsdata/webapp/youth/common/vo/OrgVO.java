package cn.sportsdata.webapp.youth.common.vo;

import java.io.Serializable;

public class OrgVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -593990550063891148L;
	
	private String id;
	private String name;
	private String description;
	
	
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

}
