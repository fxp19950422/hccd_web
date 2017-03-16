package cn.sportsdata.webapp.youth.common.vo;

import java.sql.Timestamp;

/**
 * Created by binzhu on 4/27/16.
 */
public class BaseVO {
    private String created_time;
    private String last_update;
    private String status = "active";

    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    public String getLast_update() {
        return last_update;
    }

    public void setLast_update(String last_update) {
        this.last_update = last_update;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
