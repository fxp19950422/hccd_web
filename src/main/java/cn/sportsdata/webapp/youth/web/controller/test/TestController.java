package cn.sportsdata.webapp.youth.web.controller.test;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.sportsdata.webapp.youth.common.utils.StringUtil;
import cn.sportsdata.webapp.youth.common.vo.OrgVO;
import cn.sportsdata.webapp.youth.common.vo.UserVO;
import cn.sportsdata.webapp.youth.common.vo.test.PlayerChartData;
import cn.sportsdata.webapp.youth.common.vo.test.PlayerChartRenderData;
import cn.sportsdata.webapp.youth.common.vo.test.PlayerOptResp;
import cn.sportsdata.webapp.youth.common.vo.test.PlayerWithTest;
import cn.sportsdata.webapp.youth.common.vo.test.SingleTestPO;
import cn.sportsdata.webapp.youth.common.vo.test.TestBatchRenderVO;
import cn.sportsdata.webapp.youth.common.vo.test.TestItemPO;
import cn.sportsdata.webapp.youth.common.vo.test.TestItemSelectorRenderVO;
import cn.sportsdata.webapp.youth.common.vo.test.TestManagePageRenderVO;
import cn.sportsdata.webapp.youth.service.test.TestService;
import cn.sportsdata.webapp.youth.service.user.UserService;
import cn.sportsdata.webapp.youth.web.controller.BaseController;

@Controller
@RequestMapping("/test")
public class TestController extends BaseController{
	private static Logger logger = Logger.getLogger(TestController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TestService testService;
	
	@RequestMapping(value = "/test_manage",method = RequestMethod.GET)
    public String toTestManagePage(HttpServletRequest request, Model model, @RequestParam(required=false,defaultValue = "0") int radio) {
		OrgVO orgVO = getCurrentOrg(request);
		List<UserVO> playerList = userService.getPlayersByOrgId(orgVO.getId());
		
		TestManagePageRenderVO renderVO = new TestManagePageRenderVO();
		//List<TestBatchRenderVO>  testBatchList = testService.getTestBatchListByOrgID(orgVO.getId());
		//model.addAttribute("renderData", renderVO.withTestBatches(testBatchList).withPlayers(playerList));
		model.addAttribute("renderData", renderVO.withPlayers(playerList));
		model.addAttribute("byBatch", radio == 0);
		return "test/test_list2";
	}
	
	@RequestMapping(value = "/test_batch", method = RequestMethod.GET)
	@ResponseBody
	public List<TestBatchRenderVO> loadTestBatchData(HttpServletRequest request, Model model,
			@RequestParam("siname") String searchItemName,@RequestParam("start") String startDateStr,@RequestParam("end") String endDateStr){
		OrgVO orgVO = getCurrentOrg(request);
		return testService.searchTestBatchList(orgVO.getId(), searchItemName, startDateStr, endDateStr);
	}
	
	
	@RequestMapping(value = "/test_addedit",method = RequestMethod.GET)
	public String toTestAddEditPage(HttpServletRequest request, Model model,@RequestParam(required = false,name="sid",defaultValue = "") String singleTestID) {
		TestItemSelectorRenderVO testItemRender = testService.getTestItemList();
		model.addAttribute("testItemRenderData",testItemRender);
		boolean isAdd = StringUtil.isBlank(singleTestID);
		model.addAttribute("isAdd", isAdd);
		if(!isAdd){
			SingleTestPO singleTest = testService.getSingleTestByID(singleTestID);
			model.addAttribute("singleTest", singleTest);
		}
		List<UserVO> coachList = userService.getCoachsByOrgId(getCurrentOrg(request).getId());
		model.addAttribute("coachList", coachList);
		return "test/test_addedit";
	}
	
	
	@RequestMapping(value = "/loadTestPlayersWithTestItem",method = RequestMethod.GET)
	@ResponseBody
	public Object loadTestPlayersWithTestItem(HttpServletRequest request, Model model,@RequestParam long test_item_id) {
		OrgVO orgVO = getCurrentOrg(request);
		return testService.getPlayersWithTestItemByOrgIDAndTestItemID(orgVO.getId(), test_item_id);
	}
	
	@RequestMapping(value = "/loadTestPlayersWithTestDataInItem",method = RequestMethod.GET)
	@ResponseBody
	public Object loadTestPlayersWithTestDataInItem(HttpServletRequest request, Model model,@RequestParam long test_item_id,
			 @RequestParam(name="bid") String testBatchID,@RequestParam(name="sid") String singleTestID) {
		OrgVO orgVO = getCurrentOrg(request);
		return testService.getPlayersWithTestDataAndItem(orgVO.getId(), test_item_id, testBatchID, singleTestID);
	}
	
	@RequestMapping(value = "/testdata",method = RequestMethod.POST)
	@ResponseBody
	public Object addTestData(HttpServletRequest request, @RequestBody List<PlayerWithTest> playerTestDataList,
			@RequestParam("cid") long test_category_id,@RequestParam("iid") long test_item_id, @RequestParam("test_time") String test_time,
			@RequestParam("creator_id") String creator_id){
		OrgVO orgVO = getCurrentOrg(request);
		testService.addTestData(orgVO.getId(),creator_id,playerTestDataList,test_category_id,test_item_id,test_time);
		return PlayerOptResp.getNormalSuccessResp();
	}
	
	@RequestMapping(value = "/testdata",method = RequestMethod.PUT)
	@ResponseBody
	public Object updateTestData(HttpServletRequest request, @RequestBody List<PlayerWithTest> playerTestDataList,
			@RequestParam("cid") long test_category_id,@RequestParam("iid") long test_item_id, @RequestParam("test_time") String test_time,
			@RequestParam("bid") String test_batch_id,@RequestParam("sid") String single_test_id,@RequestParam("creator_id") String creator_id){
		OrgVO orgVO = getCurrentOrg(request);
		testService.updateTestData(orgVO.getId(),creator_id,playerTestDataList,test_category_id,test_item_id,test_time,test_batch_id,single_test_id);
		return PlayerOptResp.getNormalSuccessResp();
	}
	
	@RequestMapping(value = "/testdata",method = RequestMethod.DELETE)
	@ResponseBody
	public Object deleteTestData(HttpServletRequest request,@RequestParam("bid") String test_batch_id,@RequestParam("sid") String single_test_id){
		OrgVO orgVO = getCurrentOrg(request);
		testService.deleteTestData(orgVO.getId(),test_batch_id,single_test_id);
		return PlayerOptResp.getNormalSuccessResp();
	}
	
	/**
	 * Dev version to display player test data smart (not use currently) 
	 */
	@RequestMapping(value = "/test_info_player", method = RequestMethod.GET)
    public String showPlayerTestDetail(HttpServletRequest request, Model model, @RequestParam String userID) {
		UserVO player = userService.getUserByID(userID);
		TestItemSelectorRenderVO testItemRender = testService.getTestItemList();
		List<TestItemPO> latest3TestItems = testService.getUserLatest3TestItems(getCurrentOrg(request).getId(), userID);
		
		model.addAttribute("player", player);
		model.addAttribute("testItemRenderData",testItemRender);
		model.addAttribute("latest3Items",latest3TestItems);

		return "test/test_info_player";
	}
	
	/**
	 * UE version to display player test data
	 */
	@RequestMapping(value = "/test_info_player_ui", method = RequestMethod.GET)
    public String showPlayerTestDetailUI(HttpServletRequest request, Model model, @RequestParam String userID,
    		@RequestParam(required=false,defaultValue="") String backurl) {
		UserVO player = userService.getUserByID(userID);
		TestItemSelectorRenderVO testItemRender = testService.getTestItemList();
		List<TestItemPO> latest3TestItems = testService.getUserLatest3TestItems(getCurrentOrg(request).getId(), userID);
		
		model.addAttribute("backurl", backurl);
		model.addAttribute("player", player);
		model.addAttribute("testItemRenderData",testItemRender);
		model.addAttribute("latest3Items",latest3TestItems);

		return "test/test_info_player_ui";
	}
	
	
	@RequestMapping(value = "/test_chartdata_player", method = RequestMethod.GET)
	@ResponseBody
    public PlayerChartRenderData getPlayersTestChartData(HttpServletRequest request, Model model, 
    		@RequestParam("pid") String userID, @RequestParam("tid") long test_item_id, 
    		@RequestParam("start") String startDateStr,@RequestParam("end") String endDateStr) {
		OrgVO orgVO = getCurrentOrg(request);
		List<PlayerChartData> dataList = testService.getUserChartData(orgVO.getId(), userID, test_item_id, startDateStr, endDateStr);
		return new PlayerChartRenderData().valueOf(dataList);
	}


}
