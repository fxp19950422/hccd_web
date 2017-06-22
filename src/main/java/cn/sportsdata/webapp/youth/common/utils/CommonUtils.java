package cn.sportsdata.webapp.youth.common.utils;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

public class CommonUtils {
	public static String getServerUrl(HttpServletRequest request) {
		StringBuilder sb = new StringBuilder();
		
		sb.append(request.getScheme()).append("://").append(request.getServerName()).append(":").
		append(request.getServerPort()).append(request.getContextPath()).append("/");
		
		return sb.toString();
	}
	
	public static Boolean isMobileFormat(String str) {
		String regExp = "^((13[0-9])|(15[^4])|(18[0-9])|(17[0-8])|(147))\\d{8}$";  
        Pattern p = Pattern.compile(regExp);  
        Matcher m = p.matcher(str);  
        return m.matches();
	}
}
