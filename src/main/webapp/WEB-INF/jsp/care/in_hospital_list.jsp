<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="xss" uri="http://www.sportsdata.cn/xss"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="cn.sportsdata.webapp.youth.common.utils.CommonUtils" %>

<%
	String serverUrl = CommonUtils.getServerUrl(request);
%>


<!-- <div class="button_area">
	<button id="add_btn" class="btn btn-primary"  style="float: right;">交班</button>
</div>

<div class="button_area">
	
</div> -->

<div>
<form id="player_form">
	<div class="row">
		<div class="col-md-1 inputLabel">姓名</div>
		<div class="col-md-3">
			<input type="text" class="profileEditInput form-control" id="patName" name="patName" 
				<c:if test="${condition!=null }">
					value="${condition.patName }"
				</c:if>
			/>
		</div>
		<div class="col-md-1 inputLabel">住院开始时间</div>
			<div class="col-md-3">
			<div class="input-group date">
				<input type="text" class="form-control profileEditInput calendar-input" id="careTimeStart" name="careTimeStart"
				<c:if test="${condition!=null }">
					value="${condition.careTimeStart }"
				</c:if>
				 readonly>
				<span id="birthdayIcon" class="input-group-addon calendar-icon"><i class="glyphicon glyphicon-calendar major_color"></i></span>
			</div>	
			</div>
	</div>
	</form>
	<div style="margin:0 auto;margin-top:20px;width:200px">
		<button id="search_btn" class="btn btn-primary">检索</button>
		<button id="cancle_btn" class="btn btn-default">取消</button>
	</div>
</div>


<div class="clearfix" style="height:15px;clear:both;"></div>
<div class="clearfix" style="height:15px;clear:both;"></div>
<div >
	<table style="clear: both" id="table" data-classes="table table-no-bordered sprotsdatatable" data-toggle="table"
		data-striped="true" data-pagination="true"
		data-page-size="20" data-page-list="[7,10,15,20]"
		data-pagination-first-text="第一页" data-pagination-pre-text="上页"
		data-pagination-next-text="下页" data-pagination-last-text="最后页">
		<thead>
			<tr>
				<th data-field="admissionDateTime" data-formatter="dateFormatter"   data-align="center">住院时间</th>
				<th data-field="realName" data-align="center">病人姓名</th>
				<th data-field="doctorInCharge" data-align="center">管床医生</th>
				<th data-field="nursingClass" data-align="center">护理等级</th>
				<th data-field="id"  data-formatter="actionFormatter"  data-align="center">操作</th>
			</tr>
		</thead>
<!-- 		<tbody> -->
<%-- 				<c:forEach items="${record}" var="record"> --%>
<!-- 					<tr> -->
<%-- 						<td>${record.medicalRecord.visitDate }</td> --%>
<%-- 						<td>${record.recordType }</td> --%>
<%-- 						<td>${record.medicalRecord.illnessDesc }</td> --%>
<%-- 						<td>${record.medicalRecord.doctor }</td> --%>
<%-- 						<td>${record.medicalRecord.id }</td> --%>
<!-- 					</tr> -->
<%-- 				</c:forEach> --%>
<!-- 			</tbody> -->
	</table>
</div>
	<style>
		.selected{
			border-style:solid;
  			border-color:#007EC1;
  			border-width:2px;
		}
		.profileCard{
			width: 240px;
			height:100px;
		}
	</style>
	<script type="text/javascript">
	$(function() {
		initEvent();
	});
	
	function actionFormatter(value, row, index){
		return '<span onclick=handle("'+value+'") style="margin-left:10px;cursor:pointer" ><i class="glyphicon glyphicon-edit content-color"></i></span>';
	}
	
	function dateFormatter(value, row, index){
		return new Date(value).Format("yyyy年MM月dd日")
	}
	
	
	function handlePhoto(record_id,recordType){
		window.open("<%=serverUrl%>care/upload_photo?recordId="+record_id+"&recordType=patientInhospital");
	}
	
	function handle(recordId) {
		if(recordId==undefined){
			recordId=0;
		}
		var url ="<%=serverUrl%>care/in_hospital_record_detail?id=" + recordId;
		sa.ajax({
			type : "get",
			url : url,
			data:$("#player_form").serialize(),
			success : function(data) {
				AngularHelper.Compile($('#content'), data);
			},
			error: function() {
				alert("页面打开失败");
			}
		});
	}
	
	function initEvent() {
		
		$("#careTimeStart").datepicker({
			format : "yyyy-mm-dd",
			language : "zh-CN",
			autoclose : true,
			todayHighlight : true,
			toggleActive : true,
			zIndexOffset:1031
		});
		var conditiontime;
		if('${condition}'){
			if('${condition.careTimeStart}'){
				conditiontime = '${condition.careTimeStart}';
			}
		}
		if(!conditiontime){
			$("#careTimeStart").val(new Date().Format("yyyy-MM-dd"));
		}
		
		$("#careTimeEnd").datepicker({
			format : "yyyy-mm-dd",
			language : "zh-CN",
			autoclose : true,
			todayHighlight : true,
			toggleActive : true,
			zIndexOffset:1031
		});
		
		$("#table").bootstrapTable();
		$("#table").bootstrapTable('refresh', {url: "<%=request.getContextPath()%>/care/in_hospital_records?" + $("#player_form").serialize()});
	    $("#search_btn").click(function(){
			$("#table").bootstrapTable('refresh', {url: "<%=request.getContextPath()%>/care/in_hospital_records?" + $("#player_form").serialize()});
		});
	}
</script>