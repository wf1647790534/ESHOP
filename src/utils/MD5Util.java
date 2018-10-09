package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * �������������ַ����������ַ�����ʹ�ü��ܽ��ܷ���ΪMD5
 * @author �ö�
 * @version 1
 */
public class MD5Util {

	public MD5Util() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * ȫ�����飬���ܺ���ַ��������и������е��ַ���ɵġ�
	 */
	private final static char[] chars = {
			'0','1','2','3',
			'4','5','6','7',
			'8','9','a','b',
			'c','d','e','f'
	}; 
	
	/**
	 * ͨ��bByte��ֵ��ȡ��chars�����е������ַ����һ���ַ���
	 * @param bByte
	 * @return	String���ͱ���
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
	 * ��bByteת��Ϊһ���ַ�����������������õ�
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
	 * ��bByte����ת��ΪString���󡣸ö������ַ�����chars�ַ����е��ַ�
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
	 * ���ؼ��ܺ���ַ���
	 * @param strObj
	 * @return
	 */
	public static String getMD5Code(String strObj){
		String resultString = null;
		
		resultString = new String(strObj);
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			//	����strObj�ַ����Ĺ�ϣֵ��Ȼ����ݼ�����ת��Ϊ���ܺ���ַ���
			resultString = byteToString(md.digest(strObj.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return resultString;
	}
}
