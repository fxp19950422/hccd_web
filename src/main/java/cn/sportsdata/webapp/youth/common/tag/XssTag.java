package cn.sportsdata.webapp.youth.common.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.springframework.stereotype.Service;

import cn.sportsdata.webapp.youth.common.utils.XssHelper;

@Service
public class XssTag extends BodyTagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7736051416855870428L;
	
	private String text;
	private String filter;
	
	@Override
	public int doStartTag() throws JspException {
		JspWriter writer = pageContext.getOut();
		if(text == null || text.equals("")){
			return SKIP_BODY;
		}
		String message = XssHelper.filterString(text, filter);
		try {
			writer.write(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return SKIP_BODY;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the filter
	 */
	public String getFilter() {
		return filter;
	}

	/**
	 * @param filter the filter to set
	 */
	public void setFilter(String filter) {
		this.filter = filter;
	}
	
	
	
	
}