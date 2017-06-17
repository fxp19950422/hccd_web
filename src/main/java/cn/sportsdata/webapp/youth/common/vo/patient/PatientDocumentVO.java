package cn.sportsdata.webapp.youth.common.vo.patient;

import java.util.List;

import cn.sportsdata.webapp.youth.common.exceptions.SoccerProException;
import cn.sportsdata.webapp.youth.common.utils.SecurityUtils;

public class PatientDocumentVO {
	private String dirName = null;
	private String fileName = null;
	private String filePath = null;
	
	private String storage_name = null;
	
	private List<PatientDocumentVO> subDocs = null;
	public String getDirName() {
		return dirName;
	}
	public void setDirName(String dirName) {
		this.dirName = dirName;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) throws SoccerProException {
		this.filePath = SecurityUtils.encryptByAES(filePath);
	}
	public List<PatientDocumentVO> getSubDocs() {
		return subDocs;
	}
	public void setSubDocs(List<PatientDocumentVO> subDocs) {
		this.subDocs = subDocs;
	}
	public String getStorage_name() {
		return storage_name;
	}
	public void setStorage_name(String storage_name) {
		this.storage_name = storage_name;
	}
	
}


