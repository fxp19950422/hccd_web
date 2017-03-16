package cn.sportsdata.webapp.youth.service.singletraining.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.sportsdata.webapp.youth.common.vo.SingleTrainingEquipmentVO;
import cn.sportsdata.webapp.youth.common.vo.SingleTrainingExtVO;
import cn.sportsdata.webapp.youth.common.vo.SingleTrainingVO;
import cn.sportsdata.webapp.youth.dao.singletraining.SingleTrainingDAO;
import cn.sportsdata.webapp.youth.service.singletraining.SingleTrainingService;

@Service
public class SingleTrainingServiceImpl  implements SingleTrainingService {
	@Autowired
	private SingleTrainingDAO singletrainingDAO;
	
	@Override
	public SingleTrainingVO getSingleTrainingByID(String id) {
		return singletrainingDAO.getSingleTrainingByID(id);
	}

	@Override
	public boolean createSingleTraining(SingleTrainingVO basicData, List<SingleTrainingExtVO> singletrainingExtList,List<SingleTrainingEquipmentVO> singletrainingEquipmentList) {
		return  singletrainingDAO.handleSingleTraining(basicData, singletrainingExtList, singletrainingEquipmentList,true);
	}

	@Override
	public boolean updateSingleTraining(SingleTrainingVO basicData, List<SingleTrainingExtVO> singletrainingExtList,List<SingleTrainingEquipmentVO> singletrainingEquipmentList) {
		return singletrainingDAO.handleSingleTraining(basicData, singletrainingExtList,singletrainingEquipmentList, false);
	}

	@Override
	@Transactional
	public boolean deleteSingleTraining(String id) {
		
		return singletrainingDAO.deleteSingleTraining(id);
	}

	@Override
	public List<SingleTrainingVO> getSingleTrainingList(String orgID) {
		return singletrainingDAO.getSingleTrainingList(orgID);
	}

}
