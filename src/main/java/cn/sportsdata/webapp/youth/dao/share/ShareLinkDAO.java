package cn.sportsdata.webapp.youth.dao.share;

import cn.sportsdata.webapp.youth.common.vo.share.ShareLinkVO;

public interface ShareLinkDAO {
	boolean insertShareLink(ShareLinkVO shareLinkVO);
	ShareLinkVO selectShareLinkBysha256(String sha256);
	
	ShareLinkVO getShareLinkByGUID(String guid);
	boolean deleteShareLinkBysha256(String sha256);
}
