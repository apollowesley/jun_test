package cn.uncode.dal.utils;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class IDGenerateUtils {

	public final static long BASE_TIME = 1422766646843L;
	
	private static volatile AtomicInteger SEQUENCE_ATOMIC = new AtomicInteger();
	
	static{
		Random random=new Random();
		int result = random.nextInt(9999);
		if(result < 1000){
			result += 1000;
		}
		SEQUENCE_ATOMIC.set(result);
	}

	/**
	 * 生成唯一ID
	 * 
	 * @throws Exception
	 * @author meff
	 */
	public static long getId() {
		long time = System.currentTimeMillis() - BASE_TIME;
		if (SEQUENCE_ATOMIC.incrementAndGet() >= 9999) {
			synchronized(SEQUENCE_ATOMIC){
				if (SEQUENCE_ATOMIC.get() >= 9999) {
					SEQUENCE_ATOMIC.set(1000);
				}
			}
			SEQUENCE_ATOMIC.incrementAndGet();
		}
		time = time * 10000 + SEQUENCE_ATOMIC.get();
		return time;
	}
	
	public static long getId(int thridBit) {
		long time = System.currentTimeMillis() - BASE_TIME;
		if (SEQUENCE_ATOMIC.incrementAndGet() >= 9999) {
			synchronized(SEQUENCE_ATOMIC){
				if (SEQUENCE_ATOMIC.get() >= 9999) {
					SEQUENCE_ATOMIC.set(1000);
				}
			}
			SEQUENCE_ATOMIC.incrementAndGet();
		}
		if(thridBit < 100 || thridBit > 999){
			throw new RuntimeException("只能是三位数字");
		}
		int last = thridBit * 10000 + SEQUENCE_ATOMIC.get();
		time = time * 10000000 + last;
		return time;
	}
	
	
	public static void main(String[] args) {
		for(int i = 0;i<1;i++){
			System.out.println(IDGenerateUtils.getId(777));
		}
		
		long dd = 750100022047771137l;
		long aa = (dd%10000000l)/10000;
		long bb = aa/10000;
		System.out.println(aa);
		System.out.println(bb);
	}
}
