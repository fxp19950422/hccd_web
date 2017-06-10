package cn.sportsdata.webapp.youth.patient.service;

import cn.sportsdata.webapp.youth.common.vo.account.AccountVO;

public interface PatientAccountService {

	AccountVO getPatientAccountByMobile(String phoneNum);
	
	AccountVO getPatientAccountByUserName(String username);

	String createPatientAccount(String phoneNum, String password);
	
}
