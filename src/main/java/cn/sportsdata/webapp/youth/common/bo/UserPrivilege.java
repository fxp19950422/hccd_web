/**
 * 
 */
package cn.sportsdata.webapp.youth.common.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import cn.sportsdata.webapp.youth.common.vo.privilege.PrivilegeVO;

/**
 * @author king
 *
 */
public class UserPrivilege {

	private static final String IS_ADMIN_ITEMNAME = "isAdmin";
	private static final String IS_ORG_ADMIN_ITEMNAME = "isOrgAdmin";
	public static final String IS_ORG_USER_ITEMNAME = "isOrgUser";
	
	private final static String MENU_TYPE = "menu";
	private final static String CONFIG_TYPE = "config";
	
	List<PrivilegeVO> privilegeVOs;
	List<PrivilegeVO> menuPrivilegeVOs;
	List<PrivilegeVO> configPrivilegeVOs;
    
	/**
	 * @param privilegeVOs
	 */
	public UserPrivilege(List<PrivilegeVO> privilegeVOs) {
		this.privilegeVOs = privilegeVOs;
		if (this.privilegeVOs != null) {
			this.menuPrivilegeVOs = new ArrayList<PrivilegeVO>();
			this.configPrivilegeVOs = new ArrayList<PrivilegeVO>();
			
			for (int i = 0; i < this.privilegeVOs.size(); i++) {
				if (MENU_TYPE.equals(privilegeVOs.get(i).getType())) {
					menuPrivilegeVOs.add(privilegeVOs.get(i));
				} else {
					if (CONFIG_TYPE.equals(privilegeVOs.get(i).getType())) {
						configPrivilegeVOs.add(privilegeVOs.get(i));
					}
				}
			}
		}
	}
	
	/**
	 * @return the menuPrivilegeVOs
	 */
	public List<PrivilegeVO> getMenuPrivilegeVOs() {
		return menuPrivilegeVOs;
	}

	public boolean getBooleanPrivilegeValue(String itemName) {
		if (configPrivilegeVOs != null && !StringUtils.isBlank(itemName)) {
			for (int i = 0; i < configPrivilegeVOs.size(); i++) {
				if (itemName.equals(configPrivilegeVOs.get(i).getItemName())) {
					String itemValue = configPrivilegeVOs.get(i).getItemValue();
					return "1".equals(itemValue) || "true".endsWith(itemValue);
				} 
			}
		}
		return false;
	}
	
	public boolean isAdmin() {
		return getBooleanPrivilegeValue(IS_ADMIN_ITEMNAME);
	}
	
	public boolean isOrgAdmin() {
		return getBooleanPrivilegeValue(IS_ORG_ADMIN_ITEMNAME);
	}
	
	public boolean isOrgUser() {
		return getBooleanPrivilegeValue(IS_ORG_USER_ITEMNAME);
	}
}
