package cn.sportsdata.webapp.youth.common.vo.healthdata;

import java.sql.Date;

import cn.sportsdata.webapp.youth.common.vo.BaseVO;
import cn.sportsdata.webapp.youth.common.vo.UserVO;

public class HealthDataVO extends BaseVO{

	private String id;
	private float height;
	private float weight;
	private float bmi;
	private float oxygen_content;
	private float shoulder;
	private float haunch;
	private float chest;
	private float waist;
	private int morning_pulse;
	private float lactate;
	private float waistfat;
	private Date create_time;
	private String user_id;
	private String creator_id;
	private UserVO user;
	private UserVO creator;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public float getHeight() {
		return height;
	}
	public void setHeight(float height) {
		this.height = height;
	}
	public float getWeight() {
		return weight;
	}
	public void setWeight(float weight) {
		this.weight = weight;
	}
	public float getBmi() {
		return bmi;
	}
	public void setBmi(float bmi) {
		this.bmi = bmi;
	}
	public float getOxygen_content() {
		return oxygen_content;
	}
	public void setOxygen_content(float oxygen_content) {
		this.oxygen_content = oxygen_content;
	}
	public float getShoulder() {
		return shoulder;
	}
	public void setShoulder(float shoulder) {
		this.shoulder = shoulder;
	}
	public float getHaunch() {
		return haunch;
	}
	public void setHaunch(float haunch) {
		this.haunch = haunch;
	}
	public float getChest() {
		return chest;
	}
	public void setChest(float chest) {
		this.chest = chest;
	}
	public float getWaist() {
		return waist;
	}
	public void setWaist(float waist) {
		this.waist = waist;
	}
	public int getMorning_pulse() {
		return morning_pulse;
	}
	public void setMorning_pulse(int morning_pulse) {
		this.morning_pulse = morning_pulse;
	}
	public float getLactate() {
		return lactate;
	}
	public void setLactate(float lactate) {
		this.lactate = lactate;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getCreator_id() {
		return creator_id;
	}
	public void setCreator_id(String creator_id) {
		this.creator_id = creator_id;
	}
	public float getWaistfat() {
		return waistfat;
	}
	public void setWaistfat(float waistfat) {
		this.waistfat = waistfat;
	}

}
