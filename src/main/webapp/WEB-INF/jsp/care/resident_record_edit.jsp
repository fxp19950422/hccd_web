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
					<div class="col-md-3 profileDetailItemContent">${record.name}</div>
					<div class="col-md-1 profileDetailItemTitle">诊断医生</div>
					<div class="col-md-3 profileDetailItemContent">${record.doctorName}</div>
					<div class="col-md-1 profileDetailItemTitle">入院时间</div>
					<div class="col-md-3 profileDetailItemContent">${record.admissionDate}</div>
				</div>
				</sa-panel>
				<sa-panel title="入院情况"> <textarea id="future_plan"
					name="inState" class="form-control" rows="5"
					placeholder="不超过800字" value="${record.inState}">${record.inState}</textarea>
				</sa-panel>
				<sa-panel title="入院中医诊断"> <textarea id="future_plan"
					name="inChiDiagnosis" class="form-control" rows="5"
					placeholder="不超过800字" value="${record.inChiDiagnosis}">${record.inChiDiagnosis}</textarea>
				</sa-panel>
				<sa-panel title="入院西医诊断"> <textarea id="future_plan"
					name="inWesDiagnosis" class="form-control" rows="5" placeholder="不超过800字"
					value="${record.inWesDiagnosis}">${record.inWesDiagnosis}</textarea> </sa-panel>
				<sa-panel title="诊疗经过"> <textarea id="future_plan"
					name="process" class="form-control" rows="5" placeholder="不超过800字"
					value="${record.process}">${record.process}</textarea> </sa-panel>
				<sa-panel title="出院中医诊断"> <textarea id="future_plan"
					name="outChiDiagnosis" class="form-control" rows="5"
					placeholder="不超过800字" value="${record.outChiDiagnosis}">${record.outChiDiagnosis}</textarea>
				</sa-panel>
				<sa-panel title="出院西医诊断"> <textarea id="future_plan"
					name="outWesDiagnosis" class="form-control" rows="5"
					placeholder="不超过800字" value="${record.outWesDiagnosis}">${record.outWesDiagnosis}</textarea>
				</sa-panel>
				<sa-panel title="出院情况"> <textarea id="future_plan"
					name="outState" class="form-control" rows="5"
					placeholder="不超过800字" value="${record.outState}">${record.outState}</textarea>
				</sa-panel>
				<sa-panel title="出院医嘱"> <textarea id="future_plan"
					name="suggestion" class="form-control" rows="5"
					placeholder="不超过800字" value="${record.suggestion}">${record.suggestion}</textarea>
				</sa-panel>
				<input type="hidden" name="id" value="${id}" />
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
		
		
	}
	
	function initEvent() {
		
		$('#cancle_btn').click(function() {
			$('#content').loadAngular("<%=serverUrl%>care/resident_detail?id=${id}" );
		});
		$('#save_btn').click(function() {
			
			sa.ajax({
				type : "post",
				url : "<%=serverUrl%>care/save_resident_record",
				data : $("#player_form").serialize(),
				success : function(data) {
					alert("修改成功");
					$('#content').loadAngular("<%=serverUrl%>care/care_detail?id=${id}");
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