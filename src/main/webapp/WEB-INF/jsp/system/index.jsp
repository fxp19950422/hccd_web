<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="cn.sportsdata.webapp.youth.common.utils.CommonUtils" %>
<%
	String homeBaseUrl = CommonUtils.getServerUrl(request);
%>
<!DOCTYPE html>
<html ng-app="soccerpro">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>医疗信息管理</title>
  
  <%@include file="/WEB-INF/jsp/common/include.jsp"%>
</head>
<body class="hold-transition skin-blue fixed sidebar-mini">
<div class="wrapper">

  <header class="main-header">
    <!-- Logo -->
    <a href="<%=homeBaseUrl%>system/system/index" class="logo" style="background-color: #067EC3;">
      <!-- mini logo for sidebar mini 50x50 pixels -->
      <span class="logo-mini"><img src="<%=homeBaseUrl%>resources/images/navigation_logo.png"></span>
      <!-- logo for regular state and mobile devices -->
      <span class="logo-lg">医疗信息管理</span>
    </a>
    <%@ include file="/WEB-INF/jsp/system/navbar.jsp"%>
  </header>
  <!-- Left side column. contains the logo and sidebar -->
  <aside class="main-sidebar">
    <!-- sidebar: style can be found in sidebar.less -->
    <%@ include file="/WEB-INF/jsp/system/menu.jsp"%>
    <!-- /.sidebar -->
  </aside>

  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
     <!--  <h1>
        <span>Dashboard</span>
        <small>Control panel</small>
      </h1> -->
      <!-- no need the breadcrumb, so comment out here -->
      
      <ol class="breadcrumb" >
        <li id="first"><a href="#">Home</a></li>
        <li id= "second"><span>Dashboard</span></li>
        <li id= "third" style="display:none"><span>Dashboard</span></li>
      </ol>
      
    </section>

    <!-- Main content -->
    <section class="content" id="content" style="padding-top:0px"></section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->
<!--   <footer class="main-footer"> -->
<!--     <div class="pull-right hidden-xs"> -->
<!--       <b>Version</b> 2.0 -->
<!--     </div> -->
<!--     <strong>Copyright &copy; 2015-2016 <a href="http://www.sportsdata.cn">Sports Data.cn</a>.</strong> 版权所有 -->
<!--  </footer> -->
  <!-- Control Sidebar -->
<%--    <%@ include file="/WEB-INF/jsp/system/page_setting.jsp"%> --%>
  <!-- /.control-sidebar -->
</div>

<script type="text/javascript">
	$(function() {
		var element = $('ul.sidebar-menu li:first');
		
		$("#first").find('a').text('首页');
		$("#first").find('a').css("color", "#067dc2");
		$("#second").hide();
		$("#third").hide();
		
		sa.ajax({
			type : "get",
			url : "<%=homeBaseUrl%>dashboard",
			success : function(data) {
				AngularHelper.Compile($('#content'), data);
			},
			error: function() {
				alert("打开首页失败");
			}
		});
	});
</script>
</body>
</html>
