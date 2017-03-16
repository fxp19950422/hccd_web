package cn.sportsdata.webapp.youth.common.vo.test;

import java.util.Date;

public class TestDataPO {
	private String id;
	private long test_item_id;
	private String user_id;
	private double test_result;
	private String org_id;
	private double score;
	private double additional_score;
	private Date test_time;
	private String single_test_id;
	private String test_batch_id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public long getTest_item_id() {
		return test_item_id;
	}
	public void setTest_item_id(long test_item_id) {
		this.test_item_id = test_item_id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public double getTest_result() {
		return test_result;
	}
	public void setTest_result(double test_result) {
		this.test_result = test_result;
	}
	public String getOrg_id() {
		return org_id;
	}
	public void setOrg_id(String org_id) {
		this.org_id = org_id;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public double getAdditional_score() {
		return additional_score;
	}
	public void setAdditional_score(double additional_score) {
		this.additional_score = additional_score;
	}
	public Date getTest_time() {
		return test_time;
	}
	public void setTest_time(Date test_time) {
		this.test_time = test_time;
	}
	public String getSingle_test_id() {
		return single_test_id;
	}
	public void setSingle_test_id(String single_test_id) {
		this.single_test_id = single_test_id;
	}
	public String getTest_batch_id() {
		return test_batch_id;
	}
	public void setTest_batch_id(String test_batch_id) {
		this.test_batch_id = test_batch_id;
	}
}
