package org.nature.framework.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.nature.framework.helper.ConfigHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Ocean on 2016/3/10.
 */
public final class CodecUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(CodecUtil.class);
    
    
    
    public static String ISO2UTF8(String string){
    	if (string != null) {
    		try {
    			string = new String(string.getBytes("ISO-8859-1"),"UTF-8");
    		} catch (UnsupportedEncodingException e) {
    			LOGGER.error("string ISO2UTF8 failure",e);
    			throw new RuntimeException(e);
    		}
    	}
    	return string;
    }
    
    public static String ISO2FileEncode(String string){
    	if (string != null) {
			try {
				string = new String(string.getBytes("ISO-8859-1"),ConfigHelper.getFileEncoding());
				System.out.println(ConfigHelper.getFileEncoding()+">>"+string);
			} catch (UnsupportedEncodingException e) {
				LOGGER.error("string ISO2FileEncode failure",e);
	            throw new RuntimeException(e);
			}
		}
    	return string;
    }
    
    public static String encodeURL(String string){
        String result;
        try {
            result = URLEncoder.encode(string,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("encode url failure",e);
            throw new RuntimeException(e);
        }
        return result;
    }

    public static String decodeURL(String string){
        String result;
        try {
            result = URLDecoder.decode(string, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("encode url failure",e);
            throw new RuntimeException(e);
        }
        return result;
    }
}
