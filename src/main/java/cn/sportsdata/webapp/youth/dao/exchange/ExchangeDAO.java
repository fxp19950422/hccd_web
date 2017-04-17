package cn.sportsdata.webapp.youth.dao.exchange;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.sportsdata.webapp.youth.common.vo.patient.DoctorVO;
import cn.sportsdata.webapp.youth.common.vo.patient.PatientVO;
import cn.sportsdata.webapp.youth.common.vo.patient.ResidentRecord;

public interface ExchangeDAO {
	
	List<PatientVO> getPatients( String departmentId, 
			String doctorCode);
	
	List<DoctorVO> getDoctors(@Param("department_id") String departmentId, boolean isAll);
	
	List<ResidentRecord> getMedicalRecordByPatientIds(List<String> uids);
	
	List<DoctorVO> getAllDoctors(String departmentId); 
	
	DoctorVO getDoctorById(String doctorId);
	
	boolean updateDoctorLoginId(String doctorId, String loginId);
	
	List<DoctorVO> getDoctorByLoginId(String loginId);
}
