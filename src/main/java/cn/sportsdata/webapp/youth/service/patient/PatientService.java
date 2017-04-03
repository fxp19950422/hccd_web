package cn.sportsdata.webapp.youth.service.patient;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.sportsdata.webapp.youth.common.bo.hospital.PatientMedicalRecordBO;
import cn.sportsdata.webapp.youth.common.bo.hospital.PatientOperationRecordBO;
import cn.sportsdata.webapp.youth.common.bo.hospital.PatientRecordBO;
import cn.sportsdata.webapp.youth.common.bo.hospital.PatientResidentRecordBO;
import cn.sportsdata.webapp.youth.common.vo.patient.DoctorVO;
import cn.sportsdata.webapp.youth.common.vo.patient.RecordAssetTypeVO;
import cn.sportsdata.webapp.youth.common.vo.patient.RecordAssetVO;

public interface PatientService {
	List<PatientRecordBO> getMedicalRecordList(String hospitalId, String doctorCode, String date);
	Map<String, Object> getOperationRecordList(String hospitalId, String doctorCode, 
			String doctorName, String date);
	Map<String, Object> getResidentRecordList(String hospitalId, String doctorCode, 
			String doctorName, String date);
	
	PatientMedicalRecordBO getMedicalRecord(String recordId);
	PatientOperationRecordBO getOperationRecord(String recordId);
	PatientResidentRecordBO getResidentRecord(String recordId);
	
	List<RecordAssetVO> getRecordAssetList(String recordId);
	List<RecordAssetTypeVO> getRecordAssetTypeList(String recordType);
	DoctorVO getDoctorInfoByUsername(@Param("username") String username);
}
