package cn.sportsdata.webapp.youth.common.bo.hospital;

import java.io.Serializable;

public class PatientRecordDetailBO implements Serializable{

	private static final long serialVersionUID = 3423636194105192777L;
	
	private String id;
	private String date;
	private String department;
	private String doctorName;
	private String medHistory;
	private String diagDesc;
	private String suggestion;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getDoctorName() {
		return doctorName;
	}
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}
	public String getMedHistory() {
		return medHistory;
	}
	public void setMedHistory(String medHistory) {
		this.medHistory = medHistory;
	}
	public String getDiagDesc() {
		return diagDesc;
	}
	public void setDiagDesc(String diagDesc) {
		this.diagDesc = diagDesc;
	}
	public String getSuggestion() {
		return suggestion;
	}
	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}
	
	
	
}
