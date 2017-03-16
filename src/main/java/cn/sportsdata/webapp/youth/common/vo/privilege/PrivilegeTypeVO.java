/**
 * 
 */
package cn.sportsdata.webapp.youth.common.vo.privilege;

import cn.sportsdata.webapp.youth.common.vo.BaseVO;

import java.io.Serializable;

/**
 * @author king
 *
 */
public class PrivilegeTypeVO extends BaseVO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2146975109963670327L;
	private long id;
	private String name;
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
