<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ taglib prefix="xss" uri="http://www.sportsdata.cn/xss"%>
 <!-- Header Navbar: style can be found in header.less -->
 	 <style type="text/css">
		.tooltip-inner{ 
			color: #000000;
			background-color:#ffffff;
			border:1px solid grey;
		}
		.tooltip.bottom .tooltip-arrow{
			border-top-color: #dddddd;
			border-bottom-color:#dddddd;
		}
	</style>
 	
    <nav class="navbar navbar-static-top">
      <!-- Sidebar toggle button-->
      <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
        <span class="sr-only">Toggle navigation</span>
      </a>

      <div class="navbar-custom-menu">
        <ul class="nav navbar-nav">
        
        <c:if test="${ needShowSwitch == true }">
		     <li class="dropdown messages-menu">
	            <a id="switchOrg" href="#" onclick="switchOrg()">
	              <i class="fa fa-group"></i>
	            </a>
	         </li>  
        </c:if>
          <li class="dropdown user user-menu">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
            <c:choose>
				<c:when test="${ !empty user.avatar }">
					<img accountImage="true"  class="user-image" alt="User Image" src="<%=basePath%>file/downloadFile?fileName=${ user.avatar }" style="border:1px solid #727272"></img>
				</c:when>
				<c:otherwise>
					<img accountImage="true" class="user-image" alt="User Image" src="<%=basePath%>resources/images/user_avatar.png" style="border:1px solid #727272"></img>
				</c:otherwise>
			  </c:choose>
              <span class="hidden-xs" accountName="true"><xss:xssFilter text="${user.name }" filter="html" /></span>
            </a>
            <ul class="dropdown-menu">
              <!-- User image -->
              <li class="user-header always-show-li">
				 <c:choose>
					<c:when test="${ !empty user.avatar }">
						<img accountImage="true"  class="img-circle" alt="User Image" src="<%=basePath%>file/downloadFile?fileName=${ user.avatar }" style="border:1px solid #727272"></img>
					</c:when>
					<c:otherwise>
						<img accountImage="true"  class="img-circle" alt="User Image" src="<%=basePath%>resources/images/user_avatar.png" style="border:1px solid #727272"></img>
					</c:otherwise>
			 	 </c:choose>
                <p accountName="true">
                	<xss:xssFilter text="${user.name}" filter="html" />
                </p>
                <p>
                 	<xss:xssFilter text="${orgName}" filter="html" />
                </p>
              </li>
              <!-- Menu Body -->
             <!--  <li class="user-body">
                <div class="row">
                  <div class="col-xs-4 text-center">
                    <a href="#">Followers</a>
                  </div>
                  <div class="col-xs-4 text-center">
                    <a href="#">Sales</a>
                  </div>
                  <div class="col-xs-4 text-center">
                    <a href="#">Friends</a>
                  </div>
                </div>
                /.row
              </li> -->
              <!-- Menu Footer-->
              <li class="user-footer">
                <div class="pull-left">
                  <a href="#" class="btn btn-default btn-flat" onclick="gotoPersonal()" >个人资料</a>
                </div>
                
                <div class="pull-right">
                  <a href="#" id="btnSignout" onclick="signout()" class="btn btn-default btn-flat">退出</a>
                </div>
              </li>
            </ul>
          </li>
          <!-- Control Sidebar Toggle Button -->
          <!-- <li>
            <a href="#" data-toggle="control-sidebar"><i class="fa fa-gears"></i></a>
          </li> -->
        </ul>
      </div>
    </nav>
    
    <script>
	    $(function(){
	    	$(document).off('click', 'li.always-show-li');
	    	$(document).on('click', 'li.always-show-li', function(){return false;});
	    	
	    	$("#switchOrg").tooltip({
				placement:"bottom",
				html:true,
				title : "<div style='display:block;text-align:left;background: white;color:#666666;width:90px;' <span style='color:#0d9ae7;font-weight:bold'>提示:</span> &nbsp;切换组织</div>"
			});
	    })
    	function signout(){
    		location.href="<%=basePath%>auth/logout";
    	}
    	function switchOrg(){
    		location.href="<%=basePath%>auth/OrgSel";
    	}
    	
    	
    	function gotoPersonal(){
    		sa.ajax({
				type : "get",
				url : "<%=basePath%>user/personal_edit",
				success : function(data) {
					AngularHelper.Compile($('#content'), data);
					rebuildBreadcumb("首页", "个人资料修改");
				},
				error: function() {
					alert("打开用户详情页面失败");
				}
			});
    	}
    </script>