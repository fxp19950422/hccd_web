package cn.sportsdata.webapp.youth.dao.exchange.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.sportsdata.webapp.youth.common.vo.patient.DoctorVO;
import cn.sportsdata.webapp.youth.common.vo.patient.PatientInHospital;
import cn.sportsdata.webapp.youth.common.vo.patient.PatientVO;
import cn.sportsdata.webapp.youth.common.vo.patient.ResidentRecord;
import cn.sportsdata.webapp.youth.dao.BaseDAO;
import cn.sportsdata.webapp.youth.dao.exchange.ExchangeDAO;

@Repository
public class ExchangeDAOImpl extends BaseDAO implements ExchangeDAO {

	private static final String GET_DOCTORS = "getDoctors";
	private static final String GET_PATIENTS = "getPatients";
	private static final String GET_MEDICAL_RECORDS_BY_PATIENT_IDS = "getMedicalRecordByPatientIds";
	
	private static final String GET_DOCTOR_BY_ID = "getDoctorById";
	
	private static final String UPDATE_DOCTOR_LOGIN_ID = "updateDoctorLoginId";
	
	private static final String GET_DOCTORS_BY_LOGIN_ID = "getDoctorsByLoginId";
	
	private static final String GET_EXCHANGE_OPERATION_RECORDS = "getExchangeOperationRecords";
	
	private static final String GET_EXCHANGE_PATIENT_IN_HOSPITAL= "getExchangePatientInHospitalRecords";
	
	private static final String GET_EXCHANGE_RESIDENT_RECORD= "getExchangeResidentRecords";
	
	@Override
	public List<PatientVO> getPatients(String departmentId, String doctorCode) {
		Map map = new HashMap();
		map.put("departmentId", departmentId);
		map.put("doctorCode", doctorCode);
		
		return sqlSessionTemplate.selectList(getSqlNameSpace(GET_PATIENTS), map);
	}

	@Override
	public List<DoctorVO> getDoctors(String departmentId,boolean isAll) {
		Map map = new HashMap();
		map.put("departmentId", departmentId);
		map.put("isAll", isAll);
		return sqlSessionTemplate.selectList(getSqlNameSpace(GET_DOCTORS), map);
	}

	@Override
	public List<ResidentRecord> getMedicalRecordByPatientIds(List<String> uids) {
		return sqlSessionTemplate.selectList(getSqlNameSpace(GET_MEDICAL_RECORDS_BY_PATIENT_IDS), uids);
	}
	
	
	@Override
	public List<DoctorVO> getAllDoctors(String departmentId) {
		Map map = new HashMap();
		map.put("departmentId", departmentId);
		return sqlSessionTemplate.selectList(getSqlNameSpace(GET_PATIENTS), map);
	}

	@Override
	public DoctorVO getDoctorById(String doctorId) {
		Map map = new HashMap();
		map.put("userId", doctorId);
		return sqlSessionTemplate.selectOne(getSqlNameSpace(GET_DOCTOR_BY_ID), map);
	}

	@Override
	public boolean updateDoctorLoginId(String doctorId, String loginId) {
		Map map = new HashMap();
		map.put("doctorId", doctorId);
		map.put("loginId", loginId);
		return sqlSessionTemplate.update(getSqlNameSpace(UPDATE_DOCTOR_LOGIN_ID), map) > 0;
	}

	@Override
	public List<DoctorVO> getDoctorByLoginId(String loginId) {
		Map map = new HashMap();
		map.put("loginId", loginId);
		return sqlSessionTemplate.selectList(getSqlNameSpace(GET_DOCTORS_BY_LOGIN_ID), map);
	}

	@Override
	public List<PatientInHospital> getExchangeOperationRecords(List<String> uids) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(getSqlNameSpace(GET_EXCHANGE_OPERATION_RECORDS), uids);
	}

	@Override
	public List<PatientInHospital> getExchangePatientInHospitalRecords(List<String> uids) {
		return sqlSessionTemplate.selectList(getSqlNameSpace(GET_EXCHANGE_PATIENT_IN_HOSPITAL), uids);
	}

	@Override
	public List<ResidentRecord> getExchangeResidentRecords(List<String> uids) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(getSqlNameSpace(GET_EXCHANGE_RESIDENT_RECORD), uids);
	}
	
}
