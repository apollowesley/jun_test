package cn.kiwipeach.demo.classloader;

/**
 * 封装加载类的信息
 * @author liuyazhuang
 *
 */
public class ClassLoadInfo {
	//自定义的类加载
	private CustomClassLoader myLoader;
	//记录要加载的类的时间戳-->加载的时间
	private long loadTime;
	private IHot manager;
	
	public ClassLoadInfo(CustomClassLoader myLoader, long loadTime) {
		super();
		this.myLoader = myLoader;
		this.loadTime = loadTime;
	}

	public CustomClassLoader getMyLoader() {
		return myLoader;
	}

	public void setMyLoader(CustomClassLoader myLoader) {
		this.myLoader = myLoader;
	}

	public long getLoadTime() {
		return loadTime;
	}

	public void setLoadTime(long loadTime) {
		this.loadTime = loadTime;
	}

	public IHot getManager() {
		return manager;
	}

	public void setManager(IHot manager) {
		this.manager = manager;
	}
}
