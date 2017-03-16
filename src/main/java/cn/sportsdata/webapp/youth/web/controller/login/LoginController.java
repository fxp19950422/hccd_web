package cn.sportsdata.webapp.youth.web.controller.login;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.sportsdata.webapp.youth.common.vo.Response;
import cn.sportsdata.webapp.youth.common.vo.UserVO;



@Controller
@RequestMapping("/auth1")
public class LoginController {

	@ResponseBody
	@RequestMapping(value="/login",method=RequestMethod.POST)
	
	public Object verifyLogin(HttpServletRequest request, HttpServletResponse response, UserVO user, Model model) {
		response.setContentType("text/html;charset=utf-8");        
		String veryCode = request.getParameter("veryCode");  
		
        boolean bVeryCodeCorrect = false;
        
		return Response.toSussess("system/system/index");
	}
	
}
