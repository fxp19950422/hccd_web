package cn.sportsdata.webapp.youth.dao.account;

import java.util.List;

import cn.sportsdata.webapp.youth.common.vo.OrgVO;
import cn.sportsdata.webapp.youth.common.vo.account.AccountVO;
import cn.sportsdata.webapp.youth.common.vo.login.HospitalUserInfo;

public interface AccountDao{
	
	
	/**
     * get LoginVO by user ID
     * @param userName String
     * @return LoginVO
     */
	List<AccountVO> getAccounts(String orgID);
	
	int insertAccount(AccountVO account);
	
	int insertAccountOrgRoleMapping(String loginId, String orgId, long accountRoleId);
    
	AccountVO getAccountByID(String loginID);
	
	public AccountVO getAccountByUserName(String username);
	
	int updateAccount(AccountVO account);
	
	int updatePwd(AccountVO account);
	
	List<OrgVO> getOrgsByAccount(String loginID);
	
	public OrgVO getOrgByAccountOrgId(String loginID, String orgID);
	public AccountVO getAccountByAccountIDOrgId(String loginID, String orgID);
	
	public int deleteAccount(AccountVO account);
	
	public int deleteAccountRelationInOrg(AccountVO account, OrgVO orgVo);
	
	HospitalUserInfo getHospitalUserInfoByUserId(String id);

	int getMappedPaitentCount(String userId);
}
