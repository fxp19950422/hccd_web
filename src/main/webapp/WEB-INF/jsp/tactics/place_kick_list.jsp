<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="xss" uri="http://www.sportsdata.cn/xss"%>
<%@ page import="cn.sportsdata.webapp.youth.common.utils.CommonUtils" %>
<%@ page import="java.util.List" %>
<%@ page import="com.fasterxml.jackson.databind.ObjectMapper" %>
<%@ page import="com.fasterxml.jackson.core.JsonProcessingException" %>

<%
	String serverUrl = CommonUtils.getServerUrl(request);
%>

<style type="text/css">
.placeKickCard {
	float: left;
	width: 267px;
	height: 220	px; 
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
<div class="placeKickListArea" id="placeKickListArea">
	<div class="panel panel-default">
		<div class="panel-heading" style="background-color:#067DC2;color:white">
			<div style="float: left;line-height:16px;">定位球</div>
			<div style="float:right;margin-right:10px;line-height:16px;">
				<div class="btn-group">
				  <button type="button" class="dropdown-btn dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
				    <div id="placeKickTypeBtn" style="float: left;margin-right: 10px;">Action</div><span class="caret"></span>
				  </button>
				  <ul class="dropdown-menu">
					<c:forEach items="${placeKickTypeList}" var="placeKickType" varStatus="status">
						<script type="text/javascript">
							var isChecked = '${placeKickType.checked}';
							var name = '${placeKickType.name}';
							if (isChecked == 'true') {
								$('#placeKickTypeBtn').html(name);
							}
						</script>
						<li class="placeKickTypeLi" tid="${placeKickType.id}" type_name="${placeKickType.name}">
							<a href="#"><xss:xssFilter text="${placeKickType.name}" filter="html"/></a>
						</li>
					</c:forEach>
				  </ul>
				</div>
			</div>
			<div class="clearfix"></div>
		</div>
		<div class="panel-body table-responsive">
			<c:forEach items="${placeKickList}" var="placeKick">
				<div class="placeKickCard" tid="${ placeKick.id }">
	
						<c:choose>
							<c:when test="${ !empty placeKick.imgUrl }">
								<img class="starterAvator" src="${ placeKick.imgUrl }"></img>
							</c:when>
							<c:otherwise>
								<img class="starterAvator"
									src="<%=serverUrl%>resources/images/user_avatar.png"></img>
							</c:otherwise>
						</c:choose>
						<div id="placeKickTitle" class="textDiv" title="${ placeKick.name }" style="width: 200px; margin-left: 30px;text-align: center;
							margin-top: 15px; font-size: 18px; color: #333333;"><xss:xssFilter text="${ placeKick.name }" filter="html"/></div>
				</div>
			</c:forEach> 
		</div>
		
	</div>
</div>

<script type="text/javascript">

    $(function () {
    	setTimeout(function(){
    		initEvent();
    	},50);
    	$(window).resize(function() {
    		
    	});
    });
    
	function initEvent() {
		$(document).off('click', 'li.placeKickTypeLi');
		$(document).on('click', 'li.placeKickTypeLi', function() {
			var $dom = $(this);
			$.ajax({
				type : "get",
				url : "<%=serverUrl%>placeKick/showPlaceKickList"+"?place_kick_type_id="+$dom.attr('tid'),
				success : function(data) {
					AngularHelper.Compile($('#content'), data);
				},
				error: function() {
					alert("获取定位球设置列表失败");
				}
			});
		});
		
		$(document).off('click', 'div.placeKickCard');
		$(document).on('click', 'div.placeKickCard', function() {
			var $dom = $(this);
			sa.ajax({
				type : "get",
				url : "<%=serverUrl%>placeKick/showPlaceKickView?placeKickId=" + $dom.attr('tid'),
				success : function(data) {
					//TODO: will update the container later
					$('html,body').scrollTop(0);
					AngularHelper.Compile($('#content'), data);
				},
				error: function() {
					alert("打开定位球设置失败");
				}
			});
		});
		
		$('#add_btn').click(function() {
			sa.ajax({
				type : "get",
				url : "<%=serverUrl%>placeKick/showPlaceKickEdit",
				success : function(data) {
					//TODO: will update the container later
					AngularHelper.Compile($('#content'), data);
				},
				error: function() {
					alert("打开创建定位球页面失败");
				}
			});
		});
		
		
		function tacticsTypeChanged(type){
			alert(type);
		}
	}
</script>