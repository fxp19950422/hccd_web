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
	<table style="clear: both" id="table" data-classes="table table-no-bordered sprotsdatatable" data-toggle="table"
		data-striped="true" data-pagination="true"
		data-page-size="7" data-page-list="[7,10,15,20]"
		data-pagination-first-text="第一页" data-pagination-pre-text="上页"
		data-pagination-next-text="下页" data-pagination-last-text="最后页">
		<thead>
			<tr>
				<th data-field="dirName" data-align="center">目录名</th>
				<th data-field="fileName" data-align="center">文件名</th>
				<th data-field="dirName"  data-formatter="actionFormatter"  data-align="center">点击进入</th>
			</tr>
		</thead>
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
	
	function handle(dirPath) {
		var url ="<%=serverUrl%>care/care_detail?id=" + recordId;
		sa.ajax({
			type : "get",
			url : url,
			success : function(data) {
				AngularHelper.Compile($('#content'), data);
			},
			error: function() {
				alert("页面打开失败");
			}
		});
	}
	
	function initEvent() {
		$("#table").bootstrapTable();
		
		$("#table").bootstrapTable('refresh', {url: "<%=request.getContextPath()%>/care/patient_documents?patientName=" + ${patientName});
		
	}
</script>