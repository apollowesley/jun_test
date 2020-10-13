package tom.cocook.ext;

import java.util.HashMap;

import tom.cocook.core.CocookException;

/**
 *	工厂
 */
public class BeanFactory {
	
	public static final HashMap<String, Object> singletons = new HashMap<String, Object>();

	public static <T> T getInstance(Class<T> _class){
		String key = _class.getName();
		if(singletons.containsKey(key)){
			return _class.cast(singletons.get(key)) ;
		}
		try{
			T tt = _class.newInstance();
			setSingletons(key, tt);
			return tt;
		} catch (Exception e) {
			throw new CocookException(e);
		} 
		
	}
	

	public static Object getInstance(String _class) {
		if(singletons.containsKey(_class)){
			return singletons.get(_class) ;
		}
		try{
			Class<?> clazz = loadClass(_class);
			Object tt = clazz.newInstance();
			setSingletons(_class, tt);
			return tt;
		} catch (Exception e) {
			throw new CocookException(e);
		} 
		
	}
	
	private static void setSingletons(String key, Object obj) {
		synchronized (singletons) {
			singletons.put(key, obj);
		}
	}
	
	public static <T> T newInstance(Class<T> clazz){
		try {
			return clazz.newInstance();
		} catch (Exception e) {
			throw new CocookException(e);
		} 
	}
	
	public static Object newInstance(String _class){
		try {
			Class<?> clazz = loadClass(_class);
			return clazz.newInstance();
		} catch (Exception e) {
			throw new CocookException(e);
		} 
	}
	
	public static Class<?> loadClass(String _clazz) throws ClassNotFoundException{
		Class<?> clazz = BeanFactory.class.getClassLoader().loadClass(_clazz);
		if(clazz == null){
			clazz = Thread.currentThread().getContextClassLoader().loadClass(_clazz);
		}
		return clazz;
	}
	
	
	
	
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		long a = System.currentTimeMillis();
		getInstance("webFrame.app.factory.AppFactory$aa");
		getInstance(aa.class);
		System.out.println(System.currentTimeMillis()-a);
	}
	
	static class aa{
		public aa() {
			System.out.println("-------------");
		}
	}
}
