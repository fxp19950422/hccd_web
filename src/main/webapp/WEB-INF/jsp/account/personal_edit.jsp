<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ page import="cn.sportsdata.webapp.youth.common.utils.CommonUtils" %>
<%
	String serverUrl = CommonUtils.getServerUrl(request);
%>


<div>
<div class="button_area">
	<button id="add_btn" class="btn btn-primary" style="float: right;margin-left: 10px;">保存</button>
	<button id="cancel_btn" class="btn btn-default" style="float: right; margin-left: 10px;">取消</button>
</div>
<div class="clearfix"></div>
<div id="profileListArea" class="profileEditContent">
	<sa-panel title="基本资料">
		<form id="coach_form">
			<div class="row">
			<div class="col-md-2 text-center" >
					<c:choose>
						<c:when test="${ !empty account.avatar }">
							<img id="user_avatar" class="profileEditAvatar custom-image" src="<%=serverUrl%>file/downloadFile?fileName=${ account.avatar }"></img>
						</c:when>
						<c:otherwise>
							<div style="position: relative; width: 100px; height: 90px;"> 
								<img id="user_avatar" class="profileEditAvatar default-image" src="<%=serverUrl%>resources/images/user_avatar.png"></img>
								<span style="position: absolute; bottom: 0; left: 27px;font-size: 12px; color:#999999">上传头像</span> 
							</div>
						</c:otherwise>
					</c:choose>
				</div>
				<div class="col-md-10">
					<div class="row">
						<div class="col-md-6">
							<div class="col-md-3 inputLabel">
								<span >姓名</span>
							</div>
							<div class="col-md-9">
								<div class="form-group ">
				            		<input data-bv-field="name" class="form-control" name="name" value="${account.name}" type="text">
				        		</div>
			        		</div>
						</div>
						<div class="col-md-6">
							<div class="col-md-3 inputLabel">
								<span >账号</span>
							</div>
							<div class="col-md-9">
								<div class="form-group ">
				            		<input data-bv-field="username" class="form-control" name="username" value="${account.username}"  type="text"
				            		<c:if test="${ account != null }">
				            			readonly
				            		</c:if>
				            		>
				        		</div>
			        		</div>
						</div>
					</div>
					
					<div class="row">
						<div class="col-md-6">
							<div class="col-md-3 inputLabel">
								<span >生日</span>
							</div>
							<div class="col-md-9">
								<div class="input-group date">
								<div class="form-group">
									<input type="text" class="form-control calendar-input" id="birthday" data-bv-field="birthday" value="${account.birthday}"  name="birthday" readonly>
								</div>
								<span id="birthdayIcon" class="input-group-addon calendar-icon"><i class="glyphicon glyphicon-calendar major_color"></i></span>
								</div>
			        		</div>
						</div>
						<div class="col-md-6">
							<div class="col-md-3 inputLabel">
								<span >邮箱</span>
							</div>
							<div class="col-md-9">
								<div class="form-group ">
				            		<input data-bv-field="email" class="form-control" name="email" value="${account.email}"  type="text">
				        		</div>
			        		</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-6">
							<div class="col-md-3 inputLabel">
								<span >密码</span>
							</div>
							<div class="col-md-9">
								<div class="form-group ">
				            		<input data-bv-field="password" class="form-control" name="password" type="password" value="${account.encryptedPassword}"/>
				        		</div>
			        		</div>
						</div>
						<div class="col-md-6">
							<div class="col-md-3 inputLabel">
								<span >重复密码</span>
							</div>
							<div class="col-md-9">
								<div class="form-group ">
				            		<input data-bv-field="repassword" class="form-control" name="repassword" type="password" value="${account.encryptedPassword}"/>
				        		</div>
				        		
			        		</div>
			        		<input type="hidden" name="id" value="${account.id}" />
						</div>
					</div>
				</div>
				
			</div>
			<input type="hidden" name="avatar" id="encryptFileName" />
			
		</form>
	</sa-panel>
</div>
</div>

<script>

$(function() {
	setTimeout(function(){
		initEvent();
	}, 50);
});

function initEvent() {
	$('#birthday').datepicker({
		format : 'yyyy-mm-dd',
		language : 'zh-CN',
		autoclose : true,
		todayHighlight : true,
		toggleActive : true,
		startView:2,
		zIndexOffset:1031
	});
	$('#birthdayIcon').click(function() {
		$('#birthday').datepicker('show');
	});
	
	var isReturenDashboard = ${isDashboard};
	if (!isReturenDashboard) {
		buildBreadcumb("个人资料修改");
	}
	
	validation();
	initPhotoUpload();
	addButtonListener();
	initData();
}

function initData(){
	if($('#user_avatar').is('.custom-image')) {
		$('#user_avatar').data('encryptFileName', '${ account.avatar }');
	}
}

function addButtonListener(){
	$("#add_btn").off("click");
	$("#add_btn").on("click", function(){
		var isReturenDashboard = ${isDashboard};
		
		$("#coach_form").data('bootstrapValidator').validate();
		if($("#coach_form").data('bootstrapValidator').isValid()){
			$("#encryptFileName").val($("#user_avatar").data("encryptFileName"));
			sa.ajax({
				type : "post",
				url : "<%=serverUrl%>user/saveAccount",
				data : $("#coach_form").serialize(),
				success : function(data) {
					if (data.status){
						changeAvatar(data.result.avatar);
						changeUserName(data.result.name);
						
						if (!isReturenDashboard) {
							alert("个人资料保存成功");
							loadPage("<%=serverUrl%>user/account_manage");
						} else {
							loadPage("<%=serverUrl%>dashboard");
						}
					} else {
						alert(data.errorMessage);
					}
				},
				error: function(data) {
					alert(data.responseText);
				}
			}); 
		}
	});
	
	$("#cancel_btn").off("click");
	$("#cancel_btn").on("click", function(){
		var urlPatch = "<%=serverUrl%>dashboard";
		var isReturenDashboard = ${isDashboard};
		if (!isReturenDashboard) {
			urlPatch = "<%=serverUrl%>user/account_manage";
		}
		sa.ajax({
			type : "get",
			url : urlPatch,
			success : function(data) {
				AngularHelper.Compile($('#content'), data);
			},
			error: function() {
				alert("返回首页失败");
			}
		});
	});
}

function changeAvatar (fileUrl) {
	$('[accountImage="true"]').each(function() {
		var pElem = $(this);
		if (fileUrl) {
			pElem.attr("src", "<%=serverUrl%>file/downloadFile?fileName=" + fileUrl);
		}
		
	});
}

function changeUserName (userName) {
	$('[accountName="true"]').each(function() {
		var pElem = $(this);
		if (userName) {
			var name = userName.replace(/</g,"&lt;");
			pElem.html(name);
		}
		
	});
}


function initPhotoUpload(){
	//avatar upload
	var options = {
		   "baseUrl": "<%=serverUrl%>",
	       "url" : "file/upload",
	       "flashUploadURL" : "file/flashUploadFile",
	       "downloadURL": "file/downloadFile?fileName=",
	       "flashURL" : "resources/js/takephoto.swf",
	       "defaultUserPhotoURL" : "resources/images/user_avatar.png",
	       "dlgTitle" : "上传头像",
	       "lnkUploadFileSelector" : "#user_avatar",
	       "userPhotoSelector" : "#user_avatar"
	};
	ImageUploader.init(options);
}

function validation(){
	$("#coach_form").bootstrapValidator({
        message: '您输入的值不合法。',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	name: {
                 message: '姓名不合法',
                 validators: {
                     notEmpty: {
                         message: '姓名是必填项，请正确填写姓名。'
                     },
                     stringLength: {
                         max: 30,
                         message: '姓名长度超过限制，请输入30字符以内的姓名'
                     }
                 }
             },
            username:{
            	 message: '用户名不合法',
            	 validators: {
                     notEmpty: {
                         message: '用户名是必填项，请正确填写用户名。'
                     },
                     stringLength: {
                    	 min:6,
                         max: 30,
                         message: '请输入6-18字符以内的用户名。'
                     },
                     regexp: {
                         regexp: /^[a-zA-Z0-9_]+$/,
                         message: '用户名只允许由字母，数字，下划线构成。'
                     }
                 }
            },
           /*  birthday:{
           	 message: '生日不合法',
           	 validators: {
                    notEmpty: {
                        message: '生日是必填项，请选择生日。'
                    }
                }
           }, */
            email:{
	           	 message: '邮箱不合法',
	           	 validators: {
	                    notEmpty: {
	                        message: '邮箱是必填项，请正确填写邮箱信息。'
	                    },
	                    emailAddress: {
	                        message: '请正确填写您的邮箱'
	                    }
	                }
           },
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
                       message: '用户名和密码不能相同'
                   }
               }
           },
           repassword: {
               validators: {
            	   /* notEmpty: {
                   message: '密码不能为空'
               		}, */
                   identical: {
                       field: 'password',
                       message: '两次密码不一致'
                   }
               }
           }
        }
    });
}


</script>