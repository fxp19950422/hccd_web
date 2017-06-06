<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://www.sportsdata.cn/dateutil" prefix="DateUtil"%>

<%@ page import="cn.sportsdata.webapp.youth.common.utils.CommonUtils"%>
<%@ page import="cn.sportsdata.webapp.youth.common.vo.UserVO"%>
<%@ page import="cn.sportsdata.webapp.youth.common.utils.DateUtil"%>
<jsp:useBean id="now" class="java.util.Date" />
<%@ page import="java.util.Date"%>

<%
	String serverUrl = CommonUtils.getServerUrl(request);
%>

<div class="profileEditContainer">
	<div class="coach_edit_button_area">
		<button id="add_medical_btn" class="btn btn-primary"
			style="float: right; margin-left: 10px;">新增门诊记录</button>
		<button id="history_btn" class="btn btn-primary"
			style="float: right; margin-left: 10px;">历史文档</button>
		<button id="play_btn" class="btn btn-primary"
			style="float: right; margin-left: 10px;">就医历史</button>
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
		<div class="panel-heading"
			style="background-color: #067DC2; color: white;">
			<div style="text-align: center; font-size: 16px; color: #FFFFFF;">当日记录</div>
			<div class="clearfix"></div>
		</div>

		<table style="clear: both;" id="todaytable"
			data-classes="table table-no-bordered sprotsdatatable"
			data-toggle="table" data-striped="true">
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
				<c:forEach items="${todayList}" var="record">
					<c:if test="${record.recordType == 'medical' }">
						<tr >
							<td>${record.medicalRecord.visitDate }</td>
							<td>${record.recordType }</td>
							<td>${record.medicalRecord.illnessDesc }</td>
							<td>${record.medicalRecord.doctor }
							<span  recordType="${record.recordType}" dataid="${record.medicalRecord.id }"></span></td>
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
							<td>${record.operationRecord.operatingDate }</td>
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
	<div style="clear: both; height: 20px;"></div>
	<div>
		<div class="panel-heading"
			style="background-color: #067DC2; color: white">
			<div style="text-align: center; font-size: 16px; color: #FFFFFF;">历史记录</div>
			<div class="clearfix"></div>
		</div>
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
					<th data-align="center">离本次门诊时间</th>
					<th data-align="center">医生</th>
					<th data-field="id" data-formatter="actionHistoryFormatter"
						data-align="center">操作</th>
				</tr>
			</thead>
			<tbody>
			
				<c:forEach items="${list}" var="record">
					<c:if test="${record.recordType == 'medical' }">
						<tr recordType="${record.recordType}" dateId="${record.medicalRecord.id }">
							<td>${record.medicalRecord.visitDate }</td>
							<td>${record.recordType }</td>
							<td>${DateUtil:differentDays(record.medicalRecord.visitDate, now) }天前</td>
							<td>${record.medicalRecord.doctor }</td>
							<td>${record.medicalRecord.id }</td>
						</tr>
					</c:if>
					<c:if test="${record.recordType == 'resident' }">
						<tr>
							<td>${record.residentRecord.admissionDate };${record.residentRecord.dischargeDateTime }</td>
							<td>${record.recordType }</td>
							<td>${DateUtil:differentDays(record.residentRecord.admissionDate, now) }天前</td>
							<td>${record.residentRecord.residentId }</td>
							<td>${record.residentRecord.id }</td>
						</tr>
					</c:if>
					<c:if test="${record.recordType == 'operation' }">
						<tr>
							<td>${record.operationRecord.operatingDate }</td>
							<td>${record.recordType }</td>
							<td>${DateUtil:differentDays(record.operationRecord.operatingDate, now) }天前</td>
							<td>${record.operationRecord.operator }</td>
							<td>${record.operationRecord.id }</td>
						</tr>
					</c:if>

				</c:forEach>
			</tbody>
		</table>
	</div>

	<form id="condition_form">
		<div class="row">
			<input type="hidden"
				class="form-control profileEditInput calendar-input"
				id="careTimeStart" name="careTimeStart"
				value="${condition.careTimeStart }">
		</div>
	</form>
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
		return '<span onclick=handleNow("'+value+'","'+row.recordType+'") style="margin-left:10px;cursor:pointer" ><i class="glyphicon glyphicon-search content-color"></i></span>';
	}
	
	function actionHistoryFormatter(value, row, index){
		return '<span onclick=handleHistory("'+value+'","'+row.recordType+'") style="margin-left:10px;cursor:pointer" ><i class="glyphicon glyphicon-search content-color"></i></span>';
	}
	
	function handleNow(recordId,recordType) {
		if(recordId==undefined){
			recordId=0;
		}
		var url ;
		if(recordType=='medical'){
			url ="<%=serverUrl%>care/care_edit?id=" + recordId+"&"+$("#condition_form").serialize();
		} else if(recordType=='operation'){
			url ="<%=serverUrl%>care/operation_detail?id=" + recordId+"&"+$("#condition_form").serialize();
		} else if(recordType=='resident'){
			url ="<%=serverUrl%>care/resident_detail?id=" + recordId+"&"+$("#condition_form").serialize();
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
	
	function handleHistory(recordId,recordType) {
		window.open("<%=serverUrl%>care/pat_history_document?registId="+$("#recordId").val());
	}
	
	function typeFormatter(value, row, index){
		if('medical' == value){
			return "门诊记录";
		} else if ('resident' == value) {
			return '出院记录';
		} else if ('operation' == value) {
			return '手术记录';
		}
	}
	
	
	function dateFormatter(value, row, index){
		if(value){
			if(value.indexOf(";")>0){
				var start = new Date(value.split(";")[0]).Format("yyyy年MM月dd日")
				var end ;
				if(value.split(";")[1]){
					 end = new Date(value.split(";")[1]).Format("yyyy年MM月dd日")
				} else {
					end = "~"
				}
				
				return start + " 至 " +end
			} else {
				return new Date(value).Format("yyyy年MM月dd日")
			}
			
		}
		return "-";
	}
	
	function initEvent() {
		$('#play_btn').click(function(){
			window.open("<%=serverUrl%>care/medical_history_detail?registId=${record.id }");
		});
		
		$('#cancle_btn').click(function(){
			if('${condition}'){
				$('#content').loadAngular("<%=serverUrl%>register/register_list?" + $("#condition_form").serialize() );
			} else {
				$('#content').loadAngular("<%=serverUrl%>register/register_list");
			}
		});
		$("#add_medical_btn").click(function(){
			$('#content').loadAngular("<%=serverUrl%>care/add_care?registId=${record.id }" );
		});
		$('#history_btn').click(function() {
			sa.ajax({
				type : "get",
				url : "<%=serverUrl%>care/history_document?patientName=${record.name}&id=${id}&registId=${registId}",
				success : function(data) {
					//TODO: will update the container later
					AngularHelper.Compile($('#content'), data);
				},
				error: function() {
					alert("打开编辑页面失败");
				}
			});
		});
		$("#btable").bootstrapTable();
		var msg = '当日记录尚未同步完成，请稍后';
		if('${condition.careTimeStart}'){
			var conDate = '${condition.careTimeStart}';
			var date = new Date();
			var year = date.getFullYear();
			var month = date.getMonth()+1;
			var day = date.getDate();
			var array = conDate.split("-");
			if(parseInt(year)==parseInt(array[0])&&parseInt(month)==parseInt(array[1])&&parseInt(day)==parseInt(array[2])){
				//today
			} else {
				msg = '该日期为'+'${condition.careTimeStart}'+',记录已归档';
			}
		}
		$("#todaytable").bootstrapTable({
			formatNoMatches: function () {  //没有匹配的结果  
			    return msg;  
			  }
		});
		
		/* $("#todaytable tr").click(function(event){
			var td = event.target;
			var dataTd = $(td).parent().children()[3];
			var dataid = $(dataTd).find("span").attr("dataid");
			var datatype = $(dataTd).find("span").attr("recordtype")
			handleNow(dataid,datatype);
		}) */
		
<%-- 		$("#btable").bootstrapTable('refresh', {url: "<%=request.getContextPath()%>/register/register_detail_his_list?" --%>
// 									+ $("#player_form").serialize()
// 						})
	}

</script>