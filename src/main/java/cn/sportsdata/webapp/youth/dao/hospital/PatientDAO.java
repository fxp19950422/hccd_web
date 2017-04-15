package cn.sportsdata.webapp.youth.dao.hospital;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.sportsdata.webapp.youth.common.vo.patient.DoctorVO;
import cn.sportsdata.webapp.youth.common.vo.patient.MedicalRecordVO;
import cn.sportsdata.webapp.youth.common.vo.patient.OpertaionRecord;
import cn.sportsdata.webapp.youth.common.vo.patient.PatientInHospital;
import cn.sportsdata.webapp.youth.common.vo.patient.PatientInfoVO;
import cn.sportsdata.webapp.youth.common.vo.patient.PatientRegistRecord;
import cn.sportsdata.webapp.youth.common.vo.patient.RecordAssetTypeVO;
import cn.sportsdata.webapp.youth.common.vo.patient.RecordAssetVO;
import cn.sportsdata.webapp.youth.common.vo.patient.ResidentRecord;

public interface PatientDAO {
	List<PatientRegistRecord> getRegisteRecordList(@Param("hospital_id") String hospitalId, 
			@Param("doctor_code") String doctorCode, @Param("year") long year, @Param("month") long month, @Param("day") long day);
	List<MedicalRecordVO> getMedicalRecordList(@Param("hospital_id") String hospitalId, 
			@Param("doctor_code") String doctorCode, @Param("year") long year, @Param("month") long month, @Param("day") long day);
	
	//手术记录
	OpertaionRecord getOperationById(@Param("id") String recordId);
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
	
	//
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
	
	
	int updateMedicalRecord(@Param("medicalRecordVO") MedicalRecordVO medicalRecordVO);
	int updateOperationRecord(@Param("opertaionRecord") OpertaionRecord opertaionRecord);
	int updateResidentRecord(@Param("residentRecord") ResidentRecord residentRecord);
	
	MedicalRecordVO getMedicalRecordById(@Param("id") String recordId);
}
