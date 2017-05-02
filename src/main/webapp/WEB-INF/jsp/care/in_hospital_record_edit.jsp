<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ page import="cn.sportsdata.webapp.youth.common.utils.CommonUtils"%>
<%@ page import="cn.sportsdata.webapp.youth.common.vo.UserVO"%>

<%
	String serverUrl = CommonUtils.getServerUrl(request);
%>

<div class="profileEditContainer">
	<div class="coach_edit_button_area">
		<button id="save_btn" class="btn btn-primary"
			style="float: right; margin-left: 10px;">保存</button>
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
				<sa-panel title="主诉"> <textarea id="future_plan"
					name="opPrimary" class="form-control" rows="5"
					placeholder="不超过800字" value="${record.opPrimary}">${record.opPrimary}</textarea>
				</sa-panel>
				<sa-panel title="诊断"> <textarea id="future_plan"
					name="diagnosis" class="form-control" rows="5"
					placeholder="不超过800字" value="${record.diagnosis}">${record.diagnosis}</textarea>
				</sa-panel>
				<sa-panel title="专科检查"> <textarea id="future_plan"
					name="bodyExam" class="form-control" rows="5"
					placeholder="不超过800字" value="${record.bodyExam}">${record.bodyExam}</textarea>
				</sa-panel>
				<sa-panel title="病史"> <textarea id="future_plan"
					name="illHistory" class="form-control" rows="5"
					placeholder="不超过800字" value="${record.illHistory}">${record.illHistory}</textarea>
				</sa-panel>
				<sa-panel title="病历讨论"> <textarea id="future_plan"
					name="recordDiscussion" class="form-control" rows="5"
					placeholder="不超过800字" value="${record.recordDiscussion}">${record.recordDiscussion}</textarea>
				</sa-panel>
				<input type="hidden" name="id" value="${id}" />
			</div>
		</form>
	</div>
</div>

<form id="condition_form">
	<div class="row">
		<input  class="profileEditInput form-control" id="patName" name="patName" value="${condition.patName }" />
		<input  class="form-control profileEditInput calendar-input" id="careTimeStart" name="careTimeStart" value="${condition.careTimeStart }">
	</div>
</form>

<script type="text/javascript">
	$(function() {
		setTimeout(function() {
			initData();
			initEvent();
		}, 50);  // if using angular widget like sa-panel, since the real dom loaded is after the Angular.compile method, which is behind the document ready event, so add a little timeout to hack this
	});
	
	function initData() {
		buildBreadcumb("修改住院信息");
		$('.nav-pills a:first').focus();  // fix issues of first tab is not focused after loading
		
		
	}
	
	function initEvent() {
		var registId = '${registId}';
		$('#cancle_btn').click(function() {
			$('#content').loadAngular("<%=serverUrl%>care/in_hospital_record_detail?id=${id}&"+$("#condition_form").serialize() );
		});
		$('#save_btn').click(function() {
			
			sa.ajax({
				type : "post",
				url : "<%=serverUrl%>care/save_in_hospital_record",
				data : $("#player_form").serialize(),
				success : function(data) {
					alert("修改成功");
					$('#content').loadAngular("<%=serverUrl%>care/in_hospital_record_detail?id=${id}&"+$("#condition_form").serialize());
						},
						error : function() {
							alert("修改失败");
						}
					});
				});
		validation();
	}

	function validation() {
		$("#player_form").bootstrapValidator({
			message : '您输入的值不合法。',
			feedbackIcons : {
				valid : 'glyphicon glyphicon-ok',
				invalid : 'glyphicon glyphicon-remove',
				validating : 'glyphicon glyphicon-refresh'
			},
			fields : {
				name : {
					message : '姓名不合法',
					validators : {
						notEmpty : {
							message : '姓名是必填项，请正确填写姓名。'
						},
						stringLength : {
							max : 30,
							message : '姓名长度超过限制，请输入30字符以内的姓名'
						}
					}
				}

			}
		});
	}
</script>