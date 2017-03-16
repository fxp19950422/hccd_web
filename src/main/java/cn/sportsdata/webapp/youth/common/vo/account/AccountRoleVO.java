package cn.sportsdata.webapp.youth.common.vo.account;
import java.io.Serializable;

import cn.sportsdata.webapp.youth.common.vo.BaseVO;
/**
 * Created by binzhu on 4/27/16.
 */


public class AccountRoleVO extends BaseVO implements Serializable {
    private static final long serialVersionUID = 7922477308103836118L;

    private long id;
    private String accout_role;

    public String getAccout_role() {
        return accout_role;
    }

    public void setAccout_role(String accout_role) {
        this.accout_role = accout_role;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
