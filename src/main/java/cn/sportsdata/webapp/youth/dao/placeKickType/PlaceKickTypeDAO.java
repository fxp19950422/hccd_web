package cn.sportsdata.webapp.youth.dao.placeKickType;

import java.util.List;

import cn.sportsdata.webapp.youth.common.vo.PlaceKickTypeVO;

public interface PlaceKickTypeDAO {

	PlaceKickTypeVO getPlayceKickTypeByID(String id);

	List<PlaceKickTypeVO> getPlaceKickTypeCommon(String category);

	List<PlaceKickTypeVO> getPlaceKickTypeByOrgId(String id, String category);

}
