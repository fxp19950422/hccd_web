package cn.sportsdata.webapp.youth.dao.test;

import java.util.Date;
import java.util.List;

import cn.sportsdata.webapp.youth.common.vo.test.PlayerChartData;
import cn.sportsdata.webapp.youth.common.vo.test.PlayerWithTest;
import cn.sportsdata.webapp.youth.common.vo.test.SingleTestPO;
import cn.sportsdata.webapp.youth.common.vo.test.TestBatchPO;
import cn.sportsdata.webapp.youth.common.vo.test.TestBatchRenderVO;
import cn.sportsdata.webapp.youth.common.vo.test.TestDataPO;
import cn.sportsdata.webapp.youth.common.vo.test.TestItemPO;
import cn.sportsdata.webapp.youth.common.vo.test.TestItemSelectorVO;

public interface TestDao {
	
	List<TestBatchRenderVO> queryTestBatchListByOrgID(String orgID);
	
	List<TestBatchRenderVO> searchTestBatchList(String orgId, Date start_date, Date end_date);
	
	List<TestBatchRenderVO> searchTestBatchListWithItemLike(String orgId, Date start_date, Date end_date,
			String test_item_title_like);

	List<TestItemSelectorVO> queryTestItemList();

	SingleTestPO querySingleTestByID(String singleTestID);
	
	List<PlayerWithTest> queryPlayersWithTestItemByOrgIDAndTestItemID(String orgID,long testItemID);

	List<TestDataPO> querySimpleTestDataResult(String orgID, long testItemID, String testBatchID, String singleTestID);

	void insertTestBatch(TestBatchPO testBatch);

	void insertSingleTest(SingleTestPO singleTest);

	void insertTestData(TestDataPO testData);

	void deleteTestBatchByID(String id);

	void deleteSingleTestByID(String id);

	void deleteTestDataByOrgAndBatchAndSingleTest(String org_id, String test_batch_id, String single_test_id);

	void updateSingleTestByID(SingleTestPO singleTest);

	int querySingleTestCountByOrgAndBatch(String org_id, String test_batch_id);

	List<TestItemPO> queryUserLatest3TestItems(String org_id, String user_id);

	List<PlayerChartData> queryUserChartData(String org_id, String user_id, long test_item_id, Date start_date,
			Date end_date);
	List<PlayerWithTest> getFirstListSingleTestDataByOrgID(String orgID);
}
