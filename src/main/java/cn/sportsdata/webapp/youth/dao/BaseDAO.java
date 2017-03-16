package cn.sportsdata.webapp.youth.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseDAO{
    @Autowired
    protected SqlSession sqlSessionTemplate;
    
//    protected static Logger logger = Logger.getLogger(BaseSyncDAOImpl.class);

    protected String getSqlNameSpace(String sqlName) {
        Type[] types = this.getClass().getGenericInterfaces();
        Class<?> clazz = null;

        if (types != null) {
            if (!(types[0] instanceof ParameterizedType)) {
                clazz = (Class<?>) types[0];
            }
        }

        return (clazz != null ? clazz.getName() : "") + "." + sqlName;
    }
}
