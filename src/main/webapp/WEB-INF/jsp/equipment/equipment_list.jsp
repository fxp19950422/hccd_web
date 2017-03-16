<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="xss" uri="http://www.sportsdata.cn/xss"%>
<%@ page import="cn.sportsdata.webapp.youth.common.utils.CommonUtils" %>

<%
	String serverUrl = CommonUtils.getServerUrl(request);
%>


<div class="button_area" style="top: 95px;">
	<button id="add_btn" class="btn btn-primary" style="float: right;">添加</button>
</div>
<div class="clearfix" style="height:15px;"></div>
<div class="equipmentList">
	<sa-panel-card title="器材">
		<c:forEach items="${equipmentList}" var="equipment">
			<div class="profileCard" uid="${ equipment.id }">
				<c:choose>
					<c:when test="${ !empty equipment.avatar }">
						<img class="profileAvatar" src="<%=serverUrl%>file/downloadFile?fileName=${ equipment.avatar }"></img>
					</c:when>
					<c:otherwise>
						<img class="profileAvatar" src="<%=serverUrl%>resources/images/user_avatar.png"></img>
					</c:otherwise>
				</c:choose>
				
				<div class="profileName" style="padding-right: 10px; padding-top: 10px;">
					<c:choose>
						<c:when test="${ !empty equipment.name }">
							<xss:xssFilter text="${equipment.name }" filter="html"/>
						</c:when>
						<c:otherwise>
							暂无名称
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</c:forEach>
	</sa-panel-card>
</div>

<script type="text/javascript">
	$(function() {
		initEvent();
	});
	
	function initEvent() {
		buildBreadcumb();
		$('div.equipmentList').on('click', 'div.profileCard', function() {
			var $dom = $(this);
			
			sa.ajax({
				type : "get",
				url : "<%=serverUrl%>equipment/showEquipmentDetail?id=" + $dom.attr('uid'),
				success : function(data) {
					AngularHelper.Compile($('#content'), data);
				},
				error: function() {
					alert("打开器材详情页面失败");
				}
			});
		});
		
		$('#add_btn').click(function() {
			sa.ajax({
				type : "get",
				url : "<%=serverUrl%>equipment/showEquipmentEdit",
				success : function(data) {
					AngularHelper.Compile($('#content'), data);
				},
				error: function() {
					alert("打开创建器材页面失败");
				}
			});
		});
	}
</script>