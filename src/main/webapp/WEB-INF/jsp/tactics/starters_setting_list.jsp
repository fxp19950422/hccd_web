<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="xss" uri="http://www.sportsdata.cn/xss"%>
<%@ page import="cn.sportsdata.webapp.youth.common.utils.CommonUtils" %>
<%@ page import="cn.sportsdata.webapp.youth.common.vo.TacticsTypeVO" %>
<%@ page import="cn.sportsdata.webapp.youth.common.vo.starters.StartersPreviewsVO" %>
<%@ page import="java.util.List" %>
<%@ page import="com.fasterxml.jackson.databind.ObjectMapper" %>
<%@ page import="com.fasterxml.jackson.core.JsonProcessingException" %>

<%
	String serverUrl = CommonUtils.getServerUrl(request);
%>

<style type="text/css">
.starterCard {
	float: left;
	width: 267px;
	margin: 0px 15px 20px 0px;
	background-color: #ffffff;
    cursor: pointer;
    float:left;
}

.starterAvator {
    width: 267px;
    height: 170px;
    -moz-border-radius: 2px;
    -webkit-border-radius: 2px;
    border-radius: 2px;
    border: 1px solid #cecece;
}

.aligncenter { 
	clear:both;
	margin: auto 0;
	text-align: center;
} 

.starterRecentRecord {
	float:left;
	width: 30px;
	line-height:14px;
	font-size:14px;
	color: #067dc2;
	text-align: center;
}

.starterRecords {
	float:left;
	line-height:16px;
	text-align: center;
}

.textDiv {
	overflow:hidden;
	text-overflow: ellipsis;
	white-space: nowrap;
}
</style>

<div class="button_area" id="button_area">
	<div>
		<button id="add_btn" class="btn btn-primary" style="float: right;">添加</button>
	</div>
</div>
<div class="clearfix" style="height: 15px;"></div>
<div class="startersSettingListArea" id="startersSettingListArea">
	<div class="panel panel-default">
		<div class="panel-heading" style="background-color:#067DC2;color:white">
			<div style="float: left;line-height:16px;">首发</div>
			<div style="float:right;margin-right:10px;line-height:16px;">
				<div class="btn-group">
				  <button type="button" class="dropdown-btn dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
				    <div id="formationTypeBtn" style="float: left;margin-right: 10px;">首发阵型</div><span class="caret"></span>
				  </button>
				  <ul class="dropdown-menu">
					<c:forEach items="${formationTypeList}" var="formationType" varStatus="status">
						<script type="text/javascript">
							var isChecked = '${formationType.checked}';
							var name = '${formationType.name}';
							if (isChecked == 'true') {
								$('#formationTypeBtn').html(name);
							}
						</script>
						<li class="formationLi" tid="${formationType.id}" type_name="${formationType.name}">
							<a href="#"><xss:xssFilter text="${formationType.name}" filter="html"/></a>
						</li>
					</c:forEach>
				  </ul>
				</div>
			</div>
			<div class="clearfix"></div>
		</div>
		<div class="panel-body table-responsive">
			<c:forEach items="${startersList}" var="starters">
				<div class="starterCard" tid="${ starters.tacticsId }">
						<c:choose>
							<c:when test="${ !empty starters.imgUrl }">
								<img class="starterAvator" src="${ starters.imgUrl }"></img>
							</c:when>
							<c:otherwise>
								<img class="starterAvator"
									src="<%=serverUrl%>resources/images/user_avatar.png"></img>
							</c:otherwise>
						</c:choose>
						<div id="starterTitle" class="textDiv" title="${starters.name}" style="width: 60%;margin-top: 15px; font-size: 18px; color: #333333;"><xss:xssFilter text="${ starters.name }" filter="html"/></div>
						<div style="margin-top: 15px; height: 16px;">
							<div class="starterRecords textDiv" style="font-size: 12px; color: #999999; width: 60px;">使用次数：</div>
							<div class="starterRecords textDiv" style="font-size:16px;color:#fc6b8a; width: 40px;">${ starters.usedCount }次</div>
							<div style="height: 16px; float: left; background-color: #dddddd; width: 100px; border-radius: 8px;">
								<div class="starterRecords textDiv" style="font-size:10px;color:#333333; width: 30px; margin-left: 8px;">${ starters.winCount }胜</div>
								<div class="starterRecords textDiv" style="font-size:10px;color:#333333; width: 30px;">${ starters.drawCount }平</div>
								<div class="starterRecords textDiv" style="font-size:10px;color:#333333; width: 30px;">${ starters.loseCount }负</div>
							</div>
						</div>
						<div class="starterRecents"  style="margin-top: 5px; heigth: 14px;">
							<div class="textDiv" style="float:left;font-size: 12px; color: #999999; width: 60px;line-height:14px">最新战绩：</div>
							<c:forEach items="${starters.recentRecordList}" var="record">
								<c:choose>
									<c:when test="${record.goalFor gt record.goalAgainst}">
										<div class="starterRecentRecord textDiv">胜</div>
									</c:when>
									<c:when test="${record.goalFor eq record.goalAgainst}">
										<div class="starterRecentRecord textDiv">平</div>
									</c:when>
									<c:when test="${record.goalFor lt record.goalAgainst}">
										<div class="starterRecentRecord textDiv">负</div>
									</c:when>
						            <c:otherwise>－</c:otherwise> 
					            </c:choose>
							</c:forEach> 
						</div>
				</div>
			</c:forEach> 
		</div>
		
	</div>
</div>

<script type="text/javascript">
    $(function () {
    	setTimeout(function(){
    		initEvent();
     		$('#select_formationtype').select2();
     		$('#select_formationtype').change(function(){
    			$.ajax({
    				type : "get",
    				url : "<%=serverUrl%>tactics/showStarterSettings"+"?formation_type_id="+$('#select_formationtype').val(),
    				success : function(data) {
    					AngularHelper.Compile($('#content'), data);
    				},
    				error: function() {
    					alert("获取首发设置列表失败");
    				}
    			});
    	 	});
    	},50);
    	$(window).resize(function() {
    		
    	});
    });
    
	function initEvent() {
		$(document).off('click', 'li.formationLi');
		$(document).on('click', 'li.formationLi', function() {
			var $dom = $(this);
  			$.ajax({
				type : "get",
				url : "<%=serverUrl%>tactics/showStarterSettings"+"?formation_type_id="+$dom.attr('tid'),
				success : function(data) {
					AngularHelper.Compile($('#content'), data);
				},
				error: function() {
					alert("获取首发设置列表失败");
				}
			});
		});
		
		$(document).off('click', 'div.starterCard');
		$(document).on('click', 'div.starterCard', function() {
			var $dom = $(this);
			sa.ajax({
				type : "get",
				url : "<%=serverUrl%>tactics/showStartersView?startersId=" + $dom.attr('tid'),
				success : function(data) {
					//TODO: will update the container later
					$('html,body').scrollTop(0);
					AngularHelper.Compile($('#content'), data);
				},
				error: function() {
					alert("打开首发失败");
				}
			});
		});
		
		$('#add_btn').click(function() {
			sa.ajax({
				type : "get",
				url : "<%=serverUrl%>tactics/showStartersEdit",
				success : function(data) {
					//TODO: will update the container later
					AngularHelper.Compile($('#content'), data);
				},
				error: function() {
					alert("打开创建首发页面失败");
				}
			});
		});
		
		
		function tacticsTypeChanged(type){
			alert(type);
		}
	}
</script>