package cn.sportsdata.webapp.youth.syncdao;

import cn.sportsdata.webapp.youth.common.vo.DBTimeVO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by binzhu on 5/5/16.
 */
@Repository("DBTimeDAO")
public class DBTimeDAO {
    @Autowired
    protected SqlSession sqlSessionTemplate;

    private static final String GET_DB_TIME =  "getDBTime";

    public String getDBTime() {
        DBTimeVO dbTimeVO = (DBTimeVO) sqlSessionTemplate.selectOne("cn.sportsdata.webapp.youth.syncdao.DBTimeDAO." + GET_DB_TIME);
        return dbTimeVO.getCurrentDBTime();
    }
}
