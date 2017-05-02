<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page import="cn.sportsdata.webapp.youth.common.utils.CommonUtils" %>
<%@ page import="cn.sportsdata.webapp.youth.common.vo.UserVO" %>

<%
	String serverUrl = CommonUtils.getServerUrl(request);

	
%>

<div class="profileEditContainer">
	<div class="coach_edit_button_area">
		<button id="edit_btn" class="btn btn-primary" style="float: right; margin-left: 10px;">编辑</button>
<!-- 		<button id="export_btn" class="btn btn-primary" style="float: right; margin-left: 10px;">导出病历</button> -->
		<button id="cancle_btn" class="btn btn-default" style="float: right;">返回</button>
	</div>
	<div class="clearfix"></div>
	<div class="profileEditContent">
		<form id="player_form">
				<div role="tabpanel" class="tab-pane active" id="basic_tab">
					<sa-panel title="病人信息">
						<div class="row">
							<div class="col-md-1 profileDetailItemTitle">姓名</div>
							<div class="col-md-3 profileDetailItemContent">${record.realName}</div>
							<div class="col-md-1 profileDetailItemTitle">医生</div>
							<div class="col-md-3 profileDetailItemContent">${record.doctorInCharge}</div>
							<div class="col-md-1 profileDetailItemTitle">入院时间</div>
							<div class="col-md-3 profileDetailItemContent"><fmt:formatDate pattern="yyyy-MM-dd" 
            value="${record.admissionDateTime}" /></div>
						</div>
						<div class="row">
							<div class="col-md-1 profileDetailItemTitle">病房号</div>
							<div class="col-md-3 profileDetailItemContent">${record.wardCode}</div>
							<div class="col-md-1 profileDetailItemTitle">床位号</div>
							<div class="col-md-3 profileDetailItemContent">${record.bedNo}</div>
							<div class="col-md-1 profileDetailItemTitle">护理等级</div>
							<div class="col-md-3 profileDetailItemContent">${record.nursingClass}</div>
						</div>
						
					</sa-panel>
					<sa-panel title="主诉">
							<pre style="background:white;border-width:0px;">${record.opPrimary}</pre>
						</div>
					</sa-panel>
					<sa-panel title="诊断">
						<pre style="background:white;border-width:0px">${record.diagnosis}</pre>
					</sa-panel>
					<sa-panel title="专科检查">
						<pre style="background:white;border-width:0px">${record.bodyExam}</pre>
					</sa-panel>
					<sa-panel title="病史">
						<pre style="background:white;border-width:0px">${record.illHistory}</pre>
					</sa-panel>
					<sa-panel title="病历讨论">
						<pre style="background:white;border-width:0px">${record.recordDiscussion}</pre>
					</sa-panel>
				</div>
		</form>
	</div>
</div>


<form id="condition_form">
	<div class="row">
		<input type="hidden" class="profileEditInput form-control" id="patName" name="patName" value="${condition.patName }" />
		<input type="hidden" class="form-control profileEditInput calendar-input" id="careTimeStart" name="careTimeStart" value="${condition.careTimeStart }">
	</div>
</form>
<style>
pre, code {
    white-space: pre-line;
}
</style>
<script type="text/javascript">
	$(function() {
		setTimeout(function() {
			initData();
			initEvent();
		}, 50);  // if using angular widget like sa-panel, since the real dom loaded is after the Angular.compile method, which is behind the document ready event, so add a little timeout to hack this
	});
	
	function initData() {
		buildBreadcumb("住院记录");
		$('.nav-pills a:first').focus();  // fix issues of first tab is not focused after loading
	}
	
	function initEvent() {
		var registId = '${registId}';
		$('#cancle_btn').click(function(){
			if(registId){
				$('#content').loadAngular("<%=serverUrl%>register/register_detail?id="+registId );
			} else {
				if('${condition}'){
					$('#content').loadAngular("<%=serverUrl%>care/in_hospital_list?" + $("#condition_form").serialize() );
				}
			}
		});
		$("#export_btn").click(function(){
			window.open("<%=serverUrl%>/care/download_medical_record?id=${id}");
		});
		$('#edit_btn').click(function() {
			sa.ajax({
				type : "get",
				url : "<%=serverUrl%>care/in_hospital_record_edit?id=${id}",
				data :$("#condition_form").serialize(),
				success : function(data) {
					//TODO: will update the container later
					AngularHelper.Compile($('#content'), data);
				},
				error: function() {
					alert("打开编辑页面失败");
				}
			});
		});
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