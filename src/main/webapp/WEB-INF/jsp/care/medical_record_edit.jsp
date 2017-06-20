<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ page import="cn.sportsdata.webapp.youth.common.utils.CommonUtils"%>
<%@ page import="cn.sportsdata.webapp.youth.common.vo.UserVO"%>
<%@ page import="java.util.Date" %>

<%
	String serverUrl = CommonUtils.getServerUrl(request);
%>

<div class="profileEditContainer">
	<div class="coach_edit_button_area">
		<button id="save_btn" class="btn btn-primary"
			style="float: right; margin-left: 10px;">保存并打印</button>
		<button id="cancle_btn" class="btn btn-default" style="float: right;">取消</button>
	</div>
	<div class="clearfix"></div>
	<div class="profileEditContent">
		<form id="player_form">
			<div role="tabpanel" class="tab-pane active" id="basic_tab">
				<sa-panel title="病人信息">
				<div class="row">
					<div class="col-md-1 profileDetailItemTitle">姓名</div>
					<div class="col-md-3 profileDetailItemContent">${record.realName}</div>
					<div class="col-md-1 profileDetailItemTitle">诊断医生</div>
					<div class="col-md-3 profileDetailItemContent">${record.name}</div>
					<div class="col-md-1 profileDetailItemTitle">诊断时间</div>
					<div class="col-md-3 profileDetailItemContent"><fmt:formatDate pattern="yyyy-MM-dd"
							value="${record.visitDate}" /></div>
				</div>
				</sa-panel>
				<div class="row">
					<div class="col-md-4">
					<sa-panel title="主诉" linkText="模板" link-callback="getTemplate('illnessDesc')"> <textarea id="illnessDesc"
						name="illnessDesc" class="form-control" rows="5"
						placeholder="不超过30字" value="${record.illnessDesc}">${record.illnessDesc}</textarea>
					</sa-panel>
					</div>
					<div class="col-md-4">
					<sa-panel title="病史" linkText="模板" link-callback="getTemplate('medHistory')"> <textarea id="medHistory"
						name="medHistory" class="form-control" rows="5"
						placeholder="不超过800字" value="${record.medHistory}">${record.medHistory}</textarea>
					</sa-panel>
					</div>
					<div class="col-md-4">
					<sa-panel title="专科查体" linkText="模板" link-callback="getTemplate('bodyExam')"> <textarea id="bodyExam"
						name="bodyExam" class="form-control" rows="5"
						placeholder="不超过800字" value="${record.bodyExam}">${record.bodyExam}</textarea>
					</sa-panel>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<sa-panel title="辅助检查" linkText="模板"
							link-callback="getTemplate('accExam')"> <textarea
							id="accExam" name="accExam" class="form-control" rows="5"
							placeholder="不超过800字" value="${record.accExam}">${record.accExam}</textarea> </sa-panel>
					</div>
					<div class="col-md-4">
						<sa-panel title="诊断"  linkText="模板" link-callback="getTemplate('diagDesc')"> <textarea id="diagDesc"
							name="diagDesc" class="form-control" rows="5" placeholder="不超过800字"
							value="${record.diagDesc}">${record.diagDesc}</textarea> </sa-panel>
					</div>
					<div class="col-md-4">
						<sa-panel title="建议"  linkText="模板" link-callback="getTemplate('suggestion')"> <textarea id="suggestion"
							name="suggestion" class="form-control" rows="5"
							placeholder="不超过800字" value="${record.suggestion}">${record.suggestion}</textarea>
						</sa-panel>
					</div>
				</div>
				<c:forEach items="${assets}" var="asset">
						<sa-panel title="${asset.assetTypeName}">
								<c:if test="${asset.storage_name eq 'oss'}">
									<img class="starterAvator" style="width: 100%" src="http://hospital-image.oss-cn-shanghai.aliyuncs.com/${asset.id}"></img>
								</c:if>
								<c:if test="${asset.storage_name != 'oss'}">
									<img class="starterAvator" style="width: 100%" src="<%=serverUrl%>file/asset?id=${asset.id}"></img>
								</c:if>
						</sa-panel>
					
				</c:forEach>
				<input type="hidden" name="id" value="${id}" />
			</div>
		</form>
	</div>
	
	<div id="form_print"  style="display:none;">
		<div style="text-align:center"><H3>安徽省中西医结合医院</H3></div>		
		<div style="text-align:center"><H3>门诊病历</H3></div>
		<div style="font-size:0.7em;margin-top:10px;">
			<div style="width:3%;background-color:red;float:left">&nbsp</div>
			<table style="width:94%;float:left">
				<tr>
					<td width="33%">姓名: ${record.realName}</td>
					
					<td  width="33%">性别: 
					<c:if test="${record.gender=='female'}">女</c:if>
					<c:if test="${record.gender=='male'}">男</c:if>
					</td>
					
					<td  width="33%">年龄: ${age}岁</td>
				</tr>
				<tr>
					<td width="33%">号别: 创伤外科</td>
					
					<td width="33%">就诊日期: <fmt:formatDate pattern="yyyy-MM-dd"
							value="${record.visitDate}" /></td>
					
					<td width="33%">门诊次数: 1</td>
					
				</tr>
			</table>
			<div style="width:3%;background-color:red;float:left"></div>
			<div style="clear:both;"></div>
			</div>
			<div style="border:0.5px solid #000; width:95%;margin:0 auto;margin-top:5px;"></div>
			<div style="width:95%;margin:0 auto;margin-top:10px;">
				主诉:<span id="spnIllnessDesc"></span>
			</div>
			<div style="border:0.5px dashed #000; width:95%;margin:0 auto;margin-top:10px;"></div>
			
			<div style="width:95%;margin:0 auto;margin-top:10px;">
				病史:<span id="spnMedHistory"></span>
			</div>
			<div style="margin-top:10px;border:0.5px dashed #000; width:95%;margin:0 auto;margin-top:10px;"></div>
			
			<div style="margin-top:10px;width:95%;margin:0 auto;margin-top:10px;">
				专科查体:<span id="spnBodyExam"></span>
			</div>
			<div style="margin-top:10px;border:0.5px dashed #000; width:95%;margin:0 auto;margin-top:10px;"></div>
			<div style="margin-top:10px;width:95%;margin:0 auto;margin-top:10px;">
				诊断:<span id="spnDiagDesc"></span>
			</div>
			<div style="margin-top:10px;border:0.5px dashed #000; width:95%;margin:0 auto;margin-top:10px;"></div>
			<div style="margin-top:10px;width:95%;margin:0 auto;margin-top:10px;">
				辅助检查:<span id="spnAccExam"></span>
			</div>
			<div style="margin-top:10px;border:0.5px dashed #000; width:95%;margin:0 auto;margin-top:10px;"></div>
			<div style="margin-top:10px;width:95%;margin:0 auto;margin-top:10px;">
				建议:<span id="spnSuggestion"></span>
			</div>
			<div id="bottomDiv" style=" margin:0 auto;width:95%;margin-top:10px">
<%-- 				<span style="float:left">打印日期:<fmt:formatDate value="<%=new Date()%>" pattern="yyyy-MM-dd "/></span>   --%>
				<span style="float:right">医生：${record.name}</span>
			</div>
	</div>
</div>
<form id="condition_form">
	<div class="row">
		<input type="hidden" class="profileEditInput form-control" id="patName" name="patName" value="${condition.patName }" />
		<input type="hidden" class="form-control profileEditInput calendar-input" id="careTimeStart" name="careTimeStart" value="${condition.careTimeStart }">
	</div>
</form>

<script type="text/javascript">
	$(function() {
		setTimeout(function() {
			initData();
			initEvent();
		}, 50);  // if using angular widget like sa-panel, since the real dom loaded is after the Angular.compile method, which is behind the document ready event, so add a little timeout to hack this
	});
	
	function initData() {
		buildBreadcumb("新增/修改门诊记录");
		$('.nav-pills a:first').focus();  // fix issues of first tab is not focused after loading
	}
	
	function initEvent() {
		
	<%-- 	$('#cancle_btn').click(function() {
			if('${condition}'){
				$('#content').loadAngular("<%=serverUrl%>care/care_detail?id=${id}&registId=${registId}&"+$("#condition_form").serialize() );
			} else {
				$('#content').loadAngular("<%=serverUrl%>care/care_detail?id=${id}&registId=${registId}" );
			}
		}); --%>
		$('#cancle_btn').click(function(){
			if('${condition}'){
				$('#content').loadAngular("<%=serverUrl%>care/care_list?registId=${registId}&"+$("#condition_form").serialize() );
			} else {
				$('#content').loadAngular("<%=serverUrl%>care/care_list?registId=${registId}" );
			}
			
		});
		$('#save_btn').click(function() {
			var flag = false;
			$.ajax({
				type : "post",
				url : "<%=serverUrl%>care/save_record",
				async:false,
				data : $("#player_form").serialize(),
				success : function(data) {
					<%-- alert("修改成功");
					$('#content').loadAngular("<%=serverUrl%>care/care_detail?id=${id}&registId=${registId}&"+$("#condition_form").serialize() ); --%>
					flag = true;
				
				},
				error : function() {
											alert("修改失败");
										}
									});
			if (flag){
				$("#spnIllnessDesc").html($("#illnessDesc").val());
				$("#spnMedHistory").html($("#medHistory").val());
				$("#spnBodyExam").html($("#bodyExam").val());
				$("#spnDiagDesc").html($("#diagDesc").val());
				$("#spnAccExam").html($("#accExam").val());
				$("#spnSuggestion").html($("#suggestion").val());
				 $('#form_print').printArea(
				{
					'mode' : 'popup',
					'popTitle': '医生：${record.name}',
					'popClose' : false,
					'retainAttr' : [ 'class', 'id' ],
					'extraHead' : '<meta charset="utf-8" />,<meta http-equiv="X-UA-Compatible" content="IE=edge"/>'
				}); 
			}
						});
		
		
		validation();
	}

	function getTemplate(obj){
		if('illnessDesc' == obj){
			$("#illnessDesc").val("左手外伤术后1个月");
			$("#illnessDesc").text("左手外伤术后1个月");
		} else if('medHistory' == obj){
			
		} else if('bodyExam' == obj){
			$("#bodyExam").val("左手腕背部伤口愈合良好，干燥，无肿胀、渗出。左手石膏固定在位，内固定针在位有效。创口远局部皮肤浅感觉减退。");
			$("#bodyExam").text("左手腕背部伤口愈合良好，干燥，无肿胀、渗出。左手石膏固定在位，内固定针在位有效。创口远局部皮肤浅感觉减退。");
		} else if('diagDesc' == obj){
			$("#diagDesc").val("左手外伤术后。");
			$("#diagDesc").text("左手外伤术后。");
		} else if('suggestion' == obj){
			$("#suggestion").val("1、更换敷料，拆除缝线，拔除克氏针；\n2、休息一月，避免外伤、过度用力，禁止站立、负重行走，防止内固定物断裂或再骨折，防止肌腱再断裂。\n3、继续石膏外固定，如出现骨折处疼痛、畸形、骨擦感，随时就诊；\n4、保持针眼、创口清洁干燥，隔日更换敷料，如出现红肿、发热、渗液渗血，随时就诊；\n5、适度功能锻炼；\n6、中药熏洗治疗；\n7、一月后复查，如有其他不适，门诊随诊。");
			$("#suggestion").text("1、更换敷料，拆除缝线，拔除克氏针；\n2、休息一月，避免外伤、过度用力，禁止站立、负重行走，防止内固定物断裂或再骨折，防止肌腱再断裂。\n3、继续石膏外固定，如出现骨折处疼痛、畸形、骨擦感，随时就诊；\n4、保持针眼、创口清洁干燥，隔日更换敷料，如出现红肿、发热、渗液渗血，随时就诊；\n5、适度功能锻炼；\n6、中药熏洗治疗；\n7、一月后复查，如有其他不适，门诊随诊。");
		} 
	}
	
	function validation() {
		$("#player_form").bootstrapValidator({
			message : '您输入的值不合法。',
			feedbackIcons : {
				valid : 'glyphicon glyphicon-ok',
				invalid : 'glyphicon glyphicon-remove',
				validating : 'glyphicon glyphicon-refresh'
			},
			fields : {
				name : {
					message : '姓名不合法',
					validators : {
						notEmpty : {
							message : '姓名是必填项，请正确填写姓名。'
						},
						stringLength : {
							max : 30,
							message : '姓名长度超过限制，请输入30字符以内的姓名'
						}
					}
				}

			}
		});
	}
</script>