package cn.sportsdata.webapp.youth.common.utils;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

public class CommonUtils {
	public static String getServerUrl(HttpServletRequest request) {
		StringBuilder sb = new StringBuilder();
		
		sb.append(request.getScheme()).append("://").append(request.getServerName()).append(":").
		append(request.getServerPort()).append(request.getContextPath()).append("/");
		
		return sb.toString();
	}
}
