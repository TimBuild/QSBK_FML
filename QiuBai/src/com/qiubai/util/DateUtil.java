package com.qiubai.util;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * @author MGC10
 * 工具类，获得当前的时间
 *
 */
public class DateUtil {
	public static String getCurrentTime(String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
		String currentTime = sdf.format(System.currentTimeMillis());
		return currentTime;
	}
	
	public static String getCurrentTime(){
		String format = "yyyy-MM-dd HH:mm:ss";
		return getCurrentTime(format);
	}
}
