<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page import="cn.sportsdata.webapp.youth.common.utils.CommonUtils" %>
<%@ page import="cn.sportsdata.webapp.youth.common.vo.UserVO" %>

<%
	String serverUrl = CommonUtils.getServerUrl(request);

	boolean isCreate = (Boolean) request.getAttribute("isCreate");
	UserVO vo = (UserVO) request.getAttribute("coach");
	String userId = isCreate ? "" : vo.getId();
%>

<div class="profileEditContainer">
	<div class="coach_edit_button_area">
		<button id="save_btn" class="btn btn-primary" style="float: right; margin-left: 10px;">保存</button>
		<button id="cancle_btn" class="btn btn-default" style="float: right;">取消</button>
	</div>
	<div class="clearfix"></div>
	<div class="profileEditContent">
		<form id="player_form">
				<div role="tabpanel" class="tab-pane active" id="basic_tab">
					<sa-panel title="基本信息">
						<div class="row">
							<div class="col-md-2 text-center">
									<c:choose>
										<c:when test="${ !empty coach.avatar }">
											<img id="user_avatar" class="profileEditAvatar custom-image" src="<%=serverUrl%>file/downloadFile?fileName=${ coach.avatar }"></img>
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
									<div class="col-md-1 inputLabel">姓名</div>
									<div class="col-md-3">
										<%-- <c:choose>
											<c:when test="${isCreate}">
											<div class="form-group">
												<input type="text" class="profileEditInput form-control" data-bv-field="name"  id="name" name="name" />
											</div>
											</c:when>
											<c:otherwise>
											<div class="form-group">
												<input type="text" class="profileEditInput form-control" id="name" data-bv-field="name"  name="name" value="${ coach.name }" />
											</div>
											</c:otherwise>
										</c:choose> --%>
										
										<select class="profileEditInput" id="selDoctor" name="doctorCode" style="width: 100%;">
											<c:forEach items="${doctors}" var="doctor">
												
												<option value="${doctor.userId}" <c:if test="${coach.doctorCode eq doctor.userId}">selected</c:if>>${doctor.name}</option>
											</c:forEach>
										</select>
										
									</div>
									<div class="col-md-1 inputLabel">职务</div>
									<div class="col-md-3">
										<select class="profileEditInput" id="coach_type" name="role" style="width: 100%;">
											<option value="director">主任</option>
											<option value="doctor">医生</option>
											<option value="nurse">护士</option>
										</select>
									</div>
									<div class="col-md-1 inputLabel">生日</div>
									<div class="col-md-3">
										<c:choose>
											<c:when test="${isCreate}">
												<div class="input-group date">
													<input type="text" class="form-control profileEditInput calendar-input" id="birthday" name="birthday" readonly>
													<span id="birthdayIcon" class="input-group-addon calendar-icon"><i class="glyphicon glyphicon-calendar major_color"></i></span>
												</div>
											</c:when>
											<c:otherwise>
												<div class="input-group date">
													<input type="text" class="form-control profileEditInput calendar-input" id="birthday" name="birthday" value="<fmt:formatDate value="${ coach.birthday }" pattern="yyyy-MM-dd" />" readonly>
													<span id="birthdayIcon" class="input-group-addon calendar-icon"><i class="glyphicon glyphicon-calendar major_color"></i></span>
												</div>
											</c:otherwise>
										</c:choose>
									</div>
								</div>
								
								<div class="row profileEditItemLine">
									<div class="col-md-1 inputLabel">用户名</div>
									<div class="col-md-3">
										<c:choose>
											<c:when test="${isCreate}">
											<div class="form-group">
												<input type="text" class="profileEditInput form-control" id="userName" data-bv-field="userName" name="userName" />
											</div>
											</c:when>
											<c:otherwise>
											<div class="form-group">
												<input type="text" class="profileEditInput form-control" id="userName" data-bv-field="userName"  name="userName" value="${ coach.userName }" />
											</div>
											</c:otherwise>
										</c:choose>
									</div>
									<div class="col-md-1 inputLabel">密码</div>
									<div class="col-md-3">
										<c:choose>
											<c:when test="${isCreate}">
											<div class="form-group">
												<input type="password" class="profileEditInput form-control" id="password" data-bv-field="password" name="password" />
											</div>
											</c:when>
											<c:otherwise>
											<div class="form-group">
												<input type="password" class="profileEditInput form-control" id="password" data-bv-field="password" name="password" value="${ coach.password }" />
											</div>
											</c:otherwise>
										</c:choose>
									</div>
									<div class="col-md-1 inputLabel"></div>
									<div class="col-md-3">
										<%-- <select class="profileEditInput" id="selDepartment" name="departmentCode" style="width: 100%;">
											<c:forEach items="${departments}" var="department">
												<option value="${department.departmentCode}">${department.name}</option>
											</c:forEach>
										</select> --%>
									</div>
								</div>
								
								<div class="row profileEditItemLine">
									<div class="col-md-1 inputLabel" style="padding-right: 0px;">身份证号</div>
									<div class="col-md-3">
										<c:choose>
											<c:when test="${isCreate}">
											<div class="form-group">
												<input type="text" class="profileEditInput form-control" id="idCard" data-bv-field="idCard" name="idCard" />
											</div>
											</c:when>
											<c:otherwise>
											<div class="form-group">
												<input type="text" class="profileEditInput form-control" id="idCard" data-bv-field="idCard" name="idCard" value="${ coach.idCard }" />
											</div>
											</c:otherwise>
										</c:choose>
									</div>
									<div class="col-md-1 inputLabel">电话</div>
									<div class="col-md-3">
										<c:choose>
											<c:when test="${isCreate}">
											<div class="form-group">
												<input type="text" class="profileEditInput form-control" id="phone" data-bv-field="phone" name="phone" />
											</div>
											</c:when>
											<c:otherwise>
											<div class="form-group">
												<input type="text" class="profileEditInput form-control" id="phone" data-bv-field="phone" name="phone" value="${ coach.phone }" />
											</div>
											</c:otherwise>
										</c:choose>
									</div>
									<div class="col-md-1 inputLabel">邮箱</div>
									<div class="col-md-3">
										<c:choose>
											<c:when test="${isCreate}">
											<div class="form-group">
												<input type="text" class="profileEditInput form-control" data-bv-field="email" id="email" name="email" />
											</div>
											</c:when>
											<c:otherwise>
											<div class="form-group">
												<input type="text" class="profileEditInput form-control" data-bv-field="email" id="email" name="email" value="${ coach.email }" />
											</div>
											</c:otherwise>
										</c:choose>
									</div>
								</div>
								<div class="row profileEditItemLine">
									<div class="col-md-1 inputLabel">住址</div>
									<div class="col-md-7">
										<c:choose>
											<c:when test="${isCreate}">
											<div class="form-group">
												<input type="text" class="profileEditInput form-control" id="address" data-bv-field="address" name="address" />
											</div>
											</c:when>
											<c:otherwise>
											<div class="form-group">
												<input type="text" class="profileEditInput form-control" id="address" name="address" data-bv-field="address" value="${ coach.address }" />
											</div>
											</c:otherwise>
										</c:choose>
									</div>
								</div>
							</div>
						</div>
					</sa-panel>
				</div>
		</form>
	</div>
</div>

<script type="text/javascript">
	$(function() {
		setTimeout(function() {
			initData();
			initEvent();
		}, 50);  // if using angular widget like sa-panel, since the real dom loaded is after the Angular.compile method, which is behind the document ready event, so add a little timeout to hack this
	});
	
	function initData() {
		buildBreadcumb("新增/修改教练");
		$('.nav-pills a:first').focus();  // fix issues of first tab is not focused after loading
		
		var isCreate = ${ isCreate };
		if(!isCreate) {
			var overallRatingValue = '${ coach.userExtInfoMap["coach_type"] }';
			$("#coach_type option[value='" + overallRatingValue + "']").attr('selected', 'selected');
		}
		
		if($('#user_avatar').is('.custom-image')) {
			$('#user_avatar').data('encryptFileName', '${ coach.avatar }');
		}
		
		
		var select_field_ids = ['coach_type'];
		
		$.each(select_field_ids, function(index, value) {
			var $select = $('#' + select_field_ids[index]);
			
			$select.select2({
				minimumResultsForSearch : Infinity
			});
		});
		
		$("#selDoctor").select2({
			
		});
	}
	
	function initEvent() {
		var isCreate = ${ isCreate };
		var $birthday = $('#birthday');
		
		$birthday.datepicker({
			format : 'yyyy-mm-dd',
			language : 'zh-CN',
			autoclose : true,
			todayHighlight : true,
			toggleActive : true,
			startView:2,
			zIndexOffset:1031
		});
		
		$('#birthdayIcon').click(function() {
			$birthday.datepicker('show');
		});
		
		var backURL =  "<%=serverUrl%>user/showCoachList";
		if(!isCreate) {
			backURL = "<%=serverUrl%>user/showCoachDetail?userID=" + "<%=userId%>";
		}
		$('#cancle_btn').click(function() {
			sa.ajax({
				type : "get",
				url : backURL,
				success : function(data) {
					AngularHelper.Compile($('#content'), data);
				},
				error: function() {
					alert("打开教练列表页面失败");
				}
			});
		});
		
		
		
		
		$('#save_btn').click(function() {
			$("#player_form").data('bootstrapValidator').validate();
			
			if(!$("#player_form").data('bootstrapValidator').isValid()){
				return;
			}
			var form_data = $('#player_form').serializeArray();
			var converted_ext_data = [];
			var converted_base_data = {'id': '<%=userId%>', 'avatar': $('#user_avatar').data('encryptFileName') || ''};
			var need_convert_multi_fields = [];
			var basic_user_fields = ['doctorCode', 'birthday', 'userName', 'password', 'birthPlace', 'idCard', 'email', 'phone', 'address', 'role']; 
			var date_fields = ['birthday'];
			
			$.each(form_data, function(index, item) {
				var isConvert = $.inArray(item['name'], need_convert_multi_fields) > -1;
				
				if($.inArray(item['name'], date_fields) > -1) {
					item['value'] += ' 00:00:00'; // String -> java.sql.Timestamp MUST be 'yyyy-MM-dd hh:mm:ss' format
				}
				
				if($.inArray(item['name'], basic_user_fields) > -1) {
					converted_base_data[item['name']] = isConvert ? item['value'].replace(/\r/ig, "") : item['value'];
				} else {
					converted_ext_data.push({'itemName': item['name'], 'itemValue': isConvert ? item['value'].replace(/\r/ig, "") : item['value']});
				}
			});
			
			var submitted_data = {'userItemList': converted_ext_data, 'basicData': converted_base_data};
			
			sa.ajax({
				type : "post",
				url : "<%=serverUrl%>user/saveUser",
				data: JSON.stringify(submitted_data),
				contentType: "application/json",
				success : function(data) {
					if(!data.status) {
						alert("提交教练信息异常");
						return;
					}
					
					sa.ajax({
						type : "get",
						url : "<%=serverUrl%>user/showCoachList",
						success : function(data) {
							AngularHelper.Compile($('#content'), data);
						},
						error: function() {
							alert("打开球员列表页面失败");
						}
					});
				},
				error: function() {
					alert("提交球员信息失败");
				}
			});
		});
		
		// avatar upload
		var options = {
			   "baseUrl": "<%=serverUrl%>",
               "defaultUserPhotoURL" : "resources/images/user_avatar.png",
               "dlgTitle" : "上传头像",
               "lnkUploadFileSelector" : "#user_avatar",
               "userPhotoSelector" : "#user_avatar"
		};
		
		ImageUploader.init(options);
		validation();
	}
	
	function validation(){
		$("#player_form").bootstrapValidator({
	        message: '您输入的值不合法。',
	        feedbackIcons: {
	            valid: 'glyphicon glyphicon-ok',
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	        	name:{
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
	        	 userName:{
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
	            password: {
	                validators: {
	                    notEmpty: {
	                        message: '密码不能为空'
	                    },
	                    stringLength: {
	                   	   min:6,
	                        max: 180,
	                        message: '请输入6-180字符以内的密码。'
	                    },
	                  /*   identical: {
	                        field: 'repassword',
	                        message: '两次密码不一致'
	                    }, */
	                    different: {
	                        field: 'username',
	                        message: '账号和密码不能相同'
	                    }
	                }
	            },
	        	idCard:{
	        		message: '身份证号不合法',
	        		 validators: {
			           		stringLength: {
			           			max: 500,
			                    message: '身份证号不合法'
			                }
			            }
	        	},
	        	phone:{
	        		message: '电话号码不合法',
		           	validators: {
			           	 regexp: {
	                         regexp: /(^1\d{10}$|^(0\d{2,3}-?|\(0\d{2,3}\))?[1-9]\d{4,7}(-\d{1,8})?$|^1\d{10}$)/,
	                         message: '电话号码不合法。'
	                     }
		            }
	        	},
	        	address:{
	        		message: '家庭住址不合法',
		           	 validators: {
		           		stringLength: {
		           			max: 500,
		                    message: '家庭住址不合法。'
		                }
		            }
	        	},
	            email:{
		           	 message: '邮箱不合法',
		           	 validators: {
		                    
		                    emailAddress: {
		                        message: '请正确填写您的邮箱。'
		                    },
				        	notEmpty: {
			                    message: '邮箱是必填项，请正确填写邮箱信息。'
			                }
		             }
	           }
	         
	        }
	    });
	}
</script>