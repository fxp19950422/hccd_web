package cn.sportsdata.webapp.youth.common.vo.match;

import java.io.Serializable;

public class MatchAssetVO implements Serializable {
	private static final long serialVersionUID = 8240812648863081167L;
	
	private String matchId;
	private String assetId;
	private String type;
	private String path;
	private String originalFileName;
	private String fileExt;
	private long fileSize;
	
	private String creatorId;
	private String orgId;

	public String getMatchId() {
		return matchId;
	}
	
	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}

	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}

	public String getOriginalFileName() {
		return originalFileName;
	}

	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}

	public String getFileExt() {
		return fileExt;
	}

	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
}
