package cn.sportsdata.webapp.youth.web.controller.singletraining;

import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
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
import org.springframework.web.servlet.ModelAndView;

import cn.sportsdata.webapp.youth.common.bo.SingleTrainingBO;
import cn.sportsdata.webapp.youth.common.constants.Constants;
import cn.sportsdata.webapp.youth.common.utils.CommonUtils;
import cn.sportsdata.webapp.youth.common.utils.StringUtil;
import cn.sportsdata.webapp.youth.common.vo.EquipmentVO;
import cn.sportsdata.webapp.youth.common.vo.OrgVO;
import cn.sportsdata.webapp.youth.common.vo.Response;
import cn.sportsdata.webapp.youth.common.vo.SingleTrainingVO;
import cn.sportsdata.webapp.youth.common.vo.TacticsPlaygroundVO;
import cn.sportsdata.webapp.youth.common.vo.TacticsTypeVO;
import cn.sportsdata.webapp.youth.common.vo.TacticsVO;
import cn.sportsdata.webapp.youth.common.vo.UserVO;
import cn.sportsdata.webapp.youth.common.vo.login.LoginVO;
import cn.sportsdata.webapp.youth.service.equipment.EquipmentService;
import cn.sportsdata.webapp.youth.service.singletraining.SingleTrainingService;
import cn.sportsdata.webapp.youth.service.tactics.TacticsService;
import cn.sportsdata.webapp.youth.service.tacticsplayground.TacticsPlaygroundService;
import cn.sportsdata.webapp.youth.service.tacticstype.TacticsTypeService;
import cn.sportsdata.webapp.youth.service.user.UserService;
import cn.sportsdata.webapp.youth.web.controller.BaseController;

@Controller
@RequestMapping("/singletraining")
public class SingleTrainingController  extends BaseController {
	private static Logger logger = Logger.getLogger(SingleTrainingController.class);
	private static String category = "soccer";
	
	@Autowired
	private SingleTrainingService singletrainingService;
	
	@Autowired
	private EquipmentService equipmentService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TacticsTypeService tacticsTypeService;
	
	@Autowired
	private TacticsPlaygroundService tacticsPlaygroundService;
	
	@Autowired
	private TacticsService tacticsService;
	
	@ResponseBody
	@RequestMapping(value = "/saveSingleTraining", method = RequestMethod.POST)
    public Response saveSingleTraining(HttpServletRequest request, @RequestBody SingleTrainingBO singleTrainingBo) {
		String id = singleTrainingBo.getSingleTrainingBO().getId();
		boolean isCreate = StringUtil.isBlank(id);
		SingleTrainingVO singleTrainingVO=new SingleTrainingVO();
		try {
			BeanUtils.copyProperties(singleTrainingVO, singleTrainingBo.getSingleTrainingBO());
			OrgVO orgVO = getCurrentOrg(request);
			singleTrainingVO.setOrgID(orgVO.getId());
			boolean isSuccess = false;
			if(isCreate) {
				isSuccess = singletrainingService.createSingleTraining(singleTrainingVO,singleTrainingBo.getSingleTrainingExtInfoList(),singleTrainingBo.getSingleTrainingEquipmentInfoList());
			} else {
				isSuccess = singletrainingService.updateSingleTraining(singleTrainingVO,singleTrainingBo.getSingleTrainingExtInfoList(),singleTrainingBo.getSingleTrainingEquipmentInfoList());
			}
			return isSuccess ? Response.toSussess(String.valueOf(id)) : Response.toFailure(500, isCreate ? "insert new singletraining error" : "update singletraining " + id + " error");
		} catch(Exception e) {
			logger.error(isCreate ? "Error occurs while creating singletraining: " : "Error occurs while updating singletraining " + id + ": ", e);
		}
		return Response.toFailure(500, isCreate ? "insert new singletraining error" : "update singletraining " + id + " error");
	}
	
	@RequestMapping("/showSingleTrainingList")
    public String showSingleTrainingList(HttpServletRequest request, Model model) {
		OrgVO orgVO = getCurrentOrg(request);
		List<SingleTrainingVO> singletraininglist = singletrainingService.getSingleTrainingList(orgVO.getId());
		model.addAttribute("singletraininglist", singletraininglist);
		return "singletraining/singletraining_list";
	}
	
	@RequestMapping("/showSingleTrainingDetail")
    public String showSingleTrainingDetail(HttpServletRequest request, Model model, @RequestParam String id, @RequestParam(required=false) String action) {
		SingleTrainingVO singletraining = singletrainingService.getSingleTrainingByID(id);
		model.addAttribute("singletraining", singletraining);
		
		String serverUrl = CommonUtils.getServerUrl(request);
		TacticsVO tactics=new TacticsVO();
		String stID=singletraining.getTacticsId();
		if(!"".equals(stID)&&(!StringUtils.isBlank(stID))&& !"null".equals(stID)){
			tactics = tacticsService.getTacticsByID(stID);
			if(tactics!=null && tactics.getImgName()!=null){
				String surfix = "";
				try {
					Timestamp tmp = Timestamp.valueOf(tactics.getImgAssetVo().getLast_update());
					surfix = "&_lastupdate="+tmp.getTime();
				} catch(Exception e) {
					surfix = "";
				}
				tactics.setImgUrl(serverUrl+"hagkFile/asset?id="+tactics.getImgName()+surfix);
			}
		}
		model.addAttribute("tactics", tactics);
		
		boolean isViewAction = false;
		if ("view".equals(action)) {
			isViewAction = true;
		}
		model.addAttribute("isViewAction", isViewAction);
		
		return "singletraining/singletraining_detail";
	}
	
	@RequestMapping("/showSingleTrainingEdit")
    public String showSingleTrainingEdit(HttpServletRequest request, Model model, @RequestParam(required=false) String id) {
		OrgVO orgVO = getCurrentOrg(request);
		List<EquipmentVO> equipmentList=equipmentService.getEquipmentList(orgVO.getId());
		model.addAttribute("equipmentList", equipmentList);
		
		LoginVO loginVO = getCurrentUser(request);
		if(loginVO == null) {
			return null;
		}
		List<TacticsTypeVO> tacticsTypeListCommon = tacticsTypeService.getTacticsTypeCommon(category);
		for(int i =0; i< tacticsTypeListCommon.size(); i++) {
			if(tacticsTypeListCommon.get(i).getId() == 1){
				tacticsTypeListCommon.remove(i);
				break;
			}
		}
		List<TacticsTypeVO> tacticsTypeList = tacticsTypeService.getTacticsTypeByOrgId(orgVO.getId(), category);
		tacticsTypeList.addAll(tacticsTypeListCommon);
		
		List<TacticsPlaygroundVO> tacticsPlaygroundList = tacticsPlaygroundService.getTacticsPlaygroundCommon(category);
		
		List<UserVO> playerList = userService.getPlayersByOrgId(orgVO.getId());
		
		model.addAttribute("tacticsTypeList", tacticsTypeList);	
		
		model.addAttribute("playgroundTypeList",tacticsPlaygroundList);
		
		model.addAttribute("playerList",playerList);
		
		model.addAttribute("pFlag","single");
		
		if(StringUtils.isBlank(id)) { 
			model.addAttribute("isCreateSingle", true);
			model.addAttribute("isCreate", true);
		} else {
			model.addAttribute("isCreateSingle", false);
			SingleTrainingVO singletraining = singletrainingService.getSingleTrainingByID(id);
			model.addAttribute("singletraining", singletraining);
			
			TacticsVO tactiscVo = null;
			String tactics_id =singletraining.getTacticsId();
			if(!StringUtil.isBlank(tactics_id) && !"null".equals(tactics_id)){
				model.addAttribute("isCreate", false);
				tactiscVo = tacticsService.getTacticsByID(tactics_id);
				if(tactiscVo!=null && tactiscVo.getImgName()!=null){
					String serverUrl = CommonUtils.getServerUrl(request);
					String surfix = "";
					try {
						Timestamp tmp = Timestamp.valueOf(tactiscVo.getImgAssetVo().getLast_update());
						surfix = "&_lastupdate="+tmp.getTime();
					} catch(Exception e) {
						surfix = "";
					}
					tactiscVo.setImgUrl(serverUrl+"hagkFile/asset?id="+tactiscVo.getImgName()+surfix);
				}

				model.addAttribute("tactics",tactiscVo);
			}else{
				model.addAttribute("isCreate", true);
			}
		}
		
		return "singletraining/singletraining_edit";
	}
	@ResponseBody
	@RequestMapping(value = "/deleteSingleTraining", method = RequestMethod.DELETE)
    public Response deleteSingleTraining(HttpServletRequest request, @RequestParam String id) {
		if(StringUtils.isBlank(id) ) {
			return Response.toFailure(400, "singleTraining id " + id + " is invalid");
		}
		boolean isSuccess = singletrainingService.deleteSingleTraining(id);
		return isSuccess ? Response.toSussess(null) : Response.toFailure(500, "delete singleTraining " + id + " error");
	}
}
