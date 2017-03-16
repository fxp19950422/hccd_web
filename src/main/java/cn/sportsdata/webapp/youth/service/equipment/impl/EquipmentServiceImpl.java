package cn.sportsdata.webapp.youth.service.equipment.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.sportsdata.webapp.youth.common.utils.StringUtil;
import cn.sportsdata.webapp.youth.common.vo.EquipmentVO;
import cn.sportsdata.webapp.youth.dao.equipment.EquipmentDAO;
import cn.sportsdata.webapp.youth.service.equipment.EquipmentService;

@Service
public class EquipmentServiceImpl implements EquipmentService {
	@Autowired
	private EquipmentDAO equipmentDAO;
	
	@Override
	public List<EquipmentVO> getEquipmentList(String orgId) {
		return equipmentDAO.getEquipmentList(orgId);
	}

	@Override
	public boolean createEquipment(EquipmentVO equipmentVO) {
		return equipmentDAO.handleEquipment(equipmentVO,true);
	}

	@Override
	public boolean updateEquipment(EquipmentVO equipmentVO) {
		return equipmentDAO.handleEquipment(equipmentVO,false);
	}

	@Override
	public boolean deleteEquipment(String id) {
		return equipmentDAO.deleteEquipment(id);
	}

	@Override
	public EquipmentVO getEquipmentByID(String equipmentID){
		if (StringUtil.isBlank(equipmentID)) {
			return null;
		}
		EquipmentVO equipmentVO = equipmentDAO.getEquipmentByID(equipmentID);
		return equipmentVO;
	}

}
