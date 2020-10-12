package org.nature4j.framework.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SerializeUtil {
	private static Logger LOGGER = LoggerFactory.getLogger(SerializeUtil.class); 
	private static SerializeUtil defaultSerialize = new SerializeUtil();
	public static SerializeUtil getInstance(){
		return defaultSerialize;
	}

	public byte[] serilize(Object object) {
		if (object==null) {
			return null;
		}
		ByteArrayOutputStream byteArrayOutputStream = null;
		byteArrayOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream objectOutputStream = null;
		byte[] result ;
		try {
			objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
			objectOutputStream.writeObject(object);
			result = byteArrayOutputStream.toByteArray();
		} catch (IOException e) {
			LOGGER.error("serilize object error");
			throw new RuntimeException(e);
		}finally {
			close(objectOutputStream);
			close(byteArrayOutputStream);
		}
		return result;
	}

	public Object deserilize(byte[] bytes) {
		if (bytes==null) {
			return null;
		}
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
		ObjectInputStream objectInputStream = null;
		Object result;
		try {
			objectInputStream = new ObjectInputStream(byteArrayInputStream);
			result = objectInputStream.readObject();
		} catch (Exception e) {
			LOGGER.error("deserilize bytes error");
			throw new RuntimeException(e);
		}finally {
			close(objectInputStream);
			close(byteArrayInputStream);
		}
		 return result;
	}

	private void close(Closeable closeable){
		if (closeable!=null) {
			try {
				closeable.close();
			} catch (IOException e) {
				LOGGER.error("close error");
				throw new RuntimeException(e);
			}
		}
	}
}
