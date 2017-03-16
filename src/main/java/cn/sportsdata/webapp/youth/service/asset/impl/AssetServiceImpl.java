package cn.sportsdata.webapp.youth.service.asset.impl;

import cn.sportsdata.webapp.youth.dao.asset.AssetDAO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.sportsdata.webapp.youth.common.vo.AssetVO;
import cn.sportsdata.webapp.youth.service.asset.AssetService;

@Service
public class AssetServiceImpl implements AssetService {
	private static Logger logger = Logger.getLogger(AssetServiceImpl.class);
	@Autowired
	private AssetDAO assetDAO;
	
	@Override
	public AssetVO getAssetByID(String id) {
		// TODO Auto-generated method stub
		return assetDAO.getAssetByID(id);
	}

	@Override
	public String insertAsset(AssetVO assetVo) {
		// TODO Auto-generated method stub
		return assetDAO.insertAsset(assetVo);
	}

	@Override
	public boolean updateAsset(AssetVO assetVo) {
		// TODO Auto-generated method stub
		return assetDAO.updateAsset(assetVo);
	}

	@Override
	public boolean deleteAsset(AssetVO assetVo) {
		// TODO Auto-generated method stub
		return assetDAO.deleteAsset(assetVo);
	}

	@Override
	public boolean replaceAsset(AssetVO assetVo) {
		// TODO Auto-generated method stub
		return assetDAO.replaceAsset(assetVo);
	}
}
