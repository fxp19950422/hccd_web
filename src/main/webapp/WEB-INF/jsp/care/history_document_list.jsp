

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="xss" uri="http://www.sportsdata.cn/xss"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="cn.sportsdata.webapp.youth.common.utils.CommonUtils" %>

<%
	String serverUrl = CommonUtils.getServerUrl(request);
%>

<div >
	<div class="coach_edit_button_area">
		<button id="cancle_btn" class="btn btn-default" style="float: right;">返回</button>
	</div>
	
	<table style="clear: both" id="table" data-classes="table table-no-bordered sprotsdatatable" data-toggle="table"
		data-striped="true" data-pagination="true"
		data-page-size="15" data-page-list="[7,10,15,20]"
		data-pagination-first-text="第一页" data-pagination-pre-text="上页"
		data-pagination-next-text="下页" data-pagination-last-text="最后页">
		<thead>
			<tr>
				<th data-field="dirName" data-align="center">目录地址</th>
				<th data-field="fileName" data-align="center">文件名</th>
				<th data-field="filePath"  data-formatter="actionFormatter"  data-align="center">下载文档</th>
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
		if (value == null || value == undefined) {
			return ''
		}
		return '<span onclick=handle("'+value+'") style="margin-left:10px;cursor:pointer" ><i class="glyphicon glyphicon-edit content-color"></i></span>';
	}
	function handle(filePath) {
		if (filePath == "null") {
			alert("目录无法下载");
		} else {
			if(filePath == undefined){
				alert("下载文件失败");
			} else {
				window.open("<%=serverUrl%>/care/download_history_document?filePath=" + filePath);
			}
		}
	}
	
	function initEvent() {
		$('#cancle_btn').click(function(){
			$('#content').loadAngular("<%=serverUrl%>register/register_detail?id=${registId}");
		});
		$("#table").bootstrapTable();
		$("#table").bootstrapTable('refresh', {url: "<%=request.getContextPath()%>/care/patient_documents?patientName=${patientName}"});

	}
</script>