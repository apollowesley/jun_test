package oc.tm.nio.core;

/**
 * Codec handles the encoding/decoding of application object and IoBuffer on the wire.
 */
public interface Codec{ 
	
	/**
	 * encode message object to IoBuffer 
	 * NOTE: IoBuffer should be flipped when ready to write
	 * 
	 * @param msg application message object
	 * @return IoBuffer object ready to use
	 */
	IoBuffer encode(Object msg);
	
	/**
	 * decode IoBuffer object to application object
	 * NOTE: if buffer is not enough to decode whole message, it should be restored and return null
	 * 
	 * @param buff IoBuffer from wire
	 * @return application message object, null if not fully ready
	 */
	Object decode(IoBuffer buff);
	
}
