package cn.sportsdata.webapp.youth.dao.asset;

import cn.sportsdata.webapp.youth.common.vo.AssetVO;

public interface AssetDAO {
    AssetVO getAssetByID(String id);
    String insertAsset(AssetVO assetVo);
    boolean updateAsset(AssetVO assetVo);
    boolean deleteAsset(AssetVO assetVo);
    boolean replaceAsset(AssetVO assetVo);
    
    String insertHospitalRecordAsset(String hospitalId, String hospitalRecordId, 
    		String assetId, String type, String typeId, String stageTypdId, String storageType);
    
    boolean deleteHospitalAsset(String hospitalAssetId);
}
