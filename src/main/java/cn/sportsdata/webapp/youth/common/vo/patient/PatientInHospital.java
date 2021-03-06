package cn.sportsdata.webapp.youth.common.vo.patient;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.sportsdata.webapp.youth.common.vo.AssetTypeVO;

public class PatientInHospital implements Serializable {
	private static final long serialVersionUID = 8628757275474528838L;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column patient_in_hospital.ID
	 *
	 * @mbggenerated
	 */
	private String id;

	private String type;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column patient_in_hospital.doctor_id
	 *
	 * @mbggenerated
	 */
	private String doctorId;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column patient_in_hospital.PATIENT_ID
	 *
	 * @mbggenerated
	 */
	private String patientId;
	private String patientName;
	private String patientGender;
	public String getPatientGender() {
		return patientGender;
	}
	public void setPatientGender(String patientGender) {
		this.patientGender = patientGender;
	}
	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column patient_in_hospital.VISIT_ID
	 *
	 * @mbggenerated
	 */
	private Integer visitId;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column patient_in_hospital.WARD_CODE
	 *
	 * @mbggenerated
	 */
	private String wardCode;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column patient_in_hospital.DEPT_CODE
	 *
	 * @mbggenerated
	 */
	private String deptCode;
	private String departmentId;
	public String getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column patient_in_hospital.BED_NO
	 *
	 * @mbggenerated
	 */
	private Integer bedNo;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column patient_in_hospital.ADMISSION_DATE_TIME
	 *
	 * @mbggenerated
	 */
	private Date admissionDateTime;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column patient_in_hospital.ADM_WARD_DATE_TIME
	 *
	 * @mbggenerated
	 */
	private Date admWardDateTime;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column patient_in_hospital.DIAGNOSIS
	 *
	 * @mbggenerated
	 */
	private String diagnosis;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column patient_in_hospital.PATIENT_CONDITION
	 *
	 * @mbggenerated
	 */
	private String patientCondition;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column patient_in_hospital.NURSING_CLASS
	 *
	 * @mbggenerated
	 */
	private String nursingClass;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column patient_in_hospital.DOCTOR_IN_CHARGE
	 *
	 * @mbggenerated
	 */
	private String doctorInCharge;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column patient_in_hospital.OPERATING_DATE
	 *
	 * @mbggenerated
	 */
	private Date operatingDate;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column patient_in_hospital.BILLING_DATE_TIME
	 *
	 * @mbggenerated
	 */
	private Date billingDateTime;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column patient_in_hospital.PREPAYMENTS
	 *
	 * @mbggenerated
	 */
	private Double prepayments;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column patient_in_hospital.TOTAL_COSTS
	 *
	 * @mbggenerated
	 */
	private Double totalCosts;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column patient_in_hospital.TOTAL_CHARGES
	 *
	 * @mbggenerated
	 */
	private Double totalCharges;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column patient_in_hospital.GUARANTOR
	 *
	 * @mbggenerated
	 */
	private String guarantor;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column patient_in_hospital.GUARANTOR_ORG
	 *
	 * @mbggenerated
	 */
	private String guarantorOrg;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column patient_in_hospital.GUARANTOR_PHONE_NUM
	 *
	 * @mbggenerated
	 */
	private String guarantorPhoneNum;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column patient_in_hospital.BILL_CHECKED_DATE_TIME
	 *
	 * @mbggenerated
	 */
	private Date billCheckedDateTime;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column patient_in_hospital.SETTLED_INDICATOR
	 *
	 * @mbggenerated
	 */
	private Integer settledIndicator;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column patient_in_hospital.LEND_BED_NO
	 *
	 * @mbggenerated
	 */
	private Integer lendBedNo;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column patient_in_hospital.BED_DEPT_CODE
	 *
	 * @mbggenerated
	 */
	private String bedDeptCode;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column patient_in_hospital.BED_WARD_CODE
	 *
	 * @mbggenerated
	 */
	private String bedWardCode;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column patient_in_hospital.DEPT_CODE_LEND
	 *
	 * @mbggenerated
	 */
	private String deptCodeLend;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column patient_in_hospital.LEND_INDICATOR
	 *
	 * @mbggenerated
	 */
	private Integer lendIndicator;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column patient_in_hospital.IS_NEWBORN
	 *
	 * @mbggenerated
	 */
	private Integer isNewborn;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column patient_in_hospital.HOSPITAL_ID
	 *
	 * @mbggenerated
	 */
	private String hospitalId;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column patient_in_hospital.STATUS
	 *
	 * @mbggenerated
	 */
	private String status;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column patient_in_hospital.last_update
	 *
	 * @mbggenerated
	 */
	private Date lastUpdate;

	private String realName;

	private String opPrimary;

	private Date birthday;

	private String age;

	public String getAge() {

		return getAge(this.birthday);
	}

	public void setAge(String age) {
		this.age = age;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	List<OpertaionRecord> operationRecords;

	public List<OpertaionRecord> getOperationRecords() {
		return operationRecords;
	}

	public void setOperationRecords(List<OpertaionRecord> operationRecords) {
		this.operationRecords = operationRecords;
	}

	public String getOpPrimary() {
		return opPrimary;
	}

	public void setOpPrimary(String opPrimary) {
		this.opPrimary = opPrimary;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column patient_in_hospital.ID
	 *
	 * @return the value of patient_in_hospital.ID
	 *
	 * @mbggenerated
	 */
	public String getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column patient_in_hospital.ID
	 *
	 * @param id
	 *            the value for patient_in_hospital.ID
	 *
	 * @mbggenerated
	 */
	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column patient_in_hospital.doctor_id
	 *
	 * @return the value of patient_in_hospital.doctor_id
	 *
	 * @mbggenerated
	 */
	public String getDoctorId() {
		return doctorId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column patient_in_hospital.doctor_id
	 *
	 * @param doctorId
	 *            the value for patient_in_hospital.doctor_id
	 *
	 * @mbggenerated
	 */
	public void setDoctorId(String doctorId) {
		this.doctorId = doctorId == null ? null : doctorId.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column patient_in_hospital.PATIENT_ID
	 *
	 * @return the value of patient_in_hospital.PATIENT_ID
	 *
	 * @mbggenerated
	 */
	public String getPatientId() {
		return patientId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column patient_in_hospital.PATIENT_ID
	 *
	 * @param patientId
	 *            the value for patient_in_hospital.PATIENT_ID
	 *
	 * @mbggenerated
	 */
	public void setPatientId(String patientId) {
		this.patientId = patientId == null ? null : patientId.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column patient_in_hospital.VISIT_ID
	 *
	 * @return the value of patient_in_hospital.VISIT_ID
	 *
	 * @mbggenerated
	 */
	public Integer getVisitId() {
		return visitId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column patient_in_hospital.VISIT_ID
	 *
	 * @param visitId
	 *            the value for patient_in_hospital.VISIT_ID
	 *
	 * @mbggenerated
	 */
	public void setVisitId(Integer visitId) {
		this.visitId = visitId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column patient_in_hospital.WARD_CODE
	 *
	 * @return the value of patient_in_hospital.WARD_CODE
	 *
	 * @mbggenerated
	 */
	public String getWardCode() {
		return wardCode;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column patient_in_hospital.WARD_CODE
	 *
	 * @param wardCode
	 *            the value for patient_in_hospital.WARD_CODE
	 *
	 * @mbggenerated
	 */
	public void setWardCode(String wardCode) {
		this.wardCode = wardCode == null ? null : wardCode.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column patient_in_hospital.DEPT_CODE
	 *
	 * @return the value of patient_in_hospital.DEPT_CODE
	 *
	 * @mbggenerated
	 */
	public String getDeptCode() {
		return deptCode;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column patient_in_hospital.DEPT_CODE
	 *
	 * @param deptCode
	 *            the value for patient_in_hospital.DEPT_CODE
	 *
	 * @mbggenerated
	 */
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode == null ? null : deptCode.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column patient_in_hospital.BED_NO
	 *
	 * @return the value of patient_in_hospital.BED_NO
	 *
	 * @mbggenerated
	 */
	public Integer getBedNo() {
		return bedNo;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column patient_in_hospital.BED_NO
	 *
	 * @param bedNo
	 *            the value for patient_in_hospital.BED_NO
	 *
	 * @mbggenerated
	 */
	public void setBedNo(Integer bedNo) {
		this.bedNo = bedNo;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column patient_in_hospital.ADMISSION_DATE_TIME
	 *
	 * @return the value of patient_in_hospital.ADMISSION_DATE_TIME
	 *
	 * @mbggenerated
	 */
	public Date getAdmissionDateTime() {
		return admissionDateTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column patient_in_hospital.ADMISSION_DATE_TIME
	 *
	 * @param admissionDateTime
	 *            the value for patient_in_hospital.ADMISSION_DATE_TIME
	 *
	 * @mbggenerated
	 */
	public void setAdmissionDateTime(Date admissionDateTime) {
		this.admissionDateTime = admissionDateTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column patient_in_hospital.ADM_WARD_DATE_TIME
	 *
	 * @return the value of patient_in_hospital.ADM_WARD_DATE_TIME
	 *
	 * @mbggenerated
	 */
	public Date getAdmWardDateTime() {
		return admWardDateTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column patient_in_hospital.ADM_WARD_DATE_TIME
	 *
	 * @param admWardDateTime
	 *            the value for patient_in_hospital.ADM_WARD_DATE_TIME
	 *
	 * @mbggenerated
	 */
	public void setAdmWardDateTime(Date admWardDateTime) {
		this.admWardDateTime = admWardDateTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column patient_in_hospital.DIAGNOSIS
	 *
	 * @return the value of patient_in_hospital.DIAGNOSIS
	 *
	 * @mbggenerated
	 */
	public String getDiagnosis() {
		return diagnosis;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column patient_in_hospital.DIAGNOSIS
	 *
	 * @param diagnosis
	 *            the value for patient_in_hospital.DIAGNOSIS
	 *
	 * @mbggenerated
	 */
	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis == null ? null : diagnosis.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column patient_in_hospital.PATIENT_CONDITION
	 *
	 * @return the value of patient_in_hospital.PATIENT_CONDITION
	 *
	 * @mbggenerated
	 */
	public String getPatientCondition() {
		return patientCondition;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column patient_in_hospital.PATIENT_CONDITION
	 *
	 * @param patientCondition
	 *            the value for patient_in_hospital.PATIENT_CONDITION
	 *
	 * @mbggenerated
	 */
	public void setPatientCondition(String patientCondition) {
		this.patientCondition = patientCondition == null ? null : patientCondition.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column patient_in_hospital.NURSING_CLASS
	 *
	 * @return the value of patient_in_hospital.NURSING_CLASS
	 *
	 * @mbggenerated
	 */
	public String getNursingClass() {
		return nursingClass;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column patient_in_hospital.NURSING_CLASS
	 *
	 * @param nursingClass
	 *            the value for patient_in_hospital.NURSING_CLASS
	 *
	 * @mbggenerated
	 */
	public void setNursingClass(String nursingClass) {
		this.nursingClass = nursingClass == null ? null : nursingClass.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column patient_in_hospital.DOCTOR_IN_CHARGE
	 *
	 * @return the value of patient_in_hospital.DOCTOR_IN_CHARGE
	 *
	 * @mbggenerated
	 */
	public String getDoctorInCharge() {
		return doctorInCharge;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column patient_in_hospital.DOCTOR_IN_CHARGE
	 *
	 * @param doctorInCharge
	 *            the value for patient_in_hospital.DOCTOR_IN_CHARGE
	 *
	 * @mbggenerated
	 */
	public void setDoctorInCharge(String doctorInCharge) {
		this.doctorInCharge = doctorInCharge == null ? null : doctorInCharge.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column patient_in_hospital.OPERATING_DATE
	 *
	 * @return the value of patient_in_hospital.OPERATING_DATE
	 *
	 * @mbggenerated
	 */
	public Date getOperatingDate() {
		return operatingDate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column patient_in_hospital.OPERATING_DATE
	 *
	 * @param operatingDate
	 *            the value for patient_in_hospital.OPERATING_DATE
	 *
	 * @mbggenerated
	 */
	public void setOperatingDate(Date operatingDate) {
		this.operatingDate = operatingDate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column patient_in_hospital.BILLING_DATE_TIME
	 *
	 * @return the value of patient_in_hospital.BILLING_DATE_TIME
	 *
	 * @mbggenerated
	 */
	public Date getBillingDateTime() {
		return billingDateTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column patient_in_hospital.BILLING_DATE_TIME
	 *
	 * @param billingDateTime
	 *            the value for patient_in_hospital.BILLING_DATE_TIME
	 *
	 * @mbggenerated
	 */
	public void setBillingDateTime(Date billingDateTime) {
		this.billingDateTime = billingDateTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column patient_in_hospital.PREPAYMENTS
	 *
	 * @return the value of patient_in_hospital.PREPAYMENTS
	 *
	 * @mbggenerated
	 */
	public Double getPrepayments() {
		return prepayments;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column patient_in_hospital.PREPAYMENTS
	 *
	 * @param prepayments
	 *            the value for patient_in_hospital.PREPAYMENTS
	 *
	 * @mbggenerated
	 */
	public void setPrepayments(Double prepayments) {
		this.prepayments = prepayments;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column patient_in_hospital.TOTAL_COSTS
	 *
	 * @return the value of patient_in_hospital.TOTAL_COSTS
	 *
	 * @mbggenerated
	 */
	public Double getTotalCosts() {
		return totalCosts;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column patient_in_hospital.TOTAL_COSTS
	 *
	 * @param totalCosts
	 *            the value for patient_in_hospital.TOTAL_COSTS
	 *
	 * @mbggenerated
	 */
	public void setTotalCosts(Double totalCosts) {
		this.totalCosts = totalCosts;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column patient_in_hospital.TOTAL_CHARGES
	 *
	 * @return the value of patient_in_hospital.TOTAL_CHARGES
	 *
	 * @mbggenerated
	 */
	public Double getTotalCharges() {
		return totalCharges;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column patient_in_hospital.TOTAL_CHARGES
	 *
	 * @param totalCharges
	 *            the value for patient_in_hospital.TOTAL_CHARGES
	 *
	 * @mbggenerated
	 */
	public void setTotalCharges(Double totalCharges) {
		this.totalCharges = totalCharges;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column patient_in_hospital.GUARANTOR
	 *
	 * @return the value of patient_in_hospital.GUARANTOR
	 *
	 * @mbggenerated
	 */
	public String getGuarantor() {
		return guarantor;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column patient_in_hospital.GUARANTOR
	 *
	 * @param guarantor
	 *            the value for patient_in_hospital.GUARANTOR
	 *
	 * @mbggenerated
	 */
	public void setGuarantor(String guarantor) {
		this.guarantor = guarantor == null ? null : guarantor.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column patient_in_hospital.GUARANTOR_ORG
	 *
	 * @return the value of patient_in_hospital.GUARANTOR_ORG
	 *
	 * @mbggenerated
	 */
	public String getGuarantorOrg() {
		return guarantorOrg;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column patient_in_hospital.GUARANTOR_ORG
	 *
	 * @param guarantorOrg
	 *            the value for patient_in_hospital.GUARANTOR_ORG
	 *
	 * @mbggenerated
	 */
	public void setGuarantorOrg(String guarantorOrg) {
		this.guarantorOrg = guarantorOrg == null ? null : guarantorOrg.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column patient_in_hospital.GUARANTOR_PHONE_NUM
	 *
	 * @return the value of patient_in_hospital.GUARANTOR_PHONE_NUM
	 *
	 * @mbggenerated
	 */
	public String getGuarantorPhoneNum() {
		return guarantorPhoneNum;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column patient_in_hospital.GUARANTOR_PHONE_NUM
	 *
	 * @param guarantorPhoneNum
	 *            the value for patient_in_hospital.GUARANTOR_PHONE_NUM
	 *
	 * @mbggenerated
	 */
	public void setGuarantorPhoneNum(String guarantorPhoneNum) {
		this.guarantorPhoneNum = guarantorPhoneNum == null ? null : guarantorPhoneNum.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column patient_in_hospital.BILL_CHECKED_DATE_TIME
	 *
	 * @return the value of patient_in_hospital.BILL_CHECKED_DATE_TIME
	 *
	 * @mbggenerated
	 */
	public Date getBillCheckedDateTime() {
		return billCheckedDateTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column patient_in_hospital.BILL_CHECKED_DATE_TIME
	 *
	 * @param billCheckedDateTime
	 *            the value for patient_in_hospital.BILL_CHECKED_DATE_TIME
	 *
	 * @mbggenerated
	 */
	public void setBillCheckedDateTime(Date billCheckedDateTime) {
		this.billCheckedDateTime = billCheckedDateTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column patient_in_hospital.SETTLED_INDICATOR
	 *
	 * @return the value of patient_in_hospital.SETTLED_INDICATOR
	 *
	 * @mbggenerated
	 */
	public Integer getSettledIndicator() {
		return settledIndicator;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column patient_in_hospital.SETTLED_INDICATOR
	 *
	 * @param settledIndicator
	 *            the value for patient_in_hospital.SETTLED_INDICATOR
	 *
	 * @mbggenerated
	 */
	public void setSettledIndicator(Integer settledIndicator) {
		this.settledIndicator = settledIndicator;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column patient_in_hospital.LEND_BED_NO
	 *
	 * @return the value of patient_in_hospital.LEND_BED_NO
	 *
	 * @mbggenerated
	 */
	public Integer getLendBedNo() {
		return lendBedNo;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column patient_in_hospital.LEND_BED_NO
	 *
	 * @param lendBedNo
	 *            the value for patient_in_hospital.LEND_BED_NO
	 *
	 * @mbggenerated
	 */
	public void setLendBedNo(Integer lendBedNo) {
		this.lendBedNo = lendBedNo;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column patient_in_hospital.BED_DEPT_CODE
	 *
	 * @return the value of patient_in_hospital.BED_DEPT_CODE
	 *
	 * @mbggenerated
	 */
	public String getBedDeptCode() {
		return bedDeptCode;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column patient_in_hospital.BED_DEPT_CODE
	 *
	 * @param bedDeptCode
	 *            the value for patient_in_hospital.BED_DEPT_CODE
	 *
	 * @mbggenerated
	 */
	public void setBedDeptCode(String bedDeptCode) {
		this.bedDeptCode = bedDeptCode == null ? null : bedDeptCode.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column patient_in_hospital.BED_WARD_CODE
	 *
	 * @return the value of patient_in_hospital.BED_WARD_CODE
	 *
	 * @mbggenerated
	 */
	public String getBedWardCode() {
		return bedWardCode;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column patient_in_hospital.BED_WARD_CODE
	 *
	 * @param bedWardCode
	 *            the value for patient_in_hospital.BED_WARD_CODE
	 *
	 * @mbggenerated
	 */
	public void setBedWardCode(String bedWardCode) {
		this.bedWardCode = bedWardCode == null ? null : bedWardCode.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column patient_in_hospital.DEPT_CODE_LEND
	 *
	 * @return the value of patient_in_hospital.DEPT_CODE_LEND
	 *
	 * @mbggenerated
	 */
	public String getDeptCodeLend() {
		return deptCodeLend;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column patient_in_hospital.DEPT_CODE_LEND
	 *
	 * @param deptCodeLend
	 *            the value for patient_in_hospital.DEPT_CODE_LEND
	 *
	 * @mbggenerated
	 */
	public void setDeptCodeLend(String deptCodeLend) {
		this.deptCodeLend = deptCodeLend == null ? null : deptCodeLend.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column patient_in_hospital.LEND_INDICATOR
	 *
	 * @return the value of patient_in_hospital.LEND_INDICATOR
	 *
	 * @mbggenerated
	 */
	public Integer getLendIndicator() {
		return lendIndicator;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column patient_in_hospital.LEND_INDICATOR
	 *
	 * @param lendIndicator
	 *            the value for patient_in_hospital.LEND_INDICATOR
	 *
	 * @mbggenerated
	 */
	public void setLendIndicator(Integer lendIndicator) {
		this.lendIndicator = lendIndicator;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column patient_in_hospital.IS_NEWBORN
	 *
	 * @return the value of patient_in_hospital.IS_NEWBORN
	 *
	 * @mbggenerated
	 */
	public Integer getIsNewborn() {
		return isNewborn;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column patient_in_hospital.IS_NEWBORN
	 *
	 * @param isNewborn
	 *            the value for patient_in_hospital.IS_NEWBORN
	 *
	 * @mbggenerated
	 */
	public void setIsNewborn(Integer isNewborn) {
		this.isNewborn = isNewborn;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column patient_in_hospital.HOSPITAL_ID
	 *
	 * @return the value of patient_in_hospital.HOSPITAL_ID
	 *
	 * @mbggenerated
	 */
	public String getHospitalId() {
		return hospitalId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column patient_in_hospital.HOSPITAL_ID
	 *
	 * @param hospitalId
	 *            the value for patient_in_hospital.HOSPITAL_ID
	 *
	 * @mbggenerated
	 */
	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId == null ? null : hospitalId.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column patient_in_hospital.STATUS
	 *
	 * @return the value of patient_in_hospital.STATUS
	 *
	 * @mbggenerated
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column patient_in_hospital.STATUS
	 *
	 * @param status
	 *            the value for patient_in_hospital.STATUS
	 *
	 * @mbggenerated
	 */
	public void setStatus(String status) {
		this.status = status == null ? null : status.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column patient_in_hospital.last_update
	 *
	 * @return the value of patient_in_hospital.last_update
	 *
	 * @mbggenerated
	 */
	public Date getLastUpdate() {
		return lastUpdate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column patient_in_hospital.last_update
	 *
	 * @param lastUpdate
	 *            the value for patient_in_hospital.last_update
	 *
	 * @mbggenerated
	 */
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	Date meetingDate;

	public Date getMeetingDate() {
		return meetingDate;
	}

	public void setMeetingDate(Date meetingDate) {
		this.meetingDate = meetingDate;
	}

    private String exchangeDoctorId;
    public String getExchangeDoctorId() {
		return exchangeDoctorId;
	}
	public void setExchangeDoctorId(String exchangeDoctorId) {
		this.exchangeDoctorId = exchangeDoctorId;
	}
	
	Date latestMeetingDate;
	String latestMeetingType;

	public Date getLatestMeetingDate() {
		return latestMeetingDate;
	}

	public void setLatestMeetingDate(Date latestMeetingDate) {
		this.latestMeetingDate = latestMeetingDate;
	}

	public String getLatestMeetingType() {
		return latestMeetingType;
	}

	public void setLatestMeetingType(String latestMeetingType) {
		this.latestMeetingType = latestMeetingType;
	}

	private List<AssetTypeVO> patientAssetTypes;
	
	private String supplementaryExamination;
	private String recordDiscussion;
	private String bodyExam;
	private String illHistory;
	
	

	

	public String getBodyExam() {
		return bodyExam;
	}

	public void setBodyExam(String bodyExam) {
		this.bodyExam = bodyExam;
	}

	public String getIllHistory() {
		return illHistory;
	}

	public void setIllHistory(String illHistory) {
		this.illHistory = illHistory;
	}

	public String getSupplementaryExamination() {
		return supplementaryExamination;
	}

	public void setSupplementaryExamination(String supplementaryExamination) {
		this.supplementaryExamination = supplementaryExamination;
	}

	public String getRecordDiscussion() {
		return recordDiscussion;
	}

	public void setRecordDiscussion(String recordDiscussion) {
		this.recordDiscussion = recordDiscussion;
	}

	public List<AssetTypeVO> getPatientAssetTypes() {
		return patientAssetTypes;
	}

	public void setPatientAssetTypes(List<AssetTypeVO> patientAssetTypes) {
		this.patientAssetTypes = patientAssetTypes;
	}

	public String getAge(Date birthDay) {
		if (birthDay == null)
			return "0";

		Calendar cal = Calendar.getInstance();

		if (cal.before(birthDay)) {
			return "0";
		}
		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH);
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
		cal.setTime(birthDay);

		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH);
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

		int age = yearNow - yearBirth;

		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				if (dayOfMonthNow < dayOfMonthBirth)
					age--;
			} else {
				age--;
			}
		}
		return String.valueOf(age);
	}
}
