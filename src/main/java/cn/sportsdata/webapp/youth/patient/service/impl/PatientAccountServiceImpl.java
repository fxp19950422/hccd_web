package cn.sportsdata.webapp.youth.patient.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.sportsdata.webapp.youth.common.utils.SecurityUtils;
import cn.sportsdata.webapp.youth.common.vo.UserVO;
import cn.sportsdata.webapp.youth.common.vo.account.AccountVO;
import cn.sportsdata.webapp.youth.dao.account.AccountDao;
import cn.sportsdata.webapp.youth.dao.user.UserDAO;
import cn.sportsdata.webapp.youth.patient.service.PatientAccountService;

@Service
public class PatientAccountServiceImpl implements PatientAccountService {
	
	@Autowired
	AccountDao accountDao;
	
	@Autowired
	UserDAO userDao;

	@Override
	public AccountVO getPatientAccountByMobile(String phoneNum) {
		return accountDao.getPatientAccountByMobilePhone(phoneNum);
	}

	@Override
	public AccountVO getPatientAccountByUserName(String username) {
		return accountDao.getPatientAccountByUserName(username);
	}

	@Override
	public String createPatientAccount(String phoneNum, String password) {
		UserVO userVO = new UserVO();
		userVO.setMobile(phoneNum);
		userVO.setPassword(SecurityUtils.generateHashPassword(password));
		boolean isSuccess = userDao.handleUser(userVO, null, true);
		String userId = userVO.getId();
		if (isSuccess) {
			accountDao.createPatientAccountRoleMapping(userId, 6L);
		}
		return userId;
	}

}
