package cn.sportsdata.webapp.youth.common.vo.patient;

import cn.sportsdata.webapp.youth.common.exceptions.SoccerProException;
import cn.sportsdata.webapp.youth.common.utils.SecurityUtils;

public class PatientDocumentVO {
	private String dirName = null;
	private String fileName = null;
	private String filePath = null;
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
	
}


