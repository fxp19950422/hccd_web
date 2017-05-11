package cn.sportsdata.webapp.youth.web.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.sportsdata.webapp.youth.common.bo.UserPrivilege;
import cn.sportsdata.webapp.youth.common.constants.Constants;
import cn.sportsdata.webapp.youth.common.vo.DepartmentVO;
import cn.sportsdata.webapp.youth.common.vo.OrgVO;
import cn.sportsdata.webapp.youth.common.vo.basic.Token;
import cn.sportsdata.webapp.youth.common.vo.login.LoginVO;

public class BaseController {
	
	protected LoginVO getCurrentUser(HttpServletRequest request) {
		Token obj = (Token)request.getSession().getAttribute(Constants.USER_SESSION_KEY);
		if(obj != null){
			return (LoginVO) obj.getLoginVO();
		}
		return null;
	}
	
	protected DepartmentVO getCurrentDepartment(HttpServletRequest request) {
		Token obj = (Token)request.getSession().getAttribute(Constants.USER_SESSION_KEY);
		if(obj != null){
			if (obj.getLoginVO().getUserName().equals("zhaozhuren")){
				DepartmentVO d = new DepartmentVO();
				d.setDepartmentCode("100001");
				return d;
			}
			return (DepartmentVO) obj.getDepartmentVO();
		}
		return null;
	}
	
	protected List<String> getCurrentDepartmentIdList(HttpServletRequest request) {
		Token obj = (Token)request.getSession().getAttribute(Constants.USER_SESSION_KEY);
		if(obj != null){
			String doctorCode = obj.getLoginVO().getHospitalUserInfo().getUserIdinHospital();
			if (doctorCode.equalsIgnoreCase("1701")) {
				return Arrays.asList(new String[] {"0309", "0321", "0316", "0317", "0312"});
			}
			if (doctorCode.equalsIgnoreCase("1702")) {
				return Arrays.asList(new String[] {"0309"});
			}
			if (doctorCode.equalsIgnoreCase("1705")) {
				return Arrays.asList(new String[] {"0317"});
			}
			if (doctorCode.equalsIgnoreCase("1703")) {
				return Arrays.asList(new String[] {"0316"});
			}
			if (doctorCode.equalsIgnoreCase("1765")) {
				return Arrays.asList(new String[] {"0321"});
			}
			if (doctorCode.equalsIgnoreCase("1572")) {
				return Arrays.asList(new String[] {"0312"});
			}
			return Arrays.asList(new String[] {"-1"});
		}
		return null;

	}
	
	protected OrgVO getCurrentOrg(HttpServletRequest request) {
		Token obj = (Token)request.getSession().getAttribute(Constants.USER_SESSION_KEY);
		if(obj != null){
			return obj.getOrgVO();
		}
		return null;
	}
	
	
	protected UserPrivilege getCurrentUserPrivilegeMgr(HttpServletRequest request) {
		Token obj = (Token)request.getSession().getAttribute(Constants.USER_SESSION_KEY);
		if(obj != null){
			return obj.getUserPrivilegeMgr();
		}
		return null;
	}
	
	protected String getCurrentRole(HttpServletRequest request) {
		Token obj = (Token)request.getSession().getAttribute(Constants.USER_SESSION_KEY);
		if(obj != null){
			return obj.getRole();
		}
		return null;
	}
	
	protected void setLoginVO(LoginVO loginVO, HttpServletRequest request) {
		Token obj = (Token)request.getSession().getAttribute(Constants.USER_SESSION_KEY);
		if(obj != null && loginVO != null){
			obj.setLoginVO(loginVO);
		}
	}
}
