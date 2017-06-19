package cn.sportsdata.webapp.youth.pcli.vo;

import java.io.Serializable;
import java.util.List;

public class HospitalVO extends BaseVO implements Serializable {

	private static final long serialVersionUID = 8882782759521010857L;
	
	private String id;
	private String name;
	private String code;
	private String location;
	private String grade;
	private String description;
	private List<String> assets;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<String> getAssets() {
		return assets;
	}
	public void setAssets(List<String> assets) {
		this.assets = assets;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	
}
