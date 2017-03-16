package cn.sportsdata.webapp.youth.syncservice;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;
import java.util.List;

/**
 * Created by binzhu on 5/4/16.
 */
public interface BaseSyncService {
    /**
     * Get the data modified or inserted after the given time lastUpdate
     * @param data: indicate the latest update time of local time, which means to get the data after the time
     * @param userID: the user_id of current user, which is used to determine the data to get
     * @param orgIDList: the lisf of org_ids of current user, which is used to determine the data to get
     * @return: the list of DataVO, such as EquipmentVO
     */
    public Object getNewData(JSONObject data, String userID, List<String> orgIDList);

    /**
     * Find the data that current have no privilege to update or delete
     * @param data: the data need to update or delete
     * @param userID: the user_id of current user, which is used to determine the data to get
     * @param orgIDList: the lisf of org_ids of current user, which is used to determine the data to get
     * @return: the list of DataVO that current cannot modify or delete
     */
    public Object filterData(JSONObject data, String userID, List<String> orgIDList);

    /**
     * Logically delete the given data by setting the status field to 'deleted'
     * @param data: the data need to delete
     * @param currentDBTime: current time of Database, which is used to set the last_update field of the data
     * @return: the rows of record deleted.
     */
    public Object deleteData(JSONObject data, String currentDBTime, String userID, List<String> orgIDList);

    /**
     * Insert data to Database
     * @param dataList: the data need to insert
     * @param currentDBTime: current time of Database, which is used to set the last_update field of the data
     * @return: the list of inserted data
     */
    public Object insertData(JSONObject dataList, String currentDBTime, String userID, List<String> orgIDList);

    /**
     * Update data
     * @param data: the data need to update
     * @param currentDBTime: current time of Database, which is used to set the last_update field of the data
     * @return: the rows of record updated
     */
    public Object updateData(JSONObject data, String currentDBTime, String userID, List<String> orgIDList);

    /**
     * Replace data
     * @param data: the data need to update
     * @param currentDBTime: current time of Database, which is used to set the last_update field of the data
     * @return: the rows of record updated
     */
    public Object replaceData(JSONObject data, String currentDBTime, String userID, List<String> orgIDList);

}
