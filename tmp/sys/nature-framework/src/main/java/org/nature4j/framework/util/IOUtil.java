package org.nature4j.framework.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ServletOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IOUtil {
	private static Logger LOGGER = LoggerFactory.getLogger(IOUtil.class);
	
	/**
	 * SevletOutputStream only use
	 * os is not close , it closed by response.
	 */
	public static void inputToOutup(InputStream is,ServletOutputStream os){
		if(is!=null){
			try {
				int len = -1;
				byte[] buf = new byte[1024];
				while ((len=is.read(buf))!=-1) {
					os.write(buf, 0, len);
				}
			} catch (IOException e) {
				LOGGER.error(e.toString());
			}finally {
				try {
					is.close();
				} catch (IOException e) {
					LOGGER.error(e.toString());
				}
			}
		}
	}
	
	 /**
     * 获取字符串
     */
    public static String getString(InputStream inputStream){
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        try {
            while ((line=bufferedReader.readLine())!=null){
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            LOGGER.error("InputStream to String failure",e);
        }finally {
        	try {
        		if (inputStream!=null) {
        			inputStream.close();
    				bufferedReader.close();
				}
			} catch (IOException e) {
				 LOGGER.error("close failure",e);
			}
        	
		}
        return stringBuilder.toString();
    }
}
