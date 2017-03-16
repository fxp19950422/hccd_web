<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="xss" uri="http://www.sportsdata.cn/xss"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
	+ request.getServerName() + (request.getServerPort() == 80 ? "":":" + request.getServerPort())
			+ path + "/";
	response.setHeader("Cache-Control", "no-store");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", 0);
%>

<style>
.time_area {
	clear: both;
}

.table_input {
	BACKGROUND-COLOR: transparent;
	BORDER-BOTTOM: #ffffff 1px solid;
	BORDER-LEFT: #ffffff 1px solid;
	BORDER-RIGHT: #ffffff 1px solid;
	BORDER-TOP: #ffffff 1px solid;
	HEIGHT: 100%;
	width: 100%;
	border-color: #ffffff #ffffff #ffffff #ffffff;
}

#singleTrainingModal th{
	background-color:#067DC2;
	color:white;
}
</style>

<script type="text/javascript">
var g_singleTrainingIDList = new Array()
var g_singleTrainingNameList = new Array()
var dateStart;
var dateEnd;
	$(function() {
		setTimeout(function(){
			
			buildBreadcumb("任务编辑");
			
			var utrainingBO = getBoFromPage();
			
			if(!utrainingBO){
				return;
			}
			var task = initData(utrainingBO);
			$(".typeSel").select2({
				minimumResultsForSearch : Infinity
			});
			$(".playerSel").select2({
				minimumResultsForSearch : Infinity
			});
			initEvent(utrainingBO,task);
			
			switchFunc();
		},50);
	});
	
	
	function switchFunc(){
		var func = $("#func").val();
		if("edit"==func){
			// is add or not
			var utrainingTaskId = $("#utrainingTaskId").val();
			if(!utrainingTaskId || utrainingTaskId<0){
				// add 
				$("#btn_edit").css("display","none");
				$("#btn_delete").css("display","none");
				$("#btn_back").css("display","block");
				$("#btn_save").css("display","block");
			} else {
				// edit
				$("#btn_edit").css("display","none");
				$("#btn_delete").css("display","block");
				$("#btn_back").css("display","block");
				$("#btn_save").css("display","block");
			}
		} else if("view"==func){
			// 
			$("#btn_edit").css("display","block");
			$("#btn_delete").css("display","block");
			$("#btn_back").css("display","block");
			$("#btn_save").css("display","none");
		}
		
		var tempId = $("#tempId").val();
		if(tempId&&$.trim(tempId).length>0){
			$("#btn_repeat").css("display","none");
		} else {
			$("#btn_repeat").css("display","block");
		}
	}
	
	function initData(utrainingBO){
		var tempId = $("#tempId").val();
		var taskout;
		
		var playerList = utrainingBO.playerList;
		if(playerList){
			for(var i=0;i<playerList.length;i++){
				var player = playerList[i];
				$(".playerSel").append('<option value='+player.id+'>'+player.name+'</option>');
			}
		}
		if(utrainingBO&&tempId){
			var taskList = utrainingBO.taskList;
			if(taskList){
				for(var i=0;i<taskList.length;i++){
					var task = taskList[i];
					if(task.tempId==tempId){
						taskout = task;
						$("#utraining_task_title").val(task.title);
						$("#utraining_task_location").val(task.location);
						$(".typeSel").val(task.type);
						switchType();
						if(task.type=='training'){
							$("#utraining_task_desc").val(getInfoFromExt(task.extList,"key_task_ext_task_training_desc"));
							$("#utraining_task_goal").val(getInfoFromExt(task.extList,"key_task_ext_task_training_goal"));
							if(task.evaList&&task.evaList.length>0){
								$(".task_training_evaluation").css("display","block");
								for(var j=0;j<task.evaList.length;j++){
									var eva = task.evaList[j];
									if (!isInPlayerList(utrainingBO.playerList, eva.userId)) {
										continue;
									}
									var userName = getPlayerName(utrainingBO.allPlayerList,eva.userId);
									if (userName) {
										var evaluation = eva.evaluation;
										var score = eva.score;
										var evaluationID = eva.id;
										var isTraining = getInfoFromExt(eva.evaExtList,"key_task_evaluation_training_training");
										var isHurt = getInfoFromExt(eva.evaExtList,"key_task_evaluation_training_hurt");
										var isIll = getInfoFromExt(eva.evaExtList,"key_task_evaluation_training_ill");
										var isAbsent = getInfoFromExt(eva.evaExtList,"key_task_evaluation_training_absent");
										
										var innerHtml = [];
										innerHtml.push('<tr style="height:30px;" id=');
										innerHtml.push(eva.userId);
										innerHtml.push(' evaluationID=');
										if(evaluationID){
											innerHtml.push(evaluationID);
										}
										innerHtml.push('><td style="width: 15%;">');
										innerHtml.push(userName);
										innerHtml.push('</td>');
										
										if("true"==isTraining){
											innerHtml.push('<td style="width: 10%;"><span userID="');
											innerHtml.push(eva.userId);
											innerHtml.push('" onclick="switchCheck(this, ');
											innerHtml.push("'");
											innerHtml.push('utraining_evaluation_checkBox_checked');
											innerHtml.push("'");
											innerHtml.push(');" ischeck="true" class="glyphicon glyphicon-ok utraining_evaluation_checkBox_checked utraining_evaluation_cursor"></span></td>');
											
										} else {
											innerHtml.push('<td style="width: 10%;"><span userID="');
											innerHtml.push(eva.userId);
											innerHtml.push('" onclick="switchCheck(this, ');
											innerHtml.push("'");
											innerHtml.push('utraining_evaluation_checkBox_checked');
											innerHtml.push("'");
											innerHtml.push(');" ischeck="false" class="glyphicon glyphicon-ok utraining_evaluation_checkBox_unchecked utraining_evaluation_cursor"></span></td>');
										}
										if("true"==isHurt){
											innerHtml.push('<td style="width: 10%;"><span userID="');
											innerHtml.push(eva.userId);
											innerHtml.push('" onclick="switchCheck(this, ');
											innerHtml.push("'");
											innerHtml.push('utraining_evaluation_hurt_checkBox_checked');
											innerHtml.push("'");
											innerHtml.push(');" ischeck="true" class="glyphicon glyphicon-ok utraining_evaluation_hurt_checkBox_checked utraining_evaluation_cursor"></span></td>')
										} else {
											innerHtml.push('<td style="width: 10%;"><span userID="');
											innerHtml.push(eva.userId);
											innerHtml.push('" onclick="switchCheck(this, ');
											innerHtml.push("'");
											innerHtml.push('utraining_evaluation_hurt_checkBox_checked');
											innerHtml.push("'");
											innerHtml.push(');" ischeck="false" class="glyphicon glyphicon-ok utraining_evaluation_checkBox_unchecked utraining_evaluation_cursor"></span></td>')
										}
										if("true"==isIll){
											innerHtml.push('<td style="width: 10%;"><span userID="');
											innerHtml.push(eva.userId);
											innerHtml.push('" onclick="switchCheck(this, ');
											innerHtml.push("'");
											innerHtml.push('utraining_evaluation_ill_checkBox_checked');
											innerHtml.push("'");
											innerHtml.push(');" ischeck="true" class="glyphicon glyphicon-ok utraining_evaluation_ill_checkBox_checked utraining_evaluation_cursor"></span></td>');
										} else {
											innerHtml.push('<td style="width: 10%;"><span userID="');
											innerHtml.push(eva.userId);
											innerHtml.push('" onclick="switchCheck(this, ');
											innerHtml.push("'");
											innerHtml.push('utraining_evaluation_ill_checkBox_checked');
											innerHtml.push("'");
											innerHtml.push(');" ischeck="false" class="glyphicon glyphicon-ok utraining_evaluation_checkBox_unchecked utraining_evaluation_cursor"></span></td>');
										}
										if("true"==isAbsent){
											innerHtml.push('<td style="width: 10%;"><span userID="');
											innerHtml.push(eva.userId);
											innerHtml.push('" onclick="switchCheck(this, ');
											innerHtml.push("'");
											innerHtml.push('utraining_evaluation_absent_checkBox_checked');
											innerHtml.push("'");
											innerHtml.push(');" ischeck="true" class="glyphicon glyphicon-ok utraining_evaluation_absent_checkBox_checked utraining_evaluation_cursor"></span></td>');
										} else {
											innerHtml.push('<td style="width: 10%;"><span userID="');
											innerHtml.push(eva.userId);
											innerHtml.push('" onclick="switchCheck(this, ');
											innerHtml.push("'");
											innerHtml.push('utraining_evaluation_absent_checkBox_checked');
											innerHtml.push("'");
											innerHtml.push(');" ischeck="false" class="glyphicon glyphicon-ok utraining_evaluation_checkBox_unchecked utraining_evaluation_cursor"></span></td>');
										}
										
										innerHtml.push('<td style="width: 25%;"><input id="evaluation" value="');
										innerHtml.push(evaluation);
										innerHtml.push('" class="table_input"/></td><td style="width: 10%;">');
										innerHtml.push('<input id="score" name="score" value="');
										innerHtml.push(score);
										innerHtml.push('" class="table_input"/>');
										innerHtml.push('</td><td style="width: 10%;"><span class="btn" onclick="deleteTableRow(this);"><i class="glyphicon glyphicon-trash content-color"></i></span></td></tr>');
										var table = $(".task_training_evaluation").children("table");
										$(table).append(innerHtml.join(""));
									}
								}
								
							}
							
							if(task.singleTrainingList&&task.singleTrainingList.length>0){
								g_singleTrainingIDList = [];
								for (var k = 0; k < task.singleTrainingList.length; k++) {
									g_singleTrainingIDList.push(task.singleTrainingList[k].toString());
								}
								loadSingleTrainings();
								selectedTrainingItem();
							}
							
						} else if(task.type=='match'){
							$("#utraining_match_opponent").val(getInfoFromExt(task.extList,"key_task_ext_task_match_opponent"));
							$("#utraining_match_dress").val(getInfoFromExt(task.extList,"key_task_ext_task_match_dress"));
							$("#utraining_match_food").val(getInfoFromExt(task.extList,"key_task_ext_task_match_food"));
							$("#utraining_match_note").val(getInfoFromExt(task.extList,"key_task_ext_task_match_note"));
							$("#utraining_match_id").val(getInfoFromExt(task.extList,"key_task_ext_task_match_id"));
							
							if(task.evaList&&task.evaList.length>0){
								$(".task_evaluation_area").css("display","block");
								$(".task_match_evaluation").css("display","block");
								
								for(var j=0;j<task.evaList.length;j++){
									var eva = task.evaList[j];
									if (!isInPlayerList(utrainingBO.playerList, eva.userId)) {
										continue;
									}
									var userName = getPlayerName(utrainingBO.allPlayerList,eva.userId);
									if (userName) {
										var evaluation = eva.evaluation;
										var score = eva.score;
										var evaluationID = eva.id;
										var isStarting = getInfoFromExt(eva.evaExtList,"key_task_evaluation_match_starting");
										var isReplace = getInfoFromExt(eva.evaExtList,"key_task_evaluation_match_replace");
										var isAbsent = getInfoFromExt(eva.evaExtList,"key_task_evaluation_match_absent");
										
										var innerHtml = [];
										innerHtml.push('<tr style="height:30px;" id=');
										innerHtml.push(eva.userId);
										innerHtml.push(' evaluationID=');
										innerHtml.push(evaluationID);
										innerHtml.push('><td style="width: 15%;">');
										innerHtml.push(userName);
										innerHtml.push('</td>');
										
										if("true"==isStarting){
											innerHtml.push('<td style="width: 10%;"><span userID="');
											innerHtml.push(eva.userId);
											innerHtml.push('" onclick="switchCheck(this, ');
											innerHtml.push("'");
											innerHtml.push('utraining_evaluation_checkBox_checked');
											innerHtml.push("'");
											innerHtml.push(');" ischeck="true" class="glyphicon glyphicon-ok utraining_evaluation_checkBox_checked utraining_evaluation_cursor"></span></td>');
										} else {
											innerHtml.push('<td style="width: 10%;"><span userID="');
											innerHtml.push(eva.userId);
											innerHtml.push('" onclick="switchCheck(this, ');
											innerHtml.push("'");
											innerHtml.push('utraining_evaluation_checkBox_checked');
											innerHtml.push("'");
											innerHtml.push(');" ischeck="false" class="glyphicon glyphicon-ok utraining_evaluation_checkBox_unchecked utraining_evaluation_cursor"></span></td>');
										}
										
										if("true"==isReplace){
											innerHtml.push('<td style="width: 10%;"><span userID="');
											innerHtml.push(eva.userId);
											innerHtml.push('" onclick="switchCheck(this, ');
											innerHtml.push("'");
											innerHtml.push('utraining_evaluation_replace_checkBox_checked');
											innerHtml.push("'");
											innerHtml.push(');" ischeck="true" class="glyphicon glyphicon-ok utraining_evaluation_replace_checkBox_checked utraining_evaluation_cursor"></span></td>');
										} else {
											innerHtml.push('<td style="width: 10%;"><span userID="');
											innerHtml.push(eva.userId);
											innerHtml.push('" onclick="switchCheck(this, ');
											innerHtml.push("'");
											innerHtml.push('utraining_evaluation_replace_checkBox_checked');
											innerHtml.push("'");
											innerHtml.push(');" ischeck="false" class="glyphicon glyphicon-ok utraining_evaluation_checkBox_unchecked utraining_evaluation_cursor"></span></td>');
										}
										if("true"==isAbsent){
											innerHtml.push('<td style="width: 10%;"><span userID="');
											innerHtml.push(eva.userId);
											innerHtml.push('" onclick="switchCheck(this, ');
											innerHtml.push("'");
											innerHtml.push('utraining_evaluation_absent_checkBox_checked');
											innerHtml.push("'");
											innerHtml.push(');" ischeck="true" class="glyphicon glyphicon-ok utraining_evaluation_absent_checkBox_checked utraining_evaluation_cursor"></span></td>');
										} else {
											innerHtml.push('<td style="width: 10%;"><span userID="');
											innerHtml.push(eva.userId);
											innerHtml.push('" onclick="switchCheck(this, ');
											innerHtml.push("'");
											innerHtml.push('utraining_evaluation_checkBox_checked');
											innerHtml.push("'");
											innerHtml.push(');" ischeck="false" class="glyphicon glyphicon-ok utraining_evaluation_checkBox_unchecked utraining_evaluation_cursor"></span></td>');
										}
										
										innerHtml.push('<td style="width: 35%;"><input id="evaluation" value="');
										innerHtml.push(evaluation);
										innerHtml.push('" class="table_input"/></td><td style="width: 10%;">');
										innerHtml.push('<input id="score" name="score" value="');
										innerHtml.push(score);
										innerHtml.push('" class="table_input"/>');
										innerHtml.push('</td><td style="width: 10%;"><span class="btn" onclick="deleteTableRow(this);"><i class="glyphicon glyphicon-trash content-color"></i></span></td></tr>');
										var table = $(".task_match_evaluation").children("table");
										$(table).append(innerHtml.join(''));
									}
								}
							}
							
						} else if(task.type=='life'){
							$("#utraining_other_note").val(getInfoFromExt(task.extList,"key_task_ext_task_other_note"));
						} else {
							$("#utraining_other_note").val(getInfoFromExt(task.extList,"key_task_ext_task_other_note"));
						}
						
					}
				}
			}
		}
		
		return taskout;
	}
	
	function initEvent(utrainingBo,task){
		validation();
		
		// selector
		$(".typeSel").change(function(){
			switchType();
		});
		
		// date picker
		
		 dateStart = new Date(utrainingBo.startDate);
		 dateEnd = new Date(utrainingBo.endDate);
		
		var $taskDate = $('#task_date');
		var $taskTime = $('#task_time');
		
		$taskDate.datepicker({
				format : 'yyyy-mm-dd',
				language : 'zh-CN',
				autoclose : true,
				todayHighlight : true,
				toggleActive : true,
				startDate : dateStart,
				endDate : dateEnd,
				orientation:"bottom"
		});	
		
		if(task&&task.taskDate){
			var date = new Date(task.taskDate);
			date.setHours(0);
			date.setMinutes(0);
			date.setSeconds(0);
			date.setMilliseconds(0);
			$taskDate.datepicker('setDate', date);
		}
		if($("#taskDateIn").val()){
			var date = new Date($("#taskDateIn").val())
			date.setHours(0);
			date.setMinutes(0);
			date.setSeconds(0);
			date.setMilliseconds(0);
			$taskDate.datepicker('setDate', date);
		}
		
		
		$taskTime.datetimepicker({
			language: 'zh-CN',
			format : 'hh:ii',
			autoclose: 1,
			pickerPosition: "bottom-left",
			startView:1,
			maxView:1,
			titleDisable:true
		});
		
		if(task&&task.taskTime){
			$taskTime.val(task.taskTime);
		}
		
		$('#programTaskDateIcon').click(function() {
			$taskDate.datepicker('show');
		});
		
		$('#programEndTimeIcon').click(function() {
			$taskTime.datetimepicker('show');
		});
	}
	
	function showPick(obj){
		var $taskDate = $(obj).parent().find("input");
		$taskDate.datepicker({
				format : 'yyyy-mm-dd',
				language : 'zh-CN',
				autoclose : true,
				todayHighlight : true,
				toggleActive : true,
				startDate : dateStart,
				endDate : dateEnd
		});	
		$taskDate.datepicker('show');
	}
	
	function showTimePick(obj){
		var $taskTime = $(obj).parent().find("input");
		$taskTime.datetimepicker({
			language: 'zh-CN',
			format : 'hh:ii',
			autoclose: 1,
			pickerPosition: "bottom-left",
			startView:1,
			maxView:1,
			titleDisable:true
		});
		$taskTime.datetimepicker('show');
	}
	
	
	
	function switchType(){
		var type = $(".typeSel").val();
		if("training"==type){
			$(".utraining_task_detail_training").css("display","block");
			$(".utraining_task_detail_match").css("display","none");
			$(".utraining_task_detail_common").css("display","none");
			
			$(".task_evaluation_area").css("display","block");
			$(".task_training_evaluation").css("display","block");
			$(".task_match_evaluation").css("display","none");
		} else if("match"==type){
			$(".utraining_task_detail_training").css("display","none");
			$(".utraining_task_detail_match").css("display","block");
			$(".utraining_task_detail_common").css("display","none");
			
			$(".task_evaluation_area").css("display","block");
			$(".task_training_evaluation").css("display","none");
			$(".task_match_evaluation").css("display","block");
		} else {
			$(".utraining_task_detail_training").css("display","none");
			$(".utraining_task_detail_match").css("display","none");
			$(".utraining_task_detail_common").css("display","block");
			$(".task_training_evaluation").css("display","none");
			$(".task_match_evaluation").css("display","none");
			$(".task_evaluation_area").css("display","none");
		}
	}
	
	function validateScore() {
		var result = true;
		$('[name="score"]').each(function() {
			var pElem = $(this);
			if (isNaN(pElem.val()) || pElem.val() > 10 || pElem.val() < 0) {
				result = false;
				pElem.focus();
				return;
			}
		  });
		return result;
	}
	
	function taskSave(){
		
		$("#utraining_task_form").data('bootstrapValidator').validate();
		if(!$("#utraining_task_form").data('bootstrapValidator').isValid()){
			return;
		}
		
		if (!validateScore()) {
			alert("球员打分必须是0到10的数字。");
			return;
		}
		
		var utrainingBo = getBoFromPage();
		var dataTask;
		var type = $(".typeSel").val();
		
		if(!utrainingBo.taskList){
			utrainingBo.taskList = new Array();
		}
		
		var tempArray = new Array();
		var flag = true;
		
		var timeAreaItems = $("#task_date_time_area").children();
		for(var k=0;k<timeAreaItems.length;k++){
			var timeItem = timeAreaItems[k];
			var dateStr = $(timeItem).find("#task_date").val();
			var timeStr = $(timeItem).find("#task_time").val();
			if(!dateStr||!timeStr){
				alert("请选择日期和时间");
				return;
			}
			
			if("training"==type){
				var taskTitle = $("#utraining_task_title").val();
				var taskLocation = $("#utraining_task_location").val();
				var taskDate = dateStr;
				var taskTime = timeStr;
				var trainingDesc = $("#utraining_task_desc").val();
				var trainingGoal = $("#utraining_task_goal").val();
				
				var timeStr = taskTime.replace(":","");
				var date = new Date(taskDate);
				var tempId = getDateId(date)+timeStr;
				
				var singleTrainingIdArray = g_singleTrainingIDList;
				var extArray = new Array();
				extArray.push({'itemName':'key_task_ext_task_training_desc','itemValue':trainingDesc});
				extArray.push({'itemName':'key_task_ext_task_training_goal','itemValue':trainingGoal});
				
				var evaluationArray = new Array();
				var table = $(".task_training_evaluation").children("table");
		 		for(var i=1;i<$(table).find("tr").length;i++){
		 			var tr = $(table).find("tr")[i];
		 			var userId = $(tr).attr("id");
		 			var evaluationID = $(tr).attr("evaluationID");
		 			var evaluation = $(tr).find("td").eq(5).find("input").val();
		 			var score = $(tr).find("td").eq(6).find("input").val();
		 			
		 			var evalExtArray = new Array();
		 			evalExtArray.push({'itemName':'key_task_evaluation_training_training','itemValue':$(tr).find("td").eq(1).find("span").attr("ischeck")});
		 			evalExtArray.push({'itemName':'key_task_evaluation_training_hurt','itemValue':$(tr).find("td").eq(2).find("span").attr("ischeck")});
		 			evalExtArray.push({'itemName':'key_task_evaluation_training_ill','itemValue':$(tr).find("td").eq(3).find("span").attr("ischeck")});
		 			evalExtArray.push({'itemName':'key_task_evaluation_training_absent','itemValue':$(tr).find("td").eq(4).find("span").attr("ischeck")});
		 			
		 			evaluationArray.push({'id':evaluationID, 'userId':userId,'taskId':tempId,'score':score,'evaluation':evaluation,'evaExtList':evalExtArray})
		 		}
		 		
		 		
				dataTask = {
					'title' : taskTitle,
					'location' : taskLocation,
					'taskDate' : taskDate,
					'taskTime' : taskTime,
					'type' : type,
					'tempId':tempId,
					'extList':extArray,
					'evaList':evaluationArray,
					'singleTrainingList':singleTrainingIdArray
				};
			} else if("match"==type){
				var taskTitle = $("#utraining_task_title").val();
				var taskLocation = $("#utraining_task_location").val();
				var taskDate = dateStr;
				var taskTime = timeStr;
				var trainingOpponent = $("#utraining_match_opponent").val();
				var trainingDress = $("#utraining_match_dress").val();
				var trainingFood = $("#utraining_match_food").val();
				var trainingNote = $("#utraining_match_note").val();
				var matchID = $("#utraining_match_id").val();
				var timeStr = taskTime.replace(":","");
				var date = new Date(taskDate);
				var tempId = getDateId(date)+timeStr;
				
				var extArray = new Array();
				extArray.push({'itemName':'key_task_ext_task_match_opponent','itemValue':trainingOpponent});
				extArray.push({'itemName':'key_task_ext_task_match_dress','itemValue':trainingDress});
				extArray.push({'itemName':'key_task_ext_task_match_food','itemValue':trainingFood});
				extArray.push({'itemName':'key_task_ext_task_match_note','itemValue':trainingNote});
				extArray.push({'itemName':'key_task_ext_task_match_id','itemValue':matchID});
				
				
				var evaluationArray = new Array();
				var table = $(".task_match_evaluation").children("table");
		 		for(var i=1;i<$(table).find("tr").length;i++){
		 			var tr = $(table).find("tr")[i];
		 			var userId = $(tr).attr("id");
		 			var evaluation = $(tr).find("td").eq(4).find("input").val();
		 			var score = $(tr).find("td").eq(5).find("input").val();
		 			var evaluationID = $(tr).attr("evaluationID");
		 			
		 			var evalExtArray = new Array();
		 			evalExtArray.push({'itemName':'key_task_evaluation_match_starting','itemValue':$(tr).find("td").eq(1).find("span").attr("ischeck")});
		 			evalExtArray.push({'itemName':'key_task_evaluation_match_replace','itemValue':$(tr).find("td").eq(2).find("span").attr("ischeck")});
		 			evalExtArray.push({'itemName':'key_task_evaluation_match_absent','itemValue':$(tr).find("td").eq(3).find("span").attr("ischeck")});
		 			
		 			evaluationArray.push({'id':evaluationID, 'userId':userId,'taskId':tempId,'score':score,'evaluation':evaluation,'evaExtList':evalExtArray})
		 		}
				
				dataTask = {
					'title' : taskTitle,
					'location' : taskLocation,
					'taskDate' : taskDate,
					'taskTime' : taskTime,
					'type' : type,
					'tempId':tempId,
					'extList':extArray,
					'evaList':evaluationArray
				};
			} else {
				var taskTitle = $("#utraining_task_title").val();
				var taskLocation = $("#utraining_task_location").val();
				var taskDate = dateStr;
				var taskTime = timeStr;
				var trainingNote = $("#utraining_other_note").val();
				
				var timeStr = taskTime.replace(":","");
				var date = new Date(taskDate);
				var tempId = getDateId(date)+timeStr;
				
				var extArray = new Array();
				extArray.push({'itemName':'key_task_ext_task_other_note','itemValue':trainingNote});
				
				dataTask = {
					'title' : taskTitle,
					'location' : taskLocation,
					'taskDate' : taskDate,
					'taskTime' : taskTime,
					'type' : type,
					'tempId':tempId,
					'extList':extArray
				};
			}
			
			var tId = $("#tempId").val();
			
			for(var i=0;i<utrainingBo.taskList.length;i++){
				var task = utrainingBo.taskList[i];
				if(task.tempId==dataTask.tempId&&dataTask.tempId!=tId){
					var date = new Date(task.taskDate).Format("yyyy-MM-dd");
					
					var s = date + " " + task.taskTime;
					flag = false;
					bootbox.alert({  
			            message: s+" 已安排任务，请检查任务时间", 
			            title: "提示"
			       	}); 
					return;
				}
			}
			
			for(var t=0;t<tempArray.length;t++){
				var task = tempArray[t];
				if(task.tempId==dataTask.tempId&&dataTask.tempId!=tId){
					var date = new Date(task.taskDate).Format("yyyy-MM-dd");
					
					var s = date + " " + task.taskTime;
					flag = false;
					bootbox.alert({  
			            message: s+" 已安排任务，请检查任务时间", 
			            title: "提示"
			       	}); 
					return;
				}
			}
			
			if(tId&&$.trim(tId).length>0){
				for(var i =0;i<utrainingBo.taskList.length;i++){
					var task = suTrainingBO.taskList[i];
					if(task.tempId==tId){
						dataTask.id = task.id;
						suTrainingBO.taskList.splice(i,1);
						break;
					}
				}
			} 
			tempArray.push(dataTask);
		}
		
		if(flag){
			utrainingBo.taskList = utrainingBo.taskList.concat(tempArray);
		}
		
		var url = "<%=basePath%>utraining/edit_calendar?func=edit&frompage=task&taskType="+type+"&utrainingId="+$("#utraining_id").val();
		sa.ajax({
			type : "post",
			url : url,
			data : JSON.stringify(utrainingBo),
			contentType : "application/json",
			success : function(data) {
				AngularHelper.Compile($("#content"), data);
			},
			error : function(data) {
			}
		});
	}
	
	function taskBack(){
		var func = $("#func").val();		
		var url = "<%=basePath%>utraining/edit_calendar?frompage=task&func="
				+ func + "&utrainingId=" + $("#utraining_id").val();
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

	function repeat() {
		var divarea = $(".time_area").eq(0);
		var newDiv = divarea.clone();
		newDiv.children()[4].remove();

		newDiv
				.append('<div class="col-md-4"><div class="utraining_input_text"><span class="btn" onclick="deleteRow(this);"><i class="glyphicon glyphicon-trash content-color"></i></span></div></div>')
		newDiv.appendTo($("#task_date_time_area"));
	}

	function deleteRow(obj) {
		$(obj).parent().parent().parent().remove();
	}

	function switchCheck(obj, checkedStyle) {
		
		var ischeck = $(obj).attr("ischeck");
		var userID = $(obj).attr("userID");
		disableAllEvaluation(userID, checkedStyle);
		
		if ($(obj).hasClass(checkedStyle)) {
			$(obj).attr("ischeck", false);
			$(obj).removeClass(checkedStyle);
			$(obj).addClass("utraining_evaluation_checkBox_unchecked");
		} else {
			$(obj).attr("ischeck", true);
			$(obj).removeClass("utraining_evaluation_checkBox_unchecked");
			$(obj).addClass(checkedStyle);
		}
	}
	
	var g_evaluationCSS=['utraining_evaluation_checkBox_checked','utraining_evaluation_hurt_checkBox_checked', 'utraining_evaluation_replace_checkBox_checked',
	                   'utraining_evaluation_ill_checkBox_checked', 'utraining_evaluation_absent_checkBox_checked'];
	
	function disableAllEvaluation(userID, checkedStyle) {
		$('[userID="'+userID+'"]').each(function() {
			  var pElem = $(this);
			  for (var i = 0; i < g_evaluationCSS.length; i++) {
				  if (g_evaluationCSS[i] != checkedStyle) {
					  pElem.removeClass(g_evaluationCSS[i]);
					  pElem.addClass("utraining_evaluation_checkBox_unchecked");
					  pElem.attr("ischeck", false);
				  }
			  }
			  
		});
		
	}

	function deleteTableRow(obj) {
		$(obj).parent().parent().remove();
	}

	function addPlayer() {
		var type = $(".typeSel").val();
		var uid = $(".playerSel").val();
		var uname = $(".playerSel").find("option:selected").text();
		if ("training" == type) {
			var table = $(".task_training_evaluation").children("table");
			for (var i = 0; i < $(table).find("tr").length; i++) {
				var tr = $(table).find("tr")[i];
				if ($(tr).attr("id") == uid) {
					return;
				}
			}
			var innerHtml = [];
			
			
			innerHtml.push('<tr style="height:30px;" id='+uid+'><td style="width: 15%;">');
			innerHtml.push(uname);
			innerHtml.push('</td>');
			
			innerHtml.push('<td style="width: 10%;"><span userID="');
			innerHtml.push(uid);
			innerHtml.push('" onclick="switchCheck(this, ');
			innerHtml.push("'");
			innerHtml.push('utraining_evaluation_checkBox_checked');
			innerHtml.push("'");
			innerHtml.push(');" ischeck="false" class="glyphicon glyphicon-ok utraining_evaluation_checkBox_unchecked utraining_evaluation_cursor"></span></td>');
			
			innerHtml.push('<td style="width: 10%;"><span userID="');
			innerHtml.push(uid);
			innerHtml.push('" onclick="switchCheck(this, ');
			innerHtml.push("'");
			innerHtml.push('utraining_evaluation_hurt_checkBox_checked');
			innerHtml.push("'");
			innerHtml.push(');" ischeck="false" class="glyphicon glyphicon-ok utraining_evaluation_checkBox_unchecked utraining_evaluation_cursor"></span></td>');
			
			innerHtml.push('<td style="width: 10%;"><span userID="');
			innerHtml.push(uid);
			innerHtml.push('" onclick="switchCheck(this, ');
			innerHtml.push("'");
			innerHtml.push('utraining_evaluation_ill_checkBox_checked');
			innerHtml.push("'");
			innerHtml.push(');" ischeck="false" class="glyphicon glyphicon-ok utraining_evaluation_checkBox_unchecked utraining_evaluation_cursor"></span></td>');
			
			innerHtml.push('<td style="width: 10%;"><span userID="');
			innerHtml.push(uid);
			innerHtml.push('" onclick="switchCheck(this, ');
			innerHtml.push("'");
			innerHtml.push('utraining_evaluation_absent_checkBox_checked');
			innerHtml.push("'");
			innerHtml.push(');" ischeck="false" class="glyphicon glyphicon-ok utraining_evaluation_checkBox_unchecked utraining_evaluation_cursor"></span></td>');
			
			innerHtml.push('<td style="width: 25%;"><input id="evaluation" value="" class="table_input"/></td><td style="width: 10%;"><input id="score" name="score" value="" class="table_input"/></td><td style="width: 10%;"><span class="btn" onclick="deleteTableRow(this);"><i class="glyphicon glyphicon-trash content-color"></i></span></td></tr>');
			
			$(table).append(innerHtml.join(''));
			
		} else if ("match" == type) {
			var table = $(".task_match_evaluation").children("table");
			for (var i = 0; i < $(table).find("tr").length; i++) {
				var tr = $(table).find("tr")[i];
				if ($(tr).attr("id") == uid) {
					return;
				}
			}
			var innerHtml = [];
			innerHtml.push('<tr style="height:30px;" id=');
			innerHtml.push(uid);
			innerHtml.push('><td style="width: 15%;">');
			innerHtml.push(uname);
			innerHtml.push('</td>');
			
			innerHtml.push('');
			
			innerHtml.push('<td style="width: 10%;"><span userID="');
			innerHtml.push(uid);
			innerHtml.push('" onclick="switchCheck(this, ');
			innerHtml.push("'");
			innerHtml.push('utraining_evaluation_checkBox_checked');
			innerHtml.push("'");
			innerHtml.push(');" ischeck="false" class="glyphicon glyphicon-ok utraining_evaluation_checkBox_unchecked utraining_evaluation_cursor"></span></td>');
			
			innerHtml.push('<td style="width: 10%;"><span userID="');
			innerHtml.push(uid);
			innerHtml.push('" onclick="switchCheck(this, ');
			innerHtml.push("'");
			innerHtml.push('utraining_evaluation_replace_checkBox_checked');
			innerHtml.push("'");
			innerHtml.push(');" ischeck="false" class="glyphicon glyphicon-ok utraining_evaluation_checkBox_unchecked utraining_evaluation_cursor"></span></td>');
			
			innerHtml.push('<td style="width: 10%;"><span userID="');
			innerHtml.push(uid);
			innerHtml.push('" onclick="switchCheck(this, ');
			innerHtml.push("'");
			innerHtml.push('utraining_evaluation_absent_checkBox_checked');
			innerHtml.push("'");
			innerHtml.push(');" ischeck="false" class="glyphicon glyphicon-ok utraining_evaluation_checkBox_unchecked utraining_evaluation_cursor"></span></td>');
			
			innerHtml.push('<td style="width: 35%;"><input id="evaluation" value="" class="table_input"/></td><td style="width: 10%;"><input id="score" name="score" value="" class="table_input"/></td><td style="width: 10%;"><span class="btn" onclick="deleteTableRow(this);"><i class="glyphicon glyphicon-trash content-color"></i></span></td></tr>');
			$(table).append(innerHtml.join(''));
		}
	}
	
	
	function validation(){
		$("#utraining_task_form").bootstrapValidator({
	        message: '您输入的值不合法',
	        feedbackIcons: {
	            valid: 'glyphicon glyphicon-ok',
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	        	utraining_task_title:{
	        		message: '任务标题不合法',
		           	validators: {
		                    notEmpty: {
		                        message: '任务标题是必填项，请正确填写任务标题'
		                    },
		                    stringLength: {
		                         max: 30,
		                         message: '任务标题长度超过限制，请输入30字符以内的任务标题'
		                    }
		                }
	        	},
	        	utraining_task_location:{
	        		message: '任务地点不合法',
		           	 validators: {
		           		stringLength: {
		           			max: 200,
		                    message: '任务地点过长，请输入200个字符以内的任务地点'
		                  }
		             }
	        	},
	        	utraining_task_desc:{
	        		message: '任务描述不合法',
		           	validators: {
		                    stringLength: {
		                         max: 200,
		                         message: '任务描述长度超过限制，请输入200字符以内的任务描述'
		                    }
		                }
	        	},
	        	utraining_task_goal:{
	        		message: '任务目标不合法',
	        		validators: {
	                    stringLength: {
	                         max: 200,
	                         message: '任务目标长度超过限制，请输入200字符以内的任务目标'
	                    }
	                }
	        	},
	        	utraining_match_food:{
	        		message: '饮食注意不合法',
	        		validators: {
	                    stringLength: {
	                         max: 200,
	                         message: '饮食注意长度超过限制，请输入200字符以内的饮食注意'
	                    }
	                }
	        	},
	        	utraining_match_note:{
	        		message: '备注不合法',
	        		validators: {
	                    stringLength: {
	                         max: 200,
	                         message: '备注长度超过限制，请输入200字符以内的备注'
	                    }
	                }
	        	},
	        	utraining_other_note:{
	        		message: '备注不合法',
	        		validators: {
	                    stringLength: {
	                         max: 200,
	                         message: '备注长度超过限制，请输入200字符以内的备注'
	                    }
	                }
	        	},
	        	utraining_match_opponent:{
	        		message: '备注不合法',
	        		validators: {
	        			 notEmpty: {
		                        message: '比赛对手是必填项'
		                    }
	                }
	        	}
	        }
	    });
	}
	
	function pickSingleTraining(){
		loadSingleTrainings();
		$("#selectTrainingItemModal").modal({backdrop:false,show:true});
	}
	
	/**
	* load single training data
	*/
	function loadSingleTrainings() {
		$('[trainingCheckBox="true"]').each(function() {
			var pElem = $(this);
			singleTrainingID = pElem.val();
			if (g_singleTrainingIDList && g_singleTrainingIDList.length > 0) {
				if ($.inArray(singleTrainingID, g_singleTrainingIDList) >= 0) {
					pElem.attr("checked", true);
				} else {
					pElem.attr("checked", false);
				}
			} else {
				pElem.attr("checked", false);
			}
		  });
	}
	
	function selectedTrainingItem() {
		g_singleTrainingIDList = [];
		g_singleTrainingNameList = [];
		$('[trainingCheckBox="true"]').each(function() {
			  var pElem = $(this);
			  if(pElem.is(':checked')) {
				  g_singleTrainingIDList.push(pElem.val());
				  g_singleTrainingNameList.push(pElem.attr("title"));
			  }
		  });
		fillUtrainingTaskContent(g_singleTrainingNameList);
	}
	
	function fillUtrainingTaskContent(singleTrainingNameList) {
		var tempArr = [];
		if (singleTrainingNameList && singleTrainingNameList.length > 0) {
			for (var i = 0; i < singleTrainingNameList.length; i++) {
				if (i > 0) {
					tempArr.push("、");
				}
				tempArr.push(singleTrainingNameList[i]);
			}
		}
		
		$("#utraining_task_content").val(tempArr.join(""));
	}
</script>


<div class="main_container">

	<div class="utraining_top_area">
		<div class="button_area">
			<button style="float: right; margin-left: 10px;" type='button' class='btn btn-primary'
				id="btn_save" onclick='taskSave();'>保存</button>
			<button style="float: right; margin-left: 10px;" type='button' class='btn btn-default'
				id="btn_back" onclick='taskBack();'>取消</button>
			<button style="float: right; margin-left: 10px;" type='button' class='btn btn-primary'
				id="btn_edit" onclick='taskEdit();'>编辑</button>
		</div>
	</div>

<form id="utraining_task_form">
	<div class="utraining_task_detail">
		<sa-panel title="任务详情">
		
		<div class="row">

				<div class="col-md-1 " style="height: 28px; line-height: 28px">
					<label class="utraining_input_label">标题</label>
				</div>
				<div class="col-md-3">
					<div class="form-group">
						<input type="text" id="utraining_task_title" name="utraining_task_title" class="form-control"
							value="" />
					</div>
				</div>

				<div class="col-md-1 " style="height: 28px; line-height: 28px">
					<label class="utraining_input_label">地点</label>
				</div>
				<div class="col-md-3">
					<div class="form-group">
						<input type="text" id="utraining_task_location" name="utraining_task_location"
							class="form-control" value="" />
					</div>
				</div>

				<div class="col-md-1 " style="height: 28px; line-height: 28px">
					<label class="utraining_input_label">类型</label>
				</div>
				<div class="col-md-3">
					<div class="form-group utraining_input_text">
						<select class="typeSel" class="utraining_input_text" style="width: 100px;">
							<option value="training">训练</option>
							<option value="match">比赛</option>
							<option value="life">日常起居</option>
							<!-- 					<option value="others">其他</option> -->
						</select>
					</div>
				</div>
		</div>

		<div class="row" style="margin-top: 10px;" id="task_date_time_area">
			<div class="time_area" id="0">

					<div class="col-md-1 " style="height: 28px; line-height: 28px">
						<label class="utraining_input_label">日期</label>
					</div>
					<div class="col-md-3">
						<div class="form-group input-group date utraining_input_text">
							<input type="text" class="form-control calendar-input" id="task_date" name="task_date"
								onclick="showPick(this);" readonly>
							<span id="programTaskDateIcon" class="input-group-addon calendar-icon" onclick="showPick(this);">
								<i class="glyphicon glyphicon-calendar major_color"></i>
							</span>
						</div>
					</div>
					<div class="col-md-1 " style="height: 28px; line-height: 28px">
						<label class="utraining_input_label">时间</label>
					</div>
					<div class="col-md-3">
						<div class="form-group input-group date  utraining_input_text">
							<input type="text" class="form-control calendar-input" id="task_time" name="task_time"
								onclick="showTimePick(this);" readonly>
							<span id="programEndTimeIcon" class="input-group-addon calendar-icon" onclick="showTimePick(this);">
								<i class="glyphicon glyphicon-calendar major_color"></i>
							</span>
						</div>
					</div>
				<div class="col-md-4">
					<div class="utraining_input_text">
						<button style="float: left;" type='button' class='btn btn-primary' id="btn_repeat"
							onclick='repeat();'>重复安排</button>
					</div>
				</div>
			</div>
		</div>

		<div class="utraining_task_detail_training">
			<div class="row" style="margin-top: 10px;">

					<div class="col-md-1 " style="height: 28px; line-height: 28px">
						<label class="utraining_input_label">训练内容</label>
					</div>
					<div class="col-md-3">
						<div class="form-group">
							<input readonly="readonly" type="text" id="utraining_task_content" name="utraining_task_content"
								class="form-control" value="" />
						</div>
					</div>

					<div class="col-md-4 " style="height: 28px; line-height: 28px">
						<button style="float: left;" type='button' class='btn btn-primary' id="btn_repeat"
							onclick='pickSingleTraining();'>选择训练</button>
					</div>
		</div>

		<div class="row" style="margin-top: 10px;">
				<div class="col-md-1 " style="height: 28px; line-height: 28px">
					<label class="utraining_input_label">任务描述</label>
				</div>
				<div class="col-md-10">
					<div class="form-group input-group utraining_input_text">
						<textarea id="utraining_task_desc" name="utraining_task_desc"
							class="form-control utraining_input_text" rows="3" style="line-height: 20px;"></textarea>
					</div>
				</div>
		</div>

		<div class="row" style="margin-top: 10px;">
				<div class="col-md-1 " style="height: 28px; line-height: 28px">
					<label class="utraining_input_label">任务目标</label>
				</div>
				<div class="col-md-10">
					<div class="form-group input-group utraining_input_text">
						<textarea id="utraining_task_goal" name="utraining_task_goal"
							class="form-control utraining_input_text" rows="3" style="line-height: 20px;"></textarea>
					</div>
				</div>
		</div>
	</div>
	<div class="utraining_task_detail_match" style="display: none;">
		<div class="row" style="margin-top: 10px;">

				<div class="col-md-1 " style="height: 28px; line-height: 28px">
					<label class="utraining_input_label">对手</label>
				</div>
				<div class="col-md-3">
					<div class="form-group">
						<input type="text" id="utraining_match_opponent" name="utraining_match_opponent"
							class="form-control" value="" />
					</div>
				</div>
				<div class="col-md-1 " style="height: 28px; line-height: 28px">
					<label class="utraining_input_label">着装</label>
				</div>
				<div class="col-md-3">
					<div class="form-group">
						<input type="text" id="utraining_match_dress" name="utraining_match_dress"
							class="form-control" value="" />
					</div>
				</div>
		</div>

		<div class="row" style="margin-top: 10px;">
				<div class="col-md-1 " style="height: 28px; line-height: 28px">
					<label class="utraining_input_label">饮食注意</label>
				</div>
				<div class="col-md-10">
					<div class="form-group input-group utraining_input_text">
						<textarea id="utraining_match_food" name="utraining_match_food"
							class="form-control utraining_input_text" rows="3" style="line-height: 20px;"></textarea>
					</div>
				</div>
		</div>

		<div class="row" style="margin-top: 10px;">
				<div class="col-md-1 " style="height: 28px; line-height: 28px">
					<label class="utraining_input_label">备注</label>
				</div>
				<div class="col-md-10">
					<div class="form-group input-group utraining_input_text">
						<textarea id="utraining_match_note" name="utraining_match_note"
							class="form-control utraining_input_text" rows="3" style="line-height: 20px;"></textarea>
					</div>
				</div>
		</div>
	</div>
	<div class="utraining_task_detail_common" style="display: none;">
		<div class="row" style="margin-top: 10px;">
				<div class="col-md-1 " style="height: 28px; line-height: 28px">
					<label class="utraining_input_label">备注</label>
				</div>
				<div class="col-md-10">
					<div class="form-group input-group utraining_input_text">
						<textarea id="utraining_other_note" name="utraining_other_note"
							class="form-control utraining_input_text" rows="3" style="line-height: 20px;"></textarea>
					</div>
				</div>
		</div>
	</div>
	</sa-panel>
</div>

<div class="task_evaluation_area">
	<sa-panel title="球员评估">
	
	<div class="row" style="margin-top: 10px;">
			<div class="col-md-12 player_selector_area">
				<div class="col-md-1 " style="height: 28px; line-height: 28px">
				<label class="utraining_input_label">球员</label>
				</div>
				<div class="col-md-2 ">
					<select class="playerSel" style="float: left; width: 180px;"></select>
				</div>
				<div class="col-md-2 ">
				<button style="float: left; margin-left: 20px;" type='button' class='btn btn-primary'
					id="btn_add_player" onclick='addPlayer();'>添加</button>
					</div>
			</div>
			
		</div>
	
	<div class="task_training_evaluation">
		<table border="1" class="utraining_task_table table-striped" style="margin-top:10px">
			<tr style="background-color:#E2F4FF; height: 30px;">
				<td style="width: 15%;">姓名</td>
				<td style="width: 10%;">训练</td>
				<td style="width: 10%;">受伤</td>
				<td style="width: 10%;">生病</td>
				<td style="width: 10%;">缺席</td>
				<td style="width: 25%;">评估</td>
				<td style="width: 10%;">打分( 0 ~ 10 )</td>
				<td style="width: 10%;"></td>
			</tr>
		</table>
	</div>
	<div class="task_match_evaluation detailTable" style="display: none; margin-top:10px">
		<table border="1" class="utraining_task_table table-striped">
			<tr style="background-color:#E2F4FF; height: 30px;">
				<td style="width: 15%;">姓名</td>
				<td style="width: 10%;">首发</td>
				<td style="width: 10%;">替补</td>
				<td style="width: 10%;">没上场</td>
				<td style="width: 35%;">评估</td>
				<td style="width: 10%;">打分( 0 ~ 10 )</td>
				<td style="width: 10%;"></td>
			</tr>
		</table>
	</div>
	</sa-panel>
</div>
</form>
</div>


<input type="hidden" id="utrainingTaskId" value="${utrainingTaskId}" />
<input type="hidden" id="func" value="${func}" />
<input type="hidden" id="utrainingBO" value="${utrainingBO}" />
<input type="hidden" id="tempId" value="${tempId}" />
<input type="hidden" id="utraining_id" value="${utrainingId}" />
<input type="hidden" id="taskDateIn" value="${taskDate}" />
<input type="hidden" id="utraining_match_id"/>

<div class="modal fade" id="selectTrainingItemModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" style="z-index: 100001; display: none;">
	<div class="modal-dialog" role="document" style="width: 650px;">
		<div class="modal-content" style="border-radius: 3px;">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">训练内容</h4>
			</div>
			<div class="modal-body" style="background-color: #f8f8f8;">
				<table id="singleTrainingModal" class="table table-bordered" style="background-color: #fff;margin-bottom: 0px;">
		        <thead>
		          <tr>
		            <th>选择</th>
		            <th>名称</th>
		            <th>场地尺寸(m*m)</th>
		            <th>用时(min)</th>
		            <th>针对球员</th>
		          </tr>
		        </thead>
		        <tbody id="singleTrainingContainer">
			        <c:forEach items="${singleTraininglist}" var="singleTraining">
			        	<tr>
			        		<td>
			        			<label>
			        				<input type="checkbox" trainingCheckBox="true" title='<xss:xssFilter text="${singleTraining.name}" filter="html"/>' value='<xss:xssFilter text="${singleTraining.id}" filter="html"/>'>
			        			</label>
			        		</td>
			        		<td>
			        			<xss:xssFilter text="${singleTraining.name}" filter="html"/>
			        		</td>
			        		<td>
			        			<xss:xssFilter text="${singleTraining.yard_width}" filter="html"/>*<xss:xssFilter text="${singleTraining.yard_long}" filter="html"/>
			        		</td>
			        		<td>
			        			<xss:xssFilter text="${singleTraining.singleTrainingExtInfoMap['time_sum']}" filter="html"/>
			        		</td>
			        		<td>
			        			<xss:xssFilter text="${singleTraining.translatedTarget}" filter="html"/>
			        		</td>
			        	</tr>
					</c:forEach>				
		        </tbody>
		      </table>
      		</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
				<button type="button" class="btn btn-primary" data-dismiss="modal" onClick="selectedTrainingItem();">确定</button>
			</div>
		</div>
	</div>
</div>
