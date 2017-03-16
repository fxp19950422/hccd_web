/**
 * 
 */
package cn.sportsdata.webapp.youth.common.auth.share;

import java.io.Serializable;

/**
 * @author king
 *
 */
public class ShareParameter implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6983663111026883596L;
	private String name;
	private String value;
	
	/**
	 * for json string to object
	 */
	public ShareParameter() {
	}
	
	/**
	 * @param name
	 * @param value
	 */
	public ShareParameter(String name, String value) {
		this.name = name;
		this.value = value;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	
	public boolean equals(Object obj) {
        if (obj != null && obj instanceof ShareParameter) {
        	if (this.name.equals(((ShareParameter)obj).getName()) && this.value.equals(((ShareParameter)obj).getValue())) {
        		return true;
        	}
        }
        return false;
    }
	
	

}
