package cn.sportsdata.webapp.youth.dao.tactics.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.sportsdata.webapp.youth.dao.BaseDAO;
import org.springframework.stereotype.Repository;

import cn.sportsdata.webapp.youth.common.vo.TacticsVO;
import cn.sportsdata.webapp.youth.dao.tactics.TacticsDAO;

@Repository
public class TacticsDAOImpl extends BaseDAO implements TacticsDAO {
	private static final String getTacticsByID = "getTacticsByID";
	private static final String getTacticsByOrgId = "getTacticsByOrgId";
	private static final String getTacticsByUserId = "getTacticsByUserId";
	private static final String getTacticsByTypeAndOrgId = "getTacticsByTypeAndOrgId";
	private static final String getTacticsByTypeAndUserId = "getTacticsByTypeAndUserId";
	private static final String createTactics = "createTactics";
	private static final String updateTactics = "updateTactics";
	private static final String deleteTactics = "deleteTactics";
	private static final String getTacticsByUserAndOrgId = "getTacticsByUserAndOrgId";
	private static final String getTacticsByTypeUserAndOrgId = "getTacticsByTypeUserAndOrgId";
	private static final String getTacticsByMatchId = "getTacticsByMatchId";
	
	@Override
	public TacticsVO getTacticsByID(String id) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne(getSqlNameSpace(getTacticsByID), id);
	}

	@Override
	public List<TacticsVO> getTacticsByOrgId(String org_id, String category) {
		// TODO Auto-generated method stub
		Map<String, String> paramList = new HashMap<String, String>();
		paramList.put("org_id", org_id);
		paramList.put("category", category);
		return sqlSessionTemplate.selectList(getSqlNameSpace(getTacticsByOrgId), paramList);
	}

	@Override
	public List<TacticsVO> getTacticsByUserId(String creator_id, String category) {
		// TODO Auto-generated method stub
		Map<String, Object> paramList = new HashMap<String, Object>();
		paramList.put("creator_id", creator_id);
		paramList.put("category", category);
		return sqlSessionTemplate.selectList(getSqlNameSpace(getTacticsByUserId), paramList);
	}

	@Override
	public List<TacticsVO> getTacticsByTypeAndOrgId(String org_id, long tactics_type_id) {
		// TODO Auto-generated method stub
		Map<String, Object> paramList = new HashMap<String, Object>();
		paramList.put("org_id", org_id);
		paramList.put("tactics_type_id", tactics_type_id);
		return sqlSessionTemplate.selectList(getSqlNameSpace(getTacticsByTypeAndOrgId), paramList);
	}

	@Override
	public List<TacticsVO> getTacticsByTypeAndUserId(String creator_id, long tactics_type_id) {
		// TODO Auto-generated method stub
		Map<String, Object> paramList = new HashMap<String, Object>();
		paramList.put("creator_id", creator_id);
		paramList.put("tactics_type_id", tactics_type_id);
		return sqlSessionTemplate.selectList(getSqlNameSpace(getTacticsByTypeAndUserId), paramList);
	}

	@Override
	public String createTactics(TacticsVO tactics) {
		long affectedRowNum = sqlSessionTemplate.insert(getSqlNameSpace(createTactics), tactics);
		if(affectedRowNum>0) {
			//long last_insert_id = sqlSessionTemplate.selectOne(getSqlNameSpace(lastInsertId));
			//return last_insert_id;
			return tactics.getId();
		}
		return null;
	}
	
	@Override
	public boolean updateTactics(TacticsVO tactics) {
		long affectedRowNum = sqlSessionTemplate.update(getSqlNameSpace(updateTactics), tactics);
		return affectedRowNum>0;
	}
	
	@Override
	public boolean deleteTactics(TacticsVO tactics) {
		long affectedRowNum = sqlSessionTemplate.update(getSqlNameSpace(deleteTactics), tactics);
		return affectedRowNum>0;
	}

	@Override
	public List<TacticsVO> getTacticsByUserAndOrgId(String creator_id, String org_id, String category) {
		Map<String, String> paramList = new HashMap<String, String>();
		paramList.put("creator_id", creator_id);
		paramList.put("category", category);
		paramList.put("org_id", org_id);
		return sqlSessionTemplate.selectList(getSqlNameSpace(getTacticsByUserAndOrgId), paramList);
	}

	@Override
	public List<TacticsVO> getTacticsByTypeUserAndOrgId(String creator_id, String org_id, long tactics_type_id) {
		// TODO Auto-generated method stub
		Map<String, Object> paramList = new HashMap<String, Object>();
		paramList.put("creator_id", creator_id);
		paramList.put("tactics_type_id", tactics_type_id);
		paramList.put("org_id", org_id);
		return sqlSessionTemplate.selectList(getSqlNameSpace(getTacticsByTypeUserAndOrgId), paramList);
	}

	@Override
	public List<TacticsVO> getTacticsByMatchId(String matchId) {
		return sqlSessionTemplate.selectList(getSqlNameSpace(getTacticsByMatchId), matchId);
	}
}
