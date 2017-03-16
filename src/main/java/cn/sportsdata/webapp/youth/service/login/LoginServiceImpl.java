package cn.sportsdata.webapp.youth.service.login;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.sportsdata.webapp.youth.common.constants.Constants;
import cn.sportsdata.webapp.youth.common.utils.SecurityUtils;
import cn.sportsdata.webapp.youth.common.vo.login.LoginVO;
import cn.sportsdata.webapp.youth.common.vo.privilege.UserPrivilegeTypeAssociationVO;
import cn.sportsdata.webapp.youth.dao.login.LoginDao;
import cn.sportsdata.webapp.youth.dao.privilege.PrivilegeDAO;


@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private LoginDao loginDao;
    @Autowired
    private PrivilegeDAO privilegeDAO;

	@Override
	public LoginVO getUserByUserName(String userName) {
		if (StringUtils.isBlank(userName)) return null;

        LoginVO LoginVO = loginDao.getUserByUserName(userName);
        return LoginVO;
	}

	@Override
	public LoginVO getUserByEmail(String userMail) {
		if (StringUtils.isBlank(userMail)) return null;

        LoginVO LoginVO = loginDao.getUserByEmail(userMail);
        return LoginVO;
	}
	
	@Override
	public LoginVO getUserByID(long userID) {
		if (userID < 1) return null;

        LoginVO LoginVO = loginDao.getUserByID(userID);
        return LoginVO;
	}

	@Override
	public int updateUser(LoginVO user) {
        return loginDao.updateUser(user);
	}
	
	@Override
	public boolean checkUserNameExist(String userName) {
		return loginDao.checkUserNameExist(userName);
	}
	
	@Override
    public int setUserPrivilege(LoginVO user, long privilegeId) {
		UserPrivilegeTypeAssociationVO userPrivilegeVO = new UserPrivilegeTypeAssociationVO();
		userPrivilegeVO.setUserId(user.getId());
		userPrivilegeVO.setPrivilegeTypeId(privilegeId);
		return privilegeDAO.setUsePrivilege(userPrivilegeVO);
	}


	@Override
	public int addLoginCount(long userID) {
        return loginDao.addLoginCount(userID);
	}
}
