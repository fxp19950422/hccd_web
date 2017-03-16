package cn.sportsdata.webapp.youth.service.formationtype;

import java.util.List;

import cn.sportsdata.webapp.youth.common.vo.FormationTypeVO;

public interface FormationTypeService {

	FormationTypeVO getFormationTypeByID(long userId);
	List<FormationTypeVO> getFormationTypeCommon(String category);
	List<FormationTypeVO> getFormationTypeByOrgId(String orgId, String category);

}
