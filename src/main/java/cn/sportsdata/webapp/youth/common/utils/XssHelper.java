package cn.sportsdata.webapp.youth.common.utils;


import java.util.*;

/**
 * This class provides some helper method to filter string to prevent XSS issues
 * 
 * Note:
 *	filterString() method will do char escaping to parameter "s"
 *	according to "filter". It just responsible for preventing XSS issue.
 *	It will not ensure the data format is correct. For example,
 *	filterString("a:/b.com/", "url") will return "a:/b.com/"
 *	although it is an invalid URL
 *
 * @date 2007/11/13
 * */
public class XssHelper  {

	/** These are all available filter methods for "filter" parameter
	 * of <wbxauth:write> tag as well as filterString(...) java method */
	public static final String FILTER_NONE = "none";
	public static final String FILTER_HTML = "html";
	public static final String FILTER_HTMLBR = "htmlbr";
	public static final String FILTER_JS = "js";
	public static final String FILTER_JS_OBJ_NAME = "jsobjname";
	public static final String FILTER_VS = "vs";
	public static final String FILTER_URL = "url";
	public static final String FILTER_URL_PARAM_VALUE = "urlparamvalue";
	public static final String FILTER_RTH = "rth";
	public static final String FILTER_RTH_PURIFY = "rthpurify";

	/** Following encoding not used now, because
	 * 1. WebLogic has already forbidden CR / LF in cookie value   
	 **/
	// public static final String FILTER_COOKIE = "cookie";

	/** We use "_in_" to separate more than one filter methods.
	 * Such as <webex:write filter="html_in_js"> */
	public static final String FILTER_METHODS_SEPARATOR = "_in_";
	public static final char FILTER_METHODS_SEPARATOR_CHAR = '_';
	

	/**
	 * Two formats of methods to do string filter to prevent XSS issues
	 * @param s - the string to be filtered
	 * @param filter - filtering methods such as "none", "js", "url_in_html".
	 *
	 * Note that the several filtering methods will be done one by one. Please
	 * be careful to set correct filtering order because it is significant.
	 * For example, filter parameter "js_in_html" is different from "html_in_js" 
	 * */
	public static MyStringBuilder filterString(MyStringBuilder s, String filter)
	{
		if (filter == null || filter.length() == 0) {
			return s;
		}
		if (s == null) return s;

		// Ignore case in filter value
		filter = filter.toLowerCase();
		
		// In most cases there is only one filter method, not need call split
		if (filter.indexOf(FILTER_METHODS_SEPARATOR_CHAR) < 0) {
			Filter handler = (Filter)hFilterHandler.get(filter);
			
			if (handler == null)
				throw new IllegalArgumentException("Invalid filter methods to call WbxAuthWriteTag.filterString: " + filter);

			s = handler.doFilter(s);
		}
		else { 
			String[] filterMethods = filter.split(FILTER_METHODS_SEPARATOR);

			for (int i = 0; i < filterMethods.length; i ++) {
				Filter handler = (Filter)(hFilterHandler.get(filterMethods[i]));

				if (handler == null)
					throw new IllegalArgumentException("Invalid filter methods to call WbxAuthWriteTag.filterString: " + filterMethods[i]);
	
				s = handler.doFilter(s);
			}
		}

		
		return s;
	}
	public static String filterString(String s, String filter)
	{
		if (s == null) return s;

		MyStringBuilder sb = new MyStringBuilder(s);		
		sb = filterString(sb, filter);
		
		return sb.toString();
	}

	
	
	/** Map filter methods name to implementation */
	static final HashMap hFilterHandler;

	/** Create the mapping between escaping methods and escaping actions */
	static {
		// Runtime rt = Runtime.getRuntime();
		// WbxAuth.info("Used memory: " + (rt.totalMemory() - rt.freeMemory()));
		hFilterHandler = new HashMap();
		hFilterHandler.put(FILTER_NONE, new NoneFilter());
		hFilterHandler.put(FILTER_HTML, new HtmlFilter());
		hFilterHandler.put(FILTER_HTMLBR, new HtmlBrFilter());
		hFilterHandler.put(FILTER_JS, new JsFilter());
		hFilterHandler.put(FILTER_JS_OBJ_NAME, new JsObjNameFilter());		
		hFilterHandler.put(FILTER_VS, new VsFilter());
		hFilterHandler.put(FILTER_URL, new UrlFilter());
		hFilterHandler.put(FILTER_URL_PARAM_VALUE, new UrlParamValueFilter());
		hFilterHandler.put(FILTER_RTH, new RthFilter());
		hFilterHandler.put(FILTER_RTH_PURIFY, new RthPurifyFilter());		
		// WbxAuth.info("Used memory after: " + (rt.totalMemory() - rt.freeMemory()));
	}	


	
	
	/** Interface to do string filter */
	abstract static class Filter {
		static final int CHAR_NUM = ((int)(char)(-1)) + 1;

		/* All general unsafe chars for web application.
			HTML and JS escape should use this list.
			Other type of escape can use their own list.
			generalUnsafeCharRange is ordered by unicode value
			generalUnsafeCharRange[i].length == 2 means it is a range of unicode
			generalUnsafeCharRange[i].length != 2 means it is an enum of unicode 
		*/
		static int[][] generalUnsafeCharRange = new int[][] {
			// Latin chars
			new int[] {0, 0x1f},	// before space	x20
			new int[] {0x21, 0x2b},	// before ,-.	x2c-2e
			new int[] {0x2f},		// before 0-9	x30-39
			new int[] {0x3a, 0x40},	// before A-Z	x41-5a
			new int[] {0x5b, 0x5e},	// before _		x5f
			new int[] {0x60},		// before a-z	x61-7a
			new int[] {0x7b, 0x7f},	// after z
			/* 0x80-0xff are Latin chars in ISO-8859-1.
				We can not encode them now, to keep compatible with T26
				and T27 unicode task. They use ISO-8859-1 string to
				represent utf-8 encoded byte stream of unicode string
			new int[] {0x80, 0xff},
			*/

			// Misc mathematics and table symbols
			new int[] {0x2c6, 0x2c7, 0x2c9, 0x2cb},
			new int[] {0x2cd}, new int[] {0x2d0},
			new int[] {0x2d8, 0x2dd},
			new int[] {0x300, 0x301, 0x303, 0x309, 0x323, 0x37e, 0x384, 0x385, 0x387},
			new int[] {0x559, 0x55f}, new int[] {0x589, 0x58a},
			new int[] {0x5b0, 0x5c3}, new int[] {0x5f3, 0x5f4},
			new int[] {0x2018, 0x201e},new int[] {0x2025, 0x2027},
			new int[] {0x2032, 0x2035}, new int[] {0x2039, 0x203c},
			// Japanese symbols
			new int[] {0x3000, 0x301f},
			// Misc symbols
			new int[] {0xfe30, 0xfe6b},
			// Full width ASCII chars
			new int[] {0xff01, 0xff64}, new int[] {0xffe0, 0xffee},
		};
	
		/** getEscape returns the escaping char sequences mapping array
		 *	Derived classes should implement this method properly
		 * */
		abstract char[] getEscape(char c);
		/** formatEscape create the escaping sequence for char c for
		 * current filter class */
		abstract char[] formatEscape(char c);

		static void saveEscape(char[][] charEscape, Filter type, char c)
		{charEscape[c] = type.formatEscape(c);}

		/** Some derived filter class can call this method to init
		 * their char escape mapping array */
		static void initEscapeMapping(char[][] charEscape, Filter type) {
			// Store all unsafe char's escape sequence to mapping array
			int p = 0;
			while (p < generalUnsafeCharRange.length) {
				int[] range = generalUnsafeCharRange[p];
				// range
				if (range.length == 2) {
					for (char c = (char)range[0]; c <= (char)range[1]; c ++)
						saveEscape(charEscape, type, c);
				}
				// enum
				else for (int i = 0; i < range.length; i ++)
					saveEscape(charEscape, type, (char)range[i]);
 
				p ++;
			}
		}
		
		
		/** Entry method to doFilter for current class */
		MyStringBuilder doFilter(MyStringBuilder s) {
			int length = s.length();
			int i;
			

			MyStringBuilder result = new MyStringBuilder(length * 2);

			i = 0;
			while (i < length) {
				char c = s.getChar(i ++);
				char[] escape = getEscape(c);
				
				if (escape == null)
					result.append(c);
				else
					result.append(escape);
			}

			return result;
		}		
	};

	
	
	/** Implementation classes of string filter,
	 * Note that each implementation class only need init sCharEscape properly
	 * 	unless it want more complex process other than simple char escaping
	 **/
	
	static class NoneFilter extends Filter {
		MyStringBuilder doFilter(MyStringBuilder s) {return s;}

		char[] getEscape(char c) {return null;}
		char[] formatEscape(char c) {return null;}
	};


	/**
	 * HTML, Java script string, VB script string use general unsafe char list
	 * The only difference between them is escaping format
	 * */
	static class HtmlFilter extends Filter {
		static char[][] charEscape = new char[CHAR_NUM][];
		char[] getEscape(char c) {return charEscape[c];}
		char[] formatEscape(char c) {return ("&#" + (int)c + ";").toCharArray();}
		
		static {
			initEscapeMapping(charEscape, new HtmlFilter());			
		}
	};

	// HtmlBrFilter encoding '\n' to "<br>"
	static class HtmlBrFilter extends Filter {
		static char[][] charEscape = new char[CHAR_NUM][];
		char[] getEscape(char c) {return charEscape[c];}
		char[] formatEscape(char c) {
			if (c == '\n') return "<br>".toCharArray();
			else return ("&#" + (int)c + ";").toCharArray();
		}
		
		static {
			initEscapeMapping(charEscape, new HtmlBrFilter());			
		}
	};

	static class JsFilter extends Filter {
		static char[][] charEscape = new char[CHAR_NUM][];
		char[] getEscape(char c) {return charEscape[c];}

		char[] formatEscape(char c) {
			String s = Integer.toHexString(c);
			if (c < 0x100) {
				if (s.length() != 2) s = "0" + s;
				s = "\\x" + s;
			}
			else {
				if (s.length() != 4) s = "0" + s;
				s = "\\u" + s;
			}
			
			return s.toCharArray();
		}

		static {
			initEscapeMapping(charEscape, new JsFilter());			
		}
	};

	// For "formObj" in "opener.formObj"
	static class JsObjNameFilter extends Filter {
		static char[][] charEscape = new char[CHAR_NUM][];
		char[] getEscape(char c) {return charEscape[c];}

		char[] formatEscape(char c) {
			// For "formObj" in "opener.formObj"
			if (c == '[' || c == ']') return null;
			
			String s = Integer.toHexString(c);
			if (c < 0x100) {
				if (s.length() != 2) s = "0" + s;
				s = "\\x" + s;
			}
			else {
				if (s.length() != 4) s = "0" + s;
				s = "\\u" + s;
			}
			
			return s.toCharArray();
		}

		static {
			initEscapeMapping(charEscape, new JsObjNameFilter());			
		}
	};	

	static class VsFilter extends Filter {
		/** VB script string output should be few, so we calculate escape
		real time, instead of using mapping array */
		char[] getEscape(char c) {return formatEscape(c);}

		// "abc*def" => "abc"&chrw(42)&"def" 
		char[] formatEscape(char c) {
			return ("\"&chrw(" + (int)c + ")&\"").toCharArray();
		}
	};
	
	/**
	 * This class only process URLs with un-supported protocol
	 * 
	 * It will use both white-list and black-list method
	 * 	to verify protocol legality
	 */
	static class UrlFilter extends NoneFilter {
		public final static String INVALID_URL = "WAAF_INVALID_URL";    
		
		static final String PROTOCOL_SUFFIX = "://";
		/** Confirmed with Paul Chen that these legal protocols can meet
		 * WebEx web application's function requirement */
		static final String[] LEGAL_PROTOCOLS = new String[] {
			"https", "http", "ftp", "tftp", "ftps", "sftp", "mailto", 	//parasoft-suppress BD.SECURITY.WEBHTTP "Confirmed by security team that there is no risk because it was not webex.com domain"		
			/* Following are illegal protocols
			"javascript", "vbscript",
			"dict", "ldap", "file", "gopher", "scp", "news",
			"mms", "rtsp", "pnm", "telnet", "nntp", "wais", "prospero" */
		};
		static final HashMap hLegalProtocols = new HashMap();

		static final String ILLEGAL_PROTOCOL_SUFFIX = ":";
		static final String[] ILLEGAL_PROTOCOLS = new String[] {
			"javascript", "vbscript","data"
		};
		static final HashMap hIllegalProtocols = new HashMap();
		
		static {
			for (int i = 0; i < LEGAL_PROTOCOLS.length; i ++)
				hLegalProtocols.put(LEGAL_PROTOCOLS[i], new Boolean(true));
			for (int i = 0; i < ILLEGAL_PROTOCOLS.length; i ++)
				hIllegalProtocols.put(ILLEGAL_PROTOCOLS[i], new Boolean(true));
		}

		MyStringBuilder doFilter(MyStringBuilder sb) {
			String s = sb.toString();

			/* Disable normal protocol checking for two reasons:
			1. It cannot handle some non-stanard URL string like: default.do?backUrl=http://go.webex.com
			2. There is no known attacking vectors like anyProtocol://xxx.yyy
			
			int protocolPos = s.indexOf(PROTOCOL_SUFFIX);

			// If URL contains :// then need check protocol legality(white-list)
			if (protocolPos > 0) {
				// Comparing in lower case
				String protocol = s.substring(0, protocolPos).trim().toLowerCase();

				// Illegal protocol
				if (hLegalProtocols.get(protocol) == null) {
					return new MyStringBuilder(INVALID_URL);
				}
			}
			*/

			int illegalProtocolPos = s.indexOf(ILLEGAL_PROTOCOL_SUFFIX);

			// If URL contains : then need check protocol legality(black-list)
			if (illegalProtocolPos > 0) {
				// Comparing in lower case
				String illegalProtocol = s.substring(0, illegalProtocolPos).trim().toLowerCase();

				// Illegal protocol
				if (hIllegalProtocols.get(illegalProtocol) != null) {
					return new MyStringBuilder(INVALID_URL);
				}
			}			

			return sb;
		}
	};

	/** We use the logic of java.net.URLEncoder to encode URL param value
	 **/
	static class UrlParamValueFilter extends Filter {
		static char[][] charEscape = new char[CHAR_NUM][];

		char[] getEscape(char c) {

			return charEscape[c];
		}
		char[] formatEscape(char c) {
			String s = null;
			
			try {
				/* !!! Attention !!!
					Here we use ISO_8859_1 to keep compatible with T27 unicode task
					It should be replaced to UTF-8 in future
				 */
				s = java.net.URLEncoder.encode(new String(new char[]{c}), "iso-8859-1");
			} catch (Exception e) {
			}

			// URLEncoder.encode returns null means this char should be removed
			if (s == null) return new char[]{};
			else if (s.length() > 1 || s.length() == 1 && s.charAt(0) == '+') return s.toCharArray();
			else return null;
		}

		UrlParamValueFilter() {
			// Note, this "for" loop need about 1 second in a PIV 2.8G CPU PC
			for (int c = 0; c < charEscape.length; c ++) {
				charEscape[c] = formatEscape((char)c);
			}
		}
	};
	
	
	/** Rich text HTML filtering 
	 *	Not implemented yet
	 * */
	static class RthFilter extends NoneFilter {
		RthFilter() {
			super();
		}
		MyStringBuilder doFilter(MyStringBuilder sb) {
			return sb;
		}
	};

	/** Purify rich text html to pure text (discard all HTML tags) 
	 * */
	static class RthPurifyFilter extends NoneFilter {
		final static char TAG_START = '<';
		final static char TAG_END = '>';

		MyStringBuilder doFilter(MyStringBuilder sb) {
			int size = sb.length();

			MyStringBuilder sb2 = new MyStringBuilder(size);

			/** Discard all HTML tag first */
			int p = 0;
			boolean allOver = false;
			while (p < size) {
				char c = sb.getChar(p ++);

				// Found tag start char
				while (c == TAG_START) {
					// Skip subsequent chars until tag end or string end
					while (p < size && sb.getChar(p) != TAG_END) p ++;
					// Only last one > char, or string end, break loop
					if (p >= size - 1) {
						allOver = true;
						break;
					}

					// Skip >
					p ++;
					// Get next char after >, note: it may be another <
					c = sb.getChar(p ++);
				}

				if (allOver) break;

				sb2.append(c);
			}
			
			return sb2;
		}
	};
}

