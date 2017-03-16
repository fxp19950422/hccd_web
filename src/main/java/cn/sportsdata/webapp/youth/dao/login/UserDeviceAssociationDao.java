package cn.sportsdata.webapp.youth.dao.login;

/**
 * Created by binzhu on 5/12/16.
 */
public interface UserDeviceAssociationDao {
    /**
     * Get the device identifier for given user_id and device GUID.
     * It will search database with the given two parameters. If find the identifier, just return it.
     * If not find the identifier, it will check whether the number of the user's device is bigger than 32.
     * If yes, return -1 as failure. If not, it will choose a number in range [0,32) that not be used yet and
     * return it after saving it to data.
     * @param userID
     * @param deviceGUID
     * @return <0 if failed to get device identifier; >0 for success
     */
    byte getDeviceIdentifierForUser(String userID, String deviceGUID);
}
