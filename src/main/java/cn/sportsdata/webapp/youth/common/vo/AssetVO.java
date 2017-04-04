package cn.sportsdata.webapp.youth.common.vo;

import java.io.Serializable;

import cn.sportsdata.webapp.youth.common.utils.ConfigProps;

public class AssetVO extends BaseVO implements Serializable {
	public static String PRIVACY_private = "private";
	public static String PRIVACY_public = "public";
	public static String PRIVACY_enlisted = "enlisted";
	public static String PRIVACY_protected = "protected";
	
	public static String TYPE_audio = "audio";
	public static String TYPE_video = "video";
	public static String TYPE_png = "png";
	public static String TYPE_txt = "txt";
	public static String TYPE_general = "general";
	/**
	 * 
	 */
	private static final long serialVersionUID = -5204378795852630408L;
	private String id;
	private String org_id;
	private String display_name;
	private String storage_name;
	private long size;
	private String file_extension;
	private String privacy;
	private String creator_id;
	private String assetTypeName;
	
	
	public String getAssetTypeName() {
		return assetTypeName;
	}
	public void setAssetTypeName(String assetTypeName) {
		this.assetTypeName = assetTypeName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrg_id() {
		return org_id;
	}
	public void setOrg_id(String org_id) {
		this.org_id = org_id;
	}
	public String getDisplay_name() {
		return display_name;
	}
	public void setDisplay_name(String display_name) {
		this.display_name = display_name;
	}
	public String getStorage_name() {
		return storage_name;
	}
	public void setStorage_name(String storage_name) {
		this.storage_name = storage_name;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public String getFile_extension() {
		return file_extension;
	}
	public void setFile_extension(String file_extension) {
		this.file_extension = file_extension;
	}
	public String getPrivacy() {
		return privacy;
	}
	public void setPrivacy(String privacy) {
		this.privacy = privacy;
	}
	public String getCreator_id() {
		return creator_id;
	}
	public void setCreator_id(String creator_id) {
		this.creator_id = creator_id;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
//	public String getAbsFileName() {
//		try {
//			String uploadRootPath = ConfigProps.getInstance().getConfigValue("form.attachment.folder.rootpath");
//			return uploadRootPath +"/"+this.storage_path+"/"+this.file_name;
//		}
//		catch(Exception e){
//			return null;
//		}
//	}
//	
	public boolean isNew() {
		return isNew;
	}
	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}

	private boolean isNew = true;

}
