/**
 * 
 */
package cn.sportsdata.webapp.youth.common.vo.privilege;

import cn.sportsdata.webapp.youth.common.vo.BaseVO;

import java.io.Serializable;

/**
 * @author huangpc
 *
 */
public class UserPrivilegeTypeAssociationVO extends BaseVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9160753148130645588L;
	private String userId;
	private long privilegeTypeId;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public long getPrivilegeTypeId() {
		return privilegeTypeId;
	}
	public void setPrivilegeTypeId(long privilegeTypeId) {
		this.privilegeTypeId = privilegeTypeId;
	}
}
