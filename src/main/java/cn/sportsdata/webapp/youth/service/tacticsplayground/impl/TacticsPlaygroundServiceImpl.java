package cn.sportsdata.webapp.youth.service.tacticsplayground.impl;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.sportsdata.webapp.youth.common.vo.TacticsPlaygroundVO;
import cn.sportsdata.webapp.youth.dao.tacticsplayground.TacticsPlaygroundDAO;
import cn.sportsdata.webapp.youth.service.tacticsplayground.TacticsPlaygroundService;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TacticsPlaygroundServiceImpl implements TacticsPlaygroundService {
	@Autowired
	private TacticsPlaygroundDAO tacticsPlaygroundDAO;
	@Override
	public TacticsPlaygroundVO getTacticsPlaygroundByID(long id) {
		// TODO Auto-generated method stub
		return tacticsPlaygroundDAO.getTacticsPlaygroundByID(id);
	}

	@Override
	public List<TacticsPlaygroundVO> getTacticsPlaygroundCommon(String category) {
		// TODO Auto-generated method stub
		return tacticsPlaygroundDAO.getTacticsPlaygroundCommon(category);
	}
}
