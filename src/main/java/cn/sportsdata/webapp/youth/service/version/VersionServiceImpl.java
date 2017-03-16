package cn.sportsdata.webapp.youth.service.version;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.sportsdata.webapp.youth.common.vo.VersionVO;
import cn.sportsdata.webapp.youth.dao.version.VersionDao;


@Service
public class VersionServiceImpl implements VersionService {

	@Autowired
	VersionDao versionDao;
	
	@Override
	public VersionVO getVersionInfo() {
		// TODO Auto-generated method stub
		return versionDao.getVersionInfo();
	}
    
}
