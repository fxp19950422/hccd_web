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
<title>专业足球管理系统</title>
<!-- 页面样式 --><!-- H5	 -->
<!--[if lt IE 9]>
  	<script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
  	<script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
<![endif]-->
		<base href="<%=basePath%>" />
		<link rel="stylesheet" href="<%=basePath%>resources/css/bootstrap.css" />
	    <link rel="stylesheet" href="<%=basePath%>resources/css/login_style.css" />
	    <link href="<%=basePath%>resources/css/bootstrapValidator.css" rel="stylesheet" type="text/css"/>
	    <script src="<%=basePath%>resources/js/jquery-1.11.1.min.js" type="text/javascript"></script>
	    <script src="<%=basePath%>resources/js/bootstrapValidator.js" type="text/javascript"></script>
	    
    <style>
    .logo {
		position: absolute;
		z-index: 9999;
		top: 20px;
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
		top:80px ;
		margin-right: auto;
		margin-bottom: 0px;
		margin-left: auto;
		display: table;
	}
	
    </style>
    
</head>

<body>
<div style="margin-left:10%">
    <div class="logo"><img src="<%=basePath %>resources/images/login/logo.png"/></div>
</div>
<div class="title">
<span style="color:white;font-size:25px">专业足球管理系统</span>
</div>
<div style="height: 330px;">
<section class="content_box cleafix" style="width:330px" id="secArea">
	<div>
	    <c:if test="${actionAuthenticationSuccess}">
		    <div style="color:red" id="error_msg">发送重置密码邮件</div>	
		</c:if>
		<form id="forgetForm">
			<div style="width:400px;">
				<div class="form-group clearfix"  style="display: block;">
					<span style="width: 10%;line-height:34px;float:left;">账号</span>
					<div class="form-group" style="width: 90%; float: left;">
						<input type="text" id="username" data-bv-field="username" class="form-control" name="username" required
									placeholder="请输入账号"/>
					</div>
				</div>	
				<div class="form-group clearfix"  style="display: block;">
					<span style="width: 10%;;line-height:34px;float:left;">邮箱&nbsp;</span>
					<div class="form-group" style="width: 90%; float: left;">
	            		<input id="email" data-bv-field="email" class="form-control" name="email" type="text" required placeholder="请输入注册邮箱"/>
	        		</div>
				</div>		
			</div>
			<h2 id="errorMessage" style="color: red;"></h2>
		</form>
		<div class="clearfix" data-propertyname="submit" data-controltype="Botton" style="display: block;">
					<input type="button" id="btnLogin" class="btn btn_green btn_active btn_block btn_lg" onClick="submitForm();" value="发送重置邮件">
		</div>
	</div>
</section>
</div>

<footer style="padding-top:50px">
	<!-- <h5 class="slogan"><a href="https://www.mozilla.org/zh-CN/firefox/new/?utm_source=getfirefox-com&utm_medium=referral" target='_blank' style="color:red">建议使用火狐(FireFox)浏览器</a></h5> -->
	<h5 class="slogan" style="margin-bottom:0px;color:white">华奥国科体育大数据科技(北京)有限公司</h5>
	<h5 class="slogan" style="margin-top: 5px;color:white">开启中国足球数据时代</h5>
</footer>


</body></html>
<!--[if lt IE 8]>
<script>
	alert("您的IE浏览器版本过低，建议您升级您的浏览器后使用本系统。");
	$("#btnLogin").attr("disabled","disabled"); 
</script>
<![endif]-->

<script>

	function validation(){
		$("#forgetForm").bootstrapValidator({
	        message: '您输入的值不合法。',
	        feedbackIcons: {
	            valid: 'glyphicon glyphicon-ok',
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	        	username:{
	            	 message: '账号不合法',
	            	 validators: {
	                     notEmpty: {
	                         message: '账号是必填项，请正确填写账号。'
	                     },
	                     stringLength: {
	                    	 min:6,
	                         max: 30,
	                         message: '请输入6-18字符以内的账号。'
	                     },
	                     regexp: {
	                         regexp: /^[a-zA-Z0-9_]+$/,
	                         message: '账号只允许由字母，数字，下划线构成。'
	                     }
	                 }
	            },
	            email:{
		           	 message: '邮箱不合法',
		           	 validators: {
		                    notEmpty: {
		                        message: '邮箱是必填项'
		                    },
		                    emailAddress: {
		                        message: '请正确填写您的邮箱'
		                    }
		                }
	           }
	        }
	    });
	}
	
	validation();
	
	function submitForm() {
		$("#forgetForm").data('bootstrapValidator').validate();
		if($("#forgetForm").data('bootstrapValidator').isValid()){
			$.ajax({
				type : "post",
				url : "auth/forgetPW",
				data : $('#forgetForm').serialize(),
				cache : false,
				dataType: "json",
				success : function(data) {
					if(data.status){
						alert("已经发送密码重置邮件，请登录您的注册邮箱查看");
						location.href = "<%=basePath%>auth/login";
					}else{
						if(data.errorCode == 401){
							alert("错误");
						}else if(data.errorCode == 10003){
							alert("没有此账号");
						}else if(data.errorCode == 10004){
							alert("未设置注册邮箱或账号与注册邮箱不匹配");
						}else{
							alert("服务器异常!");
						}
					}
				},
				error : function() {
					alert("服务器异常!");
				}
			});	
		}
	}
</script>