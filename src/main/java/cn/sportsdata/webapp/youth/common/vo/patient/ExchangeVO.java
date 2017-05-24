package cn.sportsdata.webapp.youth.common.vo.patient;

import java.util.ArrayList;
import java.util.List;

public class ExchangeVO {
	List<PatientInHospital> exchangeList = new ArrayList<PatientInHospital>();
	List<ResidentRecord> residentList  = new ArrayList<ResidentRecord>();
	
	public List<PatientInHospital> getExchangeList() {
		return exchangeList;
	}
	public void setExchangeList(List<PatientInHospital> exchangeList) {
		this.exchangeList = exchangeList;
	}
	public List<ResidentRecord> getResidentList() {
		return residentList;
	}
	public void setResidentList(List<ResidentRecord> residentList) {
		this.residentList = residentList;
	}
	
	
}
