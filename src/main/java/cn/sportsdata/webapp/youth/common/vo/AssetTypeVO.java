package cn.sportsdata.webapp.youth.common.vo;

import java.util.List;

public class AssetTypeVO {
	String assetTypeName;
	List<AssetVO> assets;
	public String getAssetTypeName() {
		return assetTypeName;
	}
	public void setAssetTypeName(String assetTypeName) {
		this.assetTypeName = assetTypeName;
	}
	public List<AssetVO> getAssets() {
		return assets;
	}
	public void setAssets(List<AssetVO> assets) {
		this.assets = assets;
	}
	
	
}
