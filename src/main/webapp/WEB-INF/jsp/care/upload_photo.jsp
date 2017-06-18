<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="xss" uri="http://www.sportsdata.cn/xss"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="cn.sportsdata.webapp.youth.common.utils.CommonUtils"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName()
			+ (request.getServerPort() == 80 ? "" : ":" + request.getServerPort()) + path + "/";
%>
<!--
/*
 * jQuery File Upload Plugin Basic Plus Demo
 * https://github.com/blueimp/jQuery-File-Upload
 *
 * Copyright 2013, Sebastian Tschan
 * https://blueimp.net
 *
 * Licensed under the MIT license:
 * https://opensource.org/licenses/MIT
 */
-->
<html lang="en">
<head>
<!-- Force latest IE rendering engine or ChromeFrame if installed -->
<!--[if IE]><meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"><![endif]-->
<meta charset="utf-8">
<title>照片，截图上传</title>
<!-- Bootstrap styles -->
<link href="<%=basePath%>resources/css/bootstrap.min.css"
	rel="stylesheet" type="text/css" />
<!-- Generic page styles -->
<!-- <link rel="stylesheet" href="css/style.css"> -->
<!-- CSS to style the file input field as button and adjust the Bootstrap progress bars -->
<link rel="stylesheet"
	href="<%=basePath%>resources/css/jquery.fileupload.css" />
	
	<script src="<%=basePath%>resources/js/jquery-1.11.1.min.js"
		type="text/javascript"></script>
		<script src="<%=basePath%>resources/js/load-image.all.min.js" type="text/javascript"></script>
	<!-- The jQuery UI widget factory, can be omitted if jQuery UI is already included -->
	<script type="text/javascript"
		src="<%=basePath%>resources/js/jquery.ui.widget.js" /></script>
	<script type="text/javascript"
		src="<%=basePath%>resources/js/jquery.iframe-transport.js" /></script>
	<script type="text/javascript"
		src="<%=basePath%>resources/js/jquery.fileupload.js" /></script>
	<!-- The Load Image plugin is included for the preview images and image resizing functionality -->
	
	<!-- The Canvas to Blob plugin is included for image resizing functionality -->
<!-- 		<script -->
<!-- 			src="//blueimp.github.io/JavaScript-Canvas-to-Blob/js/canvas-to-blob.min.js"></script> -->
	<!-- Bootstrap JS is not required, but included for the responsive demo navigation -->
	<script src="<%=basePath%>resources/js/bootstrap.js"
		type="text/javascript"></script>
	<!-- The Iframe Transport is required for browsers without support for XHR file uploads -->
	<!-- 	<script src="js/jquery.iframe-transport.js"></script> -->
	<!-- The basic File Upload plugin -->
	<!-- 	<script src="js/jquery.fileupload.js"></script> -->
	<!-- The File Upload processing plugin -->
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
</head>
<body>
	<div class="container">
	<input type="hidden" id="recordType" value="${recordType }">
	<input type="hidden" id="hospitalRecordId" value="${hospitalRecordId }">
	<input type="hidden" id="checked_value"/>
	<input type="hidden" id="checked_text"/>
	<input type="hidden" id="asset_stage_type_id"/>
	<input type="hidden" id="record_asset_type_id"/>
	
		<h1 id="p_title">照片，截图上传</h1>
		
		
			<blockquote>
			
				<div class="radio_medical">
					<p><input class="m_input" type="radio" name="med_usege" checked="checked" value="600:50"/><span>上传截图供门诊复查时查看</span></p>
				</div>
				<div class="radio_operation">
					<p><input class="m_input" type="radio" name="op_usege" checked="checked" value="99:52"/><span>上传交班用的手术同意书</span></p>
					<p><input class="m_input" type="radio" name="op_usege" checked="unchecked" value="50:52"/><span>上传门诊复查用的手术记录</span></p>
					<p><input class="m_input" type="radio" name="op_usege" checked="unchecked" value="50:105"/><span>上传门诊复查用的检验检查影像</span></p>
				</div>
				<div class="radio_resident">
					<p><input class="m_input" type="radio" name="re_usege" checked="checked" value="500:53"/><span>上传交班用的手术同意书</span></p>
					<p><input class="m_input" type="radio" name="re_usege" checked="unchecked" value="51:53"/><span>上传门诊复查用的出院记录</span></p>
				</div>
				<div class="radio_patientInhospital">
					<p><input class="m_input" type="radio" name="in_usege" checked="checked" value="700:54"/><span>上传交班用的手术记录</span></p>
				</div>
				
			</blockquote>
			
			<span class="btn btn-success fileinput-button" > <i
				class="glyphicon glyphicon-plus"></i> <span>添加文件</span> 
				<input id="fileupload" type="file" name="files" multiple>
			</span>
			<div style="clear:both;margin-top:10px;"></div>
			<div id="progress" class="progress">
				<div class="progress-bar progress-bar-success"></div>
			</div>
			<div id="files" class="files"></div>
			<br>
		
	</div>
	
	
	<script>
		/*jslint unparam: true, regexp: true */
		/*global window, $ */
		$(function() {
			'use strict';
			// Change this to the location of your server-side upload handler:
			var url = "<%=basePath%>/care/fileUpload",
	        uploadButton = $('<button style="float:right"/>')
	            .addClass('btn btn-primary')
	            .prop('disabled', true)
	            .text('Processing...')
	            .on('click', function () {
	                var $this = $(this),
	                    data = $this.data();
	                $this
	                    .off('click')
	                    .text('取消')
	                    .on('click', function () {
	                        $this.remove();
	                        data.abort();
	                    });
	                data.submit().always(function () {
	                    $this.remove();
	                });
	            });
			$('#fileupload')
					.fileupload(
							{
								url : url,
								dataType : 'json',
								autoUpload : false,
								formData:{asset_stage_type_id:$("#asset_stage_type_id").val(),record_asset_type_id:$("#record_asset_type_id").val(),recordType:$("#recordType").val(),hospitalRecordId:$("#hospitalRecordId").val()},
								acceptFileTypes : /(\.|\/)(gif|jpe?g|png)$/i,
								// Enable image resizing, except for Android and Opera,
								// which actually support image resizing, but fail to
								// send Blob objects via XHR requests:
								disableImageResize : /Android(?!.*Chrome)|Opera/
										.test(window.navigator.userAgent),
								previewMaxWidth : 100,
								previewMaxHeight : 100,
								previewCrop : true
							})
					.on(
							'fileuploadadd',
							function(e, data) {
								data.context = $('<div style="clear:both"/>').appendTo('#files');
								$.each(data.files, function(index, file) {
									var node = $('<p style="height:100px;line-height:100px;"/>').append(
											$('<span style="height:100px;line-height:100px;float:left;margin-left:20px;"/>').text(file.name));
									console.log(node)
									var desc = $('<span style="margin-left:20px;"/>').append($("#checked_text").val())
									node.append(desc)
									if (!index) {
										node.append(
												uploadButton.clone(true).data(
														data));
									}
									node.appendTo(data.context);
								});
							})
					.on(
							'fileuploadprocessalways',
							function(e, data) {
								var index = data.index, file = data.files[index], node = $(data.context
										.children()[index]);
								if (file.preview) {
									$(file.preview).css("float","left")
									node.prepend(file.preview);
								}
								if (file.error) {
									node.append(
											$('<span class="text-danger"/>')
													.text(file.error));
								}
								if (index + 1 === data.files.length) {
									data.context.find('button').text('上传')
											.prop('disabled',
													!!data.files.error);
								}
							})
					.on(
							'fileuploadprogressall',
							function(e, data) {
// 								var progress = parseInt(data.loaded
// 										/ data.total * 100, 10);
// 								$('#progress .progress-bar').css('width',
// 										progress + '%');
							})
					.on(
							'fileuploaddone',
							function(e, data) {
								if(!data.result.files){
									var error = $(
									'<span class="text-danger"/>')
									.text(
											"上传失败");
							$(
									data.context
											.children()[index])
									.append('<br>')
									.append(error);
									return 
								}
								$.each(
												data.result.files,
												function(index, file) {
													if (file.url) {
														var link = $('<a>')
																.attr('target',
																		'_blank')
																.prop(
																		'href',
																		file.url);
														$(
																data.context
																		.children()[index])
																.append("<span style='margin-left:20px;'>上传成功</span>");
													} else if (file.error) {
														var error = $(
																'<span class="text-danger"/>')
																.text(
																		file.error);
														$(
																data.context
																		.children()[index])
																.append('<br>')
																.append(error);
													}
												});
							})
					.on(
							'fileuploadfail',
							function(e, data) {
								$
										.each(
												data.files,
												function(index) {
													var error = $(
															'<span class="text-danger"/>')
															.text(
																	'文件上传失败.');
													$(
															data.context
																	.children()[index])
															.append('<br>')
															.append(error);
												});
							}).prop('disabled', !$.support.fileInput).parent()
					.addClass($.support.fileInput ? undefined : 'disabled');
			
			initData();
		});
	function initData(){
		var type = '${recordType}';
		var pref = ""
		$(".radio_medical").css("display","none");
		$(".radio_operation").css("display","none");
		$(".radio_resident").css("display","none");
		$(".radio_patientInhospital").css("display","none");
		if(type === 'medical'){
			pref = "门诊记录"
			$(".radio_medical").css("display","inline-block");
		} else if(type === 'operation'){
			pref = "手术记录"
			$(".radio_operation").css("display","inline-block");
		} else if(type === 'resident'){
			pref = "出院记录"
			$(".radio_resident").css("display","inline-block");
		} else if(type === 'patientInhospital') {
			pref = "入院记录"
			$(".radio_patientInhospital").css("display","inline-block");
		}
		$("#p_title").html(pref + "照片，截图上传");
		
		
		$(".m_input").bind("click",function(){ 
			var value = $(this).val();
			if(value){
				var values = value.split(":");
				$("#asset_stage_type_id").val(values[0]);
				$("#record_asset_type_id").val(values[1]);
			}
			console.log($("#asset_stage_type_id").val())
			console.log($("#record_asset_type_id").val())
			$("#checked_text").val($(this).parent().find('span').html())
		});
	}
	</script>
</body>
</html>





