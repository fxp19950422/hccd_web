package cn.sportsdata.webapp.youth.pcli.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.sportsdata.webapp.youth.common.bo.hospital.PatientRecordBriefBO;
import cn.sportsdata.webapp.youth.common.bo.hospital.PatientRecordDetailBO;
import cn.sportsdata.webapp.youth.common.vo.Response;
import cn.sportsdata.webapp.youth.service.account.AccountService;
import cn.sportsdata.webapp.youth.service.patient.PatientClientService;
import cn.sportsdata.webapp.youth.service.patient.PatientService;

@RestController
@RequestMapping("/api/v1/pcli")
public class PatientClientAPIController {

	@Autowired
	private AccountService loginService;
	
	@Autowired
	PatientClientService patientClientService;
	
	@Autowired
	PatientService patientService;

	@RequestMapping(value = "/patientRecords/{hospitalId}/{userId}",  method = RequestMethod.GET)
	public ResponseEntity<Response> patientRecords(HttpServletRequest request, HttpServletResponse resp, 
			@PathVariable String hospitalId, @PathVariable String userId) {
		List<PatientRecordBriefBO> list = patientClientService.getPatientBriefRecords(userId, hospitalId);
		return new ResponseEntity<Response>(Response.toSussess(list), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/patientRecord/{type}/detail/{recordId}",  method = RequestMethod.GET)
	public ResponseEntity<Response> patientRecordDetail(HttpServletRequest request, HttpServletResponse resp, 
			@PathVariable String recordId, @PathVariable String type) {	
		PatientRecordDetailBO bo = patientClientService.getPatientRecordDetail(recordId, type);
		return new ResponseEntity<Response>(Response.toSussess(bo), HttpStatus.OK);
	}
	
	
	
//	@RequestMapping(value = "/recordDetails",  method = RequestMethod.POST)
//	public ResponseEntity<Response> getRecordDetails(HttpServletRequest request, HttpServletResponse resp, 
//			String recordId, String recordType, String patientName, String patientId, String hospitalId) {
//		Map<String, Object> result = new HashMap<String, Object>();
//		
//		if (recordType.equalsIgnoreCase("medical")) {
//			List<PatientRecordBO> list = patientService.getPatientRecords(recordId, patientName, patientId, hospitalId);
//			result.put("historyRecordList", list);
//		}
//		if (recordType.equalsIgnoreCase("operation")) {
//			List<RecordAssetVO> assetList = patientService.getRecordAssetList(recordId);
//			result.put("assetList", assetList);
//			result.put("residentRecord", patientService.getResidentRecordByOperation(recordId, hospitalId, patientId));
//		}
//		if (recordType.equalsIgnoreCase("resident")) {
//			List<RecordAssetVO> assetList = patientService.getRecordAssetList(recordId);
//			result.put("assetList", assetList);
//			result.put("operationRecordList", patientService.getOperationsByResident(recordId, hospitalId, patientId));
//		}
//		if (recordType.equalsIgnoreCase("patientInhospital")) {
//			List<RecordAssetVO> assetList = patientService.getRecordAssetList(recordId);
//			result.put("assetList", assetList);
//			result.put("operationRecordList", patientService.getOperationsDuringInHospital(recordId, hospitalId, patientId));
//			result.put("residentList", patientService.getResidentDuringInHospital(recordId, hospitalId, patientId));
//		}
//		
//		return new ResponseEntity<Response>(Response.toSussess(result), HttpStatus.OK);
//	}
//	
//	@RequestMapping(value = "/searchPatientRecord",  method = RequestMethod.POST)
//	public ResponseEntity<Response> searchPatientRecord(HttpServletRequest request, HttpServletResponse resp, 
//			String hospitalId, String patientName, String doctorCode, String doctorName, String recordType) {
//		List<PatientRecordBO> list = patientService.searchPatientRecord(hospitalId, patientName, doctorName, doctorCode, recordType);
//		return new ResponseEntity<Response>(Response.toSussess(list), HttpStatus.OK);
//	}
//	
//	@RequestMapping(value = "/globalConfig",  method = RequestMethod.POST)
//	public ResponseEntity<Response> getDoctorGlobalConfig(HttpServletRequest request, HttpServletResponse resp, String username) {
//		DoctorVO doctor = patientService.getDoctorInfoByUsername(username);
//		
//		List<HospitalRecordTypeVO> array = new ArrayList<HospitalRecordTypeVO>();
//		//medical
//		List<String> sectionNameList1 = Arrays.asList(new String[] {"主诉", "病史", "查体", "初步诊断", "诊治项目","建议"});
//		List<String> SectionList1 = Arrays.asList(new String[] {"illnessDesc", "medHistory","bodyExam","diagDesc", "treatment", "suggestion"});
//		HospitalRecordTypeVO record1 = new HospitalRecordTypeVO();
//		record1.setRecordType("medical");
//		record1.setRecordTypeName("门诊病历");
//		record1.setSectionList(SectionList1);
//		record1.setSectionNameList(sectionNameList1);
//		record1.setAssetTypeList(patientService.getRecordAssetTypeList("medical"));
//		array.add(record1);
//		
//		//operation
//		List<String> SectionNameList2 = Arrays.asList(new String[] {"术者", "主诉", "术前诊断", "术后诊断", "手术名称", "手术经过", "手术体位",
//				"手术切口","探查所见", "手术步骤", "麻醉手段", "引流物", "术毕病人情况"});
//		List<String> sectionList2 = Arrays.asList(new String[] {"operator", "opPrimary", "beforeDiagnosis", "afterDiagnosis",
//				"operationDesc","process", "posture", "incision","exploratory", "steps", "anaesthesiaMethod", "drainage", "finishedCondition"});
//		HospitalRecordTypeVO record2 = new HospitalRecordTypeVO();
//		record2.setRecordType("operation");
//		record2.setRecordTypeName("手术记录");
//		record2.setSectionList(sectionList2);
//		record2.setSectionNameList(SectionNameList2);
//		record2.setAssetTypeList(patientService.getRecordAssetTypeList("operation"));
//		array.add(record2);
//		
//		//resident
//		List<String> SectionNameList3 = Arrays.asList(new String[] {"入院情况", "入院中医诊断", "入院西医诊断", 
//				"诊疗经过", "出院中医诊断","出院西医诊断", "出院情况", "出院医嘱"});
//		List<String> sectionList3 = Arrays.asList(new String[] {"inState", "inChiDiagnosis",
//				"inWesDiagnosis","process", "outChiDiagnosis", "outWesDiagnosis", "outState", "suggestion"});
//		HospitalRecordTypeVO record3 = new HospitalRecordTypeVO();
//		record3.setRecordType("resident");
//		record3.setRecordTypeName("出院记录");
//		record3.setSectionList(sectionList3);
//		record3.setSectionNameList(SectionNameList3);
//		record3.setAssetTypeList(patientService.getRecordAssetTypeList("resident"));
//		array.add(record3);
//		
//		//patient in hospital
//		List<String> SectionNameList4 = Arrays.asList(new String[] {"入院诊断", "病人情况", "护理级别"});
//		List<String> sectionList4 = Arrays.asList(new String[] {"diagnosis", "patientCondition",
//				"nursingClass"});
//		HospitalRecordTypeVO record4 = new HospitalRecordTypeVO();
//		record4.setRecordType("patientInhospital");
//		record4.setRecordTypeName("当前住院患者");
//		record4.setSectionList(sectionList4);
//		record4.setSectionNameList(SectionNameList4);
//		record4.setAssetTypeList(patientService.getRecordAssetTypeList("patientInhospital"));
//		array.add(record4);
//		
//		Map<String, Object> result = new HashMap<String, Object>();
//		result.put("recordTypeList", array);
//		result.put("doctor", doctor);
//		
//		return new ResponseEntity<Response>(Response.toSussess(result), HttpStatus.OK);
//	}
//	
//	@RequestMapping(value = "/updateRecords",  method = RequestMethod.POST)
//	public ResponseEntity<Response> updateRecords(HttpServletRequest request, HttpServletResponse resp, 
//			String recordType, @RequestBody PatientRecordBO record) {
//		Map<String, Object> result = new HashMap<String, Object>();
//		recordType = record.getRecordType();
//		if (recordType.equalsIgnoreCase("medical")) {
//			MedicalRecordVO medicalRecordVO = patientService
//					.getMedicalRecordVOById(record.getMedicalRecord().getId());
//			medicalRecordVO.setIllnessDesc(record.getMedicalRecord().getIllnessDesc());
//			medicalRecordVO.setMedHistory(record.getMedicalRecord().getMedHistory());
//			medicalRecordVO.setBodyExam(record.getMedicalRecord().getBodyExam());
//			medicalRecordVO.setDiagDesc(record.getMedicalRecord().getDiagDesc());
//			medicalRecordVO.setTreatment(record.getMedicalRecord().getTreatment());
//			medicalRecordVO.setSuggestion(record.getMedicalRecord().getSuggestion());
//			int count = patientService.updateMedicalRecord(medicalRecordVO);
//		}
//		if (recordType.equalsIgnoreCase("operation")) {
//			OpertaionRecord opertaionRecord = patientService
//					.getOperationRecordById(record.getOperationRecord().getId());
//			opertaionRecord.setBeforeDiagnosis(record.getOperationRecord().getBeforeDiagnosis());
//			opertaionRecord.setAfterDiagnosis(record.getOperationRecord().getAfterDiagnosis());
//			opertaionRecord.setOperationDesc(record.getOperationRecord().getOperationDesc());
//			opertaionRecord.setProcess(record.getOperationRecord().getProcess());
//			opertaionRecord.setPosture(record.getOperationRecord().getPosture());
//			opertaionRecord.setIncision(record.getOperationRecord().getIncision());
//			opertaionRecord.setExploratory(record.getOperationRecord().getExploratory());
//			opertaionRecord.setSteps(record.getOperationRecord().getSteps());
//			opertaionRecord.setDrainage(record.getOperationRecord().getDrainage());
//			opertaionRecord.setFinishedCondition(record.getOperationRecord().getFinishedCondition());
//
//			int count = patientService.updateOperationRecord(opertaionRecord);
//		}
//		if (recordType.equalsIgnoreCase("resident")) {
//			ResidentRecord residentRecord = patientService.getResidentRecordById(record.getResidentRecord().getId());
//			residentRecord.setInState(record.getResidentRecord().getInState());
//			residentRecord.setInChiDiagnosis(record.getResidentRecord().getInChiDiagnosis());
//			residentRecord.setInWesDiagnosis(record.getResidentRecord().getInWesDiagnosis());
//			residentRecord.setProcess(record.getResidentRecord().getProcess());
//			residentRecord.setOutChiDiagnosis(record.getResidentRecord().getOutChiDiagnosis());
//			residentRecord.setOutWesDiagnosis(record.getResidentRecord().getOutWesDiagnosis());
//			residentRecord.setOutState(record.getResidentRecord().getOutState());
//			residentRecord.setSuggestion(record.getResidentRecord().getSuggestion());
//
//			int count = patientService.updateResidentRecord(residentRecord);
//		}
//
//		return new ResponseEntity<Response>(Response.toSussess(result), HttpStatus.OK);
//	}
//	
//	@RequestMapping(value = "/submitMeetingRecord",  method = RequestMethod.POST)
//	public ResponseEntity<Response> submitMeetingRecord(HttpServletRequest request, HttpServletResponse resp,
//			String recordType, String recordId, String patientInhospitalId, String hospitalId, String doctorId) {
//	
//		if (patientService.submitMeetingRecord(recordType, recordId, patientInhospitalId, hospitalId, doctorId)) {
//			return new ResponseEntity<Response>(Response.toSussess("success"), HttpStatus.OK);
//		}
//		return new ResponseEntity<Response>(Response.toFailure(401, "exception"), HttpStatus.EXPECTATION_FAILED);
//	}
//	
//	@RequestMapping(value = "/revertMeetingRecord",  method = RequestMethod.POST)
//	public ResponseEntity<Response> revertMeetingRecord(HttpServletRequest request, HttpServletResponse resp,
//			String recordId) {
//	
//		if (patientService.revertMeetingRecord(recordId)) {
//			return new ResponseEntity<Response>(Response.toSussess("success"), HttpStatus.OK);
//		}
//		return new ResponseEntity<Response>(Response.toFailure(401, "exception"), HttpStatus.EXPECTATION_FAILED);
//	}
}