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
	UserVO coachInfo = (UserVO) request.getAttribute("coach");
	String age = "暂无年龄信息";
	Timestamp birthday = coachInfo.getBirthday();
	if(birthday != null) {
		age = String.valueOf(Calendar.getInstance().get(Calendar.YEAR) - Integer.parseInt(new SimpleDateFormat("yyyy").format(birthday))) + "岁";
	}
%>

<div>
	<div class="profileDetailHeader">
		<div class="profileInfo">
			<c:choose>
				<c:when test="${ !empty coach.avatar }">
					<img class="profileDetailAvatar" src="<%=serverUrl%>file/downloadFile?fileName=${ coach.avatar }"></img>
				</c:when>
				<c:otherwise>
					<img class="profileDetailAvatar" src="<%=serverUrl%>resources/images/user_avatar.png"></img>
				</c:otherwise>
			</c:choose>
			<div class="profileDetailName"><xss:xssFilter text="${coach.name}" filter="html" /></div>
			<div class="profileDetailData">
				<span><%= age %></span>
				<span>&nbsp;/&nbsp;</span>
				<span>
					<c:choose>
						<c:when test="${ !empty coach.nationality }">
							${ coach.nationality }
						</c:when>
						<c:otherwise>
							暂无国籍信息
						</c:otherwise>
					</c:choose>
				</span>
				<span>&nbsp;/&nbsp;</span>
				<span>
					<c:choose>
					<c:when test="${ !empty coach.translatedCoachType }">
						${ coach.translatedCoachType }
					</c:when>
					<c:otherwise>
						暂无职务
					</c:otherwise>
				</c:choose>
				</span>
			</div>
			
		</div>
		<div class="profileAction coach_detail_button_area">
			<button id="edit_btn" class="btn btn-primary" style="float: right; margin-left: 10px;">编辑</button>
			<button id="create_account_btn" class="btn btn-primary" style="float: right; margin-left: 10px;">创建账号</button>
			<button id="delete_btn" class="btn btn-danger" style="float: right; margin-left: 10px;">删除</button>
			<button id="back_btn" class="btn btn-default" style="float: right; margin-left: 10px;">返回</button>
			
		</div>
	</div>
	<div class="clearfix"></div>
	<div class="profileDetailContent" style="margin-top: 15px;">
		<div id="basic_panel">
			<sa-panel title="基本资料">
				<div class="row">
					<div class="col-md-1 profileDetailItemTitle">姓名</div>
					<div class="col-md-3 profileDetailItemContent"><xss:xssFilter text="${coach.name}" filter="html"/></div>
					<div class="col-md-1 profileDetailItemTitle">生日</div>
					<div class="col-md-3 profileDetailItemContent"><fmt:formatDate value="${ coach.birthday }" pattern="yyyy-MM-dd" /></div>
					<div class="col-md-1 profileDetailItemTitle">身份证</div>
					<div class="col-md-3 profileDetailItemContent">${ coach.idCard }</div>
					
				</div>
				
				<div class="row profileDetailItemLine">
					<div class="col-md-1 profileDetailItemTitle">电话</div>
					<div class="col-md-3 profileDetailItemContent">${ coach.tel }</div>
					<div class="col-md-1 profileDetailItemTitle">邮箱</div>
					<div class="col-md-3 profileDetailItemContent">${ coach.email }</div>
					<div class="col-md-1 profileDetailItemTitle">护照号</div>
					<div class="col-md-3 profileDetailItemContent">${ coach.passport }</div>
				</div>
				
				<div class="row profileDetailItemLine">
					<div class="col-md-1 profileDetailItemTitle">国籍</div>
					<div class="col-md-3 profileDetailItemContent">${ coach.nationality }</div>
					<div class="col-md-1 profileDetailItemTitle">出生地</div>
					<div class="col-md-3 profileDetailItemContent">${ coach.birthPlace }</div>
					<div class="col-md-1 profileDetailItemTitle">地址</div>
					<div class="col-md-3 profileDetailItemContent">${ coach.homeAddress }</div>
				</div>
			</sa-panel>
			
		</div>
	</div>
</div>

<script type="text/javascript">
	$(setTimeout(function() {
		initEvent();
	}, 50));

	function initEvent() {
		buildBreadcumb("教练详情");
		$('input.panel_option').click(function() {
			var $dom = $(this);
			var targetPanelId = $dom.attr('id').split('_')[0] + '_' + 'panel';
			$('#' + targetPanelId).toggle();
		});
		
		$('#edit_btn').click(function() {
			sa.ajax({
				type : "get",
				url : "<%=serverUrl%>user/showCoachEdit?userID=" + "${ coach.id }",
				success : function(data) {
					//TODO: will update the container later
					AngularHelper.Compile($('#content'), data);
				},
				error: function() {
					alert("打开编辑球员页面失败");
				}
			});
		});
		
		$('#delete_btn').click(function() {
			bootbox.dialog({
				message: "您是否要删除该教练？",
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
								url : "<%=serverUrl%>user/deleteUser?userID=" + "${ coach.id }",
								success : function(data) {
									if(!data.status) {
										alert("删除教练异常");
										return;
									}
									
									sa.ajax({
										type : "get",
										url : "<%=serverUrl%>user/showCoachList",
										success : function(data) {
											AngularHelper.Compile($('#content'), data);
										},
										error: function() {
											alert("打开教练列表页面失败");
										}
									});
								},
								error: function() {
									alert("删除教练失败");
								}
							});
						}
					}
				}
			});
		});
		
		$('#create_account_btn').click(function() {
			sa.ajax({
				type : "get",
				url : "<%=serverUrl%>user/account_add?coachId="+"${ coach.id }",
				success : function(data) {
					AngularHelper.Compile($('#content'), data);
				},
				error: function() {
					alert("打开创建账号页面失败");
				}
			});
		});
		
		$('#back_btn').click(function() {
			sa.ajax({
				type : "get",
				url : "<%=serverUrl%>user/showCoachList",
				success : function(data) {
					AngularHelper.Compile($('#content'), data);
				},
				error: function() {
					alert("打开教练列表页面失败");
				}
			});
		});
	}
</script>