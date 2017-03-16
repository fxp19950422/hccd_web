<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="xss" uri="http://www.sportsdata.cn/xss"%>
<%@ page import="cn.sportsdata.webapp.youth.common.utils.CommonUtils" %>
<%@ page import="cn.sportsdata.webapp.youth.common.vo.TacticsTypeVO" %>
<%@ page import="java.util.List" %>
<%@ page import="com.fasterxml.jackson.databind.ObjectMapper" %>
<%@ page import="com.fasterxml.jackson.core.JsonProcessingException" %>

<%
	String serverUrl = CommonUtils.getServerUrl(request);
%>

<style>
.list-body {
	border-bottom: 1px solid #cecece;
	border-bottom-right-radius: 3px;
	border-bottom-left-radius: 3px;
	border-left: 1px solid #cecece;
	border-right: 1px solid #cecece;
	padding-bottom: 3px;
}

</style>

<div class="button_area" id="button_area">
	<div>
		<button id="add_btn" class="btn btn-primary" style="float: right;">添加</button>
	</div>
</div>
<div class="clearfix"></div>
<div class="tacticsListArea" id="tacticsListArea">
	<div class="panel panel-default">
		<div class="panel-heading" style="background-color:#067DC2;color:white">
			<div style="float: left;line-height:16px;">战术板</div>
			<div style="float:right;margin-right:10px;line-height:16px;">
				<div class="btn-group">
				  <button type="button" class="dropdown-btn dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
				    <div id="tacticsTypeBtn" style="float: left;margin-right: 10px;">Action</div><span class="caret"></span>
				  </button>
				  <ul class="dropdown-menu">
					<c:forEach items="${tacticsTypeList}" var="tacticsType" varStatus="status">
						<script type="text/javascript">
							var isChecked = '${tacticsType.checked}';
							var name = '${tacticsType.name}';
							if (isChecked == 'true') {
								$('#tacticsTypeBtn').html(name);
							}
						</script>
						<li class="tacticsTypeLi" tid="${tacticsType.id}" type_name="${tacticsType.name}">
							<a href="#"><xss:xssFilter text="${tacticsType.name}" filter="html"/></a>
						</li>
					</c:forEach>
				  </ul>
				</div>
			</div>
			<div class="clearfix"></div>
		</div>
		<div class="list-body">
		<c:forEach items="${tacticsList}" var="tactics" varStatus="status">
			<div class="tacticsCard" tid="${ tactics.id }">
				<c:choose>
					<c:when test="${ !empty tactics.imgUrl }">
						<img class="tacticsAvatar" src="${ tactics.imgUrl }"></img>
					</c:when>
					<c:otherwise>
						<img class="tacticsAvatar"
							src="<%=serverUrl%>resources/images/user_avatar.png"></img>
					</c:otherwise>
				</c:choose>
				<div class="tacticsType">${ tactics.tacticsType.name }</div>
				<div class="tacticsName" style="color: #067dc2;">${ tactics.name }</div>
				<div class="tacticsData">
					<c:choose>
						<c:when test="${ !empty tactics.description }">
									<textarea class="tacticsShowInput" id="tacticsMessage"
													name="tacticsMessage" readonly="readonly" style="cursor:pointer;resize:none;"><xss:xssFilter text="${tactics.description}" filter="html"/></textarea>
								</c:when>
						<c:otherwise>
									<textarea class="tacticsShowInput" id="tacticsMessage"
													name="tacticsMessage" readonly="readonly" style="cursor:pointer;resize:none;">暂无描述</textarea>
								</c:otherwise>
					</c:choose>
				</div>
				<div class="tacticsCreateTime" style="float:left">创建时间：${ tactics.created_time }</div>
				<div class="tacticsCreateTime" style="float:left; margin-left:20px">上次更新：${ tactics.last_update }</div>
			</div>
			<c:if test="${!status.last}">
				<div style="height: 1px; border-bottom: 1px solid #cecece; padding-left: -15px; padding-right: -15px;"></div>
			</c:if>
			
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
		$(document).off('click', 'li.tacticsTypeLi');
		$(document).on('click', 'li.tacticsTypeLi', function() {
			var $dom = $(this);
			$.ajax({
				type : "get",
				url : "<%=serverUrl%>system_tool/showTacticsListDetail"+"?tactics_type_id="+$dom.attr('tid'),
				success : function(data) {
					AngularHelper.Compile($('#content'), data);
				},
				error: function() {
					alert("获取定位球设置列表失败");
				}
			});
		});
		
		$(document).off('click', 'div.tacticsCard');
		$(document).on('click', 'div.tacticsCard', function() {
			var $dom = $(this);
			
			sa.ajax({
				type : "get",
				url : "<%=serverUrl%>system_tool/showTacticsEdit?tacticsID=" + $dom.attr('tid'),
				success : function(data) {
					//TODO: will update the container later
					$('html,body').scrollTop(0);
					AngularHelper.Compile($('#content'), data);
				},
				error: function() {
					alert("打开战术板页面失败");
				}
			});
		});
		
		$('#add_btn').off('click');
		$('#add_btn').on('click', function() {
			sa.ajax({
				type : "get",
				url : "<%=serverUrl%>system_tool/showTacticsEdit",
				success : function(data) {
					//TODO: will update the container later
					AngularHelper.Compile($('#content'), data);
				},
				error: function() {
					alert("打开创建战术板页面失败");
				}
			});
		});
		
		
		function tacticsTypeChanged(type){
			alert(type);
		}
	}
</script>