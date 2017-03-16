/**
 * 
 */
package cn.sportsdata.webapp.youth.common.auth.share;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.sportsdata.webapp.youth.common.exceptions.SoccerProException;
import cn.sportsdata.webapp.youth.common.utils.StringUtil;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author king
 *
 */
public class ShareLinkBO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1138590302049232988L;

	/**
	 * note: uri is relative path
	 * E.g the full path is http://localhost:8080/soccerpro/system/system/index
	 * uri = /system/system/index
	 */
	private String uri;
	
	
	private List<ShareParameter> paramesters;
	/**
	 * @param uri
	 */
	public ShareLinkBO(String uri) {
		this.uri = uri;
	}
	
	/**
	 * for json string to object
	 */
	private ShareLinkBO() {
	}
	
	public void addParameter(String name, String value) {
		if (!StringUtil.isBlank(name) && !StringUtil.isBlank(value)) {
			if (this.paramesters == null) {
				this.paramesters = new ArrayList();
			}
			this.paramesters.add(new ShareParameter(name, value));
		}
	}
	
	
	/**
	 * @return the uri
	 */
	public String getUri() {
		return uri;
	}

	/**
	 * @return the paramesters
	 */
	public List<ShareParameter> getParamesters() {
		return paramesters;
	}

	public String toJson() throws SoccerProException {
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		try {
			json = mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			throw new SoccerProException("ShareLinkBO to JSON failed", e);
		}
		return json;
	}
	
	public String completedURLWithToken(String token){
		StringBuffer buf = new StringBuffer(uri);
		buf.append("?token=").append(token);
		if (paramesters != null) {
			for (int i = 0; i < this.paramesters.size(); i++) {
				buf.append("&");
				ShareParameter param = this.paramesters.get(i);
				buf.append(param.getName());
				buf.append("=");
				buf.append(param.getValue());
			}
		}
		return buf.toString();
	}
	
	public static ShareLinkBO getInstanceByJSON(String json) throws SoccerProException {
		ObjectMapper mapper = new ObjectMapper();  
		ShareLinkBO bo;
		try {
			bo = mapper.readValue(json, ShareLinkBO.class);
		} catch (IOException e) {
			throw new SoccerProException("JSON to ShareLinkBO object failed", e);
		}
		return bo;
	}
}
