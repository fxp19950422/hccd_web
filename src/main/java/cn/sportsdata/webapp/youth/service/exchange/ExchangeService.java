package cn.sportsdata.webapp.youth.service.exchange;

import java.util.List;

import cn.sportsdata.webapp.youth.common.vo.patient.DoctorVO;
import cn.sportsdata.webapp.youth.common.vo.patient.PatientVO;
import cn.sportsdata.webapp.youth.common.vo.patient.ResidentRecord;

public interface ExchangeService {
	List<PatientVO> getPatients(String departmentId, String doctorCode);
	
	List<DoctorVO> getDoctors(String departmentId, boolean isAll);
	
	public List<ResidentRecord> getMedicalRecordByPatientIds(List<String> uids);
	
	List<DoctorVO> getAllDoctors(String departmentId);
	
	
}
