<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page import="cn.sportsdata.webapp.youth.common.utils.CommonUtils" %>

<%
	String serverUrl = CommonUtils.getServerUrl(request);
%>

<c:if test="${ isMatch }">
	<div class="button_area">
		<button id="add_btn" class="btn btn-primary" style="float: right;">添加</button>
	</div>
	<div class="clearfix" style="height: 15px;"></div>
</c:if>
<div class="matchListArea matchList">
	<sa-panel title="未开赛">
		<c:forEach items="${unFinishMatchList}" var="match">
			<div class="matchCard unfinished" mid="${ match.id }">
				<div class="resultSection text-center matchCardResultNoRecord">未赛</div>
				<div class="opponent matchCardOpponentNoRecord" title="${ match.opponent }">VS&nbsp;${ match.opponent }</div>
				<div class="time"><fmt:formatDate value="${ match.date }" pattern="yyyy-MM-dd" />&nbsp;${ match.time }</div>
				<div class="type">${ match.translatedType }</div>
				<div class="location" title="${ match.location }">${ match.location }</div>
			</div>
		</c:forEach>
	</sa-panel>
	<sa-panel title="已结束">
		<c:forEach items="${finishMatchList}" var="match">
			<div class="matchCard finished" mid="${ match.id }">
				<div class="resultSection text-center matchCardResultRecord">${ match.homeScore }&nbsp;:&nbsp;${ match.guestScore }</div>
				<div class="opponent matchCardOpponentRecord" title="${ match.opponent }">VS&nbsp;${ match.opponent }</div>
				<div class="time"><fmt:formatDate value="${ match.date }" pattern="yyyy-MM-dd" />&nbsp;${ match.time }</div>
				<div class="type">${ match.translatedType }</div>
				<div class="location" title="${ match.location }">${ match.location }</div>
			</div>
		</c:forEach>
	</sa-panel>
</div>

<script type="text/javascript">
	$(function() {
		initEvent();
	});
	
	function initEvent() {
		buildBreadcumb();
		
		$('div.matchList').on('click', 'div.matchCard', function() {
			var match_id = $(this).attr('mid');
			var isMatch = ${ isMatch };
			
			if(isMatch) {
				sa.ajax({
					type : "get",
					url : "<%=serverUrl%>match/showMatchDetail?matchID=" + match_id,
					success : function(data) {
						AngularHelper.Compile($('#content'), data);
					},
					error: function() {
						alert("打开比赛详情页面失败");
					}
				});
			} else {
				var isFinished = $(this).is('.finished');
				var url = '<%=serverUrl%>match/' + (isFinished ? 'showMatchResultDetail' : 'showMatchResultEdit') + '?matchID=' + match_id;
				
				sa.ajax({
					type : "get",
					url : url,
					success : function(data) {
						AngularHelper.Compile($('#content'), data);
					},
					error: function() {
						alert("打开比赛结果页面失败");
					}
				});
			}
		});
		
		$('#add_btn').click(function() {
			sa.ajax({
				type : "get",
				url : "<%=serverUrl%>match/showMatchEdit",
				success : function(data) {
					AngularHelper.Compile($('#content'), data);
				},
				error: function() {
					alert("打开编辑比赛页面失败");
				}
			});
		});
	}
</script>