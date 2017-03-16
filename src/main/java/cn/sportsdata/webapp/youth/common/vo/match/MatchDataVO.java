package cn.sportsdata.webapp.youth.common.vo.match;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class MatchDataVO implements Serializable {
	private static final long serialVersionUID = 107419758031292408L;
	
	private String matchId;
	private String playerId;
	private List<MatchDataItemVO> itemList;
	
	private Map<String, String> itemMapping;
	
	private String playerName;
	private String playerJerseyNumber;
	
	public String getMatchId() {
		return matchId;
	}

	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}

	public String getPlayerId() {
		return playerId;
	}
	
	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	public List<MatchDataItemVO> getItemList() {
		return itemList;
	}

	public void setItemList(List<MatchDataItemVO> itemList) {
		this.itemList = itemList;
	}

	public Map<String, String> getItemMapping() {
		return itemMapping;
	}

	public void setItemMapping(Map<String, String> itemMapping) {
		this.itemMapping = itemMapping;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public String getPlayerJerseyNumber() {
		return playerJerseyNumber;
	}

	public void setPlayerJerseyNumber(String playerJerseyNumber) {
		this.playerJerseyNumber = playerJerseyNumber;
	}
}
