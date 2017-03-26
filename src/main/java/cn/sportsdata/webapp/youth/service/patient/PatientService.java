package cn.sportsdata.webapp.youth.service.patient;

import java.util.List;

import cn.sportsdata.webapp.youth.common.bo.hospital.PatientMedicalRecordBO;
import cn.sportsdata.webapp.youth.common.bo.hospital.PatientOperationRecordBO;
import cn.sportsdata.webapp.youth.common.bo.hospital.PatientRecordBO;
import cn.sportsdata.webapp.youth.common.bo.hospital.PatientResidentRecordBO;
import cn.sportsdata.webapp.youth.common.vo.AssetVO;

public interface PatientService {
	List<PatientRecordBO> getPatientRecordByDoctor(String hospitalId, String doctorId, String recordType);
	PatientMedicalRecordBO getMedicalRecord(String recordId);
	PatientOperationRecordBO getOperationRecord(String recordId);
	PatientResidentRecordBO getResidentRecord(String recordId);
	List<AssetVO> getRecordAssetList(String recordId);
}
