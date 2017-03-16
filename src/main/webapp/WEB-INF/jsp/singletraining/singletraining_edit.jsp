<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="xss" uri="http://www.sportsdata.cn/xss"%>
<%@ page import="cn.sportsdata.webapp.youth.common.constants.Constants" %>
<%@ page import="cn.sportsdata.webapp.youth.common.utils.CommonUtils" %>
<%@ page import="cn.sportsdata.webapp.youth.common.vo.SingleTrainingVO" %>

<%
	String serverUrl = CommonUtils.getServerUrl(request);
	String single_tactics_type_id=Constants.singleTraining_Tactics_Type;
	boolean isCreateSingle = (Boolean) request.getAttribute("isCreateSingle");
	SingleTrainingVO vo = (SingleTrainingVO) request.getAttribute("singletraining");
	String id = isCreateSingle ? "" : vo.getId();
%>
<style>
<!--
.equ_tab{
	float:center; 
	width: 96%;
	border:solid #F0F2F4;
	border-width:1px;
	border-collapse: collapse;
	border-spacing: 0;
	font-size: 14px;
	color:#666666;
}
.equ_tab tr {
	height: 30px;
	overflow: auto;
}

.equ_tab td {
	width: 30%;
	text-align: center;
	border:solid #F0F2F4;
	border-width:1px;
}

.target_title,.target_input{
	float: left;
	min-height: 1px;
    padding-left:15px;
    position: relative;
}
.target_title{
	width: 10.6667%;
	color:#666;
	line-height: 34px;
}
.target_input{
	width: 89.3333%;
	color:#333;
	padding-right:15px;
}
.single-tactics-image{
	width:248px;
	cursor:pointer;
} 
.single-tactics-body{
	width: 250px;
	margin: 10px auto 10px auto;
}
.single-tactics-title{
	width:90%;
	float:left;
	margin-top:15px;
	font-size: 14px;
	font-color:#666666;
	text-align: left;
	overflow:hidden;
	text-overflow: ellipsis;
	white-space: nowrap;
}
.tactics-name{
	cursor:pointer;
}
.btn_ljt:hover, .btn_ljt:focus, .btn_ljt.focus {
    color: #067DC2;
    text-decoration: none;
}
.equ_ljt:hover, .equ_ljt:focus, .equ_ljt.focus {
    color: #067DC2;
    text-decoration: none;
}
.btn_ljt{
	color:#666;
	margin-top: 15px; 
	padding: 0px; 
	border-top-width: 0px;

}
-->
</style>
<div class="profileEditContainer">
<div class="modal fade" id="starter_modal" tabindex="-1" role="dialog" aria-labelledby="starter_modal_label" data-backdrop="static" style="overflow-y:auto;">
	<div class="modal-dialog modal-lg" role="document"  style="min-width:980px;">
		<div class="modal-content">
			<div class="modal-header"  style="border:0px;">
       			<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
       			<span class="modal-title" style="color: #333;font-size: 16px;">添加单项训练战术板</span>
     		</div>
			<div class="modal-body" style="background-color:#ecf0f5;border:0px;padding:0px;">
				   <section class="content-header" style="background-color:#ecf0f5;">
				      <ol class="breadcrumb" >
				        <li id="first"><span>&nbsp;</span></li>
				      </ol>
				    </section>
				    <section class="content" id="tactics_panel" style="padding-top:0px;background-color:#ecf0f5;"></section>
<!--    				<div id="tactics_panel" class="panel panel-default"> -->
<!--    				</div> -->
			</div>
		</div>
	</div>
</div>
	<div class="profileAction button_area" style="top: 130px;">
		<button id="train_save_btn" class="btn btn-primary" style="float: right; margin-left: 10px;">保存</button>
		<button id="train_cancle_btn" class="btn btn-default" style="float: right;">取消</button>
	</div>
	<div class="clearfix"></div>
	<div class="profileEditContent">
		<form id="singletraining_form">
			<ul class="nav nav-tabs" role="tablist">
				<li role="presentation" class="active"><a class="profileEditTabHeader" href="#basic_tab" aria-controls="basic_tab" role="tab" data-toggle="tab">基本设置</a></li>
	  			<li role="presentation"><a class="profileEditTabHeader" href="#body_tab" aria-controls="body_tab" role="tab" data-toggle="tab">目标设置</a></li>
	  			<li role="presentation"><a class="profileEditTabHeader" href="#tactics_tab" aria-controls="tactics_tab" role="tab" data-toggle="tab">战术板</a></li>
	  			<li role="presentation"><a class="profileEditTabHeader" href="#professional_tab" aria-controls="professional_tab" role="tab" data-toggle="tab">时间器材</a></li>
			</ul>
			<div class="tab-content profileEditTabContent">
				<div role="tabpanel" class="tab-pane active" id="basic_tab">
					<sa-panel title="基本设置">
						<div class="row">
							<div class="col-md-1 profileEditTitleEx">名称</div>
							<div class="col-md-3">
								<div style="width:90%;float:left;">
									<div class="form-group">
										<c:choose>
											<c:when test="${isCreateSingle}">
												<input data-bv-field="name" style="padding: 0px 22px 0px 5px;" data-bv-excluded="false" type="text" class="form-control profileEditInput" id="name" name="name" />
											</c:when>
											<c:otherwise>
												<input data-bv-field="name" style="padding: 0px 22px 0px 5px;" data-bv-excluded="false" type="text" class="form-control profileEditInput" id="name" name="name" value="<xss:xssFilter text='${ singletraining.name }' filter='html'/>" />
											</c:otherwise>
										</c:choose>
									</div>
								</div>
							</div>
							<div class="col-md-1 profileEditTitleEx" >球员人数</div>
							<div class="col-md-2">
								<div style="width:90%;float:left;">
									<div class="form-group">
										<c:choose>
											<c:when test="${isCreateSingle}">
												<input type="text" data-bv-excluded="false" data-bv-field="player_count" class="form-control profileEditInput" id="player_count" name="player_count" />
											</c:when>
											<c:otherwise>
												<input type="text" data-bv-excluded="false" data-bv-field="player_count" class="form-control profileEditInput" id="player_count" name="player_count" value="${ singletraining.player_count }" />
											</c:otherwise>
										</c:choose>
									</div>
								</div>
								<div style="width:10%;float:left;padding-left:5px;color:#333;line-height:34px;">人</div>
							</div>
							<div class="col-md-1 profileEditTitleEx" >门将人数</div>
							<div class="col-md-2">
								<div style="width:90%;float:left;">
									<div class="form-group">
										<c:choose>
											<c:when test="${isCreateSingle}">
												<input type="text" data-bv-excluded="false" data-bv-field="goalkeeper_count" class="form-control profileEditInput" id="goalkeeper_count" name="goalkeeper_count" />
											</c:when>
											<c:otherwise>
												<input type="text" data-bv-excluded="false" data-bv-field="goalkeeper_count" class="form-control profileEditInput" id="goalkeeper_count" name="goalkeeper_count" value="${ singletraining.goalkeeper_count }" />
											</c:otherwise>
										</c:choose>
									</div>
								</div>
								<div style="width:10%;float:left;padding-left:5px;color:333#;line-height:34px;">人</div>
							</div>
						</div>
						<div class="row profileEditItemLine">
							<div  class="col-md-1 profileEditTitleEx">场地大小 </div>
							<div  class="col-md-3">
								<div style="width: 15px;float: left;margin-left:-15px;padding-right:5px;color:#333;line-height: 34px;">长 </div>
								<div style="width: 90%;float: left;">
									<div style="width: 40%;float: left;">
										<div class="form-group">
										<c:choose>
											<c:when test="${isCreateSingle}">
												<input type="text" class="form-control profileEditInput" style="padding:0px 22px 0px 5px;" id="yard_long" name="yard_long" data-bv-excluded="false" data-bv-field="yard_long" />
											</c:when>
											<c:otherwise>
												<input type="text" class="form-control profileEditInput" style="padding: 0px 22px 0px 5px;" id="yard_long" name="yard_long" data-bv-excluded="false" data-bv-field="yard_long" value="${ singletraining.yard_long }" />
											</c:otherwise>
										</c:choose>
										</div>
									</div>
									<div style="width: 20%;float: left;color:#333;text-align:center;line-height: 34px;"> 米 * 宽</div>
									<div style="width: 40%;float: left;">
										<div class="form-group">
											<c:choose>
												<c:when test="${isCreateSingle}">
													 <input type="text" class="form-control profileEditInput" style="padding: 0px 22px 0px 5px;" id="yard_width" name="yard_width" data-bv-excluded="false" data-bv-field="yard_width" />
												</c:when>
												<c:otherwise>
													<input type="text" class="form-control profileEditInput"style="padding: 0px 22px 0px 5px;" id="yard_width" name="yard_width" data-bv-excluded="false" data-bv-field="yard_width" value="${ singletraining.yard_width }" />
												</c:otherwise>
											</c:choose>
										</div>
									</div>
								</div>
								<div style="width: 10%;float: left;color:#333;padding-left:5px;line-height: 34px;">米 </div>
							</div>
							<div class="col-md-1 profileEditTitleEx">分类</div>
							<div class="col-md-2">
									<select class="tacticsEditInput form-control" style="width:90%;" id="singletraining_type" name="singletraining_type">
										<option value="normal">普通</option>
										<option value="guide">指导</option>
										<option value="antagonize">对抗</option>
									</select>
							</div>
							<div class="col-md-1 profileEditTitleEx">针对球员</div>
							<div class="col-md-2">
									<select class="tacticsEditInput form-control" style="width:90%;" id="singletraining_target" name="singletraining_target">
										<option value="all">所有队员</option>
										<option value="offensive_players">进攻队员</option>
										<option value="defensive_player">防守队员</option>
										<option value="goalkeeper">守门员</option>
									</select>
							</div>
						</div>
						<div class="row profileEditItemLine">
							<div class="col-md-1 profileEditTitleEx">描述</div>
							<div class="col-md-11">
								<div class="form-group">
								<c:choose>
									<c:when test="${isCreateSingle}">
									<div class="form-group">
										<textarea id="description" name="description" class="form-control utraining_input_text" data-bv-excluded="false" data-bv-field="description" rows="3" style="line-height: 20px;"></textarea>
									</div>
									</c:when>
									<c:otherwise>
									<div class="form-group">
										<textarea id="description" name="description" class="form-control utraining_input_text" data-bv-excluded="false" data-bv-field="description" rows="3" style="line-height: 20px;"><xss:xssFilter text='${ singletraining.description }' filter='html'/></textarea>
									</div>
									</c:otherwise>
								</c:choose>
								</div>
							</div>
							<div class="col-md-4"></div>
						</div>
					</sa-panel>
				</div>
				<div role="tabpanel" class="tab-pane" id="body_tab">
					<sa-panel title="目标设置">
						<div class="row">
							<div class="col-md-2" style="font-size:14px;color:#067DC2;">战术目标</div>
						</div>
						<div class="row profileEditItemLine">
							<div class="target_title">进攻战术目标</div>
							<div class="target_input">
								<div class="form-group">
								<c:choose>
									<c:when test="${isCreateSingle}">
										<input type="text" class="form-control profileEditInput" data-bv-excluded="false" data-bv-field="target_tactics_Offensive"  id="target_tactics_Offensive" name="target_tactics_Offensive" />
									</c:when>
									<c:otherwise>
										<input type="text" class="form-control profileEditInput" data-bv-excluded="false" data-bv-field="target_tactics_Offensive" id="target_tactics_Offensive" name="target_tactics_Offensive" value="<xss:xssFilter text='${ singletraining.singleTrainingExtInfoMap["target_tactics_Offensive"] }' filter='html'/>" />
									</c:otherwise>
								</c:choose>
								</div>
							</div>
						</div>
						<div class="row profileEditItemLine">
							<div class="target_title">防守战术目标</div>
							<div class="target_input">
								<div class="form-group">
								<c:choose>
									<c:when test="${isCreateSingle}">
										<input type="text" class="form-control profileEditInput" data-bv-excluded="false" data-bv-field="target_tactics_defense" id="target_tactics_defense" name="target_tactics_defense" />
									</c:when>
									<c:otherwise>
										<input type="text" class="form-control profileEditInput" data-bv-excluded="false" data-bv-field="target_tactics_defense" id="target_tactics_defense" name="target_tactics_defense" value="<xss:xssFilter text='${ singletraining.singleTrainingExtInfoMap["target_tactics_defense"] }' filter='html'/>" />
									</c:otherwise>
								</c:choose>
								</div>
							</div>
						</div>
						<div class="row profileEditItemLine">
							<div class="col-md-2" style="font-size:14px;color:#067DC2;">技术目标</div>
						</div>
						<div class="row profileEditItemLine">
							<div class="target_title">进攻技术目标</div>
							<div class="target_input">
								<div class="form-group">
								<c:choose>
									<c:when test="${isCreateSingle}">
										<input type="text" class="form-control profileEditInput" data-bv-excluded="false" data-bv-field="target_technology_Offensive"  id="target_technology_Offensive" name="target_technology_Offensive" />
									</c:when>
									<c:otherwise>
										<input type="text" class="form-control profileEditInput" data-bv-excluded="false" data-bv-field="target_technology_Offensive" id="target_technology_Offensive" name="target_technology_Offensive" value="<xss:xssFilter text='${ singletraining.singleTrainingExtInfoMap["target_technology_Offensive"] }' filter='html'/>" />
									</c:otherwise>
								</c:choose>
								</div>
							</div>
						</div>
						<div class="row profileEditItemLine">
							<div class="target_title">防守技术目标</div>
							<div class="target_input">
								<div class="form-group">
								<c:choose>
									<c:when test="${isCreateSingle}">
										<input type="text" class="form-control profileEditInput" data-bv-excluded="false" data-bv-field="target_technology_defense" id="target_technology_defense" name="target_technology_defense" />
									</c:when>
									<c:otherwise>
										<input type="text" class="form-control profileEditInput" data-bv-excluded="false" data-bv-field="target_technology_defense" id="target_technology_defense" name="target_technology_defense" value="<xss:xssFilter text='${ singletraining.singleTrainingExtInfoMap["target_technology_defense"] }' filter='html'/>" />
									</c:otherwise>
								</c:choose>
								</div>
							</div>
						</div>
						<div class="row profileEditItemLine">
							<div class="col-md-2" style="font-size:14px;color:#067DC2;">其他目标</div>
						</div>
						<div class="row profileEditItemLine">
							<div class="target_title">身体机能</div>
							<div class="target_input">
								<div class="form-group">
								<c:choose>
									<c:when test="${isCreateSingle}">
										<input type="text" class="form-control profileEditInput" id="target_other_body"  data-bv-excluded="false" data-bv-field="target_other_body" name="target_other_body" />
									</c:when>
									<c:otherwise>
										<input type="text" class="form-control profileEditInput" id="target_other_body" data-bv-excluded="false" data-bv-field="target_other_body" name="target_other_body" value="<xss:xssFilter text='${ singletraining.singleTrainingExtInfoMap["target_other_body"] }' filter='html'/>" />
									</c:otherwise>
								</c:choose>
								</div>
							</div>
						</div>
						<div class="row profileEditItemLine">
							<div class="target_title">意志品质</div>
							<div class="target_input">
								<div class="form-group">
								<c:choose>
									<c:when test="${isCreateSingle}">
										<input type="text" class="form-control profileEditInput" id="target_other_will"  data-bv-excluded="false" data-bv-field="target_other_will" name="target_other_will" />
									</c:when>
									<c:otherwise>
										<input type="text" class="form-control profileEditInput" id="target_other_will" data-bv-excluded="false" data-bv-field="target_other_will" name="target_other_will" value="<xss:xssFilter text='${ singletraining.singleTrainingExtInfoMap["target_other_will"] }' filter='html'/>" />
									</c:otherwise>
								</c:choose>
								</div>
							</div>
							<div class="col-md-4"></div>
						</div>
					</sa-panel>
				</div>
				<div role="tabpanel" class="tab-pane" id="tactics_tab">
					<sa-panel title="战术板">
						<div id="selectedStarterArea">
							<div class="single-tactics-body">
								<c:choose>
									<c:when test="${empty tactics.imgUrl}">
										<img class="single-tactics-image" src="<%=serverUrl%>resources/images/soccerboard/ic_tactics_single.png" onclick="selectTactics();"/>
										<div class="single-tactics-title" style="text-align: center;">
											<input type="hidden" id="tacticsId" name="tacticsId" value="null"/>
											<span  class="tactics-name"> 暂无战术安排，请<a href="#" onclick="selectTactics();" style="color:#057EC3; text-decoration:underline;cursor:pointer;">添加战术板</a></span>
										</div>
									</c:when>
									<c:otherwise>
										<img class="single-tactics-image" src="${ tactics.imgUrl }" width="248" onclick="selectTactics();"/>
										<div>
											<div class="single-tactics-title">
												<input type="hidden" id="tacticsId" name="tacticsId" value="${ tactics.id }"/>
												<span class="tactics-name" title='<xss:xssFilter text="${ tactics.name }" filter="html"/>'  onclick="selectTactics();"><xss:xssFilter text="${ tactics.name }" filter="html"/></span>
											</div>
											<span class="btn btn_ljt"  onclick="resetTactics();"><i class="glyphicon glyphicon-trash"></i></span>
										</div>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
					</sa-panel>
				</div>
				<div role="tabpanel" class="tab-pane" id="professional_tab">
					<sa-panel title="时间器材">
						<div class="row">
							<div class="col-md-1" style="font-size:14px;color:#067DC2;">时间</div>
						</div>
						<div class="row profileEditItemLine">
							<div class="col-md-1 profileEditTitleEx">小结时长</div>
							<div class="col-md-2">
								<div style="width:60%;float:left;">
									<div class="form-group">
										<c:choose>
											<c:when test="${isCreateSingle}">
												<input type="text" class="form-control profileEditInput" style="padding: 0px 22px 0px 5px;" id="time_single_train"  data-bv-excluded="false" data-bv-field="time_single_train" name="time_single_train"  onChange="changeSumTime();"/>
											</c:when>
											<c:otherwise>
												<input type="text" class="form-control profileEditInput" style="padding: 0px 22px 0px 5px;" id="time_single_train" data-bv-excluded="false" data-bv-field="time_single_train" name="time_single_train" value="${ singletraining.singleTrainingExtInfoMap['time_single_train'] }"  onChange="changeSumTime();"/>
											</c:otherwise>
										</c:choose>
									</div>
								</div>
								<span style="width:40%;padding-left:5px;color:#666;line-height: 34px;">分钟</span>
							</div>
							<div class="col-md-1 profileEditTitleEx">重复次数</div>
							<div class="col-md-2">
								<div style="width:60%;float:left;">
									<div class="form-group">
										<c:choose>
											<c:when test="${isCreateSingle}">
												<input type="text" class="form-control profileEditInput" style="padding: 0px 22px 0px 5px;" id="time_repeat"  data-bv-excluded="false" data-bv-field="time_repeat" name="time_repeat"  onChange="changeSumTime();"/>
											</c:when>
											<c:otherwise>
												<input type="text" class="form-control profileEditInput" style="padding: 0px 22px 0px 5px;" id="time_repeat" data-bv-excluded="false" data-bv-field="time_repeat" name="time_repeat" value="${ singletraining.singleTrainingExtInfoMap['time_repeat'] }"  onChange="changeSumTime();"/>
											</c:otherwise>
										</c:choose>
									</div>
								</div>
								<span style="width:40%;padding-left:5px;color:#666;line-height: 34px;">次</span>
							</div>
							<div class="col-md-1 profileEditTitleEx">间歇时长</div>
							<div class="col-md-2">
								<div style="width:60%;float:left;">
									<div class="form-group">
										<c:choose>
											<c:when test="${isCreateSingle}">
												<input type="text" class="form-control profileEditInput" style="padding: 0px 22px 0px 5px;" id="time_intermission"  data-bv-excluded="false" data-bv-field="time_intermission" name="time_intermission" onChange="changeSumTime();"/>
											</c:when>
											<c:otherwise>
												<input type="text" class="form-control profileEditInput" style="padding: 0px 22px 0px 5px;" id="time_intermission" data-bv-excluded="false" data-bv-field="time_intermission" name="time_intermission" value="${ singletraining.singleTrainingExtInfoMap['time_intermission'] }"  onChange="changeSumTime();"/>
											</c:otherwise>
										</c:choose>
									</div>
								</div>
								<span style="width:40%;padding-left:5px;color:#666;line-height: 34px;">分钟</span>
							</div>
							<div class="col-md-1 profileEditTitleEx">总时长</div>
							<div class="col-md-2">
								<div style="width:60%;float:left;">
									<div class="form-group">
										<c:choose>
											<c:when test="${isCreateSingle}">
												<input type="text" class="form-control profileEditInput" style="padding: 0px 22px 0px 5px;" id="time_sum"  data-bv-excluded="false" data-bv-field="time_sum" name="time_sum" />
											</c:when>
											<c:otherwise>
												<input type="text" class="form-control profileEditInput" style="padding: 0px 22px 0px 5px;" id="time_sum" data-bv-excluded="false" data-bv-field="time_sum" name="time_sum" value="${ singletraining.singleTrainingExtInfoMap['time_sum'] }" />
											</c:otherwise>
										</c:choose>
									</div>
								</div>
								<span style="width:40%;padding-left:5px;color:#666;line-height: 34px;">分钟</span>
							</div>
						</div>			
						<div class="row profileEditItemLine">	
							<div class="col-md-1" style="font-size:14px;color:#067DC2;">器材</div>
						</div>
						<div class="row profileEditItemLine">	
							<div class="col-md-1 profileEditTitleEx">名称</div>
							<div class="col-md-2">
									<select class="tacticsEditInput form-control"  style="width:60%;" id="single_equipment" name="single_equipment">
										<c:forEach items="${equipmentList}" var="equipment">
											<option value="${equipment.id }"><xss:xssFilter text='${equipment.name}' filter='html'/></option>
										</c:forEach>
									</select>
							</div>
							<div class="col-md-1 profileEditTitleEx">数量：</div>
							<div class="col-md-2">
								<div style="width:60%;float:left;">
									<input type="text" class="form-control profileEditInput" id="count" name="count" />
								</div>
							</div>
							<button id="finish_btn" class="btn btn-primary" onclick="changeSelect()" style="float: left;margin-left: 15px;">添加</button>
						</div>
						<div class="row profileEditItemLine" align="center">	
							<table id="equipment_tab" class="equ_tab">
								<tr style="background-color:#E2F4FF; height: 35px;">
									<td style="width: 20%;">序号</td>
									<td style="width: 30%;">名称</td>
									<td style="width: 30%;">数量</td>
									<td style="width: 20%;">操作</td>
								</tr>
							<c:forEach items="${singletraining.singleTrainingEquipmentInfoList}" var="equipmentInfo" varStatus="status">
							   <c:choose>
							   		<c:when test="${status.index%2==0}">
							   			<tr>
							   		</c:when>
							   		<c:otherwise>
							   			<tr style="background-color:#F2F5F7;">
							   		</c:otherwise>
							   </c:choose>
									<td style="width: 20%;">${status.index + 1}</td>
									<td style="width: 30%;"><xss:xssFilter text="${equipmentInfo.equipmentName}" filter="html"/></td>
									<td style="width: 30%;"><span id="count${equipmentInfo.equipmentId}">${equipmentInfo.count}</span></td>
									<td style="width: 20%;"><span class="btn equ_ljt" onclick="deleteRow(this);"><i class="glyphicon glyphicon-trash"></i></span></td>
								</tr>
							</c:forEach>
							</table>
						</div>
					</sa-panel>
				</div>
			</div>
		</form>
	</div>
</div>

<script type="text/javascript">
    function selectTactics(){
    	var stid=$("#tacticsId").val();
    	//$("#tactics_panel").empty();
    	var strurl="<%=serverUrl%>system_tool/showTacticsListDetail?tactics_type_id=<%=single_tactics_type_id%>&pFlag=single&selectedTacticsId="+stid;

    	$("#tactics_panel").loadAngular(strurl);
    	$('#starter_modal').modal('show');
    }
    $('#starter_modal').on('shown.bs.modal', function () {
    	resizeTacticsData();
    });

	function changeSumTime(){
		var xjsc=$('#time_single_train').val();
		var cfcs=$('#time_repeat').val();
		var jxsc=$('#time_intermission').val();
		var zsc=0;
		if(xjsc=='' || cfcs=='' || jxsc==''){
			$('#time_sum').val("");
		}else if(xjsc>0 && cfcs>0 &&jxsc>0){
			zsc=((xjsc*cfcs)+(jxsc*(cfcs-1)))
			if((zsc + '').indexOf('.')== -1){
				$('#time_sum').val(zsc);
			}else{
				$('#time_sum').val(zsc.toFixed(2));
			}
		}
	}
	
	String.prototype.replaceAll = function(s1,s2){ 
		return this.replace(new RegExp(s1,"gm"),s2); 
	}
	
	var arr=[];
	function changeSelect(){
		var num=$('#count').val();
		 var bz = /^[1-9]*[1-9][0-9]*$/;
		 if(!(bz.test(num))){
			 alert("器材数量只能为正整数");
             $("#count").focus();
             return;
		 }
		var selectText = $("#single_equipment").find("option:selected").text();
		if(selectText==''){
			alert("器材不允许为空，请先添加器材！");
			return;
		}
		selectText=selectText.replaceAll("<", "&lt;");
		var selectValue = $("#single_equipment").val();
		if(arr.length>0){
			for(var i=0; i<arr.length;i++) {
			    if(selectValue==arr[i].equipmentId) {
			    	arr[i].count=num;
			   		$("#count"+selectValue).text(num);
			      return;
			    }
			  }
		}
		arr.push({'equipmentName':selectText,'equipmentId':selectValue,'count':num});
		var tt='<tr>';
		if(arr.length%2==0){
			tt='<tr style="background-color:#F2F5F7;">';
		}
		$("#equipment_tab").append(tt+'<td style="width: 20%;">'+(arr.length)+'</td><td style="width: 30%;">'+selectText+'</td><td style="width: 30%;"><span id="count'+selectValue+'">'+num+'</span></td><td style="width: 20%;"><span class="btn equ_ljt" onclick="deleteRow(this);"><i class="glyphicon glyphicon-trash"></i></span></td></tr>');
		$("#count").val("");
	}
	function deleteRow(obj) {
		var e_id = $(obj).parent().parent().find("td").eq(2).find("span").attr("id");
		bootbox.dialog({
			message: "您是否要删除该器材？",
			title: "确认删除",
			buttons: {
				unchange: {
				      label: "取消",
				      className: "btn-default",
				      callback: function() {}
				    },
				danger: {
					label: "删除",
					className: "btn-danger",
					callback: function() {
						$(obj).parent().parent().parent().remove(); 
						$("#equipment_tab").append('<tr style="background-color:#E2F4FF; height: 35px;"><td style="width: 20%;">序号</td><td style="width: 30%;">名称</td><td style="width: 30%;">数量</td><td style="width: 20%;">操作</td></tr>');
						var equ=[];
						if(arr.length>0){
							for(var i=0; i<arr.length;i++) {
							    if(e_id!="count"+arr[i].equipmentId) {
							    	equ.push({'equipmentName':arr[i].equipmentName,'equipmentId':arr[i].equipmentId,'count':arr[i].count});
							    	var tt='<tr>';
									if(equ.length%2==0){
										tt='<tr style="background-color:#F2F5F7;">';
									}
							    	$("#equipment_tab").append(tt+'<td style="width: 20%;">'+(equ.length)+'</td><td style="width: 30%;">'+arr[i].equipmentName+'</td><td style="width: 30%;"><span id="count'+arr[i].equipmentId+'">'+arr[i].count+'</span></td><td style="width: 20%;"><span class="btn equ_ljt" onclick="deleteRow(this);"><i class="glyphicon glyphicon-trash"></i></span></td></tr>');
							    }
							  }
						}
						arr=[];
						arr=equ;
					}
				}
			   
			}
		});
		
	}
	function resetTactics() {
		var stid=$("#tacticsId").val();
		if(stid!=0){
			bootbox.dialog({
				message: "您是否要移除该战术板？",
				title: "确认移除",
				buttons: {
					unchange: {
					      label: "取消",
					      className: "btn-default",
					      callback: function() {}
					    },
					danger: {
						label: "移除",
						className: "btn-danger",
						callback: function() {
							//removeTactics();
							$('#selectedStarterArea').html('').html(removeTemplateCompiled());
						}
					}
				   
				}
			});
		}
	}
	
	$(function() {
		setTimeout(function() {
			initTrainData();
			initTrainEvent();

		}, 50);
		
	});
	var strurl="<%=serverUrl%>singletraining/showSingleTrainingList";
	var msg="打开单项训练列表页面失败";
	function initTrainData() {
		var isCreateSingle = ${ isCreateSingle };
		var select_field_ids = ['singletraining_type', 'singletraining_target'];
		
		if(!isCreateSingle) {
			strurl= "<%=serverUrl%>singletraining/showSingleTrainingDetail?id="+"${singletraining.id}";
			msg="打开单项训练详情页面失败";
			
			var translatedType = '${ singletraining.singleTrainingExtInfoMap["singletraining_type"] }';
			$("#singletraining_type option[value='" + translatedType + "']").attr('selected', 'selected');
			
			var translatedTarget = '${ singletraining.singleTrainingExtInfoMap["singletraining_target"] }';
			$("#singletraining_target option[value='" + translatedTarget + "']").attr('selected', 'selected');
			
		}
		
		$.each(select_field_ids, function(index, value) {
			var $select = $('#' + select_field_ids[index]);
			
			$select.select2({
				minimumResultsForSearch : Infinity
			});
		});
		<c:forEach items="${singletraining.singleTrainingEquipmentInfoList}" var="equipmentInfo">
			arr.push({'equipmentName':"<xss:xssFilter text='${equipmentInfo.equipmentName}' filter='js'/>",'equipmentId':"${equipmentInfo.equipmentId}",'count':"${equipmentInfo.count}"});
		</c:forEach>
	}


	function initTrainEvent() {
		
		
		buildBreadcumb("新增/修改单项训练");
		var isCreateSingle = ${ isCreateSingle };
		$('#matchDateIcon').click(function() {
			$date.datepicker('show');
		});
		$('#singletraining_type').select2();
		$('#singletraining_target').select2();
		$('#single_equipment').select2();
		$('#single_equipment').change(function() {
			$("#count").val("");
			//com.setChanged();
		});
		
		starter_template_str = '<div class="single-tactics-body">'	
									+'<img class="single-tactics-image" src="{{ imgSrc }}" onclick="selectTactics();"/>'
									+'<div>'
										+'<div class="single-tactics-title">'
											+'<input type="hidden" id="tacticsId" name="tacticsId" value="{{id}}"/>'
											+'<span class="tactics-name" title="{{title}}"  onclick="selectTactics();">{{ name }}</span>'
										+'</div>'
										+'<span class="btn btn_ljt" onclick="resetTactics();"><i class="glyphicon glyphicon-trash"></i></span>'
									+'</div>'
								+'</div>';
								
		remove_tactics_str= '<div class="single-tactics-body">'
								+'<img class="single-tactics-image" src="<%=serverUrl%>resources/images/soccerboard/ic_tactics_single.png" onclick="selectTactics();"/>'
								+'<div class="single-tactics-title" style="text-align: center;">'
									+'<input type="hidden" id="tacticsId" name="tacticsId" value="null"/>'
									+'<span  class="tactics-name"> 暂无战术安排，请<a href="#" onclick="selectTactics();" style="color:#057EC3; text-decoration:underline;cursor:pointer;">添加战术板</a></span>'
								+'</div>'
							+'</div>';	
								
		_.templateSettings = {
		interpolate: /\{\{(.+?)\}\}/g
		};
		starterTemplateCompiled = _.template(starter_template_str);
		removeTemplateCompiled = _.template(remove_tactics_str);
		
		validationTrain();
		$('#train_save_btn').click(function() {
			$("#singletraining_form").data('bootstrapValidator').validate();
			if(!$("#singletraining_form").data('bootstrapValidator').isValid()){
				return;
			}
			
			var form_data = $('#singletraining_form').serializeArray();
			var converted_ext_data = [];
			var converted_base_data = {'id': '<%=id%>'};
			var basic_user_fields = ['name', 'description', 'player_count', 'goalkeeper_count', 'yard_width', 'yard_long','tacticsId']; 
			$.each(form_data, function(index, item) {
				var isConvert = false;
				if($.inArray(item['name'], basic_user_fields) > -1) {
					converted_base_data[item['name']] = isConvert ? item['value'].replace(/\r/ig, "") : item['value'];
				} else {
					converted_ext_data.push({'itemName': item['name'], 'itemValue': isConvert ? item['value'].replace(/\r/ig, "") : item['value']});
				}
			});
			
			var submitted_data = {'singleTrainingExtInfoList': converted_ext_data,'singleTrainingEquipmentInfoList': arr, 'singleTrainingBO': converted_base_data};
			sa.ajax({
				type : "post",
				url : "<%=serverUrl%>singletraining/saveSingleTraining",
				data: JSON.stringify(submitted_data),
				contentType: "application/json",
				success : function(data) {
					if(!data.status) {
						alert("提交单项训练信息异常");
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
					alert("提交单项训练信息失败");
				}
			});
		});
		
		$('#train_cancle_btn').click(function() {
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
	}
	
	function validationTrain(){
		$("#singletraining_form").bootstrapValidator({
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
	        	player_count:{
	        		message: '球员人数不合法',
	        		 validators: {
			           		integer:{
			           			message: '球员人数不合法'
			           		}  
			            }	  
	        	},
	        	goalkeeper_count:{
	        		message: '门将人数不合法',
	        		 validators: {
			           		integer:{
			           			message: '门将人数不合法'
			           		}  
			            }	  
	        	},
	        	yard_long:{
	        		message: '场地长度不合法',
	        		 validators: {
			           		integer:{
			           			message: '场地长度不合法'
			           		}  
			            }	  
	        	},
	        	yard_width:{
	        		message: '场地宽度不合法',
	        		 validators: {
			           		integer:{
			           			message: '场地宽度不合法'
			           		}  
			            }	  
	        	},
	        	description:{
	        		message: '描述信息不合法',
		           	 validators: {
		           		stringLength: {
		           			max: 1000,
		                    message: '描述信息长度超过限制，请输入1000字符以内的描述信息'
		                }
		           }
	        	},
	        	target_tactics_Offensive:{
	        		message: '战术目标-进攻战术思路不合法',
		           	 validators: {
		           		stringLength: {
		           			max: 200,
		                    message: '战术目标-进攻战术思路长度超过限制，请输入200字符以内的内容'
		                }
		            }
	        	},
	        	target_tactics_defense:{
	        		message: '战术目标-防守战术思路不合法',
	        		 validators: {
			           		stringLength: {
			           			max: 200,
			                    message: '战术目标-防守战术思路长度超过限制，请输入200字符以内的内容'
			                }
			            }
	        	},
	        	target_technology_Offensive:{
	        		message: '技术目标-进攻战术思路不合法',
		           	 validators: {
		           		stringLength: {
		           			max: 200,
		                    message: '技术目标-进攻战术思路长度超过限制，请输入200字符以内的内容'
		                }
		            }
	        	},
	        	target_technology_defense:{
	        		message: '技术目标-防守战术思路不合法',
	        		 validators: {
			           		stringLength: {
			           			max: 200,
			                    message: '技术目标-防守战术思路长度超过限制，请输入200字符以内的内容'
			                }
			            }
	        	},
	        	target_other_body:{
	        		message: '身体机能不合法',
		           	 validators: {
		           		stringLength: {
		           			max: 200,
		                    message: '身体机能长度超过限制，请输入200字符以内的身体机能'
		                }
		            }
	        	},
	        	target_other_will:{
	        		message: '意志品质不合法',
	        		 validators: {
			           		stringLength: {
			           			max: 200,
			                    message: '意志品质长度超过限制，请输入200字符以内的意志品质'
			                }
			            }
	        	},
	        	time_single_train:{
	        		message: '小结时长不合法',
	        		 validators: {
				        	 regexp: {
			                     regexp: /^\d+(?=\.{0,1}\d+$|$)/,
			                     message: '小结时长不合法'
			                 }

			            }	  
	        	},
	        	time_repeat:{
	        		message: '重复次数不合法',
	        		 validators: {
	        			 regexp: {
		                     regexp: /^[1-9]*[1-9][0-9]*$/,
			           			message: '重复次数不合法'
			           		}  
			            }	  
	        	},
	        	time_intermission:{
	        		message: '间歇时长不合法',
	        		 validators: {
	        			 regexp: {
		                     regexp: /^\d+(?=\.{0,1}\d+$|$)/,
			           			message: '间歇时长不合法'
			           		}  
			            }	  
	        	},
	        	time_sum:{
	        		message: '总时长不合法',
	        		 validators: {
	        			 regexp: {
		                     regexp: /^\d+(?=\.{0,1}\d+$|$)/,
			           			message: '总时长不合法'
			           		}  
			            }	  
	        	}        	
	        }
	    });
	}
</script>
