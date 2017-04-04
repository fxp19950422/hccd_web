package cn.sportsdata.webapp.youth.web.controller.user;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.SqlDateConverter;
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

import cn.sportsdata.webapp.youth.common.bo.UserBO;
import cn.sportsdata.webapp.youth.common.constants.Constants;
import cn.sportsdata.webapp.youth.common.exceptions.SoccerProException;
import cn.sportsdata.webapp.youth.common.utils.SecurityUtils;
import cn.sportsdata.webapp.youth.common.utils.StringUtil;
import cn.sportsdata.webapp.youth.common.vo.AssetVO;
import cn.sportsdata.webapp.youth.common.vo.DepartmentVO;
import cn.sportsdata.webapp.youth.common.vo.OrgVO;
import cn.sportsdata.webapp.youth.common.vo.Response;
import cn.sportsdata.webapp.youth.common.vo.UserOrgRoleVO;
import cn.sportsdata.webapp.youth.common.vo.UserVO;
import cn.sportsdata.webapp.youth.common.vo.account.AccountVO;
import cn.sportsdata.webapp.youth.common.vo.login.LoginVO;
import cn.sportsdata.webapp.youth.common.vo.role.RoleVO;
import cn.sportsdata.webapp.youth.common.vo.utraining.UtrainingVO;
import cn.sportsdata.webapp.youth.service.account.AccountService;
import cn.sportsdata.webapp.youth.service.department.DepartmentService;
import cn.sportsdata.webapp.youth.service.user.UserService;
import cn.sportsdata.webapp.youth.service.utraining.UtrainingService;
import cn.sportsdata.webapp.youth.web.controller.BaseController;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
	private static final int END_POSITION_OF_SUB_PASSWORD = 16;

	private static final int START_POSITION_OF_SUB_PASSWORD = 0;

	private static Logger logger = Logger.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private DepartmentService departService;
	
	@Autowired
	private UtrainingService trainingService;
	
	@RequestMapping("/showPlayerList")
    public String showPlayerList(HttpServletRequest request, Model model) {
		OrgVO orgVO = getCurrentOrg(request);
		List<UserVO> playerList = userService.getPlayersByOrgId(orgVO.getId());
		
		List<UserVO> forwardList = new ArrayList<UserVO>();
		List<UserVO> centerList = new ArrayList<UserVO>();
		List<UserVO> defenderList = new ArrayList<UserVO>();
		List<UserVO> goalKeeperList = new ArrayList<UserVO>();
		
		for(UserVO player : playerList) {
			String position = player.getUserExtInfoMap().get("professional_primary_position");
			
			switch(position) {
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
		
		model.addAttribute("forwardList", forwardList);
		model.addAttribute("centerList", centerList);
		model.addAttribute("defenderList", defenderList);
		model.addAttribute("goalKeeperList", goalKeeperList);
		
		return "user/player_list";
	}
	
	@RequestMapping("/showPlayerDetail")
    public String showPlayerDetail(HttpServletRequest request, Model model, @RequestParam String userID) {
		
		UserVO player = userService.getUserByID(userID);
		model.addAttribute("player", player);
		
		List<UtrainingVO> trainingList = trainingService.getUtrainingsByUserId(userID);
		model.addAttribute("trainingList", trainingList);
		
		return "user/player_detail";
	}
	
	@RequestMapping("/showPlayerEdit")
    public String showPlayerEdit(HttpServletRequest request, Model model, @RequestParam(required=false) String userID) {
		if(StringUtils.isBlank(userID)) { // create case
			model.addAttribute("isCreate", true);
		} else {
			model.addAttribute("isCreate", false);
			
			UserVO player = userService.getUserByID(userID);
			model.addAttribute("player", player);
		}
		
		return "user/player_edit";
	}
	
	@ResponseBody
	@RequestMapping(value = "/saveUser", method = RequestMethod.POST)
    public Response saveUser(HttpServletRequest request, @RequestBody UserBO userBO) {
		if(userBO.getBasicData() == null) {
			return Response.toFailure(500, "user basic data can not be null");
		}
		
		UserVO user = new UserVO();
		String playerId = userBO.getBasicData().getId();
		boolean isCreate = StringUtil.isBlank(playerId);
		
		try {
			ConvertUtils.register(new SqlDateConverter(null), Timestamp.class);
			BeanUtils.copyProperties(user, userBO.getBasicData());
			boolean isSuccess = false;
			
			if(isCreate) {
				AssetVO asset = null ;
				long roleId = userService.getRoleIdByRoleName(userBO.getBasicData().getRole());
				if(roleId == 0) {
					return Response.toFailure(500, "error user basic data");
				}
				
				UserOrgRoleVO uorVO = new UserOrgRoleVO();
				OrgVO orgVO = getCurrentOrg(request);
				uorVO.setHospitalId(orgVO.getId()); // should be current org
				uorVO.setRoleId(roleId);
				
				if (!StringUtils.isEmpty(user.getAvatar())){
					asset = getAssetVOFromUser(user.getAvatar(), null, request);
				}
				
				isSuccess = userService.createUser(user, userBO.getUserItemList(), uorVO , asset);
				
			} else {
				AssetVO asset = null ;
				UserVO originalUser = userService.getUserByID(playerId);
				if (!user.getAvatar().equals(originalUser.getAvatar())){
					asset = getAssetVOFromUser(user.getAvatar(), originalUser.getAvatarId(), request);
				}
				
				long roleId = userService.getRoleIdByRoleName(userBO.getBasicData().getRole());
				if(roleId == 0) {
					return Response.toFailure(500, "error user basic data");
				}
				
				UserOrgRoleVO uorVO = new UserOrgRoleVO();
				OrgVO orgVO = getCurrentOrg(request);
				uorVO.setUserId(userBO.getBasicData().getId());
				uorVO.setHospitalId(orgVO.getId()); // should be current org
				uorVO.setRoleId(roleId);
				
				isSuccess = userService.updateUser(user, userBO.getUserItemList(),uorVO, asset);
			}
			
			return isSuccess ? Response.toSussess(String.valueOf(user.getId())) : Response.toFailure(500, isCreate ? "insert new user error" : "update user " + playerId + " error");
		} catch(Exception e) {
			logger.error(isCreate ? "Error occurs while creating user: " : "Error occurs while updating user " + playerId + ": ", e);
		}
		
		return Response.toFailure(500, isCreate ? "insert new user error" : "update user " + playerId + " error");
	}
	
	@ResponseBody
	@RequestMapping(value = "/deleteUser", method = RequestMethod.DELETE)
    public Response deleteUser(HttpServletRequest request, @RequestParam String userID) {
		if(StringUtils.isBlank(userID)) {
			return Response.toFailure(400, "user id " + userID + " is invalid");
		}
		
		boolean isSuccess = userService.deleteUser(userID);
		return isSuccess ? Response.toSussess(null) : Response.toFailure(500, "delete user " + userID + " error");
	}
	
	@RequestMapping("/showCoachList")
    public String showCoachList(HttpServletRequest request, Model model) {
		OrgVO orgVO = getCurrentOrg(request);
		List<UserVO> coachList = userService.getCoachsByOrgId(orgVO.getId());
		
		List<UserVO> chiefCoachList = new ArrayList<UserVO>();
		List<UserVO> assistantCoachList = new ArrayList<UserVO>();
		List<UserVO> fitnessCoachList = new ArrayList<UserVO>();
		List<UserVO> goalKeeperCoachList = new ArrayList<UserVO>();
		List<UserVO> researchCoachList = new ArrayList<UserVO>();
		List<UserVO> tacticsCoachList = new ArrayList<UserVO>();
		
		for(UserVO coach : coachList) {
			String position = coach.getRole();
			
			switch(position) {
				case Constants.ROLE_DIRECTOR:
					chiefCoachList.add(coach);
					break;
				case Constants.ROLE_DOCTOR:
					assistantCoachList.add(coach);
					break;
				case Constants.ROLE_NURSE:
					fitnessCoachList.add(coach);
					break;
//				case Constants.FITNESS_COACH:
//					fitnessCoachList.add(coach);
//					break;
//				case Constants.GOALKEEPER_COACH:
//					goalKeeperCoachList.add(coach);
//					break;
//				case Constants.RESEARCH_COACH:
//					researchCoachList.add(coach);
//					break;
//				case Constants.TACTICS_COACH:
//					tacticsCoachList.add(coach);
//					break;
				default:
					break;
			}
		}
		
		model.addAttribute("chiefCoachList", chiefCoachList);
		model.addAttribute("assistantCoachList", assistantCoachList);
		model.addAttribute("fitnessCoachList", fitnessCoachList);
		
		return "user/coach_list";
	}
	
	@RequestMapping("/showCoachEdit")
    public String showCoachEdit(HttpServletRequest request, Model model, @RequestParam(required=false) String userID) {
		if(StringUtils.isBlank(userID)) { // create case
			model.addAttribute("isCreate", true);
		} else {
			model.addAttribute("isCreate", false);
			
			UserVO coach = userService.getUserByID(userID);
			model.addAttribute("coach", coach);
		}
		List<DepartmentVO> departmentList = departService.getDepartmentList("1", "100001");
		model.addAttribute("departments", departmentList);
		
		return "user/coach_edit";
	}
	
	@RequestMapping("/showCoachDetail")
    public String showCoachDetail(HttpServletRequest request, Model model, @RequestParam String userID) {
		
		UserVO coach = userService.getUserByID(userID);
		model.addAttribute("coach", coach);
		
		return "user/coach_detail";
	}
	
	
	@RequestMapping("/account_manage")
    public String showaccountList(HttpServletRequest request, Model model) {
		LoginVO loginVO = getCurrentUser(request);
		OrgVO org = this.getCurrentOrg(request);
		List<AccountVO> accounts = accountService.getAccountsByOrg(org.getId());
		
		String loginId = loginVO.getId();
		for (AccountVO account: accounts) {
			if (account.getId().equals(loginId)) {
				account.setCurLogin(true);
				accounts.remove(account);
				accounts.add(0, account);
				model.addAttribute("curLoginAccount", account);
				break;
			}
		}
		
		model.addAttribute("accounts", accounts);
		
		return "account/account_list";
	}
	

	@RequestMapping("/account_add")
    public String showAccountAdd(HttpServletRequest request, Model model, @RequestParam(required=false) String coachId) {

		if(!StringUtils.isBlank(coachId)) { // create case
			UserVO coach = userService.getUserByID(coachId);
			model.addAttribute("coachId", coachId);
			model.addAttribute("email", coach.getEmail());
			model.addAttribute("name", coach.getName());
			if (coach.getAvatar() != null && !StringUtils.isBlank(coach.getAvatar())) {
				model.addAttribute("avatar", coach.getAvatar());
			}
	    	try {
		    	Date birthdate = coach.getBirthday();
		    	DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		    	String birthdayformated = format1.format(birthdate);
		    	model.addAttribute("birthday", birthdayformated);
	    	} catch (Exception e) {
	    		model.addAttribute("birthday", coach.getBirthday());
	    	}
		}
		return "account/account_edit";
	}
	
	@RequestMapping("/account_edit")
    public String showAccountEdit(HttpServletRequest request, Model model, String id) {
		AccountVO account = accountService.getAccountByID(id);
	    String strBirthday = account.getBirthday();
	    if(strBirthday != null && !StringUtils.isBlank(strBirthday)) {
	    	try {
		    	Date birthdate = Timestamp.valueOf(strBirthday);
		    	DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		    	String birthday = format1.format(birthdate);
		    	account.setBirthday(birthday);
	    	} catch (Exception e) {
	    		
	    	}
	    }
	    
		model.addAttribute("account", account);
		
		return "account/account_edit";
	}
	
	@RequestMapping("/personal_edit")
    public String showPersonalEdit(HttpServletRequest request, Model model, @RequestParam(required=false) Boolean isDashboard) {
		
		LoginVO currentAccount = this.getCurrentUser(request);
		AccountVO account = accountService.getAccountByID(currentAccount.getId());
		account.setEncryptedPassword(account.getPassword().substring(START_POSITION_OF_SUB_PASSWORD, END_POSITION_OF_SUB_PASSWORD));
		model.addAttribute("account", account);
		
		if (isDashboard !=null && !isDashboard) {
			model.addAttribute("isDashboard", isDashboard);
		} else {
			model.addAttribute("isDashboard", true);
		}
		
		return "account/personal_edit";
	}
	
	@ResponseBody
	@RequestMapping(value = "/saveAccount", method = RequestMethod.POST)
	public Object saveAccount(HttpServletRequest request, AccountVO accountVo) {
		try {
			OrgVO org = this.getCurrentOrg(request);
			
			if (!StringUtil.isBlank(accountVo.getId())) {
				AssetVO asset = null ;
				AccountVO originalAccount = accountService.getAccountByID(accountVo.getId());
				if (!StringUtils.isEmpty(accountVo.getPassword())){
					if (!accountVo.getPassword().equals(originalAccount.getPassword().substring(START_POSITION_OF_SUB_PASSWORD, END_POSITION_OF_SUB_PASSWORD))) {
						accountVo.setPassword(SecurityUtils.generateHashPassword(accountVo.getPassword()));
					} else {
						accountVo.setPassword(originalAccount.getPassword());
					}
				}
				if (!accountVo.getAvatar().equals(originalAccount.getAvatar())){
//					try {
//						String avatarPath = SecurityUtils.decryptByAES(accountVo.getAvatar());
//						String fileDisplayName = avatarPath.substring(avatarPath.lastIndexOf("/"), avatarPath.length()).replace("/", "");
//						String fileExt = fileDisplayName.substring(fileDisplayName.indexOf("."), fileDisplayName.length()).replace(".", "");
//						String fileStoragePath = accountVo.getAvatar();
//						asset = new AssetVO();
//						asset.setFile_extension(fileExt);
//						asset.setDisplay_name(fileDisplayName);
//						asset.setStorage_name(fileStoragePath);
//						asset.setOrg_id(this.getCurrentOrg(request).getId());
//						asset.setPrivacy(AssetVO.PRIVACY_protected);
//						asset.setCreator_id(this.getCurrentUser(request).getId());
//						if (!StringUtils.isEmpty(originalAccount.getAvatarId())){
//							asset.setId(originalAccount.getAvatarId());
//						}
//						
//					} catch (SoccerProException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
					asset = getAssetVOFromUser(accountVo.getAvatar(), originalAccount.getAvatarId(), request);
				}
				
				accountService.updateAccount(accountVo, org.getId(), RoleVO.STAFF_COACH, asset);
				 LoginVO loginVO = getCurrentUser(request);
				 if (loginVO.getId() == accountVo.getId()) {
					 LoginVO newloginVO = new LoginVO(accountVo);
					 this.setLoginVO(newloginVO, request);
				 }
				
			} else {
				if (!StringUtils.isEmpty(accountVo.getPassword())){
					accountVo.setPassword(SecurityUtils.generateHashPassword(accountVo.getPassword()));
					AssetVO asset = null ;
					if (!StringUtils.isEmpty(accountVo.getAvatar() )){
//						try {
//							String avatarPath = SecurityUtils.decryptByAES(accountVo.getAvatar());
//							String fileDisplayName = avatarPath.substring(avatarPath.lastIndexOf("/"), avatarPath.length()).replace("/", "");
//							String fileExt = fileDisplayName.substring(fileDisplayName.indexOf("."), fileDisplayName.length()).replace(".", "");
//							String fileStoragePath = accountVo.getAvatar();;
//							asset = new AssetVO();
//							asset.setFile_extension(fileExt);
//							asset.setDisplay_name(fileDisplayName);
//							asset.setStorage_name(fileStoragePath);
//							asset.setPrivacy(AssetVO.PRIVACY_protected);
//							asset.setOrg_id(this.getCurrentOrg(request).getId());
//							asset.setCreator_id(this.getCurrentUser(request).getId());
//						} catch (SoccerProException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
						asset = getAssetVOFromUser(accountVo.getAvatar(), null, request);
					}
					
					accountService.insertAccount(accountVo, org.getId(), RoleVO.STAFF_COACH, asset);
				}
			}
			return Response.toSussess(accountVo);
		} catch (RuntimeException ex) {
			return Response.toFailure(-1, ex.getMessage());
		}
	}
	
	private AssetVO getAssetVOFromUser(String avator, String originalAvatarId, HttpServletRequest request){
		AssetVO asset = null;
		try {
			String avatarPath = SecurityUtils.decryptByAES(avator);
			String fileDisplayName = avatarPath.substring(avatarPath.lastIndexOf("/"), avatarPath.length()).replace("/", "");
			String fileExt = fileDisplayName.substring(fileDisplayName.indexOf("."), fileDisplayName.length()).replace(".", "");
			String fileStoragePath = avator;
			asset = new AssetVO();
			asset.setFile_extension(fileExt);
			asset.setDisplay_name(fileDisplayName);
			asset.setStorage_name(fileStoragePath);
			asset.setOrg_id(this.getCurrentOrg(request).getId());
			asset.setPrivacy(AssetVO.PRIVACY_protected);
			asset.setCreator_id(this.getCurrentUser(request).getId());
			if (!StringUtils.isEmpty(originalAvatarId)){
				asset.setId(originalAvatarId);
			}
			return asset;
		} catch (SoccerProException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/resetPwd", method = RequestMethod.POST)
	public Object resetPwd(HttpServletRequest request, AccountVO accountVo) {
		try {
			String password = SecurityUtils.genrateInviteCode(accountVo.getUsername());
			
			accountVo.setPassword(SecurityUtils.generateHashPassword(password));
			
			int result = accountService.updatePwd(accountVo);
			if (result > 0){
				return Response.toSussess(password);
			} else {
				return Response.toFailure(-1, "重置密码失败");
			}
			
		} catch (RuntimeException ex) {
			return Response.toFailure(-1, ex.getMessage());
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/deleteAccount", method = RequestMethod.POST)
	public Object deleteAccount(HttpServletRequest request, AccountVO accountVo) {
		try {
			OrgVO orgVo = this.getCurrentOrg(request);
			LoginVO currentAccount = this.getCurrentUser(request);
			if(accountVo.getId() == currentAccount.getId()){
				return Response.toFailure(-1, "不能删除现在登录的账户");
			}
			
			int result = accountService.deleteAccount(accountVo, orgVo);
			
			if (result > 0){
				return Response.toSussess("");
			} else {
				return Response.toFailure(-1, "删除失败");
			}
			
		} catch (RuntimeException ex) {
			return Response.toFailure(-1, ex.getMessage());
		}
	}
}
