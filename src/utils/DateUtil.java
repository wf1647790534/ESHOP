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
	 * ��ȡ��ǰ����ʱ��
	 * @return
	 */
	public static String getDate(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return sdf.format(new Date());
	}
	
	/**
	 * ��ȡ��ǰ����
	 * @return
	 */
	public static String getDateStr(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String string = sdf.format(new Date());
		return string;
	}
	
	/**
	 * ��ȡ��ǰʱ��
	 * @return
	 */
	public static String getTimeStr(){
		SimpleDateFormat sdf = new SimpleDateFormat("hhmmss");
		return sdf.format(new Date());
	}
}
