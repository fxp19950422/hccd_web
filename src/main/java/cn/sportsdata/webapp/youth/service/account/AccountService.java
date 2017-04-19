package cn.sportsdata.webapp.youth.service.account;

import java.util.List;

import cn.sportsdata.webapp.youth.common.vo.AssetVO;
import cn.sportsdata.webapp.youth.common.vo.OrgVO;
import cn.sportsdata.webapp.youth.common.vo.account.AccountVO;
import cn.sportsdata.webapp.youth.common.vo.login.HospitalUserInfo;

public interface AccountService {
	public List<AccountVO> getAccountsByOrg(String orgID);
	
	public int insertAccount(AccountVO account, String orgId, long accountRoleId, AssetVO assetVO);
	
	public AccountVO getAccountByID(String id);
	
	public int updateAccount(AccountVO account, String orgId, long accountRoleId, AssetVO assetVO);
	
	public AccountVO getAccountByUserName(String username);
	
	public int updatePwd(AccountVO account);
	
	List<OrgVO> getOrgsByAccount(String loginID);
	
	public OrgVO getOrgsByAccountOrgID(String loginID, String orgID) ;
	
	public AccountVO getAccountByAccountIDOrgId(String loginID, String orgID);
	
	public int deleteAccount(AccountVO account, OrgVO orgVo);
	
	public HospitalUserInfo getHospitalUserInfoById(String id);
}
