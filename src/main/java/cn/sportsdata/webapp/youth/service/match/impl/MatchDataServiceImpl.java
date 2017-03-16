package cn.sportsdata.webapp.youth.service.match.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.sportsdata.webapp.youth.common.vo.match.MatchDataVO;
import cn.sportsdata.webapp.youth.dao.match.MatchDAO;
import cn.sportsdata.webapp.youth.dao.match.MatchDataDAO;
import cn.sportsdata.webapp.youth.service.match.MatchDataService;

@Service
public class MatchDataServiceImpl implements MatchDataService {
	@Autowired
	private MatchDataDAO matchDataDAO;
	
	@Autowired
	private MatchDAO matchDAO;

	@Override
	@Transactional
	public List<MatchDataVO> getMatchDataInfoByMatchList(List<String> matchIdList) {
		List<MatchDataVO> list = matchDataDAO.getMatchDataInfoByMatchList(matchIdList);
		Map<String, List<MatchDataVO>> map = new HashMap<String, List<MatchDataVO>>();
		
		// processing match datas
		for (int i = 0; i < list.size(); i++){
			MatchDataVO matchData = list.get(i);
			List<MatchDataVO> voList = map.get(matchData.getMatchId());
			if (voList == null){
				voList = new ArrayList<MatchDataVO>();
				map.put(matchData.getMatchId(), voList);
			}
			voList.add(matchData);
		}
		
		for (String key : map.keySet()) {
			matchDAO.handleMatchData(key, map.get(key)) ;
		}
		
		return null;
	}

	

	
	
	
}
