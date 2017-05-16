package cn.sportsdata.webapp.youth.common.vo.patient;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class RecordAssetStageVO implements Serializable {
	private static final long serialVersionUID = 8628757275474528838L;
	
	private String id;
	private String name;
	
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
}
