package cn.sportsdata.webapp.youth.pcli.service;

import cn.sportsdata.webapp.youth.common.vo.account.AccountVO;
import cn.sportsdata.webapp.youth.common.vo.regist.RegistVO;

public interface PatientAccountService {

	AccountVO getPatientAccountByMobile(String phoneNum);
	
	AccountVO getPatientAccountByUserName(String username);

	String createPatientAccount(String phoneNum, String password);

	AccountVO getPatientAccountByUserId(String id);

	boolean updatePatientAccount(RegistVO registVO);
	
}
