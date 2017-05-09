/**
 * 
 */
package cn.sportsdata.webapp.youth.dao.account;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.sportsdata.webapp.youth.dao.BaseDAO;
import org.springframework.stereotype.Repository;

import cn.sportsdata.webapp.youth.common.vo.OrgVO;
import cn.sportsdata.webapp.youth.common.vo.account.AccountVO;
import cn.sportsdata.webapp.youth.common.vo.login.HospitalUserInfo;

/**
 * @author king
 *
 */
@Repository
public class AccountDaoImpl extends BaseDAO implements AccountDao {
	
	private static final String SELECT_ACCOUNTS_BY_ORG = "getAccountsByOrg";
	
	private static final String INSERT_ACCOUNT = "insertAccount";
	private static final String INSERT_ACCOUNT_ORG_ROLE_MAPPING = "insertAccountOrgRoleMapping";
	
	private static final String SELECT_ACCOUNT_BY_ID= "getAccountById";
	
	private static final String SELECT_ACCOUNT_BY_USERNAME= "getAccountByUsername";
	
	private static final String UPDATE_ACCOUNT = "updateAccount";
	
	private static final String UPDATE_PASSWORD = "updatePwd";
	
	private static final String DELETE_ACCOUNT = "deleteAccount";
	
	private static final String DELETE_ACCOUNT_IN_ORG = "deleteRelationShipInOrg";
	
	private static final String SELECT_ORG_BY_ACCOUNT = "getOrgsByAccountID";
	
	private static final String SELECT_ORG_BY_ACCOUNT_ORG = "getOrgsByAccountIDOrgID";
	private static final String GET_ACCOUNT_BY_ACCOUNTID_ORGID = "getAccountByAccountIDOrgId";

	private static final String SELECT_NEW_ACCOUNTS = "getNewAccounts";

	private static final String UPDATE_ACCOUNTS = "updateAccounts";

	private static final String INSERT_ACCOUNTS = "insertAccounts";

	private static final String DELETE_ACCOUNTS = "deleteAccounts";

	private static final String SELECT_ACCOUNTS_TO_UPDATE = "getRecordsToUpdate";
	
	private static final String SELECT_MAPPED_PATIENT_COUNT = "getMappedPatientCount";
	
	private static final String getHospitalUserInfoByUserId = "getHospitalUserInfoByUserId";

	@Override
	public List<AccountVO> getAccounts(String orgID) {
	
		return sqlSessionTemplate.selectList(getSqlNameSpace(SELECT_ACCOUNTS_BY_ORG), orgID);
    	
	}

	@Override
	public int insertAccount(AccountVO account) {
		return sqlSessionTemplate.insert(getSqlNameSpace(INSERT_ACCOUNT), account);
	}

	@Override
	public int insertAccountOrgRoleMapping(String loginId, String orgId, long accountRoleId) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("loginId", loginId);
		map.put("orgId", orgId);
		map.put("accountRoleId", accountRoleId);
		return sqlSessionTemplate.insert(getSqlNameSpace(INSERT_ACCOUNT_ORG_ROLE_MAPPING), map);
	}

	@Override
	public AccountVO getAccountByID(String loginID) {
		return sqlSessionTemplate.selectOne(getSqlNameSpace(SELECT_ACCOUNT_BY_ID), loginID);
	}
	
	@Override
	public AccountVO getAccountByUserName(String username) {
		return sqlSessionTemplate.selectOne(getSqlNameSpace(SELECT_ACCOUNT_BY_USERNAME), username);
	}

	@Override
	public int updateAccount(AccountVO account) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.update(getSqlNameSpace(UPDATE_ACCOUNT), account);
	}

	@Override
	public int updatePwd(AccountVO account) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.update(getSqlNameSpace(UPDATE_PASSWORD), account);
	}

	@Override
	public List<OrgVO> getOrgsByAccount(String loginID) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(getSqlNameSpace(SELECT_ORG_BY_ACCOUNT), loginID);
	}
	
	@Override
	public OrgVO getOrgByAccountOrgId(String loginID, String orgID) {
		Map map = new HashMap();
		map.put("loginID", loginID);
		map.put("orgID", orgID);
		return sqlSessionTemplate.selectOne(getSqlNameSpace(SELECT_ORG_BY_ACCOUNT_ORG), map);
	}
	
	@Override
	public AccountVO getAccountByAccountIDOrgId(String loginID, String orgID) {
		Map map = new HashMap();
		map.put("loginID", loginID);
		map.put("orgID", orgID);
		return sqlSessionTemplate.selectOne(getSqlNameSpace(GET_ACCOUNT_BY_ACCOUNTID_ORGID), map);
	}

	@Override
	public int deleteAccount(AccountVO account) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.delete(getSqlNameSpace(DELETE_ACCOUNT), account);
	}

	@Override
	public int deleteAccountRelationInOrg(AccountVO account, OrgVO orgVo) {
		Map map = new HashMap();
		map.put("orgId", orgVo.getId());
		map.put("id", account.getId());
		return sqlSessionTemplate.delete(getSqlNameSpace(DELETE_ACCOUNT_IN_ORG), map);
	}

	@Override
	public HospitalUserInfo getHospitalUserInfoByUserId(String id) {
		List<HospitalUserInfo> infos = sqlSessionTemplate.selectList(getSqlNameSpace(getHospitalUserInfoByUserId), id);
		return infos.get(0);
	}

	@Override
	public int getMappedPaitentCount(String userId) {
		return sqlSessionTemplate.selectOne(getSqlNameSpace(SELECT_MAPPED_PATIENT_COUNT), userId);
	}

}
