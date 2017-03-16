<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ page import="cn.sportsdata.webapp.youth.common.utils.CommonUtils" %>
<%
	String serverUrl = CommonUtils.getServerUrl(request);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<div class="button_area">
	<button id="add_btn" class="btn btn-primary" style="float: right;">添加</button>
</div>
<div class="clearfix" style="height:15px;"></div>
<div id="accountDiv">
	<sa-panel-card title="账号管理">
		<c:forEach items="${accounts}" var="account">
			<div class="profileCard" loginId="${ account.id }" isCurLogin="${account.curLogin}">
				<c:choose>
					<c:when test="${ !empty account.avatar }">
						<img class="profileAvatar" src="<%=serverUrl%>file/downloadFile?fileName=${ account.avatar }"></img>
					</c:when>
					<c:otherwise>
						<img class="profileAvatar" src="<%=serverUrl%>resources/images/user_avatar.png"></img>
					</c:otherwise>
				</c:choose>
				
				<div class="profileName">${ account.name }</div>
				<div class="profileData">
					<c:choose>
						<c:when test="${ !empty account.username }">
							${ account.username }
						</c:when>
						<c:otherwise>
							暂无账号
						</c:otherwise>
					</c:choose>
				</div>
			</div>
			<c:if test="${ account.curLogin == 'true' }">
				<div style="width:100%; float: left;"></div>
			</c:if>
		</c:forEach>
	</sa-panel-card>
</div>
<script type="text/javascript">
	$(setTimeout(function() {
		initEvent();
	}, 50));
	
	function initEvent() {
		buildBreadcumb();
		$(document).off('click', '#accountDiv .profileCard');
		$(document).on('click', '#accountDiv .profileCard', function () {
			var $dom = $(this);
			var isCurLogin = $dom.attr('isCurLogin');
			var urlPath = "<%=serverUrl%>user/account_edit?id=" + $dom.attr('loginId');
			
			if (isCurLogin == 'true') {
				urlPath = "<%=serverUrl%>user/personal_edit?isDashboard=false";
			}
			
			sa.ajax({
				type : "get",
				url : urlPath,
				success : function(data) {
					AngularHelper.Compile($('#content'), data);
				},
				error: function() {
					alert("打开账号详情页面失败");
				}
			});
		});
		
		$('#add_btn').click(function() {
			sa.ajax({
				type : "get",
				url : "<%=serverUrl%>user/account_add",
				success : function(data) {
					AngularHelper.Compile($('#content'), data);
				},
				error: function() {
					alert("打开创建账号页面失败");
				}
			});
		});
	}
</script>