package com.ukefu.core;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.ConvertUtils;
import org.springframework.context.ApplicationContext;

import com.ukefu.util.DateConverter;

public class UKDataContext {

	public static final String USER_SESSION_NAME = "user";
	public static final String GUEST_USER = "guest";
	public static final String IM_USER_SESSION_NAME = "im_user";
	public static final String GUEST_USER_ID_CODE = "R3GUESTUSEKEY" ;
	
	public static final int AGENT_STATUS_MAX_USER = 10 ; 		//每个坐席 最大接待的 咨询数量
	
	public static final String SYSTEM_CACHE_SESSION_CONFIG = "session_config";
	
	public static String SYSTEM_ORGI = "ukewo" ;
	public static Map<String , Boolean> model = new HashMap<String,Boolean>();
	
	private static int WebIMPort = 8081 ;
	
	private static ApplicationContext applicationContext ;
	
	static{
		ConvertUtils.register(new DateConverter(), java.util.Date.class); 
	}
	
	public enum AskSectionType{
		DEFAULT ;
		public String toString(){
			return super.toString().toLowerCase() ;
		}
	}
	
	public enum AskOperatorType{
		VIEWS ,
		COMMENTS,
		UPS;
		public String toString(){
			return super.toString().toLowerCase() ;
		}
	}
	
	public enum OnlineUserInviteStatus{
		DEFAULT,
		INVITE,
		REFUSE,
		INSERV,
		ACCEPT;
		public String toString(){
			return super.toString().toLowerCase() ;
		}
	}
	
	public enum ChannelTypeEnum{
		WEIXIN ,
		WEIBO,
		WEBIM,
		PHONE,
		EMAIL;

		public String toString(){
			return super.toString().toLowerCase() ;
		}
	}
	
	public enum AgentStatusEnum{
		READY,
		NOTREADY,
		BUSY,
		IDLE,
		LEAVE,
		SERVICES;
		public String toString(){
			return super.toString().toLowerCase() ;
		}
	}
	
	
	public enum NameSpaceEnum{
		
		IM("/im/user") ,
		AGENT("/im/agent"), 
		ENTIM("/im/ent") ;
		
		private String namespace ;
		
		public String getNamespace() {
			return namespace;
		}

		public void setNamespace(String namespace) {
			this.namespace = namespace;
		}

		NameSpaceEnum(String namespace){
			this.namespace = namespace ;
		}
		
		public String toString(){
			return super.toString().toLowerCase() ;
		}
	}
	
	public enum MessageTypeEnum{
		NEW,
		MESSAGE, 
		END,
		TRANS, STATUS , AGENTSTATUS , SERVICE, WRITING;
		
		public String toString(){
			return super.toString().toLowerCase() ;
		}
	}
	
	public enum CallTypeEnum{
		IN ,
		OUT, 
		SYSTEM;
		
		public String toString(){
			return super.toString().toLowerCase() ;
		}
	}
	
	public enum OnlineUserOperatorStatus{
		ONLINE,
		OFFLINE,
		REONLINE,
		CHAT,
		RECHAT,
		BYE,
		SEARCH,
		ACCESS;
		
		public String toString(){
			return super.toString().toLowerCase() ;
		}
	}
	
	public enum OnlineUserTypeStatus{
		USER,
		WEBIM,
		WEIXIN,
		APP,
		OTHER,
		WEIBO;
		
		public String toString(){
			return super.toString().toLowerCase() ;
		}
	}
	
	public enum AgentUserStatusEnum{
		INSERVICE,
		INQUENE, END;
		
		public String toString(){
			return super.toString().toLowerCase() ;
		}
	}
	
	public static void setApplicationContext(ApplicationContext context){
		applicationContext = context ;
	}
	
	public static ApplicationContext getContext(){
		return applicationContext ;
	}

	public static int getWebIMPort() {
		return WebIMPort;
	}

	public static void setWebIMPort(int webIMPort) {
		WebIMPort = webIMPort;
	}
}

