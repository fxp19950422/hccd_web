package cn.sportsdata.webapp.youth.web.controller.match;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.SqlDateConverter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.sportsdata.webapp.youth.common.auth.share.ShareLink;
import cn.sportsdata.webapp.youth.common.auth.share.ShareLinkBO;
import cn.sportsdata.webapp.youth.common.bo.MatchBO;
import cn.sportsdata.webapp.youth.common.bo.MatchResultBO;
import cn.sportsdata.webapp.youth.common.constants.Constants;
import cn.sportsdata.webapp.youth.common.exceptions.SoccerProException;
import cn.sportsdata.webapp.youth.common.exceptions.SoccerProRuntimeException;
import cn.sportsdata.webapp.youth.common.utils.CommonUtils;
import cn.sportsdata.webapp.youth.common.utils.ConfigProps;
import cn.sportsdata.webapp.youth.common.utils.ExcelUtils;
import cn.sportsdata.webapp.youth.common.utils.SecurityUtils;
import cn.sportsdata.webapp.youth.common.utils.StringUtil;
import cn.sportsdata.webapp.youth.common.vo.FormationTypeVO;
import cn.sportsdata.webapp.youth.common.vo.OrgVO;
import cn.sportsdata.webapp.youth.common.vo.PlaceKickTypeVO;
import cn.sportsdata.webapp.youth.common.vo.PlaceKickVO;
import cn.sportsdata.webapp.youth.common.vo.Response;
import cn.sportsdata.webapp.youth.common.vo.TacticsVO;
import cn.sportsdata.webapp.youth.common.vo.UserVO;
import cn.sportsdata.webapp.youth.common.vo.login.LoginVO;
import cn.sportsdata.webapp.youth.common.vo.match.MatchAssetVO;
import cn.sportsdata.webapp.youth.common.vo.match.MatchDataItemVO;
import cn.sportsdata.webapp.youth.common.vo.match.MatchDataVO;
import cn.sportsdata.webapp.youth.common.vo.match.MatchFoulVO;
import cn.sportsdata.webapp.youth.common.vo.match.MatchGoalVO;
import cn.sportsdata.webapp.youth.common.vo.match.MatchResultVO;
import cn.sportsdata.webapp.youth.common.vo.match.MatchSubVO;
import cn.sportsdata.webapp.youth.common.vo.match.MatchTacticsVO;
import cn.sportsdata.webapp.youth.common.vo.match.MatchVO;
import cn.sportsdata.webapp.youth.common.vo.starters.StartersPreviewsVO;
import cn.sportsdata.webapp.youth.service.formationtype.FormationTypeService;
import cn.sportsdata.webapp.youth.service.match.MatchService;
import cn.sportsdata.webapp.youth.service.placeKick.PlaceKickService;
import cn.sportsdata.webapp.youth.service.placeKicktype.PlaceKickTypeService;
import cn.sportsdata.webapp.youth.service.share.ShareLinkService;
import cn.sportsdata.webapp.youth.service.share.validator.IShareLinkValidator;
import cn.sportsdata.webapp.youth.service.starters.StartersService;
import cn.sportsdata.webapp.youth.service.tactics.TacticsService;
import cn.sportsdata.webapp.youth.service.user.UserService;
import cn.sportsdata.webapp.youth.web.controller.BaseController;

@Controller
@RequestMapping("/match")
public class MatchController extends BaseController {
	private static Logger logger = Logger.getLogger(MatchController.class);
	
	@Autowired
	private MatchService matchService;
	
	@Autowired
	private FormationTypeService formationTypeService;
	
	@Autowired
	private StartersService startersService;
	
	@Autowired
	private TacticsService tacticsService;
	
	@Autowired
	private PlaceKickTypeService placeKickTypeService;
	
	@Autowired
	private PlaceKickService placeKickService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ShareLinkService sharelinkService;
	
	@Autowired
	@Qualifier("parameterValidator")
	private IShareLinkValidator shareLinkValidator;
	
	private static final String[] MATCH_DATA_EXCEL_HEADER_NAMES = {"姓名", "号码", "距离", "射正", "射歪", "过人", "越位", "短传", "长传", "向前传", "传中", "传球失误", "解围", "犯规", "抢断成功", "抢断失败", "拦截", "扑救"};
	
	@RequestMapping("/showMatchList")
    public String showMatchList(HttpServletRequest request, Model model) {
		model.addAttribute("isMatch", true);
		addMatchList4Model(request, model);
		
		return "match/match_list";
	}
	
	@RequestMapping("/showMatchResultList")
    public String showMatchResultList(HttpServletRequest request, Model model) {
		model.addAttribute("isMatch", false);
		addMatchList4Model(request, model);
		
		return "match/match_list";
	}
	
	@RequestMapping("/showMatchEdit")
    public String showMatchEdit(HttpServletRequest request, Model model, @RequestParam(required=false) String matchID) {
		StartersPreviewsVO matchStarter = null;
		List<TacticsVO> matchPlacekickList = new ArrayList<TacticsVO>();
		String serverUrl = CommonUtils.getServerUrl(request);
		if(StringUtils.isBlank(matchID)) { // create case
			model.addAttribute("isCreate", true);
		} else {
			model.addAttribute("isCreate", false);
			
			MatchVO match = matchService.getMatchById(matchID);
			model.addAttribute("match", match);
			
			List<TacticsVO> tacticsList = tacticsService.getTacticsByMatchId(matchID);
			for(TacticsVO tactics : tacticsList) {
				String surfix = "";
				try {
					Timestamp tmp = Timestamp.valueOf(tactics.getLast_update());
					surfix = "&_lastupdate="+tmp.getTime();
				} catch(Exception e) {
					surfix = "";
				}
				tactics.setImgUrl(serverUrl+"hagkFile/asset?id="+tactics.getImgName()+surfix);
				if(tactics.getTactics_type_id() == Constants.starters_tactics_type) {
					matchStarter = startersService.getStartersMatchInfo(tactics);
					matchStarter.setImgUrl(serverUrl+"hagkFile/asset?id="+tactics.getImgName()+surfix);
				} else if(tactics.getTactics_type_id() == Constants.place_kick_tactics_type) {
					matchPlacekickList.add(tactics);
				}
			}
		}
		
		model.addAttribute("starter", matchStarter);
		model.addAttribute("placekicks", matchPlacekickList);
		
		LoginVO loginVO = getCurrentUser(request);
		OrgVO orgVO = getCurrentOrg(request);
		
		// starters list
		List<FormationTypeVO> formationTypeListCommon = formationTypeService.getFormationTypeCommon(Constants.TACTICS_CATEGORY);
		List<FormationTypeVO> formationTypeList = formationTypeService.getFormationTypeByOrgId(orgVO.getId(), Constants.TACTICS_CATEGORY);
		formationTypeList.addAll(formationTypeListCommon);
		model.addAttribute("formationTypeList", formationTypeList);
		
		List<StartersPreviewsVO> startersList = startersService.getStartersByUserId(loginVO.getId(), orgVO.getId(), Constants.TACTICS_CATEGORY);
		

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
		
		model.addAttribute("startersList", startersList);
		
		// placekick list
		List<PlaceKickTypeVO> placeKickTypeListCommon = placeKickTypeService.getPlaceKickTypeCommon(Constants.TACTICS_CATEGORY);
		List<PlaceKickTypeVO> placeKickTypeList = placeKickTypeService.getPlaceKickTypeByOrgId(orgVO.getId(), Constants.TACTICS_CATEGORY);
		placeKickTypeList.addAll(placeKickTypeListCommon);
		model.addAttribute("placeKickTypeList", placeKickTypeList);
		
		List<PlaceKickVO> placeKickList = placeKickService.getPlaceKickByUserId(loginVO.getId(), orgVO.getId(), Constants.TACTICS_CATEGORY);
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
		model.addAttribute("placeKickList", placeKickList);
		
		return "match/match_edit";
	}
	
	@ResponseBody
	@RequestMapping(value = "/saveMatch", method = RequestMethod.POST)
    public Response saveMatchInfo(HttpServletRequest request, @RequestBody MatchBO matchBO) {
		boolean isCreate = true;
		if (!StringUtil.isBlank(matchBO.getId())) {
			isCreate = false;
		}
		
		try {
			MatchVO matchVO = constructMatchVO(matchBO, request);
			MatchResultVO matchResultVO = constructMatchResultVO(matchBO, request);
			
			MatchTacticsVO matchTacticsVO = null;
			if(StringUtils.isNotBlank(matchBO.getTacticIds())) {
				matchTacticsVO = new MatchTacticsVO();
				
				String[] tacticIdArray = matchBO.getTacticIds().split(",");
				List<String> tacticIdList = new ArrayList<String>();
				for(String tacticId : tacticIdArray) {
					tacticIdList.add(tacticId);
				}
				
				matchTacticsVO.setTacticsIds(tacticIdList);
			}
			
			boolean isSuccess = matchService.handleMatch(matchVO, matchResultVO, matchTacticsVO, isCreate);
			return isSuccess ? Response.toSussess(String.valueOf(matchVO.getId())) : Response.toFailure(500, isCreate ? "insert new match error" : "update match " + matchBO.getId() + " error");
			
		} catch(Exception e) {
			logger.error(isCreate ? "Error occurs while creating user: " : "Error occurs while updating user " + matchBO.getId() + ": ", e);
		}
		
		return Response.toFailure(500, isCreate ? "insert new match error" : "update match " + matchBO.getId() + " error");
	}
	
	@RequestMapping("/showMatchDetail")
    public String showMatchDetail(HttpServletRequest request, Model model, @RequestParam String matchID) throws SoccerProException {
		
		MatchVO match = matchService.getMatchById(matchID);
		if(match == null) {
			logger.error("Can not get match info by id: " + matchID);
			throw new SoccerProRuntimeException("Can not get match info by id: " + matchID);
		}
		String basePath = CommonUtils.getServerUrl(request);
		
		
		model.addAttribute("match", match);
		String serverUrl = CommonUtils.getServerUrl(request);
		StartersPreviewsVO starter = null;
		List<TacticsVO> placekickList = new ArrayList<TacticsVO>();
		List<TacticsVO> tacticsList = tacticsService.getTacticsByMatchId(matchID);
		for(TacticsVO tactics : tacticsList) {
			String surfix = "";
			try {
				Timestamp tmp = Timestamp.valueOf(tactics.getLast_update());
				surfix = "&_lastupdate="+tmp.getTime();
			} catch(Exception e) {
				surfix = "";
			}
			tactics.setImgUrl(serverUrl+"hagkFile/asset?id="+tactics.getImgName()+surfix);
			if(tactics.getTactics_type_id() == Constants.starters_tactics_type) {
				starter = startersService.getStartersMatchInfo(tactics);
				starter.setImgUrl(serverUrl+"hagkFile/asset?id="+tactics.getImgName()+surfix);
			} else if(tactics.getTactics_type_id() == Constants.place_kick_tactics_type) {
				placekickList.add(tactics);
			}
		}
		model.addAttribute("starter", starter);
		model.addAttribute("placekickList", placekickList);
		
		String barCodeLink = getShareLink(basePath, matchID);
		model.addAttribute("barCodeLink", barCodeLink);
		
		return "match/match_detail";
	}
	
	private String getShareLink(String basePath, String matchID) throws SoccerProException {
		ShareLink shareLink = new ShareLink("/match/showMatchSharing");
		shareLink.addParameter("matchID", matchID);
		String shareLinkStr = sharelinkService.getShareLink(shareLink, basePath);
		
		String encryptedShareLink = SecurityUtils.encryptByAES(shareLinkStr);
		StringBuffer barcodeLink = new StringBuffer(basePath);
		barcodeLink.append("file/getBarcode?content=").append(encryptedShareLink);
		return barcodeLink.toString();
	}
	
	@RequestMapping("/showMatchSharing")
    public String showMatchSharing(HttpServletRequest request, Model model, @RequestParam String matchID, @RequestParam(required = true) String token) throws SoccerProException {
		
		ShareLinkBO originalShareLinkBO = new ShareLinkBO("/match/showMatchSharing");
		originalShareLinkBO.addParameter("matchID", matchID);
		
		if (!shareLinkValidator.validate(token, originalShareLinkBO)) {
			logger.error("validate share link failed");
			throw new SoccerProException("validate share link failed");
		}
		
		
		MatchVO match = matchService.getMatchById(matchID);
		if(match == null) {
			logger.error("Can not get match info by id: " + matchID);
			throw new SoccerProRuntimeException("Can not get match info by id: " + matchID);
		}
		model.addAttribute("match", match);
		String serverUrl = CommonUtils.getServerUrl(request);
		StartersPreviewsVO starter = null;
		List<TacticsVO> placekickList = new ArrayList<TacticsVO>();
		List<TacticsVO> tacticsList = tacticsService.getTacticsByMatchId(matchID);
		for(TacticsVO tactics : tacticsList) {
			String surfix = "";
			try {
				Timestamp tmp = Timestamp.valueOf(tactics.getLast_update());
				surfix = "&_lastupdate="+tmp.getTime();
			} catch(Exception e) {
				surfix = "";
			}
			tactics.setImgUrl(serverUrl+"hagkFile/asset?id="+tactics.getImgName()+surfix);
			
			if(tactics.getTactics_type_id() == Constants.starters_tactics_type) {
				starter = startersService.getStartersMatchInfo(tactics);
				starter.setImgUrl(serverUrl+"hagkFile/asset?id="+tactics.getImgName()+surfix);
			} else if(tactics.getTactics_type_id() == Constants.place_kick_tactics_type) {
				placekickList.add(tactics);
			}
		}
		model.addAttribute("starter", starter);
		model.addAttribute("placekickList", placekickList);
		
		return "match/match_share";
	}
	
	@ResponseBody
	@RequestMapping(value = "/deleteMatch", method = RequestMethod.DELETE)
    public Response deleteMatch(HttpServletRequest request, @RequestParam String matchID) {
		if(StringUtils.isBlank(matchID)) {
			return Response.toFailure(400, "match id " + matchID + " is invalid");
		}
		
		boolean isSuccess = matchService.deleteMatch(matchID);
		return isSuccess ? Response.toSussess(null) : Response.toFailure(500, "delete match " + matchID + " error");
	}
	
	@RequestMapping("/showMatchResultEdit")
    public String showMatchResultEdit(HttpServletRequest request, Model model, @RequestParam String matchID) {
		fillMatchDetailInfo4Model(model, matchID);
		
		// get org players
		OrgVO orgVO = getCurrentOrg(request);
		List<UserVO> playerList = userService.getPlayersByOrgId(orgVO.getId());
		model.addAttribute("players", playerList);
		
		return "match/match_result_edit";
	}
	
	@RequestMapping("/showMatchResultDetail")
    public String showMatchResultDetail(HttpServletRequest request, Model model, @RequestParam String matchID) {
		fillMatchDetailInfo4Model(model, matchID);
		
		return "match/match_result_detail";
	}
	
	@ResponseBody
	@RequestMapping(value="/uploadMatchData", method = RequestMethod.POST, produces = "text/html; charset=utf-8")
	public String uploadMatchData(HttpServletRequest request, @RequestParam MultipartFile uploadedFile) {
		try {
			if(uploadedFile == null) {
				return "";
			}
			
			String originFileName = uploadedFile.getOriginalFilename();
			String originFileNamePrefix = originFileName.substring(0, originFileName.lastIndexOf("."));
			String originFileNamePostfix = originFileName.substring(originFileName.lastIndexOf(".") + 1);
			String tempFileName = new StringBuffer(ConfigProps.getInstance().getConfigValue("attachment.folder.temppath")).append(File.separator)
									.append(originFileNamePrefix).append("_").append(System.currentTimeMillis()).append(".").append(originFileNamePostfix).toString();
			File tempFile = new File(tempFileName);
			
			if(tempFile.exists()) {
				FileUtils.deleteQuietly(tempFile);
			}
			
			uploadedFile.transferTo(tempFile);
			
			List<List<Object>> excelData = ExcelUtils.parseExcel(tempFile);
			if (excelData == null || excelData.size() <= 1) {
				return "";
			}
			
			List<Integer> numbers = ExcelUtils.getNumbers(excelData.get(0), MATCH_DATA_EXCEL_HEADER_NAMES);
			JSONArray array = new JSONArray();
			OrgVO orgVO = getCurrentOrg(request);
			
			int[] statistics_array = new int[Constants.MATCH_DATA_ITEM_KEYS.length];
			for(int i = 1; i < excelData.size(); i++) {
				List<Object> obj = excelData.get(i);
				int size = obj.size();
				JSONObject json = new JSONObject();
				
				if (numbers.get(0) != -1 && size >= numbers.get(0) + 1) {
					String playerName = obj.get(numbers.get(0)).toString().trim();
					
					String playerJersey = "";
					if(numbers.get(1) != -1 && size >= numbers.get(1) + 1) {
						playerJersey = obj.get(numbers.get(1)).toString().trim();
					}
					
					String playerId = userService.getPlayerIdByJerseyNumberAndNameAndOrgID(playerJersey, playerName, orgVO.getId());
					if(StringUtils.isBlank(playerId)) {
						continue;
					}
					
					json.put("player_label", playerJersey + " " + playerName);
					json.put("player_id", playerId);
				}
				
				for(int pos = 0; pos < Constants.MATCH_DATA_ITEM_KEYS.length; pos++) {
					int index = (pos + 2);
					
					if (numbers.get(index) != -1 && size >= numbers.get(index) + 1) {
						int value = 0;
						
						try {
							value = (int) Double.parseDouble(obj.get(numbers.get(index)).toString());
						} catch(Exception e) {
							// no operation, use default value 0
						}
						
						json.put(Constants.MATCH_DATA_ITEM_KEYS[pos], value);
						statistics_array[pos] += value;
					}
				}
				
				array.put(json);
			}
			
			FileUtils.deleteQuietly(tempFile);
			
			JSONObject statistics = new JSONObject();
			for(int i = 0; i < Constants.MATCH_DATA_ITEM_KEYS.length; i++) {
				statistics.put(Constants.MATCH_DATA_ITEM_KEYS[i], statistics_array[i]);
			}
			
			array.put(statistics);
			return array.toString();
		} catch (Exception e) {
			logger.error("Error occurs while uploading match data excel file", e);
		}
		
		return "";
	}
	
	@ResponseBody
	@RequestMapping(value = "/saveMatchResult", method = RequestMethod.POST)
    public Response saveMatchResult(HttpServletRequest request, @RequestBody String matchResult) {
		MatchResultBO matchResultBO = null;
		
		try {
			ObjectMapper mapper = new ObjectMapper();
			matchResultBO = mapper.readValue(matchResult, MatchResultBO.class);
			matchResultBO.setOrgId(getCurrentOrg(request).getId());
			matchResultBO.setCreatorId(getCurrentUser(request).getId());
			
			boolean isSuccess = matchService.saveMatchResult(matchResultBO);
			return isSuccess ? Response.toSussess(null) : Response.toFailure(500, "Error occurs while saving match result");
		} catch(Exception e) {
			logger.error("Error occurs while saving match result: " + matchResult, e);
			return Response.toFailure(500, "Error occurs while saving match result");
		}
	}
	
	private void addMatchList4Model(HttpServletRequest request, Model model) {
		OrgVO currentOrg = getCurrentOrg(request);
		List<MatchVO> matchList = matchService.getMatchListByOrgId(currentOrg.getId());
		
		List<MatchVO> finishMatchList = new ArrayList<MatchVO>();
		List<MatchVO> unFinishMatchList = new ArrayList<MatchVO>();
		for(MatchVO match : matchList) {
			if(match.getIsEnd() == 0) {
				unFinishMatchList.add(match);
			} else {
				finishMatchList.add(match);
			}
		}
		
		// unfinished match list should be ASC
		Collections.sort(unFinishMatchList, new MatchDateComparator());
		
		model.addAttribute("unFinishMatchList", unFinishMatchList);
		model.addAttribute("finishMatchList", finishMatchList);
	}
	
	private MatchVO constructMatchVO(MatchBO matchBO, HttpServletRequest request) throws Exception {
		MatchVO matchVO = new MatchVO();
		ConvertUtils.register(new SqlDateConverter(null), Timestamp.class);
		BeanUtils.copyProperties(matchVO, matchBO);
		
		matchVO.setOrgId(getCurrentOrg(request).getId());
		matchVO.setCreatorId(getCurrentUser(request).getId());
		matchVO.setIsEnd((matchBO.getGoalFor() >= 0 && matchBO.getGoalAgainst() >= 0) ? 1 : 0);
		
		return matchVO;
	}
	
	private MatchResultVO constructMatchResultVO(MatchBO matchBO, HttpServletRequest request) throws Exception {
		int homeScore = matchBO.getGoalFor();
		int guestScore = matchBO.getGoalAgainst();
		
		if(homeScore < 0 || guestScore < 0)  return null;
		
		MatchResultVO matchResultVO = new MatchResultVO();
		ConvertUtils.register(new SqlDateConverter(null), Timestamp.class);
		BeanUtils.copyProperties(matchResultVO, matchBO);
		
		if(homeScore > guestScore) {
			matchResultVO.setResult(Constants.MATCH_RESULT_WON);
		} else if(homeScore < guestScore) {
			matchResultVO.setResult(Constants.MATCH_RESULT_LOST);
		} else {
			matchResultVO.setResult(Constants.MATCH_RESULT_DRAWN);
		}
		
		return matchResultVO;
	}
	
	private void fillMatchDetailInfo4Model(Model model, String matchID) {
		
		// get match info
		MatchVO match = matchService.getMatchById(matchID);
		if(match == null) {
			logger.error("Can not get match info by id: " + matchID);
			throw new SoccerProRuntimeException("Can not get match info by id: " + matchID);
		}
		model.addAttribute("match", match);
		
		// get match goals
		List<MatchGoalVO> goals = matchService.getMatchGoalsByMatchId(matchID);
		List<MatchGoalVO> homeGoals = new ArrayList<MatchGoalVO>();
		List<MatchGoalVO> guestGoals = new ArrayList<MatchGoalVO>();
		
		int homeAssistCount = 0;
		for(MatchGoalVO goal : goals) {
			if(StringUtils.isNotBlank(goal.getOrgId())) {
				homeGoals.add(goal);
				
				if(!StringUtil.isBlank(goal.getAssistPlayerId())) {
					homeAssistCount++;
				}
			} else {
				guestGoals.add(goal);
			}
		}
		
		model.addAttribute("homeGoals", homeGoals);
		model.addAttribute("guestGoals", guestGoals);
		
		// get match fouls
		List<MatchFoulVO> fouls = matchService.getMatchFoulsByMatchId(matchID);
		List<MatchFoulVO> homeFouls = new ArrayList<MatchFoulVO>();
		List<MatchFoulVO> guestFouls = new ArrayList<MatchFoulVO>();
		
		int homeYellowCount = 0, homeRedCount = 0;
		for(MatchFoulVO foul : fouls) {
			if(StringUtils.isNotBlank(foul.getOrgId())) {
				homeFouls.add(foul);
				
				if(StringUtils.equals(foul.getType(), Constants.MATCH_FOUL_YELLOW_CARD)) {
					homeYellowCount++;
				} else if(StringUtils.equals(foul.getType(), Constants.MATCH_FOUL_RED_CARD)) {
					homeRedCount++;
				}
			} else {
				guestFouls.add(foul);
			}
		}
		
		model.addAttribute("homeFouls", homeFouls);
		model.addAttribute("guestFouls", guestFouls);
		
		// get match subs
		List<MatchSubVO> subs = matchService.getMatchSubsByMatchId(matchID);
		List<MatchSubVO> homeSubs = new ArrayList<MatchSubVO>();
		List<MatchSubVO> guestSubs = new ArrayList<MatchSubVO>();
		
		for(MatchSubVO sub : subs) {
			if(StringUtils.isNotBlank(sub.getOrgId())) {
				homeSubs.add(sub);
			} else {
				guestSubs.add(sub);
			}
		}
		
		model.addAttribute("homeSubs", homeSubs);
		model.addAttribute("guestSubs", guestSubs);
		
		// get match data
		List<MatchDataVO> datas = matchService.getMatchDataByMatchId(matchID);
		model.addAttribute("datas", datas);
		
		if(datas.size() > 0) {
			Map<String, Integer> statistics = new HashMap<String, Integer>();
			for(MatchDataVO data : datas) {
				for(MatchDataItemVO item : data.getItemList()) {
					String key = item.getItemName();
					if(Arrays.asList(Constants.MATCH_DATA_ITEM_KEY_OF_NON_NUMBER).contains(key)) {
						continue;
					}
					
					int value = 0;
					try {
						value = Integer.parseInt(item.getItemValue());
					} catch(Exception e) {
						// do nothing, use default value 0
					}
					
					if(!statistics.containsKey(key)) {
						statistics.put(key, value);
					} else {
						int newValue = statistics.get(key) + value;
						statistics.put(key, newValue);
					}
				}
			}
			
			for(int i = 0; i < Constants.MATCH_DATA_ITEM_KEYS.length; i++) {
				if(!statistics.containsKey(Constants.MATCH_DATA_ITEM_KEYS[i])) {
					statistics.put(Constants.MATCH_DATA_ITEM_KEYS[i], 0);
				}
			}
			
			statistics.put("all_shoots", statistics.get("target_shoot") + statistics.get("aside_shoot"));
			int success_pass = statistics.get("short_pass") + statistics.get("long_pass") + statistics.get("forward_pass") + statistics.get("cross_pass");
			statistics.put("all_passes", success_pass + statistics.get("fail_pass"));
			statistics.put("all_steals", statistics.get("success_steal") + statistics.get("fail_steal"));
			statistics.put("success_pass", success_pass);
			statistics.put("match_goals", homeGoals.size());
			statistics.put("yellow_cards", homeYellowCount);
			statistics.put("red_cards", homeRedCount);
			statistics.put("match_assists", homeAssistCount);
			
			if(statistics.get("all_shoots") == 0) {
				statistics.put("shoot_percentage", 0);
			} else {
				statistics.put("shoot_percentage", (int) (((double) statistics.get("target_shoot")) / ((double) statistics.get("all_shoots")) * 100));
			}
			
			if(statistics.get("all_passes") == 0) {
				statistics.put("pass_percentage", 0);
			} else {
				statistics.put("pass_percentage", (int) (((double) success_pass) / ((double) statistics.get("all_passes")) * 100));
			}
			
			if(statistics.get("all_steals") == 0) {
				statistics.put("steal_percentage", 0);
			} else {
				statistics.put("steal_percentage", (int) (((double) statistics.get("success_steal")) / ((double) statistics.get("all_steals")) * 100));
			}
			
			model.addAttribute("statistics", statistics);
		}
		
		// get match assets
		List<MatchAssetVO> assets = matchService.getMatchAssetsByMatchId(matchID);
		List<MatchAssetVO> photos = new ArrayList<MatchAssetVO>();
		List<MatchAssetVO> attaches = new ArrayList<MatchAssetVO>();
		
		for(MatchAssetVO asset : assets) {
			if(StringUtils.equals(asset.getType(), Constants.MATCH_ASSET_PHOTO_TYPE)) {
				photos.add(asset);
			} else if(StringUtils.equals(asset.getType(), Constants.MATCH_ASSET_ATTACH_TYPE)) {
				attaches.add(asset);
			}
		}
		
		model.addAttribute("photos", photos);
		model.addAttribute("attaches", attaches);
	}
}

class MatchDateComparator implements Comparator<MatchVO>{
	@Override
	public int compare(MatchVO match1, MatchVO match2) {
		long time1 = match1.getDate().getTime();
		long time2 = match2.getDate().getTime();
		
		return time1 > time2 ? 1 : (time1 < time2 ? -1 : 0);
	}
}
