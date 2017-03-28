package cn.sportsdata.webapp.youth.common.vo.patient;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class RecordAssetVO implements Serializable {
	private static final long serialVersionUID = 8628757275474528837L;

	private String id;
	private String orgId;
	private String displayName;
	private String storageName;
	private long size;
	private String fileExtension;
	private String privacy;
	private String creatorId;
	private String assetTypeId;
	private String assetTypeName;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getStorageName() {
		return storageName;
	}
	public void setStorageName(String storageName) {
		this.storageName = storageName;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public String getFileExtension() {
		return fileExtension;
	}
	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}
	public String getPrivacy() {
		return privacy;
	}
	public void setPrivacy(String privacy) {
		this.privacy = privacy;
	}
	public String getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}
	public String getAssetTypeId() {
		return assetTypeId;
	}
	public void setAssetTypeId(String assetTypeId) {
		this.assetTypeId = assetTypeId;
	}
	public String getAssetTypeName() {
		return assetTypeName;
	}
	public void setAssetTypeName(String assetTypeName) {
		this.assetTypeName = assetTypeName;
	}
}
