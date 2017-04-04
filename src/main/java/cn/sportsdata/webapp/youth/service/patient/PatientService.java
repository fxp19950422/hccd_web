package cn.sportsdata.webapp.youth.service.patient;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.sportsdata.webapp.youth.common.bo.hospital.PatientRecordBO;
import cn.sportsdata.webapp.youth.common.vo.patient.DoctorVO;
import cn.sportsdata.webapp.youth.common.vo.patient.RecordAssetTypeVO;
import cn.sportsdata.webapp.youth.common.vo.patient.RecordAssetVO;

public interface PatientService {
	List<PatientRecordBO> getMedicalRecordList(String hospitalId, String doctorCode, 
			String doctorName, long year, long month, long day);
	Map<String, Object> getOperationRecordList(String hospitalId, String doctorCode, 
			String doctorName, long year, long month, long day);
	Map<String, Object> getResidentRecordList(String hospitalId, String doctorCode, 
			String doctorName, long year, long month, long day);
	
	List<PatientRecordBO> searchPatientRecord(String hospitalId, String patientName, String doctorName, 
			String doctorCode, String recordType);
	
	List<RecordAssetVO> getRecordAssetList(String recordId);
	List<RecordAssetTypeVO> getRecordAssetTypeList(String recordType);
	DoctorVO getDoctorInfoByUsername(@Param("username") String username);
}
