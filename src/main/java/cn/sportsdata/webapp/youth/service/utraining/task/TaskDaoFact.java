/**
 * 
 */
package cn.sportsdata.webapp.youth.service.utraining.task;

import cn.sportsdata.webapp.youth.common.vo.utraining.UtrainingTaskVO;

/**
 * @author king
 *
 */
public class TaskDaoFact {
	
	public static ITaskDao getInstance(UtrainingTaskVO task) {
		if (task != null) {
			if (ITaskDao.MATCH_TASK_TYPE.equals(task.getType())) {
				return new MatchTaskDao();
			}
		}
		return new TrainingTaskDao();
	}

}
