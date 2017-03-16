package cn.sportsdata.webapp.youth.service.test.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.sportsdata.webapp.youth.common.vo.test.PlayerChartData;
import cn.sportsdata.webapp.youth.common.vo.test.PlayerWithTest;
import cn.sportsdata.webapp.youth.common.vo.test.SingleTestPO;
import cn.sportsdata.webapp.youth.common.vo.test.TestBatchPO;
import cn.sportsdata.webapp.youth.common.vo.test.TestBatchRenderVO;
import cn.sportsdata.webapp.youth.common.vo.test.TestDataPO;
import cn.sportsdata.webapp.youth.common.vo.test.TestItemPO;
import cn.sportsdata.webapp.youth.common.vo.test.TestItemSelectorRenderVO;
import cn.sportsdata.webapp.youth.common.vo.test.TestItemSelectorVO;
import cn.sportsdata.webapp.youth.dao.test.TestDao;
import cn.sportsdata.webapp.youth.service.test.TestService;

@Service
public class TestServiceImpl implements TestService{
	private static Logger logger = Logger.getLogger(TestServiceImpl.class);
	
	@Autowired
	private TestDao testDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<TestBatchRenderVO> getTestBatchListByOrgID(String orgID){
		return testDao.queryTestBatchListByOrgID(orgID);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<TestBatchRenderVO> searchTestBatchList(String orgID,String test_item_title,String start_date_str,String end_date_str){
		Date start_date = getTestTime(start_date_str);
		Date current_end_date = getTestTime(end_date_str);
		Date end_date = new Date(current_end_date.getTime() + 1000 * 60 * 60 * 24);
		if(StringUtils.isBlank(test_item_title)){
			return testDao.searchTestBatchList(orgID, start_date, end_date);
		}else{
			String test_item_title_like = "%" + test_item_title.trim().toUpperCase() + "%";
			return testDao.searchTestBatchListWithItemLike(orgID, start_date, end_date, test_item_title_like);
		}
	}
	
	
	@Override
	@Transactional(readOnly = true)
	public TestItemSelectorRenderVO getTestItemList(){
		List<TestItemSelectorVO>  testItemSelectorList = testDao.queryTestItemList();
		return new TestItemSelectorRenderVO().categoryWith(testItemSelectorList);
	}
	
	@Override
	@Transactional(readOnly = true)
	public SingleTestPO getSingleTestByID(String singleTestID){
		return testDao.querySingleTestByID(singleTestID);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<PlayerWithTest> getPlayersWithTestItemByOrgIDAndTestItemID(String orgID, long testItemID) {
		return testDao.queryPlayersWithTestItemByOrgIDAndTestItemID(orgID, testItemID);
	}

	@Override
	@Transactional(readOnly = true)
	public List<PlayerWithTest> getPlayersWithTestDataAndItem(String orgID, long testItemID, String testBatchID,String singleTestID) {
		List<PlayerWithTest> playerWithTestDataList = testDao.queryPlayersWithTestItemByOrgIDAndTestItemID(orgID, testItemID);
		List<TestDataPO> testResultList = testDao.querySimpleTestDataResult(orgID, testItemID, testBatchID, singleTestID);
		if(testResultList != null && !testResultList.isEmpty()){
			for(TestDataPO testData : testResultList){
				PlayerWithTest testPlayer = searchPlayerByID(playerWithTestDataList,testData.getUser_id());
				if(testPlayer != null){
					testPlayer.setTest_result(testData.getTest_result());
				}
			}
		}
		return playerWithTestDataList;
	}

	private PlayerWithTest searchPlayerByID(List<PlayerWithTest> playerWithTestDataList, String user_id) {
		if(playerWithTestDataList == null){
			return null;
		}
		for(PlayerWithTest testPlayer : playerWithTestDataList){
			if(testPlayer.getUser_id().equals(user_id)){
				return testPlayer;
			}
		}
		return null;
	}

	@Override
	@Transactional
	public void addTestData(String org_id, String creator_id, List<PlayerWithTest> playerTestDataList,
			long test_category_id, long test_item_id, String test_time_str) {
		Date test_time = getTestTime(test_time_str);
		
		TestBatchPO testBatch = new TestBatchPO();
		testBatch.setOrg_id(org_id);
		testBatch.setCreator_id(creator_id);
		testDao.insertTestBatch(testBatch);
		
		SingleTestPO singleTest = new SingleTestPO();
		singleTest.setOrg_id(org_id);
		singleTest.setCreator_id(creator_id);
		singleTest.setTest_category_id(test_category_id);
		singleTest.setTest_item_id(test_item_id);
		singleTest.setTest_batch_id(testBatch.getId());
		singleTest.setTest_time(test_time);
		testDao.insertSingleTest(singleTest);
		
		List<TestDataPO> testDataList = analyzeAvaiableTestData(org_id,test_item_id,test_time,testBatch.getId(),singleTest.getId(),playerTestDataList);
		if(testDataList != null && !testDataList.isEmpty()){
			for(TestDataPO testData : testDataList){
				testDao.insertTestData(testData);
			}
		}
	}

	private Date getTestTime(String test_time_str){
		SimpleDateFormat sdmf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return sdmf.parse(test_time_str);
		} catch (ParseException e) {
			logger.error("Cannot parse yyyy-MM-dd format data input :" + test_time_str ,e);
			return new Date();
		}
	}
	
	private List<TestDataPO> analyzeAvaiableTestData(String org_id, long test_item_id, Date test_time, String test_batch_id, String single_test_id,
			List<PlayerWithTest> playerTestDataList) {
		if(playerTestDataList == null || playerTestDataList.isEmpty()){
			return null;
		}
		List<TestDataPO> testDataList = new ArrayList<TestDataPO>();
		for(PlayerWithTest playerData: playerTestDataList){
			Double result = playerData.getTest_result();
			if(result == null || result == Double.NaN){
				continue;
			}
			TestDataPO testData = new TestDataPO();
			testData.setOrg_id(org_id);
			testData.setTest_item_id(test_item_id);
			testData.setTest_time(test_time);
			testData.setTest_batch_id(test_batch_id);
			testData.setSingle_test_id(single_test_id);
			testData.setUser_id(playerData.getUser_id());
			testData.setTest_result(result);
			testDataList.add(testData);
		}
		return testDataList;
	}

	@Override
	@Transactional
	public void updateTestData(String org_id, String creator_id,List<PlayerWithTest> playerTestDataList, long test_category_id,
			long test_item_id, String test_time_str, String test_batch_id, String single_test_id) {
		Date test_time = getTestTime(test_time_str);
		SingleTestPO singleTest = new SingleTestPO();
		singleTest.setId(single_test_id);
		singleTest.setTest_category_id(test_category_id);
		singleTest.setTest_item_id(test_item_id);
		singleTest.setTest_time(test_time);
		singleTest.setCreator_id(creator_id);
		
		testDao.updateSingleTestByID(singleTest);
		testDao.deleteTestDataByOrgAndBatchAndSingleTest(org_id, test_batch_id, single_test_id);
		List<TestDataPO> testDataList = analyzeAvaiableTestData(org_id,test_item_id,test_time,test_batch_id,single_test_id,playerTestDataList);
		if(testDataList != null && !testDataList.isEmpty()){
			for(TestDataPO testData : testDataList){
				testDao.insertTestData(testData);
			}
		}
	}

	@Override
	@Transactional
	public void deleteTestData(String org_id,String test_batch_id, String single_test_id) {
		testDao.deleteTestDataByOrgAndBatchAndSingleTest(org_id, test_batch_id, single_test_id);
		int singleTestInBatchCount = testDao.querySingleTestCountByOrgAndBatch(org_id, test_batch_id);
		testDao.deleteSingleTestByID(single_test_id);
		if(singleTestInBatchCount <= 1){
			testDao.deleteTestBatchByID(test_batch_id);
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<TestItemPO> getUserLatest3TestItems(String org_id, String user_id){
		return testDao.queryUserLatest3TestItems(org_id, user_id);
	}
	
	
	@Override
	@Transactional(readOnly = true)
	public List<PlayerChartData> getUserChartData(String org_id, String user_id,long test_item_id,String start_date_str,String end_date_str){
		Date start_date = getTestTime(start_date_str);
		Date current_end_date = getTestTime(end_date_str);
		Date end_date = new Date(current_end_date.getTime() + 1000 * 60 * 60 * 24);
		return testDao.queryUserChartData(org_id, user_id, test_item_id, start_date, end_date);
		
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<PlayerWithTest> getFirstListSingleTestDataByOrgID(String orgID) {
		return testDao.getFirstListSingleTestDataByOrgID(orgID);
	}
}
