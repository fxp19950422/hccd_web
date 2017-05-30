package cn.sportsdata.webapp.youth.service.patient.impl;

import java.io.File;
import java.io.FileFilter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import cn.sportsdata.webapp.youth.common.exceptions.SoccerProException;
import cn.sportsdata.webapp.youth.common.utils.ConfigProps;
import cn.sportsdata.webapp.youth.common.vo.patient.DoctorVO;
import cn.sportsdata.webapp.youth.common.vo.patient.MedicalRecordVO;
import cn.sportsdata.webapp.youth.common.vo.patient.OpertaionRecord;
import cn.sportsdata.webapp.youth.common.vo.patient.OrdersVO;
import cn.sportsdata.webapp.youth.common.vo.patient.PatientDocumentVO;
import cn.sportsdata.webapp.youth.common.vo.patient.PatientInHospital;
import cn.sportsdata.webapp.youth.common.vo.patient.PatientInfoVO;
import cn.sportsdata.webapp.youth.common.vo.patient.PatientRegistRecord;
import cn.sportsdata.webapp.youth.common.vo.patient.RecordAssetStageVO;
import cn.sportsdata.webapp.youth.common.vo.patient.RecordAssetTypeVO;
import cn.sportsdata.webapp.youth.common.vo.patient.RecordAssetVO;
import cn.sportsdata.webapp.youth.common.vo.patient.ResidentRecord;
import cn.sportsdata.webapp.youth.common.vo.patient.ShiftMeetingVO;
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
				patientRecord.setDepartmentId("0309");
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
	public List<RecordAssetStageVO> getAssetStageList() {
		List<RecordAssetStageVO> assetStageList = null;
		try {
			assetStageList = patientDAO.getAssetStageList();
			if(assetStageList == null) {
				assetStageList = new ArrayList<RecordAssetStageVO>();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			assetStageList = new ArrayList<RecordAssetStageVO>();
		}
		return assetStageList;
	}

	@Override
	public DoctorVO getDoctorInfoByUsername(String username) {
		return patientDAO.getDoctorInfoByUsername(username);
	}

	@Override
	public Map<String, Object> getOperationRecordList(String hospitalId, String doctorCode, String doctorName,
			long year, long month, long day) {
		List<String> departmentIdList = this.getDoctorDepartmentIdList(doctorCode, hospitalId);
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			List<PatientRecordBO> patientRecordList1 = new ArrayList<PatientRecordBO>();
			List<OpertaionRecord> operationList = patientDAO.getOperationRecordList(hospitalId, departmentIdList, doctorName, doctorCode, year, month, day);
			List<String> patientIdList = new ArrayList<String>();
			for (OpertaionRecord operationRecord:operationList) {
				PatientRecordBO patientRecord = new PatientRecordBO();
				patientRecord.setRecordType("operation");
				patientRecord.setDepartmentId(operationRecord.getDepartmentId());
				patientRecord.setOperationRecord(operationRecord);
				patientRecord.setPatientId(operationRecord.getPatientId());
				patientIdList.add(patientRecord.getPatientId());
				patientRecordList1.add(patientRecord);
			}
			
			if (!patientIdList.isEmpty()) {
				List<PatientInfoVO> patientList = patientDAO.getPatients(patientIdList);

				for (PatientRecordBO patientRecord:patientRecordList1) {
					for (PatientInfoVO patient:patientList) {
						if (patient.getPatientNumber().equalsIgnoreCase(patientRecord.getPatientId())) {
							patientRecord.setPatient(patient);
							break;
						}
					}
				}
			}
			result.put("operationList", patientRecordList1);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Map<String, Object> getResidentRecordList(String hospitalId, String doctorCode, String doctorName,
			long year, long month, long day) {
		List<String> departmentIdList = this.getDoctorDepartmentIdList(doctorCode, hospitalId);
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			List<PatientRecordBO> patientRecordList = new ArrayList<PatientRecordBO>();
			List<ResidentRecord> residentRecordList = patientDAO.getResidentRecordList(hospitalId, doctorCode, departmentIdList, year, month, day);
			List<String> patientIdList = new ArrayList<String>();
			for (ResidentRecord residentRecord:residentRecordList) {
				PatientRecordBO patientRecord = new PatientRecordBO();
				patientRecord.setDepartmentId(residentRecord.getDepartmentId());
				patientRecord.setRecordType("resident");
				patientRecord.setResidentRecord(residentRecord);
				patientRecord.setPatientId(residentRecord.getPatientId());
				patientIdList.add(patientRecord.getPatientId());
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
			result.put("residentList", patientRecordList);
			/*
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

				for (PatientRecordBO patientRecord:patientRecordList1) {
					for (PatientInfoVO patient:patientList) {
						if (patient.getPatientNumber().equalsIgnoreCase(patientRecord.getPatientId())) {
							patientRecord.setPatient(patient);
							break;
						}
					}
				}
				for (PatientRecordBO patientRecord:patientRecordList2) {
					for (PatientInfoVO patient:patientList) {
						if (patient.getPatientNumber().equalsIgnoreCase(patientRecord.getPatientId())) {
							patientRecord.setPatient(patient);
							break;
						}
					}
				}
				for (PatientRecordBO patientRecord:patientRecordList3) {
					for (PatientInfoVO patient:patientList) {
						if (patient.getPatientNumber().equalsIgnoreCase(patientRecord.getPatientId())) {
							patientRecord.setPatient(patient);
							break;
						}
					}
				}
			}
			*/
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
				patientRecord.setDepartmentId(patientRecord.getDepartmentId());
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
		if (recordType.equalsIgnoreCase("patientInhospital")) {
			List<PatientInHospital> list = patientDAO.searchPatientInhospital(hospitalId, patientName, doctorName, doctorCode);
			for (PatientInHospital record:list) {
				PatientRecordBO patientRecord = new PatientRecordBO();
				patientRecord.setRecordType("patientInhospital");
				patientRecord.setPatientInhospital(record);
				patientRecord.setPatientId(record.getPatientId());
				patientRecordList.add(patientRecord);
				patientIdList.add(record.getPatientId());
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
		
		return patientRecordList;
	}

	@Override
	public List<PatientRecordBO> getPatientRecords(String recordId, String patientName, String patientId, String hospitalId) {
		try {
			List<MedicalRecordVO> medicalRecords = patientDAO.getPatientMedicalRecords(recordId, patientName, patientId, hospitalId);
			List<ResidentRecord> residentRecords = patientDAO.getPatientResidentRecords(recordId, patientName, patientId, hospitalId);
			List<OpertaionRecord> operationRecords = patientDAO.getPatientOperationRecords(recordId, patientName, patientId, hospitalId);
			List<PatientRecordBO> resultList = new ArrayList<PatientRecordBO>();
			List<String> patientIdList = new ArrayList<String>();
			for (MedicalRecordVO record:medicalRecords) {
				PatientRecordBO patientRecord = new PatientRecordBO();
				patientRecord.setRecordType("medical");
				patientRecord.setMedicalRecord(record);
				patientRecord.setPatientId(record.getPatientId());
				resultList.add(patientRecord);
				patientIdList.add(record.getPatientId());
			}
			for (ResidentRecord record:residentRecords) {
				PatientRecordBO patientRecord = new PatientRecordBO();
				patientRecord.setRecordType("resident");
				patientRecord.setResidentRecord(record);
				patientRecord.setPatientId(record.getPatientId());
				resultList.add(patientRecord);
				patientIdList.add(record.getPatientId());
			}
			for (OpertaionRecord record:operationRecords) {
				PatientRecordBO patientRecord = new PatientRecordBO();
				patientRecord.setRecordType("operation");
				patientRecord.setOperationRecord(record);
				patientRecord.setPatientId(record.getPatientId());
				resultList.add(patientRecord);
				patientIdList.add(record.getPatientId());
			}
			
			if (!patientIdList.isEmpty()) {
				List<PatientInfoVO> patientList = patientDAO.getPatients(patientIdList);

				for (PatientRecordBO patientRecord:resultList) {
					for (PatientInfoVO patient:patientList) {
						if (patient.getPatientNumber().equalsIgnoreCase(patientRecord.getPatientId())) {
							patientRecord.setPatient(patient);
							break;
						}
					}
				}
			}
			return resultList;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<PatientRecordBO> getPatientInHospital(String hospitalId, String doctorCode,
			long year, long month, long day) {
		List<String> departmentIdList = this.getDoctorDepartmentIdList(doctorCode, hospitalId);
		List<PatientRecordBO> patientRecordList = new ArrayList<PatientRecordBO>();
		try {
			List<PatientInHospital> list = patientDAO.getCurPatientsInHospital(hospitalId, doctorCode, departmentIdList);
			List<String> patientIdList = new ArrayList<String>();
			for (PatientInHospital record:list) {
				PatientRecordBO patientRecord = new PatientRecordBO();
				patientRecord.setRecordType("patientInhospital");
				patientRecord.setDepartmentId(record.getDepartmentId());
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

	private Date getTime(String test_time_str){
		SimpleDateFormat sdmf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return sdmf.parse(test_time_str);
		} catch (ParseException e) {
			return new Date();
		}
	}
	
	@Override
	public MedicalRecordVO getMedicalRecordById(String recordId) {
		// TODO Auto-generated method stub
		return patientDAO.getMedicalRecordByID(recordId);
	}

	@Override
	public int updateMedicalRecordById(String recordId, String illness_desc, String med_history, String body_exam,
			String diag_desc, String treatment, String suggestion,String accExam) {
		// TODO Auto-generated method stub
		return patientDAO.updateMedicalRecordById(recordId, illness_desc, med_history, body_exam, diag_desc, treatment, suggestion,accExam);
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
	public OpertaionRecord getOperationRecordByIdWithoutAssset(String id) {
		return patientDAO.getOperationByIdWithoutMapping(id);
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
			return Arrays.asList(new String[] {"0309", "0321", "0316", "0317", "0325", "0324"});
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
		if (doctorCode.equalsIgnoreCase("1527")) {
			return Arrays.asList(new String[] {"0325", "0324"});
		}
		return Arrays.asList(new String[] {"-1"});
	}

	@Override
	public List<PatientRegistRecord> getRegisteRecordList(String hospitalId, String doctorCode, String patName,
			long year, long month, long day) {
		return patientDAO.searchRegisteRecordList(hospitalId, doctorCode,patName, year, month, day);
	}

	@Override
	public PatientRegistRecord getRegisteRecordById(String id) {
		// TODO Auto-generated method stub
		return patientDAO.getRegisteRecordById(id);
	}

	@Override
	public List<PatientInfoVO> getPatients(List<String> patientIdList) {
		// TODO Auto-generated method stub
		return patientDAO.getPatients(patientIdList);
	}

	@Override
	public int insertMedicalRecord(MedicalRecordVO medicalRecordVO) {
		// TODO Auto-generated method stub
		return patientDAO.insertMedicalRecord(medicalRecordVO);
	}

	@Override
	public List<OpertaionRecord> searchOperationRecordList(String hospitalId, String doctorCode, String name, long year,
			long month, long day) {
		// TODO Auto-generated method stub
		return patientDAO.searchOperationRecordList(hospitalId, doctorCode, name, year, month, day);
	}

	@Override
	public Boolean submitMeetingRecord(String recordType, String recordId, String patientInhospitalId, String hospitalId, String doctorId) { 
		if (patientDAO.saveShiftMeetingRecords(hospitalId, doctorId, recordId, recordType) > 0) {
			return (patientDAO.updatePatientRecentMeetingRecord(patientInhospitalId, recordType) > 0);
		}
		return false;
	}

	@Override
	public List<ResidentRecord> getResidentDuringInHospital(String recordId, String hospitalId, String patientId) {
		return patientDAO.getResidentDuringInHospital(hospitalId, patientId, recordId);
	}
	
	public List<PatientInHospital> searchInHospitalRecordList(String hospitalId, String doctorCode, String name,
			Date careStartTime, Date careEndTime) {
		// TODO Auto-generated method stub
		return patientDAO.searchInHospitalRecordList(hospitalId, doctorCode, name, careStartTime, careEndTime);
	}

	@Override
	public List<PatientInHospital> searchDirectorInHospitalRecordList(String hospitalId, String departmentId,
			String name, Date careStartTime, Date careEndTime) {
		// TODO Auto-generated method stub
		return patientDAO.searchDirectorInHospitalRecordList(hospitalId, departmentId, name, careStartTime, careEndTime);
	}

	@Override
	public PatientInHospital searchPatientInHospitalById(String recordId) {
		// TODO Auto-generated method stub
		return patientDAO.searchPatientInHospitalById(recordId);
	}

	@Override
	public int updatePatientInHospital(PatientInHospital record) {
		// TODO Auto-generated method stub
		return patientDAO.updatePatientInHospital(record);
	}

	@Override
	public List<ResidentRecord> searchResidentRecordList(String hospitalId, String doctorCode, String name,
			Date careStartTime, Date careEndTime) {
		// TODO Auto-generated method stub
		return patientDAO.searchResidentRecordList(hospitalId, doctorCode, name, careStartTime, careEndTime);
	}

	@Override
	public List<ResidentRecord> searchDirectorResidentRecordList(String hospitalId, String departmentId, String name,
			Date careStartTime, Date careEndTime) {
		// TODO Auto-generated method stub
		return patientDAO.searchDirectorResidentRecordList(hospitalId, departmentId, name, careStartTime, careEndTime);
	}

	@Override
	public List<ShiftMeetingVO> getTodayExchangeRecordList(int difference) {
		// TODO Auto-generated method stub
		return patientDAO.getTodayMeetingRecords(difference);
	}

	@Override
	public List<OpertaionRecord> searchDirectorOperationRecordList(String hospitalId, String departmentId, String name,
			long year, long month, long day) {
		// TODO Auto-generated method stub
		return patientDAO.searchDirectorOperationRecordList(hospitalId, departmentId, name, year, month, day);
	}

	@Override
	public boolean revertMeetingRecord(String recordId) {
		return patientDAO.deleteShiftMeetingRecord(recordId);
	}

	private static final String historyDocumentDir = ConfigProps.getInstance().getConfigValue("historyDocumentDir");  
	
	@Override
	public List<PatientDocumentVO> getHistoryDocumentByPatientName(String patientName) throws SoccerProException {
		List<PatientDocumentVO> docList = new ArrayList<PatientDocumentVO>();
        File folder = new File(historyDocumentDir);// 默认目录
        if (!folder.exists()) {// 如果文件夹不存在
            return docList;
        }
        List<File> result = searchFolder(folder, patientName);// 调用方法获得文件数组
        for (File file : result) {
        	listSubFiles(file, docList);
        }
        return docList;
	}
	
	private void listSubFiles(File folder, List<PatientDocumentVO> docList) throws SoccerProException {
    	PatientDocumentVO folderVO = new PatientDocumentVO();
    	folderVO.setDirName(folder.getName());
    	docList.add(folderVO);
		
		File[] subFolders = folder.listFiles();
		for (int i = 0; i < subFolders.length; i++) {// 循环显示文件夹或文件
			
			if (subFolders[i].isDirectory()) {// 如果是文件则将文件添加到结果列表中
				listSubFiles(subFolders[i], docList);
			} else {
				PatientDocumentVO document = new PatientDocumentVO();
				document.setFileName(subFolders[i].getName());
				document.setFilePath(subFolders[i].getAbsolutePath());
				docList.add(document);
			}	
		}
    }
	
    static int countFiles = 0;// 声明统计文件个数的变量
    static int countFolders = 0;// 声明统计文件夹的变量   
    private List<File> searchFolder(File folder, final String keyWord) {// 递归查找包含关键字的文件
    	 
        File[] subFolders = folder.listFiles(new FileFilter() {// 运用内部匿名类获得文件
            @Override
            public boolean accept(File pathname) {// 实现FileFilter类的accept方法
                if (pathname.isFile())// 如果是文件
                    countFiles++;
                else
                    // 如果是目录
                    countFolders++;
                if (pathname.isDirectory() )// 目录包含关键字
                    return true;
                return false;
            }
        });
 
        List<File> result = new ArrayList<File>();// 声明一个集合
        for (int i = 0; i < subFolders.length; i++) {// 循环显示文件夹或文件
        	
            if (subFolders[i].isDirectory()) {// 如果是文件则将文件添加到结果列表中
                if (subFolders[i].getName().toLowerCase().contains(keyWord.toLowerCase())) {
                	result.add(subFolders[i]);
                } else {
                	List<File> foldResult = searchFolder(subFolders[i], keyWord);
                	result.addAll(foldResult);
                }
            }
        }
 
        return result;
    }

	@Override
	public List<PatientInHospital> getInHospitalMeetingRecords(String doctorId, long year, long month, long day) {
		List<String> departmentIdList = this.getDoctorDepartmentIdList(doctorId, "1");
		return patientDAO.getInHospitalMeetingRecords(departmentIdList, doctorId, year, month, day);
	}

	@Override
	public List<ResidentRecord> getResidentMeetingRecords(String doctorId, long year, long month, long day) {
		List<String> departmentIdList = this.getDoctorDepartmentIdList(doctorId, "1");
		return patientDAO.getResidentMeetingRecords(departmentIdList, doctorId, year, month, day);
	}

	@Override
	public List<OpertaionRecord> getOperationMeetingRecords(String doctorId, long year, long month, long day) {
		List<String> departmentIdList = this.getDoctorDepartmentIdList(doctorId, "1");
		return patientDAO.getOperationMeetingRecords(departmentIdList, doctorId, year, month, day);
	}

	@Override
	public String getDoctorAppVersion() {
		return patientDAO.getAppVersion("doctor_app");
	}

	@Override
	public List<DoctorVO> getDoctorListByOrg(String hospitalId) {
		return patientDAO.getDoctorListByOrg(hospitalId);
	}

	@Override
	public List<PatientInHospital> getPatientInHosByDepartment(String hospitalId, String departmentId) {
		return patientDAO.getPatientInHosByDepartment(hospitalId, departmentId);
	}

	@Override
	public List<OrdersVO> getPatientOrders(List<String> patientIdList) {
		return patientDAO.getPatientOrders(patientIdList);
	}
}
