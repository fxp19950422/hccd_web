package cn.sportsdata.webapp.youth.common.vo.basic;

import java.util.List;

import cn.sportsdata.webapp.youth.common.bo.UserPrivilege;
import cn.sportsdata.webapp.youth.common.utils.SpringUtils;
import cn.sportsdata.webapp.youth.common.vo.DepartmentVO;
import cn.sportsdata.webapp.youth.common.vo.OrgVO;
import cn.sportsdata.webapp.youth.common.vo.login.LoginVO;
import cn.sportsdata.webapp.youth.common.vo.privilege.PrivilegeVO;
import cn.sportsdata.webapp.youth.service.privilege.PrivilegeService;

public class Token {
	private LoginVO loginVO;
	private OrgVO orgVO;
	private DepartmentVO departmentVO;
public DepartmentVO getDepartmentVO() {
		return departmentVO;
	}
	
	String role = "";

	public void setRole(String role){
		this.role = role;
	}
	
	public String getRole(){
		return this.role;
	}

	public void setDepartmentVO(DepartmentVO departmentVO) {
		this.departmentVO = departmentVO;
	}

	//	private UserPrivilege userPrivilegeMgr;
//	private static PrivilegeService privilegeService = SpringUtils.getBean(PrivilegeService.class);
	private UserPrivilege userPrivilegeMgr;
	private static PrivilegeService privilegeService = SpringUtils.getBean(PrivilegeService.class);
	
	public Token(LoginVO loginVO) {
		this.loginVO = loginVO;
	}

	
	
	public void initPrivilege(){
		
		List<PrivilegeVO> privilegeVOs = privilegeService.getUserPrivileges(loginVO.getId(), orgVO.getId());
		this.userPrivilegeMgr = new UserPrivilege(privilegeVOs);
	}
	
	/**
	 * @return the userVO
	 */
	public LoginVO getLoginVO() {
		return loginVO;
	}
	
	/**
	 * @param loginVO the loginVO to set
	 */
	public void setLoginVO(LoginVO loginVO) {
		this.loginVO = loginVO;
	}

	/**
	 * @return the userPrivilegeMgr
	 */
	public UserPrivilege getUserPrivilegeMgr() {
		return userPrivilegeMgr;
	}

	public OrgVO getOrgVO() {
		return orgVO;
	}

	public void setOrgVO(OrgVO orgVO) {
		this.orgVO = orgVO;
	}

	
}
