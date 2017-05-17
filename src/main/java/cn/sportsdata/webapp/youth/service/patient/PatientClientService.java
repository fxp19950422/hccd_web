package cn.sportsdata.webapp.youth.service.patient;

import java.util.List;

import cn.sportsdata.webapp.youth.common.bo.hospital.PatientRecordBriefBO;
import cn.sportsdata.webapp.youth.common.bo.hospital.PatientRecordDetailBO;

public interface PatientClientService {

	List<PatientRecordBriefBO> getPatientBriefRecords(String userId, String hospitalId);

	PatientRecordDetailBO getPatientRecordDetail(String recordId, String type);
	
	
}
