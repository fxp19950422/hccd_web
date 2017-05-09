package cn.sportsdata.webapp.youth.common.bo.hospital;

import java.io.Serializable;

public class PatientRecordBriefBO implements Serializable{
	
	private static final long serialVersionUID = -6339886860019541449L;
	
	private String type;
	private String doctorId;
	private String doctorName;
	private String createDate;
	private String doctorAvatar;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(String doctorId) {
		this.doctorId = doctorId;
	}
	public String getDoctorName() {
		return doctorName;
	}
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getDoctorAvatar() {
		return doctorAvatar;
	}
	public void setDoctorAvatar(String doctorAvatar) {
		this.doctorAvatar = doctorAvatar;
	}
	
	
	
}
