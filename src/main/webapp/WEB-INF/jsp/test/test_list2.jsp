<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page import="cn.sportsdata.webapp.youth.common.utils.CommonUtils" %>

<%
	String serverUrl = CommonUtils.getServerUrl(request);
%>

<section class="top_control_area">
	<label class="radio-inline"> 
		<input type="radio" name="testViewMode" class="modelRadio" value="byBatch" <c:if test="${byBatch}">checked</c:if>> 按批次查看
	</label> 
	<label class="radio-inline"> 
		<input type="radio" name="testViewMode" class="modelRadio" value="byPlayper" <c:if test="${!byBatch}">checked</c:if>> 按人员查看
	</label> 
	<button id="add_btn" class="btn btn-primary test-btn-right">添加测试成绩</button>
	<div class="clearfix"></div>
</section>

<section class="profileListArea testBatchList hidden">
	<div class="panel panel-default"> 
		<div class="panel-heading text-left ng-binding" style="background-color:#067DC2;color:white">测试管理</div>
		<div class="panel-body table-responsive">
			<div id="testBatchControlArea" style="padding-left: 15px;margin-bottom: 20px">
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<div class="input-group-addon"><span class="glyphicon glyphicon-search"></span></div>
					     	<input type="text" class="form-control" id="selected_test_item_name" placeholder="请输入测试名称">
					    </div>
					</div>
					<div class="col-md-1 text-right" style="height:35px;line-height: 35px">时间段</div>
					<div class="col-md-2">
					    <div class="input-group">
					     	<input type="text" class="form-control calendar-input" readonly id="selected-start-datepicker">
					      	<div class="input-group-addon datepicker-addon calendar-icon"><span class="glyphicon glyphicon-calendar major_color"></span></div>
					    </div>
					</div>
					<div style="float:left">-</div>
					<div class="col-md-2">
					    <div class="input-group">
					     	<input type="text" class="form-control calendar-input" readonly id="selected-end-datepicker">
					      	<div class="input-group-addon datepicker-addon calendar-icon"><span class="glyphicon glyphicon-calendar major_color"></span></div>
					    </div>
					</div>
					<div class="col-md-1">
						<button id="search_btn" class="btn btn-primary">查询</button>
					</div>
					<div class="clearfix"></div>
				</div>
			</div>
			<div class="testCategoryItemDivider"></div>
			<div>
				<div id="testBatchResultEmptyTips" class="hidden text-center" style="margin-top:55px;margin-bottom: 45px">范围内未找到测试信息</div>
				<div id="testBatchSearchingTips" class="hidden text-center" style="margin-top:55px;margin-bottom: 45px">正在搜索中 , 请稍后...</div>
				<div id ="testBatchDisplayArea"></div>
			</div>
		</div>
	</div>
</section>
<section class="profileListArea testPlayerList hidden">
	<sa-panel-card title="前锋">
		<c:forEach items="${renderData.forwardList}" var="player">
			<div class="profileCard" uid="${ player.id }">
				<c:choose>
					<c:when test="${ !empty player.avatar }">
						<img class="profileAvatar" src="<%=serverUrl%>file/downloadFile?fileName=${ player.avatar }"></img>
					</c:when>
					<c:otherwise>
						<img class="profileAvatar" src="<%=serverUrl%>resources/images/user_avatar.png"></img>
					</c:otherwise>
				</c:choose>
				
				<div class="profileName">
					<c:choose>
						<c:when test="${ !empty player.name }">
							<c:out value="${ player.name }"></c:out>
						</c:when>
						<c:otherwise>
							暂无姓名
						</c:otherwise>
					</c:choose>
				</div>
				<div class="profileData">
					<c:choose>
						<c:when test="${ !empty player.tel }">
							${ player.tel }
						</c:when>
						<c:otherwise>
							暂无联系方式
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</c:forEach>
	</sa-panel-card>
	<sa-panel-card title="中场">
		<c:forEach items="${renderData.centerList}" var="player">
			<div class="profileCard" uid="${ player.id }">
				<c:choose>
					<c:when test="${ !empty player.avatar }">
						<img class="profileAvatar" src="<%=serverUrl%>file/downloadFile?fileName=${ player.avatar }"></img>
					</c:when>
					<c:otherwise>
						<img class="profileAvatar" src="<%=serverUrl%>resources/images/user_avatar.png"></img>
					</c:otherwise>
				</c:choose>
				
				<div class="profileName"><c:out value="${ player.name }"></c:out></div>
				<div class="profileData">
					<c:choose>
						<c:when test="${ !empty player.tel }">
							${ player.tel }
						</c:when>
						<c:otherwise>
							暂无联系方式
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</c:forEach>
	</sa-panel-card>
	<sa-panel-card title="后卫">
		<c:forEach items="${renderData.defenderList}" var="player">
			<div class="profileCard" uid="${ player.id }">
				<c:choose>
					<c:when test="${ !empty player.avatar }">
						<img class="profileAvatar" src="<%=serverUrl%>file/downloadFile?fileName=${ player.avatar }"></img>
					</c:when>
					<c:otherwise>
						<img class="profileAvatar" src="<%=serverUrl%>resources/images/user_avatar.png"></img>
					</c:otherwise>
				</c:choose>
				
				<div class="profileName"><c:out value="${ player.name }"></c:out></div>
				<div class="profileData">
					<c:choose>
						<c:when test="${ !empty player.tel }">
							${ player.tel }
						</c:when>
						<c:otherwise>
							暂无联系方式
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</c:forEach>
	</sa-panel-card>
	<sa-panel-card title="门将">
		<c:forEach items="${renderData.goalKeeperList}" var="player">
			<div class="profileCard" uid="${ player.id }">
				<c:choose>
					<c:when test="${ !empty player.avatar }">
						<img class="profileAvatar" src="<%=serverUrl%>file/downloadFile?fileName=${ player.avatar }"></img>
					</c:when>
					<c:otherwise>
						<img class="profileAvatar" src="<%=serverUrl%>resources/images/user_avatar.png"></img>
					</c:otherwise>
				</c:choose>
				
				<div class="profileName"><c:out value="${ player.name }"></c:out></div>
				<div class="profileData">
					<c:choose>
						<c:when test="${ !empty player.tel }">
							${ player.tel }
						</c:when>
						<c:otherwise>
							暂无联系方式
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</c:forEach>
	</sa-panel-card>
</section>

<div id="hiddenTemplate" class="hidden">
	<div class="testBatchCard" stid = "">
		<div class="testBatchCardItem testBatchItemName"></div>
		<div class="testBatchCardItem testBatchCategory"></div>
		<div class="testBatchCardItem testBatchTime"></div>
		<div class="testBatchCardItem testBatchCreator"></div>
	</div>

</div>

<script type="text/javascript">
	var UIController = (function(self){
		self.init = function(){
			this.dynamicRadioUIChange();
			this.initDatePicker();
			EventController.loadByBatchData();
		};
		self.dynamicRadioUIChange = function(){
			var byOption = $('input.modelRadio:radio:checked').val();
			if("byBatch" == byOption){
				$("section.testPlayerList").addClass("hidden");
				$("section.testBatchList").removeClass("hidden");
			}else{
				$("section.testBatchList").addClass("hidden");
				$("section.testPlayerList").removeClass("hidden");
			}
		};
		self.initDatePicker = function(){
			$("#selected-end-datepicker").datepicker({
				format : "yyyy-mm-dd",
				language : "zh-CN",
				autoclose : true,
				todayHighlight : true,
				toggleActive : true
			});
			$("#selected-end-datepicker").datepicker('setEndDate', '+0d');//最多选择今天
			$("#selected-end-datepicker").datepicker('setDate', '+0d');//默认今天
			$("#selected-end-datepicker").datepicker().on('changeDate', function(ev){
				var pairStartDatePicker = $("#selected-start-datepicker");
				
				var largestStartDate = new Date(ev.date)
				largestStartDate.setDate(largestStartDate.getDate() -1);
				
				var curStartDate = new Date(pairStartDatePicker.datepicker('getDate'));
				if(curStartDate.getTime() > largestStartDate.getTime()){
					pairStartDatePicker.datepicker('setDate', largestStartDate);
				}	
				pairStartDatePicker.datepicker('setEndDate', largestStartDate); 
			});
			
			$("#selected-start-datepicker").datepicker({
				format : "yyyy-mm-dd",
				language : "zh-CN",
				autoclose : true,
				todayHighlight : true,
				toggleActive : true
			});
			$("#selected-start-datepicker").datepicker('setEndDate', '-1d'); //最多选择昨天
			$("#selected-start-datepicker").datepicker('setDate', '-30d');//默认起始天30天前
			
			$("div.datepicker-addon").click(function(){
				$(this).prev().datepicker('show');
			});
		}
		return self;
	})(UIController || {});
	
	var EventController = (function(self){
		self.init = function(){
			buildBreadcumb();
			$('input.modelRadio:radio').change(function(){
				UIController.dynamicRadioUIChange();
			}); 
			$('section.testBatchList').on('click', 'div.testBatchCard', function() {
				EventController.toAddEditPage($(this).attr('stid'));
			});
			$('#add_btn').click(function(){
				EventController.toAddEditPage();
			});
			$('section.testPlayerList').on('click', 'div.profileCard', function() {
				sa.ajax({
					type : "get",
					url : "<%=serverUrl%>test/test_info_player_ui?userID=" + $(this).attr('uid'),
					success : function(data) {
						AngularHelper.Compile($('#content'), data);
					},
					error: function() {
						alert("获取球员测试数据失败");
					}
				});
			});
			$("#search_btn").click(function(){
				EventController.loadByBatchData();
			});
			
			$(document).keypress(function(e) {  
			    // 回车键事件  
			    if(e.which == 13) {  
			    	if(!$("#search_btn").attr("disabled")){
			    		EventController.loadByBatchData();
			    	}
			    }  
			 }); 
			
		};
		
		self.toAddEditPage = function(sid){
			var url = "<%=serverUrl%>test/test_addedit";
			if(!!sid){
				url = url + "?sid=" + sid;
			}
			sa.ajax({
				type : "get",
				url : url,
				success : function(data) {
					AngularHelper.Compile($('#content'), data);
				},
				error: function() {
					alert("编辑测试数据页面打开失败");
				}
			});
		};
		
		self.loadByBatchData = function(){
			$("#search_btn").attr("disabled","disabled");
			$("#testBatchResultEmptyTips").addClass("hidden");
			$("#testBatchDisplayArea").empty();
			$("#testBatchSearchingTips").removeClass("hidden");
			var selected_test_item_name = $("#selected_test_item_name").val();
			var selected_start_date = $("#selected-start-datepicker").val();
			var selected_end_date = $("#selected-end-datepicker").val();
			
			var url = ["<%=serverUrl%>test/test_batch?siname=",selected_test_item_name,"&start=",selected_start_date,"&end=",selected_end_date].join("");
			
			$.ajax({
				type : 'GET',
				url : url,
				dataType : 'json',
				success : function(message) {
					$("#search_btn").removeAttr("disabled");
					$("#testBatchSearchingTips").addClass("hidden");
					if(!!message && message.length > 0){
						$.each(message, function(index, item){
							var jdom = $("#hiddenTemplate").children(".testBatchCard").clone(false);
							jdom.attr("stid",item.id);
							jdom.find(".testBatchItemName").text(item.test_item_title);
							jdom.find(".testBatchCategory").text(item.test_category_name);
							var date = new Date(item.test_time);
							var formatDate = [date.getFullYear(),"年",date.getMonth() + 1,"月",date.getDate(),"日"].join("")
							jdom.find(".testBatchTime").text(formatDate);
							jdom.find(".testBatchCreator").text(item.creator_name);
							jdom.appendTo($("#testBatchDisplayArea"));
						});
					}else{
						$("#testBatchResultEmptyTips").removeClass("hidden");
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					$("#search_btn").removeAttr("disabled");
					$("#testBatchSearchingTips").addClass("hidden");
					$("#testBatchResultEmptyTips").removeClass("hidden");
				}
			});
			
			
		};
		return self;
	})(EventController || {});

	$(function() {
		UIController.init();
		EventController.init();
	});
	
</script>