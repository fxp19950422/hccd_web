<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="xss" uri="http://www.sportsdata.cn/xss"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page import="cn.sportsdata.webapp.youth.common.utils.CommonUtils" %>

<%
	String serverUrl = CommonUtils.getServerUrl(request);
%>
<!doctype html>
<html lang="en">   
<head>
  <meta charset="utf-8">
  <title>交班</title>
  
  <link rel="stylesheet" href="<%=serverUrl%>resources/css/reveal.css" />
  <link rel="stylesheet" href="<%=serverUrl%>resources/css/theme/white.css" />
<script src="<%=serverUrl%>resources/js/reveal.js" type="text/javascript"></script>
<script src="<%=serverUrl%>resources/js/jquery-1.11.1.min.js" type="text/javascript"></script>
</head>
<body>
<div class="reveal" >
    <!-- Wrap all slides in a single "slides" class -->
    <div class="slides">
      <!-- Each section element contains an individual slide -->
     <section>
			<H1>交班</H1>
	 	</section>
      <c:forEach items="${medicalrecords}" var="exchangeRecord">
      	<c:if test="${exchangeRecord.exchangeList != null && fn:length(exchangeRecord.exchangeList) > 0}">
      	<c:forEach items="${exchangeRecord.exchangeList}" var="record">
      	
      	<c:forEach items="${record.operationRecords}" var="operation">
				 <c:if test="${operation.id != null}">
					<c:forEach items="${operation.assetTypes}" var="assetType">
						<%-- <section>
							<span>${assetType.assetTypeName}</span>
						</section> --%>
						<c:forEach items="${assetType.assets}" var="asset">
							<section>
							<table>
      				<tbody>
      					<tr>
      						<td>
      							<span>${assetType.assetTypeName}</span>
      						</td>
      					</tr>
      					<tr>
      						<td>
      							<c:if test="${asset.storage_name eq 'oss'}">
									<img class="starterAvator" style="height:500px" src="http://hospital-image.oss-cn-shanghai.aliyuncs.com/${asset.id}"></img>
								</c:if>
								<c:if test="${asset.storage_name != 'oss'}">
									<img class="starterAvator" style="height:500px" src="<%=serverUrl%>file/asset?id=${asset.id}"></img>
								</c:if>
      						</td>
      					</tr>
      					</tbody>
      					</table>
								
							</section>
						</c:forEach>
					</c:forEach>
					</c:if>
				 </c:forEach>
      </c:forEach>
     </c:if>
      <c:if test="${exchangeRecord.residentList != null && fn:length(exchangeRecord.residentList) > 0}">
      <section>
		<H1>阅片</H1>
	  </section>
	
      <c:forEach items="${exchangeRecord.residentList}" var="record"> 
		<c:forEach items="${record.residentAssetTypes}" var="assetType">
					<%-- <section>
							<H1>${assetType.assetTypeName}</H1>
					</section> --%>
					<c:forEach items="${assetType.assets}" var="asset">
						<%-- <section>
							<img class="starterAvator" src="<%=serverUrl%>file/asset?id=${asset.id}"></img>
						</section> --%>
						
						<section>
							<table>
			      				<tbody>
			      					<tr>
			      						<td>
			      							<span>${assetType.assetTypeName}</span>
			      						</td>
			      					</tr>
			      					<tr>
			      						<td>
			      							<c:if test="${asset.storage_name eq 'oss'}">
												<img class="starterAvator" style="height:500px" src="http://hospital-image.oss-cn-shanghai.aliyuncs.com/${asset.id}"></img>
											</c:if>
											<c:if test="${asset.storage_name != 'oss'}">
												<img class="starterAvator" style="height:500px" src="<%=serverUrl%>file/asset?id=${asset.id}"></img>
											</c:if>
			      						</td>
			      					</tr>
			      					</tbody>
      						</table>
								
							</section>
					</c:forEach>
		</c:forEach>
      </c:forEach>
      </c:if>
       </c:forEach>
      <section>
			<H1>病例讨论</H1>
	  </section>
	  
	   <c:forEach items="${patient_in_hospital_records}" var="record">
			<c:forEach items="${record.patientAssetTypes}" var="assetType">
						<%-- <section>
							<H1>${assetType.assetTypeName}</H1>
						</section> --%>
						<c:forEach items="${assetType.assets}" var="asset">
							<%-- <section>
								<img class="starterAvator" src="<%=serverUrl%>file/asset?id=${asset.id}"></img>
							</section> --%>
							<section>
							<table>
			      				<tbody>
			      					<tr>
			      						<td>
			      							<span>${assetType.assetTypeName}</span>
			      						</td>
			      					</tr>
			      					<tr>
			      						<td>
			      							<c:if test="${asset.storage_name eq 'oss'}">
												<img class="starterAvator" style="height:500px" src="http://hospital-image.oss-cn-shanghai.aliyuncs.com/${asset.id}"></img>
											</c:if>
											<c:if test="${asset.storage_name != 'oss'}">
												<img class="starterAvator" style="height:500px" src="<%=serverUrl%>file/asset?id=${asset.id}"></img>
											</c:if>
			      						</td>
			      					</tr>
			      					</tbody>
      						</table>
								
							</section>
							
						</c:forEach>
			</c:forEach>
			<section>
					<H2>病例讨论</H2>
					<p style="font-size:0.7em">
      					${ record.recordDiscussion }
      				</p>
			</section>
      </c:forEach>
      
     <c:if test="${anotherOperation != null && fn:length(anotherOperation) > 0}">
      <section>
      	<H1>今日择期手术</H1>
      </section>
      <c:forEach items="${anotherOperation}" var="record">
      	<section>
      		<p style="font-size:30px;text-align:left">
      	<c:forEach items="${record}" var="subRecord">
      		${subRecord}<br/><br/>
      	</c:forEach>
      		</p>
      	</section>
      </c:forEach>
      </c:if>
    </div>
  </div>
  </body>
<style>
<!--
.flex-viewport {
    max-height: 8000px;
    transition: all 1s ease 0s;
}

.flexslider .slides > li {
height:auto !important;
}
-->
</style>
<script type="text/javascript">
	$(function() {
		initEvent();
	});
	
	function initEvent() {
		Reveal.initialize({
			autoSlide: 5000,
			mouseWheel: true,
			loop: true,
			minScale: 0.1,
			maxScale: 1.5
		  });
			/*  $('.flexslider').flexslider({
				    animation: "slide",
				    pausePlay: true,
				    smoothHeight:true
			 }); 
		setTimeout(function(){
			$('.flexslider').flexslider({
			    animation: "slide",
			    pausePlay: true,
			    smoothHeight:true
		 }); 
		}, 500) */
	}
</script>
</html>