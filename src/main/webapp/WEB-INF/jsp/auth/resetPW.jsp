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
		<div style="width:400px;">
		    <c:if test="${actionAuthenticationSuccess}">
			    <div style="color:red" id="error_msg">重置密码失败</div>
			</c:if>
			<form id="resetForm" action="auth/resetPW" method="post">
					<div>

						<input type="hidden"  name="username1" id="username1" value="${username}"/>
				
						<input type="hidden" name="key1" id="key1" value="${key}" />
					
						<div class="input_item clearfix form-group"  style="display: block;">
							<span style="margin-right:10px;line-height:34px;">新密码</span>
							<input type="password" data-bv-field="password" id="password" name="password" class="input"
										onFocus="if(this.value=='请输入新密码')this.value = '';" style="width:270px;float:right"  
										placeholder="请输入新密码" value=""/>
			
						</div>	
						<div class="input_item clearfix form-group"  style="display: block;">
							<span style="margin-right:10px;line-height:34px;">重复密码</span>
							<input type="password" data-bv-field="repassword" id="repassword" name="repassword" class="input"
										onFocus="if(this.value=='请再次输入密码')this.value = '';" style="width:270px;float:right" 
										placeholder="请再次输入密码" value=""/>
			
						</div>		
					</div>
			</form>
			<div class="input_item clearfix" data-propertyname="submit" data-controltype="Botton" style="display: block;">
						<input type="button" id="btnLogin" class="btn btn_green btn_active btn_block btn_lg" onClick="submitForm();" value="重置密码">
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
<script type="text/javascript">
	$("#resetForm").bootstrapValidator({
	    message: '您输入的值不合法。',
	    feedbackIcons: {
	        valid: 'glyphicon glyphicon-ok',
	        invalid: 'glyphicon glyphicon-remove',
	        validating: 'glyphicon glyphicon-refresh'
	    },
	    fields: {
	    	password: {
               validators: {
                   notEmpty: {
                       message: '密码不能为空'
                   },
                   stringLength: {
                  	   min:6,
                       max: 30,
                       message: '请输入6-30字符以内的密码。'
                   },
                   identical: {
                       field: 'repassword',
                       message: '两次密码不一致'
                   },
                   different: {
                       field: 'username',
                       message: '账号和密码不能相同'
                   }
               }
           },
           repassword: {
               validators: {
            	   notEmpty: {
                       message: '密码不能为空'
                   },
                   identical: {
                       field: 'password',
                       message: '两次密码不一致'
                   }
               }
           }
	    }
	});
	
	function submitForm() {
		$("#resetForm").data('bootstrapValidator').validate();
		if($("#resetForm").data('bootstrapValidator').isValid()){
			$.ajax({
				type : "post",
				url : "auth/resetPW",
				data : $('#resetForm').serialize(),
				cache : false,
				dataType: "json",
				success : function(data) {
					if(data.status){
						alert("重置密码成功，返回登录页面");
						location.href = "<%=basePath%>auth/login";
					}else{
						if(data.errorCode == 401){
							alert("错误");
						}else if (data.errorCode == 10001){
							alert("表单内容有错，请检查!");
						}else if (data.errorCode == 10002){
							alert("重置密码链接已超时，请返回主页重新请求密码重置!");
							location.href = "<%=basePath%>auth/login";
						}else {
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