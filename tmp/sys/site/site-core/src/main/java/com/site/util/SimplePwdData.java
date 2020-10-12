package com.site.util;


public class SimplePwdData {
	/**
	 * 对敏感数据进行简单加密
	 * 
	 * @param str
	 */
    private static String setPwd(String str) {
    	  char [] data=new char[str.length()]; 
	  	  str.getChars(0,str.length(),data,0); 
	  	  
	  	  for(int i=0;i<str.length();i++){ 
	  		  if(i%2==0)
	  			  data[i]-=2;
	  		  else
	  			  data[i]-=1;
	  	  } 
	  	  String str2=new String(data); 
	  	  return str2;
      }
      
	/**
	 * 对敏感数据的加密约定进行简单解密
	 * 
	 * @param str
	 */
      private static String getPwd(String str) {
      	
	      	char [] data=new char[str.length()]; 
	      	str.getChars(0,str.length(),data,0); 
	    	  
	      	for(int i=0;i<str.length();i++){ 
	    		 	if(i%2==0)
	    		 		data[i]+=2;
	    		 	else
	    		 		data[i]+=1;
	    	  	} 
	    	  	String str2=new String(data);
    	  	return str2;
  	}
      
      public static void main(String[] args){
        	String s=setPwd("123abc!@#$%^&*()");
        	System.out.println(s);
        	String s2=getPwd(s);
        	System.out.println(s2);
        }
    	
}
