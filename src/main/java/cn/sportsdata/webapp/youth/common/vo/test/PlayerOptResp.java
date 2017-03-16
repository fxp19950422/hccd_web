package cn.sportsdata.webapp.youth.common.vo.test;

public class PlayerOptResp {
	private static PlayerOptResp normalSuccessRespInstance = null;
	
	private PlayerOptResp(){};
	
	public static final PlayerOptResp getNormalSuccessResp(){
		if(normalSuccessRespInstance == null){
			normalSuccessRespInstance = new PlayerOptResp();
			normalSuccessRespInstance.setInfo("OKOKOK");
		}
		return normalSuccessRespInstance;
	}
	
	private String info;

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
	
	
}
