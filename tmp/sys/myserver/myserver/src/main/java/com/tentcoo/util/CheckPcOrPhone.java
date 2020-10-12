package com.tentcoo.util;


import javax.annotation.Resource;


import com.tentcoo.service.UserService;

public class CheckPcOrPhone {
	
	


		public boolean PcOrPhone(String requestHeader) {
			
			String[] deviceArray=new String[] {"android","mac os","windows phone"};
			if(requestHeader==null) 
				return false;
			requestHeader=requestHeader.toLowerCase();
			for(int i=0;i<deviceArray.length;i++) {
				if(requestHeader.indexOf(deviceArray[i])>0){  
	                return true;  
	            }  
				
			}
			return false;
		
		}
	
}
