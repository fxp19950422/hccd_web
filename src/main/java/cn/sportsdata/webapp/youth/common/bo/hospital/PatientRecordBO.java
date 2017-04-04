package cn.sportsdata.webapp.youth.common.bo.hospital;

import java.io.Serializable;

import cn.sportsdata.webapp.youth.common.vo.patient.MedicalRecordVO;
import cn.sportsdata.webapp.youth.common.vo.patient.OpertaionRecord;
import cn.sportsdata.webapp.youth.common.vo.patient.PatientInfoVO;
import cn.sportsdata.webapp.youth.common.vo.patient.PatientRegistRecord;
import cn.sportsdata.webapp.youth.common.vo.patient.ResidentRecord;

public class PatientRecordBO implements Serializable{
	
	private static final long serialVersionUID = -6339886860019541449L;
	private PatientRegistRecord registRecord = null;
	private MedicalRecordVO medicalRecord = null;
	private PatientInfoVO patient = null;
	private ResidentRecord residentRecord = null;
	private OpertaionRecord operationRecord = null;
	private String patientId;
	
	public String getPatientId() {
		return patientId;
	}
	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}
	public PatientRegistRecord getRegistRecord() {
		return registRecord;
	}
	public void setRegistRecord(PatientRegistRecord registRecord) {
		this.registRecord = registRecord;
	}
	public MedicalRecordVO getMedicalRecord() {
		return medicalRecord;
	}
	public void setMedicalRecord(MedicalRecordVO medicalRecord) {
		this.medicalRecord = medicalRecord;
	}
	public PatientInfoVO getPatient() {
		return patient;
	}
	public void setPatient(PatientInfoVO patient) {
		this.patient = patient;
	}
	public ResidentRecord getResidentRecord() {
		return residentRecord;
	}
	public void setResidentRecord(ResidentRecord residentRecord) {
		this.residentRecord = residentRecord;
	}
	public OpertaionRecord getOperationRecord() {
		return operationRecord;
	}
	public void setOperationRecord(OpertaionRecord operationRecord) {
		this.operationRecord = operationRecord;
	}
}
