package cn.sportsdata.webapp.youth.service.account;

import cn.sportsdata.webapp.youth.common.vo.account.ActionAuthenticationVO;


public interface ActionAuthenticationService {
	ActionAuthenticationVO getActionAuthenticationByID(long action_id, String action_type);

	ActionAuthenticationVO getActionAuthenticationByUserId(String userId, String action_type);
    
	ActionAuthenticationVO getActionAuthenticationByRanMd5(String random_md5, String action_type);

    long updateActionAuthentication(ActionAuthenticationVO actionAuthentication);
    
    int insertActionAuthentication(ActionAuthenticationVO actionAuthentication);
}
