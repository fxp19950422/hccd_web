package cn.sportsdata.webapp.youth.dao.healthdata.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.sportsdata.webapp.youth.common.vo.healthdata.HealthDataVO;
import cn.sportsdata.webapp.youth.dao.BaseDAO;
import cn.sportsdata.webapp.youth.dao.healthdata.HealthDao;
import cn.sportsdata.webapp.youth.web.controller.healthdata.request.HealthDataReqVO;
@Repository
public class HealthDaoImpl extends BaseDAO implements HealthDao {
	
	private static final String GET_HEALTHDATA_BYUSERID = "getHealthDataListByUserId";
	private static final String INSERT_HEALTHDATA_DATA = "insertOneHealthData";
	private static final String UPDATE_HEALTHDATA_DATA = "updateOneHealthData";
	private static final String GET_ONE_HEALTHDATA_DATA = "getOneHealthDataId";
	private static final String DEL_ONE_HEALTHDATA_DATA = "deleteOneHealthdataById";
	private static final String GET_HEALTHDATA_CHART="getHealthDataChart";

	@Override
	public List<HealthDataVO> getHealthDataListByUserId(String userid) {
		 return sqlSessionTemplate.selectList(getSqlNameSpace(GET_HEALTHDATA_BYUSERID),userid);
	}

	@Override
	public void insertOneHealthData(HealthDataReqVO healthData) {
		sqlSessionTemplate.insert(getSqlNameSpace(INSERT_HEALTHDATA_DATA), healthData);
	}

	@Override
	public void updateOneHealthData(HealthDataReqVO healthData) {
		sqlSessionTemplate.update(getSqlNameSpace(UPDATE_HEALTHDATA_DATA), healthData);
	}

	@Override
	public HealthDataVO getOneHealthDataByID(String id) {
		return sqlSessionTemplate.selectOne(GET_ONE_HEALTHDATA_DATA,id);
	}

	@Override
	public void deleteOneHealthdataById(String id) {
		sqlSessionTemplate.delete(DEL_ONE_HEALTHDATA_DATA, id);		
	}

	@Override
	public List<HashMap<String, String>> getHealthDataChart(Map<String, Object> args) {
		 return sqlSessionTemplate.selectList(getSqlNameSpace(GET_HEALTHDATA_CHART),args);
	}

}
