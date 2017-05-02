package cn.sportsdata.webapp.youth.web.controller.exchange;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.sportsdata.webapp.youth.common.vo.DepartmentVO;
import cn.sportsdata.webapp.youth.common.vo.patient.DoctorVO;
import cn.sportsdata.webapp.youth.common.vo.patient.PatientInHospital;
import cn.sportsdata.webapp.youth.common.vo.patient.ResidentRecord;
import cn.sportsdata.webapp.youth.common.vo.patient.ShiftMeetingVO;
import cn.sportsdata.webapp.youth.service.exchange.ExchangeService;
import cn.sportsdata.webapp.youth.service.patient.PatientService;
import cn.sportsdata.webapp.youth.web.controller.BaseController;

@Controller
@RequestMapping("/exchange")
public class ExchangeController extends BaseController{
	private static Logger logger = Logger.getLogger(ExchangeController.class);
	
	@Autowired
	ExchangeService exchangeService;
	
	@Autowired
	PatientService patientService;
	
	@RequestMapping(value = "/exchange_list",method = RequestMethod.GET)
    public String toTestManagePage(HttpServletRequest request, Model model, @RequestParam(required=false,defaultValue = "0") int radio) {
		
		DepartmentVO department = this.getCurrentDepartment(request);
		List<DoctorVO> doctors = exchangeService.getDoctors(department.getDepartmentCode(), radio == 1);
		
		
		model.addAttribute("doctors", doctors);
		model.addAttribute("radio", radio);
		
		List<ShiftMeetingVO> recordList = patientService.getTodayExchangeRecordList();
		
		
		
		
		return "exchange/exchange_list";
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
    public String toExchangeDetail(HttpServletRequest request, Model model,  String obj, String anotherOperation) throws Exception {
		
		
//		String document = new String(Base64.decode(obj.getBytes()));
//		
//		JSONObject jsonObj = JSONObject.fromObject(document);
//		JSONArray array = jsonObj.getJSONArray("uids");
//		List<String> uids = new ArrayList<String>();
//		
//		for (int i = 0; i < array.size();i++){
//			uids.add(array.getJSONObject(i).getString("uid"));
//		}
//		
//		List<ResidentRecord> medicalRecords = exchangeService.getMedicalRecordByPatientIds(uids);
//		System.out.println("the size of the records is " + medicalRecords.size());
//		if (medicalRecords.size() <= 0){
//			for (int i =0; i < 10; i++){
//				ResidentRecord vo = new ResidentRecord();
//				vo.setName("王爷"+i);
//				medicalRecords.add(vo);
//			}
//		}
		List<ShiftMeetingVO> recordList = patientService.getTodayExchangeRecordList();

		List<String> operationList = new ArrayList<String>();
		List<String> residentList = new ArrayList<String>();
		List<String> patientInHospitalList = new ArrayList<String>();
		for (ShiftMeetingVO record : recordList){
			if ("operation".equals(record.getRecordType())){
				operationList.add(record.getRecordId());
			} else if ("resident".equals(record.getRecordType())){
				residentList.add(record.getRecordId());
			} else if ("patientInhospital".equals(record.getRecordType())){
				patientInHospitalList.add(record.getRecordId());
			}
		}
		
		List<PatientInHospital>  operationRecordList = null;
		if (operationList.size() > 0){
			operationRecordList = exchangeService.getExchangeOperationRecordList(operationList);
		}
		
		List<PatientInHospital>  patientInHospitalRecordList = null;
		if (patientInHospitalList.size() > 0){
			patientInHospitalRecordList = exchangeService.getExchangePatientInHospitalRecord(patientInHospitalList);
		}
		
		List<ResidentRecord>  residentRecordList = null;
		if (residentList.size() > 0){
			residentRecordList = exchangeService.getExchangeResidentRecord(residentList);
		}
		
		model.addAttribute("medicalrecords", operationRecordList);
		
		model.addAttribute("patient_in_hospital_records", patientInHospitalRecordList);
		
		model.addAttribute("residentrecords", residentRecordList);
		
		List<List<String>> page = new ArrayList<List<String>>();
		
		if (!StringUtils.isEmpty(anotherOperation.trim())){
			String[] arrays = anotherOperation.split("<br>");
			
			List<String> lstArr = new ArrayList<String>();
			for (int i = 0; i < arrays.length; i++){
				if (!StringUtils.isEmpty(arrays[i])){
					lstArr.add(arrays[i]);
				}
			}
			
			
			List<String> tmp = new ArrayList<String>();
			for (int i = 0; i < lstArr.size(); i++){
				if (i%4 == 0 ){
					if (i != 0) {
						page.add(tmp);
						tmp = new ArrayList<String>();
					}
					tmp.add(lstArr.get(i));
				} else {
					tmp.add(lstArr.get(i));
				}
			}
			page.add(tmp);
		}
		model.addAttribute("anotherOperation", page);
		
		return "exchange/exchange_detail";
	}
}
