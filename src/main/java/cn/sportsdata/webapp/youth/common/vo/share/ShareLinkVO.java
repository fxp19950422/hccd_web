/**
 * 
 */
package cn.sportsdata.webapp.youth.common.vo.share;

/**
 * @author king
 *
 */
public class ShareLinkVO {
	private String id;
	private String guid;
	private String sha256;
	private String requestJSON;
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the guid
	 */
	public String getGuid() {
		return guid;
	}
	/**
	 * @param guid the guid to set
	 */
	public void setGuid(String guid) {
		this.guid = guid;
	}
	/**
	 * @return the sha256
	 */
	public String getSha256() {
		return sha256;
	}
	/**
	 * @param sha256 the sha256 to set
	 */
	public void setSha256(String sha256) {
		this.sha256 = sha256;
	}
	/**
	 * @return the requestJSON
	 */
	public String getRequestJSON() {
		return requestJSON;
	}
	/**
	 * @param requestJSON the requestJSON to set
	 */
	public void setRequestJSON(String requestJSON) {
		this.requestJSON = requestJSON;
	}
}
