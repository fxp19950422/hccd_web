package cn.sportsdata.webapp.youth.web.controller.exchange;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import cn.sportsdata.webapp.youth.common.utils.DateUtil;
import cn.sportsdata.webapp.youth.common.vo.DepartmentVO;
import cn.sportsdata.webapp.youth.common.vo.login.LoginVO;
import cn.sportsdata.webapp.youth.common.vo.patient.FormCondition;
import cn.sportsdata.webapp.youth.common.vo.patient.MedicalRecordVO;
import cn.sportsdata.webapp.youth.common.vo.patient.PatientRegistRecord;
import cn.sportsdata.webapp.youth.common.vo.patient.ResidentRecord;
import cn.sportsdata.webapp.youth.service.exchange.ExchangeService;
import cn.sportsdata.webapp.youth.service.patient.PatientService;
import cn.sportsdata.webapp.youth.web.controller.BaseController;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/register")
public class RegisterController extends BaseController{
	private static Logger logger = Logger.getLogger(RegisterController.class);
	
	@Autowired
	ExchangeService exchangeService;
	
	@Autowired
	PatientService patientService;
	
	@RequestMapping(value = "/register_list",method = RequestMethod.GET)
	public String toCareListPage(HttpServletRequest request, Model model, String name, String idNumber,
			String careTimeStart, String careTimeEnd,FormCondition condition) {

		if(condition!=null){
			model.addAttribute("condition", condition);
		}
		return "register/register_list";
	}
	
	@RequestMapping(value = "/register_records",method = RequestMethod.GET)
	@ResponseBody
	public List<PatientRegistRecord> getMedicalRecord(HttpServletRequest request, Model model, String name,
			String idNumber, String careTimeStart, String careTimeEnd, boolean includeMedical,FormCondition condition) {

		LoginVO login = getCurrentUser(request);
		DepartmentVO dept = getCurrentDepartment(request);

		Date date = DateUtil.string2Date2(careTimeStart, "yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		careTimeEnd = careTimeStart + " 23:59:59";

		List<PatientRegistRecord> recordList = patientService.getRegisteRecordList(login.getHospitalUserInfo().getHospitalId(),
				login.getHospitalUserInfo().getUserIdinHospital(), name, calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
		if (!includeMedical) {
			List<MedicalRecordVO> medicalList = patientService.getHospitalMedicalRecordList(null, careTimeStart,
					careTimeEnd, "", idNumber, "1", dept.getDepartmentCode());
			List<String> ids2Move = new ArrayList<>();
			if (recordList != null && medicalList != null) {
				for (PatientRegistRecord record : recordList) {
					for (MedicalRecordVO medical : medicalList) {
						if (record.getPatientId().equals(medical.getPatientId())
								&& record.getVisitNo().intValue() == medical.getVisitNo().intValue()) {
							ids2Move.add(record.getId());
						}
					}
				}
			}
			List<PatientRegistRecord> newList = new ArrayList<>();
			if (ids2Move != null && ids2Move.size()>0) {
				for (PatientRegistRecord record : recordList) {
					if (!ids2Move.contains(record.getId())) {
						newList.add(record);
					} 
				}
				recordList.clear();
				recordList.addAll(newList);
			}

		} 

		return recordList;
	}
	
	@RequestMapping(value = "/register_detail",method = RequestMethod.GET)
	public String toRegistDetail(HttpServletRequest request,String id, Model model,FormCondition condition){
		
		PatientRegistRecord registRecord = patientService.getRegisteRecordById(id);
		
		List<PatientRecordBO> allList = patientService.getPatientRecords(id, registRecord.getName(),
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
		model.addAttribute("registId", id);
		model.addAttribute("condition", condition);
		return "register/register_detail";
	}
	
	
	@RequestMapping(value = "/register_detail_his_list",method = RequestMethod.GET)
	public List<PatientRecordBO> getRegistDetailHisList(HttpServletRequest request,String recordId, Model model){
		
		PatientRegistRecord registRecord = patientService.getRegisteRecordById(recordId);
		
		List<PatientRecordBO> list = patientService.getPatientRecords(recordId, registRecord.getName(),
				registRecord.getPatientId(), registRecord.getHospitalId());
		
		return list;
	}
	
	@RequestMapping(value = "/care_detail",method = RequestMethod.GET)
	public String toRecordDetail(String id, Model model){
		
		MedicalRecordVO record = patientService.getMedicalRecordById(id);
		model.addAttribute("record", record);
		model.addAttribute("id", id);
		return "care/medical_record_detail";
	}

	@RequestMapping(value = "/care_edit",method = RequestMethod.GET)
	public String toRecordEdit(String id, Model model){
		
		MedicalRecordVO record = patientService.getMedicalRecordById(id);
		model.addAttribute("record", record);
		model.addAttribute("id", id);
		return "care/medical_record_edit";
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
}
