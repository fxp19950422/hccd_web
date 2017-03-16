package cn.sportsdata.webapp.youth.syncservice;

import cn.sportsdata.webapp.youth.syncdao.BaseSyncDAO;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by binzhu on 5/6/16.
 */

@Service
public class BaseSyncServiceImpl implements BaseSyncService {

    @Autowired
    private BaseSyncDAO tableDAO;

    @Override
    public Object getNewData(JSONObject data, String userID, List<String> orgIDList){

        Iterator iterator = data.keys();
        HashMap allData = new HashMap<String, Object>();

        while (iterator.hasNext()) {

            String tableName = (String) iterator.next();
            String lastUpdate = data.getString(tableName);

            List tableData = (List)tableDAO.getNewRecords(tableName, lastUpdate, userID, orgIDList);
  //          JSONArray jsonArray = new JSONArray(tableData);

            if(tableData != null) {
                allData.put(tableName, tableData);
            }
        }

        return allData;
    }

    @Override
    @Transactional
    public Object updateData(JSONObject data, String currentDBTime, String userID, List<String> orgIDList){
        if (data == null)
            return 0;

        Map<String, Object> allDataMap = new HashMap<String, Object>();
        String allDataJson = null;

        Iterator iterator = data.keys();

        while (iterator.hasNext()) {

            String tableName = (String) iterator.next();
            JSONArray jsonArrayData = data.getJSONArray(tableName);

            Object tableResult = tableDAO.updateRecords(tableName, jsonArrayData, currentDBTime);

            if(tableResult == null){
                throw new RuntimeException();
            }

            allDataMap.put(tableName, tableResult);
        }

        return allDataMap;

    }

    @Override
    @Transactional
    public Object insertData(JSONObject data, String currentDBTime, String userID, List<String> orgIDList){
        if (data == null)
            return null;

        Map<String, Object> allDataMap = new HashMap<String, Object>();
        Iterator iterator = data.keys();

        while (iterator.hasNext()) {

            String tableName = (String) iterator.next();

            JSONArray jsonArrayData = data.getJSONArray(tableName);

            Object insertResult = tableDAO.insertRecords(tableName, jsonArrayData, currentDBTime);

            if(insertResult == null){
                throw new RuntimeException();
            }

            allDataMap.put(tableName, insertResult);
        }

        return allDataMap;
    }

    @Override
    @Transactional
    public Object replaceData(JSONObject data, String currentDBTime, String userID, List<String> orgIDList){
        if (data == null)
            return null;

        Map<String, Object> allDataMap = new HashMap<String, Object>();
        Iterator iterator = data.keys();

        while (iterator.hasNext()) {

            String tableName = (String) iterator.next();
            JSONArray jsonArrayData = data.getJSONArray(tableName);

            Object insertResult = tableDAO.replaceRecords(tableName, jsonArrayData, currentDBTime);

            if(insertResult == null){
                throw new RuntimeException();
            }

            allDataMap.put(tableName, insertResult);
        }

        return allDataMap;
    }

    @Override
    @Transactional
    public Object deleteData(JSONObject data, String currentDBTime, String userID, List<String> orgIDList){
        if(data == null)
            return 0;

        Map<String, Object> allDataMap = new HashMap<String, Object>();

        Iterator iterator = data.keys();

        while (iterator.hasNext()) {

            String tableName = (String) iterator.next();

            JSONArray jsonArrayData = data.getJSONArray(tableName);

            Object deleteResult = tableDAO.deleteRecords(tableName, jsonArrayData, currentDBTime, userID, orgIDList);

//            Object deleteResult = tableDAO.deleteRecords(tableName, jsonArrayData, currentDBTime);

            if(deleteResult == null){
                throw new RuntimeException();
            }

            allDataMap.put(tableName, deleteResult);
        }

        return allDataMap;
    }

    @Override
    public Object filterData(JSONObject data, String userID, List<String> orgIDList){

        if(data == null)
            return 0;

        Iterator iterator = data.keys();
        while (iterator.hasNext()) {

            String tableName = (String) iterator.next();
            JSONArray jsonArrayData = data.getJSONArray(tableName);

            Object unsupportedData = tableDAO.filterData(tableName, jsonArrayData, userID, orgIDList);
            if (unsupportedData != null){
                return unsupportedData;
            }
        }
        return null;
    }
}
