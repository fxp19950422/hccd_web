package cn.sportsdata.webapp.youth.service.exchange.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.sportsdata.webapp.youth.common.vo.patient.DoctorVO;
import cn.sportsdata.webapp.youth.common.vo.patient.PatientVO;
import cn.sportsdata.webapp.youth.common.vo.patient.ResidentRecord;
import cn.sportsdata.webapp.youth.dao.exchange.ExchangeDAO;
import cn.sportsdata.webapp.youth.service.exchange.ExchangeService;

@Service
public class ExchangeServiceImpl implements ExchangeService {
	private static Logger logger = Logger.getLogger(ExchangeServiceImpl.class);
	@Autowired
	private ExchangeDAO exchangeDao;
	@Override
	public List<PatientVO> getPatients(String departmentId, String doctorCode) {
		// TODO Auto-generated method stub
		return exchangeDao.getPatients(departmentId, doctorCode);
	}
	@Override
	public List<DoctorVO> getDoctors(String departmentId, boolean isAll) {
		// TODO Auto-generated method stub
		return exchangeDao.getDoctors(departmentId, isAll);
	}
	@Override
	public List<ResidentRecord> getMedicalRecordByPatientIds(List<String> uids) {
		// TODO Auto-generated method stub
		return exchangeDao.getMedicalRecordByPatientIds(uids);
	}
	@Override
	public List<DoctorVO> getAllDoctors(String departmentId) {
		// TODO Auto-generated method stub
		return exchangeDao.getAllDoctors(departmentId);
	}
	
	
}
