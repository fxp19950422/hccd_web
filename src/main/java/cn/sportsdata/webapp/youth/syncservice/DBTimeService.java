package cn.sportsdata.webapp.youth.syncservice;

import cn.sportsdata.webapp.youth.syncdao.DBTimeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by binzhu on 5/5/16.
 */
@Service
public class DBTimeService {
    @Autowired
    private DBTimeDAO dbTimeDAODao;

    public String getDBTime(){
        return dbTimeDAODao.getDBTime();
    }
}
