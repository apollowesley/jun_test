package x.javaplus.collections;

import java.util.List;

import x.javaplus.util.Util.Random;


/**
 * 数组操作
 * @author deng		
 * @date 2015-10-27 下午7:20:31
 */
public class Arrays {
	
	/**
	 * 从池子随机固定个数出来 不重复
	 * @param count
	 * @param pool
	 * @return
	 */
	public static String[] selectNumberToArray( int count, String[] pool ) {
		int size = pool.length < count ? pool.length : count;
		List<String> ret = Lists.newArrayList();
		List<String> pools = Lists.newArrayList(pool);
		while( ret.size() < size ){
			int index = Random.get( 0, pools.size()-1 );
			ret.add( pools.remove(index) );
		}
		return ret.toArray(new String[size]);
	}
	
	/**
	 * 从池子随机固定个数出来 不重复
	 * @param count
	 * @param pool
	 * @return
	 */
	public static List<String> selectNumberToList( int count, String[] pool ) {
		int size = pool.length < count ? pool.length : count;
		List<String> ret = Lists.newArrayList();
		List<String> pools = Lists.newArrayList(pool);
		while( ret.size() < size ){
			int index = Random.get( 0, pools.size()-1 );
			ret.add( pools.remove(index) );
		}
		return ret;
	}
	
}
