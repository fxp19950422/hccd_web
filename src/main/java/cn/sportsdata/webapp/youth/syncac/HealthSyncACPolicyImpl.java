package cn.sportsdata.webapp.youth.syncac;

import cn.sportsdata.webapp.youth.syncac.BaseSyncACPolicyImpl;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.List;

/**
 * Created by binzhu on 5/10/16.
 */
@Component("HealthSyncACPolicyImpl")
public class HealthSyncACPolicyImpl extends BaseSyncACPolicyImpl{
    @Override
    public String getReadControlCondition(String strTableName, Map acParam){
        List<String> orgIDList = (List<String>) acParam.get("orgIDList");
        String userID = (String) acParam.get("userID");

        if (orgIDList == null || orgIDList.size() == 0)
            return null;

        String conditionString = null;

        conditionString = strTableName +
                ".user_id in (select user_id from user_org_association where user_org_association.org_id in "
                + listToString(orgIDList, "(", ")", ",") +
                ")";

        return conditionString;
    }

    @Override
    public String getWriteControlCondition(String strTableName, Map acParam){
        return getReadControlCondition(strTableName, acParam);
    }
}
