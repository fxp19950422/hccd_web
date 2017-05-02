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
					<div class="col-md-3 profileDetailItemContent">${record.patientName}</div>
					<div class="col-md-1 profileDetailItemTitle">医生</div>
					<div class="col-md-3 profileDetailItemContent">${record.operator}</div>
					<div class="col-md-1 profileDetailItemTitle">手术时间</div>
					<div class="col-md-3 profileDetailItemContent"><fmt:formatDate pattern="yyyy-MM-dd HH:mm" 
            value="${record.operatingDate}" /></div>
				</div>
				</sa-panel>
				<sa-panel title="主诉"> <textarea id="future_plan"
					name="opPrimary" class="form-control" rows="5"
					placeholder="不超过800字" value="${record.opPrimary}">${record.opPrimary}</textarea>
				</sa-panel>
				<sa-panel title="术前诊断"> <textarea id="future_plan"
					name="beforeDiagnosis" class="form-control" rows="5"
					placeholder="不超过800字" value="${record.beforeDiagnosis}">${record.beforeDiagnosis}</textarea>
				</sa-panel>
				<sa-panel title="术后诊断"> <textarea id="future_plan"
					name="afterDiagnosis" class="form-control" rows="5" placeholder="不超过800字"
					value="${record.afterDiagnosis}">${record.afterDiagnosis}</textarea> </sa-panel>
				<sa-panel title="手术名称"> <textarea id="future_plan"
					name="operationDesc" class="form-control" rows="5" placeholder="不超过800字"
					value="${record.operationDesc}">${record.operationDesc}</textarea> </sa-panel>
				<sa-panel title="手术经过"> <textarea id="future_plan"
					name="process" class="form-control" rows="5"
					placeholder="不超过800字" value="${record.process}">${record.process}</textarea>
				</sa-panel>
				<sa-panel title="手术体位"> <textarea id="future_plan"
					name="posture" class="form-control" rows="5"
					placeholder="不超过800字" value="${record.posture}">${record.posture}</textarea>
				</sa-panel>
				<sa-panel title="手术切口"> <textarea id="future_plan"
					name="incision" class="form-control" rows="5"
					placeholder="不超过800字" value="${record.incision}">${record.incision}</textarea>
				</sa-panel>
				<sa-panel title="探查所见"> <textarea id="future_plan"
					name="exploratory" class="form-control" rows="5"
					placeholder="不超过800字" value="${record.exploratory}">${record.exploratory}</textarea>
				</sa-panel>
				<sa-panel title="手术步骤"> <textarea id="future_plan"
					name="steps" class="form-control" rows="5"
					placeholder="不超过800字" value="${record.steps}">${record.steps}</textarea>
				</sa-panel>
				<sa-panel title="麻醉手段"> <textarea id="future_plan"
					name="anaesthesiaMethod" class="form-control" rows="5"
					placeholder="不超过800字" value="${record.anaesthesiaMethod}">${record.anaesthesiaMethod}</textarea>
				</sa-panel>
				<sa-panel title="引流物"> <textarea id="future_plan"
					name="drainage" class="form-control" rows="5"
					placeholder="不超过800字" value="${record.drainage}">${record.drainage}</textarea>
				</sa-panel>
				<sa-panel title="术毕病人情况"> <textarea id="future_plan"
					name="finishedCondition" class="form-control" rows="5"
					placeholder="不超过800字" value="${record.finishedCondition}">${record.finishedCondition}</textarea>
				</sa-panel>
				<input type="hidden" name="id" value="${id}" />
			</div>
		</form>
	</div>
</div>

<form id="condition_form">
	<div class="row">
		<input type="hidden" class="profileEditInput form-control" id="patName" name="patName" value="${condition.patName }" />
		<input type="hidden"  class="form-control profileEditInput calendar-input" id="careTimeStart" name="careTimeStart" value="${condition.careTimeStart }">
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
		buildBreadcumb("新增/修改手术信息");
		$('.nav-pills a:first').focus();  // fix issues of first tab is not focused after loading
		
		
	}
	
	function initEvent() {
		var registId = '${registId}';
		$('#cancle_btn').click(function() {
			$('#content').loadAngular("<%=serverUrl%>care/operation_detail?id=${id}&registId="+registId+"&"+$("#condition_form").serialize() );
		});
		$('#save_btn').click(function() {
			
			sa.ajax({
				type : "post",
				url : "<%=serverUrl%>care/save_operation_record",
				data : $("#player_form").serialize(),
				success : function(data) {
					alert("修改成功");
					$('#content').loadAngular("<%=serverUrl%>care/operation_detail?id=${id}&registId="+registId+"&"+$("#condition_form").serialize());
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