/**
 * 
 */
package cn.sportsdata.webapp.youth.service.share;

import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.sportsdata.webapp.youth.common.auth.share.ShareLink;
import cn.sportsdata.webapp.youth.common.auth.share.ShareLinkBO;
import cn.sportsdata.webapp.youth.common.exceptions.SoccerProException;
import cn.sportsdata.webapp.youth.common.vo.share.ShareLinkVO;
import cn.sportsdata.webapp.youth.dao.share.ShareLinkDAO;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * @author king
 *
 */
@Service
public class ShareLinkServiceImpl implements ShareLinkService {
	@Autowired
	private ShareLinkDAO shareLinkDAO;

	@Override
	public String getShareLink(ShareLink shareLink, String basePath) throws SoccerProException {
		if (shareLink != null) {
			String digest = shareLink.sha256();
			ShareLinkVO shareLinkVO = shareLinkDAO.selectShareLinkBysha256(digest);
			if (shareLinkVO == null) {
				shareLinkVO = new ShareLinkVO();
				shareLinkVO.setSha256(digest);
				shareLinkVO.setGuid(shareLink.getUUID());
				shareLinkVO.setRequestJSON(shareLink.toJson());
				shareLinkDAO.insertShareLink(shareLinkVO);
			}
			StringBuffer sharelinkBuffer = new StringBuffer(basePath);
			sharelinkBuffer.append("share/share?id=").append(shareLinkVO.getGuid());
			return sharelinkBuffer.toString();
			
		}
		return null;
	}
	
	@Override
	public String getShareLinkByGUID(String guid) throws SoccerProException {
		ShareLinkVO shareLinkVO = shareLinkDAO.getShareLinkByGUID(guid);
		if (shareLinkVO != null) {
			ShareLinkBO shareLinkBO = ShareLinkBO.getInstanceByJSON(shareLinkVO.getRequestJSON());
			return shareLinkBO.completedURLWithToken(guid);
		}
		return null;
		
	}
	
	@Override
	public ShareLinkBO getShareLinkBOByGUID(String guid) throws SoccerProException {
		ShareLinkVO shareLinkVO = shareLinkDAO.getShareLinkByGUID(guid);
		if (shareLinkVO != null) {
			ShareLinkBO shareLinkBO = ShareLinkBO.getInstanceByJSON(shareLinkVO.getRequestJSON());
			return shareLinkBO;
		}
		return null;
		
	}
}
