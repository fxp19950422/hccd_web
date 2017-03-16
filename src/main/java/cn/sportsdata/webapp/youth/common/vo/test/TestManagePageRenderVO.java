package cn.sportsdata.webapp.youth.common.vo.test;

import java.util.ArrayList;
import java.util.List;

import cn.sportsdata.webapp.youth.common.constants.Constants;
import cn.sportsdata.webapp.youth.common.vo.UserVO;

public class TestManagePageRenderVO {

	private List<UserVO> forwardList = new ArrayList<UserVO>();
	private List<UserVO> centerList = new ArrayList<UserVO>();
	private List<UserVO> defenderList = new ArrayList<UserVO>();
	private List<UserVO> goalKeeperList = new ArrayList<UserVO>();
	
	//private List<TestBatchRenderVO>  testBatchList =  new ArrayList<TestBatchRenderVO>();

	public List<UserVO> getForwardList() {
		return forwardList;
	}

	public List<UserVO> getCenterList() {
		return centerList;
	}

	public List<UserVO> getDefenderList() {
		return defenderList;
	}

	public List<UserVO> getGoalKeeperList() {
		return goalKeeperList;
	}
	
//	public List<TestBatchRenderVO> getTestBatchList() {
//		return testBatchList;
//	}
//
//	public TestManagePageRenderVO withTestBatches(List<TestBatchRenderVO>  testBatchList){
//		if(testBatchList != null){
//			this.testBatchList.addAll(testBatchList);
//		}
//		return this;
//	}

	public TestManagePageRenderVO withPlayers(List<UserVO> playerList) {
		if (playerList == null) {
			return this;
		}
		for (UserVO player : playerList) {
			String position = player.getUserExtInfoMap().get("professional_primary_position");

			switch (position) {
			case Constants.FORWARD_POSITION:
				this.forwardList.add(player);
				break;
			case Constants.CENTER_POSITION:
				this.centerList.add(player);
				break;
			case Constants.DEFENDER_POSITION:
				this.defenderList.add(player);
				break;
			case Constants.GOALKEEPER_POSITION:
				this.goalKeeperList.add(player);
				break;
			default:
				break;
			}
		}
		return this;
	}

}
