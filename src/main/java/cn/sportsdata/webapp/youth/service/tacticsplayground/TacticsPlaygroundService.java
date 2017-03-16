package cn.sportsdata.webapp.youth.service.tacticsplayground;

import java.util.List;

import cn.sportsdata.webapp.youth.common.vo.TacticsPlaygroundVO;

public interface TacticsPlaygroundService {
	TacticsPlaygroundVO getTacticsPlaygroundByID(long userId);
	List<TacticsPlaygroundVO> getTacticsPlaygroundCommon(String category);
}
