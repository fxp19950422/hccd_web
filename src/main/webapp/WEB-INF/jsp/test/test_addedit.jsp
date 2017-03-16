<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page import="cn.sportsdata.webapp.youth.common.utils.CommonUtils" %>

<%
	String serverUrl = CommonUtils.getServerUrl(request);
%>
<section class="top_control_area">
	<button id="save_btn" class="btn btn-primary test-btn-right" >保存</button>
	<button id="delete_btn" class="btn btn-danger test-btn-right">删除</button>
	<button id="back_btn" class="btn btn-default test-btn-right">取消</button>
	<div class="clearfix"></div>
</section>
<section class="center_content">
	<div class="panel panel-default"> 
		<div class="panel-heading text-left ng-binding" style="background-color:#067DC2;color:white">测试信息</div>
		<div class="panel-body table-responsive">
			<div class="row">
				<div class="col-md-1 inputLabel">测试项目: </div>
				<div class="col-md-3 form-group" >
					<select id="testItemNameSelector" style="width:153px">
						<option value="-1" class="testItemOption">&nbsp;</option>
						<c:forEach var="itemCategory" items="${testItemRenderData.itemCategoryMap}"> 
							<optgroup label="<c:out value='${itemCategory.key}'></c:out>">
								<c:forEach var="item" items="${itemCategory.value}"> 
									<option value ="${item.id}" class="testItemOption" cid = '${item.test_category_id}'><c:out value='${item.title}'></c:out></option>
								</c:forEach>
							</optgroup>
						</c:forEach>
					</select>
				</div>
				<div class="col-md-1 inputLabel">记录人： </div>
				<div class="col-md-3 form-group">
					<select id="creatorNameSelector" style="width:153px">
						<c:forEach var="coach" items="${coachList}"> 
							<option value ="${coach.id}" class="creatorOption"><c:out value='${coach.name}'></c:out></option>
						</c:forEach>
					</select>
				</div>
				<div class="col-md-1 inputLabel">测试时间:</div>
				<div class="col-md-3" >
					<div class="input-group date">
						<input type="text" class="form-control profileEditInput calendar-input" id="test_time" readonly>
						<span id="test_time_icon" class="input-group-addon calendar-icon"><i class="glyphicon glyphicon-calendar major_color"></i></span>
					</div>
				</div>
			</div>
		</div> 
	</div>

	<table id="testdataTable"
			data-classes="table table-no-bordered sprotsdatatable"
			data-toggle="table" data-striped="true">
			<thead>
				<tr>
					<th data-width="10%" data-align="center" data-formatter="UIController.indexFormatter">序号</th>
					<th data-width="25%" data-align="center" data-field="user_name" data-formatter="UIController.escapeFormatter">姓名</th>
					<th data-width="35%" data-align="center" data-field="test_item_title" data-formatter="UIController.escapeFormatter">测试项目</th>
					<th data-width="20%" data-align="center" data-field="test_result" data-editable="true" data-editable-emptytext="请输入成绩">成绩</th>
					<th data-width="10%" data-align="center" data-field="test_unit_name" data-formatter="UIController.escapeFormatter">单位</th>
					
				</tr>
			</thead>
	</table>
</section>





<script type="text/javascript">
	var DataController = (function(self){
		self.isAdd = false;
		self.single_test_id = "";
		self.test_batch_id = "";
		self.test_category_id = "";
		self.test_item_id = "";
		self.cur_test_category_id = "";
		self.cur_test_item_id = "";
		
		self.init = function(){
			this.isAdd = ${isAdd};
			if(!this.isAdd){
				this.single_test_id = "${singleTest.id}";
				this.test_batch_id = "${singleTest.test_batch_id}";
				this.test_category_id = "${singleTest.test_category_id}";
				this.test_item_id = "${singleTest.test_item_id}";
			}
			
		};
		
		self.checkSaveData = function(){
			if(this.cur_test_category_id == "" || this.cur_test_item_id == ""){
				alert("请选择一个测试项目");
				return false;
			}
			return true;
		}
		return self;
	})(DataController || {});
	
	var UIController = (function(self){
		self.init = function(){
			if(DataController.isAdd){
				$("#delete_btn").addClass("hidden");
			}
			
			$("#test_time").datepicker({
				format : "yyyy-mm-dd",
				language : "zh-CN",
				autoclose : true,
				todayHighlight : true,
				toggleActive : true
			});
			$("#test_time").datepicker('setEndDate', '+0d');
			
			if(DataController.isAdd){
				$("#test_time").datepicker('setDate', '+0d');
			}else{
				$("#test_time").datepicker('setDate', new Date(parseInt("${singleTest.test_time.time}")));
				$("option.testItemOption").attr("disabled", "disabled"); 
				$("option.testItemOption[value = '"+DataController.test_item_id+"']").removeAttr("disabled"); 
				$("option.testItemOption[value = '"+DataController.test_item_id+"']").attr("selected", true); 
				$("option.creatorOption[value = '${singleTest.creator_id}']").attr("selected", true); 
			}
			$('#testItemNameSelector').select2({
				minimumResultsForSearch : Infinity
			});
			$('#creatorNameSelector').select2({
				minimumResultsForSearch : Infinity
			});
			$("#testdataTable").bootstrapTable();
			$('#testdataTable').on('editable-save.bs.table', function ($el,field, row, oldValue) {
				if(!!field && field == "test_result"){
					if(isNaN(row.test_result)){ //false when isNaN("number") or isNaN("") 
						var rowIndex = $('#testdataTable').bootstrapTable('getData').indexOf(row);
						$("#testdataTable").bootstrapTable("updateCell", {  
							index : rowIndex,  
							field : 'test_result',  
							value : oldValue  
						});  
					}
				}
			});
			this.dynamicLoadTestTable();
		};
		self.dynamicLoadTestTable = function(){
			var choiceTestItem = $('option.testItemOption:selected');
			var choiceTestItemID = choiceTestItem.val();
			if(!choiceTestItemID || choiceTestItemID == "-1"){
				DataController.cur_test_category_id = "";
				DataController.cur_test_item_id = "";
				//removeAll -> refresh will may cause the page height more than one screen, will have cannot scroll issue on this angular compile page
				//change to hide() - > show();
				//$("#testdataTable").bootstrapTable('removeAll'); 
				$("#testdataTable").hide();
			}else{
				DataController.cur_test_category_id = choiceTestItem.attr("cid");
				DataController.cur_test_item_id = choiceTestItemID;
				var loadUrl ;
				if(!DataController.isAdd && DataController.test_item_id == DataController.cur_test_item_id){
					loadUrl = "<%=serverUrl%>test/loadTestPlayersWithTestDataInItem?test_item_id=" + DataController.cur_test_item_id 
							+ "&sid=" + DataController.single_test_id + "&bid="  + DataController.test_batch_id;
				}else{
					loadUrl = "<%=serverUrl%>test/loadTestPlayersWithTestItem?test_item_id=" + DataController.cur_test_item_id;
				}
				$("#testdataTable").show();
				$("#testdataTable").bootstrapTable('refresh', {url : loadUrl});
			}
		};
		self.indexFormatter = function(value, row, index){
			return index + 1;
		};
		/* self.testResultFormatter = function(value, row, index){
			if(value == null || value == undefined || value == "null"){
				return "请输入成绩";
			}
		}; */
		self.escapeFormatter = function(value, row, index){
			if (typeof value === 'string') {
		        return value
		            .replace(/&/g, "&amp;")
		            .replace(/</g, "&lt;")
		            .replace(/>/g, "&gt;")
		            .replace(/"/g, "&quot;")
		            .replace(/'/g, "&#039;");
		    }
		    return value;
		};
		
		self.disableAllBtn = function(){
			$("#save_btn").attr("disabled","disabled");
			$("#delete_btn").attr("disabled","disabled");
			$("#back_btn").attr("disabled","disabled");
		};
		
		self.enableAllBtn = function(){
			$("#save_btn").removeAttr("disabled");
			$("#delete_btn").removeAttr("disabled");
			$("#back_btn").removeAttr("disabled");
		}
		return self;
	})(UIController || {});
	
	var EventController = (function(self){
		self.init = function(){
			buildBreadcumb(DataController.isAdd ? "新增测试信息" : "编辑测试信息");
			$("#save_btn").click(function(){
				UIController.disableAllBtn();
				if(DataController.checkSaveData()){
					var url;
					var method;
					var creator_id = $("#creatorNameSelector").children('option:selected').val()
					if (!creator_id) {
						bootbox.dialog({
							message: "请选择测试的记录人。",
							title: "测试",
							buttons: {
								unchange: {
								      label: "确定",
								      className: "btn-primary",
								      callback: function() {
								    	  UIController.enableAllBtn();
								      }
								    }
							}
						});
						return;
					}
					if(DataController.isAdd){
						url = "<%=serverUrl%>test/testdata?cid=" + DataController.cur_test_category_id + "&iid=" + DataController.cur_test_item_id +
						"&test_time=" + $("#test_time").val() + "&creator_id=" + creator_id;
						method = "POST";
					}else{
						url = "<%=serverUrl%>test/testdata?cid=" + DataController.cur_test_category_id + "&iid=" + DataController.cur_test_item_id +
						"&test_time=" + $("#test_time").val() + "&bid=" + DataController.test_batch_id + "&sid=" + DataController.single_test_id + "&creator_id=" + creator_id;
						method = "PUT";
					}
					var obj = $("#testdataTable").bootstrapTable('getData');
					$.ajax({
						type : method,
						url : url,
						contentType : "application/json; charset=utf-8",
						dataType : 'json',
						data : JSON.stringify(obj),
						success : function(message) {
							sa.ajax({
								type : "get",
								url : "<%=serverUrl%>test/test_manage",
								success : function(data) {
									AngularHelper.Compile($('#content'), data);
								},
								error: function() {
									alert("返回页面失败");
								}
							});
						},
						error : function(XMLHttpRequest, textStatus, errorThrown) {
							alert("保存失败，请重试");
						}
					});
				}
				UIController.enableAllBtn();
			});
			$('#back_btn').click(function() {
				sa.ajax({
					type : "get",
					url : "<%=serverUrl%>test/test_manage",
					success : function(data) {
						AngularHelper.Compile($('#content'), data);
					},
					error: function() {
						alert("返回页面失败");
					}
				});
			});
			
			
			$("#delete_btn").click(function(){
				UIController.disableAllBtn();
				if(!DataController.isAdd){
					bootbox.dialog({
						message: "您是否要删除该测试数据？",
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
									var url = "<%=serverUrl%>test/testdata?bid=" + DataController.test_batch_id + "&sid=" + DataController.single_test_id;
									$.ajax({
										type : 'DELETE',
										url : url,
										dataType : 'json',
										success : function(message) {
											sa.ajax({
												type : "get",
												url : "<%=serverUrl%>test/test_manage",
												success : function(data) {
													AngularHelper.Compile($('#content'), data);
												},
												error: function() {
													alert("返回页面失败");
												}
											});
										},
										error : function(XMLHttpRequest, textStatus, errorThrown) {
											alert("保存失败，请重试");
										}
									});
								}
							}
						}
					});
				}
				UIController.enableAllBtn();
			});
			
			$("#testItemNameSelector").change(function(){
				UIController.dynamicLoadTestTable();
			});
			
			$('#test_time_icon').click(function() {
				$('#test_time').datepicker('show');
			});
		};
		return self;
	})(EventController || {});

	$(function() {
		DataController.init();
		UIController.init();
		EventController.init();
	});

</script>