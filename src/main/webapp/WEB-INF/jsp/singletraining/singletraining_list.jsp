<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="xss" uri="http://www.sportsdata.cn/xss"%>

<%@ page import="cn.sportsdata.webapp.youth.common.utils.CommonUtils" %>

<%
	String serverUrl = CommonUtils.getServerUrl(request);
%>
<style>
<!--
.single-name{
	font-size:16px;
	color:#333333;
	font-weight:bold;
	width:100px;
	height:20px;
	overflow:hidden;
	text-overflow: ellipsis;
	white-space: nowrap;
	float:left;"
}
.single-type{
	margin:0px 4px 0px 4px;
	color:#8EBADE;
	width:100px;
	height:20px;
	float:right;
}
.single-yard{
	color:#AFAFAF;
	float:left;
	width:80px;
	height:20px;
	overflow:hidden;
	text-overflow: ellipsis;
	white-space: nowrap;
}
.single-target{
	margin-left:4px;
	color:#AFAFAF;
	float:left;
	width:60px;
	height:20px;
	overflow:hidden;
	text-overflow: ellipsis;
	white-space: nowrap;
}
.single-time-sum{
	margin-right:4px;
	color:#FD8EA4;
	float:right;
	width:60px;
	height:20px;
	overflow:hidden;
	text-overflow: ellipsis;
	white-space: nowrap;
}
-->
</style>
<div class="button_area">
	<button id="add_btn" class="btn btn-primary" style="float: right;">添加</button>
</div>
<div class="clearfix" style="height:15px;"></div>
<div class="singleTrainingList">
	<sa-panel-card title="单项训练">
		<c:forEach items="${singletraininglist}" var="singleTraining">
			<div class="profileCard" style="width: 222px;" uid="${singleTraining.id }">
				<div style="margin-left:10px; margin-top:13px;">
				<div class="single-name" title="${singleTraining.name}">
					<c:choose>
						<c:when test="${ !empty  singleTraining.name }">
							<xss:xssFilter text="${ singleTraining.name }" filter="html"/>
						</c:when>
						<c:otherwise>
							暂无名称
						</c:otherwise>
					</c:choose>
				</div>
				<div class="single-type">
					<c:choose>
						<c:when test="${ !empty singleTraining.translatedType }">
							<xss:xssFilter text="${ singleTraining.translatedType }" filter="html"/>
						</c:when>
						<c:otherwise>
							暂无分类信息
						</c:otherwise>
					</c:choose>
				</div>
				</div>
				<div style="margin-left:10px; margin-top:47px;">
					<div class="single-yard">
						<c:choose>
							<c:when test="${ !empty singleTraining.yard_long }">
								${ singleTraining.yard_long }米
							</c:when>
							<c:otherwise>
								暂无长度
							</c:otherwise>
						</c:choose>
						 * 
						<c:choose>
							<c:when test="${ !empty singleTraining.yard_width }">
								${ singleTraining.yard_width }米
							</c:when>
							<c:otherwise>
								暂无宽度
							</c:otherwise>
						</c:choose>
					</div>
					<div class="single-target">
						<c:choose>
							<c:when test="${ !empty singleTraining.translatedTarget }">
								<xss:xssFilter text="${ singleTraining.translatedTarget }" filter="html"/>
							</c:when>
							<c:otherwise>
								暂无针对球员
							</c:otherwise>
						</c:choose>
					</div>
					<div class="single-time-sum">
						<c:choose>
							<c:when test="${ !empty singleTraining.singleTrainingExtInfoMap['time_sum'] }">
								${ singleTraining.singleTrainingExtInfoMap['time_sum'] }分钟
							</c:when>
							<c:otherwise>
								暂无总时长
							</c:otherwise>
						</c:choose>
					</div>
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
		$('div.singleTrainingList').on('click', 'div.profileCard', function() {
			var $dom = $(this);
			sa.ajax({
				type : "get",
				url : "<%=serverUrl%>singletraining/showSingleTrainingDetail?id=" + $dom.attr('uid'),
				success : function(data) {
					AngularHelper.Compile($('#content'), data);
				},
				error: function() {
					alert("打开单项训练详情页面失败");
				}
			});
		});
		
		$('#add_btn').click(function() {
			sa.ajax({
				type : "get",
				url : "<%=serverUrl%>singletraining/showSingleTrainingEdit",
				success : function(data) {
					AngularHelper.Compile($('#content'), data);
				},
				error: function() {
					alert("打开创建单项训练页面失败");
				}
			});
		});
	}
</script>