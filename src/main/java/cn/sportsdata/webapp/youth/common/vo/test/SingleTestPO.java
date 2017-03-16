package cn.sportsdata.webapp.youth.common.vo.test;

import java.util.Date;

public class SingleTestPO {
	private String id;
	private String org_id;
	private String creator_id;
	private long test_item_id;
	private long test_category_id;
	private String test_batch_id;
	private Date test_time;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrg_id() {
		return org_id;
	}
	public void setOrg_id(String org_id) {
		this.org_id = org_id;
	}
	public String getCreator_id() {
		return creator_id;
	}
	public void setCreator_id(String creator_id) {
		this.creator_id = creator_id;
	}
	public long getTest_item_id() {
		return test_item_id;
	}
	public void setTest_item_id(long test_item_id) {
		this.test_item_id = test_item_id;
	}
	public long getTest_category_id() {
		return test_category_id;
	}
	public void setTest_category_id(long test_category_id) {
		this.test_category_id = test_category_id;
	}
	public String getTest_batch_id() {
		return test_batch_id;
	}
	public void setTest_batch_id(String test_batch_id) {
		this.test_batch_id = test_batch_id;
	}
	public Date getTest_time() {
		return test_time;
	}
	public void setTest_time(Date test_time) {
		this.test_time = test_time;
	}
}
