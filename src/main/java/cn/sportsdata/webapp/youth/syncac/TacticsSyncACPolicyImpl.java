package cn.sportsdata.webapp.youth.syncac;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by binzhu on 5/10/16.
 */
@Component("TacticsSyncACPolicyImpl")
public class TacticsSyncACPolicyImpl extends BaseSyncACPolicyImpl{
    @Override
    public String getReadControlCondition(String tableName, Map acParam){
        List<String> orgIDList = (List<String>) acParam.get("orgIDList");
        String userID = (String) acParam.get("userID");

        if (orgIDList == null || orgIDList.size() == 0)
            return null;

        String conditionString = null;
        if (tableName.equals("tactics")){

            conditionString = "tactics.org_id in "
                    + listToString(orgIDList, "(", ")", ",");

            return conditionString;
        }

        String key1 = ".tactics_id";
        String key2 = "id";
        if (tableName.equals("tactics_type")){
            key1 = ".id";
            conditionString = " tactics_type.org_id is NULL or tactics_type.org_id in " +
                listToString(orgIDList, "(", ")", ",");
            return conditionString;
        }

        if (tableName.equals("tactics_playground")){
            key1 = ".id";
            key2 = "playground_id";
        }


        conditionString = tableName + key1 +
                " in (select " + key2 + " from tactics where org_id in "
                + listToString(orgIDList, "(", ")", ",") +
                ")";

        return conditionString;
    }

    @Override
    public String getWriteControlCondition(String strTableName, Map acParam){
        return getReadControlCondition(strTableName, acParam);
    }
}
