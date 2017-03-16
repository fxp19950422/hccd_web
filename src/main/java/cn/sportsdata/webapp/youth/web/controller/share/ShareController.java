package cn.sportsdata.webapp.youth.web.controller.share;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.sportsdata.webapp.youth.common.exceptions.SoccerProException;
import cn.sportsdata.webapp.youth.service.share.ShareLinkService;
import cn.sportsdata.webapp.youth.web.controller.BaseController;

@Controller
@RequestMapping("/share")
public class ShareController extends BaseController {
	private static Logger logger = Logger.getLogger(ShareController.class);
	@Autowired
	private ShareLinkService shareLinkService;
	
	@RequestMapping(value = "/share", method = RequestMethod.GET, produces="text/html;charset=UTF-8")
	public String uploadFormAttachment(HttpServletRequest request, @RequestParam(required=true) String id) throws SoccerProException {
		
		String link  = shareLinkService.getShareLinkByGUID(id);
		if (link != null) {
			return "forward:"+link;
		}
		logger.error("can not found share link by ID" + id);
		throw new SoccerProException("can not found share link by ID" + id);
	}
}
