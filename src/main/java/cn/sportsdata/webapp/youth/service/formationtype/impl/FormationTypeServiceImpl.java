package cn.sportsdata.webapp.youth.service.formationtype.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.sportsdata.webapp.youth.common.vo.FormationTypeVO;
import cn.sportsdata.webapp.youth.dao.formationtype.FormationTypeDAO;
import cn.sportsdata.webapp.youth.service.formationtype.FormationTypeService;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FormationTypeServiceImpl implements FormationTypeService {
	@Autowired
	private FormationTypeDAO formationTypeDAO;
	
	@Override
	public FormationTypeVO getFormationTypeByID(long id) {
		return formationTypeDAO.getFormationTypeByID(id);
	}

	@Override
	public List<FormationTypeVO> getFormationTypeCommon(String category) {
		List<FormationTypeVO> tacticsTypeList = formationTypeDAO.getFormationTypeCommon(category);
		if(tacticsTypeList == null) {
			tacticsTypeList = new ArrayList<FormationTypeVO>();
		}
		return tacticsTypeList;
	}

	@Override
	public List<FormationTypeVO> getFormationTypeByOrgId(String org_id, String category) {
		List<FormationTypeVO> tacticsTypeList = formationTypeDAO.getFormationTypeByOrgId(org_id, category);
		if(tacticsTypeList == null) {
			tacticsTypeList = new ArrayList<FormationTypeVO>();
		}
		return tacticsTypeList;
	}
}
