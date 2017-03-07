<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
	+ request.getServerName() + (request.getServerPort() == 80 ? "":":" + request.getServerPort())
			+ path + "/";
 	response.setHeader("Cache-Control", "Private");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", 0);
%>
<html>
<head>
<!-- meta -->
<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
<meta name="renderer" content="webkit">
<meta charset="UTF-8">
<title>心理趣味测试</title>

<link rel="stylesheet" href="<%=basePath%>resources/css/common.css" />
<link rel="stylesheet" href="<%=basePath%>resources/css/bootstrap.min.css" />
<link rel="stylesheet" href="<%=basePath%>resources/css/bootstrapValidator.css" />
<script src="<%=basePath%>resources/js/jquery-1.11.1.min.js" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/bootstrapValidator.js" type="text/javascript"></script>
<style>
	.inputLabel {
		color: #666;
		height: 34px;
		line-height: 34px;
	}
	
	.weui_cell_warn{
		border-color:red
	}
	
	.page_title {
	    text-align: center;
	    font-size: 34px;
	    color: #ffbb80;
	    font-weight: 400;
	    margin: 0 15%;
	}
	.page_sub_title {
	    text-align: center;
	    font-size: 18px;
	    color: #f29048;
	    font-weight: 400;
	    margin: 0 15%;
	}
	
	.container {
	    padding-right: 15px;
	    padding-left: 15px;
	    margin-right: 0;
	    margin-left: 0;
	}
	
	.top_banner {
	    background:url(<%=basePath%>resources/img/xinli.png) top center no-repeat; background-size:cover;
	    filter: progid:DXImageTransform.Microsoft.AlphaImageLoader(
		src="<%=basePath%>resources/img/xinli.png",
		sizingMethod='scale');
    }
	
	body{
		background:#EFF4F7;
	}
</style>
</head>
<body>
<div style="padding: 2em 0;background-color: #ffffff;" class="top_banner">
		    <p>&nbsp;</p>
		    <p class="page_sub_title">本测试是根据知名高校的心理学研究成果进行的关于青少年运动心理评估的趣味测试</p>
</div>
<div style="margin-left:20px;margin-right:20px;" id="container">
		

	
	<form id="loginForm" action="<%=basePath %>auth/login" method="post" style="margin-top:30px;padding-top:30px;">
		<div class="row">
			<div class="col-md-3 col-sm-3 col-xs-3 inputLabel">
				<span >手机号码</span>
			</div>
			<div class="col-md-9 col-sm-9 col-xs-9">
				<div class="form-group " >
		          	<input id="phone_number" name="phone_number" class="form-control weui_phone weui_cell"  placeholder="请输入手机号码"  value="${loginVO.phone_number}"/>
	      		</div>
	   		</div>
   		</div>
   		
   		<div class="row">
			<div class="col-md-3 col-sm-3 col-xs-3 inputLabel">
				<span >年龄</span>
			</div>
			<div class="col-md-9 col-sm-9 col-xs-9">
				<div class="form-group ">
		          	<input id="age" name="age" class="form-control"  placeholder="请输入年龄"  value="${loginVO.age}"/>
	      		</div>
	   		</div>
   		</div>
   		
   		<div class="row">
			<div class="col-md-3 col-sm-3 col-xs-3 inputLabel">
				<span >性别</span>
			</div>
			<div class="col-md-9 col-sm-9 col-xs-9">
				<div class="form-group ">
		          	<select class="form-control" name="gender">
		          		<option value="male">男</option>
		          		<option value="male">女</option>
		          	</select>
	      		</div>
	   		</div>
   		</div>
   		
   		<div class="row">
			<div class="col-md-3 col-sm-3 col-xs-3 inputLabel">
				<span >邀请码</span>
			</div>
			<div class="col-md-9 col-sm-9 col-xs-9">
				<div class="form-group weui_invite_code">
		          	<input class="form-control" id="inviteCode" name="inviteCode" type="text" placeholder="请输入邀请码" value="${inviteCode}"/>
		          	<i id="inviteCodeIcon" data-bv-icon-for="phone_number" class="form-control-feedback bv-no-label glyphicon glyphicon-remove " style="display: none;"></i>
		          	<small id="inviteCodeMsg" data-bv-result="INVALID" data-bv-for="phone_number" data-bv-validator="regexp" class="help-block weui_invite_msg" style="display: none;">邀请码有误</small>
	      		</div>
	   		</div>
   		</div>
   		
   		<div class="row">
			<div class="col-md-3 col-sm-3 col-xs-3 inputLabel">
				<span >验证码</span>
			</div>
			<div class="col-md-5 col-sm-5 col-xs-5">
				<div class="form-group weui_vcode">
		          	<input class="form-control" id="veryCode" name="veryCode" type="text" placeholder="请输入验证码" value="${veryCode}"/>
		          	<i id="veryCodeIcon" data-bv-icon-for="phone_number" class="form-control-feedback bv-no-label glyphicon glyphicon-remove" style="display: none;"></i>
		          	<small id="veryCodeMsg" data-bv-result="INVALID" data-bv-for="phone_number" data-bv-validator="regexp" class="help-block" style="display: none;">验证码有误</small>
	      		</div>
	   		</div>
	   		<div class="col-md-4 col-sm-4 col-xs-4">
	            <img id="imgObj" name="imgObj" style="width:100%;height:34px" title="点击图片刷新"  onclick="this.src='<%=basePath %>xuan/verifyCode'+'?'+(new Date()).getTime()" />
	        </div>
   		</div>
   		
	</form>
	<div  style="margin-top:30px; padding: 10px;">
	    	<button id="gotoTest" type="button" class="btn btn-danger" style="width:80%;margin-left:10%;margin-right:10%;border-color:#d7d7d9;background:#ffb96d;color:#ffffff">进入测试</button>
		    <button id="tryTest" type="button" class="btn btn-primary" style="width:80%;margin-top:15px;margin-left:10%;margin-right:10%;border-color:#d7d7d9;background:#ffb96d;color:#ffffff">试测</button>
	</div>
	
</div>


</body>
<script>
	 
	 $(function(){
			$("#veryCode").val("");
			$("#imgObj").attr("src","<%=basePath %>xuan/verifyCode?"+(new Date()).getTime());
			validation();
			$("#gotoTest").click(function(){
	            $("#loginForm").data('bootstrapValidator').validate();
	            if(!$("#loginForm").data('bootstrapValidator').isValid()){
	                return;
	            }
	            $("#loginForm")[0].submit();
	        });
			
			$("#tryTest").click(function(){
				var url1 = "/psytest/psytest_index_try";
				location.href = "<%=request.getContextPath()%>"+ url1;
	        });
			
			$("#inviteCode").on('input',function(e){
				$(".weui_invite_code").removeClass("has-feedback");
				$(".weui_invite_code").removeClass("has-error");
				$(".weui_invite_code i").css("display","none");
				$(".weui_invite_code .weui_invite_msg").css("display","none");
			}); 
			
			
			$("#veryCode").on('input',function(e){  
				$(".weui_vcode").removeClass("has-feedback");
				$(".weui_vcode").removeClass("has-error");
				$(".weui_vcode i").css("display","none");
				$(".weui_vcode small").css("display","none");
			}); 
			
			<c:if test="${validationFailedCode == '0001'}">
			$(".weui_vcode").addClass("has-feedback");
			$(".weui_vcode").addClass("has-error");
			$(".weui_vcode i").css("display","block");
			$(".weui_vcode small").css("display","block");
			</c:if>
		
		
		
			<c:if test="${validationFailedCode == '0002'}">
			$(".weui_invite_code").addClass("has-feedback");
			$(".weui_invite_code").addClass("has-error");
			$(".weui_invite_code i").css("display","block");
			$(".weui_invite_code .weui_invite_msg").css("display","block");
			</c:if>
	 });
	 
	 function submitForm(){

	        $.ajax({
	            type: 'post',
	            url: '<%=basePath %>auth/login',
	            data: $("#loginForm").serialize() ,
	            success : function(message){
	               alert('提交成功！');
	               $("#loginForm")[0].reset();
	            },
	            error : function(XMLHttpRequest, textStatus, errorThrown){
	               alert("提交失败！");
	               
	            }
	        });        
	    }
	 
	 function submitInfo(){
		
		 $("#loginForm").submit();
	 }
	 
	 function validation(){
	        $("#loginForm").bootstrapValidator({
	            message: '您输入的值不合法。',
	            feedbackIcons: {
	                valid: 'glyphicon glyphicon-ok',
	                invalid: 'glyphicon glyphicon-remove',
	                validating: 'glyphicon glyphicon-refresh'
	            },
	            fields: {
	            	phone_number:{
	                    message: '手机号码不合法',
	                    validators: {
	                        notEmpty: {
	                            message: '请输入手机号码'
	                        },
	                        regexp: {
	                             regexp: /((\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)/,
	                             message: '手机号码格式错误'
	                        }
	                     }
	                },
	                age:{
	                    message: '年龄不合法',
	                    validators: {
	                        notEmpty: {
	                            message: '请输入年龄'
	                        },
	                        between: {
	                            min: 1,
	                            max: 100,
	                            message: '年龄在1-100之间'
	                        }
	                    }
	                },
	                inviteCode:{
	                    message: '联系电话不合法',
	                    validators: {
	                        notEmpty: {
	                            message: '请输入邀请码'
	                        }
	                     }
	                }    
	            }
	        });
	    }
</script>
</html>

