package cn.sportsdata.webapp.youth.web.controller.exchange;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.sportsdata.webapp.youth.common.bo.hospital.PatientRecordBO;
import cn.sportsdata.webapp.youth.common.constants.Constants;
import cn.sportsdata.webapp.youth.common.utils.DateUtil;
import cn.sportsdata.webapp.youth.common.utils.StringUtil;
import cn.sportsdata.webapp.youth.common.vo.DepartmentVO;
import cn.sportsdata.webapp.youth.common.vo.login.LoginVO;
import cn.sportsdata.webapp.youth.common.vo.patient.FormCondition;
import cn.sportsdata.webapp.youth.common.vo.patient.MedicalRecordVO;
import cn.sportsdata.webapp.youth.common.vo.patient.OpertaionRecord;
import cn.sportsdata.webapp.youth.common.vo.patient.PatientInHospital;
import cn.sportsdata.webapp.youth.common.vo.patient.PatientInfoVO;
import cn.sportsdata.webapp.youth.common.vo.patient.PatientRegistRecord;
import cn.sportsdata.webapp.youth.common.vo.patient.ResidentRecord;
import cn.sportsdata.webapp.youth.service.exchange.ExchangeService;
import cn.sportsdata.webapp.youth.service.patient.PatientService;
import cn.sportsdata.webapp.youth.web.controller.BaseController;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/care")
public class CareController extends BaseController{
	private static Logger logger = Logger.getLogger(CareController.class);
	
	@Autowired
	ExchangeService exchangeService;
	
	@Autowired
	PatientService patientService;
	
	@RequestMapping(value = "/care_list",method = RequestMethod.GET)
    public String toCareListPage(HttpServletRequest request, Model model, String name, String idNumber, String careTimeStart, String careTimeEnd,String registId) {
		
//		DepartmentVO department = this.getCurrentDepartment(request);
//		List<DoctorVO> doctors = exchangeService.getDoctors(department.getDepartmentCode(), radio == 1);
//		
//		
//		model.addAttribute("doctors", doctors);
//		model.addAttribute("radio", radio);
		if(StringUtil.isEmpty(registId)){
			return "care/care_list";
		} else {
			PatientRegistRecord registRecord = patientService.getRegisteRecordById(registId);
			
			List<PatientRecordBO> list = patientService.getPatientRecords(registId, registRecord.getName(),
					registRecord.getPatientId(), registRecord.getHospitalId());
			
			model.addAttribute("record", registRecord);
			model.addAttribute("list", list);
			model.addAttribute("registId", registId);
			return "register/register_detail";
		}
		
	}
	
	@RequestMapping(value = "/medical_records",method = RequestMethod.GET)
	@ResponseBody
    public List<MedicalRecordVO> getMedicalRecord(HttpServletRequest request, Model model, String name, String idNumber, String careTimeStart, String careTimeEnd) {
		
		
		String role = this.getCurrentRole(request);
		DepartmentVO dept = this.getCurrentDepartment(request);
		LoginVO user = getCurrentUser(request);
		if (StringUtils.isNotEmpty(role) && "director".equals(role)){
			List<MedicalRecordVO> recordList = patientService.getHospitalMedicalRecordList(null, careTimeStart, careTimeEnd, name, idNumber, "1", dept.getDepartmentCode());
			return recordList;
		} else if (StringUtils.isNotEmpty(role) && "doctor".equals(role)){
			List<MedicalRecordVO> recordList = patientService.getHospitalMedicalRecordList(user.getHospitalUserInfo().getId(), careTimeStart, careTimeEnd, name, idNumber, "1", null);
			return recordList;
		} else {
			return null;
		}
		
//		List<MedicalRecordVO> recordList = patientService.getHospitalMedicalRecordList(null, careTimeStart, careTimeEnd, name, idNumber, "1", "1");
//
//		return recordList;
	}
	
	@RequestMapping(value = "/care_detail",method = RequestMethod.GET)
	public String toRecordDetail(String id, Model model,String registId){
		
		MedicalRecordVO record = patientService.getMedicalRecordById(id);
		model.addAttribute("record", record);
		model.addAttribute("id", id);
		model.addAttribute("registId", registId);
		return "care/medical_record_detail";
	}
	
	@RequestMapping(value = "/add_care",method = RequestMethod.GET)
	public String toAddDetail(HttpServletRequest request,Model model,String registId){
		LoginVO user = getCurrentUser(request);
		
		PatientRegistRecord registRecord = patientService.getRegisteRecordById(registId);
		MedicalRecordVO record = new MedicalRecordVO();
		record.setId(UUID.randomUUID().toString());
		record.setRealName(registRecord.getName());
		record.setPatientId(registRecord.getPatientId());
		record.setVisitDate(registRecord.getVisitDate());
		record.setVisitNo(registRecord.getVisitNo());
		record.setHospitalId(user.getHospitalUserInfo().getHospitalId());
		record.setDoctorNo(user.getHospitalUserInfo().getUserIdinHospital());
		record.setDoctor(user.getHospitalUserInfo().getUserName());
		
		model.addAttribute("record", record);
		model.addAttribute("registId", registId);
		return "care/medical_record_add";
	}

	@RequestMapping(value = "/care_edit",method = RequestMethod.GET)
	public String toRecordEdit(String id, Model model,String registId){
		
		MedicalRecordVO record = patientService.getMedicalRecordById(id);
		model.addAttribute("record", record);
		model.addAttribute("id", id);
		model.addAttribute("registId", registId);
		return "care/medical_record_edit";
	}
	

	@RequestMapping(value = "/care_insert",method = RequestMethod.GET)
	public String toRecordInsert(Model model){
		
		MedicalRecordVO record = new MedicalRecordVO();
		model.addAttribute("record", record);
		return "care/medical_record_edit";
	}
	
	@RequestMapping(value = "/resident_detail",method = RequestMethod.GET)
	public String toResidentRecordDetail(String id, Model model,String registId){
		
		ResidentRecord record = patientService.getResidentRecordById(id);
		model.addAttribute("record", record);
		model.addAttribute("id", id);
		model.addAttribute("registId", registId);
		return "care/resident_record_detail";
	}
	
	@RequestMapping(value = "/resident_edit",method = RequestMethod.GET)
	public String toResidentRecordEdit(String id, Model model,String registId){
		
		ResidentRecord record = patientService.getResidentRecordById(id);
		model.addAttribute("record", record);
		model.addAttribute("id", id);
		model.addAttribute("registId", registId);
		return "care/resident_record_edit";
	}
	
	@RequestMapping(value = "/operation_detail",method = RequestMethod.GET)
	public String toOperationRecordDetail(String id, Model model,String registId,FormCondition condition){
		
		OpertaionRecord record = patientService.getOperationRecordById(id);
		List<String> ids = new ArrayList<>();
		ids.add(record.getPatientId());
		List<PatientInfoVO> pList = patientService.getPatients(ids);
		record.setPatientName(pList==null?"":pList.get(0).getRealName());
		model.addAttribute("record", record);
		model.addAttribute("id", id);
		model.addAttribute("registId", registId);
		model.addAttribute("condition", condition);
		return "care/operation_record_detail";
	}
	
	@RequestMapping(value = "/operation_edit",method = RequestMethod.GET)
	public String toOperationRecordEdit(String id, Model model,String registId,FormCondition condition){
		
		OpertaionRecord record = patientService.getOperationRecordById(id);
		List<String> ids = new ArrayList<>();
		ids.add(record.getPatientId());
		List<PatientInfoVO> pList = patientService.getPatients(ids);
		record.setPatientName(pList==null?"":pList.get(0).getRealName());
		model.addAttribute("record", record);
		model.addAttribute("id", id);
		model.addAttribute("registId", registId);
		model.addAttribute("condition", condition);
		return "care/operation_record_edit";
	}
	
	
	
	
	@RequestMapping(value = "/operation_list",method = RequestMethod.GET)
    public String toOperationListPage(HttpServletRequest request, Model model, FormCondition condition,String registId) {
		
//		DepartmentVO department = this.getCurrentDepartment(request);
//		List<DoctorVO> doctors = exchangeService.getDoctors(department.getDepartmentCode(), radio == 1);
//		
//		
//		model.addAttribute("doctors", doctors);
//		model.addAttribute("radio", radio);
		if(StringUtil.isEmpty(registId)){
			
//			LoginVO login = getCurrentUser(request);
//			DepartmentVO dept = getCurrentDepartment(request);
//			
//			Date date = DateUtil.string2Date2(careTimeStart,"yyyy-MM-dd");
//			Calendar calendar = Calendar.getInstance();
//			calendar.setTime(date);
//			
//			Map<String, Object> result = patientService.getOperationRecordList(login.getHospitalUserInfo().getHospitalId(),
//					login.getHospitalUserInfo().getUserIdinHospital(), name, calendar.get(Calendar.YEAR),
//					calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
//			model.addAttribute("record", result);
			model.addAttribute("condition", condition);
			return "care/operation_list";
		} else {
			PatientRegistRecord registRecord = patientService.getRegisteRecordById(registId);
			
			List<PatientRecordBO> list = patientService.getPatientRecords(registId, registRecord.getName(),
					registRecord.getPatientId(), registRecord.getHospitalId());
			
			model.addAttribute("record", registRecord);
			model.addAttribute("list", list);
			model.addAttribute("registId", registId);
			return "register/register_detail";
		}
		
	}
	
	@RequestMapping(value = "/operation_records",method = RequestMethod.GET)
	@ResponseBody
    public List<OpertaionRecord> getOperationRecords(HttpServletRequest request, Model model, FormCondition condition) {
		
		LoginVO login = getCurrentUser(request);
		DepartmentVO dept = getCurrentDepartment(request);
		
		Date date = DateUtil.string2Date2(condition.getCareTimeStart(),"yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		

		String role = this.getCurrentRole(request);
		if (StringUtils.isNotEmpty(role) && "director".equals(role)){
			List<OpertaionRecord> list = patientService.searchDirectorOperationRecordList(login.getHospitalUserInfo().getHospitalId(),
					dept.getDepartmentCode(), condition.getPatName(), calendar.get(Calendar.YEAR),
					calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
			return list;
		} else if (StringUtils.isNotEmpty(role) && "doctor".equals(role)){
			List<OpertaionRecord> list = patientService.searchOperationRecordList(login.getHospitalUserInfo().getHospitalId(),
					login.getHospitalUserInfo().getUserIdinHospital(), condition.getPatName(), calendar.get(Calendar.YEAR),
					calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
			return list;
		} else {
			return null;
		}
		
		
		
		
//		Map<String, Object> result = patientService.getOperationRecordList(login.getHospitalUserInfo().getHospitalId(),
//				login.getHospitalUserInfo().getUserIdinHospital(), name, calendar.get(Calendar.YEAR),
//				calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
	}
	
	@RequestMapping(value = "/download_medical_record",method = RequestMethod.GET)
	public ResponseEntity<byte[]> download(HttpServletRequest request, String id, HttpServletResponse response) throws IOException{
		
		MedicalRecordVO record = patientService.getMedicalRecordById(id);

        // 获取模板文件
       
        InputStream is = this.getClass().getResourceAsStream("/templates/medical_record_template.doc" );
        ByteArrayOutputStream ostream = null;
        try {
//            FileInputStream in = new FileInputStream(templateFile);
        	HWPFDocument hwpfDocument = new HWPFDocument(is);
            // 替换读取到的 word 模板内容的指定字段
            Map<String,String> params = new HashMap<>();
            
            params.put("${name}", StringUtils.isEmpty(record.getRealName())?"":record.getRealName());
            params.put("${gender}", "female".equalsIgnoreCase(record.getGender())?"女":"男");  
            params.put("${age}", getAge(record.getBirthday()));
            params.put("${visitDate}", DateUtil.date2String(record.getVisitDate(), "yyyy-MM-dd"));
            params.put("${illnessDesc}", StringUtils.isEmpty(record.getIllnessDesc())?"":record.getIllnessDesc());
            params.put("${medHistory}", StringUtils.isEmpty(record.getMedHistory())?"":record.getMedHistory());
            params.put("${bodyExam}", StringUtils.isEmpty(record.getBodyExam())?"":record.getBodyExam());
            params.put("${diagDesc}", StringUtils.isEmpty(record.getDiagDesc())?"":record.getDiagDesc());
            params.put("${treatment}", StringUtils.isEmpty(record.getTreatment())?"":record.getTreatment());
            params.put("${suggestion}", StringUtils.isEmpty(record.getSuggestion())?"":record.getSuggestion());
            params.put("${pDate}", DateUtil.date2String(new Date(), "yyyy-MM-dd"));
            params.put("${doctorName}", StringUtils.isEmpty(record.getName())?"":record.getName());
            Range range = hwpfDocument.getRange();
            for(Map.Entry<String,String> entry:params.entrySet()){
                range.replaceText(entry.getKey(),entry.getValue());
            }
            // 输出 word 内容文件流，提供下载
            ostream = new ByteArrayOutputStream();
         
//            OutputStream servletOS = response.getOutputStream();
            String name = record.getRealName() + "_" + DateUtil.date2String(record.getVisitDate(), "yyyy-MM-dd") + ".doc";
            hwpfDocument.write(ostream);
            String dfileName = new String( name.getBytes("gbk"), "iso8859-1"); 
           
            HttpHeaders headers = new HttpHeaders(); headers.setContentType(MediaType.APPLICATION_OCTET_STREAM); 
            headers.setContentDispositionFormData("attachment", dfileName);
//           
            return new ResponseEntity<byte[]>(ostream.toByteArray(), headers, HttpStatus.CREATED);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
		 
	}
	
	public  String getAge(Date birthDay)  {  
		if (birthDay == null) return "未知";
		
        Calendar cal = Calendar.getInstance();  
  
        if (cal.before(birthDay)) {  
        	 return "未知"; 
        }  
        int yearNow = cal.get(Calendar.YEAR);  
        int monthNow = cal.get(Calendar.MONTH);  
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);  
        cal.setTime(birthDay);  
  
        int yearBirth = cal.get(Calendar.YEAR);  
        int monthBirth = cal.get(Calendar.MONTH);  
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);  
  
        int age = yearNow - yearBirth;  
  
        if (monthNow <= monthBirth) {  
            if (monthNow == monthBirth) {  
                if (dayOfMonthNow < dayOfMonthBirth) age--;  
            }else{  
                age--;  
            }  
        }  
        return String.valueOf(age);  
    }  
	
	@ResponseBody
	@RequestMapping(value = "/save_record", method = RequestMethod.POST)
	public Object saveRecord(HttpServletRequest request, MedicalRecordVO record) {
		
		patientService.updateMedicalRecordById(record.getId(), record.getIllnessDesc(), record.getMedHistory(), record.getBodyExam(), record.getDiagDesc(),record.getTreatment(), record.getSuggestion());
		
		return record;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/add_record", method = RequestMethod.POST)
	public Object addRecord(HttpServletRequest request, MedicalRecordVO record) {
		record.setMemo(Constants.PLATFORM_CLOUD);
		record.setStatus("active");
		patientService.insertMedicalRecord(record);
		return record;
	}
	
	@ResponseBody
	@RequestMapping(value = "/save_resident_record", method = RequestMethod.POST)
	public Object saveResidentRecord(HttpServletRequest request, ResidentRecord record,String id) {
		
		ResidentRecord dbRecord = patientService.getResidentRecordById(id);
		
		dbRecord.setInChiDiagnosis(record.getInChiDiagnosis());
		dbRecord.setInWesDiagnosis(record.getInWesDiagnosis());
		dbRecord.setProcess(record.getProcess());
		dbRecord.setOutChiDiagnosis(record.getOutChiDiagnosis());
		dbRecord.setOutWesDiagnosis(record.getOutWesDiagnosis());
		dbRecord.setSuggestion(record.getSuggestion());
		dbRecord.setInState(record.getInState());
		dbRecord.setOutState(record.getOutState());
		
		patientService.updateResidentRecord(dbRecord);
		
		return record;
	}
	
	@ResponseBody
	@RequestMapping(value = "/save_operation_record", method = RequestMethod.POST)
	public Object saveOperationRecord(HttpServletRequest request, OpertaionRecord record,String id) {
		
		OpertaionRecord dbRecord = patientService.getOperationRecordById(id);
		
		dbRecord.setOperator(record.getOperator());
		dbRecord.setOpPrimary(record.getOpPrimary());
		dbRecord.setBeforeDiagnosis(record.getBeforeDiagnosis());
		dbRecord.setAfterDiagnosis(record.getAfterDiagnosis());
		dbRecord.setOperationDescription(record.getOperationDescription());
		dbRecord.setProcess(record.getProcess());
		dbRecord.setPosture(record.getPosture());
		dbRecord.setIncision(record.getIncision());
		dbRecord.setExploratory(record.getExploratory());
		dbRecord.setSteps(record.getSteps());
		dbRecord.setAnaesthesiaMethod(record.getAnaesthesiaMethod());
		dbRecord.setDrainage(record.getDrainage());
		dbRecord.setFinishedCondition(record.getFinishedCondition());
		patientService.updateOperationRecord(dbRecord);
		
		return record;
	}
	
//	@RequestMapping(value = "/exchange_detail",method = RequestMethod.POST)
//    public String toExchangeDetail(HttpServletRequest request, Model model,  @RequestBody JSONObject obj) {
//		
//		JSONArray array = obj.getJSONArray("uids");
//		List<String> uids = new ArrayList<String>();
//		
//		for (int i = 0; i < array.size();i++){
//			uids.add(array.getJSONObject(i).getString("uid"));
//		}
//		
//		List<ResidentRecord> medicalRecords = exchangeService.getMedicalRecordByPatientIds(uids);
//		if (medicalRecords.size() <= 0){
//			for (int i =0; i < 10; i++){
//				ResidentRecord vo = new ResidentRecord();
//				vo.setName("王爷"+i);
//				medicalRecords.add(vo);
//			}
//		}
//		
//		model.addAttribute("medicalrecords", medicalRecords);
//		return "exchange/exchange_detail";
//	}
	
	@RequestMapping(value = "/exchange_detail",method = RequestMethod.GET)
    public String toExchangeDetail(HttpServletRequest request, Model model,  String obj) throws Exception {
		
		
		String document = new String(Base64.decode(obj.getBytes()));
		
		JSONObject jsonObj = JSONObject.fromObject(document);
		JSONArray array = jsonObj.getJSONArray("uids");
		List<String> uids = new ArrayList<String>();
		
		for (int i = 0; i < array.size();i++){
			uids.add(array.getJSONObject(i).getString("uid"));
		}
		
		List<ResidentRecord> medicalRecords = exchangeService.getMedicalRecordByPatientIds(uids);
		System.out.println("the size of the records is " + medicalRecords.size());
		if (medicalRecords.size() <= 0){
			for (int i =0; i < 10; i++){
				ResidentRecord vo = new ResidentRecord();
				vo.setName("王爷"+i);
				medicalRecords.add(vo);
			}
		}
		
		model.addAttribute("medicalrecords", medicalRecords);
		return "exchange/exchange_detail";
	}
	
	
	@RequestMapping(value = "/in_hospital_list",method = RequestMethod.GET)
	public String toInHospitalListPage(HttpServletRequest request, Model model, FormCondition condition,String registId){
		model.addAttribute("condition", condition);
		return "care/in_hospital_list";
	}
	
	@RequestMapping(value = "/in_hospital_records",method = RequestMethod.GET)
	@ResponseBody
    public List<PatientInHospital> getInHospitalRecords(HttpServletRequest request, Model model, FormCondition condition) {
		
		LoginVO login = getCurrentUser(request);
		DepartmentVO dept = getCurrentDepartment(request);
		
		Date date = DateUtil.string2Date2(condition.getCareTimeStart(),"yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, -1);
		
		
		
		String role = this.getCurrentRole(request);
		if (StringUtils.isNotEmpty(role) && "director".equals(role)){
			List<PatientInHospital> list = patientService.searchDirectorInHospitalRecordList(login.getHospitalUserInfo().getHospitalId(),
					dept.getDepartmentCode(), condition.getPatName(), calendar.getTime(), date);
			return list;
		} else if (StringUtils.isNotEmpty(role) && "doctor".equals(role)){
			List<PatientInHospital> list = patientService.searchInHospitalRecordList(login.getHospitalUserInfo().getHospitalId(),
					login.getHospitalUserInfo().getUserIdinHospital(), condition.getPatName(), calendar.getTime(), date);
			return list;
		} else {
			return null;
		}
		
//		List<PatientInHospital> list = patientService.searchInHospitalRecordList(login.getHospitalUserInfo().getHospitalId(),
//				login.getHospitalUserInfo().getUserIdinHospital(), condition.getPatName(), null, null);
		
		
		
//		Map<String, Object> result = patientService.(login.getHospitalUserInfo().getHospitalId(),
//				login.getHospitalUserInfo().getUserIdinHospital(), name, calendar.get(Calendar.YEAR),
//				calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
		
		
	}
	
	@RequestMapping(value = "/in_hospital_record_detail",method = RequestMethod.GET)
	public String toInHospitalRecordDetail(String id, Model model,String registId,FormCondition condition){
		
		PatientInHospital record = patientService.searchPatientInHospitalById(id);
//		List<String> ids = new ArrayList<>();
//		ids.add(record.getPatientId());
//		List<PatientInfoVO> pList = patientService.getPatients(ids);
//		record.setPatientName(pList==null?"":pList.get(0).getRealName());
		model.addAttribute("record", record);
		model.addAttribute("id", id);
		model.addAttribute("registId", registId);
		model.addAttribute("condition", condition);
		return "care/in_hospital_record_detail";
	}
	
	@RequestMapping(value = "/in_hospital_record_edit",method = RequestMethod.GET)
	public String toInHospitalRecordEdit(String id, Model model,String registId,FormCondition condition){
		
		PatientInHospital record = patientService.searchPatientInHospitalById(id);
		model.addAttribute("record", record);
		model.addAttribute("id", id);
		model.addAttribute("registId", registId);
		model.addAttribute("condition", condition);
		return "care/in_hospital_record_edit";
	}
	
	@ResponseBody
	@RequestMapping(value = "/save_in_hospital_record", method = RequestMethod.POST)
	public Object saveInHospitalRecord(HttpServletRequest request, PatientInHospital record,String id) {
		
		PatientInHospital dbRecord = patientService.searchPatientInHospitalById(id);
		dbRecord.setOpPrimary(record.getOpPrimary());
		dbRecord.setDiagnosis(record.getDiagnosis());
		dbRecord.setSupplementaryExamination(record.getSupplementaryExamination());
		dbRecord.setRecordDiscussion(record.getRecordDiscussion());
		patientService.updatePatientInHospital(dbRecord);
		
		return record;
	}
	
	@RequestMapping(value = "/out_hospital_list",method = RequestMethod.GET)
	public String toOutHospitalListPage(HttpServletRequest request, Model model, FormCondition condition,String registId){
		model.addAttribute("condition", condition);
		return "care/out_hospital_list";
	}
	
	@RequestMapping(value = "/out_hospital_records",method = RequestMethod.GET)
	@ResponseBody
    public List<ResidentRecord> getOutHospitalRecords(HttpServletRequest request, Model model, FormCondition condition) {
		
		LoginVO login = getCurrentUser(request);
		DepartmentVO dept = getCurrentDepartment(request);
		
		Date date = DateUtil.string2Date2(condition.getCareTimeStart(),"yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, -1);
		
		
		String role = this.getCurrentRole(request);
		
		if (StringUtils.isNotEmpty(role) && "director".equals(role)){
			List<ResidentRecord> list = patientService.searchDirectorResidentRecordList(login.getHospitalUserInfo().getHospitalId(),
					dept.getDepartmentCode(), condition.getPatName(), calendar.getTime(), date);
			return list;
		} else if (StringUtils.isNotEmpty(role) && "doctor".equals(role)){
			List<ResidentRecord> list = patientService.searchResidentRecordList(login.getHospitalUserInfo().getHospitalId(),
					login.getHospitalUserInfo().getUserIdinHospital(), condition.getPatName(), calendar.getTime(), date);
			return list;
		} else {
			return null;
		}
		
//		List<PatientInHospital> list = patientService.searchInHospitalRecordList(login.getHospitalUserInfo().getHospitalId(),
//				login.getHospitalUserInfo().getUserIdinHospital(), condition.getPatName(), null, null);
		
//		List<ResidentRecord> list = patientService.searchDirectorResidentRecordList(login.getHospitalUserInfo().getHospitalId(),
//				login.getHospitalUserInfo().getDeptId(), condition.getPatName(), date, null);
		
//		Map<String, Object> result = patientService.getOperationRecordList(login.getHospitalUserInfo().getHospitalId(),
//				login.getHospitalUserInfo().getUserIdinHospital(), name, calendar.get(Calendar.YEAR),
//				calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
		
	}
	
	@RequestMapping(value = "/out_hospital_record_detail",method = RequestMethod.GET)
	public String toOutHospitalRecordDetail(String id, Model model,String registId,FormCondition condition){
		
		ResidentRecord record = patientService.getResidentRecordById(id);
//		List<String> ids = new ArrayList<>();
//		ids.add(record.getPatientId());
//		List<PatientInfoVO> pList = patientService.getPatients(ids);
//		record.setPatientName(pList==null?"":pList.get(0).getRealName());
		model.addAttribute("record", record);
		model.addAttribute("id", id);
		model.addAttribute("registId", registId);
		model.addAttribute("condition", condition);
		return "care/out_hospital_record_detail";
	}
	
	@RequestMapping(value = "/out_hospital_record_edit",method = RequestMethod.GET)
	public String toOutHospitalRecordEdit(String id, Model model,String registId,FormCondition condition){
		
		ResidentRecord record = patientService.getResidentRecordById(id);
		
		model.addAttribute("record", record);
		model.addAttribute("id", id);
		model.addAttribute("registId", registId);
		model.addAttribute("condition", condition);
		return "care/out_hospital_record_edit";
	}
	
	@ResponseBody
	@RequestMapping(value = "/save_out_hospital_record", method = RequestMethod.POST)
	public Object saveOutHospitalRecord(HttpServletRequest request, ResidentRecord record,String id) {
		
		ResidentRecord dbRecord = patientService.getResidentRecordById(id);
		dbRecord.setDiagnose(record.getDiagnose());
		patientService.updateResidentRecord(dbRecord);
		
		return record;
	}
}
