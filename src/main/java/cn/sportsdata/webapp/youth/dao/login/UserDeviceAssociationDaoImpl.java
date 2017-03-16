package cn.sportsdata.webapp.youth.dao.login;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.sportsdata.webapp.youth.common.vo.login.UserDeviceAssociationVO;
import cn.sportsdata.webapp.youth.dao.BaseDAO;

/**
 * Created by binzhu on 5/12/16.
 */
@Repository
public class UserDeviceAssociationDaoImpl extends BaseDAO implements UserDeviceAssociationDao {

    private static final String SELECT_ALL_DEVICE_FOR_USER = "selectAllDevicesForUser";
    private static final String UPDATE_DEVICE_IDENTIFIER = "updateDeviceIdentifier";
    private static final String DELETE_DEVICE_IDENTIFIER = "deleteDeviceIdentifier";
    private static final String INSERT_DEVICE_IDENTIFIER = "insertDeviceIdentifier";

    public byte getDeviceIdentifierForUser(String userID, String deviceGUID){
        List<UserDeviceAssociationVO> voList = getAllDevicesForUser(userID);
        int len = voList.size();
        for(int i=0; i<len; i++){
            UserDeviceAssociationVO vo = voList.get(i);
            if(deviceGUID.equals(vo.getDeviceGUID())){
                updateDeviceIdentifier(vo);
                return vo.getDeviceIdentifier();
            }
        }

        byte newIdentifier = genNewIdentifier(voList);
        if(newIdentifier < 0){
            return newIdentifier;
        }

        UserDeviceAssociationVO newVO = new UserDeviceAssociationVO();
        newVO.setUserID(userID);
        newVO.setDeviceGUID(deviceGUID);
        newVO.setDeviceIdentifier(newIdentifier);

        long rows = insertDeviceIdentifier(newVO);

        if(rows <= 0){
            return -2;
        }

        return newIdentifier;
    }

    private List<UserDeviceAssociationVO> getAllDevicesForUser(String userID){
        return sqlSessionTemplate.selectList(getSqlNameSpace(SELECT_ALL_DEVICE_FOR_USER), userID);
    }

    private long updateDeviceIdentifier(UserDeviceAssociationVO vo){
        return sqlSessionTemplate.update(getSqlNameSpace(UPDATE_DEVICE_IDENTIFIER), vo);
    }

    private long insertDeviceIdentifier(UserDeviceAssociationVO vo){
        return sqlSessionTemplate.insert(getSqlNameSpace(INSERT_DEVICE_IDENTIFIER), vo);
    }

    private long deleteDeviceIdentifier(UserDeviceAssociationVO vo){
        return sqlSessionTemplate.delete(getSqlNameSpace(DELETE_DEVICE_IDENTIFIER), vo);
    }

    private byte genNewIdentifier(List<UserDeviceAssociationVO> voList){
        int len = voList.size();
        int max_len = 32;
        if (len > max_len){
            return -1;
        }

        int[] indexArray = new int[max_len];

        for(int i=0; i<len; i++){
            int index = voList.get(i).getDeviceIdentifier();
            indexArray[index] = 1;
        }

        for(byte i=0; i<max_len; i++){
            if(indexArray[i] == 0){
                return i;
            }
        }

        return -1;
    }
}
