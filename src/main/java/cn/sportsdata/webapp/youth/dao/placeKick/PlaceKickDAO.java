package cn.sportsdata.webapp.youth.dao.placeKick;

import java.util.List;

import cn.sportsdata.webapp.youth.common.vo.PlaceKickVO;

public interface PlaceKickDAO {
	PlaceKickVO getPlaceKickByID(String id);
	long createPlaceKick(PlaceKickVO PlaceKickVo);
	List<PlaceKickVO> getPlaceKickByUserId(String userId, String orgId, String category);
	List<PlaceKickVO> getPlaceKickByTypeAndUserId(String userId, String orgId, String placeKickTypeId);
	long updatePlaceKick(String tacticsId, String placeKickTypeId);
	List<PlaceKickVO> getPlaceKickByOrg(String orgId, String category);
	List<PlaceKickVO> getPlaceKickByOrgAndType(String orgId, String placeKickTypeId);
	long deletePlaceKick(String tacticsId);
}
