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
	.select2-container--default .select2-selection--single, .select2-selection .select2-selection--single{
		height:24px;
		padding:2px 12px;
		font-size:14px;
		margin-top: -5px;
	}

	.select2-container--default .select2-selection--single .select2-selection__arrow{
		top: -40%
	}
	
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
		<div class="testItemChoiceAction">
			<c:forEach var="latest3Item" items="${latest3Items}" varStatus="status">
				<c:choose>
					<c:when test="${status.index < 2}">
						<label class="testItemCheckLabel"><input class="testItemCheckBox" type="checkbox" value="${latest3Item.id}" checked>&nbsp;<c:out value="${latest3Item.title}"></c:out> </label>
					</c:when>
					<c:otherwise>
						<label for="testItemNameSelector">其他项目：</label>
						<select id="testItemNameSelector">
							<option value="-1" class="testItemOption">&nbsp;</option>
							<c:forEach var="itemCategory" items="${testItemRenderData.itemCategoryMap}"> 
								<optgroup label="<c:out value='${itemCategory.key}'></c:out>">
									<c:forEach var="item" items="${itemCategory.value}"> 
										<option value ="${item.id}" class="testItemOption"><c:out value='${item.title}'></c:out></option>
									</c:forEach>
								</optgroup>
							</c:forEach>
						</select>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</div>
	</c:if>
	
	<section class="center_content">
		<c:if test="${latest3Items_len == 0}">
			<sa-panel title="测试数据">
				该球员没有任何测试数据
			</sa-panel>
		</c:if>
		
		<c:forEach var="latest3Item" items="${latest3Items}" varStatus="status">
			<div class="panel panel-default" 
				<c:choose>
					<c:when test="${status.index == 2}">
						id = "test_item_panel_lastdynamic"
					</c:when>
					<c:otherwise>
						id = "test_item_panel-${latest3Item.id}"
					</c:otherwise>
				</c:choose>>
				<div class="panel-heading text-left ng-binding" 
				style="background-color:#067DC2;color:white">${latest3Item.title}</div>
				<div class="panel-body table-responsive">
					<div class="col-md-1 col-sm-offset-6" style="text-align:right;height:35px;line-height: 35px">时间段</div>
					<div class="col-md-2">
					    <div class="input-group">
					     	<input type="text" class="form-control start-datepicker calendar-input" readonly id="start-datepicker-${status.index}" datepickerindex="${status.index}">
					      	<div class="input-group-addon calendar-icon"><span class="glyphicon glyphicon-calendar major_color"></span></div>
					    </div>
					</div>
					<div style="float:left">-</div>
					<div class="col-md-2">
					    <div class="input-group">
					     	<input type="text" class="form-control end-datepicker calendar-input" readonly id="end-datepicker-${status.index}" datepickerindex="${status.index}">
					      	<div class="input-group-addon calendar-icon"><span class="glyphicon glyphicon-calendar major_color"></span></div>
					    </div>
					</div>
					<div class="clearfix"></div>
					<div class="test_item_chart" style="width:80%; height:400px; margin: 0 auto">
					</div>
  				</div>
			</div>
		</c:forEach>
	</section>

	
</div>

<script type="text/javascript">
	var DataController = (function(self){
		self.player_id = ${player.id};
		self.test_items = [];
		self.test_item_charts = [];
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
       	        	    return texts.join('/');
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
			<c:forEach var="latest3Item" items="${latest3Items}">
				this.test_items.push(${latest3Item.id});
				this.test_item_charts.push("");
			</c:forEach>
		};
		return self;
	})(DataController || {});
	
	var UIController = (function(self){
		self.init = function(){
			//init select for first two option cannot selected because already list before it
			//init sleect for last opetion as the default selected option
			<c:if test="${latest3Items_len == 3}">
				var option_first_disabled_item_id =  ${latest3Items[0].id};
				$("option.testItemOption[value = '"+option_first_disabled_item_id+"']").attr("disabled", "disabled"); 
				
				var option_second_disabled_item_id =  ${latest3Items[1].id};
				$("option.testItemOption[value = '"+option_second_disabled_item_id+"']").attr("disabled", "disabled"); 
				
				var option_third_choose_item_id = ${latest3Items[2].id};
				$("option.testItemOption[value = '"+option_third_choose_item_id+"']").attr("selected", true); 
			</c:if>
			
			$('#testItemNameSelector').select2({
				minimumResultsForSearch : Infinity,
			});
			
		};
		
		self.updateRange = function(index){
			var startDateStr = $("#start-datepicker-" + index).val();
			var endDateStr = $("#end-datepicker-" + index).val();
			var test_item_id = DataController.test_items[index];
			var dom = (index == 2) ? $("#test_item_panel_lastdynamic") : $("#test_item_panel-" + test_item_id);
			this.updateChart(startDateStr,endDateStr,index,test_item_id,dom.children(".panel-body ").children(".test_item_chart"));
		};
		
		self.updateDynamicTestItem = function(test_item_id,test_item_title){
			var startDateStr = $("#start-datepicker-2").val();
			var endDateStr = $("#end-datepicker-2").val();
			var dom = $("#test_item_panel_lastdynamic"); 
			dom.children(".panel-heading").text(test_item_title);
			this.updateChart(startDateStr,endDateStr,2,test_item_id,dom.children(".panel-body ").children(".test_item_chart"));
		};
		
		self.updateChart = function(startDateStr,endDateStr,index,test_item_id,chartDom){
			//var text = "from : " + startDateStr + " to : " + endDateStr + "<br/>";
			//text += " test_item_id : " + test_item_id + "<br/>";
			//text += " index : " + index;
			//chartDom.html(text);
			var chart = DataController.test_item_charts[index];
			if(!chart){
				chart = echarts.init(chartDom.get(0));
				chart.setOption(DataController.chart_default_option);
				DataController.test_item_charts[index] = chart;
			}
			chart.showLoading({
				text: '加载中',
				color: '#27adf9',
				textColor: '#000',
				maskColor: 'rgba(255, 255, 255, 0.8)',
				zlevel: 0
			});
			var url = "<%=serverUrl%>test/test_chartdata_player?pid="+ DataController.player_id 
					+ "&tid=" + test_item_id + "&start=" + startDateStr + "&end=" + endDateStr;
			$.ajax({
				type : 'GET',
				url : url,
				dataType : 'json',
				success : function(message) {
					chart.hideLoading();
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
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					chart.hideLoading();
				}
			});
			
		}
		
		return self;
	})(UIController || {});
	
	var EventController = (function(self){
		self.init = function(){
			buildBreadcumb("球员测试信息");
			
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
				var index = $(ev.target).attr("datepickerindex");
				var pairStartDatePicker = $("#start-datepicker-" + index);
				
				var largestStartDate = new Date(ev.date)
				largestStartDate.setDate(largestStartDate.getDate() -1);
				
				var curStartDate = new Date(pairStartDatePicker.datepicker('getDate'));
				if(curStartDate.getTime() > largestStartDate.getTime()){
					pairStartDatePicker.datepicker('setDate', largestStartDate);
				}else{
					UIController.updateRange(index);
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
				var index = $(ev.target).attr("datepickerindex");
				UIController.updateRange(index);
			});
			
			$("div.input-group-addon").click(function(){
				$(this).prev().datepicker('show');
			});
			
			
			$(".testItemCheckBox").click(function(){
				var test_item_id = $(this).val();
				$("#test_item_panel-" + test_item_id).toggle();
			});
			
			$("#testItemNameSelector").change(function(){
				var choiceTestItem = $('option.testItemOption:selected');
				var choiceTestItemID = choiceTestItem.val();
				if(!choiceTestItemID || choiceTestItemID == "-1"){
					$("#test_item_panel_lastdynamic").hide();
				}else{
					$("#test_item_panel_lastdynamic").show();
					var intchoiceTestItemID = parseInt(choiceTestItemID);
					if(intchoiceTestItemID != DataController.test_items[2]){
						UIController.updateDynamicTestItem(intchoiceTestItemID,choiceTestItem.text());
					}
					DataController.test_items[2] = intchoiceTestItemID;
					
				}
			});
			
		};
		$('#back_btn').click(function() {
			sa.ajax({
				type : "get",
				url : "<%=serverUrl%>test/test_manage?radio=1",
				success : function(data) {
					AngularHelper.Compile($('#content'), data);
				},
				error: function() {
					alert("返回页面失败");
				}
			});
		});
		
		return self;
	})(EventController || {});
	
	$(function() {
		DataController.init();
		UIController.init();
		EventController.init();
		for (var i = 0; i < DataController.test_items.length; i++) {
			UIController.updateRange(i);
	    }
	});
	
</script>