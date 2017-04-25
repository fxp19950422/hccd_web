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
		<button id="add_medical_btn" class="btn btn-primary"
			style="float: right; margin-left: 10px;">新增门诊记录</button>
		<button id="cancle_btn" class="btn btn-default" style="float: right;">返回</button>
	</div>
	<div class="clearfix"></div>
	<div class="profileEditContent">
		<form id="player_form">
			<input type="hidden" value="${record.id }" id="recordId"
				name="recordId" readonly>
		</form>
		<div role="tabpanel" class="tab-pane active" id="basic_tab">
			<sa-panel title="病人信息">
			<div class="row">
				<div class="col-md-1 profileDetailItemTitle">姓名</div>
				<div class="col-md-3 profileDetailItemContent">${record.name}</div>
				<div class="col-md-1 profileDetailItemTitle">性别</div>
				<div class="col-md-3 profileDetailItemContent">${record.sex}</div>
				<div class="col-md-1 profileDetailItemTitle">年龄</div>
				<div class="col-md-3 profileDetailItemContent">${record.age}</div>
			</div>
			</sa-panel>
		</div>

	</div>

	<div>
		<table style="clear: both" id="btable"
			data-classes="table table-no-bordered sprotsdatatable"
			data-toggle="table" data-striped="true" data-pagination="true"
			data-page-size="7" data-page-list="[7,10,15,20]"
			data-pagination-first-text="第一页" data-pagination-pre-text="上页"
			data-pagination-next-text="下页" data-pagination-last-text="最后页">
			<thead>
				<tr>
					<th data-field="visitDate" data-formatter="dateFormatter"
						data-align="center">日期</th>
					<th data-field="recordType" data-formatter="typeFormatter"
						data-align="center">类型</th>
					<th data-align="center">项目</th>
					<th data-align="center">医生</th>
					<th data-field="id" data-formatter="actionFormatter"
						data-align="center">操作</th>
				</tr>
				
				
				
			</thead>
			<tbody>
				<c:forEach items="${list}" var="record">
				<c:if test="${record.recordType == 'medical' }">
					<tr>
						<td>${record.medicalRecord.visitDate }</td>
						<td>${record.recordType }</td>
						<td>${record.medicalRecord.illnessDesc }</td>
						<td>${record.medicalRecord.doctor }</td>
						<td>${record.medicalRecord.id }</td>
					</tr>
				</c:if>
				<c:if test="${record.recordType == 'resident' }">
					<tr>
						<td>${record.residentRecord.admissionDate }</td>
						<td>${record.recordType }</td>
						<td>${record.residentRecord.inChiDiagnosis }</td>
						<td>${record.residentRecord.residentId }</td>
						<td>${record.residentRecord.id }</td>
					</tr>
				</c:if>
				<c:if test="${record.recordType == 'operation' }">
					<tr>
						<td>
						${record.operationRecord.operatingDate }
						</td>
						<td>${record.recordType }</td>
						<td>${record.operationRecord.operationDescription }</td>
						<td>${record.operationRecord.operator }</td>
						<td>${record.operationRecord.id }</td>
					</tr>
				</c:if>
				
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>
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
		buildBreadcumb("新增/修改诊断记录");
		$('.nav-pills a:first').focus();  // fix issues of first tab is not focused after loading
	}
	
	
	
	function actionFormatter(value, row, index){
		return '<span onclick=handle("'+value+'","'+row.recordType+'") style="margin-left:10px;cursor:pointer" ><i class="glyphicon glyphicon-search content-color"></i></span>';
	}
	
	function handle(recordId,recordType) {
		if(recordId==undefined){
			recordId=0;
		}
		var url ;
		if(recordType=='medical'){
			url ="<%=serverUrl%>care/care_detail?id=" + recordId;
		} else if(recordType=='operation'){
			url ="<%=serverUrl%>care/operation_detail?id=" + recordId;
		} else if(recordType=='resident'){
			url ="<%=serverUrl%>care/resident_detail?id=" + recordId;
		}
		sa.ajax({
			type : "get",
			url : url,
			data :{registId:$("#recordId").val()},
			success : function(data) {
				AngularHelper.Compile($('#content'), data);
			},
			error: function() {
				alert("页面打开失败");
			}
		});
	}
	
	function typeFormatter(value, row, index){
		if('medical' == value){
			return "门诊记录";
		} else if ('resident' == value) {
			return '住院记录';
		} else if ('operation' == value) {
			return '手术记录';
		}
	}
	
	
	function dateFormatter(value, row, index){
		if(value){
			return new Date(value).Format("yyyy年MM月dd日")
		}
		return "-";
	}
	
	function initEvent() {
		$('#cancle_btn').click(function(){
			$('#content').loadAngular("<%=serverUrl%>register/register_list" );
		});
		$("#add_medical_btn").click(function(){
			$('#content').loadAngular("<%=serverUrl%>care/add_care?registId=${record.id }" );
		});
		
		$("#btable").bootstrapTable();
<%-- 		$("#btable").bootstrapTable('refresh', {url: "<%=request.getContextPath()%>/register/register_detail_his_list?" --%>
// 									+ $("#player_form").serialize()
// 						})
	}

</script>