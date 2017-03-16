<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="xss" uri="http://www.sportsdata.cn/xss"%>
<%@ page import="cn.sportsdata.webapp.youth.common.utils.CommonUtils" %>
<%@ page import="cn.sportsdata.webapp.youth.common.vo.UserVO" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.sql.Timestamp" %>

<%
	String serverUrl = CommonUtils.getServerUrl(request);
%>

<div>
	<div class="profileDetailHeader">
		<div class="profileInfo">
			<c:choose>
				<c:when test="${!empty equipment.avatar }">
					<img class="profileDetailAvatar" src="<%=serverUrl%>file/downloadFile?fileName=${equipment.avatar }"></img>
				</c:when>
				<c:otherwise>
					<img class="profileDetailAvatar" src="<%=serverUrl%>resources/images/user_avatar.png"></img>
				</c:otherwise>
			</c:choose>
			<div class="profileDetailName" style="padding-top: 15px;">
				<c:choose>
					<c:when test="${!empty equipment.name }">
						<xss:xssFilter text="${equipment.name }" filter="html"/>
					</c:when>
					<c:otherwise>
						暂无名称
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<div class="profileAction button_area" style="top: 96px;">
			<button id="edit_btn" class="btn btn-primary" style="float: right; margin-left: 10px;">编辑</button>
			<button id="delete_btn" class="btn btn-danger" style="float: right; margin-left: 10px;">删除</button>
			<button id="back_btn" class="btn btn-default" style="float: right;">返回</button>
		</div>
	</div>
	<div class="clearfix"></div>
	<div class="profileDetailContent" style="margin-top: 15px;">
		<div id="basic_panel">
			<sa-panel title="基本资料">
				<div class="row">
					<div class="col-md-1 profileDetailItemTitle">描述</div>
					<div class="col-md-10 profileEditValue">
						<c:choose>
							<c:when test="${!empty equipment.description }">
								<xss:xssFilter text="${ equipment.description }" filter="html"/>
							</c:when>
							<c:otherwise>
								暂无描述
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</sa-panel>
		</div>
	</div>
</div>

<script type="text/javascript">
	$(function() {
		initEvent();
	});
	
	function initEvent() {
		buildBreadcumb("器材详情");
		$('input.panel_option').click(function() {
			var $dom = $(this);
			var targetPanelId = $dom.attr('id').split('_')[0] + '_' + 'panel';
			$('#' + targetPanelId).toggle();
		});
		
		$('#back_btn').click(function() {
			sa.ajax({
				type : "get",
				url : "<%=serverUrl%>equipment/showEquipmentList",
				success : function(data) {
					AngularHelper.Compile($('#content'), data);
				},
				error: function() {
					alert("打开器材列表页面失败");
				}
			});
		});
		
		$('#delete_btn').click(function() {
			bootbox.dialog({
				message: "您是否要删除该器材？",
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
								url : "<%=serverUrl%>equipment/deleteEquipment?id=" + "${ equipment.id }",
								success : function(data) {
									if(!data.status) {
										alert("删除器材信息异常");
										return;
									}
									
									sa.ajax({
										type : "get",
										url : "<%=serverUrl%>equipment/showEquipmentList",
										success : function(data) {
											AngularHelper.Compile($('#content'), data);
										},
										error: function() {
											alert("打开器材列表页面失败");
										}
									});
								},
								error: function() {
									alert("删除器材信息失败");
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
				url : "<%=serverUrl%>equipment/showEquipmentEdit?id=" + "${ equipment.id }",
				success : function(data) {
					AngularHelper.Compile($('#content'), data);
				},
				error: function() {
					alert("打开编辑器材页面失败");
				}
			});
		});
	}
</script>