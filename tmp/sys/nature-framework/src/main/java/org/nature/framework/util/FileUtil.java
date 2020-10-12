package org.nature.framework.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtil {
	  private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);
	  
	public static void move(File oldFile,File newFile){
		FileInputStream oldFileInputStream = null;
		FileOutputStream newFileOutputStream = null;
		try {
			oldFileInputStream = new FileInputStream(oldFile);
			newFileOutputStream = new FileOutputStream(newFile);
			byte[] buf = new byte[1024];
			int len = -1;
			while((len=oldFileInputStream.read(buf))!=-1){
				newFileOutputStream.write(buf, 0, len);
			}
			newFileOutputStream.flush();
			oldFile.delete();
		} catch (IOException e) {
			LOGGER.error("move file error");
			throw new RuntimeException(e);
		}finally {
			try {
				if (oldFileInputStream!=null) {
					oldFileInputStream.close();
				}
				if (newFileOutputStream!=null) {
					newFileOutputStream.close();
				}
			} catch (IOException e) {
				LOGGER.error("move file error");
				throw new RuntimeException(e);
			}
		}
	}
}
