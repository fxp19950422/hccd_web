/**
 * 
 */
package cn.sportsdata.webapp.youth.service.share.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.sportsdata.webapp.youth.common.auth.share.ShareLinkBO;
import cn.sportsdata.webapp.youth.common.exceptions.SoccerProException;
import cn.sportsdata.webapp.youth.common.vo.share.ShareLinkVO;
import cn.sportsdata.webapp.youth.dao.share.ShareLinkDAO;

/**
 * @author king
 *
 */
@Service("parameterValidator")
public class ParameterValidator implements IShareLinkValidator {
	@Autowired
	private ShareLinkDAO shareLinkDAO;

	@Override
	public boolean validate(String shareLinkGUID, ShareLinkBO shareLinkBO) throws SoccerProException {
		ShareLinkVO shareLinkVO = shareLinkDAO.getShareLinkByGUID(shareLinkGUID);
		if (shareLinkVO != null) {
			ShareLinkBO dbShareLinkBO = ShareLinkBO.getInstanceByJSON(shareLinkVO.getRequestJSON());
			if (shareLinkBO.getParamesters().size() == dbShareLinkBO.getParamesters().size()) {
				for (int i = 0; i < shareLinkBO.getParamesters().size(); i++) {
					if (!dbShareLinkBO.getParamesters().contains(shareLinkBO.getParamesters().get(i))) {
						return false;
					}
				}
				return true;
			}
			
		}
		return false;
	}

}
