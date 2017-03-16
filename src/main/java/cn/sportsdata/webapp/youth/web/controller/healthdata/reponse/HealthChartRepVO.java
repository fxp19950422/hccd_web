package cn.sportsdata.webapp.youth.web.controller.healthdata.reponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HealthChartRepVO {
	private String colum;
	private String unit_name = "cm";
	private List<List<Object>> renderDataSeries = new ArrayList<List<Object>>(1);
	public String getUnit_name() {
		return unit_name;
	}
	public String getColum() {
		return colum;
	}
	public void setColum(String colum) {
		this.colum = colum;
	}
	public void setUnit_name(String unit_name) {
		this.unit_name = unit_name;
	}
	public List<List<Object>> getRenderDataSeries() {
		return renderDataSeries;
	}
	public void setRenderDataSeries(List<List<Object>> renderDataSeries) {
		this.renderDataSeries = renderDataSeries;
	}
	public HealthChartRepVO valueOf(){
		for(List<Object> ls:renderDataSeries){
			if(ls.get(0) instanceof java.util.Date){
				Date d = (Date) ls.get(0);
				ls.remove(0);
				ls.add(0, getTime(d));
			}
		}
		return this;
	}
	private String getTime(Date date){
		SimpleDateFormat sdmf = new SimpleDateFormat("yyyy-MM-dd");
		return sdmf.format(date);
	}
}
