package cn.sportsdata.webapp.youth.dao.equipment.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.sportsdata.webapp.youth.common.vo.EquipmentVO;
import cn.sportsdata.webapp.youth.dao.BaseDAO;
import cn.sportsdata.webapp.youth.dao.equipment.EquipmentDAO;

@Repository
public class EquipmentDAOImpl  extends BaseDAO  implements EquipmentDAO {
	private static final String GET_BY_ID = "getEquipmentByID";
	private static final String INSERT_EQUIPMENT_DATA = "insertEquipmentData";
	private static final String UPDATE_EQUIPMENT_DATA = "updateEquipmentData";
	private static final String DELETE_EQUIPMENT_BY_ID = "deleteEquipmentById";
	private static final String DELETE_SINGLE_EQUIPMENT_BY_ID = "deleteSingleTrainingEquipmentById";
	private static final String EQUIPMENT_LIST = "selectEquipmentList";
	private static final String SINGLETRAIN_EQUIPMENT_LIST = "getSingleTrainingEquipmentByEquipmentId";

	@Override
	public List<EquipmentVO> getEquipmentList(String orgId) {
		return sqlSessionTemplate.selectList(getSqlNameSpace(EQUIPMENT_LIST),orgId);
	}

	@Override
	public EquipmentVO getEquipmentByID(String id) {
		return sqlSessionTemplate.selectOne(getSqlNameSpace(GET_BY_ID), id);
	}

	@Override
	public boolean handleEquipment(EquipmentVO equipment,boolean isCreate) {
		int affectedFormNum =  sqlSessionTemplate.insert(getSqlNameSpace(isCreate ? INSERT_EQUIPMENT_DATA : UPDATE_EQUIPMENT_DATA), equipment);
		return affectedFormNum==1;
	}

	@Override
	public boolean deleteEquipment(String id) {
		int equNum = getSingleTrainingEquipmentListById(id);
		if(equNum>0){
			int equDel =sqlSessionTemplate.delete(getSqlNameSpace(DELETE_SINGLE_EQUIPMENT_BY_ID), id);
			if(equDel!=equNum){
				return false;
			}
		}
		int affectedRowNum = sqlSessionTemplate.delete(getSqlNameSpace(DELETE_EQUIPMENT_BY_ID), id);
		return affectedRowNum == 1;
	}

	private int getSingleTrainingEquipmentListById(String id) {
		
		return sqlSessionTemplate.selectOne(getSqlNameSpace(SINGLETRAIN_EQUIPMENT_LIST), id);
	}
}
