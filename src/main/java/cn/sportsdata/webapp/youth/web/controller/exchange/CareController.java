package cn.sportsdata.webapp.youth.web.controller.exchange;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.sportsdata.webapp.youth.common.vo.patient.MedicalRecordVO;
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
    public String toCareListPage(HttpServletRequest request, Model model, String name, String idNumber, String careTimeStart, String careTimeEnd) {
		
//		DepartmentVO department = this.getCurrentDepartment(request);
//		List<DoctorVO> doctors = exchangeService.getDoctors(department.getDepartmentCode(), radio == 1);
//		
//		
//		model.addAttribute("doctors", doctors);
//		model.addAttribute("radio", radio);
		return "care/care_list";
	}
	
	@RequestMapping(value = "/medical_records",method = RequestMethod.GET)
	@ResponseBody
    public List<MedicalRecordVO> getMedicalRecord(HttpServletRequest request, Model model, String name, String idNumber, String careTimeStart, String careTimeEnd) {
		
		List<MedicalRecordVO> recordList = patientService.getHospitalMedicalRecordList(null, careTimeStart, careTimeEnd, name, idNumber, "1", "100001");

		return recordList;
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
	
	@ResponseBody
	@RequestMapping(value = "/save_record", method = RequestMethod.POST)
	public Object saveRecord(HttpServletRequest request, MedicalRecordVO record) {
		
		patientService.updateMedicalRecordById(record.getId(), record.getIllnessDesc(), record.getMedHistory(), record.getBodyExam(), record.getDiagDesc(),record.getTreatment(), record.getSuggestion());
		
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
