package cn.sportsdata.webapp.youth.common.bo;

import java.io.Serializable;
import java.util.List;

import cn.sportsdata.webapp.youth.common.vo.UserExtVO;

public class UserBO implements Serializable {
	private static final long serialVersionUID = -3241057053751465160L;
	
	private UserBasicBO basicData;
	private List<UserExtVO> userItemList;
	
	public UserBasicBO getBasicData() {
		return basicData;
	}

	public void setBasicData(UserBasicBO basicData) {
		this.basicData = basicData;
	}

	public List<UserExtVO> getUserItemList() {
		return userItemList;
	}
	
	public void setUserItemList(List<UserExtVO> userItemList) {
		this.userItemList = userItemList;
	}
}
