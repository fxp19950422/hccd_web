package cn.sportsdata.webapp.youth.dao.hospital;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.sportsdata.webapp.youth.common.bo.hospital.PatientRecordBriefBO;
import cn.sportsdata.webapp.youth.common.vo.patient.DoctorVO;
import cn.sportsdata.webapp.youth.common.vo.patient.MedicalRecordVO;
import cn.sportsdata.webapp.youth.common.vo.patient.OpertaionRecord;
import cn.sportsdata.webapp.youth.common.vo.patient.PatientInHospital;
import cn.sportsdata.webapp.youth.common.vo.patient.PatientInfoVO;
import cn.sportsdata.webapp.youth.common.vo.patient.PatientRegistRecord;
import cn.sportsdata.webapp.youth.common.vo.patient.RecordAssetTypeVO;
import cn.sportsdata.webapp.youth.common.vo.patient.RecordAssetVO;
import cn.sportsdata.webapp.youth.common.vo.patient.ResidentRecord;
import cn.sportsdata.webapp.youth.common.vo.patient.ShiftMeetingVO;

public interface PatientDAO {
	List<PatientRegistRecord> getRegisteRecordList(@Param("hospital_id") String hospitalId, 
			@Param("doctor_code") String doctorCode, @Param("year") long year, @Param("month") long month, @Param("day") long day);
	List<MedicalRecordVO> getMedicalRecordList(@Param("hospital_id") String hospitalId, 
			@Param("doctor_code") String doctorCode, @Param("year") long year, @Param("month") long month, @Param("day") long day);
	
	List<MedicalRecordVO> getHospitalMedicalRecordList(@Param("doctor_code") String doctorId, 
			@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("name") String name, @Param("idNumber") String idNumber, @Param("hospital_id") String hospitalId, @Param("depart_code") String departCode);
	
	//手术记录
	OpertaionRecord getOperationById(@Param("id") String recordId);
	OpertaionRecord getOperationByIdWithoutMapping(@Param("id") String recordId);
	List<OpertaionRecord> getCurMyOperationRecordList(@Param("hospital_id") String hospitalId, 
			@Param("doctor_name") String doctorName, @Param("doctor_code") String doctorCode);
	List<OpertaionRecord> getMyOperationRecordList(@Param("hospital_id") String hospitalId, 
			@Param("doctor_name") String doctorName, @Param("doctor_code") String doctorCode);
	List<OpertaionRecord> getFirstAsistOperationRecordList(@Param("hospital_id") String hospitalId, 
			@Param("doctor_name") String doctorName, @Param("year") long year, 
			@Param("month") long month, @Param("day") long day);
	List<OpertaionRecord> getSecondAsistOperationRecordList(@Param("hospital_id") String hospitalId, 
			@Param("doctor_name") String doctorName, @Param("year") long year, 
			@Param("month") long month, @Param("day") long day);
	List<OpertaionRecord> getAnesthesiaOperationRecordList(@Param("hospital_id") String hospitalId, 
			@Param("doctor_name") String doctorName, @Param("year") long year, 
			@Param("month") long month, @Param("day") long day);
	List<OpertaionRecord> getOperationsByResident(@Param("hospital_id") String hospitalId, 
			@Param("patient_id") String patientId, @Param("admission_date") Date admissionDate,
			 @Param("discharge_date") Date dischargeDate);

	//住院记录
	ResidentRecord getResidentById(@Param("id") String recordId);
	List<PatientInHospital> getCurPatientsInHospital(@Param("hospital_id") String hospitalId, 
			@Param("doctor_code") String doctorCode, @Param("departmentIdList") List<String> departmentIdList);
	List<OpertaionRecord> getOperationsDuringInHospital(@Param("hospital_id") String hospitalId, 
			@Param("patient_id") String patientId, @Param("record_id") String recordId);
	List<ResidentRecord> getResidentDuringInHospital(@Param("hospital_id") String hospitalId, 
			@Param("patient_id") String patientId, @Param("record_id") String recordId);
	
	//出院记录
	List<ResidentRecord> getResidentRecordByOperation(@Param("hospital_id") String hospitalId, 
			@Param("patient_id") String patientId, @Param("operating_date") Date operatingDate);
	
	List<ResidentRecord> getCurMyResidentRecordList(@Param("hospital_id") String hospitalId, 
			@Param("doctor_code") String doctorCode);
	List<ResidentRecord> getMyResidentRecordList(@Param("hospital_id") String hospitalId, 
			@Param("doctor_code") String doctorCode, @Param("year") long year, 
			@Param("month") long month, @Param("day") long day);
	List<ResidentRecord> getDirectResidentRecordList(@Param("hospital_id") String hospitalId, 
			@Param("doctor_name") String doctorName, @Param("year") long year, 
			@Param("month") long month, @Param("day") long day);
	List<ResidentRecord> getAttendingResidentRecordList(@Param("hospital_id") String hospitalId, 
			@Param("doctor_name") String doctorName, @Param("year") long year, 
			@Param("month") long month, @Param("day") long day);
	
	//按病人姓名搜索
	List<MedicalRecordVO> searchPatientMedicalRecord(
			@Param("hospital_id") String hospitalId, 
			@Param("patient_name") String patientName,
			@Param("doctor_name") String doctorName, 
			@Param("doctor_code") String doctorCode);
	List<OpertaionRecord> searchPatientOperationRecord(
			@Param("hospital_id") String hospitalId, 
			@Param("patient_name") String patientName,
			@Param("doctor_name") String doctorName, 
			@Param("doctor_code") String doctorCode);
	List<ResidentRecord> searchPatientResidentRecord(
			@Param("hospital_id") String hospitalId, 
			@Param("patient_name") String patientName,
			@Param("doctor_name") String doctorName, 
			@Param("doctor_code") String doctorCode);
	List<PatientInHospital> searchPatientInhospital(
			@Param("hospital_id") String hospitalId, 
			@Param("patient_name") String patientName,
			@Param("doctor_name") String doctorName, 
			@Param("doctor_code") String doctorCode);
	
	List<RecordAssetVO> getRecordAssetList(@Param("record_id") String recordId);

	List<RecordAssetTypeVO> getRecordAssetTypeList(@Param("record_type") String recordType);
	
	DoctorVO getDoctorInfoByUsername(@Param("username") String username);
	PatientInfoVO getPatientInfoVOById(@Param("patient_id") String patientId);
	List<PatientInfoVO> getPatients(@Param("patientIdList") List<String> patientIdList);
	
	List<ResidentRecord> getPatientResidentRecords(
			@Param("record_id") String recordId, @Param("patient_name") String patientName, 
			@Param("patient_id") String patientId, @Param("hospital_id") String hospitalId);
	List<MedicalRecordVO> getPatientMedicalRecords(
			@Param("record_id") String recordId, @Param("patient_name") String patientName, 
			@Param("patient_id") String patientId, @Param("hospital_id") String hospitalId);
	List<OpertaionRecord> getPatientOperationRecords(
			@Param("record_id") String recordId, @Param("patient_name") String patientName, 
			@Param("patient_id") String patientId, @Param("hospital_id") String hospitalId);
	
	MedicalRecordVO getMedicalRecordByID(
			@Param("record_id") String recordId);
	
	int updateMedicalRecordById(@Param("record_id") String recordId, @Param("illness_desc") String illness_desc,
			@Param("med_history") String med_history,
			@Param("body_exam") String body_exam,
			@Param("diag_desc") String diag_desc,
			@Param("treatment") String treatment,
			@Param("suggestion") String suggestion);
	
	int updateMedicalRecord(@Param("medicalRecordVO") MedicalRecordVO medicalRecordVO);
	int updatePatientInHospital(@Param("record") PatientInHospital record);
	int insertMedicalRecord(@Param("medicalRecordVO") MedicalRecordVO medicalRecordVO);
	int updateOperationRecord(@Param("opertaionRecord") OpertaionRecord opertaionRecord);
	int updateResidentRecord(@Param("residentRecord") ResidentRecord residentRecord);
	
	MedicalRecordVO getMedicalRecordById(@Param("id") String recordId);
	PatientRegistRecord getRegisteRecordById(@Param("id") String recordId);
	
	List<PatientRegistRecord> searchRegisteRecordList(@Param("hospital_id") String hospitalId,
			@Param("doctor_code") String doctorCode, @Param("patName") String name, @Param("year") long year,
			@Param("month") long month, @Param("day") long day);
	
	List<OpertaionRecord> searchOperationRecordList(@Param("hospital_id") String hospitalId,
			@Param("doctor_code") String doctorCode, @Param("patName") String name, @Param("year") long year,
			@Param("month") long month, @Param("day") long day);
	
	List<OpertaionRecord> searchDirectorOperationRecordList(@Param("hospital_id") String hospitalId,
			@Param("departmentId") String departmentId, @Param("patName") String name, @Param("year") long year,
			@Param("month") long month, @Param("day") long day);

	int saveShiftMeetingRecords(@Param("hospital_id") String hospitalId,
			@Param("doctor_id") String doctorId, @Param("record_id") String recordId,
			@Param("record_type") String recordType);
	boolean deleteShiftMeetingRecord(@Param("record_id") String recordId);
	List<ShiftMeetingVO> getTodayMeetingRecords(@Param("diffDays") int difference);
	int updatePatientRecentMeetingRecord(@Param("record_id") String recordId, @Param("record_type") String recordType);

	List<PatientInHospital> searchInHospitalRecordList(@Param("hospital_id") String hospitalId,
			@Param("doctor_code") String doctorCode, @Param("patName") String name, @Param("careStartTime") Date careStartTime,
			@Param("careEndTime") Date careEndTime);
	
	List<PatientInHospital> searchDirectorInHospitalRecordList(@Param("hospital_id") String hospitalId,
			@Param("departmentId") String departmentId, @Param("patName") String name, @Param("careStartTime") Date careStartTime,
			@Param("careEndTime") Date careEndTime);
	
	List<ResidentRecord> searchResidentRecordList(@Param("hospital_id") String hospitalId,
			@Param("doctor_code") String doctorCode, @Param("patName") String name, @Param("careStartTime") Date careStartTime,
			@Param("careEndTime") Date careEndTime);
	
	List<ResidentRecord> searchDirectorResidentRecordList(@Param("hospital_id") String hospitalId,
			@Param("departmentId") String departmentId, @Param("patName") String name, @Param("careStartTime") Date careStartTime,
			@Param("careEndTime") Date careEndTime);
	
	PatientInHospital searchPatientInHospitalById(@Param("recordId") String recordId);
	
	List<PatientRecordBriefBO> getPatientBriefRecordsByHospital(@Param("userId") String userId, @Param("hospitalId") String hospitalId);
	
}
