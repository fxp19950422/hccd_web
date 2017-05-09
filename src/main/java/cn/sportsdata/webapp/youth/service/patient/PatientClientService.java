package cn.sportsdata.webapp.youth.service.patient;

import java.util.List;

import cn.sportsdata.webapp.youth.common.bo.hospital.PatientRecordBriefBO;

public interface PatientClientService {

	List<PatientRecordBriefBO> getPatientBriefRecords(String userId, String hospitalId);
	
	
}
