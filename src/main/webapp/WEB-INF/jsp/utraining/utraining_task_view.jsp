<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="xss" uri="http://www.sportsdata.cn/xss"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName()
			+ (request.getServerPort() == 80 ? "" : ":" + request.getServerPort()) + path + "/";
	response.setHeader("Cache-Control", "no-store");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", 0);
%>

<style>
label>.utraining_dis_label {
	height: 35px;
	line-height: 35px;
	font-size: 14px;
	color: #333333;
}

div>.utraining_dis_label {
	min-height: 35px;
	line-height: 35px;
	font-size: 14px;
	color: #666666;
	height:auto;
	padding-right: 0px;
	display:inline-table;
}
</style>

<script type="text/javascript">

	$(function() {
		setTimeout(function(){
			
			if($("#reqSource").val()!="share"){
				buildBreadcumb("任务查看");
			} else {
				$(".utraining_top_area").empty();
				$(".utraining_top_area").html("<button style='float: right; margin-left: 10px;' type='button' class='btn btn-default' id='btn_back' onclick='taskBack();'>返回</button>");
			}
			
			initData();
			initEvent();
			switchFunc();
		},50);
	});
	
	function initData(){
		var tempId = $("#tempId").val();
		var utrainingBO = getBoFromPage();
		if(utrainingBO){
			var taskList = utrainingBO.taskList;
			if(taskList){
				var singleTrainingListStr = '<xss:xssFilter text="${singleTrainingList}" filter="js" />';
				var singleTrainingVOList = JSON.parse(singleTrainingListStr);
				for(var p=0;p<taskList.length;p++){
					var task = taskList[p];
					if(task.tempId==tempId){
						if (task.title) {
							$("#utraining_task_title").html(task.title.replaceAll("<", "&lt;"));
						}
						if (task.location) {
							$("#utraining_task_location").html(task.location.replaceAll("<", "&lt;"));
						}
						
						$("#utraining_task_date").html(new Date(task.taskDate).Format("yyyy-MM-dd"));
						$("#utraining_task_time").html(task.taskTime);
						
						if(task.type=='training'){
							$(".utraining_task_detail_training").css("display","block");
							$(".utraining_task_detail_match").css("display","none");
							$(".utraining_task_detail_other").css("display","none");
							
							$(".task_training_evaluation").css("display","none");
							$(".task_match_evaluation").css("display","none");
							
							$("#utraining_task_type").html("训练");
							var desc = getInfoFromExt(task.extList,"key_task_ext_task_training_desc");
							if (desc) {
								$("#utraining_task_desc").html(desc.replaceAll("<", "&lt;"));
							}
							var goalDesc = getInfoFromExt(task.extList,"key_task_ext_task_training_goal");
							if (goalDesc) {
								$("#utraining_task_goal").html(goalDesc.replaceAll("<", "&lt;"));
							}
							var extTaskGoal = getInfoFromExt(task.extList,"key_task_ext_task_training_goal");
							if (extTaskGoal) {
								$("#utraining_task_goal").html(extTaskGoal.replaceAll("<", "&lt;"));
							}
							
							
							
							if(task.evaList&&task.evaList.length>0){
								$(".task_evaluation_area").css("display","block");
								$(".task_training_evaluation").css("display","block");
								
								for(var i=0;i<task.evaList.length;i++){
									var eva = task.evaList[i];
									if (!isInPlayerList(utrainingBO.playerList, eva.userId)) {
										continue;
									}
									var userName = getPlayerName(utrainingBO.allPlayerList,eva.userId);
									if (userName) {
										var evaluation = eva.evaluation;
										var score = eva.score;
										var isTraining = getInfoFromExt(eva.evaExtList,"key_task_evaluation_training_training");
										var isHurt = getInfoFromExt(eva.evaExtList,"key_task_evaluation_training_hurt");
										var isIll = getInfoFromExt(eva.evaExtList,"key_task_evaluation_training_ill");
										var isAbsent = getInfoFromExt(eva.evaExtList,"key_task_evaluation_training_absent");
										
										var innerHtml = [];
										innerHtml.push('<tr style="height:30px;"><td style="width: 15%;">');
										if (userName) {
											innerHtml.push(userName.replaceAll("<", "&lt;"));
										}
										innerHtml.push('</td>');
										if("true"==isTraining){
											innerHtml.push('<td style="width: 10%;"><span ischeck="true" class="glyphicon glyphicon-ok utraining_evaluation_checkBox_checked"></span></td>');
										} else {
											innerHtml.push('<td style="width: 10%;"><span ischeck="false" class="glyphicon glyphicon-ok utraining_evaluation_checkBox_unchecked"></span></td>');
										}
										if("true"==isHurt){
											innerHtml.push('<td style="width: 10%;"><span ischeck="true" class="glyphicon glyphicon-ok utraining_evaluation_hurt_checkBox_checked"></span></td>');
										} else {
											innerHtml.push('<td style="width: 10%;"><span ischeck="false" class="glyphicon glyphicon-ok utraining_evaluation_checkBox_unchecked"></span></td>');
										}
										if("true"==isIll){
											innerHtml.push('<td style="width: 10%;"><span ischeck="true" class="glyphicon glyphicon-ok utraining_evaluation_ill_checkBox_checked"></span></td>');
										} else {
											innerHtml.push('<td style="width: 10%;"><span ischeck="false" class="glyphicon glyphicon-ok utraining_evaluation_checkBox_unchecked"></span></td>');
										}
										if("true"==isAbsent){
											innerHtml.push('<td style="width: 10%;"><span ischeck="true" class="glyphicon glyphicon-ok utraining_evaluation_absent_checkBox_checked"></span></td>');
										} else {
											innerHtml.push('<td style="width: 10%;"><span ischeck="false" class="glyphicon glyphicon-ok utraining_evaluation_checkBox_unchecked"></span></td>');
										}
										innerHtml.push('<td style="width: 35%;">');
										if (evaluation) {
											innerHtml.push(evaluation.replaceAll("<", "&lt;"));
										}
										innerHtml.push('</td><td style="width: 10%;">');
										innerHtml.push(score);
										innerHtml.push('</td></tr>');
										var table = $(".task_training_evaluation").children("table");
										$(table).append(innerHtml.join(''));
									}
								}
							}
							
							if(task.singleTrainingList&&task.singleTrainingList.length>0){
								var singleTrainingArray = [];
								for (var i = 0; i < task.singleTrainingList.length; i++) {
									var found = false;
									for (var j = 0; j < singleTrainingVOList.length && !found; j++) {
										if (task.singleTrainingList[i] == singleTrainingVOList[j].id) {
											singleTrainingArray.push(singleTrainingVOList[j]);
											found = true;
										}
									}
								}
								
								var trainingContent = [];
								for (var i = 0; i < singleTrainingArray.length; i++) {
									var name = singleTrainingArray[i].name;
									name = name.replaceAll("<", "&lt;");
									name = name.replaceAll(">", "&gt;");
									trainingContent.push('<button type="button" class="btn btn-link singleTraingName" singleTraingID="')
									trainingContent.push(singleTrainingArray[i].id)
									trainingContent.push('" title="');
									trainingContent.push(name);
									trainingContent.push('" onclick="showSingleTrainingDetail(this)">')
									trainingContent.push(name);
									trainingContent.push('</button>');
								}
								$("#utraining_task_content").html(trainingContent.join(""));
							}
							
						} else if(task.type=='match'){
							$(".utraining_task_detail_training").css("display","none");
							$(".utraining_task_detail_match").css("display","block");
							$(".utraining_task_detail_other").css("display","none");
							
							$(".task_training_evaluation").css("display","none");
							$(".task_match_evaluation").css("display","none");

							$("#utraining_task_type").html("比赛");
							var matchOpponent = getInfoFromExt(task.extList,"key_task_ext_task_match_opponent");
							if (matchOpponent) {
								$("#utraining_task_match_opponent").html(matchOpponent.replaceAll("<", "&lt;"));
							}
							var matchAddress = getInfoFromExt(task.extList,"key_task_ext_task_match_dress")
							if (matchAddress) {
								$("#utraining_task_match_dress").html(matchAddress.replaceAll("<", "&lt;"));
							}
							
							var matchFood = getInfoFromExt(task.extList,"key_task_ext_task_match_food");
							if (matchFood) {
								$("#utraining_task_match_food").html(matchFood.replaceAll("<", "&lt;"));
							}
							
							var matchNote = getInfoFromExt(task.extList,"key_task_ext_task_match_note");
							if (matchNote) {
								$("#utraining_task_match_note").html(matchNote.replaceAll("<", "&lt;"));
							}
							if(task.evaList&&task.evaList.length>0){
								$(".task_evaluation_area").css("display","block");
								$(".task_match_evaluation").css("display","block");
								
								for(var i=0;i<task.evaList.length;i++){
									var eva = task.evaList[i];
									if (!isInPlayerList(utrainingBO.playerList, eva.userId)) {
										continue;
									}
									var userName = getPlayerName(utrainingBO.allPlayerList,eva.userId);
									if (userName) {
										var evaluation = eva.evaluation;
										var score = eva.score;
										var isStarting = getInfoFromExt(eva.evaExtList,"key_task_evaluation_match_starting");
										var isReplace = getInfoFromExt(eva.evaExtList,"key_task_evaluation_match_replace");
										var isAbsent = getInfoFromExt(eva.evaExtList,"key_task_evaluation_match_absent");
										
										var innerHtml = [];
										innerHtml.push('<tr style="height:30px;"><td style="width: 15%;">');
										if (userName) {
											innerHtml.push(userName.replaceAll("<", "&lt;"));
										}
										innerHtml.push('</td>');
										if("true"==isStarting){
											innerHtml.push('<td style="width: 10%;"><span ischeck="true" class="glyphicon glyphicon-ok utraining_evaluation_checkBox_checked"></span></td>');
										} else {
											innerHtml.push('<td style="width: 10%;"><span ischeck="false" class="glyphicon glyphicon-ok utraining_evaluation_checkBox_unchecked"></span></td>');
										}
										if("true"==isReplace){
											innerHtml.push('<td style="width: 10%;"><span ischeck="true" class="glyphicon glyphicon-ok utraining_evaluation_replace_checkBox_checked"></span></td>');
										} else {
											innerHtml.push('<td style="width: 10%;"><span ischeck="false" class="glyphicon glyphicon-ok utraining_evaluation_checkBox_unchecked"></span></td>');
										}
										if("true"==isAbsent){
											innerHtml.push('<td style="width: 10%;"><span ischeck="true" class="glyphicon glyphicon-ok utraining_evaluation_absent_checkBox_checked"></span></td>');
										} else {
											innerHtml.push('<td style="width: 10%;"><span ischeck="false" class="glyphicon glyphicon-ok utraining_evaluation_checkBox_unchecked"></span></td>');
										}
										innerHtml.push('<td style="width: 45%;">');
										if (evaluation) {
											innerHtml.push(evaluation.replaceAll("<", "&lt;"));
										}
										innerHtml.push('</td><td style="width: 10%;">');
										innerHtml.push(score);
										innerHtml.push('</td></tr>');
										var table = $(".task_match_evaluation").children("table");
										$(table).append(innerHtml.join(''));
									}
								}
							}
							
						} else if(task.type=='life'){
							$(".utraining_task_detail_training").css("display","none");
							$(".utraining_task_detail_match").css("display","none");
							$(".utraining_task_detail_other").css("display","block");
							
							$(".task_training_evaluation").css("display","none");
							$(".task_match_evaluation").css("display","none");
							
							$("#utraining_task_type").html("日常生活");
							$("#utraining_task_other_note").html(getInfoFromExt(task.extList,"key_task_ext_task_other_note"));
						} else {
							$(".utraining_task_detail_training").css("display","none");
							$(".utraining_task_detail_match").css("display","none");
							$(".utraining_task_detail_other").css("display","block");
							
							$(".task_training_evaluation").css("display","none");
							$(".task_match_evaluation").css("display","none");
							
							$("#utraining_task_type").html("其他");
							$("#utraining_task_other_note").html(getInfoFromExt(task.extList,"key_task_ext_task_other_note"));
						}
					}
				}
			}
		}
	}
	
	function initEvent(){
		
	}
	
	function switchFunc(){
		var func = $("#func").val();
		if("view"==func){
			$("#btn_back").css("display","block");
			$("#btn_edit").css("display","none");
			$("#btn_delete").css("display","none");
		} else {
			$("#btn_back").css("display","block");
			$("#btn_edit").css("display","block");
			$("#btn_delete").css("display","block");
		}
	}
	
	
	function taskBack(){
		var func = $("#func").val();	
		var url = "";
		if($("#reqSource").val()!="share"){
			url = "<%=basePath%>utraining/edit_calendar?frompage=task&func="
				+ func+"&utrainingId="+$("#utraining_id").val();
		} else{
			url = "<%=basePath%>utraining/share_plan?orgId="+$("#orgId").val()+"&utrainingId="+$("#utraining_id").val();
		}
		sa.ajax({
			type : "post",
			url : url,
			data : JSON.stringify(getBoFromPage()),
			contentType : "application/json",
			success : function(data) {
				AngularHelper.Compile($("#content"), data);
			},
			error : function(data) {
			}
		});
	}

	function taskDelete() {
		var tempId = $("#tempId").val();
		var trainingBO = getBoFromPage();
		if (trainingBO) {
			if (trainingBO.taskList) {
				for (var i = 0; i < trainingBO.taskList.length; i++) {
					var task = trainingBO.taskList[i];
					if (task.tempId == tempId) {
						trainingBO.taskList.splice(i, 1);
						break;
					}
				}
			}
			putBo2Page(trainingBO);
		}
		taskBack();
	}
	
	
	function taskEdit(){
		var url = "<%=basePath%>utraining/edit_training_task?func=edit&tempId="
				+ $("#tempId").val() + "&utrainingId="
				+ $("#utraining_id").val();
		sa.ajax({
			type : "post",
			url : url,
			data : JSON.stringify(getBoFromPage()),
			contentType : "application/json",
			success : function(data) {
				AngularHelper.Compile($("#content"), data);
			},
			error : function(data) {
			}
		});
	}
	
	function showConfirmDialog(){
// 		$("#confirmClassModal").modal({backdrop:false,show:true});
		bootbox.dialog({
			message: "您是否要删除该任务？",
			title: "确认删除",
			buttons: {
				unchange: {
				      label: "取消",
				      className: "btn-default",
				      callback: function() {}
				    },
				danger: {
					label: "删除",
					className: "btn-danger",
					callback: function() {
						taskDelete();
					}
				}
			}
		});
	}
	
	function showSingleTrainingDetail(obj) {
		var element = $(obj);
		var singleTrainingID = element.attr("singleTraingID");
		var singleTrainingName = element.text();
		$("#singleTrainingTitle").text(singleTrainingName);
		var url = "<%=basePath%>singletraining/showSingleTrainingDetail?id=" + singleTrainingID + "&action=view";
		sa.ajax({
			type : "get",
			url : url,
			success : function(data) {
				AngularHelper.Compile($('#singleTrainingDetailContainer'), data);
			},
			error: function() {
				alert("打开单项训练详情页面失败。");
			}
		});
		$("#singleTrainingModal").modal({backdrop:false,show:true});
	}
</script>


<div class="main_container">

	<div class="utraining_top_area">
		<div class="button_area">
			<button style="float: right; margin-left: 10px;" type='button' class='btn btn-primary'
				id="btn_edit" onclick='taskEdit();'>编辑</button>
			<button style="float: right; margin-left: 10px;" type='button' class='btn btn-danger'
				id="btn_delete" onclick='showConfirmDialog();'>删除</button>
			<button style="float: right; margin-left: 10px;" type='button' class='btn btn-default'
				id="btn_back" onclick='taskBack();'>返回</button>
		</div>
	</div>


	<div class="utraining_task_detail">
		<sa-panel title="任务详情">
		<div class="row">
				<label class="col-md-1 utraining_dis_label">标题</label>
				<div class="col-md-4 utraining_dis_label" id="utraining_task_title"></div>
				<label class="col-md-1 utraining_dis_label">地点</label>
				<div class="col-md-4 utraining_dis_label" id="utraining_task_location"></div>
		</div>

		<div class="row" style="margin-top: 10px;">
				<label class="col-md-1 utraining_dis_label">日期</label>
				<div class="col-md-4 utraining_dis_label" id="utraining_task_date"></div>
					<label class="col-md-1 utraining_dis_label">时间</label>
					<div class="col-md-4 utraining_dis_label" id="utraining_task_time"></div>
		</div>

		<div class="utraining_task_detail_training">
			<div class="row" style="margin-top: 10px;">
					<label class="col-md-1 utraining_dis_label">训练内容</label>
					<div class="col-md-10 utraining_dis_label" id="utraining_task_content"></div>
			</div>

			<div class="row" style="margin-top: 10px;">
				<label class="col-md-1 utraining_dis_label">任务描述</label>
				<div class="col-md-10 utraining_dis_label" id="utraining_task_desc" style=" height: auto;"></div>
			</div>

			<div class="row" style="margin-top: 10px;">
				<label class="col-md-1 utraining_dis_label">任务目标</label>
				<div class="col-md-10 utraining_dis_label" id="utraining_task_goal" style=" height: auto;"></div>
			</div>
		</div>
		<div class="utraining_task_detail_match" style="display: none;">
			<div class="row" style="margin-top: 10px;">
					<label class="col-md-1 utraining_dis_label">对手</label>
					<div class="col-md-4 utraining_dis_label" id="utraining_task_match_opponent"></div>
					<label class="col-md-1 utraining_dis_label">着装</label>
					<div class="col-md-4 utraining_dis_label" id="utraining_task_match_dress"></div>
			</div>

			<div class="row" style="margin-top: 10px;">
				<label class="col-md-1 utraining_dis_label">饮食注意</label>
				<div class="col-md-10 utraining_dis_label" id="utraining_task_match_food" style=" height: auto;"></div>
			</div>

			<div class="row" style="margin-top: 20px;">
				<label class="col-md-1 utraining_dis_label">备注</label>
				<div class="col-md-10 utraining_dis_label" id="utraining_task_match_note" style=" height: auto;"></div>
			</div>
		</div>
		<div class="utraining_task_detail_other" style="display: none;">
			<div class="row" style="margin-top: 10px;">
				<label class="col-md-1 utraining_dis_label">备注</label>
				<div class="col-md-10 utraining_dis_label" id="utraining_task_other_note" style=" height: auto;"></div>
				</div>
		</div>
		</sa-panel>
	</div>

	<div class="task_evaluation_area" style="display: none;">
		<sa-panel title="球员评估">
		<div class="task_training_evaluation">
			<table border="1" class="utraining_task_table">
				<tr style="background-color:#E2F4FF; height: 30px;">
					<td style="width: 15%;">姓名</td>
					<td style="width: 10%;">训练</td>
					<td style="width: 10%;">受伤</td>
					<td style="width: 10%;">生病</td>
					<td style="width: 10%;">缺席</td>
					<td style="width: 35%;">评估</td>
					<td style="width: 10%;">打分( 0 ~ 10 )</td>
				</tr>
			</table>
		</div>
		<div class="task_match_evaluation">
			<table border="1" class="utraining_task_table">
				<tr style="background-color:#E2F4FF; height: 30px;">
					<td style="width: 15%;">姓名</td>
					<td style="width: 10%;">首发</td>
					<td style="width: 10%;">替补</td>
					<td style="width: 10%;">没上场</td>
					<td style="width: 45%;">评估</td>
					<td style="width: 10%;">打分( 0 ~ 10 )</td>
				</tr>
			</table>
		</div>
		</sa-panel>
	</div>
</div>

<input id="tempId" value="${tempId}" type="hidden" />
<input id="func" value="${func}" type="hidden" />
<input type="hidden" id="utraining_id" value="${utrainingId}" />
<input type="hidden" id="orgId" value="${orgId}" />



<!-- <div class="modal fade" id="confirmClassModal" tabindex="-1" role="dialog" -->
<!-- 	aria-labelledby="myModalLabel" style="z-index: 100001; display: none;"> -->
<!-- 	<div class="modal-dialog" role="document" style="width: 400px;"> -->
<!-- 		<div class="modal-content"> -->
<!-- 			<div class="modal-header"> -->
<!-- 				<button type="button" class="close" data-dismiss="modal" -->
<!-- 					aria-label="Close"> -->
<!-- 					<span aria-hidden="true">&times;</span> -->
<!-- 				</button> -->
<!-- 				<h4 class="modal-title" id="myModalLabel">确认删除该记录？</h4> -->
<!-- 			</div> -->
<!-- 			<div class="modal-footer"> -->
<!-- 				<button type="button" class="btn btn-default" data-dismiss="modal">取消</button> -->
<!-- 				<button type="button" class="btn btn-primary" data-dismiss="modal" -->
<!-- 					onClick="taskDelete();">确定</button> -->
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 	</div> -->
<!-- </div> -->

<div class="modal fade" id="singleTrainingModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" style="z-index: 100001; display: none;">
	<div class="modal-dialog" role="document" style="width: 1024px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="singleTrainingTitle"></h4>
			</div>
			<div class="modal-body">
			<div id="singleTrainingDetailContainer"></div>
			</div>
		</div>
	</div>
</div>