package cn.sportsdata.webapp.youth.common.vo.patient;

import java.io.Serializable;
import java.util.List;

public class HospitalRecordTypeVO implements Serializable {
	private static final long serialVersionUID = 8628757275474528838L;
	private String recordType;
	private String recordTypeName;
	private List<String> sectionList;
	private List<String> sectionNameList;
	private List<RecordAssetTypeVO> assetTypeList;
	private List<RecordAssetStageVO> assetStageList;
	
	public List<RecordAssetStageVO> getAssetStageList() {
		return assetStageList;
	}
	public void setAssetStageList(List<RecordAssetStageVO> assetStageList) {
		this.assetStageList = assetStageList;
	}
	public String getRecordType() {
		return recordType;
	}
	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}
	public List<String> getSectionList() {
		return sectionList;
	}
	public void setSectionList(List<String> sectionList) {
		this.sectionList = sectionList;
	}
	public List<String> getSectionNameList() {
		return sectionNameList;
	}
	public void setSectionNameList(List<String> sectionNameList) {
		this.sectionNameList = sectionNameList;
	}
	public String getRecordTypeName() {
		return recordTypeName;
	}
	public void setRecordTypeName(String recordTypeName) {
		this.recordTypeName = recordTypeName;
	}
	public List<RecordAssetTypeVO> getAssetTypeList() {
		return assetTypeList;
	}
	public void setAssetTypeList(List<RecordAssetTypeVO> assetTypeList) {
		this.assetTypeList = assetTypeList;
	}

}