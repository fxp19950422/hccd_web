package cn.sportsdata.webapp.youth.common.vo.patient;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class RecordImageInfoVO implements Serializable {
	private static final long serialVersionUID = 8628757275474528837L;

	private String fileName;
	private String assetTypeId;
	private String assetStageId;
	private String createdTime;
	private String index;

	public String getAssetStageId() {
		return assetStageId;
	}
	public void setAssetStageId(String assetStageId) {
		this.assetStageId = assetStageId;
	}
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	public String getAssetTypeId() {
		return assetTypeId;
	}
	public void setAssetTypeId(String assetTypeId) {
		this.assetTypeId = assetTypeId;
	}
}
