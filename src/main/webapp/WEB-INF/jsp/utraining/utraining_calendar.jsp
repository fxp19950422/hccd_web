<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="cn.sportsdata.webapp.youth.common.utils.CommonUtils" %>
<%
	String basePath = CommonUtils.getServerUrl(request);
%>
<style type="text/css">
.utraining_container {
	clear:both;
	width: 97%;
	min-width:1001px;
	border-top: 1px solid #e8e8e8;
	border-right: 1px solid #e8e8e8;
	border-left: 1px solid #e8e8e8;
	white-space: nowrap;
	margin-left: auto;
	margin-right: auto;
}
.tooltip-inner{ 
	color: #000000;
	background-color:#ffffff;
	border:1px solid grey;
}
.tooltip.bottom .tooltip-arrow{
	border-top-color: #dddddd;
	border-bottom-color:#dddddd;
}
.all_container {
	clear:both;
	width: 100%;
	min-width:1001px;
	border: 1px solid #e8e8e8;
	white-space: nowrap;
	padding-bottom: 10px;
}

.header {
	clear:both;
	background-color: #56b9ef;
	border-bottom: 1px solid #e8e8e8;
	color: #fff;
	white-space: nowrap;
	text-align: center;
}


.contentline {
	clear: both;
	height: 20px;
	width: 100%;
	min-width:1001px;
	background-color: #fff;
	border-bottom: 1px solid #e8e8e8;
	white-space: nowrap;
	line-height: 20px;
	
}

.footer {
	background-color: #99bbbb;
	clear: both;
	text-align: center;
}

h1 {
	margin-bottom: 0;
}

h2 {
	margin-bottom: 0;
	font-size: 18px;
}

.contentline>ul  {
	margin: 0;
	height: inherit;
	padding-left: 0px;
	white-space: nowrap;
	line-height: inherit;
}

.contentline>ul>li {
	list-style: none;
	float: left;
	border-left: 1px solid #e8e8e8;
	width: 2%;
	height: inherit;
	font-size: 12px;
	text-align: center;
	line-height: inherit;
	white-space: nowrap;
	overflow: hidden;
	color:#666666;
	text-overflow: ellipsis;
}

.contentline>ul>li:FIRST-CHILD {
	list-style: none;
	float: left;
	width: 70px;
	border: 0px;
	height: inherit;
	color: #333333;
	font-weight:500;
	font-size: 14px;
	background-color:#f8f8f8;
	line-height: inherit;
 	border-bottom: 1px solid #e8e8e8; 
}
</style>
<script type="text/javascript">
	var liWidth;
	var firstLiWidth = 70;

	$(document)
			.ready(
					function() {
						buildBreadcumb();
						
						var year = new Date().getFullYear();
						
						$("#reqYear").val(year);
						
						changeUtrainingYear();

					});

	function showTrainingDetail(obj) {
		var id=$(obj).attr("id");
		$("#hiddenUtrainingBO").removeData("utrainingBO");
		loadPage("<%=basePath%>utraining/edit_calendar?utrainingId="+id+"&func=view&frompage=calendar");
	}
	
	function setTitle(year){
		var title = $("#org_name").val();
		$(".header").html(title);
		var selectTitle = year + "年训练计划";
		$("#selectTitle").html(selectTitle);
		
	}
	
	function addYear(param){
		var value = $("#reqYear").val();
		$("#reqYear").val(add(value,param))
		changeUtrainingYear();
	}
	
	function changeUtrainingYear(){
		
		$(".utraining_container .contentline").remove();
		var value = $("#reqYear").val();
		
		setTitle(value);
		
		$.ajax({
			type : "post",
			url : "<%=basePath%>utraining/get_calendar_info",
			data : {
				year : value
			},
			dataType : "json",
			success : function(data) {
				drawUtrainingCalendar(data.result);
				$(".utraining_container").find(".contentline").eq(0).find("ul").find("li").css("font-size","14px");
				$(".utraining_container").find(".contentline").eq(0).find("ul").find("li").css("color","#333333");
			},
			error : function(data) {
			}
		});
	}
	
	function addUtraining(){
		var url = "<%=basePath%>utraining/edit_calendar?func=edit&frompage=calendar";
		$("#hiddenUtrainingBO").removeData("utrainingBO");
		loadPage(url);
	}
	
	function drawUtrainingCalendar(json){
		var allData = jQuery.parseJSON(json);
		var maxWeekCount = allData.maxWeek;
		var jsonObjStr = allData.allMonthData;
		var jsonObj = jQuery.parseJSON(jsonObjStr);
		
		var jsonTrainingStr = allData.trainingData;
		if(jsonTrainingStr){
			var jsonTraining = jQuery.parseJSON(jsonTrainingStr);
		}
		var jsonMatchStr = allData.matchData;
		if(jsonMatchStr){
			var jsonMatch = jQuery.parseJSON(jsonMatchStr);
		}
		// base layout date matching week
		var containerWidth = $(".utraining_container").width();
		liWidth = (containerWidth)/ add(maxWeekCount * 7,2) /(containerWidth);
		liWidth= liWidth*100;
		$(".utraining_container").append("<div class='contentline' style='color: #333333;font-weight:500;font-size:14px;background-color:#f8f8f8;' id='0'><ul><li></li></ul></div>");
		for (var i = 0; i < maxWeekCount; i++) {
			$(".contentline")
					.find("ul")
					.append(
							"<li>一</li><li>二</li><li>三</li><li>四</li><li>五</li><li>六</li><li>日</li>");
		}
		for (var i = 0; i < 12; i++) {
			var month = add(i, 1);
			var jsonArray = jsonObj[month];
			var lineId = "m"+month;
			$(".utraining_container")
					.append(
							"<div class='contentline' id='"+lineId+"'></div>");
			$("#" + lineId).append(
					"<ul><li>" + month + "月</li></ul>");
			for (var k = 0; k < jsonArray.length; k++) {
				$("#" + lineId).find("ul").append(
						"<li>" + jsonArray[k] + "</li>")
			}
			$(".utraining_container")
					.append(
							"<div class='contentline' id=sub_"+lineId+"></div>");
			$("#sub_" + lineId).append("<ul><li></li></ul>");
			$("#sub_" + lineId).css("height","40px");
			$("#sub_" + lineId).css("line-height","40px");
		}
		$(".contentline li").css("width", liWidth+"%");
		$(".contentline ul li:first-child").css("width", 2*liWidth+"%");

		// second layout: training
		if(jsonTraining){
			for (var j = 0; j < 12; j++) {
				var month = add(j, 1);
				var lineId = "m"+month;
				var subLineId = "sub_m" + month;
				if (jsonTraining[month]) {
					var latestIndex = 0;
					var trainingObjArray = jsonTraining[month];
					for (var k = 0; k < trainingObjArray.length; k++) {
						var oneTrainingInMonth = trainingObjArray[k];

						// training date array
						var array = oneTrainingInMonth.days;
						// one month full date array
						var fullMonthArray = jsonObj[month];
						// the first day in training date array
						var firstDay = array[0];
						// the last day in training date array
						var lastDay = array[array.length - 1];
						// the firstday's index in full array
						var firstIndex = 0;
						// the lastday's index in full array
						var lastIndex = 0;
						for (var i = 0; i < fullMonthArray.length; i++) {
							if (firstDay == fullMonthArray[i]) {
								firstIndex = i;
							}
							if (lastDay == fullMonthArray[i]) {
								lastIndex = i;
							}
						}
						
						var emptyDivWidth = (firstIndex-latestIndex)*liWidth ;
						var contentDivWidth = add((lastDay-firstDay),1)*liWidth ;
						if(emptyDivWidth!=0){
							$("#" + subLineId)
							.find("ul")
							.append(
									"<li style='float:left;width:"+emptyDivWidth+"%;heigth:inhert;'></li>");
						}
						var trainingName = oneTrainingInMonth.trainingName;
						trainingName = trainingName.replace(/</g,"&lt;")
						var innerHtml = [];
						innerHtml.push("<li id=");
						innerHtml.push(oneTrainingInMonth.trainingId);
						innerHtml.push(" title='");
						innerHtml.push(trainingName);
						innerHtml.push("'");
						innerHtml.push(" onclick=showTrainingDetail(this); style='float:left;width:");
						innerHtml.push(contentDivWidth);
						innerHtml.push("%;heigth:inherit;background-color:");
						innerHtml.push(oneTrainingInMonth.color);
						innerHtml.push(";color:#fff;cursor:pointer;z-index:100;border-top:1px solid #e8e8e8;border-bottom:1px solid #e8e8e8;border-left:1px solid ");
						innerHtml.push(oneTrainingInMonth.color);
						innerHtml.push(";'>");
						innerHtml.push(trainingName);
						innerHtml.push("</li>");
						$("#" + subLineId).find("ul").append(innerHtml.join(''));
						for (var i = (firstIndex + 1); i <= (lastIndex + 1); i++) {
							$("#" + lineId).find("ul").children().eq(i).css("color", "#fff");
							$("#" + lineId).find("ul").children().eq(i).css("background-color",oneTrainingInMonth.color);

						}
						latestIndex = add(lastIndex,1);
					}
				} else {
					$("#" + subLineId)
					.find("ul")
					.append(
							"<li style='float:left;width:10%;heigth:inhert;'></li>");
				}
			}
		}
		// third layout: matches
		if(jsonMatch){
			for (var j = 0; j < 12; j++) {
				var month = add(j, 1);
				var lineId = "m" + month;
				var subLineId = "sub_" + month;
				if (jsonMatch[month]) {
					var latestIndex = -1;
					var trainingObjArray = jsonMatch[month];
					for (var k = 0; k < trainingObjArray.length; k++) {
						var oneTrainingInMonth = trainingObjArray[k];

						// training date array
						var array = oneTrainingInMonth.days;
						// one month full date array
						var fullMonthArray = jsonObj[month];
						// the first day in training date array
						var firstDay = array[0];
						// the last day in training date array
						var lastDay = array[array.length - 1];
						// the firstday's index in full array
						var firstIndex = 0;
						// the lastday's index in full array
						var lastIndex = 0;
						for (var i = 0; i < fullMonthArray.length; i++) {
							if (firstDay == fullMonthArray[i]) {
								firstIndex = i;
							}
							if (lastDay == fullMonthArray[i]) {
								lastIndex = i;
							}
						}
						var emptyDivWidth = add(liWidth
								* (firstIndex - latestIndex - 1),
								(firstIndex - latestIndex - 1));
						var contentDivWidth = add(liWidth
								* (lastIndex - firstIndex + 1),
								(lastIndex - firstIndex));
						if (k == trainingObjArray.length - 1) {
							contentDivWidth++;
						}
						for (var i = (firstIndex + 1); i <= (lastIndex + 1); i++) {
							$("#" + lineId).find("ul").children().eq(i).css("color", "#fff");
							var opponent = oneTrainingInMonth.opponent.replace(/</g,"&lt;")
							$("#" + lineId).find("ul").children().eq(i).attr("title", opponent);
							$("#" + lineId).find("ul").children().eq(i).css( "background-color", oneTrainingInMonth.color);
						}
						latestIndex = lastIndex;
					}
				}
			}
		}
		$("[data-toggle='tooltip']").each(function(i){
			$(this).tooltip({
				placement:"bottom",
				html:true,
				title : "<div style='display:block;text-align:left;background: white;color:#666666;' <span style='color:#0d9ae7;font-weight:bold'>提示:</span> &nbsp;"+$(this).text()+"</div>"
			});
			
		})
	}
</script>


	<div class="button_area" style="float:right">
		<div style="float: left; margin-right: 5px;margin-left:20px;">
			<button type='button' class='btn btn-primary' id="btn_add" onclick='addUtraining();'>添加</button>
		</div>
	</div>
	
	<div class="clearfix" style="height: 15px;"></div>

	<div class="all_container" style = "height: auto;background-color: #fff;border-right: 1px solid #e8e8e8;border-left: 1px solid #e8e8e8;text-align: center;">
		<div class="header panel-heading" style="margin-left:1px;margin-right:1px; background-color:#067DC2;color:white;text-align: left;">
		</div>
			
		<div class="year_change" style = "height: 50px;line-height:50px;text-align: center;">
			<button type="button" class="btn btn-default btn-sm" onclick="addYear(-1);">
	          <span class="glyphicon glyphicon-chevron-left"></span> 
	        </button>
	        <span id="selectTitle" style="margin-left: 15px;font-size: 18px;color:#333333;margin-right: 15px;">训练计划</span>
	        <button type="button" class="btn btn-default btn-sm" onclick="addYear(1);">
	          <span class="glyphicon glyphicon-chevron-right"></span> 
	        </button>
		</div>

		<div class="utraining_container">
		
			<div class="contentline">
				<ul>
					<li></li>
				</ul>
			</div>
		</div>
	</div>

	<input type="hidden" id="org_name" value="${orgVO.name}" />
	<input type="hidden" id="reqYear" value="" />
