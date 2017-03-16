package cn.sportsdata.webapp.youth.syncac;

import java.util.List;
import java.util.Map;

/**
 * Created by binzhu on 5/10/16.
 */
public interface BaseSyncACPolicy {
    public String getReadControlCondition(String strTableName, Map acParam);
    public String getWriteControlCondition(String strTableName, Map acParam);
}
