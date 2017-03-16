package cn.sportsdata.webapp.youth.common.vo.test;

import java.util.ArrayList;
import java.util.List;

public class PlayerChartRenderData {
	private String test_unit_name = "绩点";
	private List<List<Object>> renderDataSeries = new ArrayList<List<Object>>(1);
	public String getTest_unit_name() {
		return test_unit_name;
	}
	public void setTest_unit_name(String test_unit_name) {
		this.test_unit_name = test_unit_name;
	}
	public List<List<Object>> getRenderDataSeries() {
		return renderDataSeries;
	}
	public void setRenderDataSeries(List<List<Object>> renderDataSeries) {
		this.renderDataSeries = renderDataSeries;
	}
	
	
	
	
	/**
	 * 
	 * 
	 * Data format is : 
	  var data =
	 				[
	 					['2016-01-12', 100.5], 
						['2016-01-13’, 700], 
						['2016-01-14’, 300.2], 
						['2016-01-15’, 500],
						['2016-01-16’, 200]
					];
					
		OR 
		
		var data =
					[
						[new Date('06/02/2014 01:00').getTime(), 100.5], 
						[new Date('06/05/2014 10:00').getTime(), 700], 
						[new Date('06/10/2014 13:00').getTime(), 300.2], 
						[new Date('06/15/2014 15:00').getTime(), 500],
						[new Date('06/19/2014 15:00').getTime(), 200]
					];
	 *
	 */
	public PlayerChartRenderData valueOf(List<PlayerChartData> dataList){
		if(dataList != null && !dataList.isEmpty()){
			test_unit_name = dataList.get(0).getTest_unit_name();
			for(PlayerChartData data: dataList){
				List<Object> singleRenderData = new ArrayList<Object>();
				renderDataSeries.add(singleRenderData);
				singleRenderData.add(data.getTest_time_str());
				singleRenderData.add(data.getTest_result());
			}
		}
		return this;
	}
}
