package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 该类用来加密字符串，解密字符串，使用加密解密方法为MD5
 * @author 悦尔
 * @version 1
 */
public class MD5Util {

	public MD5Util() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 全局数组，加密后的字符串都是有该数组中的字符组成的。
	 */
	private final static char[] chars = {
			'0','1','2','3',
			'4','5','6','7',
			'8','9','a','b',
			'c','d','e','f'
	}; 
	
	/**
	 * 通过bByte的值，取出chars数组中的两个字符组成一个字符串
	 * @param bByte
	 * @return	String类型变量
	 */
	private static String byteToArrayString(byte bByte){
		int iRet = bByte;
		if(iRet < 0)
			iRet += 256;
		int index1 = iRet / 16;
		int index2 = iRet % 16;
		
		return String.valueOf(chars[index1])+String.valueOf(chars[index2]);
	}
	
	
	/**
	 * 由bByte转化为一个字符串，不过这个干嘛用的
	 * @param bByte
	 * @return
	 */
	@SuppressWarnings("unused")
	private static String byteToNum(byte bByte){
		
		int iRet = bByte ;
		if(iRet < 0)
			iRet += 256;
		
		return String.valueOf(iRet);
	}
	
	/**
	 * 将bByte数组转化为String对象。该对象中字符都是chars字符组中的字符
	 * 
	 * @param bByte
	 * @return
	 */
	private static String byteToString(byte[] bByte){
		
		StringBuffer buffer = new StringBuffer();
		int len = bByte.length;
		for(int i = 0; i < len; i++){
			buffer.append(byteToArrayString(bByte[i]));
		}
		
		return buffer.toString();
	}
	
	/**
	 * 返回加密后的字符串
	 * @param strObj
	 * @return
	 */
	public static String getMD5Code(String strObj){
		String resultString = null;
		
		resultString = new String(strObj);
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			//	计算strObj字符串的哈希值，然后根据计算结果转化为加密后的字符串
			resultString = byteToString(md.digest(strObj.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return resultString;
	}
}
