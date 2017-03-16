package cn.sportsdata.webapp.youth.job;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Service;


import cn.sportsdata.webapp.youth.common.utils.SpringUtils;
import cn.sportsdata.webapp.youth.common.vo.match.MatchDataVO;
import cn.sportsdata.webapp.youth.service.match.MatchDataService;

@Service
public class MatchDataAnalysisJob implements Job{
	private static Logger log = Logger.getLogger(MatchDataAnalysisJob.class);
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try{
			log.info("=========================== Match Data Handle Job Start ===========================");
			Object paramArray = context.getMergedJobDataMap().get("matchIdList");
			List<String> matchIdList = new ArrayList<String>();
			
			if (paramArray == null){
				log.info("=========================== Job data is null so cancel this job ===========================");
				return;
			}
			
			if (paramArray != null){
				Object[] array = (Object[])paramArray;
				for (int i = 0; i < array.length; i++){
					matchIdList.add(String.valueOf(array[i]));
				}
			}

			MatchDataService service = SpringUtils.getBean(MatchDataService.class);

			List<MatchDataVO> listVo = service.getMatchDataInfoByMatchList(matchIdList);
			log.info("=========================== Match Data Handle Job End ===========================");
		} catch(Exception ex){
			log.error("=========================== Match Data Handle Job Exception occurred ===========================");
			log.error(ex.getStackTrace());
		}
	}

}
