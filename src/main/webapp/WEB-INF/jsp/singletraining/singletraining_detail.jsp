<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="xss" uri="http://www.sportsdata.cn/xss"%>
<%@ page import="cn.sportsdata.webapp.youth.common.utils.CommonUtils" %>
<%@ page import="cn.sportsdata.webapp.youth.common.vo.SingleTrainingVO" %>
<%@ page import="java.util.Calendar" %>
<style>
<!--

.target_title,.target_input{
	float: left;
	min-height: 1px;
    padding-left:15px;
    position: relative;
    font-size:14px;
}
.target_title{
	width: 10.6667%;
	color:#666;
}
.target_input{
	width: 89.3333%;
	color:#333;
	padding-right:15px;
}
-->
</style>
<%
	String serverUrl = CommonUtils.getServerUrl(request);
	SingleTrainingVO singletraining = (SingleTrainingVO) request.getAttribute("singletraining");
	boolean isViewAction = (Boolean) request.getAttribute("isViewAction");
%>

<div>
	<div class="profileDetailHeader">
	<c:if test="${!isViewAction}">
		<div class="profileAction button_area" style="top: 130px;">
			<button id="edit_btn" class="btn btn-primary" style="float: right; margin-left: 10px;">编辑</button>
			<button id="delete_btn" class="btn btn-danger" style="float: right; margin-left: 10px;">删除</button>
			<button id="back_btn" class="btn btn-default" style="float: right;">返回</button>
		</div>
	</c:if>
	<div class="clearfix"></div>
	<div class="profileDetailAction">
		<input id="basic_option" class="panel_option" type="checkbox" checked><label for="basic_option">&nbsp;基本设置</label>
		<input id="professional_option" class="panel_option profileDetailActionItem" type="checkbox" checked><label for="professional_option">&nbsp;目标设置</label>
		<input id="club_option" class="panel_option profileDetailActionItem" type="checkbox" checked><label for="club_option">&nbsp;战术板</label>
		<input id="technical_option" class="panel_option profileDetailActionItem" type="checkbox" checked><label for="technical_option">&nbsp;时间器材</label>
	</div>
			<div class="profileDetailContent">
				<div id="basic_panel">
					<sa-panel title="基本设置">
						<div class="row">
							<div class="col-md-10">
								<div class="row">
									<div class="col-md-1 profileEditTitle">名称</div>
									<div class="col-md-3 profileEditValue" style="overflow:hidden;text-overflow: ellipsis;white-space: nowrap;" title="${ singletraining.name }">
										<xss:xssFilter text="${ singletraining.name }" filter="html"/>
									</div>
									<div class="col-md-1 profileEditTitle">球员人数</div>
									<div class="col-md-2 profileEditValue">
										<xss:xssFilter text="${ singletraining.player_count}" filter="html"/>人
									</div>
									<div class="col-md-1 profileEditTitle">门将人数</div>
									<div class="col-md-2 profileEditValue">
										<xss:xssFilter text="${ singletraining.goalkeeper_count }" filter="html"/>人
									</div>
								</div>
								<div class="row profileDetailItemLine">
								<div class="col-md-1 profileEditTitle">场地大小</div>
									<div class="col-md-3 profileEditValue">
											 长：${ singletraining.yard_long }米 *宽：${ singletraining.yard_width }米
									</div>
									<div class="col-md-1 profileEditTitle">分类</div>
									<div class="col-md-2 profileEditValue">
										${ singletraining.translatedType}
									</div>
									<div class="col-md-1 profileEditTitle">针对球员</div>
									<div class="col-md-2 profileEditValue">
										${singletraining.translatedTarget }
									</div>
								</div>
								<div class="row profileDetailItemLine">
									<div class="col-md-1 profileEditTitle">描述</div>
									<div class="col-md-11 profileEditValue">
										<xss:xssFilter text="${ singletraining.description }" filter="html"/>
									</div>
									<div class="col-md-4"></div>
								</div>
							</div>
						</div>
					</sa-panel>
				</div>
				<div id="professional_panel">
					<sa-panel title="目标设置">
						<div class="row">
							<div class="col-md-2" style="color:#067DC2;">战术目标</div>
						</div>
						<div class="row profileDetailItemLine">
							<div class="target_title">进攻战术目标</div>
							<div class="target_input">
								<xss:xssFilter text="${singletraining.singleTrainingExtInfoMap['target_tactics_Offensive'] }" filter="html"/>
							</div>
						</div>
						<div class="row profileDetailItemLine">
							<div class="target_title">防守战术目标</div>
							<div class="target_input">
								<xss:xssFilter text="${ singletraining.singleTrainingExtInfoMap['target_tactics_defense'] }" filter="html"/>
							</div>
						</div>
						<div class="row">
							<div class="col-md-2" style="color:#067DC2;">技术目标</div>
						</div>
						<div class="row profileDetailItemLine">
							<div class="target_title">进攻技术目标</div>
							<div class="target_input">
								<xss:xssFilter text="${ singletraining.singleTrainingExtInfoMap['target_technology_Offensive'] }" filter="html"/>
							</div>
						</div>
						<div class="row profileDetailItemLine">
							<div class="target_title">防守技术目标</div>
							<div class="target_input">
								<xss:xssFilter text="${ singletraining.singleTrainingExtInfoMap['target_technology_defense'] }" filter="html"/>
							</div>
						</div>
						<div class="row">
							<div class="col-md-2" style="color:#067DC2;">其他目标</div>
						</div>
						<div class="row profileDetailItemLine">
							<div class="target_title">身体机能</div>
							<div class="target_input">
								<xss:xssFilter text="${ singletraining.singleTrainingExtInfoMap['target_other_body'] }" filter="html"/>
							</div>
						</div>
						<div class="row profileDetailItemLine">
							<div class="target_title">意志品质</div>
							<div class="target_input">
								<xss:xssFilter text="${ singletraining.singleTrainingExtInfoMap['target_other_will'] }" filter="html"/>
							</div>
							<div class="col-md-4"></div>
						</div>
					</sa-panel>
				</div>
				<div id="club_panel">
					<sa-panel title="战术板">
					<div style="width: 250px;margin: 10px auto 10px auto;">
						<c:choose>
							<c:when test="${empty tactics.imgUrl}">
								<img class="starter-image" src="<%=serverUrl%>resources/images/soccerboard/ic_tactics_single.png" width="248"/>
								<div style="text-align: center;font-size: 14px;margin-top:15px;font-color:#666666;">
									<span class="starter-name">暂无战术安排</span>
								</div>
							</c:when>
							<c:otherwise>
								<img class="starter-image" src="${ tactics.imgUrl }" width="248" />
								<div style="text-align: center; font-size: 14px;margin-top:15px;font-color:#666666;overflow:hidden;text-overflow: ellipsis;white-space: nowrap;">
									<span class="starter-name" title='<xss:xssFilter text="${ tactics.name }" filter="html"/>'><xss:xssFilter text="${ tactics.name }" filter="html"/></span>
								</div>
							</c:otherwise>
						</c:choose>
						</div>
					</sa-panel>
				</div>
				<div id="technical_panel">
					<sa-panel title="时间器材">
						<div class="row">
							<div class="col-md-1" style="color:#067DC2;">时间</div>
						</div>
						<div class="row profileDetailItemLine">
							<div class="col-md-1 profileEditTitle">小结时长</div>
							<div class="col-md-1 profileEditValue">
								${ singletraining.singleTrainingExtInfoMap['time_single_train'] }分钟
							</div>
							<div class="col-md-1 profileEditTitle">重复次数</div>
							<div class="col-md-1 profileEditValue">
								${ singletraining.singleTrainingExtInfoMap['time_repeat'] }次
							</div>
							<div class="col-md-1 profileEditTitle">间歇时长</div>
							<div class="col-md-1 profileEditValue">
								${ singletraining.singleTrainingExtInfoMap['time_intermission'] }分钟
							</div>
							<div class="col-md-1 profileEditTitle">总时长</div>
							<div class="col-md-1 profileEditValue">
								${ singletraining.singleTrainingExtInfoMap['time_sum'] }分钟
							</div>
						</div>			
						<div class="row profileDetailItemLine">	
							<div class="col-md-1" style="color:#067DC2;">器材</div>
						</div>
						<c:forEach items="${singletraining.singleTrainingEquipmentInfoList}" var="equipmentInfo">
							<div class="col-md-1 profileEditTitle" style="overflow:hidden;text-overflow: ellipsis;white-space: nowrap; height: 20px;" title="<xss:xssFilter text='${equipmentInfo.equipmentName}' filter='html'/>"><xss:xssFilter text="${equipmentInfo.equipmentName}" filter="html"/></div>
							<div class="col-md-1 profileEditValue">${equipmentInfo.count}个</div>
						</c:forEach>
						<div class="row profileDetailItemLine">	
							<div class="col-md-4"></div>
						</div>
					</sa-panel>
				</div>
			</div>
	</div>
</div>

<script type="text/javascript">
	$(function() {
		initEvent();
	});
	
	function initEvent() {
		var isViewAction = ${isViewAction};
		if (!isViewAction) {
			buildBreadcumb("单项训练详情");
		}
		$('input.panel_option').click(function() {
			var $dom = $(this);
			var targetPanelId = $dom.attr('id').split('_')[0] + '_' + 'panel';
			$('#' + targetPanelId).toggle();
		});
		
		$('#back_btn').click(function() {
			sa.ajax({
				type : "get",
				url : "<%=serverUrl%>singletraining/showSingleTrainingList",
				success : function(data) {
					AngularHelper.Compile($('#content'), data);
				},
				error: function() {
					alert("打开单项训练列表页面失败");
				}
			});
		});
		
		$('#delete_btn').click(function() {
			bootbox.dialog({
				message: "您是否要删除该单项训练？",
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
								url : "<%=serverUrl%>singletraining/deleteSingleTraining?id=" + "${ singletraining.id }",
								success : function(data) {
									if(!data.status) {
										alert("删除单项训练异常");
										return;
									}
									
									sa.ajax({
										type : "get",
										url : "<%=serverUrl%>singletraining/showSingleTrainingList",
										success : function(data) {
											AngularHelper.Compile($('#content'), data);
										},
										error: function() {
											alert("打开单项训练列表页面失败");
										}
									});
								},
								error: function() {
									alert("删除单项训练失败");
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
				url : "<%=serverUrl%>singletraining/showSingleTrainingEdit?id=" + "${ singletraining.id }",
				success : function(data) {
					AngularHelper.Compile($('#content'), data);
				},
				error: function() {
					alert("打开编辑单项训练页面失败");
				}
			});
		});
	}
</script>
