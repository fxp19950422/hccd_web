package cn.sportsdata.webapp.youth.web.controller.auth;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.sportsdata.webapp.youth.common.constants.Constants;
import cn.sportsdata.webapp.youth.common.exceptions.SoccerProException;
import cn.sportsdata.webapp.youth.common.utils.CookieUtils;
import cn.sportsdata.webapp.youth.common.utils.SecurityUtils;
import cn.sportsdata.webapp.youth.common.vo.DepartmentVO;
import cn.sportsdata.webapp.youth.common.vo.OrgVO;
import cn.sportsdata.webapp.youth.common.vo.Response;
import cn.sportsdata.webapp.youth.common.vo.account.AccountVO;
import cn.sportsdata.webapp.youth.common.vo.account.ActionAuthenticationVO;
import cn.sportsdata.webapp.youth.common.vo.basic.Token;
import cn.sportsdata.webapp.youth.common.vo.login.LoginVO;
import cn.sportsdata.webapp.youth.service.account.AccountService;
import cn.sportsdata.webapp.youth.service.account.ActionAuthenticationService;
import cn.sportsdata.webapp.youth.service.department.DepartmentService;
import cn.sportsdata.webapp.youth.web.utils.EmailUtils;
@Controller
@RequestMapping("/auth")
public class AuthController {
    private static Logger logger = Logger.getLogger(AuthController.class);
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private ActionAuthenticationService actionAuthenticationService;
	
	@Autowired @Qualifier("org.springframework.security.authenticationManager")
	private AuthenticationManager myAuthenticationManager;
	
	@RequestMapping(value="/login",method=RequestMethod.GET)
    public String login(HttpServletRequest request, HttpServletResponse response, Model model) throws SoccerProException {
		Token obj = (Token) request.getSession().getAttribute(Constants.USER_SESSION_KEY);
		if (obj == null || obj.getLoginVO() == null){
			return "auth/login";
		}
		
//		jMSProducer.sendMessage(queueDestination, "test");
		List<OrgVO> orgs = accountService.getOrgsByAccount(obj.getLoginVO().getId());
		if (orgs.size() <= 0){
			return "auth/login";
		} else if (orgs.size() == 1) {
			obj.setOrgVO(orgs.get(0));
			obj.initPrivilege();
			CookieUtils.setHagkCookie(response, obj);
			return "redirect:/system/system/index";
		} else {
			model.addAttribute("orgs", orgs);
			return "auth/org_select";
		}
//		return "redirect:/system/system/index";
    }
	
//	@ResponseBody
	@RequestMapping(value="/login",method=RequestMethod.POST)
	/**
	 * verify login request
	 * @param user UserVO
	 * @param model Model
	 * @return ModelAndView login url
	 */
	public Object verifyLogin(HttpServletRequest request, HttpServletResponse response, LoginVO loginVO, Model model, RedirectAttributes attrs) {
		response.setContentType("text/html;charset=utf-8");        
		String veryCode = request.getParameter("veryCode");  
		
        boolean bVeryCodeCorrect = false;
		try {
			bVeryCodeCorrect = ResultServlet.doVeryfy(request, veryCode);     
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		jMSProducer.sendMessage(queueDestination, "test");
//        if (bVeryCodeCorrect == false) {
//        	model.addAttribute("validationFailed", true);
//        	model.addAttribute("validationFailedMsg", "验证码有误");
//        	model.addAttribute("loginVO", loginVO);
//        	return "/auth/login";
//        }
        
        AccountVO accountVO = accountService.getAccountByUserName(loginVO.getUserName());
        if (accountVO != null){
	        LoginVO userVO = new LoginVO(accountVO);
			boolean verificationPassed = false;
			verificationPassed = SecurityUtils.verifyPassword(loginVO.getPassword(), userVO.getPassword());
			if (verificationPassed) {
				userVO.setHospitalUserInfo(accountService.getHospitalUserInfoById(accountVO.getId()));
				setToken(request, response, userVO);
				return "redirect:/auth/OrgSel"; 
			}
        }
		
		model.addAttribute("validationFailed", true);
    	model.addAttribute("validationFailedMsg", "用户名或密码有误");
    	model.addAttribute("loginVO", loginVO);
		return "/auth/login";
	}
	
	
	@RequestMapping(value="/OrgSel")
	/**
	 * verify login request
	 * @param user UserVO
	 * @param model Model
	 * @return ModelAndView login url
	 */
	public Object OrgSelect(HttpServletRequest request, HttpServletResponse response, Model model) throws SoccerProException {
		Token obj = (Token)request.getSession().getAttribute(Constants.USER_SESSION_KEY);
		if (obj.getLoginVO() == null){
			return "auth/login";
		}
		
		List<OrgVO> orgs = accountService.getOrgsByAccount(obj.getLoginVO().getId());
		
		if (orgs.size() <= 0){
			orgs.add(new OrgVO());
		}  
		
		if (orgs.size() == 1) {
			obj.setOrgVO(orgs.get(0));
			DepartmentVO department = departmentService.getDepartmentFromLoginId(obj.getLoginVO().getId());
			obj.setDepartmentVO(department);
			obj.initPrivilege();
			CookieUtils.setHagkCookie(response, obj);
			return "redirect:/system/system/index";
		} else {
			model.addAttribute("orgs", orgs);
			return "auth/org_select";
		}
//		obj.initPrivilege();
//		return "redirect:/system/system/index";
	}
	
	
	@RequestMapping(value="/org_determined")
	/**
	 * verify login request
	 * @param user UserVO
	 * @param model Model
	 * @return ModelAndView login url
	 */
	public Object determineOrg(HttpServletRequest request, HttpServletResponse response, String orgID) throws SoccerProException {
		Token obj = (Token)request.getSession().getAttribute(Constants.USER_SESSION_KEY);
		if (obj.getLoginVO() == null){
			return "auth/login";
		}
		OrgVO org = accountService.getOrgsByAccountOrgID(obj.getLoginVO().getId(), orgID);
		
		DepartmentVO department = departmentService.getDepartmentFromLoginId(obj.getLoginVO().getId());
		obj.setDepartmentVO(department);
		obj.setOrgVO(org);
		obj.initPrivilege();
		CookieUtils.setHagkCookie(response, obj);
		return "redirect:/system/system/index";
	}
	
	
	private void setToken(HttpServletRequest request, HttpServletResponse response, LoginVO loginVO) {
		Token token = new Token(loginVO);
		request.getSession().setAttribute(Constants.USER_SESSION_KEY, token);
	}
	
	@ResponseBody
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	/**
	 * logout and go to login page
	 * @return ModelAndView login url
	 */
    public Object logout(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().removeAttribute(Constants.USER_SESSION_KEY);
		CookieUtils.removeCookie(request, response, CookieUtils.HAGK_TOKEN);
		ModelAndView mv = new ModelAndView("/auth/login");
		return mv;
    }
	
	@RequestMapping(value="/forgetPW")
	/**
	 * go to forgetPW page
	 * @return ModelAndView forgetPW url
	 */
    public Object forgetPW() {
		ModelAndView mv = new ModelAndView("/auth/forgetPW");
		return mv;
		
    }
	
	@ResponseBody
	@RequestMapping(value="/forgetPW",method=RequestMethod.POST)
	/**
	 * verify forgetPW request
	 * @param user UserVO
	 * @param model Model
	 * @return ModelAndView login url
	 */
	public Object forgetPW(HttpServletRequest request, HttpServletResponse response, AccountVO user, Model model) {	
		AccountVO userVO = accountService.getAccountByUserName(user.getUsername());
		boolean emailSendSuccess = false;
		Response response_send = Response.toSussess("send mail success");
		if (userVO != null && user.getEmail().equals(userVO.getEmail()) == false) {
			response_send = Response.toFailure(10004, "email mismatch");
			return response_send;
		}
		if (userVO == null) {
			response_send = Response.toFailure(10003, "no such username");
			return response_send;
		}
		try{
			// add recode in reset_pwd database
			Date resetDate = new Date(System.currentTimeMillis());
			Long expirationtime = resetDate.getTime()+1000*60*60*24;
			String secretKey = UUID.randomUUID().toString(); // 密钥
			String key =userVO.getUsername() + "$" + expirationtime + "$" + secretKey;
			String randomMd5 = SecurityUtils.generateHashPassword(key);
			Date expirationDate = new Date(expirationtime);
			ActionAuthenticationVO actionAuthenticationVO = actionAuthenticationService.getActionAuthenticationByUserId(userVO.getId(), Constants.ACTION_AUTH_EMAIL_RESET_PWD);
			if (actionAuthenticationVO == null) {
				actionAuthenticationVO = new ActionAuthenticationVO();
				actionAuthenticationVO.setExpiration_time(expirationDate);
				actionAuthenticationVO.setRandom_md5(randomMd5);
				actionAuthenticationVO.setUser_id(userVO.getId());
				actionAuthenticationVO.setAction_type(Constants.ACTION_AUTH_EMAIL_RESET_PWD);
				actionAuthenticationService.insertActionAuthentication(actionAuthenticationVO);
			} else {
				actionAuthenticationVO.setExpiration_time(expirationDate);
				actionAuthenticationVO.setRandom_md5(randomMd5);
				actionAuthenticationVO.setUser_id(userVO.getId());
				actionAuthenticationVO.setAction_type(Constants.ACTION_AUTH_EMAIL_RESET_PWD);
				actionAuthenticationService.updateActionAuthentication(actionAuthenticationVO);
			}
			
			String path = request.getContextPath();
			String url_prefix = request.getScheme() + "://"
			+ request.getServerName() + (request.getServerPort() == 80 ? "":":" + request.getServerPort())
					+ path + "/"+ "auth/resetPW";
			
	        String mailContent = "请在24小时内点击下面链接进行重置密码或者拷贝该链接粘贴到浏览器打开进行重置密码";
	        mailContent+=url_prefix+"?username="+userVO.getUsername()+"&key="+actionAuthenticationVO.getRandom_md5();
	        emailSendSuccess = EmailUtils.sendmail(userVO.getEmail(),"专业足球管理系统密码重置",mailContent);
			
	        if (emailSendSuccess == true) {
				return response_send;
			} else {
				response_send = Response.toFailure(10000, "send mail fail");
				return response;
			}
		}
        catch (Exception e) {
            // TODO: handle exception 
            e.printStackTrace();
            response_send = Response.toFailure(10000, "send mail fail");
        }
		return response_send;
	}
	
	@RequestMapping(value="/resetPW",method = RequestMethod.GET)
	/**
	 * go to resetPW page
	 * @return ModelAndView resetPW url
	 */
    public Object resetPW(HttpServletRequest request, HttpServletResponse response) {
		try{
			String username = request.getParameter("username");
			String key = request.getParameter("key");
			// 验证是否超时
			AccountVO userVO = accountService.getAccountByUserName(username);
			ActionAuthenticationVO actionAuthenticationVO = actionAuthenticationService.getActionAuthenticationByUserId(userVO.getId(), Constants.ACTION_AUTH_EMAIL_RESET_PWD);
			if (actionAuthenticationVO==null) {
	    		ModelAndView mv = new ModelAndView("/error/reset_pwd_timeout");
	    		return mv;
			}
			if(key.equals(actionAuthenticationVO.getRandom_md5())) {
				Date currentDate = new Date(System.currentTimeMillis());
				if (currentDate.before(actionAuthenticationVO.getExpiration_time())) {
					ModelAndView mv = new ModelAndView("/auth/resetPW").addObject("username",username).addObject("key",key);
					return mv;
				} else {
		    		ModelAndView mv = new ModelAndView("/error/reset_pwd_timeout");
		    		return mv;
				}
			}	
		}
        catch (Exception e) {
            // TODO: handle exception 
            e.printStackTrace();
        }
		ModelAndView mv = new ModelAndView("/error/reset_pwd_contenterror");
		return mv;
    }

	@ResponseBody
	@RequestMapping(value="/resetPW",method=RequestMethod.POST)
	/**
	 * verify forgetPW request
	 * @param user UserVO
	 * @param model Model
	 * @return ModelAndView login url
	 */
	public Object resetPW(HttpServletRequest request, HttpServletResponse response, AccountVO user, Model model) {	
		Response response_reset = Response.toSussess("reset password success");
		try{
			String newPassword = request.getParameter("password");
			String confirmedPassword = request.getParameter("repassword");
			if (confirmedPassword.equals(newPassword) == false) {
				ModelAndView mv = new ModelAndView("/error/reset_pwd_contenterror");
				return mv;
			}
			String username = request.getParameter("username1");
			user.setUsername(username);
			String key = request.getParameter("key1");
			// 验证是否超时
			AccountVO userVO = accountService.getAccountByUserName(username);
			ActionAuthenticationVO actionAuthenticationVO = actionAuthenticationService.getActionAuthenticationByUserId(userVO.getId(), Constants.ACTION_AUTH_EMAIL_RESET_PWD);
			if (actionAuthenticationVO==null) {
				response_reset = Response.toFailure(10001, "reset pwd content error");
				return response_reset;
			}
			if(key.equals(actionAuthenticationVO.getRandom_md5())) {
				Date currentDate = new Date(System.currentTimeMillis());
				if (currentDate.before(actionAuthenticationVO.getExpiration_time())) {
					String newHashPassword = SecurityUtils.generateHashPassword(newPassword);
					userVO.setPassword(newHashPassword);
					int ret = accountService.updatePwd(userVO);
					if (ret > 0 ) {
						actionAuthenticationVO.setExpiration_time(currentDate);
						actionAuthenticationService.updateActionAuthentication(actionAuthenticationVO);
						return response_reset;
					} else {
						response_reset = Response.toFailure(10001, "reset pwd content error");
						//ModelAndView mv = new ModelAndView("/error/reset_pwd_content_error");
						return response_reset;
					}
				} else {
					response_reset = Response.toFailure(10002, "reset pwd timeout");
					//ModelAndView mv = new ModelAndView("/error/reset_pwd_timeout");
		    		return response_reset;
				}
			}
		}
        catch (Exception e) {
            // TODO: handle exception 
            e.printStackTrace();
        }
		
		response_reset = Response.toFailure(10001, "reset pwd content error");
		//ModelAndView mv = new ModelAndView("/error/reset_pwd_contenterror");
		return response_reset;
	}
}
