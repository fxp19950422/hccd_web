package cn.sportsdata.webapp.youth.web.controller.dashboard;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.sportsdata.webapp.youth.common.bo.UtrainingTaskEvaBO;
import cn.sportsdata.webapp.youth.common.constants.Constants;
import cn.sportsdata.webapp.youth.common.vo.OrgVO;
import cn.sportsdata.webapp.youth.common.vo.match.MatchDataItemVO;
import cn.sportsdata.webapp.youth.common.vo.match.MatchDataVO;
import cn.sportsdata.webapp.youth.common.vo.match.MatchVO;
import cn.sportsdata.webapp.youth.common.vo.test.PlayerWithTest;
import cn.sportsdata.webapp.youth.common.vo.utraining.UtrainingTaskVO;
import cn.sportsdata.webapp.youth.service.match.MatchService;
import cn.sportsdata.webapp.youth.service.test.TestService;
import cn.sportsdata.webapp.youth.service.utraining.UtrainingService;
import cn.sportsdata.webapp.youth.web.controller.BaseController;

@Controller
@RequestMapping("/dashboard")
public class DashboardController extends BaseController {
	//private static Logger logger = Logger.getLogger(DashboardController.class);
	
	@Autowired
	private UtrainingService utrainingService;
	
	@Autowired
	private MatchService matchService;
	
	@Autowired
	private TestService testService;
	
	@RequestMapping("")
    public String showDashboardPage(HttpServletRequest request, Model model) {
		OrgVO currentOrg = getCurrentOrg(request);
		
//		// training related
//		UtrainingTaskVO latestTask = utrainingService.getLatestTrainingTask(currentOrg.getId());
//		List<UtrainingTaskEvaBO> latestTaskEvaList = null;
//		if (latestTask != null) {
//			latestTaskEvaList = utrainingService.getTrainingTaskEvaResults(latestTask.getId(), currentOrg.getId());
//		}
//		List<UtrainingTaskVO> comingTrainingTaskList= utrainingService.getComingTrainingTask(currentOrg.getId());
//		
//		model.addAttribute("latestTask", latestTask);
//		model.addAttribute("latestTaskEvaList", latestTaskEvaList);
//		model.addAttribute("comingTrainingTaskList", comingTrainingTaskList);
//		
//		// match related
//		MatchVO match = matchService.getLatestEndedMatchByOrgId(currentOrg.getId());
//		model.addAttribute("match", match);
//		
//		if(match != null) {
//			List<MatchDataVO> datas = matchService.getMatchDataByMatchId(match.getId());
//			
//			if(datas.size() > 0) {
//				Map<String, Integer> statistics = new HashMap<String, Integer>();
//				for(MatchDataVO data : datas) {
//					for(MatchDataItemVO item : data.getItemList()) {
//						String key = item.getItemName();
//						if(Arrays.asList(Constants.MATCH_DATA_ITEM_KEY_OF_NON_NUMBER).contains(key)) {
//							continue;
//						}
//						
//						int value = 0;
//						try {
//							value = Integer.parseInt(item.getItemValue());
//						} catch(Exception e) {
//							// do nothing, use default value 0
//						}
//						
//						if(!statistics.containsKey(key)) {
//							statistics.put(key, value);
//						} else {
//							int newValue = statistics.get(key) + value;
//							statistics.put(key, newValue);
//						}
//					}
//				}
//				
//				for(int i = 0; i < Constants.MATCH_DATA_ITEM_KEYS.length; i++) {
//					if(!statistics.containsKey(Constants.MATCH_DATA_ITEM_KEYS[i])) {
//						statistics.put(Constants.MATCH_DATA_ITEM_KEYS[i], 0);
//					}
//				}
//				
//				int success_pass = statistics.get("short_pass") + statistics.get("long_pass") + statistics.get("forward_pass") + statistics.get("cross_pass");
//				statistics.put("all_shoots", statistics.get("target_shoot") + statistics.get("aside_shoot"));
//				statistics.put("all_passes", success_pass + statistics.get("fail_pass"));
//				statistics.put("all_steals", statistics.get("success_steal") + statistics.get("fail_steal"));
//				
//				if(statistics.get("all_shoots") == 0) {
//					statistics.put("shoot_percentage", 0);
//				} else {
//					statistics.put("shoot_percentage", (int) (((double) statistics.get("target_shoot")) / ((double) statistics.get("all_shoots")) * 100));
//				}
//				
//				if(statistics.get("all_passes") == 0) {
//					statistics.put("pass_percentage", 0);
//				} else {
//					statistics.put("pass_percentage", (int) (((double) success_pass) / ((double) statistics.get("all_passes")) * 100));
//				}
//				
//				if(statistics.get("all_steals") == 0) {
//					statistics.put("steal_percentage", 0);
//				} else {
//					statistics.put("steal_percentage", (int) (((double) statistics.get("success_steal")) / ((double) statistics.get("all_steals")) * 100));
//				}
//				
//				model.addAttribute("statistics", statistics);
//			}
//		}
//		List<PlayerWithTest> pwtlist=testService.getFirstListSingleTestDataByOrgID(currentOrg.getId());
//		if(pwtlist.size()>0){
//			model.addAttribute("test_item_title", pwtlist.get(0).getTest_item_title());
//			model.addAttribute("test_unit_name",pwtlist.get(0).getTest_unit_name());
//			StringBuffer user_name=new StringBuffer(pwtlist.get(0).getUser_name());
//			StringBuffer test_result=new StringBuffer(""+pwtlist.get(0).getTest_result());
//			for(int i=1;i<pwtlist.size();i++){
//				PlayerWithTest pwt=pwtlist.get(i);
//				user_name.append(","+pwt.getUser_name());
//				test_result.append(","+pwt.getTest_result());
//			}
//			model.addAttribute("user_name", user_name);
//			model.addAttribute("test_result", test_result);
//		}
//		
//		List<MatchVO> comingMatches = matchService.getComingMatchesByOrgId(currentOrg.getId());
//		model.addAttribute("comingMatches", comingMatches);
		
		return "dashboard/dashboard";
	}
}
