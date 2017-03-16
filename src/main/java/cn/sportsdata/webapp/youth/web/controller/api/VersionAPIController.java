package cn.sportsdata.webapp.youth.web.controller.api;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.impl.StdScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.sportsdata.webapp.youth.common.vo.Response;
import cn.sportsdata.webapp.youth.common.vo.VersionVO;
import cn.sportsdata.webapp.youth.service.version.VersionService;

@RestController
@RequestMapping("/api/v1/")
public class VersionAPIController {

//	@Resource
//	private StdScheduler scheduler;
	
	@Autowired
	VersionService versionService;

	@RequestMapping(value = "/version", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
	public ResponseEntity<Response> getLoginAccount(String username, String password) {
		VersionVO version = versionService.getVersionInfo();
		
//		try {
//			JobDataMap map = new JobDataMap();
//			Set<String> set = new HashSet<String>();
//			set.add("e5984a0f-2090-11e6-90fb-000c29adfeca");
//			
//			map.put("matchIdList",set.toArray());
////			scheduler.triggerJob(new JobKey("jobDetail"), map);
//		} catch (SchedulerException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return new ResponseEntity<Response>(Response.toSussess(version), HttpStatus.OK);
	}
}