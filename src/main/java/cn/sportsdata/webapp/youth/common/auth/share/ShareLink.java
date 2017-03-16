/**
 * 
 */
package cn.sportsdata.webapp.youth.common.auth.share;

import java.io.Serializable;
import java.util.UUID;

import cn.sportsdata.security.crypto.exceptions.WbxSecException;
import cn.sportsdata.webapp.youth.common.exceptions.SoccerProException;
import cn.sportsdata.webapp.youth.common.utils.SecurityUtils;
import cn.sportsdata.webapp.youth.common.utils.StringUtil;

/**
 * @author king
 *
 */
public class ShareLink implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1138590302049232988L;

	private ShareLinkBO shareLinkBO;
	
	private String sha256Digest;
	
	private String uuidStr;
	
	public ShareLink(String uri) {
		this.shareLinkBO = new ShareLinkBO(uri);
	}
	
	public void addParameter(String name, String value) {
		if (!StringUtil.isBlank(name) && !StringUtil.isBlank(value)) {
			this.shareLinkBO.addParameter(name, value);
		}
	}
	
	public String toJson() throws SoccerProException {
		return this.shareLinkBO.toJson();
	}
	
	public String sha256() throws SoccerProException {
		if (sha256Digest == null) {
			try {
				sha256Digest =  SecurityUtils.getSha256Sum(this.toJson());
			} catch (WbxSecException e) {
				throw new SoccerProException("create sha256 failed", e);
			}
		}
		return sha256Digest;
	}
	
	public String getUUID() {
		if (uuidStr == null) {
			UUID uuid = UUID.randomUUID();
			uuidStr = uuid.toString();
		}
		return uuidStr;
	}
}
