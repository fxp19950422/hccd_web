package cn.sportsdata.webapp.youth.service.account;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.sportsdata.webapp.youth.common.utils.StringUtil;
import cn.sportsdata.webapp.youth.common.vo.account.ActionAuthenticationVO;
import cn.sportsdata.webapp.youth.dao.account.ActionAuthenticationDAO;

@Service
public class ActionAuthenticationServiceImpl implements ActionAuthenticationService {
    @Autowired
    private ActionAuthenticationDAO actionAuthenticationDAO;

	@Override
	public ActionAuthenticationVO getActionAuthenticationByID(long action_id, String action_type) {
		if (action_id < 1) return null;

		ActionAuthenticationVO actionAuthenticationVO = actionAuthenticationDAO.getActionAuthenticationByID(action_id, action_type);
        return actionAuthenticationVO;
	}

	@Override
	public ActionAuthenticationVO getActionAuthenticationByUserId(String userId, String action_type) {
		if (StringUtil.isBlank(userId)) return null;

		ActionAuthenticationVO actionAuthenticationVO = actionAuthenticationDAO.getActionAuthenticationByUserId(userId, action_type);
        return actionAuthenticationVO;
	}

	@Override
	public ActionAuthenticationVO getActionAuthenticationByRanMd5(String random_md5, String action_type) {
		if (StringUtils.isBlank(random_md5)) return null;

		ActionAuthenticationVO actionAuthenticationVO = actionAuthenticationDAO.getActionAuthenticationByRanMd5(random_md5, action_type);
        return actionAuthenticationVO;
	}

	@Override
	public long updateActionAuthentication(ActionAuthenticationVO actionAuthentication) {
        return actionAuthenticationDAO.updateActionAuthentication(actionAuthentication);
	}

	@Override
	public int insertActionAuthentication(ActionAuthenticationVO actionAuthentication) {
		return actionAuthenticationDAO.insertActionAuthentication(actionAuthentication);
	}
}
