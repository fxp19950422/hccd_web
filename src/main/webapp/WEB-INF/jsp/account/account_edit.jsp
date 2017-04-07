<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="xss" uri="http://www.sportsdata.cn/xss"%>

<%@ page import="cn.sportsdata.webapp.youth.common.utils.CommonUtils" %>
<%@ page import="cn.sportsdata.webapp.youth.common.vo.UserVO" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%
	String serverUrl = CommonUtils.getServerUrl(request);
	String coachId = (String) request.getAttribute("coachId");
%>

<div>
<div class="button_area">
	<button id="add_btn" class="btn btn-primary" style="float: right;margin-left: 10px;">保存</button>
	
	<c:if test="${ account != null }">
		<button id="delete_btn" class="btn btn-danger" style="float: right; margin-left: 10px;">删除账户</button>
	</c:if>
	
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
								<span id="uploadTip" style="position: absolute; bottom: 0; left: 27px; color:#999999;font-size: 12px;">上传头像</span> 
							</div>
						</c:otherwise>
					</c:choose>
				</div>
				<div class="col-md-10">
					<div class="row">
						<div class="col-md-6">
							<div class="col-md-3 inputLabel">
								<span>姓名</span>
							</div>
							<div class="col-md-9">
								<div class="form-group ">
				            		<input id="accountName" data-bv-field="name" class="form-control" name="name" value="${account.name}" type="text">
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
									<input type="text" class="form-control calendar-input" id="birthday" data-bv-field="birthday" value="${ account.birthday }" name="birthday" readonly>
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
				            		<input id="accountEmail" data-bv-field="email" class="form-control" name="email" value="${account.email}"  type="text">
				        		</div>
			        		</div>
						</div>
					</div>
					
					<c:choose>
						<c:when test="${ account == null }">
							<div class="row">
								<div class="col-md-6">
									<div class="col-md-3 inputLabel">
										<span >密码</span>
									</div>
									<div class="col-md-9">
										<div class="form-group ">
						            		<input data-bv-field="password" class="form-control" name="password" type="password">
						        		</div>
					        		</div>
								</div>
								<div class="col-md-6">
									<div class="col-md-3 inputLabel">
										<span >重复密码</span>
									</div>
									<div class="col-md-9">
										<div class="form-group ">
						            		<input data-bv-field="repassword" class="form-control" name="repassword" type="password">
						        		</div>
						        		
					        		</div>
								</div>
							</div>
							
						</c:when>
						<c:otherwise>
							<div class="row">
								<div class="col-md-6">
									<div class="col-md-3 inputLabel">
										<span ></span>
									</div>
									<div class="col-md-9">
										<div class="form-group ">
						            		<button id="reset_pwd_btn" class="btn btn-primary" style="">重置密码</button>
						        		</div>
					        		</div>
								</div>
								<div class="col-md-6">
									
								</div>
							</div>
							<input type="hidden" name="id" value="${account.id}" />
						</c:otherwise>
					</c:choose>
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
		createFromUser();
		initEvent();
	}, 50);
});

function createFromUser() {
	var coachId = '${coachId}';
	if (coachId.length > 0) {
		var avatar='${avatar}';
		var name='${name}';
		var email='${email}';
		var birthday = '${birthday}';
		
		path = "<%=serverUrl%>file/downloadFile?fileName=" + avatar; 
		if (avatar.length < 1) {
			path = "<%=serverUrl%>resources/images/user_avatar.png";
		} else {
			$("#uploadTip").empty(); 
		}
		
		$("#user_avatar").attr('src',path); 
		$("#encryptFileName").val(avatar);
		$("#birthday").val(birthday);
		$("#accountName").val('<xss:xssFilter text="${name}" filter="js"/>');
		$("#accountEmail").val('<xss:xssFilter text="${email}" filter="js"/>');
	}
}

function initEvent() {
	$("#birthday").datepicker({
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
	buildBreadcumb("新增／修改账号");
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
		$("#coach_form").data('bootstrapValidator').validate();
		if($("#coach_form").data('bootstrapValidator').isValid()){
			<c:if test="${ coachId == null }">
				$("#encryptFileName").val($("#user_avatar").data("encryptFileName"));
			</c:if>
			sa.ajax({
				type : "post",
				url : "<%=serverUrl%>user/saveAccount",
				data : $("#coach_form").serialize(),
				success : function(data) {
					if (data.status){

						var coachId = "${coachId}";
						if (coachId.length > 0) {
							alert("新建账号信息成功。");
							$('#content').loadAngular("<%=serverUrl%>user/showCoachDetail?userID=" + coachId);
						} else {
							var account = "${account}";
							if (account.length > 0) {
								alert("修改账号信息成功。");
							} else {
								alert("新建账号信息成功。");
							}
							$('#content').loadAngular("<%=serverUrl%>user/account_manage");
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
		var coachId = "${coachId}";
		if (coachId.length > 0) {
			$('#content').loadAngular("<%=serverUrl%>user/showCoachDetail?userID=" + "${coachId}");
		} else {
			$('#content').loadAngular("<%=serverUrl%>user/account_manage");
		}
	});
	
	$("#reset_pwd_btn").off("click");
	$("#reset_pwd_btn").on("click", function(){
		sa.ajax({
			type : "post",
			url : "<%=serverUrl%>user/resetPwd",
			data : $("#coach_form").serialize(),
			success : function(data) {
				if (data.status){
					alert("已经将密码重置为:" + data.result);
				} else {
					alert(data.errorMessage);
				}
			},
			error: function(data) {
				alert(data.responseText);
			}
		}); 
	});
	
	$("#delete_btn").off("click");
	$("#delete_btn").on("click", function(){
		bootbox.dialog({
			message: "您是否要删除该账号？",
			title: "确认删除",
			buttons: {
				unchange: {
				      label: "取消",
				      className: "btn-default",
				      callback: function() {}
				    },
				danger: {
					label: "删除",
					className: "btn-danger",
					callback: function() {
						sa.ajax({
							type : "post",
							url : "<%=serverUrl%>user/deleteAccount",
							data : $("#coach_form").serialize(),
							success : function(data) {
								if (data.status){
									alert("删除账号成功。");
									$('#content').loadAngular("<%=serverUrl%>user/account_manage");
								} else {
									alert(data.errorMessage);
								}
							},
							error: function(data) {
								alert(data.responseText);
							}
						}); 
					}
				}
		
			}	
		});
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
}


</script>
