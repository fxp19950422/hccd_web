package cn.sportsdata.webapp.youth.service.asset;

import cn.sportsdata.webapp.youth.common.vo.AssetVO;

public interface AssetService {
	AssetVO getAssetByID(String id);
	String insertAsset(AssetVO assetVo);
	boolean updateAsset(AssetVO assetVo);
	boolean deleteAsset(AssetVO assetVo);
	boolean replaceAsset(AssetVO assetVo);
}
