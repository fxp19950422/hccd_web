package cn.sportsdata.webapp.youth.patient.dao;

import cn.sportsdata.webapp.youth.common.vo.account.AccountVO;

public interface PatientAccountDao {

	AccountVO getAccountByUserName(String username);
	
	Integer getMappedPaitentCount(String userId);
	
}
