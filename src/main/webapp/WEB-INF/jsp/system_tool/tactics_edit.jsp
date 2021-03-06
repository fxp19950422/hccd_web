<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="xss" uri="http://www.sportsdata.cn/xss"%>
<%@ page import="cn.sportsdata.webapp.youth.common.constants.Constants" %>
<%@ page import="cn.sportsdata.webapp.youth.common.utils.CommonUtils"%>
<%@ page import="cn.sportsdata.webapp.youth.common.vo.TacticsVO"%>


<%
	String serverUrl = CommonUtils.getServerUrl(request);
	String single_tactics_type_id=Constants.singleTraining_Tactics_Type;
	boolean isCreate = (Boolean) request.getAttribute("isCreate");
	//UserVO vo = (UserVO) request.getAttribute("player");
	//long userId = isCreate ? 0 : vo.getId();
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

body,a{ font-size: 14px; color: #555;;}
.wordCount{ position:relative;width:auto;min-width:100%;display:inline-block}
.wordCount textarea{ width: 100%;min-width:100%;}
.wordCount .wordwrap{ position:absolute; right: 16px; bottom: 2px;}
.wordCount .word{ color: red; padding: 0 4px;;}
</style>

<div class="clearfix"></div>
<input type="hidden" id="tactics_page_changed" value="false"></input>
<input type="hidden" id="tactics_page_changed_choose" value=""></input>
<div>

	<div class="button_area" id="button_area">
		<button id="save_btn" class="btn btn-primary" style="float: right;margin-left: 10px;">保存</button>	
		<c:choose>
			<c:when test="${ isCreate == false }">
				<button id="delete_btn" class="btn btn-danger" style="float: right; margin-left: 10px;">删除</button>
			</c:when>
			<c:otherwise>
				<button id="delete_btn" class="btn btn-danger" style="display:none; float: right; margin-left: 10px;">删除</button>
			</c:otherwise>
		</c:choose>
		<button id="cancel_btn" class="btn btn-default" style="float: right; margin-left: 10px;">取消</button>
	</div>
	<div class="clearfix" style="height: 15px;"></div>
	
	<div>
	
	<div class="panel panel-default">
		<div class="panel-heading text-left" style="background-color:#067DC2;color:white">编辑</div>
		
		<div style="padding-bottom: 15px;">			

		<div style="padding: 15px 15px 0px;">
			<form id="tactics_form" enctype="multipart/form-data">
				<div class="row">
					<div class="col-md-12">
						<div class="tacticsEditItemLine row " style="">
							<div class="col-md-1" style="width:80px;line-height:34px">标题：</div>
							<div class="col-md-4">
								<div class ="form-group">
									<c:choose>
										<c:when test="${isCreate}">
											<input type="text" class="tacticsEditInput form-control"
												id="name" name="name" required placeholder="请输入战术板标题" onchange="com.setChanged();" />
										</c:when>
										<c:otherwise>
											<input type="text" class="tacticsEditInput form-control"
												id="name" name="name" required placeholder="请输入战术板标题"  onchange="com.setChanged();" 
												value="<xss:xssFilter text="${ tactics.name }" filter="html"/>" />
										</c:otherwise>
									</c:choose>
								</div>
							</div>
							<c:if test="${pFlag!='single'}">
								<div class="col-md-1" style="width:80px;line-height:34px;" >类型：</div>
							</c:if>
							<div class="col-md-2" <c:if test="${pFlag=='single'}">style="display: none;"</c:if>>
							<select id="select_tacticstype"
									class="tacticsEditInput form-control" style="width: 100%">
									<c:forEach items="${tacticsTypeList}" var="tacticsType"
										varStatus="status">
										<c:choose>
											<c:when test="${tacticsType.checked == true}">
												<option value='${tacticsType.id}' selected>${tacticsType.name}</option>
											</c:when>
											<c:otherwise>
												<option value='${tacticsType.id}'>${tacticsType.name}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</select>
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
												<option value='${playgroundType.toJson()}' selected><xss:xssFilter text="${playgroundType.name}" filter="html"/></option>
											</c:when>
											<c:otherwise>
												<option value='${playgroundType.toJson()}'><xss:xssFilter text="${playgroundType.name}" filter="html"/></option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="row tacticsEditItemLine" style="">
							<div class="col-md-1" style="width:80px;line-height:34px">备注：</div>
							<div class="col-md-10">
								<div class ="wordCount form-group" id="wordCount">
									<c:choose>
										<c:when test="${isCreate}">
											<textarea class="tacticsEditInput" id="description"
												name="description" style="height: 80px"  onchange="com.setChanged();"></textarea>
											<span class="wordwrap hidden"><var class="word">1000</var>/1000</span>
										</c:when>
										<c:otherwise>
											<textarea class="tacticsEditInput" id="description"
												name="description" style="height: 65px"  onchange="com.setChanged();"><xss:xssFilter text="${tactics.description}" filter="html"/></textarea>
											<span class="wordwrap hidden"><var class="word">1000</var>/1000</span>
										</c:otherwise>
									</c:choose>
								</div>
							</div>
						</div>
						<div class="row tacticsEditItemLine" style="display: none">
							<div class="col-md-4">战术板ID：</div>
							<div class="col-md-8">
								<c:choose>
									<c:when test="${isCreate}">
										<input type="text" class="tacticsEditInput form-control"
											id="tacticsid" name="id" value="0" />
									</c:when>
									<c:otherwise>
										<input type="text" class="tacticsEditInput form-control"
											id="tacticsid" name="id" value="${ tactics.id }" />
									</c:otherwise>
								</c:choose>
							</div>
						</div>
						<div class="row tacticsEditItemLine" style="display: none">
							<div class="col-md-4">战术板内容：</div>
							<div class="col-md-8">
								<c:choose>
									<c:when test="${isCreate}">
										<textarea class="tacticsEditInput" id="tacticsFrames"
											name="tacticsFrames" /></textarea>
									</c:when>
									<c:otherwise>
										<textarea class="tacticsEditInput" id="tacticsFrames"
											name="tacticsFrames" /></textarea>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
					</div>
					<div class="col-md-4 text-center" style="display: none">
						<c:choose>
							<c:when test="${ !empty tactics.imgUrl }">
								<img class="tacticsAvatar" id="imgSrc" name="imgSrc"
									src="${ tactics.imgUrl }"></img>
							</c:when>
							<c:otherwise>
								<img class="tacticsAvatar" id="imgSrc" name="imgSrc"
									src="<%=serverUrl%>resources/images/user_avatar.png"></img>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</form>
		</div>

		<div style="height:1px;border-bottom: 1px solid #cecece; margin-bottom:15px; padding-left:-15px;padding-right:-15px;"></div>

		<div style="min-width:850px;margin-left: 15px;margin-right: 15px;">

			<div style="float: right; width: 260px;margin-left: 15px;" id="team_member_div">
				<div id="team_member" style="background-color: white; height:100%; width: 100%">
					<div class="tab-content" style="width: 100%; padding: auto;">
						<div role="tabpanel" class="tab-pane active" id="basic_tab">
							<div class="panel panel-default">
								<div>
									<div>
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
														tid="${ player.id }" tname="<xss:xssFilter text="${player.name}" filter="html"/>"
														tavatar="${ player.avatar}" 
														tavatar_id="${ player.avatarId}" 
														tjersey_number="${player.userExtInfoMap['professional_jersey_number']}"
														tposition="${player.translatedPosition}"
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
																	style="border-left: 0px solid #cecece; border-bottom: 0px solid #cecece; border-right: 0px solid #cecece;">${player.translatedPosition}
																</div>
															</c:when>
															<c:otherwise>
																<div class="col-md-4 tacticsPlayerCell"
																	style="border-left: 0px solid #cecece; border-bottom: 0px solid #cecece; border-right: 0px solid #cecece;">无
																</div>
															</c:otherwise>
														</c:choose>
														<c:choose>
															<c:when
																test="${ !empty player.userExtInfoMap['professional_jersey_number'] }">
																<div class="col-md-4 tacticsPlayerCell"
																	style="border-bottom: 0px solid #cecece; border-right: 0px solid #cecece;">${
	                                                            player.userExtInfoMap['professional_jersey_number'] }
																</div>
															</c:when>
															<c:otherwise>
																<div class="col-md-4 tacticsPlayerCell"
																	style="border-bottom: 0px solid #cecece; border-right: 0px solid #cecece;">无
																</div>
															</c:otherwise>
														</c:choose>
														<div class="col-md-4 tacticsPlayerCell" title="${player.name}"
															style="border-right: 0px solid #cecece; border-bottom: 0px solid #cecece;"><xss:xssFilter text="${player.name}" filter="html"/></div>
													</div>
													<!-- <div class="clearfix"></div> -->
												</c:forEach>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			
			<div id="tacticsArea"  style="margin-right: 275px;">
				<jsp:include page="tactics_area.jsp" />
			</div>
			
			
		</div>
		</div>
		</div>
		</div>
	</div>

<script type="text/javascript">
	playerList = [];
</script>
<c:forEach items="${playerList}" var="player">
	<script type="text/javascript">
		var playerInfo = {};
		playerInfo.id = '${player.id}';
		playerInfo.name = "<xss:xssFilter text="${player.name}" filter="html"/>";
		playerList.push(playerInfo);
	</script>
</c:forEach>

<script type="text/javascript">
    var pFlag;
	form_data_load = {};
	isCreate = true;

	$(function() {
		setTimeout(function() {
			$('#team_member').height($('#tacticsArea').height());
			initData();
			initTacticsData();
			initEvent();
			var tmpval = JSON.parse($('#select_playgroundtype').val());
			if (tmpval != null){
				changeTacticsBg(tmpval.abbr);
			}
			resizeTacticsData();
		}, 50);
 		$(window).resize(function() {
		 resizeTacticsData();
		}); 
	});

	function initData() {
		pFlag='${pFlag}';
		$('.nav-pills a:first').focus(); // fix issues of first tab is not focused after loading
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
		$("#tactics_form").bootstrapValidator({
	        message: '您输入的值不合法',
	        feedbackIcons: {
	            valid: 'glyphicon glyphicon-ok',
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	        	name:{
	        		message: '请先设置战术板标题,然后再保存',
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
		
		$("#tactics_form").data('bootstrapValidator').validate();
		if(!$("#tactics_form").data('bootstrapValidator').isValid()){
			alert("战术板标题不能为空，请设置后再保存");
			return;
		}
  	    
    	var saved_tactics = com.save_tactics();
    	var tacticsdata = JSON.stringify(saved_tactics.tacticsdata);
    	//$('#tacticsdata').val(tacticsdata);
    	$('#imgSrc').attr('src',saved_tactics.imgSrc);
		var form_data = $('#tactics_form').serializeObject();
		form_data.description = form_data.description.replace(/\r/ig, "");
		form_data.imgData = saved_tactics.imgSrc;
		//form_data.tacticsdata = stringToBytes(tacticsdata);
		form_data.tacticsFrames = tacticsdata;
		if('${pFlag}'=='single'){
			form_data.tactics_type_id=<%=single_tactics_type_id%>;
		}else{
			 form_data.tactics_type_id = $('#select_tacticstype').get(0).value;
		}
		form_data.playground_id = JSON.parse($('#select_playgroundtype').get(0).value).id;
		form_data.pFlag='${pFlag}';
		if (isCreate == false) {
			form_data.org_id = form_data_load.org_id;
			form_data.creator_id = form_data_load.creator_id;
			form_data.tacticsdata = form_data_load.tacticsdata;
			form_data.imgName = form_data_load.imgName;
			form_data.id = form_data_load.id;
		}
		else {
			form_data.tacticsdata = "";
			form_data.id = 0;
		}
		
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
			url : "<%=serverUrl%>system_tool/saveTactics",
							data : submitdata,
							contentType : "application/json",
							success : function(data) {
								//AngularHelper.Compile($('#content'), data);

								if (!data.status) {
									$('#tactics_page_changed_choose').val("SaveContinue_failed");
									if(failCallback) {
										if (data.errorCode == -1) {
											alert(data.errorMessage);
										} else {
											eval(failCallback);
										}
									} else {
										alert("提交战术板信息异常");
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
									eval(successCallback);
								} else {
									alert('战术板信息保存成功');
								}
								if(pFlag=='single'){
									//$("#tactics_panel").empty();
									$("#tactics_panel").loadAngular("<%=serverUrl%>system_tool/showTacticsListDetail?tactics_type_id=<%=single_tactics_type_id%>&pFlag=single");
								}
							},
							error : function() {
								$('#tactics_page_changed_choose').val("SaveContinue_failed");
								
								if(failCallback) {
									eval(failCallback);
								} else {
									alert("保存战术板信息失败");
								}
							}
						});
	}
	
	function doCancel(message) {
		  if(message == "NoSaveContinue" || message == "SaveContinue_success") {
			  if(pFlag=='single'){
// 				  $('#starter_modal').modal('hide');
				 // $("#tactics_panel").empty();
				  $("#tactics_panel").loadAngular("<%=serverUrl%>system_tool/showTacticsListDetail?tactics_type_id=<%=single_tactics_type_id%>&pFlag=single");
			  }else{
				sa.ajax({
					type : "get",
					url : "<%=serverUrl%>system_tool/showTacticsList",
					success : function(data) {
						//TODO: will update the container later
						AngularHelper.Compile($('#content'), data);
					},
					error: function() {
						alert("打开创建战术板列表页面失败");
					}
				});
			  }
				return;
		  } else if(message == "SaveCancel" || message == "SaveContinue_failed") {
			  return;
		  }
		  return;
	}	
	
	function SaveContinue( ) {
		 $('#save_btn').trigger('click');	 
		 if ($('#classModal_AlertSave').length > 0){
		 	$("#classModal_AlertSave").hide();
		 }
		 if ($('#classModal_AlertCancel').length > 0){
			 $("#classModal_AlertCancel").hide();
		 } 
	}
	
	function NoSaveContinue() {
		$('#tactics_page_changed_choose').val("NoSaveContinue");
		 if ($('#classModal_AlertSave').length > 0){
			 	$("#classModal_AlertSave").hide();
			 }
			 if ($('#classModal_AlertCancel').length > 0){
				 $("#classModal_AlertCancel").hide();
			 } 
	}
	
	function SaveCancel() {
		$('#tactics_page_changed_choose').val("SaveCancel");
		 if ($('#classModal_AlertSave').length > 0){
			 	$("#classModal_AlertSave").hide();
			 }
			 if ($('#classModal_AlertCancel').length > 0){
				 $("#classModal_AlertCancel").hide();
			 } 
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
        //先选出 textarea 和 统计字数 dom 节点
        var wordCount = $("#wordCount"),
            textArea = wordCount.find("textarea"),
            word = wordCount.find(".word");
        //调用
        statInputNum(textArea,word);

		isCreate = ${isCreate};
		validation();
		if (isCreate == false) {
			form_data_load.org_id = '${tactics.org_id}';
			form_data_load.creator_id = '${tactics.creator_id}';
			form_data_load.tacticsdata = '${tactics.tacticsdata}';
			form_data_load.imgName = '${tactics.imgName}';
			form_data_load.id = '${tactics.id}';
		} else {
			form_data_load.tacticsdata = "";
			form_data_load.id = 0;
		}

		$('#select_tacticstype').select2();
		$('#select_tacticstype').change(function() {
			com.setChanged();
		});

		$('#select_playgroundtype').select2();
		$('#select_playgroundtype').change(function() {
			var tmpval = JSON.parse($('#select_playgroundtype').val());
			com.setChanged();
			if (tmpval != null){
				changeTacticsBg(tmpval.abbr);
			}
		});

		$("#delete_btn").off("click");
		$("#delete_btn").on("click",function() {
			bootbox.dialog({
				  message: "您是否要删除该战术板？",
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
				    	  var submitdata = {"tacticsID" : "" + form_data_load.id};
							$.ajax({
								type : "post",
								url : "<%=serverUrl%>system_tool/deleteTactics",
								data : submitdata,
								dataType : "json",
				 				success : function(data) {
				 					if (data.status){
				 						if('${pFlag}'=='single'){
				 							//$("#tactics_panel").empty();
				 							$("#tactics_panel").loadAngular("<%=serverUrl%>system_tool/showTacticsListDetail?tactics_type_id=<%=single_tactics_type_id%>&pFlag=single");
				 						}else{
				 							$('#content').loadAngular("<%=serverUrl%>system_tool/showTacticsList");
				 						}
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

		$("#cancel_btn").off("click");
		$("#cancel_btn").on("click",function() {
			// for tactics need to 
			var tmp = $("#tactics_page_changed");
			if(tmp && tmp != null && $(tmp).val()=="true") {
				bootbox.dialog({
					  message: "战术板所做更改尚未保存，请选择",
					  title: "退出战术板",
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
					    	  saveTacticsToDatabase("doCancel('SaveContinue_success');", "alert('保存战术板信息失败');", null);
					      }
					    }
					  }
					});
			} else {
				doCancel("NoSaveContinue");
			}
 		});

 		$("#save_btn").off("click");
        $('#save_btn').click(function(){
        	saveTacticsToDatabase("alert('保存战术板成功');", "alert('保存战术板信息失败');");
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
	    playerObj.avatar_id = tAvatorId;
	    playerObj.jerseyid = tId;
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
	    com.childList.mypush(playerObj);
	    com.show();
	});
</script>

<script type="text/javascript">
	function changebg(item, strUrl) {
		document.getElementById(item).style.backgroundImage = "url('" + strUrl
				+ "')";
	}

	/* 	$(document).ready(function() {
	 var $div = $("div#computerMove");
	 $div.bind("mousedown", function(event) {

	 var offset_x = $(this)[0].offsetLeft;
	 var offset_y = $(this)[0].offsetTop;

	 var mouse_x = event.pageX;
	 var mouse_y = event.pageY;

	 $(document).bind("mousemove", function(ev) {
	 var _x = ev.pageX - mouse_x;
	 var _y = ev.pageY - mouse_y;
	 var now_x = (offset_x + _x) + "px";
	 var now_y = (offset_y + _y) + "px";
	 $div.css({
	 top : now_y,
	 left : now_x
	 });
	 });
	 });

	 $(document).bind("mouseup", function() {
	 $(this).unbind("mousemove");
	 });

	 }) */
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