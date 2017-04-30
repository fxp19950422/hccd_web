package cn.sportsdata.webapp.youth.web.controller;

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
				d.setDepartmentCode("1");
				return d;
			}
			return (DepartmentVO) obj.getDepartmentVO();
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
	
	protected void setLoginVO(LoginVO loginVO, HttpServletRequest request) {
		Token obj = (Token)request.getSession().getAttribute(Constants.USER_SESSION_KEY);
		if(obj != null && loginVO != null){
			obj.setLoginVO(loginVO);
		}
	}
}
