package cn.sportsdata.webapp.youth.pcli.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.sportsdata.webapp.youth.pcli.dao.PCliHospitalDao;
import cn.sportsdata.webapp.youth.pcli.service.PCliHospitalService;
import cn.sportsdata.webapp.youth.pcli.vo.HospitalVO;

@Service
public class PCliHospitalServiceImpl implements PCliHospitalService {

	@Autowired
	PCliHospitalDao hospitalDao;
	
	@Override
	public HospitalVO getHospitalInfo(String id) {
		return hospitalDao.getHospitalInfo(id);
	}

	

}
