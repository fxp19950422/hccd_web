package cn.sportsdata.webapp.youth.syncac;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by binzhu on 5/10/16.
 */
@Component("NoteSyncACPolicyImpl")
public class NoteSyncACPolicyImpl extends BaseSyncACPolicyImpl{
    @Override
    public String getReadControlCondition(String tableName, Map acParam){
        List<String> orgIDList = (List<String>) acParam.get("orgIDList");
        String userID = (String) acParam.get("userID");

        if (orgIDList == null || orgIDList.size() == 0)
            return null;

        String conditionString = null;
        String key = ".note_id";
        if (tableName.equals("note")) {
            key = ".creator_id";
            conditionString = tableName + key + " = '" + userID + "'";
            return conditionString;
        }

        conditionString = tableName + key + 
                " in (select id from note where creator_id='" + userID + "')";

        return conditionString;
    }

    @Override
    public String getWriteControlCondition(String strTableName, Map acParam){
        return getReadControlCondition(strTableName, acParam);
    }
}
