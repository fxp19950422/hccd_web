package cn.sportsdata.webapp.youth.web.controller.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.sportsdata.webapp.youth.common.bo.hospital.PatientMedicalRecordBO;
import cn.sportsdata.webapp.youth.common.bo.hospital.PatientOperationRecordBO;
import cn.sportsdata.webapp.youth.common.bo.hospital.PatientRecordBO;
import cn.sportsdata.webapp.youth.common.bo.hospital.PatientResidentRecordBO;
import cn.sportsdata.webapp.youth.common.vo.Response;
import cn.sportsdata.webapp.youth.common.vo.patient.DoctorVO;
import cn.sportsdata.webapp.youth.common.vo.patient.HospitalRecordTypeVO;
import cn.sportsdata.webapp.youth.common.vo.patient.MedicalRecordVO;
import cn.sportsdata.webapp.youth.common.vo.patient.OpertaionRecord;
import cn.sportsdata.webapp.youth.common.vo.patient.PatientInHospital;
import cn.sportsdata.webapp.youth.common.vo.patient.RecordAssetVO;
import cn.sportsdata.webapp.youth.common.vo.patient.ResidentRecord;
import cn.sportsdata.webapp.youth.service.account.AccountService;
import cn.sportsdata.webapp.youth.service.patient.PatientService;
import cn.sportsdata.webapp.youth.web.controller.BaseController;

@RestController
@RequestMapping("/api/v1/")
public class PatientAPIController extends BaseController{

	@Autowired
	private AccountService loginService;
	
	@Autowired
	PatientService patientService;

	//医生app登录工作台展示的记录列表
	@RequestMapping(value = "/getDoctorRecords",  method = RequestMethod.POST)
	public ResponseEntity<Response> patientRecord(HttpServletRequest request, HttpServletResponse resp,
			String hospitalId, String doctorCode, String doctorName, String recordType, long year, long month, long day) {
		List<String> departmentIdList = getCurrentDepartmentIdList(request);
		
		if (recordType.equalsIgnoreCase("medical")) {
			List<PatientRecordBO> list = patientService.getMedicalRecordList(hospitalId, doctorCode, doctorName, year, month, day);
			return new ResponseEntity<Response>(Response.toSussess(list), HttpStatus.OK);
		}
		if (recordType.equalsIgnoreCase("operation")) {
			Map<String, Object> result = patientService.getOperationRecordList(hospitalId, doctorCode, doctorName, year, month, day);
			return new ResponseEntity<Response>(Response.toSussess(result), HttpStatus.OK);
		}
		if (recordType.equalsIgnoreCase("resident")) {
			Map<String, Object> result = patientService.getResidentRecordList(hospitalId, doctorCode, doctorName, year, month, day);
			return new ResponseEntity<Response>(Response.toSussess(result), HttpStatus.OK);
		}
		
		
		if (recordType.equalsIgnoreCase("patientInhospital")) {
			List<PatientRecordBO> list = patientService.getPatientInHospital(hospitalId, doctorCode, departmentIdList, year, month, day);
			return new ResponseEntity<Response>(Response.toSussess(list), HttpStatus.OK);
		}
		return new ResponseEntity<Response>(Response.toFailure(-1, "invalide record type"), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/recordDetails",  method = RequestMethod.POST)
	public ResponseEntity<Response> getRecordDetails(HttpServletRequest request, HttpServletResponse resp, 
			String recordId, String recordType, String patientName, String patientId, String hospitalId) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		if (recordType.equalsIgnoreCase("medical")) {
			List<PatientRecordBO> list = patientService.getPatientRecords(recordId, patientName, patientId, hospitalId);
			result.put("historyRecordList", list);
		}
		if (recordType.equalsIgnoreCase("operation")) {
			List<RecordAssetVO> assetList = patientService.getRecordAssetList(recordId);
			result.put("assetList", assetList);
			result.put("residentRecord", patientService.getResidentRecordByOperation(recordId, hospitalId, patientId));
		}
		if (recordType.equalsIgnoreCase("resident")) {
			List<RecordAssetVO> assetList = patientService.getRecordAssetList(recordId);
			result.put("assetList", assetList);
			result.put("operationRecordList", patientService.getOperationsByResident(recordId, hospitalId, patientId));
		}
		if (recordType.equalsIgnoreCase("patientInhospital")) {
			List<RecordAssetVO> assetList = patientService.getRecordAssetList(recordId);
			result.put("assetList", assetList);
			result.put("operationRecordList", patientService.getOperationsDuringInHospital(recordId, hospitalId, patientId));
			result.put("residentList", patientService.getResidentDuringInHospital(recordId, hospitalId, patientId));
		}
		
		return new ResponseEntity<Response>(Response.toSussess(result), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/searchPatientRecord",  method = RequestMethod.POST)
	public ResponseEntity<Response> searchPatientRecord(HttpServletRequest request, HttpServletResponse resp, 
			String hospitalId, String patientName, String doctorCode, String doctorName, String recordType) {
		List<PatientRecordBO> list = patientService.searchPatientRecord(hospitalId, patientName, doctorName, doctorCode, recordType);
		return new ResponseEntity<Response>(Response.toSussess(list), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/globalConfig",  method = RequestMethod.POST)
	public ResponseEntity<Response> getDoctorGlobalConfig(HttpServletRequest request, HttpServletResponse resp, String username) {
		DoctorVO doctor = patientService.getDoctorInfoByUsername(username);
		
		List<HospitalRecordTypeVO> array = new ArrayList<HospitalRecordTypeVO>();
		//medical
		List<String> sectionNameList1 = Arrays.asList(new String[] {"主诉", "病史", "查体", "初步诊断", "诊治项目","建议"});
		List<String> SectionList1 = Arrays.asList(new String[] {"illnessDesc", "medHistory","bodyExam","diagDesc", "treatment", "suggestion"});
		HospitalRecordTypeVO record1 = new HospitalRecordTypeVO();
		record1.setRecordType("medical");
		record1.setRecordTypeName("门诊病历");
		record1.setSectionList(SectionList1);
		record1.setSectionNameList(sectionNameList1);
		record1.setAssetTypeList(patientService.getRecordAssetTypeList("medical"));
		array.add(record1);
		
		//operation
		List<String> SectionNameList2 = Arrays.asList(new String[] {"术者", "主诉", "术前诊断", "术后诊断", "手术名称", "手术经过", "手术体位",
				"手术切口","探查所见", "手术步骤", "麻醉手段", "引流物", "术毕病人情况"});
		List<String> sectionList2 = Arrays.asList(new String[] {"operator", "opPrimary", "beforeDiagnosis", "afterDiagnosis",
				"operationDesc","process", "posture", "incision","exploratory", "steps", "anaesthesiaMethod", "drainage", "finishedCondition"});
		HospitalRecordTypeVO record2 = new HospitalRecordTypeVO();
		record2.setRecordType("operation");
		record2.setRecordTypeName("手术记录");
		record2.setSectionList(sectionList2);
		record2.setSectionNameList(SectionNameList2);
		record2.setAssetTypeList(patientService.getRecordAssetTypeList("operation"));
		array.add(record2);
		
		//resident
		List<String> SectionNameList3 = Arrays.asList(new String[] {"入院情况", "入院中医诊断", "入院西医诊断", 
				"诊疗经过", "出院中医诊断","出院西医诊断", "出院情况", "出院医嘱"});
		List<String> sectionList3 = Arrays.asList(new String[] {"inState", "inChiDiagnosis",
				"inWesDiagnosis","process", "outChiDiagnosis", "outWesDiagnosis", "outState", "suggestion"});
		HospitalRecordTypeVO record3 = new HospitalRecordTypeVO();
		record3.setRecordType("resident");
		record3.setRecordTypeName("出院记录");
		record3.setSectionList(sectionList3);
		record3.setSectionNameList(SectionNameList3);
		record3.setAssetTypeList(patientService.getRecordAssetTypeList("resident"));
		array.add(record3);
		
		//patient in hospital
		List<String> SectionNameList4 = Arrays.asList(new String[] {"主诉", "入院诊断", "查体", "病史", "检查检验", "病例讨论"});
		List<String> sectionList4 = Arrays.asList(new String[] {"opPrimary", "diagnosis", "bodyExam", "illHistory", "supplementaryExamination",
				"recordDiscussion"});
		HospitalRecordTypeVO record4 = new HospitalRecordTypeVO();
		record4.setRecordType("patientInhospital");
		record4.setRecordTypeName("当前住院患者");
		record4.setSectionList(sectionList4);
		record4.setSectionNameList(SectionNameList4);
		record4.setAssetTypeList(patientService.getRecordAssetTypeList("patientInhospital"));
		array.add(record4);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("recordTypeList", array);
		result.put("doctor", doctor);
		
		return new ResponseEntity<Response>(Response.toSussess(result), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/updateRecords",  method = RequestMethod.POST)
	public ResponseEntity<Response> updateRecords(HttpServletRequest request, HttpServletResponse resp, 
			String recordType, @RequestBody PatientRecordBO record) {
		Map<String, Object> result = new HashMap<String, Object>();
		recordType = record.getRecordType();
		if (recordType.equalsIgnoreCase("medical")) {
			MedicalRecordVO medicalRecordVO = patientService
					.getMedicalRecordVOById(record.getMedicalRecord().getId());
			medicalRecordVO.setIllnessDesc(record.getMedicalRecord().getIllnessDesc());
			medicalRecordVO.setMedHistory(record.getMedicalRecord().getMedHistory());
			medicalRecordVO.setBodyExam(record.getMedicalRecord().getBodyExam());
			medicalRecordVO.setDiagDesc(record.getMedicalRecord().getDiagDesc());
			medicalRecordVO.setTreatment(record.getMedicalRecord().getTreatment());
			medicalRecordVO.setSuggestion(record.getMedicalRecord().getSuggestion());
			int count = patientService.updateMedicalRecord(medicalRecordVO);
		}
		if (recordType.equalsIgnoreCase("operation")) {
			OpertaionRecord opertaionRecord = patientService
					.getOperationRecordById(record.getOperationRecord().getId());
			opertaionRecord.setBeforeDiagnosis(record.getOperationRecord().getBeforeDiagnosis());
			opertaionRecord.setAfterDiagnosis(record.getOperationRecord().getAfterDiagnosis());
			opertaionRecord.setOperationDesc(record.getOperationRecord().getOperationDesc());
			opertaionRecord.setProcess(record.getOperationRecord().getProcess());
			opertaionRecord.setPosture(record.getOperationRecord().getPosture());
			opertaionRecord.setIncision(record.getOperationRecord().getIncision());
			opertaionRecord.setExploratory(record.getOperationRecord().getExploratory());
			opertaionRecord.setSteps(record.getOperationRecord().getSteps());
			opertaionRecord.setDrainage(record.getOperationRecord().getDrainage());
			opertaionRecord.setFinishedCondition(record.getOperationRecord().getFinishedCondition());
			opertaionRecord.setAnaesthesiaMethod(record.getOperationRecord().getAnaesthesiaMethod());
			opertaionRecord.setOpPrimary(record.getOperationRecord().getOpPrimary());
			opertaionRecord.setOperator(record.getOperationRecord().getOperator());
			
			int count = patientService.updateOperationRecord(opertaionRecord);
		}
		if (recordType.equalsIgnoreCase("resident")) {
			ResidentRecord residentRecord = patientService.getResidentRecordById(record.getResidentRecord().getId());
			residentRecord.setInState(record.getResidentRecord().getInState());
			residentRecord.setInChiDiagnosis(record.getResidentRecord().getInChiDiagnosis());
			residentRecord.setInWesDiagnosis(record.getResidentRecord().getInWesDiagnosis());
			residentRecord.setProcess(record.getResidentRecord().getProcess());
			residentRecord.setOutChiDiagnosis(record.getResidentRecord().getOutChiDiagnosis());
			residentRecord.setOutWesDiagnosis(record.getResidentRecord().getOutWesDiagnosis());
			residentRecord.setOutState(record.getResidentRecord().getOutState());
			residentRecord.setSuggestion(record.getResidentRecord().getSuggestion());

			int count = patientService.updateResidentRecord(residentRecord);
		}
		if (recordType.equalsIgnoreCase("resident")) {
			ResidentRecord residentRecord = patientService.getResidentRecordById(record.getResidentRecord().getId());
			residentRecord.setInState(record.getResidentRecord().getInState());
			residentRecord.setInChiDiagnosis(record.getResidentRecord().getInChiDiagnosis());
			residentRecord.setInWesDiagnosis(record.getResidentRecord().getInWesDiagnosis());
			residentRecord.setProcess(record.getResidentRecord().getProcess());
			residentRecord.setOutChiDiagnosis(record.getResidentRecord().getOutChiDiagnosis());
			residentRecord.setOutWesDiagnosis(record.getResidentRecord().getOutWesDiagnosis());
			residentRecord.setOutState(record.getResidentRecord().getOutState());
			residentRecord.setSuggestion(record.getResidentRecord().getSuggestion());

			int count = patientService.updateResidentRecord(residentRecord);
		}
		if (recordType.equalsIgnoreCase("patientInhospital")) {
			PatientInHospital newRecord = record.getPatientInhospital();
			PatientInHospital oldRecord = patientService.searchPatientInHospitalById(newRecord.getId());
			oldRecord.setOpPrimary(newRecord.getOpPrimary());
			oldRecord.setBodyExam(newRecord.getBodyExam());
			oldRecord.setDiagnosis(newRecord.getDiagnosis());
			oldRecord.setIllHistory(newRecord.getIllHistory());
			oldRecord.setRecordDiscussion(newRecord.getRecordDiscussion());
			oldRecord.setSupplementaryExamination(newRecord.getSupplementaryExamination());			
			int count = patientService.updatePatientInHospital(oldRecord);
		}

		return new ResponseEntity<Response>(Response.toSussess(result), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/submitMeetingRecord",  method = RequestMethod.POST)
	public ResponseEntity<Response> submitMeetingRecord(HttpServletRequest request, HttpServletResponse resp,
			String recordType, String recordId, String patientInhospitalId, String hospitalId, String doctorId) {
	
		if (patientService.submitMeetingRecord(recordType, recordId, patientInhospitalId, hospitalId, doctorId)) {
			return new ResponseEntity<Response>(Response.toSussess("success"), HttpStatus.OK);
		}
		return new ResponseEntity<Response>(Response.toFailure(401, "exception"), HttpStatus.EXPECTATION_FAILED);
	}
	
	@RequestMapping(value = "/revertMeetingRecord",  method = RequestMethod.POST)
	public ResponseEntity<Response> revertMeetingRecord(HttpServletRequest request, HttpServletResponse resp,
			String recordId) {
	
		if (patientService.revertMeetingRecord(recordId)) {
			return new ResponseEntity<Response>(Response.toSussess("success"), HttpStatus.OK);
		}
		return new ResponseEntity<Response>(Response.toFailure(401, "exception"), HttpStatus.EXPECTATION_FAILED);
	}
}