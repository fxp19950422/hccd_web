package cn.healthdata.webapp.hospital.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class CookieUtils {
	public static final String HAGK_TOKEN = "HAGK_TOKEN";
	
	public static void setHagkCookie(HttpServletResponse response) {
		
		
//		ObjectMapper mapper = new ObjectMapper();
//        ObjectNode node = mapper.createObjectNode();
//        node.put("loginID", token.getLoginVO().getId());
//        node.put("orgID", token.getOrgVO().getId());
//        String info;
		
//			info = mapper.writeValueAsString(node);
//			String tokenCookie =  SecurityUtils.generateAuthToken(info);
			addCookie(response, HAGK_TOKEN, "cookieforsession", 60*60*24);
		
        
	}
	
	public static String getHagkTokenCookie(HttpServletRequest request) {
		return getCookie(request, HAGK_TOKEN);
	}
	
	private static void addCookie(HttpServletResponse response, String name, String value, int keepTime){
		Cookie cookies = new Cookie(name, value);  
		
        cookies.setPath("/public_platform");  
        cookies.setMaxAge(keepTime);
        cookies.setHttpOnly(true);
        
        response.addCookie(cookies);
	}
	
	private static String getCookie(HttpServletRequest request, String name) {
		Cookie cookie = getCookieObj(request, name);
		if (cookie != null) {
			return cookie.getValue();
		}
        
        return null;
    }
	
	private static Cookie getCookieObj(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        
        if (cookies == null) {
        	return null;
        }
        
        for (int i = 0; i < cookies.length; i++) {
            if (cookies[i].getName().equals(name)) {
                return cookies[i];
            }
        }
        
        return null;
    }
	
	
	public static void removeCookie(HttpServletRequest request, HttpServletResponse response, String cookieName){
	    Cookie newCookie = getCookieObj(request, cookieName);
	    if (newCookie != null) {
	    	newCookie.setPath("/soccerpro");
		    newCookie.setMaxAge(0);
		    response.addCookie(newCookie);
	    }
	}
}
