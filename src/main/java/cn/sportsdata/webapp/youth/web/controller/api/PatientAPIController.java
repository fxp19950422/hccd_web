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
import cn.sportsdata.webapp.youth.common.vo.patient.RecordAssetVO;
import cn.sportsdata.webapp.youth.service.account.AccountService;
import cn.sportsdata.webapp.youth.service.patient.PatientService;

@RestController
@RequestMapping("/api/v1/")
public class PatientAPIController {

	@Autowired
	private AccountService loginService;
	
	@Autowired
	PatientService patientService;

	@RequestMapping(value = "/getDoctorRecords",  method = RequestMethod.POST)
	public ResponseEntity<Response> patientRecord(HttpServletRequest request, HttpServletResponse resp,
			String hospitalId, String doctorCode, String recordType, String date) {
		if (recordType.equalsIgnoreCase("medical")) {
			List<PatientRecordBO> list = patientService.getMedicalRecordList(hospitalId, doctorCode, date);
			return new ResponseEntity<Response>(Response.toSussess(list), HttpStatus.OK);
		}
		if (recordType.equalsIgnoreCase("operation")) {
			List<PatientRecordBO> list = patientService.getMedicalRecordList(hospitalId, doctorCode, date);
			return new ResponseEntity<Response>(Response.toSussess(list), HttpStatus.OK);
		}
		if (recordType.equalsIgnoreCase("resident")) {
			List<PatientRecordBO> list = patientService.getMedicalRecordList(hospitalId, doctorCode, date);
			return new ResponseEntity<Response>(Response.toSussess(list), HttpStatus.OK);
		}
		return new ResponseEntity<Response>(Response.toFailure(-1, "invalide record type"), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/recordDetails",  method = RequestMethod.POST)
	public ResponseEntity<Response> getRecordDetails(HttpServletRequest request, HttpServletResponse resp, 
			String recordId, String recordType) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		if (recordType.equalsIgnoreCase("medical")) {
			PatientMedicalRecordBO record = patientService.getMedicalRecord(recordId);
			result.put("recordData", record);
		}
		if (recordType.equalsIgnoreCase("operation")) {
			PatientOperationRecordBO record = patientService.getOperationRecord(recordId);
			result.put("recordData", record);
		}
		if (recordType.equalsIgnoreCase("resident")) {
			PatientResidentRecordBO record = patientService.getResidentRecord(recordId);
			result.put("recordData", record);
		}
		
		List<RecordAssetVO> assetList = patientService.getRecordAssetList(recordId);
		result.put("assetList", assetList);
		
		return new ResponseEntity<Response>(Response.toSussess(result), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/globalConfig",  method = RequestMethod.POST)
	public ResponseEntity<Response> getDoctorGlobalConfig(HttpServletRequest request, HttpServletResponse resp, String username) {
		DoctorVO doctor = patientService.getDoctorInfoByUsername(username);
		
		List<HospitalRecordTypeVO> array = new ArrayList<HospitalRecordTypeVO>();
		//medical
		List<String> sectionNameList1 = Arrays.asList(new String[] {"主诉", "病史", "查体", "初步诊断", "诊治项目","建议"});
		List<String> SectionList1 = Arrays.asList(new String[] {"chief_complaint", "medical_history","examnination","primary_diagnosis", "treatment", "suggestion"});
		HospitalRecordTypeVO record1 = new HospitalRecordTypeVO();
		record1.setRecordType("medical");
		record1.setRecordTypeName("门诊病历");
		record1.setSectionList(SectionList1);
		record1.setSectionNameList(sectionNameList1);
		record1.setAssetTypeList(patientService.getRecordAssetTypeList("medical"));
		array.add(record1);
		
		//operation
		List<String> SectionNameList2 = Arrays.asList(new String[] {"术前诊断", "术后诊断", "手术名称", "手术经过", "手术体位",
				"手术切口","探查所见", "手术步骤", "引流物", "术毕病人情况"});
		List<String> sectionList2 = Arrays.asList(new String[] {"before_diagnosis", "after_diagnosis",
				"name","process", "posture", "incision","exploratory", "steps", "drainage", "finished_condition"});
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
		List<String> sectionList3 = Arrays.asList(new String[] {"in_state", "in_chi_diagnosis",
				"in_wes_diagnosis","process", "out_chi_diagnosis", "out_wes_diagnosis", "out_state", "suggestion"});
		HospitalRecordTypeVO record3 = new HospitalRecordTypeVO();
		record3.setRecordType("resident");
		record3.setRecordTypeName("住院记录");
		record3.setSectionList(sectionList3);
		record3.setSectionNameList(SectionNameList3);
		record3.setAssetTypeList(patientService.getRecordAssetTypeList("resident"));
		array.add(record3);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("recordTypeList", array);
		result.put("doctor", doctor);
		
		return new ResponseEntity<Response>(Response.toSussess(result), HttpStatus.OK);
	}
}