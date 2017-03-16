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

	UserVO playerInfo = (UserVO) request.getAttribute("player");
	String age = "暂无年龄信息";
	Timestamp birthday = playerInfo.getBirthday();
	
	if(birthday != null) {
		age = String.valueOf(Calendar.getInstance().get(Calendar.YEAR) - Integer.parseInt(new SimpleDateFormat("yyyy").format(birthday))) + "岁";
	}
%>

<div>
	<div class="profileDetailHeader">
		<div class="profileInfo">
			<c:choose>
				<c:when test="${ !empty player.avatar }">
					<img class="profileDetailAvatar" src="<%=serverUrl%>file/downloadFile?fileName=${ player.avatar }"></img>
				</c:when>
				<c:otherwise>
					<img class="profileDetailAvatar" src="<%=serverUrl%>resources/images/user_avatar.png"></img>
				</c:otherwise>
			</c:choose>
			
			<div class="profileDetailName">
				<c:choose>
					<c:when test="${ !empty player.name }">
						<xss:xssFilter text="${player.name}" filter="html"/>
					</c:when>
					<c:otherwise>
						暂无姓名
					</c:otherwise>
				</c:choose>
			</div>
			<div class="profileDetailData">
				<span><%= age %></span>
				<span>&nbsp;/&nbsp;</span>
				<span>
					<c:choose>
						<c:when test="${ !empty player.nationality }">
							<xss:xssFilter text="${ player.nationality }" filter="html"/>
						</c:when>
						<c:otherwise>
							暂无国籍信息
						</c:otherwise>
					</c:choose>
				</span>
				<span>&nbsp;/&nbsp;</span>
				<span>
					<c:choose>
						<c:when test="${ !empty player.userExtInfoMap['professional_jersey_number'] }">
							<xss:xssFilter text="${ player.userExtInfoMap['professional_jersey_number'] }" filter="html"/>号
						</c:when>
						<c:otherwise>
							暂无号码信息
						</c:otherwise>
					</c:choose>
				</span>
				<span>&nbsp;/&nbsp;</span>
				<span>
					<c:choose>
						<c:when test="${ !empty player.translatedPosition }">
							<xss:xssFilter text="${ player.translatedPosition }" filter="html"/>
						</c:when>
						<c:otherwise>
							暂无位置信息
						</c:otherwise>
					</c:choose>
				</span>
			</div>
		</div>
		<div class="profileAction player_detail_button_area">
			<button id="edit_btn" class="btn btn-primary" style="float: right; margin-left: 10px;">编辑</button>
			<button id="delete_btn" class="btn btn-danger" style="float: right; margin-left: 10px;">删除</button>
			<button id="body_index_btn" class="btn btn-default" style="float: right; margin-left: 10px;">身体指标</button>
			<button id="test_analysis_btn" class="btn btn-default" style="float: right;margin-left: 10px">测试分析</button>
			<button id="back_btn" class="btn btn-default" style="float: right;">返回</button>
		</div>
	</div>
	<div class="clearfix"></div>
	<div class="profileDetailAction">
		<input id="basic_option" class="panel_option" type="checkbox" checked><label for="basic_option">&nbsp;基本资料</label>
		<input id="professional_option" class="panel_option profileDetailActionItem" type="checkbox" checked><label for="professional_option">&nbsp;身体信息</label>
		<input id="technical_option" class="panel_option profileDetailActionItem" type="checkbox" checked><label for="technical_option">&nbsp;专业信息</label>
		<input id="club_option" class="panel_option profileDetailActionItem" type="checkbox" checked><label for="club_option">&nbsp;俱乐部</label>
		<input id="training_option" class="panel_option profileDetailActionItem" type="checkbox" checked><label for="training_option">&nbsp;参加过的集训</label>
	</div>
	<div class="profileDetailContent">
		<div id="basic_panel">
			<sa-panel title="基本资料">
				<div class="row">
					<div class="col-md-1 profileDetailItemTitle">姓名</div>
					<div class="col-md-3 profileDetailItemContent"><xss:xssFilter text="${ player.name }" filter="html"/></div>
					<div class="col-md-1 profileDetailItemTitle">生日</div>
					<div class="col-md-3 profileDetailItemContent"><fmt:formatDate value="${ player.birthday }" pattern="yyyy-MM-dd" /></div>
					<div class="col-md-1 profileDetailItemTitle">电话</div>
					<div class="col-md-3 profileDetailItemContent"><xss:xssFilter text="${ player.tel }" filter="html"/></div>
				</div>
				<div class="row profileDetailItemLine">
				<div class="col-md-1 profileDetailItemTitle">身份证号</div>
					<div class="col-md-3 profileDetailItemContent"><xss:xssFilter text="${ player.idCard }" filter="html"/></div>
					<div class="col-md-1 profileDetailItemTitle">邮箱</div>
					<div class="col-md-3 profileDetailItemContent"><xss:xssFilter text="${ player.email }" filter="html"/></div>
					<div class="col-md-1 profileDetailItemTitle">护照号</div>
					<div class="col-md-3 profileDetailItemContent"><xss:xssFilter text="${ player.passport }" filter="html"/></div>
				</div>
				<div class="row profileDetailItemLine">
					<div class="col-md-1 profileDetailItemTitle">出生地</div>
					<div class="col-md-3 profileDetailItemContent"><xss:xssFilter text="${ player.birthPlace }" filter="html"/></div>
					<div class="col-md-1 profileDetailItemTitle">国籍</div>
					<div class="col-md-3 profileDetailItemContent"><xss:xssFilter text="${ player.nationality }" filter="html"/></div>
					<div class="col-md-1 profileDetailItemTitle">地址</div>
					<div class="col-md-3 profileDetailItemContent"><xss:xssFilter text="${ player.homeAddress }" filter="html"/></div>
				</div>
			</sa-panel>
		</div>
		<div id="professional_panel">
			<sa-panel title="身体信息">
				<div class="row">
					<div class="col-md-1 profileDetailItemTitle">身高</div>
					<div class="col-md-2 profileDetailItemContent"><xss:xssFilter text="${ player.userExtInfoMap['professional_tall'] }" filter="html"/>厘米</div>
					<div class="col-md-1 col-md-offset-1 profileDetailItemTitle">体重</div>
					<div class="col-md-2 profileDetailItemContent"><xss:xssFilter text="${ player.userExtInfoMap['professional_weight'] }" filter="html"/>公斤</div>
					<div class="col-md-4"></div>
				</div>
				<div class="row profileDetailItemLine">
					<div class="col-md-1 profileDetailItemTitle">腰围</div>
					<div class="col-md-2 profileDetailItemContent"><xss:xssFilter text="${ player.userExtInfoMap['professional_waist'] }" filter="html"/>厘米</div>
					<div class="col-md-1 col-md-offset-1 profileDetailItemTitle">体格</div>
					<div class="col-md-2 profileDetailItemContent"><xss:xssFilter text="${ player.translatedPhysique }" filter="html"/></div>
					<div class="col-md-4"></div>
				</div>
			</sa-panel>
		</div>
		<div id="technical_panel">
			<sa-panel title="专业信息">
				<div class="row">
					<div class="col-md-1 profileDetailItemTitle">号码</div>
					<div class="col-md-3 profileDetailItemContent"><xss:xssFilter text="${ player.userExtInfoMap['professional_jersey_number'] }" filter="html"/></div>
					<div class="col-md-1 profileDetailItemTitle">位置</div>
					<div class="col-md-3 profileDetailItemContent"><xss:xssFilter text="${ player.translatedPosition }" filter="html"/></div>
					<div class="col-md-1 profileDetailItemTitle">第二位置</div>
					<div class="col-md-3 profileDetailItemContent"><xss:xssFilter text="${ player.translatedSecondaryPosition }" filter="html"/></div>
				</div>
				<div class="row profileDetailItemLine">
					<div class="col-md-1 profileDetailItemTitle">惯用脚</div>
					<div class="col-md-3 profileDetailItemContent"><xss:xssFilter text="${ player.translatedPreferredFoot }" filter="html"/></div>
					<div class="col-md-1 profileDetailItemTitle">类型</div>
					<div class="col-md-3 profileDetailItemContent"><xss:xssFilter text="${ player.translatedProfessionType }" filter="html"/></div>
					<div class="col-md-4"></div>
				</div>
				<div class="row profileDetailItemLine">
					<div class="col-md-1 profileDetailItemTitle">整体评价</div>
					<div class="col-md-3 profileDetailItemContent"><xss:xssFilter text="${ player.translatedOverallRating }" filter="html"/></div>
					<div class="col-md-1 profileDetailItemTitle">技术水平</div>
					<div class="col-md-3 profileDetailItemContent"><xss:xssFilter text="${ player.translatedTechniqueRating }" filter="html"/></div>
					<div class="col-md-1 profileDetailItemTitle">团队合作</div>
					<div class="col-md-3 profileDetailItemContent"><xss:xssFilter text="${ player.translatedTeamworkRating }" filter="html"/></div>
				</div>
				<div class="row profileDetailItemLine">
					<div class="col-md-1 profileDetailItemTitle">心理素质</div>
					<div class="col-md-3 profileDetailItemContent"><xss:xssFilter text="${ player.translatedMentalityRating }" filter="html"/></div>
					<div class="col-md-1 profileDetailItemTitle">身体素质</div>
					<div class="col-md-3 profileDetailItemContent"><xss:xssFilter text="${ player.translatedFitnessRating }" filter="html"/></div>
					<div class="col-md-4"></div>
				</div>
			</sa-panel>
		</div>
		<div id="club_panel">
			<sa-panel title="俱乐部">
				<div class="row">
					<div class="col-md-1 profileDetailItemTitle">现俱乐部</div>
					<div class="col-md-3 profileDetailItemContent"><xss:xssFilter text="${ player.userExtInfoMap['club_club_name'] }" filter="html"/></div>
					<div class="col-md-1 profileDetailItemTitle">市场价格</div>
					<div class="col-md-3 profileDetailItemContent"><xss:xssFilter text="${ player.userExtInfoMap['club_market_price'] }" filter="html"/>万元</div>
					<div class="col-md-1 profileDetailItemTitle">买断价格</div>
					<div class="col-md-3 profileDetailItemContent"><xss:xssFilter text="${ player.userExtInfoMap['club_buyout_price'] }" filter="html"/>万元</div>
				</div>
				<div class="row profileDetailItemLine">
					<div class="col-md-1 profileDetailItemTitle">经纪人</div>
					<div class="col-md-3 profileDetailItemContent"><xss:xssFilter text="${ player.userExtInfoMap['club_agent_name'] }" filter="html"/></div>
					<div class="col-md-1 profileDetailItemTitle" style="padding-right:0px;">经纪人电话</div>
					<div class="col-md-3 profileDetailItemContent"><xss:xssFilter text="${ player.userExtInfoMap['club_agent_tel'] }" filter="html"/></div>
					<div class="col-md-1 profileDetailItemTitle" style="padding-right:0px;">经纪人邮箱</div>
					<div class="col-md-3 profileDetailItemContent"><xss:xssFilter text="${ player.userExtInfoMap['club_agent_email'] }" filter="html"/></div>
				</div>
				<div class="row profileDetailItemLine">
					<div class="col-md-1 profileDetailItemTitle" style="padding-right:0px;">合同到期日</div>
					<div class="col-md-4 profileDetailItemContent"><xss:xssFilter text="${ player.userExtInfoMap['club_contract_expiration'] }" filter="html"/></div>
				</div>
			</sa-panel>
		</div>
		<div id="training_panel">
			<sa-panel title="参加过的集训">
				<c:forEach items="${trainingList}" var="training">
					<div class="row">
						<div class="col-md-4 profileDetailItemTitle"><fmt:formatDate value="${ training.startDate }" pattern="yyyy / MM / dd" /> ~ <fmt:formatDate value="${ training.endDate }" pattern="yyyy / MM / dd" /></div>
						<div class="col-md-8">
							<a type="button" class="btn-link utraining_evaluation_cursor" trainingID="${training.id}" onclick="goTraingPlan(this)">
								<xss:xssFilter text="${ training.name }" filter="html"/>
							</a>
						</div>
					</div>
				</c:forEach>
			</sa-panel>
		</div>
	</div>
</div>

<script type="text/javascript">
	$(function() {
		initEvent();
	});
	
	function initEvent() {
		buildBreadcumb("球员详情");
		$('input.panel_option').click(function() {
			var $dom = $(this);
			var targetPanelId = $dom.attr('id').split('_')[0] + '_' + 'panel';
			$('#' + targetPanelId).toggle();
		});
		
		$('#back_btn').click(function() {
			sa.ajax({
				type : "get",
				url : "<%=serverUrl%>user/showPlayerList",
				success : function(data) {
					AngularHelper.Compile($('#content'), data);
				},
				error: function() {
					alert("打开球员列表页面失败");
				}
			});
		});
		
		$('#delete_btn').click(function() {
			bootbox.dialog({
				message: "您是否要删除该球员？",
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
								url : "<%=serverUrl%>user/deleteUser?userID=" + "${ player.id }",
								success : function(data) {
									if(!data.status) {
										alert("删除球员异常");
										return;
									}
									
									sa.ajax({
										type : "get",
										url : "<%=serverUrl%>user/showPlayerList",
										success : function(data) {
											AngularHelper.Compile($('#content'), data);
										},
										error: function() {
											alert("打开球员列表页面失败");
										}
									});
								},
								error: function() {
									alert("删除球员失败");
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
				url : "<%=serverUrl%>user/showPlayerEdit?userID=" + "${ player.id }",
				success : function(data) {
					AngularHelper.Compile($('#content'), data);
				},
				error: function() {
					alert("打开编辑球员页面失败");
				}
			});
		});
		
		
		$('#test_analysis_btn').click(function() {
			var url = "<%=serverUrl%>test/test_info_player_ui?userID=${player.id}&backurl=" + encodeURIComponent("<%=serverUrl%>user/showPlayerDetail?userID=${player.id}");
			sa.ajax({
				type : "get",
				url :  url,
				success : function(data) {
					AngularHelper.Compile($('#content'), data);
				},
				error: function() {
					alert("打开测试分析页面失败");
				}
			});
		});
		
		$('#body_index_btn').click(function() {
			var url = "<%=serverUrl%>healthdata/goonepeoplehealthdata?userID=${player.id}&backurl=" + encodeURIComponent("<%=serverUrl%>user/showPlayerDetail?userID=${player.id}");
			sa.ajax({
				type : "get",
				url :  url,
				success : function(data) {
					AngularHelper.Compile($('#content'), data);
					gotoMenu('people-menu-healthdata');
				},
				error: function() {
					alert("打开测试分析页面失败");
				}
			});
		});
	}
	
	function goTraingPlan(obj) {
		var element = $(obj);
		var trainingID = element.attr("trainingID");
		var url = "<%=serverUrl%>utraining/edit_calendar?utrainingId="+trainingID+"&func=view&frompage=calendar";
		$.ajax({
			type : "get",
			url : url,
			success : function(data) {
				AngularHelper.Compile($('#content'), data);
				
				gotoMenu('regular-menu-utraining');
				rebuildBreadcumb("日常管理", "计划管理", "计划详情");
			},
			error: function() {
				alert("训练计划页面失败");
			}
		});
	}
</script>