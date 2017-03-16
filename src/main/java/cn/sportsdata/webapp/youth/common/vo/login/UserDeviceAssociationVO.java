package cn.sportsdata.webapp.youth.common.vo.login;

import cn.sportsdata.webapp.youth.common.vo.BaseVO;

import java.io.Serializable;

/**
 * Created by binzhu on 5/12/16.
 */
public class UserDeviceAssociationVO extends BaseVO implements Serializable {
    private static final long serialVersionUID = 7922477308103836118L;
    private String id;
    private String userID;
    private String deviceGUID;
    private byte deviceIdentifier;
    private int loginCount;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getUserID(){return userID;}
    public void setUserID(String userID){this.userID = userID;}

    public String getDeviceGUID(){return deviceGUID;}
    public void setDeviceGUID(String deviceGUID){this.deviceGUID = deviceGUID;}

    public byte getDeviceIdentifier(){return deviceIdentifier;}
    public void setDeviceIdentifier(byte deviceIdentifier){this.deviceIdentifier = deviceIdentifier;}

    public int getLoginCount(){return loginCount;}
    public void setLoginCount(int loginCount){this.loginCount = loginCount;}
}
