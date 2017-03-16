package cn.sportsdata.webapp.youth.dao.singletraining;

import java.util.List;

import cn.sportsdata.webapp.youth.common.vo.SingleTrainingEquipmentVO;
import cn.sportsdata.webapp.youth.common.vo.SingleTrainingExtVO;
import cn.sportsdata.webapp.youth.common.vo.SingleTrainingVO;

public interface SingleTrainingDAO {

	SingleTrainingVO getSingleTrainingByID(String id);

	boolean handleSingleTraining(SingleTrainingVO basicData, List<SingleTrainingExtVO> singletrainingExtList,List<SingleTrainingEquipmentVO> singletrainingEquipmentList,boolean isCreate);

	boolean deleteSingleTraining(String id);

	List<SingleTrainingVO> getSingleTrainingList(String orgID);

}
