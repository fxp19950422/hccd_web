package cn.sportsdata.webapp.youth.service.starters;

import java.util.List;

import cn.sportsdata.webapp.youth.common.vo.TacticsVO;
import cn.sportsdata.webapp.youth.common.vo.starters.StartersPreviewsVO;
import cn.sportsdata.webapp.youth.common.vo.starters.StartersVO;

public interface StartersService {

	String createStarters(StartersVO startersVo, TacticsVO tacticsVo);
	
	long saveStartersPlayer(StartersVO startersVo);

	List<StartersPreviewsVO> getStartersByUserId(String userId, String orgId, String category);

	List<StartersPreviewsVO> getStartersByTypeAndUserId(String userId, String orgId, long formationId);

	StartersVO getStartersById(String starters_id);

	boolean deleteStarters(StartersVO startersVo);

	boolean updateStarters(StartersVO startersVo, TacticsVO tacticsVo);

	TacticsVO getTacticsVOData(StartersVO startersVo);

	StartersPreviewsVO getStartersMatchInfo(TacticsVO tacticsVo);
	
}
