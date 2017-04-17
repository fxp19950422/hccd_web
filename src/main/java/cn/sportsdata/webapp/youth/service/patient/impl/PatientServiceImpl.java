package cn.sportsdata.webapp.youth.service.patient.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.sportsdata.webapp.youth.common.bo.hospital.PatientRecordBO;
import cn.sportsdata.webapp.youth.common.vo.patient.DoctorVO;
import cn.sportsdata.webapp.youth.common.vo.patient.MedicalRecordVO;
import cn.sportsdata.webapp.youth.common.vo.patient.OpertaionRecord;
import cn.sportsdata.webapp.youth.common.vo.patient.PatientInHospital;
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
	public List<PatientRecordBO> getMedicalRecordList(String hospitalId, String doctorCode, 
			String doctorName, long year, long month, long day) {
		List<PatientRecordBO> patientRecordList = new ArrayList<PatientRecordBO>();
		try {
			List<MedicalRecordVO> medicalRecordList = patientDAO.getMedicalRecordList(hospitalId, doctorCode, year, month, day);
			List<PatientRegistRecord> registeRecordList = patientDAO.getRegisteRecordList(hospitalId, doctorCode, year, month, day);
			List<String> patientIdList = new ArrayList<String>();
			for (MedicalRecordVO record:medicalRecordList) {
				PatientRecordBO patientRecord = new PatientRecordBO();
				patientRecord.setRecordType("medical");
				patientRecord.setMedicalRecord(record);
				patientRecord.setPatientId(record.getPatientId());
				patientIdList.add(record.getPatientId());
				patientRecordList.add(patientRecord);
			}
			
			for (PatientRegistRecord record:registeRecordList) {
				String patientId = record.getPatientId();
				if (patientIdList.contains(patientId)) {
					for (PatientRecordBO patientRecord:patientRecordList) {
						if (patientRecord.getMedicalRecord()!=null&&patientRecord.getMedicalRecord().getPatientId().equalsIgnoreCase(patientId)) {
							patientRecord.setRegistRecord(record);
						}
					}
				} else {
					PatientRecordBO patientRecord = new PatientRecordBO();
					patientRecord.setRecordType("medical");
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
			long year, long month, long day) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			List<PatientRecordBO> patientRecordList1 = new ArrayList<PatientRecordBO>();
			List<OpertaionRecord> operationList = patientDAO.getMyOperationRecordList(hospitalId, doctorName, doctorCode);
			List<String> patientIdList = new ArrayList<String>();
			for (OpertaionRecord operationRecord:operationList) {
				PatientRecordBO patientRecord = new PatientRecordBO();
				patientRecord.setRecordType("operation");
				patientRecord.setOperationRecord(operationRecord);
				patientRecord.setPatientId(operationRecord.getPatientId());
				patientIdList.add(patientRecord.getPatientId());
				patientRecordList1.add(patientRecord);
			}
			
			List<PatientRecordBO> patientRecordList2 = new ArrayList<PatientRecordBO>();
			List<OpertaionRecord> firstAssitList = patientDAO.getFirstAsistOperationRecordList(hospitalId, doctorName, year, month, day);
			for (OpertaionRecord operationRecord:firstAssitList) {
				PatientRecordBO patientRecord = new PatientRecordBO();
				patientRecord.setRecordType("operation");
				patientRecord.setOperationRecord(operationRecord);
				patientRecord.setPatientId(operationRecord.getPatientId());
				patientIdList.add(patientRecord.getPatientId());
				patientRecordList2.add(patientRecord);
			}
			
			List<PatientRecordBO> patientRecordList3 = new ArrayList<PatientRecordBO>();
			List<OpertaionRecord> secondAssitList = patientDAO.getSecondAsistOperationRecordList(hospitalId, doctorName, year, month, day);
			for (OpertaionRecord operationRecord:secondAssitList) {
				PatientRecordBO patientRecord = new PatientRecordBO();
				patientRecord.setRecordType("operation");
				patientRecord.setOperationRecord(operationRecord);
				patientRecord.setPatientId(operationRecord.getPatientId());
				patientIdList.add(patientRecord.getPatientId());
				patientRecordList3.add(patientRecord);
			}
			
			List<PatientRecordBO> patientRecordList4 = new ArrayList<PatientRecordBO>();
			List<OpertaionRecord> anesthesiaList = patientDAO.getAnesthesiaOperationRecordList(hospitalId, doctorName, year, month, day);
			for (OpertaionRecord operationRecord:anesthesiaList) {
				PatientRecordBO patientRecord = new PatientRecordBO();
				patientRecord.setRecordType("operation");
				patientRecord.setOperationRecord(operationRecord);
				patientRecord.setPatientId(operationRecord.getPatientId());
				patientIdList.add(patientRecord.getPatientId());
				patientRecordList4.add(patientRecord);
			}
			
			if (!patientIdList.isEmpty()) {
				List<PatientInfoVO> patientList = patientDAO.getPatients(patientIdList);

				for (PatientInfoVO patient:patientList) {
					for (PatientRecordBO patientRecord:patientRecordList1) {
						if (patient.getPatientNumber().equalsIgnoreCase(patientRecord.getPatientId())) {
							patientRecord.setPatient(patient);
							break;
						}
					}
					for (PatientRecordBO patientRecord:patientRecordList2) {
						if (patient.getPatientNumber().equalsIgnoreCase(patientRecord.getPatientId())) {
							patientRecord.setPatient(patient);
							break;
						}
					}
					for (PatientRecordBO patientRecord:patientRecordList3) {
						if (patient.getPatientNumber().equalsIgnoreCase(patientRecord.getPatientId())) {
							patientRecord.setPatient(patient);
							break;
						}
					}
					for (PatientRecordBO patientRecord:patientRecordList4) {
						if (patient.getPatientNumber().equalsIgnoreCase(patientRecord.getPatientId())) {
							patientRecord.setPatient(patient);
							break;
						}
					}
				}
			}
			
			result.put("operationList", patientRecordList1);
			result.put("firstAssitList", patientRecordList2);
			result.put("secondAssitList", patientRecordList3);
			result.put("anesthesiaList", patientRecordList4);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Map<String, Object> getResidentRecordList(String hospitalId, String doctorCode, String doctorName,
			long year, long month, long day) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			/*
			List<PatientRecordBO> chargedPatientList = new ArrayList<PatientRecordBO>();
			List<PatientInHospital> patientInHosList = patientDAO.getCurPatientsInHospital(hospitalId, doctorCode);
			List<ResidentRecord> chargedList = patientDAO.getCurMyResidentRecordList(hospitalId, doctorCode);
			List<String> patientIdList = new ArrayList<String>();
			for (PatientInHospital patientInhospital:patientInHosList) {
				PatientRecordBO patientRecord = new PatientRecordBO();
				patientRecord.setRecordType("resident");
				patientRecord.setPatientInhospital(patientInhospital);
				patientRecord.setPatientId(patientInhospital.getPatientId());
				patientIdList.add(patientRecord.getPatientId());
				
				for (ResidentRecord residentRecord:chargedList) {
					if (residentRecord.getPatientId().equalsIgnoreCase(patientInhospital.getPatientId())) {
						patientRecord.setResidentRecord(residentRecord);
						chargedList.remove(residentRecord);
						break;
					}
				}
				
				chargedPatientList.add(patientRecord);
			}
			*/
			List<PatientRecordBO> patientRecordList1 = new ArrayList<PatientRecordBO>();
			List<ResidentRecord> chargedList = patientDAO.getMyResidentRecordList(hospitalId, doctorCode, year, month, day);
			List<String> patientIdList = new ArrayList<String>();
			for (ResidentRecord residentRecord:chargedList) {
				PatientRecordBO patientRecord = new PatientRecordBO();
				patientRecord.setRecordType("resident");
				patientRecord.setResidentRecord(residentRecord);
				patientRecord.setPatientId(residentRecord.getPatientId());
				patientIdList.add(patientRecord.getPatientId());
				patientRecordList1.add(patientRecord);
			}

			List<PatientRecordBO> patientRecordList2 = new ArrayList<PatientRecordBO>();
			List<ResidentRecord> directList = patientDAO.getDirectResidentRecordList(hospitalId, doctorName, year, month, day);
			for (ResidentRecord residentRecord:directList) {
				PatientRecordBO patientRecord = new PatientRecordBO();
				patientRecord.setRecordType("resident");
				patientRecord.setResidentRecord(residentRecord);
				patientRecord.setPatientId(residentRecord.getPatientId());
				patientIdList.add(patientRecord.getPatientId());
				patientRecordList2.add(patientRecord);
			}
			
			List<PatientRecordBO> patientRecordList3 = new ArrayList<PatientRecordBO>();
			List<ResidentRecord> attendingList = patientDAO.getAttendingResidentRecordList(hospitalId, doctorName, year, month, day);
			for (ResidentRecord residentRecord:attendingList) {
				PatientRecordBO patientRecord = new PatientRecordBO();
				patientRecord.setRecordType("resident");
				patientRecord.setResidentRecord(residentRecord);
				patientRecord.setPatientId(residentRecord.getPatientId());
				patientIdList.add(patientRecord.getPatientId());
				patientRecordList3.add(patientRecord);
			}
			
			if (!patientIdList.isEmpty()) {
				List<PatientInfoVO> patientList = patientDAO.getPatients(patientIdList);

				for (PatientInfoVO patient:patientList) {
					for (PatientRecordBO patientRecord:patientRecordList1) {
						if (patient.getPatientNumber().equalsIgnoreCase(patientRecord.getPatientId())) {
							patientRecord.setPatient(patient);
							break;
						}
					}
					for (PatientRecordBO patientRecord:patientRecordList2) {
						if (patient.getPatientNumber().equalsIgnoreCase(patientRecord.getPatientId())) {
							patientRecord.setPatient(patient);
							break;
						}
					}
					for (PatientRecordBO patientRecord:patientRecordList3) {
						if (patient.getPatientNumber().equalsIgnoreCase(patientRecord.getPatientId())) {
							patientRecord.setPatient(patient);
							break;
						}
					}
				}
			}
			
			result.put("directList", patientRecordList2);
			result.put("operationList", patientRecordList1);
			result.put("attendingList", patientRecordList3);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<PatientRecordBO> searchPatientRecord(String hospitalId, String patientName, String doctorName,
			String doctorCode, String recordType) {
		List<PatientRecordBO> patientRecordList = new ArrayList<PatientRecordBO>();
		List<String> patientIdList = new ArrayList<String>();
		
		if (recordType.equalsIgnoreCase("medical")) {
			List<MedicalRecordVO> list = patientDAO.searchPatientMedicalRecord(hospitalId, patientName, doctorName, doctorCode);
			for (MedicalRecordVO record:list) {
				PatientRecordBO patientRecord = new PatientRecordBO();
				patientRecord.setRecordType("medical");
				patientRecord.setMedicalRecord(record);
				patientRecord.setPatientId(record.getPatientId());
				patientRecordList.add(patientRecord);
				patientIdList.add(record.getPatientId());
			}
		}
		if (recordType.equalsIgnoreCase("operation")) {
			List<OpertaionRecord> list = patientDAO.searchPatientOperationRecord(hospitalId, patientName, doctorName, doctorCode);
			for (OpertaionRecord record:list) {
				PatientRecordBO patientRecord = new PatientRecordBO();
				patientRecord.setRecordType("operation");
				patientRecord.setOperationRecord(record);
				patientRecord.setPatientId(record.getPatientId());
				patientRecordList.add(patientRecord);
				patientIdList.add(record.getPatientId());
			}
		}
		if (recordType.equalsIgnoreCase("resident")) {
			List<ResidentRecord> list = patientDAO.searchPatientResidentRecord(hospitalId, patientName, doctorName, doctorCode);
			for (ResidentRecord record:list) {
				PatientRecordBO patientRecord = new PatientRecordBO();
				patientRecord.setRecordType("resident");
				patientRecord.setResidentRecord(record);
				patientRecord.setPatientId(record.getPatientId());
				patientRecordList.add(patientRecord);
				patientIdList.add(record.getPatientId());
			}
		}
		
		if (!patientIdList.isEmpty()) {
			List<PatientInfoVO> patientList = patientDAO.getPatients(patientIdList);

			for (PatientInfoVO patient:patientList) {
				for (PatientRecordBO patientRecord:patientRecordList) {
					if (patient.getPatientNumber().equalsIgnoreCase(patientRecord.getPatientId())) {
						patientRecord.setPatient(patient);
						break;
					}
				}
			}
		}
		
		return patientRecordList;
	}

	@Override
	public List<PatientRecordBO> getPatientRecords(String recordId, String patientName, String patientId, String hospitalId) {
		try {
			List<MedicalRecordVO> medicalRecords = patientDAO.getPatientMedicalRecords(recordId, patientName, patientId, hospitalId);
			List<ResidentRecord> residentRecords = patientDAO.getPatientResidentRecords(recordId, patientName, patientId, hospitalId);
			List<OpertaionRecord> operationRecords = patientDAO.getPatientOperationRecords(recordId, patientName, patientId, hospitalId);
			List<PatientRecordBO> resultList = new ArrayList<PatientRecordBO>();
			for (MedicalRecordVO record:medicalRecords) {
				PatientRecordBO patientRecord = new PatientRecordBO();
				patientRecord.setRecordType("medical");
				patientRecord.setMedicalRecord(record);
				patientRecord.setPatientId(record.getPatientId());
				resultList.add(patientRecord);
			}
			for (ResidentRecord record:residentRecords) {
				PatientRecordBO patientRecord = new PatientRecordBO();
				patientRecord.setRecordType("resident");
				patientRecord.setResidentRecord(record);
				patientRecord.setPatientId(record.getPatientId());
				resultList.add(patientRecord);
			}
			for (OpertaionRecord record:operationRecords) {
				PatientRecordBO patientRecord = new PatientRecordBO();
				patientRecord.setRecordType("operation");
				patientRecord.setOperationRecord(record);
				patientRecord.setPatientId(record.getPatientId());
				resultList.add(patientRecord);
			}
			return resultList;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<PatientRecordBO> getPatientInHospital(String hospitalId, String doctorCode, String doctorName,
			long year, long month, long day) {
		List<PatientRecordBO> patientRecordList = new ArrayList<PatientRecordBO>();
		try {
			List<String> departmentIdList = this.getDoctorDepartmentIdList(doctorCode, hospitalId);
			List<PatientInHospital> list = patientDAO.getCurPatientsInHospital(hospitalId, doctorCode, departmentIdList);
			List<String> patientIdList = new ArrayList<String>();
			for (PatientInHospital record:list) {
				PatientRecordBO patientRecord = new PatientRecordBO();
				patientRecord.setRecordType("patientInhospital");
				patientRecord.setPatientInhospital(record);
				patientRecord.setPatientId(record.getPatientId());
				patientIdList.add(record.getPatientId());
				patientRecordList.add(patientRecord);
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
		}
		return patientRecordList;
	}

	@Override
	public ResidentRecord getResidentRecordByOperation(String recordId, String hospitalId, String patientId) {
		try {
			OpertaionRecord operation = patientDAO.getOperationById(recordId);
			List<ResidentRecord> list = patientDAO.getResidentRecordByOperation(hospitalId, patientId, operation.getOperatingDate());
			if (list != null && !list.isEmpty()) {
				return list.get(0);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public List<OpertaionRecord> getOperationsByResident(String recordId, String hospitalId, String patientId) {
		try {
			ResidentRecord record = patientDAO.getResidentById(recordId);
			Date dischargeDate = record.getDischargeDateTime();
			if (dischargeDate == null) {
				dischargeDate = record.getAdmissionDate();
				dischargeDate.setYear(2030);
			}
			List<OpertaionRecord> list = patientDAO.getOperationsByResident(hospitalId, 
					patientId, record.getAdmissionDate(), record.getDischargeDateTime());
			return list;
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public List<MedicalRecordVO> getHospitalMedicalRecordList(String doctorId, String startDate, String endDate,
			String name, String idNumber, String hospitalId, String departCode) {
		// TODO Auto-generated method stub
		return patientDAO.getHospitalMedicalRecordList(doctorId, startDate, endDate, name, idNumber, hospitalId, departCode);
	}

	@Override
	public MedicalRecordVO getMedicalRecordById(String recordId) {
		// TODO Auto-generated method stub
		return patientDAO.getMedicalRecordByID(recordId);
	}

	@Override
	public int updateMedicalRecordById(String recordId, String illness_desc, String med_history, String body_exam,
			String diag_desc, String treatment, String suggestion) {
		// TODO Auto-generated method stub
		return patientDAO.updateMedicalRecordById(recordId, illness_desc, med_history, body_exam, diag_desc, treatment, suggestion);
	}
	
	@Override
	public List<OpertaionRecord> getOperationsDuringInHospital(String recordId, String hospitalId, String patientId) {
		try {
			List<OpertaionRecord> list = patientDAO.getOperationsDuringInHospital(hospitalId, 
					patientId, recordId);
			return list;
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public int updateMedicalRecord(MedicalRecordVO medicalRecordVO) {
		return patientDAO.updateMedicalRecord(medicalRecordVO);
	}

	@Override
	public int updateOperationRecord(OpertaionRecord opertaionRecord) {
		return patientDAO.updateOperationRecord(opertaionRecord);
	}

	@Override
	public int updateResidentRecord(ResidentRecord residentRecord) {
		return patientDAO.updateResidentRecord(residentRecord);
	}

	@Override
	public OpertaionRecord getOperationRecordById(String id) {
		return patientDAO.getOperationById(id);
	}

	@Override
	public ResidentRecord getResidentRecordById(String id) {
		return patientDAO.getResidentById(id);
	}

	@Override
	public MedicalRecordVO getMedicalRecordVOById(String id) {
		return patientDAO.getMedicalRecordById(id);
	}

	@Override
	public List<String> getDoctorDepartmentIdList(String doctorCode, String hospitalId) {
		if (doctorCode.equalsIgnoreCase("1701")) {
			return Arrays.asList(new String[] {"0309", "0321", "0316", "0317"});
		}
		if (doctorCode.equalsIgnoreCase("1702")) {
			return Arrays.asList(new String[] {"0309"});
		}
		if (doctorCode.equalsIgnoreCase("1705")) {
			return Arrays.asList(new String[] {"0317"});
		}
		if (doctorCode.equalsIgnoreCase("1703")) {
			return Arrays.asList(new String[] {"0316"});
		}
		if (doctorCode.equalsIgnoreCase("1765")) {
			return Arrays.asList(new String[] {"0321"});
		}
		return Arrays.asList(new String[] {"-1"});
	}
}
