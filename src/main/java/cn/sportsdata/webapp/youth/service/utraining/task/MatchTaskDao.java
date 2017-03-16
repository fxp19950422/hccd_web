/**
 * 
 */
package cn.sportsdata.webapp.youth.service.utraining.task;

import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import cn.sportsdata.webapp.youth.common.utils.SpringUtils;
import cn.sportsdata.webapp.youth.common.vo.match.MatchVO;
import cn.sportsdata.webapp.youth.common.vo.utraining.TrainingExtVO;
import cn.sportsdata.webapp.youth.common.vo.utraining.UtrainingTaskVO;
import cn.sportsdata.webapp.youth.dao.match.MatchDAO;
import cn.sportsdata.webapp.youth.dao.utraining.UtrainingDAO;
import cn.sportsdata.webapp.youth.web.controller.utraining.UtrainingController;

/**
 * @author king
 *
 */
public class MatchTaskDao implements ITaskDao {

	@Override
	public String addEdit2DB(UtrainingDAO utrainingDao, UtrainingTaskVO task, String orgID) {
		MatchVO matchVO = new MatchVO();
		matchVO.setOrgId(orgID);
		matchVO.setDate(new Timestamp(task.getTaskDate().getTime()));
		matchVO.setLocation(task.getLocation());
		matchVO.setType("friendly");
		matchVO.setFieldType("home");
		matchVO.setIsEnd(0);
		matchVO.setTime(task.getTaskTime());
		String opponent = getMatchValue(task, UtrainingController.KEY_TASK_EXT_TASK_MATCH_OPPONENT);
		if (!StringUtils.isBlank(opponent)) {
			matchVO.setOpponent(opponent);
		}
		MatchDAO matchDao = SpringUtils.getBean(MatchDAO.class);
		
		String dress = getMatchValue(task, UtrainingController.KEY_TASK_EXT_TASK_MATCH_DRESS);
		if (!StringUtils.isBlank(dress)) {
			matchVO.setDress(dress);
		}
		boolean isCreate = true;
		String matchID = getMatchValue(task, UtrainingController.KEY_TASK_EXT_TASK_MATCH_ID);
		if (!StringUtils.isBlank(matchID)) {
			isCreate = false;
			matchVO.setId(matchID);
			MatchVO dbMatch = matchDao.getMatchById(matchID);
			if (dbMatch != null) {
				matchVO.setIsEnd(dbMatch.getIsEnd());
				matchVO.setType(dbMatch.getType());
				matchVO.setFieldType(dbMatch.getFieldType());
			}
		}
		
		matchDao.handleMatchBasic(matchVO, isCreate);
		utrainingDao.handleTask(task);
		if (isCreate) {
			utrainingDao.insertTrainingTaskMatchAssociation(task.getId(), matchVO.getId());
		}
		return task.getId();
	}
	
	private String getMatchValue(UtrainingTaskVO task, String itemName) {
		if (task != null) {
			List<TrainingExtVO> trainingExtVOs = task.getExtList();
			if (trainingExtVOs != null) {
				for (int i = 0; i < trainingExtVOs.size(); i++) {
					TrainingExtVO trainingExtVO = trainingExtVOs.get(i);
					if (itemName.equals(trainingExtVO.getItemName())) {
						return trainingExtVO.getItemValue();
					}
				}
			}
		}
		return null;
	}

}
