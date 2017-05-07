package cn.sportsdata.webapp.youth.service.patient;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.sportsdata.webapp.youth.common.bo.hospital.PatientRecordBO;
import cn.sportsdata.webapp.youth.common.vo.patient.DoctorVO;
import cn.sportsdata.webapp.youth.common.vo.patient.MedicalRecordVO;
import cn.sportsdata.webapp.youth.common.vo.patient.OpertaionRecord;
import cn.sportsdata.webapp.youth.common.vo.patient.PatientDocumentVO;
import cn.sportsdata.webapp.youth.common.vo.patient.PatientInHospital;
import cn.sportsdata.webapp.youth.common.vo.patient.PatientInfoVO;
import cn.sportsdata.webapp.youth.common.vo.patient.PatientRegistRecord;
import cn.sportsdata.webapp.youth.common.vo.patient.RecordAssetTypeVO;
import cn.sportsdata.webapp.youth.common.vo.patient.RecordAssetVO;
import cn.sportsdata.webapp.youth.common.vo.patient.ResidentRecord;
import cn.sportsdata.webapp.youth.common.vo.patient.ShiftMeetingVO;

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
	
	List<MedicalRecordVO> getHospitalMedicalRecordList(String doctorId, String startDate, String endDate, String name, String idNumber, String hospitalId, String departCode);
	
	MedicalRecordVO getMedicalRecordById(String recordId);
	
	int updateMedicalRecordById( String recordId,  String illness_desc,
			String med_history,
			 String body_exam,
			 String diag_desc,
			 String treatment,
			 String suggestion);
	
	int updatePatientInHospital(PatientInHospital record);
	int updateMedicalRecord(MedicalRecordVO medicalRecordVO);
	int insertMedicalRecord(MedicalRecordVO medicalRecordVO);
	int updateOperationRecord(OpertaionRecord opertaionRecord);
	int updateResidentRecord(ResidentRecord residentRecord);
	
	OpertaionRecord getOperationRecordById(String id);
	
	public OpertaionRecord getOperationRecordByIdWithoutAssset(String id);
	ResidentRecord getResidentRecordById(String id);
	MedicalRecordVO getMedicalRecordVOById(String id);
	
	List<String> getDoctorDepartmentIdList(String doctorCode, String hospitalId);
	List<OpertaionRecord> getOperationsDuringInHospital(String recordId, String hospitalId, String patientId);
	List<ResidentRecord> getResidentDuringInHospital(String recordId, String hospitalId, String patientId);
	
	List<PatientRegistRecord> getRegisteRecordList(String hospitalId, String doctorCode,String patName, long year,long month,long day);
	PatientRegistRecord getRegisteRecordById(String id);
	
	List<PatientInfoVO> getPatients(List<String> patientIdList);

	List<OpertaionRecord> searchOperationRecordList(String hospitalId, String doctorCode, String name, long year,
			long month, long day);
	
	List<OpertaionRecord> searchDirectorOperationRecordList(String hospitalId, String departmentId, String name, long year,
			long month, long day);
	
	Boolean submitMeetingRecord(String recordType, String recordId, String patientInhospitalId, String hospitalId, String doctorId);

	List<PatientInHospital> searchInHospitalRecordList(String hospitalId, String doctorCode, String name, Date careStartTime, Date careEndTime);
	
	List<PatientInHospital> searchDirectorInHospitalRecordList(String hospitalId, String departmentId, String name, Date careStartTime, Date careEndTime);
	
	List<ResidentRecord> searchResidentRecordList(String hospitalId, String doctorCode, String name, Date careStartTime, Date careEndTime);
	
	List<ResidentRecord> searchDirectorResidentRecordList(String hospitalId, String departmentId, String name, Date careStartTime, Date careEndTime);
	
	PatientInHospital searchPatientInHospitalById(String recordId);

	List<ShiftMeetingVO> getTodayExchangeRecordList(int difference);
	boolean revertMeetingRecord(String recordId);
	List<PatientDocumentVO> getHistoryDocumentByPatientName(String patientName);
	
}
