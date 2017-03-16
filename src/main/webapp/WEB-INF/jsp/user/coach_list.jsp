<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="xss" uri="http://www.sportsdata.cn/xss"%>

<%@ page import="cn.sportsdata.webapp.youth.common.utils.CommonUtils" %>

<%
	String serverUrl = CommonUtils.getServerUrl(request);
%>


<div class="button_area">
	<button id="add_btn" class="btn btn-primary"  style="float: right;">添加</button>
</div>
<div class="clearfix" style="height:15px;"></div>
<div class=" userCoachList">
	<sa-panel-card title="主教练">
		<c:forEach items="${chiefCoachList}" var="coach">
			<div class="profileCard" uid="${ coach.id }">
				<c:choose>
					<c:when test="${ !empty coach.avatar }">
						<img class="profileAvatar" src="<%=serverUrl%>file/downloadFile?fileName=${ coach.avatar }"></img>
					</c:when>
					<c:otherwise>
						<img class="profileAvatar" src="<%=serverUrl%>resources/images/user_avatar.png"></img>
					</c:otherwise>
				</c:choose>
				
				<div class="profileName"><xss:xssFilter text="${coach.name}" filter="html"/></div>
				<div class="profileData">
					<c:choose>
						<c:when test="${ !empty coach.tel }">
							${ coach.tel }
						</c:when>
						<c:otherwise>
							暂无联系方式
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</c:forEach>
	</sa-panel-card>
	<sa-panel-card title="助理教练">
		<c:forEach items="${assistantCoachList}" var="coach">
			<div class="profileCard" uid="${ coach.id }">
				<c:choose>
					<c:when test="${ !empty coach.avatar }">
						<img class="profileAvatar" src="<%=serverUrl%>file/downloadFile?fileName=${ coach.avatar }"></img>
					</c:when>
					<c:otherwise>
						<img class="profileAvatar" src="<%=serverUrl%>resources/images/user_avatar.png"></img>
					</c:otherwise>
				</c:choose>
				
				<div class="profileName"><xss:xssFilter text="${coach.name}" filter="html"/></div>
				<div class="profileData">
					<c:choose>
						<c:when test="${ !empty coach.tel }">
							${ coach.tel }
						</c:when>
						<c:otherwise>
							暂无联系方式
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</c:forEach>
	</sa-panel-card>
	<sa-panel-card title="体能教练">
		<c:forEach items="${fitnessCoachList}" var="coach">
			<div class="profileCard" uid="${ coach.id }">
				<c:choose>
					<c:when test="${ !empty coach.avatar }">
						<img class="profileAvatar" src="<%=serverUrl%>file/downloadFile?fileName=${ coach.avatar }"></img>
					</c:when>
					<c:otherwise>
						<img class="profileAvatar" src="<%=serverUrl%>resources/images/user_avatar.png"></img>
					</c:otherwise>
				</c:choose>
				
				<div class="profileName"><xss:xssFilter text="${coach.name}" filter="html"/></div>
				<div class="profileData">
					<c:choose>
						<c:when test="${ !empty coach.tel }">
							${ coach.tel }
						</c:when>
						<c:otherwise>
							暂无联系方式
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</c:forEach>
	</sa-panel-card>
	<sa-panel-card title="守门员教练">
		<c:forEach items="${goalKeeperCoachList}" var="coach">
			<div class="profileCard" uid="${ coach.id }">
				<c:choose>
					<c:when test="${ !empty coach.avatar }">
						<img class="profileAvatar" src="<%=serverUrl%>file/downloadFile?fileName=${ coach.avatar }"></img>
					</c:when>
					<c:otherwise>
						<img class="profileAvatar" src="<%=serverUrl%>resources/images/user_avatar.png"></img>
					</c:otherwise>
				</c:choose>
				
				<div class="profileName"><xss:xssFilter text="${coach.name}" filter="html"/></div>
				<div class="profileData">
					<c:choose>
						<c:when test="${ !empty coach.tel }">
							${ coach.tel }
						</c:when>
						<c:otherwise>
							暂无联系方式
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</c:forEach>
	</sa-panel-card>
	<sa-panel-card title="科研教练">
		<c:forEach items="${researchCoachList}" var="coach">
			<div class="profileCard" uid="${ coach.id }">
				<c:choose>
					<c:when test="${ !empty coach.avatar }">
						<img class="profileAvatar" src="<%=serverUrl%>file/downloadFile?fileName=${ coach.avatar }"></img>
					</c:when>
					<c:otherwise>
						<img class="profileAvatar" src="<%=serverUrl%>resources/images/user_avatar.png"></img>
					</c:otherwise>
				</c:choose>
				
				<div class="profileName"><xss:xssFilter text="${coach.name}" filter="html"/></div>
				<div class="profileData">
					<c:choose>
						<c:when test="${ !empty coach.tel }">
							${ coach.tel }
						</c:when>
						<c:otherwise>
							暂无联系方式
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</c:forEach>
	</sa-panel-card>
	<sa-panel-card title="战术教练">
		<c:forEach items="${tacticsCoachList}" var="coach">
			<div class="profileCard" uid="${ coach.id }">
				<c:choose>
					<c:when test="${ !empty coach.avatar }">
						<img class="profileAvatar" src="<%=serverUrl%>file/downloadFile?fileName=${ coach.avatar }"></img>
					</c:when>
					<c:otherwise>
						<img class="profileAvatar" src="<%=serverUrl%>resources/images/user_avatar.png"></img>
					</c:otherwise>
				</c:choose>
				
				<div class="profileName"><xss:xssFilter text="${coach.name}" filter="html"/></div>
				<div class="profileData">
					<c:choose>
						<c:when test="${ !empty coach.tel }">
							${ coach.tel }
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
		$('div.userCoachList').on('click', 'div.profileCard', function() {
			var $dom = $(this);
			
			sa.ajax({
				type : "get",
				url : "<%=serverUrl%>user/showCoachDetail?userID=" + $dom.attr('uid'),
				success : function(data) {
					//TODO: will update the container later
					AngularHelper.Compile($('#content'), data);
				},
				error: function() {
					alert("打开教练详情页面失败");
				}
			});
		});
		
		$('#add_btn').click(function() {
			sa.ajax({
				type : "get",
				url : "<%=serverUrl%>user/showCoachEdit",
				success : function(data) {
					//TODO: will update the container later
					AngularHelper.Compile($('#content'), data);
				},
				error: function() {
					alert("打开创建球员页面失败");
				}
			});
		});
	}
</script>
