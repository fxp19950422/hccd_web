package cn.sportsdata.webapp.youth.syncdao;

import cn.sportsdata.webapp.youth.common.utils.SpringUtils;
import cn.sportsdata.webapp.youth.dao.BaseDAO;
import cn.sportsdata.webapp.youth.syncac.BaseSyncACPolicy;
import cn.sportsdata.webapp.youth.web.controller.api.SyncAPIController;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.apache.commons.lang.StringEscapeUtils;
import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.impl.StdScheduler;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.io.*;
import java.sql.Timestamp;
import java.util.*;

@Repository
public class BaseSyncDAOImpl extends BaseDAO implements BaseSyncDAO {

    private HashMap<String, BaseSyncACPolicy> tableACPMapping = null;
    private HashMap<String, List<String>> tablePrimarykeyMapping = null;
    private List<String> tableNameList = null;
    private boolean bInitialized = false;
    private int MAX_SQL_STATE_ROWS = 20;

    private static Logger logger = Logger.getLogger(BaseSyncDAOImpl.class);

//    @Resource
//    private StdScheduler scheduler;

    public List getPrimaryKeyList(String tableName){
        if (!bInitialized){
            init();
        }

        if (tablePrimarykeyMapping == null){
            return null;
        }
        return tablePrimarykeyMapping.get(tableName);
    }

    public BaseSyncACPolicy getAcPolicy(String tableName) {
        if (!bInitialized){
            init();
        }

        if (tableACPMapping == null){
            return null;
        }
        return tableACPMapping.get(tableName);
    }

    public List<String> getTableNameList() {
        if (!bInitialized){
            init();
        }

        return tableNameList;
    }


    protected static final String INSERT_RECORDS =  "insertRecords";
    protected static final String UPDATE_RECORDS =  "updateRecords";
    protected static final String SELECT_NEW_RECORDS =  "getNewRecords";
    protected static final String DELETE_RECORDS =  "deleteRecords";
    protected static final String REPLACE_RECORDS =  "replaceRecords";
    protected static final String SELECT_COLUMNS_UNDER_CONDITION =  "getColumnsUnderCondition";

//    protected static Logger logger = Logger.getLogger(BaseSyncDAOImpl.class);
    @Override
    protected String getSqlNameSpace(String sqlName) {
        return "cn.sportsdata.webapp.youth.syncdao.SingleTableSync."+sqlName;
    }

    @Override
    public Object getNewRecords(String tableName, String lastUpdate, String userID, List<String> orgIDList){

        if(!isTableExisted(tableName)){
            return null;
        }

        Map<String, Object> mapParam = new HashMap<String, Object>();

        lastUpdate = StringEscapeUtils.escapeSql(lastUpdate);

        String condition = getReadControlCondition(tableName, userID, orgIDList);

        String sqlState = null;


        if(lastUpdate.isEmpty() || lastUpdate.equals("0")){
            sqlState = "SELECT " +"* FROM `" + tableName + "` where "  +
                    "`status` != 'deleted'";
        }else {
            sqlState = "SELECT " +"* FROM `" + tableName + "` where "  +
                    "`last_update` > '" + lastUpdate + "'";
        }

        if (condition != null && !condition.isEmpty()){
            sqlState = sqlState + " AND " + condition;
        }

        mapParam.put("sqlState", sqlState);

        List listResult = sqlSessionTemplate.selectList(getSqlNameSpace(SELECT_NEW_RECORDS), mapParam);

        return trimData(listResult);
    }

    @Override
    public Object insertRecords(String tableName, JSONArray dataList, String currentDBTime) {

        if(!isTableExisted(tableName)){
            return null;
        }
        int dataListLen = dataList.length();
        int offset = 0;
        while(offset < dataListLen) {
            int rows = MAX_SQL_STATE_ROWS;
            String sqlState = genSqlStateForReplaceAndInsert(tableName, dataList, currentDBTime, true, offset, rows);
            if(sqlState == null || sqlState.isEmpty()){
                break;
            }
            HashMap<String, String> dataMap = new HashMap<String, String>();
            dataMap.put("sqlState", sqlState);
            int ret = sqlSessionTemplate.insert(getSqlNameSpace(INSERT_RECORDS), dataMap);
            offset += rows;
        }
        return dataListLen;
    }

    @Override
    public Object updateRecords(String tableName, JSONArray dataList, String currentDBTime){

        if(!isTableExisted(tableName)){
            return null;
        }
        int dataListLen = dataList.length();
        int offset = 0;
        while (offset < dataListLen) {
            int rows = MAX_SQL_STATE_ROWS;
            String sqlState = genSqlStateForUpdateAndDelete(tableName, dataList, currentDBTime, true, offset, rows);
            if(sqlState == null || sqlState.isEmpty()){
                break;
            }
            HashMap<String, String> dataMap = new HashMap<String, String>();
            dataMap.put("sqlState", sqlState);

            int ret = sqlSessionTemplate.update(getSqlNameSpace(UPDATE_RECORDS), dataMap);
            offset += rows;
        }
        return dataListLen;
    }

    @Override
    public Object replaceRecords(String tableName, JSONArray dataList, String currentDBTime){

        if(!isTableExisted(tableName)){
            return null;
        }

        int dataListLen = dataList.length();
        int offset = 0;
        while (offset < dataListLen) {
            int rows = MAX_SQL_STATE_ROWS;
            String sqlState = genSqlStateForReplaceAndInsert(tableName, dataList, currentDBTime, false, offset, rows);
            if(sqlState == null || sqlState.isEmpty()){
                break;
            }
            HashMap<String, String> dataMap = new HashMap<String, String>();
            dataMap.put("sqlState", sqlState);

            int ret = sqlSessionTemplate.update(getSqlNameSpace(REPLACE_RECORDS), dataMap);

            offset += rows;
        }

        if (tableName.equals("match_data_log")){
            noticeUpdateMatchData(dataList);
        }

        return dataListLen;
    }

    @Override
    public Object deleteRecords(String tableName, JSONArray dataList, String currentDBTime){

        if(!isTableExisted(tableName)){
            return null;
        }

        int dataListLen = dataList.length();
        int offset = 0;
        while (offset < dataListLen) {
            int rows = MAX_SQL_STATE_ROWS;
            String sqlState = genSqlStateForUpdateAndDelete(tableName, dataList, currentDBTime, false, offset ,rows);
            if(sqlState == null || sqlState.isEmpty()){
                break;
            }
            HashMap<String, String> dataMap = new HashMap<String, String>();
            dataMap.put("sqlState", sqlState);
            int ret = sqlSessionTemplate.delete(getSqlNameSpace(DELETE_RECORDS), dataMap);
            offset += rows;
        }

        return dataListLen;
    }

    @Override
    public Object deleteRecords(String tableName, JSONArray dataList, String currentDBTime, String userID,
                                List<String> orgIDList) {
        if (!isTableExisted(tableName)) {
            return null;
        }

        List<String> primaryKeys = getPrimaryKeyList(tableName);
        String primaryTuple = convertListToString(primaryKeys);
        String primaryKeyValues = getPrimaryKeyValuesFromData(tableName, dataList);
        String equal = "=";
        if (dataList.length() > 1) {
            equal = " in ";
        }

        String condition = primaryTuple + equal + primaryKeyValues;

        String accessCondition = getWriteControlCondition(tableName, userID, orgIDList);

        if (accessCondition != null) {
            condition = condition + " and (" + accessCondition + ")";
        }

        StringBuilder sqlState = new StringBuilder("UPDATE `" + tableName + "` SET `status`='deleted', `last_update`= '" + currentDBTime + "' ");

        if (condition != null && !condition.isEmpty()) {
            sqlState.append(" WHERE " + condition + ";");
        }

        Map<String, Object> mapParam = new HashMap<String, Object>();
        mapParam.put("sqlState", sqlState.toString());

        int rows = sqlSessionTemplate.delete(getSqlNameSpace(DELETE_RECORDS), mapParam);

        return rows;
    }

    @Override
    public Object filterData(String tableName, JSONArray dataList, String userID, List<String> orgIDList){

        if(!isTableExisted(tableName)){
            return dataList;
        }

        List<String> primaryKeys = getPrimaryKeyList(tableName);
        String primaryTuple = convertListToString(primaryKeys);
        String primaryKeyValues = getPrimaryKeyValuesFromData(tableName, dataList);
        String equal = "=";
        if (dataList.length() > 1){
            equal = " in ";
        }

        String condition = primaryTuple + equal + primaryKeyValues;

        String accessCondition = getWriteControlCondition(tableName, userID, orgIDList);

        if (accessCondition != null){
            condition = condition + " and " + accessCondition;
        }

        StringBuilder sqlState = new StringBuilder("SELECT ");

        String separator = "";
        for (String key: primaryKeys){
            sqlState.append(separator+"`"+key+"`");
            separator = ",";
        }

        sqlState.append(" FROM `"+tableName+"` ");

        if(condition !=null && !condition.isEmpty()){
            sqlState.append(" WHERE "+condition+";");
        }

        Map<String, Object> mapParam = new HashMap<String, Object>();
        mapParam.put("sqlState", sqlState.toString());

        List<Map> results = sqlSessionTemplate.selectList(getSqlNameSpace(SELECT_COLUMNS_UNDER_CONDITION), mapParam);

        if (dataList.length() != results.size()){
            return dataList;
        }

        return null;
    }

    protected String getReadControlCondition(String tableName, String userID, List<String> orgIDList){
        BaseSyncACPolicy acPolicy = getAcPolicy(tableName);
        if (acPolicy == null){
            return null;
        }

        Map<String, Object> acParam = new HashMap<>();
        acParam.put("orgIDList", orgIDList);
        acParam.put("userID", userID);

        return acPolicy.getReadControlCondition(tableName, acParam);

    }

    protected String getWriteControlCondition(String tableName, String userID, List<String> orgIDList){
        BaseSyncACPolicy acPolicy = getAcPolicy(tableName);
        if (acPolicy == null){
            return null;
        }

        Map<String, Object> acParam = new HashMap<>();
        acParam.put("orgIDList", orgIDList);
        acParam.put("userID", userID);

        return acPolicy.getWriteControlCondition(tableName, acParam);
    }

    protected String convertListToString(List<String> primaryKeys){
        String primaryKeytuple = "";

        String open = "";
        String close = "";
        String separator = "";

        if (primaryKeys.size() > 1){
            open = "(";
            close = ")";
        }

        primaryKeytuple = open;
        for(int j=0; j<primaryKeys.size(); j++){
            primaryKeytuple = primaryKeytuple + separator;
            separator = ",";
            primaryKeytuple = primaryKeytuple + primaryKeys.get(j);
        }

        primaryKeytuple = primaryKeytuple + close;

        return primaryKeytuple;
    }

    protected String getPrimaryKeyValuesFromData(String tableName, JSONArray dataList){
        List<String> primaryKeys = getPrimaryKeyList(tableName);

        StringBuilder finalString = new StringBuilder();
        String open1 = "";
        String close1 = "";
        String separator1 = "";
        String open2 = "";
        String close2 = "";
        String separator2 = "";

        if(dataList.length() > 1) {
            open1 = "(";
            close1 = ")";
        }

        if (primaryKeys.size() > 1){
            open2 = "(";
            close2 = ")";
        }

        finalString.append(open1);
        for(int i=0; i<dataList.length(); i++){
            finalString.append(separator1);
            separator1 = ",";

            separator2 = "";

            JSONObject jsonObject = dataList.getJSONObject(i);
            finalString.append(open2);
            for(int j=0; j<primaryKeys.size(); j++){
                finalString.append(separator2);
                separator2 = ",";
                finalString.append("'"+StringEscapeUtils.escapeSql(jsonObject.getString(primaryKeys.get(j)))+"'");
            }

            finalString.append(close2);
        }
        finalString.append(close1);
        return finalString.toString();
    }

    private void init(){

        if (bInitialized){
            return;
        }
        tableACPMapping = new HashMap<String, BaseSyncACPolicy>();
        tablePrimarykeyMapping = new HashMap<String, List<String>>();
        tableNameList = new ArrayList<String>();

        InputStream is = BaseSyncDAOImpl.class.getResourceAsStream("/tabledao.json");

        String JsonContext = getStringFromInputStream(is);
        JSONObject jsonAllTableInfo = new JSONObject(JsonContext);
        int size = jsonAllTableInfo.length();

        Iterator it = jsonAllTableInfo.keys();
        while(it.hasNext()){
            String tableName = (String) it.next();

            JSONObject tableInfo = jsonAllTableInfo.getJSONObject(tableName);
            JSONArray array = tableInfo.getJSONArray("primary_key");
            List<String> primaryKeys = new ArrayList<String>();
            for(int i=0; i<array.length();i++){
                primaryKeys.add(array.getString(i));
            }

            tablePrimarykeyMapping.put(tableName, primaryKeys);

            String acPolicyName = tableInfo.getString("ac_policy");
            BaseSyncACPolicy baseSyncACPolicy = null;
            if (!acPolicyName.equals(null) && !acPolicyName.equals("")){
                baseSyncACPolicy  = (BaseSyncACPolicy) SpringUtils.getBean(acPolicyName);
            }
            tableACPMapping.put(tableName, baseSyncACPolicy);

            tableNameList.add(tableName);
        }

        bInitialized = true;

        return;

    }

    private  String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();

    }

    protected String genSqlStateForReplaceAndInsert(String tableName, JSONArray dataList, String currentDBTime, boolean insert, int offset, int rows) {

        int dataListLen = dataList.length();
        if (offset >=  dataListLen || offset < 0){
            return null;
        }

        String action = "REPLACE";
        if(insert){
            action = "INSERT";
        }

        tableName = "`"+tableName+"`";
        action = action + " INTO " + tableName + " (";

        StringBuilder sqlState = new StringBuilder();
//        StringBuilder subSqlState = new StringBuilder();
        StringBuilder valueString = new StringBuilder();

        for (int i=offset; i<dataListLen; i++) {
//            subSqlState.delete(0, subSqlState.length());
            valueString.delete(0, valueString.length());

            sqlState.append(action);

            valueString.append("(");
            String separator = "";

            JSONObject jsonObject = (JSONObject) dataList.get(i);
            HashMap<String, String> dataMap = new HashMap<String, String>();
            Iterator it = jsonObject.keys();
            while (it.hasNext()) {
                String key = (String) it.next();
                sqlState.append(separator + "`"+key+"`");

                if (isNULL(jsonObject.get(key))){
                    valueString.append(separator + "NULL");
                }else {
                    String value = jsonObject.getString(key);

                    if(key.equals("created_time") || key.equals("last_update")){
                        value = currentDBTime;
                    }

                    valueString.append(separator + "'" + StringEscapeUtils.escapeSql(value) + "'");
                }

                separator = ", ";
            }

            sqlState.append(") values");
            valueString.append(");\n");

            sqlState.append(valueString);

            rows -= 1;

            if (rows <= 0){
                break;
            }

        }

        return sqlState.toString();
    }

    protected String genSqlStateForUpdateAndDelete(String tableName, JSONArray dataList, String currentDBTime, boolean update, int offset, int rows) {

        int dataListLen = dataList.length();
        if (offset >=  dataListLen || offset < 0){
            return null;
        }


        List<String> primaryKeys = getPrimaryKeyList(tableName);
        tableName = "`"+tableName+"`";

        StringBuilder sqlState = new StringBuilder();
        StringBuilder conditionString = new StringBuilder();

        for (int i=offset; i<dataListLen; i++) {
            sqlState.append(" UPDATE " + tableName + " SET ");
            conditionString.delete(0, conditionString.length());

            String separatorForValue = "";
            String separatorForCondition = "";

            JSONObject jsonObject = (JSONObject) dataList.get(i);
            HashMap<String, String> dataMap = new HashMap<String, String>();
            Iterator it = jsonObject.keys();
            while (it.hasNext()) {
                String key = (String) it.next();
                if (primaryKeys.contains(key)){
                    if (isNULL(jsonObject.get(key))) {
                        conditionString.append(separatorForCondition + "`" + key + "`=NULL");
                    }else{
                        String value = jsonObject.getString(key);
                        conditionString.append(separatorForCondition + "`"+key+"`='" + value + "'");
                    }
                    separatorForCondition = " AND ";
                }else{
                    if(update) {
                        if (isNULL(jsonObject.get(key))) {
                            sqlState.append(separatorForValue + "`" + key + "`=NULL");
                        } else {
                            String value = jsonObject.getString(key);
                            if(key.equals("created_time") || key.equals("last_update")){
                                value = currentDBTime;
                            }
                            sqlState.append(separatorForValue + "`" + key + "`='" + value + "'");
                        }
                        separatorForValue = ",";
                    }
                }
            }

            if (!update){
                sqlState.append(" status='deleted' ");
            }

            if(conditionString.length() > 0){
                sqlState.append(" where ");
                sqlState.append(conditionString);
            }

            sqlState.append(";\n");

            rows -= 1;

            if (rows <= 0) {
                break;
            }
        }

        return sqlState.toString();
    }

    protected List trimData(List listResult){

        if(listResult == null){
            return null;
        }
        for (int i=0; i< listResult.size(); i++){
            Map row = (Map)listResult.get(i);
            for(Object key : row.keySet()) {
                Object obj = row.get(key);
                if(obj == null){
//                    row.put(key, "null");
                    continue;
                }
                if (obj instanceof Timestamp) {
                    row.put(key, obj.toString());
                }
            }
        }

        return listResult;
    }

    private  boolean isNULL(Object value){

        if (JSONObject.NULL.equals(value)){
            return true;
        }

        return false;
    }

    private boolean isTableExisted(String tableName){
        if(getTableNameList().contains(tableName)){
            return true;
        }

        logger.debug("No table named " + tableName + " in config file tabledao.jons!");
        return false;
    }

    private void noticeUpdateMatchData( JSONArray dataList){
        Set<String> matchIDList = new HashSet<>();;

        for (int i=0; i<dataList.length(); i++) {

            JSONObject jsonObject = (JSONObject) dataList.get(i);
            String matchID = jsonObject.optString("match_id", null);
            if (matchID != null) {
                matchIDList.add(matchID);
            }
        }

//        try {
//            JobDataMap map = new JobDataMap();
//            map.put("matchIdList",  matchIDList.toArray());
//            scheduler.triggerJob(new JobKey("jobDetail"), map);
//        } catch (SchedulerException e) {
//            e.printStackTrace();
//        }

    }

    public Object deleteData(String tableName, JSONArray dataList, String userID, List<String> orgIDList){

        if(!isTableExisted(tableName)){
            return null;
        }

        List<String> primaryKeys = getPrimaryKeyList(tableName);
        String primaryTuple = convertListToString(primaryKeys);
        String primaryKeyValues = getPrimaryKeyValuesFromData(tableName, dataList);
        String equal = "=";
        if (dataList.length() > 1){
            equal = " in ";
        }

        String condition = primaryTuple + equal + primaryKeyValues;

        String accessCondition = getWriteControlCondition(tableName, userID, orgIDList);

        if (accessCondition != null){
            condition = condition + " and " + accessCondition;
        }

        StringBuilder sqlState = new StringBuilder("UPDATE ");

        String separator = "";
        for (String key: primaryKeys){
            sqlState.append(separator+"`"+key+"`");
            separator = ",";
        }

        sqlState.append(" `"+tableName+"` SET `status`='deleted' ");

        if(condition !=null && !condition.isEmpty()){
            sqlState.append(" WHERE "+condition+";");
        }

        Map<String, Object> mapParam = new HashMap<String, Object>();
        mapParam.put("sqlState", sqlState.toString());

        int rows = sqlSessionTemplate.delete(getSqlNameSpace(DELETE_RECORDS), mapParam);

        return rows;
    }
}
