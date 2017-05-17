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
					<div class="col-md-1 profileDetailItemTitle">诊断医生</div>
					<div class="col-md-3 profileDetailItemContent">${record.doctor}</div>
					<div class="col-md-1 profileDetailItemTitle">诊断时间</div>
					<div class="col-md-3 profileDetailItemContent">
						<fmt:formatDate pattern="yyyy-MM-dd" value="${record.visitDate}" />
					</div>
				</div>
				</sa-panel>
				<div class="row">
					<div class="col-md-4">
						<sa-panel title="主诉" linkText="模板"
							link-callback="getTemplate('illnessDesc')"> <textarea
							id="illnessDesc" name="illnessDesc" class="form-control" rows="5"
							placeholder="不超过30字" value=""></textarea> </sa-panel>
					</div>
					<div class="col-md-4">
						<sa-panel title="病史" linkText="模板"
							link-callback="getTemplate('medHistory')"> <textarea
							id="medHistory" name="medHistory" class="form-control" rows="5"
							placeholder="不超过800字" value=""></textarea> </sa-panel>
					</div>
					<div class="col-md-4">
						<sa-panel title="专科查体" linkText="模板"
							link-callback="getTemplate('bodyExam')"> <textarea
							id="bodyExam" name="bodyExam" class="form-control" rows="5"
							placeholder="不超过800字" value=""></textarea> </sa-panel>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<sa-panel title="辅助检查" linkText="模板"
							link-callback="getTemplate('accExam')"> <textarea
							id="accExam" name="accExam" class="form-control" rows="5"
							placeholder="不超过800字" value=""></textarea> </sa-panel>
						</div>
					<div class="col-md-4">
						<sa-panel title="诊断" linkText="模板"
							link-callback="getTemplate('diagDesc')"> <textarea
							id="diagDesc" name="diagDesc" class="form-control" rows="5"
							placeholder="不超过800字" value=""></textarea> </sa-panel>
					</div>
					<div class="col-md-4">
						<sa-panel title="建议" linkText="模板"
							link-callback="getTemplate('suggestion')"> <textarea
							id="suggestion" name="suggestion" class="form-control" rows="5"
							placeholder="不超过800字" value=""></textarea> </sa-panel>
					</div>
				</div>


			</div>
			<input type="hidden" name="id" value="${record.id}" /> <input
				type="hidden" name="patientId" value="${record.patientId}" /> <input
				type="hidden" name="visitDate" value="${record.visitDate}" /> <input
				type="hidden" name="visitNo" value="${record.visitNo}" /> <input
				type="hidden" name="doctor" value="${record.doctor}" /> <input
				type="hidden" name="doctorNo" value="${record.doctorNo}" /> <input
				type="hidden" name="hospitalId" value="${record.hospitalId}" />
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
		buildBreadcumb("新增门诊记录");
		$('.nav-pills a:first').focus();  
		
		
	}
	
	function initEvent() {
		var registerId='${registId}'
							;
		$('#cancle_btn').click(function() {
			$('#content').loadAngular("<%=serverUrl%>register/register_detail?id="+registerId );
		});
		$('#save_btn').click(function() {
			
			sa.ajax({
				type : "post",
				url : "<%=serverUrl%> care/add_record",
				data
							: $("#player_form").serialize(),
				success :
							function(data) {
					alert("新增成功");
					$('#content').loadAngular("<%=serverUrl%>care/care_detail?id=${record.id}&registId="
											+ registerId);
						},
						error : function() {
							alert("新增失败");
						}
					});
				});
		validation();
	}

	function getTemplate(obj) {
		if ('illnessDesc' == obj) {
			$("#illnessDesc").val("左手外伤术后1个月");
			$("#illnessDesc").text("左手外伤术后1个月");
		} else if ('medHistory' == obj) {

		} else if ('bodyExam' == obj) {
			$("#bodyExam").val(
					"左手腕背部伤口愈合良好，干燥，无肿胀、渗出。左手石膏固定在位，内固定针在位有效。创口远局部皮肤浅感觉减退。");
			$("#bodyExam").text(
					"左手腕背部伤口愈合良好，干燥，无肿胀、渗出。左手石膏固定在位，内固定针在位有效。创口远局部皮肤浅感觉减退。");
		} else if ('diagDesc' == obj) {
			$("#diagDesc").val("左手外伤术后。");
			$("#diagDesc").text("左手外伤术后。");
		} else if ('suggestion' == obj) {
			$("#suggestion")
					.val(
							"1.摄左手正斜位片，拆除石膏外固定；\n2.患肢避免过度用力、外伤，防止内固定断裂；\n3.患肢适度功能锻炼；\n4.如有不适，我科随诊。");
			$("#suggestion")
					.text(
							"1.摄左手正斜位片，拆除石膏外固定；\n2.患肢避免过度用力、外伤，防止内固定断裂；\n3.患肢适度功能锻炼；\n4.如有不适，我科随诊。");
		}
	}

	function validation() {
		$("#player_form").bootstrapValidator({
			message : '您输入的值不合法。',
			feedbackIcons : {
				valid : 'glyphicon glyphicon-ok',
				invalid : 'glyphicon glyphicon-remove',
				validating : 'glyphiconglyphicon-refresh'
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