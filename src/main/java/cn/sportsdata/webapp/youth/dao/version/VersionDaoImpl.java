/**
 * 
 */
package cn.sportsdata.webapp.youth.dao.version;

import org.springframework.stereotype.Repository;

import cn.sportsdata.webapp.youth.common.vo.VersionVO;
import cn.sportsdata.webapp.youth.dao.BaseDAO;


@Repository
public class VersionDaoImpl extends BaseDAO implements VersionDao {
	private static final String SELECT_VERSION = "selectVersion";

	@Override
	public VersionVO getVersionInfo() {
		return sqlSessionTemplate.selectOne(getSqlNameSpace(SELECT_VERSION));
	}

}
