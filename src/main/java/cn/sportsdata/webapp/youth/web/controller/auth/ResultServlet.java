package cn.sportsdata.webapp.youth.web.controller.auth;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.sportsdata.webapp.youth.common.constants.Constants;
import cn.sportsdata.webapp.youth.common.utils.ConfigProps;
@Controller
@RequestMapping("/resultServlet")
public class ResultServlet {
     @RequestMapping(value="/validateCode",method=RequestMethod.POST)
     public static boolean doPost(HttpServletRequest request, HttpServletResponse response)        
             throws ServletException, IOException {   
     	 String verifyCodeEnable = ConfigProps.getInstance().getConfigValue("soccerda.enable.verifycode");
 		 if(Constants.STRING_FALSE.equalsIgnoreCase(verifyCodeEnable)) {
 			 return true;
 		 }
 		
    	 boolean bVeryCodeCorrect = false; 
         response.setContentType("text/html;charset=utf-8");        
         String validateC = (String) request.getSession().getAttribute("validateCode");        
         String veryCode = request.getParameter("veryCode");        
         PrintWriter out = response.getWriter();        
         if(veryCode==null||"".equals(veryCode)){  
        	 out.write("<script type='text/javascript'>alert('验证码为空！');</script>");      
         }else{        
        	 if(StringUtils.equalsIgnoreCase(veryCode, validateC)){
                 bVeryCodeCorrect = true;
             }else{         
                 out.write("<script type='text/javascript'>alert('验证码错误！');</script>");   
             }        
         }        
         out.flush();        
         out.close();
		return bVeryCodeCorrect;        
     }
     
     public static boolean doVeryfy(HttpServletRequest request, String veryCode)        
             throws ServletException, IOException {      
    	String verifyCodeEnable = ConfigProps.getInstance().getConfigValue("soccerda.enable.verifycode");
		if(Constants.STRING_FALSE.equalsIgnoreCase(verifyCodeEnable)) {
			return true;
		}

 		String validateC = (String) request.getSession().getAttribute("validateCode");        
        boolean bVeryCodeCorrect = false;   
        if(veryCode==null||"".equals(veryCode)){        
        	bVeryCodeCorrect = false;       
        }else{        
        	if(StringUtils.equalsIgnoreCase(veryCode, validateC)){
        		bVeryCodeCorrect = true;       
            }else{        
            	bVeryCodeCorrect = false;        
            }      
        } 
        return bVeryCodeCorrect;
     }
}