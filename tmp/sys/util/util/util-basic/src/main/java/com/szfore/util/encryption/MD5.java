package com.szfore.util.encryption;


import java.security.MessageDigest;

public final class MD5 {
	
	public final static String UTF8 = "utf-8";
	public final static String GBK = "gbk";
	public final static String GB18030 = "GB18030";
	public final static String ISO8859 = "iso8859-1";
	/**
	 * �������ַ����MD5����
	 * @param plainText ��Ҫ�����ַ�
	 * @param charEncode ���뷽ʽ
	 * @return ���ܺ�string
	 */
	 public final static String encrptMD5(String plainText,String charEncode) {
		 if(plainText == null){
			 return null;
		 }
		 char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',  
				    'A', 'B', 'C', 'D', 'E', 'F' };  
		  try {  
			   byte[] strTemp = plainText.getBytes(charEncode);  
			   MessageDigest mdTemp = MessageDigest.getInstance("MD5");  
			   mdTemp.update(strTemp);  
			   byte[] md = mdTemp.digest();  
			   int j = md.length;  
			   char str[] = new char[j * 2];  
			   int k = 0;  
			   for (int i = 0; i < j; i++) {  
			    byte byte0 = md[i];  
			    str[k++] = hexDigits[byte0 >>> 4 & 0xf];  
			    str[k++] = hexDigits[byte0 & 0xf];  
			   }  
			   return new String(str);  
		  } catch (Exception e) {  
			  return null;  
		  }  
	 }
	 
	/* public static void main(String[] args) {
		 System.out.println(MD5.encrptMD5("10001X", "utf-8"));
	 }*/
}
