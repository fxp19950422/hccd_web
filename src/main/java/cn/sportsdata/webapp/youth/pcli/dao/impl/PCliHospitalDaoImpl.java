package cn.sportsdata.webapp.youth.pcli.dao.impl;

import org.springframework.stereotype.Repository;

import cn.sportsdata.webapp.youth.dao.BaseDAO;
import cn.sportsdata.webapp.youth.pcli.dao.PCliHospitalDao;
import cn.sportsdata.webapp.youth.pcli.vo.HospitalVO;

@Repository
public class PCliHospitalDaoImpl extends BaseDAO implements PCliHospitalDao {
	
	private static final String SELECT_HOSPITAL_INFO_BY_ID = "getHospitalInfoById";

	@Override
	public HospitalVO getHospitalInfo(String id) {
		return sqlSessionTemplate.selectOne(getSqlNameSpace(SELECT_HOSPITAL_INFO_BY_ID), id);
	}

}
