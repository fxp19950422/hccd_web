package cn.sportsdata.webapp.youth.common.vo;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.sportsdata.webapp.youth.common.constants.Constants;


public class SingleTrainingVO implements Serializable {

	private static final long serialVersionUID = 719475452932662818L;

	private String id;
	private String name;
	private String description;
	private long player_count;
	private long goalkeeper_count;
	private String yard_width;
	private String yard_long;
	private String orgID;
	private String tacticsId;
	private List<SingleTrainingExtVO> singleTrainingExtInfoList;
	private List<SingleTrainingEquipmentVO> singleTrainingEquipmentInfoList;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public long getPlayer_count() {
		return player_count;
	}
	public void setPlayer_count(long player_count) {
		this.player_count = player_count;
	}
	public long getGoalkeeper_count() {
		return goalkeeper_count;
	}
	public void setGoalkeeper_count(long goalkeeper_count) {
		this.goalkeeper_count = goalkeeper_count;
	}
	public String getYard_width() {
		return yard_width;
	}
	public void setYard_width(String yard_width) {
		this.yard_width = yard_width;
	}
	public String getYard_long() {
		return yard_long;
	}
	public void setYard_long(String yard_long) {
		this.yard_long = yard_long;
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

	private Map<String, String> singleTrainingExtInfoMap;
	public Map<String, String> getSingleTrainingExtInfoMap() {
		if(singleTrainingExtInfoMap == null) {
			singleTrainingExtInfoMap = new HashMap<String, String>();
			
			if(singleTrainingExtInfoList != null) {
				for(SingleTrainingExtVO singleTrainingExt : singleTrainingExtInfoList) {
					singleTrainingExtInfoMap.put(singleTrainingExt.getItemName(), singleTrainingExt.getItemValue());
				}
			}
		}
		
		return singleTrainingExtInfoMap;
	}
	
	private Map<String, String> singleTrainingEquipmentInfoMap;
	public Map<String, String> getSingleTrainingEquipmentInfoMap() {
		if(singleTrainingEquipmentInfoMap == null) {
			singleTrainingEquipmentInfoMap = new HashMap<String, String>();
			if(singleTrainingEquipmentInfoList != null) {
				for(SingleTrainingEquipmentVO singleTrainingEquipment : singleTrainingEquipmentInfoList) {
					singleTrainingEquipmentInfoMap.put(singleTrainingEquipment.getEquipmentId()+"", singleTrainingEquipment.getCount()+"");
				}
			}
		}
		return singleTrainingEquipmentInfoMap;
	}
	
	private String translatedType;
	public String getTranslatedType() {
		String value = getSingleTrainingExtInfoMap().get("singletraining_type");
		translatedType = Constants.SINGLETRAINING_TYPE_MAPPING.get(value);
		
		return translatedType;
	}
	
	private String translatedTarget;
	public String getTranslatedTarget() {
		String value = getSingleTrainingExtInfoMap().get("singletraining_target");
		translatedTarget = Constants.SINGLETRAINING_TARGET_MAPPING.get(value);
		
		return translatedTarget;
	}
	/**
	 * @return the orgID
	 */
	public String getOrgID() {
		return orgID;
	}
	/**
	 * @param orgID the orgID to set
	 */
	public void setOrgID(String orgID) {
		this.orgID = orgID;
	}
	public String getTacticsId() {
		return tacticsId;
	}
	public void setTacticsId(String tacticsId) {
		this.tacticsId = tacticsId;
	}
	
}
