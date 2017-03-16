var com = com || {};
com.initWidth = 600;
com.font = "10pt 宋体";
com.fontJerseyId = "18pt 宋体";
com.playersize = 30;
com.resmapping = {
		"bg1":"/strategy_background1.png",
		"bg2":"/strategy_background2.png",
		"bg3":"/strategy_background3.png",
		"bg4":"/strategy_background4.png",
		"player_1":"/ic_red_normal.png",
		"player_2":"/ic_blue_normal.png",
		"tool_1":"/ic_ball_normal.png",
		"tool_2":"/ic_bar_normal.png",
		"tool_3":"/ic_goal_normal.png",
		"tool_4":"/ic_trash_yellow_normal.png",
		"tool_5":"/ic_trash_red_normal.png",
		"tool_6":"/ic_trash_blue_normal.png",
		"tool_7":"/biaogan.png",
		"tool_8":"/bluecircl.png",
		"tool_9":"/bluejump.png",
		"tool_10":"/ladder.png",
		"tool_11":"/peoplewall.png",
		"tool_12":"/redbucket.png"
};

com.gen_player_jerseyid = function(team){
	var jerseyid = 1;
	var foundId = true;
	while (foundId) {
		foundId = false;
		for (var i = 0; i< com.childList.length; i++) {
			if(com.childList[i].type == team && jerseyid == com.childList[i].jerseyid) {
				foundId = true;
				break;
			}
		}
		if(foundId == true) {
			jerseyid++;
		}
	}
	return jerseyid;
};

Array.prototype.mypush = function(obj) {
	com.setChanged();
	this.push(obj);
}


function cancelTools() {
    com.timer && clearTimeout(com.timer);
    var tmp = com.resetTools();
    com.show();
    return tmp;
}

com.setChanged = function () {
	$('#tactics_page_changed').val("true");
}

function canvasMouseDown(e, touch) {
	var e1 = e || window.event;
    // 鼠标新的位置.
	com.tacticsarea = com.tacticsarea || {}
	com.tacticsarea.offsetLeft = $('#soccerboard').offset().left;
	com.tacticsarea.offsetTop = $('#soccerboard').offset().top;
	com.tacticsarea.center_x = $('#soccerboard').offset().left + $('#soccerboard').width()/2;
	com.tacticsarea.center_y = $('#soccerboard').offset().top + $('#soccerboard').height()/2;
    var x;
    var y;
    var e;
	try{
		e1.preventDefault(); 
		e = e1.originalEvent.touches[0];
	}
	catch(err) {
		e = e1;
	    if(e.button == "2"){  // right click
	        e.cancelBubble = true;
	        e.returnValue = false;
	        cancelTools();     
	        return false;
	    }  
	}
	
    x = e.pageX - com.tacticsarea.offsetLeft;
    y = e.pageY - com.tacticsarea.offsetTop;

    $('#input_text_message').hide();
	var clickX = e.pageX - com.tacticsarea.offsetLeft;
    var clickY = e.pageY - com.tacticsarea.offsetTop;
    com.mousedown_x = clickX;
    com.mousedown_y = clickY;
    com.mouseLast_x = clickX;
    com.mouseLast_y = clickY;
    var tmpPen = com.paintPen;
    com.paintPen = null;
    // 获取鼠标在canvas中点击的位置.
    
    com.isPainting = true;
    if(com.isRotating == true && tmpPen == null) {
    	com.isRotating = false;
    	com.show();
    }
    
    com.isResizing = false;
    //disable this now, in next version, support resizing
//    if(com.previousSelectedObj != null && 
//    		(com.previousSelectedObj.type=="player_1" || com.previousSelectedObj.type=="player_2" || com.previousSelectedObj.type=="tool")) {
//    	if(com.previousSelectedObj.isOnResizeArea(clickX, clickY)) {
//    		com.isResizing = true;
//    		com.to_save = true;
//    		return;
//    	}
//    } else {
//    	com.isResizing = false;
//    }

    if (com.paint == "text") {
    	com.paint = "none";
        com.p_x = clickX;// 当前瞬间的鼠标位置
        com.p_y = clickY;
        $('#input_text_message').css({position: "absolute",
            "margin":"0px",
            'left': clickX,
            'top': clickY+20,
            'width': 100,
        });
        $('#input_text_message').val("");
        $('#input_clickx').val(Math.floor(clickX));
        $('#input_clicky').val(Math.floor(clickY));
        $('#input_listindex').val(-1);
        $('#input_text_message').show();
        com.setChanged();
        //not work $('#input_text_message').focus();

        com.to_save = true;
        return;
    } else {
        com.get("ic_text").setAttribute("bPressed","false");
        com.get("ic_text").setAttribute("src", com.imgPath+"/ic_text_sign.png");
    }
    
    if (com.paint == "pencil" && com.isPainting == true) {
        com.p_x = clickX;// 当前瞬间的鼠标位置
        com.p_y = clickY;
        if (com.ptList == null) {
            com.ptList = new com.class.PointsList(com.line_color, com.tool_imgSrc); 
        } else {
            com.ptList.clearPoints();
        }
        com.ptList.freestyle = com.freestyle;
        com.ptList.addPoint(com.p_x, com.p_y);
        com.to_save = true;
        return;
    }

    if (com.paint == "eraser" && com.isPainting == true) {
        com.p_x = clickX;// 当前瞬间的鼠标位置
        com.p_y = clickY;
        if (com.ptList == null) {
            com.ptList = new com.class.EraserPointsList(com.line_color, com.tool_imgSrc);
        } else {
            com.ptList.clearPoints();
        }
        com.ptList.addPoint(com.p_x, com.p_y);
        com.to_save = true;
        return;
    }

    if (com.paint == "line" && com.isPainting == true) {
        com.p_x = clickX;// 当前瞬间的鼠标位置
        com.p_y = clickY;

        if (com.ptList != null) {
            com.ptList.x1 = com.p_x;
            com.ptList.y1 = com.p_y;
        }
        if (com.ptList == null) {
            com.ptList = new com.class.Line(com.p_x, com.p_y, com.p_x, com.p_y, com.line_start, com.line_end, com.line_pattern, com.line_color, com.tool_imgSrc);
        } else {
            com.ptList.x1 = com.p_x;
            com.ptList.y1 = com.p_y;
        }
        com.to_save = true;
        return;
    }

    if (com.paint == "pencil_rect" && com.isPainting == true) {
        com.p_x = clickX;// 当前瞬间的鼠标位置
        com.p_y = clickY;

        if (com.ptList != null) {
            com.ptList.x1 = com.p_x;
            com.ptList.y1 = com.p_y;
        }
        if (com.ptList == null) {
            com.ptList = new com.class.Rect(com.p_x, com.p_y, com.p_x, com.p_y, com.line_color, com.tool_imgSrc);
        } else {
            com.ptList.x1 = com.p_x;
            com.ptList.y1 = com.p_y;
        }
        com.to_save = true;
        return;
    }

    if (com.paint == "ruler_rect" && com.isPainting == true) {
        com.p_x = clickX;// 当前瞬间的鼠标位置
        com.p_y = clickY;

        if (com.ptList != null) {
            com.ptList.x1 = com.p_x;
            com.ptList.y1 = com.p_y;
        }
        if (com.ptList == null) {
            com.ptList = new com.class.Ruler(com.p_x, com.p_y, com.p_x, com.p_y, "red", com.tool_imgSrc);
        } else {
            com.ptList.x1 = com.p_x;
            com.ptList.y1 = com.p_y;
        }
        com.ruler = com.ptList;
        com.to_save = true;
        return;
    }
    
    if(com.previousSelectedObj != null) {
    	com.previousSelectedObj.isSelected = false;
    	com.previousSelectedObj = null;
    	com.show();
    }
    if (tmpPen == null) {
    	for (var i = com.childList.length - 1; i >= 0; i--) {
    		var childObj = com.childList[i];
            if (childObj.type != "background") {
            	childObj.isSelected = false;
            }
    	}
        // 判断点击了哪个圆.
        for (var i = com.childList.length - 1; i >= 0; i--) {
            var childObj = com.childList[i];
            if (childObj.type != "background") {
                if (childObj.isOnObj(clickX, clickY, 2)) {
                    com.previousSelectedObj = childObj;// 记录选中的圆
                    com.previousSelectedObj_XOffset = (clickX - childObj.x);
                    com.previousSelectedObj_YOffset = (clickY - childObj.y);
                    if (com.paint == "delete") {
                        childObj.isSelected = false;
                        com.opUndoLists.push(com.arr2Clone(com.childList));
                        com.childList.splice(i, 1);
                        com.show();
                        return;
                    }

                    childObj.isSelected = true;
                    // 可以被拖动
                    if (com.isDragging == false) {
                        com.isDragging = true;
                        com.dragLine.x1 = childObj.x+childObj.width/2;
                        com.dragLine.y1 = childObj.y+childObj.height/2;
                    }
                    com.to_save = true;
                    com.show();
                    return;
                }
            }
        }
        
        for (var i = com.childList.length - 1; i >= 0; i--) {
            var childObj = com.childList[i];
            if (childObj.type != "background") {
                if (childObj.isOnObj(clickX, clickY, 1)) {
                    com.previousSelectedObj = childObj;// 记录选中的圆
                    com.previousSelectedObj_XOffset = (clickX - childObj.x);
                    com.previousSelectedObj_YOffset = (clickY - childObj.y);
                    if (com.paint == "delete") {
                        childObj.isSelected = false;
                        com.opUndoLists.push(com.arr2Clone(com.childList));
                        com.childList.splice(i, 1);
                        com.show();
                        return;
                    }

                    childObj.isSelected = true;
                    // 可以被拖动
                    if (com.isDragging == false) {
                        com.isDragging = true;
                        com.dragLine.x1 = childObj.x+childObj.width/2;
                        com.dragLine.y1 = childObj.y+childObj.height/2;
                        console.log(com.dragLine);
                    }
                    com.to_save = true;
                    com.show();
                    return;
                }
            }
        }
    }

    com.timer && clearTimeout(com.timer);
    com.timer = setTimeout(function () {
        if (tmpPen != null) {
            com.opUndoLists.push(com.arr2Clone(com.childList));
            tmpPen.x = clickX;
            tmpPen.y = clickY;
            com.childList.mypush(tmpPen);

            com.show();
            if (com.paintPenMulti == true) {
                com.paintPen = tmpPen.slice();
                com.paintPen.id = Guid.NewGuid().ToString("N");
                if(com.paintPen.type =="player_1" || com.paintPen.type =="player_2") {
                	com.paintPen.jerseyid = com.gen_player_jerseyid(com.paintPen.type);
                }
            }
            return;
        }
    }, 300);
}

function doubleClick(e, touch) {
	var e1 = e || window.event;
    // 鼠标新的位置.
	com.tacticsarea = com.tacticsarea || {}
	com.tacticsarea.offsetLeft = $('#soccerboard').offset().left;
	com.tacticsarea.offsetTop = $('#soccerboard').offset().top;
	com.tacticsarea.center_x = $('#soccerboard').offset().left + $('#soccerboard').width()/2;
	com.tacticsarea.center_y = $('#soccerboard').offset().top + $('#soccerboard').height()/2;
    // 获取鼠标在canvas中点击的位置.
    var clickX;
    var clickY;
	try{
		e1.preventDefault();
		clickX = touch.startTouch.x - com.tacticsarea.offsetLeft;
		clickY = touch.startTouch.y - com.tacticsarea.offsetTop;
	}
	catch(err) {
		clickX = e1.pageX - com.tacticsarea.offsetLeft;
		clickY = e1.pageY - com.tacticsarea.offsetTop;
	}
    
    if(cancelTools() == true) {
    	return;
    }


    // 判断点击了哪个圆.
    for (var i = com.childList.length - 1; i >= 0; i--) {
        var childObj = com.childList[i];
        if (childObj.type != "background") {
            if (childObj.isOnObj(clickX, clickY)) {
                com.previousSelectedObj = childObj;// 记录选中的圆
                com.previousSelectedObj_XOffset = (clickX - childObj.x);
                com.previousSelectedObj_YOffset = (clickY - childObj.y);
                childObj.isSelected = true;
                com.isDragging = false;

                if (childObj.type == "player_1" || childObj.type == "player_2") {
                	$('#list_obj_index').val(i);
                	$('#SelTeamRadio input[value='+"'"+childObj.type+"'"+']').prop("checked",true);
                	$('#edit_player_name').val(childObj.name);
                	$('#edit_player_jerseyid').val(childObj.jerseyid);
                	$('#edit_player_pos').val(childObj.position);
                	
                	$('#modal_context_player').css({
                        position: "absolute",
                        "margin":"0px",
                        'left': com.tacticsarea.center_x-($('#modal_context_player').width()/2) - $(document).scrollLeft(),
                        'top': com.tacticsarea.center_y-($('#modal_context_player').height()/2) - $(document).scrollTop(),
                    });
                	$('#classModal_Player').modal({backdrop:false,show:true});
                	$('#classModal_Player').show();
                	
                } else if (childObj.type == "text") {
                    $('#input_text_message').css({position: "absolute",
                        "margin":"0px",
                        'left': childObj.x,
                        'top': childObj.y+20,
                        'width':childObj.width,
                    });
                    $('#input_text_message').val(childObj.text);
                    $('#input_clickx').val(childObj.x);
                    $('#input_clicky').val(childObj.y);
                    $('#input_listindex').val(i);
                    $('#input_text_message').show();
                    $('#input_text_message').focus();
                } else {
                    if (com.isRotating == false) {
                        com.isRotating = true;
                        com.to_save = true;
                        //com.opUndoLists.push(com.arr2Clone(com.childList));
                    }
                }
                com.show();
                return;
            }
        }
    }
}

$('#input_text_message').off('input propertychange');
$('#input_text_message').on('input propertychange', function(){
    var index = $('#input_listindex').val();
    var inputText = $('#input_text_message').val();
    if (index > 0 && index < com.childList.length) {
        com.childList[index].text = inputText;
        com.show();
        $('#input_text_message').css({
            'width':com.childList[index].width,
        });   
    } else {
    	var tmp = new com.class.ObjText("text",parseInt($('#input_clickx').val()), parseInt($('#input_clicky').val()), inputText);
    	com.childList.push(tmp);
    	index = com.childList.length-1;
        $('#input_listindex').val(index);
    	com.show();
        $('#input_text_message').css({
            'width':com.childList[index].width,
        }); 
    }
});


function blockContextMenu(e) {
    return false;
}

com.editPlayerObj = function(index, player_name, player_jerseyid, player_pos, player_team){
    if (index > 0 && index < com.childList.length) {
		for (var i = 0; i< com.childList.length; i++) {
			if(i!=index && com.childList[i].type == player_team && player_jerseyid == com.childList[i].jerseyid) {
				alert("号码为 "+com.childList[i].jerseyid+" 的队员已经存在, 无法更改");
				return;
			}
		}

        com.childList[index].name = player_name || "";
        com.childList[index].type = player_team;
        com.childList[index].sys_img = player_team;
        com.childList[index].position = player_pos || "";
        com.childList[index].jerseyid = player_jerseyid || "";
        com.show();
    }
    $('#classModal_Player').modal('hide');
}

function canvasMouseUp(e, touch) {
	var e1 = e || window.event;
	com.ctMove.clearRect(0, 0, com.width, com.height);
	
    // 鼠标新的位置.
	com.tacticsarea = com.tacticsarea || {}
	com.tacticsarea.offsetLeft = $('#soccerboard').offset().left;
	com.tacticsarea.offsetTop = $('#soccerboard').offset().top;
	com.tacticsarea.center_x = $('#soccerboard').offset().left + $('#soccerboard').width()/2;
	com.tacticsarea.center_y = $('#soccerboard').offset().top + $('#soccerboard').height()/2;
    var e;
	try{
		e1.preventDefault(); 
		e = e1.originalEvent.touches[0];
	}
	catch(err) {
		e = e1;
	    if(e.button == "2"){  // right click up
	        return false;
	    }  
	}

    com.isPainting = false;
    if (com.ptList != null) {   
        if(com.paint!= "ruler_rect") {
        	com.opUndoLists.push(com.arr2Clone(com.childList));
            com.childList.mypush(com.ptList);
        }
        com.ptList = null;
        com.show();
    }
    if (com.isDragging == true) {
    	com.show();
        com.isDragging = false;
    }
    if (com.isResizing == true) {
    	com.show();
        com.isResizing = false;
    }
    if (com.isRotating == true) {
    	com.show();
        com.isRotating = false;
    }
    if(com.paint == "ruler_rect" && com.ruler != null) {
    	com.ruler.show();
    	var tmp_x_in_pix = Math.abs(com.ruler.x2-com.ruler.x1);
    	var tmp_y_in_pix = Math.abs(com.ruler.y2-com.ruler.y1);
    	$('#rect_width_pix').val(tmp_x_in_pix);
    	$('#rect_height_pix').val(tmp_y_in_pix);
    	$('#rect_width').val(Math.floor(tmp_x_in_pix*com.x_m_per_pix*10)/10);
    	$('#rect_height').val(Math.floor(tmp_y_in_pix*com.y_m_per_pix*10)/10);
    	$('#modal_context').css({
            position: "absolute",
            "margin":"0px",
            'left': com.tacticsarea.center_x-($('#modal_context').width()/2)- $(document).scrollLeft(),
            'top': com.tacticsarea.center_y-($('#modal_context').height()/2)- $(document).scrollTop(),
        });
    	$('#classModal').modal({backdrop:false,show:true});
    	$('#classModal').show();
    	//com.paint = "none";
    	//com.ruler = null;
    	com.resetTools();
    }
}

com.ruler = null;
function outCanvas() {
	com.isResizing = false;
	if(com.ruler != null) {
		return;
	}
    com.isPainting = false;
    if (com.ptList != null) {
        com.opUndoLists.push(com.arr2Clone(com.childList))
        if(com.paint!= "ruler_rect") {
            com.childList.mypush(com.ptList);
        } else {
        	com.ruler = com.ptList;
        }
        com.ptList = null;
    }
    if (com.isDragging == true) {
    	// delete when drag out an obj
    	if(com.previousSelectedObj != null) {
    		for(var i = 0; i< com.childList.length; i++) {
    			if(com.previousSelectedObj.id == com.childList[i].id ){
    				 com.previousSelectedObj.isSelected = false;
                     com.opUndoLists.push(com.arr2Clone(com.childList));
                     com.childList.splice(i, 1);
                     com.show();
                     com.previousSelectedObj = null;
                     break;
    			}
    		}
    	}
        com.isDragging = false;
    }
    com.show();
    
    if(com.paint == "ruler_rect" && com.ruler != null) {
    	com.ruler.show();
    	var tmp_x_in_pix = Math.abs(com.ruler.x2-com.ruler.x1);
    	var tmp_y_in_pix = Math.abs(com.ruler.y2-com.ruler.y1);
    	$('#rect_width_pix').val(tmp_x_in_pix);
    	$('#rect_height_pix').val(tmp_y_in_pix);
    	$('#rect_width').val(Math.floor(tmp_x_in_pix*com.x_m_per_pix*10)/10);
    	$('#rect_height').val(Math.floor(tmp_y_in_pix*com.y_m_per_pix*10)/10);
    	$('#modal_context').css({
            position: "absolute",
            "margin":"0px",
            'left': com.tacticsarea.center_x-($('#modal_context').width()/2)- $(document).scrollLeft(),
            'top': com.tacticsarea.center_y-($('#modal_context').height()/2)- $(document).scrollTop(),
        });
    	$('#classModal').modal({backdrop:false,show:true});
    	$('#classModal').show();
    	//com.ruler = null;
    }
}

com.setCancel = function(modal) {
	com.show();
	com.ruler = null;
	modal.modal('hide');
}

com.modalCancel = function(modal) {
	modal.modal('hide');
}


com.setMeterPerPix = function(width_in_pix, height_in_pix, width_in_meter, height_in_meter) {
	if(com.ruler != null && width_in_meter >0 && height_in_meter>0) {
		com.x_m_per_pix = parseFloat(width_in_meter)/width_in_pix;
		com.y_m_per_pix = parseFloat(height_in_meter)/height_in_pix;
	} else {
		alert("场地尺寸设置失败，请重新设置");
	}
	com.show();
	if(com.paint == "ruler_rect" && com.ruler != null) {
		com.ruler.show();
	}
	com.ruler = null;
	$('#classModal').modal('hide');
}

function getAngle(x1, y1, x2, y2) {
    // 直角的边长
    var x = Math.abs(x1 - x2);
    var y = Math.abs(y1 - y2);
    // 斜边长
    var z = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    // 正弦
    var sin = y / z;
    // 弧度
    var radina = Math.asin(sin);
    // 角度
    var angle = 180 / (Math.PI / radina);
    if (x1 > x2 && y1 < y2) {
        angle = 360 - angle;
    } else if (x1 < x2 && y1 > y2) {
        angle = 180 - angle;
    } else if (x1 < x2 && y1 < y2) {
        angle = 180 + angle;
    }
    return parseInt(angle);
}


function canvasMouseMove(e, touch) {
	var e1 = e || window.event;
    // 鼠标新的位置.
	com.tacticsarea = com.tacticsarea || {}
	com.tacticsarea.offsetLeft = $('#soccerboard').offset().left;
	com.tacticsarea.offsetTop = $('#soccerboard').offset().top;
	com.tacticsarea.center_x = $('#soccerboard').offset().left + $('#soccerboard').width()/2;
	com.tacticsarea.center_y = $('#soccerboard').offset().top + $('#soccerboard').height()/2;
    var x;
    var y;
	try{
		e1.preventDefault(); 
		e1 = e1.originalEvent.touches[0]
	}
	catch(err) {

	}
    x = e1.pageX - com.tacticsarea.offsetLeft;
    y = e1.pageY - com.tacticsarea.offsetTop;


    if(timelineBar.Play == true) {
        return;
    }
    
    if(com.annimation_timer) {
        com.annimation_timer && clearTimeout(com.annimation_timer);
        com.annimation_timer = null;
        com.show();
    }

    if(com.isResizing && com.previousSelectedObj != null && 
    		(com.previousSelectedObj.type=="player_1" || com.previousSelectedObj.type=="player_2" || com.previousSelectedObj.type=="tool")) {
        if (com.to_save == true) {
            com.to_save = false;
            com.opUndoLists.push(com.arr2Clone(com.childList));
        }
    	com.previousSelectedObj.width = Math.max(x-com.mouseLast_x + com.previousSelectedObj.width, 0);
    	com.previousSelectedObj.height = Math.max(y-com.mouseLast_y + com.previousSelectedObj.height, 0);
        com.mouseLast_x = x;
        com.mouseLast_y = y;
        com.ctMove.clearRect(0, 0, com.width, com.height);
        com.previousSelectedObj.show(com.ctMove);
        return;
    } 
    
    if (com.isDragging == true) {// 处于拖动状态
        if (com.previousSelectedObj != null) {// 被选中的圆
            if (com.to_save == true) {
                com.to_save = false;
                com.opUndoLists.push(com.arr2Clone(com.childList));
            }
            // Move the circle to that position.
            
            com.previousSelectedObj.moveWithOffset(x-com.mouseLast_x,y-com.mouseLast_y);
            com.mouseLast_x = x;
            com.mouseLast_y = y;
            //com.previousSelectedObj.x = x - com.previousSelectedObj_XOffset;
            //com.previousSelectedObj.y = y - com.previousSelectedObj_YOffset;
            
            com.ctMove.clearRect(0, 0, com.width, com.height);
            com.previousSelectedObj.show(com.ctMove);
            com.dragLine.x2 = com.previousSelectedObj.x+com.previousSelectedObj.width/2;
            com.dragLine.y2 = com.previousSelectedObj.y+com.previousSelectedObj.height/2;
            com.dragLine.show(com.ctMove);
            //com.show();
        }
    } else if (com.isRotating == true) {// 处于旋转状态
        if (com.previousSelectedObj != null) {// 被选中的
            if (com.to_save == true) {
                com.to_save = false;
                com.opUndoLists.push(com.arr2Clone(com.childList));
            }
            var x_center_pos = com.spaceX * com.previousSelectedObj.x + (com.previousSelectedObj.width / 2);
            var y_center_pos = com.spaceY * com.previousSelectedObj.y + (com.previousSelectedObj.height / 2);
            com.previousSelectedObj.rotate = getAngle(x, y, x_center_pos, y_center_pos);
            com.previousSelectedObj.rotate = com.previousSelectedObj.rotate % 360;
            //com.show();
            
            com.ctMove.clearRect(0, 0, com.width, com.height);
            com.previousSelectedObj.show(com.ctMove);
        }
    }
    else {
        if (com.paint == "player" || com.paint == "tool") {
            if (com.paintPen != null) {
                com.paintPen.x = x;
                com.paintPen.y = y;
                
                com.ctMove.clearRect(0, 0, com.width, com.height);
                com.paintPen.show(com.ctMove);
                
                //com.show();
                //com.paintPen.show();
            }
        }

        if (com.paint == "pencil" && com.isPainting == true) {
            com.p_x_now = x;// 当前瞬间的鼠标位置
            com.p_y_now = y;
            com.ptList.addPoint(com.p_x_now, com.p_y_now);
            var ctx = com.ctMask;
            var canvas = com.canvasMask;
            //com.ct.save();
            ctx.strokeStyle = com.line_color;
            ctx.beginPath();// 开始路径
            // 曲线是由一段段非常小的直线构成，计算机运算速度很快，这是一种以直线迭代画曲线的方式
            ctx.moveTo(com.p_x - 5, com.p_y - 5);// 移动到起始点
            ctx.lineTo(com.p_x_now - 5, com.p_y_now - 5);// 从起始点画直线到终点
            ctx.stroke();
            ctx.closePath();// 封闭路径，这个很重要，如果路径不封闭，
            //com.ct.restore();
            // 那么只要canvas颜色发生改变，所有的之前画过的颜色都发生改变
            com.p_x = com.p_x_now;// 一次迭代后将当前的瞬间坐标值赋给上次鼠标坐标值
            com.p_y = com.p_y_now;
        }

        if ((com.paint == "pencil_rect" || com.paint == "ruler_rect") && com.isPainting == true) {
            com.ptList.x2 = x;
            com.ptList.y2 = y;
            //com.show();
            com.ctMove.clearRect(0, 0, com.width, com.height);
            com.ptList.show(com.ctMove);
        }

        if (com.paint == "eraser" && com.isPainting == true) {
            com.p_x_now = x;// 当前瞬间的鼠标位置
            com.p_y_now = y;
            com.get("info").setAttribute("value", "x " + com.p_x_now);
            com.ptList.addPoint(com.p_x_now, com.p_y_now);
            var ctx = com.ctMask;
            //com.ct.save();
            var a = 5;
            var x1 = com.p_x;
            var y1 = com.p_y;
            var x2 = com.p_x_now;
            var y2 = com.p_y_now;

            ctx.save();
            ctx.lineCap = "round";
            ctx.lineJoin = "round";
            ctx.lineWidth = a * 2;
            ctx.globalCompositeOperation = "destination-out";

            ctx.save();
            ctx.beginPath()
            ctx.arc(x1, y1, 1, 0, 2 * Math.PI);
            ctx.fill();
            ctx.restore();

            ctx.save();
            ctx.moveTo(x1, y1);
            ctx.lineTo(x2, y2);
            ctx.stroke();
            ctx.restore();
            ctx.restore();
            com.p_x = com.p_x_now;// 一次迭代后将当前的瞬间坐标值赋给上次鼠标坐标值
            com.p_y = com.p_y_now;
        }

        if (com.paint == "line" && com.isPainting == true) {

            // create a new line object
            com.ptList.x2 = x;
            com.ptList.y2 = y;
            //com.show();
            //com.ptList.show();
            com.ctMove.clearRect(0, 0, com.width, com.height);
            com.ptList.show(com.ctMove);
        }

        if(com.previousSelectedObj != null){
        	return;
        }
        if (com.paintPen == null && com.isPainting == false && com.paint != "pencil" && com.paint != "pencil_rect" && com.paint != "ruler_rect" && com.paint != "line" && com.paint != "eraser") {
            // 判断点击了哪个Obj.
        	for (var i = com.childList.length - 1; i >= 0; i--) {
        		var childObj = com.childList[i];
                if (childObj.type != "background") {
                	childObj.isSelected = false;
                }
        	}
            for (var i = com.childList.length - 1; i >= 0; i--) {
                var childObj = com.childList[i];
                if (childObj.type != "background") {
                    if (childObj.isOnObj(x, y, 2)) {
                        childObj.isSelected = true;
                        if (com.lastShow != childObj) {
                            com.lastShow = childObj;
                            //com.lastShow.show(com.ctMove);
                            com.show();	
                        }
                        return;
                    } else {
                        childObj.isSelected = false;  
                    }
                }
            }
         // 判断点击了哪个线.
            for (var i = com.childList.length - 1; i >= 0; i--) {
                var childObj = com.childList[i];
                if (childObj.type != "background") {
                    if (childObj.isOnObj(x, y,1)) {
                        childObj.isSelected = true;
                        if (com.lastShow != childObj) {
                            com.lastShow = childObj;
                            //com.lastShow.show(com.ctMove);
                            com.show();	
                        }
                        return;
                    } else {
                        childObj.isSelected = false;  
                    }
                }
            }
            if(com.lastShow) {
            	com.show();
            	//com.ctMove.clearRect(0, 0, com.width, com.height);
            	com.lastShow = null;
            }
            
        }

    }
}

com.get = function (id) {
    return document.getElementById(id)
}

com.getCookie = function (name) {
    if (document.cookie.length > 0) {
        start = document.cookie.indexOf(name + "=")
        if (start != -1) {
            start = start + name.length + 1
            end = document.cookie.indexOf(";", start)
            if (end == -1)
                end = document.cookie.length
            return unescape(document.cookie.substring(start, end))
        }
    }
    return false;
}

com.arr2Clone = function (arr) {
    var newArr = [];
    com.get("info").setAttribute("value", com.opUndoLists.length + "")
    for (var i = 0; i < arr.length; i++) {
        newArr[i] = arr[i].slice();
    }
    return newArr;
}


com.savePlayer = function (in_name, index) {
    if (index > 0 && index < com.childList.length) {
        com.childList[index].name = in_name;
        com.show();
    }
    $('#input_text_message').hide();
}

com.saveTextMessage = function (inputText, index, click_x, click_y) {
    if (index > 0 && index < com.childList.length) {
        com.childList[index].text = inputText;
        com.show();
    } else {
    	var tmp = new com.class.ObjText("text",parseInt(click_x), parseInt(click_y), inputText);
    	com.childList.push(tmp);
    	com.show();
    }
    //com.resetTools();
    com.get("ic_text").setAttribute("bPressed","false");
    com.get("ic_text").setAttribute("src", com.imgPath+"/ic_text_sign.png");
    $('#input_text_message').hide();
}

com.resetTools = function () {
	var tmp = false;
    if (com.paint != "none") {
        com.paint = "none";
        tmp = true;
    }
    if (com.paintPen != null) {
        com.paintPen = null;
        tmp = true;
    }
    
    if(com.previousSelectedObj != null){
    	com.previousSelectedObj.isSelected = false;
    	com.previousSelectedObj = null;
    	com.show();
    }
    
    com.tool_imgSrc = null;
    com.isRotating = false;
    
	
    com.get("ic_pencil").setAttribute("style", "float:left");
    com.get("ic_pencil").setAttribute("src", com.imgPath+"/ic_pencil_normal.png");
    com.get("ic_delete").setAttribute("src", com.imgPath+"/ic_delete_normal.png");
    
    com.get("ic_ruler").setAttribute("bPressed","false");
    com.get("ic_ruler").setAttribute("src", com.imgPath+"/ic_ruler_normal.png");
    com.get("ic_text").setAttribute("bPressed","false");
    com.get("ic_text").setAttribute("src", com.imgPath+"/ic_text_sign.png");
    return tmp;
}

com.loadImages = function (stype) {
    com.bgImg = new Image();
    com.bgImg.src = com.imgPath+"/strategy_background1.png";  	
}

com.loadImagesCallBack = function(url, callback) {
	com.bgImg = new Image(); // 创建一个Image对象，实现图片的预下载
	com.bgImg.src = url;

	if (com.bgImg.complete) { // 如果图片已经存在于浏览器缓存，直接调用回调函数
		callback.call();
		return; // 直接返回，不用再处理onload事件
	}
	com.bgImg.onload = function() { // 图片下载完毕时异步调用callback函数。
		callback.call();// 将回调函数的this替换为Image对象
	};
};
	
com.class = com.class || {}
com.between = function (a,b,c){
	if(a<=b) {
		return (a<=c&&c<=b);
	} else {
		return (b<=c&&c<=a);
	}
}

com.class.Bg = function (imgSrc, x, y, width, height, x_m_per_pix, y_m_per_pix) {
    this.id = Guid.NewGuid().ToString("N");
    this.sys_img = "";
    this.x = x || 0;
    this.y = y || 0;
    this.isShow = true;
    this.type = "background";
    this.img = new Image();
    this.zindex = 0;
    this.imgSrc = imgSrc || (com.imgPath+"/strategy_background1.png");
    this.img.src = imgSrc || (com.imgPath+"/strategy_background1.png");
    this.x_m_per_pix = x_m_per_pix || com.x_m_per_pix;
    this.y_m_per_pix = y_m_per_pix || com.y_m_per_pix;
    this.name = "";
    this.rotate = 0;
    this.width = width || com.width;
    this.height = height || com.height;
    
    this.resize = function () {
    	if(this.x_m_per_pix!=com.x_m_per_pix) {
    		this.x_m_per_pix = com.x_m_per_pix;
    	}
    	if(this.y_m_per_pix!=com.y_m_per_pix) {
    		this.y_m_per_pix = com.y_m_per_pix;
    	}
    	if(com.width != this.width) {		
    		var scale_x = com.width/this.width;
    		var scale_y = com.height/this.height;
    		this.width = com.width;
    		this.height = com.height; 
    		this.x = Math.floor(this.x*scale_x*100)/100;
    		this.y = Math.floor(this.y*scale_y*100)/100;	
    	}	
    }
    this.show = function () {
    	this.resize();
    	
        if (this.isShow) {
        	com.ct.save();
            com.ct.drawImage(this.img, com.spaceX * this.x,
            		com.spaceY * this.y, this.width, this.height);
            com.ct.restore();   	
        }
    }
    this.saveData = function () {
        var data = JSON.stringify(this);
        return data;
    }
    this.init = function (dataParse) {
        this.id = dataParse.id;
        this.sys_img = dataParse.sys_img;
        if(this.sys_img && this.sys_img!="") {
            this.imgSrc = com.imgPath + com.resmapping[this.sys_img];
            this.img.src = this.imgSrc;	
        } else {
            this.imgSrc = dataParse.imgSrc;
            this.img.src = dataParse.imgSrc;
        }
        this.x = dataParse.x;
        this.y = dataParse.y;
        this.isShow = dataParse.isShow;
        this.type = dataParse.type;

        this.name = dataParse.name;
        this.width = dataParse.width;
        this.height = dataParse.height;
        this.rotate = dataParse.rotate;
        this.x_m_per_pix = dataParse.x_m_per_pix;
        this.y_m_per_pix = dataParse.y_m_per_pix;
    }
    this.slice = function () {
        var tmp = new com.class.Bg(this.img.src, this.x, this.y, this.width, this.height,this.x_m_per_pix,this.y_m_per_pix);
        tmp.isShow = this.isShow;
        tmp.name = this.name;
        tmp.type = this.type;
        tmp.rotate = this.rotate;
        tmp.width = this.width;
        tmp.height = this.height;
        tmp.id = this.id;
        tmp.imgSrc = this.imgSrc;
        tmp.bg_width = this.bg_width;
        tmp.bg_height = this.bg_height;
        tmp.sys_img = this.sys_img;
        return tmp;
    }
    this.isOnObj = function (x, y, targetZ) {
    	if(targetZ>this.zindex) {
    		return false;
    	}
    	if(com.between(this.x,(this.x + this.width),x) && 
    			com.between(this.y,(this.y + this.height),y)) {
    		return true;
    	}
        return false;
    }
}


//圆角矩形
CanvasRenderingContext2D.prototype.roundRect = function (x, y, w, h, r) {
    var min_size = Math.min(w, h);
    if (r > min_size / 2) r = min_size / 2;
    // 开始绘制
    this.beginPath();
    this.moveTo(x + r, y);
    this.arcTo(x + w, y, x + w, y + h, r);
    this.arcTo(x + w, y + h, x, y + h, r);
    this.arcTo(x, y + h, x, y, r);
    this.arcTo(x, y, x + w, y, r);
    this.closePath();
    return this;
}

com.class.Obj = function (type, imgSrc, name, x, y, width, height) {
    this.id = Guid.NewGuid().ToString("N");
    this.sys_img = "";
    this.jerseyid = "";
    this.position = "";
    this.zindex = 2;
    this.x = x || 0;
    this.y = y || 0;
    this.isShow = true;
    this.type = type || "player_1";
    this.imgPlayerBg = new Image();
    this.img = new Image();
    this.imgSrc = imgSrc || "";
    this.avatar_id = "";
    this.srcInitialized = false;
    this.name = name || "";
    this.width = width || com.playersize;
    this.height = height || com.playersize;
    this.bg_width = com.width;
    this.bg_height = com.height;
    this.isSelected = false;
    this.rotate = 0;
    this.saveData = function () {
        var data = JSON.stringify(this);
        return data;
    }
    this.resize = function ( ) {
    	if(com.width != this.bg_width) {
    		var scale_x = com.width/this.bg_width;
    		var scale_y = com.height/this.bg_height;
    		this.width = this.width*scale_x;
    		this.height = this.height*scale_y; 
    		this.width = com.playersize;//Math.max(com.playersize, Math.floor(this.width + 0.5));
    		this.height = com.playersize;//Math.max(com.playersize, Math.floor(this.height + 0.5));
    		this.x = Math.floor(this.x*scale_x*100)/100;
    		this.y = Math.floor(this.y*scale_y*100)/100;	
    		this.bg_width = com.width;
    		this.bg_height = com.height; 
    	}	
    }
    this.init = function (dataParse) {
        this.id = dataParse.id;
        this.sys_img = dataParse.sys_img;
        this.x = dataParse.x;
        this.y = dataParse.y;
        this.isShow = dataParse.isShow;
        this.type = dataParse.type;
        this.avatar_id = dataParse.avatar_id;
        if(this.type == "player_1" || this.type == "player_2") {
        	this.imgSrc = dataParse.imgSrc;
        } else {
            if(this.sys_img && this.sys_img!="") {
                this.imgSrc = com.imgPath + com.resmapping[this.sys_img];
            } else {
                this.imgSrc = dataParse.imgSrc;
            }
        }
        this.name = dataParse.name;
        this.width = dataParse.width;
        this.height = dataParse.height;
        this.isSelected = dataParse.isSelected;
        this.rotate = dataParse.rotate;
        this.bg_width = dataParse.bg_width;
        this.bg_height = dataParse.bg_height;
        this.jerseyid = dataParse.jerseyid;
        this.position = dataParse.position;
    }
    this.show = function (ctx_in) {
    	var ctx = ctx_in || com.ct;
    	this.resize();
        if (this.isShow) {
        	ctx.save();
        	ctx.fillStyle = "white";
            var xpos = com.spaceX * this.x + (this.width / 2);
            var ypos = com.spaceY * this.y + (this.height / 2);
            if(this.rotate!=0) {
            	ctx.beginPath();
            	ctx.translate(xpos, ypos);
            	ctx.rotate(this.rotate * Math.PI / 180);//旋转
            	ctx.translate(-xpos, -ypos);	
            }

            if(this.type == "player_1" || this.type == "player_2") {
                if(this.sys_img && this.sys_img!="") {
                	this.imgPlayerBg.src = com.imgPath + com.resmapping[this.sys_img];
                } else {
                	this.imgPlayerBg.src = (this.type == "player_1")? (com.imgPath+"/ic_red_normal.png") : (com.imgPath+"/ic_blue_normal.png");
                }
            	this.imgPlayerBg.src = (this.type == "player_1")? (com.imgPath+"/ic_red_normal.png") : (com.imgPath+"/ic_blue_normal.png");
            	ctx.drawImage(this.imgPlayerBg, com.spaceX * this.x,
                		com.spaceY * this.y, this.width, this.height);
            	
            	if( this.avatar_id && this.avatar_id != null &&  this.avatar_id != "") {
            		this.imgSrc = com.avatorPath+this.avatar_id;
            		var sameImage = this.imgSrc.indexOf("ic_red_normal.png") >=0 || this.imgSrc.indexOf("ic_blue_normal.png") >=0;
            		if(sameImage == false) {
                    	var insideW = Math.max(2,Math.floor(Math.sqrt(Math.pow(this.width,2)/2))-2);
                    	ctx.save();
                    	try
                    	{
                    		if(this.srcInitialized == false) {
                    			this.img.crossOrigin = "*";
                    			this.img.src = this.imgSrc;
                    			this.srcInitialized = true;
                    		}
                    		ctx.beginPath();
                    		ctx.arc(xpos,ypos,insideW/2,0,360,false);
                    		ctx.clip();
                    		ctx.beginPath();
                    		ctx.drawImage(this.img, xpos-(insideW/2), ypos-(insideW/2), insideW, insideW);
                    	}
                    	catch (e)
                    	{
                    		
                    	} 
                    	ctx.restore();
            		}
            	}
            } else {
        		if(this.srcInitialized == false) {
        			this.img.src = this.imgSrc;
        			this.srcInitialized = true;
        		}
        		ctx.drawImage(this.img, com.spaceX * this.x,
                		com.spaceY * this.y, this.width, this.height);
            }
            ctx.textAlign='center';
            ctx.textBaseline='bottom';
            if (this.type == "player_1" || this.type == "player_2") {
            	ctx.font = com.font;
            	ctx.fillText(this.name, xpos, com.spaceY * this.y);
            	//ctx.fillText(this.name+"("+this.jerseyid+")", com.spaceX * this.x, com.spaceY * this.y);
            	ctx.textBaseline='middle';
            	ctx.font = ctx.fontJerseyId;
            	ctx.fillText(this.jerseyid+"", xpos, ypos);
            	ctx.textBaseline='bottom';
            }
            else if (this.type == "current_tool") {
            	ctx.font = com.font;
            	ctx.fillText(this.name, com.spaceX * this.x, com.spaceY * this.y);
            }
            if (this.isSelected == true) {
                if (com.paint == "delete") {
                	ctx.strokeStyle = "rgb(255,0,0)";
                } else {
                	ctx.strokeStyle = "rgb(0,255,0)";
                }
                ctx.strokeRect(com.spaceX * this.x, com.spaceY * this.y, this.width, this.height);
            }
            ctx.restore();
        }
    }
    this.slice = function () {
        var tmp = new com.class.Obj(this.type, this.imgSrc, this.name, this.x,
            this.y, this.width, this.height);
        tmp.isShow = this.isShow;
        tmp.isSelected = this.isSelected;
        tmp.rotate = this.rotate;
        tmp.id = this.id;
        tmp.bg_width = this.bg_width;
        tmp.bg_height = this.bg_height;
        tmp.jerseyid = this.jerseyid;
        tmp.position = this.position;
        tmp.sys_img = this.sys_img;
        tmp.avatar_id = this.avatar_id;
        return tmp;
    }
    this.isOnObj = function (x, y, targetZ) {
    	if(targetZ>this.zindex) {
    		return false;
    	}
    	if(com.between(this.x,(this.x + this.width),x) && 
    			com.between(this.y,(this.y + this.height),y)) {
    		return true;
    	}
        return false;
    }
    
    this.isOnResizeArea = function (x, y) {
    	if(com.between((this.x + this.width - 4),(this.x + this.width),x) && 
    			com.between((this.y + this.height -4),(this.y + this.height),y)) {
    		return true;
    	}
        return false;
    }
    
    this.moveWithOffset = function(offsetX, offsetY) {
    	this.x += offsetX;
    	this.y += offsetY;
    };
}

com.class.ObjText = function (type, x, y, text) {
    this.id = Guid.NewGuid().ToString("N");
    this.zindex = 2;
    this.x = x || 0;
    this.y = y || 0;
    this.isShow = true;
    this.type = type || "text";
    this.text = text || "";
    this.width = com.playersize;
    this.height = com.playersize;
    this.bg_width = com.width;
    this.bg_height = com.height;
    this.isSelected = false;
    this.saveData = function () {
        var data = JSON.stringify(this);
        return data;
    }
    this.resize = function ( ) {
    	if(com.width != this.bg_width) {
    		var scale_x = com.width/this.bg_width;
    		var scale_y = com.height/this.bg_height;
    		this.x = Math.floor(this.x*scale_x*100)/100;
    		this.y = Math.floor(this.y*scale_y*100)/100;			
    		this.bg_width = com.width;
    		this.bg_height = com.height; 
    	}	
    }
    this.init = function (dataParse) {
        this.id = dataParse.id;
        this.x = dataParse.x;
        this.y = dataParse.y;
        this.isShow = dataParse.isShow;
        this.type = dataParse.type;
        this.text = dataParse.text;
        this.isSelected = dataParse.isSelected;
        this.bg_width = dataParse.bg_width;
        this.bg_height = dataParse.bg_height;
        this.width = dataParse.width;
        this.height = dataParse.height;
    }
    this.show = function (ctx_in) {
    	var ctx = ctx_in || com.ct;
    	this.resize();
        if (this.isShow) {
        	ctx.save();
        	ctx.font = "14pt 宋体";
        	this.width = ctx.measureText(this.text).width;
        	this.height = 20;
        	ctx.textBaseline = "top";
        	ctx.fillText(this.text, com.spaceX * this.x, com.spaceY * this.y);
        	
            if (this.isSelected == true) {
                if (com.paint == "delete") {
                	ctx.strokeStyle = "rgb(255,0,0)";
                } else {
                	ctx.strokeStyle = "rgb(0,255,0)";
                }
                ctx.strokeRect(com.spaceX * this.x, com.spaceY * this.y, this.width, this.height);
            }
            ctx.restore();
        }
    }
    this.slice = function () {
        var tmp = new com.class.ObjText(this.type, this.x,
            this.y, this.text);
        tmp.isShow = this.isShow;
        tmp.isSelected = this.isSelected;
        tmp.id = this.id;
        tmp.bg_width = this.bg_width;
        tmp.bg_height = this.bg_height;
        tmp.width = this.width;
        tmp.height = this.height;
        return tmp;
    }
    this.isOnObj = function (x, y, targetZ) {
    	if(targetZ>this.zindex) {
    		return false;
    	}
    	var width = Math.max(com.playersize, this.width);
    	var height = Math.max(com.playersize, this.height);
    	if(com.between(this.x,(this.x + width),x) && 
    			com.between(this.y,(this.y + height),y)) {
    		return true;
    	}
        return false;
    }
    this.moveWithOffset = function(offsetX, offsetY) {
    	this.x += offsetX;
    	this.y += offsetY;
    };
}

com.class.Point = function (x, y) {
    this.x = x;
    this.y = y;
    //this.type = "point";
    this.slice = function () {
        var tmp = new com.class.Point(this.x, this.y);
        return tmp;
    }
    this.saveData = function () {
        var data = JSON.stringify(this);
        return data;
    }
}

function getH2_C(p_b,p_c,p_a) {
	var a = Math.sqrt(Math.pow(p_b.x-p_c.x,2)+Math.pow(p_b.y-p_c.y,2));
	var b = Math.sqrt(Math.pow(p_a.x-p_c.x,2)+Math.pow(p_a.y-p_c.y,2));
	var c =  Math.sqrt(Math.pow(p_b.x-p_a.x,2)+Math.pow(p_b.y-p_a.y,2));
	return (a+b+c)*(a+b-c)*(a+c-b)*(b+c-a)/(4*c*c);
}

com.class.PointsList = function (color, imgSrc) {
    this.id = Guid.NewGuid().ToString("N");
    this.pointsList = [];
    this.isShow = true;
    this.zindex = 1;
    this.color = color || "blue";
    this.type = "PointsList";
    this.freestyle = false;
    this.bg_width = com.width;
    this.bg_height = com.height;
    this.isSelected = false;
    this.imgSrc = imgSrc || (com.imgPath+"/ic_pencil_normal.png");
    this.addPoint = function (x, y) {
        var pt = new com.class.Point(x, y);
        this.pointsList.push(pt);
//        if(this.pointsList.length <= 4) {
//        	this.pointsList.push(pt);
//        } else {
//        	this.pointsList.splice(3,1,pt);
//        }
//        
    }
    this.clearPoints = function () {
        this.pointsList = [];
    }
    this.saveData = function () {
        var data = JSON.stringify(this);
        return data;
    }
    this.init = function (dataParse) {
        this.type = dataParse.type;
        this.id = dataParse.id;
        this.pointsList = dataParse.pointsList;
        this.isShow = dataParse.isShow;
        this.imgSrc = dataParse.imgSrc;
        this.color = dataParse.color;
        this.bg_width = dataParse.bg_width;
        this.bg_height = dataParse.bg_height;
        this.freestyle = dataParse.freestyle;
    }
    
    this.resize = function ( ) {
    	if(com.width != this.bg_width) {
    		var scale_x = com.width/this.bg_width;
    		var scale_y = com.height/this.bg_height;	
    		for (var i = 0; i < this.pointsList.length; i++) {
    			this.pointsList[i].x = this.pointsList[i].x*scale_x;
    			this.pointsList[i].y = this.pointsList[i].y*scale_y;
    			this.pointsList[i].x = Math.floor(this.pointsList[i].x*100)/100;
    			this.pointsList[i].y = Math.floor(this.pointsList[i].y*100)/100;
    		}
    		this.bg_width = com.width;
    		this.bg_height = com.height; 
    	}	
    }
    

    this.drawArrowhead = function (ctx, x, y, radians) {
        ctx.save();
        if (this.isSelected == true) {
            if (com.paint == "delete") {
            	ctx.strokeStyle = "rgb(255,0,0)";
            	ctx.fillStyle = "rgb(255,0,0)";
            } else {
            	ctx.strokeStyle = "rgb(0,255,0)";
            	ctx.fillStyle = "rgb(0,255,0)";
            }
        } else {
            ctx.strokeStyle = this.color;	
            ctx.fillStyle = this.color;
        }
        ctx.beginPath();
        ctx.translate(x, y);
        ctx.rotate(radians);
  
        ctx.moveTo(0, -20);
        ctx.lineTo(5, 0);
        ctx.lineTo(-5, 0);
        ctx.closePath();
        ctx.fill();
        ctx.restore();
    }
    
    this.show = function (ctx_in) {
        if(this.pointsList.length>=4 && this.freestyle == false){
        	var maxH = -1; 
        	var maxIndex = -1; 
			for (var i = 1; i < this.pointsList.length-1; i++) {
				var tmp = getH2_C(this.pointsList[0],this.pointsList[i],this.pointsList[this.pointsList.length-1]);
				if(tmp>maxH) {
					maxH = tmp;
					maxIndex = i;
				}
			}
			
			var tmp_p = this.pointsList[maxIndex].slice();
			this.pointsList.splice(1,this.pointsList.length-2,tmp_p);
        }
        
    	this.resize();
        var ctx = ctx_in || (this.freestyle==true? com.ctMask : com.ct);
        var canvas = com.canvasMask;
        if (this.isShow) {
            ctx.save();
            if (this.isSelected == true) {
                if (com.paint == "delete") {
                	ctx.strokeStyle = "rgb(255,0,0)";
                	ctx.fillStyle = "rgb(255,0,0)";
                } else {
                	ctx.strokeStyle = "rgb(0,255,0)";
                	ctx.fillStyle = "rgb(0,255,0)";
                }
            } else {
                ctx.strokeStyle = this.color;	
                ctx.fillStyle = this.color;
            }
            
            if(this.freestyle == false) {
                while(this.pointsList.length<3){
                	var tmp = this.pointsList[this.pointsList.length-1].slice();
                	this.pointsList.splice(this.pointsList.length-1,0,tmp);
                }

                var x1 = (this.pointsList[1].x-5) * 2 - (this.pointsList[0].x + this.pointsList[this.pointsList.length-1].x -10) / 2;
                var y1 = (this.pointsList[1].y-5) * 2 - (this.pointsList[0].y + this.pointsList[this.pointsList.length-1].y-10) / 2;
      
                ctx.beginPath();
                ctx.moveTo(this.pointsList[0].x-5,this.pointsList[0].y-5);
                ctx.quadraticCurveTo(x1,y1,this.pointsList[2].x-5,this.pointsList[2].y-5);
                ctx.stroke();

                // draw the ending arrowhead
                var endRadians = Math.atan((this.pointsList[2].y - y1+5) / (this.pointsList[2].x - x1+5));
                endRadians += ((this.pointsList[2].x - x1+5) > 0 ? 90 : -90) * Math.PI / 180;
                this.drawArrowhead(ctx, this.pointsList[2].x-5, this.pointsList[2].y-5, endRadians);
                ctx.restore();
                var width = Math.floor(Math.abs(this.pointsList[2].x-this.pointsList[0].x) * com.x_m_per_pix * 10)/10;
                var height = Math.floor(Math.abs(this.pointsList[2].y-this.pointsList[0].y) * com.y_m_per_pix * 10)/10;
                var len = Math.floor(Math.sqrt((Math.pow(width,2) + Math.pow(height,2)),2)*10)/10;
                ctx.fillText(len+"米", com.spaceX * (this.pointsList[1].x-5), com.spaceY * (this.pointsList[1].y-5));
            } else {
            	for (var i = 0; i < this.pointsList.length; i++) {
					if (i == 0) {
						p_x = this.pointsList[i].x;
						p_y = this.pointsList[i].y;
					}
					p_x_now = this.pointsList[i].x;
					p_y_now = this.pointsList[i].y;
					ctx.beginPath();// 开始路径
					  // 曲线是由一段段非常小的直线构成，计算机运算速度很快，这是一种以直线迭代画曲线的方式
					ctx.moveTo(p_x - 5, p_y - 5);// 移动到起始点
					ctx.lineTo(p_x_now - 5, p_y_now - 5);// 从起始点画直线到终点
					ctx.stroke();
					ctx.closePath();// 封闭路径，这个很重要，如果路径不封闭，
					  // 那么只要canvas颜色发生改变，所有的之前画过的颜色都发生改变
					p_x = p_x_now;// 一次迭代后讲当前的瞬间坐标值赋给上次鼠标坐标值
					p_y = p_y_now;
	          	}	
            	ctx.restore();
            }
        }
    }
    this.slice = function () {
        var tmp = new com.class.PointsList(this.color);
        for (var i = 0; i < this.pointsList.length; i++) {
            tmp.addPoint(this.pointsList[i].x, this.pointsList[i].y);
        }
        tmp.id = this.id;
        tmp.imgSrc = this.imgSrc;
        tmp.bg_width = this.bg_width;
        tmp.bg_height = this.bg_height;
        tmp.freestyle = this.freestyle;
        return tmp;
    }
    
    this.isOnObj = function (x, y, targetZ) {
    	if(targetZ>this.zindex || this.freestyle == true) {
    		return false;
    	}
    	var dist1 = Math.sqrt(Math.pow(this.pointsList[0].x-x -5,2)+Math.pow(this.pointsList[0].y-y-5,2));
    	if(dist1 <= 5) {
    		return true;
    	}
    	var dist2 = Math.sqrt(Math.pow(this.pointsList[1].x-x,2)+Math.pow(this.pointsList[1].y-y,2));
    	if(dist2 <= 5) {
    		return true;
    	}
    	var dist3 = Math.sqrt(Math.pow(this.pointsList[2].x-x-5,2)+Math.pow(this.pointsList[2].y-y-5,2));
    	if(dist3 <= 5) {
    		return true;
    	}
        return false;
    }
    this.moveWithOffset = function(offsetX, offsetY) {
    	for(var i = 0; i<this.pointsList.length; i++ ) {
        	this.pointsList[i].x += offsetX;
        	this.pointsList[i].y += offsetY;
    	}
    };
}

com.class.EraserPointsList = function (color, imgSrc) {
    this.id = Guid.NewGuid().ToString("N");
    this.type = "EraserPointsList";
    this.pointsList = [];
    this.isShow = true;
    this.zindex = 1;
    this.color = color || "blue";
    this.bg_width = com.width;
    this.bg_height = com.height;
    this.imgSrc = imgSrc || (com.imgPath+"/ic_eraser_normal.png");
    this.a = 5;
    this.addPoint = function (x, y) {
        var pt = new com.class.Point(x, y);
        this.pointsList.push(pt);
    }
    this.clearPoints = function () {
        this.pointsList = [];
    }
    this.saveData = function () {
        var data = JSON.stringify(this);
        return data;
    }
    this.init = function (dataParse) {
        this.type = dataParse.type;
        this.id = dataParse.id;
        this.pointsList = dataParse.pointsList;
        this.isShow = dataParse.isShow;
        this.imgSrc = dataParse.imgSrc;
        this.color = dataParse.color;
        this.bg_width = dataParse.bg_width;
        this.bg_height = dataParse.bg_height;
        this.a = dataParse.a;
    }
    this.resize = function ( ) {
    	if(com.width != this.bg_width) {
    		var scale_x = com.width/this.bg_width;
    		var scale_y = com.height/this.bg_height;	
    		this.a = this.a * scale_x; 
    		for (var i = 0; i < this.pointsList.length; i++) {
    			this.pointsList[i].x = this.pointsList[i].x*scale_x;
    			this.pointsList[i].y = this.pointsList[i].y*scale_y;
    			this.pointsList[i].x = Math.floor(this.pointsList[i].x*100)/100;
    			this.pointsList[i].y = Math.floor(this.pointsList[i].y*100)/100;
    		}
    		this.bg_width = com.width;
    		this.bg_height = com.height; 
    	}	
    }
    this.show = function (ctx_in) {
    	this.resize();
        var ctx = ctx_in || com.ctMask;
        if (this.isShow) {
            var x1 = 0;
            var y1 = 0;
            var x2 = 0;
            var y2 = 0;
            ctx.save();
            ctx.strokeStyle = this.color;
            for (var i = 0; i < this.pointsList.length; i++) {
                if (i == 0) {
                    p_x = this.pointsList[i].x;
                    p_y = this.pointsList[i].y;
                }
                p_x_now = this.pointsList[i].x;
                p_y_now = this.pointsList[i].y;

                x1 = p_x;
                y1 = p_y;
                x2 = p_x_now;
                y2 = p_y_now;

                ctx.lineCap = "round";
                ctx.lineJoin = "round";
                ctx.lineWidth = this.a * 2;
                ctx.globalCompositeOperation = "destination-out";

                ctx.save();
                ctx.beginPath()
                ctx.arc(x1, y1, 1, 0, 2 * Math.PI);
                ctx.fill();
                ctx.restore();

                ctx.save();
                ctx.moveTo(x1, y1);
                ctx.lineTo(x2, y2);
                ctx.stroke();
                ctx.restore();

//				com.ct.beginPath();// 开始路径
//				// 曲线是由一段段非常小的直线构成，计算机运算速度很快，这是一种以直线迭代画曲线的方式
//				com.ct.moveTo(p_x - 5, p_y - 5);// 移动到起始点
//				com.ct.lineTo(p_x_now - 5, p_y_now - 5);// 从起始点画直线到终点
//				com.ct.stroke();
//				com.ct.closePath();// 封闭路径，这个很重要，如果路径不封闭，
//				// 那么只要canvas颜色发生改变，所有的之前画过的颜色都发生改变
                p_x = p_x_now;// 一次迭代后讲当前的瞬间坐标值赋给上次鼠标坐标值
                p_y = p_y_now;
            }
            ctx.restore();
        }
    }
    this.slice = function () {
        var tmp = new com.class.EraserPointsList(this.color);
        for (var i = 0; i < this.pointsList.length; i++) {
            tmp.addPoint(this.pointsList[i].x, this.pointsList[i].y);
        }
        tmp.id = this.id;
        tmp.imgSrc = this.imgSrc;
        tmp.bg_width = this.bg_width;
        tmp.bg_height = this.bg_height;
        tmp.a = this.a;
        return tmp;
    }
    this.isOnObj = function (x, y, targetZ) {
    	if(targetZ>this.zindex) {
    		return false;
    	}
//    	var x1 = this.pointsList[0].x - 5;
//    	var y1 = this.pointsList[0].y - 5;
//    	
//    	var width = this.pointsList[2].x-this.pointsList[0].x + 2;
//    	var height = this.pointsList[2].y-this.pointsList[0].y +2;
//    	
//        if (x1 <= x && x <= (x1 + width) && y1 <= y
//            && y <= (y1 + height)) {
//        	alert(1)
//            return true;
//        }
        return false;
    }
}

com.class.Line = function (x1, y1, x2, y2, start, end, pattern, color, imgSrc) {
    this.id = Guid.NewGuid().ToString("N");
    this.type = "Line";
    this.x1 = x1 || 0;
    this.y1 = y1 || 0;
    this.x2 = x2 || 0;
    this.y2 = y2 || 0;
    this.start = start || false;
    this.end = end || false;
    this.pattern = pattern || 0;
    this.color = color || "blue";
    this.isShow = true;
    this.zindex = 1;
    this.bg_width = com.width;
    this.bg_height = com.height;
    this.imgSrc = imgSrc || (com.imgPath+"/ic_pencil_normal.png");
    this.saveData = function () {
        var data = JSON.stringify(this);
        return data;
    }
    this.init = function (dataParse) {
        this.type = dataParse.type;
        this.id = dataParse.id;
        this.x1 = dataParse.x1;
        this.y1 = dataParse.y1;
        this.x2 = dataParse.x2;
        this.y2 = dataParse.y2;
        this.start = dataParse.start;
        this.end = dataParse.end;
        this.pattern = dataParse.pattern;
        this.color = dataParse.color;
        this.isShow = dataParse.isShow;
        this.imgSrc = dataParse.imgSrc;
        this.bg_width = dataParse.bg_width;
        this.bg_height = dataParse.bg_height;
    }
    this.resize = function ( ) {
    	if(com.width != this.bg_width) {
    		var scale_x = com.width/this.bg_width;
    		var scale_y = com.height/this.bg_height;	
    		this.x1 = this.x1 * scale_x; 
    		this.y1 = this.y1 * scale_y; 
    		this.x2 = this.x2 * scale_x; 
    		this.y2 = this.y2 * scale_y; 
			this.x1 = Math.floor(this.x1*100)/100;
			this.y1 = Math.floor(this.y1*100)/100;
			this.x2 = Math.floor(this.x2*100)/100;
			this.y2 = Math.floor(this.y2*100)/100;
    		this.bg_width = com.width;
    		this.bg_height = com.height; 
    	}	
    }
    this.show = function (ctx_in) {
    	var ctx = ctx_in || com.ct;
    	this.resize();
        if (this.isShow) {
            this.drawWithArrowheads(ctx);
        }
    }
    this.slice = function () {
        var tmp = new com.class.Line(this.x1, this.y1, this.x2, this.y2, this.start, this.end, this.pattern, this.color);
        tmp.id = this.id;
        tmp.imgSrc = this.imgSrc;
        tmp.bg_width = this.bg_width;
        tmp.bg_height = this.bg_height;
        return tmp;
    }
    this.isOnObj = function (x, y, targetZ) {
    	if(targetZ>this.zindex) {
    		return false;
    	}
    	var tmp_p0 = new com.class.Point(this.x1,this.y1);
    	var tmp_p1 = new com.class.Point(x,y);
    	var tmp_p2 = new com.class.Point(this.x2,this.y2);
    	var width = this.x2-this.x1 + 2;
    	var height = this.y2-this.y1 + 2;
    	
    	if(com.between(this.x1,(this.x1 + width),x) && 
    			com.between(this.y1,(this.y1 + height),y)) {
        	var tmp = getH2_C(tmp_p0,tmp_p1,tmp_p2);
        	if(tmp <= 5*5) {
        		return true;
        	}
        	return false;
    	}

        return false;
    }
    this.moveWithOffset = function(offsetX, offsetY) {
    	this.x1 += offsetX;
    	this.y1 += offsetY;
    	this.x2 += offsetX;
    	this.y2 += offsetY;
    };
}

com.class.Line.prototype.drawWithArrowheads = function (ctx) {

    ctx.save();

    var width = Math.floor(Math.abs(this.x2-this.x1) * com.x_m_per_pix * 10)/10;
    var height = Math.floor(Math.abs(this.y2-this.y1) * com.y_m_per_pix * 10)/10;
    var len = Math.floor(Math.sqrt((Math.pow(width,2) + Math.pow(height,2)),2)*10)/10;
    ctx.fillText(len+"米", com.spaceX * ((this.x1+this.x2)/2), com.spaceY * ((this.y1+this.y2)/2));

    // arbitrary styling
    
    if (this.isSelected == true) {
        if (com.paint == "delete") {
        	ctx.strokeStyle = "rgb(255,0,0)";
        	ctx.fillStyle = "rgb(255,0,0)";
        } else {
        	ctx.strokeStyle = "rgb(0,255,0)";
        	ctx.fillStyle = "rgb(0,255,0)";
        }
    } else {
        ctx.strokeStyle = this.color;	
        ctx.fillStyle = this.color;
    }
    
    ctx.lineWidth = 1;

    if (this.pattern <= 0) {
        // draw the line
        ctx.beginPath();
        ctx.moveTo(this.x1, this.y1);
        ctx.lineTo(this.x2, this.y2);
        ctx.stroke();
    } else {
        // calculate the delta x and delta y
        var dx = (this.x2 - this.x1);
        var dy = (this.y2 - this.y1);
        var distance = Math.floor(Math.sqrt(dx * dx + dy * dy));
        var dashlineInteveral = (this.pattern <= 0) ? distance : (distance / this.pattern);
        var deltay = (dy / distance) * this.pattern;
        var deltax = (dx / distance) * this.pattern;

        // draw dash line  
        ctx.beginPath();
        for (var dl = 0; dl < dashlineInteveral; dl++) {
            if (dl % 2) {
                ctx.lineTo(this.x1 + dl * deltax, this.y1 + dl * deltay);
            } else {
                ctx.moveTo(this.x1 + dl * deltax, this.y1 + dl * deltay);
            }
        }
        ctx.stroke();
    }

    if (this.start) {
        // draw the starting arrowhead
        var startRadians = Math.atan((this.y2 - this.y1) / (this.x2 - this.x1));
        startRadians += ((this.x2 > this.x1) ? -90 : 90) * Math.PI / 180;
        this.drawArrowhead(ctx, this.x1, this.y1, startRadians);
    }

    if (this.end) {
        // draw the ending arrowhead
        var endRadians = Math.atan((this.y2 - this.y1) / (this.x2 - this.x1));
        endRadians += ((this.x2 >= this.x1) ? 90 : -90) * Math.PI / 180;
        this.drawArrowhead(ctx, this.x2, this.y2, endRadians);
    }

    ctx.restore();

}

com.class.Line.prototype.drawArrowhead = function (ctx, x, y, radians) {
    ctx.save();
    ctx.beginPath();
    ctx.translate(x, y);
    ctx.rotate(radians);
    ctx.moveTo(0, 0);
    ctx.lineTo(5, 20);
    ctx.lineTo(-5, 20);
    ctx.closePath();
    ctx.fill();
    ctx.restore();
}

com.class.Rect = function (x1, y1, x2, y2, color, imgSrc) {
    this.id = Guid.NewGuid().ToString("N");
    this.type = "Rect";
    this.x1 = x1 || 0;
    this.y1 = y1 || 0;
    this.x2 = x2 || 0;
    this.y2 = y2 || 0;
    this.color = color || "blue";
    this.zindex = 1;
    this.isShow = true;
    this.bg_width = com.width;
    this.bg_height = com.height;
    this.imgSrc = imgSrc || (com.imgPath+"/ic_rect_normal.png");
    this.saveData = function () {
        var data = JSON.stringify(this);
        return data;
    }
    this.init = function (dataParse) {
        this.type = dataParse.type;
        this.id = dataParse.id;
        this.x1 = dataParse.x1;
        this.y1 = dataParse.y1;
        this.x2 = dataParse.x2;
        this.y2 = dataParse.y2;
        this.color = dataParse.color;
        this.isShow = dataParse.isShow;
        this.imgSrc = dataParse.imgSrc;
        this.bg_width = dataParse.bg_width;
        this.bg_height = dataParse.bg_height;
    }
    this.resize = function ( ) {
    	if(com.width != this.bg_width) {
    		var scale_x = com.width/this.bg_width;
    		var scale_y = com.height/this.bg_height;	
    		this.x1 = this.x1 * scale_x; 
    		this.y1 = this.y1 * scale_y; 
    		this.x2 = this.x2 * scale_x; 
    		this.y2 = this.y2 * scale_y; 
			this.x1 = Math.floor(this.x1*100)/100;
			this.y1 = Math.floor(this.y1*100)/100;
			this.x2 = Math.floor(this.x2*100)/100;
			this.y2 = Math.floor(this.y2*100)/100;
    		this.bg_width = com.width;
    		this.bg_height = com.height; 
    	}	
    }
    this.show = function (ctx_in) {
    	this.resize();
        if (this.isShow) {
            var ctx = ctx_in || com.ct;
            ctx.save();
            if (this.isSelected == true) {
                if (com.paint == "delete") {
                	ctx.strokeStyle = "rgb(255,0,0)";
                } else {
                	ctx.strokeStyle = "rgb(0,255,0)";
                }
            } else {
            	ctx.strokeStyle = this.color;
            }
            ctx.lineWidth = 1;
            ctx.font = com.font;
            var width = Math.floor(Math.abs(this.x2-this.x1) * com.x_m_per_pix * 10)/10;
            var height = Math.floor(Math.abs(this.y2-this.y1) * com.y_m_per_pix * 10)/10;
            ctx.fillText(width+"米", com.spaceX * ((this.x1+this.x2)/2), com.spaceY * this.y1);
            ctx.fillText(height+"米", com.spaceX * this.x1, com.spaceY * (this.y1+this.y2)/2);
            ctx.fillStyle = "rgba(0, 0, 0, 0.2)";
            ctx.fillRect(this.x1, this.y1, this.x2-this.x1, this.y2-this.y1);
            ctx.strokeRect(this.x1, this.y1, this.x2-this.x1, this.y2-this.y1);
            ctx.restore();
        }
    }
    this.slice = function () {
        var tmp = new com.class.Rect(this.x1, this.y1, this.x2, this.y2, this.color);
        tmp.id = this.id;
        tmp.imgSrc = this.imgSrc;
        tmp.bg_width = this.bg_width;
        tmp.bg_height = this.bg_height;
        return tmp;
    }
    this.isOnObj = function (x, y, targetZ) {
    	if(targetZ>this.zindex) {
    		return false;
    	}
    	var width = this.x2-this.x1;
    	var height = this.y2-this.y1;
    	if(com.between(this.x1,(this.x1 + width),x) && 
    			com.between(this.y1,(this.y1 + height),y)) {
        	return true;
    	}
        return false;
    }
    this.moveWithOffset = function(offsetX, offsetY) {
    	this.x1 += offsetX;
    	this.y1 += offsetY;
    	this.x2 += offsetX;
    	this.y2 += offsetY;
    };
}

com.fillTextCenter= function (ctx, str, x, y){
	var width = ctx.measureText(str).width;
	ctx.fillText(str, x-width/2, y);
}

com.class.Ruler = function (x1, y1, x2, y2, color, imgSrc) {
    this.id = Guid.NewGuid().ToString("N");
    this.type = "Ruler";
    this.x1 = x1 || 0;
    this.y1 = y1 || 0;
    this.x2 = x2 || 0;
    this.y2 = y2 || 0;
    this.color = color || "red";
    this.isShow = true;
    this.bg_width = com.width;
    this.bg_height = com.height;
    this.imgSrc = imgSrc || (com.imgPath+"/ic_ruler_normal.png");
    this.saveData = function () {
        var data = JSON.stringify(this);
        return data;
    }
    this.init = function (dataParse) {
        this.type = dataParse.type;
        this.id = dataParse.id;
        this.x1 = dataParse.x1;
        this.y1 = dataParse.y1;
        this.x2 = dataParse.x2;
        this.y2 = dataParse.y2;
        this.color = dataParse.color;
        this.isShow = dataParse.isShow;
        this.imgSrc = dataParse.imgSrc;
        this.bg_width = dataParse.bg_width;
        this.bg_height = dataParse.bg_height;
    }
    this.resize = function ( ) {
    	if(com.width != this.bg_width) {
    		var scale_x = com.width/this.bg_width;
    		var scale_y = com.height/this.bg_height;	
    		this.x1 = this.x1 * scale_x; 
    		this.y1 = this.y1 * scale_y; 
    		this.x2 = this.x2 * scale_x; 
    		this.y2 = this.y2 * scale_y; 
			this.x1 = Math.floor(this.x1*100)/100;
			this.y1 = Math.floor(this.y1*100)/100;
			this.x2 = Math.floor(this.x2*100)/100;
			this.y2 = Math.floor(this.y2*100)/100;
    		this.bg_width = com.width;
    		this.bg_height = com.height; 
    	}	
    }
    this.show = function (ctx_in) {
    	this.resize();
        if (this.isShow) {
            var ctx = ctx_in || com.ctMask;
            ctx.save();
            ctx.strokeStyle = this.color;
            ctx.lineWidth = 1;
            ctx.font = com.font;
            var width = Math.floor(Math.abs(this.x2-this.x1) * com.x_m_per_pix * 10)/10;
            var height = Math.floor(Math.abs(this.y2-this.y1) * com.y_m_per_pix * 10)/10;
            ctx.fillText(width+"米", com.spaceX * ((this.x1+this.x2)/2), com.spaceY * this.y1);
            ctx.fillText(height+"米", com.spaceX * this.x1, com.spaceY * (this.y1+this.y2)/2);
            
            com.fillTextCenter(ctx,"请框出一个矩形区，并输入长宽(米)", com.spaceX * ((this.x1+this.x2)/2), com.spaceY * (this.y1+this.y2)/2 );
                        
            ctx.fillStyle = "rgba(0, 0, 0, 0.2)";
            ctx.fillRect(this.x1, this.y1, this.x2-this.x1, this.y2-this.y1);
            ctx.strokeRect(this.x1, this.y1, this.x2-this.x1, this.y2-this.y1);

            ctx.restore();
        }
    }
    this.slice = function () {
        var tmp = new com.class.Rect(this.x1, this.y1, this.x2, this.y2, this.color);
        tmp.id = this.id;
        tmp.imgSrc = this.imgSrc;
        tmp.bg_width = this.bg_width;
        tmp.bg_height = this.bg_height;
        return tmp;
    }
}

com.class.GenerateObj = function (dataParse) {
    //var dataParse = JSON.parse(data);
    if (dataParse.type == "Line") {
        var obj = new com.class.Line();
        obj.init(dataParse);
        return obj;
    }

    if (dataParse.type == "Rect") {
        var obj = new com.class.Rect();
        obj.init(dataParse);
        return obj;
    }

    if (dataParse.type == "EraserPointsList") {
        var obj = new com.class.EraserPointsList();
        obj.init(dataParse);
        return obj;
    }

    if (dataParse.type == "PointsList") {
        var obj = new com.class.PointsList();
        obj.init(dataParse);
        return obj;
    }
    if (dataParse.type == "player_1") {
        var obj = new com.class.Obj();
        obj.init(dataParse);
        return obj;
    }
    if (dataParse.type == "player_2") {
        var obj = new com.class.Obj();
        obj.init(dataParse);
        return obj;
    }
    if (dataParse.type == "text") {
        var obj = new com.class.ObjText();
        obj.init(dataParse);
        return obj;
    }
    if (dataParse.type == "tool") {
        var obj = new com.class.Obj();
        obj.init(dataParse);
        return obj;
    }

    if (dataParse.type == "background") {
        var obj = new com.class.Bg();
        obj.init(dataParse);
        return obj;
    }
}

com.show = function (tmpchildList) {
	com.ctMove.clearRect(0, 0, com.width, com.height);
    com.ct.clearRect(0, 0, com.width, com.height);
    com.ctMask.clearRect(0, 0, com.width, com.height);
    if(tmpchildList) {
        for (var i = 0; i < tmpchildList.length; i++) {
            tmpchildList[i].show();
        }
    } else {
        for (var i = 0; i < com.childList.length; i++) {
            com.childList[i].show();
        }
    	$("#ic_red").text(com.gen_player_jerseyid("player_1"));
    	$("#ic_blue").text(com.gen_player_jerseyid("player_2"));
    }
    // add tools
    if(com.tool_imgSrc != null) {
    	
    	$('#ic_pencil').attr('src',com.tool_imgSrc);
    	$('#ic_pencil').attr('style','float:left;border: 2px solid '+com.line_color);
    	//$('#ic_pencil').attr('background-color', '#FFFF00');
    	
        //var tmp = new com.class.Obj("current_tool",com.tool_imgSrc,"当前画笔",30,30,30,30);
        //tmp.show();
        return;
    }
}

//com.ic_formation_toolbar = $('#ic_formation').toolbar({
//    content: '#ic_formation_toolbar',
//    position: 'right',
//    style: "toolbar_background"
//});

//com.ic_tools_toolbar = $('#ic_tools').toolbar({
//    content: '#ic_tools_toolbar',
//    position: 'right',
//    style: "toolbar_background"
//});

//com.ic_pencil_toolbar = $('#ic_pencil').toolbar({
//    content: '#ic_pencil_toolbar',
//    position: 'right',
//    style: "toolbar_background"
//});

//com.ic_playground_toolbar = $('#ic_playground').toolbar({
//    content: '#ic_playground_toolbar',
//    position: 'top',
//    style: "toolbar_background"
//});
$('#ic_text').off('click');
$('#ic_text').on('click',function(event){
    com.resetTools();
    com.paint = "text";
	com.get("ic_text").setAttribute("bPressed","true");
    com.get("ic_text").setAttribute("src",
        com.imgPath+"/ic_text_active.png");
    //var tmpObj = new com.class.ObjText("text", 0, 0, "");
	
});


$('#ic_playground').off('click');
$('#ic_playground').on('click',function(event){
	com.tacticsarea = com.tacticsarea || {}
	
	com.tacticsarea.offsetLeft = $('#soccerboard').offset().left;
	com.tacticsarea.offsetTop = $('#soccerboard').offset().top;
	com.tacticsarea.center_x = $('#soccerboard').offset().left + $('#soccerboard').width()/2;
	com.tacticsarea.center_y = $('#soccerboard').offset().top + $('#soccerboard').height()/2;
	$('#modal_context_selectbg').css({
        position: "absolute",
        "margin":"0px",
        'left': com.tacticsarea.center_x-($('#modal_context_selectbg').width()/2) -$(document).scrollLeft(),
        'top': com.tacticsarea.center_y-($('#modal_context_selectbg').height()/2) - $(document).scrollTop(),
    });

	$('#classModal_SelectBg').modal({backdrop:false,show:true});
	$('#classModal_SelectBg').show();
});

com.get("ic_red").addEventListener(
    "click",
    function (e) {
    	//com.get("ic_red").removeEventListener("click",arguments.callee);
        com.resetTools();
        com.paint = "player";
        var tmpObj = new com.class.Obj("player_1",
            com.imgPath+"/ic_red_normal.png", "队员", 0, 0, com.playersize, com.playersize);
        tmpObj.sys_img="player_1";
        tmpObj.jerseyid = com.gen_player_jerseyid(tmpObj.type);

        com.paintPen = tmpObj;
        com.paintPenMulti = true;
        com.show();
        
    })


com.get("ic_blue").addEventListener(
    "click",
    function (e) {
    	//com.get("ic_blue").removeEventListener("click",arguments.callee);
        com.resetTools();
        com.paint = "player";
        var tmpObj = new com.class.Obj("player_2",
            com.imgPath+"/ic_blue_normal.png", "队员", 0, 0, com.playersize, com.playersize);
        tmpObj.sys_img="player_2";
        tmpObj.jerseyid = com.gen_player_jerseyid(tmpObj.type);
        com.paintPen = tmpObj;
        com.paintPenMulti = true;
        com.show();
    });

com.showformation = function (formation) {
	$('#ic_formation_list').hide();
	$('#ic_formation').attr('src',com.imgPath+"/ic_formation_pressed.png");
	$('#ic_formation').attr('bPressed','false');
	
    var datas = com.formations[com.nowStype][formation];
    com.resetTools();
    com.opUndoLists.push(com.arr2Clone(com.childList));
    com.childList.splice(1, com.childList.length - 1);
    for (var i = 0; i < datas.length; i++) {
        var tmpObj = new com.class.Obj("player_1",
            com.imgPath+"/ic_red_normal.png", "队员", parseInt(datas[i][0] * com.scale_x), parseInt(datas[i][1] * com.scale_y), com.playersize, com.playersize);
        tmpObj.jerseyid = i+1;
        tmpObj.sys_img="player_1";
        com.childList.mypush(tmpObj);
    }
    com.show();
}

com.ic_formation_click = function (obj) {
    com.showformation(obj.getAttribute("name"));
}

com.get("ic_formation1121").addEventListener("click", function (e) {
	//com.get("ic_formation1121").removeEventListener("click",arguments.callee);
    com.showformation("1121");
})

com.get("ic_formation122").addEventListener("click", function (e) {
	//com.get("ic_formation122").removeEventListener("click",arguments.callee);
    com.showformation("122");
})

com.get("ic_formation131").addEventListener("click", function (e) {
	//com.get("ic_formation131").removeEventListener("click",arguments.callee);
    com.showformation("131");
})

com.get("ic_formation1331").addEventListener("click", function (e) {
	//com.get("ic_formation1331").removeEventListener("click",arguments.callee);
    com.showformation("1331");
})

com.get("ic_formation1322").addEventListener("click", function (e) {
	//com.get("ic_formation1322").removeEventListener("click",arguments.callee);
    com.showformation("1322");
})

com.get("ic_formation1433").addEventListener("click", function (e) {
	//com.get("ic_formation1433").removeEventListener("click",arguments.callee);
    com.showformation("1433");
})


com.get("ic_formation1442").addEventListener("click", function (e) {
	//com.get("ic_formation1442").removeEventListener("click",arguments.callee);
    com.showformation("1442");
})

com.get("ic_formation1451").addEventListener("click", function (e) {
	//com.get("ic_formation1451").removeEventListener("click",arguments.callee);
    com.showformation("1451");
})

com.ic_tool_click = function (obj) {
    com.resetTools();
    
	$('#ic_tools_list').hide();
	$('#ic_tools').attr('src',com.imgPath+"/ic_tools_normal.png");
	$('#ic_tools').attr('bPressed','false');
	
    com.paint = "tool";
    var src_img = "";
    var sys_img= "";
    if (obj.getAttribute("name") == "ic_ball") {
        src_img = com.imgPath+"/ic_ball_normal.png";
        sys_img = "tool_1";
    } else if (obj.getAttribute("name") == "ic_bar") {
        src_img = com.imgPath+"/ic_bar_normal.png";
        sys_img = "tool_2";
    } else if (obj.getAttribute("name") == "ic_goal") {
        src_img = com.imgPath+"/ic_goal_normal.png";
        sys_img = "tool_3";
    } else if (obj.getAttribute("name") == "ic_trash_yellow") {
        src_img = com.imgPath+"/ic_trash_yellow_normal.png";
        sys_img = "tool_4";
    } else if (obj.getAttribute("name") == "ic_trash_red") {
        src_img = com.imgPath+"/ic_trash_red_normal.png";
        sys_img = "tool_5";
    } else if (obj.getAttribute("name") == "ic_trash_blue") {
        src_img = com.imgPath+"/ic_trash_blue_normal.png";
        sys_img = "tool_6";
    } else if (obj.getAttribute("name") == "ic_biaogan") {
        src_img = com.imgPath+"/biaogan.png";
        sys_img = "tool_7";
    }  else if (obj.getAttribute("name") == "ic_bluecircl") {
        src_img = com.imgPath+"/bluecircl.png";
        sys_img = "tool_8";
    }  else if (obj.getAttribute("name") == "ic_bluejump") {
        src_img = com.imgPath+"/bluejump.png";
        sys_img = "tool_9";
    }  else if (obj.getAttribute("name") == "ic_ladder") {
        src_img = com.imgPath+"/ladder.png";
        sys_img = "tool_10";
    }  else if (obj.getAttribute("name") == "ic_peoplewall") {
        src_img = com.imgPath+"/peoplewall.png";
        sys_img = "tool_11";
    }  else if (obj.getAttribute("name") == "ic_redbucket") {
        src_img = com.imgPath+"/redbucket.png";
        sys_img = "tool_12";
    }
    else {
    }
    //com.tool_imgSrc = src_img;
    var tmpObj = new com.class.Obj("tool", src_img, obj.getAttribute("name"), 0, 0, com.playersize, com.playersize);
    tmpObj.sys_img = sys_img;
    com.paintPen = tmpObj;
    com.paintPenMulti = true;
    com.show();
}

com.ic_ruler_click = function (obj) {
    com.resetTools();
	com.paint = "ruler_rect";
	com.get("ic_ruler").setAttribute("bPressed","true");
    com.get("ic_ruler").setAttribute("src",
        com.imgPath+"/ic_ruler_pressed.png");
    return;
    
//    if(com.paint != "line" && com.paint != "pencil" && com.paint != "pencil_rect" && com.paint != "eraser" && com.paint != "ruler_rect") {
//        com.resetTools();
//    }
    
    //com.paint = "ruler_rect";
    //com.tool_imgSrc = com.imgPath+"/ic_ruler_normal.png";
    //com.show();
}

com.ic_paint_click = function (obj) {
	$('#ic_pencil_toolbar').hide();
	$('#ic_pencil').attr('bPressed','false');
	
    if(com.paint != "line" && com.paint != "pencil" && com.paint != "pencil_rect" && com.paint != "eraser" && com.paint != "ruler_rect") {
        com.resetTools();
    }

    if (obj.getAttribute("name") == "ic_arrow") {
        com.paint = "line";
        com.line_pattern = 0;
        com.line_end = true;
        com.tool_imgSrc = com.imgPath+"/ic_arrow_pressed.png";
    } else if (obj.getAttribute("name") == "ic_line") {
        com.paint = "line";
        com.line_pattern = 0;
        com.line_end = false;
        com.tool_imgSrc = com.imgPath+"/ic_line_pressed.png";
    } else if (obj.getAttribute("name") == "ic_arrow_dashed") {
        com.paint = "line";
        com.line_pattern = 5;
        com.line_end = true;
        com.tool_imgSrc = com.imgPath+"/ic_arrow_dashed_pressed.png";
    } else if (obj.getAttribute("name") == "ic_dashed_line") {
        com.paint = "line";
        com.line_pattern = 5;
        com.line_end = false;
        com.tool_imgSrc = com.imgPath+"/ic_dashed_line_pressed.png";
    } else if (obj.getAttribute("name") == "ic_dark_pen") {
        com.line_color = "blue";
        //com.tool_imgSrc = com.imgPath+"/ic_dark_pen_pressed.png";
    } else if (obj.getAttribute("name") == "ic_light_pen") {
        com.line_color = "yellow";
        //com.tool_imgSrc = com.imgPath+"/ic_light_pen_normal.png";
    } else if (obj.getAttribute("name") == "freestyle_line") {
        com.paint = "pencil";
        com.freestyle = false;
        com.tool_imgSrc = com.imgPath+"/freestyle_line.png";
    } else if (obj.getAttribute("name") == "freestyle_line_0") {
        com.paint = "pencil";
        com.freestyle = true;
        com.tool_imgSrc = com.imgPath+"/freestyle_line.png";
    } else if (obj.getAttribute("name") == "ic_draw_rect") {
        com.paint = "pencil_rect";
        com.tool_imgSrc = com.imgPath+"/ic_rect_pressed.png";
    } else {
        com.paint = "eraser";
        com.tool_imgSrc = com.imgPath+"/ic_eraser_pressed.png";
    }
    com.show();
    return;
}


com.ic_playground_click = function (obj) {
    com.resetTools();
    var src_img = "";
    var stype = null;
    if (obj.getAttribute("name") == "ic_strategy_background1") {
        src_img = com.imgPath+"/ic_strategy_background1.png";
        com.bg_sysimg = "bg1";
        stype = "stype1";
    } else if (obj.getAttribute("name") == "ic_strategy_background2") {
        src_img = com.imgPath+"/ic_strategy_background2.png";
        com.bg_sysimg = "bg2";
        stype = "stype2";
    } else if (obj.getAttribute("name") == "ic_strategy_background3") {
        src_img = com.imgPath+"/ic_strategy_background3.png";
        com.bg_sysimg = "bg3";
        stype = "stype3";
    } else {
        src_img = com.imgPath+"/ic_strategy_background4.png";
        com.bg_sysimg = "bg4";
        stype = "stype4";
    }
	com.init(stype);
    com.show();
    $('#classModal_SelectBg').modal('hide');
}

com.changeBg  = function (stype) {
	com.init(stype);
    com.show();
}

$("#ic_formation_list_div").mouseleave(function () {
	$("#ic_formation_list").hide();
});

$("#ic_tools_list_div").mouseleave(function () {
	$("#ic_tools_list").hide();
});

$("#ic_pencil_bar_div").mouseleave(function () {
	$("#ic_pencil_toolbar").hide();
});

com.get("ic_tools").addEventListener(
    "click",
    function (e) {
    	//com.get("ic_pencil").removeEventListener("click",arguments.callee);
        com.resetTools();
    	$('#ic_pencil_toolbar').hide();
    	$('#ic_pencil').attr('bPressed','false');
    	$('#ic_formation_list').hide();
    	$('#ic_formation').attr('src',com.imgPath+"/ic_formation_pressed.png");
    	$('#ic_formation').attr('bPressed','false');
		$(this).attr('bPressed', ($(this).attr('bPressed') == 'true') ? 'false' : 'true');
		if ($(this).attr('bPressed') == 'false') {
			$('#ic_tools_list').hide();
			$(this).attr('src',com.imgPath+"/ic_tools_normal.png");
		} else {
			$('#ic_tools_list').show();
			$(this).attr('src',com.imgPath+"/ic_tools_pressed.png");
		}
    });

com.get("ic_pencil").addEventListener(
	    "click",
	    function (e) {
	    	//com.get("ic_pencil").removeEventListener("click",arguments.callee);
	        com.resetTools();
	    	$('#ic_formation_list').hide();
	    	$('#ic_formation').attr('src',com.imgPath+"/ic_formation_pressed.png");
	    	$('#ic_formation').attr('bPressed','false');
	    	$('#ic_tools_list').hide();
	    	$('#ic_tools').attr('src',com.imgPath+"/ic_tools_normal.png");
	    	$('#ic_tools').attr('bPressed','false');
			$(this).attr('bPressed', ($(this).attr('bPressed') == 'true') ? 'false' : 'true');
			if ($(this).attr('bPressed') == 'false') {
				$('#ic_pencil_toolbar').hide();
				$(this).attr('src',com.imgPath+"/ic_pencil_normal.png");
			} else {
				$('#ic_pencil_toolbar').show();
				$(this).attr('src',com.imgPath+"/ic_pencil_normal.png");
			}
	    });

com.get("ic_formation").addEventListener(
	    "click",
	    function (e) {
	    	//com.get("ic_pencil").removeEventListener("click",arguments.callee);
	        com.resetTools();
	    	$('#ic_pencil_toolbar').hide();
	    	$('#ic_pencil').attr('bPressed','false');
	    	$('#ic_tools_list').hide();
	    	$('#ic_tools').attr('src',com.imgPath+"/ic_tools_normal.png");
	    	$('#ic_tools').attr('bPressed','false');
			$(this).attr('bPressed', ($(this).attr('bPressed') == 'true') ? 'false' : 'true');
			if ($(this).attr('bPressed') == 'false') {
				$('#ic_formation_list').hide();
				$(this).attr('src',com.imgPath+"/ic_formation_normal.png");
			} else {
				$('#ic_formation_list').show();
				$(this).attr('src',com.imgPath+"/ic_formation_pressed.png");
			}
	    });

com.get("ic_refresh").addEventListener("click", function (e) {
	//com.get("ic_refresh").removeEventListener("click",arguments.callee);
    com.opUndoLists = new com.class.MyArray();
    com.opRedoLists = new com.class.MyArray();
    //localStorage.soccerboard_data = null;
    com.loadDataFromDB();
})


com.get("ic_undo").addEventListener("click", function (e) {
	//com.get("ic_undo").removeEventListener("click",arguments.callee);
    if (com.opUndoLists.length > 0) {
        com.opRedoLists.push(com.childList);
        com.childList = com.opUndoLists.pop();
        com.saveData(com.childFrameList_Idx);
        com.show()
    }
})


com.get("ic_redo").addEventListener("click", function (e) {
	//com.get("ic_redo").removeEventListener("click",arguments.callee);
    if (com.opRedoLists.length > 0) {
        com.opUndoLists.push(com.childList)
        com.childList = com.opRedoLists.pop();
        com.saveData(com.childFrameList_Idx);
        com.show()
    }
})

com.get("ic_delete").addEventListener("click", function (e) {
	//com.get("ic_delete").removeEventListener("click",arguments.callee);
    com.paintPen = null;
    com.paint = "none";
    if (com.get("ic_delete").getAttribute("src") == com.imgPath+"/ic_delete_normal.png") {
        com.paint = "delete";
        //timelineBar.status = "delete";
        com.get("ic_delete").setAttribute("src",
            com.imgPath+"/ic_delete_active.png");
        return;
    }
    if (com.get("ic_delete").getAttribute("src") == com.imgPath+"/ic_delete_active.png") {
        com.paint = "none";
        //timelineBar.status = "add";
        com.get("ic_delete").setAttribute("src",
            com.imgPath+"/ic_delete_normal.png");
        return;
    }
    com.childList = [com.bg];
    com.opUndoLists = new com.class.MyArray();
    com.opRedoLists = new com.class.MyArray();
    com.show();
});

com.get("ic_load_storage").addEventListener("click", function (e) {
	//com.get("ic_load_storage").removeEventListener("click",arguments.callee);
	com.loadDataFromStorage();
});

com.save_tactics = function() {
	com.ct.drawImage(com.canvasMask,0,0);
	var imgurl = com.canvas.toDataURL();
	com.show();
	var tacticsdata = com.saveDataToDB();
	var res = {"tacticsdata":tacticsdata, "imgSrc":imgurl};
	return res;
}

com.get("ic_clear_tool").addEventListener("click", function (e) {
	//com.get("ic_clear_tool").removeEventListener("click",arguments.callee);
    com.resetTools();
    com.show();
});


$('#frame_delete').off('mouseover');
$('#frame_delete').on('mouseover', function(){
	if(com.previousSelectedObj != null) {
		com.paint = "delete";
		com.show();
		return;
	}
	
	if(timelineBar.todelete != null) {
		$(timelineBar.todelete).removeClass('older-event');
		$(timelineBar.todelete).addClass('todelete');
		return;
	}
});


$('#frame_delete').off('mouseout');
$('#frame_delete').on('mouseout', function(){
	if(com.previousSelectedObj != null) {
		com.paint = "none";
		com.show();
		return;
	}
	
	if(timelineBar.todelete != null) {
		$(timelineBar.todelete).removeClass('todelete');
		return;
	}
});

$('#frame_delete').off('click');
$('#frame_delete').on('click', function(event){
	if(com.previousSelectedObj != null) {
		for(var i = 0; i< com.childList.length; i++) {
			if(com.previousSelectedObj.id == com.childList[i].id ){
				 com.previousSelectedObj.isSelected = false;
                 com.opUndoLists.push(com.arr2Clone(com.childList));
                 com.childList.splice(i, 1);
                 com.paint = "none";
                 com.show();
                 com.previousSelectedObj = null;
                 break;
			}
		}
		return;
	}
	
	if(timelineBar.todelete != null) {
		com.opUndoLists.push(com.arr2Clone(com.childList));
		
		timelineBar.status = "delete";
		$(timelineBar.todelete).removeClass('todelete');
		$(timelineBar.todelete).trigger('click');
		timelineBar.status = "add";
		timelineBar.todelete = null;
	}
	return;
	
	// for another delete action
    if (com.get("frame_delete").getAttribute("src") == com.imgPath+"/ic_clear_normal.png") {
        timelineBar.status = "delete";
        com.get("frame_delete").setAttribute("src",
            com.imgPath+"/ic_clear_pressed.png");
        return;
    }
    if (com.get("frame_delete").getAttribute("src") == com.imgPath+"/ic_clear_pressed.png") {
        timelineBar.status = "add";
        com.get("frame_delete").setAttribute("src",
            com.imgPath+"/ic_clear_normal.png");
        return;
    }
    com.childList = [com.bg];
    com.opUndoLists = new com.class.MyArray();
    com.opRedoLists = new com.class.MyArray();
    com.show();
});

com.saveData = function (frameIdx) {
    var frameObj = new Object();
    frameObj.data = [];
    for (var i = 0; i < com.childList.length; i++) {
        frameObj.data.push(com.childList[i].slice());
    }
    if (frameIdx <= com.childFrameList.framedata.length) {
        com.childFrameList.framedata[frameIdx - 1] = frameObj;
    } else {
        com.childFrameList.framedata.push(frameObj);
    }
    com.childList = frameObj.data;
    localStorage.soccerboard_data = JSON.stringify(com.childFrameList);
    com.setChanged();
    return;
}

com.delData = function (frameIdx) {
    if (frameIdx > 0 && frameIdx <= com.childFrameList.framedata.length) {
        com.childFrameList.framedata.splice(frameIdx - 1, 1);
    }
    if(com.childFrameList.framedata.length==0) {
        com.childList = [com.bg];
        //com.opUndoLists = new com.class.MyArray();
        //com.opRedoLists = new com.class.MyArray();
        com.show();
    }
}

com.reLoad = function (frameIdx, lastFrameIdx, preview) {
    if(preview != null && preview == true) {
        if (frameIdx > 0 && frameIdx <= com.childFrameList.framedata.length) {
            com.show(com.childFrameList.framedata[frameIdx - 1].data);
        }
        return;
    }

    com.opUndoLists = new com.class.MyArray();
    com.opRedoLists = new com.class.MyArray();
    com.childFrameList_Idx = frameIdx;
    if (lastFrameIdx==null && frameIdx > 0 && frameIdx <= com.childFrameList.framedata.length) {
        com.childList = com.childFrameList.framedata[frameIdx - 1].data;
        com.show();
        return;
    }
    if (lastFrameIdx>0 && frameIdx > 0 && frameIdx <= com.childFrameList.framedata.length) {

        var childListEnd = com.childFrameList.framedata[frameIdx - 1].data;
        com.childList = childListEnd;

        var childListBegin = com.childFrameList.framedata[lastFrameIdx - 1].data;
        com.frames_per_action = 30;
        var inter_val = Math.floor(timelineBar.autoPlayPause/com.frames_per_action);
        inter_val = Math.max(30,inter_val);
        if(com.frames_per_action>0){
            com.frameLists = com.genFrames(childListBegin, childListEnd, com.frames_per_action);
            com.iframe = 0;
            com.annimation_timer && clearTimeout(com.annimation_timer);
            com.annimation_timer = setInterval(function () {
                if(com.iframe>=com.frames_per_action) {
                    com.annimation_timer && clearTimeout(com.annimation_timer);
                    return;
                }
                com.show(com.frameLists[com.iframe]);
                com.iframe ++ ;
            }, inter_val);   	
        }
        return;
    }
}


com.genFrames = function(childListBegin, childListEnd, frames_per_action) {
    var frameLists = [];
    for (var j = 0; j < frames_per_action; j++) {
        frameLists.push([]);
    }

    frameLists[0] = childListBegin;
    for(var i = 0; i < childListEnd.length; i++) {
        var objList = com.genObjList(childListEnd[i],childListBegin, frames_per_action);
        for (var j = 1; j < frames_per_action; j++) {
            if( j < objList.length) {
                frameLists[j].push(objList[j]);
            }
        }
    }
    return frameLists;
};

com.genObjList = function(objEnd, childListBegin, frames_per_action) {
    var objList = [];
    var index = -1;
    // 1. if player, match with jersery id and team type
    if((objEnd.type=="player_1" || objEnd.type == "player_2")) {
        for(var i = 0; i < childListBegin.length; i++) {
            if(childListBegin[i].type == objEnd.type && objEnd.jerseyid!="" && objEnd.jerseyid != null &&childListBegin[i].jerseyid == objEnd.jerseyid) {
                index = i;
                break;
            }
        }	
        // 2. if no player matched, match with id
        if(index==-1) {
            for(var i = 0; i < childListBegin.length; i++) {
                if(childListBegin[i].id == objEnd.id) {
                    index = i;
                    break;
                }
            }
        }
    } else {
        for(var i = 0; i < childListBegin.length; i++) {
            if(childListBegin[i].id == objEnd.id) {
                index = i;
                break;
            }
        }	
    }
    
    // do not move this
    objList.push(["not user"]);
    if(index >= 0){
        for (var j = 1; j < frames_per_action; j++) {
            var tmpObj = com.genObj(childListBegin[index], objEnd,frames_per_action, j);
            objList.push(tmpObj);
        }
    } else {
        if (objEnd.type == "Line" || objEnd.type == "PointsList") {
            for (var j = 1; j < frames_per_action; j++) {
                var tmpObj = com.genObj(null, objEnd,frames_per_action, j);
                objList.push(tmpObj);
            }
        } else {
            for (var j = 1; j < frames_per_action; j++) {
                var tmpObj = com.genObj(null, objEnd,frames_per_action,frames_per_action-1);
                objList.push(tmpObj);
            }
        }
    }

    return objList;
};

com.genObj = function(objBegin, objEnd, frames_per_action, index) {

    if(index==0) {
        return objBegin;
    }

    if(index == (frames_per_action-1)){
        return objEnd;
    }

    var len_tol = frames_per_action-1;
    switch (objEnd.type) {
        case "player_1":
        case "player_2":
            var tmpObj = objEnd.slice();
            tmpObj.x = objBegin.x + (objEnd.x - objBegin.x)*index/len_tol;
            tmpObj.y = objBegin.y + (objEnd.y - objBegin.y)*index/len_tol;
            return tmpObj;
            break;
        case "Line":
            if(objBegin != null) {
                return objEnd;
            }
            var tmpObj = objEnd.slice();
            tmpObj.x2 = objEnd.x1 + (objEnd.x2 - objEnd.x1)*index/len_tol;
            tmpObj.y2 = objEnd.y1 + (objEnd.y2 - objEnd.y1)*index/len_tol;
            return tmpObj;
            break;
        case "PointsList":
            if(objBegin != null) {
                return objEnd;
            }
            var tmpObj = objEnd.slice();
            var start = (tmpObj.pointsList.length)*index/len_tol;
            var to_move_len = tmpObj.pointsList.length - start +1;
            tmpObj.pointsList.splice(start, to_move_len);
            return tmpObj;
            break;
        case "tool":
        	if(objEnd.type == "tool" && objEnd.sys_img == "tool_1") {
                var tmpObj = objEnd.slice();
                tmpObj.x = objBegin.x + (objEnd.x - objBegin.x)*index/len_tol;
                tmpObj.y = objBegin.y + (objEnd.y - objBegin.y)*index/len_tol;
                return tmpObj;
        	} else {
        		return objEnd;
        	}	
        default :
            return objEnd;
            break;
    }
}

com.saveDataToDB = function () {
    localStorage.soccerboard_data = JSON.stringify(com.childFrameList);
    return com.childFrameList;
}

com.loadDataFromStorage = function () {
	com.loadDataFromDB(localStorage.soccerboard_data);
}

com.loadDataFromDB = function (inputdata) {
    timelineBar.DeleteAllRows($);
    com.childFrameList = new Object();
    com.childFrameList.Bg = new Object();
    com.childFrameList.framedata = [];

    var initdata = inputdata;
    if (initdata && initdata != "null") {
        var frameListParse = JSON.parse(initdata);
        com.childFrameList.name = frameListParse.name;
        com.childFrameList.date = frameListParse.date;
        com.childFrameList.Bg = frameListParse.Bg;
        for (var i = 0; i < frameListParse.framedata.length; i++) {
            timelineBar.AddRow($);
            var frameParse = frameListParse.framedata[i];
            var frameObj = new Object();
            frameObj.data = [];
            for (var j = 0; j < frameParse.data.length; j++) {
                frameObj.data.push(new com.class.GenerateObj(frameParse.data[j]));
            }
            com.childFrameList.framedata.push(frameObj);
        }
        if(com.childFrameList.framedata.length > 0) {
            com.childList = com.childFrameList.framedata[0].data;
            try {
                com.bg_src = com.childList[0].imgSrc;
                com.bg.sys_img = com.childList[0].sys_img;
            }  catch( e ) {
            	
            }           
        } else {
            com.bg = new com.class.Bg(com.bg_src, 0, 0, com.width, com.height, com.x_m_per_pix, com.y_m_per_pix);
            com.bg.sys_img = com.bg_sysimg;
            var frameObj = new Object();
            frameObj.data = [com.bg];
            com.childFrameList.framedata.push(frameObj)
            com.childList = frameObj.data;
            com.show();
        }
        timelineBar.AddRow($, true);
        timelineBar.show($, com.saveData, com.reLoad, com.delData);
        timelineBar.initClickData(1);
    } else {
        com.bg = new com.class.Bg(com.bg_src, 0, 0, com.width, com.height, com.x_m_per_pix, com.y_m_per_pix);
        com.bg.sys_img = com.bg_sysimg;
        var frameObj = new Object();
        frameObj.data = [com.bg];
        com.childFrameList.name = (new Date()).toLocaleString();
        com.childFrameList.date = (new Date()).getTime();
        com.childFrameList.framedata.push(frameObj)
        com.childList = frameObj.data;
        com.show();
        timelineBar.AddRow($, true);
        timelineBar.show($, com.saveData, com.reLoad, com.delData);
    }
}

// debug alert
com.alert = function (obj, f, n) {
    if (typeof obj !== "object") {
        try {
            console.log(obj)
        } catch (e) {
        }
        // return alert(obj);
    }
    var arr = [];
    for (var i in obj)
        arr.push(i + " = " + obj[i]);
    try {
        console.log(arr.join(n || "\n"))
    } catch (e) {
    }
    // return alert(arr.join(n||"\n\r"));
}

com.class.MyArray = function(maxsize) {
	this.maxsize = maxsize || 5;
	this.array = new Array();
	this.length = 0;
	this.push = function(obj){
		if(this.array.length >= this.maxsize){
			this.array.splice(0,1);
		}
		this.array.push(obj);
		this.length = this.array.length;
	}
	this.pop = function( ){
		var tmp = this.array.pop();
		this.length = this.array.length;
		return tmp;
	}
}

com.init = function (stype, width) {
	com.initWidth = width || com.initWidth;

	$('#input_text_message').hide();
	com.get("bnBox").style.display = "block";
	com.timer = null;
	com.paintPen = null;
	com.paintPenMulti = false;
	com.opUndoLists = com.opUndoLists || new com.class.MyArray();
	com.opRedoLists = com.opRedoLists || new com.class.MyArray();

	com.previousSelectedObj = null;// 记录前一个选中的圆
	com.previousSelectedObj_XOffset = 0;
	com.previousSelectedObj_YOffset = 0;

	com.isPainting = false; // 是否正在绘画
	com.isRotating = false; // 是否正在旋转
	com.ptList = null // 是否正在绘画
	com.to_save = false;
	com.line_pattern = 0;
	com.line_color = "blue";
	com.line_start = false;
	com.line_end = false;
	com.tool_imgSrc = null;
	com.p_x = 0;// 上次鼠标位置
	com.p_y = 0;
	com.p_x_now = 0;// 当前瞬间鼠标位置
	com.p_y_now = 0;
	com.pointsList = com.pointsList || []

	com.isDragging = false;
	
	com.std_width = 608 * 1.5;
	com.std_height = 383 * 1.5;
	com.scale_x = 1;
	com.scale_y = 1;
	com.stype = {
	    stype1: {
	        width: 608 * 1.5,
	        height: 383 * 1.5,
	        x_m_per_pix:0.1344,
	        y_m_per_pix:0.1344,
	        spaceX: 1,
	        spaceY: 1,
	        bg_src: com.imgPath+"/strategy_background1.png",
	        bg_sysimg: "bg1",
	        pointStartX: 0,
	        pointStartY: 0,
	        page: "stype_1"
	    },
	    stype2: {
	        width: 608 * 1.5,
	        height: 383 * 1.5,
	        x_m_per_pix:0.0897,
	        y_m_per_pix:0.1004,
	        spaceX: 1,
	        spaceY: 1,
	        bg_src: com.imgPath+"/strategy_background2.png",
	        bg_sysimg: "bg2",
	        pointStartX: 0,
	        pointStartY: 0,
	        page: "stype_2"
	    },
	    stype3: {
	        width: 608 * 1.5,
	        height: 383 * 1.5,
	        x_m_per_pix:0.0897,
	        y_m_per_pix:0.1010,
	        spaceX: 1,
	        spaceY: 1,
	        bg_src: com.imgPath+"/strategy_background3.png",
	        bg_sysimg: "bg3",
	        pointStartX: 0,
	        pointStartY: 0,
	        page: "stype_3"
	    },
	    stype4: {
	        width: 608 * 1.5,
	        height: 383 * 1.5,
	        x_m_per_pix:0.1344,
	        y_m_per_pix:0.1344,
	        spaceX: 1,
	        spaceY: 1,
	        bg_src: com.imgPath+"/strategy_background4.png",
	        bg_sysimg: "bg4",
	        pointStartX: 0,
	        pointStartY: 0,
	        page: "stype_4"
	    },
	}
	com.childList = com.childList || [];
    com.nowStype = stype || com.getCookie("stype") || "stype1";
    com.paint == "none"
    var stype = com.stype[com.nowStype];
    stype.height = Math.floor(stype.height * com.initWidth / stype.width);
    stype.width = com.initWidth;
    
    com.width = stype.width;
    com.height = stype.height;
    com.spaceX = stype.spaceX;
    com.spaceY = stype.spaceY;
    com.pointStartX = stype.pointStartX;
    com.pointStartY = stype.pointStartY;
    com.page = stype.page;
    com.bg_src = stype.bg_src;
    com.bg_sysimg = stype.bg_sysimg;

    com.scale_x = 1.0 * stype.width / com.std_width;
    com.scale_y = 1.0 * stype.height / com.std_height;
    com.x_m_per_pix = stype.x_m_per_pix;
    com.y_m_per_pix = stype.y_m_per_pix;

    com.get("box").style.width = com.width + "px";

	//com.loadImages(com.page);
	
    com.bg = new com.class.Bg(com.bg_src, 0, 0, com.width, com.height, com.x_m_per_pix, com.y_m_per_pix);
    com.bg.sys_img = com.bg_sysimg;
    
    if(com.childList.length > 0) {
        com.childList[0] = com.bg;
    } else {
        com.childList.mypush(com.bg);
    }

	com.childFrameList_Idx = 0;
	if(com.childFrameList == null) {
		com.childFrameList = new Object();
		com.childFrameList.name = (new Date()).toLocaleString();
		com.childFrameList.date = (new Date()).getTime();
		com.childFrameList.Bg = new Object();
		com.childFrameList.framedata = [];
		com.saveData(0);
	} else {

	}
	
	for(var i = 0; i<com.childFrameList.framedata.length; i++ ) {
		var objList = com.childFrameList.framedata[i].data;
		if(objList && objList.length>0){
			objList[0] = com.bg;
		}
	}
	
	com.childFrameList.Bg.bg_src = com.bg_src;
	com.childFrameList.Bg.display_width = com.width;
	com.childFrameList.Bg.display_height = com.height;
	com.childFrameList.Bg.x_m_per_pix = com.x_m_per_pix;
	com.childFrameList.Bg.y_m_per_pix = com.y_m_per_pix;

	com.canvas = com.get("soccerboard");
	com.ct = com.canvas.getContext("2d");
	com.canvas.width = com.width;
	com.canvas.height = com.height;

	com.tacticsarea = com.tacticsarea || {}
	com.tacticsarea.offsetLeft = $('#soccerboard').offset().left;
	com.tacticsarea.offsetTop = $('#soccerboard').offset().top;
	com.tacticsarea.center_x = $('#soccerboard').offset().left + $('#soccerboard').width()/2;
	com.tacticsarea.center_y = $('#soccerboard').offset().top + $('#soccerboard').height()/2;
	
	$('#soccerboard').off("contextmenu",blockContextMenu);
	$('#soccerboard_mask').off("contextmenu",blockContextMenu);
	$('#soccerboard_move').off("contextmenu",blockContextMenu);
	
	$('#soccerboard').on("contextmenu",blockContextMenu);
	$('#soccerboard_mask').on("contextmenu",blockContextMenu);
	$('#soccerboard_move').on("contextmenu",blockContextMenu);
	
	$('#soccerboard').off('mousedown', canvasMouseDown);
	$('#soccerboard').off('mouseup', canvasMouseUp);
	$('#soccerboard').off('mouseout', outCanvas);
	$('#soccerboard').off('mousemove', canvasMouseMove);
	$('#soccerboard').off('dblclick', doubleClick);
	//$('#soccerboard').off('click', canvasMouseDown);
	
	$('#soccerboard_mask').off('mousedown', canvasMouseDown);
	$('#soccerboard_mask').off('mouseup', canvasMouseUp);
	$('#soccerboard_mask').off('mouseout', outCanvas);
	$('#soccerboard_mask').off('mousemove', canvasMouseMove);
	$('#soccerboard_mask').off('dblclick', doubleClick);
	//$('#soccerboard_mask').off('click', canvasMouseDown);
	
	$('#soccerboard_move').off('mousedown', canvasMouseDown);
	$('#soccerboard_move').off('mouseup', canvasMouseUp);
	$('#soccerboard_move').off('mouseout', outCanvas);
	$('#soccerboard_move').off('mousemove', canvasMouseMove);
	$('#soccerboard_move').off('dblclick', doubleClick);
	//$('#soccerboard_move').off('click', canvasMouseDown);
	
	$('#soccerboard').on('mousedown', canvasMouseDown);
	$('#soccerboard').on('mouseup', canvasMouseUp);
	$('#soccerboard').on('mouseout', outCanvas);
	$('#soccerboard').on('mousemove', canvasMouseMove);
	$('#soccerboard').on('dblclick', doubleClick);
	
	$('#soccerboard_mask').on('mousedown', canvasMouseDown);
	$('#soccerboard_mask').on('mouseup', canvasMouseUp);
	$('#soccerboard_mask').on('mouseout', outCanvas);
	$('#soccerboard_mask').on('mousemove', canvasMouseMove);
	$('#soccerboard_mask').on('dblclick', doubleClick);

	
	$('#soccerboard_move').on('mousedown', canvasMouseDown);
	$('#soccerboard_move').on('mouseup', canvasMouseUp);
	$('#soccerboard_move').on('mouseout', outCanvas);
	$('#soccerboard_move').on('mousemove', canvasMouseMove);
	$('#soccerboard_move').on('dblclick', doubleClick);

	
	var even_canvas = $('#soccerboard').Touchable();
	even_canvas.bind('touchstart', canvasMouseDown);
	even_canvas.bind('touchmove', canvasMouseMove);
	even_canvas.bind('touchend', canvasMouseUp);
	even_canvas.unbind('longTap',doubleClick);
	even_canvas.bind('longTap',doubleClick);
	
	var even_canvasMask = $('#soccerboard_mask').Touchable();
	even_canvasMask.bind('touchstart', canvasMouseDown);
	even_canvasMask.bind('touchmove', canvasMouseMove);
	even_canvasMask.bind('touchend', canvasMouseUp);
	even_canvasMask.unbind('longTap',doubleClick);
	even_canvasMask.bind('longTap',doubleClick);
	
	var even_canvasMove = $('#soccerboard_move').Touchable();
	even_canvasMove.bind('touchstart', canvasMouseDown);
	even_canvasMove.bind('touchmove', canvasMouseMove);
	even_canvasMove.bind('touchend', canvasMouseUp);
	even_canvasMove.unbind('longTap',doubleClick);
	even_canvasMove.bind('longTap',doubleClick);
	
	
	
	
//	com.canvas.onmousedown = canvasMouseDown;
//	com.canvas.onmouseup = canvasMouseUp;// 鼠标松开，停止拖动
//	com.canvas.onmouseout = outCanvas;
//	com.canvas.onmousemove = canvasMouseMove;
//	com.canvas.removeEventListener('dblclick',doubleClick);
//	com.canvas.addEventListener('dblclick', doubleClick, false);
	
	//com.canvas.ontouchstart = canvasMouseDown;
	//com.canvas.ontouchend = canvasMouseUp;// 鼠标松开，停止拖动
	//com.canvas.ontouchend = outCanvas;
	//com.canvas.ontouchmove = canvasMouseMove;
	//com.canvas.addEventListener('dblclick', doubleClick, false);
	
	com.canvas.setAttribute("style", "backgroundImage:"+"'"+com.imgPath+"/strategy_background1.png'");
	com.canvasMask = com.get("soccerboard_mask");
	com.ctMask = com.canvasMask.getContext("2d");
	com.canvasMask.width = com.width;
	com.canvasMask.height = com.height;
//	com.canvasMask.onmousedown = canvasMouseDown;
//	com.canvasMask.onmouseup = canvasMouseUp;// 鼠标松开，停止拖动
//	com.canvasMask.onmouseout = outCanvas;
//	com.canvasMask.onmousemove = canvasMouseMove;
//	com.canvasMask.removeEventListener('dblclick',doubleClick);
//	com.canvasMask.addEventListener('dblclick', doubleClick, false);

	com.canvasMove = com.get("soccerboard_move");
	com.ctMove = com.canvasMove.getContext("2d");
	com.canvasMove.width = com.width;
	com.canvasMove.height = com.height;
//	com.canvasMove.onmousedown = canvasMouseDown;
//	com.canvasMove.onmouseup = canvasMouseUp;// 鼠标松开，停止拖动
//	com.canvasMove.onmouseout = outCanvas;
//	com.canvasMove.onmousemove = canvasMouseMove;
//	com.canvasMove.removeEventListener('dblclick',doubleClick);
//	com.canvasMove.addEventListener('dblclick', doubleClick, false);
	//com.show();
	com.loadImagesCallBack(com.bg_src, com.show);
	com.dragLine = com.dragLine || (new com.class.Line(0, 0, 0, 0, false, true, 3, "red"));
}

com.resize = function(width) {
	com.initWidth = width || com.initWidth; 
	com.tacticsarea = com.tacticsarea || {}
	com.tacticsarea.offsetLeft = $('#soccerboard').offset().left;
	com.tacticsarea.offsetTop = $('#soccerboard').offset().top;
	com.tacticsarea.center_x = $('#soccerboard').offset().left + $('#soccerboard').width()/2;
	com.tacticsarea.center_y = $('#soccerboard').offset().top + $('#soccerboard').height()/2;
	var ratio = com.initWidth / com.width;
	var widht_old = com.width;
	var height_old = com.height;
	com.height = Math.floor(com.height * ratio*100)/100;
	com.width = com.initWidth;
	com.canvas.width = com.width;
	com.canvas.height = com.height;
	com.canvasMask.width = com.width;
	com.canvasMask.height = com.height;
	com.canvasMove.width = com.width;
	com.canvasMove.height = com.height;
	$('#box').width(com.width);
	
	var scale_x = com.width/widht_old;
	var scale_y = com.height/height_old;
	com.x_m_per_pix = com.x_m_per_pix/scale_x;
	com.y_m_per_pix = com.y_m_per_pix/scale_y;
    com.scale_x = 1.0 * com.width / com.std_width;
    com.scale_y = 1.0 * com.height / com.std_height;
	com.childFrameList.Bg.display_width = com.width;
	com.childFrameList.Bg.display_height = com.height;
	
	for(var i = 0; i<com.childFrameList.framedata.length; i++ ) {
		var objList = com.childFrameList.framedata[i].data;
		for(var j = 0; j< objList.length; j++) {
			var obj = objList[j];
			obj.resize();
		}
	}
	com.show();
	
	// com.x_m_per_pix will be updated after com.show() 
	com.childFrameList.Bg.x_m_per_pix = com.x_m_per_pix;
	com.childFrameList.Bg.y_m_per_pix = com.y_m_per_pix;
}
// 表示全局唯一标识符 (GUID)。

function Guid(g) {
    var arr = new Array(); // 存放32位数值的数组
    if (typeof (g) == "string") { // 如果构造函数的参数为字符串
        InitByString(arr, g);
    }
    else {
        InitByOther(arr);
    }
    // 返回一个值，该值指示 Guid 的两个实例是否表示同一个值。
    this.Equals = function (o) {
        if (o && o.IsGuid) {
            return this.ToString() == o.ToString();
        }
        else {
            return false;
        }
    }

    // Guid对象的标记
    this.IsGuid = function () {
    }

    // 返回 Guid 类的此实例值的 String 表示形式。
    this.ToString = function (format) {
        if (typeof (format) == "string") {
            if (format == "N" || format == "D" || format == "B"
                || format == "P") {
                return ToStringWithFormat(arr, format);
            }
            else {
                return ToStringWithFormat(arr, "D");
            }
        }
        else {
            return ToStringWithFormat(arr, "D");
        }
    }

    // 由字符串加载
    function InitByString(arr, g) {
        g = g.replace(/\{|\(|\)|\}|-/g, "");
        g = g.toLowerCase();
        if (g.length != 32 || g.search(/[^0-9,a-f]/i) != -1) {
            InitByOther(arr);
        }
        else {
            for (var i = 0; i < g.length; i++) {
                arr.push(g[i]);
            }
        }
    }

    // 由其他类型加载
    function InitByOther(arr) {
        var i = 32;
        while (i--) {
            arr.push("0");
        }
    }

    /*
     *
     * 根据所提供的格式说明符，返回此 Guid 实例值的 String 表示形式。
     *
     * N 32 位： xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
     *
     * D 由连字符分隔的 32 位数字 xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx
     *
     * B 括在大括号中、由连字符分隔的 32 位数字：{xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx}
     *
     * P 括在圆括号中、由连字符分隔的 32 位数字：(xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx)
     *
     */
    function ToStringWithFormat(arr, format) {
        switch (format) {
            case "N":
                return arr.toString().replace(/,/g, "");
            case "D":
                var str = arr.slice(0, 8) + "-" + arr.slice(8, 12) + "-"
                    + arr.slice(12, 16) + "-" + arr.slice(16, 20) + "-"
                    + arr.slice(20, 32);

                str = str.replace(/,/g, "");
                return str;
            case "B":
                var str = ToStringWithFormat(arr, "D");
                str = "{" + str + "}";
                return str;
            case "P":
                var str = ToStringWithFormat(arr, "D");
                str = "(" + str + ")";
                return str;
            default:
                return new Guid();
        }
    }
}

// Guid 类的默认实例，其值保证均为零。
Guid.Empty = new Guid();
// 初始化 Guid 类的一个新实例。
Guid.NewGuid = function () {
    var g = "";
    var i = 32;
    while (i--) {
        g += Math.floor(Math.random() * 16.0).toString(16);
    }
    return new Guid(g);
}


com.formations = {
    "stype1":{
        "1121": [[114, 273], [211, 273], [390, 384], [390, 162], [504, 273]],
        "122": [[114, 273], [211, 373], [211, 173], [367, 373], [367, 173]],
        "131": [[114, 273], [211, 127], [211, 273], [211, 419], [367, 273]],
        "1331": [[114, 273], [211, 127], [211, 273], [211, 419], [367, 127], [367, 273], [367, 419], [504, 273]],
        "1322": [[114, 273], [211, 127], [211, 273], [211, 429], [367, 193], [367, 359], [504, 193], [504, 359]],
        "1433": [[114, 273], [210, 110], [210, 209], [210, 324], [209, 435], [355, 161], [356, 269], [358, 372], [514, 160], [516, 269], [518, 374]],
        "1442": [[114, 273], [210, 110], [210, 209], [210, 324], [209, 435], [354, 111], [354, 206], [355, 324], [356, 436], [523, 184], [526, 369]],
        "1451": [[114, 273], [210, 110], [210, 209], [210, 324], [209, 435], [348, 75], [351, 170], [350, 266], [355, 356], [356, 459], [516, 270]]
    },
    "stype2":{
        "1121": [[493,67],[496,205],[660,343],[324,342],[492,452]],
        "122": [[488,57],[592,208],[376,209],[381,401],[597,399]],
        "131": [[485,78],[256,235],[489,250],[720,241],[492,411]],
        "1331": [[485,56],[266,189],[488,187],[720,191],[266,365],[492,357],[718,355],[494,497]],
        "1322": [[485,56],[266,189],[488,187],[720,191],[388,353],[589,355],[581,484],[399,485]],
        "1433": [[488,58],[260,178],[404,176],[584,173],[722,174],[327,312],[492,313],[659,312],[324,472],[497,468],[663,468]],
        "1442": [[488,58],[260,178],[404,176],[584,173],[722,174],[259,329],[406,323],[583,328],[728,331],[406,478],[579,475]],
        "1451": [[488,58],[260,178],[404,176],[584,173],[722,174],[198,337],[337,336],[658,334],[795,330],[491,336],[493,469]]
    },
    "stype3":{
        "1121": [[494,472],[494,346],[333,205],[660,203],[491,79]],
        "122": [[492,477],[614,351],[372,350],[372,176],[607,171]],
        "131": [[493,474],[252,274],[491,272],[739,280],[488,130]],
        "1331": [[489,475],[270,358],[493,351],[717,353],[267,183],[492,177],[716,179],[492,82]],
        "1322": [[492,475],[270,353],[492,348],[707,349],[381,225],[600,229],[379,94],[599,96]],
        "1433": [[494,491],[413,360],[572,358],[247,357],[722,353],[329,251],[498,250],[650,247],[327,123],[501,126],[650,124]],
        "1442": [[494,481],[411,355],[575,353],[252,355],[738,353],[255,229],[413,230],[579,232],[743,224],[411,100],[577,100]],
        "1451": [[494,481],[411,355],[575,353],[252,355],[738,353],[185,222],[334,219],[496,219],[667,226],[815,225],[494,96]]
    },
    "stype4":{
        "1121": [[114, 273], [211, 273], [390, 384], [390, 162], [504, 273]],
        "122": [[114, 273], [211, 373], [211, 173], [367, 373], [367, 173]],
        "131": [[114, 273], [211, 127], [211, 273], [211, 419], [367, 273]],
        "1331": [[114, 273], [211, 127], [211, 273], [211, 419], [367, 127], [367, 273], [367, 419], [504, 273]],
        "1322": [[114, 273], [211, 127], [211, 273], [211, 429], [367, 193], [367, 359], [504, 193], [504, 359]],
        "1433": [[114, 273], [210, 110], [210, 209], [210, 324], [209, 435], [355, 161], [356, 269], [358, 372], [514, 160], [516, 269], [518, 374]],
        "1442": [[114, 273], [210, 110], [210, 209], [210, 324], [209, 435], [354, 111], [354, 206], [355, 324], [356, 436], [523, 184], [526, 369]],
        "1451": [[114, 273], [210, 110], [210, 209], [210, 324], [209, 435], [348, 75], [351, 170], [350, 266], [355, 356], [356, 459], [516, 270]]
    },
}









