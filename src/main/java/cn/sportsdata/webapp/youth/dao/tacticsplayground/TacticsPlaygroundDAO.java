package cn.sportsdata.webapp.youth.dao.tacticsplayground;

import java.util.List;

import cn.sportsdata.webapp.youth.common.vo.TacticsPlaygroundVO;

public interface TacticsPlaygroundDAO {
	TacticsPlaygroundVO getTacticsPlaygroundByID(long userId);
	List<TacticsPlaygroundVO> getTacticsPlaygroundCommon(String category);
}
