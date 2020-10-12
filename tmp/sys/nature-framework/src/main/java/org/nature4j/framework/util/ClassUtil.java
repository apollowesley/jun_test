package org.nature4j.framework.util;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.net.www.protocol.jar.JarURLConnection;

public final class ClassUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassUtil.class);

    /**
     * 获取类加载器
     */
    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * 加载类
     */
    public static Class<?> loadClass(String className, boolean isInitialized) {
        Class<?> cls = null;
        try {
            cls = Class.forName(className, isInitialized, getClassLoader());
        } catch (ClassNotFoundException e) {
            LOGGER.error("class load failure", e);
            throw new RuntimeException(e);
        }
        return cls;
    }

    public static Class<?> loadClass(String className) {
        Class<?> cls = null;
        try {
            cls = Class.forName(className, true, getClassLoader());
        } catch (ClassNotFoundException e) {
            LOGGER.error("class load failure", e);
            throw new RuntimeException(e);
        }
        return cls;
    }
    /**
     * 获取指定包下所有类被注解的类
     * 
     * TODO 待优化//一次循环，组装完毕所有需要加载的类
     */
    public static Set<Class<?>> getClassSet(String packageNames,Class<? extends Annotation> annotationClass) {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        try {
            String[] packages= packageNames.split(",");
            for (String packageName : packages) {
            	Enumeration<URL> urls = getClassLoader().getResources(packageName.replaceAll("\\.","/"));
                while (urls.hasMoreElements()){
                    URL url = urls.nextElement();
                    String protocol = url.getProtocol();
                    if("file".equals(protocol)){
                        String packagePath = url.getPath().replace("%20"," ");
                        addClass(classSet,packagePath,packageName,annotationClass);
                    }else if ("jar".equals(protocol)){
                        JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
                        JarFile jarFile = jarURLConnection.getJarFile();
                        if(jarFile!=null){
                            Enumeration<JarEntry> jarEntities = jarFile.entries();
                            while (jarEntities.hasMoreElements()){
                                JarEntry jarEntity = jarEntities.nextElement();
                                String jarEntityName = jarEntity.getName();
                                if(jarEntityName.endsWith(".class")){
                                    String className = jarEntityName.substring(0,jarEntityName.lastIndexOf(".")).replaceAll("/",".");
                                    LOGGER.debug(className);
                                    doAddClass(classSet,className,annotationClass);
                                }
                            }
                        }
                    }
                }
			}
        } catch (IOException e) {
            LOGGER.error("get class set failure",e);
        }
        return classSet;
    }
    
    /**
     * 获取指定包下所有类被注解的类
     * 
     * TODO 待优化//一次循环，组装完毕所有需要加载的类
     */
    public static Set<Class<?>> getClassSetByInterface(String packageNames,Class<?> interfaceClass) {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        try {
        	 String[] packages= packageNames.split(",");
             for (String packageName : packages) {
				Enumeration<URL> urls = getClassLoader().getResources(packageName.replaceAll("\\.", "/"));
				while (urls.hasMoreElements()) {
					URL url = urls.nextElement();
					String protocol = url.getProtocol();
					if ("file".equals(protocol)) {
						String packagePath = url.getPath().replace("%20", " ");
						addClassInterface(classSet, packagePath, packageName, interfaceClass);
					} else if ("jar".equals(protocol)) {
						JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
						JarFile jarFile = jarURLConnection.getJarFile();
						if (jarFile != null) {
							Enumeration<JarEntry> jarEntities = jarFile.entries();
							while (jarEntities.hasMoreElements()) {
								JarEntry jarEntity = jarEntities.nextElement();
								String jarEntityName = jarEntity.getName();
								if (jarEntityName.endsWith(".class")) {
									String className = jarEntityName.substring(0, jarEntityName.lastIndexOf("."))
											.replaceAll("/", ".");
									doAddClassInterface(classSet, className, interfaceClass);
								}
							}
						} 
					}
				} 
			}
        } catch (IOException e) {
            LOGGER.error("get class set failure",e);
        }
        return classSet;
    }
    /**
     * 增加类
     */
    private static void addClassInterface(Set<Class<?>> classSet, String packagePath, String packageName,Class<?> interfaceClass) {
        File[] files = new File(packagePath).listFiles(new FileFilter() {
            public boolean accept(File file) {
                return (file.isFile() && file.getName().endsWith(".class")) || file.isDirectory();
            }
        });

        for (File file : files) {
            String fileName = file.getName();
            if (file.isFile()) {
                String className = fileName.substring(0, fileName.lastIndexOf("."));
                if (StringUtil.isNotEmpty(packageName)) {
                    className = packageName + "." + className;
                    doAddClassInterface(classSet, className,interfaceClass);//加入到集合
                }
            } else {
                String subPackagePath = fileName;
                if (StringUtil.isNotEmpty(packagePath)) {
                    subPackagePath = packagePath + "/" + fileName;
                }
                String subPackageName = fileName;
                if (StringUtil.isNotEmpty(packageName)) {
                    subPackageName = packageName + "." + subPackageName;
                }
                addClassInterface(classSet, subPackagePath, subPackageName,interfaceClass);
            }
        }
    }
    /**
     * 直接增加接口实现类
     */
    private static void doAddClassInterface(Set<Class<?>> classSet, String className,Class<?> interfaceClass) {
        Class<?> cls = loadClass(className, false);
        Class<?>[] interfaces = cls.getInterfaces();
       	for (Class<?> intf : interfaces) {
       		if(intf == interfaceClass){
            	classSet.add(cls);
            	LOGGER.debug("load class:"+className);
            }
		}
       
    }
    /**
     * 增加类
     */
    private static void addClass(Set<Class<?>> classSet, String packagePath, String packageName,Class<? extends Annotation> annotationClass) {
        File[] files = new File(packagePath).listFiles(new FileFilter() {
            public boolean accept(File file) {
                return (file.isFile() && file.getName().endsWith(".class")) || file.isDirectory();
            }
        });

        for (File file : files) {
            String fileName = file.getName();
            if (file.isFile()) {
                String className = fileName.substring(0, fileName.lastIndexOf("."));
                if (StringUtil.isNotEmpty(packageName)) {
                    className = packageName + "." + className;
                    doAddClass(classSet, className,annotationClass);//加入到集合
                }
            } else {
                String subPackagePath = fileName;
                if (StringUtil.isNotEmpty(packagePath)) {
                    subPackagePath = packagePath + "/" + fileName;
                }
                String subPackageName = fileName;
                if (StringUtil.isNotEmpty(packageName)) {
                    subPackageName = packageName + "." + subPackageName;
                }
                addClass(classSet, subPackagePath, subPackageName,annotationClass);
            }
        }
    }
    
    /**
     * 直接增加被注解的类
     */
    private static void doAddClass(Set<Class<?>> classSet, String className,Class<? extends Annotation> annotationClass) {
        Class<?> cls = loadClass(className, false);
        if(cls.isAnnotationPresent(annotationClass)){
        	classSet.add(cls);
        	LOGGER.debug("load class:"+className);
        }
    }
    
}
