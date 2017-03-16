package cn.sportsdata.webapp.youth.web.controller.file;

import java.io.Serializable;

public class FileUploadBO implements Serializable {
	private static final long serialVersionUID = 8116404699439303250L;
	
	private String fileName;
	private String cropStartX;
	private String cropStartY;
	private String cropWidth;
	private String cropHeight;
	
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getCropStartX() {
		return cropStartX;
	}
	
	public void setCropStartX(String cropStartX) {
		this.cropStartX = cropStartX;
	}
	
	public String getCropStartY() {
		return cropStartY;
	}
	
	public void setCropStartY(String cropStartY) {
		this.cropStartY = cropStartY;
	}
	
	public String getCropWidth() {
		return cropWidth;
	}
	
	public void setCropWidth(String cropWidth) {
		this.cropWidth = cropWidth;
	}
	
	public String getCropHeight() {
		return cropHeight;
	}
	
	public void setCropHeight(String cropHeight) {
		this.cropHeight = cropHeight;
	}
}
