package cn.sportsdata.webapp.youth.syncac;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by binzhu on 5/10/16.
 */
@Component("TrainingSyncACPolicyImpl")
public class TrainingSyncACPolicyImpl extends BaseSyncACPolicyImpl{
    @Override
    public String getReadControlCondition(String tableName, Map acParam){
        List<String> orgIDList = (List<String>) acParam.get("orgIDList");
        String userID = (String) acParam.get("userID");

        if (orgIDList == null || orgIDList.size() == 0)
            return null;

        String conditionString = null;
        if (tableName.equals("training")){

            conditionString = "training.org_id in "
                    + listToString(orgIDList, "(", ")", ",");

            return conditionString;
        }

        conditionString = tableName +
                ".training_id in (select id from training where org_id in "
                + listToString(orgIDList, "(", ")", ",") +
                ")";

        return conditionString;
    }

    @Override
    public String getWriteControlCondition(String strTableName, Map acParam){
        return getReadControlCondition(strTableName, acParam);
    }
}
