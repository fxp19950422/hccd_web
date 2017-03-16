package cn.sportsdata.webapp.youth.syncac;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by binzhu on 5/11/16.
 */
@Component("PlaceKickSyncACPolicyImpl")
public class PlaceKickSyncACPolicyImpl extends BaseSyncACPolicyImpl{
    @Override
    public String getReadControlCondition(String tableName, Map acParam){
        List<String> orgIDList = (List<String>) acParam.get("orgIDList");
        String userID = (String) acParam.get("userID");

        if (orgIDList == null || orgIDList.size() == 0)
            return null;

        String conditionString = null;
        if (tableName.equals("place_kick_type")){

            conditionString = "place_kick_type.org_id in "
                    + listToString(orgIDList, "(", ")", ",");

            return conditionString;
        }

        conditionString = tableName +
                ".place_kick_type_id in (select id from place_kick_type where org_id in "
                + listToString(orgIDList, "(", ")", ",") +
                ")";

        return conditionString;
    }

    @Override
    public String getWriteControlCondition(String strTableName, Map acParam){
        return getReadControlCondition(strTableName, acParam);
    }
}
