package cn.sportsdata.webapp.youth.dao.healthdata;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.sportsdata.webapp.youth.common.vo.healthdata.HealthDataVO;
import cn.sportsdata.webapp.youth.web.controller.healthdata.request.HealthDataReqVO;

public interface HealthDao {
	public List<HealthDataVO> getHealthDataListByUserId(String userid);
	public void insertOneHealthData(HealthDataReqVO healthData);
	public void updateOneHealthData(HealthDataReqVO healthData);
	public HealthDataVO getOneHealthDataByID(String id);
	public void deleteOneHealthdataById(String id);
	public List<HashMap<String, String>> getHealthDataChart(Map<String, Object> args);
}
