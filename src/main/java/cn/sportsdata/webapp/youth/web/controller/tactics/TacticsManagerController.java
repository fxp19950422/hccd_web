package cn.sportsdata.webapp.youth.web.controller.tactics;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.sportsdata.webapp.youth.common.constants.Constants;
import cn.sportsdata.webapp.youth.common.utils.CommonUtils;
import cn.sportsdata.webapp.youth.common.utils.StringUtil;
import cn.sportsdata.webapp.youth.common.vo.FormationTypeVO;
import cn.sportsdata.webapp.youth.common.vo.OrgVO;
import cn.sportsdata.webapp.youth.common.vo.Response;
import cn.sportsdata.webapp.youth.common.vo.TacticsPlaygroundVO;
import cn.sportsdata.webapp.youth.common.vo.TacticsVO;
import cn.sportsdata.webapp.youth.common.vo.UserVO;
import cn.sportsdata.webapp.youth.common.vo.login.LoginVO;
import cn.sportsdata.webapp.youth.common.vo.match.PlayerMatchStatisticsVO;
import cn.sportsdata.webapp.youth.common.vo.starters.StartersPreviewsVO;
import cn.sportsdata.webapp.youth.common.vo.starters.StartersVO;
import cn.sportsdata.webapp.youth.common.vo.utraining.UtrainingTaskEvaluationVO;
import cn.sportsdata.webapp.youth.service.formationtype.FormationTypeService;
import cn.sportsdata.webapp.youth.service.starters.StartersService;
import cn.sportsdata.webapp.youth.service.tactics.TacticsService;
import cn.sportsdata.webapp.youth.service.tacticsplayground.TacticsPlaygroundService;
import cn.sportsdata.webapp.youth.service.user.UserService;
import cn.sportsdata.webapp.youth.web.controller.BaseController;

@Controller
@RequestMapping("/tactics")
public class TacticsManagerController extends BaseController {
	private static Logger logger = Logger.getLogger(TacticsManagerController.class);
	private static String category = "soccer";
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TacticsService tacticsService;
	
	@Autowired
	private StartersService startersService;
	
	@Autowired
	private FormationTypeService formationTypeService;
	
	@Autowired
	private TacticsPlaygroundService tacticsPlaygroundService;
	
	@RequestMapping("/showStarterSettings")
    public String showStarterSettings(HttpServletRequest request, Model model, @RequestParam(required=false) String formation_type_id) {
		logger.info("TacticsManagerController.showStarterSettings");

		LoginVO loginVO = getCurrentUser(request);
		OrgVO orgVO = getCurrentOrg(request);
		if(loginVO == null) {
			return null;
		}
		List<FormationTypeVO> formationTypeListCommon = formationTypeService.getFormationTypeCommon(category);
		List<FormationTypeVO> formationTypeList = formationTypeService.getFormationTypeByOrgId(orgVO.getId(), category);
		formationTypeList.addAll(formationTypeListCommon);
		
		long defaultTypeId = 0;
		FormationTypeVO defaultType = new FormationTypeVO();
		defaultType.setId(defaultTypeId);
		defaultType.setName("全部阵型");
		formationTypeList.add(defaultType);
		
		List<StartersPreviewsVO> startersList = null;
		if(StringUtils.isBlank(formation_type_id) || !StringUtils.isNumeric(formation_type_id)) {
			defaultType.setChecked(true);
			startersList = startersService.getStartersByUserId(loginVO.getId(), orgVO.getId(), category);
		} else {
			defaultTypeId = Long.parseLong(formation_type_id);
			if (defaultTypeId == 0) {
				defaultType.setChecked(true);
				startersList = startersService.getStartersByUserId(loginVO.getId(), orgVO.getId(), category);
			} else {
				for(FormationTypeVO obj : formationTypeList) {
					
					if (obj.getId() == defaultTypeId) {
						obj.setChecked(true);
						break;
					}
				}
				startersList = startersService.getStartersByTypeAndUserId(loginVO.getId(), orgVO.getId(), defaultTypeId);
			}
		}
		
		String serverUrl = CommonUtils.getServerUrl(request);
		for(StartersPreviewsVO obj : startersList) {
			String surfix = "";
			try {
				Timestamp tmp = Timestamp.valueOf(obj.getLast_update());
				surfix = "&_lastupdate="+tmp.getTime();
			} catch(Exception e) {
				surfix = "";
			}
			obj.setImgUrl(serverUrl+"hagkFile/asset?id="+obj.getImgName()+surfix);
		}
		model.addAttribute("formationTypeList", formationTypeList);
		model.addAttribute("startersList", startersList);
		
		return "tactics/starters_setting_list";
	}
	
	@ResponseBody
	@RequestMapping(value = "/getStartersData", produces = "text/html; charset=utf-8")
    public String getStartersData(HttpServletRequest request, @RequestParam String formation_type_id) {
		LoginVO loginVO = getCurrentUser(request);
		OrgVO orgVO = getCurrentOrg(request);
		
		long formationTypeId = 1;
		if(!StringUtils.isBlank(formation_type_id) && StringUtils.isNumeric(formation_type_id)) {
			formationTypeId = Long.parseLong(formation_type_id);
		}
		
		List<StartersPreviewsVO> startersList = null;
		if(formationTypeId == 1) {
			startersList = startersService.getStartersByUserId(loginVO.getId(), orgVO.getId(), category);
		} else {
			startersList = startersService.getStartersByTypeAndUserId(loginVO.getId(), orgVO.getId(), formationTypeId);
		}
		String serverUrl = CommonUtils.getServerUrl(request);

		JSONArray array = new JSONArray();
		for(StartersPreviewsVO starter : startersList) {
			JSONObject json = new JSONObject();
			json.put("name", starter.getName());
			json.put("imgName", starter.getImgName());
			json.put("tacticId", starter.getTacticsId());
			String surfix = "";
			try {
				Timestamp tmp = Timestamp.valueOf(starter.getLast_update());
				surfix = "&_lastupdate="+tmp.getTime();
			} catch(Exception e) {
				surfix = "";
			}
			starter.setImgUrl(serverUrl+"hagkFile/asset?id="+starter.getImgName()+surfix);
			json.put("imgUrl", starter.getImgUrl());
			array.put(json);
		}
		
		return array.toString();
	}
	
	@RequestMapping("/showStartersEdit")
    public String showStartersEdit(HttpServletRequest request, Model model, @RequestParam(required=false) String startersId) {
		LoginVO loginVO = getCurrentUser(request);
		OrgVO orgVO = getCurrentOrg(request);
		if(loginVO == null) {
			return null;
		}
		List<FormationTypeVO> formationTypeListCommon = formationTypeService.getFormationTypeCommon(category);
		List<FormationTypeVO> formationTypeList = formationTypeService.getFormationTypeByOrgId(orgVO.getId(), category);
		formationTypeList.addAll(formationTypeListCommon);
		
		List<TacticsPlaygroundVO> tacticsPlaygroundList = tacticsPlaygroundService.getTacticsPlaygroundCommon(category);
		
		List<UserVO> playerList = userService.getPlayersByOrgId(orgVO.getId());
		
		List<UtrainingTaskEvaluationVO> playerLatestEvaList = userService.getPlayersLatestEvaluation(orgVO.getId());
		
		StartersVO startersVo = null;
		
		if(StringUtils.isBlank(startersId)) {
			model.addAttribute("isCreate", true);
		} else {
			model.addAttribute("isCreate", false);

			startersVo = startersService.getStartersById(startersId);
			if(startersVo == null) {
				return null;
			}
			for (FormationTypeVO tmp : formationTypeList) {
				if(tmp.getId()==startersVo.getFormation_id()) {
					tmp.setChecked(true);
					break;
				}
			}
			
			TacticsVO tactiscVo = tacticsService.getTacticsByID(startersVo.getTacticsId());
			if(tactiscVo == null) {
				return null;
			}
			for (TacticsPlaygroundVO tmp : tacticsPlaygroundList) {
				if(tmp.getId()==tactiscVo.getPlayground_id()) {
					tmp.setChecked(true);
					break;
				}
			}
			tacticsService.loadTacticsFiles(tactiscVo);
			String serverUrl = CommonUtils.getServerUrl(request);
			String surfix = "";
			try {
				Timestamp tmp = Timestamp.valueOf(tactiscVo.getLast_update());
				surfix = "&_lastupdate="+tmp.getTime();
			} catch(Exception e) {
				surfix = "";
			}
			tactiscVo.setImgUrl(serverUrl+"hagkFile/asset?id="+tactiscVo.getImgName()+surfix);
			startersVo.setTacticsVo(tactiscVo);
			
			model.addAttribute("tactics", tactiscVo);
			model.addAttribute("starters", startersVo);
		}
		
		model.addAttribute("formationTypeList", formationTypeList);
		model.addAttribute("playerList", playerList);
		model.addAttribute("playerLatestEvaList", playerLatestEvaList);
		model.addAttribute("playgroundTypeList", tacticsPlaygroundList);
		
		List<PlayerMatchStatisticsVO> playerGameRecordList = userService.getPlayersGameRecord(orgVO.getId(), playerList);
		model.addAttribute("playerGameRecordList", playerGameRecordList);
		
		model.addAttribute("isCopied", false);
		return "tactics/starters_edit";
	}
	
	@RequestMapping("/copyStarters")
	public String copyStarters(HttpServletRequest request, Model model, @RequestParam(required=true) String startersId) {
		OrgVO orgVO = getCurrentOrg(request);
		LoginVO loginVO = getCurrentUser(request);
		if(loginVO == null) {
			return null;
		}
		
		if(StringUtils.isBlank(startersId)) {
			return null;
		}

		TacticsVO tacticsVo = tacticsService.getTacticsByID(startersId);
		
		tacticsVo.setName(tacticsVo.getName() + Constants.TACTICS_COPY);
		
		tacticsService.loadTacticsFiles(tacticsVo);
		String serverUrl = CommonUtils.getServerUrl(request);
		String surfix = "";
		try {
			Timestamp tmp = Timestamp.valueOf(tacticsVo.getLast_update());
			surfix = "&_lastupdate="+tmp.getTime();
		} catch(Exception e) {
			surfix = "";
		}
		tacticsVo.setImgUrl(serverUrl+"hagkFile/asset?id="+tacticsVo.getImgName()+surfix);
		
		model.addAttribute("isCopied", true);
		model.addAttribute("isCreate", false);
		model.addAttribute("tactics", tacticsVo);
		
		List<FormationTypeVO> formationTypeListCommon = formationTypeService.getFormationTypeCommon(category);
		List<FormationTypeVO> formationTypeList = formationTypeService.getFormationTypeByOrgId(orgVO.getId(), category);
		formationTypeList.addAll(formationTypeListCommon);
		
		List<TacticsPlaygroundVO> tacticsPlaygroundList = tacticsPlaygroundService.getTacticsPlaygroundCommon(category);
		
		List<UserVO> playerList = userService.getPlayersByOrgId(orgVO.getId());
		
		List<UtrainingTaskEvaluationVO> playerLatestEvaList = userService.getPlayersLatestEvaluation(orgVO.getId());
		
		StartersVO startersVo = startersService.getStartersById(startersId);
		if(startersVo == null) {
			return null;
		}
		for (FormationTypeVO tmp : formationTypeList) {
			if(tmp.getId()==startersVo.getFormation_id()) {
				tmp.setChecked(true);
				break;
			}
		}
		
		for (TacticsPlaygroundVO tmp : tacticsPlaygroundList) {
			if(tmp.getId()==tacticsVo.getPlayground_id()) {
				tmp.setChecked(true);
				break;
			}
		}
		
		startersVo.setTacticsId(null);
		model.addAttribute("starters", startersVo);
		model.addAttribute("formationTypeList", formationTypeList);
		model.addAttribute("playerList", playerList);
		model.addAttribute("playerLatestEvaList", playerLatestEvaList);
		model.addAttribute("playgroundTypeList", tacticsPlaygroundList);
		
		List<PlayerMatchStatisticsVO> playerGameRecordList = userService.getPlayersGameRecord(orgVO.getId(), playerList);
		model.addAttribute("playerGameRecordList", playerGameRecordList);
		model.addAttribute("srcTacticsId", startersId);
		return "tactics/starters_edit";
	}
	
	@RequestMapping("/showStartersView")
    public String showStartersView(HttpServletRequest request, Model model, @RequestParam(required=true) String startersId) {
		LoginVO loginVO = getCurrentUser(request);
		OrgVO orgVO = getCurrentOrg(request);
		if(loginVO == null) {
			return null;
		}
		
		List<UserVO> playerList = userService.getPlayersByOrgId(orgVO.getId());
		
		List<UtrainingTaskEvaluationVO> playerLatestEvaList = userService.getPlayersLatestEvaluation(orgVO.getId());
		
		StartersVO startersVo = null;
		
		startersVo = startersService.getStartersById(startersId);
		if(startersVo == null) {
			return null;
		}
		
		TacticsVO tactiscVo = tacticsService.getTacticsByID(startersVo.getTacticsId());
		if(tactiscVo == null) {
			return null;
		}

		tacticsService.loadTacticsFiles(tactiscVo);
		String serverUrl = CommonUtils.getServerUrl(request);
		String surfix = "";
		try {
			Timestamp tmp = Timestamp.valueOf(tactiscVo.getLast_update());
			surfix = "&_lastupdate="+tmp.getTime();
		} catch(Exception e) {
			surfix = "";
		}
		tactiscVo.setImgUrl(serverUrl+"hagkFile/asset?id="+tactiscVo.getImgName()+surfix);
		startersVo.setTacticsVo(tactiscVo);
		
		model.addAttribute("tactics", tactiscVo);
		model.addAttribute("starters", startersVo);
		
		model.addAttribute("playerList", playerList);
		model.addAttribute("playerLatestEvaList", playerLatestEvaList);
		List<PlayerMatchStatisticsVO> playerGameRecordList = userService.getPlayersGameRecord(orgVO.getId(), playerList);
		model.addAttribute("playerGameRecordList", playerGameRecordList);
		
		if(loginVO.getId().equals(startersVo.getCreator_id())) {
			model.addAttribute("isCreator", true);
		}
		
		return "tactics/starters_view";
	}
	
	@ResponseBody
	@RequestMapping(value = "/saveStarters", method = RequestMethod.POST)
	public Response saveStarters(HttpServletRequest request, HttpServletResponse response, @RequestBody StartersVO startersVo) throws IOException {
		LoginVO loginVO = getCurrentUser(request);
		OrgVO orgVO = getCurrentOrg(request);
		if(loginVO == null) {
			return null;
		}
		
		String tacticsId = startersVo.getTacticsId();
		TacticsVO tacticsVo = startersService.getTacticsVOData(startersVo);
		if (!StringUtil.isBlank(tacticsId)) {
			tacticsVo.setId(startersVo.getTacticsId());
		}
		
		String serverUrl = CommonUtils.getServerUrl(request);
		tacticsVo.setImgUrl(serverUrl);
		boolean bSuccess = tacticsService.saveTacticsFiles(tacticsVo, loginVO.getId(), orgVO.getId());
		if(bSuccess==false) {
			return Response.toFailure(500, "create new tactics file error");
		}
		
		String startersId = startersVo.getTacticsId();
		if(StringUtil.isBlank(startersId)) {
			// new
			startersVo.setCreator_id(loginVO.getId());
			startersVo.setOrg_id(orgVO.getId());
			tacticsVo.setCreator_id(loginVO.getId());
			tacticsVo.setOrg_id(orgVO.getId());
			startersVo.setTacticsId(startersService.createStarters(startersVo, tacticsVo));
			if(StringUtil.isBlank(startersVo.getTacticsId())) {
				return Response.toFailure(500, "insert new tactics error");
			}
		} else {
			// update
			boolean bUpdated =  startersService.updateStarters(startersVo, tacticsVo);
			if(bUpdated == false) {
				return Response.toFailure(500, "update tactics error");
			}
		}
		
		startersService.saveStartersPlayer(startersVo);
		
		Map<String, Object> paramList = new HashMap<String, Object>();
		paramList.put("tacticsId", startersVo.getTacticsId());
		paramList.put("creator_id", tacticsVo.getCreator_id());
		paramList.put("org_id", tacticsVo.getOrg_id());
		paramList.put("tacticsdata", tacticsVo.getTacticsdata());
		paramList.put("url", tacticsVo.getImgUrl());
		paramList.put("imgName", tacticsVo.getImgName());
		
		// must clean old files
		tacticsService.cleanOldFiles();
		return Response.toSussess(paramList);
	}
	
	@ResponseBody
	@RequestMapping(value = "/deleteStarters", method = RequestMethod.POST)
	public Object deleteStarters(HttpServletRequest request, String startersID) {
		try {
			LoginVO currentAccount = this.getCurrentUser(request);
			StartersVO startersVo = null;
			boolean result = false;
			if(!StringUtils.isBlank(startersID)) {
				startersVo = startersService.getStartersById(startersID);
			}
			
			if (startersVo == null) {
				return Response.toFailure(-1, "该首发设置已在其他地方被删除");
			}
			
			if(currentAccount.getId().equals(startersVo.getCreator_id()) == false) {
				return Response.toFailure(-1, "只有创建者才能删除该首发设置");
			}
			startersVo.setStatus("deleted");
			result = startersService.deleteStarters(startersVo);
			
			if (result){
				return Response.toSussess("");
			} else {
				return Response.toFailure(-1, "删除失败");
			}
			
		} catch (RuntimeException ex) {
			return Response.toFailure(-1, ex.getMessage());
		}
	}
}


