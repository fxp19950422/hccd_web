package cn.sportsdata.webapp.youth.common.vo.patient;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class PatientDocumentVO implements Serializable {
	private static final long serialVersionUID = 8628757275474528838L;
	
	private String dirName = null;
	private String fileName = null;
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
	
}
