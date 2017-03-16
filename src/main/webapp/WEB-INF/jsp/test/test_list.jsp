<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page import="cn.sportsdata.webapp.youth.common.utils.CommonUtils" %>

<%
	String serverUrl = CommonUtils.getServerUrl(request);
%>

<section class="top_control_area">
	<label class="radio-inline"> 
		<input type="radio" name="testViewMode" class="modelRadio" value="byBatch" <c:if test="${byBatch}">checked</c:if>> 按批次查看
	</label> 
	<label class="radio-inline"> 
		<input type="radio" name="testViewMode" class="modelRadio" value="byPlayper" <c:if test="${!byBatch}">checked</c:if>> 按人员查看
	</label> 
	<button id="add_btn" class="btn btn-primary test-btn-right">添加测试成绩</button>
	<div class="clearfix"></div>
</section>

<section class="profileListArea testBatchList hidden">
	<sa-panel title="测试管理">
		<c:if test="${empty renderData.testBatchList}">
			暂无测试信息
		</c:if> 
		<c:if test="${!empty renderData.testBatchList}">
			<c:forEach items="${renderData.testBatchList}" var="testBatch">
				<div class="testBatchCard" stid = "${testBatch.id}">
					<div class="testBatchCardItem testBatchItemName">
						<c:out value="${testBatch.test_item_title}"></c:out>
					</div>
					<div class="testBatchCardItem testBatchCategory">
						<c:out value="${testBatch.test_category_name}"></c:out>
					</div>
					<div class="testBatchCardItem testBatchTime">
						<fmt:formatDate value="${testBatch.test_time}" pattern="yyyy年MM月dd日"/>
					</div>
					<div class="testBatchCardItem testBatchCreator">
						<c:out value="${testBatch.creator_name}"></c:out>
					</div>
				</div>
			</c:forEach>
		</c:if>
	</sa-panel>
</section>
<section class="profileListArea testPlayerList hidden">
	<sa-panel-card title="前锋">
		<c:forEach items="${renderData.forwardList}" var="player">
			<div class="profileCard" uid="${ player.id }">
				<c:choose>
					<c:when test="${ !empty player.avatar }">
						<img class="profileAvatar" src="<%=serverUrl%>file/downloadFile?fileName=${ player.avatar }"></img>
					</c:when>
					<c:otherwise>
						<img class="profileAvatar" src="<%=serverUrl%>resources/images/user_avatar.png"></img>
					</c:otherwise>
				</c:choose>
				
				<div class="profileName">
					<c:choose>
						<c:when test="${ !empty player.name }">
							<c:out value="${ player.name }"></c:out>
						</c:when>
						<c:otherwise>
							暂无姓名
						</c:otherwise>
					</c:choose>
				</div>
				<div class="profileData">
					<c:choose>
						<c:when test="${ !empty player.tel }">
							${ player.tel }
						</c:when>
						<c:otherwise>
							暂无联系方式
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</c:forEach>
	</sa-panel-card>
	<sa-panel-card title="中场">
		<c:forEach items="${renderData.centerList}" var="player">
			<div class="profileCard" uid="${ player.id }">
				<c:choose>
					<c:when test="${ !empty player.avatar }">
						<img class="profileAvatar" src="<%=serverUrl%>file/downloadFile?fileName=${ player.avatar }"></img>
					</c:when>
					<c:otherwise>
						<img class="profileAvatar" src="<%=serverUrl%>resources/images/user_avatar.png"></img>
					</c:otherwise>
				</c:choose>
				
				<div class="profileName"><c:out value="${ player.name }"></c:out></div>
				<div class="profileData">
					<c:choose>
						<c:when test="${ !empty player.tel }">
							${ player.tel }
						</c:when>
						<c:otherwise>
							暂无联系方式
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</c:forEach>
	</sa-panel-card>
	<sa-panel-card title="后卫">
		<c:forEach items="${renderData.defenderList}" var="player">
			<div class="profileCard" uid="${ player.id }">
				<c:choose>
					<c:when test="${ !empty player.avatar }">
						<img class="profileAvatar" src="<%=serverUrl%>file/downloadFile?fileName=${ player.avatar }"></img>
					</c:when>
					<c:otherwise>
						<img class="profileAvatar" src="<%=serverUrl%>resources/images/user_avatar.png"></img>
					</c:otherwise>
				</c:choose>
				
				<div class="profileName"><c:out value="${ player.name }"></c:out></div>
				<div class="profileData">
					<c:choose>
						<c:when test="${ !empty player.tel }">
							${ player.tel }
						</c:when>
						<c:otherwise>
							暂无联系方式
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</c:forEach>
	</sa-panel-card>
	<sa-panel-card title="门将">
		<c:forEach items="${renderData.goalKeeperList}" var="player">
			<div class="profileCard" uid="${ player.id }">
				<c:choose>
					<c:when test="${ !empty player.avatar }">
						<img class="profileAvatar" src="<%=serverUrl%>file/downloadFile?fileName=${ player.avatar }"></img>
					</c:when>
					<c:otherwise>
						<img class="profileAvatar" src="<%=serverUrl%>resources/images/user_avatar.png"></img>
					</c:otherwise>
				</c:choose>
				
				<div class="profileName"><c:out value="${ player.name }"></c:out></div>
				<div class="profileData">
					<c:choose>
						<c:when test="${ !empty player.tel }">
							${ player.tel }
						</c:when>
						<c:otherwise>
							暂无联系方式
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</c:forEach>
	</sa-panel-card>
</section>

<script type="text/javascript">
	var UIController = (function(self){
		self.init = function(){
			this.dynamicRadioUIChange();
		};
		self.dynamicRadioUIChange = function(){
			var byOption = $('input.modelRadio:radio:checked').val();
			if("byBatch" == byOption){
				$("section.testPlayerList").addClass("hidden");
				$("section.testBatchList").removeClass("hidden");
			}else{
				$("section.testBatchList").addClass("hidden");
				$("section.testPlayerList").removeClass("hidden");
			}
		};
		return self;
	})(UIController || {});
	
	var EventController = (function(self){
		self.init = function(){
			buildBreadcumb();
			$('input.modelRadio:radio').change(function(){
				UIController.dynamicRadioUIChange();
			}); 
			$('section.testBatchList').on('click', 'div.testBatchCard', function() {
				EventController.toAddEditPage($(this).attr('stid'));
			});
			$('#add_btn').click(function(){
				EventController.toAddEditPage();
			});
			$('section.testPlayerList').on('click', 'div.profileCard', function() {
				sa.ajax({
					type : "get",
					url : "<%=serverUrl%>test/test_info_player_ui?userID=" + $(this).attr('uid'),
					success : function(data) {
						AngularHelper.Compile($('#content'), data);
					},
					error: function() {
						alert("获取球员测试数据失败");
					}
				});
			});
		};
		
		self.toAddEditPage = function(sid){
			var url = "<%=serverUrl%>test/test_addedit";
			if(!!sid){
				url = url + "?sid=" + sid;
			}
			sa.ajax({
				type : "get",
				url : url,
				success : function(data) {
					AngularHelper.Compile($('#content'), data);
				},
				error: function() {
					alert("编辑测试数据页面打开失败");
				}
			});
		}
		return self;
	})(EventController || {});

	$(function() {
		UIController.init();
		EventController.init();
	});
	
</script>