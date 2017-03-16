<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="xss" uri="http://www.sportsdata.cn/xss"%>

<%@ page import="cn.sportsdata.webapp.youth.common.utils.CommonUtils" %>

<%
	String serverUrl = CommonUtils.getServerUrl(request);
    String stID="null";
	if(session.getAttribute("selectedTacticsId")!=null && session.getAttribute("selectedTacticsId")!=""){
		stID=session.getAttribute("selectedTacticsId").toString();
	}
%>
<style type="text/css">
.starter-item{
	float:left;
	width: 224px;
	margin:10px 0px 0px 10px;
	border:1px solid #cecece;
}

.starter-image {
    width:222px;
    height:140px;
    cursor:pointer;
}
.starter-title{
	margin: 5px 0 5px 5px;
	text-align: center; 
	font-size: 14px;
	overflow:hidden;
	text-overflow: ellipsis;
	white-space: nowrap;
}
.starter-name{
	font-size: 14px;
	color:#333;
	display: inline;
    font-weight: normal;
    margin-bottom: 0px;
    max-width: 90%;
    cursor:pointer;
}
</style>


<div class="profileEditContainer">
	<div class="button_area" id="button_area"  style="top:45px;">
		<div>
			<button id="tactics_save_btn" class="btn btn-primary" style="float: right; margin-left: 10px;">保存</button>
			<button id="tactics_cancel_btn" class="btn btn-default" style="float: right; margin-left: 10px;">取消</button>
		</div>
	</div>
	<div class="clearfix" style="height:15px;"></div>
	<div id="tacticsListArea" class="panel-body table-responsive" style="border: 0px none; padding: 0px;">
		<sa-panel-card title="战术板"> 
			<c:forEach items="${tacticsList}" var="tactics">
				<div class="starter-item">
					<c:choose>
						<c:when test="${ !empty tactics.imgUrl }">
							<img class="starter-image" src="${ tactics.imgUrl }" onclick="editTactics('${ tactics.id }')"/>
						</c:when>
						<c:otherwise>
							<img class="starter-image" src="<%=serverUrl%>resources/images/soccerboard/ic_tactics_single.png" onclick="editTactics('${ tactics.id }')"/>
						</c:otherwise>
					</c:choose>
					<div class="starter-title">
						<input type="radio" name="tactics_list" value="${ tactics.id }" id="tac${ tactics.id }" <c:if test="${tactics.checked}">checked</c:if> />&nbsp;<label class="starter-name" title='<xss:xssFilter text="${ tactics.name }" filter="html"/>' for="tac${ tactics.id }"> <xss:xssFilter text="${ tactics.name }" filter="html"/></label>
					</div>
				</div>
			</c:forEach> 
			<div class="starter-item" style="border:0;">
				<img class="starter-image" src="<%=serverUrl%>resources/images/photo_plus.png" onclick="newTactics()"/>
			</div>
		</sa-panel-card>
	</div>
</div>
<script type="text/javascript">
	$(function () {
		setTimeout(function(){
			initTacticsEvent();
		});
	});
    function editTactics(tid){
		$("#tactics_panel").loadAngular("<%=serverUrl%>system_tool/showTacticsEdit?tacticsID="+tid+"&pFlag=single"); 
    }
    function newTactics(){
		$("#tactics_panel").loadAngular("<%=serverUrl%>system_tool/showTacticsEdit?pFlag=single");
    }
    function isExist_tacticsId(){
    	var tID="<%=stID%>";
    	if(tID!="null"){
    		var tt=0;
        	<c:forEach items="${tacticsList}" var="tactics">
        		if('${tactics.id}'==tID){
        			tt=1;
        		}
        	</c:forEach> 
        	if(tt==0){
        		//removeTactics();
        		$('#selectedStarterArea').html('').html(removeTemplateCompiled());
        	}
    	}
    }
    function escapeHTMLforName(source){
		  var filtered = "";
		  var retStr = "";
		  for (var i = 0; i < source.length; i++) {
			 filtered = source.charAt(i);
		     switch (filtered) {
				case '<':
					filtered = "&lt;";
					break;
				case '>':
					filtered = "&gt;";
					break;
				case '&':
					filtered = "&amp;";
					break;
				case '"':
					filtered = "&quot;";
					break;
				case '\'':
					filtered = "&#39;";
					break;
			}
			retStr = retStr + filtered;
		  }
		  return retStr;
		}
    
	function initTacticsEvent() {
		$('#tactics_cancel_btn').off('click');
		$('#tactics_cancel_btn').click(function() {
			isExist_tacticsId();
			$('#starter_modal').modal('hide');
		});
		
		$('#tactics_save_btn').off('click');
		$('#tactics_save_btn').click(function() {
			var $targets = $('#tacticsListArea').find('input[name="tactics_list"]:checked');
			if($targets.length==0) {
				isExist_tacticsId();
			}else{
				var $root = $targets.parents('div.starter-item');
				var sdata = {'imgSrc': $root.find('.starter-image').attr('src'), 'id': $targets.val(),'title':escapeHTMLforName($root.find('.starter-name').text()),'name':escapeHTMLforName($root.find('.starter-name').text())};
				$('#selectedStarterArea').html('').html(starterTemplateCompiled(sdata));
			}
			$('#starter_modal').modal('hide');
		});
	}
</script>