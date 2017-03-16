/**
 * 
 */
package cn.sportsdata.webapp.youth.service.share.validator;

import cn.sportsdata.webapp.youth.common.auth.share.ShareLinkBO;
import cn.sportsdata.webapp.youth.common.exceptions.SoccerProException;

/**
 * @author king
 *
 */
public interface IShareLinkValidator {
	
	boolean validate(String shareLinkGUID, ShareLinkBO shareLinkBO) throws SoccerProException;

}
