package cn.sportsdata.webapp.youth.dao.asset.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.sportsdata.webapp.youth.common.utils.StringUtil;
import cn.sportsdata.webapp.youth.common.vo.AssetVO;
import cn.sportsdata.webapp.youth.dao.BaseDAO;
import cn.sportsdata.webapp.youth.dao.asset.AssetDAO;

@Repository
public class AssetDAOImpl extends BaseDAO implements AssetDAO {
	private static final String getAssetByID = "getAssetByID";
	private static final String insertAsset = "insertAsset";
	private static final String updateAsset = "updateAsset";
	private static final String replaceAsset = "replaceAsset";
	private static final String deleteAsset = "deleteAsset";
	@Override
	public AssetVO getAssetByID(String id) {
		if(StringUtil.isBlank(id)) {
			return null;
		}
		return sqlSessionTemplate.selectOne(getSqlNameSpace(getAssetByID), id);
	}

	@Override
	public String insertAsset(AssetVO assetVo) {
		long affectedRowNum = sqlSessionTemplate.insert(getSqlNameSpace(insertAsset), assetVo);
		if(affectedRowNum>0) {
			return assetVo.getId();
		}
		return null;
	}

	@Override
	public boolean updateAsset(AssetVO assetVo) {
		long affectedRowNum = sqlSessionTemplate.update(getSqlNameSpace(updateAsset), assetVo);
		return affectedRowNum>0;
	}

	@Override
	public boolean deleteAsset(AssetVO assetVo) {
		long affectedRowNum = sqlSessionTemplate.update(getSqlNameSpace(deleteAsset), assetVo);
		return affectedRowNum>0;
	}

	@Override
	public boolean replaceAsset(AssetVO assetVo) {
		long affectedRowNum = sqlSessionTemplate.update(getSqlNameSpace(replaceAsset), assetVo);
		return affectedRowNum>0;
	}

	@Override
	public String insertHospitalRecordAsset(String hospitalId, String hospitalRecordId, String assetId, String type,
			String typeId) {
		Map<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("hospital_id", hospitalId);
		hashMap.put("hospital_record_id", hospitalRecordId);
		hashMap.put("asset_id", assetId);
		hashMap.put("record_type", type);
		hashMap.put("record_asset_type_id", typeId);
		int num = sqlSessionTemplate.insert(getSqlNameSpace(replaceAsset), hashMap);
		return String.valueOf(num);
	}

}
