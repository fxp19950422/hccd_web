<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="xss" uri="http://www.sportsdata.cn/xss"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ page import="cn.sportsdata.webapp.youth.common.utils.CommonUtils"%>

<%
	String serverUrl = CommonUtils.getServerUrl(request);
%>
<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">
<title></title>

<link rel="stylesheet" href="<%=serverUrl%>resources/css/reveal.css" />
<link rel="stylesheet" href="<%=serverUrl%>resources/js/plugin/toc-progress/toc-progress.css" />
<link rel="stylesheet"
	href="<%=serverUrl%>resources/css/theme/white.css" />
<script src="<%=serverUrl%>resources/js/reveal.js"
	type="text/javascript"></script>
<script src="<%=serverUrl%>resources/js/head.min.js"
	type="text/javascript"></script>
<script src="<%=serverUrl%>resources/js/jquery-1.11.1.min.js"
	type="text/javascript"></script>
</head>

<style>
.scrollable {
    overflow-y: auto  !important;
    overflow-x: hidden !important;
    padding-top:0px;
    height: 680px;
}
</style>
<body>
	<div class="reveal">
		<!-- Wrap all slides in a single "slides" class -->
		<div class="slides">
			<!-- Each section element contains an individual slide -->
<%-- 			<c:if test="${medicalrecords != null&& fn:length(medicalrecords)>0}"> --%>
<!-- 				<section> -->
<!-- 					<H1>门诊记录</H1> -->
<!-- 				</section> -->
<%-- 				<c:forEach items="${medicalrecords}" var="record"> --%>
<!-- 					<section> -->
<!-- 						<table> -->
<!-- 							<tbody> -->
<!-- 								<tr> -->
<!-- 									<td>主诉</td> -->
<%-- 									<td>${record.illnessDesc}</td> --%>
<!-- 								</tr> -->
<!-- 								<tr> -->
<!-- 									<td>病史</td> -->
<%-- 									<td>${record.medHistory}</td> --%>
<!-- 								</tr> -->
<!-- 								<tr> -->
<!-- 									<td>专科查体</td> -->
<%-- 									<td>${record.bodyExam}</td> --%>
<!-- 								</tr> -->
<!-- 								<tr> -->
<!-- 									<td>辅助检查</td> -->
<%-- 									<td>${record.accExam}</td> --%>
<!-- 								</tr> -->
<!-- 								<tr> -->
<!-- 									<td>诊断</td> -->
<%-- 									<td>${record.diagDesc}</td> --%>
<!-- 								</tr> -->
<!-- 								<tr> -->
<!-- 									<td>建议</td> -->
<%-- 									<td>${record.suggestion}</td> --%>
<!-- 								</tr> -->
<!-- 							</tbody> -->
<!-- 						</table> -->
<!-- 					</section> -->


<%-- 				</c:forEach> --%>
<%-- 			</c:if> --%>
			<c:if
				test="${residentrecords != null&& fn:length(residentrecords)>0}">
				<section>
					<H1>住院病历资料</H1>
				</section>

				<c:forEach items="${residentrecords}" var="record">
					<section>
						<table>
							<tbody>
								<tr>
									<td>入院情况</td>
									<td>${record.inState}</td>
								</tr>
								<tr>
									<td>入院中医诊断</td>
									<td>${record.inChiDiagnosis}</td>
								</tr>
								<tr>
									<td>入院西医诊断</td>
									<td>${record.inWesDiagnosis}</td>
								</tr>
								<tr>
									<td>诊疗经过</td>
									<td>${record.process}</td>
								</tr>
								<tr>
									<td>出院中医诊断</td>
									<td>${record.outChiDiagnosis}</td>
								</tr>
								<tr>
									<td>出院西医诊断</td>
									<td>${record.outWesDiagnosis}</td>
								</tr>
								<tr>
									<td>出院情况</td>
									<td>${record.outState}</td>
								</tr>
								<tr>
									<td>出院医嘱</td>
									<td>${record.suggestion}</td>
								</tr>

							</tbody>
						</table>
					</section>
				</c:forEach>
			</c:if>
			<c:if
				test="${operationRecords != null && fn:length(operationRecords)>0}">
				<section>
					<H1>手术记录</H1>
				</section>

				<c:forEach items="${operationRecords}" var="operation">
					<c:if test="${operation.id != null}">
						<section>
							<H2>
								手术情况<br />(
								<fmt:formatDate value="${ operation.operatingDate }"
									pattern="yyyy年MM月dd日 HH时" />
								)
							</H2>
							<table>
								<tbody>
									<tr>
										<td>麻醉方法</td>
										<td>${ operation.anaesthesiaMethod }</td>
									</tr>
									<tr>
										<td>手术名称</td>
										<td>
											<p>${ operation.operationDesc }</p>

										</td>
									</tr>
									<tr>
										<td>术者</td>
										<td>
											<p>${operation.operatorName}</p>

										</td>
									</tr>
								</tbody>
							</table>

						</section>
						<c:forEach items="${operation.assetTypes}" var="assetType">
							<section>
								<H1>${assetType.assetTypeName}</H1>
							</section>
							<c:forEach items="${assetType.assets}" var="asset">
								<section>
									<img class="starterAvator"
										src="<%=serverUrl%>file/asset?id=${asset.id}"></img>
								</section>
							</c:forEach>
						</c:forEach>
					</c:if>
				</c:forEach>
			</c:if>
			<c:forEach items="${docList}" var="doc">
				<section class="scrollable">
					<H6>  </H6>
					<img class="docImg" 
						src="<%=serverUrl%>file/downloadFile?fileName=${doc.filePath}"></img>
				</section>
			</c:forEach>
		</div>
	</div>
</body>
<style>
<!--
.flex-viewport {
	max-height: 8000px;
	transition: all 1s ease 0s;
}

.flexslider .slides>li {
	height: auto !important;
}
-->
</style>
<script type="text/javascript">
	$(function() {
		initEvent();
	});

	function initEvent() {
		Reveal.initialize({
			autoSlide : 5000,
			mouseWheel : true,
			loop : true,
// 			touch:false,
			dependencies : [

			// TOC-Progress plugin
			{
				src : '<%=serverUrl%>resources/js/plugin/toc-progress/toc-progress.js',
				async : true,
				callback : function() {
					toc_progress.initialize();
					toc_progress.create();
				}
			} ]
		});

// 		var he = document.body.clientHeight - 150;
// 		console.log(he);
// 		$(".docImg").css("height", he);

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