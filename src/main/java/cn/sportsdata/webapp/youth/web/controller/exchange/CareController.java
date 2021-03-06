package cn.sportsdata.webapp.youth.web.controller.exchange;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectResult;

import cn.sportsdata.webapp.youth.common.bo.hospital.PatientRecordBO;
import cn.sportsdata.webapp.youth.common.constants.Constants;
import cn.sportsdata.webapp.youth.common.exceptions.SoccerProException;
import cn.sportsdata.webapp.youth.common.utils.DateUtil;
import cn.sportsdata.webapp.youth.common.utils.OSSUtil;
import cn.sportsdata.webapp.youth.common.utils.SecurityUtils;
import cn.sportsdata.webapp.youth.common.utils.StringUtil;
import cn.sportsdata.webapp.youth.common.vo.AssetVO;
import cn.sportsdata.webapp.youth.common.vo.DepartmentVO;
import cn.sportsdata.webapp.youth.common.vo.Response;
import cn.sportsdata.webapp.youth.common.vo.login.LoginVO;
import cn.sportsdata.webapp.youth.common.vo.patient.FormCondition;
import cn.sportsdata.webapp.youth.common.vo.patient.MedicalRecordVO;
import cn.sportsdata.webapp.youth.common.vo.patient.OpertaionRecord;
import cn.sportsdata.webapp.youth.common.vo.patient.PatientDocumentVO;
import cn.sportsdata.webapp.youth.common.vo.patient.PatientInHospital;
import cn.sportsdata.webapp.youth.common.vo.patient.PatientInfoVO;
import cn.sportsdata.webapp.youth.common.vo.patient.PatientRegistRecord;
import cn.sportsdata.webapp.youth.common.vo.patient.ResidentRecord;
import cn.sportsdata.webapp.youth.service.asset.AssetService;
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
	AssetService assetservice;
	
	@Autowired
	PatientService patientService;
	
	@RequestMapping(value = "/care_list",method = RequestMethod.GET)
	public String toCareListPage(HttpServletRequest request, Model model, String name, String idNumber,
			String careTimeStart, String careTimeEnd, String registId,FormCondition condition) {

		// DepartmentVO department = this.getCurrentDepartment(request);
		// List<DoctorVO> doctors =
		// exchangeService.getDoctors(department.getDepartmentCode(), radio ==
		// 1);
		//
		//
		// model.addAttribute("doctors", doctors);
		// model.addAttribute("radio", radio);
		if (StringUtil.isEmpty(registId)) {
			return "care/care_list";
		} else {
			PatientRegistRecord registRecord = patientService.getRegisteRecordById(registId);

			List<PatientRecordBO> allList = patientService.getPatientRecords(registId, registRecord.getName(),
					registRecord.getPatientId(), registRecord.getHospitalId());
			
			List<PatientRecordBO> todayList = new ArrayList<>();
			List<PatientRecordBO> list = new ArrayList<>();
			for(PatientRecordBO bo:allList){
				if("medical".equals(bo.getRecordType())){
					MedicalRecordVO medical = bo.getMedicalRecord();
					if(DateUtil.date2String(medical.getVisitDate()).equals(DateUtil.date2String(new Date()))){
						todayList.add(bo);
					} else {
						list.add(bo);
					}
				} else if ("resident".equals(bo.getRecordType())) {
					list.add(bo);
				}
			}
			
			model.addAttribute("record", registRecord);
			model.addAttribute("list", list);
			model.addAttribute("todayList", todayList);
			model.addAttribute("registId", registId);
			model.addAttribute("condition", condition);
			return "register/register_detail";
		}

	}
	
	@RequestMapping(value = "/medical_records",method = RequestMethod.GET)
	@ResponseBody
	public List<MedicalRecordVO> getMedicalRecord(HttpServletRequest request, Model model, String name, String idNumber,
			String careTimeStart, String careTimeEnd, boolean includeMedical) {

		String role = this.getCurrentRole(request);
		DepartmentVO dept = this.getCurrentDepartment(request);
		LoginVO user = getCurrentUser(request);
		
		
		if (StringUtils.isNotEmpty(role) && "director".equals(role)) {
			List<MedicalRecordVO> recordList = patientService.getHospitalMedicalRecordList(null, careTimeStart,
					careTimeEnd, name, idNumber, "1", dept.getDepartmentCode());
			return recordList;
		} else if (StringUtils.isNotEmpty(role) && "doctor".equals(role)) {
			List<MedicalRecordVO> recordList = patientService.getHospitalMedicalRecordList(
					user.getHospitalUserInfo().getId(), careTimeStart, careTimeEnd, name, idNumber, "1", null);
			return recordList;
		} else {
			return null;
		}

		// List<MedicalRecordVO> recordList =
		// patientService.getHospitalMedicalRecordList(null, careTimeStart,
		// careTimeEnd, name, idNumber, "1", "1");
		//
		// return recordList;
	}
	
	@RequestMapping(value = "/history_document",method = RequestMethod.GET)
	public String getHistoryDocument(Model model,String patientName, String id, String registId){
		model.addAttribute("patientName", patientName);
		model.addAttribute("id", id);
		model.addAttribute("registId", registId);
		return "care/history_document_list";
	}
	
	@RequestMapping(value = "/upload_photo",method = RequestMethod.GET)
	public String getUploadPhotoPage(Model model,String recordType,  String recordId){
		
		model.addAttribute("recordType", recordType);
		model.addAttribute("hospitalRecordId", recordId);
		return "care/upload_photo";
	}
	
	@RequestMapping(value = "/patient_documents",method = RequestMethod.GET)
	@ResponseBody
    public List<PatientDocumentVO> getPatientDocumentDirs(HttpServletRequest request, Model model, String patientName) throws SoccerProException {
		List<PatientDocumentVO> docList = patientService.getHistoryDocumentByPatientName(patientName);
		return docList;
	}
	
	@RequestMapping(value = "/download_history_document",method = RequestMethod.GET)
	public ResponseEntity<byte[]> downloadDoc(HttpServletRequest request, String filePath, HttpServletResponse response) throws IOException, SoccerProException{	
		String originalFilePath = SecurityUtils.decryptByAES(filePath);
		File targetFile = new File(originalFilePath);
		if(!targetFile.exists()) {
			return null;
		}
		InputStream is = null;
		OutputStream os = null;
		String targetFileName = targetFile.getName();
		String extName = targetFileName.substring(targetFileName.lastIndexOf(".") + 1);
		
		try {
			if(Arrays.asList(Constants.VALID_ATTACHMENTS_PIC_TYPES).contains(extName.toLowerCase())) {
				response.setContentType("image/*");
			} else {
				response.setContentType("application/octet-stream");
				
				String ua = request.getHeader("User-Agent");
				if(ua != null && (ua.toLowerCase().indexOf("msie") > -1 || (ua.toLowerCase().indexOf("trident") > -1 && ua.toLowerCase().indexOf("rv") > -1))) {
					targetFileName = URLEncoder.encode(targetFileName, "UTF8");
				} else {
					targetFileName = new String(targetFileName.getBytes("UTF-8"), "ISO8859-1");
				}
				
				response.setHeader("Content-disposition", "attachment; filename=" + targetFileName);
			}
			
			is = new FileInputStream(targetFile);
			os = response.getOutputStream();

			IOUtils.copy(is, os);
		} catch(Exception e) {
			//
		} finally {
			IOUtils.closeQuietly(is);
			IOUtils.closeQuietly(os);
		}
		
		return null;
		 
	}
	
	@RequestMapping(value = "/care_detail",method = RequestMethod.GET)
	public String toRecordDetail(String id, Model model,String registId,FormCondition condition){
		
		MedicalRecordVO record = patientService.getMedicalRecordById(id);
		
		model.addAttribute("age", getAge(record.getBirthday()));
		model.addAttribute("gender", "female".equalsIgnoreCase(record.getGender())?"女":"男"); 
		model.addAttribute("record", record);
		model.addAttribute("id", id);
		model.addAttribute("registId", registId);
		model.addAttribute("condition", condition);
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
	public String toRecordEdit(String id, Model model,String registId,FormCondition condition){
		PatientRegistRecord registRecord = patientService.getRegisteRecordById(registId);
		MedicalRecordVO record = patientService.getMedicalRecordById(id);
		
		List<String> uids = new ArrayList<>();
		uids.add(id);
		List<AssetVO> assets = exchangeService.getMedicalAsset(uids);
		
		model.addAttribute("record", record);
		model.addAttribute("id", id);
		model.addAttribute("registId", registId);
		model.addAttribute("condition", condition);
		model.addAttribute("age", registRecord.getAge());
		model.addAttribute("assets", assets);
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
		
		patientService.updateMedicalRecordById(record.getId(), record.getIllnessDesc(), record.getMedHistory(),
				record.getBodyExam(), record.getDiagDesc(), record.getTreatment(), record.getSuggestion(),record.getAccExam());
		
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
	public Object saveOperationRecord(HttpServletRequest request, OpertaionRecord record,String id, String avatar) {
		LoginVO login = getCurrentUser(request);
		
	    String accountID = login.getId();
	    String assetTypeId = "50";
	    String stageTypeId = "50";
	    String type = "operation";
		 AssetVO vo = new AssetVO();
	      
        vo.setCreator_id(accountID);
        vo.setOrg_id("");
        vo.setDisplay_name("");
        vo.setFile_extension("jpg");
        vo.setSize(0);
        vo.setPrivacy("protected");
        vo.setStatus("active");
        vo.setStorage_name(avatar);

        
		
		OpertaionRecord dbRecord = patientService.getOperationRecordByIdWithoutAssset(id);
		
//		dbRecord.setOperator(record.getOperator());
		dbRecord.setOpPrimary(record.getOpPrimary());
		dbRecord.setBeforeDiagnosis(record.getBeforeDiagnosis());
		dbRecord.setAfterDiagnosis(record.getAfterDiagnosis());
		dbRecord.setOperationDesc(record.getOperationDesc());
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
		assetservice.insertAsset(vo, login.getHospitalUserInfo().getHospitalId(), id, type, assetTypeId, stageTypeId);
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
		dbRecord.setBodyExam(record.getBodyExam());
		dbRecord.setIllHistory(record.getIllHistory());
		
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
	public Object saveOutHospitalRecord(HttpServletRequest request, ResidentRecord record,String id, String avatar) {
		LoginVO login = getCurrentUser(request);
		
	    String accountID = login.getId();
	    String assetTypeId = "50";
	    String stageTypeId = "51";
	    String type = "resident";
		 AssetVO vo = new AssetVO();
	      
        vo.setCreator_id(accountID);
        vo.setOrg_id("");
        vo.setDisplay_name("");
        vo.setFile_extension("jpg");
        vo.setSize(0);
        vo.setPrivacy("protected");
        vo.setStatus("active");
        vo.setStorage_name(avatar);
		
		
		ResidentRecord dbRecord = patientService.getResidentRecordById(id);
		dbRecord.setDiagnose(record.getDiagnose());
		patientService.updateResidentRecord(dbRecord);
		assetservice.insertAsset(vo, login.getHospitalUserInfo().getHospitalId(), id, type, assetTypeId, stageTypeId);
		return record;
	}
	

	@RequestMapping(value = "/medical_history_detail", method = RequestMethod.GET)
	public String toExchangeDetail(HttpServletRequest request, Model model, String registId,
			String startDate) throws Exception {

		List<OpertaionRecord> operations = new ArrayList<>();
		List<ResidentRecord> residents = new ArrayList<>();
		List<MedicalRecordVO> medicals = new ArrayList<>();
		
		PatientRegistRecord registRecord = patientService.getRegisteRecordById(registId);
		
		List<PatientRecordBO> list = patientService.getPatientRecords(registId, registRecord.getName(),
				registRecord.getPatientId(), registRecord.getHospitalId());
		
		for(PatientRecordBO record : list){
			if("medical".equals(record.getRecordType())){
				medicals.add(record.getMedicalRecord());
			} else if("resident".equals(record.getRecordType())){
				residents.add(record.getResidentRecord());
			} else if("operation".equals(record.getRecordType())){
				operations.add(record.getOperationRecord());
			}
		}
		
		model.addAttribute("medicalrecords", medicals);
		model.addAttribute("residentrecords", residents);
		model.addAttribute("operationRecords", operations);

		

		return "care/medical_history_detail";
	}
	
	@RequestMapping(value = "/pat_history_document", method = RequestMethod.GET)
	public String toHistoryDocument(HttpServletRequest request, Model model, String registId,String recordId,
			String startDate) throws Exception {
		
		List<OpertaionRecord> operations = new ArrayList<>();
		List<ResidentRecord> residents = new ArrayList<>();
		List<MedicalRecordVO> medicals = new ArrayList<>();
		
		PatientRegistRecord registRecord = patientService.getRegisteRecordById(registId);
		
//		List<PatientDocumentVO> docList = patientService.getHistoryDocumentByPatientName("尹飞虎");
		List<PatientDocumentVO> docList = patientService.getHistoryDocumentByPatientName(registRecord.getName());
		
		List<PatientRecordBO> list = patientService.getPatientRecords(registId, registRecord.getName(),
				registRecord.getPatientId(), registRecord.getHospitalId());
		
		for(PatientRecordBO record : list){
			if("medical".equals(record.getRecordType())){
				medicals.add(record.getMedicalRecord());
			} else if("resident".equals(record.getRecordType())){
				residents.add(record.getResidentRecord());
			} else if("operation".equals(record.getRecordType())){
				operations.add(record.getOperationRecord());
			}
		}
		List<PatientDocumentVO> docs = new ArrayList<>();
		
		List<String> uids = new ArrayList<>();
		uids.add(recordId);
		List<AssetVO> assets = exchangeService.getMedicalAsset(uids);
		for(AssetVO as : assets){
			PatientDocumentVO docvo = new PatientDocumentVO();
			docvo.setFilePath(as.getId());
			docvo.setStorage_name(as.getStorage_name());
			docvo.setFileName(as.getAssetTypeName());
			docs.add(docvo);
		}
		
		for(PatientDocumentVO doc :docList){
			if(!StringUtil.isBlank(doc.getFileName())&&doc.getFileName().endsWith("jpg")
					|| !StringUtil.isBlank(doc.getFileName())&&doc.getFileName().endsWith("JPG")){
				doc.setStorage_name("local");
				docs.add(doc);
			}
		}
		
		model.addAttribute("medicalrecords", medicals);
		model.addAttribute("residentrecords", residents);
		model.addAttribute("operationRecords", operations);
		model.addAttribute("docList", docs);
		
		
		
		return "care/pat_history_document";
	}
	
	@RequestMapping(value="/fileUpload", method = RequestMethod.POST, produces = "text/html; charset=utf-8")
	public void fileUpload(HttpServletRequest request,HttpServletResponse response,  @RequestParam MultipartFile[] files,
			String asset_stage_type_id, String record_asset_type_id, String hospitalId, String hospitalRecordId,
			String recordType) {
		JSONObject obj = new JSONObject();
		response.setContentType("application/json");
		PrintWriter writer = null;
		System.out.println("asset_stage_type_id:"+asset_stage_type_id);
		System.out.println("record_asset_type_id:"+record_asset_type_id);
		
		try {
			writer = response.getWriter();
			LoginVO loginVO = getCurrentUser(request);
			String accountID = loginVO.getId();
			hospitalId = loginVO.getHospitalUserInfo().getHospitalId();
			String ret = "";
			for (MultipartFile uploadFile : files) {

				String timesp = new Date().getTime() + "";
				String idx = "0";
				String assetId = hospitalRecordId + "_" + timesp + "_" + idx;
				OSSClient client = OSSUtil.getOSSClient();
				PutObjectResult result = client.putObject(OSSUtil.BUCKET_NAME, assetId,
						uploadFile.getInputStream());

				ret = assetId;
				OSSUtil.shutdownOSSClient(client);
				String fileName = assetId;
				String assetTypeId = record_asset_type_id;
				String assetStageId = asset_stage_type_id;
				String createdTime = DateUtil.date2String(new Date(), "yyyy-MM-dd HH:mm:ss");

				assetservice.insertHospitalRecordOSSAsset(fileName, hospitalId, hospitalRecordId, recordType,
						assetTypeId, assetStageId, createdTime, "0", "oss");
			}
			JSONArray array = new JSONArray();
			JSONObject obj1 = new JSONObject();
			obj1.put("url", ret);
			array.add(obj1);
			obj.put("files", array);
		} catch (Exception e) {
			logger.error("Error occurs while uploading file", e);
		}finally {
			writer.write(obj.toString());
            writer.close();
		}

	}
	
	
	@RequestMapping(value = "/operation_request_list",method = RequestMethod.GET)
    public String toOperationRequestListPage(HttpServletRequest request, Model model, FormCondition condition,String registId) {
		
		model.addAttribute("condition", condition);
		return "care/operation_request_list";
	}
}
