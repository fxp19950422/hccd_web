package cn.sportsdata.webapp.youth.common.vo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class TacticsVO extends BaseVO implements Serializable {
	private static final long serialVersionUID = 4557828888208291532L;
	private String id;
	private String creator_id;
	private String org_id;
	private String name;
	private String description;
	private long tactics_type_id;
	private long playground_id;
	private String imgName;
	private String imgUrl;
	private String imgData;
	private String tacticsdata;
	private String tacticsFrames;

	private boolean checked = false;
	
	private TacticsTypeVO tacticsType = null;
	private TacticsPlaygroundVO tacticsPlayground = null;
	private AssetVO imgAssetVo =null;
	private AssetVO dataAssetVo =null;

	private List<UserVO> playerList;
	
	public List<UserVO> getPlayerList() {
		return playerList;
	}

	public void setPlayerList(List<UserVO> playerList) {
		this.playerList = playerList;
	}

	public TacticsTypeVO getTacticsType() {
		return tacticsType;
	}

	public void setTacticsType(TacticsTypeVO tacticsType) {
		this.tacticsType = tacticsType;
	}

	public TacticsPlaygroundVO getTacticsPlayground() {
		return tacticsPlayground;
	}

	public void setTacticsPlayground(TacticsPlaygroundVO tacticsPlayground) {
		this.tacticsPlayground = tacticsPlayground;
	}

	public String getImgData() {
		return imgData;
	}

	public void setImgData(String imgData) {
		this.imgData = imgData;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

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

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getCreator_id() {
		return creator_id;
	}

	public void setCreator_id(String creator_id) {
		this.creator_id = creator_id;
	}

	public String getOrg_id() {
		return org_id;
	}

	public void setOrg_id(String org_id) {
		this.org_id = org_id;
	}


	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getImgName() {
		return imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	public long getPlayground_id() {
		return playground_id;
	}

	public void setPlayground_id(long playground_id) {
		this.playground_id = playground_id;
	}

	public long getTactics_type_id() {
		return tactics_type_id;
	}

	public void setTactics_type_id(long tactics_type_id) {
		this.tactics_type_id = tactics_type_id;
	}

	public String getTacticsdata() {
		return tacticsdata;
	}

	public void setTacticsdata(String tacticsdata) {
		this.tacticsdata = tacticsdata;
	}

	public String getTacticsFrames() {
		return tacticsFrames;
	}

	public void setTacticsFrames(String tacticsFrames) {
		this.tacticsFrames = tacticsFrames;
	}

	public AssetVO getImgAssetVo() {
		return imgAssetVo;
	}

	public void setImgAssetVo(AssetVO imgAssetVo) {
		this.imgAssetVo = imgAssetVo;
	}

	public AssetVO getDataAssetVo() {
		return dataAssetVo;
	}

	public void setDataAssetVo(AssetVO dataAssetVo) {
		this.dataAssetVo = dataAssetVo;
	}

}
