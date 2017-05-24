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
  <title>Reveal.js 3 Slide Demo</title>
  
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
     
      <c:forEach items="${medicalrecords}" var="exchangeRecord">
      	<c:if test="${exchangeRecord.exchangeList != null && fn:length(exchangeRecord.exchangeList) > 0}">
      	<section>
			<H1>交班</H1>
	 	</section>
      	<c:forEach items="${exchangeRecord.exchangeList}" var="record">
      	<section style="top:25px">
      			<H2>基本资料</H2>
      			
      			<table>
      				<tbody>
      					<tr>
      						<td>
      						姓名
      						</td>
      						<td>
      							${ record.realName }
      						</td>
      					</tr>
      					<tr>
      						<td>
      						年龄
      						</td>
      						<td>
      							${ record.age }岁
      						</td>
      					</tr>
      					<tr>
      						<td>
      						入院时间
      						</td>
      						<td>
      							<fmt:formatDate value="${ record.admissionDateTime }" pattern="yyyy年MM月dd日" />
      						</td>
      					</tr>
      					<tr>
      						<td>
      						收治医师
      						</td>
      						<td>
      							${record.doctorInCharge}
      						</td>
      					</tr>
      					<tr>
      						<td>
      						住院号
      						</td>
      						<td>
      							${record.patientId}
      						</td>
      					</tr>
      				</tbody>
      			</table>
      		
      	</section>
      	<section>
      		<table>
      				<tbody>
      					<tr>
      						<td>
      						主诉
      						</td>
      						<td>
      							${record.opPrimary}
      						</td>
      					</tr>
      					<tr>
      						<td>
      						诊断
      						</td>
      						<td>
      							<p>
      							${record.diagnosis}
      							</p>
      							
      						</td>
      					</tr>
      					
      				</tbody>
      			</table>
      	</section>
      	
      	<c:forEach items="${record.operationRecords}" var="operation">
				 <c:if test="${operation.id != null}">
				 	<section>
				 	<%-- <sa-panel title="手术记录(<fmt:formatDate value="${ operation.operatingDate }" pattern="yyyy年MM月dd日 HH时" />)">
						<div class="row">
							<div class="col-md-1 profileDetailItemTitle">麻醉方法</div>
							<div class="col-md-11 profileDetailItemContent">${ operation.anaesthesiaMethod }</div>
						</div>
						<div class="row">
							<div class="col-md-1 profileDetailItemTitle">手术名称</div>
							<div class="col-md-11 profileDetailItemContent">${ operation.operationDesc }</div>
						</div>
						<div class="row">
							<div class="col-md-1 profileDetailItemTitle">术者</div>
							<div class="col-md-11 profileDetailItemContent">${operation.operator}</div>
						</div>
					</sa-panel> --%>
					<H2>手术情况<br/>(<fmt:formatDate value="${ operation.operatingDate }" pattern="yyyy年MM月dd日 HH时" />)</H2>
					<table>
      				<tbody>
      					<tr>
      						<td>
      						麻醉方法
      						</td>
      						<td>
      							${ operation.anaesthesiaMethod }
      						</td>
      					</tr>
      					<tr>
      						<td>
      						手术名称
      						</td>
      						<td>
      							<p>
      							${ operation.operationDesc }
      							</p>
      							
      						</td>
      					</tr>
      					<tr>
      						<td>
      						术者
      						</td>
      						<td>
      							<p>
      							${operation.operator}
      							</p>
      							
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
								<img class="starterAvator" src="<%=serverUrl%>file/asset?id=${asset.id}"></img>
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
      	<section style="top:25px">
      			<H2>基本资料</H2>
      			
      			<table>
      				<tbody>
      					<tr>
      						<td>
      						姓名
      						</td>
      						<td>
      							${ record.name }
      						</td>
      					</tr>
      					<tr>
      						<td>
      						年龄
      						</td>
      						<td>
      							<fmt:formatNumber value="${ record.age }" pattern="###"/>岁
      						</td>
      					</tr>
      					<tr>
      						<td>
      						收治医师
      						</td>
      						<td>
      							${record.doctorName}
      						</td>
      					</tr>
      					<tr>
      						<td>
      						住院号
      						</td>
      						<td>
      							${record.patientId}
      						</td>
      					</tr>
      				</tbody>
      			</table>
      		
      	</section>
		<c:forEach items="${record.residentAssetTypes}" var="assetType">
					<section>
							<H1>${assetType.assetTypeName}</H1>
						</section>
					<c:forEach items="${assetType.assets}" var="asset">
						<section>
							<img class="starterAvator" src="<%=serverUrl%>file/asset?id=${asset.id}"></img>
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
      	<section style="top:25px">
      			<H2>基本资料</H2>
      			
      			<table>
      				<tbody>
      					<tr>
      						<td>
      						姓名
      						</td>
      						<td>
      							${ record.realName }
      						</td>
      					</tr>
      					<tr>
      						<td>
      						年龄
      						</td>
      						<td>
      							<fmt:formatNumber value="${ record.age }" pattern="###"/>岁
      						</td>
      					</tr>
      					<tr>
      						<td>
      						入院时间
      						</td>
      						<td>
      							<fmt:formatDate value="${ record.admissionDateTime }" pattern="yyyy年MM月dd日" />
      						</td>
      					</tr>
      					<tr>
      						<td>
      						收治医师
      						</td>
      						<td>
      							${record.doctorInCharge}
      						</td>
      					</tr>
      					<tr>
      						<td>
      						住院号
      						</td>
      						<td>
      							${record.patientId}
      						</td>
      					</tr>
      				</tbody>
      			</table>
      	</section>
      	<section>
      		<table>
      				<tbody>
      					<tr>
      						<td>
      						主诉
      						</td>
      						<td>
      							${record.opPrimary}
      						</td>
      					</tr>
      					<tr>
      						<td>
      						诊断
      						</td>
      						<td>
      							<p>
      							${record.diagnosis}
      							</p>
      							
      						</td>
      					</tr>
      					
      				</tbody>
      			</table>
      	</section>
      	<section>
      		<table>
      				<tbody>
      					<tr>
      						<td>
      						专科检查
      						</td>
      						<td>
      							${record.bodyExam}
      						</td>
      					</tr>
      					<tr>
      						<td>
      						病史
      						</td>
      						<td>
      							<p>
      							${record.illHistory}
      							</p>
      							
      						</td>
      					</tr>
      					
      				</tbody>
      			</table>
      	</section>
      	

			<c:forEach items="${record.patientAssetTypes}" var="assetType">
						<section>
							<H1>${assetType.assetTypeName}</H1>
						</section>
						<c:forEach items="${assetType.assets}" var="asset">
							<section>
								<img class="starterAvator" src="<%=serverUrl%>file/asset?id=${asset.id}"></img>
							</section>
						</c:forEach>
			</c:forEach>
			<section>
					<H2>病例讨论</H2>
					<p>
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
<%-- <div class="flexslider">
  <ul class="slides">
    
    <c:forEach items="${medicalrecords}" var="record">
    	<li>
			<div class="profileDetailContent" style="margin-top: 15px;margin-left:30px;margin-right:30px">
				<div id="basic_panel">
					<sa-panel title="基本资料">
						<div class="row">
							<div class="col-md-1 profileDetailItemTitle">姓名</div>
							<div class="col-md-3 profileDetailItemContent">${ record.name }</div>
							<div class="col-md-1 profileDetailItemTitle">年龄</div>
							<div class="col-md-3 profileDetailItemContent"> 
							<fmt:formatNumber value="${ record.age }" pattern="###"/>岁
							</div>
							<div class="col-md-1 profileDetailItemTitle">入院时间</div>
							<div class="col-md-3 profileDetailItemContent"><fmt:formatDate value="${ record.admissionDate }" pattern="yyyy年MM月dd日" /></div>
							
						</div>
						
						<div class="row profileDetailItemLine">
							<div class="col-md-1 profileDetailItemTitle">收治医师</div>
							<div class="col-md-3 profileDetailItemContent">${record.doctorName}</div>
							<div class="col-md-1 profileDetailItemTitle">住院号</div>
							<div class="col-md-3 profileDetailItemContent">${record.admissionNumber}</div>
							<div class="col-md-1 profileDetailItemTitle"></div>
							<div class="col-md-3 profileDetailItemContent"></div>
						</div>
						
					</sa-panel>
					
				</div>
				
				<div id="basic_panel1">
					<sa-panel title="主诉">
						${record.diagnose}
					</sa-panel>
					
				</div>
				
				<div id="basic_panel2">
					<sa-panel title="诊断">
						<p>
							1. 刀割伤致左手疼痛，出血5.5小时<br/>
						<p>
					</sa-panel>
					
				</div>
				 <c:forEach items="${record.operationRecords}" var="operation">
				 <c:if test="${operation.id != null}">
				 	<sa-panel title="手术记录(<fmt:formatDate value="${ operation.operatingDate }" pattern="yyyy年MM月dd日 HH时" />)">
						<div class="row">
							<div class="col-md-1 profileDetailItemTitle">麻醉方法</div>
							<div class="col-md-11 profileDetailItemContent">${ operation.anaesthesiaMethod }</div>
						</div>
						<div class="row">
							<div class="col-md-1 profileDetailItemTitle">手术名称</div>
							<div class="col-md-11 profileDetailItemContent">${ operation.operationDesc }</div>
						</div>
						<div class="row">
							<div class="col-md-1 profileDetailItemTitle">术者</div>
							<div class="col-md-11 profileDetailItemContent">${operation.operator}</div>
						</div>
					</sa-panel>
					<c:forEach items="${operation.assetTypes}" var="assetType">
						<sa-panel title="${assetType.assetTypeName}">
							<c:forEach items="${assetType.assets}" var="asset">
								<div style="width:100%;float:left" >
									<img class="starterAvator" src="<%=serverUrl%>file/asset?id=${asset.id}"></img>
								</div>	
							</c:forEach>
						</sa-panel>
					</c:forEach>
					</c:if>
				 </c:forEach>
				
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
</div> --%>
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
			loop: true
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