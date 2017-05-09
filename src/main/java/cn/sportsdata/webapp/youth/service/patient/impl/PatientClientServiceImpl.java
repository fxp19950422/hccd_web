package cn.sportsdata.webapp.youth.service.patient.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.sportsdata.webapp.youth.common.bo.hospital.PatientRecordBriefBO;
import cn.sportsdata.webapp.youth.dao.hospital.PatientDAO;
import cn.sportsdata.webapp.youth.service.patient.PatientClientService;

@Service
public class PatientClientServiceImpl implements PatientClientService {
	private static Logger logger = Logger.getLogger(PatientClientServiceImpl.class);
	
	@Autowired
	private PatientDAO patientDAO;

	@Override
	public List<PatientRecordBriefBO> getPatientBriefRecords(String userId, String hospitalId) {
		List<PatientRecordBriefBO> list = patientDAO.getPatientBriefRecordsByHospital(userId, hospitalId);
		return list;
	}

	
	
}
