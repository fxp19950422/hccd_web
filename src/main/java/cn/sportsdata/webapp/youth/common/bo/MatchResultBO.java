package cn.sportsdata.webapp.youth.common.bo;

import java.io.Serializable;
import java.util.List;

import cn.sportsdata.webapp.youth.common.vo.match.GuestFoulVO;
import cn.sportsdata.webapp.youth.common.vo.match.GuestScoreVO;
import cn.sportsdata.webapp.youth.common.vo.match.GuestSubVO;
import cn.sportsdata.webapp.youth.common.vo.match.HomeFoulVO;
import cn.sportsdata.webapp.youth.common.vo.match.HomeScoreVO;
import cn.sportsdata.webapp.youth.common.vo.match.HomeSubVO;
import cn.sportsdata.webapp.youth.common.vo.match.MatchAssetVO;
import cn.sportsdata.webapp.youth.common.vo.match.MatchDataVO;

public class MatchResultBO implements Serializable {
	private static final long serialVersionUID = -3913013149264196034L;
	
	private String matchId;
	private String orgId;
	private String creatorId;
	private int homeScore;
	private int guestScore;
	private List<HomeScoreVO> homeScoreData;
	private List<GuestScoreVO> guestScoreData;
	private List<HomeFoulVO> homeFoulData;
	private List<GuestFoulVO> guestFoulData;
	private List<HomeSubVO> homeSubData;
	private List<GuestSubVO> guestSubData;
	private List<MatchDataVO> matchData;
	private List<MatchAssetVO> matchPhotoAssets;
	private List<MatchAssetVO> matchAttachAssets;
	
	public String getMatchId() {
		return matchId;
	}

	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public int getHomeScore() {
		return homeScore;
	}
	
	public void setHomeScore(int homeScore) {
		this.homeScore = homeScore;
	}
	
	public int getGuestScore() {
		return guestScore;
	}
	
	public void setGuestScore(int guestScore) {
		this.guestScore = guestScore;
	}
	
	public List<HomeScoreVO> getHomeScoreData() {
		return homeScoreData;
	}
	
	public void setHomeScoreData(List<HomeScoreVO> homeScoreData) {
		this.homeScoreData = homeScoreData;
	}
	
	public List<GuestScoreVO> getGuestScoreData() {
		return guestScoreData;
	}
	
	public void setGuestScoreData(List<GuestScoreVO> guestScoreData) {
		this.guestScoreData = guestScoreData;
	}
	
	public List<HomeFoulVO> getHomeFoulData() {
		return homeFoulData;
	}
	
	public void setHomeFoulData(List<HomeFoulVO> homeFoulData) {
		this.homeFoulData = homeFoulData;
	}
	
	public List<GuestFoulVO> getGuestFoulData() {
		return guestFoulData;
	}
	
	public void setGuestFoulData(List<GuestFoulVO> guestFoulData) {
		this.guestFoulData = guestFoulData;
	}
	
	public List<HomeSubVO> getHomeSubData() {
		return homeSubData;
	}
	
	public void setHomeSubData(List<HomeSubVO> homeSubData) {
		this.homeSubData = homeSubData;
	}
	
	public List<GuestSubVO> getGuestSubData() {
		return guestSubData;
	}
	
	public void setGuestSubData(List<GuestSubVO> guestSubData) {
		this.guestSubData = guestSubData;
	}
	
	public List<MatchDataVO> getMatchData() {
		return matchData;
	}
	
	public void setMatchData(List<MatchDataVO> matchData) {
		this.matchData = matchData;
	}
	
	public List<MatchAssetVO> getMatchPhotoAssets() {
		return matchPhotoAssets;
	}
	
	public void setMatchPhotoAssets(List<MatchAssetVO> matchPhotoAssets) {
		this.matchPhotoAssets = matchPhotoAssets;
	}
	
	public List<MatchAssetVO> getMatchAttachAssets() {
		return matchAttachAssets;
	}
	
	public void setMatchAttachAssets(List<MatchAssetVO> matchAttachAssets) {
		this.matchAttachAssets = matchAttachAssets;
	}
}
