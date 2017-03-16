/**
 * 
 */
package cn.sportsdata.webapp.youth.service.utraining.task;

import cn.sportsdata.webapp.youth.common.vo.utraining.UtrainingTaskVO;
import cn.sportsdata.webapp.youth.dao.utraining.UtrainingDAO;

/**
 * @author king
 *
 */
public class TrainingTaskDao implements ITaskDao {

	@Override
	public String addEdit2DB(UtrainingDAO utrainingDao, UtrainingTaskVO task, String orgID) {
		return utrainingDao.handleTask(task);
	}

}
