package cn.healthdata.webapp.hospital.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.healthdata.webapp.hospital.dao.account.AccountDao;
import cn.healthdata.webapp.hospital.vo.LoginVO;


@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountDao accountDao;

	@Override
	@Transactional
	public LoginVO getLoginInfo(LoginVO loginVo) {
		LoginVO vo = accountDao.getLoginInfo(loginVo.getPhone_number());
		if (vo == null){
			loginVo.setLogin_count(1);
			accountDao.insertLoginInfo(loginVo);
		} else {
			vo.setLogin_count(vo.getLogin_count() + 1);
			vo.setAge(loginVo.getAge());
			vo.setGender(loginVo.getGender());
			accountDao.updateLoginInfo(vo);
		}
		return accountDao.getLoginInfo(loginVo.getPhone_number());
	}

	@Override
	public void upsertLoginInfo(LoginVO loginVO) {
		
	}

	@Override
	public LoginVO getLoginInfo(String phone) {
		LoginVO vo = accountDao.getLoginInfo(phone);
		return vo;
	}

	@Override
	public List<String> getInviteCode(String inviteCode) {
		
		return accountDao.getInviteCode(inviteCode);
	}
	

}
