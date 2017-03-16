package cn.sportsdata.webapp.youth.dao.starters;

import java.util.List;

import cn.sportsdata.webapp.youth.common.vo.match.MatchStartersResultVO;
import cn.sportsdata.webapp.youth.common.vo.starters.StartersPlayerAssociationVO;
import cn.sportsdata.webapp.youth.common.vo.starters.StartersPreviewsVO;
import cn.sportsdata.webapp.youth.common.vo.starters.StartersVO;

public interface StartersDAO {
	StartersVO getStartersByID(String id);
	long createStarters(StartersVO startersVo);
	List<StartersPreviewsVO> getStartersByUserId(String userId, String orgId, String category);
	List<StartersPreviewsVO> getStartersByTypeAndUserId(String userId, String orgId, long formationId);
	long saveStartersPlayer(List<StartersPlayerAssociationVO> playerList);
	long clearStartersPlayer(String starterid);
	List<MatchStartersResultVO> getMatchStartersByOrgId(String orgId, String category);
	List<MatchStartersResultVO> getMatchStartersByOrgIdAndFormation(long userId, long orgId, long formationId);
	long updateStarters(String tacticsId, long formationId);
	List<MatchStartersResultVO> getStartersMatchInfoById(String tacticsId);
	List<StartersPreviewsVO> getStartersByOrg(String orgId, String category);
	List<StartersPreviewsVO> getStartersByOrgAndFormation(String orgId, long formationId);
	long deleteStarters(String tacticsId);
}
