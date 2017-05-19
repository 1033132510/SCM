package com.zzc.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 由于SimpleDateFormat不是线程安全，并且在格式化时间时想要减少创建的DateFormat对象并且实现线程安全，则可使用此工具类
 * 
 * @author chenjiahai
 *
 */
public class ConcurrentDateUtil {

	private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	private static ThreadLocal<DateFormat> threadLocal = new ThreadLocal<DateFormat>();

	public static DateFormat getDateFormat(String pattern) {
		DateFormat dateFormat = threadLocal.get();
		if (dateFormat == null) {
			dateFormat = new SimpleDateFormat(pattern);
			threadLocal.set(dateFormat);
		}
		return dateFormat;
	}

	public static DateFormat getDateFormat() {
		return getDateFormat(DEFAULT_DATE_FORMAT);
	}

	public static String formatDate(Date date) throws ParseException {
		return formatDate(date, DEFAULT_DATE_FORMAT);
	}

	public static String formatDate(Date date, String pattern)
			throws ParseException {
		return getDateFormat(pattern).format(date);
	}

	public static Date parse(String dateString) throws ParseException {
		return parse(dateString, DEFAULT_DATE_FORMAT);
	}

	public static Date parse(String dateString, String pattern)
			throws ParseException {
		return getDateFormat(pattern).parse(dateString);
	}
}
