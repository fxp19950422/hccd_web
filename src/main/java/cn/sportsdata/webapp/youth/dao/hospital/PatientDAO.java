package cn.sportsdata.webapp.youth.dao.hospital;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.sportsdata.webapp.youth.common.bo.hospital.PatientMedicalRecordBO;
import cn.sportsdata.webapp.youth.common.bo.hospital.PatientOperationRecordBO;
import cn.sportsdata.webapp.youth.common.bo.hospital.PatientRecordBO;
import cn.sportsdata.webapp.youth.common.bo.hospital.PatientResidentRecordBO;
import cn.sportsdata.webapp.youth.common.vo.AssetVO;

public interface PatientDAO {
	List<PatientRecordBO> getPatientRecordByDoctor(@Param("hospital_id") String hospitalId, 
			@Param("doctor_id") String doctorId, @Param("type") String recordType);
	
	PatientMedicalRecordBO getMedicalRecord(@Param("record_id") String recordId);
	PatientOperationRecordBO getOperationRecord(@Param("record_id") String recordId);
	PatientResidentRecordBO getResidentRecord(@Param("record_id") String recordId);
	List<AssetVO> getRecordAssetList(@Param("record_id") String recordId);
}
