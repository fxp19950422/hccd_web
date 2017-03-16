<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="xss" uri="http://www.sportsdata.cn/xss"%>
<%@ page import="cn.sportsdata.webapp.youth.common.utils.CommonUtils" %>

<%
	String serverUrl = CommonUtils.getServerUrl(request);
%>
<div class="clearfix"></div>
<div class="profileListArea userPlayerList">
	<sa-panel-card title="前锋">
		<c:forEach items="${forwardList}" var="player">
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
							<xss:xssFilter filter="html" text="${ player.name }"></xss:xssFilter>  
						</c:when>
						<c:otherwise>
							暂无姓名
						</c:otherwise>
					</c:choose>
				</div>
				<div class="profileData">
					<c:choose>
						<c:when test="${ !empty player.tel }">
							<xss:xssFilter filter="html" text="${ player.tel }"></xss:xssFilter>  
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
		<c:forEach items="${centerList}" var="player">
			<div class="profileCard" uid="${ player.id }">
				<c:choose>
					<c:when test="${ !empty player.avatar }">
						<img class="profileAvatar" src="<%=serverUrl%>file/downloadFile?fileName=${ player.avatar }"></img>
					</c:when>
					<c:otherwise>
						<img class="profileAvatar" src="<%=serverUrl%>resources/images/user_avatar.png"></img>
					</c:otherwise>
				</c:choose>
				
				<div class="profileName"> <xss:xssFilter filter="html" text=" ${ player.name }"></xss:xssFilter> </div>
				<div class="profileData">
					<c:choose>
						<c:when test="${ !empty player.tel }">
							<xss:xssFilter filter="html" text="${ player.tel }"></xss:xssFilter>
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
		<c:forEach items="${defenderList}" var="player">
			<div class="profileCard" uid="${ player.id }">
				<c:choose>
					<c:when test="${ !empty player.avatar }">
						<img class="profileAvatar" src="<%=serverUrl%>file/downloadFile?fileName=${ player.avatar }"></img>
					</c:when>
					<c:otherwise>
						<img class="profileAvatar" src="<%=serverUrl%>resources/images/user_avatar.png"></img>
					</c:otherwise>
				</c:choose>
				
				<div class="profileName"><xss:xssFilter filter="html" text="${ player.name }"></xss:xssFilter> </div>
				<div class="profileData">
					<c:choose>
						<c:when test="${ !empty player.tel }">
							<xss:xssFilter filter="html" text="${ player.tel }"></xss:xssFilter>
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
		<c:forEach items="${goalKeeperList}" var="player">
			<div class="profileCard" uid="${ player.id }">
				<c:choose>
					<c:when test="${ !empty player.avatar }">
						<img class="profileAvatar" src="<%=serverUrl%>file/downloadFile?fileName=${ player.avatar }"></img>
					</c:when>
					<c:otherwise>
						<img class="profileAvatar" src="<%=serverUrl%>resources/images/user_avatar.png"></img>
					</c:otherwise>
				</c:choose>
				
				<div class="profileName"><xss:xssFilter filter="html" text=" ${ player.name }"></xss:xssFilter></div>
				<div class="profileData">
					<c:choose>
						<c:when test="${ !empty player.tel }">
							<xss:xssFilter filter="html" text="${ player.tel }"></xss:xssFilter>
						</c:when>
						<c:otherwise>
							暂无联系方式
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
		$('div.userPlayerList').on('click', 'div.profileCard', function() {
			var $dom = $(this);
			var url ="<%=serverUrl%>/healthdata/goonepeoplehealthdata?userID=" + $dom.attr('uid');
			sa.ajax({
				type : "get",
				url : url,
				success : function(data) {
					AngularHelper.Compile($('#content'), data);
				},
				error: function() {
					alert("身体页面打开失败");
				}
			});
			
		});
		
	}
</script>