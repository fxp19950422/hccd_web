/**
 * 
 */
package cn.sportsdata.webapp.youth.dao.share;

import org.springframework.stereotype.Repository;

import cn.sportsdata.webapp.youth.common.vo.share.ShareLinkVO;
import cn.sportsdata.webapp.youth.dao.BaseDAO;

/**
 * @author king
 *
 */
@Repository
public class ShareLinkDAOImpl extends BaseDAO implements ShareLinkDAO {
	private static final String SELECT_SHARE_LINK_BY_SHA256 = "selectShareLinkBysha256";
	private static final String INSERT_SHARE_LINK = "insertShareLink";
	private static final String GET_SHARE_LINK_BY_GUID = "getShareLinkByGUID";
	private static final String DELETE_SHARE_LINK_BY_SHA256 = "deleteShareLinkBysha256";

	
	@Override
	public boolean insertShareLink(ShareLinkVO shareLinkVO) {
		int affectedRowNum = sqlSessionTemplate.insert(getSqlNameSpace(INSERT_SHARE_LINK), shareLinkVO);
		return affectedRowNum == 1;
	}

	
	@Override
	public ShareLinkVO selectShareLinkBysha256(String sha256) {
		return sqlSessionTemplate.selectOne(getSqlNameSpace(SELECT_SHARE_LINK_BY_SHA256), sha256);
	}
	
	
	@Override
	public ShareLinkVO getShareLinkByGUID(String guid) {
		return sqlSessionTemplate.selectOne(getSqlNameSpace(GET_SHARE_LINK_BY_GUID), guid);
	}
	
	@Override
	public boolean deleteShareLinkBysha256(String sha256) {
		int i = sqlSessionTemplate.delete(getSqlNameSpace(DELETE_SHARE_LINK_BY_SHA256), sha256);
		return i > 0;
	}

}
