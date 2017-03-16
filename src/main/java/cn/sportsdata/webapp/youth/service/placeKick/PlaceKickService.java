package cn.sportsdata.webapp.youth.service.placeKick;

import java.util.List;

import cn.sportsdata.webapp.youth.common.vo.PlaceKickVO;
import cn.sportsdata.webapp.youth.common.vo.TacticsVO;

public interface PlaceKickService {

	List<PlaceKickVO> getPlaceKickByUserId(String userId, String orgId, String category);

	List<PlaceKickVO> getPlaceKickByTypeAndUserId(String userId, String orgId, String placeKickTypeId);

	PlaceKickVO getPlaceKickById(String place_kick_id);
	TacticsVO getTacticsVOData(PlaceKickVO placeKickVo);

	String createPlaceKick(PlaceKickVO placeKickVo);

	boolean deletePlaceKick(PlaceKickVO placeKickVo);

	boolean updatePlaceKickType(PlaceKickVO placeKickVo);
}
