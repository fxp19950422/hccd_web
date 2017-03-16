package cn.sportsdata.webapp.youth.service.privilege;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.sportsdata.webapp.youth.common.vo.privilege.PrivilegeVO;
import cn.sportsdata.webapp.youth.dao.privilege.PrivilegeDAO;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PrivilegeServiceImpl implements PrivilegeService {
    @Autowired
    private PrivilegeDAO privilegeDAO;
    
	@Override
	public List<PrivilegeVO> getUserPrivileges(String userID,String orgID) {
		return privilegeDAO.getUserPrivileges(userID, orgID);
	}
}
