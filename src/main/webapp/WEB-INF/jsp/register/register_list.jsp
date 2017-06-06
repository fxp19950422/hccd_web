<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="xss" uri="http://www.sportsdata.cn/xss"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="cn.sportsdata.webapp.youth.common.utils.CommonUtils" %>

<%
	String serverUrl = CommonUtils.getServerUrl(request);
%>



<div>

	

	<div class="row">
<div class="col-md-2 inputLabel">
</div>
	<div class="col-md-2 inputLabel">
		<input type="checkbox" checked="checked" name="includeMedical" id="includeMedical" >已完成诊疗</input>
	</div>
		<form id="player_form">
		<div class="col-md-1 inputLabel">就诊时间</div>
			<div class="col-md-3">
			<div class="input-group date">
				<input type="text" class="form-control profileEditInput calendar-input" id="careTimeStart" name="careTimeStart" readonly
				<c:if test="${condition!=null }">
					value="${condition.careTimeStart }"
				</c:if>
				>
				<span id="birthdayIcon" class="input-group-addon calendar-icon"><i class="glyphicon glyphicon-calendar major_color"></i></span>
			</div>	
			</div>
<!-- 			<div class="col-md-1" style="width:200px"> -->
<!-- 				<button id="search_btn" class="btn btn-primary">检索</button> -->
<!-- 				<button id="cancle_btn" class="btn btn-default">取消</button> -->
<!-- 			</div> -->
			</form>
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
				<th data-field="visitDate" data-formatter="dateFormatter"  data-align="center">日期</th>
				<th data-field="name" data-align="center">病人姓名</th>
				<th data-field="sex" data-align="center">性别</th>
				<th data-field="age" data-align="center">年龄</th>
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
		setTimeout(function(){
			initEvent();	
		}),10
	});
	
	/* $(function() {
		setTimeout(function() {
			initData();
			initEvent();
		}, 50);  // if using angular widget like sa-panel, since the real dom loaded is after the Angular.compile method, which is behind the document ready event, so add a little timeout to hack this
	}); */
	
	function actionFormatter(value, row, index){
		return '<span onclick=handle("'+value+'") style="margin-left:10px;cursor:pointer" ><i class="glyphicon glyphicon-edit content-color"></i></span>';
	}
	
	
	function dateFormatter(value, row, index){
		return new Date(value).Format("yyyy年MM月dd日")
	}
	
	function handle(recordId) {
		if(recordId==undefined){
			recordId=0;
		}
		var url ="<%=serverUrl%>register/register_detail?id=" + recordId;
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
		}).on('changeDate', function(e){
			$("#table").bootstrapTable('refresh', {url: "<%=request.getContextPath()%>/register/register_records?" + $("#player_form").serialize()+"&includeMedical="+$("#includeMedical").is(':checked')});
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
		
		$("#table").bootstrapTable('refresh', {url: "<%=request.getContextPath()%>/register/register_records?" + $("#player_form").serialize()+"&includeMedical="+$("#includeMedical").is(':checked')});
		
	    $("#search_btn").click(function(){
				//$("#table").bootstrapTable('refresh', {url: "<%=request.getContextPath()%>/register/register_records?" + $("#player_form").serialize()}); 
	    	$("#table").bootstrapTable('refresh', {url: "<%=request.getContextPath()%>/register/register_records?" + $("#player_form").serialize()+"&includeMedical="+$("#includeMedical").is(':checked')});
		});
		
		$("#includeMedical").change(function(){
			$("#table").bootstrapTable('refresh', {url: "<%=request.getContextPath()%>/register/register_records?" + $("#player_form").serialize()+"&includeMedical="+$("#includeMedical").is(':checked')});
		});
	}
</script>