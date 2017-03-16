package cn.sportsdata.webapp.youth.syncac;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by binzhu on 5/10/16.
 */
@Component("TrainingTaskSyncACPolicyImpl")
public class TrainingTaskSyncACPolicyImpl extends BaseSyncACPolicyImpl{
    @Override
    public String getReadControlCondition(String tableName, Map acParam){
        List<String> orgIDList = (List<String>) acParam.get("orgIDList");
        String userID = (String) acParam.get("userID");

        if (orgIDList == null || orgIDList.size() == 0)
            return null;

        String conditionString = null;
        String key = ".task_id";
        if (tableName.equals("training_task")){
            key = ".id";
        }else if (tableName.equals("training_task_evaluation_ext")) {
            key = ".task_evaluation_id";

            conditionString = tableName + key +
                " in (select id from training_task_evaluation where task_id" +
                " in (select training_task_id from training_training_task_association where " +
                "training_id in ( select id from training where org_id in " +
                listToString(orgIDList, "(", ")", ",") + " )))";
            return conditionString;
        }else if (tableName.equals("training_task_match_association")||
                tableName.equals("training_task_single_training_association") ||
                tableName.equals("training_training_task_association")) {
            key = ".training_task_id";
        }

        conditionString = tableName + key +
                " in (select training_task_id from training_training_task_association where " +
                "training_id in ( select id from training where org_id in " +
                listToString(orgIDList, "(", ")", ",") + " ))";

        return conditionString;
    }

    @Override
    public String getWriteControlCondition(String strTableName, Map acParam){
        return getReadControlCondition(strTableName, acParam);
    }
}
