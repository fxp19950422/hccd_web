package cn.sportsdata.webapp.youth.service.department.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.sportsdata.webapp.youth.common.vo.AssetVO;
import cn.sportsdata.webapp.youth.common.vo.DepartmentVO;
import cn.sportsdata.webapp.youth.common.vo.UserExtVO;
import cn.sportsdata.webapp.youth.common.vo.UserHospitalDepartmentVO;
import cn.sportsdata.webapp.youth.common.vo.UserOrgRoleVO;
import cn.sportsdata.webapp.youth.common.vo.UserVO;
import cn.sportsdata.webapp.youth.common.vo.match.PlayerMatchStatisticsVO;
import cn.sportsdata.webapp.youth.common.vo.utraining.UtrainingTaskEvaluationVO;
import cn.sportsdata.webapp.youth.dao.department.DepartmentDAO;
import cn.sportsdata.webapp.youth.service.department.DepartmentService;

@Service
public class DepartmentServiceImpl implements DepartmentService {
	
	@Autowired
	private DepartmentDAO departDAO;

	@Override
	public List<DepartmentVO> getDepartmentList(String hospitalId, String departmentId) {
		// TODO Auto-generated method stub
		return departDAO.getDepartmentList(hospitalId, departmentId);
	}

	@Override
	public DepartmentVO getDepartmentFromLoginId(String userId) {
		// TODO Auto-generated method stub
		return departDAO.getDepartmentFromLoginId(userId);
	}

	
	
}
