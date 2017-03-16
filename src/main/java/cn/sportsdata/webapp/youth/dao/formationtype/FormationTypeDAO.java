package cn.sportsdata.webapp.youth.dao.formationtype;

import java.util.List;

import cn.sportsdata.webapp.youth.common.vo.FormationTypeVO;

public interface FormationTypeDAO {
	FormationTypeVO getFormationTypeByID(long userId);
	List<FormationTypeVO> getFormationTypeCommon(String category);
	List<FormationTypeVO> getFormationTypeByOrgId(String orgId, String category);
}
