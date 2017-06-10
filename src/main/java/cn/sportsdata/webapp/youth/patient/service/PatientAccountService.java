package cn.sportsdata.webapp.youth.patient.service;

import cn.sportsdata.webapp.youth.common.vo.account.AccountVO;

public interface PatientAccountService {

	AccountVO getPatientAccountByMobilePhone(String phoneNum);
	
	AccountVO getPatientAccountByUserName(String username);
	
}
