package cn.sportsdata.webapp.youth.dao.test.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.sportsdata.webapp.youth.common.vo.test.PlayerChartData;
import cn.sportsdata.webapp.youth.common.vo.test.PlayerWithTest;
import cn.sportsdata.webapp.youth.common.vo.test.SingleTestPO;
import cn.sportsdata.webapp.youth.common.vo.test.TestBatchPO;
import cn.sportsdata.webapp.youth.common.vo.test.TestBatchRenderVO;
import cn.sportsdata.webapp.youth.common.vo.test.TestDataPO;
import cn.sportsdata.webapp.youth.common.vo.test.TestItemPO;
import cn.sportsdata.webapp.youth.common.vo.test.TestItemSelectorVO;
import cn.sportsdata.webapp.youth.dao.BaseDAO;
import cn.sportsdata.webapp.youth.dao.test.TestDao;

@Repository
public class TestDaoImpl extends BaseDAO implements TestDao{
	
	@Override
	public List<TestBatchRenderVO> queryTestBatchListByOrgID(String orgID){
		Map<String,String> paraMap = new HashMap<String,String>();
		paraMap.put("orgId", orgID);
		return sqlSessionTemplate.selectList(getSqlNameSpace("queryTestBatchListByOrgID"), paraMap);
	}
	
	@Override
	public List<TestBatchRenderVO> searchTestBatchList(String orgId,Date start_date,Date end_date){
		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("orgId", orgId);
		paraMap.put("start_date", start_date);
		paraMap.put("end_date", end_date);
		return sqlSessionTemplate.selectList(getSqlNameSpace("searchTestBatchList"), paraMap);
	}
	
	
	@Override
	public List<TestBatchRenderVO> searchTestBatchListWithItemLike(String orgId,Date start_date,Date end_date, String test_item_title_like){
		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("orgId", orgId);
		paraMap.put("start_date", start_date);
		paraMap.put("end_date", end_date);
		paraMap.put("test_item_title_like", test_item_title_like);
		return sqlSessionTemplate.selectList(getSqlNameSpace("searchTestBatchListWithItemLike"), paraMap);
	}
	
	@Override
	public List<TestItemSelectorVO> queryTestItemList(){
		return sqlSessionTemplate.selectList(getSqlNameSpace("queryTestItemList"));
	}
	
	
	@Override
	public SingleTestPO querySingleTestByID(String singleTestID){
		Map<String,String> paraMap = new HashMap<String,String>();
		paraMap.put("singleTestID", singleTestID);
		return sqlSessionTemplate.selectOne(getSqlNameSpace("querySingleTestByID"), paraMap);
	}

	@Override
	public List<PlayerWithTest> queryPlayersWithTestItemByOrgIDAndTestItemID(String orgID, long testItemID) {
		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("orgId", orgID);
		paraMap.put("testItemID", testItemID);
		return sqlSessionTemplate.selectList(getSqlNameSpace("queryPlayersWithTestItemByOrgIDAndTestItemID"), paraMap);
	}
	
	
	@Override
	public List<TestDataPO> querySimpleTestDataResult(String orgID, long testItemID,String testBatchID,String singleTestID) {
		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("orgId", orgID);
		paraMap.put("testItemID", testItemID);
		paraMap.put("testBatchID", testBatchID);
		paraMap.put("singleTestID", singleTestID);
		return sqlSessionTemplate.selectList(getSqlNameSpace("querySimpleTestDataResult"), paraMap);
	}
	
	@Override
	public void insertTestBatch(TestBatchPO testBatch){
		sqlSessionTemplate.insert(getSqlNameSpace("insertTestBatch"), testBatch);
	}
	
	@Override
	public void insertSingleTest(SingleTestPO singleTest){
		sqlSessionTemplate.insert(getSqlNameSpace("insertSingleTest"), singleTest);
	}
	
	@Override
	public void insertTestData(TestDataPO testData){
		sqlSessionTemplate.insert(getSqlNameSpace("insertTestData"), testData);
	}
	
	@Override
	public void deleteTestBatchByID(String id){
		sqlSessionTemplate.delete(getSqlNameSpace("deleteTestBatchByID"), id);
	}
	
	@Override
	public void deleteSingleTestByID(String id){
		sqlSessionTemplate.delete(getSqlNameSpace("deleteSingleTestByID"), id);
	}
	
	@Override
	public void deleteTestDataByOrgAndBatchAndSingleTest(String org_id,String test_batch_id, String single_test_id){
		Map<String,String> map = new HashMap<String,String>();
		map.put("org_id", org_id);
		map.put("test_batch_id", test_batch_id);
		map.put("single_test_id", single_test_id);
		sqlSessionTemplate.delete(getSqlNameSpace("deleteTestDataByOrgAndBatchAndSingleTest"), map);
	}
	
	@Override
	public void updateSingleTestByID(SingleTestPO singleTest){
		sqlSessionTemplate.update(getSqlNameSpace("updateSingleTestByID"),singleTest);
	}
	
	
	
	@Override
	public int querySingleTestCountByOrgAndBatch(String org_id, String test_batch_id){
		Map<String,String> map = new HashMap<String,String>();
		map.put("org_id", org_id);
		map.put("test_batch_id", test_batch_id);
		return sqlSessionTemplate.selectOne(getSqlNameSpace("querySingleTestCountByOrgAndBatch"), map);
	}
	
	@Override
	public List<TestItemPO> queryUserLatest3TestItems(String org_id, String user_id){
		Map<String,String> map = new HashMap<String,String>();
		map.put("org_id", org_id);
		map.put("user_id", user_id);
		return sqlSessionTemplate.selectList(getSqlNameSpace("queryUserLatest3TestItems"), map);
	}
	
	
	@Override
	public List<PlayerChartData> queryUserChartData(String org_id, String user_id,long test_item_id,Date start_date,Date end_date){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("org_id", org_id);
		map.put("user_id", user_id);
		map.put("test_item_id", test_item_id);
		map.put("start_date", start_date);
		map.put("end_date", end_date);
		return sqlSessionTemplate.selectList(getSqlNameSpace("queryUserChartData"), map);
	}
	
	
	
	@Override
	public List<PlayerWithTest> getFirstListSingleTestDataByOrgID(String orgID) {
		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("orgId", orgID);
		return sqlSessionTemplate.selectList(getSqlNameSpace("getFirstListSingleTestDataByOrgID"), paraMap);
	}
	

}
