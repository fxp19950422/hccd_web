<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="xss" uri="http://www.sportsdata.cn/xss"%>
<%@ page import="cn.sportsdata.webapp.youth.common.utils.CommonUtils" %>
<%@ page import="cn.sportsdata.webapp.youth.common.vo.UserVO" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.sql.Timestamp" %>
<style type="text/css">
.choose{
   margin-left: 17px;
}

.as_panel_container{
	height: 537px;
	background-color: #fff;
/* 	border-right: 1px solid #e8e8e8; */
/* 	border-left: 1px solid #e8e8e8; */
	border:1px solid #ddd;
	text-align: center;
	border-radius: 3px;
	margin-bottom: 20px;
}

.as_panel_header{
	border-top-left-radius: 3px;
	border-top-right-radius: 3px;
	background-color: #067DC2;
	color: white;
	text-align: left;
	height: 40px;
	padding-left: 10px;
	padding-top: 3px;
}

.btnMenu {
	background-color: #067DC2;
	color: white;
	border-color: #067DC2;
	border-radius: 3px;
	-webkit-box-shadow: none;
	box-shadow: none;
	border: 1px solid transparent;
}

.timeLabel {
	text-align:right;
	height:35px;
	line-height: 35px;
	padding:0px;
	margin-left:15px;
	width:65px;
}
</style>
<%
UserVO playerInfo = (UserVO) request.getAttribute("player");
String age = "暂无年龄信息";
Timestamp birthday = playerInfo.getBirthday();
String serverUrl = CommonUtils.getServerUrl(request);
if(birthday != null) {
	age = String.valueOf(Calendar.getInstance().get(Calendar.YEAR) - Integer.parseInt(new SimpleDateFormat("yyyy").format(birthday))) + "岁";
}
%>
<script type="text/javascript">
var chartMap = {};
var chart_default_option = {
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
   	            return ["采集日期: ",date.getFullYear(),"年",date.getMonth() + 1,"月",date.getDate(),"日","</br>","采集结果: ",value].join("");
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
 
function updateRange(index,id,col){
	var colums= id.split(",");
	for(var i =0 ; i<colums.length; i++){
		if(i>0){
			break
		}
		var startDateStr = $("#start-datepicker-" + index).val();
		var endDateStr = $("#end-datepicker-" + index).val();
		if(col){
			var chart = chartMap[col];
		}else{
			var chart = chartMap[id];
		}
		if(!chart){
			chart = echarts.init($("#chart_"+id).get(0));
			chart.setOption(chart_default_option);
			chartMap[id] = chart;
		}
		chart.showLoading({
			text: '加载中',
			color: '#27adf9',
			textColor: '#000',
			maskColor: 'rgba(255, 255, 255, 0.8)',
			zlevel: 0
		});
		if(["shoulder","haunch","waistfat"].indexOf(colums[i])!=-1){
			colums[i]=$("#JBchart").val();
		}
		if(["chest","waist"].indexOf(colums[i])!=-1){
			colums[i]=$("#XYchart").val();
		}
		var url = "<%=serverUrl%>healthdata/healthchart?userid=${id}&col=" +colums[i]+ "&startdate=" + startDateStr + "&enddate=" + endDateStr;
		var callback=function(color){
			
		}
		$.ajax({
			type : 'GET',
			url : url,
			dataType : 'json',
			success : function(message) {
						chart.hideLoading();
						if(message.length>0){
							_color="";
							switch (message[0].colum) {
							case "height":
							case "weight":
								_color="#D8F0FD"
								break;
							case "bmi":
							case "oxygen_content":
								_color="#DDFFE3"
								break;
							case "shoulder":
							case "haunch":
							case "waistfat":
							case "chest":
							case "waist":
								_color="#FFF6C3"
								break;
							case "morning_pulse":
							case "lactate":
								_color="#FFC3B0"
								break;
							default:
								break;
							}
							
							
							chart.setOption({
								yAxis: {
					       	    	//名称，默认显示在上方
					       	    	name: message[0].unit_name
					       	    },
					       	    
						        series: [{
						            name: '数据',
						            data: message[0].renderDataSeries,
						            areaStyle: {normal: {
						   	        	color : _color
						   	        }}
						        }]
						    })
						}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				chart.hideLoading();
			}
		});
		}
	}
	
function changeJBchart(obj){
	var col= $(obj).attr("val");
	$("#JBchart").val(col);
	$("#formationTypeBtn").html($(obj).html());
	updateRange(5,col,"shoulder,haunch");
}	
function changeXYchart(obj){
	var col=$(obj).attr("val");
	$("#XYchart").val(col);
	$("#formationXYTypeBtn").html($(obj).html());
	updateRange(6,col,"chest,waist");
}
$(function(){
	
	setTimeout(function(){
		buildBreadcumb("单个成员身体指标");
// 		$('#JBchart').select2({
// 			minimumResultsForSearch: Infinity
// 		});
// 		$('#XYchart').select2({
// 			minimumResultsForSearch: Infinity
// 		});
		$("#table").bootstrapTable();
		
		$("#table").bootstrapTable('refresh', {url: "<%=request.getContextPath()%>/healthdata/gethealthdatalist/${id}?rnd=" + Math.random()});
		
		$(".end-datepicker").each(function(i){
			$(this).datepicker({
				format : "yyyy-mm-dd",
				language : "zh-CN",
				autoclose : true,
				todayHighlight : true,
				toggleActive : true,
				zIndexOffset:1031
			});
			var b=$(this);
			$(this).parent().find(".input-group-addon").click(function() {
				b.datepicker('show');
			});
		})
		
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
				//UIController.updateRange(index);
				var id = $(ev.target).attr("datahold");
				updateRange(index,id)
			}
			pairStartDatePicker.datepicker('setEndDate', largestStartDate); 
		});	
		$(".start-datepicker").each(function(i){
			$(this).datepicker({
				format : "yyyy-mm-dd",
				language : "zh-CN",
				autoclose : true,
				todayHighlight : true,
				toggleActive : true,
				zIndexOffset:1031
			});
			var b=$(this);
			$(this).parent().find(".input-group-addon").click(function() {
				b.datepicker('show');
			});
		})
		
		$(".start-datepicker").datepicker('setEndDate', '-1d'); //最多选择昨天
		$(".start-datepicker").datepicker('setDate', '-30d');//默认起始天30天前
		$(".start-datepicker").datepicker().on('changeDate', function(ev){
			var index = $(ev.target).attr("datepickerindex");
			var id = $(ev.target).attr("datahold");
			updateRange(index,id)
			
		});
		
		$(".start-datepicker").each(function(i){
			var id = $(this).attr("datahold");
			updateRange(i+1,id);
		})
	},50);
	
	
	
})



function back() {
	var url ="<%=serverUrl%>healthdata/gohealthdata";
	if("${backurl}"!=""){
		url="${backurl}";
	}
	
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
function handle(userID,healthdata_id) {
	if(healthdata_id==undefined){
		healthdata_id=0;
	}
	var url ="<%=serverUrl%>healthdata/gohandlehealthdata/"+healthdata_id+"/"+userID+"?backurl=${backurl}";
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
function actionFormatter(value, row, index){
	return '<span style="cursor:pointer" onclick=del("'+value+'") ><i class="glyphicon glyphicon-trash content-color"></i></span><span onclick=handle("${id}","'+value+'") style="margin-left:10px;cursor:pointer" ><i class="glyphicon glyphicon-edit content-color"></i></span>';
}
function clickchose(){
	var col=[];
	var hidden= $("#table").bootstrapTable("getHiddenColumns"); 
	var fids=[];
	for(var i =0 ; i<hidden.length; i++){
		fids.push(hidden[i].field);
	}
	$("input[name='chosechart']").each(function(i){
		if(!$(this).is(":checked")){
			$("#contain_"+$(this).val()).hide();
			var b=$(this).val().split(",")
				for(var i =0 ;i<b.length; i++){
					col.push(b[i])
				}
		}else {
			var b=$(this).val().split(",")
			$("#contain_"+$(this).val()).show()
				for(var i =0 ;i<b.length; i++){
					if(fids.indexOf(b[i])!=-1){
						$("#table").bootstrapTable("showColumn",b[i]);
					}
				}
		}
	})
	for(var i=0 ; i<col.length; i++){
		$("#table").bootstrapTable("hideColumn",col[i]);
	}
	
}
function expcel() {
	$("#expexcel").submit();
}
function del(id){
		bootbox.dialog({
			  message: "您是否要删除这条记录？",
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
				   		$.ajax({
							type : 'POST',
							url : '<%=serverUrl%>healthdata/deletehealthdata/'+id,
							success : function(message) {
								if(message){
									$("#table").bootstrapTable('remove', {field: 'id', values: [id]});
									$(".start-datepicker").each(function(i){
										var id = $(this).attr("datahold");
										updateRange(i+1,id);
									})
								}else{
									alert('删除记录失败');
								}
							},
							error : function(XMLHttpRequest, textStatus, errorThrown) {
								alert('删除记录失败');
							}
						});
			      }
			    }			   
			  }
			});
}
</script>

	
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
	<div class="profileAction button_area" style=" width: 196px;">
				<button id="back_btn" onclick="back()" class="btn btn-default" style="margin-left: 13px;">返回</button>
				<button id="add_btn" onclick="handle('${id}')" class="btn btn-primary" style="float: right;margin-left: 10px">添加</button>
				<button id="export_btn" onclick="expcel()" class="btn btn-default" style="float: right; margin-left: 10px;">导出</button>
	</div>
	
	
	<div class="clearfix"></div>
	<div class="profileDetailAction">
		<label class="content-text">
		<input type="checkbox"  name="chosechart"  onclick="clickchose();" checked="checked" value="height">身高</label>
		<label class="choose content-text" ><input type="checkbox" name="chosechart" checked="checked" onclick="clickchose();"  value="weight">体重</label>
		<label class="choose content-text" ><input type="checkbox" name="chosechart" checked="checked" onclick="clickchose();"  value="bmi">BMI</label>
		<label class="choose content-text" ><input type="checkbox" name="chosechart" checked="checked" onclick="clickchose();"  value="oxygen_content">含氧量</label>
		<label class="choose content-text" ><input type="checkbox" name="chosechart" checked="checked" onclick="clickchose();" value="shoulder,haunch,waistfat">肩胛脂/大臂脂/腰围脂</label>
		<label class="choose content-text" ><input type="checkbox" name="chosechart" checked="checked" onclick="clickchose();"  value="chest,waist">胸围/腰围</label>
		<label class="choose content-text" ><input type="checkbox" name="chosechart" checked="checked" onclick="clickchose();" value="morning_pulse">晨脉</label>
		<label class="choose content-text" ><input type="checkbox" name="chosechart" checked="checked" onclick="clickchose();" value="lactate">乳酸</label>
	</div>
	</div>
	<table style="clear: both" id="table" data-classes="table table-no-bordered sprotsdatatable" data-toggle="table"
		data-striped="true" data-pagination="true"
		data-page-size="7" data-page-list="[7,10,15,20]"
		data-pagination-first-text="第一页" data-pagination-pre-text="上页"
		data-pagination-next-text="下页" data-pagination-last-text="最后页">
		<thead>
			<tr>
				<th data-field="create_time"   data-align="center">采集时间</th>
				<th data-field="height" data-align="center">身高cm</th>
				<th data-field="weight" data-align="center">体重kg</th>
				<th data-field="bmi" data-align="center">BMI</th>
				<th data-field="oxygen_content" data-align="center">含氧量</th>
				<th data-field="shoulder" data-align="center">肩胛脂</th>
				<th data-field="haunch" data-align="center">大臂脂</th>
				<th data-field="chest" data-align="center">胸围cm</th>
				<th data-field="waist" data-align="center">腰围cm</th>
				<th data-field="morning_pulse" data-align="center">晨脉</th>
				<th data-field="lactate" data-align="center">乳酸</th>
				<th data-field="waistfat" data-align="center">腰围脂</th>
				<th data-field="id"  data-formatter="actionFormatter"  data-align="center">操作</th>
			</tr>
		</thead>
	</table>
	
	<div style="height: 20px;width: 100%"></div>
	
	<div class="row">
	<div class="col-md-12">
		<div id="contain_height" class="col-md-6">
			<sa-panel title="身高">
					<div class="panel-body col-md-12" >
						<div class="col-md-3 timeLabel">时间段</div>
						<div class="col-md-4" style="width: 37%;">
						    <div class="input-group">
						     	<input type="text" class="form-control start-datepicker calendar-input" readonly id="start-datepicker-1" datepickerindex="1"  datahold="height" >
						      	<div class="input-group-addon calendar-icon"><span class="glyphicon glyphicon-calendar major_color"></span></div>
						    </div>
						</div>
						<div style="float:left">-</div>
						<div class="col-md-4" style="width: 37%;">
						    <div class="input-group">
						     	<input type="text" class="form-control end-datepicker calendar-input" readonly id="end-datepicker-1" datepickerindex="1" datahold="height" >
						      	<div class="input-group-addon calendar-icon"><span class="glyphicon glyphicon-calendar major_color"></span></div>
						    </div>
						</div>
						<div class="clearfix"></div>
						<div id="chart_height" style="width:100%; height:400px; margin: 0 auto">
						</div>
	  			  </div>
	  	</sa-panel>
	   </div>
	   <div id="contain_weight" class="col-md-6">
	   		<sa-panel title="体重">
					<div class="panel-body col-md-12" >
						<div class="col-md-3 timeLabel" >时间段</div>
						<div class="col-md-4" style="width: 37%;">
						    <div class="input-group">
						     	<input type="text" class="form-control start-datepicker calendar-input" readonly id="start-datepicker-2" datepickerindex="2" datahold="weight" >
						      	<div class="input-group-addon calendar-icon"><span class="glyphicon glyphicon-calendar major_color"></span></div>
						    </div>
						</div>
						<div style="float:left">-</div>
						<div class="col-md-4" style="width: 37%;">
						    <div class="input-group">
						     	<input type="text" class="form-control end-datepicker calendar-input" readonly id="end-datepicker-2" datepickerindex="2" datahold="weight">
						      	<div class="input-group-addon calendar-icon"><span class="glyphicon glyphicon-calendar major_color"></span></div>
						    </div>
						</div>
						<div class="clearfix"></div>
						<div id="chart_weight" style="width:100%; height:400px; margin: 0 auto">
						</div>
	  			  </div>
	  		</sa-panel>
	   </div>
   </div>
   </div>
   <div class="row">
   <div class="col-md-12">
		<div id="contain_bmi" class="col-md-6">
			<sa-panel title="BMI">
					<div class="panel-body col-md-12" >
						<div class="col-md-3 timeLabel" >时间段</div>
						<div class="col-md-4" style="width: 37%;">
						    <div class="input-group">
						     	<input type="text" class="form-control start-datepicker calendar-input" readonly id="start-datepicker-3" datepickerindex="3" datahold="bmi">
						      	<div class="input-group-addon calendar-icon"><span class="glyphicon glyphicon-calendar major_color"></span></div>
						    </div>
						</div>
						<div style="float:left">-</div>
						<div class="col-md-4" style="width: 37%;">
						    <div class="input-group">
						     	<input type="text" class="form-control end-datepicker calendar-input" readonly id="end-datepicker-3" datepickerindex="3" datahold="bmi">
						      	<div class="input-group-addon calendar-icon"><span class="glyphicon glyphicon-calendar major_color"></span></div>
						    </div>
						</div>
						<div class="clearfix"></div>
						<div id="chart_bmi" style="width:100%; height:400px; margin: 0 auto">
						</div>
	  			  </div>
	  		</sa-panel>
	   </div>
	   <div id="contain_oxygen_content" class="col-md-6">
	   		<sa-panel title="含氧量">
					<div class="panel-body col-md-12" >
						<div class="col-md-3 timeLabel" >时间段</div>
						<div class="col-md-4" style="width: 37%;">
						    <div class="input-group">
						     	<input type="text" class="form-control start-datepicker calendar-input" readonly id="start-datepicker-4" datepickerindex="4" datahold="oxygen_content">
						      	<div class="input-group-addon calendar-icon"><span class="glyphicon glyphicon-calendar major_color"></span></div>
						    </div>
						</div>
						<div style="float:left">-</div>
						<div class="col-md-4" style="width: 37%;">
						    <div class="input-group">
						     	<input type="text" class="form-control end-datepicker calendar-input" readonly id="end-datepicker-4" datepickerindex="4" datahold="oxygen_content">
						      	<div class="input-group-addon calendar-icon"><span class="glyphicon glyphicon-calendar major_color"></span></div>
						    </div>
						</div>
						<div class="clearfix"></div>
						<div id="chart_oxygen_content" style="width:100%; height:400px; margin: 0 auto">
						</div>
	  			  </div>
	  		</sa-panel>
	   </div>
   </div>
   </div>
   <div class="row">
   <div class="col-md-12">
		<div id="contain_shoulder"  class="col-md-6">
		<div class="all_container as_panel_container" >
		<div class="header as_panel_header" >
			<div class="btn-group" style="margin-top:3px;">
				  <button type="button" class=" btnMenu dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
				    <div id="formationTypeBtn" style="float: left;margin-right: 10px;font-size: 16px;">肩胛脂</div><span class="caret"></span>
				  </button>
				  <ul class="dropdown-menu">
						<li class="formationLi" style="color:#333;">
							<a href="javascript:void(0);" onclick="changeJBchart(this);" val="shoulder">肩胛脂</a>
						</li>
						<li class="formationLi">
							<a href="javascript:void(0);" onclick="changeJBchart(this);" val="haunch">大臂脂</a>
						</li>
						<li class="formationLi">
							<a href="javascript:void(0);" onclick="changeJBchart(this);" val="waistfat">腰围脂</a>
						</li>
				  </ul>
				</div>
		</div>
			<div class="panel-body col-md-12" >
					<div class="panel-body col-md-12" >
						<div class="col-md-3 timeLabel" >时间段</div>
						<div class="col-md-4" style="width: 37%;">
						    <div class="input-group">
						     	<input type="text" class="form-control start-datepicker calendar-input" readonly id="start-datepicker-5" datepickerindex="5"  datahold="shoulder,haunch" >
						      	<div class="input-group-addon calendar-icon"><span class="glyphicon glyphicon-calendar major_color"></span></div>
						    </div>
						</div>
						<div style="float:left">-</div>
						<div class="col-md-4" style="width: 37%;">
						    <div class="input-group">
						     	<input type="text" class="form-control end-datepicker calendar-input" readonly id="end-datepicker-5" datepickerindex="5" datahold="shoulder,haunch" >
						      	<div class="input-group-addon calendar-icon"><span class="glyphicon glyphicon-calendar major_color"></span></div>
						    </div>
						</div>
						<div class="clearfix"></div>
						<div id="chart_shoulder" style="width:100%; height:400px; margin: 0 auto">
						</div>
	  			  </div>
	  			  </div>
	  			  </div>
	  			  
	   </div>
	   <div id="contain_chest" class="col-md-6">
	   
	   <div class="all_container as_panel_container" >
		<div class="header as_panel_header">
			<div class="btn-group" style="margin-top:3px;">
				  <button type="button" class=" btnMenu dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
				    <div id="formationXYTypeBtn" style="float: left;margin-right: 10px;font-size: 16px;">胸围</div><span class="caret"></span>
				  </button>
				  <ul class="dropdown-menu">
						<li class="formationLi" style="color:#333;">
							<a href="javascript:void(0);" onclick="changeXYchart(this);" val="chest">胸围</a>
						</li>
						<li class="formationLi">
							<a href="javascript:void(0);" onclick="changeXYchart(this);" val="waist">腰围</a>
						</li>
				  </ul>
				</div>
		
		</div>
	   
		<div class="panel-body col-md-12" >
					<div class="panel-body col-md-12" >
						<div class="col-md-3 timeLabel" >时间段</div>
						<div class="col-md-4" style="width: 37%;">
						    <div class="input-group">
						     	<input type="text" class="form-control start-datepicker calendar-input"  readonly id="start-datepicker-6" datepickerindex="6" datahold="chest,waist">
						      	<div class="input-group-addon calendar-icon"><span class="glyphicon glyphicon-calendar major_color"></span></div>
						    </div>
						</div>
						<div style="float:left">-</div>
						<div class="col-md-4" style="width: 37%;">
						    <div class="input-group">
						     	<input type="text" class="form-control end-datepicker calendar-input" readonly id="end-datepicker-6" datepickerindex="6" datahold="chest,waist">
						      	<div class="input-group-addon calendar-icon"><span class="glyphicon glyphicon-calendar major_color"></span></div>
						    </div>
						</div>
						<div class="clearfix"></div>
						<div  id="chart_chest" style="width:100%; height:400px; margin: 0 auto">
						</div>
	  			  </div>
	  			  </div>
	  			  </div>
	   </div>
   </div>
   </div>
   <div class="row">
   <div class="col-md-12">
		<div id="contain_morning_pulse" class="col-md-6">
			<sa-panel title="晨脉">
					<div class="panel-body col-md-12" >
						<div class="col-md-3 timeLabel" >时间段</div>
						<div class="col-md-4" style="width: 37%;">
						    <div class="input-group">
						     	<input type="text" class="form-control start-datepicker calendar-input" readonly id="start-datepicker-7" datepickerindex="7" datahold="morning_pulse">
						      	<div class="input-group-addon calendar-icon"><span class="glyphicon glyphicon-calendar major_color"></span></div>
						    </div>
						</div>
						<div style="float:left">-</div>
						<div class="col-md-4" style="width: 37%;">
						    <div class="input-group">
						     	<input type="text" class="form-control end-datepicker calendar-input" readonly id="end-datepicker-7" datepickerindex="7" datahold="morning_pulse">
						      	<div class="input-group-addon calendar-icon"><span class="glyphicon glyphicon-calendar major_color"></span></div>
						    </div>
						</div>
						<div class="clearfix"></div>
						<div id="chart_morning_pulse" style="width:100%; height:400px; margin: 0 auto">
						</div>
	  			  </div>
	  		</sa-panel>
	   </div>
	   <div id="contain_lactate"  class="col-md-6">
	   		<sa-panel title="乳酸">
					<div class="panel-body col-md-12" >
						<div class="col-md-3 timeLabel" >时间段</div>
						<div class="col-md-4" style="width: 37%;">
						    <div class="input-group">
						     	<input type="text" class="form-control start-datepicker calendar-input" readonly id="start-datepicker-8" datepickerindex="8"  datahold="lactate">
						      	<div class="input-group-addon calendar-icon"><span class="glyphicon glyphicon-calendar major_color"></span></div>
						    </div>
						</div>
						<div style="float:left">-</div>
						<div class="col-md-4" style="width: 37%;">
						    <div class="input-group">
						     	<input type="text" class="form-control end-datepicker calendar-input" readonly id="end-datepicker-8" datepickerindex="8" datahold="lactate">
						      	<div class="input-group-addon calendar-icon"><span class="glyphicon glyphicon-calendar major_color"></span></div>
						    </div>
						</div>
						<div class="clearfix"></div>
						<div  id="chart_lactate"  style="width:100%; height:400px; margin: 0 auto">
						</div>
	  			  </div>
	  		</sa-panel>
	   </div>
   </div>
   </div>
   
   <input type="hidden" id="JBchart" value="shoulder">
   <input type="hidden" id="XYchart" value="chest">
   
   <form id="expexcel" method="post" action="<%=request.getContextPath()%>/healthdata/exportexecl/${id}">
   
   
   </form>