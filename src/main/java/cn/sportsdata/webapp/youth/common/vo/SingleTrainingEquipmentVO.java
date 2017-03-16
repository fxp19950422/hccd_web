package cn.sportsdata.webapp.youth.common.vo;

import java.io.Serializable;

import cn.sportsdata.webapp.youth.common.constants.Constants;

public class SingleTrainingEquipmentVO implements Serializable {
	
	private static final long serialVersionUID = 2675161611391950521L;
	
	private String equipmentId;
	private String equipmentName;
	private long count;
	
	public String getEquipmentId() {
		return equipmentId;
	}
	public void setEquipmentId(String equipmentId) {
		this.equipmentId = equipmentId;
	}

	public String getEquipmentName() {
		return equipmentName;
	}
	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	
}
