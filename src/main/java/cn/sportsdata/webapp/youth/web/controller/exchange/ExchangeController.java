package cn.sportsdata.webapp.youth.web.controller.exchange;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.sportsdata.webapp.youth.common.vo.patient.DoctorVO;
import cn.sportsdata.webapp.youth.common.vo.patient.MedicalRecordVO;
import cn.sportsdata.webapp.youth.service.exchange.ExchangeService;
import cn.sportsdata.webapp.youth.web.controller.BaseController;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/exchange")
public class ExchangeController extends BaseController{
	private static Logger logger = Logger.getLogger(ExchangeController.class);
	
	@Autowired
	ExchangeService exchangeService;
	
	@RequestMapping(value = "/exchange_list",method = RequestMethod.GET)
    public String toTestManagePage(HttpServletRequest request, Model model, @RequestParam(required=false,defaultValue = "0") int radio) {
		
		List<DoctorVO> doctors = exchangeService.getDoctors("100001");
		
		
		model.addAttribute("doctors", doctors);
		return "exchange/exchange_list";
	}

	@RequestMapping(value = "/exchange_detail",method = RequestMethod.POST)
    public String toExchangeDetail(HttpServletRequest request, Model model,  @RequestBody JSONObject obj) {
		
		JSONArray array = obj.getJSONArray("uids");
		List<String> uids = new ArrayList<String>();
		
		for (int i = 0; i < array.size();i++){
			uids.add(array.getString(i));
		}
		
		List<MedicalRecordVO> medicalRecords = exchangeService.getMedicalRecordByPatientIds(uids);
		for (int i =0; i < 10; i++){
			MedicalRecordVO vo = new MedicalRecordVO();
			vo.setPatient_name("王爷"+i);
			medicalRecords.add(vo);
		}
		
		model.addAttribute("medicalrecords", medicalRecords);
		return "exchange/exchange_detail";
	}
}
