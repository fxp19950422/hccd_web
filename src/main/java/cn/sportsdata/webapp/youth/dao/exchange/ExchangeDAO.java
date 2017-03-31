package cn.sportsdata.webapp.youth.dao.exchange;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.sportsdata.webapp.youth.common.vo.patient.DoctorVO;
import cn.sportsdata.webapp.youth.common.vo.patient.MedicalRecordVO;
import cn.sportsdata.webapp.youth.common.vo.patient.PatientVO;

public interface ExchangeDAO {
	
	List<PatientVO> getPatients( String departmentId, 
			String doctorCode);
	
	List<DoctorVO> getDoctors(@Param("department_id") String departmentId);
	
	List<MedicalRecordVO> getMedicalRecordByPatientIds(List<String> uids);
}
