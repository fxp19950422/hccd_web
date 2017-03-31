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
<div class="flexslider">
  <ul class="slides">
    
    <c:forEach items="${medicalrecords}" var="record">
    	<li>
			<div class="profileDetailContent" style="margin-top: 15px;margin-left:30px;margin-right:30px">
				<div id="basic_panel">
					<sa-panel title="基本资料">
						<div class="row">
							<div class="col-md-1 profileDetailItemTitle">姓名</div>
							<div class="col-md-3 profileDetailItemContent">${ record.patient_name }</div>
							<div class="col-md-1 profileDetailItemTitle">年龄</div>
							<div class="col-md-3 profileDetailItemContent">29</div>
							<div class="col-md-1 profileDetailItemTitle">入院时间</div>
							<div class="col-md-3 profileDetailItemContent">2010-11-29 00:00：00</div>
							
						</div>
						
						<div class="row profileDetailItemLine">
							<div class="col-md-1 profileDetailItemTitle">收治医师</div>
							<div class="col-md-3 profileDetailItemContent">叶建波</div>
							<div class="col-md-1 profileDetailItemTitle">住院号</div>
							<div class="col-md-3 profileDetailItemContent">XXXXXXXXX</div>
							<div class="col-md-1 profileDetailItemTitle"></div>
							<div class="col-md-3 profileDetailItemContent"></div>
						</div>
						
					</sa-panel>
					
				</div>
				
				<div id="basic_panel1">
					<sa-panel title="主诉">
						刀割伤致左手疼痛，出血5.5小时
					</sa-panel>
					
				</div>
				
				<div id="basic_panel2">
					<sa-panel title="诊断">
						<p>
							1. 刀割伤致左手疼痛，出血5.5小时<br/>
							1. 刀割伤致左手疼痛，出血5.5小时<br/>
							1. 刀割伤致左手疼痛，出血5.5小时<br/>
							1. 刀割伤致左手疼痛，出血5.5小时<br/>
							1. 刀割伤致左手疼痛，出血5.5小时<br/>
							1. 刀割伤致左手疼痛，出血5.5小时<br/>
							1. 刀割伤致左手疼痛，出血5.5小时
							1. 刀割伤致左手疼痛，出血5.5小时
							1. 刀割伤致左手疼痛，出血5.5小时
						<p>
					</sa-panel>
					
				</div>
				<div>
					<sa-panel title="术前照片">
						
					</sa-panel>
					
				</div>
				<div>
					<sa-panel title="术中照片">
						
					</sa-panel>
					
				</div>
				<div>
					<sa-panel title="术后照片">
						<div style="width:50%;float:left" >
							<img class="starterAvator"    src="<%=serverUrl%>resources/images/test.png"></img>
						</div>	
						<div style="width:50%;float:left">
							<img class="starterAvator"  src="<%=serverUrl%>resources/images/test.png"></img>
						</div>	
						<div style="width:50%;float:left">
							<img class="starterAvator"  src="<%=serverUrl%>resources/images/test.png"></img>
						</div>
						<div style="width:50%;float:left">
							<img class="starterAvator"  src="<%=serverUrl%>resources/images/test.png"></img>
						</div>
					</sa-panel>
					
				</div>
		</li>
		</c:forEach>

  </ul>
</div>
<<style>
<!--
.flex-viewport {
    max-height: 8000px;
    transition: all 1s ease 0s;
}
-->
</style>
<script type="text/javascript">
	$(function() {
		initEvent();
	});
	
	function initEvent() {
		 $('.flexslider').flexslider({
			    animation: "slide",
			    pausePlay: true,
			    smoothHeight:true
		 });
		 
		 
	}
</script>