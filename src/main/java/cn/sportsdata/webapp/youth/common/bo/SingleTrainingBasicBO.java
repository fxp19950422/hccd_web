package cn.sportsdata.webapp.youth.common.bo;
import java.io.Serializable;
import java.util.List;

import cn.sportsdata.webapp.youth.common.vo.SingleTrainingEquipmentVO;
import cn.sportsdata.webapp.youth.common.vo.SingleTrainingExtVO;


public class SingleTrainingBasicBO implements Serializable {

	private static final long serialVersionUID = -4766938024427127443L;
	private String id;
	private String name;
	private String description;
	private long player_count;
	private long goalkeeper_count;
	private String yard_width;
	private String yard_long;
	private String tacticsId;
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
	public String getTacticsId() {
		return tacticsId;
	}
	public void setTacticsId(String tacticsId) {
		this.tacticsId = tacticsId;
	}

}
