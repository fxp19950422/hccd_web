package cn.sportsdata.webapp.youth.common.bo;
import java.io.Serializable;
import java.util.List;

import cn.sportsdata.webapp.youth.common.vo.SingleTrainingEquipmentVO;
import cn.sportsdata.webapp.youth.common.vo.SingleTrainingExtVO;


public class SingleTrainingBO implements Serializable {

	private static final long serialVersionUID = -4956409529287739061L;
	
	private SingleTrainingBasicBO singleTrainingBO;
	private List<SingleTrainingExtVO> singleTrainingExtInfoList;
	private List<SingleTrainingEquipmentVO> singleTrainingEquipmentInfoList;
	
	
	public SingleTrainingBasicBO getSingleTrainingBO() {
		return singleTrainingBO;
	}
	public void setSingleTrainingBO(SingleTrainingBasicBO singleTrainingBO) {
		this.singleTrainingBO = singleTrainingBO;
	}
	public List<SingleTrainingExtVO> getSingleTrainingExtInfoList() {
		return singleTrainingExtInfoList;
	}
	public void setSingleTrainingExtInfoList(List<SingleTrainingExtVO> singleTrainingExtInfoList) {
		this.singleTrainingExtInfoList = singleTrainingExtInfoList;
	}
	
	public List<SingleTrainingEquipmentVO> getSingleTrainingEquipmentInfoList() {
		return singleTrainingEquipmentInfoList;
	}
	public void setSingleTrainingEquipmentInfoList(List<SingleTrainingEquipmentVO> singleTrainingEquipmentInfoList) {
		this.singleTrainingEquipmentInfoList = singleTrainingEquipmentInfoList;
	}

}
