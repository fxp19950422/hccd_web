package cn.sportsdata.webapp.youth.syncdao;

import org.json.JSONArray;

import java.util.List;
import java.util.Map;

/**
 * Created by binzhu on 5/6/16.
 */
public interface BaseSyncDAO {
    Object getNewRecords(String tableName, String lastUpdate, String userID, List<String> orgIDList);

    Object insertRecords(String tableName, JSONArray dataList, String currentDBTime);

    Object updateRecords(String tableName, JSONArray dataList, String currentDBTime);

    Object deleteRecords(String tableName, JSONArray dataList, String currentDBTime);

    Object deleteRecords(String tableName, JSONArray dataList, String currentDBTime, String userID,
                                List<String> orgIDList);

    Object replaceRecords(String tableName, JSONArray dataList, String currentDBTime);

    Object filterData(String tableName, JSONArray dataList, String userID, List<String> orgIDList);
}
