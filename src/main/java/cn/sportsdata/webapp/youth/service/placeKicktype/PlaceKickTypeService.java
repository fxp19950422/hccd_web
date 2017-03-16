package cn.sportsdata.webapp.youth.service.placeKicktype;

import java.util.List;

import cn.sportsdata.webapp.youth.common.vo.PlaceKickTypeVO;

public interface PlaceKickTypeService {

	List<PlaceKickTypeVO> getPlaceKickTypeCommon(String category);

	List<PlaceKickTypeVO> getPlaceKickTypeByOrgId(String id, String category);

	PlaceKickTypeVO getPlaceKickTypeByID(String id);

}
