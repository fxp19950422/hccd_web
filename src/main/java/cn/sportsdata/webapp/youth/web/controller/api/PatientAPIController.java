package cn.sportsdata.webapp.youth.web.controller.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.sportsdata.webapp.youth.common.bo.hospital.PatientRecordBO;
import cn.sportsdata.webapp.youth.common.utils.SecurityUtils;
import cn.sportsdata.webapp.youth.common.vo.Response;
import cn.sportsdata.webapp.youth.common.vo.account.AccountVO;
import cn.sportsdata.webapp.youth.common.vo.login.LoginVO;
import cn.sportsdata.webapp.youth.service.account.AccountService;
import cn.sportsdata.webapp.youth.service.patient.PatientService;

@RestController
@RequestMapping("/api/v1/")
public class PatientAPIController {

	@Autowired
	private AccountService loginService;
	
	@Autowired
	PatientService patientService;

	@RequestMapping(value = "/patientRecords", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
	public ResponseEntity<Response> patientRecord(String hospitalId, String doctorId, String recordType) {
		
		List<PatientRecordBO> list = patientService.getPatientRecordByDoctor(hospitalId, doctorId, recordType);
		
		return new ResponseEntity<Response>(Response.toSussess(list), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/doctorLogin",  method = RequestMethod.POST)
	public ResponseEntity<Response> token(HttpServletRequest request, HttpServletResponse resp, String username, String password) {
		
		AccountVO account = loginService.getAccountByUserName(username);
		if (account == null){
			return new ResponseEntity<Response>(Response.toFailure(-1, "invalid username or password"), HttpStatus.OK);
		}
		LoginVO loginVO = new LoginVO(account);
		if (loginVO != null && !StringUtils.isEmpty(password)){
			
			if (SecurityUtils.verifyPassword(password, loginVO.getPassword())){				
				return new ResponseEntity<Response>(Response.toSussess(loginVO), HttpStatus.OK);
			} else {
				return new ResponseEntity<Response>(Response.toFailure(-1, "invalid username or password"), HttpStatus.OK);
			}
		} else {
			return new ResponseEntity<Response>(Response.toFailure(-1, "invalid username or password"), HttpStatus.OK);
		}
	}
}