<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="xss" uri="http://www.sportsdata.cn/xss"%>

<%@ page import="cn.sportsdata.webapp.youth.common.utils.CommonUtils" %>
<%
	String serverUrl = CommonUtils.getServerUrl(request);
%>
<%@include file="/WEB-INF/jsp/common/include.jsp"%>
<html>
<head>
<style type="text/css">

.trainingUserProfileName {
	float:left;
	font-size: 14px;
	max-width:45px;
	overflow: hidden;
	text-overflow: ellipsis;
	white-space: nowrap;
}

.trainingUserProfileAvatar{
	float:left;
    width: 50px;
    height: 50px;
    margin: 3px;
    -moz-border-radius: 50px;
    -webkit-border-radius: 50px;
    border-radius: 50px;
    border: 1px solid #cecece;
}

</style>
<script type="text/javascript">

String.prototype.replaceAll = function(s1,s2){ 
	return this.replace(new RegExp(s1,"gm"),s2); 
	}

	var suTrainingBO = null;
	$(function() {
		
		setTimeout(function(){
			
			initData();
			switchFunc();
			
		},50)
	});
	
	
	function switchFunc(){
		$(".utraining_title").css("display","block");
		$(".utraining_detail_goal").css("display","block");
		$("#btn_add_task").css("display","none");
		drawPlayerCard();
	}
	

	function initData(){
		var jsonStr = '<xss:xssFilter text="${boJson}" filter="js" />';
		jsonStr = jsonStr.replaceAll("\n", "\\n");  
		jsonStr = jsonStr.replaceAll("\r", "\\r"); 
		var jsonObj = JSON.parse(jsonStr);
		suTrainingBO = jsonObj;
		
		if(!suTrainingBO){
			return;
		}
		

		if(suTrainingBO.goal){
			$("#goal").val(suTrainingBO.goal);
			$("#goal_view").html(suTrainingBO.goal.replaceAll("<", "&lt;"));
		}
		if(suTrainingBO.evaluation){
			$("#evaluation").val(suTrainingBO.evaluation);
			$("#evaluation_view").html(suTrainingBO.evaluation.replaceAll("<", "&lt;"));
		}
		if(suTrainingBO.name){
			$("#div_t_name").html(suTrainingBO.name.replaceAll("<", "&lt;"));
		}
		if(suTrainingBO.location){
			$("#div_t_location").html(suTrainingBO.location.replaceAll("<", "&lt;"));
		}
		if(suTrainingBO.startDate&&suTrainingBO.endDate){
			var startDate = new Date(suTrainingBO.startDate).Format("yyyy-MM-dd");
			var endDate = new Date(suTrainingBO.endDate).Format("yyyy-MM-dd");
			$("#div_t_date_range").html(startDate.substr(0,10) +" ~ "+endDate.substr(0,10));
			addTaskLine(startDate,endDate);
		}
		
		
		drawPlayerCard();
	}
	
	function drawPlayerCard(){
		$(".utraining_players").empty();
		if(suTrainingBO.forwardList){
			makeGroupPlayerCard(suTrainingBO.forwardList,"前锋：");
		}
		if(suTrainingBO.centerList){
			makeGroupPlayerCard(suTrainingBO.centerList,"中场：");
		}
		if(suTrainingBO.defenderList){
			makeGroupPlayerCard(suTrainingBO.defenderList,"后卫：");
		}
		if(suTrainingBO.goalKeeperList){
			makeGroupPlayerCard(suTrainingBO.goalKeeperList,"门将：");
		}
		checkEmpty();
	}
	
	function checkEmpty(){
		for(var k=0;k<$(".utraining_players").children("ul").length;k++){
			var ul = $(".utraining_players").children("ul")[k];
			if($(ul).find("li").length<=0){
				$(ul).empty();
			}
		}
	}
	
	function userInSelectList(user,list){
		if(list){
			for(var u = 0;u<list.length;u++){
				if(user.id==list[u]){
					return true;
				}
			}
		}
		return false;
	}
	
	function makeGroupPlayerCard(list,title){
		
		$(".utraining_players").append("<ul style='clear:both;display:block;'><span style='clear:both;display:block;'>"+title+"</span></ul>");
		if(list){
			for(var p=0;p<list.length;p++){
				var user = list[p];
				if(userInSelectList(user,suTrainingBO.trainingPlayIdList)){
					$(makeOnePlayerCard(user)).appendTo($(".utraining_players").children("ul:last-child"));
				}
			}
		}
	}
	
	function makeOnePlayerCard(player){
		var imgUrl = "";
		var checkCtl = '';
		if(player.avatar){
			imgUrl = "<%=serverUrl%>file/downloadFile?fileName="+player.avatar;
		} else {
			imgUrl = "<%=serverUrl%>resources/images/user_avatar.png";	
		}
		
		if(userInSelectList(player,suTrainingBO.trainingPlayIdList)){
			checkCtl = 'ischeck="true" class="utraining_profileCard_checked_view"';
		} else {
			checkCtl = 'ischeck="false" class="utraining_profileCard"';
		}
		
		var html = '<li uid='+player.id+' email="'+player.email+'" '+checkCtl+'><div style="float:right;width:30px;padding-right:5px;"><span class="glyphicon glyphicon-ok utraining_profileCard_checkBox"></span></div><img class="trainingUserProfileAvatar" src='+imgUrl+'></img><div class="trainingUserProfileName">'+player.name+'</div></li>';
		return html;
	}
	
	function makeOneTask(time,title,type,tempId,taskId){
		title = title.replaceAll("<", "&lt;"); 
		var desc = "";
		if(type=='training'){
			desc = time+"         训练  "+title;
		} else if(type=='match') {
			desc = time+"         比赛  "+title;
		} else if(type='life'){
			desc = time+"         日常  "+title;
		} else {
			desc = time+"         其他  "+title;
		}
		var html = '<div class="utraining_task_item_view">'+desc+'</div> ';
		return html;
	}
	
	
	
	function hasDateError($dom, msg) {
		var curStartDate = new Date($('#start_date').datepicker('getDate'));
		var curEndDate = new Date($('#end_date').datepicker('getDate'));
		
		$('#errorMsg').text('');
		
		if(curStartDate.getTime() >= curEndDate.getTime()) {
			$('#errorMsg').html(msg);
			return true;
		}
		
		return false;
	}
	
	
	function addTaskLine(startDate,endDate) {
		
			var curStartDate,curEndDate;
			if(startDate&&endDate){
				 curStartDate = new Date(startDate);
				 curEndDate = new Date(endDate);
			} else {
				 curStartDate = new Date($('#start_date').val());
				 curEndDate = new Date($('#end_date').val());
			}
			
			if(!curStartDate||!curEndDate){
				return;
			}
			$(".utraining_task_table").empty();

			var newTaskList = new Array();
			
			for (var i = curStartDate.getTime(); i <= curEndDate.getTime(); i = add(
					i, 24 * 60 * 60 * 1000)) {
				var date = new Date();
				date.setTime(i);
				var dayOfWeekStr = "";
				var dayOfWeek = date.getDay();
				switch (dayOfWeek) {
				case 0:
					dayOfWeekStr = "周日";
					break;
				case 1:
					dayOfWeekStr = "周一";
					break;
				case 2:
					dayOfWeekStr = "周二";
					break;
				case 3:
					dayOfWeekStr = "周三";
					break;
				case 4:
					dayOfWeekStr = "周四";
					break;
				case 5:
					dayOfWeekStr = "周五";
					break;
				case 6:
					dayOfWeekStr = "周六";
					break;
				default:
					break;
				}
				var month = String(add(date.getMonth(), 1));
				var day = String(date.getDate());
				var monthStr = month.length == 1 ? "0" + month : month;
				var dayStr = day.length == 1 ? "0" + day : day;
				var dateStr = monthStr + "/" + dayStr;
				var dateId = date.getFullYear()+""+monthStr+""+dayStr;
				
				if(suTrainingBO&&suTrainingBO.taskList){
					var oldTask = containTask(suTrainingBO.taskList,dateId);
					if(oldTask){
						newTaskList = newTaskList.concat(oldTask);
					}
				}
				
				
				$(".utraining_task_table").append(
						'<tr id='+dateId+'><td style="position: relative;"><div><span class="sp_task_inline_1">'+dateStr+'</span><span class="sp_task_inline_2">'+dayOfWeekStr+'</span></div></td><td></td><td></td><td></td></tr>'		
					);
			}
			
			if(suTrainingBO&&suTrainingBO.taskList){
				suTrainingBO.taskList = newTaskList;
				addTaskItem(suTrainingBO.taskList);
			}
	}
	
	function addTaskItem(list){
		if(list){
			for(var j=0;j<list.length;j++){
				var task = list[j];
				var date = new Date(task.taskDate);
				var time = task.taskTime;
				var title = task.title;
				var type = task.type;
				var tempId = task.tempId;
				var taskId = task.id;
				var hour = time.split(":")[0];
			
				var dateId = getDateId(date);
				if(!tempId){
					var timeStr = time.replace(":","");
					tempId = dateId+timeStr;
				}
				var tdIndex = -1;
				if(hour<12){
					tdIndex=1;
				} else if(hour<18) {
					tdIndex=2;
				} else {
					tdIndex=3;
				}
			
				$("#"+dateId).children("td").eq(tdIndex).append(makeOneTask(time,title,type,tempId,taskId));
			}
			
		}
	}
	
	function containTask(list,dateId){
		var tempArray = new Array();
		if(list&&list.length>0){
			for(var k=0;k<list.length;k++){
				var tk = list[k];
				if(tk.tempId){
					var dId = tk.tempId.substring(0,8);
					if(dateId==dId){
						tempArray.push(tk);
					}
				}
			}
			return tempArray;
		}
		return null;
	}
	
	
	function showChangeDateModal(){
		$("#changeDateModal").modal({backdrop:false,show:true});
	}
	function showShareModal(){
		alert("share");
	}
</script>
<title><xss:xssFilter text="${title}" filter="html"/></title>
</head>
<body>
<div class="main_container">

	<div class="utraining_title">
		<div class="row">
			<div id="div_t_name" class="col-md-2 utraining_title_name"></div>
			<div id="div_t_date_range" class="col-md-3" style="padding-top: 4px;"></div>
			<div id="div_t_location" class="col-md-3" style="padding-top: 4px;"></div>
		</div>
	</div>
	<div class="utraining_detail_goal">
		<div class="panel panel-default"> 
		<div class="panel-heading text-left" style="background-color:#067DC2;color:white">
			目标与评估
		</div>
		<div class="panel-body table-responsive">
			<div class="row" style="margin-top: 10px;">
					<div class="col-md-1 " style="height:28px;line-height:28px">
						<label class="utraining_input_label">目标</label>
					</div>
					<div class="col-md-10">
						<span id="goal_view" class="utraining_input_text" style=" height: auto;"></span>
	        		</div>
			</div>	
			<div class="row" style="margin-top: 10px;">
					<div class="col-md-1 " style="height:28px;line-height:28px">
						<label class="utraining_input_label">评估</label>
					</div>
					<div class="col-md-10">
						<span id="evaluation_view" class="utraining_input_text" style="height: auto;"></span>
	        		</div>
			</div>
		</div>
		</div>
	</div>
	<div class="mission_panel">
		<div class="utraining_container panel panel-default">
			<!-- 		 task head -->
			<div class="header panel-heading text-center"
				style="background-color: #067DC2; color: white; height: 41px; line-height: 41px; padding-bottom: 0px; padding-top: 0px;">
				<div class="utraining_task_col_p10 btn_add_task">
				</div>
				<div class="utraining_task_col_p30 task_title_morning">上午</div>
				<div class="utraining_task_col_p30 task_title_afternoon">下午</div>
				<div class="utraining_task_col_p30 task_title_night">晚上</div>
			</div>
			<!-- 		task body -->

			<table border="1" style="font-size: 14px;" class="utraining_task_table">
			</table>
		</div>
	</div>
	<div class="include_player">
		<div class="panel panel-default"> 
		<div class="panel-heading text-left" style="background-color:#067DC2;color:white">
			参加球员
		</div>
		<div class="panel-body table-responsive">
			<div class="utraining_players"></div>
		</div>
		</div>
	</div>
</div>

<input type="hidden" id="utraining_id" value="${utrainingId}" />
</body>
</html>
