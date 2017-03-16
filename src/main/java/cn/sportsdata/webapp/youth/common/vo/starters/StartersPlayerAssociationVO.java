package cn.sportsdata.webapp.youth.common.vo.starters;

import java.io.Serializable;

import cn.sportsdata.webapp.youth.common.vo.BaseVO;

public class StartersPlayerAssociationVO extends BaseVO implements Serializable {
	private static final long serialVersionUID = 4557828888208291532L;
	private String startersId;
	private String playerId;
	
	public String getStartersId() {
		return startersId;
	}
	public void setStartersId(String startersId) {
		this.startersId = startersId;
	}
	public String getPlayerId() {
		return playerId;
	}
	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}
}
