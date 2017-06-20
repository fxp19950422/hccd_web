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
	<script src="<%=serverUrl%>resources/js/bootstrap.js" type="text/javascript"></script>
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
				<section>
					<H1>住院病历资料</H1>
				</section>
			<c:forEach items="${docList}" var="doc">
				<section class="scrollable">
					<H6>  </H6>
						<c:if test="${doc.storage_name eq 'oss'}">
							<img class="docImg" onclick="showBigPic(this);" src="http://hospital-image.oss-cn-shanghai.aliyuncs.com/${doc.filePath}"></img>
						</c:if>
						<c:if test="${asset.storage_name != 'oss'}">
							<img class="docImg"  onclick="showBigPic(this);"
								src="<%=serverUrl%>file/downloadFile?fileName=${doc.filePath}"></img>
						</c:if>
				</section>
			</c:forEach>
		</div>
	</div>
	
	
	<div class="modal fade" id="imgClassModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" style="z-index: 100001; display: none;padding-right:5px;padding-left:5px;">
	<div class="modal-dialog" role="document" style="width: 100%;">
			<button type="button" class="close" data-dismiss="modal"
				style="border: 1px solid #000;background-color:#000;  opacity:0.5; -moz-border-radius: 20px; -webkit-border-radius: 20px; border-radius: 20px; z-index: 100011; width: 40px; height: 40px; position: fixed; right: 20px; top: 20px;"
				aria-label="Close">
				<span aria-hidden="true" style="color: #fff;">&times;</span>
			</button>

			<div class="modal-body" id="class_main_body" style="padding: 0px;overflow: auto;height: 700px;">
				<img id="image_big" style="height: auto; width: 100%;" />
			</div>
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
	
	function showBigPic(obj) {
		var url = obj.getAttribute("src");
		if(url){
			console.log(Reveal.getConfig())
			Reveal.getConfig().mouseWheel=false;
			$("#image_big").attr('src', url);
			$("#imgClassModal").modal('show');
		}
	}

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