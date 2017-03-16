package cn.sportsdata.webapp.youth.service.equipment;

import java.util.List;


/**
 * Created by binzhu on 5/4/16.
 */
import cn.sportsdata.webapp.youth.common.vo.EquipmentVO;

public interface EquipmentService{

	List<EquipmentVO> getEquipmentList(String orgId);

	boolean createEquipment(EquipmentVO equipmentVO);

	boolean updateEquipment(EquipmentVO equipmentVO);

	boolean deleteEquipment(String parseLong);

    public EquipmentVO getEquipmentByID(String equipmentID);

}
