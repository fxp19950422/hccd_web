package cn.sportsdata.webapp.youth.common.vo.account;

import java.io.Serializable;
import java.util.Date;

public class ActionAuthenticationVO implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 754020881810090267L;
	private long action_id;
    private String user_id;
    private Date expiration_time;
    private String random_md5;
    private String action_type;
	public String getAction_type() {
		return action_type;
	}
	public void setAction_type(String action_type) {
		this.action_type = action_type;
	}
	public long getRetrieve_psw_id() {
		return action_id;
	}
	public void setRetrieve_psw_id(long action_id) {
		this.action_id = action_id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public Date getExpiration_time() {
		return expiration_time;
	}
	public void setExpiration_time(Date expiration_time) {
		this.expiration_time = expiration_time;
	}
	public String getRandom_md5() {
		return random_md5;
	}
	public void setRandom_md5(String random_md5) {
		this.random_md5 = random_md5;
	}
}
