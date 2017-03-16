package cn.sportsdata.webapp.youth.dao.equipment;
import java.util.List;

import cn.sportsdata.webapp.youth.common.vo.EquipmentVO;

public interface EquipmentDAO{
	List<EquipmentVO> getEquipmentList(String orgId);

    boolean handleEquipment(EquipmentVO equipment,boolean isCreate);

    boolean deleteEquipment(String id);

    EquipmentVO getEquipmentByID(String equipmentID);

}

