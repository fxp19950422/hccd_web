<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
	+ request.getServerName() + (request.getServerPort() == 80 ? "":":" + request.getServerPort())
			+ path + "/";
	response.setHeader("Cache-Control", "no-store");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", 0);
%>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!--> <html lang="en" class="no-js"> <!--<![endif]-->
<meta charset="utf-8" />
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<meta content="" name="description" />
<meta content="" name="author" />
<!-- BEGIN GLOBAL MANDATORY STYLES -->
<link href="<%=basePath%>resources/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
<link href="<%=basePath%>resources/css/jquery.toolbar.css" rel="stylesheet" type="text/css"/>
<link href="<%=basePath%>resources/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
<link href="<%=basePath%>resources/css/error.css" rel="stylesheet" type="text/css"/>
<link href="<%=basePath%>resources/css/bootstrapValidator.css" rel="stylesheet" type="text/css"/>
<link href="<%=basePath%>resources/css/select2.min.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>resources/css/soccerpro.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>resources/css/bootstrap-datetimepicker.min.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>resources/css/bootstrap-datepicker3.min.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="<%=basePath%>resources/css/skins/_all-skins.css">
<link rel="stylesheet" href="<%=basePath%>resources/css/font-awesome.min.css">
<link rel="stylesheet" href="<%=basePath%>resources/css/AdminLTE.css">
<link rel="stylesheet" href="<%=basePath%>resources/css/jquery.fileupload.css" />
<link rel="stylesheet" href="<%=basePath%>resources/css/jquery.crop.css" />
<link rel="stylesheet" href="<%=basePath%>resources/css/simple-slider.css" />

<link rel="stylesheet" type="text/css" href="<%=basePath%>resources/css/style.css">
<link rel="shortcut icon" href="<%=basePath%>resources/media/image/favicon.ico" />
<link href="<%=basePath%>resources/css/bootstrap-table.min.css" rel="stylesheet">
<link href="<%=basePath%>resources/css/sportsdata.table.css" rel="stylesheet">
<link href="<%=basePath%>resources/css/sweet-alert.css" rel="stylesheet">
<link href="<%=basePath%>resources/css/flexslider.css" rel="stylesheet">
<!-- END FOOTER -->
	<script src="<%=basePath%>resources/js/jquery-1.11.1.min.js" type="text/javascript"></script>
	<script src="<%=basePath%>resources/js/load-image.all.min.js" type="text/javascript"></script>
	<script src="<%=basePath%>resources/js/base64.min.js" type="text/javascript"></script>
	<script src="<%=basePath%>resources/js/sweet-alert.min.js" type="text/javascript"></script>
	<script src="<%=basePath%>resources/js/jquery.toolbar.js" type="text/javascript"></script>
	<script src="<%=basePath%>resources/js/bootstrap.js" type="text/javascript"></script>
	<script src="<%=basePath%>resources/js/bootstrap-table.min.js"></script>
	<script src="<%=basePath%>resources/js/bootstrap-table-zh-CN.min.js"></script>
	<script src="<%=basePath%>resources/js/bootstrap-table-editable.min.js"></script>
	<script src="<%=basePath%>resources/js/bootstrap-editable.js"></script>
	<script src="<%=basePath%>resources/js/angular.js" type="text/javascript"></script>
	<script src="<%=basePath%>resources/js/angularhelper.js" type="text/javascript"></script>
	<script src="<%=basePath%>resources/js/bootstrapValidator.js" type="text/javascript"></script>
	<script src="<%=basePath%>resources/js/jquery.blockUI.js" type="text/javascript"></script>
	<script src="<%=basePath%>resources/js/lodash.min.js" type="text/javascript"></script>
	<script src="<%=basePath%>resources/js/select2.full.min.js" type="text/javascript"></script>	
	<script src="<%=basePath%>resources/js/common.js" type="text/javascript"></script>
	<script src="<%=basePath%>resources/js/bootstrap-datetimepicker.js" type="text/javascript"></script>
	<script src="<%=basePath%>resources/js/bootstrap-datepicker.js" type="text/javascript"></script>
	<script src="<%=basePath%>resources/js/bootstrap-datepicker.zh-CN.min.js" type="text/javascript"></script>
	<script src="<%=basePath%>resources/js/bootstrap-datetimepicker.zh-CN.js" type="text/javascript"></script>
	<script src="<%=basePath%>resources/js/app.js" type="text/javascript"></script>
	<script src="<%=basePath%>resources/js/bootbox.js" type="text/javascript"></script>	
	<script src="<%=basePath%>resources/js/plupload.full.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="<%=basePath%>resources/js/json2.js" /></script>
	<script type="text/javascript" src="<%=basePath%>resources/js/jquery.ui.widget.js" /></script>
	<script type="text/javascript" src="<%=basePath%>resources/js/jquery.iframe-transport.js" /></script>
	<script type="text/javascript" src="<%=basePath%>resources/js/jquery.fileupload.js" /></script>
	<script type="text/javascript" src="<%=basePath%>resources/js/jquery.crop.js" /></script>
	<script type="text/javascript" src="<%=basePath%>resources/js/simple-slider.js" /></script>
	<script type="text/javascript" src="<%=basePath%>resources/js/swfobject.js" /></script>
	<script type="text/javascript" src="<%=basePath%>resources/js/imageUploader.js" /></script>
	<script type="text/javascript" src="<%=basePath%>resources/js/echarts.min.js" /></script>
	<script type="text/javascript" src="<%=basePath%>resources/js/jquery.flexslider-min.js" /></script>
	<script src="<%=basePath%>resources/js/fileupload.js" type="text/javascript"></script>
	<script src="<%=basePath%>resources/js/jquery.PrintArea.js" type="text/javascript"></script>
	
		<script type="text/javascript"
		src="<%=basePath%>resources/js/jquery.fileupload-process.js"></script>
	<!-- The File Upload image preview & resize plugin -->
	<script type="text/javascript"
		src="<%=basePath%>resources/js/jquery.fileupload-image.js"></script>
	<!-- The File Upload audio preview plugin -->
	<script type="text/javascript"
		src="<%=basePath%>resources/js/jquery.fileupload-audio.js"></script>
	<!-- The File Upload video preview plugin -->
	<script type="text/javascript"
		src="<%=basePath%>resources/js/jquery.fileupload-video.js"></script>
	<!-- The File Upload validation plugin -->
	<script type="text/javascript"
		src="<%=basePath%>resources/js/jquery.fileupload-validate.js"></script>
	
	<!--[if lt IE 9]>
	<script src="<%=basePath%>resources/js/excanvas.min.js"></script>
	<script src="<%=basePath%>resources/js/respond.min.js"></script>  
	<![endif]--> 
	