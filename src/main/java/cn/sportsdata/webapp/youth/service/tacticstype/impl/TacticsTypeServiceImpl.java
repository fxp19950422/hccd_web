package cn.sportsdata.webapp.youth.service.tacticstype.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.sportsdata.webapp.youth.common.vo.TacticsTypeVO;
import cn.sportsdata.webapp.youth.dao.tacticstype.TacticsTypeDAO;
import cn.sportsdata.webapp.youth.service.tacticstype.TacticsTypeService;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TacticsTypeServiceImpl implements TacticsTypeService {
	@Autowired
	private TacticsTypeDAO tacticsTypeDAO;
	@Override
	public TacticsTypeVO getTacticsTypeByID(long id) {
		// TODO Auto-generated method stub
		return tacticsTypeDAO.getTacticsTypeByID(id);
	}

	@Override
	public List<TacticsTypeVO> getTacticsTypeCommon(String category) {
		List<TacticsTypeVO> tacticsTypeList = tacticsTypeDAO.getTacticsTypeCommon(category);
		if(tacticsTypeList == null) {
			tacticsTypeList = new ArrayList<TacticsTypeVO>();
		}
		return tacticsTypeList;
	}

	@Override
	public List<TacticsTypeVO> getTacticsTypeByOrgId(String org_id, String category) {
		List<TacticsTypeVO> tacticsTypeList = tacticsTypeDAO.getTacticsTypeByOrgId(org_id, category);
		if(tacticsTypeList == null) {
			tacticsTypeList = new ArrayList<TacticsTypeVO>();
		}
		return tacticsTypeList;
	}
}
