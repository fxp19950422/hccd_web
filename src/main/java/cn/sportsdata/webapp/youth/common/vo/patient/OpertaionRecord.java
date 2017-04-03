package cn.sportsdata.webapp.youth.common.vo.patient;

import java.io.Serializable;
import java.util.Date;

public class OpertaionRecord implements Serializable {
	public static final long OPERATOR_DOCTOR = 0x0001;
	public static final long FIRST_ASSIST_DOCTOR = 0x0002;
	public static final long SECOND_ASSIST_DOCTOR = 0x0004;
	public static final long ANESTHESIA_DOCTOR = 0x0008;
	public static final long ASSIST_NURSE = 0x0010;
	private static final long serialVersionUID = 8628757275474528838L;
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column operation.id
     *
     * @mbggenerated
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column operation.PATIENT_ID
     *
     * @mbggenerated
     */
    private String patientId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column operation.VISIT_ID
     *
     * @mbggenerated
     */
    private Integer visitId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column operation.OPERATION_NO
     *
     * @mbggenerated
     */
    private Integer operationNo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column operation.OPERATION_DESC
     *
     * @mbggenerated
     */
    private String operationDesc;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column operation.OPERATION_CODE
     *
     * @mbggenerated
     */
    private String operationCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column operation.HEAL
     *
     * @mbggenerated
     */
    private String heal;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column operation.WOUND_GRADE
     *
     * @mbggenerated
     */
    private String woundGrade;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column operation.OPERATING_DATE
     *
     * @mbggenerated
     */
    private Date operatingDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column operation.ANAESTHESIA_METHOD
     *
     * @mbggenerated
     */
    private String anaesthesiaMethod;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column operation.OPERATOR
     *
     * @mbggenerated
     */
    private String operator;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column operation.FIRST_ASSISTANT
     *
     * @mbggenerated
     */
    private String firstAssistant;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column operation.SECOND_ASSISTANT
     *
     * @mbggenerated
     */
    private String secondAssistant;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column operation.ANESTHESIA_DOCTOR
     *
     * @mbggenerated
     */
    private String anesthesiaDoctor;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column operation.OPERATION_SCALE
     *
     * @mbggenerated
     */
    private String operationScale;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column operation.OP_PRIMARY
     *
     * @mbggenerated
     */
    private String opPrimary;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column operation.OPTYPE_PRIMARY
     *
     * @mbggenerated
     */
    private String optypePrimary;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column operation.OPERATION_NURSE
     *
     * @mbggenerated
     */
    private String operationNurse;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column operation.BEFORE_OPER
     *
     * @mbggenerated
     */
    private String beforeOper;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column operation.OPERATION_START_DATE
     *
     * @mbggenerated
     */
    private Date operationStartDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column operation.OPERATION_STOP_DATE
     *
     * @mbggenerated
     */
    private Date operationStopDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column operation.OPERATION_EMER_INDICATOR
     *
     * @mbggenerated
     */
    private String operationEmerIndicator;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column operation.OPERATING_END_DATE
     *
     * @mbggenerated
     */
    private Date operatingEndDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column operation.IF_GCK
     *
     * @mbggenerated
     */
    private String ifGck;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column operation.HOSPITAL_ID
     *
     * @mbggenerated
     */
    private String hospitalId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column operation.STATUS
     *
     * @mbggenerated
     */
    private String status;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column operation.id
     *
     * @return the value of operation.id
     *
     * @mbggenerated
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column operation.id
     *
     * @param id the value for operation.id
     *
     * @mbggenerated
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column operation.PATIENT_ID
     *
     * @return the value of operation.PATIENT_ID
     *
     * @mbggenerated
     */
    public String getPatientId() {
        return patientId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column operation.PATIENT_ID
     *
     * @param patientId the value for operation.PATIENT_ID
     *
     * @mbggenerated
     */
    public void setPatientId(String patientId) {
        this.patientId = patientId == null ? null : patientId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column operation.VISIT_ID
     *
     * @return the value of operation.VISIT_ID
     *
     * @mbggenerated
     */
    public Integer getVisitId() {
        return visitId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column operation.VISIT_ID
     *
     * @param visitId the value for operation.VISIT_ID
     *
     * @mbggenerated
     */
    public void setVisitId(Integer visitId) {
        this.visitId = visitId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column operation.OPERATION_NO
     *
     * @return the value of operation.OPERATION_NO
     *
     * @mbggenerated
     */
    public Integer getOperationNo() {
        return operationNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column operation.OPERATION_NO
     *
     * @param operationNo the value for operation.OPERATION_NO
     *
     * @mbggenerated
     */
    public void setOperationNo(Integer operationNo) {
        this.operationNo = operationNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column operation.OPERATION_DESC
     *
     * @return the value of operation.OPERATION_DESC
     *
     * @mbggenerated
     */
    public String getOperationDesc() {
        return operationDesc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column operation.OPERATION_DESC
     *
     * @param operationDesc the value for operation.OPERATION_DESC
     *
     * @mbggenerated
     */
    public void setOperationDesc(String operationDesc) {
        this.operationDesc = operationDesc == null ? null : operationDesc.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column operation.OPERATION_CODE
     *
     * @return the value of operation.OPERATION_CODE
     *
     * @mbggenerated
     */
    public String getOperationCode() {
        return operationCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column operation.OPERATION_CODE
     *
     * @param operationCode the value for operation.OPERATION_CODE
     *
     * @mbggenerated
     */
    public void setOperationCode(String operationCode) {
        this.operationCode = operationCode == null ? null : operationCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column operation.HEAL
     *
     * @return the value of operation.HEAL
     *
     * @mbggenerated
     */
    public String getHeal() {
        return heal;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column operation.HEAL
     *
     * @param heal the value for operation.HEAL
     *
     * @mbggenerated
     */
    public void setHeal(String heal) {
        this.heal = heal == null ? null : heal.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column operation.WOUND_GRADE
     *
     * @return the value of operation.WOUND_GRADE
     *
     * @mbggenerated
     */
    public String getWoundGrade() {
        return woundGrade;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column operation.WOUND_GRADE
     *
     * @param woundGrade the value for operation.WOUND_GRADE
     *
     * @mbggenerated
     */
    public void setWoundGrade(String woundGrade) {
        this.woundGrade = woundGrade == null ? null : woundGrade.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column operation.OPERATING_DATE
     *
     * @return the value of operation.OPERATING_DATE
     *
     * @mbggenerated
     */
    public Date getOperatingDate() {
        return operatingDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column operation.OPERATING_DATE
     *
     * @param operatingDate the value for operation.OPERATING_DATE
     *
     * @mbggenerated
     */
    public void setOperatingDate(Date operatingDate) {
        this.operatingDate = operatingDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column operation.ANAESTHESIA_METHOD
     *
     * @return the value of operation.ANAESTHESIA_METHOD
     *
     * @mbggenerated
     */
    public String getAnaesthesiaMethod() {
        return anaesthesiaMethod;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column operation.ANAESTHESIA_METHOD
     *
     * @param anaesthesiaMethod the value for operation.ANAESTHESIA_METHOD
     *
     * @mbggenerated
     */
    public void setAnaesthesiaMethod(String anaesthesiaMethod) {
        this.anaesthesiaMethod = anaesthesiaMethod == null ? null : anaesthesiaMethod.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column operation.OPERATOR
     *
     * @return the value of operation.OPERATOR
     *
     * @mbggenerated
     */
    public String getOperator() {
        return operator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column operation.OPERATOR
     *
     * @param operator the value for operation.OPERATOR
     *
     * @mbggenerated
     */
    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column operation.FIRST_ASSISTANT
     *
     * @return the value of operation.FIRST_ASSISTANT
     *
     * @mbggenerated
     */
    public String getFirstAssistant() {
        return firstAssistant;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column operation.FIRST_ASSISTANT
     *
     * @param firstAssistant the value for operation.FIRST_ASSISTANT
     *
     * @mbggenerated
     */
    public void setFirstAssistant(String firstAssistant) {
        this.firstAssistant = firstAssistant == null ? null : firstAssistant.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column operation.SECOND_ASSISTANT
     *
     * @return the value of operation.SECOND_ASSISTANT
     *
     * @mbggenerated
     */
    public String getSecondAssistant() {
        return secondAssistant;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column operation.SECOND_ASSISTANT
     *
     * @param secondAssistant the value for operation.SECOND_ASSISTANT
     *
     * @mbggenerated
     */
    public void setSecondAssistant(String secondAssistant) {
        this.secondAssistant = secondAssistant == null ? null : secondAssistant.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column operation.ANESTHESIA_DOCTOR
     *
     * @return the value of operation.ANESTHESIA_DOCTOR
     *
     * @mbggenerated
     */
    public String getAnesthesiaDoctor() {
        return anesthesiaDoctor;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column operation.ANESTHESIA_DOCTOR
     *
     * @param anesthesiaDoctor the value for operation.ANESTHESIA_DOCTOR
     *
     * @mbggenerated
     */
    public void setAnesthesiaDoctor(String anesthesiaDoctor) {
        this.anesthesiaDoctor = anesthesiaDoctor == null ? null : anesthesiaDoctor.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column operation.OPERATION_SCALE
     *
     * @return the value of operation.OPERATION_SCALE
     *
     * @mbggenerated
     */
    public String getOperationScale() {
        return operationScale;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column operation.OPERATION_SCALE
     *
     * @param operationScale the value for operation.OPERATION_SCALE
     *
     * @mbggenerated
     */
    public void setOperationScale(String operationScale) {
        this.operationScale = operationScale == null ? null : operationScale.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column operation.OP_PRIMARY
     *
     * @return the value of operation.OP_PRIMARY
     *
     * @mbggenerated
     */
    public String getOpPrimary() {
        return opPrimary;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column operation.OP_PRIMARY
     *
     * @param opPrimary the value for operation.OP_PRIMARY
     *
     * @mbggenerated
     */
    public void setOpPrimary(String opPrimary) {
        this.opPrimary = opPrimary == null ? null : opPrimary.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column operation.OPTYPE_PRIMARY
     *
     * @return the value of operation.OPTYPE_PRIMARY
     *
     * @mbggenerated
     */
    public String getOptypePrimary() {
        return optypePrimary;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column operation.OPTYPE_PRIMARY
     *
     * @param optypePrimary the value for operation.OPTYPE_PRIMARY
     *
     * @mbggenerated
     */
    public void setOptypePrimary(String optypePrimary) {
        this.optypePrimary = optypePrimary == null ? null : optypePrimary.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column operation.OPERATION_NURSE
     *
     * @return the value of operation.OPERATION_NURSE
     *
     * @mbggenerated
     */
    public String getOperationNurse() {
        return operationNurse;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column operation.OPERATION_NURSE
     *
     * @param operationNurse the value for operation.OPERATION_NURSE
     *
     * @mbggenerated
     */
    public void setOperationNurse(String operationNurse) {
        this.operationNurse = operationNurse == null ? null : operationNurse.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column operation.BEFORE_OPER
     *
     * @return the value of operation.BEFORE_OPER
     *
     * @mbggenerated
     */
    public String getBeforeOper() {
        return beforeOper;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column operation.BEFORE_OPER
     *
     * @param beforeOper the value for operation.BEFORE_OPER
     *
     * @mbggenerated
     */
    public void setBeforeOper(String beforeOper) {
        this.beforeOper = beforeOper == null ? null : beforeOper.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column operation.OPERATION_START_DATE
     *
     * @return the value of operation.OPERATION_START_DATE
     *
     * @mbggenerated
     */
    public Date getOperationStartDate() {
        return operationStartDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column operation.OPERATION_START_DATE
     *
     * @param operationStartDate the value for operation.OPERATION_START_DATE
     *
     * @mbggenerated
     */
    public void setOperationStartDate(Date operationStartDate) {
        this.operationStartDate = operationStartDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column operation.OPERATION_STOP_DATE
     *
     * @return the value of operation.OPERATION_STOP_DATE
     *
     * @mbggenerated
     */
    public Date getOperationStopDate() {
        return operationStopDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column operation.OPERATION_STOP_DATE
     *
     * @param operationStopDate the value for operation.OPERATION_STOP_DATE
     *
     * @mbggenerated
     */
    public void setOperationStopDate(Date operationStopDate) {
        this.operationStopDate = operationStopDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column operation.OPERATION_EMER_INDICATOR
     *
     * @return the value of operation.OPERATION_EMER_INDICATOR
     *
     * @mbggenerated
     */
    public String getOperationEmerIndicator() {
        return operationEmerIndicator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column operation.OPERATION_EMER_INDICATOR
     *
     * @param operationEmerIndicator the value for operation.OPERATION_EMER_INDICATOR
     *
     * @mbggenerated
     */
    public void setOperationEmerIndicator(String operationEmerIndicator) {
        this.operationEmerIndicator = operationEmerIndicator == null ? null : operationEmerIndicator.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column operation.OPERATING_END_DATE
     *
     * @return the value of operation.OPERATING_END_DATE
     *
     * @mbggenerated
     */
    public Date getOperatingEndDate() {
        return operatingEndDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column operation.OPERATING_END_DATE
     *
     * @param operatingEndDate the value for operation.OPERATING_END_DATE
     *
     * @mbggenerated
     */
    public void setOperatingEndDate(Date operatingEndDate) {
        this.operatingEndDate = operatingEndDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column operation.IF_GCK
     *
     * @return the value of operation.IF_GCK
     *
     * @mbggenerated
     */
    public String getIfGck() {
        return ifGck;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column operation.IF_GCK
     *
     * @param ifGck the value for operation.IF_GCK
     *
     * @mbggenerated
     */
    public void setIfGck(String ifGck) {
        this.ifGck = ifGck == null ? null : ifGck.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column operation.HOSPITAL_ID
     *
     * @return the value of operation.HOSPITAL_ID
     *
     * @mbggenerated
     */
    public String getHospitalId() {
        return hospitalId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column operation.HOSPITAL_ID
     *
     * @param hospitalId the value for operation.HOSPITAL_ID
     *
     * @mbggenerated
     */
    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId == null ? null : hospitalId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column operation.STATUS
     *
     * @return the value of operation.STATUS
     *
     * @mbggenerated
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column operation.STATUS
     *
     * @param status the value for operation.STATUS
     *
     * @mbggenerated
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }
}