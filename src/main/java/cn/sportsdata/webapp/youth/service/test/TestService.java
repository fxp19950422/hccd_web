package cn.sportsdata.webapp.youth.service.test;

import java.util.List;

import cn.sportsdata.webapp.youth.common.vo.test.PlayerChartData;
import cn.sportsdata.webapp.youth.common.vo.test.PlayerWithTest;
import cn.sportsdata.webapp.youth.common.vo.test.SingleTestPO;
import cn.sportsdata.webapp.youth.common.vo.test.TestBatchRenderVO;
import cn.sportsdata.webapp.youth.common.vo.test.TestItemPO;
import cn.sportsdata.webapp.youth.common.vo.test.TestItemSelectorRenderVO;

public interface TestService {
	
	List<TestBatchRenderVO> getTestBatchListByOrgID(String orgID);

	TestItemSelectorRenderVO getTestItemList();

	SingleTestPO getSingleTestByID(String singleTestID);

	List<PlayerWithTest> getPlayersWithTestItemByOrgIDAndTestItemID(String orgID, long testItemID);
	
	List<PlayerWithTest> getPlayersWithTestDataAndItem(String orgID, long testItemID, String testBatchID, String singleTestID);

	void addTestData(String org_id, String creator_id, List<PlayerWithTest> playerTestDataList, long test_category_id,long test_item_id, String test_time_str);

	void updateTestData(String org_id, String creator_id,List<PlayerWithTest> playerTestDataList, long test_category_id, long test_item_id,
			String test_time_str, String test_batch_id, String single_test_id);

	void deleteTestData(String org_id,String test_batch_id, String single_test_id);

	List<TestItemPO> getUserLatest3TestItems(String org_id, String user_id);

	List<PlayerChartData> getUserChartData(String org_id, String user_id, long test_item_id, String start_date_str,
			String end_date_str);

	List<TestBatchRenderVO> searchTestBatchList(String orgID, String test_item_title, String start_date_str,
			String end_date_str);
	List<PlayerWithTest> getFirstListSingleTestDataByOrgID(String orgID);
}
