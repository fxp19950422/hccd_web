<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="xss" uri="http://www.sportsdata.cn/xss"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%> 

<%@ page import="cn.sportsdata.webapp.youth.common.utils.CommonUtils" %>

<%
	String serverUrl = CommonUtils.getServerUrl(request);
%>


<div class="button_area">
	<button id="add_btn" class="btn btn-primary"  style="float: right;">交班</button>
</div>
<div class="clearfix" style="height:15px;"></div>
<div class=" doctorPatientList">
<c:forEach items="${doctors}" var="doctor">
	<sa-panel-card title="${doctor.name}">
		 <c:choose>
				<c:when test="${ fn:length(doctor.patients) == 0 }">
					暂无病人
				</c:when>
				<c:otherwise>
					 <c:forEach items="${doctor.patients}" var="patient">
						<div class="profileCard" uid="${ patient.patient_number }">
							<%--  <c:choose>
								<c:when test="${ !empty patient.name }">
									<img class="profileAvatar" src="<%=serverUrl%>file/downloadFile?fileName=${ coach.avatar }"></img>
								</c:when>
								<c:otherwise> --%>
									<img class="profileAvatar" src="<%=serverUrl%>resources/images/user_avatar.png"></img>
								<%-- </c:otherwise>
							</c:choose> --%>
							
							<div class="profileName"><xss:xssFilter text="${patient.real_name}" filter="html"/></div>
							<div class="profileData">
								<c:choose>
									<c:when test="${ !empty patient.phone_number }">
										${ patient.phone_number }
									</c:when>
									<c:otherwise>
										暂无联系方式
									</c:otherwise>
								</c:choose>
							</div>
						</div>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		
	</sa-panel-card>
	</c:forEach>
	</div>
	<style>
		.selected{
			border-style:solid;
  			border-color:#007EC1;
  			border-width:2px;
		}
	</style>
	<script type="text/javascript">
	$(function() {
		initEvent();
	});
	
	function initEvent() {
		
		$('div.doctorPatientList').on('click', 'div.profileCard', function() {
			var $dom = $(this);
			if ($dom.hasClass("selected")){
				$dom.removeClass("selected");
			} else {
				$dom.addClass("selected");
			}

		});
		
		$('#add_btn').click(function() {
			if ($("div.profileCard.selected").length <= 0){
				alert("请选择需要交班的病人");
			} else {
				var uidArray = new Array();
				$("div.profileCard.selected").each(function(){
					uidArray.push($(this).attr("uid"));
				})
				
				var postdata = {};
				postdata.uids=uidArray;
				
				sa.ajax({
					type : "post",
					url : "<%=serverUrl%>exchange/exchange_detail",
					data: JSON.stringify(postdata),
					contentType: "application/json",
					success : function(data) {
						//TODO: will update the container later
						AngularHelper.Compile($('#content'), data);
					},
					error: function() {
						alert("打开创建球员页面失败");
					}
				});
			}
		});
	}
</script>