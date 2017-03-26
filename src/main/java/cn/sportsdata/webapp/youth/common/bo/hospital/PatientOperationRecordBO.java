package cn.sportsdata.webapp.youth.common.bo.hospital;

import java.io.Serializable;

public class PatientOperationRecordBO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6339886860019541449L;
	private String patientName;
	private String departmentName;
	private String startTime;
	private String endTime;
	private String chiefComplaint;
	private String medicalHistory;
	private int examnination;
	private int primaryDiagnosis;
	private String treatment;
	private String suggestion;  // separated by ','
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
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getChiefComplaint() {
		return chiefComplaint;
	}
	public void setChiefComplaint(String chiefComplaint) {
		this.chiefComplaint = chiefComplaint;
	}
	public String getMedicalHistory() {
		return medicalHistory;
	}
	public void setMedicalHistory(String medicalHistory) {
		this.medicalHistory = medicalHistory;
	}
	public int getExamnination() {
		return examnination;
	}
	public void setExamnination(int examnination) {
		this.examnination = examnination;
	}
	public int getPrimaryDiagnosis() {
		return primaryDiagnosis;
	}
	public void setPrimaryDiagnosis(int primaryDiagnosis) {
		this.primaryDiagnosis = primaryDiagnosis;
	}
	public String getTreatment() {
		return treatment;
	}
	public void setTreatment(String treatment) {
		this.treatment = treatment;
	}
	public String getSuggestion() {
		return suggestion;
	}
	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}
}
