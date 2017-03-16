
package cn.sportsdata.webapp.youth.common.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Menu implements Serializable{

	private static final int MAX_ORDER = 10000;


	/**
	 * 
	 */
	private static final long serialVersionUID = -2986241267191360418L;

	
	private String name;
	
	private String code;
	
	private String display;
	
	private String url;
	
	private String icon;
	
	private String className;
	
	private int order;
	
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	private String tips;
	
	private long tipIndex;
	
	
	//打开类型,0为弹出，1位内嵌
	private Integer openType;
	
	private List<Menu> subMenus;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getOpenType() {
		return openType;
	}

	public void setOpenType(Integer openType) {
		this.openType = openType;
	}

	public List<Menu> getSubMenus() {
		if(subMenus == null){
			subMenus = new ArrayList<Menu>();
		}
		return subMenus;
	}

	public void setSubMenus(List<Menu> subMenus) {
		this.subMenus = subMenus;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	/**
	 * @return the tips
	 */
	public String getTips() {
		return tips;
	}

	/**
	 * @param tips the tips to set
	 */
	public void setTips(String tips) {
		this.tips = tips;
	}

	/**
	 * @return the tipIndex
	 */
	public long getTipIndex() {
		return tipIndex;
	}

	/**
	 * @param tipIndex the tipIndex to set
	 */
	public void setTipIndex(long tipIndex) {
		this.tipIndex = tipIndex;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		if (order <= 0) {
			this.order = MAX_ORDER;
		} else {
			this.order = order;
		}
		
	}
}
