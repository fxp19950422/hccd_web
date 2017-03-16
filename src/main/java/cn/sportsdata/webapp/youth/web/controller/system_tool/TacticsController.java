package cn.sportsdata.webapp.youth.web.controller.system_tool;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.sportsdata.webapp.youth.common.utils.CommonUtils;
import cn.sportsdata.webapp.youth.common.utils.StringUtil;
import cn.sportsdata.webapp.youth.common.vo.OrgVO;
import cn.sportsdata.webapp.youth.common.vo.PlaceKickTypeVO;
import cn.sportsdata.webapp.youth.common.vo.Response;
import cn.sportsdata.webapp.youth.common.vo.TacticsPlaygroundVO;
import cn.sportsdata.webapp.youth.common.vo.TacticsTypeVO;
import cn.sportsdata.webapp.youth.common.vo.TacticsVO;
import cn.sportsdata.webapp.youth.common.vo.UserVO;
import cn.sportsdata.webapp.youth.common.vo.login.LoginVO;
import cn.sportsdata.webapp.youth.service.starters.StartersService;
import cn.sportsdata.webapp.youth.service.tactics.TacticsService;
import cn.sportsdata.webapp.youth.service.tacticsplayground.TacticsPlaygroundService;
import cn.sportsdata.webapp.youth.service.tacticstype.TacticsTypeService;
import cn.sportsdata.webapp.youth.service.user.UserService;
import cn.sportsdata.webapp.youth.web.controller.BaseController;

@Controller
@RequestMapping("/system_tool")
public class TacticsController extends BaseController {
	private static Logger logger = Logger.getLogger(TacticsController.class);
	private static String category = "soccer";
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TacticsTypeService tacticsTypeService;
	
	@Autowired
	private TacticsPlaygroundService tacticsPlaygroundService;
	
	@Autowired
	private TacticsService tacticsService;
	
	@RequestMapping(value = "/showTacticsList")
	public Object showTacticsList(HttpServletRequest request, HttpServletResponse response) {
		logger.info("TacticsController.showTacticsList");

		LoginVO loginVO = getCurrentUser(request);
		OrgVO orgVO = getCurrentOrg(request);
		if(loginVO == null) {
			return null;
		}
		List<TacticsTypeVO> tacticsTypeListCommon = tacticsTypeService.getTacticsTypeCommon(category);
		List<TacticsTypeVO> tacticsTypeList = tacticsTypeService.getTacticsTypeByOrgId(orgVO.getId(), category);
		tacticsTypeList.addAll(tacticsTypeListCommon);
		
		long defaultTypeId = 0;
		TacticsTypeVO defaultType = new TacticsTypeVO();
		defaultType.setId(defaultTypeId);
		defaultType.setName("全部类型");
		tacticsTypeList.add(defaultType);

		List<TacticsVO>tacticsList = null;
		if(defaultTypeId == 0) {
			defaultType.setChecked(true);
			tacticsList = tacticsService.getTacticsByUserAndOrgId(loginVO.getId(), orgVO.getId(), category);
		} else {
			tacticsList = tacticsService.getTacticsByTypeUserAndOrgId(loginVO.getId(), orgVO.getId(), defaultTypeId);
		}
		String serverUrl = CommonUtils.getServerUrl(request);
		for(TacticsVO obj : tacticsList) {
			String surfix = "";
			try {
				Timestamp tmp = Timestamp.valueOf(obj.getImgAssetVo().getLast_update());
				surfix = obj.getImgAssetVo() == null? "":("&_lastupdate="+tmp.getTime());
			} catch(Exception e) {
				surfix = "";
			}
			
			obj.setImgUrl(serverUrl+"hagkFile/asset?id="+obj.getImgName()+surfix);
		}
		return new ModelAndView("/system_tool/tactics_list").addObject("tacticsList", tacticsList).addObject("tacticsTypeList", tacticsTypeList);
	}
	
	@RequestMapping(value = "/showTacticsListDetail")
	public Object showTacticsListDetail(HttpServletRequest request, HttpServletResponse response,@RequestParam String tactics_type_id) {
		logger.info("TacticsController.showTacticsListDetail");
		LoginVO loginVO = getCurrentUser(request);
		OrgVO orgVO = getCurrentOrg(request);

		if(loginVO == null) {
			return null;
		}
		String pFlag="tactics";
		if(request.getParameter("pFlag")!=null && request.getParameter("pFlag")!=""){
			pFlag=request.getParameter("pFlag");
		}
		String selectedTacticsId="null";
		HttpSession session=request.getSession();
		if(request.getParameter("selectedTacticsId")!=null && request.getParameter("selectedTacticsId")!=""){
			selectedTacticsId=request.getParameter("selectedTacticsId");
			if("null".equals(selectedTacticsId)){
				session.removeAttribute("selectedTacticsId");
			}else{
				session.setAttribute("selectedTacticsId", selectedTacticsId);
			}
		}
		if("null".equals(selectedTacticsId) && session.getAttribute("selectedTacticsId")!=null && session.getAttribute("selectedTacticsId")!=""){
			selectedTacticsId=session.getAttribute("selectedTacticsId").toString();
		}
//		if(StringUtil.isBlank(selectedTacticsId) == false){
//			selectedTacticsId=(String)(session.getAttribute("selectedTacticsId"));
//		}
		
		long tacticsTypeId = 0; 
		if(StringUtils.isBlank(tactics_type_id)) {
			return null;
		} else {
			tacticsTypeId = Long.parseLong(tactics_type_id);
		}
		
		List<TacticsTypeVO> tacticsTypeListCommon = tacticsTypeService.getTacticsTypeCommon(category);
		List<TacticsTypeVO> tacticsTypeList = tacticsTypeService.getTacticsTypeByOrgId(orgVO.getId(), category);
		tacticsTypeList.addAll(tacticsTypeListCommon);
		
		TacticsTypeVO defaultType = new TacticsTypeVO();
		defaultType.setId(0);
		defaultType.setName("全部类型");
		tacticsTypeList.add(defaultType);
		
		for (TacticsTypeVO tmpObj: tacticsTypeList) {
			if (tmpObj.getId() == tacticsTypeId) {
				tmpObj.setChecked(true);
				break;
			}
		}
		
		List<TacticsVO>tacticsList = null;
		if(tacticsTypeId == 0) {
			tacticsList = tacticsService.getTacticsByUserAndOrgId(loginVO.getId(), orgVO.getId(), category);
		} else {
			tacticsList = tacticsService.getTacticsByTypeUserAndOrgId(loginVO.getId(), orgVO.getId(), tacticsTypeId);
		}

		String serverUrl = CommonUtils.getServerUrl(request);
		for(TacticsVO obj : tacticsList) {
			String surfix = "";
			try {
				Timestamp tmp = Timestamp.valueOf(obj.getImgAssetVo().getLast_update());
				surfix = obj.getImgAssetVo() == null? "":("&_lastupdate="+tmp.getTime());
			} catch(Exception e) {
				surfix = "";
			}
			obj.setImgUrl(serverUrl+"hagkFile/asset?id="+obj.getImgName()+surfix);
			if(!"null".equals(selectedTacticsId) && obj.getId().equals(selectedTacticsId)) {
				obj.setChecked(true);
			}
		}
		
		return new ModelAndView("single".equals(pFlag)?"/system_tool/single_tactics_list":"/system_tool/tactics_list").addObject("tacticsList", tacticsList).addObject("tacticsTypeList", tacticsTypeList);
	}
	
	@RequestMapping("/showTacticsEdit")
    public Object showTacticsEdit(HttpServletRequest request, HttpServletResponse response, @RequestParam(required=false) String tacticsID) {
		LoginVO loginVO = getCurrentUser(request);
		OrgVO orgVO = getCurrentOrg(request);
		if(loginVO == null) {
			return null;
		}
		List<TacticsTypeVO> tacticsTypeListCommon = tacticsTypeService.getTacticsTypeCommon(category);
		List<TacticsTypeVO> tacticsTypeList = tacticsTypeService.getTacticsTypeByOrgId(orgVO.getId(), category);
		tacticsTypeList.addAll(tacticsTypeListCommon);
		
		List<TacticsPlaygroundVO> tacticsPlaygroundList = tacticsPlaygroundService.getTacticsPlaygroundCommon(category);
		
		List<UserVO> playerList = userService.getPlayersByOrgId(orgVO.getId());
		
		
		TacticsVO tactiscVo = null;
		String pFlag="tactics";
		if(request.getParameter("pFlag")!=null && request.getParameter("pFlag")!=""){
			pFlag=request.getParameter("pFlag");
		}
		
		ModelAndView model_new = new ModelAndView("/system_tool/tactics_edit");
		if(StringUtils.isBlank(tacticsID)) {
			model_new.addObject("isCreate", true);
		} else {
			model_new.addObject("isCreate", false);
			tactiscVo = tacticsService.getTacticsByID(tacticsID);
			for (TacticsTypeVO tmp : tacticsTypeList) {
				if(tmp.getId()==tactiscVo.getTactics_type_id()) {
					tmp.setChecked(true);
					break;
				}
			}
			for (TacticsPlaygroundVO tmp : tacticsPlaygroundList) {
				if(tmp.getId()==tactiscVo.getPlayground_id()) {
					tmp.setChecked(true);
					break;
				}
			}
			if(tactiscVo == null) {
				return null;
			}
			
			tacticsService.loadTacticsFiles(tactiscVo);
			String serverUrl = CommonUtils.getServerUrl(request);
			String surfix = "";
			try {
				Timestamp tmp = Timestamp.valueOf(tactiscVo.getImgAssetVo().getLast_update());
				surfix = tactiscVo.getImgAssetVo() == null? "":("&_lastupdate="+tmp.getTime());
			} catch(Exception e) {
				surfix = "";
			}
			tactiscVo.setImgUrl(serverUrl+"hagkFile/asset?id="+tactiscVo.getImgName()+surfix);
			model_new.addObject("tactics",tactiscVo);
		}
		model_new.addObject("tacticsTypeList", tacticsTypeList);	
		
		model_new.addObject("playgroundTypeList",tacticsPlaygroundList);
		
		model_new.addObject("playerList",playerList);
		model_new.addObject("pFlag",pFlag);
		return model_new;
	}
	
	@ResponseBody
	@RequestMapping(value = "/saveTactics", method = RequestMethod.POST)
	public Response saveTacticsFiles(HttpServletRequest request, HttpServletResponse response, @RequestBody TacticsVO tacticsVo) throws IOException {
		String tactiscId = tacticsVo.getId();
		LoginVO loginVO = getCurrentUser(request);
		OrgVO orgVO = getCurrentOrg(request);
		if(loginVO == null) {
			return null;
		}
		
		if(!StringUtil.isBlank(tactiscId)) {
			if(loginVO.getId().equals(tacticsVo.getCreator_id()) == false ) {
				return Response.toFailure(-1, "只有创建者才能编辑该战术板");
			}
		}

		String serverUrl = CommonUtils.getServerUrl(request);
		tacticsVo.setImgUrl(serverUrl);
		boolean bSuccess = tacticsService.saveTacticsFiles(tacticsVo, loginVO.getId(), orgVO.getId());
		if(bSuccess==false) {
			return Response.toFailure(500, "create new tactics file error");
		}
		
		if(StringUtil.isBlank(tactiscId)) {
			// new
			tacticsVo.setCreator_id(loginVO.getId());
			tacticsVo.setOrg_id(orgVO.getId());
			tacticsVo.setId(tacticsService.createTactics(tacticsVo));
			if(StringUtil.isBlank(tacticsVo.getId())) {
				return Response.toFailure(500, "insert new tactics error");
			}
		} else {
			// update
			boolean bUpdated =  tacticsService.updateTactics(tacticsVo);
			if(bUpdated == false) {
				return Response.toFailure(500, "update tactics error");
			}
		}
		
		tacticsService.saveTacticsPlayer(tacticsVo);
		
		Map<String, Object> paramList = new HashMap<String, Object>();
		paramList.put("id", tacticsVo.getId());
		paramList.put("url", tacticsVo.getImgUrl());
		paramList.put("imgName", tacticsVo.getImgName());
		paramList.put("creator_id", tacticsVo.getCreator_id());
		paramList.put("org_id", tacticsVo.getOrg_id());
		paramList.put("tacticsdata", tacticsVo.getTacticsdata());
		paramList.put("name", tacticsVo.getName());
		// must clean old files
		tacticsService.cleanOldFiles();
		return Response.toSussess(paramList);
	}
	
	@ResponseBody
	@RequestMapping(value = "/deleteTactics", method = RequestMethod.POST)
	public Object deleteTactics(HttpServletRequest request, String tacticsID) {
		try {
			OrgVO orgVo = this.getCurrentOrg(request);
			LoginVO currentAccount = this.getCurrentUser(request);
			TacticsVO tactiscVo = null;			
			if(!StringUtils.isBlank(tacticsID)) {
				tactiscVo = tacticsService.getTacticsByID(tacticsID);
			}
			if(tactiscVo == null) {
				return Response.toFailure(-1, "该战术板已在其他地方被删除");
			}
			if(currentAccount.getId().equals(tactiscVo.getCreator_id()) == false ) {
				return Response.toFailure(-1, "只有创建者才能删除该战术板");
			}
			tactiscVo.setStatus("deleted");
			boolean result = tacticsService.deleteTactics(tactiscVo);
			
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
