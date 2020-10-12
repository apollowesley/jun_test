package tom.net.http;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tom.net.IoBuffer;
import tom.net.http.id.TicketManager.Id;

public class HttpMessage implements Id{ 
	private static final long serialVersionUID = 4379223525215626137L;
	private static final Logger log = LoggerFactory.getLogger(HttpMessage.class);
	
	public static final String HEARTBEAT         = "heartbeat"; //心跳消息
	public static final String CHARSET   = "UTF-8"; //心跳消息
	
	//使用到的标准HTTP头部
	public static final String HEADER_REMOTE_ADDR      = "remote-addr";
	public static final String HEADER_CONTENT_ENCODING = "content-encoding";
	public static final String HEADER_CONTENT_LENGTH   = "content-length";
	public static final String HEADER_CONTENT_TYPE     = "content-type";
	
	//扩展HTTP协议头部
	public static final String HEADER_CMD    	 = "cmd"; 
	public static final String HEADER_SUBCMD     = "sub_cmd";    
	public static final String HEADER_MQ         = "mq";
	public static final String HEADER_MQ_REPLY   = "mq_reply";
	public static final String HEADER_MSGID      = "msgid";	    //消息ID
	public static final String HEADER_MSGID_RAW  = "msgid_raw"; //原始消息ID
	
	public static final String HEADER_TOKEN      = "token";
	public static final String HEADER_BROKER     = "broker"; 
	public static final String HEADER_TOPIC      = "topic"; //使用,分隔 
	public static final String HEADER_ACK        = "ack";	 	 
	public static final String HEADER_WINDOW     = "window";
	public static final String HEADER_REPLY_CODE = "reply_code";
	public static final String HEADER_SEQ        = "seq";
	
	
	 
	protected Meta meta = new Meta(); 
	protected Map<String, String> head = new ConcurrentHashMap<String, String>();
	protected byte[] body; 
	
	public HttpMessage(){
		setBody((byte[])null);
	} 
	
	public HttpMessage(String body){
		setBody(body); 
	}
	
	public HttpMessage(byte[] body){
		setBody(body);
	}
	
	public static HttpMessage copyWithoutBody(HttpMessage msg){
		HttpMessage res = new HttpMessage();
		res.meta = new Meta(msg.meta);
		res.head = new HashMap<String, String>(msg.head);
		res.body = msg.body;
		return res;
	}
	
	public String getMetaString() {
		return meta.toString();
	}
	
	public Meta getMeta(){
		return meta;
	}
	
	public void setMeta(String meta) { 
		this.meta = new Meta(meta);
	}
	
	public void setMeta(Meta meta) { 
		this.meta = meta;
	}
	
	public Map<String, String> getHead() {
		return head;
	} 
	
	public void setHead(Map<String, String> head) {
		this.head = head;
	} 
	
	public String getHead(String key){
		return this.head.get(key);
	}
	
	public void setHead(String key, String value){
		if(value == null) return;
		this.head.put(key, value);
	} 
	
	public String removeHead(String key){
		return this.head.remove(key);
	}
	
	public String getParam(String key){
		return meta.getParam(key); 
	}
	
	public String getHeadOrParam(String key){ 
		String value = getHead(key);
		if(value == null){
			value = getParam(key); 
		} 
		if(value == null){
			value = getPath();
		}
		return value;
	}
	
	public String getHeadOrParam(String key, String defaultValue) { 
		String value = getHeadOrParam(key);
		if(value == null){
			value = defaultValue;
		}
		return value;
	}   
	
	public byte[] getBody() {
		byte[] b = body;
		String bodyOfHead = getHead("body");
		if(b == null && bodyOfHead != null){
			b = bodyOfHead.getBytes();
		}
		return b;
	}
	
	public HttpMessage setBody(byte[] body) {
		int len = 0;
		if( body != null){
			len = body.length;
		}
		this.setHead(HEADER_CONTENT_LENGTH, ""+len);
		this.body = body;
		return this;
	}
	
	public HttpMessage setBody(String body){
		try {
			return setBody(body.getBytes(CHARSET));
		} catch (UnsupportedEncodingException e) {
			return setBody(body.getBytes());
		}
	} 
	
	public HttpMessage setBody(String format, Object ...args) { 
		this.setBody(String.format(format, args));
		return this;
	} 
	
	public HttpMessage setJsonBody(String body){
		try {
			return this.setJsonBody(body.getBytes(CHARSET));
		} catch (UnsupportedEncodingException e) {
			return this.setJsonBody(body.getBytes());
		}
	}
	
	public HttpMessage setJsonBody(byte[] body){
		this.setHead(HEADER_CONTENT_TYPE, "application/json;charset="+CHARSET);
		this.setBody(body);
		return this;
	}
	
	public String getBodyString() {
		if (this.getBody() == null) return null;
		return new String(this.getBody());
	}

	public String getBodyString(String encoding) {
		if (this.getBody() == null) return null;
		try {
			return new String(this.getBody(), encoding);
		} catch (UnsupportedEncodingException e) {
			return new String(this.getBody());
		}
	}
	
	//////////////////////////////////////////////////////////////
	
	public void decodeHeaders(byte[] data, int offset, int size){
		ByteArrayInputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader in = null;
		try{ 
			inputStream = new ByteArrayInputStream(data, offset, size);
			inputStreamReader = new InputStreamReader(inputStream);
			in = new BufferedReader(inputStreamReader);
			String meta = in.readLine();
			if(meta == null) return;
			this.meta = new Meta(meta);
			
			String line = in.readLine();
	        while (line != null && line.trim().length() > 0) {
	            int p = line.indexOf(':');
	            if (p >= 0){ 
	                head.put(line.substring(0, p).trim().toLowerCase(), line.substring(p + 1).trim());
	            }
	            line = in.readLine();
	        }
	        //合并: header优先，url参数次之, 取消合并
	       /* if(this.meta.params != null){
	        	 for(Map.Entry<String, String> kv : this.meta.params.entrySet()){
	        		 String key = kv.getKey().toLowerCase();
	        		 if(!head.containsKey(key)){
	        			 head.put(key, kv.getValue());
	        		 }
	        	 }
	        }*/
	       
		} catch(IOException e){ 
			log.error(e.getMessage(), e);
		} finally {
			if(in != null){
				try { in.close(); } catch (IOException e) { }
			}
			if(inputStreamReader != null){
				try { inputStreamReader.close(); } catch (IOException e) { }
			}
			if(inputStream != null){
				try { inputStream.close(); } catch (IOException e) { }
			}
		}
	}
	
	public String getCommand() { 
		return this.getHeadOrParam(HEADER_CMD);
	} 
	public HttpMessage setCommand(String value) {
		this.setHead(HEADER_CMD, value); 
		return this;
	}  
	
	public String getSubCommand() { 
		return this.getHeadOrParam(HEADER_SUBCMD);
	} 
	public HttpMessage setSubCommand(String value) {
		this.setHead(HEADER_SUBCMD, value); 
		return this;
	}   
	
	public String getBroker(){
		return this.getHeadOrParam(HEADER_BROKER);
	}
	
	public void setBroker(String value){
		this.setHead(HEADER_BROKER, value);
	}
	
	public String getMqReply() {
		return this.getHeadOrParam(HEADER_MQ_REPLY);
	}
	public HttpMessage setMqReply(String value) {
		this.setHead(HEADER_MQ_REPLY, value);
		return this;
	}
	
	public String getEncoding() {
		return this.getHeadOrParam(HEADER_CONTENT_ENCODING);
	}
	public HttpMessage setEncoding(String encoding) {
		this.setHead(HEADER_CONTENT_ENCODING, encoding);
		return this;
	}
	
	public String getMsgId() {
		return this.getHeadOrParam(HEADER_MSGID);
	}
	public HttpMessage setMsgId(String msgId) {
		if(msgId == null) return this;
		this.setHead(HEADER_MSGID, msgId);
		return this;
	}
	
	
	public String getMsgIdRaw() {
		return this.getHeadOrParam(HEADER_MSGID_RAW);
	}
	public HttpMessage setMsgIdRaw(String value) {
		if(value == null) return this;
		this.setHead(HEADER_MSGID_RAW, value);
		return this;
	}
	
	public boolean isAck() {
		String ack = this.getHeadOrParam(HEADER_ACK);
		if(ack == null) return true; //默认ack为true
		ack = ack.trim().toLowerCase();
		return ack.equals("1") || ack.equals("true");
	}
	
	public void setAck(boolean ack){
		String value = ack? "1":"0";
		this.setHead(HEADER_ACK, value);
	}
	
	public String getMq(){
		String value = this.getHeadOrParam(HEADER_MQ);
		if(value == null){
			value = getPath();
		}
		return value;
	}
	
	public String getUri(){
		return this.meta.url;
	}
	
	public String getPath(){
		return this.meta.path;
	}
	 
	 
	public HttpMessage setMq(String mq) {
		this.setHead(HEADER_MQ, mq);
		return this;
	} 
	
	public String getToken() {
		return this.getHeadOrParam(HEADER_TOKEN);
	}
	public HttpMessage setToken(String token) {
		this.setHead(HEADER_TOKEN, token);
		return this;
	} 
	
	public String getTopic() {
		return getHeadOrParam(HEADER_TOPIC);
	}

	public HttpMessage setTopic(String topic) {
		this.setHead(HEADER_TOPIC, topic);
		return this;
	} 
	
	public String getWindow() {
		return getHeadOrParam(HEADER_WINDOW);
	}

	public HttpMessage setWindow(int window) {
		this.setHead(HEADER_WINDOW, ""+window);
		return this;
	} 
	
	public String getReplyCode() {
		return getHeadOrParam(HEADER_REPLY_CODE);
	}
	public HttpMessage setReplyCode(String value) {
		this.setHead(HEADER_REPLY_CODE, value);
		return this;
	} 
	
	
	public String getStatus() {  
		return meta.status;
	}
	public HttpMessage setStatus(String status) { 
		meta.status = status;
		return this; 
	}
	
	public boolean isStatus200() {
		return "200".equals(this.getStatus());
	}

	public boolean isStatus404() {
		return "404".equals(this.getStatus());
	}

	public boolean isStatus500() {
		return "500".equals(this.getStatus());
	}  
	
	protected String getBodyPrintString() {
		if (this.body == null)
			return null;
		if (this.body.length > 1024) {
			return new String(this.body, 0, 1024) + "...";
		} else {
			return getBodyString();
		}
	}
	
	static final byte[] CLCR = "\r\n".getBytes();
	static final byte[] KV_SPLIT = ": ".getBytes();
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(meta+"\r\n");
		
		List<String> keys = new ArrayList<String>(head.keySet());
		Collections.sort(keys);
		
		for(String key : keys){ 
			String val = head.get(key);
			sb.append(key+": "+val+"\r\n");
		}
		sb.append("\r\n");
		String bodyString = getBodyPrintString();
		if(bodyString != null){
			sb.append(bodyString);
		}
		return sb.toString();
	} 
	
	public byte[] toBytes(){
		IoBuffer bb = toIoBuffer(); 
		byte[] b = new byte[bb.remaining()];
		bb.readBytes(b);
		return b;
	}
	
	public IoBuffer toIoBuffer(){
		IoBuffer bb = meta.toIoBuffer();
		bb.writeBytes(CLCR);
		Iterator<Entry<String, String>> it = head.entrySet().iterator();
		while(it.hasNext()){
			Entry<String, String> kv = it.next();
			bb.writeBytes(kv.getKey().getBytes());
			bb.writeBytes(KV_SPLIT);
			bb.writeBytes(kv.getValue().getBytes());
			bb.writeBytes(CLCR);
		}
		bb.writeBytes(CLCR);
		if(body != null){
			bb.writeBytes(body);
		}
		bb.flip();
		return bb;
	}
	

public static class Meta implements Serializable{ 
	private static final long serialVersionUID = -8557063231118504061L;
	//HTTP响应头部: 状态(200)
	String status; //根据status是否设置来决定Meta是请求还是应答	
	//HTTP请求头部: 方法(GET/POST)-RequestString-KV参数
	String method = "GET"; 
	String url = "/";
	
	//请求分析出来的两个部分：path + kv组
	String path; 
	private Map<String,String> params = new HashMap<String, String>();
	
	static Set<String> httpMethod = new HashSet<String>();
	static Map<String,String> httpStatus = new HashMap<String, String>();
	
	static{ 
		httpMethod.add("GET");
		httpMethod.add("POST"); 
		httpMethod.add("PUT");
		httpMethod.add("DELETE");
		httpMethod.add("HEAD");
		httpMethod.add("OPTIONS"); 
		
		httpStatus.put("101", "Switching Protocols"); 
		httpStatus.put("200", "OK");
		httpStatus.put("201", "Created");
		httpStatus.put("202", "Accepted");
		httpStatus.put("204", "No Content"); 
		httpStatus.put("206", "Partial Content"); 
		httpStatus.put("301", "Moved Permanently");
		httpStatus.put("304", "Not Modified"); 
		httpStatus.put("400", "Bad Request"); 
		httpStatus.put("401", "Unauthorized"); 
		httpStatus.put("403", "Forbidden");
		httpStatus.put("404", "Not Found"); 
		httpStatus.put("405", "Method Not Allowed"); 
		httpStatus.put("416", "Requested Range Not Satisfiable");
		httpStatus.put("500", "Internal Server Error");
	}
	

	
	@Override
	public String toString() { 
		//如果status存在，理解为响应包，否则默认就是请求包
		if(this.status != null){
			String desc = httpStatus.get(this.status);
			if(desc == null){
				desc = "Unknown Status";
			}
			return String.format("HTTP/1.1 %s %s", status, desc); 
		}
		String method = this.method;
		String url = this.url;
		return String.format("%s %s HTTP/1.1", method, url);
	}
	
	final static byte[] BLANK = " ".getBytes();
	final static byte[] PREFIX = "HTTP/1.1 ".getBytes();
	final static byte[] SUFFIX = " HTTP/1.1".getBytes(); 
	
	public IoBuffer toIoBuffer(){
		IoBuffer bb = IoBuffer.allocate(1024); 
		if(this.status != null){  // 有 state 表示返回信息
			String desc = httpStatus.get(this.status);
			if(desc == null){
				desc = "Unknown Status";
			}
			bb.writeBytes(PREFIX);
			bb.writeString(status);
			bb.writeBytes(BLANK);
			bb.writeString(desc);  
		} else {
			String method = this.method;
			bb.writeString(method);
			bb.writeBytes(BLANK);
			String url = calcURL();
			bb.writeString(url);
			bb.writeBytes(SUFFIX); 
		}
		return bb;
	}
	
	public Meta(){ }
	
	public Meta(Meta m){
		this.url = m.url;
		this.path = m.path;
		this.method = m.method;
		this.status = m.status;
		if(m.params != null){
			this.params = new HashMap<String, String>(m.params);
		}
	}
	
	public Meta(String meta){
		if("".equals(meta)){
			return;
		}
		StringTokenizer st = new StringTokenizer(meta);
		String firstWord = st.nextToken();
		if(firstWord.toUpperCase().startsWith("HTTP")){ //理解为响应
			this.status = st.nextToken();
			return;
		}
		//理解为请求
		this.method = firstWord;
		
		this.url = st.nextToken();
		
		decodeURL(this.url);
	} 
	
	private String calcURL(){
		StringBuilder sb = new StringBuilder();
		if(path!=null){
			sb.append("/").append(path);
		}
		if(params.size()>0){
			sb.append("?").append(paramToUrl());
		}
	
		this.url = sb.toString(); 
		return url;
	}
	
	private void decodeURL(String commandString){
		int idx = commandString.indexOf('?');
		if(idx < 0){
			this.path = decode(commandString);
		} else {
			this.path = commandString.substring(0, idx);
		}
		if(this.path.startsWith("/")){
			this.path = this.path.substring(1);
		}
		if(idx < 0) return;
		
		this.params = new HashMap<String, String>(); 
		String paramString = commandString.substring(idx+1); 
		StringTokenizer st = new StringTokenizer(paramString, "&");
        while (st.hasMoreTokens()) {
            String e = st.nextToken();
            int sep = e.indexOf('=');
            if (sep >= 0) {
                this.params.put(decode(e.substring(0, sep)).trim(),
                		decode(e.substring(sep + 1)));
            } else {
                this.params.put(decode(e).trim(), "");
            }
        } 
	}
	public void setPath(String path){
		this.path = path;
	}
	
	private String encode(String url){
		try {
			return URLEncoder.encode(url,CHARSET);
		} catch (Exception e) {
			return "";
		}
	}
	private String decode(String str) {
        String decoded = null;
        try {
            decoded = URLDecoder.decode(str, CHARSET);
        } catch (UnsupportedEncodingException ignored) {
        }
        return decoded;
    }
	
	public String getParam(String key){
		if(params == null) return null;
		return params.get(key);
	}
	
	public String getParam(String key, String defaultValue){
		String value = getParam(key);
		if(value == null){
			value = defaultValue;
		}
		return value;
	}
	
	public String setParam(String key ,String value){
		return params.put(key, value);
	}
	
	public Map<String,String> getParams(){
		return params;
	}
	
	public String paramToUrl(){
		StringBuffer buffer = new StringBuffer();
		for(String key: params.keySet()){
			if(buffer.length()>0){
				buffer.append("&");
			}
			buffer.append(key).append("=").append(encode(params.get(key)));
		}
		return buffer.toString();
	
	}
	
}
	 
}
