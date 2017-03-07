package cn.healthdata.webapp.hospital.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.healthdata.webapp.hospital.service.AccountService;
import cn.healthdata.webapp.hospital.utils.CookieUtils;
import cn.healthdata.webapp.hospital.vo.LoginVO;

@Controller
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private AccountService accountService;

	@RequestMapping(value="/login",method=RequestMethod.GET)
	/**
	 * go to login page
	 * @return ModelAndView login url
	 */
    public Object login() {
		
		return "/auth/login";
		
    }
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	/**
	 * verify login request
	 * @param user UserVO
	 * @param model Model
	 * @return ModelAndView login url
	 */
	public Object verifyLogin(HttpServletRequest request, HttpServletResponse response,LoginVO vo, String inviteCode, Model model) {
		response.setContentType("text/html;charset=utf-8");        
		String veryCode = request.getParameter("veryCode");  
		
        boolean bVeryCodeCorrect = false;
//		try {
////			bVeryCodeCorrect = ResultServlet.doVeryfy(request, veryCode);
////			VerifyCodeServlet.reset_code(request);
//		} catch (ServletException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

        if (bVeryCodeCorrect == false) {
        	model.addAttribute("validationFailed", true);
        	model.addAttribute("validationFailedCode", "0001");
        	model.addAttribute("validationFailedMsg", "验证码有误");
        	model.addAttribute("loginVO", vo);
        	model.addAttribute("inviteCode", inviteCode);
        	model.addAttribute("veryCode", veryCode);
        	return "/auth/login";
        }
        
//        InviteCodeChecker checker = new InviteCodeChecker(new HardCodeCheckPolicy());
//        boolean checkResult = checker.validateInviteCode(inviteCode);
//        if (!checkResult){
//        	model.addAttribute("validationFailed", true);
//        	model.addAttribute("validationFailedCode", "0002");
//        	model.addAttribute("validationFailedMsg", "邀请码有误");
//        	model.addAttribute("loginVO", vo);
//        	model.addAttribute("inviteCode", inviteCode);
//        	model.addAttribute("veryCode", veryCode);
//        	return "/auth/login";
//        }
        
        LoginVO loginVO = accountService.getLoginInfo(vo);
        request.getSession().setAttribute("current_user_info", loginVO);
        request.getSession().setAttribute("invite_code", inviteCode);
        CookieUtils.setHagkCookie(response);
        
        return "/psytest/psytest_index";
	}
}
