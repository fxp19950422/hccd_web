package cn.sportsdata.webapp.youth.dao.placeKickType.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.sportsdata.webapp.youth.common.vo.PlaceKickTypeVO;
import cn.sportsdata.webapp.youth.dao.BaseDAO;
import cn.sportsdata.webapp.youth.dao.placeKickType.PlaceKickTypeDAO;

@Repository
public class PlaceKickTypeDAOImpl extends BaseDAO implements PlaceKickTypeDAO {
	private static final String getPlayceKickTypeByID = "getPlayceKickTypeByID";
	private static final String getPlaceKickTypeCommon = "getPlaceKickTypeCommon";
	private static final String getPlaceKickTypeByOrgId = "getPlaceKickTypeByOrgId";

	@Override
	public PlaceKickTypeVO getPlayceKickTypeByID(String id) {
		return sqlSessionTemplate.selectOne(getSqlNameSpace(getPlayceKickTypeByID), id);
	}

	@Override
	public List<PlaceKickTypeVO> getPlaceKickTypeCommon(String category) {
		return sqlSessionTemplate.selectList(getSqlNameSpace(getPlaceKickTypeCommon), category);
	}

	@Override
	public List<PlaceKickTypeVO> getPlaceKickTypeByOrgId(String id, String category) {
		Map<String, String> paramList = new HashMap<String, String>();
		paramList.put("org_id", id);
		paramList.put("category", category);
		return sqlSessionTemplate.selectList(getSqlNameSpace(getPlaceKickTypeByOrgId), paramList);
	}

}
