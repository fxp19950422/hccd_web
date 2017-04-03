package cn.sportsdata.webapp.youth.service.patient.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.sportsdata.webapp.youth.common.bo.hospital.PatientMedicalRecordBO;
import cn.sportsdata.webapp.youth.common.bo.hospital.PatientOperationRecordBO;
import cn.sportsdata.webapp.youth.common.bo.hospital.PatientRecordBO;
import cn.sportsdata.webapp.youth.common.bo.hospital.PatientResidentRecordBO;
import cn.sportsdata.webapp.youth.common.vo.patient.DoctorVO;
import cn.sportsdata.webapp.youth.common.vo.patient.MedicalRecordVO;
import cn.sportsdata.webapp.youth.common.vo.patient.OpertaionRecord;
import cn.sportsdata.webapp.youth.common.vo.patient.PatientInfoVO;
import cn.sportsdata.webapp.youth.common.vo.patient.PatientRegistRecord;
import cn.sportsdata.webapp.youth.common.vo.patient.RecordAssetTypeVO;
import cn.sportsdata.webapp.youth.common.vo.patient.RecordAssetVO;
import cn.sportsdata.webapp.youth.common.vo.patient.ResidentRecord;
import cn.sportsdata.webapp.youth.dao.asset.AssetDAO;
import cn.sportsdata.webapp.youth.dao.hospital.PatientDAO;
import cn.sportsdata.webapp.youth.service.patient.PatientService;

@Service
public class PatientServiceImpl implements PatientService {
	private static Logger logger = Logger.getLogger(PatientServiceImpl.class);
	@Autowired
	private PatientDAO patientDAO;
	
	@Autowired
	private AssetDAO assetDAO;
	
	@Override
	public List<PatientRecordBO> getMedicalRecordList(String hospitalId, String doctorCode, String date) {
		List<PatientRecordBO> patientRecordList = new ArrayList<PatientRecordBO>();
		try {
			List<MedicalRecordVO> medicalRecordList = patientDAO.getMedicalRecordList(hospitalId, doctorCode, date);
			List<PatientRegistRecord> registeRecordList = patientDAO.getRegisteRecordList(hospitalId, doctorCode, date);
			List<String> patientIdList = new ArrayList<String>();
			for (MedicalRecordVO record:medicalRecordList) {
				PatientRecordBO patientRecord = new PatientRecordBO();
				patientRecord.setMedicalRecord(record);
				patientRecord.setPatientId(record.getPatientId());
				patientIdList.add(record.getPatientId());
				patientRecordList.add(patientRecord);
			}
			
			for (PatientRegistRecord record:registeRecordList) {
				String patientId = record.getPatientId();
				if (patientIdList.contains(patientId)) {
					for (PatientRecordBO patientRecord:patientRecordList) {
						if (patientRecord.getMedicalRecord().getPatientId().equalsIgnoreCase(patientId)) {
							patientRecord.setRegistRecord(record);
						}
					}
				} else {
					PatientRecordBO patientRecord = new PatientRecordBO();
					patientRecord.setRegistRecord(record);
					patientIdList.add(record.getPatientId());
					patientRecord.setPatientId(record.getPatientId());
					patientRecordList.add(patientRecord);
				}
			}
			
			if (!patientIdList.isEmpty()) {
				List<PatientInfoVO> patientList = patientDAO.getPatients(patientIdList);
				for (PatientRecordBO patientRecord:patientRecordList) {
					for (PatientInfoVO patient:patientList) {
						if (patient.getPatientNumber().equalsIgnoreCase(patientRecord.getPatientId())) {
							patientRecord.setPatient(patient);
							break;
						}
					}
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			return patientRecordList;
		}
		return patientRecordList;
	}

	@Override
	public PatientMedicalRecordBO getMedicalRecord(String recordId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PatientOperationRecordBO getOperationRecord(String recordId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PatientResidentRecordBO getResidentRecord(String recordId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RecordAssetVO> getRecordAssetList(String recordId) {
		List<RecordAssetVO> assetTypeList = null;
		try {
			assetTypeList = patientDAO.getRecordAssetList(recordId);
			if(assetTypeList == null) {
				assetTypeList = new ArrayList<RecordAssetVO>();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			assetTypeList = new ArrayList<RecordAssetVO>();
		}
		return assetTypeList;
	}

	@Override
	public List<RecordAssetTypeVO> getRecordAssetTypeList(String recordType) {
		List<RecordAssetTypeVO> assetTypeList = null;
		try {
			assetTypeList = patientDAO.getRecordAssetTypeList(recordType);
			if(assetTypeList == null) {
				assetTypeList = new ArrayList<RecordAssetTypeVO>();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			assetTypeList = new ArrayList<RecordAssetTypeVO>();
		}
		return assetTypeList;
	}

	@Override
	public DoctorVO getDoctorInfoByUsername(String username) {
		return patientDAO.getDoctorInfoByUsername(username);
	}

	@Override
	public Map<String, Object> getOperationRecordList(String hospitalId, String doctorCode, String doctorName,
			String date) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			List<OpertaionRecord> operationList = patientDAO.getMyOperationRecordList(hospitalId, doctorName, doctorCode, date);
			result.put("operationList", operationList);
			List<OpertaionRecord> firstAssitList = patientDAO.getFirstAsistOperationRecordList(hospitalId, doctorName, date);
			result.put("firstAssitList", firstAssitList);
			List<OpertaionRecord> secondAssitList = patientDAO.getSecondAsistOperationRecordList(hospitalId, doctorName, date);
			result.put("secondAssitList", secondAssitList);
			List<OpertaionRecord> anesthesiaList = patientDAO.getAnesthesiaOperationRecordList(hospitalId, doctorName, date);
			result.put("anesthesiaList", anesthesiaList);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Map<String, Object> getResidentRecordList(String hospitalId, String doctorCode, String doctorName,
			String date) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			List<ResidentRecord> operationList = patientDAO.getMyResidentRecordList(hospitalId, doctorCode, date);
			result.put("operationList", operationList);
			List<ResidentRecord> directList = patientDAO.getDirectResidentRecordList(hospitalId, doctorName, date);
			result.put("directList", directList);
			List<ResidentRecord> attendingList = patientDAO.getAttendingResidentRecordList(hospitalId, doctorName, date);
			result.put("attendingList", attendingList);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
