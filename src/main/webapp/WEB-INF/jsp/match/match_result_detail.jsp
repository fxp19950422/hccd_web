<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page import="cn.sportsdata.webapp.youth.common.utils.CommonUtils" %>

<%
	String serverUrl = CommonUtils.getServerUrl(request);
%>

<div class="matchResultEditContainer">
	<div class="button_area">
		<button id="edit_btn" class="btn btn-primary" style="float: right; margin-left: 10px;">重新录入</button>
		<button id="back_btn" class="btn btn-default" style="float: right; margin-left: 10px;">返回</button>
	</div>
	<div class="clearfix" style="height: 15px;"></div>
	<div class="matchResultEditBody">
		<div id="match_record_panel">
			<sa-panel title="比分">
				<div class="matchResultSummary">
					<div>
						<div class="section">本队</div>
						<div class="section-middle vs-title">VS</div>
						<div class="section ellipsis" title="${ match.opponent }">${ match.opponent }</div>
					</div>
					<div>
						<div class="section score-title">${ match.homeScore }
						</div>
						<div class="section-middle">
							<div class="date-location-title"><fmt:formatDate value="${ match.date }" pattern="yyyy-MM-dd" />&nbsp;${ match.time }</div>
							<div class="date-location-title ellipsis" title="${ match.location }">${ match.location }</div>
						</div>
						<div class="section score-title">
						${ match.guestScore }
						</div>
					</div>
					<c:if test="${ statistics['hometime'] != null && statistics['awaytime'] != null 
					&&  (statistics['awaytime'] + statistics['hometime']) != 0 }">
					
					
					<div>
						<div class="section ">控球率:
						<fmt:formatNumber value="${ statistics['hometime']/(statistics['awaytime'] + statistics['hometime'])*100 }" pattern="#0" />%
						
						</div>
						<div class="section-middle">
						</div>
						<div class="section ">
						控球率:<fmt:formatNumber value="${ statistics['awaytime']/(statistics['awaytime'] + statistics['hometime'])*100 }" pattern="#0" />%
						</div>
					</div>
					</c:if>
				</div>
				<div class="matchResultRecords" style="margin-top: 20px;">
					<div style="width:50%;float:left;">
						<div class="record-area">
							<div class="homeTitle table-title">进球</div>
							<table id="home_score_table" class="table table-bordered text-center table-layout-fixed">
								<tr class="tableHeader">
									<th>时间</th>
									<th>进球</th>
									<th>类型</th>
									<th>方式</th>
									<th>助攻</th>
								</tr>
								<c:forEach items="${homeGoals}" var="goal" varStatus="status">
									<tr>
										<td><fmt:formatNumber type="number" value="${ goal.time / 60 }" maxFractionDigits="0"/>&#39;</td>
										<td>
											<c:choose>
												<c:when test="${!empty goal.playerId}">
													<div class="ellipsis" title="${ goal.homePlayerJerseyNumber }&nbsp;${ goal.homePlayerName }">
														${ goal.homePlayerJerseyNumber }&nbsp;${ goal.homePlayerName }
													</div>
												</c:when>
												<c:otherwise>
													<div class="ellipsis" title="乌龙球">
														乌龙球
													</div>
												</c:otherwise>
											</c:choose>
										</td>
										<td>
											<c:choose>
												<c:when test="${!empty goal.type}">
													${ goal.translatedGoalType }
												</c:when>
												<c:otherwise>
													无
												</c:otherwise>
											</c:choose>
										</td>
										<td>
											<c:choose>
												<c:when test="${!empty goal.mode}">
													${ goal.translatedGoalMode }
												</c:when>
												<c:otherwise>
													无
												</c:otherwise>
											</c:choose>
										</td>
										<td>
											<c:choose>
												<c:when test="${!empty goal.assistPlayerId}">
													<div class="ellipsis" title="${ goal.homeAssistPlayerJerseyNumber }&nbsp;${ goal.homeAssistPlayerName }">
														${ goal.homeAssistPlayerJerseyNumber }&nbsp;${ goal.homeAssistPlayerName }
													</div>
												</c:when>
												<c:otherwise>
													<div class="ellipsis" title="无">
														无
													</div>
												</c:otherwise>
											</c:choose>
										</td>
									</tr>
								</c:forEach>
							</table>
							<div class="homeTitle">
								<div style="float:left;" class="table-title">犯规</div>
							</div>
							<table id="home_foul_table" class="table table-bordered text-center table-layout-fixed">
								<tr class="tableHeader">
									<th>时间</th>
									<th>球员</th>
									<th>红 / 黄</th>
								</tr>
								<c:forEach items="${homeFouls}" var="foul" varStatus="status">
									<tr>
										<td><fmt:formatNumber type="number" value="${ foul.time / 60 }" maxFractionDigits="0"/>&#39;</td>
										<td><div class="ellipsis" title="${ foul.homePlayerJerseyNumber }&nbsp;${ foul.homePlayerName }">${ foul.homePlayerJerseyNumber }&nbsp;${ foul.homePlayerName }</div></td>
										<td>${ foul.translatedType }</td>
									</tr>
								</c:forEach>
							</table>
							<div class="homeTitle">
								<div style="float:left;" class="table-title">换人</div>
							</div>
							<table id="home_sub_table" class="table table-bordered text-center table-layout-fixed">
								<tr class="tableHeader">
									<th>时间</th>
									<th>下</th>
									<th>上</th>
								</tr>
								<c:forEach items="${homeSubs}" var="sub" varStatus="status">
									<tr>
										<td><fmt:formatNumber type="number" value="${ sub.time / 60 }" maxFractionDigits="0"/>&#39;</td>
										<td><div class="ellipsis" title="${ sub.homeOffPlayerJerseyNumber }&nbsp;${ sub.homeOffPlayerName }">${ sub.homeOffPlayerJerseyNumber }&nbsp;${ sub.homeOffPlayerName }</div></td>
										<td><div class="ellipsis" title="${ sub.homeOnPlayerJerseyNumber }&nbsp;${ sub.homeOnPlayerName }">${ sub.homeOnPlayerJerseyNumber }&nbsp;${ sub.homeOnPlayerName }</div></td>
									</tr>
								</c:forEach>
							</table>
						</div>
					</div>
					<div style="width:50%;float:right;">
						<div class="record-area">
							<div class="guestTitle table-title">进球</div>
							<table id="guest_score_table" class="table table-bordered text-center table-layout-fixed">
								<tr class="tableHeader">
									<th>时间</th>
									<th>进球</th>
									<th>类型</th>
									<th>方式</th>
									<th>助攻</th>
								</tr>
								<c:forEach items="${guestGoals}" var="goal" varStatus="status">
									<tr>
										<td><fmt:formatNumber type="number" value="${ goal.time / 60 }" maxFractionDigits="0"/>&#39;</td>
										<td><div class="ellipsis" title="${ goal.playerLabel }">${ goal.playerLabel }</div></td>
										<td>
											<c:choose>
												<c:when test="${!empty goal.type}">
													${ goal.translatedGoalType }
												</c:when>
												<c:otherwise>
													无
												</c:otherwise>
											</c:choose>
										</td>
										<td>
											<c:choose>
												<c:when test="${!empty goal.mode}">
													${ goal.translatedGoalMode }
												</c:when>
												<c:otherwise>
													无
												</c:otherwise>
											</c:choose>
										</td>
										<td>
											<c:choose>
												<c:when test="${!empty goal.assistPlayerLabel}">
													<div class="ellipsis" title="${ goal.assistPlayerLabel }">
														${ goal.assistPlayerLabel }
													</div>
												</c:when>
												<c:otherwise>
													<div class="ellipsis" title="无">
														无
													</div>
												</c:otherwise>
											</c:choose>
										</td>
									</tr>
								</c:forEach>
							</table>
							<div class="guestTitle">
								<div style="float:left;" class="table-title">犯规</div>
							</div>
							<table id="guest_foul_table" class="table table-bordered text-center table-layout-fixed">
								<tr class="tableHeader">
									<th>时间</th>
									<th>球员</th>
									<th>红 / 黄</th>
								</tr>
								<c:forEach items="${guestFouls}" var="foul" varStatus="status">
									<tr>
										<td><fmt:formatNumber type="number" value="${ foul.time / 60 }" maxFractionDigits="0"/>&#39;</td>
										<td><div class="ellipsis" title="${ foul.playerLabel }">${ foul.playerLabel }</div></td>
										<td>${ foul.translatedType }</td>
									</tr>
								</c:forEach>
							</table>
							<div class="guestTitle">
								<div style="float:left;" class="table-title">换人</div>
							</div>
							<table id="guest_sub_table" class="table table-bordered text-center table-layout-fixed">
								<tr class="tableHeader">
									<th>时间</th>
									<th>下</th>
									<th>上</th>
								</tr>
								<c:forEach items="${guestSubs}" var="sub" varStatus="status">
									<tr>
										<td><fmt:formatNumber type="number" value="${ sub.time / 60 }" maxFractionDigits="0"/>&#39;</td>
										<td><div class="ellipsis" title="${ sub.offPlayerLabel }">${ sub.offPlayerLabel }</div></td>
										<td><div class="ellipsis" title="${ sub.onPlayerLabel }">${ sub.onPlayerLabel }</div></td>
									</tr>
								</c:forEach>
							</table>
						</div>
						</div>
					</div>
				</div>
			</sa-panel>
		</div>
		<div id="match_data_panel">
			<div class="panel panel-default">
				<div class="panel-heading panel-header">
					<div style="float: left;">比赛数据</div>
					<div style="float: right;">
						<div class="btn-group">
							<button type="button" class="dropdown-btn dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
									<span id="match_data_dropdown_title">列表</span>&nbsp;<span class="caret"></span>
							</button>
							<ul class="match-data-menu dropdown-menu">
								<li><a menu-type="list" href="javascript:void(0)">列表</a></li>
								<li><a menu-type="chart" href="javascript:void(0)">图表</a></li>
							</ul>
						</div>
					</div>
					<div style="clear:both;"></div>
				</div>
				<div class="panel-body table-responsive">
					<div id="match_data_list">
						<table id="match_data_table" class="table table-bordered text-center fixed-panel_table">
							<tr class="table-header">
								<th>姓名</th>
								<th>距离</th>
								<th>射正</th>
								<th>射歪</th>
								<th>过人</th>
								<th>越位</th>
								<th>短传</th>
								<th>长传</th>
								<th>向前传</th>
								<th>传中</th>
								<th>传球失误</th>
								<th>解围</th>
								<th>犯规</th>
								<th>抢断成功</th>
								<th>抢断失败</th>
								<th>拦截</th>
								<th>扑救</th>
								<th>任意球</th>
								<th>角球</th>
							</tr>
							<tr class="table-data">
								<td>全队</td>
								<td>${ statistics['run_distance'] }</td>
								<td>${ statistics['target_shoot'] }</td>
								<td>${ statistics['aside_shoot'] }</td>
								<td>${ statistics['break_through'] }</td>
								<td>${ statistics['off_side'] }</td>
								<td>${ statistics['short_pass'] }</td>
								<td>${ statistics['long_pass'] }</td>
								<td>${ statistics['forward_pass'] }</td>
								<td>${ statistics['cross_pass'] }</td>
								<td>${ statistics['fail_pass'] }</td>
								<td>${ statistics['clearance_kick'] }</td>
								<td>${ statistics['foul'] }</td>
								<td>${ statistics['success_steal'] }</td>
								<td>${ statistics['fail_steal'] }</td>
								<td>${ statistics['intercept'] }</td>
								<td>${ statistics['save'] }</td>
								<td>${ statistics['freekick'] }</td>
								<td>${ statistics['cornerkick'] }</td>
							</tr>
							<c:forEach items="${datas}" var="data" varStatus="status">
								<tr class="table-data">
									<c:choose>
										<c:when test="${ empty data.playerJerseyNumber && empty data.playerName }">
											<td>未知球员</td>
										</c:when>
										<c:otherwise>
											<td>${ data.playerJerseyNumber }&nbsp;${ data.playerName }</td>
										</c:otherwise>
									</c:choose>
									<td>${ data.itemMapping['run_distance'] }</td>
									<td>${ data.itemMapping['target_shoot'] }</td>
									<td>${ data.itemMapping['aside_shoot'] }</td>
									<td>${ data.itemMapping['break_through'] }</td>
									<td>${ data.itemMapping['off_side'] }</td>
									<td>${ data.itemMapping['short_pass'] }</td>
									<td>${ data.itemMapping['long_pass'] }</td>
									<td>${ data.itemMapping['forward_pass'] }</td>
									<td>${ data.itemMapping['cross_pass'] }</td>
									<td>${ data.itemMapping['fail_pass'] }</td>
									<td>${ data.itemMapping['clearance_kick'] }</td>
									<td>${ data.itemMapping['foul'] }</td>
									<td>${ data.itemMapping['success_steal'] }</td>
									<td>${ data.itemMapping['fail_steal'] }</td>
									<td>${ data.itemMapping['intercept'] }</td>
									<td>${ data.itemMapping['save'] }</td>
									<td>${ data.itemMapping['freekick'] }</td>
									<td>${ data.itemMapping['cornerkick'] }</td>
								</tr>
							</c:forEach>
						</table>
					</div>
					<div id="match_data_chart" style="display:none;">
						<div style="width:50%;float:left;">
							<div style="float: left; width: 33%">
								<div class="match-data-chart-percent-title">${ statistics['shoot_percentage'] }%</div>
								<div class="match-data-chart-rect" style="padding-top: ${ 200 - statistics['shoot_percentage'] * 2 }px;">
									<div class="match-data-chart-enabled-rect"></div>
								</div>
								<div class="match-data-chart-name-title">射门精准率</div>
							</div>
							<div style="float: left; width: 33%">
								<div class="match-data-chart-percent-title">${ statistics['steal_percentage'] }%</div>
								<div class="match-data-chart-rect" style="padding-top: ${ 200 - statistics['steal_percentage'] * 2 }px;">
									<div class="match-data-chart-enabled-rect"></div>
								</div>
								<div class="match-data-chart-name-title">抢断成功率</div>
							</div>
							<div style="float: left; width: 33%">
								<div class="match-data-chart-percent-title">${ statistics['pass_percentage'] }%</div>
								<div class="match-data-chart-rect" style="padding-top: ${ 200 - statistics['pass_percentage'] * 2 }px;">
									<div class="match-data-chart-enabled-rect"></div>
								</div>
								<div class="match-data-chart-name-title">传球成功率</div>
							</div>
						</div>
						<div style="width:50%;float:right;">
							<div class="match-data-chart-right">
								<div>
									<div class="match-data-chart-block-title">射门</div>
									<div class="match-data-chart-block-seperate1"></div>
									<div class="match-data-chart-block-title"><span style="color: red;">${ statistics['target_shoot'] }</span>/<span style="color: blue;">${ statistics['all_shoots'] }</span></div>
									<div class="match-data-chart-block-seperate2"></div>
									<div class="match-data-chart-block-title">拦截</div>
									<div class="match-data-chart-block-seperate1"></div>
									<div class="match-data-chart-block-title">${ statistics['intercept'] }</div>
								</div>
								<div style="clear:both;"></div>
								<div class="match-data-chart-block-item">
									<div class="match-data-chart-block-title">进球</div>
									<div class="match-data-chart-block-seperate1"></div>
									<div class="match-data-chart-block-title">${ statistics['match_goals'] }</div>
									<div class="match-data-chart-block-seperate2"></div>
									<div class="match-data-chart-block-title">抢断</div>
									<div class="match-data-chart-block-seperate1"></div>
									<div class="match-data-chart-block-title"><span style="color: red;">${ statistics['success_steal'] }</span>/<span style="color: blue;">${ statistics['all_steals'] }</span></div>
								</div>
								<div style="clear:both;"></div>
								<div class="match-data-chart-block-item">
									<div class="match-data-chart-block-title">助攻</div>
									<div class="match-data-chart-block-seperate1"></div>
									<div class="match-data-chart-block-title">${ statistics['match_assists'] }</div>
									<div class="match-data-chart-block-seperate2"></div>
									<div class="match-data-chart-block-title">解围</div>
									<div class="match-data-chart-block-seperate1"></div>
									<div class="match-data-chart-block-title">${ statistics['clearance_kick'] }</div>
								</div>
								<div style="clear:both;"></div>
								<div class="match-data-chart-block-item">
									<div class="match-data-chart-block-title">传球</div>
									<div class="match-data-chart-block-seperate1"></div>
									<div class="match-data-chart-block-title"><span style="color: red;">${ statistics['success_pass'] }</span>/<span style="color: blue;">${ statistics['all_passes'] }</span></div>
									<div class="match-data-chart-block-seperate2"></div>
									<div class="match-data-chart-block-title">犯规</div>
									<div class="match-data-chart-block-seperate1"></div>
									<div class="match-data-chart-block-title">${ statistics['foul'] }</div>
								</div>
								<div style="clear:both;"></div>
								<div class="match-data-chart-block-item">
									<div class="match-data-chart-block-title">过人</div>
									<div class="match-data-chart-block-seperate1"></div>
									<div class="match-data-chart-block-title">${ statistics['break_through'] }</div>
									<div class="match-data-chart-block-seperate2"></div>
									<div class="match-data-chart-block-title">黄牌</div>
									<div class="match-data-chart-block-seperate1"></div>
									<div class="match-data-chart-block-title">${ statistics['yellow_cards'] }</div>
								</div>
								<div style="clear:both;"></div>
								<div class="match-data-chart-block-item">
									<div class="match-data-chart-block-title">扑救</div>
									<div class="match-data-chart-block-seperate1"></div>
									<div class="match-data-chart-block-title">${ statistics['save'] }</div>
									<div class="match-data-chart-block-seperate2"></div>
									<div class="match-data-chart-block-title">红牌</div>
									<div class="match-data-chart-block-seperate1"></div>
									<div class="match-data-chart-block-title">${ statistics['red_cards'] }</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div id="match_attach_panel">
			<div style="float:left;width:50%;">
				<sa-panel title="比赛照片">
					<div id="match_photo_container">
						<c:choose>
							<c:when test="${ !empty photos }">
								<c:forEach items="${photos}" var="photo" varStatus="status">
									<div class="added-match-photo" style="float:left; width:202px; height: 152px; margin: 0 15px 10px 15px; cursor: pointer;">
										<img class="uploaded-match-photo" src="<%=serverUrl%>hagkFile/asset?id=${ photo.assetId }" width="200" height="150">
									</div>
								</c:forEach>
		 					</c:when>
		 					<c:otherwise>
		 						<div class="text-center">暂无照片</div>
		 					</c:otherwise> 
		 				</c:choose>
					</div>
				</sa-panel>
			</div>
			<div style="float:right;width:49%;">
				<sa-panel title="附件">
					<div id="match_photo_container">
						<c:choose>
							<c:when test="${ !empty attaches }">
								<c:forEach items="${attaches}" var="attach" varStatus="status">
									<div class="added-match-attach" style="float:left; height: 165px;">
										<div style="width:107px; height: 137px; margin: 0 10px 10px 10px; position: relative; text-align: center;">
											<a href="<%=serverUrl%>hagkFile/asset?id=${ attach.assetId }">
												<c:choose>
													<c:when test="${attach.fileExt == 'doc' or attach.fileExt == 'docx'}">
														<img class="uploaded-match-attach" src="<%=serverUrl%>resources/images/attach_doc.png" width="105" height="135">
													</c:when>
													<c:when test="${attach.fileExt == 'ppt' or attach.fileExt == 'pptx'}">
														<img class="uploaded-match-attach" src="<%=serverUrl%>resources/images/attach_ppt.png" width="105" height="135">
													</c:when>
													<c:when test="${attach.fileExt == 'xls' or attach.fileExt == 'xlsx'}">
														<img class="uploaded-match-attach" src="<%=serverUrl%>resources/images/attach_xls.png" width="105" height="135">
													</c:when>
													<c:when test="${attach.fileExt == 'rar'}">
														<img class="uploaded-match-attach" src="<%=serverUrl%>resources/images/attach_rar.png" width="105" height="135">
													</c:when>
													<c:when test="${attach.fileExt == 'zip'}">
														<img class="uploaded-match-attach" src="<%=serverUrl%>resources/images/attach_zip.png" width="105" height="135">
													</c:when>
													<c:when test="${attach.fileExt == 'txt'}">
														<img class="uploaded-match-attach" src="<%=serverUrl%>resources/images/attach_txt.png" width="105" height="135">
													</c:when>
													<c:otherwise>
														<img class="uploaded-match-attach" src="<%=serverUrl%>resources/images/general_attach.png" width="105" height="135">
													</c:otherwise>
												</c:choose>
											</a>
											<div style="margin-top: 5px; font-size: 12px; color: #333333; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;">
												<span class="attach-file-name">${ attach.originalFileName }</span>
											</div>
										</div>
									</div>
								</c:forEach>
		 					</c:when>
		 					<c:otherwise>
		 						<div class="text-center">暂无附件</div>
		 					</c:otherwise> 
		 				</c:choose>
					</div>
				</sa-panel>
			</div>
			<div style="clear:both;"></div>
		</div>
	</div>
</div>
<div class="modal fade" id="view_image_modal" tabindex="-1" role="dialog" aria-labelledby="viewImageLabel" style="z-index: 100001; display: none;padding-right:5px;padding-left:5px;">
	<div class="modal-dialog" role="document" style="width: 100%; text-align: center;">
		<button type="button" class="close" data-dismiss="modal"
			style="border: 1px solid #000;background-color:#000;  opacity:0.5; -moz-border-radius: 20px; -webkit-border-radius: 20px; border-radius: 20px; z-index: 100011; width: 40px; height: 40px; position: fixed; right: 20px; top: 20px;"
			aria-label="Close">
			<span aria-hidden="true" style="color: #fff;">&times;</span>
		</button>
		<div class="modal-body" id="view_image_body" style="padding: 0px;overflow: auto;height: 700px;">
			<img id="view_image" style="height: auto; width: auto;">
		</div>
	</div>
</div>

<script type="text/javascript">
	$(function() {
		setTimeout(function() {
			initEvent();
		}, 50);
	});
	
	function initEvent() {
		buildBreadcumb("比赛结果查看");
		
		$('ul.match-data-menu').on('click', 'a', function(evt) {
			var $target = $(evt.target);
			var type = $target.attr('menu-type');
			
			$('#match_data_dropdown_title').text($target.text());
			
			if(type === 'list') {
				$('#match_data_list').show();
				$('#match_data_chart').hide();
			} else if(type === 'chart') {
				$('#match_data_list').hide();
				$('#match_data_chart').show();
			}
		});
		
		$('#back_btn').click(function() {
			sa.ajax({
				type : "get",
				url : "<%=serverUrl%>match/showMatchResultList",
				success : function(data) {
					AngularHelper.Compile($('#content'), data);
				},
				error: function() {
					alert("打开比赛结果列表页面失败");
				}
			});
		});
		
		$('#edit_btn').click(function() {	
			sa.ajax({
				type : "get",
				url : '<%=serverUrl%>match/showMatchResultEdit?matchID=${ match.id }',
				success : function(data) {
					AngularHelper.Compile($('#content'), data);
				},
				error: function() {
					alert("打开比赛结果页面失败");
				}
			});
		});
		
		$('div.matchResultEditContainer').on('click', 'img.uploaded-match-photo', function(evt) {
			var $target = $(evt.target);
			
			$('#view_image').attr('src', $target.attr('src'));
			$('#view_image_modal').modal('show');
		});
	}
</script>