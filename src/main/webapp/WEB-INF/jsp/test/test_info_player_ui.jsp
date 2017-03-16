<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="xss" uri="http://www.sportsdata.cn/xss"%>

<%@ page import="cn.sportsdata.webapp.youth.common.utils.CommonUtils" %>
<%@ page import="cn.sportsdata.webapp.youth.common.vo.UserVO" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.sql.Timestamp" %>
<style>
<!--
	.datepicker{
		z-index:9999 !important
	}
-->
</style>
<%
	String serverUrl = CommonUtils.getServerUrl(request);

	UserVO playerInfo = (UserVO) request.getAttribute("player");
	String age = "暂无年龄信息";
	Timestamp birthday = playerInfo.getBirthday();
	
	if(birthday != null) {
		age = String.valueOf(Calendar.getInstance().get(Calendar.YEAR) - Integer.parseInt(new SimpleDateFormat("yyyy").format(birthday))) + "岁";
	}
%>

<c:set value="${fn:length(latest3Items)}" var="latest3Items_len" scope="page"/>
<c:set value="-1" var="chosenCategoryID" scope="page"/>

<div>
	<div class="profileDetailHeader">
		<div class="profileInfo">
			<c:choose>
				<c:when test="${ !empty player.avatar }">
					<img class="profileDetailAvatar" src="<%=serverUrl%>file/downloadFile?fileName=${ player.avatar }"></img>
				</c:when>
				<c:otherwise>
					<img class="profileDetailAvatar" src="<%=serverUrl%>resources/images/user_avatar.png"></img>
				</c:otherwise>
			</c:choose>
			
			<div class="profileDetailName">
				<c:choose>
					<c:when test="${ !empty player.name }">
						<xss:xssFilter text="${player.name}" filter="html"/>
					</c:when>
					<c:otherwise>
						暂无姓名
					</c:otherwise>
				</c:choose>
			</div>
			<div class="profileDetailData">
				<span><%= age %></span>
				<span>&nbsp;/&nbsp;</span>
				<span>
					<c:choose>
						<c:when test="${ !empty player.nationality }">
							${ player.nationality }
						</c:when>
						<c:otherwise>
							暂无国籍信息
						</c:otherwise>
					</c:choose>
				</span>
				<span>&nbsp;/&nbsp;</span>
				<span>
					<c:choose>
						<c:when test="${ !empty player.userExtInfoMap['professional_jersey_number'] }">
							${ player.userExtInfoMap['professional_jersey_number'] }号
						</c:when>
						<c:otherwise>
							暂无号码信息
						</c:otherwise>
					</c:choose>
				</span>
				<span>&nbsp;/&nbsp;</span>
				<span>
					<c:choose>
						<c:when test="${ !empty player.translatedPosition }">
							${ player.translatedPosition }
						</c:when>
						<c:otherwise>
							暂无位置信息
						</c:otherwise>
					</c:choose>
				</span>
			</div>
		</div>
		<div class="profileAction button_area">
			<button id="back_btn" class="btn btn-default" style="float: right;">返回</button>
		</div>
	</div>
	<div class="clearfix"></div>
	<c:if test="${latest3Items_len > 0}">
		<div class="testCategoryChoiceAction">
			<c:forEach var="itemCategory" items="${testItemRenderData.itemCategoryMap}"> 
				<label class="testCategoryCheckLabel">
					<c:choose>
						<c:when test="${fn:length(itemCategory.value) == 0}">
							<input class="testCategoryCheckBox" name="testCategoryCheckBox" type="radio" disabled>
						</c:when>
						<c:otherwise>
							<input class="testCategoryCheckBox" name="testCategoryCheckBox" type="radio" value="${itemCategory.value[0].test_category_id}" cid="${itemCategory.value[0].test_category_id}">
						</c:otherwise>
					</c:choose>
					&nbsp;<c:out value="${itemCategory.key}"></c:out>
				</label>	
			</c:forEach>
		</div>
		<div class="testCategoryItemDivider"></div>
		
		<c:forEach var="itemCategory" items="${testItemRenderData.itemCategoryMap}"> 
			<c:choose>
				<c:when test="${fn:length(itemCategory.value) == 0}">
					<div class="testItemChoiceAction"></div> 
				</c:when>
				<c:otherwise>
					<div class="testItemChoiceAction" id="testItemChoiceAction-${itemCategory.value[0].test_category_id}" style="display:none">
						<c:forEach var="item" items="${itemCategory.value}"> 
							<label class="testItemCheckLabel"><input class="testItemCheckBox" type="checkbox" value="${item.id}" tid="${item.id}">&nbsp;<c:out value="${item.title}"></c:out> </label>
							<c:if test="${item.id == latest3Items[0].id}">
								<c:set value="${item.test_category_id}" var="chosenCategoryID" scope="page"/>
							</c:if>
						</c:forEach>
					</div>
				</c:otherwise>
			</c:choose>
		</c:forEach>
	</c:if>
	
	<section class="center_content">
		<c:if test="${latest3Items_len == 0}">
			<sa-panel title="测试数据">
				该球员没有任何测试数据
			</sa-panel>
		</c:if>
		
		<c:forEach var="itemCategory" items="${testItemRenderData.itemCategoryMap}"> 
			<c:choose>
				<c:when test="${fn:length(itemCategory.value) == 0}">
					<div class="panel-group"></div>
				</c:when>
				<c:otherwise>
					<div class="panel-group" id="panel-group-${itemCategory.value[0].test_category_id}">
						<c:forEach var="item" items="${itemCategory.value}"> 
							<div class="panel panel-default" id="panel-${item.id}" style="display:none">
								<div class="panel-heading text-left ng-binding" style="background-color:#067DC2;color:white">${item.title}</div>
									<div class="panel-body table-responsive">
									<div class="col-md-1 col-sm-offset-6" style="text-align:right;height:35px;line-height: 35px">时间段</div>
									<div class="col-md-2">
									    <div class="input-group">
									     	<input type="text" class="form-control start-datepicker calendar-input" readonly id="start-datepicker-${item.id}" tid="${item.id}">
									      	<div class="input-group-addon calendar-icon"><span class="glyphicon glyphicon-calendar major_color"></span></div>
									    </div>
									</div>
									<div style="float:left">-</div>
									<div class="col-md-2">
									    <div class="input-group">
									     	<input type="text" class="form-control end-datepicker calendar-input" readonly id="end-datepicker-${item.id}" tid="${item.id}">
									      	<div class="input-group-addon calendar-icon"><span class="glyphicon glyphicon-calendar major_color"></span></div>
									    </div>
									</div>
									<div class="clearfix"></div>
									<div id="test_item_chart-${item.id}" style="width:90%; height:400px; margin: 0 auto">
									</div>
				  				</div>
							</div> 
						</c:forEach>
					</div>
				</c:otherwise>
			</c:choose>
		</c:forEach>
	</section>

	
</div>

<script type="text/javascript">
	var DataController = (function(self){
		self.chartMap = {};
		self.player_id = '${player.id}';
		self.chart_default_option = {
			//辅助工具，默认显示在右上角
       		toolbox: {
       	        feature: {
       	            saveAsImage: {}
       	        },
       	        left : 'center',
       	     	top : 'bottom'
       	    },
       	    tooltip: {
       	    	//设置鼠标在坐标轴上就会触发信息显示
       	        trigger: 'axis',
       	        //信息显示的样式，自定义实现
       	        formatter: function (params) {
   	        		var param = params[0];
   	        		if(!param.value){
   	        			return "";
   	        		}
       	            var date = new Date(param.value[0]);
       	            var value = param.value[1];
       	            return ["测试日期: ",date.getFullYear(),"年",date.getMonth() + 1,"月",date.getDate(),"日","</br>","测试结果: ",value].join("");
       	        }
       	    },
       	    grid: {
       	    	//在宽度不确定，或者横轴信息点比较多时，设置为true保证不会超出框
       	        containLabel: true
       	    },
       	    xAxis: {
       	    	//名称，默认显示在右方
       	    	name: '月/日',
       	    	//日期类型，需要日期类型数据(比如timestamp)
       	        type: 'time',
       	      	//最小值和最大值与坐标轴两端的距离,对于area的情况有效果，对于单纯line的情况基本无效
       	        boundaryGap: [0, 0],
       	      	//不显示tick(坐标轴线外,label之内，很短那截线头)
       	        axisTick : {
       	            show: false
       	        },
       	      	//设置label的格式
       	        axisLabel : {
       	        	formatter: function (value, index) {
       	        	    // 格式化成月/日，只在第一个刻度显示年份
       	        	    var date = new Date(value);
       	        	    var texts = [(date.getMonth() + 1), date.getDate()];
       	        	    return texts.join('/') + "   ";
       	        	}
       	        },
       	     	axisLine:{
    	        	lineStyle: {
     	        	color : "#666666"
     	        	}
    	        },
    	        splitLine:{
    	        	lineStyle: {
         	        	color : "#999999"
         	        	}
    	        }
       	        
       	    },
       	    yAxis: {
       	    	//名称，默认显示在上方
       	    	name: '绩点',
       	    	//数字类型，可接受数字
       	        type: 'value',
       	        //最小值和最大值与坐标轴两端的距离,对于area的情况有效果，对于单纯line的情况基本无效
       	        boundaryGap: [0, '10%'],
       	      	//不显示tick(坐标轴线外,label之内，很短那截线头)
       	        axisTick : {
       	            show: false
       	        },
       	     	axisLine:{
    	        	lineStyle: {
     	        	color : "#666666"
     	        	}
    	        },
    	        splitLine:{
    	        	lineStyle: {
         	        	color : "#999999"
         	        	}
    	        }
       	    },
       	    series: [{
       	    	//这个series的name,也是key,通过这个判断同一个series的key(比如重设option覆盖data时候需要)
       	    	name: '数据',
       	    	//线图
       	        type: 'line',
       	        //是否显示数值对应的标记点
       	        showSymbol: true,
       	        //标记点样式，这里用实心圆(默认是空心圆)
       	        symbol:'circle',
       	        //标记点大小
       	        symbolSize : [6, 6],
       	        //折点拐点(这里就是标记点)的颜色，
       	        //这里一修改，连带itemStyle的emphasis也一起修改掉了,连lineStyle和areaStyle的颜色都被改掉了
       	        //所以lineStyle和areaStyle需要额外自定义
       	        itemStyle : {
       	        	normal: {
       	        		color : "#27adf9"
       	        	}
       	        },
       	        lineStyle: {normal: {
       	        	color : "#c4e6fd"
       	        }},
       	        //areastyle设置，至少也要设置"areaStyle: {normal: {}}"才能保证有填充区域，没这个就变成线图了
       	        areaStyle: {normal: {
       	        	color : "#c4e6fd"
       	        }},
       	        data: []
       	    }]
	    };
		self.init = function(){
			
		};
		return self;
	})(DataController || {});
	
	var UIController = (function(self){
		self.init = function(){
			$("input.testCategoryCheckBox[cid = '${chosenCategoryID}']").attr("checked","checked");
			<c:forEach var="latest3Item" items="${latest3Items}" varStatus="status">
				$("input.testItemCheckBox[tid = '${latest3Item.id}']").attr("checked","checked");
			</c:forEach>
			this.updateDatePicker();
			this.updateCategoryLevel();
		};
		self.updateDatePicker = function(){
			$(".end-datepicker").datepicker({
				format : "yyyy-mm-dd",
				language : "zh-CN",
				autoclose : true,
				todayHighlight : true,
				toggleActive : true
			});
			$(".end-datepicker").datepicker('setEndDate', '+0d');//最多选择今天
			$(".end-datepicker").datepicker('setDate', '+0d');//默认今天
			$(".end-datepicker").datepicker().on('changeDate', function(ev){
				var test_item_id = $(ev.target).attr("tid");
				var pairStartDatePicker = $("#start-datepicker-" + test_item_id);
				
				var largestStartDate = new Date(ev.date)
				largestStartDate.setDate(largestStartDate.getDate() -1);
				
				var curStartDate = new Date(pairStartDatePicker.datepicker('getDate'));
				if(curStartDate.getTime() > largestStartDate.getTime()){
					pairStartDatePicker.datepicker('setDate', largestStartDate);
				}else{
					UIController.updateChart(test_item_id,true);
				}
				pairStartDatePicker.datepicker('setEndDate', largestStartDate); 
			});
			
			$(".start-datepicker").datepicker({
				format : "yyyy-mm-dd",
				language : "zh-CN",
				autoclose : true,
				todayHighlight : true,
				toggleActive : true
			});
			$(".start-datepicker").datepicker('setEndDate', '-1d'); //最多选择昨天
			$(".start-datepicker").datepicker('setDate', '-30d');//默认起始天30天前
			$(".start-datepicker").datepicker().on('changeDate', function(ev){
				var test_item_id = $(ev.target).attr("tid");
				UIController.updateChart(test_item_id,true);
			});
			
			$("div.input-group-addon").click(function(){
				$(this).prev().datepicker('show');
			});
		};
		
		self.updateCategoryLevel = function(){
			var test_category_id = $('input.testCategoryCheckBox:radio:checked').attr("cid");
			$("div.testItemChoiceAction").hide();
			$("#testItemChoiceAction-"+test_category_id).show();
			//$("div.panel-group").hide();
			//$("#panel-group-"+test_category_id).show();
			this.updateItemLevelPanel();
		};
		self.updateItemLevelPanel = function(){
			var test_category_id = $('input.testCategoryCheckBox:radio:checked').attr("cid");
			//$("#panel-group-"+test_category_id).children(".panel").hide();
			$(".panel").hide();
			//var checkedItems = $("#testItemChoiceAction-"+test_category_id).find("input.testItemCheckBox:checkbox:checked");
			var checkedItems = $("input.testItemCheckBox:checkbox:checked");
			checkedItems.each(function(index,element){
				var test_item_id = $(element).attr("tid");
				$("#panel-" + test_item_id).show();
				UIController.updateChart(test_item_id,false);		
			});
		};
		self.updateChart = function(test_item_id,forceFLush){
			var startDateStr = $("#start-datepicker-" + test_item_id).val();
			var endDateStr = $("#end-datepicker-" + test_item_id).val();
			var chart = DataController.chartMap[test_item_id+""];
			
			if(!chart){
				chart = echarts.init($("#test_item_chart-"+test_item_id).get(0));
				chart.setOption(DataController.chart_default_option);
				DataController.chartMap[test_item_id+""] = chart;
				forceFLush = true;
			}
			
			if(forceFLush){
				chart.showLoading({
					text: '加载中',
					color: '#27adf9',
					textColor: '#000',
					maskColor: 'rgba(255, 255, 255, 0.8)',
					zlevel: 0
				});
				var url = "<%=serverUrl%>test/test_chartdata_player?pid="+ 'DataController.player_id'
						+ "&tid=" + test_item_id + "&start=" + startDateStr + "&end=" + endDateStr;
				$.ajax({
					type : 'GET',
					url : url,
					dataType : 'json',
					success : function(message) {
						chart.hideLoading();
						if(message.renderDataSeries.length > 0){
							chart.setOption({
								yAxis: {
					       	    	//名称，默认显示在上方
					       	    	name: message.test_unit_name
					       	    },
						        series: [{
						            // 根据名字对应到相应的系列
						            name: '数据',
						            data: message.renderDataSeries
						        }]
						    });
						}
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						chart.hideLoading();
					}
				});
			}
		}
		return self;
	})(UIController || {});
	
	var EventController = (function(self){
		self.init = function(){
			buildBreadcumb("球员测试信息");
		};
		$('#back_btn').click(function() {
			var backurl = "${backurl}";
			if(!backurl){
				backurl = "<%=serverUrl%>test/test_manage?radio=1";
			}
			sa.ajax({
				type : "get",
				url : backurl,
				success : function(data) {
					AngularHelper.Compile($('#content'), data);
				},
				error: function() {
					alert("返回页面失败");
				}
			});
		});
		
		
		$("input.testCategoryCheckBox").click(function(){
			UIController.updateCategoryLevel();
		});
		
		$("input.testItemCheckBox").click(function(){
			UIController.updateItemLevelPanel();
		});
		
		return self;
	})(EventController || {});
	
	$(function() {
		DataController.init();
		UIController.init();
		EventController.init();
	});
	
</script>