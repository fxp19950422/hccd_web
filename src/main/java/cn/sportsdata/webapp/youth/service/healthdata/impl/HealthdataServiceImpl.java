package cn.sportsdata.webapp.youth.service.healthdata.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.sportsdata.webapp.youth.common.vo.healthdata.HealthDataVO;
import cn.sportsdata.webapp.youth.dao.healthdata.HealthDao;
import cn.sportsdata.webapp.youth.service.healthdata.HealthDataService;
import cn.sportsdata.webapp.youth.web.controller.healthdata.reponse.HealthChartRepVO;
import cn.sportsdata.webapp.youth.web.controller.healthdata.request.HealthDataReqVO;
@Service
public class HealthdataServiceImpl implements HealthDataService {

	@Autowired
	private HealthDao healthDao;
	
	private Map<String, String> pair= new HashMap<String, String>();
	{
		pair.put("height", "cm");
		pair.put("weight", "kg");
		pair.put("bmi", "bmi");
		pair.put("oxygen_content", "含氧量");
		pair.put("shoulder", "肩胛脂");
		pair.put("haunch", "大臂脂");
		pair.put("chest", "胸围");
		pair.put("waist", "腰围");
		pair.put("morning_pulse", "次");
		pair.put("lactate", "乳酸");
		pair.put("waistfat", "腰围脂");
	}
	
	
	private Map<String, String> pairname= new HashMap<String, String>();
	{
		pairname.put("height", "身高cm");
		pairname.put("weight", "体重kg");
		pairname.put("bmi", "bmi");
		pairname.put("oxygen_content", "含氧量");
		pairname.put("shoulder", "肩胛脂");
		pairname.put("haunch", "大臂脂");
		pairname.put("chest", "胸围cm");
		pairname.put("waist", "腰围cm");
		pairname.put("morning_pulse", "晨脉");
		pairname.put("lactate", "乳酸");
		pairname.put("waistfat", "腰围脂");
	}
	
	@Override
	public List<HealthDataVO> getHealthDataListByUserId(String userid) {
		return healthDao.getHealthDataListByUserId(userid);
	}

	@Override
	@Transactional
	public void createOneHealthData(HealthDataReqVO healthData) {
		healthDao.insertOneHealthData(healthData);
	}

	@Override
	@Transactional
	public void updateOneHealthData(HealthDataReqVO healthData) {
		healthDao.updateOneHealthData(healthData);
	}

	@Override
	public HealthDataVO getOneHealthDataByID(String id) {
		return healthDao.getOneHealthDataByID(id);
	}

	@Override
	@Transactional
	public void deleteOneHealthdataById(String id) {
		healthDao.deleteOneHealthdataById(id);
	}

	@Override
	public HealthChartRepVO getHealthChartlist(Map<String, Object> args) {
		List<HashMap<String, String>> ls = healthDao.getHealthDataChart(args);
		String col= (String) args.get("colum");
		HealthChartRepVO vo = new HealthChartRepVO();
		List<List<Object>> renderDataSeries = new ArrayList<List<Object>>();
		vo.setUnit_name(pair.get(col));
		vo.setColum(col);
		for(Map<String, String> map:ls){
			List<Object> re= new ArrayList<>();
			re.add(map.get("created_time"));
			re.add(map.get(col));
			re.add(pairname.get(col));
			renderDataSeries.add(re);
		}
		vo.setRenderDataSeries(renderDataSeries);
		return vo;
	}

}
