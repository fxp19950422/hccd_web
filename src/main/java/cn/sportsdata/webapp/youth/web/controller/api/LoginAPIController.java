package cn.sportsdata.webapp.youth.web.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.sportsdata.webapp.youth.common.vo.OrgVO;
import cn.sportsdata.webapp.youth.common.vo.Response;
import cn.sportsdata.webapp.youth.common.vo.login.LoginVO;
import cn.sportsdata.webapp.youth.service.account.AccountService;
import cn.sportsdata.webapp.youth.service.login.LoginService;
import cn.sportsdata.webapp.youth.web.api.vo.AccountInfo;

@RestController
@RequestMapping("/api/v1/")
public class LoginAPIController {

	@Autowired
	private LoginService loginService;
	
	@Autowired
	private AccountService accountService;

	@RequestMapping(value = "/user", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
	public ResponseEntity<Response> getLoginAccount(String username, String password) {
	
		LoginVO login = (LoginVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		List<OrgVO> orgs = accountService.getOrgsByAccount(login.getId());
		
		AccountInfo account = new AccountInfo();
		account.setAccount(login);
		account.setOrgs(orgs);
		return new ResponseEntity<Response>(Response.toSussess(account), HttpStatus.OK);
	}

	@RequestMapping(value = "/account/{id:\\d+}", method = RequestMethod.GET)
	public ResponseEntity getAccount(@PathVariable("id") int id) {
		Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return new ResponseEntity<LoginVO>(new LoginVO(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/account/{id:\\d+}", method = RequestMethod.DELETE)
	public Object deleteAccount(@PathVariable("id") int id) {
		Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return Response.toSussess("成功");
	}
	
	@RequestMapping(value = "/account/{id:\\d+}", method = RequestMethod.PUT)
	public Object updateAccount(@PathVariable("id") int id) {
		
		return Response.toSussess("成功");
	}
	
}