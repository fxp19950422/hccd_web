<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
	+ request.getServerName() + (request.getServerPort() == 80 ? "":":" + request.getServerPort())
			+ path + "/";
	response.setHeader("Cache-Control", "no-store");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", 0);
%>
<html><head>
<!-- meta -->
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="renderer" content="webkit">
<meta charset="UTF-8">
<title>专业医疗信息管理系统</title>
<!-- 页面样式 --><!-- H5	 -->
<!--[if lt IE 9]>
  	<script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
  	<script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
<![endif]-->
    <link rel="stylesheet" href="<%=basePath%>resources/css/bootstrap.css" />
    <link rel="stylesheet" href="<%=basePath%>resources/css/login_style.css" />
   <script src="<%=basePath%>resources/js/jquery-1.11.1.min.js" type="text/javascript"></script>
    <style>
    .logo {
		position: absolute;
		z-index: 9999;
		top: 50px;
		display: inline-block;
		width: 104px;
		height: 104px;
	}

	body {
	background:url(<%=basePath %>resources/images/login/background.jpg) top center no-repeat; background-size:cover;
	    filter: progid:DXImageTransform.Microsoft.AlphaImageLoader(
		src="<%=basePath %>resources/images/login/background.jpg",
		sizingMethod='scale');
		font-family: "微软雅黑";
    }
	
	.title {
		position: relative;
		z-index: 1;
		top:130px ;
		margin-right: auto;
		margin-bottom: 0px;
		margin-left: auto;
		display: table;
	}
	
    </style>
    
</head>
<body>
<div style="margin-left:10%">
    <div class="logo">
    	<!-- TODO add new logo of medical sports -->
    	
    </div>
</div>
<div class="title">
<span style="color:#FFFFFF;font-size:24px">专业医疗信息管理系统</span>
</div>
<div style="height: 330px;">
<section class="content_box cleafix" style="width:330px;" id="secArea">
	<div>
		
		<form id="loginForm" action="<%=basePath %>auth/login" method="post">
			<div>
				<div class="input_item clearfix"  style="display: block;">
					<!-- <span style="margin-right:10px;line-height:34px;">帐&nbsp;&nbsp;&nbsp;号</span> -->
					<input type="text" id="userName" name="userName" class="input input_white" value="${loginVO.userName}"
								onFocus="if(this.value=='请输入账号')this.value = '';" style="width:100%"  onkeydown="if(event.keyCode==13){submitForm();}" 
								placeholder="请输入账号"/>
					
				</div>
				<div class="input_item clearfix"  style="display: block;">
					<!-- <span style="margin-right:10px;line-height:34px;">密&nbsp;&nbsp;&nbsp;码</span> -->
					<input id="password" name="password" type="password" value="${loginVO.password}" class="input input_white" style="width:100%" onkeydown="if(event.keyCode==13){submitForm();}" 
					placeholder="请输入密码"/>
				</div>
				<div class="input_item clearfix"  style="display: block;">
					<!-- <span style="margin-right:10px;line-height:34px;">验证码</span> -->
					<input id="veryCode" placeholder="验证码" name="veryCode" class="input input_white" style="width:48% " type="text" onkeydown="if(event.keyCode==13){submitForm();}"/>
					<img id="imgObj" name="imgObj" title="点击图片刷新" style="vertical-align:middle;margin-left:5%;" width="45%" height="34px" src="<%=basePath %>xuan/verifyCode" onclick="this.src='<%=basePath %>xuan/verifyCode'+'?'+(new Date()).getTime()" />
					
					
				</div>
				<div class="input_item clearfix" data-propertyname="submit" data-controltype="Botton" style="display: block;">
					<input type="button" id="btnLogin" class="btn btn_green btn_active btn_block btn_lg" onClick="submitForm();" value="登&nbsp;录">
				</div>
				<div class="input_item clearfix" style="margin-top:15px">
					<ul>
						<%-- <li class="first">
							<a title="创建新用户账号" class="forgot_pwd" href="<%=basePath %>user/regist">注册</a>
						</li>
						<span><a  class="forgot_pwd">&nbsp;&nbsp;&nbsp;</a></span> --%>
						<li class="last">
							<a title="通过电子邮件重设密码。" class="forgot_pwd" href="<%=basePath %>auth/forgetPW" style="color:#fff;text-decoration:underline">忘记密码?</a>
						</li>
						<%-- <li class="first">
							<a target="_blank" title="常见问题" class="forgot_pwd" href="<%=basePath %>system/system/faq">常见问题&nbsp;&nbsp;&nbsp;</a>
						</li> --%>
					</ul>
				</div>
				
			</div>
		</form>
	</div>
</section>
</div>
<footer style="padding-top:50px">
	<!-- <h5 class="slogan"><a href="https://www.mozilla.org/zh-CN/firefox/new/?utm_source=getfirefox-com&utm_medium=referral" target='_blank' style="color:red">建议使用火狐(FireFox)浏览器</a></h5> -->
	<h5 class="slogan" style="margin-bottom:0px;color:white;font-size: 12px;">XXXXXXXXXXXXXXXX有限公司</h5>
	<h5 class="slogan" style="margin-top: 5px;color:white;font-size: 12px;">开启中国医疗数据时代</h5>
</footer>


</body></html>
<!--[if lt IE 8]>
<script>
	alert("您的IE浏览器版本过低，建议您升级您的浏览器后使用本系统。");
	$("#btnLogin").attr("disabled","disabled"); 
	
</script>
<![endif]-->

<!--[if lt IE 9]>
<script>
	$("#secArea").css("width","420px"); 
</script>
<![endif]-->
<script> 
<c:if test="${validationFailed}">
$(function(){
	alert("${validationFailedMsg}");
})
</c:if>
	function submitForm() {
		<%-- $.ajax({
			type : "post",
			url : "<%=basePath %>auth/login",
			data : $('#loginForm').serialize(),
			cache : false,
			dataType: "json",
			success : function(data) {
				if(data.status){
					location.href = '<%=basePath%>'+data.result;
				}else{
					if(data.errorCode == 401){
						alert("错误");
					}else if (data.errorCode == 10007){
						alert('验证码错误！');
						document.getElementById("imgObj").src = "<%=basePath %>xuan/verifyCode?"+(new Date()).getTime();
					}else if (data.errorCode == 10008){
						alert('用户名或密码错误！');
						document.getElementById("imgObj").src = "<%=basePath %>xuan/verifyCode?"+(new Date()).getTime();
					}else {
						alert("服务器异常!");
					}
				}
			},
			error : function() {
				alert("服务器异常!");
			}
		}); --%>
		
		$("#loginForm").submit();
	}
</script>
