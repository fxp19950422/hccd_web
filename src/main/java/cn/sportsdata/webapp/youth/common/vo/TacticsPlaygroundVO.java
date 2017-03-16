package cn.sportsdata.webapp.youth.common.vo;

import java.io.Serializable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.sportsdata.webapp.youth.common.constants.Constants;

public class TacticsPlaygroundVO extends BaseVO implements Serializable {
	private static final long serialVersionUID = 8372823902137128340L;
	private long id;
	private String name;
	private String abbr;
	private String backgroundimg;
	private long width_in_meter;
	private long height_in_meter;
	String category;
	private boolean checked;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAbbr() {
		return abbr;
	}
	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}
	public String getBackgroundimg() {
		return backgroundimg;
	}
	public void setBackgroundimg(String backgroundimg) {
		this.backgroundimg = backgroundimg;
	}
	public long getWidth_in_meter() {
		return width_in_meter;
	}
	public void setWidth_in_meter(long width_in_meter) {
		this.width_in_meter = width_in_meter;
	}
	public long getHeight_in_meter() {
		return height_in_meter;
	}
	public void setHeight_in_meter(long height_in_meter) {
		this.height_in_meter = height_in_meter;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public String toJson() {
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		try {
			json = mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return json;
	}
}
