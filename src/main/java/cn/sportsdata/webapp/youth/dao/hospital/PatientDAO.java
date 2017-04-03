package cn.sportsdata.webapp.youth.dao.hospital;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.sportsdata.webapp.youth.common.vo.patient.DoctorVO;
import cn.sportsdata.webapp.youth.common.vo.patient.MedicalRecordVO;
import cn.sportsdata.webapp.youth.common.vo.patient.OpertaionRecord;
import cn.sportsdata.webapp.youth.common.vo.patient.PatientInfoVO;
import cn.sportsdata.webapp.youth.common.vo.patient.PatientRegistRecord;
import cn.sportsdata.webapp.youth.common.vo.patient.RecordAssetTypeVO;
import cn.sportsdata.webapp.youth.common.vo.patient.RecordAssetVO;
import cn.sportsdata.webapp.youth.common.vo.patient.ResidentRecord;

public interface PatientDAO {
	List<PatientRegistRecord> getRegisteRecordList(@Param("hospital_id") String hospitalId, 
			@Param("doctor_code") String doctorCode, @Param("date") String date);
	List<MedicalRecordVO> getMedicalRecordList(@Param("hospital_id") String hospitalId, 
			@Param("doctor_code") String doctorCode, @Param("date") String date);
	
	//手术记录
	List<OpertaionRecord> getMyOperationRecordList(@Param("hospital_id") String hospitalId, 
			@Param("doctor_name") String doctorName, @Param("doctor_code") String doctorCode, @Param("date") String date);
	List<OpertaionRecord> getFirstAsistOperationRecordList(@Param("hospital_id") String hospitalId, 
			@Param("doctor_name") String doctorName, @Param("date") String date);
	List<OpertaionRecord> getSecondAsistOperationRecordList(@Param("hospital_id") String hospitalId, 
			@Param("doctor_name") String doctorName, @Param("date") String date);
	List<OpertaionRecord> getAnesthesiaOperationRecordList(@Param("hospital_id") String hospitalId, 
			@Param("doctor_name") String doctorName, @Param("date") String date);
	
	//住院记录
	List<ResidentRecord> getMyResidentRecordList(@Param("hospital_id") String hospitalId, 
			@Param("doctor_code") String doctorCode, @Param("date") String date);
	List<ResidentRecord> getDirectResidentRecordList(@Param("hospital_id") String hospitalId, 
			@Param("doctor_name") String doctorName, @Param("date") String date);
	List<ResidentRecord> getAttendingResidentRecordList(@Param("hospital_id") String hospitalId, 
			@Param("doctor_name") String doctorName, @Param("date") String date);	
	
	List<RecordAssetVO> getRecordAssetList(@Param("record_id") String recordId);

	List<RecordAssetTypeVO> getRecordAssetTypeList(@Param("record_type") String recordType);
	
	
	DoctorVO getDoctorInfoByUsername(@Param("username") String username);
	List<PatientInfoVO> getPatients(@Param("patientIdList") List<String> patientIdList);
}
