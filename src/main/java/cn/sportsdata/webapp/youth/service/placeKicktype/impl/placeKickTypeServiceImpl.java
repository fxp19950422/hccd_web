package cn.sportsdata.webapp.youth.service.placeKicktype.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.sportsdata.webapp.youth.common.vo.PlaceKickTypeVO;
import cn.sportsdata.webapp.youth.dao.placeKickType.PlaceKickTypeDAO;
import cn.sportsdata.webapp.youth.service.placeKicktype.PlaceKickTypeService;
import org.springframework.transaction.annotation.Transactional;

@Service
public class placeKickTypeServiceImpl implements PlaceKickTypeService {
	@Autowired
	private PlaceKickTypeDAO placeKickTypeDAO;

	@Override
	public PlaceKickTypeVO getPlaceKickTypeByID(String id) {
		return placeKickTypeDAO.getPlayceKickTypeByID(id);
	}
	
	@Override
	public List<PlaceKickTypeVO> getPlaceKickTypeCommon(String category) {
		List<PlaceKickTypeVO> tacticsTypeList = placeKickTypeDAO.getPlaceKickTypeCommon(category);
		if(tacticsTypeList == null) {
			tacticsTypeList = new ArrayList<PlaceKickTypeVO>();
		}
		return tacticsTypeList;
	}

	@Override
	public List<PlaceKickTypeVO> getPlaceKickTypeByOrgId(String id, String category) {
		List<PlaceKickTypeVO> tacticsTypeList = placeKickTypeDAO.getPlaceKickTypeByOrgId(id, category);
		if(tacticsTypeList == null) {
			tacticsTypeList = new ArrayList<PlaceKickTypeVO>();
		}
		return tacticsTypeList;
	}

}
