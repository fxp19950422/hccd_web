package cn.sportsdata.webapp.youth.syncac;

import java.util.List;
import java.util.Map;

/**
 * Created by binzhu on 5/10/16.
 */
public class BaseSyncACPolicyImpl implements BaseSyncACPolicy {
    @Override
    public String getReadControlCondition(String strTableName, Map acParam){
        return null;
    }

    @Override
    public String getWriteControlCondition(String strTableName, Map acParam){
        return null;
    }

    protected String listToString(List listInput, String open, String close, String strSeparator){

        String separator = "";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(open);
        for(int i=0; i<listInput.size(); i++){
            stringBuilder.append(separator);
            separator = strSeparator;
            stringBuilder.append("'"+listInput.get(i)+"'");
        }
        stringBuilder.append(close);
        return stringBuilder.toString();
    }
}
