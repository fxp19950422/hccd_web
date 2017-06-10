package cn.sportsdata.webapp.youth.patient.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.sportsdata.webapp.youth.common.vo.account.AccountVO;
import cn.sportsdata.webapp.youth.dao.account.AccountDao;
import cn.sportsdata.webapp.youth.patient.service.PatientAccountService;

@Service
public class PatientAccountServiceImpl implements PatientAccountService {
	
	@Autowired
	AccountDao accountDao;

	@Override
	public AccountVO getPatientAccountByMobilePhone(String phoneNum) {
		return accountDao.getPatientAccountByMobilePhone(phoneNum);
	}

	@Override
	public AccountVO getPatientAccountByUserName(String username) {
		return accountDao.getPatientAccountByUserName(username);
	}

}
