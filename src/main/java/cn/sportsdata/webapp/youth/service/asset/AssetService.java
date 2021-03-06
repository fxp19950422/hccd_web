package cn.sportsdata.webapp.youth.service.asset;

import cn.sportsdata.webapp.youth.common.vo.AssetVO;

public interface AssetService {
	AssetVO getAssetByID(String id);
	String insertAsset(AssetVO assetVo, String hospitalId, String recordId, String type, String recordTypeId, String stageTypeId);
	boolean updateAsset(AssetVO assetVo);
	boolean deleteAsset(AssetVO assetVo);
	boolean replaceAsset(AssetVO assetVo);
	boolean deleteHospitalAsset(String hostpitalAssetId);
	String insertHospitalRecordOSSAsset(String filename, String hospitalId, String recordId, 
			String type, String recordTypeId, String stageTypdId, String createdTime, String index, String storageType);
}
