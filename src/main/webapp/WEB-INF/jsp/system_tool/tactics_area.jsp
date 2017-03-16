<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="xss" uri="http://www.sportsdata.cn/xss"%>

<%@ page import="cn.sportsdata.webapp.youth.common.utils.CommonUtils"%>
<%@ page import="cn.sportsdata.webapp.youth.common.vo.TacticsVO"%>


<%
	String serverUrl = CommonUtils.getServerUrl(request);

	boolean isCreate = (Boolean) request.getAttribute("isCreate");
	//UserVO vo = (UserVO) request.getAttribute("player");
	//long userId = isCreate ? 0 : vo.getId();
%>

<link rel="stylesheet"
	href="<%=serverUrl%>resources/css/soccerboard/reset.css">
<link rel="stylesheet" type="text/css"
	href="<%=serverUrl%>resources/css/soccerboard/default.css">
<link rel="stylesheet"
	href="<%=serverUrl%>resources/css/soccerboard/style.css">

<script src="<%=serverUrl%>resources/js/soccerboard/modernizr.js"></script>
<script
	src="<%=serverUrl%>resources/js/soccerboard/jquery.mobile.custom.min.js"></script>
<script src="<%=serverUrl%>resources/js/soccerboard/controler.js"></script>
<script src="<%=serverUrl%>resources/js/soccerboard/canvas2image.js"></script>
<script src="<%=serverUrl%>resources/js/soccerboard/base64.js"></script>
<script src="<%=serverUrl%>resources/js/soccerboard/com.js"></script>
<script src="<%=serverUrl%>resources/js/soccerboard/touchable.js"></script>

<style>
.img_wd {
	background:
		url(<%=serverUrl%>resources/images/soccerboard/ic_red_normal.png) top
		center no-repeat;
	background-size: 100% 100%;
	width: 30px;
	height: 30px;
	line-height: 30px;
	text-align: center; <!--
	position: absolute;
	-->
}

.img_wd_2 {
	background:
		url(<%=serverUrl%>resources/images/soccerboard/ic_blue_normal.png) top
		center no-repeat;
	background-size: 100% 100%;
	width: 30px;
	height: 30px;
	line-height: 30px;
	text-align: center; <!--
	position: absolute;
	-->
}

.test_icon {
	background:
		url(<%=serverUrl%>resources/images/soccerboard/ic_blue_normal.png) top
		center no-repeat;
	background-size: 100% 100%;
	width: 20px;
	height: 20px;
	line-height: 20px;
	display: block;
}

.toolbar-toolbar_background {
	background: #fff;
	background-color: #000000;
	background-color: rgba(0, 0, 0, 0.2);
 	height: 100%px;
	/* line-height: 20px; */
	z-index:9999;
}
</style>
<div class="box" id="box" style="width: 100%">
	<div style="width: 100%">
		<canvas id="soccerboard" style="position: absolute; left: 0; top: 0;">
                适用浏览器：360、FireFox、Chrome、Safari、Opera、傲游、搜狗、世界之窗. 不支持IE8及以下浏览器。
            </canvas>
		<canvas id="soccerboard_move"
			style="position: absolute; left: 0; top: 0;">
                适用浏览器：360、FireFox、Chrome、Safari、Opera、傲游、搜狗、世界之窗. 不支持IE8及以下浏览器。
            </canvas>
		<canvas id="soccerboard_mask"
			style="position: absolute; left: 0; top: 0;">
                适用浏览器：360、FireFox、Chrome、Safari、Opera、傲游、搜狗、世界之窗. 不支持IE8及以下浏览器。
            </canvas>

		<div style="position: absolute; left: 0px; top: 0px;">
			<div style="" id="ic_red" name="ic_red" class="img_wd"
				draggable="true"
				onMouseOver="changebg('ic_red','<%=serverUrl%>resources/images/soccerboard/ic_red_pressed.png')"
				onMouseOut="changebg('ic_red','<%=serverUrl%>resources/images/soccerboard/ic_red_normal.png')">1</div>
			<div style="" id="ic_blue" name="ic_blue"
				class="img_wd_2" draggable="true" 
				onMouseOver="changebg('ic_blue','<%=serverUrl%>resources/images/soccerboard/ic_blue_pressed.png')"
				onMouseOut="changebg('ic_blue','<%=serverUrl%>resources/images/soccerboard/ic_blue_normal.png')">1
			</div>
			<div style="clear: both;height:30px"  id="ic_formation_list_div">
				<input type="image"
					src="<%=serverUrl%>resources/images/soccerboard/ic_formation_pressed.png"
					name="ic_formation" width="30px" height="30px" border="0" title="阵型"
					id="ic_formation"
					bPressed="false" 
					onMouseOver="MM_swapImage('ic_formation','','<%=serverUrl%>resources/images/soccerboard/ic_formation.png',1);$('#ic_formation_list').show();"
					onMouseOut="if($(this).attr('bPressed') == 'false') {MM_swapImgRestore();}"
					style="float:left">
				<div id="ic_formation_list" style="display:none;float:left;height:30px;">
					<input type="image"
						src="<%=serverUrl%>resources/images/soccerboard/ic_formation_121_normal.png"
						name="ic_formation1121" width="30px" height="30px" border="0"
						id="ic_formation1121"
						onMouseOver="MM_swapImage('ic_formation1121','','<%=serverUrl%>resources/images/soccerboard/ic_formation_121_pressed.png',1)"
						onMouseOut="MM_swapImgRestore()"> 
					<input type="image"
						src="<%=serverUrl%>resources/images/soccerboard/ic_formation_22_normal.png"
						name="ic_formation122" width="30px" height="30px" border="0"
						id="ic_formation122"
						onMouseOver="MM_swapImage('ic_formation122','','<%=serverUrl%>resources/images/soccerboard/ic_formation_22_pressed.png',1)"
						onMouseOut="MM_swapImgRestore()"> 
					<input type="image"
						src="<%=serverUrl%>resources/images/soccerboard/ic_formation_31_normal.png"
						name="ic_formation131" width="30px" height="30px" border="0"
						id="ic_formation131"
						onMouseOver="MM_swapImage('ic_formation131','','<%=serverUrl%>resources/images/soccerboard/ic_formation_31_pressed.png',1)"
						onMouseOut="MM_swapImgRestore()"> 
					<input type="image"
						src="<%=serverUrl%>resources/images/soccerboard/ic_formation_331_normal.png"
						name="ic_formation1331" width="30px" height="30px" border="0"
						id="ic_formation1331"
						onMouseOver="MM_swapImage('ic_formation1331','','<%=serverUrl%>resources/images/soccerboard/ic_formation_331_pressed.png',1)"
						onMouseOut="MM_swapImgRestore()"> 
					<input type="image"
						src="<%=serverUrl%>resources/images/soccerboard/ic_formation_322_normal.png"
						name="ic_formation1322" width="30px" height="30px" border="0"
						id="ic_formation1322"
						onMouseOver="MM_swapImage('ic_formation1322','','<%=serverUrl%>resources/images/soccerboard/ic_formation_322_pressed.png',1)"
						onMouseOut="MM_swapImgRestore()"> 
					<input type="image"
						src="<%=serverUrl%>resources/images/soccerboard/ic_formation_433_normal.png"
						name="ic_formation1433" width="30px" height="30px" border="0"
						id="ic_formation1433"
						onMouseOver="MM_swapImage('ic_formation1433','','<%=serverUrl%>resources/images/soccerboard/ic_formation_433_pressed.png',1)"
						onMouseOut="MM_swapImgRestore()"> 
					<input type="image"
						src="<%=serverUrl%>resources/images/soccerboard/ic_formation_442_normal.png"
						name="ic_formation1442" width="30px" height="30px" border="0"
						id="ic_formation1442"
						onMouseOver="MM_swapImage('ic_formation1442','','<%=serverUrl%>resources/images/soccerboard/ic_formation_442_pressed.png',1)"
						onMouseOut="MM_swapImgRestore()"> 
					<input type="image"
						src="<%=serverUrl%>resources/images/soccerboard/ic_formation_451_normal.png"
						name="ic_formation1451" width="30px" height="30px" border="0"
						id="ic_formation1451"
						onMouseOver="MM_swapImage('ic_formation1451','','<%=serverUrl%>resources/images/soccerboard/ic_formation_451_pressed.png',1)"
						onMouseOut="MM_swapImgRestore()">
				</div>
					
			</div>
			<div style="clear: both;height:30px" id="ic_tools_list_div"">
				<input type="image"
					src="<%=serverUrl%>resources/images/soccerboard/ic_tools_normal.png"
					name="ic_tools" width="30" height="30" border="0" id="ic_tools"
					title="工具"
					onMouseOver="MM_swapImage('ic_tools','','<%=serverUrl%>resources/images/soccerboard/ic_tools_pressed.png',1);$('#ic_tools_list').show();"
					onMouseOut="if($(this).attr('bPressed') == 'false') {MM_swapImgRestore();}"
					bPressed="false" 
					style="float:left">
				<div id="ic_tools_list"  style="display:none;float:left;height:30px;">
					<input type="image"
						src="<%=serverUrl%>resources/images/soccerboard/ic_ball_normal.png" 
						name="ic_ball" width="30px" height="30px" border="0" id="ic_ball" 
						onclick="com.ic_tool_click(this);"
						onMouseOver="MM_swapImage('ic_ball','','<%=serverUrl%>resources/images/soccerboard/ic_ball_pressed.png',1)" 
						onMouseOut="MM_swapImgRestore()"> <input type="image" 
						src="<%=serverUrl%>resources/images/soccerboard/ic_bar_normal.png" 
						name="ic_bar" width="30px" height="30px" border="0" id="ic_bar" 
						onclick="com.ic_tool_click(this);"
						onMouseOver="MM_swapImage('ic_bar','','<%=serverUrl%>resources/images/soccerboard/ic_bar_pressed.png',1)" 
						onMouseOut="MM_swapImgRestore()"> <input type="image" 
						src="<%=serverUrl%>resources/images/soccerboard/ic_goal_normal.png"
						name="ic_goal" width="30px" height="30px" border="0" id="ic_goal" 
						onclick="com.ic_tool_click(this);"
						onMouseOver="MM_swapImage('ic_goal','','<%=serverUrl%>resources/images/soccerboard/ic_goal_pressed.png',1)" 
						onMouseOut="MM_swapImgRestore()"> <input type="image" 
						src="<%=serverUrl%>resources/images/soccerboard/ic_trash_yellow_normal.png"
						name="ic_trash_yellow" width="30px" height="30px" border="0" 
						id="ic_trash_yellow" 
						onclick="com.ic_tool_click(this);"
						onMouseOver="MM_swapImage('ic_trash_yellow','','<%=serverUrl%>resources/images/soccerboard/ic_trash_yellow_pressed.png',1)" 
						onMouseOut="MM_swapImgRestore()"> <input type="image" 
						src="<%=serverUrl%>resources/images/soccerboard/ic_trash_red_normal.png" 
						name="ic_trash_red" width="30px" height="30px" border="0" 
						id="ic_trash_red" 
						onclick="com.ic_tool_click(this);"
						onMouseOver="MM_swapImage('ic_trash_red','','<%=serverUrl%>resources/images/soccerboard/ic_trash_red_pressed.png',1)" 
						onMouseOut="MM_swapImgRestore()"> <input type="image" 
						src="<%=serverUrl%>resources/images/soccerboard/ic_trash_blue_normal.png" 
						name="ic_trash_blue" width="30px" height="30px" border="0" 
						id="ic_trash_blue" 
						onclick="com.ic_tool_click(this);"
						onMouseOver="MM_swapImage('ic_trash_blue','','<%=serverUrl%>resources/images/soccerboard/ic_trash_blue_pressed.png',1)" 
						onMouseOut="MM_swapImgRestore()"> 
						
						<input type="image" 
						src="<%=serverUrl%>resources/images/soccerboard/biaogan.png" 
						name="ic_biaogan" width="30px" height="30px" border="0" 
						id="ic_biaogan" 
						onclick="com.ic_tool_click(this);"
						onMouseOver="MM_swapImage('ic_biaogan','','<%=serverUrl%>resources/images/soccerboard/biaogan.png',1)" 
						onMouseOut="MM_swapImgRestore()"> 
						
						<input type="image" 
						src="<%=serverUrl%>resources/images/soccerboard/bluecircl.png" 
						name="ic_bluecircl" width="30px" height="30px" border="0" 
						id="ic_bluecircl" 
						onclick="com.ic_tool_click(this);"
						onMouseOver="MM_swapImage('ic_bluecircl','','<%=serverUrl%>resources/images/soccerboard/bluecircl.png',1)" 
						onMouseOut="MM_swapImgRestore()"> 
						
						<input type="image" 
						src="<%=serverUrl%>resources/images/soccerboard/bluejump.png" 
						name="ic_bluejump" width="30px" height="30px" border="0" 
						id="ic_bluejump" 
						onclick="com.ic_tool_click(this);"
						onMouseOver="MM_swapImage('ic_bluejump','','<%=serverUrl%>resources/images/soccerboard/bluejump.png',1)" 
						onMouseOut="MM_swapImgRestore()"> 
						
						<input type="image" 
						src="<%=serverUrl%>resources/images/soccerboard/ladder.png" 
						name="ic_ladder" width="30px" height="30px" border="0" 
						id="ic_ladder" 
						onclick="com.ic_tool_click(this);"
						onMouseOver="MM_swapImage('ic_ladder','','<%=serverUrl%>resources/images/soccerboard/ladder.png',1)" 
						onMouseOut="MM_swapImgRestore()"> 
						
						<input type="image" 
						src="<%=serverUrl%>resources/images/soccerboard/peoplewall.png" 
						name="ic_peoplewall" width="30px" height="30px" border="0" 
						id="ic_peoplewall" 
						onclick="com.ic_tool_click(this);"
						onMouseOver="MM_swapImage('ic_peoplewall','','<%=serverUrl%>resources/images/soccerboard/peoplewall.png',1)" 
						onMouseOut="MM_swapImgRestore()"> 
						
						<input type="image" 
						src="<%=serverUrl%>resources/images/soccerboard/redbucket.png" 
						name="ic_redbucket" width="30px" height="30px" border="0" 
						id="ic_redbucket" 
						onclick="com.ic_tool_click(this);"
						onMouseOver="MM_swapImage('ic_redbucket','','<%=serverUrl%>resources/images/soccerboard/redbucket.png',1)" 
						onMouseOut="MM_swapImgRestore()"> 
				</div>

			</div>
			<div style="clear: both;height:30px" id="ic_pencil_bar_div">
				<input type="image"
					src="<%=serverUrl%>resources/images/soccerboard/ic_pencil_normal.png"
					name="ic_pencil" width="30px" height="30px" border="0" id="ic_pencil"
					title="画笔"
					onMouseOver="$('#ic_pencil_toolbar').show();"
					onMouseOut="if($(this).attr('bPressed') == 'false') {MM_swapImgRestore();}"
					bPressed="false" 
					style="float:left">
				<div id="ic_pencil_toolbar" style="display:none;float:left;height:30px;">
					<input type="image" 
						src="<%=serverUrl%>resources/images/soccerboard/freestyle_line.png"
						onclick="com.ic_paint_click(this);" name="freestyle_line_0"
						id="freestyle_line_0" width="30px" height="30px"/>
					<input type="image" 
						src="<%=serverUrl%>resources/images/soccerboard/curve_normal.png"
						onclick="com.ic_paint_click(this);" name="freestyle_line"
						onmouseover="MM_swapImage('freestyle_line','','<%=serverUrl%>resources/images/soccerboard/curve_pressed.png',1);"
						onmouseout="MM_swapImgRestore();"
						id="freestyle_line" width="30px" height="30px"/>
					<input type="image" 
						src="<%=serverUrl%>resources/images/soccerboard/ic_rect_normal.png"
						onclick="com.ic_paint_click(this);"
						onmouseover="MM_swapImage('ic_draw_rect','','<%=serverUrl%>resources/images/soccerboard/ic_rect_pressed.png',1);"
						onmouseout="MM_swapImgRestore();"
						name="ic_draw_rect" id="ic_draw_rect" width="30px" height="30px"/> 
					<input type="image" 
						src="<%=serverUrl%>resources/images/soccerboard/ic_arrow_normal.png"
						onclick="com.ic_paint_click(this);"
						onmouseover="MM_swapImage('ic_arrow','','<%=serverUrl%>resources/images/soccerboard/ic_arrow_pressed.png',1);"
						onmouseout="MM_swapImgRestore();"
						name="ic_arrow" id="ic_arrow" width="30px" height="30px"/>
					<input type="image" 
						src="<%=serverUrl%>resources/images/soccerboard/ic_line_normal.png"
						onclick="com.ic_paint_click(this);"
						onmouseover="MM_swapImage('ic_line','','<%=serverUrl%>resources/images/soccerboard/ic_line_pressed.png',1);"
						onmouseout="MM_swapImgRestore();"
						name="ic_line" id="ic_line" width="30px" height="30px"/>
					<input type="image" 
						src="<%=serverUrl%>resources/images/soccerboard/ic_arrow_dashed_normal.png"
						onclick="com.ic_paint_click(this);"
						onmouseover="MM_swapImage('ic_arrow_dashed','','<%=serverUrl%>resources/images/soccerboard/ic_arrow_dashed_pressed.png',1);"
						onmouseout="MM_swapImgRestore();"
						name="ic_arrow_dashed" id="ic_arrow_dashed" width="30px" height="30px"/>
					<input type="image" 
						src="<%=serverUrl%>resources/images/soccerboard/ic_dashed_line_normal.png"
						onclick="com.ic_paint_click(this);"
						onmouseover="MM_swapImage('ic_dashed_line','','<%=serverUrl%>resources/images/soccerboard/ic_dashed_line_pressed.png',1);"
						onmouseout="MM_swapImgRestore();"
						name="ic_dashed_line" id="ic_dashed_line" width="30px" height="30px"/>
					<input type="image" 
						src="<%=serverUrl%>resources/images/soccerboard/ic_dark_pen_normal.png"
						onclick="com.ic_paint_click(this);"
						onmouseover="MM_swapImage('ic_dark_pen','','<%=serverUrl%>resources/images/soccerboard/ic_dark_pen_pressed.png',1);"
						onmouseout="MM_swapImgRestore();"
						name="ic_dark_pen" id="ic_dark_pen" width="30px" height="30px"/>
					<input type="image" 
						src="<%=serverUrl%>resources/images/soccerboard/ic_light_pen_normal.png"
						onclick="com.ic_paint_click(this);"
						onmouseover="MM_swapImage('ic_light_pen','','<%=serverUrl%>resources/images/soccerboard/ic_light_pen_pressed.png',1);"
						onmouseout="MM_swapImgRestore();"
						name="ic_light_pen" id="ic_light_pen" width="30px" height="30px"/>
					<input type="image" 
						src="<%=serverUrl%>resources/images/soccerboard/ic_eraser_normal.png"
						onclick="com.ic_paint_click(this);"
						onmouseover="MM_swapImage('ic_eraser','','<%=serverUrl%>resources/images/soccerboard/ic_eraser_pressed.png',1);"
						onmouseout="MM_swapImgRestore();"
						name="ic_eraser" id="ic_eraser" width="30px" height="30px"/>
				</div>
			</div>
			<div style="clear: both;">
				<input type="image"
					src="<%=serverUrl%>resources/images/soccerboard/ic_text_sign.png"
					name="ic_text" width="30" height="30" border="0" id="ic_text"
					title="添加文字"
					bPressed="false" 
					onMouseOver="MM_swapImage('ic_text','','<%=serverUrl%>resources/images/soccerboard/ic_text_active.png',1)"
					onMouseOut="if($(this).attr('bPressed') == 'false') {MM_swapImgRestore();}"
				>
			</div>
			<div style="display:none">
				<input type="image"
					src="<%=serverUrl%>resources/images/soccerboard/ic_inputbox_delete_normal.png"
					name="ic_clear_tool" width="30" height="30" border="0"
					title="取消画笔" id="ic_clear_tool"
					onMouseOver="MM_swapImage('ic_clear_tool','','<%=serverUrl%>resources/images/soccerboard/ic_inputbox_delete_pressed.png',1)"
					onMouseOut="MM_swapImgRestore()">
			</div>
		</div>
			
			
		<input type="text" id="input_text_message" placeholder="请输入备注"
			onMouseOver="focus();" onclick="focus();" 
			onblur="com.saveTextMessage($('#input_text_message').val(),$('#input_listindex').val(), $('#input_clickx').val(), $('#input_clicky').val())"
			value=""
			style="display:none; border: 2px solid groove; position: absolute; left: 100px; top: 100px; opacity: .5" />

		<input type="hidden" id="input_listindex" />
		<input type="hidden" id="input_clickx" />
		<input type="hidden" id="input_clicky" />
		<!-- modal -->
		<div class="modal fade" id="classModal" tabindex="-1" role="dialog" 
			aria-labelledby="myModalLabel"
			data-backdrop="static" 
   			data-keyboard="false" 
			style="z-index: 100001; display: none;">
			<div class="modal-dialog" role="document"  id="modal_context" style="width: 400px;height: 150px;">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" onclick="com.setCancel($('#classModal'));" 
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="myModalLabel">请输入所选区间的长宽</h4>
					</div>
					<div class="modal-body" id="class_main_body">
						水平：<input type="text" onkeyup="javascript:CheckInputIntFloat(this);" name="rect_width" required id="rect_width" value="" style="width:70px"/>米 X 
						竖直：<input type="text" onkeyup="javascript:CheckInputIntFloat(this);" name="rect_height" required id="rect_height" value="" style="width:70px"/>米
						<input type="hidden" name="rect_width_pix" id="rect_width_pix" value="" style="width:50px"/>
						<input type="hidden" name="rect_height_pix" id="rect_height_pix" value="" style="width:50px"/>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" onclick="com.setCancel($('#classModal'));">取消</button>
						<button type="button" class="btn btn-primary" 
							onClick="com.setMeterPerPix($('#rect_width_pix').val(),$('#rect_height_pix').val(),$('#rect_width').val(),$('#rect_height').val());">确定
						</button>
					</div>
				</div>
			</div>
		</div>
		
		<!-- modal -->
		<div class="modal fade" id="classModal_SelectBg" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabelSelectBg"
			data-backdrop="static" 
   			data-keyboard="false" 
			style="z-index: 100001; display: none;">
			<div class="modal-dialog" role="document"  id="modal_context_selectbg" style="width: 400px; height: 400px;">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" onclick="com.modalCancel($('#classModal_SelectBg'));"  
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="myModalLabelSelectBg">请选择球场</h4>
					</div>
					<div class="modal-body" id="class_main_body_selectbg">
						<div 
						style="margin:5px 5px 5px 5px; float:left; height:100px; width:175px; background: url(<%=serverUrl%>resources/images/soccerboard/strategy_background1.png) top center no-repeat; background-size: 100% 100%;"
						onclick="com.ic_playground_click(this);"
						onMouseOver="$(this).css({'border': '2px solid red'});"
						onMouseOut="$(this).css({'border': '0px solid red'});"
						name="ic_strategy_background1" id="ic_strategy_background1"></div>
						<div 
						style="margin:5px 5px 5px 5px; float:left; height:100px; width:175px; background: url(<%=serverUrl%>resources/images/soccerboard/strategy_background2.png) top center no-repeat; background-size: 100% 100%;"
						onclick="com.ic_playground_click(this);"
						onMouseOver="$(this).css({'border': '2px solid red'});"
						onMouseOut="$(this).css({'border': '0px solid red'});"
						name="ic_strategy_background2" id="ic_strategy_background2">
						</div> 
						<div 
						style="margin:5px 5px 20px 5px; float:left; height:100px; width:175px;  background: url(<%=serverUrl%>resources/images/soccerboard/strategy_background3.png) top center no-repeat; background-size: 100% 100%;"
						onclick="com.ic_playground_click(this);"
						onMouseOver="$(this).css({'border': '2px solid red'});"
						onMouseOut="$(this).css({'border': '0px solid red'});"
						name="ic_strategy_background3" id="ic_strategy_background3"></div>
						<div 
						style="margin:5px 5px 20px 5px; float:left;height:100px; width:175px; background: url(<%=serverUrl%>resources/images/soccerboard/strategy_background4.png) top center no-repeat; background-size: 100% 100%;"
						onclick="com.ic_playground_click(this);"
						onMouseOver="$(this).css({'border': '2px solid red'});"
						onMouseOut="$(this).css({'border': '0px solid red'});"
						name="ic_strategy_background4" id="ic_strategy_background4"></div>
					</div>
					<div class="modal-footer" style="clear: both;">
						<button type="button" class="btn btn-default" onclick="com.modalCancel($('#classModal_SelectBg'));" >取消</button>
						<!-- <button type="button" class="btn btn-primary" data-dismiss="modal">确定</button> -->
					</div>
				</div>
			</div>
		</div>
		
		<!-- modal -->
		<div class="modal fade" id="classModal_Player" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabelPlayer"
			data-backdrop="static" 
   			data-keyboard="false" 
			style="z-index: 100001; display: none;">
			<div class="modal-dialog" role="document"  id="modal_context_player" style="width: 400px;height: 180px;">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" onclick="com.modalCancel($('#classModal_Player'));"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="myModalLabelPlayer">请编辑球员属性</h4>
					</div>
					<div class="modal-body" id="class_main_body_player">
						姓名：<input type="text" onkeyup="" name="edit_player_name" required id="edit_player_name" value="" style="width:70px"/> 
						号码：<input type="number" onkeyup="" name="edit_player_jerseyid" required id="edit_player_jerseyid" value="" style="width:70px"/>
						位置：<input type="text" onkeyup="" name="edit_player_pos" required id="edit_player_pos" value="" style="width:70px"/>
						<div id="SelTeamRadio">
						选队：<label><input name="SelTeam" type="radio" value="player_1" />红队 </label> 
							 <label><input name="SelTeam" type="radio" value="player_2" />蓝队 </label> 
						</div>
						<input type="hidden" name="list_obj_index" id="list_obj_index" value="" style="width:50px"/>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default"  onclick="com.modalCancel($('#classModal_Player'));">取消</button>
						<button type="button" class="btn btn-primary" 
							onClick="com.editPlayerObj($('#list_obj_index').val(),$('#edit_player_name').val(),$('#edit_player_jerseyid').val(),$('#edit_player_pos').val(),$('input:radio:checked').val());">确定
						</button>
					</div>
				</div>
			</div>
		</div>
		
		<!-- modal -->
		<div class="modal fade" id="classModal_AlertSave" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabelAlertSave"
			data-backdrop="static" 
   			data-keyboard="false" 
			style="z-index: 100001; display: none;">
			<div class="modal-dialog" role="document"  id="modal_context_AlertSave" style="width: 400px;height: 180px;">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" onclick="SaveCancel()"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="myModalLabelAlertSave">战术板所做更改尚未保存，请选择：</h4>
					</div>
					<div class="modal-body" id="class_main_body_AlertSave">
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" onclick="SaveCancel()">留在此页</button>
						<button type="button" class="btn btn-danger" onclick="NoSaveContinue()">不保存</button>
						<button type="button" class="btn btn-primary" 
							onClick="SaveContinue();">保存并继续
						</button>
					</div>
				</div>
			</div>
		</div>
		
		<!-- modal -->
		<div class="modal fade" id="classModal_AlertCancel" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabelAlertCancel"
			data-backdrop="static" 
   			data-keyboard="false" 
			style="z-index: 100001; display: none;">
			<div class="modal-dialog" role="document"  id="modal_context_AlertCancel" style="width: 400px;height: 180px;">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="myModalLabelAlertCancel">战术板所做更改尚未保存,请选择:</h4>
					</div>
					<div class="modal-body" id="class_main_body_AlertSave">
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" onclick="SaveCancel()">留在页面</button>
						<button type="button" class="btn btn-danger" onclick="NoSaveContinue()">直接退出</button>
						<button type="button" class="btn btn-primary" 
							onClick="SaveContinue();">保存并退出
						</button>
					</div>
				</div>
			</div>
		</div>
		
		<div>
			<div style="">
				<div class="bn_box" id="bnBox">
					<div style="position:absolute; z-index:2">
						<div style="float: left;">
							<input type="image"
								src="<%=serverUrl%>resources/images/soccerboard/ic_ruler_normal.png"
								name="ic_ruler" width="30" height="30" border="0" id="ic_ruler"
								title="设置场地长宽" onclick="com.ic_ruler_click(this);"
								bPressed="false" 
								onMouseOver="MM_swapImage('ic_ruler','','<%=serverUrl%>resources/images/soccerboard/ic_ruler_pressed.png',1)"
								onMouseOut="if($(this).attr('bPressed') == 'false') {MM_swapImgRestore();}">
						</div>
						<div style="float: left;">
							<input type="image"
								src="<%=serverUrl%>resources/images/soccerboard/ic_board_sign.png"
								name="ic_playground" width="30" height="30" border="0"
								id="ic_playground"
								onMouseOver="MM_swapImage('ic_playground','','<%=serverUrl%>resources/images/soccerboard/ic_board_active.png',1)"
								onMouseOut="MM_swapImgRestore()">
						</div>
						<div style="float: left;">
							<input type="image"
								src="<%=serverUrl%>resources/images/soccerboard/ic_undo_normal.png"
								name="ic_undo" width="30" height="30" border="0" id="ic_undo"
								title="撤销"
								onMouseOver="MM_swapImage('ic_undo','','<%=serverUrl%>resources/images/soccerboard/ic_undo_pressed.png',1)"
								onMouseOut="MM_swapImgRestore()">
						</div>
						<div style="float: left;">
							<input type="image"
								src="<%=serverUrl%>resources/images/soccerboard/ic_redo_normal.png"
								name="ic_redo" width="30" height="30" border="0" id="ic_redo"
								title="重做"
								onMouseOver="MM_swapImage('ic_redo','','<%=serverUrl%>resources/images/soccerboard/ic_redo_pressed.png',1)"
								onMouseOut="MM_swapImgRestore()">
						</div>
						<div style="float: left; display:none">
							<input type="image"
								src="<%=serverUrl%>resources/images/soccerboard/ic_delete_normal.png"
								name="ic_delete" width="30" height="30" border="0" id="ic_delete"
								title="删除元素：点击后再点击元素">
						</div>
						<div style="float: left;">
							<input type="image"
								src="<%=serverUrl%>resources/images/soccerboard/ic_refresh_normal.png"
								name="ic_refresh" width="30" height="30" border="0" title="清空所有帧"
								id="ic_refresh"
								onMouseOver="MM_swapImage('ic_refresh','','<%=serverUrl%>resources/images/soccerboard/ic_refresh_pressed.png',1)"
								onMouseOut="MM_swapImgRestore()">
						</div>
						<div style="float: left;">
							<input type="image"
								src="<%=serverUrl%>resources/images/soccerboard/ic_quit_normal.png"
								name="ic_load_storage" width="30" height="30" border="0" id="ic_load_storage"
								title="读取自动保存的战术板(误关闭浏览器可以尝试此操作来恢复战术板内容)"
								onMouseOver="MM_swapImage('ic_load_storage','','<%=serverUrl%>resources/images/soccerboard/ic_quit_pressed.png',1)"
								onMouseOut="MM_swapImgRestore()">
						</div>
						<div style="float: left;width:30px; height:30px">
							<input type="image"
								src="<%=serverUrl%>resources/images/soccerboard/ic_clear_normal.png"
								name="frame_delete" class="deleteframe" title="删除：先点击要删除的元素再点击此按钮"
								id="frame_delete" style="width: 36px; height: 36px; margin-top: -3px; margin-left: -3px;" 
								onMouseOver="MM_swapImage('frame_delete','','<%=serverUrl%>resources/images/soccerboard/ic_clear_pressed.png',1)"
								onMouseOut="MM_swapImgRestore()">
						</div>
						<div style="float: left; width:30px; height:30px">
							<input type="image"
								src="<%=serverUrl%>resources/images/soccerboard/play.png"
								name="auto_play" class="autoPlay" title="播放动画" id="auto_play"
								style="width:28px;height:28px; margin-top: 1px; margin-left: 1px;">
						</div>
						<div style="float: left; width:65px; height:30px">
							<input type="text" onkeyup="javascript:CheckInputIntFloat(this);" value="0.5" required onblur="if($(this).val()==''){$(this).val(0.5);}" 
								name="auto_play_interval" title="设置帧播放间隔（秒）" id="auto_play_interval"
								style="width:28px;height:28px; margin-top: 1px; margin-left: 1px;">秒/帧
						</div>
					</div>
					<!-- <div style="margin: 0px; height: 30px;width: 50%; float: left;" -->
					<div style="margin: 0px; height: 30px; width: 100%; float: left;padding-left:303px;padding-right:4px; position:absolute; z-index:1"
						class="cd-horizontal-timeline" id="cd-horizontal-timeline"
						style="margin-top: 0px; margin-bottom: 0px;">
						<div class=board_timeline style="float: left; height:30px; width:100%; margin-left: 4px;margin-right: 4px;"
							id="timeline">
							<div class="events-box" id="events-box" style="">
								<div class="events-wrapper" id="events-wrapper" style="width:100%; margin:0 0 0 0;padding:0 0 0 0;">
									<div class="events" id="events" style="width:100%">
										<ol id="frame_list">
											<!--<li><a href="javascript:void(0);" data-date="16/01/2014" data-index="1" class="selected">1</a></li>-->
											<!--<li><a href="javascript:void(0);" data-date="16/01/2014" data-index="2">2</a></li>-->
										</ol>
	
										<span class="filling-line" aria-hidden="true"></span>
									</div>
									<!-- .events -->
								</div>	
							</div>

							<!-- .events-wrapper -->

							<ul class="cd-timeline-navigation" style="width:100%">
								<li><a href="#0" class="prev" id="prev" style="width: 30px;height:30px">Prev</a></li>
								<li><a href="#0" class="next" id="next" style="width: 30px;height:30px">Next</a></li>
							</ul>
							<!-- .cd-timeline-navigation -->
						</div>
						<!-- .timeline -->
					</div>
				</div>
				<div style="display: none">
					<input type="button" name="playground" id="playground" value="换场地" />
					<img
						src="<%=serverUrl%>resources/images/soccerboard/ic_red_normal.png"
						draggable="true" name="addplayer" id="addplayer" value="添加球员" />
					<input type="button" name="formation" id="formation" value="阵型" />
					<input type="input" style="width: 700px" name="info" id="info"
						value="信息" />
				</div>

				<div class="toolbar-toolbar_background" id="ic_formation_toolbar"
					style="display: none;">
					<a class="test_icon"
						style="background: url(<%=serverUrl%>resources/images/soccerboard/ic_formation_121_normal.png) top center no-repeat; background-size: 100% 100%;"
						onclick="com.ic_formation_click(this);"
						onmouseover="MM_changeimg(this, '<%=serverUrl%>resources/images/soccerboard/ic_formation_121_pressed.png');"
						onmouseout="MM_changeimg(this, '<%=serverUrl%>resources/images/soccerboard/ic_formation_121_normal.png');"
						name="1121" id="ic_formation_1121"></a> <a class="test_icon"
						style="background: url(<%=serverUrl%>resources/images/soccerboard/ic_formation_22_normal.png) top center no-repeat; background-size: 100% 100%;"
						onclick="com.ic_formation_click(this);"
						onmouseover="MM_changeimg(this, '<%=serverUrl%>resources/images/soccerboard/ic_formation_22_pressed.png');"
						onmouseout="MM_changeimg(this, '<%=serverUrl%>resources/images/soccerboard/ic_formation_22_normal.png');"
						name="122" id="ic_formation_122"></a> <a class="test_icon"
						style="background: url(<%=serverUrl%>resources/images/soccerboard/ic_formation_31_normal.png) top center no-repeat; background-size: 100% 100%;"
						onclick="com.ic_formation_click(this);"
						onmouseover="MM_changeimg(this, '<%=serverUrl%>resources/images/soccerboard/ic_formation_31_pressed.png');"
						onmouseout="MM_changeimg(this, '<%=serverUrl%>resources/images/soccerboard/ic_formation_31_normal.png');"
						name="131" id="ic_formation_131"></a> <a class="test_icon"
						style="background: url(<%=serverUrl%>resources/images/soccerboard/ic_formation_331_normal.png) top center no-repeat; background-size: 100% 100%;"
						onclick="com.ic_formation_click(this);"
						onmouseover="MM_changeimg(this, '<%=serverUrl%>resources/images/soccerboard/ic_formation_331_pressed.png');"
						onmouseout="MM_changeimg(this, '<%=serverUrl%>resources/images/soccerboard/ic_formation_331_normal.png');"
						name="1331" id="ic_formation_1331"></a> <a class="test_icon"
						style="background: url(<%=serverUrl%>resources/images/soccerboard/ic_formation_322_normal.png) top center no-repeat; background-size: 100% 100%;"
						onclick="com.ic_formation_click(this);"
						onmouseover="MM_changeimg(this, '<%=serverUrl%>resources/images/soccerboard/ic_formation_322_pressed.png');"
						onmouseout="MM_changeimg(this, '<%=serverUrl%>resources/images/soccerboard/ic_formation_322_normal.png');"
						name="1322" id="ic_formation_1322"></a> <a class="test_icon"
						style="background: url(<%=serverUrl%>resources/images/soccerboard/ic_formation_433_normal.png) top center no-repeat; background-size: 100% 100%;"
						onclick="com.ic_formation_click(this);"
						onmouseover="MM_changeimg(this, '<%=serverUrl%>resources/images/soccerboard/ic_formation_433_pressed.png');"
						onmouseout="MM_changeimg(this, '<%=serverUrl%>resources/images/soccerboard/ic_formation_433_normal.png');"
						name="1433" id="ic_formation_1433"></a> <a class="test_icon"
						style="background: url(<%=serverUrl%>resources/images/soccerboard/ic_formation_442_normal.png) top center no-repeat; background-size: 100% 100%;"
						onclick="com.ic_formation_click(this);"
						onmouseover="MM_changeimg(this, '<%=serverUrl%>resources/images/soccerboard/ic_formation_442_pressed.png');"
						onmouseout="MM_changeimg(this, '<%=serverUrl%>resources/images/soccerboard/ic_formation_442_normal.png');"
						name="1442" id="ic_formation_1442"></a> <a class="test_icon"
						style="background: url(<%=serverUrl%>resources/images/soccerboard/ic_formation_451_normal.png) top center no-repeat; background-size: 100% 100%;"
						onclick="com.ic_formation_click(this);"
						onmouseover="MM_changeimg(this, '<%=serverUrl%>resources/images/soccerboard/ic_formation_451_pressed.png');"
						onmouseout="MM_changeimg(this, '<%=serverUrl%>resources/images/soccerboard/ic_formation_451_normal.png');"
						name="1451" id="ic_formation_1451"></a>
				</div>

				<div class="toolbar-toolbar_background" id="ic_tools_toolbar"
					style="display: none;">
					<a class="test_icon"
						style="background: url(<%=serverUrl%>resources/images/soccerboard/ic_ball_normal.png) top center no-repeat; background-size: 100% 100%;"
						onclick="com.ic_tool_click(this);"
						onmouseover="MM_changeimg(this, '<%=serverUrl%>resources/images/soccerboard/ic_ball_pressed.png');"
						onmouseout="MM_changeimg(this, '<%=serverUrl%>resources/images/soccerboard/ic_ball_normal.png');"
						name="ic_ball" id="ic_ball"></a> <a class="test_icon"
						style="background: url(<%=serverUrl%>resources/images/soccerboard/ic_bar_normal.png) top center no-repeat; background-size: 100% 100%;"
						onclick="com.ic_tool_click(this);"
						onmouseover="MM_changeimg(this, '<%=serverUrl%>resources/images/soccerboard/ic_bar_pressed.png');"
						onmouseout="MM_changeimg(this, '<%=serverUrl%>resources/images/soccerboard/ic_bar_normal.png');"
						name="ic_bar" id="ic_bar"></a> <a class="test_icon"
						style="background: url(<%=serverUrl%>resources/images/soccerboard/ic_goal_normal.png) top center no-repeat; background-size: 100% 100%;"
						onclick="com.ic_tool_click(this);"
						onmouseover="MM_changeimg(this, '<%=serverUrl%>resources/images/soccerboard/ic_goal_pressed.png');"
						onmouseout="MM_changeimg(this, '<%=serverUrl%>resources/images/soccerboard/ic_goal_normal.png');"
						name="ic_goal" id="ic_goal"></a> <a class="test_icon"
						style="background: url(<%=serverUrl%>resources/images/soccerboard/ic_trash_yellow_normal.png) top center no-repeat; background-size: 100% 100%;"
						onclick="com.ic_tool_click(this);"
						onmouseover="MM_changeimg(this, '<%=serverUrl%>resources/images/soccerboard/ic_trash_yellow_pressed.png');"
						onmouseout="MM_changeimg(this, '<%=serverUrl%>resources/images/soccerboard/ic_trash_yellow_normal.png');"
						name="ic_trash_yellow" id="ic_trash_yellow"></a> <a
						class="test_icon"
						style="background: url(<%=serverUrl%>resources/images/soccerboard/ic_trash_red_normal.png) top center no-repeat; background-size: 100% 100%;"
						onclick="com.ic_tool_click(this);"
						onmouseover="MM_changeimg(this, '<%=serverUrl%>resources/images/soccerboard/ic_trash_red_pressed.png');"
						onmouseout="MM_changeimg(this, '<%=serverUrl%>resources/images/soccerboard/ic_trash_red_normal.png');"
						name="ic_trash_red" id="ic_trash_red"></a> <a class="test_icon"
						style="background: url(<%=serverUrl%>resources/images/soccerboard/ic_trash_blue_normal.png) top center no-repeat; background-size: 100% 100%;"
						onclick="com.ic_tool_click(this);"
						onmouseover="MM_changeimg(this, '<%=serverUrl%>resources/images/soccerboard/ic_trash_blue_pressed.png');"
						onmouseout="MM_changeimg(this, '<%=serverUrl%>resources/images/soccerboard/ic_trash_blue_normal.png');"
						name="ic_trash_blue" id="ic_trash_blue"></a>
				</div>


				<div class="toolbar-toolbar_background" id="ic_playground_toolbar"
					style="display: none;">
					<a class="test_icon"
						style="background: url(<%=serverUrl%>resources/images/soccerboard/strategy_background1.png) top center no-repeat; background-size: 80% 80%;"
						onclick="com.ic_playground_click(this);"
						name="ic_strategy_background1" id="ic_strategy_background1"></a> <a
						class="test_icon"
						style="background: url(<%=serverUrl%>resources/images/soccerboard/strategy_background2.png) top center no-repeat; background-size: 80% 80%;"
						onclick="com.ic_playground_click(this);"
						name="ic_strategy_background2" id="ic_strategy_background2"></a> <a
						class="test_icon"
						style="background: url(<%=serverUrl%>resources/images/soccerboard/strategy_background3.png) top center no-repeat; background-size: 80% 80%;"
						onclick="com.ic_playground_click(this);"
						name="ic_strategy_background3" id="ic_strategy_background3"></a> <a
						class="test_icon"
						style="background: url(<%=serverUrl%>resources/images/soccerboard/strategy_background4.png) top center no-repeat; background-size: 80% 80%;"
						onclick="com.ic_playground_click(this);"
						name="ic_strategy_background4" id="ic_strategy_background4"></a>
				</div>
			</div>

		</div>
	</div>
</div>

<script type="text/javascript">
    function initTacticsData( ) {
    	com.avatorPath = "<%=serverUrl%>hagkFile/asset?id=";
    	com.imgPath = "<%=serverUrl%>resources/images/soccerboard";
    	timelineBar.imgPath = "<%=serverUrl%>resources/images/soccerboard";
		var pd_type = 'stype1';
		var isCreate = ${isCreate};
		var initTacticsData = null;
		if (isCreate == false) {
			initTacticsData = '<xss:xssFilter text="${tactics.tacticsFrames}" filter="js"/>';
			pd_type = '${tactics.tacticsPlayground.abbr}';
			com.init(pd_type, $('#tacticsArea').width());
			com.loadDataFromDB(initTacticsData);
		} else {
			com.init(pd_type, $('#tacticsArea').width());
			com.loadDataFromDB(null);
		}
	}

	function resizeTacticsData() {
		if ($('#tacticsArea').length > 0){
			com.resize($('#tacticsArea').width());
			$('#team_member_row_div').height($('#tacticsArea').height());
		} else {
			$(window).off("resize");
		}
	}

	function changeTacticsBg(bgType) {
		// disable change Bg on Page
		//com.changeBg(bgType);
	}
	

</script>

<script language=JavaScript type=text/JavaScript>	
	function CheckInputIntFloat(oInput) {
	    if('' != oInput.value.replace(/\d{1,}\.{0,1}\d{0,}/,''))
	    {
	        oInput.value = oInput.value.match(/\d{1,}\.{0,1}\d{0,}/) == null ? '' :oInput.value.match(/\d{1,}\.{0,1}\d{0,}/);
	    }
	}

	function MM_changeimg(obj, src) {
		$(obj).css("background","url("+src+") top center no-repeat");
		$(obj).css("background-size","100% 100%");
	}
	
	function MM_preloadImages() { // v3.0
		var d = document;
		if (d.images) {
			if (!d.MM_p)
				d.MM_p = new Array();
			var i, j = d.MM_p.length, a = MM_preloadImages.arguments;
			for (i = 0; i < a.length; i++) {
				if (a[i].indexOf("#") != 0) {
					d.MM_p[j] = new Image;
					d.MM_p[j++].src = a[i];
				}
			}
		}
	}
	function MM_swapImgRestore( ) { // v3.0
		var i, x, a = document.MM_sr;
		for (i = 0; a && i < a.length && (x = a[i]) && x.oSrc; i++) {
			x.src = x.oSrc;
		}
	}
	function MM_findObj(n, d) { // v4.01
		var p, i, x;
		if (!d) {
			d = document;
		}
		if ((p = n.indexOf("?")) > 0 && parent.frames.length) {
			d = parent.frames[n.substring(p + 1)].document;
			n = n.substring(0, p);
		}
		if (!(x = d[n]) && d.all) {
			x = d.all[n];
		}
		for (i = 0; !x && i < d.forms.length; i++) {
			x = d.forms[i][n];
		}
		for (i = 0; !x && d.layers && i < d.layers.length; i++) {
			x = MM_findObj(n, d.layers[i].document);
		}

		if (!x && d.getElementById) {
			x = d.getElementById(n);
		}
		return x;
	}

	function MM_swapImage() { // v3.0
		var i, j = 0, x, a = MM_swapImage.arguments;
		document.MM_sr = new Array;
		for (i = 0; i < (a.length - 2); i += 3) {
			if ((x = MM_findObj(a[i])) != null) {
				document.MM_sr[j++] = x;
				if (!x.oSrc) {
					x.oSrc = x.src;
				}
				x.src = a[i + 2];
			}
		}
	}

	function SetImg(name, src1, src2) {
		if (document.getElementById(name).getAttribute("src") == src1) {
			document.getElementById(name).setAttribute("src", src2);
			return;
		}
		if (document.getElementById(name).getAttribute("src") == src2) {
			document.getElementById(name).setAttribute("src", src1);
			return;
		}
	}
</SCRIPT>