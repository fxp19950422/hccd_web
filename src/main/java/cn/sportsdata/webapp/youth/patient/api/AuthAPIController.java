package cn.sportsdata.webapp.youth.patient.api;

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

import com.fasterxml.jackson.databind.JsonNode;

import cn.sportsdata.webapp.youth.common.utils.CommonUtils;
import cn.sportsdata.webapp.youth.common.utils.SecurityUtils;
import cn.sportsdata.webapp.youth.common.vo.Response;
import cn.sportsdata.webapp.youth.common.vo.account.AccountVO;
import cn.sportsdata.webapp.youth.common.vo.login.LoginVO;
import cn.sportsdata.webapp.youth.patient.service.PatientAccountService;
import cn.sportsdata.webapp.youth.service.patient.PatientService;

@RestController
@RequestMapping("/api/v1/patient")
public class AuthAPIController {

	@Autowired
	private PatientAccountService accountService;

	@Autowired
	PatientService patientService;

	private final String HEADER_TOKEN = "X-Auth-Token";
	
	@RequestMapping(value = "/regist", method = RequestMethod.POST)
	public ResponseEntity<Response> regist(HttpServletRequest request, HttpServletResponse resp, String phoneNum,
			String password, String veriCode) {

		//TODO verify vericode first
		boolean codeValid = true;
		if (!codeValid) {
			return new ResponseEntity<Response>(Response.toFailure(1001, "verify code error"), HttpStatus.OK);
		}
		
		AccountVO account = accountService.getPatientAccountByMobilePhone(phoneNum);
		if (account != null) {
			return new ResponseEntity<Response>(Response.toFailure(1002, "phone number already exists"), HttpStatus.OK);
		}
		
		return null;
		
		
//		AccountVO account = accountService.getPatientAccount(username);
//
//		if (account == null) {
//			return new ResponseEntity<Response>(Response.toFailure(-1, "invalid username or password"), HttpStatus.OK);
//		}
//		LoginVO loginVO = new LoginVO(account);
//		if (loginVO != null && !StringUtils.isEmpty(password)) {
//
//			if (SecurityUtils.verifyPassword(password, loginVO.getPassword())) {
//				JSONObject json = new JSONObject();
//				json.put("userid", loginVO.getId());
//				json.put("username", loginVO.getUserName());
//				json.put("email", loginVO.getEmail());
//				json.put("name", loginVO.getName());
//				json.put("avatar", loginVO.getAvatar());
//				json.put("avatar_id", loginVO.getAvatar_id());
//				json.put("birthday", loginVO.getBirthday());
//
//				String tokeninfo = SecurityUtils.generateAuthToken_ts(json.toString());
//				Map<String, Object> loginInfo = new HashMap<String, Object>();
//				loginInfo.put("token", tokeninfo);
//				loginInfo.put("user", loginVO);
//				return new ResponseEntity<Response>(Response.toSussess(loginInfo), HttpStatus.OK);
//			} else {
//				return new ResponseEntity<Response>(Response.toFailure(-1, "invalid username or password"),
//						HttpStatus.OK);
//			}
//		} else {
//			return new ResponseEntity<Response>(Response.toFailure(-1, "invalid username or password"), HttpStatus.OK);
//		}
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<Response> patientToken(HttpServletRequest request, HttpServletResponse resp, String username,
			String password) {

		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
			return new ResponseEntity<Response>(Response.toFailure(-1, "invalid username or password"),
					HttpStatus.OK);
		}
		
		AccountVO account = null;
		if (CommonUtils.isMobileFormat(username)) {
			account = accountService.getPatientAccountByMobilePhone(username);
		} else {
			account = accountService.getPatientAccountByUserName(username);
		}
	
		if (account == null) {
			return new ResponseEntity<Response>(Response.toFailure(-1, "invalid username or password"), HttpStatus.OK);
		}
		LoginVO loginVO = new LoginVO(account);
		if (loginVO != null && !StringUtils.isEmpty(password)) {

			if (SecurityUtils.verifyPassword(password, loginVO.getPassword())) {
				JSONObject json = new JSONObject();
				json.put("userid", loginVO.getId());
				json.put("username", loginVO.getUserName());
				json.put("email", loginVO.getEmail());
				json.put("name", loginVO.getName());
				json.put("avatar", loginVO.getAvatar());
				json.put("avatar_id", loginVO.getAvatar_id());
				json.put("birthday", loginVO.getBirthday());

				String tokeninfo = SecurityUtils.generateAuthToken_ts(json.toString());
				Map<String, Object> loginInfo = new HashMap<String, Object>();
				loginInfo.put("token", tokeninfo);
				loginInfo.put("user", loginVO);
				return new ResponseEntity<Response>(Response.toSussess(loginInfo), HttpStatus.OK);
			} else {
				return new ResponseEntity<Response>(Response.toFailure(-1, "invalid username or password"),
						HttpStatus.OK);
			}
		} else {
			return new ResponseEntity<Response>(Response.toFailure(-1, "invalid username or password"), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/verifyToken", method = RequestMethod.GET)
	public ResponseEntity<Response> patientValidateToken(HttpServletRequest request, HttpServletResponse resp) {
		String token = request.getHeader(HEADER_TOKEN);
		JsonNode node = SecurityUtils.vefifyAuthToken_ts(token);
		LoginVO login = new LoginVO();
		login.setAvatar(node.get("avatar") == null ? "" : node.get("avatar").asText());
		login.setAvatar_id(node.get("avatar_id") == null ? "" : node.get("avatar_id").asText());
		login.setEmail(node.get("email") == null ? "" : node.get("email").asText());
		login.setId(node.get("userid") == null ? "" : node.get("userid").asText());
		login.setName(node.get("name") == null ? "" : node.get("name").asText());
		login.setUserName(node.get("username") == null ? "" : node.get("username").asText());
		login.setBirthday(node.get("birthday") == null ? "" : node.get("birthday").asText());

		Map<String, Object> loginInfo = new HashMap<String, Object>();
		loginInfo.put("token", token);
		loginInfo.put("user", login);
		return new ResponseEntity<Response>(Response.toSussess(loginInfo), HttpStatus.OK);
	}
}
