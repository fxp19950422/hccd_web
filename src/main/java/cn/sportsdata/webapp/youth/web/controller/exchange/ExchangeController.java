package cn.sportsdata.webapp.youth.web.controller.exchange;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.sportsdata.webapp.youth.common.utils.StringUtil;
import cn.sportsdata.webapp.youth.common.vo.OrgVO;
import cn.sportsdata.webapp.youth.common.vo.UserVO;
import cn.sportsdata.webapp.youth.common.vo.test.PlayerChartData;
import cn.sportsdata.webapp.youth.common.vo.test.PlayerChartRenderData;
import cn.sportsdata.webapp.youth.common.vo.test.PlayerOptResp;
import cn.sportsdata.webapp.youth.common.vo.test.PlayerWithTest;
import cn.sportsdata.webapp.youth.common.vo.test.SingleTestPO;
import cn.sportsdata.webapp.youth.common.vo.test.TestBatchRenderVO;
import cn.sportsdata.webapp.youth.common.vo.test.TestItemPO;
import cn.sportsdata.webapp.youth.common.vo.test.TestItemSelectorRenderVO;
import cn.sportsdata.webapp.youth.common.vo.test.TestManagePageRenderVO;
import cn.sportsdata.webapp.youth.service.test.TestService;
import cn.sportsdata.webapp.youth.service.user.UserService;
import cn.sportsdata.webapp.youth.web.controller.BaseController;

@Controller
@RequestMapping("/exchange")
public class ExchangeController extends BaseController{
	private static Logger logger = Logger.getLogger(ExchangeController.class);
	
	
	@RequestMapping(value = "/exchange_list",method = RequestMethod.GET)
    public String toTestManagePage(HttpServletRequest request, Model model, @RequestParam(required=false,defaultValue = "0") int radio) {
		
		return "exchange/exchange_list";
	}

}
