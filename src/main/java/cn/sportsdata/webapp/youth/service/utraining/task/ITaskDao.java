package cn.sportsdata.webapp.youth.service.utraining.task;

import cn.sportsdata.webapp.youth.common.vo.utraining.UtrainingTaskVO;
import cn.sportsdata.webapp.youth.dao.utraining.UtrainingDAO;

public interface ITaskDao {
	
	public final static String TRAINING_TASK_TYPE = "training";
	public final static String MATCH_TASK_TYPE = "match";
	public final static String LIFE_TASK_TYPE = "life";
	public final static String OTHERS_TASK_TYPE = "others";
	
	/**
	 * insert or update task to DB
	 * @param utrainingDao UtrainingDAO
	 * @param task UtrainingTaskVO
	 * @return task ID in db
	 */
	String addEdit2DB(UtrainingDAO utrainingDao, UtrainingTaskVO task, String orgID);

}
