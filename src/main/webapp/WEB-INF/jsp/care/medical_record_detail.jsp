<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page import="cn.sportsdata.webapp.youth.common.utils.CommonUtils" %>
<%@ page import="cn.sportsdata.webapp.youth.common.vo.UserVO" %>
<%@ page import="java.util.Date" %>

<%
	String serverUrl = CommonUtils.getServerUrl(request);

	
%>

<div class="profileEditContainer" >
	<div class="coach_edit_button_area">
		<button id="edit_btn" class="btn btn-primary" style="float: right; margin-left: 10px;">编辑</button>
 		<button id="print_btn" class="btn btn-primary" style="float: right; margin-left: 10px;">打印</button> 
		<button id="export_btn" class="btn btn-primary" style="float: right; margin-left: 10px;">导出病历</button>
		<button id="cancle_btn" class="btn btn-default" style="float: right;">取消</button>
	</div>
	<div class="clearfix"></div>
	<div class="profileEditContent">
		<form id="player_form">
				<div role="tabpanel" class="tab-pane active" id="basic_tab">
					<sa-panel title="病人信息">
						<div class="row">
							<div class="col-md-1 profileDetailItemTitle">姓名</div>
							<div class="col-md-3 profileDetailItemContent">${record.realName}</div>
							<div class="col-md-1 profileDetailItemTitle">诊断医生</div>
							<div class="col-md-3 profileDetailItemContent">${record.name}</div>
							<div class="col-md-1 profileDetailItemTitle">诊断时间</div>
							<div class="col-md-3 profileDetailItemContent"><fmt:formatDate pattern="yyyy-MM-dd"
							value="${record.visitDate}" /></div>
						</div>
					</sa-panel>
					<div class="row">
						<div class="col-md-4">
						<sa-panel title="主诉">
								<pre style="background:white;border-width:0px;">${record.illnessDesc}</pre>
						</sa-panel>
						</div>
						<div class="col-md-4">
						<sa-panel title="病史">
							<pre style="background:white;border-width:0px">${record.medHistory}</pre>
						</sa-panel>
						</div>
						<div class="col-md-4">
						<sa-panel title="专科查体">
							<pre style="background:white;border-width:0px">${record.bodyExam}</pre>
						</sa-panel>
						</div>
					</div>
					<div class="row">
						<div class="col-md-4">
						<sa-panel title="辅助检查">
								<pre style="background:white;border-width:0px;">${record.accExam}</pre>
						</sa-panel>
						</div>
						<div class="col-md-4">
						<sa-panel title="诊断">
							<pre style="background:white;border-width:0px">${record.diagDesc}</pre>
						</sa-panel>
						</div>
						<div class="col-md-4">
						<sa-panel title="建议">
							<pre style="background:white;border-width:0px">${record.suggestion}</pre>
						</sa-panel>
						</div>
					</div>
					
				</div>
		</form>
	</div>
	<div id="form_print"  style="display:none;">
		<div style="text-align:center"><H3>安徽省中西医结合医院</H3></div>		
		<div style="text-align:center"><H3>门诊病历</H3></div>
		<div style="font-size:0.7em;margin-top:10px;">
			<div style="width:3%;background-color:red;float:left">&nbsp</div>
			<table style="width:94%;float:left">
				<tr>
					<td width="33%">姓名: ${record.realName}</td>
					
					<td  width="33%">性别: ${gender}</td>
					
					<td  width="33%">年龄: ${age}岁</td>
				</tr>
				<tr>
					<td width="33%">号别: 创伤外科</td>
					
					<td width="33%">就诊日期: <fmt:formatDate pattern="yyyy-MM-dd"
							value="${record.visitDate}" /></td>
					
					<td width="33%">门诊次数: 1</td>
					
				</tr>
			</table>
			<div style="width:3%;background-color:red;float:left"></div>
			<div style="clear:both;"></div>
			</div>
			<div style="border:0.5px solid #000; width:95%;margin:0 auto;margin-top:5px;"></div>
			<div style="width:95%;margin:0 auto;margin-top:10px;">
				主诉:${record.illnessDesc}
			</div>
			<div style="border:0.5px dashed #000; width:95%;margin:0 auto;margin-top:10px;"></div>
			
			<div style="width:95%;margin:0 auto;margin-top:10px;">
				病史:${record.medHistory}
			</div>
			<div style="margin-top:10px;border:0.5px dashed #000; width:95%;margin:0 auto;margin-top:10px;"></div>
			
			<div style="margin-top:10px;width:95%;margin:0 auto;margin-top:10px;">
				专科查体:${record.bodyExam}
			</div>
			<div style="margin-top:10px;border:0.5px dashed #000; width:95%;margin:0 auto;margin-top:10px;"></div>
			<div style="margin-top:10px;width:95%;margin:0 auto;margin-top:10px;">
				诊断:${record.diagDesc}
			</div>
			<div style="margin-top:10px;border:0.5px dashed #000; width:95%;margin:0 auto;margin-top:10px;"></div>
			<div style="margin-top:10px;width:95%;margin:0 auto;margin-top:10px;">
				辅助检查:${record.accExam}
			</div>
			<div style="margin-top:10px;border:0.5px dashed #000; width:95%;margin:0 auto;margin-top:10px;"></div>
			<div style="margin-top:10px;width:95%;margin:0 auto;margin-top:10px;">
				建议:${record.suggestion}
			</div>
			<div id="bottomDiv" style=" margin:0 auto;width:95%;margin-top:10px">
				<span style="float:left">打印日期:<fmt:formatDate value="<%=new Date()%>" pattern="yyyy-MM-dd "/></span>  
				<span style="float:right">医生：${record.name}</span>
			</div>
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
		buildBreadcumb("新增/修改门诊信息");
		$('.nav-pills a:first').focus();  // fix issues of first tab is not focused after loading
		/* $("#bottomDiv").css({"margin-top":$(document).height() - $("#form_print").height() - 50}) */
		
		/* $("#form_print").height();
		alert($(document).height()); */
	}
	
	function initEvent() {
		$('#cancle_btn').click(function(){
			if('${condition}'){
				$('#content').loadAngular("<%=serverUrl%>care/care_list?registId=${registId}&"+$("#condition_form").serialize() );
			} else {
				$('#content').loadAngular("<%=serverUrl%>care/care_list?registId=${registId}" );
			}
			
		});
		$("#export_btn").click(function(){
			window.open("<%=serverUrl%>/care/download_medical_record?id=${id}");
		});
		$('#print_btn')
		.click(
				function() {
					$('#form_print')
							.printArea(
									{
										'mode' : 'popup',
										'popTitle': '医生：${record.name}',
										'popClose' : false,
										'retainAttr' : [ 'class', 'id' ],
										'extraHead' : '<meta charset="utf-8" />,<meta http-equiv="X-UA-Compatible" content="IE=edge"/>'
									});
				});
		$('#edit_btn').click(function() {
			sa.ajax({
				type : "get",
				url : "<%=serverUrl%>care/care_edit?id=${id}&registId=${registId}",
				data:$("#condition_form").serialize(),
				success : function(data) {
					//TODO: will update the container later
					AngularHelper.Compile($('#content'), data);
				},
				error: function() {
					alert("打开编辑页面失败");
				}
			});
		});
		$('#new_btn').click(function() {
			sa.ajax({
				type : "get",
				url : "<%=serverUrl%>care/care_insert",
				data:$("#condition_form").serialize(),
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