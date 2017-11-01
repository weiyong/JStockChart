package com.weiyong.jstockchart.utils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.FastDateFormat;


public class StringUtils extends org.apache.commons.lang3.StringUtils {

	
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");    
	public static final String PATTERN_ISO_ON_DATETIME = "yyyyMMddHHmmss";
	public static final String PATTERN_ISO_DEFAULT_DATETIME = "yyyy-MM-dd HH:mm:ss";

	
	public static String getNowDate() {
		return FastDateFormat.getInstance(PATTERN_ISO_ON_DATETIME).format(new Date());
	}
	
	
	/**
	 * 获取日期字符串 格式（yyyy）
	 * 
	 * @param date
	 * @return
	 */
	public static String getYear(final String date) {
		return date == null ? null : date.replaceAll("(\\d{4})(\\d{2})(\\d{2})(\\d{2})(\\d{2})(\\d{2})", "$1");
	}

	/**
	 * 获取日期字符串 格式（MM）
	 * 
	 * @param date
	 * @return
	 */
	public static String getMonth(final String date) {
		return date == null ? null : date.replaceAll("(\\d{4})(\\d{2})(\\d{2})(\\d{2})(\\d{2})(\\d{2})", "$2");
	}

	/**
	 * 获取日期字符串 格式（dd）
	 * 
	 * @param date
	 * @return
	 */
	public static String getDay(final String date) {
		return date == null ? null : date.replaceAll("(\\d{4})(\\d{2})(\\d{2})(\\d{2})(\\d{2})(\\d{2})", "$3");
	}
	
	
	/**
	 * 获取日期字符串 格式（MMdd）
	 * 
	 * @param date
	 * @return
	 */
	public static String getDate(final String date) {
		return date == null ? null : date.replaceAll("(\\d{4})(\\d{2})(\\d{2})(\\d{2})(\\d{2})(\\d{2})", "$2$3");
	}
	
	
	/**
	 * 获取日期字符串 格式（HH:mm）
	 * 
	 * @param date
	 * @return
	 */
	public static String getTime(final String date) {
		return date == null ? null : date.replaceAll("(\\d{4})(\\d{2})(\\d{2})(\\d{2})(\\d{2})(\\d{2})", "$4:$5");
	}
	
	
	/**
	 * 得到当天八点的日期
	 * @throws ParseException 
	 */
	public static String beginOfHour() throws ParseException {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.HOUR_OF_DAY, 8);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return formatDate(calendar.getTime(), PATTERN_ISO_ON_DATETIME);
	}
	
	public static int subMinutes(String startDate, String endDate) throws ParseException {
		int start = Integer.parseInt(formatDate(startDate, "HHmm"));
		int end = Integer.parseInt(formatDate(endDate, "HHmm"));
		return end - start;
	}

	public static String formatDate(Date date, Object... pattern) throws ParseException {
		return DateFormatUtils.format(date, pattern[0].toString());
	}
	
	public static String formatDate(String date, Object... pattern) throws ParseException {
		Date datetime = FastDateFormat.getInstance(PATTERN_ISO_ON_DATETIME).parse(date);
		return DateFormatUtils.format(datetime, pattern[0].toString());
	}
	
	/**
	 * JAVA自动适配Linux与Windows文件路径分隔符
	 * @param path
	 * @return
	 */
    public static String getRealFilePath(String path) {  
        return path.replace("/", FILE_SEPARATOR).replace("\\", FILE_SEPARATOR);  
    }  
  
}
