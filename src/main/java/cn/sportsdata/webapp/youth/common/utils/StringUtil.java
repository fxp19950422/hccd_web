package cn.sportsdata.webapp.youth.common.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 对字符串操作的工具类
 * 
 * @author qhliu
 * @since 1.5
 */
public class StringUtil {
	/**
	 * 首字母大写
	 * 
	 * @param srcStr
	 * @return
	 */
	public static String firstCharacterToUpper(String srcStr) {
		return srcStr.substring(0, 1).toUpperCase() + srcStr.substring(1);
	}

	public static boolean isNumeric(String str)
	{
	  return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
	}
	/**
	 * 替换字符串并让它的下一个字母为大写
	 * 
	 * @param srcStr
	 * @param org
	 * @param ob
	 * @return
	 */
	public static String replaceUnderlineAndfirstToUpper(String srcStr,
			String org, String ob) {
		String newString = "";
		srcStr = srcStr.toLowerCase();
		int first = 0;
		while (srcStr.indexOf(org) != -1) {
			first = srcStr.indexOf(org);
			if (first != srcStr.length()) {
				newString = newString + srcStr.substring(0, first) + ob;
				srcStr = srcStr
						.substring(first + org.length(), srcStr.length());
				srcStr = firstCharacterToUpper(srcStr);
			}
		}
		newString = newString + srcStr;
		return newString;
	}

	/**
	 * 转换HTML语言中的字符
	 * 
	 * @param source
	 * @return
	 */
	public static String changeHTML(String source) {
		String str = source;
		// str=str.replace(">", "&gt;");
		// str=str.replace("<", "&lt;");
		// str=str.replace(" ", "&nbsp;");
		// str=str.replace("&", "&amp");
		return str;
	}

	/**
	 * 获取文件格式（即后缀名）
	 * 
	 * @param path
	 *            文件
	 * @return 文件格式
	 */
	public static String getFileLayout(String path) {
		if (path != null && !"".equals(path) && path.indexOf(".") != -1) {
			return path.substring(path.lastIndexOf(".") + 1, path.length());
		} else {
			return null;
		}

	}

	public static String delHTMLTag(String htmlStr) {
		String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
		String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
		String regEx_iframe = "<iframe[^>]*?>[\\s\\S]*?<\\/iframe>"; // 定义iframe的正则表达式
//		String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式

		Pattern p_script = Pattern.compile(regEx_script,
				Pattern.CASE_INSENSITIVE);
		Matcher m_script = p_script.matcher(htmlStr);
		htmlStr = m_script.replaceAll(""); // 过滤script标签

		Pattern p_style = Pattern
				.compile(regEx_style, Pattern.CASE_INSENSITIVE);
		Matcher m_style = p_style.matcher(htmlStr);
		htmlStr = m_style.replaceAll(""); // 过滤style标签

		Pattern p_iframe = Pattern.compile(regEx_iframe,
				Pattern.CASE_INSENSITIVE);
		Matcher m_iframe = p_iframe.matcher(htmlStr);
		htmlStr = m_iframe.replaceAll(""); // 过滤iframe标签

		// Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE);
		// Matcher m_html=p_html.matcher(htmlStr);
		// htmlStr=m_html.replaceAll(""); //过滤html标签

		return htmlStr.trim(); // 返回文本字符串
	}

	public static String htmlFilter(String value) {
		if ((value == null) || (value.length() == 0)) {
			return value;
		}
		StringBuffer result = null;
		String filtered = null;

		for (int i = 0; i < value.length(); i++) {
			filtered = null;

			switch (value.charAt(i)) {
			// case '<':
			// filtered = "&lt;";
			// break;
			// case '>':
			// filtered = "&gt;";
			// break;
			case '&':
				filtered = "&";
				break;
			case '"':
				filtered = "";
				break;
			case '\'':
				filtered = "\\\'";
				break;
			case '\\':
				filtered = "\\\\";
				break;
			}

			if (result == null) {
				if (filtered != null) {
					result = new StringBuffer(value.length() + 50);

					if (i > 0) {
						result.append(value.substring(0, i));
					}

					result.append(filtered);
				}
			} else {
				if (filtered == null) {
					result.append(value.charAt(i));
				} else {
					result.append(filtered);
				}
			}
		}
		value = (result == null) ? value : result.toString();

		// value = value.replace("<script>", "");

		return value;
	}

	/**
	 * 功能 : 格式化字符串
	 * <p/>
	 * 开发：bintang 2012-7-26
	 * 
	 * @param str
	 *            源字符串
	 * @param values
	 *            参数
	 * @return 格式化后的字符串
	 */
	public static String format(String str, Object... values) {
		String result = str;

		for (int i = 0; i < values.length; i++) {
			result = result.replaceAll("\\{" + i + "\\}", values[i] + "");
		}

		return result;
	}

	public static boolean isNotBlank(String str) {
		return str != null && !str.trim().equals("null")
				&& !str.trim().equals("NaN") && !"0".equals(str.trim())
				&& str.trim().length() != 0;
	}

	public static boolean isBlank(String str) {
		return !isNotBlank(str);
	}
	
	public static boolean isNotEmpty(String str) {
		return str != null && !str.trim().equalsIgnoreCase("null")
				&& str.trim().length() != 0;
	}

	public static boolean isEmpty(String str) {
		return !isNotEmpty(str);
	}

	/**
	 * 判断某个字符串是否存在于数组中
	 * 
	 * @param stringArray
	 *            原数组
	 * @param source
	 *            查找的字符串
	 * @return 是否找到
	 * @author zzj
	 */
	public static boolean contains(String[] stringArray, String source) {
		// 转换为list
		List<String> tempList = Arrays.asList(stringArray);

		// 利用list的包含方法，进行判断
		if (tempList.contains(source)) {
			return true;
		}
		return false;
	}

	/**
	 * 获得指定长度的字符串,65~90
	 * 
	 * @param size
	 * @return
	 */
	public static String getRandomString(int size) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < size; ++i) {
			// Random rdm = new Random(System.currentTimeMillis());
			double number = Math.random();
			int t = (int) (number * (90 - 65));
			char c = (char) (t + 65);
			sb.append(c);
		}
		return sb.toString();
	}

	/**
	 * 获取字符串的长度，中文占一个字符,英文数字占半个字符
	 * 
	 * @param value
	 *            指定的字符串
	 * @return 字符串的长度
	 */
	public static double length(String value) {
		double valueLength = 0;
		String chinese = "[\u4e00-\u9fa5\uFE30-\uFFA0]";
		// 获取字段值的长度，如果含中文字符，则每个中文字符长度为1，否则为0.5
		for (int i = 0; i < value.length(); i++) {
			// 获取一个字符
			String temp = value.substring(i, i + 1);
			// 判断是否为中文字符
			if (temp.matches(chinese)) {
				// 中文字符长度为1
				valueLength += 1;
			} else {
				// 其他字符长度为0.5
				valueLength += 0.5;
			}
		}
		// 进位取整
		return Math.ceil(valueLength);
	}
	/**
	 * 指定字符右切割字符串。
	 * /upload/400_100_FJLSD.jpg => 400_100_FJLSD.jpg
	 * 
	 * @param value
	 * @param _char
	 * @return
	 */
	public static String rightSplit(String value, String _char) {
		int index = value.lastIndexOf(_char);
		
		if (index < 0) {
			return "";
		}
		
		return value.substring(index + 1, value.length());
	}
	
	/**
	 * 获取字符按指定长度截断，区分中英文。 中文长度为1，英文为0.5
	 * 
	 * @param value
	 * @param num
	 * @return
	 */
	public static String substring(String value, int num) {
		double valueLength = 0;
		String chinese = "[\u4e00-\u9fa5\uFE30-\uFFA0]";

		int strLen = value.length();
		StringBuffer result = new StringBuffer();

		// 获取字段值的长度，如果含中文字符，则每个中文字符长度为1，否则为0.5
		for (int i = 0; i < strLen; i++) {
			// 获取一个字符
			String temp = value.substring(i, i + 1);
			// 加入结果
			result.append(temp);
			// 判断是否为中文字符
			if (temp.matches(chinese)) {
				// 中文字符长度为1
				valueLength += 1;
			} else {
				// 其他字符长度为0.5
				valueLength += 0.5;
			}
			// 如果已经超过指定字符长度
			if (valueLength > num) {
				return result.substring(0, i) + "...";
			} else if (valueLength == num) {
				if (i < strLen) {
					return result.toString() + "...";
				} else {
					return result.toString();
				}
			}
		}

		return result.toString();
	}

	/**
	 * 判断一个字符串是否匹配一个正则
	 * 
	 * @param src
	 * @param des
	 * @return
	 */
	public static boolean isMatching(String src, String des) {

		String des1 = des.replace("*", "\\w*");
		des1 = des1.replace("?", "\\w{1}");
		Pattern p = Pattern.compile(des1);
		Matcher m = p.matcher(src);
		return m.matches();
	}

	
	
	public static String getString2Server(String param){
		try {
			return URLEncoder.encode(param, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String getCurrentSchoolYear(){
		int year = 0;
		int month = 0;

		Calendar calendar = Calendar.getInstance();
		/**
		 * 获取年份
		 */
		year = calendar.get(Calendar.YEAR);
		/**
		 * 获取月份
		 */
		month = calendar.get(Calendar.MONTH) + 1;

		if (month < 9) {
			year--;
		}
		return String.valueOf(year);
	}
	
	public static boolean isBooleanStr(String str) {
		if (isEmpty(str)) {
			return false;
		}
		if ("1".equals(str.trim()) || "true".equalsIgnoreCase(str.trim())) {
			return true;
		}
		return false;
	}
	
	public static String formatDouble(Double d) {
		if (d != null){
			DecimalFormat df = new DecimalFormat();
			df.applyPattern("0.#");
			return df.format(d);
		} else {
			return "";
		}
	}
	
	public static String getAge(Date birthDay)  {  
		if (birthDay == null) return "0";
		
        Calendar cal = Calendar.getInstance();  
  
        if (cal.before(birthDay)) {  
        	 return "0"; 
        }  
        int yearNow = cal.get(Calendar.YEAR);  
        int monthNow = cal.get(Calendar.MONTH);  
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);  
        cal.setTime(birthDay);  
  
        int yearBirth = cal.get(Calendar.YEAR);  
        int monthBirth = cal.get(Calendar.MONTH);  
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);  
  
        int age = yearNow - yearBirth;  
  
        if (monthNow <= monthBirth) {  
            if (monthNow == monthBirth) {  
                if (dayOfMonthNow < dayOfMonthBirth) age--;  
            }else{  
                age--;  
            }  
        }  
        return String.valueOf(age);  
    }  
}