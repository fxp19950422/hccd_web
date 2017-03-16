package cn.sportsdata.webapp.youth.web.controller.equipment;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.sportsdata.webapp.youth.common.vo.EquipmentVO;
import cn.sportsdata.webapp.youth.common.vo.OrgVO;
import cn.sportsdata.webapp.youth.common.vo.Response;
import cn.sportsdata.webapp.youth.service.equipment.EquipmentService;
import cn.sportsdata.webapp.youth.web.controller.BaseController;

@Controller
@RequestMapping("/equipment")
public class EquipmentController extends BaseController {
	private static Logger logger = Logger.getLogger(EquipmentController.class);
	
	@Autowired
	private EquipmentService equipmentService;
	
	@RequestMapping("/showEquipmentList")
    public String showEquipmentList(HttpServletRequest request, Model model) {
		OrgVO orgVO = getCurrentOrg(request);
		List<EquipmentVO> equipmentList = equipmentService.getEquipmentList(orgVO.getId());
		model.addAttribute("equipmentList", equipmentList);
		
		return "equipment/equipment_list";
	}
	
	@RequestMapping("/showEquipmentDetail")
    public String showEquipmentDetail(HttpServletRequest request, Model model, @RequestParam String id) {
		EquipmentVO equipment = equipmentService.getEquipmentByID(id);
		model.addAttribute("equipment", equipment);
		
		return "equipment/equipment_detail";
	}
	
	@RequestMapping("/showEquipmentEdit")
    public String showEquipmentEdit(HttpServletRequest request, Model model, @RequestParam(required=false) String id) {
		if(StringUtils.isBlank(id)) { 
			model.addAttribute("isCreate", true);
		} else {
			model.addAttribute("isCreate", false);
			EquipmentVO equipment = equipmentService.getEquipmentByID(id);
			model.addAttribute("equipment", equipment);
		}
		
		return "equipment/equipment_edit";
	}
	
	@ResponseBody
	@RequestMapping(value = "/saveEquipment", method = RequestMethod.POST)
    public Response saveEquipment(HttpServletRequest request, @RequestBody EquipmentVO equipmentVO) {
		if(equipmentVO == null) {
			return Response.toFailure(500, "equipment data can not be null");
		}
		String id = equipmentVO.getId();
		OrgVO orgVO = getCurrentOrg(request);
		equipmentVO.setOrgId(orgVO.getId());
		boolean isCreate = (StringUtils.isBlank(id));
		try {
			boolean isSuccess = false;
			if(isCreate) {
				isSuccess = equipmentService.createEquipment(equipmentVO);
			} else {
				isSuccess = equipmentService.updateEquipment(equipmentVO);
			}
			
			return isSuccess ? Response.toSussess(String.valueOf(id)) : Response.toFailure(500, isCreate ? "insert new equipment error" : "update equipment " + id + " error");
		} catch(Exception e) {
			logger.error(isCreate ? "Error occurs while creating equipment: " : "Error occurs while updating equipment " + id + ": ", e);
		}
		return Response.toFailure(500, isCreate ? "insert new equipment error" : "update equipment " + id + " error");
	}
	
	@ResponseBody
	@RequestMapping(value = "/deleteEquipment", method = RequestMethod.DELETE)
    public Response deleteEquipment(HttpServletRequest request, @RequestParam String id) {
		if(StringUtils.isBlank(id)) {
			return Response.toFailure(400, "equipment id " + id + " is invalid");
		}
		boolean isSuccess = equipmentService.deleteEquipment(id);
		
		return isSuccess ? Response.toSussess(null) : Response.toFailure(500, "delete equipment " + id + " error");
	}
}
