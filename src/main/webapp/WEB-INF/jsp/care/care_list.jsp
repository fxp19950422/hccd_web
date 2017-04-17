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
	<div class="row">
		<div class="col-md-1 inputLabel">姓名</div>
		<div class="col-md-3">
			<input type="text" class="profileEditInput form-control" id="name" name="name" />
		</div>
		<div class="col-md-1 inputLabel">身份证</div>
		<div class="col-md-3">
			<input type="text" class="profileEditInput form-control" id="idNumber" name="idNumber" />
		</div>
		<div class="col-md-1 inputLabel">就诊时间</div>
			<div class="col-md-3">
			<div class="input-group date">
				<input type="text" class="form-control profileEditInput calendar-input" id="careTimeStart" name="careTimeStart" readonly>
				<span id="birthdayIcon" class="input-group-addon calendar-icon"><i class="glyphicon glyphicon-calendar major_color"></i></span>
			</div>	
			</div>
	</div>
	<div class="row" style="margin-top:10px">
			
			<div class="col-md-1 inputLabel">至</div>
			<div class="col-md-3">
			<div class="input-group date">
				<input type="text" class="form-control profileEditInput calendar-input" id="careTimeEnd" name="careTimeEnd" readonly>
				<span id="birthdayIcon" class="input-group-addon calendar-icon"><i class="glyphicon glyphicon-calendar major_color"></i></span>
			</div>	
			</div>
	</div>
	
	<!-- <div class="row" style="margin-top:10px">
			<div class="col-md-3 inputLabel"></div>
			<div class="col-md-6 inputLabel" >
				<button id="save_btn" class="btn btn-primary" style="margin:0 auto">保存</button>
				<button id="cancle_btn" class="btn btn-default">取消</button>
			</div>
			<div class="col-md-3"></div>
	</div> -->
	<div style="margin:0 auto;width:200px">
		<button id="save_btn" class="btn btn-primary">检索</button>
		<button id="cancle_btn" class="btn btn-default">取消</button>
	</div>
</div>


<div class="clearfix" style="height:15px;clear:both;"></div>
<div class="clearfix" style="height:15px;clear:both;"></div>
<div >
	<table style="clear: both" id="table" data-classes="table table-no-bordered sprotsdatatable" data-toggle="table"
		data-striped="true" data-pagination="true"
		data-page-size="7" data-page-list="[7,10,15,20]"
		data-pagination-first-text="第一页" data-pagination-pre-text="上页"
		data-pagination-next-text="下页" data-pagination-last-text="最后页">
		<thead>
			<tr>
				<th data-field="visitDate"   data-align="center">就诊时间</th>
				<th data-field="realName" data-align="center">病人姓名</th>
				<th data-field="illnessDesc" data-align="center">主诉</th>
				<th data-field="diagDesc" data-align="center">诊断</th>
				<th data-field="phoneNumber" data-align="center">电话号码</th>
				<th data-field="identityNumber" data-align="center">身份证号</th>
				<th data-field="id"  data-formatter="actionFormatter"  data-align="center">操作</th>
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
	
	function handle(recordId) {
		if(recordId==undefined){
			recordId=0;
		}
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
		
		$("#table").bootstrapTable('refresh', {url: "<%=request.getContextPath()%>/care/medical_records?rnd=" + Math.random()});
	}
</script>