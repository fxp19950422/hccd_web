package cn.sportsdata.webapp.youth.common.utils;

import java.util.Collection;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;

import cn.sportsdata.webapp.youth.common.bo.UserPrivilege;
import cn.sportsdata.webapp.youth.common.constants.Constants;
import cn.sportsdata.webapp.youth.common.vo.basic.Token;

public class SAAccessDecisionManager implements AccessDecisionManager{
	private static final String ROLE_ADMIN="ROLE_ADMIN";
	private static final String ROLE_ORG_ADMIN ="ROLE_ORG_ADMIN";
	private static final String ROLE_ORG_USER="ROLE_ORG_USER";
	

	@Override
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
		
		FilterInvocation fi = (FilterInvocation)object;
		
		Token token = (Token)fi.getHttpRequest().getSession().getAttribute(Constants.USER_SESSION_KEY);
		
		if (isAuthenticatedUser(configAttributes, token.getUserPrivilegeMgr())){
			return;
		} else {
			throw new InsufficientAuthenticationException("Insufficient privilege to access the url");
		}
	}

	private boolean isAuthenticatedUser(Collection<ConfigAttribute> configAttributes, UserPrivilege privilege){
		boolean isAuth = false;
		for (ConfigAttribute attribute : configAttributes){
			if (ROLE_ADMIN.equals(attribute.getAttribute())){
				isAuth = isAuth || privilege.isAdmin();
				
			} else if (ROLE_ORG_ADMIN.equals(attribute.getAttribute())){
				isAuth = isAuth || privilege.isOrgAdmin();
				
			} else if (ROLE_ORG_USER.equals(attribute.getAttribute())){
				isAuth = isAuth || privilege.isOrgUser();
				
			} 
			
		}
		
		return isAuth;
	}
	
	@Override
	public boolean supports(ConfigAttribute attribute) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return true;
	}

}
