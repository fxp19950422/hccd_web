package cn.sportsdata.webapp.youth.service.share;

import cn.sportsdata.webapp.youth.common.auth.share.ShareLink;
import cn.sportsdata.webapp.youth.common.auth.share.ShareLinkBO;
import cn.sportsdata.webapp.youth.common.exceptions.SoccerProException;

public interface ShareLinkService {
	String getShareLink(ShareLink shareLink, String basePath) throws SoccerProException;
	
	String getShareLinkByGUID(String guid) throws SoccerProException;
	
	ShareLinkBO getShareLinkBOByGUID(String guid) throws SoccerProException;
}
