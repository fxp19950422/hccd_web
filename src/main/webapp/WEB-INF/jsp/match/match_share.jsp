<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page import="cn.sportsdata.webapp.youth.common.utils.CommonUtils" %>
<%@include file="/WEB-INF/jsp/common/include.jsp"%>

<%
	String serverUrl = CommonUtils.getServerUrl(request);
%>
	<div class="row">
		<div class="col-md-12">
		<div class="panel panel-default">
			<div class="panel-heading" style="background-color:#067DC2;color:white">
				<div style="float: left;">比赛信息</div>
				<div class="clearfix"></div>
			</div>
			<div class="panel-body table-responsive">
			<div class="row">
				<div class="col-md-1"><strong>对手</strong></div>
				<div class="col-md-3">${ match.opponent }</div>
				<div class="col-md-1"><strong>地点</strong></div>
				<div class="col-md-3">${ match.location }（${ match.translatedFieldType }）</div>
				<div class="col-md-1"><strong>着装</strong></div>
				<div class="col-md-3">${ match.dress }</div>
			</div>
			<div class="row profileEditItemLine">
				<div class="col-md-1"><strong>类型</strong></div>
				<div class="col-md-3">${ match.translatedType }</div>
				<div class="col-md-1"><strong>日期</strong></div>
				<div class="col-md-3"><fmt:formatDate value="${ match.date }" pattern="yyyy-MM-dd" /></div>
				<div class="col-md-1"><strong>时间</strong></div>
				<div class="col-md-3">${ match.time }</div>
			</div>
			<div class="row profileEditItemLine">
				<div class="col-md-1"><strong>比分</strong></div>
				<div class="col-md-3">
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
			</div>
		</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-6">
			<div class="panel panel-default">
			<div class="panel-heading" style="background-color:#067DC2;color:white">
				<div style="float: left;">首发设定</div>
				<div class="clearfix"></div>
			</div>
			<div class="panel-body table-responsive">
				<c:if test="${ !empty starter }">
					<div style="width: 250px; height: 250px;margin:0 auto 20px auto;">
						<img class="starter-image" src="<%=serverUrl%>hagkFile/asset_pub?id=${ starter.imgName }" width="250">
						<div style="margin: 5px 0; font-size: 18px;">
							<span class="starter-name">${ starter.name }</span>
						</div>
						<div>
	    					<span>使用次数</span>&nbsp;&nbsp;
	    					<span style="color: #fc6b8a; font-size: 18px;">${ starter.usedCount }次</span>&nbsp;&nbsp;
	    					<div style="font-size: 10px;display: inline-block; background-color:#dddddd; border-radius: 8px; padding: 2px 5px;">${ starter.winCount }胜&nbsp;${ starter.drawCount }平&nbsp;${ starter.loseCount }负</div>&nbsp;&nbsp;
	    				</div>
	    				<div>
	    					<span>最近战绩</span>&nbsp;&nbsp;
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
				</div>
			</div>
		</div>
		<div class="col-md-6">
			<div class="panel panel-default">
			<div class="panel-heading" style="background-color:#067DC2;color:white">
				<div style="float: left;">定位球设定</div>
				<div class="clearfix"></div>
			</div>
			<div class="panel-body table-responsive">
				<c:forEach items="${placekickList}" var="placekick" varStatus="status">
					<div style="float: left;width: 250px; height: 220px;margin: 0 30px 20px 30px;">
						<img class="placekick-image" src="<%=serverUrl%>hagkFile/asset_pub?id=${ placekick.imgName }" width="250">
						<div style="margin: 5px 0; font-size: 18px;">
							<span class="placekick-name">${ placekick.name }</span>
						</div>
					</div>
				</c:forEach>
				</div>
			</div>
		</div>
	</div>
