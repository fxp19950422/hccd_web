<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<!-- meta -->
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="renderer" content="webkit">
<meta charset="UTF-8">
<title>机构选择</title>
<%@include file="/WEB-INF/jsp/common/include.jsp"%>
</head>
<body>
	<div style="height: 50px; width: 89px; margin: 0 auto;">
		<div class="logo"></div>
	</div>
	<div class="title">
		<span style="color: white; font-size: 30px"></span>
	</div>
	<div >
		<section class="content_box cleafix" style="width: 620px;margin:0 auto" id="secArea">
			<div>
				<div class="panel panel-default">
					<div class="panel-heading text-center"
						style="background-color: #0099FF; color: white">请选择您要登录的机构</div>
					<div class="panel-body table-responsive" style="padding: 0px 0px 15px 0px;">

						<c:forEach items="${orgs}" var="org">
							<div class="profileCard" orgId="${ org.id }">
								<img class="profileAvatar"
									src="<%=basePath%>resources/images/user_avatar.png"></img>

								<div class="profileName">${ org.name }</div>
								<div class="profileData">
								<c:choose>
										<c:when test="${ !empty org.name }">
											${ org.name }
										</c:when>
									<c:otherwise>
										暂无用户名
									</c:otherwise>
								</c:choose>
								</div>
							</div>
						</c:forEach>
					</div>
				</div>
			</div>
		</section>
	</div>
	<script>
	$(function(){
		$(".profileCard").on("click", function(){
			var $dom = $(this);
			location.href="<%=basePath%>auth/org_determined?orgID=" + $dom.attr('orgId');
		})
	})
	
	
	</script>
</body>
</html>

