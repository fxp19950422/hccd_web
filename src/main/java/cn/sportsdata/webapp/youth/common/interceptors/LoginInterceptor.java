package cn.sportsdata.webapp.youth.common.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.sportsdata.webapp.youth.common.constants.Constants;
import cn.sportsdata.webapp.youth.common.utils.CommonUtils;
import cn.sportsdata.webapp.youth.common.utils.CookieUtils;
import cn.sportsdata.webapp.youth.common.utils.SecurityUtils;
import cn.sportsdata.webapp.youth.common.utils.StringUtil;
import cn.sportsdata.webapp.youth.common.vo.OrgVO;
import cn.sportsdata.webapp.youth.common.vo.account.AccountVO;
import cn.sportsdata.webapp.youth.common.vo.basic.Token;
import cn.sportsdata.webapp.youth.common.vo.login.LoginVO;
import cn.sportsdata.webapp.youth.service.account.AccountService;

import com.fasterxml.jackson.databind.JsonNode;

public class LoginInterceptor extends HandlerInterceptorAdapter {
	private static Logger logger = Logger.getLogger(LoginInterceptor.class);
	@Autowired
	private AccountService accountService;
	
	

	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		Token token = (Token) request.getSession().getAttribute(Constants.USER_SESSION_KEY);
		if(token == null) {
			String tokenCookie = CookieUtils.getHagkTokenCookie(request);
			
			if(tokenCookie != null) {
				JsonNode json = SecurityUtils.vefifyAuthToken(tokenCookie);
				if(json != null) {
					String loginID = json.get("loginID").asText();
					String orgID = json.get("orgID").asText();
					AccountVO accountVO = accountService.getAccountByAccountIDOrgId(loginID, orgID);
					OrgVO orgVO = accountService.getOrgsByAccountOrgID(loginID, orgID);
					
					if (accountVO != null && orgVO != null) {
						LoginVO userVO = new LoginVO(accountVO);
						token = new Token(userVO);
						token.setOrgVO(orgVO);
						token.initPrivilege();
						request.getSession().setAttribute(Constants.USER_SESSION_KEY, token);
					}
				}
			}
		}
		
		if(token == null){
			String requestType = request.getHeader("X-Requested-With");  
            if (!StringUtil.isBlank(requestType) && requestType.equalsIgnoreCase("XMLHttpRequest")) {  
            	response.setStatus(401);  
            	response.setHeader("sessionstatus", "timeout");  
            	response.addHeader("loginPath", CommonUtils.getServerUrl(request) + "auth/login");  
            	response.getWriter().write("gotoLogin");
                return false;  
            } else {
            	logger.error("Login required for : " + request.getRequestURL());
    			response.sendRedirect(CommonUtils.getServerUrl(request) + "auth/login");
            }
            
			return false;
		}
		
		return super.preHandle(request, response, handler);
	
	}
}

