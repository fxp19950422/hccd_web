package cn.sportsdata.webapp.youth.syncac;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by binzhu on 5/10/16.
 */
@Component("UserSyncACPolicyImpl")
public class UserSyncACPolicyImpl extends BaseSyncACPolicyImpl{
    @Override
    public String getReadControlCondition(String tableName, Map acParam){
        List orgIDList = (List<String>) acParam.get("orgIDList");
        String userID = (String) acParam.get("userID");

        if (orgIDList == null || orgIDList.size() == 0)
            return null;

        String conditionString = null;
        String key = ".task_id";
        if (tableName.equals("user")){
            key = ".id";
        }else if (tableName.equals("user_ext")){
            key = ".user_id";
        }else if (tableName.equals("user_training_association")) {
            key = ".user_id";
        }

        conditionString = tableName + key +
                " in (select user_id from user_org_association where org_id in " +
                listToString(orgIDList, "(", ")", ",") + " )";

        return conditionString;
    }

    @Override
    public String getWriteControlCondition(String strTableName, Map acParam){
        return getReadControlCondition(strTableName, acParam);
    }
}
