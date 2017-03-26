package cn.sportsdata.webapp.youth.common.bo.hospital;

import java.io.Serializable;
import java.util.Date;

public class PatientRecordBO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6339886860019541449L;
	private String recordId;
	private String patientName;
	private String departmentName;
	private Date startTime;
	private Date endTime;
	private String patientAvatar;
	private String gender;
	
	public String getRecordId() {
		return recordId;
	}
	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getPatientAvatar() {
		return patientAvatar;
	}
	public void setPatientAvatar(String patientAvatar) {
		this.patientAvatar = patientAvatar;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
}
