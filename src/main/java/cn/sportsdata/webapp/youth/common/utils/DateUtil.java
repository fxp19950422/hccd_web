package cn.sportsdata.webapp.youth.common.utils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


/**
 * 时间类型的工具类。 主要包含所有针对时间型的类型数据操作的工具方法
 * 
 * @author wangye
 * @version 1.0 2011-4-27
 * @since 1.5
 */
public class DateUtil {
	
	public static final long DAY_MILLISECONDS = 1000 * 3600 * 24;
	public static final int UPGRADE_MONTH = 6;

	/**
	 * 将一个date型变量转换成为BigDecimal型。 将一个date型转换成BigDecimal，转换后的形式为20110918.091823
	 * 
	 * @param date
	 *            日期型变量
	 * @return 转换后的变量
	 */
	public static BigDecimal date2BigDecimal(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd.HHmmss");
		String s = sdf.format(date);
		return BigDecimal.valueOf(Double.valueOf(s));
	}

	/**
	 * 将当前时间转换成为float型对象
	 * 
	 * @return float型对象
	 */
	public static BigDecimal nowDate2BigDecimal() {
		Date date = new Date();
		return date2BigDecimal(date);
	}

	/**
	 * 将一个BigDecimal型数字转换成一个日期
	 * 
	 * @param bigdecimal
	 * @return
	 */
	public static Date bigDecimal2Date(BigDecimal bigdecimal) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd.HHmmss");
		Date date;
		try {
			date = sdf.parse(bigdecimal.setScale(6, BigDecimal.ROUND_HALF_UP).toString());
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 日期型格式化成字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String date2String(Date date) {
		return date2String(date, "yyyy-MM-dd");
	}

	/**
	 * 日期型格式化成字符串
	 * 
	 * @param date
	 * @param formate
	 * @return
	 */
	public static String date2String(Date date, String formate) {
		SimpleDateFormat sdf = new SimpleDateFormat(formate);
		String result = sdf.format(date);
		return result;
	}

	/**
	 * 字符串转换成日期
	 * 
	 * @param value
	 * @return
	 */
	public static Date string2Date(String source, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.parse(source);
		} catch (ParseException e) {
			e.printStackTrace();
			return new Date();
		}
	}
	
	public static Date string2Date2(String source, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.parse(source);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}


	/**
	 * 功能 : 大数字的日期转换成字符串
	 * <p/>
	 * 开发：wangye 2013-2-10
	 * 
	 * @param source
	 *            大数字日期
	 * @return
	 */
	public static String bigDecimal2String(BigDecimal source) {
		Date date = bigDecimal2Date(source);
		return date2String(date, "yyyy-MM-dd HH:mm");
	}

	/**
	 * 功能 : 大数字的日期转换成字符串
	 * <p/>
	 * 开发：wangye 2013-2-10
	 * 
	 * @param source
	 *            大数字日期
	 * @param pattern
	 *            格式
	 * @return
	 */
	public static String bigDecimal2String(BigDecimal source, String pattern) {
		if (source.compareTo(new BigDecimal(0)) == 0) {
			return "";
		} else {
			Date date = bigDecimal2Date(source);
			return date2String(date, pattern);
		}
	}

	public static boolean isStringValidDate(String date) {
		if (date == null || date.equals("")) {
			return false;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			sdf.parse(date);
			return true;
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 时间戳转换为dateFormat
	 * 
	 * @param beginDate
	 * @return
	 */
	public static String timestampToDate(long date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sd = sdf.format(date);
		return sd;
	}
	
	/**
	 * 时间戳转换为dateFormat
	 * 
	 * @param beginDate
	 * @return
	 */
	public static String timestampToDate(long date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String sd = sdf.format(date);
		return sd;
	}

	


	public static int getCurrentYear() {
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.YEAR);
	}

	public static int getCurrentMonth() {
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.MONTH) + 1;
	}

	/**
	 * 检查两个日期是否前后顺序
	 * 
	 * @param startDate1
	 * @param endDate1
	 * @return
	 */
	public static boolean checkDateSeq(String startDate, String endDate, boolean isContain) {

		Date start = string2Date(startDate, "yyyy-MM-dd");
		Date end = string2Date(endDate, "yyyy-MM-dd");
		if (start.before(end)) {
			return true;
		}
		if (isContain && start.compareTo(end) == 0) {
			return true;
		}
		return false;
	}

	public static boolean checkTimeSeq(String startTime, String endTime) {
		Date start = string2Date(startTime, "HH:mm");
		Date end = string2Date(endTime, "HH:mm");
		if (start.before(end)) {
			return true;
		}
		return false;
	}

	public static String convertFormat(String date) {
		return convertFormat(date, "yyyy-MM-dd", "MM/dd/yyyy");
	}

	public static String convertFormat(String date, String before, String after) {
		return DateUtil.date2String(DateUtil.string2Date(date, before), after);
	}
	
	public static String convertFormat2(String date, String before, String after) {
		return DateUtil.date2String(DateUtil.string2Date2(date, before), after);
	}
	
	public static Date getDateFromStr(String date){
		return getDateFromStr(date, "yyyy-MM-dd");
	}
	
	public static Date getDateFromStr(String dateStr, String format){
	
		try  
		{  
		    SimpleDateFormat sdf = new SimpleDateFormat(format);  
		    Date date = sdf.parse(dateStr); 
		    return date;
		}  
		catch (ParseException e)  
		{  
		    System.out.println(e.getMessage());  
		    return null;
		}  
	}

	public static boolean checkNowDate(String objDate, String format) {
		Date compared = string2Date(objDate, format);
		Date now = new Date();
		if (compared.before(now)) {
			return false;
		}
		return true;
	}

	public static boolean isBetween(String startDate, String endDate) {
		Calendar c = Calendar.getInstance();
		String now = c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DAY_OF_MONTH);
		if (checkDateSeq(startDate, now, true) && checkDateSeq(now, endDate, true)) {
			return true;
		}
		return false;
	}

//	public static int getStartWeekIndex(String startDate) {
//		Date start = string2Date(startDate, "yyyy-MM-dd");
//		Calendar c = Calendar.getInstance();
//		c.setTime(start);
//		return c.get(Calendar.WEEK_OF_YEAR);
//	}

	public static int getWeekOfYear(String date) {
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			// 这一句必须要设置，否则美国认为第一天是周日，而我国认为是周一，对计算当期日期是第几周会有错误
			cal.setFirstDayOfWeek(Calendar.MONDAY); // 设置每周的第一天为星期一
			cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);// 每周从周一开始
			cal.setMinimalDaysInFirstWeek(1); // 设置每周最少为1天
			cal.setTime(df.parse(date));
			return cal.get(Calendar.WEEK_OF_YEAR);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public static int getWeekOfYear(long timestamp) {
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(timestamp);
			// 这一句必须要设置，否则美国认为第一天是周日，而我国认为是周一，对计算当期日期是第几周会有错误
			cal.setFirstDayOfWeek(Calendar.MONDAY); // 设置每周的第一天为星期一
			cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);// 每周从周一开始
			cal.setMinimalDaysInFirstWeek(1); // 设置每周最少为1天
			return cal.get(Calendar.WEEK_OF_YEAR);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public static int getMyYear(String date){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(df.parse(date));
			return cal.get(Calendar.YEAR);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

	
	/** 
     * 计算指定年度共有多少个周。 
     * @param year 格式 yyyy  ，必须大于1900年度 小于9999年  
     * @return  
     */  
    public static int getWeekNumByYear(final int year){  
        if(year<1900 || year >9999){  
            throw new NullPointerException("年度必须大于等于1900年小于等于9999年");  
        }  
        int result = 52;//每年至少有52个周 ，最多有53个周。  
        String date = getYearWeekFirstDay(year,53);  
        if(date.substring(0, 4).equals(year+"")){ //判断年度是否相符，如果相符说明有53个周。  
            result = 53;  
        }  
        return result;  
    }
    
    public static int getWeekIndexFromStartDate(Date startDate, Calendar currentCal) {
    	Calendar startCal = Calendar.getInstance();
    	startCal.setTime(startDate);
    	startCal.set(startCal.get(Calendar.YEAR), startCal.get(Calendar.MONTH), startCal.get(Calendar.DATE), 0, 0, 0);
    	startCal.set(Calendar.MILLISECOND, 0);
    	
    	Calendar currentTemCal = Calendar.getInstance();
    	currentTemCal.set(currentCal.get(Calendar.YEAR), currentCal.get(Calendar.MONTH), currentCal.get(Calendar.DATE), 0, 0, 0);
    	currentTemCal.set(Calendar.MILLISECOND, 0);
    	int index = 1;
    	// start week is same as current week
    	if (isInSameWeek(startCal, currentTemCal)) {
    		return index;
    	}
    	
    	do{
    		index++;
    		startCal.add(Calendar.WEEK_OF_YEAR, 1);
    	}while(!isInSameWeek(startCal, currentTemCal) && startCal.getTimeInMillis() < currentTemCal.getTimeInMillis());
    	
    	return index;
    	
    }
    
    public static boolean isInSameWeek(Calendar startCal, Calendar currentTemCal) {
    	return startCal.get(Calendar.YEAR) == currentTemCal.get(Calendar.YEAR) && startCal.get(Calendar.WEEK_OF_YEAR) == currentTemCal.get(Calendar.WEEK_OF_YEAR);
    }
    
    /** 
     * 计算某年某周的开始日期 
     * @param yearNum 格式 yyyy  ，必须大于1900年度 小于9999年  
     * @param weekNum 1到52或者53 
     * @return 日期，格式为yyyy-MM-dd 
     */  
    public static String getYearWeekFirstDay(int yearNum,int weekNum)  {  
        if(yearNum<1900 || yearNum >9999){  
            throw new NullPointerException("年度必须大于等于1900年小于等于9999年");  
        }  
         Calendar cal = Calendar.getInstance();  
         cal.setFirstDayOfWeek(Calendar.MONDAY); //设置每周的第一天为星期一  
         cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);//每周从周一开始  
//       上面两句代码配合，才能实现，每年度的第一个周，是包含第一个星期一的那个周。  
         cal.setMinimalDaysInFirstWeek(7);  //设置每周最少为7天       
         cal.set(Calendar.YEAR, yearNum);  
         cal.set(Calendar.WEEK_OF_YEAR, weekNum);  
          
         //分别取得当前日期的年、月、日  
//         return getFormatDate(cal.getTime());  
         return date2String(cal.getTime(), "yyyy-MM-dd");
    }

	public static int getDayInWeek(long timestamp) {
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(timestamp);
			return cal.get(Calendar.DAY_OF_WEEK) - 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}  
	
	
	/**
	 * Compare two date by day based on local time zone
	 * @param date1
	 * @param date2
	 * @return day count of (date1 - date2)
	 */
	public static int compareDateOnLocal(long date1, long date2) {
	    long offset = TimeZone.getDefault().getRawOffset();
	    
		long nDate1 = (date1 + offset) / DAY_MILLISECONDS;
        long nDate2 = (date2 + offset) / DAY_MILLISECONDS;
        
        if (nDate1 == nDate2){
            return 0;
        } else {
            return nDate1 > nDate2 ? 1 : -1;
        }
	}
	
    public static boolean isSameDayOnLocal(long date1, long date2) {
        return compareDateOnLocal(date1, date2) == 0;
    }
    
    
    /**
     * <p>Checks if a date is today.</p>
     * @param date the date, not altered.
     * @return true if the date is today.
     * @throws IllegalArgumentException if the date is <code>null</code>
     */
    public static boolean isTodayOnLocal(long date) {
        return isSameDayOnLocal(date, System.currentTimeMillis());
    }
    
    public static boolean isBeforeDayOnLocal(long date1, long date2) {
        return compareDateOnLocal(date1, date2) < 0;
    }
      
    public static boolean isAfterDayOnLocal(long date1, long date2) {
        return compareDateOnLocal(date1, date2) > 0;
    }
    
    public static Date clearTimeOnLocal(Date date) {
        if (date == null) {
            return null;
        }
        
        return clearTimeOnLocal(date.getTime());
    }
    
    public static Date clearTimeOnLocal(long time) {
        return new Date(clearTimeforLongOnLocal(time));
    }
    
    public static long clearTimeforLongOnLocal(long time) {
    	
        long offset = TimeZone.getDefault().getRawOffset();
        return (time + offset) / DAY_MILLISECONDS * DAY_MILLISECONDS - offset;
    }
    
    public static long getLastTimeForLongOnLocal(long time){
    	 return time  + DAY_MILLISECONDS - 1;
    }
    
    /**
     * date2比date1多的天数
     * @param date1    
     * @param date2
     * @return    
     */
    public static Integer differentDays(Date date1,Date date2)
    {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
       int day1= cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);
        
        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if(year1 != year2)   //同一年
        {
            int timeDistance = 0 ;
            for(int i = year1 ; i < year2 ; i ++)
            {
                if(i%4==0 && i%100!=0 || i%400==0)    //闰年            
                {
                    timeDistance += 366;
                }
                else    //不是闰年
                {
                    timeDistance += 365;
                }
            }
            
            return timeDistance + (day2-day1) ;
        }
        else    //不同年
        {
            System.out.println("判断day2 - day1 : " + (day2-day1));
            return day2-day1;
        }
    }
  
    public static String hello(String name) {

		return name;
	}
    
}
