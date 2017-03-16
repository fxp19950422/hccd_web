<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="xss" uri="http://www.sportsdata.cn/xss"%>
<%@ page import="cn.sportsdata.webapp.youth.common.utils.CommonUtils"%>
<%@ page import="cn.sportsdata.webapp.youth.common.vo.UserVO"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.sql.Timestamp"%>
<!-- data-visible -->
<%
	UserVO playerInfo = (UserVO) request.getAttribute("player");
	String age = "暂无年龄信息";
	Timestamp birthday = playerInfo.getBirthday();
	String serverUrl = CommonUtils.getServerUrl(request);
	if (birthday != null) {
		age = String.valueOf(Calendar.getInstance().get(Calendar.YEAR)
				- Integer.parseInt(new SimpleDateFormat("yyyy").format(birthday))) + "岁";
	}
%>
<script type="text/javascript">
$(function(){
	buildBreadcumb("单个成员身体指标");
	$("#create_time").datepicker({
		format : "yyyy-mm-dd",
		language : "zh-CN",
		autoclose : true,
		todayHighlight : true,
		toggleActive : true,
		zIndexOffset:1031
	});
	$("#create_time").datepicker('setEndDate', '+0d');
	if($("#create_time").val()==""){
		$("#create_time").datepicker('setDate', '+0d');
	}
	$('#creator_id').select2({
		minimumResultsForSearch: Infinity
	});
	
	$("#healthform").bootstrapValidator({
        message: '您输入的值不合法。',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	height:{
        		message: '体重不合法',
	           	 validators: {
	                    stringLength: {
	                         max: 7,
	                         message: '长度超过限制'
	                    },
	                    regexp: {
	                         regexp:/^[1-9]\d*$|^\d+\.{1}\d+$/,
	                         message: '体重不合法，请输入整数或者小数。'
	                     }
	                }
        	},
        	shoulder:{
        		message: '肩胛脂不合法',
	           	 validators: {
                    stringLength: {
                        max: 7,
                        message: '长度超过限制'
                   },
                    regexp: {
                         regexp: /^[1-9]\d*$|^\d+\.{1}\d+$/,
                         message: '肩胛脂不合法，请输入整数或者小数。'
                     }
	             }
        	},
        	lactate:{
        		message: '乳酸不合法',
        		 validators: {
                     stringLength: {
                         max: 7,
                         message: '长度超过限制'
                     },
                     regexp: {
                          regexp: /^[1-9]\d*$|^\d+\.{1}\d+$/,
                          message: '乳酸不合法，请输入整数或者小数。'
                      }
 	             }
        	},
        	haunch:{
        		message: '大臂脂不合法',
        		validators: {
                     stringLength: {
                         max: 7,
                         message: '长度超过限制'
                     },
                     regexp: {
                          regexp: /^[1-9]\d*$|^\d+\.{1}\d+$/,
                          message: '大臂脂不合法，请输入整数或者小数。'
                      }
 	             }
        	},
        	chest:{
        		message: '胸围不合法',
        		validators: {
                     stringLength: {
                         max: 7,
                         message: '长度超过限制'
                     },
                     regexp: {
                          regexp: /^[1-9]\d*$|^\d+\.{1}\d+$/,
                          message: '胸围不合法，请输入整数或者小数。'
                      }
 	             }
        	},
        	oxygen_content:{
        		message: '含氧量不合法',
        		validators: {
                     stringLength: {
                         max: 7,
                         message: '长度超过限制'
                  	  },
                     regexp: {
                          regexp: /^[1-9]\d*$|^\d+\.{1}\d+$/,
                          message: '含氧量不合法，请输入整数或者小数。'
                      }
 	             }
        	},
           waist:{
	           	 message: '腰围不合法',
	           		validators: {
	                     stringLength: {
	                         max: 7,
	                         message: '长度超过限制'
	                     },
	                     regexp: {
	                          regexp: /^[1-9]\d*$|^\d+\.{1}\d+$/,
	                          message: '腰围不合法，请输入整数或者小数。'
	                      }
	             }
         },
         morning_pulse:{
           	 message: '晨脉不合法',
           		validators: {
                     stringLength: {
                         max: 7,
                         message: '长度超过限制'
                     },
                     regexp: {
                          regexp: /^[1-9]\d*$/,
                          message: '晨脉不合法，请输入整数。'
                      }
             }
     	},
        waistfat:{
          	 message: '腰围脂不合法',
          		validators: {
                    stringLength: {
                        max: 7,
                        message: '长度超过限制'
                    },
                    regexp: {
                         regexp: /^[1-9]\d*$|^\d+\.{1}\d+$/,
                         message: '腰围脂不合法，请输入整数或者小数。'
                     }
            }
    	},
     	weight:{
            	 message: '体重不合法',
            		validators: {
                      stringLength: {
                          max: 7,
                          message: '长度超过限制'
                      },
                      regexp: {
                           regexp: /^[1-9]\d*$|^\d+\.{1}\d+$/,
                           message: '体重不合法，请输入整数或者小数。'
                       }
              }
      	}
        }
    });
	
	
})

function save(){
	var data={};
	data.creator_id=$("#creator_id").children('option:selected').val()
	data.create_time=$("#create_time").val();
	data.height=$("#height").val();
	data.weight=$("#weight").val();
	data.oxygen_content=$("#oxygen_content").val();
	data.shoulder=$("#shoulder").val();
	data.haunch=$("#haunch").val();
	data.hanch=$("#chest").val();
	data.waist=$("#waist").val();
	data.morning_pulse=$("#morning_pulse").val();
	data.lactate=$("#lactate").val();
	data.user_id='${id}';
	data.chest=$("#chest").val();
	data.waistfat=$("#waistfat").val();
	if($("#healthid").val()!=""){
		data.id=$("#healthid").val();
	}
	var postdata=  JSON.stringify(data)
	$("#healthform").data('bootstrapValidator').validate();
	if(!$("#healthform").data('bootstrapValidator').isValid()){
		return;
	}
	

	$.ajax({
		type : 'POST',
		url : '<%=serverUrl%>healthdata/handlehealthdata/${update}',
		contentType : "application/json; charset=utf-8",
		dataType : 'json',
		data : postdata,
		success : function(message) {
			if(message){
				var url ="<%=serverUrl%>/healthdata/goonepeoplehealthdata?userID=${id}&backurl=${backurl}";
				sa.ajax({
					type : "get",
					url : url,
					success : function(data) {
						AngularHelper.Compile($('#content'), data);
					},
					error: function() {
						alert("页面打开失败");
					}
				});
			}else{
				alert("保存身体指标信息失败");
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("保存身体指标信息失败");
		}
	}); 
	
}

function back() {
	var url ="<%=serverUrl%>/healthdata/goonepeoplehealthdata?userID=${id}&backurl=${backurl}";
	sa.ajax({
		type : "get",
		url : url,
		success : function(data) {
			AngularHelper.Compile($('#content'), data);
		},
		error: function() {
			alert("页面打开失败");
		}
	});
}
</script>
<style type="text/css">
.healthlabel {
	float: left;
	color: #ccc;
	line-height: 28px;
}
</style>
	<div class="profileDetailHeader">
		<div class="profileInfo">
			<c:choose>
				<c:when test="${ !empty player.avatar }">
					<img class="profileDetailAvatar"
						src="<%=serverUrl%>file/downloadFile?fileName=${ player.avatar }"></img>
				</c:when>
				<c:otherwise>
					<img class="profileDetailAvatar"
						src="<%=serverUrl%>resources/images/user_avatar.png"></img>
				</c:otherwise>
			</c:choose>

			<div class="profileDetailName">
				<c:choose>
					<c:when test="${ !empty player.name }">
						<xss:xssFilter text="${player.name}" filter="html" />
					</c:when>
					<c:otherwise>
						暂无姓名
					</c:otherwise>
				</c:choose>
			</div>
			<div class="profileDetailData">
				<span><%=age%></span> <span>&nbsp;/&nbsp;</span> <span> <c:choose>
						<c:when test="${ !empty player.nationality }">
							${ player.nationality }
						</c:when>
						<c:otherwise>
							暂无国籍信息
						</c:otherwise>
					</c:choose>
				</span> <span>&nbsp;/&nbsp;</span> <span> <c:choose>
						<c:when
							test="${ !empty player.userExtInfoMap['professional_jersey_number'] }">
							${ player.userExtInfoMap['professional_jersey_number'] }号
						</c:when>
						<c:otherwise>
							暂无号码信息
						</c:otherwise>
					</c:choose>
				</span> <span>&nbsp;/&nbsp;</span> <span> <c:choose>
						<c:when test="${ !empty player.translatedPosition }">
							${ player.translatedPosition }
						</c:when>
						<c:otherwise>
							暂无位置信息
						</c:otherwise>
					</c:choose>
				</span>
			</div>
		</div>
		<div class="profileAction button_area" style="padding-top: 78px;">
			<button  id="save_btn" onclick="save()"   class="btn btn-primary" style="float: right;margin-left: 10px;">保存</button>
			<button id="back_btn"  onclick="back()" class="btn btn-default"   style="float: right;margin-left: 10px">取消</button>
	    </div>
		
	</div>
	<div
		style="clear: both; background: #337ab7; height: 36px; margin-top: 99px; line-height: 36px;">
		<span style="font-size: 16px; color: #ffffff; margin-left: 10px;">身体指标</span>
	</div>
	<input type="hidden" id="healthid" value="${data.id }">
	<form id="healthform">
					<div class="col-md-12" style="padding-top: 18px;background-color: #ffffff;">
								<div class="row">
									<div class="col-md-1 inputLabel">记录人</div>
									<div class="col-md-3">
											<div class="form-group">
												<select name="creator_id" id="creator_id" class="form-control">
													<c:forEach var="coach" items="${coachs}" varStatus="status">    
														<c:choose>
      															 <c:when test="${coach.id ==data.creator_id}">
           															<option value="${coach.id}"  selected = "selected"><xss:xssFilter text="${coach.name}" filter="html" /></option>
     															  </c:when>
														       <c:otherwise>
														           <option value="${coach.id}"  <c:if test="{coach.id==id}"> selected = "selected" </c:if>><xss:xssFilter text="${coach.name}" filter="html" /></option>
														       </c:otherwise>
														</c:choose>
													</c:forEach>
												</select>
											</div>
									</div>
									<div class="col-md-1 inputLabel">记录时间</div>
									<div class="col-md-3">
												<div class="input-group date">
													<input value="${data.create_time}" type="text" class="form-control profileEditInput calendar-input" id="create_time" name="create_time" readonly>
													<span  class="input-group-addon calendar-icon"><i class="glyphicon glyphicon-calendar major_color"></i></span>
												</div>
									</div>
									<div class="col-md-1 inputLabel">体重</div>
									<div class="col-md-3">
										<div class="form-group">
												<input type="text" class="form-control" value="<c:if test="${ data.weight > 0 }">${ data.weight }</c:if>" id="weight" name="weight" />
										</div>
									</div>
								</div>
								<div class="row profileEditItemLine">
									<div class="col-md-1 inputLabel">肩胛脂</div>
									<div class="col-md-3">
									<div class="form-group">
										<input type="text" value="<c:if test="${ data.shoulder > 0 }">${ data.shoulder }</c:if>"  class="form-control" id="shoulder" name="shoulder" />
								    </div>
									</div>
									<div class="col-md-1 inputLabel">乳酸</div>
									<div class="col-md-3">
										<div class="form-group">
											<input type="text" class="form-control" value="<c:if test="${ data.lactate > 0 }">${ data.lactate }</c:if>" id="lactate" name="lactate"  />
										</div>
									</div>
									<div class="col-md-1 inputLabel">大臂脂</div>
									<div class="col-md-3">
										<div class="form-group">
												<input type="text" value="<c:if test="${ data.haunch > 0 }">${ data.haunch }</c:if>" class="form-control" id="haunch" name="haunch" />
										</div>
									</div>
								</div>
								<div class="row profileEditItemLine">
									<div class="col-md-1 inputLabel">胸围</div>
									<div class="col-md-3">
										<div class="form-group">
												<input type="text" value="<c:if test="${ data.chest > 0 }">${ data.chest }</c:if>" class="form-control" id="chest" name="chest" />
										</div>
									</div>
									<div class="col-md-1 inputLabel">含氧量</div>
									<div class="col-md-3">
										<div class="form-group">
												<input type="text" value="<c:if test="${ data.oxygen_content > 0 }">${ data.oxygen_content }</c:if>" class="form-control" id="oxygen_content" name="oxygen_content" />
										</div>
									</div>
									<div class="col-md-1 inputLabel">身高</div>
									<div class="col-md-3">
										<div class="form-group">
												<input type="text" value="<c:if test="${ data.height > 0 }">${ data.height }</c:if>" class="form-control" id="height"  name="height" />
										</div>
									</div>
								</div>
								<div class="row profileEditItemLine">
									<div class="col-md-1 inputLabel">腰围</div>
									<div class="col-md-3">
										<div class="form-group">
												<input type="text" value="<c:if test="${ data.waist > 0 }">${ data.waist }</c:if>" class="form-control" id="waist" name="waist" />
										</div>
									</div>
									<div class="col-md-1 inputLabel">晨脉</div>
									<div class="col-md-3">
										<div class="form-group">
												<input type="text" value="<c:if test="${ data.morning_pulse > 0 }">${ data.morning_pulse }</c:if>" class="form-control" id="morning_pulse" name="morning_pulse" />
										</div>
									</div>
									<div class="col-md-1 inputLabel">腰围脂</div>
									<div class="col-md-3">
										<div class="form-group">
												<input type="text" value="<c:if test="${ data.waistfat > 0 }">${ data.waistfat }</c:if>" class="form-control" id="waistfat" name="waistfat" />
										</div>
									</div>
									
								</div>
					</div>
		
	</form>
			
