<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page import="cn.sportsdata.webapp.youth.common.utils.CommonUtils" %>
<%@ page import="cn.sportsdata.webapp.youth.common.vo.match.MatchVO" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>

<%
	String serverUrl = CommonUtils.getServerUrl(request);

	boolean isCreate = (Boolean) request.getAttribute("isCreate");
	MatchVO matchVO = (MatchVO) request.getAttribute("match");
	String matchId = isCreate ? "" : matchVO.getId();
	
	Date now = new Date();
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	String dateString = dateFormat.format(now);
	
	SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
	String timeString = timeFormat.format(now);
%>

<style>
	.start-statistic-label {
		color: #999;
		font-size: 12px;
	}
</style>

<div class="matchEditContainer" ng-controller='MatchController'>
	<div class="modal fade" id="starter_modal" tabindex="-1" role="dialog" aria-labelledby="starter_modal_label">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
        			<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        			<h4 class="modal-title" id="starter_modal_label">添加战术</h4>
      			</div>
      			<div class="modal-body" style="background-color:#ecf0f5;">
      				<div>
						<button id="starter_save_btn" class="btn btn-primary" style="float: right; margin-left: 10px;">保存</button>
						<button id="starter_cancel_btn" class="btn btn-default" style="float: right; margin-left: 10px;">取消</button>
						<div style="clear:both;"></div>
					</div>
      				<div class="panel panel-default" style="margin-top:10px;">
      					<div class="panel-heading panel-header">
      						<div style="float: left;">首发设定</div>
      						<div style="float: right;">
      							<div class="btn-group">
  									<button type="button" class="dropdown-btn dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
    									<span id="starter-menu-title">全部阵型</span>&nbsp;<span class="caret"></span>
  									</button>
  									<ul class="starter-menu dropdown-menu">
  										<li><a href="javascript:void(0)" fid="0">全部阵型</a></li>
  										<c:forEach items="${formationTypeList}" var="formationType" varStatus="status">
  											<li><a href="javascript:void(0)" fid="${ formationType.id }">${ formationType.name }</a></li>
  										</c:forEach>
  									</ul>
								</div>
      						</div>
      						<div style="clear:both;"></div>
      					</div>
      					<div id="starter-panel-body" class="panel-body table-responsive">
      						<c:forEach items="${startersList}" var="starter_item" varStatus="status">
	      						<div fid="${ starter_item.formation_id }" class="starter-item" style="float:left; width: 250px; height: 250px;margin:10px 10px 10px 10px;">
	      							<img class="starter-image" src="${ starter_item.imgUrl }" width="250">
	      							<div style="margin: 15px 0 0 0; font-size: 18px;" class="starter-name-area">
	      								<input type="radio" name="starter_list" value="${ starter_item.tacticsId }" <c:if test="${ !empty starter and starter.tacticsId == starter_item.tacticsId }">checked="checked"</c:if> />
	      								<span class="starter_blank" style="float:left;">&nbsp;</span>
	      								<div tid="${ starter_item.tacticsId }" class="starter-name" style="display: inline-block;overflow: hidden;text-overflow: ellipsis;white-space: nowrap; width: 90%; float:right;">${ starter_item.name }</div>
	      							</div>
	      							<div>
	      								<span class="start-statistic-label">使用次数</span>&nbsp;&nbsp;
	      								<span style="color: #fc6b8a; font-size: 16px;">${ starter_item.usedCount }次</span>&nbsp;&nbsp;
	      								<div style="font-size: 10px;display: inline-block; background-color:#dddddd; border-radius: 8px; padding: 2px 5px;">${ starter_item.winCount }胜&nbsp;${ starter_item.drawCount }平&nbsp;${ starter_item.loseCount }负</div>&nbsp;&nbsp;
	      							</div>
	      							<div>
	      								<span class="start-statistic-label">最近战绩</span>&nbsp;&nbsp;
	      								<c:forEach items="${starter_item.recentRecordList}" var="record" varStatus="status">
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
      						</c:forEach>
      					</div>
      				</div>
      			</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="placekick_modal" tabindex="-1" role="dialog" aria-labelledby="placekick_modal_label">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
        			<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        			<h4 class="modal-title" id="placekick_modal_label">添加战术</h4>
      			</div>
      			<div class="modal-body" style="background-color:#ecf0f5;">
      				<div>
						<button id="placekick_save_btn" class="btn btn-primary" style="float: right; margin-left: 10px;">保存</button>
						<button id="placekick_cancel_btn" class="btn btn-default" style="float: right; margin-left: 10px;">取消</button>
						<div style="clear:both;"></div>
					</div>
      				<div class="panel panel-default" style="margin-top:10px;">
      					<div class="panel-heading panel-header">
      						<div style="float: left;">定位球设定</div>
      						<div style="float: right;">
      							<div class="btn-group">
  									<button type="button" class="dropdown-btn dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
    									<span id="placekick-menu-title" pid="0">全部类型</span>&nbsp;<span class="caret"></span>
  									</button>
  									<ul class="placekick-menu dropdown-menu">
  										<li><a href="javascript:void(0)" pid="0">全部类型</a></li>
  										<c:forEach items="${placeKickTypeList}" var="placekickType" varStatus="status">
  											<li><a href="javascript:void(0)" pid="${ placekickType.id }">${ placekickType.name }</a></li>
  										</c:forEach>
  									</ul>
								</div>
      						</div>
      						<div style="clear:both;"></div>
      					</div>
      					<div id="placekick-panel-body" class="panel-body table-responsive">
      						<c:forEach items="${placeKickList}" var="placeKick" varStatus="status">
	      						<div pid="${ placeKick.placeKickTypeId }" class="placekick-item" style="float:left; width: 250px; height: 220px;margin:10px 10px 10px 10px;">
	      							<img class="placekick-image" src="${ placeKick.imgUrl }" width="250">
	      							<div style="margin: 15px 0 0 0;">
	      								<div style="font-size: 18px;">
	      									<c:set var="flag" value="0" />
	      									<c:forEach items="${placekicks}" var="selected_placekick" varStatus="status1">
	      										<c:if test="${ placeKick.id == selected_placekick.id }">
	      											<c:set var="flag" value="1" />
	      										</c:if>
	      									</c:forEach>
	      									<input type="checkbox" name="placekick_list" value="${ placeKick.id }" <c:if test="${ flag == '1' }">checked="checked"</c:if> />&nbsp;
	      									<div class="placekick-name" style="display: inline-block;overflow: hidden;text-overflow: ellipsis;white-space: nowrap; width: 90%; float:right;">${ placeKick.name }</div>
	      								</div>
	      							</div>
	      						</div>
      						</c:forEach>
      					</div>
      				</div>
      			</div>
			</div>
		</div>
	</div>
	<form id="match_form">
		<div class="button_area">
			<button id="edit_btn" class="btn btn-primary" style="float: right; margin-left: 10px;">保存</button>
			<button id="back_btn" class="btn btn-default" style="float: right; margin-left: 10px;">取消</button>
		</div>
		<div class="clearfix" style="height: 15px;"></div>
		<div class="matchEditArea">
			<sa-panel title="比赛信息">
				<div class="row">
					<div class="col-md-1 profileEditTitleEx">对手</div>
					<div class="col-md-5">
						<div class="form-group">
							<c:choose>
								<c:when test="${isCreate}">
									<input type="text" data-bv-field="opponent" class="profileEditInput form-control" id="opponent" name="opponent" />
								</c:when>
								<c:otherwise>
									<input type="text" data-bv-field="opponent" class="profileEditInput form-control" id="opponent" name="opponent" value="${ match.opponent }" />
								</c:otherwise>
							</c:choose>
						</div>
					</div>
					<div class="col-md-1 profileEditTitleEx">着装</div>
					<div class="col-md-5">
						<div class="form-group">
							<c:choose>
								<c:when test="${isCreate}">
									<input type="text" data-bv-field="dress" class="profileEditInput form-control" id="dress" name="dress" />
								</c:when>
								<c:otherwise>
									<input type="text" data-bv-field="dress" class="profileEditInput form-control" id="dress" name="dress" value="${ match.dress }" />
								</c:otherwise>
							</c:choose>
						</div>
					</div>
				</div>
				<div class="row profileEditItemLine">
					<div class="col-md-6">
						<div class="row">
							<div class="col-md-2 profileEditTitleEx">日期</div>
							<div class="col-md-4">
								<div class="input-group date">
									<c:choose>
										<c:when test="${isCreate}">
											<input type="text" data-bv-field="date" class="form-control profileEditInput calendar-input dateField" id="date" name="date" readonly />
											<span id="matchDateIcon" class="input-group-addon calendar-icon"><i class="glyphicon glyphicon-calendar major_color"></i></span>
										</c:when>
										<c:otherwise>
											<input type="text" data-bv-field="date" class="form-control profileEditInput calendar-input dateField" id="date" name="date" value="<fmt:formatDate value="${ match.date }" pattern="yyyy-MM-dd" />" readonly />
											<span id="matchDateIcon" class="input-group-addon calendar-icon"><i class="glyphicon glyphicon-calendar major_color"></i></span>
										</c:otherwise>
									</c:choose>
								</div>
							</div>
							<div class="col-md-2 profileEditTitleEx">时间</div>
							<div class="col-md-4">
								<div class="input-group date">
									<c:choose>
										<c:when test="${isCreate}">
											<input type="text" data-bv-field="time" class="form-control profileEditInput calendar-input" id="time" name="time" readonly />
											<span id="matchTimeIcon" class="input-group-addon calendar-icon"><i class="glyphicon glyphicon-calendar major_color"></i></span>
										</c:when>
										<c:otherwise>
											<input type="text" data-bv-field="time" class="form-control profileEditInput calendar-input" id="time" name="time" value="${ match.time }" readonly />
											<span id="matchTimeIcon" class="input-group-addon calendar-icon"><i class="glyphicon glyphicon-calendar major_color"></i></span>
										</c:otherwise>
									</c:choose>
								</div>
							</div>
						</div>
					</div>
					<div class="col-md-1 profileEditTitleEx">比赛类型</div>
					<div class="col-md-5">
						<div class="form-group">
							<select class="profileEditInput" id="type" name="type">
								<option value="formal">正式比赛</option>
								<option value="friendly">友谊赛</option>
							</select>
						</div>
					</div>
				</div>
				<div class="row profileEditItemLine">
					<div class="col-md-1 profileEditTitleEx">地点</div>
					<div class="col-md-3">
						<div class="form-group">
							<c:choose>
								<c:when test="${isCreate}">
									<input type="text" data-bv-field="location" class="profileEditInput form-control" id="location" name="location" />
								</c:when>
								<c:otherwise>
									<input type="text" data-bv-field="location" class="profileEditInput form-control" id="location" name="location" value="${ match.location }" />
								</c:otherwise>
							</c:choose>
						</div>
					</div>
					<div class="col-md-2">
						<div class="form-group">
							<select class="profileEditInput" id="fieldType" name="fieldType">
								<option value="home">主场</option>
								<option value="guest">客场</option>
								<option value="neutrality">中立</option>
							</select>
						</div>
					</div>
					<div class="col-md-1 profileEditTitleEx">比分</div>
					<div class="col-md-5">
						<div class="row">
							<div class="col-md-5">
								<div class="form-group">
									<c:choose>
										<c:when test="${ isCreate or match.isEnd == 0 }">
											<input type="text" data-bv-field="goalFor" class="form-control profileEditInput numberField" id="goalFor" name="goalFor" />
										</c:when>
										<c:otherwise>
											<input type="text" data-bv-field="goalFor" class="form-control profileEditInput numberField" id="goalFor" name="goalFor" value="${ match.homeScore }" />
										</c:otherwise>
									</c:choose>
								</div>
							</div>
							<div class="col-md-2 text-center">:</div>
							<div class="col-md-5">
								<div class="form-group">
									<c:choose>
										<c:when test="${ isCreate or match.isEnd == 0 }">
											<input type="text" data-bv-field="goalAgainst" class="form-control profileEditInput numberField" id="goalAgainst" name="goalAgainst" />
										</c:when>
										<c:otherwise>
											<input type="text" data-bv-field="goalAgainst" class="form-control profileEditInput numberField" id="goalAgainst" name="goalAgainst" value="${ match.guestScore }" />
										</c:otherwise>
									</c:choose>
								</div>
							</div>
						</div>
					</div>
				</div>
			</sa-panel>
		</div>
		<div class="matchExtArea">
			<div style="float:left;width:35%;">
				<sa-panel title="首发设定" link-text="编辑" link-callback="onStartsSettingsClick()">
					<div id="selectedStarterArea">
						<c:if test="${ !empty starter }">
							<div style="width: 250px; height: 250px;margin: 0 auto 30px auto;" class="starter-item">
								<img class="starter-image" src="${ starter.imgUrl }" width="250">
								<div style="margin: 15px 0 0 0; font-size: 18px;">
									<div tid="${ starter.tacticsId }" class="starter-name" style="display: inline-block;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;width: 80%;">${ starter.name }</div>
									<span class="glyphicon glyphicon-trash content-color trash-icon delete-starter-link" aria-hidden="true"></span>
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
					</div>
				</sa-panel>
			</div>
			<div style="float:right;width:64%;">
				<sa-panel title="定位球设定" link-text="编辑" link-callback="onPlacekickSettingsClick()">
					<div id="selectedPlacekickArea">
						<c:forEach items="${placekicks}" var="placekick" varStatus="status">
							<div style="float: left;width: 250px; height: 220px;margin: 8px 15px" class="added-placekicks">
								<img class="placekick-image" src="${ placekick.imgUrl }" width="250">
								<div style="margin: 15px 0 0 0; font-size: 18px;">
									<div tid="${ placekick.id }" class="placekick-name" style="display: inline-block;overflow: hidden;text-overflow: ellipsis;white-space: nowrap; width: 80%;">${ placekick.name }</div>
									<span class="glyphicon glyphicon-trash content-color trash-icon delete-placekick-link" aria-hidden="true"></span>
								</div> 
							</div>
						</c:forEach>
					</div>
				</sa-panel>
			</div>
			<div style="clear:both;"></div>
		</div>
	</form>
</div>

<script type="text/javascript">
	$(function() {
		setTimeout(function() {
			initData();
			initEvent();
		}, 50);
	});
	
	function initData() {
		var isCreate = ${isCreate};
		
		if(!isCreate) {
			$('#type').val('${ match.type }');
			$('#fieldType').val('${ match.fieldType }');
		}
		
		var $starter = $('#selectedStarterArea').find('.starter-name');
		if($starter.length > 0) {
			$('#match_form').data('starter_tactic_id', $starter.attr('tid'));
		}
		
		var $placekicks = $('#selectedPlacekickArea').find('.placekick-name');
		if($placekicks.length > 0) {
			var pid_list = [];
			
			$placekicks.each(function(index, dom) {
				pid_list.push($(dom).attr('tid'));
			});
			
			$('#match_form').data('placekick_tactic_ids', pid_list);
		}
	}
	
	function MatchController($scope) {
		$scope.onStartsSettingsClick = function() {
			$('#starter_modal').modal('show');
		};
		
		$scope.onPlacekickSettingsClick = function() {
			$('#placekick_modal').modal('show');
		};
	}
	
	function initEvent() {
		buildBreadcumb("新增/修改比赛");
		
		$('#type').select2({ minimumResultsForSearch : Infinity });
		$('#fieldType').select2({ minimumResultsForSearch : Infinity });
		
		var $date = $('#date');
		$date.datepicker({
			format : 'yyyy-mm-dd',
			language : 'zh-CN',
			autoclose : true,
			todayHighlight : true,
			toggleActive : true,
			orientation: 'bottom'
		});
		
		$('#matchDateIcon').click(function() {
			$date.datepicker('show');
		});
		
		var $time = $('#time');
		$time.datetimepicker({
			language: 'zh-CN',
			format : 'hh:ii',
			autoclose: 1,
			pickerPosition: "bottom-left",
			startView:1,
			maxView:1,
			titleDisable:true
		});
		
		$('#matchTimeIcon').click(function() {
			$time.datetimepicker('show');
		});
		
		// button click event
		$('#edit_btn').click(function() {
			$('#match_form').data('bootstrapValidator').validate();
			if(!$('#match_form').data('bootstrapValidator').isValid()){
				return;
			}
			
			if(!otherValidate()) {
				return;
			}
			
			var form_data = $('#match_form').serializeArray();
			var converted_data = {'id': '<%=matchId%>'};
			
			var starter_tactic_id = $('#match_form').data('starter_tactic_id');
			var placekick_tactic_ids = $('#match_form').data('placekick_tactic_ids');
			
			var tactic_ids = placekick_tactic_ids || [];
			if(starter_tactic_id) {
				tactic_ids.push(starter_tactic_id);
			}
			converted_data['tacticIds'] = tactic_ids.join(',');
			
			$.each(form_data, function(index, item) {
				var $item = $('#' + item['name']);
				item['value'] = $.trim(item['value']);
				
				if($item.is('.dateField')) {
					item['value'] += ' 00:00:00';
				} else if($item.is('.numberField')) {
					item['value'] = item['value'] ? parseInt(item['value']) : -1;
				}
				
				converted_data[item['name']] = item['value'];
			});

			sa.ajax({
				type : "post",
				url : "<%=serverUrl%>match/saveMatch",
				data: JSON.stringify(converted_data),
				contentType: "application/json",
				success : function(data) {
					if(!data.status) {
						alert("提交比赛信息异常");
						return;
					}
					
					sa.ajax({
						type : "get",
						url : "<%=serverUrl%>match/showMatchDetail?matchID=" + data.result,
						success : function(data) {
							AngularHelper.Compile($('#content'), data);
						},
						error: function() {
							alert("打开比赛详情页面失败");
						}
					});
				},
				error: function() {
					alert("提交比赛信息失败");
				}
			});
		});
		
		$('#back_btn').click(function() {
			var isCreate = ${isCreate};
			var url = '<%=serverUrl%>match/' + (isCreate ? 'showMatchList' : 'showMatchDetail?matchID=<%=matchId%>');
			
			sa.ajax({
				type : "get",
				url : url,
				success : function(data) {
					AngularHelper.Compile($('#content'), data);
				},
				error: function() {
					alert("页面返回失败");
				}
			});
		});
		
		var placekick_template_str = '<div style="float: left;width: 250px; height: 220px;margin: 8px 15px;" class="added-placekicks">' + 
										'<img class="placekick-image" src="{{ imgSrc }}" width="250">' +
										'<div style="margin: 15px 0 0 0; font-size: 18px;">' +
											'<div tid="{{ tid }}" class="placekick-name" style="display: inline-block;overflow: hidden;text-overflow: ellipsis;white-space: nowrap; width: 80%;">{{ name }}</div>' +
											'<span class="glyphicon glyphicon-trash content-color trash-icon delete-placekick-link" aria-hidden="true"></span>' +
										'</div>' +
									'</div>';
									
		_.templateSettings = {
			interpolate: /\{\{(.+?)\}\}/g
		};
										
		var placekickTemplateCompiled = _.template(placekick_template_str);
		
		$('ul.starter-menu').on('click', 'a', function(evt) {
			var $target = $(evt.target);
			$('#starter-menu-title').text($target.text());
			var currentFid = $target.attr('fid');
			
			$('#starter-panel-body').find('.starter-item').each(function(index, dom) {
				var $dom = $(dom);
				if (currentFid == 0) {
					$dom.show();
				} else {
					if($dom.attr('fid') === currentFid) {
						$dom.show();
					} else {
						$dom.hide();
					}
				}
			});
		});
		
		$('ul.placekick-menu').on('click', 'a', function(evt) {
			var $target = $(evt.target);
			$('#placekick-menu-title').text($target.text());
			var currentPid = $target.attr('pid');
			
			$('#placekick-panel-body').find('.placekick-item').each(function(index, dom) {
				var $dom = $(dom);
				
				if (currentPid == 0) {
					$dom.show();
				} else {
					if($dom.attr('pid') === currentPid) {
						$dom.show();
					} else {
						$dom.hide();
					}
				}
			});
		});
		
		$('#starter_cancel_btn').click(function() {
			$('#starter_modal').modal('hide');
		});
		
		$('#starter_save_btn').click(function() {
			var $target = $('#starter-panel-body').find('input[name="starter_list"]:checked');
			if($target.length === 0) {
				alert('必须选中一个首发设置');
				return;
			}
			
			$('#match_form').data('starter_tactic_id', $target.val());
			
			var $root = $target.parents('div.starter-item');
			var $clone = $root.clone();
			
			// additional operation for dom
			$clone.find('input[name="starter_list"]').remove();
			$clone.find('.starter_blank').remove();
			$clone.css({'margin': '0 auto 20px auto', 'float': 'none'});
			$clone.find('.starter-name').css({'width': '80%', 'float': 'none'});
			$clone.find('.starter-name-area').append('<span class="glyphicon glyphicon-trash content-color trash-icon delete-starter-link" aria-hidden="true"></span>');
			
			$('#selectedStarterArea').empty();
			$('#selectedStarterArea').append($clone);
			
			//var data = {'imgSrc': $root.find('.starter-image').attr('src'), 'name': $root.find('.starter-name').text()};
			//$('#selectedStarterArea').html('').html(starterTemplateCompiled(data));

			$('#starter_modal').modal('hide');
		});
		
		$('#placekick_cancel_btn').click(function() {
			$('#placekick_modal').modal('hide');
		});
		
		$('#placekick_save_btn').click(function() {
			var $targets = $('#placekick-panel-body').find('input[name="placekick_list"]:checked');
			if($targets.length === 0) {
				alert('至少选中一个定位球设置');
				return;
			}
			
			var pid_list = [];
			$('#selectedPlacekickArea').html('');
			$targets.each(function(index, dom) {
				var $dom = $(dom);
				var $root = $dom.parents('div.placekick-item');
				
				pid_list.push($dom.val());
				var data = {'imgSrc': $root.find('.placekick-image').attr('src'), 'name': $root.find('.placekick-name').text(), 'tid': $root.find('input[name="placekick_list"]').val()};
				$('#selectedPlacekickArea').append(placekickTemplateCompiled(data));
			});
			
			$('#match_form').data('placekick_tactic_ids', pid_list);
			
			$('#placekick_modal').modal('hide');
		});
		
		$('div.matchEditContainer').on('click', 'span.delete-starter-link', function(evt) {
			var $target = $(evt.target);
			var $root = $target.parents('div.starter-item');
			
			var tid = $root.find('.starter-name').attr('tid');
			$('#starter-panel-body').find('input[name="starter_list"][value=' + tid + ']').removeAttr('checked');
			$('#selectedStarterArea').empty();
			
			$('#match_form').removeData('starter_tactic_id');
		});
		
		$('div.matchEditContainer').on('click', 'span.delete-placekick-link', function(evt) {
			var $target = $(evt.target);
			var $root = $target.parents('div.added-placekicks');
			
			var tid = $root.find('.placekick-name').attr('tid');
			$('#placekick-panel-body').find('input[name="placekick_list"][value=' + tid + ']').removeAttr('checked');
			$root.remove();
			
			var pid_list = [];
			$('#selectedPlacekickArea').find('.added-placekicks').each(function(index, dom) {
				pid_list.push($(dom).find('.placekick-name').attr('tid'));
			});
			$('#match_form').data('placekick_tactic_ids', pid_list);
		});
		
		formValidation();
	}
	
	function otherValidate() {
		if(!$('#date').val() || !$('#time').val()) {
			alert('日期和时间为必填项，请选择正确的日期和时间');
			return false;
		}
		
		var isMatchEnd = ('${ match.isEnd }' === '1');
		var homeScore = $.trim($('#goalFor').val());
		var guestScore = $.trim($('#goalAgainst').val());
		
		if(isMatchEnd && (!homeScore || !guestScore)) {
			alert('已经结束的比赛比分不能为空');
			return false;
		}
		
		if((homeScore && !guestScore) || (!homeScore && guestScore)) {
			alert('比赛比分不能只录入一个');
			return false;
		}
		
		return true;
	}
	
	function formValidation() {
		$("#match_form").bootstrapValidator({
	        message: '您输入的值不合法',
	        feedbackIcons: {
	            valid: 'glyphicon glyphicon-ok',
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	        	'opponent': {
	        		message: '对手名称不合法',
	        		validators: {
	                    notEmpty: {
	                        message: '对手名称是必填项'
	                    },
	                    stringLength: {
	                         max: 30,
	                         message: '对手名称长度超过限制，请输入30字符以内的名称'
	                    }
	                }
	        	},
	        	'goalFor': {
	        		message: '比分不合法',
	        		validators: {
	        			regexp: {
	                         regexp: /^(0|[1-9])[0-9]*$/,
	                         message: '比分必须为自然数'
	                    }
	                }
	        	},
	        	'goalAgainst': {
	        		message: '比分不合法',
	        		validators: {
	        			regexp: {
	                         regexp: /^(0|[1-9])[0-9]*$/g,
	                         message: '比分必须为自然数'
	                    }
	                }
	        	}
	        }
		});
	}
</script>
