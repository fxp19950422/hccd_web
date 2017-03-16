<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page import="cn.sportsdata.webapp.youth.common.utils.CommonUtils" %>

<%
	String serverUrl = CommonUtils.getServerUrl(request);
%>

<style>
	.start-statistic-label {
		color: #999;
		font-size: 12px;
	}
</style>

<div class="matchDetailContainer">
	<div class="button_area">
		<button id="edit_btn" class="btn btn-primary" style="float: right; margin-left: 10px;">编辑</button>
		<button id="del_btn" class="btn btn-danger" style="float: right; margin-left: 10px;">删除</button>
		<button id="view_result_btn" class="btn btn-default" style="float: right; margin-left: 10px;">
			<c:choose>
				<c:when test="${ match.isEnd == 1 }">
					查看结果
				</c:when>
				<c:otherwise>
					结果录入
				</c:otherwise>
			</c:choose>
		</button>
		<button id="share_btn" class="btn btn-default" data-toggle="popover" data-placement="bottom" style="float: right; margin-left: 10px;">分享</button>
		<button id="back_btn" class="btn btn-default" style="float: right; margin-left: 10px;">返回</button>
	</div>
	<div class="clearfix" style="height: 15px;"></div>
	<div class="matchDetailArea">
		<sa-panel title="比赛信息">
			<div class="row">
				<div class="col-md-1 profileEditTitle">对手</div>
				<div class="col-md-3 profileEditValue">${ match.opponent }</div>
				<div class="col-md-1 profileEditTitle">地点</div>
				<div class="col-md-3 profileEditValue">${ match.location }（${ match.translatedFieldType }）</div>
				<div class="col-md-1 profileEditTitle">着装</div>
				<div class="col-md-3 profileEditValue">${ match.dress }</div>
			</div>
			<div class="row profileEditItemLine">
				<div class="col-md-1 profileEditTitle">类型</div>
				<div class="col-md-3 profileEditValue">${ match.translatedType }</div>
				<div class="col-md-1 profileEditTitle">日期</div>
				<div class="col-md-3 profileEditValue"><fmt:formatDate value="${ match.date }" pattern="yyyy-MM-dd" /></div>
				<div class="col-md-1 profileEditTitle">时间</div>
				<div class="col-md-3 profileEditValue">${ match.time }</div>
			</div>
			<div class="row profileEditItemLine">
				<div class="col-md-1 profileEditTitle">比分</div>
				<div class="col-md-3 profileEditValue">
					<c:choose>
						<c:when test="${ match.isEnd == 1 }">
							${ match.homeScore }:${ match.guestScore }
						</c:when>
						<c:otherwise>
							未赛
						</c:otherwise>
					</c:choose>
				</div>
				<div class="col-md-8"></div>
			</div>
		</sa-panel>
	</div>
	<div class="matchDetailExtArea">
		<div style="float:left;width:35%;">
			<sa-panel title="首发设定">
				<c:if test="${ !empty starter }">
					<div style="width: 250px; height: 250px;margin:0 auto 30px auto;">
						<img class="starter-image" src="${ starter.imgUrl }" width="250">
						<div style="margin: 15px 0 0 0; font-size: 18px;">
							<div class="starter-name" style="overflow: hidden;text-overflow: ellipsis;white-space: nowrap;">${ starter.name }</div>
						</div>
						<div>
	    					<span class="start-statistic-label">使用次数</span>&nbsp;&nbsp;
	    					<span style="color: #fc6b8a; font-size: 16px;">${ starter.usedCount }次</span>&nbsp;&nbsp;
	    					<div style="font-size: 10px;display: inline-block; background-color:#dddddd; border-radius: 8px; padding: 2px 5px;">${ starter.winCount }胜&nbsp;${ starter.drawCount }平&nbsp;${ starter.loseCount }负</div>&nbsp;&nbsp;
	    				</div>
	    				<div>
	    					<span class="start-statistic-label">最近战绩</span>&nbsp;&nbsp;
	    					<c:forEach items="${starter.recentRecordList}" var="record" varStatus="status">
	    						<span style="font-size: 16px; color: #067dc2;">
	    							<c:choose>
										<c:when test="${ record.result == 'won' }">胜</c:when>
										<c:when test="${ record.result == 'lost' }">负</c:when>
										<c:otherwise>平</c:otherwise>
									</c:choose>
	    						</span>&nbsp;
	    					</c:forEach>
	    				</div>
					</div>
				</c:if>
			</sa-panel>
		</div>
		<div style="float:right;width:64%;">
			<sa-panel title="定位球设定">
				<c:forEach items="${placekickList}" var="placekick" varStatus="status">
					<div style="float: left;width: 250px; height: 220px;margin: 8px 15px;">
						<img class="placekick-image" src="${ placekick.imgUrl }" width="250">
						<div style="margin: 15px 0 0 0; font-size: 18px;">
							<div class="placekick-name" style="overflow: hidden;text-overflow: ellipsis;white-space: nowrap;">${ placekick.name }</div>
						</div>
					</div>
				</c:forEach>
			</sa-panel>
		</div>
		<div style="clear:both;"></div>
	</div>
</div>

<script type="text/javascript">
	$(function() {
		setTimeout(function() {
			initEvent();
		}, 50);
	});
	
	function initEvent() {
		buildBreadcumb("查看比赛设置");
		
		$('#back_btn').click(function() {
			sa.ajax({
				type : "get",
				url : "<%=serverUrl%>match/showMatchList",
				success : function(data) {
					AngularHelper.Compile($('#content'), data);
				},
				error: function() {
					alert("返回比赛列表页面失败");
				}
			});
		});
		
		$('#view_result_btn').click(function() {
			var isFinished = ${ match.isEnd };
			var url = '<%=serverUrl%>match/' + (isFinished ? 'showMatchResultDetail' : 'showMatchResultEdit') + '?matchID=${ match.id }';
			
			sa.ajax({
				type : "get",
				url : url,
				success : function(data) {
					AngularHelper.Compile($('#content'), data);
					
					gotoMenu('match-menu-result');
					rebuildBreadcumb("比赛管理", "结果录入", isFinished ? "比赛结果查看" : "比赛结果录入");
				},
				error: function() {
					alert("打开比赛结果页面失败");
				}
			});
		});
		
		$('#del_btn').click(function() {
			bootbox.dialog({
			message: "您是否要删除该比赛？",
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
						sa.ajax({
							type : "delete",
							url : "<%=serverUrl%>match/deleteMatch?matchID=" + '${ match.id }',
							success : function(data) {
								if(!data.status) {
									alert("删除比赛异常");
									return;
								}
								
								sa.ajax({
									type : "get",
									url : "<%=serverUrl%>match/showMatchList",
									success : function(data) {
										AngularHelper.Compile($('#content'), data);
									},
									error: function() {
										alert("返回比赛列表页面失败");
									}
								});
							},
							error: function() {
								alert("删除比赛失败");
							}
						});
					}
				}
			   
			}
		});
		});
		
		$('#edit_btn').click(function() {
			sa.ajax({
				type : "get",
				url : "<%=serverUrl%>match/showMatchEdit?matchID=" + '${ match.id }',
				success : function(data) {
					AngularHelper.Compile($('#content'), data);
				},
				error: function() {
					alert("打开编辑比赛页面失败");
				}
			});
		});
		loadPopOver('${barCodeLink}');
	}
	
	function loadPopOver(imageSrc) {
		  var pElem = $("#share_btn");
		  var popoverObj = pElem.popover({
		    html: true,
		    trigger: "hover",
		    content: createPopOverElement(imageSrc),
		    container: 'body',
		    template:'<div class="popover" style="max-width:152px; width:152px" role="tooltip"><div class="arrow"></div><h3 class="popover-title"></h3><div class="popover-content" ></div></div>'
		  });
	}
	
	function createPopOverElement(imageSrc) {
		var tempArr = [];
		tempArr.push("<img src='");
		tempArr.push(imageSrc);
		tempArr.push("'  alt='分享到微信' />");
		return tempArr.join("");
	}
</script>