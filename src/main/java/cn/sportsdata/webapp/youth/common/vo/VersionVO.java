package cn.sportsdata.webapp.youth.common.vo;

public class VersionVO {
	private String id;
	private String 	mandatory_version;
	private String latest_version;
	private String min_version;
	private String apk_download_url;
	
	public String getMandatory_version() {
		return mandatory_version;
	}
	public void setMandatory_version(String mandatory_version) {
		this.mandatory_version = mandatory_version;
	}
	public String getLatest_version() {
		return latest_version;
	}
	public void setLatest_version(String latest_version) {
		this.latest_version = latest_version;
	}
	public String getMin_version() {
		return min_version;
	}
	public void setMin_version(String min_version) {
		this.min_version = min_version;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getApk_download_url() {
		return apk_download_url;
	}
	public void setApk_download_url(String apk_download_url) {
		this.apk_download_url = apk_download_url;
	}
	
	
}
