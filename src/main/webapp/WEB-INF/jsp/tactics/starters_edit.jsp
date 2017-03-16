<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="xss" uri="http://www.sportsdata.cn/xss"%>

<%@ page import="cn.sportsdata.webapp.youth.common.utils.CommonUtils"%>
<%@ page import="cn.sportsdata.webapp.youth.common.vo.TacticsVO"%>
<%@ page import="cn.sportsdata.webapp.youth.common.vo.UserVO"%>
<%@ page import="cn.sportsdata.webapp.youth.common.vo.utraining.UtrainingTaskEvaluationVO"%>

<%
	String serverUrl = CommonUtils.getServerUrl(request);
	boolean isCreate = (Boolean) request.getAttribute("isCreate");
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

body,a{ font-size: 14px; color: #555;;}
.wordCount{ position:relative;width:auto;min-width:100%;display:inline-block}
.wordCount textarea{ width: 100%;min-width:100%;}
.wordCount .wordwrap{ position:absolute; right: 16px; bottom: 2px;}
.wordCount .word{ color: red; padding: 0 4px;;}
</style>

<div class="clearfix"></div>
<input type="hidden" id="tactics_page_changed" value="false"></input>
<input type="hidden" id="tactics_page_changed_choose" value=""></input>
<div class="button_area" id="button_area">
	<button id="save_btn" class="btn btn-primary" style="float: right;margin-left: 10px;">保存</button>	
	<button id="cancel_btn" class="btn btn-default" style="float: right; margin-left: 10px;">取消</button>
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

						<div class="row" style="overflow:auto;margin-left:0px;margin-right:0px;" id="team_member_row_div">
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
										tname="${ player.name}"
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
												<div class="col-md-4 tacticsPlayerCell" style="border-bottom: 0px solid #cecece; 
													border-right: 0px solid #cecece;">${player.userExtInfoMap['professional_jersey_number'] }</div>
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
		<div class="panel-heading text-left" style="background-color:#067DC2;color:white">编辑</div>
		<div class="panel-body" style="background-color: white;">
			<!-- <sa-panel title="编辑"> -->
			<div style="height: 130px;">
				<form id="starters_form" enctype="multipart/form-data">
					<div class="row">
						<div class="col-md-12">
							<div class="row" style="">
								<div class="col-md-1" style="width:80px;line-height:34px">标题：</div>
								<div class="col-md-4">
									<div class ="form-group">
									<c:choose>
										<c:when test="${isCreate == true}">
											<input type="text" class="tacticsEditInput form-control"
												id="name" name="name" style="width: 100%" required placeholder="请输入首发标题"  onchange="com.setChanged();"/>
										</c:when>
										<c:otherwise>
											<input type="text" class="form-control"
												id="name" name="name" style="width: 100%" required placeholder="请输入首发标题" 
												value="<xss:xssFilter text="${ tactics.name }" filter="html"/>" onchange="com.setChanged();"/>
										</c:otherwise>
									</c:choose>
									</div>
								</div>
								<div class="col-md-1" style="width:80px;line-height:34px">阵型：</div>
								<div class="col-md-2">
									<select id="select_formationtype"
										class="tacticsEditInput form-control" style="width: 100%">
										<c:forEach items="${formationTypeList}" var="formationType"
											varStatus="status">
											<c:choose>
												<c:when test="${formationType.checked == true}">
													<option value='${formationType.id}' selected><xss:xssFilter text="${formationType.name}" filter="html"/></option>
												</c:when>
												<c:otherwise>
													<option value='${formationType.id}'><xss:xssFilter text="${formationType.name}" filter="html"/></option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="row" style="">
								<div class="col-md-1" style="width:80px;line-height:34px">备注：</div>
								<div class="col-md-10">
									<div class ="wordCount form-group" id="wordCount">
										<c:choose>
											<c:when test="${isCreate == true}">
												<textarea id="description"
													name="description" style="height: 55px; width: 100%"  onchange="com.setChanged();"></textarea>
											</c:when>
											<c:otherwise>
												<textarea class="tacticsEditInput" id="description" maxlength="1000"
													name="description" style="height: 65px;" onchange="com.setChanged();"></textarea>
													<p id="stored_future_plan" class="hidden"><xss:xssFilter text="${tactics.description}" filter="html"/></p>								
											</c:otherwise>
										</c:choose>
										<span class="wordwrap hidden"><var class="word">1000</var>/1000</span>
									</div>
								</div>
							</div>
							<div class="row tacticsEditItemLine"
								style="display: none;">
								<div class="col-md-4">场地：</div>
								<div class="col-md-8">
									<select id="select_playgroundtype"
										class="tacticsEditInput form-control" style="width: 100%">
										<c:forEach items="${playgroundTypeList}" var="playgroundType"
											varStatus="status">
											<c:choose>
												<c:when test="${playgroundType.checked == true}">
													<option value='${playgroundType.toJson()}' selected>
														<xss:xssFilter text="${playgroundType.name}" filter="html"/>
													</option>
												</c:when>
												<c:otherwise>
													<option value='${playgroundType.toJson()}'>
														<xss:xssFilter text="${playgroundType.name}" filter="html"/>
													</option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="row" style="display: none">
								<div class="col-md-4">首发ID：</div>
								<div class="col-md-8">
									<c:choose>
										<c:when test="${isCreate}">
											<input type="text" class="tacticsEditInput form-control"
												id="starterId" name="id" value="0" />
										</c:when>
										<c:otherwise>
											<input type="text" class="tacticsEditInput form-control"
												id="starterId" name="id" value="<xss:xssFilter text="${ starters.tacticsId }" filter="html"/>" />
										</c:otherwise>
									</c:choose>
								</div>
							</div>
						</div>
					</div>
				</form>
			</div>
	
			<div style="height:1px;border-bottom: 1px solid #cecece; margin-top:15px; margin-bottom:15px; padding-left:-15px;padding-right:-15px;"></div>
	
			<div>
				<div id="tacticsArea" style="width: 100%;float: left;">
					<jsp:include page="../system_tool/tactics_area.jsp" />
				</div>
			</div>
		</div>
	</div>
	
</div>
	
<script type="text/javascript">
	playerList = [];
	playerEvaList = [];
	playerGameRecordList = [];
	trainScore = "--";
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

<c:forEach items="${playerList}" var="player">
	<script type="text/javascript">
		var playerInfo = {};
		playerInfo.id = '${player.id}';
		playerInfo.name = "<xss:xssFilter text="${player.name}" filter="html"/>";
		playerList.push(playerInfo);
	</script>
</c:forEach>

<script type="text/javascript">
	starters_data_load = {};
	
	$(function() {
		setTimeout(function() {
			$('#team_member_row_div').height($('#tacticsArea').height());
			initData();
			initTacticsData();
			initEvent();
			var tmpval = JSON.parse($('#select_playgroundtype').val());
			if (tmpval!=null){
				changeTacticsBg(tmpval.abbr);
			}
			resizeTacticsData();
		}, 50);
		$(window).resize(function() {
			resizeTacticsData();
		});
	});

	function initData() {
		$('.nav-pills a:first').focus(); // fix issues of first tab is not focused after loading
		$('#description').val($('#stored_future_plan').text());
	}

	$.fn.serializeObject = function() {
		var o = {};
		var a = this.serializeArray();
		
		$.each(a, function() {
			if (o[this.name]) {
				if (!o[this.name].push) {
					o[this.name] = [ o[this.name] ];
				}
				o[this.name].push(this.value || '');
			} else {
				o[this.name] = this.value || '';
			}
		});
		return o;
	};

	function stringToBytes(str) {
		var ch, st, re = [];
		for (var i = 0; i < str.length; i++) {
			ch = str.charCodeAt(i); // get char   
			st = []; // set up "stack"  
			do {
				st.push(ch & 0xFF); // push byte to stack  
				ch = ch >> 8; // shift value down by 1 byte  
			} while (ch);
			// add stack contents to result  
			// done because chars have "wrong" endianness  
			re = re.concat(st.reverse());
		}
		// return an array of bytes  
		return re;
	}

	function validation(){
		$('#starters_form').bootstrapValidator({
	        message: '您输入的值不合法',
	        feedbackIcons: {
	            valid: 'glyphicon glyphicon-ok',
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	        	name:{
	        		message: '请先设置首发标题,然后再保存',
		           	validators: {
		                    notEmpty: {
		                        message: '标题是必填项，请正确填写标题。'
		                    },
		                    stringLength: {
		                         max: 60,
		                         message: '标题长度超过限制，请输入60字符以内的标题'
		                    }
		                }
	        	},
	        	description:{
	        		message: '请输入备注信息，限1000字',
		           	validators: {
	                    stringLength: {
	                         max: 1000,
	                         message: '备注长度超过限制，限1000字符'
	                    }
	                }
	    		}
	        }
	    });
	}
	
	function saveTacticsToDatabase(successCallback, failCallback, element) {
		$('#starters_form').data('bootstrapValidator').validate();
		if(!$('#starters_form').data('bootstrapValidator').isValid()){
			alert("首发标题不能为空，请设置后再保存");
			return;
		}
		
    	var form_data = $('#starters_form').serializeObject();
    	var isCreate = ${isCreate};
    	var isCopied = ${isCopied};
       	var tactics = {};
    	var saved_tactics = com.save_tactics();
    	var tacticsdata = JSON.stringify(saved_tactics.tacticsdata);
    	$('#imgSrc').attr('src',saved_tactics.imgSrc);
    	form_data.imgData = saved_tactics.imgSrc;
    	form_data.tacticsFrames = tacticsdata;
    	form_data.playground_id = JSON.parse($('#select_playgroundtype').get(0).value).id;
    	
		if (isCreate == false && isCopied == false) {
			form_data.tacticsdata = starters_data_load.tacticsdata;
			form_data.imgName = starters_data_load.imgName;
			form_data.org_id = starters_data_load.org_id;
			form_data.creator_id = starters_data_load.creator_id;
			form_data.tacticsId = starters_data_load.tacticsId;
			form_data.tactics_type_id = '${ tactics.tactics_type_id }';
		}
		else {
			form_data.tacticsdata = "";
			form_data.tacticsId = 0;
		}
		
		form_data.description = form_data.description.replace(/\r/ig, "");
		form_data.name = form_data.name
		form_data.formation_id = $('#select_formationtype').get(0).value;
		form_data.playerList = [];
		//ignore first default player		
		for(var index=0; index<com.childList.length;index++) {
			var tmpObj = com.childList[index];
			
			for(var idx=0; idx<playerList.length;idx++) {
				var player = playerList[idx];
				if (player.id == tmpObj.id ) {
					form_data.playerList.push(player);
				}
			}
		}
		
		var submitdata = JSON.stringify(form_data);
		$.ajax({
			type : "post",
			url : "<%=serverUrl%>tactics/saveStarters",
							data : submitdata,
							contentType : "application/json",
							success : function(data) {
								//AngularHelper.Compile($('#content'), data);

								if (!data.status) {
									$('#tactics_page_changed_choose').val("SaveContinue_failed");
									if(failCallback) {
										eval(failCallback);
									} else {
										alert("提交首发设置信息异常");
									}
									return;
								}

								//AngularHelper.Compile($('#content'), data.result);
								$('#imgSrc').attr('src', data.result.url);
								form_data_load = data.result;
								isCreate = false;
								$('#delete_btn').css('display','block'); 
								localStorage.soccerboard_data = null;
								$('#tactics_page_changed_choose').val("SaveContinue_success");
								$('#tactics_page_changed').val("false");
								
								if(successCallback) {
									var urlPath = "<%=serverUrl%>tactics/showStartersView?startersId=" + form_data_load.tacticsId;
									eval(successCallback);
									$('#content').loadAngular(urlPath);
								} else {
									alert('首发设置信息保存成功');
								}
							},
							error : function() {
								$('#tactics_page_changed_choose').val("SaveContinue_failed");
								
								if(failCallback) {
									eval(failCallback);
								} else {
									alert("保存首发信息失败");
								}
							}
						});
	}
	
	function doCancel(message) {
		  if(message == "NoSaveContinue" || message == "SaveContinue_success") {
			  var urlPath = "<%=serverUrl%>tactics/showStartersView?startersId=" + '${starters.tacticsId}';
				var isCreate = ${isCreate};
				var isCopied = ${isCopied};
				if (isCreate == true) {
					urlPath = "<%=serverUrl%>tactics/showStarterSettings";
				}
				if (isCopied == true) {
					urlPath = "<%=serverUrl%>tactics/showStartersView?startersId=" + '${srcTacticsId}';
				}
				sa.ajax({
					type : "get",
					url : urlPath,
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
	
	function SaveContinue( ) {
		 $('#save_btn').trigger('click');	 
	}
	
	function NoSaveContinue() {
		$('#tactics_page_changed_choose').val("NoSaveContinue");
	}
	
	function SaveCancel() {
		$('#tactics_page_changed_choose').val("SaveCancel");
	}
	
    function statInputNum(textArea,numItem) {
        var max = numItem.text(),
            curLength;
        textArea[0].setAttribute("maxlength", max);
        curLength = textArea.val().length;
        numItem.text(max - curLength);
        textArea.on('input propertychange', function () {
            numItem.text(max - $(this).val().length);
        });
    }
    
	function initEvent() {
		buildBreadcumb("首发编辑");
        //先选出 textarea 和 统计字数 dom 节点
        var wordCount = $("#wordCount"),
            textArea = wordCount.find("textarea"),
            word = wordCount.find(".word");
        //调用
        statInputNum(textArea,word);
        
		var isCreate = ${isCreate};
		var isCopied = ${isCopied};
		validation();
		if (isCreate == false && isCopied == false) {
			var tactics = {}
			starters_data_load.org_id = '${tactics.org_id}';
			starters_data_load.creator_id = '${tactics.creator_id}';
			starters_data_load.tacticsId = '${tactics.id}';
			starters_data_load.imgName = '${tactics.imgName}';
			starters_data_load.tacticsdata = '${tactics.tacticsdata}';
		} else {
			starters_data_load.tacticsdata = "";
			starters_data_load.id = 0;
		}
		
		$('#select_formationtype').select2();
		
		$('#select_playgroundtype').select2();
		$('#select_playgroundtype').change(function() {
			var tmpval = JSON.parse($('#select_playgroundtype').val());
			if (tmpval != null){
				changeTacticsBg(tmpval.abbr);
			}
		});
 		
		$("#cancel_btn").off("click");
		$("#cancel_btn").on("click",function() {
			// for tactics need to 
			var tmp = $("#tactics_page_changed");
			if(tmp && tmp != null && $(tmp).val()=="true") {
				bootbox.dialog({
					  message: "首发设置所做更改尚未保存，请选择",
					  title: "退出首发编辑",
					  buttons: {
					    unchange: {
						      label: "留在此页",
						      className: "btn-default",
						      callback: function() {
								return;
						      }
						    },
					    danger: {
					      label: "不保存",
					      className: "btn-danger",
					      callback: function() {
					    	  doCancel('NoSaveContinue');
					      }
					    },
					    main: {
					      label: "保存并继续",
					      className: "btn-primary",
					      callback: function() {
					    	  saveTacticsToDatabase("doCancel('SaveContinue_success');", "alert('保存首发信息失败');", null);
					      }
					    }
					  }
					});
			} else {
				doCancel("NoSaveContinue");
			}
 		});
		
 		$("#save_btn").off("click");
        $('#save_btn').click(function() {
        	saveTacticsToDatabase("alert('保存首发设置成功');", "alert('保存首发设置失败');", null);
		});
	}
	
	$('#tablePlayerList').off('mouseenter','.tacticsPlayerItemLine');
	$('#tablePlayerList').on('mouseenter','.tacticsPlayerItemLine', function highlightPlayer(event){
		event.preventDefault();
		var obj = $(event.target).parent();
		var tId = $(obj).attr('tid');
		com.childList = com.childList || {};
		for(var index=0; index<com.childList.length;index++) {
			var tmpObj = com.childList[index];
			if(tmpObj.id == tId) {
				tmpObj.isSelected = true;
				if(com.lastHighLightObj != tmpObj){
					com.lastHighLightObj = tmpObj;
					tmpObj.show();	
				}
				return;
			}
		}
	});

	$('#tablePlayerList').off('mouseleave','.tacticsPlayerItemLine');
	$('#tablePlayerList').on('mouseleave','.tacticsPlayerItemLine', function clearhighlight(event){
		event.preventDefault();
		var obj = $(event.target).parent();
		com.lastHighLightObj = null;
		var tId = $(obj).attr('tid');
		
		com.childList = com.childList || {};
		for(var index=0; index<com.childList.length;index++) {
			var tmpObj = com.childList[index];
			if(tmpObj.id == tId) {
				tmpObj.isSelected = false;
				com.show();
				return;
			}
		}
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
			$('#playerNumber').html(tJersey_number);
			$('#playerPosition').html(tPosition);
			$('#playerHeight').html(tHeight);
			$('#playerWeight').html(tWeight);
			$('#trainScore').html('-');
			$('#trainBehavior').html('无数据');
			
			$("#playerName").attr({"title" : tName});
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

	var TimeFn = null;
	$('#tablePlayerList').off('dblclick','.tacticsPlayerItemLine');
	$('#tablePlayerList').on('dblclick','.tacticsPlayerItemLine',function addPlayer(event) {

		event.preventDefault();
		var obj = $(event.target).parent();
		var tId = $(obj).attr('tid');
		var tName = $(obj).attr('tname');
		var tJersey_number = $(obj).attr('tjersey_number');
		var tPosition = $(obj).attr('tposition');
		var tAvatar = $(obj).attr('tavatar_id');
		
		com.childList = com.childList || {};
		for(var index=0; index<com.childList.length;index++) {
			var tmpObj = com.childList[index];
			if(tmpObj.id == tId) {
				alert(tName+"已经在场上");
				return;
			}
		}
		var tAvatorId = tAvatar;
		if(tAvatar == null || tAvatar == "") {
			tAvatar = null;
		} else {
			tAvatar = "<%=serverUrl%>hagkFile/asset?id=" + tAvatar;
		}
	    var playerObj = new com.class.Obj("player_1",
	    		tAvatar, tName, $('#soccerboard').width()/2, $('#soccerboard').height()/2, com.playersize, com.playersize);
	    playerObj.id = tId;
	    playerObj.sys_img = "player_1";
	    playerObj.jerseyid = tId;
	    playerObj.avatar_id = tAvatorId;
	    if(tJersey_number!=null && tJersey_number != ""){
	    	playerObj.jerseyid = tJersey_number;
			for (var i = 0; i< com.childList.length; i++) {
				if(com.childList[i].type == "player_1" && tJersey_number == com.childList[i].jerseyid) {
					alert("号码为 "+com.childList[i].jerseyid+" 的队员已经在场");
					return;
				}
			}
			
	    } else {
	    	playerObj.jerseyid = com.gen_player_jerseyid("player_1");
	    }

	    playerObj.position = tPosition;
	    com.childList.push(playerObj);
	    com.show();
	});
</script>

<script type="text/javascript">
	function changebg(item, strUrl) {
		document.getElementById(item).style.backgroundImage = "url('" + strUrl
				+ "')";
	}
</script>

<script language=JavaScript type=text/JavaScript>
	function MM_preloadImages() { // v3.0
		var d = document;
		if (d.images) {
			if (!d.MM_p)
				d.MM_p = new Array();
			var i, j = d.MM_p.length, a = MM_preloadImages.arguments;
			for (i = 0; i < a.length; i++) {
				if (a[i].indexOf("#") != 0) {
					d.MM_p[j] = new Image;
					d.MM_p[j++].src = a[i];
				}
			}
		}
	}
	function MM_swapImgRestore() { // v3.0
		var i, x, a = document.MM_sr;
		for (i = 0; a && i < a.length && (x = a[i]) && x.oSrc; i++) {
			x.src = x.oSrc;
		}
	}
	function MM_findObj(n, d) { // v4.01
		var p, i, x;
		if (!d) {
			d = document;
		}
		if ((p = n.indexOf("?")) > 0 && parent.frames.length) {
			d = parent.frames[n.substring(p + 1)].document;
			n = n.substring(0, p);
		}
		if (!(x = d[n]) && d.all) {
			x = d.all[n];
		}
		for (i = 0; !x && i < d.forms.length; i++) {
			x = d.forms[i][n];
		}
		for (i = 0; !x && d.layers && i < d.layers.length; i++) {
			x = MM_findObj(n, d.layers[i].document);
		}

		if (!x && d.getElementById) {
			x = d.getElementById(n);
		}
		return x;
	}

	function MM_swapImage() { // v3.0
		var i, j = 0, x, a = MM_swapImage.arguments;
		document.MM_sr = new Array;
		for (i = 0; i < (a.length - 2); i += 3) {
			if ((x = MM_findObj(a[i])) != null) {
				document.MM_sr[j++] = x;
				if (!x.oSrc) {
					x.oSrc = x.src;
				}
				x.src = a[i + 2];
			}
		}
	}

	function SetImg(name, src1, src2) {
		if (document.getElementById(name).getAttribute("src") == src1) {
			document.getElementById(name).setAttribute("src", src2);
			return;
		}
		if (document.getElementById(name).getAttribute("src") == src2) {
			document.getElementById(name).setAttribute("src", src1);
			return;
		}
	}
</SCRIPT>
