package cn.sportsdata.webapp.youth.web.controller.utraining;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.sportsdata.webapp.youth.common.auth.share.ShareLink;
import cn.sportsdata.webapp.youth.common.auth.share.ShareLinkBO;
import cn.sportsdata.webapp.youth.common.bo.UtrainingBO;
import cn.sportsdata.webapp.youth.common.constants.Constants;
import cn.sportsdata.webapp.youth.common.exceptions.SoccerProException;
import cn.sportsdata.webapp.youth.common.utils.CommonUtils;
import cn.sportsdata.webapp.youth.common.utils.SecurityUtils;
import cn.sportsdata.webapp.youth.common.utils.StringUtil;
import cn.sportsdata.webapp.youth.common.vo.OrgVO;
import cn.sportsdata.webapp.youth.common.vo.Response;
import cn.sportsdata.webapp.youth.common.vo.SingleTrainingVO;
import cn.sportsdata.webapp.youth.common.vo.UserVO;
import cn.sportsdata.webapp.youth.common.vo.utraining.TrainingMatchVO;
import cn.sportsdata.webapp.youth.service.share.ShareLinkService;
import cn.sportsdata.webapp.youth.service.share.validator.IShareLinkValidator;
import cn.sportsdata.webapp.youth.service.singletraining.SingleTrainingService;
import cn.sportsdata.webapp.youth.service.user.UserService;
import cn.sportsdata.webapp.youth.service.utraining.UtrainingService;
import cn.sportsdata.webapp.youth.web.controller.BaseController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/utraining")
public class UtrainingController extends BaseController {

	private static final Logger logger = Logger.getLogger(UtrainingController.class);

	public static final String KEY_TASK_EXT_TASK_TRAINING_DESC = "key_task_ext_task_training_desc";
	public static final String KEY_TASK_EXT_TASK_TRAINING_GOAL = "key_task_ext_task_training_goal";
	public static final String KEY_TASK_EXT_TASK_MATCH_OPPONENT = "key_task_ext_task_match_opponent";
	public static final String KEY_TASK_EXT_TASK_MATCH_ID = "key_task_ext_task_match_id";
	public static final String KEY_TASK_EXT_TASK_MATCH_DRESS = "key_task_ext_task_match_dress";
	public static final String KEY_TASK_EXT_TASK_MATCH_FOOD = "key_task_ext_task_match_food";
	public static final String KEY_TASK_EXT_TASK_MATCH_NOTE = "key_task_ext_task_match_note";
	public static final String KEY_TASK_EXT_TASK_OTHER_NOTE = "key_task_ext_task_other_note";
	


	public static final String KEY_TASK_EVALUATION_TRAINING_TRAINING = "key_task_evaluation_training_training";
	public static final String KEY_TASK_EVALUATION_TRAINING_HURT = "key_task_evaluation_training_hurt";
	public static final String KEY_TASK_EVALUATION_TRAINING_ILL = "key_task_evaluation_training_ill";
	public static final String KEY_TASK_EVALUATION_TRAINING_ABSENT = "key_task_evaluation_training_absent";

	public static final String KEY_TASK_EVALUATION_MATCH_STARTING = "key_task_evaluation_match_starting";
	public static final String KEY_TASK_EVALUATION_MATCH_REPLACE = "key_task_evaluation_match_replace";
	public static final String KEY_TASK_EVALUATION_MATCH_ABSENT = "key_task_evaluation_match_absent";
	
	
	@Autowired
	private UtrainingService utrainingService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private SingleTrainingService singletrainingService;
	
	@Autowired
	@Qualifier("parameterValidator")
	private IShareLinkValidator shareLinkValidator;
	
	@Autowired
	private ShareLinkService sharelinkService;

	@RequestMapping(value = "/show_calendar")
	public Object showUtrainingCalendar(HttpServletRequest request, HttpServletResponse response) {
		logger.info("UtrainingController.showUtrainingCalendar");

		OrgVO orgVO = getCurrentOrg(request);

		return new ModelAndView("/utraining/utraining_calendar").addObject("orgVO",
				orgVO);
	}

	@ResponseBody
	@RequestMapping(value = "/get_calendar_info")
	public Object getUtrainingCalendarInfo(HttpServletRequest request, HttpServletResponse response, String year) {
		logger.info("getUtrainingCalendarInfo");

		OrgVO orgVO = getCurrentOrg(request);

		Calendar calendar = Calendar.getInstance();

		// year calendar info
		calendar.set(Calendar.YEAR, Integer.parseInt(year));
		int firstDayOfWeek = calendar.getFirstDayOfWeek();
		int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
		int sumDay = calendar.getActualMaximum(Calendar.DAY_OF_YEAR);
		int maxWeek = calendar.getActualMaximum(Calendar.WEEK_OF_MONTH) + 1;
		
//		logger.debug("firstDayOfWeek:" + firstDayOfWeek + "  dayOfYear:" + dayOfYear + "   sumDay:" + sumDay);
//		logger.debug("maxWeek:" + (maxWeek));

		int maxColCount = (maxWeek) * 7;

		List<String> dayInMonth = null;
		Map<String, Object> oneMonth = new HashMap<String, Object>();
		for (int i = 0; i < 12; i++) {
			dayInMonth = new ArrayList<String>();
			calendar.set(Calendar.MONTH, i);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
			int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 2;
			if (dayOfWeek < 0) {
				dayOfWeek = 6;
			}
//			logger.debug("");
//			logger.debug("month:" + (i + 1) + " days in month:" + daysInMonth);
			for (int j = 0; j < dayOfWeek; j++) {
				dayInMonth.add(" ");
			}
			for (int j = 0; j < daysInMonth; j++) {
				dayInMonth.add(calendar.get(Calendar.DAY_OF_MONTH) + "");
				calendar.add(Calendar.DAY_OF_YEAR, 1);
			}
			for (int j = 0; j < (maxColCount - dayOfWeek - daysInMonth); j++) {
				dayInMonth.add(" ");
			}

			oneMonth.put((i + 1) + "", dayInMonth);
		}

		// year training info
		List<UtrainingBO> utrainingList = utrainingService.getUtrainingInYear(year, orgVO.getId());
		Calendar startCalendar = Calendar.getInstance();
		Calendar endCalendar = Calendar.getInstance();
		Date startDate = null;
		Date endDate = null;

		Map<String, List<Map<String, Object>>> allTrainingMap = new HashMap<String, List<Map<String, Object>>>();

		for (UtrainingBO bo : utrainingList) {
			// COLOR
			if (Constants.UTRAINING_CALENDAR_COLOR_BLUE.equals(bo.getColor())) {
				bo.setColor(Constants.UTRAINING_CALENDAR_COLOR_BLUE_RGB);
			} else if (Constants.UTRAINING_CALENDAR_COLOR_GREEN.equals(bo.getColor())) {
				bo.setColor(Constants.UTRAINING_CALENDAR_COLOR_GREEN_RGB);
			} else if (Constants.UTRAINING_CALENDAR_COLOR_YELLOW.equals(bo.getColor())) {
				bo.setColor(Constants.UTRAINING_CALENDAR_COLOR_YELLOW_RGB);
			} else if (Constants.UTRAINING_CALENDAR_COLOR_RED.equals(bo.getColor())) {
				bo.setColor(Constants.UTRAINING_CALENDAR_COLOR_RED_RGB);
			}

			startDate = bo.getStartDate();
			startCalendar.setTime(startDate);
			if (startCalendar.get(Calendar.YEAR) != Integer.parseInt(year)) {
				startCalendar.set(Integer.parseInt(year), 0, 1);
			}
			endDate = bo.getEndDate();
			endCalendar.setTime(endDate);
			if (endCalendar.get(Calendar.YEAR) != Integer.parseInt(year)) {
				endCalendar.set(Integer.parseInt(year), 11, 31);
			}
			int startMonth = startCalendar.get(Calendar.MONTH) + 1;
			int endMonth = endCalendar.get(Calendar.MONTH) + 1;
			int startDay = startCalendar.get(Calendar.DAY_OF_MONTH);
			int endDay = endCalendar.get(Calendar.DAY_OF_MONTH);
			Calendar increaceCal = Calendar.getInstance();
			increaceCal.setTime(startCalendar.getTime());
			for (int i = startMonth; i <= endMonth; i++) {
				List<Map<String, Object>> utrainingInMonth = null;
				if (allTrainingMap.get(String.valueOf(i)) == null) {
					utrainingInMonth = new ArrayList<Map<String, Object>>();
					allTrainingMap.put(String.valueOf(i), utrainingInMonth);
				} else {
					utrainingInMonth = allTrainingMap.get(String.valueOf(i));
				}
				List<String> daysInMonth = new ArrayList<String>();
				Map<String, Object> oneTraining = new HashMap<String, Object>();
				if (i == startMonth && i != endMonth) {
					// first month from startday to month end
					for (int k = startDay; k <= startCalendar.getActualMaximum(Calendar.DAY_OF_MONTH); k++) {
						daysInMonth.add(String.valueOf(k));
					}
				} else if (i == endMonth && i != startMonth) {
					// last month from month startday to training endday
					for (int k = 1; k <= endDay; k++) {
						daysInMonth.add(String.valueOf(k));
					}
				} else if (i == startMonth && i == endMonth) {
					for (int k = startDay; k <= endDay; k++) {
						daysInMonth.add(String.valueOf(k));
					}
				} else {
					for (int k = 1; k <= increaceCal.getActualMaximum(Calendar.DAY_OF_MONTH); k++) {
						daysInMonth.add(String.valueOf(k));
					}
				}
				increaceCal.add(Calendar.MONTH, 1);

				oneTraining.put("days", daysInMonth);
				oneTraining.put("trainingId", bo.getId());
				oneTraining.put("trainingName", bo.getName());
				oneTraining.put("color", bo.getColor());

				utrainingInMonth.add(oneTraining);
			}

		}

		// year match info
		// TODO:year match info
		Map<String, List<Map<String, Object>>> allMathMap = new HashMap<String, List<Map<String, Object>>>();
		List<TrainingMatchVO> trainingMatchVOs = utrainingService.getUtrainingMatchInOneYear(year, orgVO.getId());
		if (trainingMatchVOs != null) {
			for (int i = 0; i < trainingMatchVOs.size(); i++) {
				TrainingMatchVO matchVO = trainingMatchVOs.get(i);
				Calendar cal = Calendar.getInstance();
				cal.setTimeInMillis(matchVO.getMatchDate().getTime());
				int month = cal.get(Calendar.MONTH);
				
				Map<String, Object> match = new HashMap<String, Object>();
				List<String> daysInMonth = new ArrayList<String>();
				daysInMonth.add(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)));
				match.put("days", daysInMonth);
				match.put("trainingId", matchVO.getTrainingID());
				match.put("trainingName", matchVO.getTrainingName());
				match.put("opponent", matchVO.getOpponent());
				match.put("color", "red");
				List<Map<String, Object>> matchInMonth = allMathMap.get(String.valueOf(++month));
				if (matchInMonth == null) {
					matchInMonth = new ArrayList<Map<String, Object>>();
					allMathMap.put(String.valueOf(month), matchInMonth);
				}
				matchInMonth.add(match);
				
			}
		}
		
		

		String allDataJson = null;
		ObjectMapper mapper = new ObjectMapper();
		String allDaysJson = null;
		String allTrainingJson = null;
		String allMatchJson = null;
		try {
			Map<String, String> allDataMap = new HashMap<String, String>();

			allDaysJson = mapper.writeValueAsString(oneMonth);

			allDataMap.put("allMonthData", allDaysJson);
			allDataMap.put("maxWeek", String.valueOf(maxWeek));

			allTrainingJson = mapper.writeValueAsString(allTrainingMap);
			allDataMap.put("trainingData", allTrainingJson);
			
			allMatchJson = mapper.writeValueAsString(allMathMap);
			allDataMap.put("matchData", allMatchJson);

			allDataJson = mapper.writeValueAsString(allDataMap);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Response.toSussess(allDataJson);
	}

	@RequestMapping(value = "/edit_calendar")
	public Object editUtraining(HttpServletRequest request, HttpServletResponse response, String utrainingId,
			String func, String frompage, @RequestBody(required = false) UtrainingBO bo) throws SoccerProException {
		logger.info("UtrainingController.editUtraining");

		String orgID = getCurrentOrg(request).getId();
		if (bo == null) {
			if (!StringUtils.isEmpty(utrainingId)) {
				bo = utrainingService.getUtrainingById(utrainingId);
				String basePath = CommonUtils.getServerUrl(request);
				String barCodeLink = getShareLink(basePath, orgID, utrainingId);
				bo.setBarCodeLink(barCodeLink);
			} else {
				bo = new UtrainingBO();
			}

		}
		
		// all players in org
		List<UserVO> playerList = userService.getPlayersByOrgId(orgID);

		List<UserVO> forwardList = new ArrayList<UserVO>();
		List<UserVO> centerList = new ArrayList<UserVO>();
		List<UserVO> defenderList = new ArrayList<UserVO>();
		List<UserVO> goalKeeperList = new ArrayList<UserVO>();
		
		List<UserVO> selecedList = new ArrayList<UserVO>();
		
		List<UserVO> allPlayerList = new ArrayList<UserVO>();

		UserVO user = null;
		
		for (UserVO player : playerList) {
			user = new UserVO();
			user.setId(player.getId());
			user.setName(player.getName());
			allPlayerList.add(user);
			String position = player.getUserExtInfoMap().get("professional_primary_position");

			if (bo.getTrainingPlayIdList() != null && bo.getTrainingPlayIdList().size() > 0) {
				if (bo.getTrainingPlayIdList().contains(player.getId())) {
					player.setChecked(true);
					selecedList.add(player);
				} else {
					player.setChecked(false);
				}
			}

			switch (position) {
			case Constants.FORWARD_POSITION:
				forwardList.add(player);
				break;
			case Constants.CENTER_POSITION:
				centerList.add(player);
				break;
			case Constants.DEFENDER_POSITION:
				defenderList.add(player);
				break;
			case Constants.GOALKEEPER_POSITION:
				goalKeeperList.add(player);
				break;
			default:
				break;
			}
		}

		bo.setForwardList(forwardList);
		bo.setCenterList(centerList);
		bo.setDefenderList(defenderList);
		bo.setGoalKeeperList(goalKeeperList);
		
		bo.setPlayerList(selecedList);
		bo.setAllPlayerList(allPlayerList);
		
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		try {
			json = mapper.writeValueAsString(bo);
		} catch (JsonProcessingException e) {
			throw new SoccerProException("conver bo to jso failed", e);
		}

		logger.info(json);

		return new ModelAndView("/utraining/utraining_detail").addObject("utrainingBO", bo).addObject("func", func)
				.addObject("utrainingId", utrainingId).addObject("frompage", frompage).addObject("boJson", json);
	}

	@ResponseBody
	@RequestMapping(value = "/save_calender")
	public Object saveUtrainingCalendarInfo(HttpServletRequest request, HttpServletResponse response,
			@RequestBody UtrainingBO utrainingBO) {

		if(utrainingBO.getStartDate()==null||utrainingBO.getEndDate()==null){
			return Response.toFailure(510, "date emtpy");
		}
		utrainingBO.getStartDate().setHours(0);
		utrainingBO.getEndDate().setHours(23);
		utrainingBO.getEndDate().setMinutes(59);
		utrainingBO.getEndDate().setSeconds(59);
		
		utrainingBO.setOrgId(getCurrentOrg(request).getId());
		
		if(!utrainingService.checkUtrainingDate(utrainingBO.getId(),utrainingBO.getStartDate(), utrainingBO.getEndDate(),utrainingBO.getOrgId())){
			return Response.toFailure(501, "date conflict");
		}
		
		String id = "";

		if (!StringUtil.isBlank(utrainingBO.getId())) {
			// update
			if (utrainingService.updateUtrainingById(utrainingBO)) {
				id = utrainingBO.getId();
			}
		} else {
			// add
			id = utrainingService.addNewUtraining(utrainingBO);
		}

		if (!StringUtil.isBlank(id)) {
			return Response.toSussess(id);
		} else {
			return Response.toFailure(500, "oprate failed");
		}
	}

	@ResponseBody
	@RequestMapping(value = "/delete_calender")
	public Object deleteUtrainingCalendarInfo(HttpServletRequest request, HttpServletResponse response,
			String utrainingId) {
		
		OrgVO orgVO = getCurrentOrg(request);
		boolean flag = utrainingService.deleteUtrainingById(utrainingId, orgVO.getId());

		if (flag) {
			return Response.toSussess("");
		} else {
			return Response.toFailure(500, "oprate failed");
		}
	}

	@RequestMapping(value = "/edit_training_task")
	public Object editUtrainingTask(HttpServletRequest request, HttpServletResponse response,
			@RequestBody UtrainingBO utrainingBO, String func, String tempId, String utrainingId,String taskDate) {
		logger.info("UtrainingController.editUtrainingTask");

		OrgVO orgVO = getCurrentOrg(request);
		
		if(!StringUtils.isEmpty(taskDate)){
			String year = taskDate.substring(0,4);
			String month = taskDate.substring(4,6);
			String day = taskDate.substring(6,8);
			
			taskDate = year+"-"+month+"-"+day;
			logger.info(taskDate);
		}
		
		List<SingleTrainingVO> singleTraininglist = singletrainingService.getSingleTrainingList(orgVO.getId());
		
		
		return new ModelAndView("/utraining/utraining_task").addObject("utrainingBO", utrainingBO)
				.addObject("orgVO", orgVO).addObject("func", func).addObject("tempId", tempId)
				.addObject("utrainingId", utrainingId).addObject("taskDate",taskDate).addObject("singleTraininglist",singleTraininglist);
	}

	@RequestMapping(value = "/view_training_task")
	public Object viewUtrainingTask(HttpServletRequest request, HttpServletResponse response, String tempId,
			String func, String utrainingId,String orgId) {
		logger.info("UtrainingController.viewUtrainingTask");
		OrgVO orgVO = getCurrentOrg(request);
		
		List<SingleTrainingVO> singleTraininglist = singletrainingService.getSingleTrainingList(orgVO.getId());
		List<HashMap<String, Object>> list = new ArrayList();
		if (singleTraininglist != null) {
			for (int i = 0; i < singleTraininglist.size(); i++) {
				HashMap<String, Object> hashMap = new HashMap();
				hashMap.put("name", singleTraininglist.get(i).getName());
				hashMap.put("id", singleTraininglist.get(i).getId());
				list.add(hashMap);
			}
		}
		
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		try {
			json = mapper.writeValueAsString(list);
		} catch (JsonProcessingException e) {
			logger.error("convert JSON failed. orgID:" + orgVO.getId(), e);
		}

		return new ModelAndView("/utraining/utraining_task_view").addObject("tempId", tempId).addObject("func", func)
				.addObject("utrainingId", utrainingId).addObject("orgId", orgId).addObject("singleTrainingList",json);
	}
	
	
	
	@RequestMapping(value = "/share_plan")
	public Object shareUtraining(HttpServletRequest request, HttpServletResponse response, @RequestParam(required = true) String utrainingId, 
			@RequestParam(required = true) String orgId, @RequestParam(required = true) String token) throws SoccerProException {
		
		ShareLinkBO originalShareLinkBO = new ShareLinkBO("/utraining/share_plan");
		originalShareLinkBO.addParameter("orgId", String.valueOf(orgId));
		originalShareLinkBO.addParameter("utrainingId", String.valueOf(utrainingId));
		
		if (!shareLinkValidator.validate(token, originalShareLinkBO)) {
			logger.error("validate share link failed");
			throw new SoccerProException("validate share link failed");
		}
		
		
		logger.info("UtrainingController.shareUtraining");
		
		UtrainingBO utrainingBO = null;
		if (!StringUtils.isEmpty(utrainingId)) {
			utrainingBO = utrainingService.getUtrainingById(utrainingId);
		}
		
		if (utrainingBO == null) {
			logger.error("can not found training plan");
			throw new SoccerProException("can not found training plan");
		}
		
			
		
		// all players in org
		List<UserVO> playerList = userService.getPlayersByOrgId(orgId);
		
		List<UserVO> forwardList = new ArrayList<UserVO>();
		List<UserVO> centerList = new ArrayList<UserVO>();
		List<UserVO> defenderList = new ArrayList<UserVO>();
		List<UserVO> goalKeeperList = new ArrayList<UserVO>();
		
		List<UserVO> selecedList = new ArrayList<UserVO>();
		
		List<UserVO> allPlayerList = new ArrayList<UserVO>();
		
		UserVO user = null;
		
		for (UserVO player : playerList) {
			user = new UserVO();
			user.setId(player.getId());
			user.setName(player.getName());
			allPlayerList.add(user);
			String position = player.getUserExtInfoMap().get("professional_primary_position");
			
			if (utrainingBO.getTrainingPlayIdList() != null && utrainingBO.getTrainingPlayIdList().size() > 0) {
				if (utrainingBO.getTrainingPlayIdList().contains(player.getId())) {
					player.setChecked(true);
					selecedList.add(player);
				} else {
					player.setChecked(false);
				}
			}
			
			switch (position) {
			case Constants.FORWARD_POSITION:
				forwardList.add(player);
				break;
			case Constants.CENTER_POSITION:
				centerList.add(player);
				break;
			case Constants.DEFENDER_POSITION:
				defenderList.add(player);
				break;
			case Constants.GOALKEEPER_POSITION:
				goalKeeperList.add(player);
				break;
			default:
				break;
			}
		}
		
		utrainingBO.setForwardList(forwardList);
		utrainingBO.setCenterList(centerList);
		utrainingBO.setDefenderList(defenderList);
		utrainingBO.setGoalKeeperList(goalKeeperList);
		
		utrainingBO.setPlayerList(selecedList);
		utrainingBO.setAllPlayerList(allPlayerList);
		
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		try {
			json = mapper.writeValueAsString(utrainingBO);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new ModelAndView("/utraining/utraining_share").addObject("title", utrainingBO.getName())
				.addObject("utrainingId", utrainingId).addObject("boJson", json);
	}
	
	@RequestMapping(value = "/get_share_info")
	public Object getShareUtraining(HttpServletRequest request, HttpServletResponse response, String utrainingId,
			String func, String frompage, @RequestBody(required = false) UtrainingBO bo,String orgId) {
		logger.info("UtrainingController.shareUtraining");
		
		
		if (bo == null) {
			if (!StringUtils.isEmpty(utrainingId)) {
				bo = utrainingService.getUtrainingById(utrainingId);
			} else {
				bo = new UtrainingBO();
			}
			
		}
		
		// all players in org
		List<UserVO> playerList = userService.getPlayersByOrgId(orgId);
		
		List<UserVO> forwardList = new ArrayList<UserVO>();
		List<UserVO> centerList = new ArrayList<UserVO>();
		List<UserVO> defenderList = new ArrayList<UserVO>();
		List<UserVO> goalKeeperList = new ArrayList<UserVO>();
		
		List<UserVO> selecedList = new ArrayList<UserVO>();
		
		List<UserVO> allPlayerList = new ArrayList<UserVO>();
		
		UserVO user = null;
		
		for (UserVO player : playerList) {
			user = new UserVO();
			user.setId(player.getId());
			user.setName(player.getName());
			allPlayerList.add(user);
			String position = player.getUserExtInfoMap().get("professional_primary_position");
			
			if (bo.getTrainingPlayIdList() != null && bo.getTrainingPlayIdList().size() > 0) {
				if (bo.getTrainingPlayIdList().contains(player.getId())) {
					player.setChecked(true);
					selecedList.add(player);
				} else {
					player.setChecked(false);
				}
			}
			
			switch (position) {
			case Constants.FORWARD_POSITION:
				forwardList.add(player);
				break;
			case Constants.CENTER_POSITION:
				centerList.add(player);
				break;
			case Constants.DEFENDER_POSITION:
				defenderList.add(player);
				break;
			case Constants.GOALKEEPER_POSITION:
				goalKeeperList.add(player);
				break;
			default:
				break;
			}
		}
		
		bo.setForwardList(forwardList);
		bo.setCenterList(centerList);
		bo.setDefenderList(defenderList);
		bo.setGoalKeeperList(goalKeeperList);
		
		bo.setPlayerList(selecedList);
		bo.setAllPlayerList(allPlayerList);
		
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		try {
			json = mapper.writeValueAsString(bo);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new ModelAndView("/utraining/utraining_detail").addObject("utrainingBO", bo).addObject("func", "view")
				.addObject("utrainingId", utrainingId).addObject("frompage", "calendar").addObject("boJson", json)
				.addObject("reqSource", "share").addObject("orgId", orgId);
	}
	
	private String getShareLink(String basePath,String orgId, String utrainingId) throws SoccerProException {
		ShareLink shareLink = utrainingService.getShareLink(orgId, utrainingId);
		String shareLinkStr = sharelinkService.getShareLink(shareLink, basePath);
		
		String encryptedShareLink = SecurityUtils.encryptByAES(shareLinkStr);
		StringBuffer barcodeLink = new StringBuffer(basePath);
		barcodeLink.append("file/getBarcode?content=").append(encryptedShareLink);
		return barcodeLink.toString();
	}
}
