package cn.sportsdata.webapp.youth.service.singletraining;

import java.util.List;

import cn.sportsdata.webapp.youth.common.vo.SingleTrainingEquipmentVO;
import cn.sportsdata.webapp.youth.common.vo.SingleTrainingExtVO;
import cn.sportsdata.webapp.youth.common.vo.SingleTrainingVO;

public interface SingleTrainingService {
	SingleTrainingVO getSingleTrainingByID(String id);
	boolean createSingleTraining(SingleTrainingVO basicData, List<SingleTrainingExtVO> singletrainingExtList,List<SingleTrainingEquipmentVO> singletrainingEquipmentList);
	boolean updateSingleTraining(SingleTrainingVO basicData, List<SingleTrainingExtVO> singletrainingExtList,List<SingleTrainingEquipmentVO> singletrainingEquipmentList);
	boolean deleteSingleTraining(String id);
	List<SingleTrainingVO> getSingleTrainingList(String orgID);
}
