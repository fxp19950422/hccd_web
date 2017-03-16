<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="xss" uri="http://www.sportsdata.cn/xss"%>

<%@ page import="cn.sportsdata.webapp.youth.common.utils.CommonUtils"%>
<%@ page import="cn.sportsdata.webapp.youth.common.vo.TacticsVO"%>


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
.starterAvator {
    width: 100%;
    height: auto;
    float: left;
    border-radius: 3px;
    border: 1px solid #cecece;
}
</style>

<div class="clearfix"></div>
<input type="hidden" id="tactics_page_changed" value="false"></input>
<input type="hidden" id="tactics_page_changed_choose" value=""></input>
<div>
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
		<div class="panel panel-default">
			<div class="panel-heading text-left"
				style="background-color: #067DC2; color: white"><xss:xssFilter text="${ placeKick.name }" filter="html"/></div>
			<div class="panel-body table-responsive">
				<div>
					<div style="float: right; width: 260px;margin-left: 15px;">
						<div id="team_member" style="background-color: white; width: 100%">
							<div class="tab-content" style="width: 100%; padding: auto;">
								<div role="tabpanel" class="tab-pane active" id="basic_tab">
									<div class="panel panel-default">
										<div>
											<div>
												<div class="row" style="overflow:auto;height: 500px;margin-left:0px;margin-right:0px;">
													<div class="col-md-12" style="padding-left: 0px; padding-right: 0px;">
														<div class="row tacticsPlayerItemHeader text-center">
															<div class="col-md-4 tacticsPlayerCell">位置</div>
															<div class="col-md-4 tacticsPlayerCell">号码</div>
															<div class="col-md-4 tacticsPlayerCell">姓名</div>
														</div>
													</div>
													<div class="col-md-12" id="tablePlayerList" style="padding-left: 0px; padding-right: 0px;">
														<c:forEach items="${playerList}" var="player"
															varStatus="status">
															<div class="row tacticsPlayerItemLine text-center" 
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
																	</c:choose>>
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
					
					<div style="margin-right: 275px;">
						<img class="starterAvator" src="${ placeKick.imgUrl }"></img>
						<div class="col-md-1" style="margin-top: 15px;width: 80px;">阵型：</div>
						<div class="col-md-10">
							<div id="description" 
								name="description" style="margin-top: 15px;background-color: white;" disabled="disabled"><xss:xssFilter text="${placeKick.placeKickTypeName}" filter="html"/></div>
						</div>
						<div class="col-md-1" style="margin-top: 15px;width: 80px;">备注：</div>
						<div class="col-md-10">
							<div id="description" 
								name="description" style="margin-top: 15px;background-color: white;" disabled="disabled"><xss:xssFilter text="${placeKick.description}" filter="html"/></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- </sa-panel> -->
</div>


<div style="width: 95%; margin-left: 1%"></div>


<script type="text/javascript">
	$(function() {
		setTimeout(function() {
			initEvent();
		}, 50);
	});

	function doCancel() {
		sa.ajax({
			type : "get",
			url : "<%=serverUrl%>placeKick/showPlaceKickList",
			success : function(data) {
				//TODO: will update the container later
				AngularHelper.Compile($('#content'), data);
			},
			error: function() {
				alert("返回定位球列表页面失败");
			}
		});
	}
    
	function initEvent() {
		buildBreadcumb("定位球详情");
		$("#return_btn").off("click");
		$("#return_btn").on("click",function() {
			doCancel();
 		});

		$("#delete_btn").off("click");
		$("#delete_btn").on(
						"click",
						function() {
							
							bootbox.dialog({
								  message: "您是否要删除该定位球？",
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
								    	  var submitdata = {"tacticsID" : "" + "${tactics.id}"};
											$.ajax({
												type : "post",
												url : "<%=serverUrl%>placeKick/deletePlaceKick",
												data : submitdata,
												dataType : "json",
								 				success : function(data) {
								 					if (data.status){
								 						$('#content').loadAngular("<%=serverUrl%>placeKick/showPlaceKickList");
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
		
 		$("#copy_btn").off("click");
        $('#copy_btn').click(function(data) {
			sa.ajax({
				type : "get",
				url : "<%=serverUrl%>placeKick/copyPlaceKick?placeKickId=" + '${placeKick.id}',
				success : function(data) {
					//TODO: will update the container later
					AngularHelper.Compile($('#content'), data);
				},
				error: function() {
					alert("复制定位球页面失败");
				}
			});
		});
		
 		$("#edit_btn").off("click");
        $('#edit_btn').click(function(){
			var $dom = $(this);
			sa.ajax({
				type : "get",
				url : "<%=serverUrl%>placeKick/showPlaceKickEdit?placeKickId=" + '${placeKick.id}',
				success : function(data) {
					//TODO: will update the container later
					AngularHelper.Compile($('#content'), data);
				},
				error: function() {
					alert("打开战术板编辑页面失败");
				}
			});
        });
	}

</script>