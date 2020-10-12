package tom.net.http;

import tom.net.IoAdaptor;
import tom.net.IoBuffer;

public class MessageAdaptor extends IoAdaptor{  
	
	
	public IoBuffer encode(Object obj) { 
		if(!(obj instanceof HttpMessage)){ 
			throw new RuntimeException("Message unknown"); 
		}  
		
		HttpMessage msg = (HttpMessage)obj;   
		IoBuffer buf = msg.toIoBuffer(); 
		return buf; 
	}
        
	public Object decode(IoBuffer buf) {  
		int headerIdx = findHeaderEnd(buf);
		if(headerIdx == -1) return null; 
		
		int headerLen = headerIdx+1-buf.position();
		
		buf.markReadIndex();
		HttpMessage msg = new HttpMessage();  
		byte[] header = buf.readBytes(buf.position(), headerLen);
		msg.decodeHeaders(header, 0, headerLen);
		
		String contentLength = msg.getHeadOrParam(HttpMessage.HEADER_CONTENT_LENGTH);
		if(contentLength == null){ //just head 
			return msg;
		}
		
		int bodyLen = Integer.valueOf(contentLength); 
		if(buf.remaining()<bodyLen) {
			buf.resetReadIndex();
			return null;
		}
		
		byte[] body = new byte[bodyLen];
		buf.readBytes(body);
		msg.setBody(body); 
//		System.out.println("msg===" +msg);
		return msg;
	} 
	
	private static int findHeaderEnd(IoBuffer buf){
		buf.markReadIndex();
		int i = buf.position();
		int limit = buf.limit();
		byte[] data = buf.readBytes(i, limit);
		buf.resetReadIndex();
		while(i+3<limit){
			if(data[i] != '\r') {
				i += 1;
				continue;
			}
			if(data[i+1] != '\n'){
				i += 1;
				continue;
			}
			
			if(data[i+2] != '\r'){
				i += 3;
				continue;
			}
			
			if(data[i+3] != '\n'){
				i += 3;
				continue;
			}
			
			return i+3; 
		}
		return -1;
	}

}