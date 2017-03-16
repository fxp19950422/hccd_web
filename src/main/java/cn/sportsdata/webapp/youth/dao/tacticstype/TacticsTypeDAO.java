package cn.sportsdata.webapp.youth.dao.tacticstype;

import java.util.List;

import cn.sportsdata.webapp.youth.common.vo.TacticsTypeVO;

public interface TacticsTypeDAO {
	TacticsTypeVO getTacticsTypeByID(long userId);
	List<TacticsTypeVO> getTacticsTypeCommon(String category);
	List<TacticsTypeVO> getTacticsTypeByOrgId(String orgId, String category);
}
