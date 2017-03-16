<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<script type="text/javascript">
<!--
window.alert = function (msg, title, callback) {
    if (!title) {
        title = '提示';
    }
 var dialogHTML = '<div id="selfAlert" class="modal fade" style="z-index:2147483584;">'
  					+'<div class="modal-dialog" style="width: 400px;">'
 						+'<div class="modal-content">'
 							+'<div class="modal-header">'
 								+'<button type="button" class="close" data-dismiss="modal" aria-label="Close">'
 									+'<span aria-hidden="true">&times;</span>'
 								+'</button>'
 								+'<span class="modal-title" style="color: #333;font-size: 16px;">' + title + '</span>'
 							+'</div>'
	  						+'<div class="modal-body" style="color: #333;font-size: 14px;text-align: center;background-color:#ecf0f5;border:0px;">'
	  							+ msg
	  						+'</div>'
		   					+'<div class="modal-footer" style="background-color:#ecf0f5;border:0px;border-radius: 3px;">'
		   						+'<button type="button" class="btn btn-primary" data-dismiss="modal">确定</button>'
		   					+'</div>'
 						+'</div>'
 					+'</div>'
 				+'</div>';
    if ($('#selfAlert').length <= 0) {
        $('body').append(dialogHTML);
    }

    $('#selfAlert').on('hidden.bs.modal', function () {
        $('#selfAlert').remove();
        if (typeof callback == 'function') {
            callback();
        }
    }).modal('show');
}
	function onMenuClicked(element, url){
	removeBoFromPage();
		var callback = function(response){
			$(".sidebar-menu li.active").removeClass('active');
			var categoryEle = element.closest(".treeview");
			if (categoryEle.is("li")) {
				rebuildBreadcumb(categoryEle.find(">a span").text(), element.find("span").text());
				categoryEle.addClass("active");
				element.closest("li").addClass("active");
			} else {
				rebuildBreadcumb(categoryEle.find(">a span").text());
				element.closest("li").addClass("active");
			}
		}
		
		// for tactics need to 
		var tmp = $("#tactics_page_changed");
		if(tmp && tmp != null && $(tmp).val()=="true") {
			bootbox.dialog({
					  message: "战术板所做更改尚未保存，请选择",
					  title: "退出战术板",
					  buttons: {
					    unchange: {
						      label: "留在此页",
						      className: "btn-default",
						      callback: function() {
								return;
						      }
						    },
					    danger: {
					      label: "不保存",
					      className: "btn-danger",
					      callback: function() {
					    	  $("#content").loadAngular(url, callback);
					      }
					    },
					    main: {
					      label: "保存并继续",
					      className: "btn-primary",
					      callback: function() {
					    	  saveTacticsToDatabase("$('#content').loadAngular('"+url+"', "+callback+")", "alert('保存战术板信息失败');", element);
					      }
					    }
					  }
					});
		} else {
			$("#content").loadAngular(url, callback);
		}
	}
	
	
	function loadPage(url){
		$("#content").loadAngular(url);
	}
	
	function buildBreadcumb(value){
		if (value){
			$("#third>span").text(value);
			$("#third").show();
			$("#third").addClass("curBreakColor");
			$("#second").removeClass("curBreakColor");
		} else {
			$("#third").hide();
			$("#third").removeClass("curBreakColor");
		}
	}
	
	function gotoMenu(menuClass) {
		$('ul.treeview-menu').hide();
		$('ul.sidebar-menu > li:first').removeClass('active'); // hide dashboard menu
		
		var $targetMenu = $('ul.sidebar-menu').find('li.' + menuClass);
		if($targetMenu.length === 0)  return;
		
		var isFirstLevelMenu = $('ul.sidebar-menu > li.' + menuClass).length > 0;
		if(isFirstLevelMenu) {
			var hasSubMenu = $targetMenu.is('.treeview');
			
			if(!hasSubMenu) { // show menu itself
				$targetMenu.toggleClass('active');
			} else { // show first sub menu
				$targetMenu.find('ul.treeview-menu').show();
				$targetMenu.find('li:eq(0)').toggleClass('active');
			}
		} else { // sub menu
			var $parentMenu = $targetMenu.parents('li.treeview');
			$parentMenu.find('li').removeClass('active');
			
			$parentMenu.find('ul.treeview-menu').show();
			$targetMenu.toggleClass('active');
		}
	}
	
	function rebuildBreadcumb(first, second, third) {
		$("#first>a").text(first);
		$("#first").show();
		$("#first").find('a').css("color", "#067dc2");
		$("#second").hide();
		$("#second").removeClass("curBreakColor");
		$("#third").hide();
		$("#third").removeClass("curBreakColor");
		
		if (typeof(second) != "undefined") {
			$("#second>span").text(second);
			$("#second").show();
			$("#second").addClass("curBreakColor");
			$("#first").find('a').css("color", "#444");
		}
		if (typeof(third) != "undefined") {
			$("#third>span").text(third);
			$("#third").show();
			$("#third").addClass("curBreakColor");
			$("#second").removeClass("curBreakColor");
		}
	}
//-->
</script>
<section class="sidebar">
      <!-- Sidebar user panel -->
     <%--  <div class="user-panel">
        <div class="pull-left image">
       		<c:choose>
       		<c:when test="${ !empty user.avatar }">
				<img  class="img-circle" alt="User Image" src="<%=basePath%>file/downloadFile?fileName=${ user.avatar }" style="border:1px solid #727272"></img>
			</c:when>
			<c:otherwise>
				<img  class="img-circle" alt="User Image" src="<%=basePath%>resources/images/user_avatar.png" style="border:1px solid #727272"></img>
			</c:otherwise>
			</c:choose>
        </div>
        <div class="pull-left info">
          <p>${ user.name }</p>
          <a href="#"><i class="fa fa-circle text-success"></i> Online</a>
        </div>
      </div> --%>
      <!-- search form -->
     <!--  <form action="#" method="get" class="sidebar-form">
        <div class="input-group">
          <input type="text" name="q" class="form-control" placeholder="Search...">
              <span class="input-group-btn">
                <button type="submit" name="search" id="search-btn" class="btn btn-flat"><i class="fa fa-search"></i>
                </button>
              </span>
        </div>
      </form> -->
      <!-- /.search form -->
      <!-- sidebar menu: : style can be found in sidebar.less -->
      <ul class="sidebar-menu" >
       <!--  <li class="header"><span class="icon-tactics-board"></span></li> -->
	        <c:if test="${!empty userMenu}">
			<c:forEach items="${userMenu}" var="menu" varStatus="vs">
				<c:if test="${!empty menu.subMenus}">
					<li class="treeview ${ menu.className }">
						 <a href="#" style="font-size:16px;">
		            		<i class="${menu.icon}"></i> <span>${menu.name}</span> 
		            		<i class="fa fa-angle-right pull-right"></i>
		          		 </a>
				         <ul class="treeview-menu">
							<c:forEach items="${menu.subMenus}" var="subMenu" varStatus="subvs">
								<li style="padding-left:30px" class="${ subMenu.className }">
									<a href="#" onclick="onMenuClicked($(this),'<%=basePath%>${subMenu.url}')" style="line-height:30px" >
										<i class="${subMenu.icon}" style="vertical-align:middle;font-size:18px" ></i> <span style="vertical-align:middle;">${subMenu.name}</span>
									</a>
								</li>
							</c:forEach>
				         </ul>
			         </li>
				</c:if>
				<c:if test="${empty menu.subMenus}">
					<li class="${ menu.className }">
			          <a href="#" onclick="onMenuClicked($(this),'<%=basePath%>${menu.url}')" style="line-height:20px;font-size:16px;">
			            <i class="${menu.icon}" style="vertical-align:middle;font-size:18px"></i> <span style="vertical-align:middle;">${menu.name}</span>
			          </a>
			       </li>
				</c:if>
			</c:forEach>
		</c:if>
      </ul>
    </section>