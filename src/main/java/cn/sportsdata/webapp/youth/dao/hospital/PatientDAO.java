package cn.sportsdata.webapp.youth.dao.hospital;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.sportsdata.webapp.youth.common.bo.hospital.PatientMedicalRecordBO;
import cn.sportsdata.webapp.youth.common.bo.hospital.PatientOperationRecordBO;
import cn.sportsdata.webapp.youth.common.bo.hospital.PatientRecordBO;
import cn.sportsdata.webapp.youth.common.bo.hospital.PatientResidentRecordBO;
import cn.sportsdata.webapp.youth.common.vo.patient.DoctorVO;
import cn.sportsdata.webapp.youth.common.vo.patient.RecordAssetTypeVO;
import cn.sportsdata.webapp.youth.common.vo.patient.RecordAssetVO;

public interface PatientDAO {
	List<PatientRecordBO> getMedicalRecordList(@Param("hospital_id") String hospitalId, 
			@Param("doctor_code") String doctorCode);
	
	PatientMedicalRecordBO getMedicalRecord(@Param("record_id") String recordId);
	PatientOperationRecordBO getOperationRecord(@Param("record_id") String recordId);
	PatientResidentRecordBO getResidentRecord(@Param("record_id") String recordId);
	List<RecordAssetVO> getRecordAssetList(@Param("record_id") String recordId);

	List<RecordAssetTypeVO> getRecordAssetTypeList(@Param("record_type") String recordType);
	DoctorVO getDoctorInfoByUsername(@Param("username") String username);
}
