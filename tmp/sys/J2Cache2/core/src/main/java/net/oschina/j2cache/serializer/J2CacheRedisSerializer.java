/**
 * 
 */
package net.oschina.j2cache.serializer;

import java.io.IOException;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import net.oschina.j2cache.util.SerializationUtils;

/**
 * @description <br>
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @date 2016年10月31日
 */
public class J2CacheRedisSerializer implements RedisSerializer<Object> {

	@Override
	public byte[] serialize(Object t) throws SerializationException {
		try {
			return SerializationUtils.serialize(t);
		} catch (IOException e) {
			throw new SerializationException(e.getMessage(),e);
		}
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.redis.serializer.RedisSerializer#deserialize(byte[])
	 */
	@Override
	public Object deserialize(byte[] bytes) throws SerializationException {
		try {
			return SerializationUtils.deserialize(bytes);
		} catch (IOException e) {
			throw new SerializationException(e.getMessage(),e);
		}
	}

}
