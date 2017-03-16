<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page import="cn.sportsdata.webapp.youth.common.utils.CommonUtils" %>
<%@ page import="cn.sportsdata.webapp.youth.common.vo.UserVO" %>

<%
	String serverUrl = CommonUtils.getServerUrl(request);

	boolean isCreate = (Boolean) request.getAttribute("isCreate");
	UserVO vo = (UserVO) request.getAttribute("player");
	String userId = isCreate ? "" : vo.getId();
%>

<div class="profileEditContainer">
	<div class="player_edit_button_area">
		<button id="save_btn" class="btn btn-primary" style="float: right; margin-left: 10px;">保存</button>
		<button id="cancle_btn" class="btn btn-default" style="float: right;">取消</button>
	</div>
	<div class="clearfix"></div>
	<div class="profileEditContent">
		<form id="player_form">
			<ul class="nav nav-tabs" role="tablist">
				<li role="presentation" class="active"><a class="profileEditTabHeader" href="#basic_tab" aria-controls="basic_tab" role="tab" data-toggle="tab">基本资料</a></li>
	  			<li role="presentation"><a class="profileEditTabHeader" href="#body_tab" aria-controls="body_tab" role="tab" data-toggle="tab">身体信息</a></li>
	  			<li role="presentation"><a class="profileEditTabHeader" href="#professional_tab" aria-controls="professional_tab" role="tab" data-toggle="tab">专业信息</a></li>
	  			<li role="presentation"><a class="profileEditTabHeader" href="#club_tab" aria-controls="club_tab" role="tab" data-toggle="tab">俱乐部</a></li>
			</ul>
			
			<div class="tab-content profileEditTabContent">
				<div role="tabpanel" class="tab-pane active" id="basic_tab">
					<sa-panel title="基本资料">
						<div class="row">
							<div class="col-md-2 text-center">
								<c:choose>
									<c:when test="${ !empty player.avatar }">
										<img id="user_avatar" class="profileEditAvatar custom-image" src="<%=serverUrl%>file/downloadFile?fileName=${ player.avatar }"></img>
									</c:when>
									<c:otherwise>
										<div style="position: relative; width: 100px; height: 90px;"> 
											<img id="user_avatar" class="profileEditAvatar default-image" src="<%=serverUrl%>resources/images/user_avatar.png"></img>
											<span style="position: absolute; bottom: 0;font-size: 12px; left: 27px; color:#999999">上传头像</span> 
										</div>
									</c:otherwise>
								</c:choose>
							</div>
							<div class="col-md-10">
								<div class="row">
									<div class="col-md-1 inputLabel">姓名</div>
									<div class="col-md-3">
										<c:choose>
											<c:when test="${isCreate}">
											<div class="form-group">
												<input data-bv-excluded="false" data-bv-field="name" type="text" class="form-control" id="name" name="name" />
											</div>
											</c:when>
											<c:otherwise>
											<div class="form-group">
												<input data-bv-excluded="false" data-bv-field="name" type="text" class="form-control" id="name" name="name" value="${ player.name }" />
											</div>
											</c:otherwise>
										</c:choose>
									</div>
									<div class="col-md-1 inputLabel">生日</div>
									<div class="col-md-3">
										<c:choose>
											<c:when test="${isCreate}">
												<div class="input-group date">
													<input type="text" class="form-control profileEditInput calendar-input" id="birthday" name="birthday" readonly>
													<span id="birthdayIcon" class="input-group-addon calendar-icon"><i class="glyphicon glyphicon-calendar major_color"></i></span>
												</div>
											</c:when>
											<c:otherwise>
												<div class="input-group date">
													<input type="text" class="form-control profileEditInput calendar-input" id="birthday" name="birthday" value="<fmt:formatDate value="${ player.birthday }" pattern="yyyy-MM-dd" />" readonly>
													<span id="birthdayIcon" class="input-group-addon calendar-icon"><i class="glyphicon glyphicon-calendar major_color"></i></span>
												</div>
											</c:otherwise>
										</c:choose>
									</div>
									<div class="col-md-1 inputLabel">电话</div>
									<div class="col-md-3">
										<div class="form-group">
										<c:choose>
											<c:when test="${isCreate}">
												<input type="text" class="form-control" data-bv-excluded="false" data-bv-field="tel" id="tel" name="tel" />
											</c:when>
											<c:otherwise>
												<input type="text" class="form-control" id="tel" data-bv-excluded="false" data-bv-field="tel" name="tel" value="${ player.tel }" />
											</c:otherwise>
										</c:choose>
										</div>
									</div>
								</div>
								<div class="row profileEditItemLine">
									<div class="col-md-1 inputLabel" style="padding-right:0px;">身份证号</div>
									<div class="col-md-3">
									<div class="form-group">
										<c:choose>
											<c:when test="${isCreate}">
												<input type="text" data-bv-excluded="false" data-bv-field="idCard" class="form-control" id="idCard" name="idCard" />
											</c:when>
											<c:otherwise>
												<input type="text" data-bv-excluded="false" data-bv-field="idCard" class="form-control" id="idCard" name="idCard" value="${ player.idCard }" />
											</c:otherwise>
										</c:choose>
										</div>
									</div>
									<div class="col-md-1 inputLabel">邮箱</div>
									<div class="col-md-3">
										<div class="form-group">
										<c:choose>
											<c:when test="${isCreate}">
												<input type="text" class="form-control" id="email" name="email" data-bv-excluded="false" data-bv-field="email" />
											</c:when>
											<c:otherwise>
												<input type="text" class="form-control" id="email" name="email" data-bv-excluded="false" data-bv-field="email"  value="${ player.email }" />
											</c:otherwise>
										</c:choose>
										</div>
									</div>
									<div class="col-md-1 inputLabel">护照号</div>
									<div class="col-md-3">
										<div class="form-group">
										<c:choose>
											<c:when test="${isCreate}">
												<input type="text" data-bv-excluded="false" data-bv-field="passport" class="form-control" id="passport" name="passport" />
											</c:when>
											<c:otherwise>
												<input type="text" data-bv-excluded="false" data-bv-field="passport" class="form-control" id="passport" name="passport" value="${ player.passport }" />
											</c:otherwise>
										</c:choose>
										</div>
									</div>
								</div>
								<div class="row profileEditItemLine">
									<div class="col-md-1 inputLabel">出生地</div>
									<div class="col-md-3">
										<div class="form-group">
										<c:choose>
											<c:when test="${isCreate}">
												<input type="text" class="form-control" id="birthPlace" data-bv-excluded="false" data-bv-field="birthPlace" name="birthPlace" />
											</c:when>
											<c:otherwise>
												<input type="text" class="form-control" id="birthPlace" name="birthPlace" data-bv-excluded="false" data-bv-field="birthPlace" value="${ player.birthPlace }" />
											</c:otherwise>
										</c:choose>
										</div>
									</div>
									<div class="col-md-1 inputLabel">国籍</div>
									<div class="col-md-3">
										<div class="form-group">
										<c:choose>
											<c:when test="${isCreate}">
												<input type="text" data-bv-excluded="false" data-bv-field="nationality" class="form-control" id="nationality" name="nationality" />
											</c:when>
											<c:otherwise>
												<input type="text" data-bv-excluded="false" data-bv-field="nationality" class="form-control" id="nationality" name="nationality" value="${ player.nationality }" />
											</c:otherwise>
										</c:choose>
										</div>
									</div>
									<div class="col-md-1 inputLabel">住址</div>
									<div class="col-md-3">
										<div class="form-group">
										<c:choose>
											<c:when test="${isCreate}">
												<input type="text" class="form-control" id="homeAddress" data-bv-excluded="false" data-bv-field="homeAddress"  name="homeAddress" />
											</c:when>
											<c:otherwise>
												<input type="text" class="form-control" id="homeAddress" name="homeAddress" data-bv-excluded="false" data-bv-field="homeAddress"  value="${ player.homeAddress }" />
											</c:otherwise>
										</c:choose>
										</div>
									</div>
								</div>
							</div>
						</div>
					</sa-panel>
				</div>
				<div role="tabpanel" class="tab-pane" id="body_tab">
					<sa-panel title="身体信息">
						<div class="row">
							<div class="col-md-1 inputLabel">身高</div>
							<div class="col-md-4">
								<div class="row">
									<div class="col-md-10">
										<div class="form-group">
										<c:choose>
											<c:when test="${isCreate}">
												<input type="text" class="form-control" data-bv-excluded="false" data-bv-field="professional_tall"  id="professional_tall" name="professional_tall" />
											</c:when>
											<c:otherwise>
												<input type="text" class="form-control" data-bv-excluded="false" data-bv-field="professional_tall" id="professional_tall" name="professional_tall" value="${ player.userExtInfoMap['professional_tall'] }" />
											</c:otherwise>
										</c:choose>
										</div>
									</div>
									<div class="col-md-2 inputLabel" style="padding:0px;">
										<div>厘米</div>
									</div>
								</div>
							</div>
							<div class="col-md-1 inputLabel">体重</div>
							<div class="col-md-4">
								<div class="row">
									<div class="col-md-10">
										<div class="form-group">
										<c:choose>
											<c:when test="${isCreate}">
												<input type="text" class="form-control" data-bv-excluded="false" data-bv-field="professional_weight" id="professional_weight" name="professional_weight" />
											</c:when>
											<c:otherwise>
												<input type="text" class="form-control" id="professional_weight" data-bv-excluded="false" data-bv-field="professional_weight" name="professional_weight" value="${ player.userExtInfoMap['professional_weight'] }" />
											</c:otherwise>
										</c:choose>
										</div>
									</div>
									<div class="col-md-2 inputLabel" style="padding:0px;">
										<div>公斤</div>
									</div>
								</div>
							</div>
							<div class="col-md-2"></div>
						</div>
						<div class="row profileEditItemLine">
							<div class="col-md-1 inputLabel">腰围</div>
							<div class="col-md-4">
								<div class="row">
									<div class="col-md-10">
										<div class="form-group">
										<c:choose>
											<c:when test="${isCreate}">
												<input type="text" class="form-control" id="professional_waist"  data-bv-excluded="false" data-bv-field="professional_waist" name="professional_waist" />
											</c:when>
											<c:otherwise>
												<input type="text" class="form-control" id="professional_waist" data-bv-excluded="false" data-bv-field="professional_waist" name="professional_waist" value="${ player.userExtInfoMap['professional_waist'] }" />
											</c:otherwise>
										</c:choose>
										</div>
									</div>
									<div class="col-md-2 inputLabel" style="padding:0px;">
										<div>厘米</div>
									</div>
								</div>
							</div>
							<div class="col-md-1 inputLabel">体格</div>
							<div class="col-md-4">
								<div class="row">
									<div class="col-md-10">
										<div class="form-group">
										<select class="profileEditInput" id="professional_physique" name="professional_physique" style="width: 100%;">
											<option value="strong">强壮</option>
											<option value="middle">适中</option>
											<option value="weak">瘦弱</option>
										</select>
										</div>
									</div>
								</div>
							</div>
							<div class="col-md-2"></div>
						</div>
					</sa-panel>
				</div>
				<div role="tabpanel" class="tab-pane" id="professional_tab">
					<sa-panel title="专业信息">
						<div class="row">
							<div class="col-md-1 inputLabel">号码</div>
							<div class="col-md-3">
								<div class="form-group">
								<c:choose>
									<c:when test="${isCreate}">
										<input type="text" class="form-control" id="professional_jersey_number" data-bv-excluded="false" data-bv-field="professional_jersey_number" name="professional_jersey_number" />
									</c:when>
									<c:otherwise>
										<input type="text" class="form-control" id="professional_jersey_number" data-bv-excluded="false" data-bv-field="professional_jersey_number" name="professional_jersey_number" value="${ player.userExtInfoMap['professional_jersey_number'] }" />
									</c:otherwise>
								</c:choose>
								</div>
							</div>
							<div class="col-md-1 inputLabel">位置</div>
							<div class="col-md-3">
								<div class="form-group">
									<select class="profileEditInput" id="professional_primary_position" name="professional_primary_position" style="width: 100%;">
										<option value="forward">前锋</option>
										<option value="center">中场</option>
										<option value="defender">后卫</option>
										<option value="goalkeeper">门将</option>
									</select>
								</div>
							</div>
							<div class="col-md-1 inputLabel">第二位置</div>
							<div class="col-md-3">
								<div class="form-group">
									<select class="profileEditInput" id="professional_secondary_position" name="professional_secondary_position" style="width: 100%;">
										<option value="forward">前锋</option>
										<option value="center">中场</option>
										<option value="defender">后卫</option>
										<option value="goalkeeper">门将</option>
									</select>
								</div>
							</div>
						</div>
						<div class="row profileEditItemLine">
							<div class="col-md-1 inputLabel">惯用脚</div>
							<div class="col-md-3">
								<div class="form-group">
									<select class="profileEditInput" id="professional_preferred_foot" name="professional_preferred_foot" style="width: 100%;">
										<option value="right">右脚</option>
										<option value="left">左脚</option>
									</select>
								</div>
							</div>
							<div class="col-md-1 inputLabel">类型</div>
							<div class="col-md-3">
								<div class="form-group">
									<select class="profileEditInput" id="professional_type" name="professional_type" style="width: 100%;">
										<option value="youth">青训</option>
										<option value="transfer">转会</option>
										<option value="rent">租借</option>
									</select>
								</div>
							</div>
							<div class="col-md-4"></div>
						</div>
						<div class="row profileEditItemLine">
							<div class="col-md-1 inputLabel">总体评价</div>
							<div class="col-md-3">
								<div class="form-group">
									<select class="profileEditInput" id="technical_overall_rating" name="technical_overall_rating" style="width: 100%;">
										<option value="excellent">优秀</option>
										<option value="good">良好</option>
										<option value="average">中等</option>
										<option value="bad">不及格</option>
									</select>
								</div>
							</div>
							<div class="col-md-1 inputLabel">技术水平</div>
							<div class="col-md-3">
								<div class="form-group">
									<select class="profileEditInput" id="technical_technique_rating" name="technical_technique_rating" style="width: 100%;">
										<option value="excellent">优秀</option>
										<option value="good">良好</option>
										<option value="average">中等</option>
										<option value="bad">不及格</option>
									</select>
								</div>
							</div>
							<div class="col-md-1 inputLabel">团队合作</div>
							<div class="col-md-3">
								<div class="form-group">
									<select class="profileEditInput" id="technical_teamwork_rating" name="technical_teamwork_rating" style="width: 100%;">
										<option value="excellent">优秀</option>
										<option value="good">良好</option>
										<option value="average">中等</option>
										<option value="bad">不及格</option>
									</select>
								</div>
							</div>
						</div>			
						<div class="row profileEditItemLine">	
							<div class="col-md-1 inputLabel">身体素质</div>
							<div class="col-md-3">
								<div class="form-group">
									<select class="profileEditInput" id="technical_fitness_rating" name="technical_fitness_rating" style="width: 100%;">
										<option value="excellent">优秀</option>
										<option value="good">良好</option>
										<option value="average">中等</option>
										<option value="bad">不及格</option>
									</select>
								</div>
							</div>
							<div class="col-md-1 inputLabel">心理素质</div>
							<div class="col-md-3">
								<div class="form-group">
									<select class="profileEditInput" id="technical_mentality_rating" name="technical_mentality_rating" style="width: 100%;">
										<option value="excellent">优秀</option>
										<option value="good">良好</option>
										<option value="average">中等</option>
										<option value="bad">不及格</option>
									</select>
								</div>
							</div>
							<div class="col-md-4"></div>
						</div>
					</sa-panel>
				</div>
				<div role="tabpanel" class="tab-pane" id="club_tab">
					<sa-panel title="俱乐部信息">
						<div class="row">
							<div class="col-md-1 inputLabel">现俱乐部</div>
							<div class="col-md-3">
								<div class="form-group">
								<c:choose>
									<c:when test="${isCreate}">
										<input type="text" class="form-control" data-bv-excluded="false" data-bv-field="club_club_name" id="club_club_name" name="club_club_name" />
									</c:when>
									<c:otherwise>
										<input type="text" class="form-control" data-bv-excluded="false" data-bv-field="club_club_name" id="club_club_name" name="club_club_name" value="${ player.userExtInfoMap['club_club_name'] }" />
									</c:otherwise>
								</c:choose>
								</div>
							</div>
							<div class="col-md-1 inputLabel">市场价格</div>
							<div class="col-md-3">
								<div class="form-group">
								<c:choose>
									<c:when test="${isCreate}">
										<input type="text" class="form-control" data-bv-excluded="false" data-bv-field="club_market_price" id="club_market_price" name="club_market_price" style="width: 80%; float: left;"/>
									</c:when>
									<c:otherwise>
										<input type="text" class="form-control" data-bv-excluded="false" data-bv-field="club_market_price" id="club_market_price" name="club_market_price" style="width: 80%; float: left;" value="${ player.userExtInfoMap['club_market_price'] }" />
									</c:otherwise>
								</c:choose>
								<div class="inputLabel" style="width: 45px;float: left; text-align: center;">万元</div>
								</div>
							</div>
							
							<div class="col-md-1 inputLabel">买断价格</div>
							<div class="col-md-3">
								<div class="form-group">
								<c:choose>
									<c:when test="${isCreate}">
										<input type="text" class="form-control" data-bv-excluded="false" data-bv-field="club_buyout_price" id="club_buyout_price" name="club_buyout_price" style="width: 80%; float: left;"/>
									</c:when>
									<c:otherwise>
										<input type="text" class="form-control" data-bv-excluded="false" data-bv-field="club_buyout_price" id="club_buyout_price" name="club_buyout_price" style="width: 80%; float: left;" value="${ player.userExtInfoMap['club_buyout_price'] }" />
									</c:otherwise>
								</c:choose>
								<div class="inputLabel" style="width: 45px;float: left; text-align: center;">万元</div>
								</div>
							</div>
						</div>
						<div class="row profileEditItemLine">
							<div class="col-md-1 inputLabel">经纪人</div>
							<div class="col-md-3">
								<div class="form-group">
								<c:choose>
									<c:when test="${isCreate}">
										<input type="text" class="form-control" data-bv-excluded="false" data-bv-field="club_agent_name" id="club_agent_name" name="club_agent_name"/>
									</c:when>
									<c:otherwise>
										<input type="text" class="form-control" data-bv-excluded="false" data-bv-field="club_agent_name" id="club_agent_name" name="club_agent_name" value="${ player.userExtInfoMap['club_agent_name'] }" />
									</c:otherwise>
								</c:choose>
								</div>
							</div>
							<div class="col-md-1 inputLabel" style="padding-right:0px;">经纪人电话</div>
							<div class="col-md-3">
								<div class="form-group">
								<c:choose>
									<c:when test="${isCreate}">
										<input type="text" class="form-control" data-bv-excluded="false" data-bv-field="club_agent_tel" id="club_agent_tel" name="club_agent_tel" style="width: 80%;"/>
									</c:when>
									<c:otherwise>
										<input type="text" class="form-control" data-bv-excluded="false" data-bv-field="club_agent_tel" id="club_agent_tel" name="club_agent_tel" style="width: 80%;" value="${ player.userExtInfoMap['club_agent_tel'] }" />
									</c:otherwise>
								</c:choose>
								</div>
							</div>
							<div class="col-md-1 inputLabel" style="padding-right:0px;">经纪人邮箱</div>
							<div class="col-md-3">
								<div class="form-group">
									<c:choose>
										<c:when test="${isCreate}">
											<input type="text" class="form-control" data-bv-excluded="false" data-bv-field="club_agent_email" id="club_agent_email" name="club_agent_email" style="width: 80%;"/>
										</c:when>
										<c:otherwise>
											<input type="text" class="form-control" data-bv-excluded="false" data-bv-field="club_agent_email" id="club_agent_email" name="club_agent_email" style="width: 80%;" value="${ player.userExtInfoMap['club_agent_email'] }" />
										</c:otherwise>
									</c:choose>
								</div>
							</div>
						</div>
						<div class="row profileEditItemLine">
							<div class="col-md-1 inputLabel">合同到期</div>
							<div class="col-md-3">
								<div class="form-group">
								<c:choose>
									<c:when test="${isCreate}">
										<div class="input-group date">
											<input type="text" class="form-control profileEditInput calendar-input" id="club_contract_expiration" name="club_contract_expiration" readonly>
											<span id="club_contract_expirationIcon" class="input-group-addon calendar-icon"><i class="glyphicon glyphicon-calendar major_color"></i></span>
										</div>
									</c:when>
									<c:otherwise>
										<div class="input-group date">
											<input type="text" class="form-control profileEditInput calendar-input" id="club_contract_expiration" name="club_contract_expiration" value="${ player.userExtInfoMap['club_contract_expiration'] }"  readonly>
											<span id="club_contract_expirationIcon" class="input-group-addon calendar-icon"><i class="glyphicon glyphicon-calendar major_color"></i></span>
										</div>
									</c:otherwise>
								</c:choose>
								</div>
							</div>
							<div class="col-md-8"></div>
						</div>
					</sa-panel>
				</div>
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
	
	var strurl="<%=serverUrl%>user/showPlayerList";
	var msg="打开球员列表页面失败";
	function initData() {
		var isCreate = ${ isCreate };
		var select_field_ids = ['professional_physique', 'professional_primary_position', 'professional_secondary_position',
		                        'professional_preferred_foot', 'professional_type', 'technical_overall_rating',
		                        'technical_technique_rating', 'technical_teamwork_rating', 'technical_mentality_rating',
		                        'technical_fitness_rating'];
		
		if(!isCreate) {
			strurl= "<%=serverUrl%>user/showPlayerDetail?userID=" + "${player.id}";
			msg="打开球员详情页面失败";
			
			var physiqueValue = '${ player.userExtInfoMap["professional_physique"] }';
			$("#professional_physique option[value='" + physiqueValue + "']").attr('selected', 'selected');
			var primaryPositionValue = '${ player.userExtInfoMap["professional_primary_position"] }';
			$("#professional_primary_position option[value='" + primaryPositionValue + "']").attr('selected', 'selected');
			var secondaryPositionValue = '${ player.userExtInfoMap["professional_secondary_position"] }';
			$("#professional_secondary_position option[value='" + secondaryPositionValue + "']").attr('selected', 'selected');
			var preferredFootValue = '${ player.userExtInfoMap["professional_preferred_foot"] }';
			$("#professional_preferred_foot option[value='" + preferredFootValue + "']").attr('selected', 'selected');
			var professionalTypeValue = '${ player.userExtInfoMap["professional_type"] }';
			$("#professional_type option[value='" + professionalTypeValue + "']").attr('selected', 'selected');
			var overallRatingValue = '${ player.userExtInfoMap["technical_overall_rating"] }';
			$("#technical_overall_rating option[value='" + overallRatingValue + "']").attr('selected', 'selected');
			var techRatingValue = '${ player.userExtInfoMap["technical_technique_rating"] }';
			$("#technical_technique_rating option[value='" + techRatingValue + "']").attr('selected', 'selected');
			var teamworkRatingValue = '${ player.userExtInfoMap["technical_teamwork_rating"] }';
			$("#technical_teamwork_rating option[value='" + teamworkRatingValue + "']").attr('selected', 'selected');
			var mentalRatingValue = '${ player.userExtInfoMap["technical_mentality_rating"] }';
			$("#technical_mentality_rating option[value='" + mentalRatingValue + "']").attr('selected', 'selected');
			var fitnessRatingValue = '${ player.userExtInfoMap["technical_fitness_rating"] }';
			$("#technical_fitness_rating option[value='" + fitnessRatingValue + "']").attr('selected', 'selected');
		}
		
		$.each(select_field_ids, function(index, value) {
			var $select = $('#' + select_field_ids[index]);
			
			$select.select2({
				minimumResultsForSearch : Infinity
			});
		});
		
		if($('#user_avatar').is('.custom-image')) {
			$('#user_avatar').data('encryptFileName', '${ player.avatar }');
		}
	}
	
	function initEvent() {
		buildBreadcumb("新增/修改球员");
		var isCreate = ${ isCreate };
		var $birthday = $('#birthday');
		var $club_contract_expiration = $('#club_contract_expiration');
		validation();
		$birthday.datepicker({
			format : 'yyyy-mm-dd',
			language : 'zh-CN',
			autoclose : true,
			todayHighlight : true,
			toggleActive : true,
			startView:2,
			zIndexOffset:1031
		});
		$club_contract_expiration.datepicker({
			format : 'yyyy-mm-dd',
			language : 'zh-CN',
			autoclose : true,
			todayHighlight : true,
			toggleActive : true
		});
		$('#birthdayIcon').click(function() {
			$birthday.datepicker('show');
		});
		$('#club_contract_expirationIcon').click(function() {
			$club_contract_expiration.datepicker('show');
		});
		
		$('#save_btn').click(function() {
			$("#player_form").data('bootstrapValidator').validate();
			if(!$("#player_form").data('bootstrapValidator').isValid()){
				return;
			}
			
			var form_data = $('#player_form').serializeArray();
			var converted_ext_data = [];
			var converted_base_data = {'id': '<%=userId%>', 'roleName': 'player', 'avatar': $('#user_avatar').data('encryptFileName') || ''};
			//var need_convert_multi_fields = ['professional_description'];
			var basic_user_fields = ['name', 'birthday', 'passport', 'nationality', 'birthPlace', 'idCard', 'email', 'tel', 'homeAddress']; 
			var date_fields = ['birthday'];
			
			$.each(form_data, function(index, item) {
				//var isConvert = $.inArray(item['name'], need_convert_multi_fields) > -1;
				var isConvert = false;
				
				if($.inArray(item['name'], date_fields) > -1) {
					item['value'] += ' 00:00:00'; // String -> java.sql.Timestamp MUST be 'yyyy-MM-dd hh:mm:ss' format
				}
				
				if($.inArray(item['name'], basic_user_fields) > -1) {
					converted_base_data[item['name']] = isConvert ? item['value'].replace(/\r/ig, "") : item['value'];
				} else {
					converted_ext_data.push({'itemName': item['name'], 'itemValue': isConvert ? item['value'].replace(/\r/ig, "") : item['value']});
				}
			});
			
			var submitted_data = {'userItemList': converted_ext_data, 'basicData': converted_base_data};
			
			sa.ajax({
				type : "post",
				url : "<%=serverUrl%>user/saveUser",
				data: JSON.stringify(submitted_data),
				contentType: "application/json",
				success : function(data) {
					if(!data.status) {
						alert("提交球员信息异常");
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
					alert("提交球员信息失败");
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
		
		// avatar upload
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
		$("#player_form").bootstrapValidator({
	        message: '您输入的值不合法。',
	        feedbackIcons: {
	            valid: 'glyphicon glyphicon-ok',
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	        	name:{
	        		message: '姓名不合法',
		           	validators: {
		                    notEmpty: {
		                        message: '姓名是必填项，请正确填写姓名。'
		                    },
		                    stringLength: {
		                         max: 30,
		                         message: '姓名长度超过限制，请输入30字符以内的姓名'
		                    }
		                }
	        	},
	        	birthPlace:{
	        		message: '出生地不合法',
		           	 validators: {
		           		stringLength: {
		           			max: 500,
		                    message: '出生地不合法'
		                  }
		             }
	        	},
	        	passport:{
	        		message: '护照号不合法',
		           	 validators: {
		           		stringLength: {
		           			max: 500,
		                    message: '护照号不合法'
		                }
		           }
	        	},
	        	nationality:{
	        		message: '国籍不合法',
		           	 validators: {
		           		stringLength: {
		           			max: 500,
		                    message: '国籍不合法'
		                }
		            }
	        	},
	        	idCard:{
	        		message: '身份证号不合法',
	        		 validators: {
			           		stringLength: {
			           			max: 500,
			                    message: '身份证号不合法'
			                }
			            }
	        	},
	        	tel:{
	        		message: '电话号码不合法',
		           	validators: {
			           	 regexp: {
	                         regexp: /(^1\d{10}$|^(0\d{2,3}-?|\(0\d{2,3}\))?[1-9]\d{4,7}(-\d{1,8})?$|^1\d{10}$)/,
	                         message: '电话号码不合法。'
	                     }
		            }
	        	},
	        	homeAddress:{
	        		message: '家庭住址不合法',
		           	 validators: {
		           		stringLength: {
		           			max: 500,
		                    message: '家庭住址不合法。'
		                }
		            }
	        	},
	            email:{
		           	 message: '邮箱不合法',
		           	 validators: {
		                    
		                    emailAddress: {
		                        message: '请正确填写您的邮箱。'
		                    }
		             }
	           },
	           professional_tall:{
	        	   message: '身高填写不合法',
		           validators: {
		           		integer:{
		           			message: '身高填写不合法'
		           		}  
		            }
	           },
	           professional_jersey_number:{
	        	   message: '号码填写不合法',
		           validators: {
		        	    notEmpty: {
	                        message: '号码是必填项，请正确填写号码。'
	                    },
		           		integer:{
		           			message: '号码填写不合法'
		           		}  
		            }	        	   
	           },
	           professional_weight:{
	        	   message: '体重填写不合法',
		           validators: {
		           		integer:{
		           			message: '体重填写不合法'
		           		}  
		            }	        	   
	           },
	           professional_waist:{
	        	   message: '腰围填写不合法',
		           validators: {
		           		integer:{
		           			message: '腰围填写不合法'
		           		}  
		            }	        	   
	           },
	           club_club_name:{
	        	   message: '俱乐部名称不合法',
		           validators: {
	                    stringLength: {
	                         max: 500,
	                         message: '俱乐部名称不合法'
	                    }
	                }
	           },
	           club_agent_name:{
	        	   message: '经纪人名称不合法',
		           validators: {
	                    stringLength: {
	                         max: 500,
	                         message: '经纪人名称不合法'
	                    }
	                }
	           },
	           club_buyout_price:{
	        	   message: '买断价格不合法',
		           validators: {
		        	   numeric: {
	                         message: '买断价格不合法'
	                    }
	                }
	           },
	          /* club_contract_expiration:{
	        	   message: '合同到期不合法',
		           validators: {
		        	   notEmpty: {
	                       message: '合同到期不能为空'
	                   }, 
		        	   date: {
	                         message: '合同到期不合法'
	                    }
	                }
	           },*/
	           club_agent_tel:{
	        	   message: '经纪人电话不合法',
	        	   validators: {
			           	 regexp: {
	                         regexp: /(^1\d{10}$|^(0\d{2,3}-?|\(0\d{2,3}\))?[1-9]\d{4,7}(-\d{1,8})?$|^1\d{10}$)/,
	                         message: '经纪人电话不合法。'
	                     }
		            }
	           },
	           club_market_price:{
	        	   message: '市场价格不合法',
		           validators: {
		        	   numeric: {
	                         message: '市场价格不合法'
	                    }
	                }
	           },
	           club_agent_email:{
	        	   message: '经纪人邮箱不合法',
		           validators: {
		        	   emailAddress: {
	                        message: '经纪人邮箱不合法'
	                    }
	                }
	           }
	        }
	    });
	}
</script>
