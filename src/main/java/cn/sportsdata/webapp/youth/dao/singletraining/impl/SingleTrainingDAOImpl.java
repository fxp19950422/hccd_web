package cn.sportsdata.webapp.youth.dao.singletraining.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.sportsdata.webapp.youth.common.vo.SingleTrainingEquipmentVO;
import cn.sportsdata.webapp.youth.common.vo.SingleTrainingExtVO;
import cn.sportsdata.webapp.youth.common.vo.SingleTrainingVO;
import cn.sportsdata.webapp.youth.dao.BaseDAO;
import cn.sportsdata.webapp.youth.dao.singletraining.SingleTrainingDAO;

@Repository
public class SingleTrainingDAOImpl extends BaseDAO  implements SingleTrainingDAO{
	private static final String GET_BY_ID = "getSingleTrainingByID";
	private static final String INSERT_SINGLE_BASIC_DATA = "insertSingleTrainingBasicData";
	private static final String INSERT_SINGLE_EXT_DATA = "insertSingleTrainingExtData";
	private static final String INSERT_SINGLE_EQU_DATA = "insertSingleTrainingEquipmentData";
	private static final String UPDATE_SINGLE_BASIC_DATA = "updateSingleTrainingBasicData";
	private static final String UPDATE_SINGLE_EXT_DATA = "updateSingleTrainingExtData";
	private static final String UPDATE_SINGLE_EQU_DATA = "updateSingleTrainingEquipmentData";
	private static final String DELETE_SINGLE_BY_ID = "deleteSingleTrainingById";
	private static final String DELETE_SINGLE_EXT_BY_ID = "deleteSingleTrainingExtBySingleTrainingId";
	private static final String DELETE_SINGLE_EQU_BY_ID = "deleteSingleTrainingEquipmentBySingleTrainingId";
	private static final String GET_SINGLE_EXT_BY_ID = "getSingleTrainingExtBySingleTrainingId";
	private static final String GET_SINGLE_EQU_BY_ID = "getSingleTrainingEquipmentBySingleTrainingId";
	private static final String SINGLE_LIST = "selectSingleTrainingList";
	private static final String DELETE_SINGLE_EQU_DATA = "deteleSingleTrainingEquipmentData";
	private static final String GET_SINGLE_EQU_DATA_BY_ID = "getSingleTrainingEquipmentListByTd";
	
	
	@Override
	public SingleTrainingVO getSingleTrainingByID(String id) {
		return sqlSessionTemplate.selectOne(getSqlNameSpace(GET_BY_ID), id);
	}

	@Override
	public boolean handleSingleTraining(SingleTrainingVO singletraining, List<SingleTrainingExtVO> singletrainingExtList,List<SingleTrainingEquipmentVO> singletrainingEquipmentList,boolean isCreate) {
		int affectedFormNum = sqlSessionTemplate.insert(getSqlNameSpace(isCreate ? INSERT_SINGLE_BASIC_DATA : UPDATE_SINGLE_BASIC_DATA), singletraining);
		
		if(affectedFormNum != 1) {
			return false;
		}

		Map<String, Object> paramList = new HashMap<String, Object>();
		paramList.put("id", singletraining.getId());
		paramList.put("singleTrainingExtList", singletrainingExtList);
		
		sqlSessionTemplate.insert(getSqlNameSpace(isCreate ? INSERT_SINGLE_EXT_DATA : UPDATE_SINGLE_EXT_DATA), paramList);
		paramList.put("singleTrainingEquipmentList", singletrainingEquipmentList);
		if(isCreate){
			if(singletrainingEquipmentList == null || singletrainingEquipmentList.size() == 0) {
				return true;
			}
			sqlSessionTemplate.insert(getSqlNameSpace(INSERT_SINGLE_EQU_DATA), paramList);
		}else{
			SingleTrainingVO stvo=new SingleTrainingVO();
			stvo=sqlSessionTemplate.selectOne(getSqlNameSpace(GET_SINGLE_EQU_DATA_BY_ID), singletraining.getId());
			List<SingleTrainingEquipmentVO> stequList=stvo.getSingleTrainingEquipmentInfoList();
			if((stequList==null||stequList.size()==0) && (singletrainingEquipmentList!=null&&singletrainingEquipmentList.size()>0)){
				sqlSessionTemplate.insert(getSqlNameSpace(INSERT_SINGLE_EQU_DATA), paramList);
			}else if((stequList!=null && stequList.size()>0) && (singletrainingEquipmentList==null ||singletrainingEquipmentList.size()==0)){
				sqlSessionTemplate.insert(getSqlNameSpace(DELETE_SINGLE_EQU_BY_ID), singletraining.getId());
				
			}else if((stequList!=null && stequList.size()>0) && (singletrainingEquipmentList!=null && singletrainingEquipmentList.size()>0)){
				List<SingleTrainingEquipmentVO> insertList=new ArrayList<SingleTrainingEquipmentVO>();
				List<SingleTrainingEquipmentVO> updateList=new ArrayList<SingleTrainingEquipmentVO>();
				List<SingleTrainingEquipmentVO> deleteList=new ArrayList<SingleTrainingEquipmentVO>();
				for(SingleTrainingEquipmentVO ste1 : singletrainingEquipmentList){
					int n=0;
					for(SingleTrainingEquipmentVO ste2 : stequList){
						if((ste1.getEquipmentId()).equals(ste2.getEquipmentId())){
							n=1;
							break;
						}
					}
					if(n==0){
						insertList.add(ste1);
					}else{
						updateList.add(ste1);
					}
				}
				for(SingleTrainingEquipmentVO ste1 : stequList){
					int n=0;
					for(SingleTrainingEquipmentVO ste2 : singletrainingEquipmentList){
						if((ste1.getEquipmentId()).equals(ste2.getEquipmentId())){
							n=1;
							break;
						}
					}
					if(n==0){
						deleteList.add(ste1);
					}
				}
				if(insertList!=null && insertList.size()>0){
					paramList.put("singleTrainingEquipmentList", insertList);
					sqlSessionTemplate.insert(getSqlNameSpace(INSERT_SINGLE_EQU_DATA), paramList);
				}
				if(updateList!=null && updateList.size()>0){
					paramList.put("singleTrainingEquipmentList", updateList);
					sqlSessionTemplate.insert(getSqlNameSpace(UPDATE_SINGLE_EQU_DATA), paramList);
				}
				if(deleteList!=null && deleteList.size()>0){
					paramList.put("singleTrainingEquipmentList", deleteList);
					sqlSessionTemplate.insert(getSqlNameSpace(DELETE_SINGLE_EQU_DATA), paramList);
				}
			}
		}
		return true;
	}

	@Override
	public boolean deleteSingleTraining(String id) {
		int equNum = getSingleTrainingEquListById(id);
		if(equNum>0){
			int equDel =sqlSessionTemplate.insert(getSqlNameSpace(DELETE_SINGLE_EQU_BY_ID), id);
			if(equDel!=equNum){
				return false;
			}
		}
		int extNum = getSingleTrainingExtListById(id);
		if(extNum>0){
			int extDel = sqlSessionTemplate.insert(getSqlNameSpace(DELETE_SINGLE_EXT_BY_ID), id);
			if(extDel!=extNum){
				return false;
			}
		}
		int affectedRowNum = sqlSessionTemplate.insert(getSqlNameSpace(DELETE_SINGLE_BY_ID), id);
		return affectedRowNum == 1;
	}

	private int getSingleTrainingExtListById(String id) {
		
		return sqlSessionTemplate.selectOne(getSqlNameSpace(GET_SINGLE_EXT_BY_ID), id);
	}

	private int getSingleTrainingEquListById(String id) {
		
		return sqlSessionTemplate.selectOne(getSqlNameSpace(GET_SINGLE_EQU_BY_ID), id);
	}
	
	@Override
	public List<SingleTrainingVO> getSingleTrainingList(String orgID) {
		return sqlSessionTemplate.selectList(getSqlNameSpace(SINGLE_LIST), orgID);
	}

}
