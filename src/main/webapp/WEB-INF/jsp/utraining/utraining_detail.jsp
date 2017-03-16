<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="xss" uri="http://www.sportsdata.cn/xss"%>

<%@ page import="cn.sportsdata.webapp.youth.common.utils.CommonUtils" %>
<%
	String serverUrl = CommonUtils.getServerUrl(request);
%>

<c:if test="${reqSource=='share'}">
<%@include file="/WEB-INF/jsp/common/include.jsp"%>
</c:if>

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

.utraining_task_table tr:hover{
	background-color:#D9EDF7;
}
.utraining_task_table tr:hover .utraining_task_item{
	color:#067dc2;
}

</style>
<script type="text/javascript">

String.prototype.replaceAll = function(s1,s2){ 
	return this.replace(new RegExp(s1,"gm"),s2); 
	}

	var suTrainingBO = null;
	$(function() {
		
		setTimeout(function(){
			
			if($("#reqSource").val()!="share"){
				buildBreadcumb("计划详情");
				validation();
			} else {
				$(".utraining_top_area").empty();
			}
			
			
			if ($("body").find("#hiddenUtrainingBO").length == 0) {
				$("body").append("<div id='hiddenUtrainingBO'></div>");
			}

			initData();
			$("#colorSel").select2({
				minimumResultsForSearch : Infinity
			});
			initEvent();
			
			switchFunc();
			
		},50)
	});
	
	
	function switchFunc(){
		var func = $("#func").val();
		if("edit"==func){
			// is add or not
			var utrainingId = $("#utrainingId").val();
			if(!utrainingId || utrainingId<0){
				// add 
				$("#shareButton").css("display","none");
				$("#btn_edit").css("display","none");
				$("#btn_delete").css("display","none");
				$("#btn_back").css("display","block");
				$("#btn_save").css("display","block");
			} else {
				// edit
				$("#shareButton").css("display","none");
				$("#btn_edit").css("display","none");
				$("#btn_delete").css("display","block");
				$("#btn_back").css("display","block");
				$("#btn_save").css("display","block");
			}
			$(".utraining_title").css("display","none");
			$(".utraining_detail_goal").css("display","none");
			$(".utraining_base_info").css("display","block");
			
			$("#btn_add_task").css("display","block");
			$("#btn_back").html("取消");
			showPlusIcon(true);
		} else if("view"==func){
			$(".utraining_title").css("display","block");
			$(".utraining_detail_goal").css("display","block");
			$(".utraining_base_info").css('display','none');
			// 
			$("#shareButton").css("display","block");
			$("#btn_edit").css("display","block");
			$("#btn_delete").css("display","block");
			$("#btn_back").css("display","block");
			$("#btn_back").html("返回");
			$("#btn_save").css("display","none");
			
			$("#btn_add_task").css("display","none");
			showPlusIcon(false);
		}
		drawPlayerCard();
	}
	
	function showPlusIcon(isShow) {
		$('[plusFlag="true"]').each(function() {
			var pElem = $(this);
			if (isShow) {
				pElem.css("display","block");
			} else {
				pElem.css("display","none");
			}
			
		});
	}
	
	function loadPopOver(imageSrc) {
		  var pElem = $("#shareButton");
		  var popoverObj = pElem.popover({
		    html: true,
		    trigger: "hover",
		    content: createPopOverElement(imageSrc),
		    container: 'body',
		    template:'<div class="popover" style="max-width:152px; width:152px" role="tooltip"><div class="arrow"></div><h3 class="popover-title"></h3><div class="popover-content" ></div></div>'
		  });
	}
	
	function createPopOverElement(imageSrc) {
		var tempArr = [];
		tempArr.push("<img src='");
		tempArr.push(imageSrc);
		tempArr.push("'  alt='分享到微信' />");
		return tempArr.join("");
	}
	
	function initData(){
		if(getBoFromPage()){
			suTrainingBO = getBoFromPage();
		} else {
			var jsonStr = '<xss:xssFilter text="${boJson}" filter="js" />';
			var jsonObj = JSON.parse(jsonStr);
			suTrainingBO = jsonObj;
		}
		
		if(!suTrainingBO){
			return;
		}
		
		loadPopOver(suTrainingBO.barCodeLink);

		if(suTrainingBO.goal){
			$("#goal").val(suTrainingBO.goal);
			$("#goal_view").html(suTrainingBO.goal.replaceAll("<", "&lt;"));
		}
		if(suTrainingBO.evaluation){
			$("#evaluation").val(suTrainingBO.evaluation);
			$("#evaluation_view").html(suTrainingBO.evaluation.replaceAll("<", "&lt;"));
		}
		if(suTrainingBO.color){
			$("#colorSel").val(suTrainingBO.color);
		}
		if(suTrainingBO.name){
			$("#div_t_name").attr("title", suTrainingBO.name);
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
				if($("#func").val()=="view"){
					if(userInSelectList(user,suTrainingBO.trainingPlayIdList)){
						$(makeOnePlayerCard(user, true)).appendTo($(".utraining_players").children("ul:last-child"));
					}
				} else {
					$(makeOnePlayerCard(user, false)).appendTo($(".utraining_players").children("ul:last-child"));
				}
			}
		}
	}
	
	function makeOnePlayerCard(player, isView){
		var imgUrl = "";
		var checkCtl = '';
		if(player.avatar){
			imgUrl = "<%=serverUrl%>file/downloadFile?fileName="+player.avatar;
		} else {
			imgUrl = "<%=serverUrl%>resources/images/user_avatar.png";	
		}
		if($("#utraining_id").val()<=0&&suTrainingBO.trainingPlayIdList==null){
			// add
			checkCtl = 'ischeck="true" class="utraining_profileCard_checked"';
		} else {
			if(userInSelectList(player,suTrainingBO.trainingPlayIdList)){
				checkCtl = 'ischeck="true" class="utraining_profileCard_checked"';
				if (isView) {
					checkCtl = 'ischeck="true" class="utraining_profileCard_checked_view"';
				}
			} else {
				checkCtl = 'ischeck="false" class="utraining_profileCard"';
			}
		}
		var innerHtml = [];
		innerHtml.push('<li uid=');
		innerHtml.push(player.id);
		innerHtml.push(' email="');
		innerHtml.push(player.email);
		innerHtml.push('" onclick="switchCheck(this);" ');
		innerHtml.push(checkCtl);
		innerHtml.push('> <div style="float:right;width:30px;padding-right:5px;"><span class="glyphicon glyphicon-ok utraining_profileCard_checkBox"></span></div><img class="trainingUserProfileAvatar" src=');
		innerHtml.push(imgUrl);
		innerHtml.push('></img><div class="trainingUserProfileName">');
		innerHtml.push(player.name);
		innerHtml.push('</div></li>');
		return innerHtml.join('');
	}
	
	
	
	function makeOneUserCard(player){
		var imgUrl = "";
		if(player.avatar){
			imgUrl = "<%=serverUrl%>file/downloadFile?fileName="+player.avatar;
		} else {
			imgUrl = "<%=serverUrl%>resources/images/user_avatar.png";	
		}
		var html = '<li uid='+player.id+' email="'+player.email+'" onclick="switchShareCheck(this);" ischeck="true" class="utraining_profileCard_checked"><div style="float:right;width:30px;"><span class="glyphicon glyphicon-ok utraining_profileCard_checkBox"></span></div><img class="trainingUserProfileAvatar" src='+imgUrl+'></img><div class="trainingUserProfileName">'+player.name+'</div></li>';
		return html;
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
		var html = '<div class="utraining_task_item" onmouseleave="displayBtn(this,false);"><span onmouseover="displayBtn(this,true);" onclick="viewTask(this);" style="float: left;" taskId='+taskId+' tempId='+tempId+'> '+desc+'  </span><span onclick="showDeleteTaskModal(this);"> <i class="glyphicon glyphicon-minus" style="display:none;margin-left:5px;float:left;color:#bbbbbb"></i> </span> </div> ';
		return html;
	}
	
	
	function initEvent(){
		
		var $startDate = $('#start_date');
		var $endDate = $('#end_date');
		
		$startDate.datepicker({
				format : 'yyyy-mm-dd',
				language : 'zh-CN',
				autoclose : true,
				todayHighlight : true,
				toggleActive : true,
				orientation:"bottom"
		});	
		if(suTrainingBO&&suTrainingBO.startDate){
			$startDate.datepicker('setDate', new Date(suTrainingBO.startDate));
		}
		
		$startDate.datepicker().on('changeDate', function(evt) {
			if(!hasDateError($('#errorMsg'), '开始时间不能大于结束时间')){
				if($(".utraining_task_table").find("tr").length>0){
					showChangeDateModal();
				}else {
					addTaskLine();
				}
			}
		});
		
		$endDate.datepicker({
			format : 'yyyy-mm-dd',
			language : 'zh-CN',
			autoclose : true,
			todayHighlight : true,
			toggleActive : true,
			orientation:"bottom"
		});
		if(suTrainingBO&&suTrainingBO.endDate){
			$endDate.datepicker('setDate', new Date(suTrainingBO.endDate));
		}
		
		$endDate.datepicker().on('changeDate', function(evt) {
			if(!hasDateError($('#errorMsg'), '开始时间不能大于结束时间')){
				if($(".utraining_task_table").find("tr").length>0){
					showChangeDateModal();
				}else {
					addTaskLine();
				}
			}
		});
		
		$('#programStartTimeIcon').click(function() {
			$startDate.datepicker('show');
		});
		
		$('#programEndTimeIcon').click(function() {
			$endDate.datepicker('show');
		});
	}
	
	function hasDateError($dom, msg) {
		var curStartDate = new Date($('#start_date').datepicker('getDate'));
		if ($('#end_date').datepicker('getDate')) {
			var curEndDate = new Date($('#end_date').datepicker('getDate'));
			
			$('#errorMsg').text('');
			if(curStartDate.getTime() > curEndDate.getTime()) {
				$('#errorMsg').html(msg);
				return true;
			}
		}
		return false;
	}
	
	function buildBoData(){
		var utraining_name = $("#utraining_name").val();
		var start_date = $("#start_date").val();
		var end_date = $("#end_date").val();
		var color = $("#colorSel").val();
		var location = $("#utraining_location").val();
		var goal = $("#goal").val();
		var evaluation = $("#evaluation").val();
		var playerIdArray = new Array();
		var playerArray = new Array();
		
		var ulSize = $(".utraining_players ul").length;
		for(var i = 0; i<ulSize;i++){
			var liSize = $($(".utraining_players ul")[i]).children("li").length;
			for(var k=0;k<liSize;k++){
				var li = $($(".utraining_players ul")[i]).children("li")[k];
				var ischeck = $(li).attr("ischeck");
				if (ischeck == "true") {
					playerIdArray.push($(li).attr("uid"));
					playerArray.push({id:$(li).attr("uid"),name:$(li).find(".trainingUserProfileName").html()})
				}
			}
		}
		
		suTrainingBO.name = utraining_name;
		suTrainingBO.id=$("#utraining_id").val();
		suTrainingBO.startDate=start_date;
		suTrainingBO.endDate=end_date;
		suTrainingBO.location=location;
		suTrainingBO.color=color;
		suTrainingBO.goal=goal;
		suTrainingBO.evaluation=evaluation;
		suTrainingBO.trainingPlayIdList=playerIdArray;
		suTrainingBO.playerList=playerArray;
		
	}
	
	function save(){
		
		$("#utraining_form").data('bootstrapValidator').validate();
		if(!$("#utraining_form").data('bootstrapValidator').isValid()){
			return;
		}
		
		if(hasDateError($('#errorMsg'), '开始时间不能大于结束时间')){
			return;
		}
		
		buildBoData();

		sa.ajax({
			type : "post",
			url : "<%=serverUrl%>utraining/save_calender",
			data : JSON.stringify(suTrainingBO),
			contentType : "application/json",
			success : function(data) {
				if (data.status) {
					loadPage("<%=serverUrl%>utraining/edit_calendar?utrainingId="+data.result+"&func=view");
				} else {
					if(data.errorCode==501){
						alert("保存失败,日期与已有计划冲突");
					}else if(data.errorCode==510){
						alert("保存失败,请确认日期是否正确填写");
					}else {
						alert("保存失败");
					}
				}
			},
			error : function(data) {
				alert("保存失败");
			}
		});
	}

	function back() {
		var func = $("#func").val();
		if("edit"==func){
			if($("#utraining_id").val()>0){
				var id = $("#utraining_id").val();
				removeBoFromPage();
				loadPage("<%=serverUrl%>utraining/edit_calendar?utrainingId="+id+"&func=view&frompage=calendar");
			} else {
				loadPage("<%=serverUrl%>utraining/show_calendar");
			}
		} else {
			loadPage("<%=serverUrl%>utraining/show_calendar");
		}
	}

	function training_delete() {
		
		sa.ajax({
			type : "post",
			url : "<%=serverUrl%>utraining/delete_calender",
			data : {
				utrainingId : $("#utraining_id").val()
			},
			dataType : "json",
			success : function(data) {
				if (data.status) {
					loadPage("<%=serverUrl%>utraining/show_calendar");
				} else {
					alert("删除失败");
				}
			},
			error : function(data) {
				alert("删除失败");
			}
		});
	}
	function edit() {
		$("#func").val("edit");
		switchFunc();
	}

	function switchCheck(obj) {

		var func = $("#func").val();
		if (func == 'view') {
			return;
		}

		var ischeck = $(obj).attr("ischeck");
		if (ischeck == "true") {
			$(obj).attr("ischeck", false);
			$(obj).removeClass("utraining_profileCard_checked");
			$(obj).addClass("utraining_profileCard");
		} else {
			$(obj).attr("ischeck", true);
			$(obj).removeClass("utraining_profileCard");
			$(obj).addClass("utraining_profileCard_checked");
		}
	}
	
	function switchShareCheck(obj) {

		var ischeck = $(obj).attr("ischeck");
		if (ischeck == "true") {
			$(obj).attr("ischeck", false);
			$(obj).removeClass("utraining_profileCard_checked");
			$(obj).addClass("utraining_profileCard");
		} else {
			$(obj).attr("ischeck", true);
			$(obj).removeClass("utraining_profileCard");
			$(obj).addClass("utraining_profileCard_checked");
		}
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
						'<tr id='+dateId+'><td style="position: relative;"><div><span class="sp_task_inline_1">'+dateStr+'</span><span class="sp_task_inline_2">'+dayOfWeekStr+'</span></div><div style="position: absolute;right:0;top:0;"><span class="btn" style="padding:2px;" onclick="addTask(this);"><i plusFlag="true" class="glyphicon glyphicon-plus" style="font-size: 15px;color:#bbbbbb"></i></span></div></td><td></td><td></td><td></td></tr>'		
					);
			}
			
			if(suTrainingBO&&suTrainingBO.taskList){
				suTrainingBO.taskList = newTaskList;
				addTaskItem(suTrainingBO.taskList);
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
	
	

	function addTask(obj) {
		
		if($("#func").val()=="view"){
			return ;
		}
		
		var date = $(obj).parent().parent().parent().attr("id");
		
		var url = "<%=serverUrl%>utraining/edit_training_task?func=edit&utrainingId="+$("#utraining_id").val()+"&taskDate="+date;
		buildBoData();
		putBo2Page(suTrainingBO);
		sa.ajax({
			type : "post",
			url : url,
			data : JSON.stringify(suTrainingBO),
			contentType : "application/json",
			success : function(data) {
				AngularHelper.Compile($("#content"), data);
			},
			error : function(data) {
			}
		});

	}

	function displayBtn(obj, flag) {
		if($("#func").val()=="edit"){
			if (flag) {
				$(obj).parent().find("i").css("display", "inline-block");
			} else {
				$(obj).find("i").css("display", "none");
			}
		}
	}

	function deleteTask(obj){
		
		var tempId = $("#deleteTempId").val();
		if(suTrainingBO&&tempId){
			if(suTrainingBO.taskList){
				for(var i =0;i<suTrainingBO.taskList.length;i++){
					var task = suTrainingBO.taskList[i];
					if(task.tempId==tempId){
						suTrainingBO.taskList.splice(i,1);
						break;
					}
				}
			}
		}
		var lineId = tempId.substring(0,8);
		
		$(".utraining_task_table").find("tr[id="+lineId+"]").find("td").find("span[tempId="+tempId+"]").parent().remove();
// 		$(obj).parent().remove();
		putBo2Page(suTrainingBO);
		return false;
	}
	
	function viewTask(obj){
		var tempId = $(obj).attr("tempId");
		buildBoData();
		putBo2Page(suTrainingBO);

		if(tempId){
			var url = "<%=serverUrl%>utraining/view_training_task?func="+$("#func").val()+"&tempId="+tempId+"&utrainingId="+$("#utraining_id").val()+"&reqSource="+$("#reqSource").val()+"&orgId="+$("#orgId").val();
			sa.ajax({
				type : "post",
				url : url,
				data : JSON.stringify(suTrainingBO),
				contentType : "application/json",
				success : function(data) {
					AngularHelper.Compile($("#content"), data);
				},
				error : function(data) {
				}
			});
		}
	}
	
	function validation(){
		$("#utraining_form").bootstrapValidator({
	        message: '您输入的值不合法',
	        feedbackIcons: {
	            valid: 'glyphicon glyphicon-ok',
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	        	utraining_name:{
	        		message: '名称不合法',
		           	validators: {
		                    notEmpty: {
		                        message: '名称是必填项，请正确填写名称。'
		                    },
		                    stringLength: {
		                         max: 30,
		                         message: '名称长度超过限制，请输入30字符以内的名称'
		                    }
		                }
	        	},
	        	utraining_location:{
	        		message: '地点不合法',
		           	 validators: {
		           		stringLength: {
		           			max: 200,
		                    message: '地点过长，请输入200个字符以内的地点'
		                  }
		             }
	        	},
	        	goal:{
	        		message: '目标不合法',
		           	validators: {
		                    stringLength: {
		                         max: 1500,
		                         message: '目标长度超过限制，请输入1500字符以内的目标'
		                    }
		                }
	        	},
	        	evaluation:{
	        		message: '评估不合法',
	        		validators: {
	                    stringLength: {
	                         max: 1500,
	                         message: '评估长度超过限制，请输入1500字符以内的评估'
	                    }
	                }
	        	}
	        }
	    });
	}
	
	function showTrainingDelete(){
// 		$("#deleteTrainingModal").modal({backdrop:false,show:true});
		bootbox.dialog({
			message: "您是否要删除该计划？",
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
						training_delete();
					}
				}
			}
		});
	}
	
	function showDeleteTaskModal(obj){
		$("#deleteTempId").val($(obj).parent().find("span").attr("tempId"))
		//$("#deleteTaskModal").modal({backdrop:false,show:true});
		bootbox.dialog({
			message: "您是否要删除该记录？",
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
						deleteTask();
					}
				}
			}
		});
	}
	function showChangeDateModal(){
		$("#changeDateModal").modal({backdrop:false,show:true});
	}
	function showShareModal(){
		alert("share");
	}
</script>


<div class="main_container">
	<div class="utraining_top_area">
		<div class="button_area">
			<button style="float: right; margin-left: 10px;display:'none';" type='button' class='btn btn-primary' 
				id="btn_save" onclick='save();'>保存</button>
			<button style="float: right; margin-left: 10px;display:'none';" type='button' class='btn btn-primary'
				id="btn_edit" onclick='edit();'>编辑</button>
			<button style="float: right; margin-left: 10px;display:'none';" type='button' class='btn btn-danger'
				id="btn_delete" onclick='showTrainingDelete();'>删除</button>
			<button style="float: right; margin-left: 10px;display:'none';" type='button' class='btn btn-default'
				id="shareButton" data-toggle="popover" data-placement="bottom">分享</button>
			<button style="float: right; margin-left: 10px;display:'none';" type='button' class='btn btn-default'
				id="btn_back" onclick='back();'>取消</button>
		</div>
	</div>

	<div class="utraining_title">
		<div class="row">
			<div id="div_t_name" class=" col-md-2 utraining_title_name"></div>
			<div id="div_t_date_range" class="col-md-3" style="padding-top: 4px;"></div>
			<div id="div_t_location" class="col-md-3" style="padding-top: 4px;"></div>
		</div>
	</div>

	<div class="utraining_base_info">
		<sa-panel title="基本信息">
		<form id="utraining_form">
		<div class="row">
					<div class="col-md-1 " style="height:28px;line-height:28px">
						<label class="utraining_input_label">名称</label>
					</div>
					<div class="col-md-3">
						<div class="form-group ">
		            		<input type="text" id="utraining_name" name="utraining_name"
								class=" form-control" value="${utrainingBO.name}" />
		        		</div>
	        		</div>

				
					<div class="col-md-1 " style="height:28px;line-height:28px">
						<label class="utraining_input_label">开始时间</label>
					</div>
					<div class="col-md-3">
						<div class="form-group input-group date utraining_input_text">
							<input type="text" class="form-control calendar-input" id="start_date" name="start_date" readonly>
							<span id="programStartTimeIcon" class="input-group-addon calendar-icon">
								<i class="glyphicon glyphicon-calendar major_color"></i>
							</span>
						</div>
	        		</div>
				
					<div class="col-md-1 " style="height:28px;line-height:28px">
						<label class="utraining_input_label">颜色</label>
					</div>
					<div class="col-md-3">
						<div class="form-group utraining_input_text">
							<select id="colorSel" class="utraining_input_text">
								<option value="red">橙色</option>
								<option value="blue">蓝色</option>
								<option value="yellow">黄色</option>
								<option value="green">绿色</option>
							</select>
						</div>
	        		</div>
		</div>

		<div class="row" style="margin-top: 10px;">
		
				<div class="col-md-1 " style="height:28px;line-height:28px">
					<label class="utraining_input_label" >地点</label>
				</div>
				<div class="col-md-3">
					<div class="form-group ">
	            		<input id="utraining_location" 
	            			data-bv-field="utraining_location" class="form-control" name="utraining_location"
	            			value="${utrainingBO.location}" type="text">
	        		</div>
        		</div>
		
		
				<div class="col-md-1 " style="height:28px;line-height:28px">
					<label class="utraining_input_label">结束时间</label>
				</div>
				<div class="col-md-3">
					<div class="form-group input-group date utraining_input_text">
						<input type="text" class="form-control  calendar-input" id="end_date" name="end_date" readonly>
						<span id="programEndTimeIcon" class="input-group-addon calendar-icon">
							<i class="glyphicon glyphicon-calendar major_color"></i>
						</span>
					</div>
        		</div>
			<div class="col-md-4">
				<div id="errorMsg" class="utraining_input_text"
					style="text-align: left !important; color: red;"></div>
			</div>
		</div>

		<div class="row" style="margin-top: 10px;">
				<div class="col-md-1 " style="height:28px;line-height:28px">
					<label class="utraining_input_label">目标</label>
				</div>
				<div class="col-md-10">
					<div class="form-group input-group utraining_input_text">
						<textarea id="goal" name="goal" class="form-control utraining_input_text" rows="3"
							style=" line-height: 20px;"></textarea>
					</div>
        		</div>
		</div>
		
		<div class="row" style="margin-top: 10px;">
				<div class="col-md-1 " style="height:28px;line-height:28px">
					<label class="utraining_input_label">评估</label>
				</div>
				<div class="col-md-10">
					<div class="form-group input-group utraining_input_text">
						<textarea id="evaluation" name="evaluation" class="form-control utraining_input_text" rows="3"
							style="line-height: 20px;"></textarea>
					</div>
        		</div>
		</div>

		</form>
		</sa-panel>
	</div>
	<div class="utraining_detail_goal">
		<sa-panel title="目标与评估">
		
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
		
		</sa-panel>
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
		<sa-panel title="参加球员">
		<div class="utraining_players">
			
		</div>
		</sa-panel>
	</div>
</div>

<input type="hidden" id="utraining_id" value="${utrainingId}" />
<input type="hidden" id="func" value="${func}" />
<input type="hidden" id="frompage" value="${frompage}" />
<input type="hidden" id="reqSource" value="${reqSource}" />

<input type="hidden" id="deleteTempId" value="" />


<div class="modal fade" id="changeDateModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" style="z-index: 100001; display: none;">
	<div class="modal-dialog" role="document" style="width: 400px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">修改时间将导致没有保存的任务丢失<br/>是否确认？</h4>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
				<button type="button" class="btn btn-primary" data-dismiss="modal"
					onClick="addTaskLine();">确定</button>
			</div>
		</div>
	</div>
</div>

<!-- <div class="modal fade" id="deleteTaskModal" tabindex="-1" role="dialog" -->
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
<!-- 					onClick="deleteTask();">确定</button> -->
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 	</div> -->
<!-- </div> -->

<!-- <div class="modal fade" id="deleteTrainingModal" tabindex="-1" role="dialog" -->
<!-- 	aria-labelledby="myModalLabel" style="z-index: 100001; display: none;"> -->
<!-- 	<div class="modal-dialog" role="document" style="width: 400px;"> -->
<!-- 		<div class="modal-content"> -->
<!-- 			<div class="modal-header"> -->
<!-- 				<button type="button" class="close" data-dismiss="modal" -->
<!-- 					aria-label="Close"> -->
<!-- 					<span aria-hidden="true">&times;</span> -->
<!-- 				</button> -->
<!-- 				<h4 class="modal-title" id="myModalLabel">确认删除该计划？</h4> -->
<!-- 			</div> -->
<!-- 			<div class="modal-footer"> -->
<!-- 				<button type="button" class="btn btn-default" data-dismiss="modal">取消</button> -->
<!-- 				<button type="button" class="btn btn-primary" data-dismiss="modal" -->
<!-- 					onClick="training_delete();">确定</button> -->
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 	</div> -->
<!-- </div> -->
