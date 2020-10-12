package cn.kiwipeach.demo.classloader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * 加载manager的工厂
 *
 * @author liuyazhuang
 */
public class HotClassImplFactory {

    private static final Logger logger = LoggerFactory.getLogger(HotClassImplFactory.class);

    //记录热加载类的加载信息
    private static final Map<String, ClassLoadInfo> loadTimeMap = new HashMap<String, ClassLoadInfo>();
    //要加载的类的classpath
    public static final String CLASS_PATH = "D:/20180125Git/FreeMarker-Start/target/classes/";
    //实现热加载的类的全名称(包名+类名)
    public static final String MY_MANAGER = "cn.kiwipeach.demo.classloader.TargetHotImpl";

    public static IHot getManager(String className) {
        File loadFile = new File(CLASS_PATH + className.replaceAll("\\.", "/") + ".class");
        long lastModified = loadFile.lastModified();
        //loadTimeMap不包含className为key的LoadInfo信息。证明这个类没有被加载，那么需要加载这个类到JVM
        if (loadTimeMap.get(className) == null) {
            load(className, lastModified);
            logger.debug("热加载对象第一次装载，文件信息:[name={},timestamp={}]", className, lastModified);
        }//加载类的时间戳变化了，我们同样要重新加载这个类到JVM
        else if (loadTimeMap.get(className).getLoadTime() != lastModified) {
            load(className, lastModified);
            logger.debug("热加载对象时间戳变化，文件信息:[name={},timestamp={}]", className, lastModified);
        }
        IHot resultManager = loadTimeMap.get(className).getManager();
        return resultManager;
    }

    private static void load(String className, long lastModified) {
        CustomClassLoader myClassLoader = new CustomClassLoader(CLASS_PATH);
        Class<?> loadClass = null;
        //loadClass = myClassLoader.loadClass(className);
        loadClass = myClassLoader.findClass(className);
        IHot manager = newInstance(loadClass);
        ClassLoadInfo loadInfo = new ClassLoadInfo(myClassLoader, lastModified);
        loadInfo.setManager(manager);
        loadTimeMap.put(className, loadInfo);
        logger.debug("热加载对象加载成功，文件信息:[name={},timestamp={}]", className, lastModified);
    }

    //以反射的方式创建BaseManager子类对象
    private static IHot newInstance(Class<?> loadClass) {
        try {
            return (IHot) loadClass.getConstructor(new Class[]{}).newInstance(new Object[]{});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
