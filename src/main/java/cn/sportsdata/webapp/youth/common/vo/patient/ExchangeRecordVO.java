package cn.sportsdata.webapp.youth.common.vo.patient;

public class ExchangeRecordVO {
	public String getExchangePatientName() {
		return exchangePatientName;
	}

	public void setExchangePatientName(String exchangePatientName) {
		this.exchangePatientName = exchangePatientName;
	}

	public String getExchangePatientBedNo() {
		return exchangePatientBedNo;
	}

	public void setExchangePatientBedNo(String exchangePatientBedNo) {
		this.exchangePatientBedNo = exchangePatientBedNo;
	}

	public String getExchangeType() {
		return exchangeType;
	}

	public void setExchangeType(String exchangeType) {
		this.exchangeType = exchangeType;
	}

	private String exchangePatientName;
	
	private String exchangePatientBedNo;
	
	private String exchangeType;
}
