<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="xss" uri="http://www.sportsdata.cn/xss"%>
<%@ page import="cn.sportsdata.webapp.youth.common.utils.CommonUtils" %>
<%@ page import="cn.sportsdata.webapp.youth.common.vo.EquipmentVO" %>

<%
	String serverUrl = CommonUtils.getServerUrl(request);

	boolean isCreate = (Boolean) request.getAttribute("isCreate");
	EquipmentVO vo = (EquipmentVO) request.getAttribute("equipment");
	String Id = isCreate ? "" : vo.getId();
%>

<div class="profileEditContainer">
	<div class="button_area" style="top: 90px;">
		<button id="save_btn" class="btn btn-primary" style="float: right; margin-left: 10px;">保存</button>
		<button id="cancle_btn" class="btn btn-default" style="float: right;">取消</button>
	</div>
	<div class="clearfix"></div>
	<div class="profileEditContent">
		<form id="equipment_form">
			<div role="tabpanel" class="tab-pane active" id="basic_tab">
				<sa-panel title="基本资料">
					<div class="row">
						<div class="col-md-2 text-center">
							<c:choose>
								<c:when test="${ !empty equipment.avatar }">
									<img id="user_avatar" class="profileEditAvatar custom-image" src="<%=serverUrl%>file/downloadFile?fileName=${ equipment.avatar }"></img>
								</c:when>
								<c:otherwise>
									<img id="user_avatar" class="profileEditAvatar default-image" src="<%=serverUrl%>resources/images/user_avatar.png"></img>
								</c:otherwise>
							</c:choose>
						</div>
						<div class="col-md-10">
							<div class="row">
								<div class="col-md-1 profileDetailItemTitle inputLabel">名称</div>
								<div class="col-md-4 profileEditValue">
									<c:choose>
										<c:when test="${isCreate}">
										<div class="form-group">
											<input data-bv-field="name" type="text" class="form-control" id="name" name="name" />
										</div>
										</c:when>
										<c:otherwise>
										<div class="form-group">
											<input data-bv-field="name" type="text" class="form-control" id="name" name="name" value="<xss:xssFilter text='${equipment.name }' filter='html'/>" />
										</div>
										</c:otherwise>
									</c:choose>
								</div>
							</div>	
							 <div class="row profileEditItemLine">
								<div class="col-md-1 profileDetailItemTitle inputLabel">描述</div>
								<div class="col-md-10 profileEditValue">
									<div class="form-group">
									<c:choose>
										<c:when test="${isCreate}">
											<textarea id="description" name="description" style="resize: none;" class="form-control utraining_input_text" data-bv-field="description" rows="3" style="line-height: 20px;"></textarea>
										</c:when>
										<c:otherwise>
											<textarea id="description" name="description" style="resize: none;" class="form-control utraining_input_text" data-bv-field="description" rows="3" style="line-height: 20px;"><xss:xssFilter text="${ equipment.description }" filter="html"/></textarea>
										</c:otherwise>
									</c:choose>
									</div>
								</div>
							</div>
						</div>
					</div>
				</sa-panel>
			</div>
		</form>
	</div>
</div>

<script type="text/javascript">
	$(function() {
		setTimeout(function() {
			initData();
			initEvent();
		}, 50);
	});
	var strurl="<%=serverUrl%>equipment/showEquipmentList";
	var msg="打开器材列表页面失败";
	function initData() {
		var isCreate = ${ isCreate };
		
		if(!isCreate) {
			strurl= "<%=serverUrl%>equipment/showEquipmentDetail?id=" + "${ equipment.id }";
			msg="打开器材详情页面失败";
		}
		if($('#user_avatar').is('.custom-image')) {
			$('#user_avatar').data('encryptFileName', '${ equipment.avatar }');
		}
	}
	
	function initEvent() {
		buildBreadcumb("新增/修改器材信息");
		var isCreate = ${ isCreate };
		validation();
		
		$('#save_btn').click(function() {
			$("#equipment_form").data('bootstrapValidator').validate();
			if(!$("#equipment_form").data('bootstrapValidator').isValid()){
				return;
			}
			
			var form_data = $('#equipment_form').serializeArray();
			var submitted_data = {'id': '<%=Id%>','name':$('#name').val(),'description':$('#description').val(), 'avatar': $('#user_avatar').data('encryptFileName') || ''};
			
			sa.ajax({
				type : "post",
				url : "<%=serverUrl%>equipment/saveEquipment",
				data: JSON.stringify(submitted_data),
				contentType: "application/json",
				success : function(data) {
					if(!data.status) {
						alert("提交器材信息异常");
						return;
					}
					
					sa.ajax({
						type : "get",
						url : strurl,
						success : function(data) {
							AngularHelper.Compile($('#content'), data);
						},
						error: function() {
							alert(strmsg);
						}
					});
				},
				error: function() {
					alert("提交器材信息失败");
				}
			});
		});
		
		$('#cancle_btn').click(function() {
			sa.ajax({
				type : "get",
				url : strurl,
				success : function(data) {
					AngularHelper.Compile($('#content'), data);
				},
				error: function() {
					alert(strmsg);
				}
			});
		});
		
		var options = {
			   "baseUrl": "<%=serverUrl%>",
               "defaultUserPhotoURL" : "resources/images/user_avatar.png",
               "dlgTitle" : "上传头像",
               "lnkUploadFileSelector" : "#user_avatar",
               "userPhotoSelector" : "#user_avatar"
		};
		ImageUploader.init(options);
	}
	
	function validation(){
		$("#equipment_form").bootstrapValidator({
	        message: '您输入的值不合法。',
	        feedbackIcons: {
	            valid: 'glyphicon glyphicon-ok',
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	        	name:{
	        		message: '名称不合法',
		           	validators: {
		                    notEmpty: {
		                        message: '名称是必填项，请正确填写名称。'
		                    },
		                    stringLength: {
		                         max: 30,
		                         message: '名称长度超过限制，请输入30字符以内的名称'
		                    }
		                }
	        	},
	        	description:{
	        		message: '描述不合法',
		           	 validators: {
		           		stringLength: {
		           			max: 1000,
		                    message: '描述信息长度超过限制，请输入1000字符以内的描述信息'
		                  }
		             }
	        	}
	        }
	    });
	}
</script>