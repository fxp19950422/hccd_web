package cn.sportsdata.webapp.youth.service.patient;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.sportsdata.webapp.youth.common.bo.hospital.PatientRecordBO;
import cn.sportsdata.webapp.youth.common.vo.patient.DoctorVO;
import cn.sportsdata.webapp.youth.common.vo.patient.MedicalRecordVO;
import cn.sportsdata.webapp.youth.common.vo.patient.OpertaionRecord;
import cn.sportsdata.webapp.youth.common.vo.patient.RecordAssetTypeVO;
import cn.sportsdata.webapp.youth.common.vo.patient.RecordAssetVO;
import cn.sportsdata.webapp.youth.common.vo.patient.ResidentRecord;

public interface PatientService {
	List<PatientRecordBO> getMedicalRecordList(String hospitalId, String doctorCode, 
			String doctorName, long year, long month, long day);
	Map<String, Object> getOperationRecordList(String hospitalId, String doctorCode, 
			String doctorName, long year, long month, long day);
	Map<String, Object> getResidentRecordList(String hospitalId, String doctorCode, 
			String doctorName, long year, long month, long day);
	List<PatientRecordBO> getPatientInHospital(String hospitalId, String doctorCode, 
			String doctorName, long year, long month, long day);
	
	List<PatientRecordBO> searchPatientRecord(String hospitalId, String patientName, String doctorName, 
			String doctorCode, String recordType);
	
	List<RecordAssetVO> getRecordAssetList(String recordId);
	List<RecordAssetTypeVO> getRecordAssetTypeList(String recordType);
	DoctorVO getDoctorInfoByUsername(String username);
	List<PatientRecordBO> getPatientRecords(String recordId, String patientName, String patientId, String hospitalId);
	ResidentRecord getResidentRecordByOperation(String recordId, String hospitalId, String patientId);
	List<OpertaionRecord> getOperationsByResident(String recordId, String hospitalId, String patientId);
	
	int updateMedicalRecord(MedicalRecordVO medicalRecordVO);
	int updateOperationRecord(OpertaionRecord opertaionRecord);
	int updateResidentRecord(ResidentRecord residentRecord);
}
