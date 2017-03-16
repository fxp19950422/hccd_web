package cn.sportsdata.webapp.youth.web.controller.healthdata;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.sportsdata.webapp.youth.common.constants.Constants;
import cn.sportsdata.webapp.youth.common.utils.ExportExcel;
import cn.sportsdata.webapp.youth.common.vo.OrgVO;
import cn.sportsdata.webapp.youth.common.vo.UserVO;
import cn.sportsdata.webapp.youth.common.vo.basic.Token;
import cn.sportsdata.webapp.youth.common.vo.healthdata.HealthDataVO;
import cn.sportsdata.webapp.youth.service.healthdata.HealthDataService;
import cn.sportsdata.webapp.youth.service.user.UserService;
import cn.sportsdata.webapp.youth.web.controller.healthdata.reponse.HealthChartRepVO;
import cn.sportsdata.webapp.youth.web.controller.healthdata.reponse.HealthdataRepVO;
import cn.sportsdata.webapp.youth.web.controller.healthdata.request.HealthDataReqVO;

@Controller
@RequestMapping("/healthdata")
public class HealthDataController {
	@Autowired
	private HealthDataService healthDataService;
	
	@Autowired
	private UserService userService;
	
	protected OrgVO getCurrentOrg(HttpServletRequest request) {
		Token obj = (Token)request.getSession().getAttribute(Constants.USER_SESSION_KEY);
		if(obj != null){
			return obj.getOrgVO();
		}
		return null;
	}
	
	@RequestMapping("/gohealthdata")
    public String gohealthdatapeople(HttpServletRequest request, Model model) {
		
		OrgVO orgVO = getCurrentOrg(request);
		List<UserVO> playerList = userService.getPlayersByOrgId(orgVO.getId());
		
		List<UserVO> forwardList = new ArrayList<UserVO>();
		List<UserVO> centerList = new ArrayList<UserVO>();
		List<UserVO> defenderList = new ArrayList<UserVO>();
		List<UserVO> goalKeeperList = new ArrayList<UserVO>();
		
		for(UserVO player : playerList) {
			String position = player.getUserExtInfoMap().get("professional_primary_position");
			
			switch(position) {
				case Constants.FORWARD_POSITION:
					forwardList.add(player);
					break;
				case Constants.CENTER_POSITION:
					centerList.add(player);
					break;
				case Constants.DEFENDER_POSITION:
					defenderList.add(player);
					break;
				case Constants.GOALKEEPER_POSITION:
					goalKeeperList.add(player);
					break;
				default:
					break;
			}
		}
		
		model.addAttribute("forwardList", forwardList);
		model.addAttribute("centerList", centerList);
		model.addAttribute("defenderList", defenderList);
		model.addAttribute("goalKeeperList", goalKeeperList);
		
		return "healthdata/player_list";
	}
	
	@RequestMapping("/gethealthdatalist/{user_id}")
	@ResponseBody
    public List<HealthdataRepVO> showhealthdataList(@PathVariable String user_id, Model model) {
		 List<HealthDataVO> ls=healthDataService.getHealthDataListByUserId(user_id);
		 List<HealthdataRepVO> res = new ArrayList<>();
		 for(HealthDataVO vo:ls){
			 res.add(new HealthdataRepVO(vo));
		 }
		return res;
	}
	
	@RequestMapping("/goonepeoplehealthdata")
    public String showhealthdataList(HttpServletRequest request, Model model) {
		model.addAttribute("id", request.getParameter("userID"));
		String userId = request.getParameter("userID");
		String backurl=request.getParameter("backurl");
		model.addAttribute("backurl", backurl);
		UserVO player = userService.getUserByID(userId);
		model.addAttribute("player", player);
		return "healthdata/healthdatalist";
	}
	
	
	@RequestMapping("/gohandlehealthdata/{healthdata_id}/{userID}")
    public String gohandlehealthdata(HttpServletRequest request,@PathVariable String healthdata_id,@PathVariable String userID ,Model model) {
		model.addAttribute("id", userID);
		String backurl=request.getParameter("backurl");
		model.addAttribute("backurl", backurl);
		HealthDataVO vo = healthDataService.getOneHealthDataByID(healthdata_id);
		if(vo!=null){
			model.addAttribute("update", "0");
		}else{
			model.addAttribute("update", "1");
		}
		model.addAttribute("data", vo);
		OrgVO orgVO = getCurrentOrg(request);
		UserVO player = userService.getUserByID(userID);
		model.addAttribute("player", player);
		List<UserVO> ls =userService.getCoachsByOrgId(orgVO.getId());
		model.addAttribute("coachs", ls);
		return "healthdata/handlehealthdata";
	}
	
	@RequestMapping("/handlehealthdata/{flag}")
	@ResponseBody
    public Object handlehealthdata(HttpServletRequest request,@PathVariable int flag,@RequestBody HealthDataReqVO vo) {
		if(1==(flag)){
			if(vo.getCreate_time()==null){
				vo.setCreate_time(new java.sql.Date(new java.util.Date().getTime()));
			}
			if(vo.getHeight()==0){
				vo.setBmi(0);
			}else{
				vo.setBmi(vo.getWeight()*10000/(vo.getHeight()*vo.getHeight()));
			}
			healthDataService.createOneHealthData(vo);
		}else{
			if(vo.getCreate_time()==null){
				vo.setCreate_time(new java.sql.Date(new java.util.Date().getTime()));
			}
			if(vo.getHeight()==0){
				vo.setBmi(0);
			}else{
				vo.setBmi(vo.getWeight()*10000/(vo.getHeight()*vo.getHeight()));
			}
			healthDataService.updateOneHealthData(vo);
		}
		return true;
	}
	
	@RequestMapping("/deletehealthdata/{id}")
	@ResponseBody
    public Object handlehealthdata(@PathVariable String id) {
		healthDataService.deleteOneHealthdataById(id);
		return true;
	}
	
	
	@RequestMapping(value = "/healthchart", method = RequestMethod.GET)
	@ResponseBody
    public List<HealthChartRepVO> healthchart(HttpServletRequest request) {
		String userid=request.getParameter("userid");
		String start_date=request.getParameter("startdate");
		String end_date=request.getParameter("enddate");
		String colum = request.getParameter("col");
		String col[]= colum.split(",");
		List<HealthChartRepVO> res = new ArrayList<>();
		for(String c:col){
			Map<String, Object> args = new HashMap<>();
			args.put("userid", userid);
			args.put("start_date", getTime(start_date));
			args.put("end_date", getTime(end_date));
			args.put("colum", c);
			res.add(healthDataService.getHealthChartlist(args).valueOf());
		}
		return res;
	}
	
	@RequestMapping(value = "/exportexecl/{user_id}", method = RequestMethod.POST)
    public void exportexecl(@PathVariable String user_id,HttpServletRequest request,HttpServletResponse response) throws IOException {
	    List<HealthDataVO> ls=healthDataService.getHealthDataListByUserId(user_id);
        List<HealthdataRepVO> res = new ArrayList<>();
        for(HealthDataVO vo:ls){
            res.add(new HealthdataRepVO(vo));
        }
        ExportExcel<HealthdataRepVO> ex = new ExportExcel<>(); 
        String[] headers =   { "采集时间", "身高cm", "体重kg", "bmi", "含氧量","肩胛脂","大臂脂","腰围脂","胸围cm" ,"腰围cm","晨脉","乳酸"}; 
        response.setHeader("Content-Disposition", "attachment; filename="+parseGBK("身体指标.xls"));
        ex.exportExcel("身体指标", headers, res, response.getOutputStream(), "yyyy-MM-dd", headers.length);
        response.getOutputStream().close();
	}
	
	private Date getTime(String test_time_str){
		SimpleDateFormat sdmf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return sdmf.parse(test_time_str);
		} catch (ParseException e) {
			return new Date();
		}
	}
	
    public  String parseGBK(String sIn) {
	        if (sIn == null || sIn.equals(""))
	            return sIn;
	        try {
	            return new String(sIn.getBytes("UTF-8"), "ISO-8859-1");
	        } catch (UnsupportedEncodingException usex) {
	            return sIn;
	        }
	    }
	
}
