package cn.sportsdata.webapp.youth.common.vo.starters;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.sportsdata.webapp.youth.common.vo.BaseVO;
import cn.sportsdata.webapp.youth.common.vo.match.MatchStartersResultVO;

public class StartersPreviewsVO extends BaseVO implements Serializable {
	private static final long serialVersionUID = 6957030523585631757L;
	private String tacticsId;
	private String name;
	private String description;
	private String imgName;
	private String imgUrl;
	private long usedCount = 0;
	private long winCount = 0;
	private long loseCount = 0;
	private long drawCount = 0;
	private long formation_id;
	private String formationTypeName;
	private List<MatchStartersResultVO> recentRecordList = new ArrayList<MatchStartersResultVO>();;
	
	
	public long getFormation_id() {
		return formation_id;
	}
	public void setFormation_id(long formation_id) {
		this.formation_id = formation_id;
	}
	public String getFormationTypeName() {
		return formationTypeName;
	}
	public void setFormationTypeName(String formationTypeName) {
		this.formationTypeName = formationTypeName;
	}
	public List<MatchStartersResultVO> getRecentRecordList() {
		return recentRecordList;
	}
	public void setRecentRecordList(List<MatchStartersResultVO> recentRecordList) {
		this.recentRecordList = recentRecordList;
	}
	public String getTacticsId() {
		return tacticsId;
	}
	public void setTacticsId(String tacticsId) {
		this.tacticsId = tacticsId;
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
	public String getImgName() {
		return imgName;
	}
	public void setImgName(String imgName) {
		this.imgName = imgName;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public long getUsedCount() {
		return usedCount;
	}
	public void setUsedCount(long usedCount) {
		this.usedCount = usedCount;
	}
	public long getWinCount() {
		return winCount;
	}
	public void setWinCount(long winCount) {
		this.winCount = winCount;
	}
	public long getLoseCount() {
		return loseCount;
	}
	public void setLoseCount(long loseCount) {
		this.loseCount = loseCount;
	}
	public long getDrawCount() {
		return drawCount;
	}
	public void setDrawCount(long drawCount) {
		this.drawCount = drawCount;
	}
}
