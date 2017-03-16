package cn.sportsdata.webapp.youth.service.utraining;

import java.util.Date;
import java.util.List;

import cn.sportsdata.webapp.youth.common.auth.share.ShareLink;
import cn.sportsdata.webapp.youth.common.bo.UtrainingBO;
import cn.sportsdata.webapp.youth.common.bo.UtrainingTaskEvaBO;
import cn.sportsdata.webapp.youth.common.vo.utraining.UtrainingTaskVO;
import cn.sportsdata.webapp.youth.common.vo.utraining.TrainingMatchVO;
import cn.sportsdata.webapp.youth.common.vo.utraining.UtrainingVO;

public interface UtrainingService {
	List<String> getUtrainingYears(String orgId);
	
	String addNewUtraining(UtrainingBO bo);
	
	List<UtrainingBO> getUtrainingInYear(String year,String orgId);
	
	List<UtrainingVO> getUtrainingsByUserId(String userId);
	
	UtrainingBO getUtrainingById(String utrainingId);
	
	boolean deleteUtrainingById(String utrainingId, String orgId);
	
	boolean updateUtrainingById(UtrainingBO bo);
	
	boolean checkUtrainingDate(String utrainingId,Date startDate,Date endDate,String orgId);
	ShareLink getShareLink(String orgId, String utrainingId);
	

	UtrainingTaskVO getLatestTrainingTask(String orgId);
	List<UtrainingTaskEvaBO> getTrainingTaskEvaResults(String taskId, String orgId);

	List<UtrainingTaskVO> getComingTrainingTask(String orgId);
	List<TrainingMatchVO> getUtrainingMatchInOneYear(String year, String orgID);
}
