package cn.sportsdata.webapp.youth.syncac;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by binzhu on 5/10/16.
 */
@Component("AssetSyncACPolicyImpl")
public class AssetSyncACPolicyImpl extends BaseSyncACPolicyImpl{
    @Override
    public String getReadControlCondition(String tableName, Map acParam){
        List orgIDList = (List<String>) acParam.get("orgIDList");
        String userID = (String) acParam.get("userID");

        if (orgIDList == null || orgIDList.size() == 0)
            return null;

        String conditionString = null;

        conditionString = "(" + tableName + ".creator_id='"+userID + "' or (" +
                tableName + ".privacy != 'private' and " + tableName +
                ".org_id in "
                + listToString(orgIDList, "(", ")", ",") +
                "))";

        return conditionString;
    }

    @Override
    public String getWriteControlCondition(String strTableName, Map acParam){
        return getReadControlCondition(strTableName, acParam);
    }
}
