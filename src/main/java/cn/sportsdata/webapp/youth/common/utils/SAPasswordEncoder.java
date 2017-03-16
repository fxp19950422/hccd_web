package cn.sportsdata.webapp.youth.common.utils;

import org.springframework.security.authentication.encoding.PasswordEncoder;

public class SAPasswordEncoder implements PasswordEncoder{

	@Override
	public String encodePassword(String rawPass, Object salt) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isPasswordValid(String encPass, String rawPass, Object salt) {
		
		return true;
	}

}
