<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="xss" uri="http://www.sportsdata.cn/xss"%>
<%@ page import="cn.sportsdata.webapp.youth.common.utils.CommonUtils" %>

<%
	String serverUrl = CommonUtils.getServerUrl(request);
%>

<style>
.overflow_text {
	overflow:hidden;
	text-overflow: ellipsis;
	white-space: nowrap;
}
</style>

<div class="dashboard-container" style="background-image: url(<%=serverUrl%>resources/images/background.jpg);background-color: #000000;height:746px;margin-top:-80px;margin-left:-20px;margin-right:-20px;
-webkit-filter: blur(5px);
            -moz-filter: blur(5px);
            -o-filter: blur(5px);
            -ms-filter: blur(5px);
            filter: blur(5px);">
	
</div>

