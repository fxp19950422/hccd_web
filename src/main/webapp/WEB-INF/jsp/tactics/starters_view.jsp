<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="xss" uri="http://www.sportsdata.cn/xss"%>

<%@ page import="cn.sportsdata.webapp.youth.common.utils.CommonUtils"%>
<%@ page import="cn.sportsdata.webapp.youth.common.vo.TacticsVO"%>
<%@ page import="cn.sportsdata.webapp.youth.common.vo.utraining.UtrainingTaskEvaluationVO"%>

<%
	String serverUrl = CommonUtils.getServerUrl(request);
%>
<style>
.table-play-list table {
	border-right: 1px solid #cecece;
	border-bottom: 1px solid #cecece
}

.table-play-list table td {
	border-left: 1px solid #cecece;
	border-top: 1px solid #cecece;
	height: 30px;
	text-align: center;
	valign: middle;
	align: middle;
}

.profileDetailAvatar {
    float: left;
	width: 90px;
	height: 90px;
	border: 1px solid #cecece;
	margin: 15px 15px 15px 15px;
	border-radius: 3px;
}

.textDiv {
	overflow:hidden;
	text-overflow: ellipsis;
	white-space: nowrap;
}

.gameDataColumn {
	clear: both; 
	width: 60px; 
	color: #666;
}

.gameDataColumnContent {
	float: left; 
	width: 30px; 
	text-align: center;
	font-size:14px;
}

.separatorLine {
	width:1px; 
	border-left:1px solid;
}

.starterAvator {
    width: 100%;
    height: auto;
    float: left;
    border-radius: 2px;
    border: 1px solid #cecece;
}

.button_area{
position: absolute;
right: 0px;
margin-top: -30px;
margin-right: 15px;
}

</style>

<div class="clearfix"></div>
<input type="hidden" id="tactics_page_changed" value="false"></input>
<input type="hidden" id="tactics_page_changed_choose" value=""></input>
<div class="button_area" id="button_area">
	<c:if test="${isCreator == true}">
		<button id="edit_btn" class="btn btn-primary" style="float: right;margin-left: 10px;">编辑</button>	
	</c:if>
	<button id="copy_btn" class="btn btn-primary" style="float: right; margin-left: 10px;">复制</button>
	<c:if test="${isCreator == true}">
		<button id="delete_btn" class="btn btn-danger" style="float: right; margin-left: 10px;">删除</button>
	</c:if>
	<button id="return_btn" class="btn btn-default" style="float: right; margin-left: 10px;">返回</button>
</div>
<div class="clearfix" style="height: 15px;"></div>
<div>		
	<div style="float: right; width: 336px;">
		<div class="card" style="background-color: white; width: 336px; clear: both; height: 155px;">
			<div class="personalInfo" style="float: left;width: 240px">
				<div class="playerInfo" style="height: 120px;">
					<div class="playerAvatar" style="float: left;">
						<img class="profileDetailAvatar" id="playerAvaterId" src="<%=serverUrl%>resources/images/user_avatar.png"></img>
					</div>
					<div class="playerBaseInfo textDiv" style="float: left; height: 100px;margin-top: 15px;">
						<div class="textDiv" id="playerName" style="clear: both;; 
							width: 120px; font-size:18px; color: #333333;">
							<c:choose>
								<c:when test="${ !empty player.name }">
									<xss:xssFilter text="${ player.name }" filter="html"/>
								</c:when>
								<c:otherwise>
									暂无姓名
								</c:otherwise>
							</c:choose>
						</div>
						<div class="numberAndPostion" style="clear: both; height: 15px;margin-top: 15px;">
							<div style="float: left; width: 60px;">
								<div class="textDiv" id="playerNumber" style="float: left; font-size: 14px; line-height: 15px;color:#333;">--</div>
								<div class="textDiv" style="float: left; font-size: 12px; width: 15px; line-height: 15px; color: #666;">号</div>
							</div>
							<div class="textDiv" id="playerPosition" style="float: left; width: 50px; font-size: 14px; line-height: 15px;">前锋</div>
						</div>
						<div class="heightAndWeight" style="clear: both; margin-top: 10px; height: 15px;margin-top: 15px;">
							<div style="float: left; width: 60px;">
								<div  class="textDiv" id="playerHeight" style="float: left; font-size: 14px; line-height: 15px;color:#333;">--</div>
								<div  class="textDiv" style="float: left; width: 30px; margin-left: 2px; font-size: 12px; line-height: 15px;color: #666;">厘米</div>
							</div>
							<div class="textDiv" style="float: left; width: 60px;">
								<div  class="textDiv" id="playerWeight" style="float: left; font-size: 14px; line-height: 15px;color:#333;">--</div>
								<div  class="textDiv" style="float: left; margin-left: 2px; width: 30px; font-size: 12px; line-height: 15px;color: #666;">公斤</div>
							</div>
						</div>
					</div>
				</div>
				<div class="trainInfo" style="clear: both;width: 225px;margin-left: 15px;height: 20px;line-height: 20px;">
					<div class="trainTitle textDiv" style="float: left; font-size: 14px; 
						width: 90px; text-align: center; color: #333">
						最后一次训练
					</div>
					<div style="float: left; width: 45px;margin-left: 15px;">
						<div class="textDiv" id="trainScore" style="float: left; max-width: 30px;font-size: 16px;color: #fc6b8a">--</div>
						<div class="textDiv" style="float: left; width: 14px; margin-left: 1px; font-size: 12px;color: #666">分</div>
					</div>
					<div class="textDiv" id="trainBehavior" style="float: left; font-size: 14px; 
						width: 75px; color: #057ec3">
						未选择球员
					</div>
				</div>
			</div>
			<div class="separatorLine" style="float: left; height: 125px; margin-top: 15px; color: #cecece;"></div>
			<div class="gameData" style="float: left; width: 60px;margin-top: 15px; margin-left: 15px;">
				<div class="gameDataColumn" >
					<div class="textDiv gameDataColumnContent">出场</div>
					<div class="textDiv gameDataColumnContent" id="starterCount">0</div>
					<div class="clearfix" style="height: 15px;clear: both;"></div>
				</div>
				<div class="gameDataColumn">
					<div class="textDiv gameDataColumnContent" style="color: #666;">替补</div>
					<div class="textDiv gameDataColumnContent" id="substituteCount">0</div>
					<div class="clearfix" style="height: 15px;clear: both;"></div>
				</div>
				<div class="gameDataColumn">
					<div class="textDiv gameDataColumnContent" style="color: #666;">进球</div>
					<div class="textDiv gameDataColumnContent" id="goalCount">0</div>
					<div class="clearfix" style="height: 15px;clear: both;"></div>
				</div>
				<div class="gameDataColumn">
					<div class="textDiv gameDataColumnContent" style="color: #666;">助攻</div>
					<div class="textDiv gameDataColumnContent" id="assistCount">0</div>
					<div class="clearfix" style="height: 15px;clear: both;"></div>
				</div>
			</div>
		</div>
		<div class="clearfix" style="clear: both; width: 336px;height: 15px;"></div>
		<div id="team_member" style="background-color: white; width: 336px; clear: both;">
			<div class="tab-content" style="width: 100%; padding: auto;">
				<div role="tabpanel" class="tab-pane active" id="basic_tab">
					<div class="panel panel-default">
						<div class="row" style="overflow:auto;height:350px;margin-left:0px;margin-right:0px;">
							<div class="col-md-12" style="padding-left: 0px; padding-right: 0px;">
								<div class="row tacticsPlayerItemHeader text-center">
									<div class="col-md-4 tacticsPlayerCell">位置</div>
									<div class="col-md-4 tacticsPlayerCell">号码</div>
									<div class="col-md-4 tacticsPlayerCell">姓名</div>
								</div>
							</div>
							<div class="col-md-12" id="tablePlayerList" style="padding-left: 0px; padding-right: 0px;">
								<c:forEach items="${playerList}" var="player" varStatus="status">
									<div class="row tacticsPlayerItemLine text-center"
										tid="${ player.id }" 
										tname="<xss:xssFilter text="${player.name}" filter="html"/>"
										tavatar="${ player.avatar}" 
										tavatar_id="${ player.avatarId}" 
										tjersey_number="${player.userExtInfoMap['professional_jersey_number']}"
										tposition="${player.translatedPosition}"
										theight="${player.userExtInfoMap['professional_tall']}"
										tweight="${player.userExtInfoMap['professional_weight']}"
											<c:if test="${ status.index%2==1 }">
												style="background-color: #F2F5F7;"
											</c:if>
											onMouseOver="$(this).css({'background-color':'#D9EDF7'});"
											<c:choose>
												<c:when test="${ status.index%2==1 }">
													onMouseOut="$(this).css({'background-color':'#F2F5F7'});"
												</c:when>
												<c:otherwise>
													onMouseOut="$(this).css({'background-color':'inherit'});"
												</c:otherwise>
											</c:choose>
										>
										<c:choose>
											<c:when test="${ !empty player.translatedPosition }">
												<div class="col-md-4 tacticsPlayerCell"
													style="border-left: 0px solid #cecece; border-bottom: 0px solid #cecece; 
													border-right: 0px solid #cecece;">${player.translatedPosition}</div>
											</c:when>
											<c:otherwise>
												<div class="col-md-4 tacticsPlayerCell"
													style="border-left: 0px solid #cecece; border-bottom: 0px solid #cecece; 
													border-right: 0px solid #cecece;">无</div>
											</c:otherwise>
										</c:choose>
										<c:choose>
											<c:when
												test="${ !empty player.userExtInfoMap['professional_jersey_number'] }">
												<div class="col-md-4 tacticsPlayerCell"
													style="border-bottom: 0px solid #cecece; border-right: 0px solid #cecece;">${player.userExtInfoMap['professional_jersey_number'] }</div>
											</c:when>
											<c:otherwise>
												<div class="col-md-4 tacticsPlayerCell"
													style="border-bottom: 0px solid #cecece; border-right: 0px solid #cecece;">无</div>
											</c:otherwise>
										</c:choose>
										<div class="col-md-4 tacticsPlayerCell" title="${player.name}"
											style="border-right: 0px solid #cecece; border-bottom: 0px solid #cecece;"><xss:xssFilter text="${player.name}" filter="html"/></div>
									</div>
								</c:forEach>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div style="margin-right: 351px;">
		<div class="panel-heading text-left" style="background-color:#067DC2;color:white"><xss:xssFilter text="${ starters.name }" filter="html"/></div>
		<div class="panel-body" style="background-color: white;">
			<!-- <sa-panel title="编辑"> -->

			<div>
				<img class="starterAvator" src="${ tactics.imgUrl }"></img>
				<div class="col-md-1" style="margin-top: 15px;width: 80px;">阵型：</div>
				<div class="col-md-10">
					<div id="description" 
						name="description" style="width:100%;margin-top: 15px;background-color: white;" disabled="disabled"><xss:xssFilter text="${starters.formationTypeName}" filter="html"/></div>
				</div>

				<div class="col-md-1" style="margin-top: 15px;width: 80px;">备注：</div>
				<div class="col-md-10">
					<div id="description" 
						name="description" style="width:100%;margin-top: 15px;background-color: white;" disabled="disabled"><xss:xssFilter text="${tactics.description}" filter="html"/></div>
				</div>
			</div>
		</div>
	</div>
	
	
</div>
	
<script type="text/javascript">
	playerEvaList = [];
	playerGameRecordList = [];
	trainScore = "X";
	trainBehavior = "无数据";
</script>
<c:forEach items="${playerLatestEvaList}" var="playerEva">
	<script type="text/javascript">
		var playerEva = {};
		playerEva.userId = '${playerEva.userId}';
		playerEva.score = '${playerEva.score}';
		playerEva.evaluation = '<xss:xssFilter text="${playerEva.evaluation}" filter="html"/>';
		playerEvaList.push(playerEva);
	</script>
</c:forEach>
<c:forEach items="${playerGameRecordList}" var="playerGameRecord">
	<script type="text/javascript">
		var playerGameRecord = {};
		playerGameRecord.userId = '${playerGameRecord.userId}';
		playerGameRecord.goalCount = '${playerGameRecord.goalCount}';
		playerGameRecord.assistCount = '${playerGameRecord.assistCount}';
		playerGameRecord.starterCount = '${playerGameRecord.starterCount}';
		playerGameRecord.substituteCount = '${playerGameRecord.substituteCount}';
		playerGameRecordList.push(playerGameRecord);
	</script>
</c:forEach>


<script type="text/javascript">
	$(function() {
		setTimeout(function() {
			initEvent();
		}, 50);
	});
	
	function doCancel(message) {
		  if(message == "NoSaveContinue" || message == "SaveContinue_success") {
				sa.ajax({
					type : "get",
					url : "<%=serverUrl%>tactics/showStarterSettings",
					success : function(data) {
						//TODO: will update the container later
						AngularHelper.Compile($('#content'), data);
					},
					error: function() {
						alert("打开首发设置列表页面失败");
					}
				});
				return;
		  } else if(message == "SaveCancel" || message == "SaveContinue_failed") {
			  return;
		  }
		  return;
	}
    
	function initEvent() {
		buildBreadcumb("首发详情");
		$("#edit_btn").off("click");
		$("#edit_btn").on("click",function() {
			sa.ajax({
				type : "get",
				url : "<%=serverUrl%>tactics/showStartersEdit?startersId=" + '${starters.tacticsId}',
				success : function(data) {
					//TODO: will update the container later
					AngularHelper.Compile($('#content'), data);
				},
				error: function() {
					alert("打开首发编辑页面失败");
				}
			});
 		});
 		
		$("#return_btn").off("click");
		$("#return_btn").on("click",function() {
			sa.ajax({
				type : "get",
				url : "<%=serverUrl%>tactics/showStarterSettings",
				success : function(data) {
					//TODO: will update the container later
					AngularHelper.Compile($('#content'), data);
				},
				error: function() {
					alert("打开首发列表页面失败");
				}
			});
 		});
		
 		$("#copy_btn").off("click");
        $('#copy_btn').click(function(data) {
			sa.ajax({
				type : "get",
				url : "<%=serverUrl%>tactics/copyStarters?startersId=" + '${starters.tacticsId}',
				success : function(data) {
					//TODO: will update the container later
					AngularHelper.Compile($('#content'), data);
				},
				error: function() {
					alert("复制首发页面失败");
				}
			});
		});
	}
	
	$("#delete_btn").off("click");
	$("#delete_btn").on("click",function() {
		bootbox.dialog({
			  message: "您是否要删除该首发设置？",
			  title: "确认删除",
			  buttons: {
			  main: {
			      label: "取消",
			      className: "btn-default",
			      callback: function() {

			      }
			    },
			    danger: {
			      label: "删除",
			      className: "btn-danger",
			      callback: function() {
			    	  var submitdata = {"startersID" : "" + '${starters.tacticsId}'};
						$.ajax({
							type : "post",
							url : "<%=serverUrl%>tactics/deleteStarters",
							data : submitdata,
							dataType : "json",
			 				success : function(data) {
			 					if (data.status){
			 						$('#content').loadAngular("<%=serverUrl%>tactics/showStarterSettings");
			 					} else {
			 						alert(data.errorMessage);
			 					}
			 				},
			 				error: function(data) {
			 					alert(data.responseText);
			 				}
		 				});	 
			      }
			    }
			    
			  }
			}); 
		});
	
	$('#tablePlayerList').off('click','.tacticsPlayerItemLine');
	$('#tablePlayerList').on('click','.tacticsPlayerItemLine',function changePlayer(event) {

			event.preventDefault();
			var obj = $(event.target).parent();
			var tId = $(obj).attr('tid');
			var tName = $(obj).attr('tname');
			var tJersey_number = $(obj).attr('tjersey_number');
			var tPosition = $(obj).attr('tposition');
			var tAvatar = $(obj).attr('tavatar');
			var tWeight = parseFloat($(obj).attr('tweight'));
			var tHeight = parseFloat($(obj).attr('theight'));
			
			if(tJersey_number == null || tJersey_number == "") {
				tJersey_number = '--';
			}
			if(!tWeight) {
				tWeight = '--';
			}
			if(!tHeight) {
				tHeight = '--';
			}
			
			$('#playerName').html(tName);
			$("#playerName").attr({"title" : tName});
			$('#playerNumber').html(tJersey_number);
			$('#playerPosition').html(tPosition);
			$('#playerHeight').html(tHeight);
			$('#playerWeight').html(tWeight);
			$('#trainScore').html('-');
			$('#trainBehavior').html('无数据');
			$("#trainBehavior").attr({"title" : '无数据'});
			
			if(tAvatar == null || tAvatar == "") {
				path = "<%=serverUrl%>resources/images/user_avatar.png";
				$("#playerAvaterId").attr('src',path); 
			} else {
				path = "<%=serverUrl%>file/downloadFile?fileName=" + tAvatar;
				$("#playerAvaterId").attr('src',path); 
			}
			
			for(var index=0; index<playerEvaList.length;index++) {
				var tmpObj = playerEvaList[index];
				if(tmpObj.userId == tId) {
					trainScore = parseFloat(tmpObj.score);
					if(!trainScore) {
						trainScore = '--';
					}
					$('#trainScore').html(trainScore);
					
					if(!tmpObj.evaluation || tmpObj.evaluation == "") {
						$('#trainBehavior').html('无数据');
						$("#trainBehavior").attr({"title" : '无数据'});
					} else {
						$('#trainBehavior').html(tmpObj.evaluation);
						$("#trainBehavior").attr({"title" : tmpObj.evaluation});
					}
					break;
				}
			}
			
			for(var index=0; index<playerGameRecordList.length;index++) {
				var tmpObj = playerGameRecordList[index];
				if(tmpObj.userId == tId) {
					$('#starterCount').html(tmpObj.starterCount);
					$('#substituteCount').html(tmpObj.substituteCount);
					$('#goalCount').html(tmpObj.goalCount);
					$('#assistCount').html(tmpObj.assistCount);
					break;
				}
			}
	});

</script>