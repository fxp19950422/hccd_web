package cn.sportsdata.webapp.youth.service.exchange;

import java.util.List;

import cn.sportsdata.webapp.youth.common.vo.patient.DoctorVO;
import cn.sportsdata.webapp.youth.common.vo.patient.MedicalRecordVO;
import cn.sportsdata.webapp.youth.common.vo.patient.PatientVO;

public interface ExchangeService {
	List<PatientVO> getPatients(String departmentId, String doctorCode);
	
	List<DoctorVO> getDoctors(String departmentId);
	
	public List<MedicalRecordVO> getMedicalRecordByPatientIds(List<String> uids);
	
	
}
