package cn.sportsdata.webapp.youth.web.controller.api;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.sportsdata.webapp.youth.common.utils.SecurityUtils;
import cn.sportsdata.webapp.youth.common.vo.Response;
import cn.sportsdata.webapp.youth.common.vo.account.AccountVO;
import cn.sportsdata.webapp.youth.common.vo.login.LoginVO;
import cn.sportsdata.webapp.youth.service.account.AccountService;
import cn.sportsdata.webapp.youth.service.patient.PatientService;
import cn.sportsdata.webapp.youth.web.api.vo.ApiToken;

@RestController
@RequestMapping("/api/v1/")
public class TokenAPIController {
	
	@Autowired
	private AccountService loginService;
	
	@Autowired
	PatientService patientService;
	
	@RequestMapping(value = "/token",  method = RequestMethod.POST)
	public ResponseEntity<Response> token(HttpServletRequest request, HttpServletResponse resp, String username, String password) {
		
		AccountVO account = loginService.getAccountByUserName(username);
		if (account == null){
			return new ResponseEntity<Response>(Response.toFailure(-1, "invalid username or password"), HttpStatus.OK);
		}
		LoginVO loginVO = new LoginVO(account);
		if (loginVO != null && !StringUtils.isEmpty(password)){
			
			if (SecurityUtils.verifyPassword(password, loginVO.getPassword())){
				JSONObject json = new JSONObject();
				json.put("userid", loginVO.getId());
				json.put("username", loginVO.getUserName());
				json.put("email", loginVO.getEmail());
				json.put("name", loginVO.getName());
				json.put("avatar", loginVO.getAvatar());
				json.put("avatar_id", loginVO.getAvatar_id());
				json.put("birthday", loginVO.getBirthday());
				
				String tokeninfo = SecurityUtils.generateAuthToken_ts(json.toString());
				ApiToken token = new ApiToken();
				token.setToken(tokeninfo);
				
				Map<String, Object> result = new HashMap<String, Object>();
				result.put("token", token);
				result.put("doctorInfo", loginVO.getHospitalUserInfo());
				result.put("version", patientService.getDoctorAppVersion());
				return new ResponseEntity<Response>(Response.toSussess(result), HttpStatus.OK);
			} else {
				return new ResponseEntity<Response>(Response.toFailure(-1, "invalid username or password"), HttpStatus.OK);
			}
		} else {
			return new ResponseEntity<Response>(Response.toFailure(-1, "invalid username or password"), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/patient/token",  method = RequestMethod.POST)
	public ResponseEntity<Response> patientToken(HttpServletRequest request, HttpServletResponse resp, String username, String password) {
		
		AccountVO account = loginService.getPatientAccount(username);

		if (account == null){
			return new ResponseEntity<Response>(Response.toFailure(-1, "invalid username or password"), HttpStatus.OK);
		}
		LoginVO loginVO = new LoginVO(account);
		if (loginVO != null && !StringUtils.isEmpty(password)){
			
			if (SecurityUtils.verifyPassword(password, loginVO.getPassword())){
				JSONObject json = new JSONObject();
				json.put("userid", loginVO.getId());
				json.put("username", loginVO.getUserName());
				json.put("email", loginVO.getEmail());
				json.put("name", loginVO.getName());
				json.put("avatar", loginVO.getAvatar());
				json.put("avatar_id", loginVO.getAvatar_id());
				json.put("birthday", loginVO.getBirthday());
				
				String tokeninfo = SecurityUtils.generateAuthToken_ts(json.toString());
//				ApiToken token = new ApiToken();
//				token.setToken(tokeninfo);
				Map<String, Object> loginInfo = new HashMap<String, Object>();
				loginInfo.put("token", tokeninfo);
				loginInfo.put("user", loginVO);
				return new ResponseEntity<Response>(Response.toSussess(loginInfo), HttpStatus.OK);
			} else {
				return new ResponseEntity<Response>(Response.toFailure(-1, "invalid username or password"), HttpStatus.OK);
			}
		} else {
			return new ResponseEntity<Response>(Response.toFailure(-1, "invalid username or password"), HttpStatus.OK);
		}
	}
}
