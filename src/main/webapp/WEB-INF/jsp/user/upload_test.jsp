<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ page import="cn.sportsdata.webapp.youth.common.utils.CommonUtils" %>

<%
	String serverUrl = CommonUtils.getServerUrl(request);
%>

<html>
	<head>
		<link href="<%=serverUrl%>resources/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
		<link href="<%=serverUrl%>resources/css/jquery.crop.css" rel="stylesheet" type="text/css"/>
		<link href="<%=serverUrl%>resources/css/simple-slider.css" rel="stylesheet" type="text/css"/>
		
		<script src="<%=serverUrl%>resources/js/jquery-1.11.1.min.js" type="text/javascript"></script>
		<script src="<%=serverUrl%>resources/js/bootstrap.js" type="text/javascript"></script>
		<script src="<%=serverUrl%>resources/js/lodash.min.js" type="text/javascript"></script>
		<script src="<%=serverUrl%>resources/js/plupload.full.min.js" type="text/javascript"></script>
		<script src="<%=serverUrl%>resources/js/jquery.crop.js" type="text/javascript"></script>
		<script src="<%=serverUrl%>resources/js/simple-slider.js" type="text/javascript"></script>
		<script src="<%=serverUrl%>resources/js/fileupload.js" type="text/javascript"></script>
		
	</head>
	<body>
		<h3>文件上传控件测试页面，临时页面</h3>
		<button id="upload_btn" class="btn btn-success">上传图片</button>
		<img id="user_avatar" style="border: 1px solid #cecece;border-radius: 100px;height: 100px;width: 100px;" src="<%=serverUrl%>resources/images/user_avatar.png">
		
		<script>
			$(function() {
				var hagkUpload = new HagkUpload({
					'baseUrl': '<%=serverUrl%>',
					'uploadTriggerSelector': '#upload_btn',
					'imageSelector': '#user_avatar'
				});
				
				hagkUpload.init();
			});
		</script>
	</body>
</html>



