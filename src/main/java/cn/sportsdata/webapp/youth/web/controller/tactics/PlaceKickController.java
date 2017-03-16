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
import cn.sportsdata.webapp.youth.common.vo.OrgVO;
import cn.sportsdata.webapp.youth.common.vo.Response;
import cn.sportsdata.webapp.youth.common.vo.PlaceKickTypeVO;
import cn.sportsdata.webapp.youth.common.vo.PlaceKickVO;
import cn.sportsdata.webapp.youth.common.vo.TacticsPlaygroundVO;
import cn.sportsdata.webapp.youth.common.vo.TacticsVO;
import cn.sportsdata.webapp.youth.common.vo.UserVO;
import cn.sportsdata.webapp.youth.common.vo.login.LoginVO;
import cn.sportsdata.webapp.youth.service.placeKick.PlaceKickService;
import cn.sportsdata.webapp.youth.service.placeKicktype.PlaceKickTypeService;
import cn.sportsdata.webapp.youth.service.tactics.TacticsService;
import cn.sportsdata.webapp.youth.service.tacticsplayground.TacticsPlaygroundService;
import cn.sportsdata.webapp.youth.service.user.UserService;
import cn.sportsdata.webapp.youth.web.controller.BaseController;

@Controller
@RequestMapping("/placeKick")
public class PlaceKickController extends BaseController {
	private static Logger logger = Logger.getLogger(PlaceKickController.class);
	private static String category = "soccer";
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TacticsService tacticsService;
	
	@Autowired
	private PlaceKickService placeKickService;
	
	@Autowired
	private PlaceKickTypeService placeKickTypeService;
	
	@Autowired
	private TacticsPlaygroundService tacticsPlaygroundService;
	
	@RequestMapping("/showPlaceKickList")
    public String showPlaceKickList(HttpServletRequest request, Model model, @RequestParam(required=false) String place_kick_type_id) {
		logger.info("PlaceKickController.showPlaceKickList");

		LoginVO loginVO = getCurrentUser(request);
		OrgVO orgVO = getCurrentOrg(request);
		if(loginVO == null) {
			return null;
		}
		
		String defaultTypeId = null;
		List<PlaceKickTypeVO> placeKickTypeListCommon = placeKickTypeService.getPlaceKickTypeCommon(category);
		List<PlaceKickTypeVO> placeKickTypeList = placeKickTypeService.getPlaceKickTypeByOrgId(orgVO.getId(), category);
		placeKickTypeList.addAll(placeKickTypeListCommon);
		
		PlaceKickTypeVO defaultType = new PlaceKickTypeVO();
		defaultType.setId(defaultTypeId);
		defaultType.setName("全部类型");

		placeKickTypeList.add(defaultType);
		if(StringUtils.isBlank(place_kick_type_id) || !StringUtils.isNumeric(place_kick_type_id)) {
			defaultType.setChecked(true);
		} else {
			for(PlaceKickTypeVO obj : placeKickTypeList) {
				defaultTypeId = place_kick_type_id;
				if (obj.getId().equals(defaultTypeId)) {
					obj.setChecked(true);
					break;
				}
			}
		}
		
		List<PlaceKickVO> placeKickList = null;
		if(StringUtil.isBlank(defaultTypeId)) {
			placeKickList = placeKickService.getPlaceKickByUserId(loginVO.getId(), orgVO.getId(), category);
		} else {
			placeKickList = placeKickService.getPlaceKickByTypeAndUserId(loginVO.getId(), orgVO.getId(), defaultTypeId);
		}
		
		String serverUrl = CommonUtils.getServerUrl(request);
		for(PlaceKickVO obj : placeKickList) {
			String surfix = "";
			try {
				Timestamp tmp = Timestamp.valueOf(obj.getLast_update());
				surfix = "&_lastupdate="+tmp.getTime();
			} catch(Exception e) {
				surfix = "";
			}
			obj.setImgUrl(serverUrl+"hagkFile/asset?id="+obj.getImgName()+surfix);
		}
		model.addAttribute("placeKickTypeList", placeKickTypeList);
		model.addAttribute("placeKickList", placeKickList);
		
		return "tactics/place_kick_list";
	}
	
	@RequestMapping("/showPlaceKickEdit")
    public String showStartersEdit(HttpServletRequest request, Model model, @RequestParam(required=false) String placeKickId) {
		LoginVO loginVO = getCurrentUser(request);
		OrgVO orgVO = getCurrentOrg(request);
		if(loginVO == null) {
			return null;
		}
		List<PlaceKickTypeVO> placeKickTypeListCommon = placeKickTypeService.getPlaceKickTypeCommon(category);
		List<PlaceKickTypeVO> placeKickTypeList = placeKickTypeService.getPlaceKickTypeByOrgId(orgVO.getId(), category);
		placeKickTypeList.addAll(placeKickTypeListCommon);
		
		List<TacticsPlaygroundVO> tacticsPlaygroundList = tacticsPlaygroundService.getTacticsPlaygroundCommon(category);
		
		List<UserVO> playerList = userService.getPlayersByOrgId(orgVO.getId());
		
		PlaceKickVO placeKickVo = null;
		
		if(StringUtils.isBlank(placeKickId)) {
			model.addAttribute("isCreate", true);
		} else {
			model.addAttribute("isCreate", false);

			placeKickVo = placeKickService.getPlaceKickById(placeKickId);
			if(placeKickVo == null) {
				return null;
			}
			for (PlaceKickTypeVO tmp : placeKickTypeList) {
				if(tmp.getId().equals(placeKickVo.getPlaceKickTypeId())) {
					tmp.setChecked(true);
					break;
				}
			}

			for (TacticsPlaygroundVO tmp : tacticsPlaygroundList) {
				if(tmp.getId()==placeKickVo.getPlayground_id()) {
					tmp.setChecked(true);
					break;
				}
			}
			tacticsService.loadTacticsFiles(placeKickVo);
			String serverUrl = CommonUtils.getServerUrl(request);
			String surfix = "";
			try {
				Timestamp tmp = Timestamp.valueOf(placeKickVo.getLast_update());
				surfix = "&_lastupdate="+tmp.getTime();
			} catch(Exception e) {
				surfix = "";
			}
			placeKickVo.setImgUrl(serverUrl+"hagkFile/asset?id="+placeKickVo.getImgName()+surfix);
			
			model.addAttribute("tactics", placeKickVo);
			model.addAttribute("placeKick", placeKickVo);
		}
		
		model.addAttribute("placeKickTypeList", placeKickTypeList);
		model.addAttribute("playerList", playerList);
		model.addAttribute("playgroundTypeList", tacticsPlaygroundList);
		
		model.addAttribute("isCopied", false);
		return "tactics/place_kick_edit";
	}
	
	@RequestMapping("/showPlaceKickView")
    public String showPlaceKickView(HttpServletRequest request, Model model, @RequestParam(required=true) String placeKickId) {
		LoginVO loginVO = getCurrentUser(request);
		OrgVO orgVO = getCurrentOrg(request);
		if(loginVO == null) {
			return null;
		}
		
		PlaceKickVO placeKickVo = null;
		
		placeKickVo = placeKickService.getPlaceKickById(placeKickId);
		if(placeKickVo == null) {
			return null;
		}

		tacticsService.loadTacticsFiles(placeKickVo);
		String serverUrl = CommonUtils.getServerUrl(request);
		String surfix = "";
		try {
			Timestamp tmp = Timestamp.valueOf(placeKickVo.getLast_update());
			surfix = "&_lastupdate="+tmp.getTime();
		} catch(Exception e) {
			surfix = "";
		}
		placeKickVo.setImgUrl(serverUrl+"hagkFile/asset?id="+placeKickVo.getImgName()+surfix);
		
		model.addAttribute("tactics", placeKickVo);
		model.addAttribute("placeKick", placeKickVo);
		
		List<UserVO> playerList = userService.getPlayersByOrgId(orgVO.getId());
		model.addAttribute("playerList", playerList);
		
		if(loginVO.getId().equals(placeKickVo.getCreator_id())) {
			model.addAttribute("isCreator", true);
		}
		
		return "tactics/place_kick_view";
	}
	
	@ResponseBody
	@RequestMapping(value = "/savePlaceKick", method = RequestMethod.POST)
	public Response savePlaceKick(HttpServletRequest request, HttpServletResponse response, @RequestBody PlaceKickVO placeKickVo) throws IOException {
		LoginVO loginVO = getCurrentUser(request);
		OrgVO orgVO = getCurrentOrg(request);
		if(loginVO == null) {
			return null;
		}
		
		String placeKickId = placeKickVo.getId();
		if (StringUtil.isBlank(placeKickId)) {
			placeKickVo.setId(placeKickVo.getId());
		}
		
		String serverUrl = CommonUtils.getServerUrl(request);
		placeKickVo.setImgUrl(serverUrl);
		boolean bSuccess = tacticsService.saveTacticsFiles(placeKickVo, loginVO.getId(), orgVO.getId());
		if(bSuccess==false) {
			return Response.toFailure(500, "create new tactics file error");
		}
		
		if(StringUtil.isBlank(placeKickId)) {
			// new
			placeKickVo.setCreator_id(loginVO.getId());
			placeKickVo.setOrg_id(orgVO.getId());
			placeKickVo.setId(placeKickService.createPlaceKick(placeKickVo));
			if(StringUtil.isBlank(placeKickVo.getId())) {
				return Response.toFailure(500, "insert new tactics error");
			}
		} else {
			// update
			boolean bUpdated =  placeKickService.updatePlaceKickType(placeKickVo);
			if(bUpdated == false) {
				return Response.toFailure(500, "update tactics error");
			}
		}
			
		Map<String, Object> paramList = new HashMap<String, Object>();
		paramList.put("id", placeKickVo.getId());
		paramList.put("creator_id", placeKickVo.getCreator_id());
		paramList.put("org_id", placeKickVo.getOrg_id());
		paramList.put("tacticsdata", placeKickVo.getTacticsdata());
		paramList.put("url", placeKickVo.getImgUrl());
		paramList.put("imgName", placeKickVo.getImgName());
		
		// must clean old files
		tacticsService.cleanOldFiles();
		return Response.toSussess(paramList);
	}
	
	@RequestMapping("/copyPlaceKick")
	public String copyPlaceKick(HttpServletRequest request, Model model, @RequestParam(required=true) String placeKickId) {
		LoginVO loginVO = getCurrentUser(request);
		OrgVO orgVO = getCurrentOrg(request);
		if(loginVO == null) {
			return null;
		}
		
		if(StringUtils.isBlank(placeKickId)) {
			return null;
		}
		PlaceKickVO placeKickVo = placeKickService.getPlaceKickById(placeKickId);
		if(placeKickVo == null) {
			return null;
		}
		
		boolean bSuccess = tacticsService.loadTacticsFiles(placeKickVo);
		String surfix = "";
		try {
			Timestamp tmp = Timestamp.valueOf(placeKickVo.getLast_update());
			surfix = "&_lastupdate="+tmp.getTime();
		} catch(Exception e) {
			surfix = "";
		}
		placeKickVo.setImgUrl(CommonUtils.getServerUrl(request) +"hagkFile/asset?id="+placeKickVo.getImgName()+surfix);
		if(bSuccess==false) {
			return null;
		}
		
		List<PlaceKickTypeVO> placeKickTypeListCommon = placeKickTypeService.getPlaceKickTypeCommon(category);
		List<PlaceKickTypeVO> placeKickTypeList = placeKickTypeService.getPlaceKickTypeByOrgId(orgVO.getId(), category);
		placeKickTypeList.addAll(placeKickTypeListCommon);
		
		List<TacticsPlaygroundVO> tacticsPlaygroundList = tacticsPlaygroundService.getTacticsPlaygroundCommon(category);
		for (PlaceKickTypeVO tmp : placeKickTypeList) {
			if(tmp.getId().equals(placeKickVo.getPlaceKickTypeId())) {
				tmp.setChecked(true);
				break;
			}
		}
		for (TacticsPlaygroundVO tmp : tacticsPlaygroundList) {
			if(tmp.getId()==placeKickVo.getPlayground_id()) {
				tmp.setChecked(true);
				break;
			}
		}
		
		placeKickVo.setName(placeKickVo.getName() + Constants.TACTICS_COPY);
		model.addAttribute("tactics", placeKickVo);
		model.addAttribute("placeKick", placeKickVo);
		
		List<UserVO> playerList = userService.getPlayersByOrgId(orgVO.getId());
		model.addAttribute("playerList", playerList);
		model.addAttribute("placeKickTypeList", placeKickTypeList);
		model.addAttribute("playgroundTypeList", tacticsPlaygroundList);
		
		model.addAttribute("isCopied", true);
		model.addAttribute("isCreate", false);
		model.addAttribute("srcPlaceKickId", placeKickId);
		return "tactics/place_kick_edit";
	}
	
	@ResponseBody
	@RequestMapping(value = "/deletePlaceKick", method = RequestMethod.POST)
	public Object deletePlaceKick(HttpServletRequest request, String tacticsID) {
		try {
			LoginVO currentAccount = this.getCurrentUser(request);
			PlaceKickVO placeKickVo = null;
			boolean result = false;
			if(!StringUtils.isBlank(tacticsID)) {
				placeKickVo = placeKickService.getPlaceKickById(tacticsID);
			}
			
			if (placeKickVo == null) {
				return Response.toFailure(-1, "该定位球设置已在其他地方被删除");
			}
			
			if(currentAccount.getId().equals(placeKickVo.getCreator_id()) == false) {
				return Response.toFailure(-1, "只有创建者才能删除该定位球设置");
			}
			placeKickVo.setStatus("deleted");
			result = placeKickService.deletePlaceKick(placeKickVo);
			
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


