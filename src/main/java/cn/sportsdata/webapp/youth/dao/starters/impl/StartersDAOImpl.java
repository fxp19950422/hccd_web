package cn.sportsdata.webapp.youth.dao.starters.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.sportsdata.webapp.youth.common.vo.match.MatchStartersResultVO;
import cn.sportsdata.webapp.youth.common.vo.starters.StartersPlayerAssociationVO;
import cn.sportsdata.webapp.youth.common.vo.starters.StartersPreviewsVO;
import cn.sportsdata.webapp.youth.common.vo.starters.StartersVO;
import cn.sportsdata.webapp.youth.dao.BaseDAO;
import cn.sportsdata.webapp.youth.dao.starters.StartersDAO;

@Repository
public class StartersDAOImpl extends BaseDAO implements StartersDAO {
	private static final String createStarters = "createStarters";
	private static final String getStartersByID = "getStartersByID";
	private static final String getStartersByUserId = "getStartersByUserId";
	private static final String getStartersByUserIdAndFormation = "getStartersByUserIdAndFormation";
	private static final String saveStartersPlayer = "saveStartersPlayer";
	private static final String clearStartersPlayer = "clearStartersPlayer";
	private static final String getMatchStartersByOrgId = "getMatchStartersByOrgId";
	private static final String getMatchStartersByOrgIdAndFormation = "getMatchStartersByOrgIdAndFormation";
	private static final String updateStarters = "updateStarters";
	private static final String getStartersMatchInfoById = "getStartersMatchInfoById";
	private static final String getStartersByOrg = "getStartersByOrg";
	private static final String getStartersByOrgAndFormation = "getStartersByOrgAndFormation";
	private static final String deleteStarters = "deleteStarters";
	
	@Override
	public long createStarters(StartersVO startersVo) {
		long affectedRowNum = sqlSessionTemplate.insert(getSqlNameSpace(createStarters), startersVo);
		return affectedRowNum;
	}

	@Override
	public StartersVO getStartersByID(String id) {
		return sqlSessionTemplate.selectOne(getSqlNameSpace(getStartersByID), id);
	}
	
	@Override
	public List<StartersPreviewsVO> getStartersByOrg(String orgId, String category) {
		Map<String, String> paramList = new HashMap<String, String>();
		paramList.put("org_id", orgId);
		paramList.put("category", category);
		return sqlSessionTemplate.selectList(getSqlNameSpace(getStartersByOrg), paramList);
	}

	@Override
	public List<StartersPreviewsVO> getStartersByOrgAndFormation(String orgId, long formationId) {
		Map<String, Object> paramList = new HashMap<String, Object>();
		paramList.put("org_id", orgId);
		paramList.put("formation_id", formationId);
		return sqlSessionTemplate.selectList(getSqlNameSpace(getStartersByOrgAndFormation), paramList);
	}

	@Override
	public List<StartersPreviewsVO> getStartersByUserId(String userId, String orgId, String category) {
		Map<String, String> paramList = new HashMap<String, String>();
		paramList.put("creator_id", userId);
		paramList.put("org_id", orgId);
		paramList.put("category", category);
		return sqlSessionTemplate.selectList(getSqlNameSpace(getStartersByUserId), paramList);
	}

	@Override
	public List<StartersPreviewsVO> getStartersByTypeAndUserId(String userId, String orgId, long formationId) {
		Map<String, Object> paramList = new HashMap<String, Object>();
		paramList.put("creator_id", userId);
		paramList.put("org_id", orgId);
		paramList.put("formation_id", formationId);
		return sqlSessionTemplate.selectList(getSqlNameSpace(getStartersByUserIdAndFormation), paramList);
	}

	@Override
	public long saveStartersPlayer(List<StartersPlayerAssociationVO> playerList) {
		return sqlSessionTemplate.insert(getSqlNameSpace(saveStartersPlayer), playerList);
	}

	@Override
	public long clearStartersPlayer(String starterid) {
		return sqlSessionTemplate.delete(getSqlNameSpace(clearStartersPlayer), starterid);
	}
	
	@Override
	public List<MatchStartersResultVO> getMatchStartersByOrgId(String orgId, String category) {
		Map<String, String> paramList = new HashMap<String, String>();
		paramList.put("org_id", orgId);
		paramList.put("category", category);
		return sqlSessionTemplate.selectList(getSqlNameSpace(getMatchStartersByOrgId), paramList);
	}

	@Override
	public List<MatchStartersResultVO> getMatchStartersByOrgIdAndFormation(long userId, long orgId, long formationId) {
		Map<String, Object> paramList = new HashMap<String, Object>();
		paramList.put("creator_id", userId);
		paramList.put("org_id", orgId);
		paramList.put("formation_id", formationId);
		return sqlSessionTemplate.selectList(getSqlNameSpace(getMatchStartersByOrgIdAndFormation), paramList);
	}
	
	@Override
	public long updateStarters(String tacticsId, long formationId) {
		Map<String, Object> paramList = new HashMap<String, Object>();
		paramList.put("tactics_id", tacticsId);
		paramList.put("formation_id", formationId);
		return sqlSessionTemplate.update(getSqlNameSpace(updateStarters), paramList);
	}
	
	@Override
	public long deleteStarters(String tacticsId) {
		return sqlSessionTemplate.update(getSqlNameSpace(deleteStarters), tacticsId);
	}
	
	@Override
	public List<MatchStartersResultVO> getStartersMatchInfoById(String tacticsId) {
		return sqlSessionTemplate.selectList(getSqlNameSpace(getStartersMatchInfoById), tacticsId);
	}
}
