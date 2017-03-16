package cn.sportsdata.webapp.youth.common.vo.starters;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.sportsdata.webapp.youth.common.constants.Constants;
import cn.sportsdata.webapp.youth.common.vo.BaseVO;
import cn.sportsdata.webapp.youth.common.vo.TacticsVO;
import cn.sportsdata.webapp.youth.common.vo.UserVO;

public class StartersVO extends BaseVO implements Serializable {
	private static final long serialVersionUID = 4557828888208291532L;
	private String creator_id;
	private String org_id;
	private String tacticsId;
	private long formation_id;
	private String formationTypeName;
	private String name;
	private TacticsVO tacticsVo;
	private String description;
	private String status = "active";
	private String imgName;
	private String imgUrl;
	
	private long playground_id;
	private String tacticsdata;
	private String tacticsFrames;
	private String imgData;
	private long tactics_type_id;
	private boolean checked;
	
	private List<UserVO> playerList;
	
	public TacticsVO getTacticsVo() {
		return tacticsVo;
	}

	public void setTacticsVo(TacticsVO tacticsVo) {
		this.tacticsVo = tacticsVo;
	}

	public long getPlayground_id() {
		return playground_id;
	}

	public void setPlayground_id(long playground_id) {
		this.playground_id = playground_id;
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

	public String getImgData() {
		return imgData;
	}

	public void setImgData(String imgData) {
		this.imgData = imgData;
	}

	public long getTactics_type_id() {
		return tactics_type_id;
	}

	public void setTactics_type_id(long tactics_type_id) {
		this.tactics_type_id = tactics_type_id;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getFormationTypeName() {
		return formationTypeName;
	}

	public void setFormationTypeName(String formationTypeName) {
		this.formationTypeName = formationTypeName;
	}

	public List<UserVO> getPlayerList() {
		return playerList;
	}

	public void setPlayerList(List<UserVO> playerList) {
		this.playerList = playerList;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getImgName() {
		return imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	public long getFormation_id() {
		return formation_id;
	}

	public void setFormation_id(long formation_id) {
		this.formation_id = formation_id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
}
