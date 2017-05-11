package cn.sportsdata.webapp.youth.service.account;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.sportsdata.webapp.youth.common.vo.AssetVO;
import cn.sportsdata.webapp.youth.common.vo.OrgVO;
import cn.sportsdata.webapp.youth.common.vo.account.AccountVO;
import cn.sportsdata.webapp.youth.common.vo.login.HospitalUserInfo;
import cn.sportsdata.webapp.youth.dao.account.AccountDao;
import cn.sportsdata.webapp.youth.dao.asset.AssetDAO;


@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountDao accountDao;
    
    @Autowired
    private AssetDAO assetDao;
    
	@Override
	public List<AccountVO> getAccountsByOrg(String orgID) {
		// TODO Auto-generated method stub
		return accountDao.getAccounts(orgID);
	}

	@Override
	@Transactional
	public int insertAccount(AccountVO account, String orgId, long accountRoleId, AssetVO assetVO) {
		AccountVO existAccount = accountDao.getAccountByUserName(account.getUsername());
		if (existAccount == null){
			if (assetVO != null){
				String assetID = assetDao.insertAsset(assetVO);
				account.setAvatarId(assetID);
			}
			accountDao.insertAccount(account);
			accountDao.insertAccountOrgRoleMapping(account.getId(), orgId, accountRoleId);
			return 1;
		} else {
			throw new RuntimeException("账号冲突");
		}
	}
	
	@Override
	@Transactional
	public int updateAccount(AccountVO account, String orgId, long accountRoleId, AssetVO assetVO) {
		 if (assetVO != null ){
			 String avatarId = null;
			 if (!StringUtils.isEmpty(assetVO.getId())){
				 assetDao.updateAsset(assetVO);
			 } else {
				 avatarId = assetDao.insertAsset(assetVO);
				 account.setAvatarId(avatarId);
			 }
			
			 
		 }
		
		 return accountDao.updateAccount(account);
	}

	@Override
	public AccountVO getAccountByID(String id) {
		return accountDao.getAccountByID(id);
	}

	@Override
	public int updatePwd(AccountVO account) {
		return accountDao.updatePwd(account);
	}
	
	@Override
	@Transactional
	public int deleteAccount(AccountVO account, OrgVO orgVo) {
		List<OrgVO> orgs = accountDao.getOrgsByAccount(account.getId());
		int retValue= 0;
		if (orgs.size() > 1){
			retValue = accountDao.deleteAccountRelationInOrg(account, orgVo);
		} else {
			retValue = accountDao.deleteAccount(account);
		} 
		
		return retValue;
	}

	@Override
	public AccountVO getAccountByUserName(String username) {
		// TODO Auto-generated method stub
		return accountDao.getAccountByUserName(username);
	}

	@Override
	public List<OrgVO> getOrgsByAccount(String loginID) {
		// TODO Auto-generated method stub
		return accountDao.getOrgsByAccount(loginID);
	}
	@Override
	public OrgVO getOrgsByAccountOrgID(String loginID, String orgID) {
		// TODO Auto-generated method stub
		return accountDao.getOrgByAccountOrgId(loginID, orgID);
	}
	
	@Override
	public AccountVO getAccountByAccountIDOrgId(String loginID, String orgID) {
		// TODO Auto-generated method stub
		return accountDao.getAccountByAccountIDOrgId(loginID, orgID);
	}

	@Override
	public HospitalUserInfo getHospitalUserInfoById(String id) {
		// TODO Auto-generated method stub
		return accountDao.getHospitalUserInfoByUserId(id);
	}

	@Override
	public AccountVO getPatientAccount(String username) {
		AccountVO account = accountDao.getAccountByUserName(username);
		if (account != null) {
			int patientCount = accountDao.getMappedPaitentCount(account.getId());
			if (patientCount > 0) {
				return account;
			}
 		}
		return null;
	}

}
