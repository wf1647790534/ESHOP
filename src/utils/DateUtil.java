package utils;

import java.text.SimpleDateFormat;
import java.util.Date;


public class DateUtil {

	
	public static String getDateStr(Date d)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return sdf.format(d);
	}
	
	/**
	 * 获取当前日期时间
	 * @return
	 */
	public static String getDate(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return sdf.format(new Date());
	}
	
	/**
	 * 获取当前日期
	 * @return
	 */
	public static String getDateStr(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String string = sdf.format(new Date());
		return string;
	}
	
	/**
	 * 获取当前时间
	 * @return
	 */
	public static String getTimeStr(){
		SimpleDateFormat sdf = new SimpleDateFormat("hhmmss");
		return sdf.format(new Date());
	}
}
