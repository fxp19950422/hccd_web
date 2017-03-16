package cn.sportsdata.webapp.youth.syncac;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by binzhu on 5/10/16.
 */
@Component("MatchSyncACPolicyImpl")
public class MatchSyncACPolicyImpl extends BaseSyncACPolicyImpl{
    @Override
    public String getReadControlCondition(String tableName, Map acParam){
        List<String> orgIDList = (List<String>) acParam.get("orgIDList");
        String String = (String) acParam.get("userID");

        if (orgIDList == null || orgIDList.size() == 0)
            return null;

        String conditionString = null;
        if (tableName.equals("match")){
            conditionString = "`match`.org_id in "
                    + listToString(orgIDList, "(", ")", ",");

            return conditionString;
        }

        conditionString = tableName +
                ".match_id in (select id from `match` where org_id in "
                + listToString(orgIDList, "(", ")", ",") +
                ")";

        return conditionString;
    }

    @Override
    public String getWriteControlCondition(String strTableName, Map acParam){
        return getReadControlCondition(strTableName, acParam);
    }
}
