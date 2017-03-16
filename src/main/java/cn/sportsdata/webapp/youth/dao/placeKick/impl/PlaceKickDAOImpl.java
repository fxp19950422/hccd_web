package cn.sportsdata.webapp.youth.dao.placeKick.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.sportsdata.webapp.youth.common.vo.PlaceKickVO;
import cn.sportsdata.webapp.youth.dao.BaseDAO;
import cn.sportsdata.webapp.youth.dao.placeKick.PlaceKickDAO;

@Repository
public class PlaceKickDAOImpl extends BaseDAO implements PlaceKickDAO {
	private static final String createPlaceKick = "createPlaceKick";
	private static final String getPlaceKickByID = "getPlaceKickByID";
	private static final String getPlaceKickByUserId = "getPlaceKickByUserId";
	private static final String getPlaceKickByUserIdAndType = "getPlaceKickByUserIdAndType";
	private static final String getPlaceKickByOrg = "getPlaceKickByOrg";
	private static final String getPlaceKickByOrgAndType = "getPlaceKickByOrgAndType";
	private static final String updatePlaceKick = "updatePlaceKick";
	private static final String deletePlaceKick = "deletePlaceKick";
	
	@Override
	public long createPlaceKick(PlaceKickVO PlaceKickVo) {
		long affectedRowNum = sqlSessionTemplate.insert(getSqlNameSpace(createPlaceKick), PlaceKickVo);
		return affectedRowNum;
	}

	@Override
	public PlaceKickVO getPlaceKickByID(String id) {
		return sqlSessionTemplate.selectOne(getSqlNameSpace(getPlaceKickByID), id);
	}

	@Override
	public List<PlaceKickVO> getPlaceKickByUserId(String userId, String orgId, String category) {
		Map<String, Object> paramList = new HashMap<String, Object>();
		paramList.put("creator_id", userId);
		paramList.put("category", category);
		paramList.put("org_id", orgId);
		return sqlSessionTemplate.selectList(getSqlNameSpace(getPlaceKickByUserId), paramList);
	}

	@Override
	public List<PlaceKickVO> getPlaceKickByTypeAndUserId(String userId, String orgId, String placeKickTypeId) {
		Map<String, String> paramList = new HashMap<String, String>();
		paramList.put("creator_id", userId);
		paramList.put("org_id", orgId);
		paramList.put("place_kick_type_id", placeKickTypeId);
		return sqlSessionTemplate.selectList(getSqlNameSpace(getPlaceKickByUserIdAndType), paramList);
	}
	
	@Override
	public List<PlaceKickVO> getPlaceKickByOrg(String orgId, String category) {
		Map<String, Object> paramList = new HashMap<String, Object>();
		paramList.put("category", category);
		paramList.put("org_id", orgId);
		return sqlSessionTemplate.selectList(getSqlNameSpace(getPlaceKickByOrg), paramList);
	}

	@Override
	public List<PlaceKickVO> getPlaceKickByOrgAndType(String orgId, String placeKickTypeId) {
		Map<String, String> paramList = new HashMap<String, String>();
		paramList.put("org_id", orgId);
		paramList.put("place_kick_type_id", placeKickTypeId);
		return sqlSessionTemplate.selectList(getSqlNameSpace(getPlaceKickByOrgAndType), paramList);
	}
	
	@Override
	public long updatePlaceKick(String tacticsId, String placeKickTypeId) {
		Map<String, String> paramList = new HashMap<String, String>();
		paramList.put("tactics_id", tacticsId);
		paramList.put("place_kick_type_id", placeKickTypeId);
		return sqlSessionTemplate.update(getSqlNameSpace(updatePlaceKick), paramList);
	}
	
	@Override
	public long deletePlaceKick(String tacticsId) {
		return sqlSessionTemplate.update(getSqlNameSpace(deletePlaceKick), tacticsId);
	}
}
