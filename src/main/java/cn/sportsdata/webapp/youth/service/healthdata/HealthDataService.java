package cn.sportsdata.webapp.youth.service.healthdata;

import java.util.List;
import java.util.Map;

import cn.sportsdata.webapp.youth.common.vo.healthdata.HealthDataVO;
import cn.sportsdata.webapp.youth.web.controller.healthdata.reponse.HealthChartRepVO;
import cn.sportsdata.webapp.youth.web.controller.healthdata.request.HealthDataReqVO;

public interface HealthDataService {
	public List<HealthDataVO> getHealthDataListByUserId(String userid);
	public void createOneHealthData(HealthDataReqVO healthData);
	public void updateOneHealthData(HealthDataReqVO healthData);
	public HealthDataVO getOneHealthDataByID(String id);
	public void deleteOneHealthdataById(String id);
	public HealthChartRepVO getHealthChartlist(Map<String, Object> args);
}
