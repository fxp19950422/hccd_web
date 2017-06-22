<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://www.sportsdata.cn/dateutil" prefix="DateUtil"%>

<%@ page import="cn.sportsdata.webapp.youth.common.utils.CommonUtils"%>
<%@ page import="cn.sportsdata.webapp.youth.common.vo.UserVO"%>
<%@ page import="cn.sportsdata.webapp.youth.common.utils.DateUtil"%>
<jsp:useBean id="now" class="java.util.Date" />
<%@ page import="java.util.Date"%>

<%@ page import="org.apache.log4j.Logger" %>  
<%@ page import="java.util.*,java.io.*,java.text.*,java.net.*" %> 

<%
	String serverUrl = CommonUtils.getServerUrl(request);
%>

<div class="profileEditContainer">
	<div class="coach_edit_button_area">
		<button id="add_medical_btn" class="btn btn-primary"
			style="float: right; margin-left: 10px;">新增门诊记录</button>

		<button id="cancle_btn" class="btn btn-default" style="float: right;margin-left: 10px;">返回</button>
	</div>
	<button class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal">上传图片</button>

	
	<div class="clearfix"></div>
	<div class="profileEditContent">
		<form id="player_form">
			<input type="hidden" value="${record.id }" id="recordId"
				name="recordId" readonly>
		</form>
		<div role="tabpanel" class="tab-pane active" id="basic_tab">
			<sa-panel title="病人信息">
			<div class="row">
				<div class="col-md-1 profileDetailItemTitle">姓名</div>
				<div class="col-md-3 profileDetailItemContent">${record.name}</div>
				<div class="col-md-1 profileDetailItemTitle">性别</div>
				<div class="col-md-3 profileDetailItemContent">${record.sex}</div>
				<div class="col-md-1 profileDetailItemTitle">年龄</div>
				<div class="col-md-3 profileDetailItemContent">${record.age}</div>
			</div>
			</sa-panel>
		</div>

	</div>

	<div>
		<div class="panel-heading"
			style="background-color: #067DC2; color: white;">
			<div style="text-align: center; font-size: 16px; color: #FFFFFF;">当日记录</div>
			<div class="clearfix"></div>
		</div>

		<table style="clear: both;" id="todaytable"
			data-classes="table table-no-bordered sprotsdatatable"
			data-toggle="table" data-striped="true">
			<thead>
				<tr>
					<th data-field="visitDate" data-formatter="dateFormatter"
						data-align="center">日期</th>
					<th data-field="recordType" data-formatter="typeFormatter"
						data-align="center">类型</th>
					<th data-align="center">项目</th>
					<th data-align="center">医生</th>
					<th data-field="id" data-formatter="actionFormatter"
						data-align="center">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${todayList}" var="record">
					<c:if test="${record.recordType == 'medical' }">
						<tr >
							<td>${record.medicalRecord.visitDate }</td>
							<td>${record.recordType }</td>
							<td>${record.medicalRecord.illnessDesc }</td>
							<td>${record.medicalRecord.doctor }
							<span  recordType="${record.recordType}" dataid="${record.medicalRecord.id }"></span></td>
							<td>${record.medicalRecord.id }</td>
						</tr>
					</c:if>
					<c:if test="${record.recordType == 'resident' }">
						<tr>
							<td>${record.residentRecord.admissionDate }</td>
							<td>${record.recordType }</td>
							<td>${record.residentRecord.inChiDiagnosis }</td>
							<td>${record.residentRecord.residentId }</td>
							<td>${record.residentRecord.id }</td>
						</tr>
					</c:if>
					<c:if test="${record.recordType == 'operation' }">
						<tr>
							<td>${record.operationRecord.operatingDate }</td>
							<td>${record.recordType }</td>
							<td>${record.operationRecord.operationDescription }</td>
							<td>${record.operationRecord.operator }</td>
							<td>${record.operationRecord.id }</td>
						</tr>
					</c:if>

				</c:forEach>
			</tbody>
		</table>
	</div>
	<div style="clear: both; height: 20px;"></div>
	<div>
		<div class="panel-heading"
			style="background-color: #067DC2; color: white">
			<div style="text-align: center; font-size: 16px; color: #FFFFFF;">历史记录</div>
			<div class="clearfix"></div>
		</div>
		<table style="clear: both" id="btable"
			data-classes="table table-no-bordered sprotsdatatable"
			data-toggle="table" data-striped="true" data-pagination="true"
			data-page-size="20" data-page-list="[7,10,15,20]"
			data-pagination-first-text="第一页" data-pagination-pre-text="上页"
			data-pagination-next-text="下页" data-pagination-last-text="最后页">
			<thead>
				<tr>
					<th data-field="visitDate" data-formatter="dateFormatter"
						data-align="center">日期</th>
					<th data-field="recordType" data-formatter="typeFormatter"
						data-align="center">类型</th>
					<th data-align="center">离本次门诊时间</th>
					<th data-align="center">医生</th>
					<th data-field="id" data-formatter="actionHistoryFormatter"
						data-align="center">操作</th>
				</tr>
			</thead>
			<tbody>
			
				<c:forEach items="${list}" var="record">
					<c:if test="${record.recordType == 'medical' }">
						<tr recordType="${record.recordType}" dateId="${record.medicalRecord.id }">
							<td>${record.medicalRecord.visitDate }</td>
							<td>${record.recordType }</td>
							<td>${DateUtil:differentDays(record.medicalRecord.visitDate, now) }天前</td>
							<td>${record.medicalRecord.doctor }</td>
							<td>${record.medicalRecord.id }</td>
						</tr>
					</c:if>
					<c:if test="${record.recordType == 'resident' }">
						<tr>
							<td>${record.residentRecord.admissionDate };${record.residentRecord.dischargeDateTime }</td>
							<td>${record.recordType }</td>
							<td>${DateUtil:differentDays(record.residentRecord.admissionDate, now) }天前</td>
							<td>${record.residentRecord.residentId }</td>
							<td>${record.residentRecord.id }</td>
						</tr>
					</c:if>
					<c:if test="${record.recordType == 'operation' }">
						<tr>
							<td>${record.operationRecord.operatingDate }</td>
							<td>${record.recordType }</td>
							<td>${DateUtil:differentDays(record.operationRecord.operatingDate, now) }天前</td>
							<td>${record.operationRecord.operator }</td>
							<td>${record.operationRecord.id }</td>
						</tr>
					</c:if>

				</c:forEach>
			</tbody>
		</table>
	</div>

	<form id="condition_form">
		<div class="row">
			<input type="hidden"
				class="form-control profileEditInput calendar-input"
				id="careTimeStart" name="careTimeStart"
				value="${condition.careTimeStart }">
		</div>
	</form>
</div>


<div class="modal fade" id="changeDateModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" style="z-index: 100001; display: none;">
	<div class="modal-dialog" role="document" style="width: 50%;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">图像上传</h4>
			</div>
			<span class="btn btn-success fileinput-button"> <i
			class="glyphicon glyphicon-plus"></i> <span>添加文件</span> <!-- The file input field used as target for the file upload widget -->
			<input id="fileupload" type="file" name="files[]" multiple>
		</span> <br> <br>
		<!-- The global progress bar -->
		<div id="progress" class="progress">
			<div class="progress-bar progress-bar-success"></div>
		</div>
		<!-- The container for the uploaded files -->
		<div id="files" class="files"></div>
		<br>
			<div class="modal-footer">
				<div id="template_cont"></div>
			</div>
		</div>
	</div>
</div>
<style>
pre, code {
	white-space: pre-line;
}
</style>
<script type="text/javascript">
	$(function() {
		setTimeout(function() {
			initData();
			initEvent();
			
			initUpload();
			
			
		}, 50);  // if using angular widget like sa-panel, since the real dom loaded is after the Angular.compile method, which is behind the document ready event, so add a little timeout to hack this
	});
	
	function initData() {
		buildBreadcumb("新增/修改诊断记录");
		$('.nav-pills a:first').focus();  // fix issues of first tab is not focused after loading
	}
	
	function initUpload(){
		var url = "<%=serverUrl%>/api/vi/fileUpload",
        uploadButton = $('<button/>')
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
							data.context = $('<div/>').appendTo('#files');
							$.each(data.files, function(index, file) {
								var node = $('<p/>').append(
										$('<span/>').text(file.name));
								if (!index) {
									node.append('<br>').append(
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
								node.prepend('<br>').prepend(file.preview);
							}
							if (file.error) {
								node.append('<br>').append(
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
							var progress = parseInt(data.loaded
									/ data.total * 100, 10);
							$('#progress .progress-bar').css('width',
									progress + '%');
						})
				.on(
						'fileuploaddone',
						function(e, data) {
							$
									.each(
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
															.wrap(link);
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
	}
	
	function actionFormatter(value, row, index){
		return  actionPhotoFormatter(value, row, index) +
		'<span onclick=handleNow("'+value+'","'+row.recordType+'") style="margin-left:10px;cursor:pointer" ><i class="glyphicon glyphicon-search content-color"></i></span>';
	}
	
	function actionPhotoFormatter(value, row, index){
		return '<span onclick=handlePhoto("'+value+'","'+row.recordType+'") style="margin-left:10px;cursor:pointer" ><i class="glyphicon glyphicon-camera content-color"></i></span>';
	}
	
	function actionHistoryFormatter(value, row, index){
		return  '<span onclick=handleHistory("'+value+'","'+row.recordType+'","'+monthFormatter(row.visitDate, row, index)+'") style="margin-left:10px;cursor:pointer" ><i class="glyphicon glyphicon-search content-color"></i></span>';
	}
	
	function handlePhoto(record_id,recordType){
		window.open("<%=serverUrl%>care/upload_photo?recordId="+record_id+"&recordType="+recordType);
	}
	
	function handleNow(recordId,recordType) {
		if(recordId==undefined){
			recordId=0;
		}
		var url ;
		if(recordType=='medical'){
			url ="<%=serverUrl%>care/care_edit?id=" + recordId+"&"+$("#condition_form").serialize();
		} else if(recordType=='operation'){
			url ="<%=serverUrl%>care/operation_detail?id=" + recordId+"&"+$("#condition_form").serialize();
		} else if(recordType=='resident'){
			url ="<%=serverUrl%>care/resident_detail?id=" + recordId+"&"+$("#condition_form").serialize();
		}
		sa.ajax({
			type : "get",
			url : url,
			data :{registId:$("#recordId").val()},
			success : function(data) {
				AngularHelper.Compile($('#content'), data);
			},
			error: function() {
				alert("页面打开失败");
			}
		});
	}
	
	function handleHistory(recordId,recordType,row) {
		if(recordType=='medical'){
			url ="<%=serverUrl%>care/care_edit?id=" + recordId+"&"+$("#condition_form").serialize();
			sa.ajax({
				type : "get",
				url : url,
				data :{registId:$("#recordId").val()},
				success : function(data) {
					AngularHelper.Compile($('#content'), data);
				},
				error: function() {
					alert("页面打开失败");
				}
			});
		}else {
			console.log(row)
			window.open("<%=serverUrl%>care/pat_history_document?recordId="+recordId+"&registId="+$("#recordId").val());
		}
		
	}
	
	function typeFormatter(value, row, index){
		if('medical' == value){
			return "门诊记录";
		} else if ('resident' == value) {
			return "住院病历资料";
		} else if ('operation' == value) {
			return '手术记录';
		}
	}
	
	
	function dateFormatter(value, row, index){
		if(value){
			if(value.indexOf(";")>0){
				var start = new Date(value.split(";")[0]).Format("yyyy年MM月dd日")
				var end ;
				if(value.split(";")[1]){
					 end = new Date(value.split(";")[1]).Format("yyyy年MM月dd日")
				} else {
					end = "~"
				}
				
				return start + " 至 " +end
			} else {
				return new Date(value).Format("yyyy年MM月dd日")
			}
			
		}
		return "-";
	}
	function monthFormatter(value, row, index){
		if(value){
			if(value.indexOf(";")>0){
				var start = new Date(value.split(";")[0]).Format("yyyy-MM")
				var end ;
				if(value.split(";")[1]){
					 end = new Date(value.split(";")[1]).Format("yyyy-MM")
					 return end;
				} else {
					return start;
				}
			} else {
				return ""
			}
			
		}
		return "";
	}
	
	function initEvent() {
		$('#cancle_btn').click(function(){
			if('${condition}'){
				$('#content').loadAngular("<%=serverUrl%>register/register_list?" + $("#condition_form").serialize() );
			} else {
				$('#content').loadAngular("<%=serverUrl%>register/register_list");
			}
		});
		$("#add_medical_btn").click(function(){
			$('#content').loadAngular("<%=serverUrl%>care/add_care?registId=${record.id }" );
			
		});
		$('#upload_btn').click(function(){
			$('#content').loadAngular("<%=serverUrl%>WEB-INF/jsp/user/load_test.jsp" );
		});
		
		$("#add_review_photo").click(function(){
			showUploadPhoto()
		})
		
		function showUploadPhoto(){
// 			$("#changeDateModal").modal({backdrop:false,show:true});
			window.open("<%=serverUrl%>care/upload_photo?registId="+$("#recordId").val());
		}

		$("#btable").bootstrapTable();
		var msg = '当日记录尚未同步完成，请稍后';
		if('${condition.careTimeStart}'){
			var conDate = '${condition.careTimeStart}';
			var date = new Date();
			var year = date.getFullYear();
			var month = date.getMonth()+1;
			var day = date.getDate();
			var array = conDate.split("-");
			if(parseInt(year)==parseInt(array[0])&&parseInt(month)==parseInt(array[1])&&parseInt(day)==parseInt(array[2])){
				//today
			} else {
				msg = '该日期为'+'${condition.careTimeStart}'+',记录已归档';
			}
		}
		$("#todaytable").bootstrapTable({
			formatNoMatches: function () {  //没有匹配的结果  
			    return msg;  
			  }
		});
		
		
		
		/* $("#todaytable tr").click(function(event){
			var td = event.target;
			var dataTd = $(td).parent().children()[3];
			var dataid = $(dataTd).find("span").attr("dataid");
			var datatype = $(dataTd).find("span").attr("recordtype")
			handleNow(dataid,datatype);
		}) */
		
<%-- 		$("#btable").bootstrapTable('refresh', {url: "<%=request.getContextPath()%>/register/register_detail_his_list?" --%>
// 									+ $("#player_form").serialize()
// 						})
//	};

	



</script>