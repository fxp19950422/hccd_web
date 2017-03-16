package cn.sportsdata.webapp.youth.service.tactics;

import java.util.List;

import cn.sportsdata.webapp.youth.common.vo.TacticsVO;
import cn.sportsdata.webapp.youth.common.vo.UserVO;

public interface TacticsService {
	TacticsVO getTacticsByID(String id);
	String createTactics(TacticsVO tactics);
	List<TacticsVO> getTacticsByOrgId(String org_id, String category);
	List<TacticsVO> getTacticsByUserId(String creator_id, String category);
	List<TacticsVO> getTacticsByTypeAndOrgId(String org_id, long tactics_type_id);
	List<TacticsVO> getTacticsByTypeAndUserId(String creator_id, long tactics_type_id);
	List<TacticsVO> getTacticsByUserAndOrgId(String creator_id, String org_id, String category);
	List<TacticsVO> getTacticsByTypeUserAndOrgId(String creator_id, String org_id,  long tactics_type_id);	
	List<TacticsVO> getTacticsByMatchId(String matchId);
	boolean updateTactics(TacticsVO tactics);
	boolean deleteTactics(TacticsVO tactics);
	boolean saveTacticsFiles(TacticsVO tacticsVo, String userId, String orgId);
	boolean loadTacticsFiles(TacticsVO tacticsVo);
	void cleanOldFiles();
	boolean copyTacticsFiles(TacticsVO dstTacticsVo, TacticsVO srcTacticsVo);
	long saveTacticsPlayer(TacticsVO tactics);
}
