<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page import="cn.sportsdata.webapp.youth.common.utils.CommonUtils" %>

<%
	String serverUrl = CommonUtils.getServerUrl(request);
%>

<div class="matchResultEditContainer">
	<div class="modal fade" id="home_goal_record_modal" tabindex="-1" role="dialog" aria-labelledby="home_goal_record_modal_label">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
        			<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        			<h4 class="modal-title" id="home_goal_record_modal_label">进球记录</h4>
      			</div>
      			<div class="modal-body" style="background-color:#ecf0f5;">
      				<div>
						<button id="home_goal_record_save_btn" class="btn btn-primary" style="float: right; margin-left: 10px;">保存</button>
						<button id="home_goal_record_cancel_btn" class="btn btn-default" style="float: right; margin-left: 10px;">取消</button>
						<div style="clear:both;"></div>
					</div>
      				<table id="home_goal_record_table" class="table table-bordered text-center table-layout-fixed" style="background-color: #fff; margin-top: 10px;">
      					<tr class="table-header">
      						<th>时间</th>
      						<th>进球</th>
      						<th>助攻</th>
      					</tr>
      					<tr class="table-item">
      						<td>
      							<select class="match-select1 goal-time" style="width: 100%;">
									<c:forEach var="time"  begin="1" end="120">
										<option value="${ time * 60 }">${ time }&#39;</option>
									</c:forEach>
								</select>
      						</td>
      						<td>
      							<select class="match-select1 goal-player" style="width: 100%;">
									<c:forEach items="${players}" var="player" varStatus="status">
										<option value="${ player.id }">${ player.userExtInfoMap['professional_jersey_number'] }&nbsp;${ player.name }</option>
									</c:forEach>
									<option value='0'>乌龙球</option>
								</select>
      						</td>
      						<td>
      							<select class="match-select1 goal-assist-player" style="width: 100%;">
									<option value='0'>无</option>
									<c:forEach items="${players}" var="player" varStatus="status">
										<option value="${ player.id }">${ player.userExtInfoMap['professional_jersey_number'] }&nbsp;${ player.name }</option>
									</c:forEach>
								</select>
      						</td>
      					</tr>
      				</table>
      			</div>
      		</div>
      	</div>
   	</div>
   	<div class="modal fade" id="guest_goal_record_modal" tabindex="-1" role="dialog" aria-labelledby="guest_goal_record_modal_label">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
        			<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        			<h4 class="modal-title" id="guest_goal_record_modal_label">进球记录</h4>
      			</div>
      			<div class="modal-body" style="background-color:#ecf0f5;">
      				<div>
						<button id="guest_goal_record_save_btn" class="btn btn-primary" style="float: right; margin-left: 10px;">保存</button>
						<button id="guest_goal_record_cancel_btn" class="btn btn-default" style="float: right; margin-left: 10px;">取消</button>
						<div style="clear:both;"></div>
					</div>
      				<table id="guest_goal_record_table" class="table table-bordered text-center table-layout-fixed" style="background-color: #fff; margin-top: 10px;">
      					<tr class="table-header">
      						<th>时间</th>
      						<th>进球</th>
      						<th>助攻</th>
      					</tr>
      					<tr class="table-item">
      						<td>
      							<select class="match-select1 goal-time" style="width: 100%;">
									<c:forEach var="time"  begin="1" end="120">
										<option value="${ time * 60 }">${ time }&#39;</option>
									</c:forEach>
								</select>
      						</td>
      						<td>
      							<input class="goal-player form-control" type="text" style="width: 100%;">
      						</td>
      						<td>
      							<input class="goal-assist-player form-control" type="text" style="width: 100%;">
      						</td>
      					</tr>
      				</table>
      			</div>
      		</div>
      	</div>
   	</div>
	<div class="button_area">
		<button id="edit_btn" class="btn btn-primary" style="float: right; margin-left: 10px;">保存</button>
		<button id="back_btn" class="btn btn-default" style="float: right; margin-left: 10px;">取消</button>
	</div>
	<div class="matchResultEditAction">
		<input id="match_record_option" class="panel_option" type="checkbox" checked disabled><label for="basic_option">&nbsp;比分</label>
		<input id="match_data_option" class="panel_option matchResultEditActionItem" type="checkbox" checked><label for="professional_option">&nbsp;比赛数据</label>
		<input id="match_attach_option" class="panel_option matchResultEditActionItem" type="checkbox" checked><label for="technical_option">&nbsp;附件</label>
	</div>
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
						<div class="section">
							<select id="home_score_select" class="match-select1 point-select">
								<c:forEach var="score"  begin="0" end="50">
									<option value="${ score }" <c:if test="${match.homeScore == score}">selected = "selected"</c:if>>${ score }</option>
								</c:forEach>
							</select>
						</div>
						<div class="section-middle">
							<div class="date-location-title"><fmt:formatDate value="${ match.date }" pattern="yyyy-MM-dd" />&nbsp;${ match.time }</div>
							<div class="date-location-title ellipsis" title="${ match.location }">${ match.location }</div>
						</div>
						<div class="section">
							<select id="guest_score_select" class="match-select1 point-select">
								<c:forEach var="score"  begin="0" end="50">
									<option value="${ score }" <c:if test="${match.guestScore == score}">selected = "selected"</c:if>>${ score }</option>
								</c:forEach>
							</select>
						</div>
					</div>
				</div>
				<div class="matchResultRecords" style="margin-top: 20px;">
					<div style="width:50%;float:left;">
						<div class="record-area">
							<div class="homeTitle table-title">进球</div>
							<table id="home_score_table" class="table table-bordered text-center table-layout-fixed">
								<tr class="tableHeader">
									<th>进球</th>
									<th>类型</th>
									<th>方式</th>
								</tr>
								<c:forEach items="${homeGoals}" var="goal" varStatus="status">
									<tr class="table-item">
										<td>
											<c:choose>
												<c:when test="${!empty goal.playerId}">
													<div class="home-goal-player-col goal-player-dv ellipsis" title="${ goal.homePlayerJerseyNumber }&nbsp;${ goal.homePlayerName }">
														${ goal.homePlayerJerseyNumber }&nbsp;${ goal.homePlayerName }
													</div>
												</c:when>
												<c:otherwise>
													<div class="home-goal-player-col goal-player-dv ellipsis" title="乌龙球">
														乌龙球
													</div>
												</c:otherwise>
											</c:choose>
											<input class="goal-time" type="hidden" value="${ goal.time }" />
											<input class="goal-player" type="hidden" value="<c:choose><c:when test="${!empty goal.playerId}">${ goal.playerId }</c:when><c:otherwise>0</c:otherwise></c:choose>" />
											<input class="goal-assist-player" type="hidden" value="<c:choose><c:when test="${!empty goal.assistPlayerId}">${ goal.assistPlayerId }</c:when><c:otherwise>0</c:otherwise></c:choose>" />
										</td>
										<td>
											<select class="match-select goal-type" style="width: 100%;">
												<option value='normal' <c:if test="${goal.type == 'normal'}">selected = "selected"</c:if>>运动战</option>
												<option value='penalty' <c:if test="${goal.type == 'penalty'}">selected = "selected"</c:if>>点球</option>
												<option value='corner' <c:if test="${goal.type == 'corner'}">selected = "selected"</c:if>>角球</option>
												<option value='freekick' <c:if test="${goal.type == 'freekick'}">selected = "selected"</c:if>>任意球</option>
												<option value='' <c:if test="${goal.type == null}">selected = "selected"</c:if>>无</option>
											</select>
										</td>
										<td>
											<select class="match-select goal-mode" style="width: 100%;">
												<option value='left' <c:if test="${goal.mode == 'left'}">selected = "selected"</c:if>>左脚</option>
												<option value='right' <c:if test="${goal.mode == 'right'}">selected = "selected"</c:if>>右脚</option>
												<option value='header' <c:if test="${goal.mode == 'header'}">selected = "selected"</c:if>>头球</option>
												<option value='' <c:if test="${goal.type == null}">selected = "selected"</c:if>>无</option>
											</select>
										</td>
									</tr>
								</c:forEach>
							</table>
							<div class="homeTitle">
								<div style="float:left;" class="table-title">犯规</div>
								<div style="float:right;">
									<span id="home_foul_plus" class="record-action">+</span>&nbsp;&nbsp;<span id="home_foul_minus" class="record-action">-</span>
								</div>
							</div>
							<table id="home_foul_table" class="table table-bordered text-center table-layout-fixed">
								<tr class="tableHeader">
									<th>时间</th>
									<th>球员</th>
									<th>红 / 黄</th>
									
									<c:forEach items="${homeFouls}" var="foul" varStatus="status">
										<tr class="table-item">
											<td>
												<select class="match-select foul-time" style="width: 100%;">
													<c:forEach var="time"  begin="1" end="120">
														<option value="${ time * 60 }" <c:if test="${foul.time == time * 60 }">selected = "selected"</c:if>>${ time }&#39;</option>
													</c:forEach>
												</select>
											</td>
											<td>
												<select class="match-select foul-player" style="width: 100%;">
													<c:forEach items="${players}" var="player" varStatus="status">
														<option value="${ player.id }" <c:if test="${foul.playerId == player.id}">selected = "selected"</c:if>>${ player.userExtInfoMap['professional_jersey_number'] }&nbsp;${ player.name }</option>
													</c:forEach>
												</select>
											</td>
											<td>
												<select class="match-select foul-type" style="width: 100%;">
													<option value='yellow' <c:if test="${foul.type == 'yellow'}">selected = "selected"</c:if>>黄</option>
													<option value='red' <c:if test="${foul.type == 'red'}">selected = "selected"</c:if>>红</option>
												</select>
											</td>
										</tr>
									</c:forEach>
								</tr>
							</table>
							<div class="homeTitle">
								<div style="float:left;" class="table-title">换人</div>
								<div style="float:right;">
									<span id="home_sub_plus" class="record-action">+</span>&nbsp;&nbsp;<span id="home_sub_minus" class="record-action">-</span>
								</div>
							</div>
							<table id="home_sub_table" class="table table-bordered text-center table-layout-fixed">
								<tr class="tableHeader">
									<th>时间</th>
									<th>下</th>
									<th>上</th>
								</tr>
								
								<c:forEach items="${homeSubs}" var="sub" varStatus="status">
									<tr class="table-item">
										<td>
											<select class="match-select sub-time" style="width: 100%;">
												<c:forEach var="time"  begin="1" end="120">
													<option value="${ time * 60 }" <c:if test="${sub.time == time * 60 }">selected = "selected"</c:if>>${ time }&#39;</option>
												</c:forEach>
											</select>
										</td>
										<td>
											<select class="match-select sub-off-player" style="width: 100%;">
												<c:forEach items="${players}" var="player" varStatus="status">
													<option value="${ player.id }" <c:if test="${sub.offPlayerId == player.id}">selected = "selected"</c:if>>${ player.userExtInfoMap['professional_jersey_number'] }&nbsp;${ player.name }</option>
												</c:forEach>
											</select>
										</td>
										<td>
											<select class="match-select sub-on-player" style="width: 100%;">
												<c:forEach items="${players}" var="player" varStatus="status">
													<option value="${ player.id }" <c:if test="${sub.onPlayerId == player.id}">selected = "selected"</c:if>>${ player.userExtInfoMap['professional_jersey_number'] }&nbsp;${ player.name }</option>
												</c:forEach>
											</select>
										</td>
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
									<th>进球</th>
									<th>类型</th>
									<th>方式</th>
								</tr>
								
								<c:forEach items="${guestGoals}" var="goal" varStatus="status">
									<tr class="table-item">
										<td>
											<div class="guest-goal-player-col goal-player-dv ellipsis" title="${ goal.playerLabel }">${ goal.playerLabel }</div>
											<input class="goal-time" type="hidden" value="${ goal.time }" />
											<input class="goal-player" type="hidden" value="${ goal.playerLabel }" />
											<input class="goal-assist-player" type="hidden" value="${ goal.assistPlayerLabel }" />
										</td>
										<td>
											<select class="match-select goal-type" style="width: 100%;">
												<option value='normal' <c:if test="${goal.type == 'normal'}">selected = "selected"</c:if>>运动战</option>
												<option value='penalty' <c:if test="${goal.type == 'penalty'}">selected = "selected"</c:if>>点球</option>
												<option value='corner' <c:if test="${goal.type == 'corner'}">selected = "selected"</c:if>>角球</option>
												<option value='freekick' <c:if test="${goal.type == 'freekick'}">selected = "selected"</c:if>>任意球</option>
												<option value='' <c:if test="${goal.type == null}">selected = "selected"</c:if>>无</option>
											</select>
										</td>
										<td>
											<select class="match-select goal-mode" style="width: 100%;">
												<option value='left' <c:if test="${goal.mode == 'left'}">selected = "selected"</c:if>>左脚</option>
												<option value='right' <c:if test="${goal.mode == 'right'}">selected = "selected"</c:if>>右脚</option>
												<option value='header' <c:if test="${goal.mode == 'header'}">selected = "selected"</c:if>>头球</option>
												<option value='' <c:if test="${goal.mode == null}">selected = "selected"</c:if>>无</option>
											</select>
										</td>
									</tr>
								</c:forEach>
							</table>
							<div class="guestTitle">
								<div style="float:left;" class="table-title">犯规</div>
								<div style="float:right;">
									<span id="guest_foul_plus" class="record-action">+</span>&nbsp;&nbsp;<span id="guest_foul_minus" class="record-action">-</span>
								</div>
							</div>
							<table id="guest_foul_table" class="table table-bordered text-center table-layout-fixed">
								<tr class="tableHeader">
									<th>时间</th>
									<th>球员</th>
									<th>红 / 黄</th>
								</tr>
								
								<c:forEach items="${guestFouls}" var="foul" varStatus="status">
									<tr class="table-item">
										<td>
											<select class="match-select foul-time" style="width: 100%;">
												<c:forEach var="time"  begin="1" end="120">
													<option value="${ time * 60 }" <c:if test="${foul.time == time * 60 }">selected = "selected"</c:if>>${ time }&#39;</option>
												</c:forEach>
											</select>
										</td>
										<td><input class="foul-player form-control foul-player-dv" type="text" style="width: 100%;" value="${ foul.playerLabel }"></td>
										<td>
											<select class="match-select foul-type" style="width: 100%;">
												<option value='yellow' <c:if test="${foul.type == 'yellow'}">selected = "selected"</c:if>>黄</option>
												<option value='red' <c:if test="${foul.type == 'red'}">selected = "selected"</c:if>>红</option>
											</select>
										</td>
									</tr>
								</c:forEach>
							</table>
							<div class="guestTitle">
								<div style="float:left;" class="table-title">换人</div>
								<div style="float:right;">
									<span id="guest_sub_plus" class="record-action">+</span>&nbsp;&nbsp;<span id="guest_sub_minus" class="record-action">-</span>
								</div>
							</div>
							<table id="guest_sub_table" class="table table-bordered text-center table-layout-fixed">
								<tr class="tableHeader">
									<th>时间</th>
									<th>下</th>
									<th>上</th>
								</tr>
								
								<c:forEach items="${guestSubs}" var="sub" varStatus="status">
									<tr class="table-item">
										<td>
											<select class="match-select sub-time" style="width: 100%;">
												<c:forEach var="time"  begin="1" end="120">
													<option value="${ time * 60 }" <c:if test="${sub.time == time * 60 }">selected = "selected"</c:if>>${ time }&#39;</option>
												</c:forEach>
											</select>
										</td>
										<td><input class="sub-off-player form-control sub-off-player-dv" type="text" style="width: 100%;" value="${ sub.offPlayerLabel }"></td>
										<td><input class="sub-on-player form-control sub-on-player-dv" type="text" style="width: 100%;" value="${ sub.onPlayerLabel }"></td>
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
					<div style="float: right;"><a id="match_data_upload" href="javascript:void(0);" style="color: white; margin-left: 10px;">导入</a></div>
					<div style="float: right;"><a href="<%= serverUrl %>hagkFile/downloadTemplate?fileName=match_data_template.xls" style="color: white; margin-left: 10px;">下载模板</a></div>
					<div class="clearfix"></div>
				</div>
				<div class="panel-body table-responsive">
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
						<tr class="table-item table-data" pid="0">
							<td dname="player_label">全队</td>
							<td dname="run_distance">${ statistics['run_distance'] }</td>
							<td dname="target_shoot">${ statistics['target_shoot'] }</td>
							<td dname="aside_shoot">${ statistics['aside_shoot'] }</td>
							<td dname="break_through">${ statistics['break_through'] }</td>
							<td dname="off_side">${ statistics['off_side'] }</td>
							<td dname="short_pass">${ statistics['short_pass'] }</td>
							<td dname="long_pass">${ statistics['long_pass'] }</td>
							<td dname="forward_pass">${ statistics['forward_pass'] }</td>
							<td dname="cross_pass">${ statistics['cross_pass'] }</td>
							<td dname="fail_pass">${ statistics['fail_pass'] }</td>
							<td dname="clearance_kick">${ statistics['clearance_kick'] }</td>
							<td dname="foul">${ statistics['foul'] }</td>
							<td dname="success_steal">${ statistics['success_steal'] }</td>
							<td dname="fail_steal">${ statistics['fail_steal'] }</td>
							<td dname="intercept">${ statistics['intercept'] }</td>
							<td dname="save">${ statistics['save'] }</td>
							<td dname="freekick">${ statistics['freekick'] }</td>
							<td dname="cornerkick">${ statistics['cornerkick'] }</td>
							<td dname="hometime" style="display:none">${ statistics['hometime'] }</td>
							<td dname="awaytime" style="display:none">${ statistics['awaytime'] }</td>
							<td dname="totaltime" style="display:none">${ statistics['totaltime'] }</td>
						</tr>
						<c:forEach items="${datas}" var="data" varStatus="status">
							<tr class="table-item table-data" pid="${ data.playerId }">
								<c:choose>
										<c:when test="${ empty data.playerJerseyNumber && empty data.playerName }">
											<td dname="player_label">未知球员</td>
										</c:when>
										<c:otherwise>
											<td dname="player_label">${ data.playerJerseyNumber }&nbsp;${ data.playerName }</td>
										</c:otherwise>
								</c:choose>
								<td dname="run_distance">${ data.itemMapping['run_distance'] }</td>
								<td dname="target_shoot">${ data.itemMapping['target_shoot'] }</td>
								<td dname="aside_shoot">${ data.itemMapping['aside_shoot'] }</td>
								<td dname="break_through">${ data.itemMapping['break_through'] }</td>
								<td dname="off_side">${ data.itemMapping['off_side'] }</td>
								<td dname="short_pass">${ data.itemMapping['short_pass'] }</td>
								<td dname="long_pass">${ data.itemMapping['long_pass'] }</td>
								<td dname="forward_pass">${ data.itemMapping['forward_pass'] }</td>
								<td dname="cross_pass">${ data.itemMapping['cross_pass'] }</td>
								<td dname="fail_pass">${ data.itemMapping['fail_pass'] }</td>
								<td dname="clearance_kick">${ data.itemMapping['clearance_kick'] }</td>
								<td dname="foul">${ data.itemMapping['foul'] }</td>
								<td dname="success_steal">${ data.itemMapping['success_steal'] }</td>
								<td dname="fail_steal">${ data.itemMapping['fail_steal'] }</td>
								<td dname="intercept">${ data.itemMapping['intercept'] }</td>
								<td dname="save">${ data.itemMapping['save'] }</td>
								<td dname="freekick">${ data.itemMapping['freekick'] }</td>
								<td dname="cornerkick">${ data.itemMapping['cornerkick'] }</td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</div>
		</div>
		<div id="match_attach_panel">
			<div style="float:left;width:50%;">
				<div class="panel panel-default">
					<div class="panel-heading panel-header">
						<div style="float: left;">比赛照片（最大上传文件大小：5MB）</div>
						<div style="float: right;"><a id="match_photo_delete" href="javascript:void(0);" style="color: white;">删除</a></div>
						<div class="clearfix"></div>
					</div>
					<div class="panel-body table-responsive">
						<div id="match_photo_container">
							<c:forEach items="${photos}" var="photo" varStatus="status">
								<div class="added-match-photo" style="float:left; width:202px; height: 152px; margin: 0 15px 10px 15px; position: relative; border: 1px solid #cecece;">
									<img class="uploaded-match-photo" src="<%=serverUrl%>hagkFile/asset?id=${ photo.assetId }" width="200" height="150">
									<input type="checkbox" name="deleted_match_photo" style="position: absolute; left: -5px; top: -5px; margin: 0;">
									<input type="hidden" class="photo-hidden-field" fPath="${ photo.path }" ofName="${ photo.originalFileName }" extName="${ photo.fileExt }" fSize="${ photo.fileSize }">
								</div>
							</c:forEach>
							<div style="float:left; margin: 0 15px 10px 15px;">
								<img id="match_photo_upload" src="<%=serverUrl%>resources/images/photo_plus.png" width="200" height="150">
							</div>
						</div>
					</div>
				</div>
			</div>
			<div style="float:right;width:49%;">
				<div class="panel panel-default">
					<div class="panel-heading panel-header">
						<div style="float: left;">附件（最大上传文件大小：20MB）</div>
						<div style="float: right;"><a id="match_attach_delete" href="javascript:void(0);" style="color: white;">删除</a></div>
						<div class="clearfix"></div>
					</div>
					<div class="panel-body table-responsive">
						<div id="match_attach_container">
							<c:forEach items="${attaches}" var="attach" varStatus="status">
								<div class="added-match-attach" style="float:left; height: 165px;">
									<div style=" width:107px; height: 137px; margin: 0 10px 10px 10px; position: relative; border: 1px solid #cecece; text-align: center;">
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
										<div style="margin-top: 5px; font-size: 12px; color: #333333; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;">
											<span class="attach-file-name">${ attach.originalFileName }</span>
										</div>
										<input type="checkbox" name="deleted_match_attach" style="position: absolute; left: -5px; top: -5px; margin: 0;">
										<input type="hidden" class="attach-hidden-field" fPath="${ attach.path }" ofName="${ attach.originalFileName }" extName="${ attach.fileExt }" fSize="${ attach.fileSize }">
									</div>
								</div>
							</c:forEach>
							<div style="float:left; margin: 0 10px 10px 10px;">
								<img id="match_attach_upload" src="<%=serverUrl%>resources/images/attach_plus.png" width="105" height="135">
							</div>
						</div>
		 			</div>
		 		</div>
			</div>
			<div style="clear:both;"></div>
		</div>
	</div>
	<table class="hidden" id="home_goal_row_template">
		<tr class="table-item">
			<td>
				<div class="home-goal-player-col goal-player-plus goal-player-dv">+</div>
				<input class="goal-time" type="hidden" value="${ goal.time }" />
				<input class="goal-player" type="hidden" />
				<input class="goal-assist-player" type="hidden" />
			</td>
			<td>
				<select class="match-select goal-type" style="width: 100%;">
					<option value='normal'>运动战</option>
					<option value='penalty'>点球</option>
					<option value='corner'>角球</option>
					<option value='freekick'>任意球</option>
					<option value=''>无</option>
				</select>
			</td>
			<td>
				<select class="match-select goal-mode" style="width: 100%;">
					<option value='left'>左脚</option>
					<option value='right'>右脚</option>
					<option value='header'>头球</option>
					<option value=''>无</option>
				</select>
			</td>
		</tr>
	</table>
	<table class="hidden" id="guest_goal_row_template">
		<tr class="table-item">
			<td>
				<div class="guest-goal-player-col goal-player-plus goal-player-dv">+</div>
				<input class="goal-time" type="hidden" />
				<input class="goal-player" type="hidden" />
				<input class="goal-assist-player" type="hidden" />
			</td>
			<td>
				<select class="match-select goal-type" style="width: 100%;">
					<option value='normal'>运动战</option>
					<option value='penalty'>点球</option>
					<option value='corner'>角球</option>
					<option value='freekick'>任意球</option>
					<option value=''>无</option>
				</select>
			</td>
			<td>
				<select class="match-select goal-mode" style="width: 100%;">
					<option value='left'>左脚</option>
					<option value='right'>右脚</option>
					<option value='header'>头球</option>
					<option value=''>无</option>
				</select>
			</td>
		</tr>
	</table>
	<table class="hidden" id="home_foul_row_template">
		<tr class="table-item">
			<td>
				<select class="match-select foul-time" style="width: 100%;">
					<c:forEach var="time"  begin="1" end="120">
						<option value="${ time * 60 }">${ time }&#39;</option>
					</c:forEach>
				</select>
			</td>
			<td>
				<select class="match-select foul-player" style="width: 100%;">
					<c:forEach items="${players}" var="player" varStatus="status">
						<option value="${ player.id }">${ player.userExtInfoMap['professional_jersey_number'] }&nbsp;${ player.name }</option>
					</c:forEach>
				</select>
			</td>
			<td>
				<select class="match-select foul-type" style="width: 100%;">
					<option value='yellow'>黄</option>
					<option value='red'>红</option>
				</select>
			</td>
		</tr>
	</table>
	<table class="hidden" id="guest_foul_row_template">
		<tr class="table-item">
			<td>
				<select class="match-select foul-time" style="width: 100%;">
					<c:forEach var="time"  begin="1" end="120">
						<option value="${ time * 60 }">${ time }&#39;</option>
					</c:forEach>
				</select>
			</td>
			<td><input class="foul-player form-control foul-player-dv" type="text" style="width: 100%;"></td>
			<td>
				<select class="match-select foul-type" style="width: 100%;">
					<option value='yellow'>黄</option>
					<option value='red'>红</option>
				</select>
			</td>
		</tr>
	</table>
	<table class="hidden" id="home_sub_row_template">
		<tr class="table-item">
			<td>
				<select class="match-select sub-time" style="width: 100%;">
					<c:forEach var="time"  begin="1" end="120">
						<option value="${ time * 60 }">${ time }&#39;</option>
					</c:forEach>
				</select>
			</td>
			<td>
				<select class="match-select sub-off-player" style="width: 100%;">
					<c:forEach items="${players}" var="player" varStatus="status">
						<option value="${ player.id }">${ player.userExtInfoMap['professional_jersey_number'] }&nbsp;${ player.name }</option>
					</c:forEach>
				</select>
			</td>
			<td>
				<select class="match-select sub-on-player" style="width: 100%;">
					<c:forEach items="${players}" var="player" varStatus="status">
						<option value="${ player.id }">${ player.userExtInfoMap['professional_jersey_number'] }&nbsp;${ player.name }</option>
					</c:forEach>
				</select>
			</td>
		</tr>
	</table>
	<table class="hidden" id="guest_sub_row_template">
		<tr class="table-item">
			<td>
				<select class="match-select sub-time" style="width: 100%;">
					<c:forEach var="time"  begin="1" end="120">
						<option value="${ time * 60 }">${ time }&#39;</option>
					</c:forEach>
				</select>
			</td>
			<td><input class="sub-off-player form-control sub-off-player-dv" type="text" style="width: 100%;"></td>
			<td><input class="sub-on-player form-control sub-on-player-dv" type="text" style="width: 100%;"></td>
		</tr>
	</table>
</div>

<script type="text/javascript">
	$(function() {
		setTimeout(function() {
			initData();
			initEvent();
		}, 50);
	});
	
	function initData() {
		$('#match_record_panel').find('.match-select').select2({ minimumResultsForSearch : Infinity });
		
		$('.added-match-photo').each(function(index, dom) {
			var $dom = $(dom), data = {}, $hiddenField = $dom.find('.photo-hidden-field');
			
			data['encryptedFileName'] = $hiddenField.attr('fPath');
			data['originalFileName'] = $hiddenField.attr('ofName');
			data['extName'] = $hiddenField.attr('extName');
			data['size'] = $hiddenField.attr('fSize');
			
			$dom.find('img').data('photo_info', data);
			$hiddenField.remove();
		});
		
		$('.added-match-attach').each(function(index, dom) {
			var $dom = $(dom), data = {}, $hiddenField = $dom.find('.attach-hidden-field');
			
			data['encryptedFileName'] = $hiddenField.attr('fPath');
			data['originalFileName'] = $hiddenField.attr('ofName');
			data['extName'] = $hiddenField.attr('extName');
			data['size'] = $hiddenField.attr('fSize');
			
			$dom.find('img').data('attach_info', data);
			$hiddenField.remove();
		});
	}
	
	function initEvent() {
		buildBreadcumb("比赛结果录入");
		
		$('.matchResultEditContainer').on('click', 'div.home-goal-player-col', function(evt) {
			var $target = $(evt.target);
			var isAdd = $target.is('.goal-player-plus');
			
			var $time = $('#home_goal_record_table').find('.goal-time');
			var $player = $('#home_goal_record_table').find('.goal-player');
			var $assist_player = $('#home_goal_record_table').find('.goal-assist-player');
			
			if(isAdd) {
				$time.val($time.find('option:eq(0)').val());
				$player.val($player.find('option:eq(0)').val());
				$assist_player.val($assist_player.find('option:eq(0)').val());
			} else {
				$time.val($target.parent().find('.goal-time').val());
				$player.val($target.parent().find('.goal-player').val());
				$assist_player.val($target.parent().find('.goal-assist-player').val());
			}
			
			$time.select2({ minimumResultsForSearch : Infinity });
			$player.select2({ minimumResultsForSearch : Infinity });
			$assist_player.select2({ minimumResultsForSearch : Infinity });
			
			$('#home_goal_record_table').data('target', $target);
			$('#home_goal_record_modal').modal('show');
		});
		
		$('#home_goal_record_save_btn').click(function() {
			var $target = $('#home_goal_record_table').data('target');
			
			if($target.is('.goal-player-plus')) {
				$target.removeClass('goal-player-plus');
			}
			
			var goalPlayerName = $('#home_goal_record_table').find('.goal-player option:selected').text();
			$target.text(goalPlayerName);
			$target.attr('title', goalPlayerName);
			$target.parent().find('.goal-time').val($('#home_goal_record_table').find('.goal-time').val());
			$target.parent().find('.goal-player').val($('#home_goal_record_table').find('.goal-player').val());
			$target.parent().find('.goal-assist-player').val($('#home_goal_record_table').find('.goal-assist-player').val());
			
			$('#home_goal_record_modal').modal('hide');
		});
		
		$('#home_goal_record_cancel_btn').click(function() {
			$('#home_goal_record_modal').modal('hide');
		});
		
		$('.matchResultEditContainer').on('click', 'div.guest-goal-player-col', function(evt) {
			var $target = $(evt.target);
			var isAdd = $target.is('.goal-player-plus');
			
			var $time = $('#guest_goal_record_table').find('.goal-time');
			var $player = $('#guest_goal_record_table').find('.goal-player');
			var $assist_player = $('#guest_goal_record_table').find('.goal-assist-player');
			
			if(isAdd) {
				$time.val($time.find('option:eq(0)').val());
				$player.val('');
				$assist_player.val('');
			} else {
				$time.val($target.parent().find('.goal-time').val());
				$player.val($target.parent().find('.goal-player').val());
				$assist_player.val($target.parent().find('.goal-assist-player').val());
			}
			
			$time.select2({ minimumResultsForSearch : Infinity });
			
			$('#guest_goal_record_table').data('target', $target);
			$('#guest_goal_record_modal').modal('show');
		});
		
		$('#guest_goal_record_save_btn').click(function() {
			var $target = $('#guest_goal_record_table').data('target');
			
			if($target.is('.goal-player-plus')) {
				$target.removeClass('goal-player-plus');
			}
			
			var goalPlayerName = $.trim($('#guest_goal_record_table').find('.goal-player').val());
			$target.text(goalPlayerName);
			$target.attr('title', goalPlayerName);
			$target.parent().find('.goal-time').val($('#guest_goal_record_table').find('.goal-time').val());
			$target.parent().find('.goal-player').val(goalPlayerName);
			$target.parent().find('.goal-assist-player').val($.trim($('#guest_goal_record_table').find('.goal-assist-player').val()));
			
			$('#guest_goal_record_modal').modal('hide');
		});
		
		$('#guest_goal_record_cancel_btn').click(function() {
			$('#guest_goal_record_modal').modal('hide');
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
			if(!formValidation()) {
				return;
			}
			
			var result = {'matchId': '${ match.id }'};
			
			// basic score
			result['homeScore'] = parseInt($('#home_score_select').val());
			result['guestScore'] = parseInt($('#guest_score_select').val());
			
			// home goal data
			var homeScoreData = [];
			$('#home_score_table').find('.table-item').each(function(index, dom) {
				var data = {}, $dom = $(dom);
				
				data['time'] = parseInt($.trim($dom.find('.goal-time').val()));
				
				var type = $dom.find('.goal-type').val();
				if(type) {
					data['type'] = type;
				}
				
				var playerId = $dom.find('.goal-player').val();
				if(playerId !== '0') {
					data['playerId'] = playerId;
				}
				
				var assistPlayerId = $dom.find('.goal-assist-player').val();
				if(assistPlayerId !== '0') {
					data['assistPlayerId'] = assistPlayerId;
				}
				
				var mode = $dom.find('.goal-mode').val();
				if(mode) {
					data['mode'] = mode;
				}
				
				homeScoreData.push(data);
			});
			result['homeScoreData'] = homeScoreData;
			
			// guest goal data
			var guestScoreData = [];
			$('#guest_score_table').find('.table-item').each(function(index, dom) {
				var data = {}, $dom = $(dom);
				
				data['time'] = parseInt($.trim($dom.find('.goal-time').val()));
				
				var type = $dom.find('.goal-type').val();
				if(type) {
					data['type'] = type;
				}

				data['playerLabel'] = $.trim($dom.find('.goal-player').val());
				data['assistPlayerLabel'] = $.trim($dom.find('.goal-assist-player').val());
				
				var mode = $dom.find('.goal-mode').val();
				if(mode) {
					data['mode'] = mode;
				}
				
				guestScoreData.push(data);
			});
			result['guestScoreData'] = guestScoreData;
			
			// home foul data
			var homeFoulData = [];
			$('#home_foul_table').find('.table-item').each(function(index, dom) {
				var data = {}, $dom = $(dom);
				
				data['time'] = parseInt($.trim($dom.find('.foul-time').val()));
				data['type'] = $dom.find('.foul-type').val();
				data['playerId'] = $dom.find('.foul-player').val();
				
				homeFoulData.push(data);
			});
			result['homeFoulData'] = homeFoulData;
			
			// guest foul data
			var guestFoulData = [];
			$('#guest_foul_table').find('.table-item').each(function(index, dom) {
				var data = {}, $dom = $(dom);
				
				data['time'] = parseInt($.trim($dom.find('.foul-time').val()));
				data['type'] = $dom.find('.foul-type').val();
				data['playerLabel'] = $.trim($dom.find('.foul-player').val());
				
				guestFoulData.push(data);
			});
			result['guestFoulData'] = guestFoulData;
			
			// home sub data
			var homeSubData = [];
			$('#home_sub_table').find('.table-item').each(function(index, dom) {
				var data = {}, $dom = $(dom);
				
				data['time'] = parseInt($.trim($dom.find('.sub-time').val()));
				data['offPlayerId'] = $dom.find('.sub-off-player').val();
				data['onPlayerId'] = $dom.find('.sub-on-player').val();
				
				homeSubData.push(data);
			});
			result['homeSubData'] = homeSubData;
			
			// guest sub data
			var guestSubData = [];
			$('#guest_sub_table').find('.table-item').each(function(index, dom) {
				var data = {}, $dom = $(dom);
				
				data['time'] = parseInt($.trim($dom.find('.sub-time').val()));
				data['offPlayerLabel'] = $.trim($dom.find('.sub-off-player').val());
				data['onPlayerLabel'] = $.trim($dom.find('.sub-on-player').val());
				
				guestSubData.push(data);
			});
			result['guestSubData'] = guestSubData;
			
			// match data
			var matchData = [];
			$('#match_data_table').find('.table-item').each(function(index, dom) {
				var data = {}, $dom = $(dom);
				
				var playerId = $dom.attr('pid');
				if(playerId !== '0') { // exclude statistic row
					var items = [];
					$dom.find('td').each(function(index1, dom1) {
						var item = {}, $dom1 = $(dom1);
						item['itemName'] = $dom1.attr('dname');
						item['itemValue'] = $dom1.text();
						
						items.push(item);
					});
					var item = {};
					item['itemName'] = "hometime";
					item['itemValue'] = $('[dname="hometime"]').text();
					items.push(item);
					item = {};
					item['itemName'] = "awaytime";
					item['itemValue'] = $('[dname="awaytime"]').text();
					items.push(item);
					item = {};
					item['itemName'] = "totaltime";
					item['itemValue'] = $('[dname="totaltime"]').text();
					items.push(item);
					
					data['playerId'] = playerId;
					data['itemList'] = items;
					matchData.push(data);
				}
			});
			result['matchData'] = matchData;
			
			// match photo
			var matchPhotoAssets = [];
			$('#match_photo_container').find('.added-match-photo').each(function(index, dom) {
				var data = {}, domData = $(dom).find('img').data('photo_info');
				
				data['path'] = domData['encryptedFileName'];
				data['originalFileName'] = domData['originalFileName'];
				data['fileExt'] = domData['extName'];
				data['fileSize'] = domData['size'];
				
				matchPhotoAssets.push(data);
			});
			result['matchPhotoAssets'] = matchPhotoAssets;
			
			// match attach
			var matchAttachAssets = [];
			$('#match_attach_container').find('.added-match-attach').each(function(index, dom) {
				var data = {}, domData = $(dom).find('img').data('attach_info');
				
				data['path'] = domData['encryptedFileName'];
				data['originalFileName'] = domData['originalFileName'];
				data['fileExt'] = domData['extName'];
				data['fileSize'] = domData['size'];
				
				matchAttachAssets.push(data);
			});
			result['matchAttachAssets'] = matchAttachAssets;
			
			sa.ajax({
				type : "post",
				url : "<%=serverUrl%>match/saveMatchResult",
				data: JSON.stringify(result),
				contentType: "application/json",
				success : function(data) {
					if(!data.status) {
						alert("提交比赛结果信息异常");
						return;
					}
					
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
				},
				error: function() {
					alert("提交比赛结果信息失败");
				}
			});
		});
		
		$('input.panel_option').click(function() {
			var $dom = $(this);
			var targetPanelId = $dom.attr('id').replace('option', 'panel');
			$('#' + targetPanelId).toggle();
		});
		
		$('.match-select1').each(function(index, dom) {
			$(dom).select2({ minimumResultsForSearch : Infinity });
		});
		
		$.each(['home', 'guest'], function(index, item) {
	        $('#' + item + '_score_select').change(function() {
				var count = parseInt($(this).val());
				var $tr_template = $('#' + item + '_goal_row_template').find('tr');
				
				var $table = $('#' + item + '_score_table');
				//$table.find('.table-item').remove();
				var existedItemCount = $table.find('.table-item').length;
				
				if(count > existedItemCount) {
					for(var i = 0; i < (count - existedItemCount); i++) {
						$table.append($tr_template.clone(false));
					}
				} else {
					if(count === 0) {
						$table.find('.table-item').remove();
					} else {
						$table.find('.table-item:gt(' + (count - 1) + ')').remove();
					}
				}
				
				$table.find('.match-select').each(function(index, dom) {
	    			$(dom).select2({ minimumResultsForSearch : Infinity });
	    		});
			});
	        
	        $('#' + item + '_foul_plus').click(function() {
	        	var $table = $('#' + item + '_foul_table');
	        	$table.append($('#' + item + '_foul_row_template').find('tr').clone(false));
	        	
	        	$table.find('.match-select').each(function(index, dom) {
	    			$(dom).select2({ minimumResultsForSearch : Infinity });
	    		});
	        });
	        
	        $('#' + item + '_foul_minus').click(function() {
	        	var $tr = $('#' + item + '_foul_table').find('tr:last');
	        	if(!$tr.is('.tableHeader')) {
	        		$tr.remove();
	        	}
	        });
	        
	        $('#' + item + '_sub_plus').click(function() {
	        	var $table = $('#' + item + '_sub_table');
	        	$table.append($('#' + item + '_sub_row_template').find('tr').clone(false));

	        	$table.find('.match-select').each(function(index, dom) {
	    			$(dom).select2({ minimumResultsForSearch : Infinity });
	    		});
	        });
	        
	        $('#' + item + '_sub_minus').click(function() {
	        	var $tr = $('#' + item + '_sub_table').find('tr:last');
	        	if(!$tr.is('.tableHeader')) {
	        		$tr.remove();
	        	}
	        });
		});
		
		$('#match_photo_delete').click(function() {
			$('input[name="deleted_match_photo"]:checked').each(function(index, dom) {
				$(dom).parent().remove();
			});
		});
		
		$('#match_attach_delete').click(function() {
			$('input[name="deleted_match_attach"]:checked').each(function(index, dom) {
				$(dom).parents('div.added-match-attach').remove();
			});
		});
		
		var match_photo_template = '<div class="added-match-photo" style="float:left; width:202px; height: 152px; margin: 0 15px 10px 15px; position: relative; border: 1px solid #cecece;">' + 
										'<img class="uploaded-match-photo" src="<%=serverUrl%>hagkFile/download?fileName={{ encryptedFileName }}" width="200" height="150">' +
										'<input type="checkbox" name="deleted_match_photo" style="position: absolute; left: -5px; top: -5px; margin: 0;">' +
									'</div>';
		var match_data_row_template = '<tr class="table-item table-data" pid="{{ player_id }}">' +
											'<td dname="player_label">{{ player_label }}</td><td dname="run_distance">{{ run_distance }}</td><td dname="target_shoot">{{ target_shoot }}</td><td dname="aside_shoot">{{ aside_shoot }}</td>' +
											'<td dname="break_through">{{ break_through }}</td><td dname="off_side">{{ off_side }}</td><td dname="short_pass">{{ short_pass }}</td><td dname="long_pass">{{ long_pass }}</td><td dname="forward_pass">{{ forward_pass }}</td>' +
											'<td dname="cross_pass">{{ cross_pass }}</td><td dname="fail_pass">{{ fail_pass }}</td><td dname="clearance_kick">{{ clearance_kick }}</td><td dname="foul">{{ foul }}</td><td dname="success_steal">{{ success_steal }}</td>' +
											'<td dname="fail_steal">{{ fail_steal }}</td><td dname="intercept">{{ intercept }}</td><td dname="save">{{ save }}</td>' +
									'</tr>';
		var match_attach_template = '<div class="added-match-attach" style="float:left; height: 165px;">' + 
										'<div style="width:107px; height: 137px; margin: 0 10px 10px 10px; position: relative; border: 1px solid #cecece; text-align: center;">' +
											'<img class="uploaded-match-attach" src="<%=serverUrl%>resources/images/{{ fileExt }}.png" width="105" height="135">' +
											'<div style="margin-top: 5px; font-size: 12px; color: #333333; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;">' +
												'<span>{{ originalFileName }}</span>' +
											'</div>' +
											'<input type="checkbox" name="deleted_match_attach" style="position: absolute; left: -5px; top: -5px; margin: 0;">' +
										'</div>' +
									'</div>';
									
		_.templateSettings = {
			interpolate: /\{\{(.+?)\}\}/g
		};
		
		var matchPhotoTemplate = _.template(match_photo_template);
		var matchDataRowTemplate = _.template(match_data_row_template);
		var matchAttachTemplate = _.template(match_attach_template);
		
		// match data upload
		var matchDataUpload = new plupload.Uploader({
			browse_button : 'match_data_upload',
			url: '<%= serverUrl %>match/uploadMatchData',
			file_data_name: 'uploadedFile',
			multi_selection: false,
			flash_swf_url: '<%= serverUrl %>resources/js/common/Moxie.swf',
			silverlight_xap_url: '<%= serverUrl %>resources/js/common/Moxie.xap',
			multipart_params: {},
			filters: {
				max_file_size: '20mb',
				mime_types: [
					{title: 'Document Files', extensions: 'xls,xlsx'}
				]
			},
			init: {
				FilesAdded: function(uploader, files) {
					//$('#declare_attachment_progress').width('0%');
					
					matchDataUpload.disableBrowse(true);
					matchDataUpload.start();
				},
				FileUploaded: function(uploader, file, responseObject) {
					matchDataUpload.disableBrowse(false);
					var match_data = responseObject.response;
					
					if(!match_data) {
						alert('比赛数据上传异常，请检查Excel文件格式是否正确');
						return;
					}			
					
					var match_json = $.parseJSON(match_data);
					var statistic_data = match_json.pop();
					statistic_data['player_label'] = '全队';
					statistic_data['player_id'] = 0;
					match_json.unshift(statistic_data);
					
					$('#match_data_table').find('.table-item').remove();
					$.each(match_json, function(index, item) {
						$('#match_data_table').append(matchDataRowTemplate(item));
					});
					
					//$('#declare_attachment_progress').hide();
				},
				UploadProgress: function(uploader, file) {
					//$('#declare_attachment_progress').width(file.percent + "%");
				},
				Error: function(uploader, errObject) {
					if(errObject.code === plupload.FILE_SIZE_ERROR) {
						alert('文件大小超过最大上传文件大小');
					} else if(errObject.code === plupload.FILE_SIZE_ERROR) {
						alert('文件格式不合法');
					}
				}
			}
		});
		
		matchDataUpload.init();
		
        var matchPhotoUpload = new plupload.Uploader({
			browse_button : 'match_photo_upload',
			url: '<%= serverUrl %>hagkFile/fileUpload',
			file_data_name: 'uploadedFile',
			multi_selection: false,
			flash_swf_url: '<%= serverUrl %>resources/js/common/Moxie.swf',
			silverlight_xap_url: '<%= serverUrl %>resources/js/common/Moxie.xap',
			multipart_params: {},
			filters: {
				max_file_size: '5mb',
				mime_types: [
					{title: 'Document Files', extensions: 'jpg,jpeg,png,gif'}
				]
			},
			init: {
				FilesAdded: function(uploader, files) {
					//$('#declare_attachment_progress').width('0%');
					
					matchPhotoUpload.disableBrowse(true);
					matchPhotoUpload.start();
				},
				FileUploaded: function(uploader, file, responseObject) {
					matchPhotoUpload.disableBrowse(false);
					var json = responseObject.response;
					
					if(!json) {
						alert('比赛照片上传异常');
						return;
					}
					
					//$('#declare_attachment_progress').hide();
					
					var ret_data = $.parseJSON(json);
					$('#match_photo_container').prepend(matchPhotoTemplate({'encryptedFileName': ret_data['encryptedFileName']}));
					$('div.added-match-photo:first > img').data('photo_info', ret_data);
				},
				UploadProgress: function(uploader, file) {
					//$('#declare_attachment_progress').width(file.percent + "%");
				},
				Error: function(uploader, errObject) {
					if(errObject.code === plupload.FILE_SIZE_ERROR) {
						alert('文件大小超过最大上传文件大小');
					} else if(errObject.code === plupload.FILE_SIZE_ERROR) {
						alert('文件格式不合法');
					}
				}
			}
		});
		
        matchPhotoUpload.init();
        
        var matchAttachUpload = new plupload.Uploader({
			browse_button : 'match_attach_upload',
			url: '<%= serverUrl %>hagkFile/fileUpload',
			file_data_name: 'uploadedFile',
			multi_selection: false,
			flash_swf_url: '<%= serverUrl %>resources/js/common/Moxie.swf',
			silverlight_xap_url: '<%= serverUrl %>resources/js/common/Moxie.xap',
			multipart_params: {},
			filters: {
				max_file_size: '20mb'
			},
			init: {
				FilesAdded: function(uploader, files) {
					//$('#declare_attachment_progress').width('0%');
					
					matchAttachUpload.disableBrowse(true);
					matchAttachUpload.start();
				},
				FileUploaded: function(uploader, file, responseObject) {
					matchAttachUpload.disableBrowse(false);
					var json = responseObject.response;
					
					if(!json) {
						alert('比赛附件上传异常');
						return;
					}
					
					//$('#declare_attachment_progress').hide();
					
					var ret_data = $.parseJSON(json);
					var existed_ext_logos = ['txt', 'doc', 'docx', 'ppt', 'pptx', 'xls', 'xlsx', 'zip', 'rar'];
					var fileExtLogo = ($.inArray(ret_data['extName'], existed_ext_logos) >= 0 ? ('attach_' + ret_data['extName']) : 'general_attach');
					
					$('#match_attach_container').prepend(matchAttachTemplate({'encryptedFileName': ret_data['encryptedFileName'], 'fileExt': fileExtLogo, 'originalFileName': ret_data['originalFileName']}));
					$('div.added-match-attach:first img').data('attach_info', ret_data);
				},
				UploadProgress: function(uploader, file) {
					//$('#declare_attachment_progress').width(file.percent + "%");
				},
				Error: function(uploader, errObject) {
					if(errObject.code === plupload.FILE_SIZE_ERROR) {
						alert('文件大小超过最大上传文件大小');
					} else if(errObject.code === plupload.FILE_SIZE_ERROR) {
						alert('文件格式不合法');
					}
				}
			}
		});
		
        matchAttachUpload.init();
	}
	
	function formValidation() {
		var result = true;
		
		var $homeGoalPlayer = $('#home_score_table').find('.goal-player-dv');
		
		$homeGoalPlayer.css({'border': 'none'});
		$homeGoalPlayer.each(function(index, dom) {
			var $dom = $(dom);
			
			if($dom.is('.goal-player-plus')) {
				$dom.css({'border': '1px solid red'});
				result = false;
			}
		});
		
		if(!result)  {
			alert('主队进球队员名字不能为空');
			return false;
		}
		
		var $goalPlayer = $('#guest_score_table').find('.goal-player-dv');
		
		$goalPlayer.css({'border': 'none'});
		$goalPlayer.each(function(index, dom) {
			var $dom = $(dom);
			
			if($dom.is('.goal-player-plus') || !$dom.text()) {
				$dom.css({'border': '1px solid red'});
				result = false;
			}
		});
		
		if(!result)  {
			alert('客队进球队员名字不能为空');
			return false;
		}
		
		var $foulPlayer = $('#guest_foul_table').find('input.foul-player-dv');
		$foulPlayer.css({'border-color': '#d2d6de'});
		$foulPlayer.each(function(index, dom) {
			var $dom = $(dom);
			
			if(!$.trim($dom.val())) {
				$dom.css({'border-color': 'red'});
				result = false;
			}
		});
		
		if(!result)  {
			alert('客队犯规队员名字不能为空');
			return false;
		}
		
		var $subOffPlayer = $('#guest_sub_table').find('input.sub-off-player-dv');
		$subOffPlayer.css({'border-color': '#d2d6de'});
		$subOffPlayer.each(function(index, dom) {
			var $dom = $(dom);
			
			if(!$.trim($dom.val())) {
				$dom.css({'border-color': 'red'});
				result = false;
			}
		});
		
		if(!result)  {
			alert('客队下场队员名字不能为空');
			return false;
		}
		
		var $subOnPlayer = $('#guest_sub_table').find('input.sub-on-player-dv');
		$subOnPlayer.css({'border-color': '#d2d6de'});
		$subOnPlayer.each(function(index, dom) {
			var $dom = $(dom);
			
			if(!$.trim($dom.val())) {
				$dom.css({'border-color': 'red'});
				result = false;
			}
		});
		
		if(!result)  {
			alert('客队上场队员名字不能为空');
			return false;
		}

		return true;
	}
</script>