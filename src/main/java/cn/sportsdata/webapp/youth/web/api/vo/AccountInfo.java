package cn.sportsdata.webapp.youth.web.api.vo;

import java.util.List;

import cn.sportsdata.webapp.youth.common.vo.OrgVO;
import cn.sportsdata.webapp.youth.common.vo.login.LoginVO;

public class AccountInfo {
	private LoginVO account;
	
	private List<OrgVO> orgs;

	public LoginVO getAccount() {
		return account;
	}

	public void setAccount(LoginVO account) {
		this.account = account;
	}

	public List<OrgVO> getOrgs() {
		return orgs;
	}

	public void setOrgs(List<OrgVO> orgs) {
		this.orgs = orgs;
	}
	
	
}
