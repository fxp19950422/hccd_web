package cn.sportsdata.webapp.youth.common.vo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by binzhu on 5/5/16.
 */
public class DBTimeVO implements Serializable {
    private static final long serialVersionUID = -593990550063891148L;
    private String currentDBTime;
    public String getCurrentDBTime(){return currentDBTime;}
    public void setCurrentDBTime(String currentDBTime){this.currentDBTime = currentDBTime;}
    public void setCurrentDBTime(Timestamp currentDBTime){this.currentDBTime = currentDBTime.toString();}
}
