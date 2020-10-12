package com.ly.utils.dynamic.javaclass;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import javax.tools.JavaCompiler.CompilationTask;

/**
 * 动态编译java文件并执行
 * 
 * <br>
 *  <em style="color:green">
 *  例：
 *  <ul>
 *		<li>getClass("src/","com.Test")</li>
 *		<li>getClass("src/main/java/","com.Test")</li>
 *  </ul>
 *  </em>
 * 
 * @version 1.2.1
 */
public class JavaCompilerUtil {
	
	public static String url = null;
	public static String javaPath = null;

	/**
	 * 获取java文件类对象
	 * 
	 * @param url
	 * @param filePath
	 * @param className
	 * @return
	 * @throws ClassNotFoundException 未找到
	 * @throws IOException	编译失败
	 */
	public static Class<?> getClass(String className) throws IOException, ClassNotFoundException {
		format("src/main/java/",className);
		//编译
		compile(javaPath,className);
		// 使用URLClassLoader加载class到内存
		URL[] urls = new URL[] { new URL(url) };
		URLClassLoader cLoader = new URLClassLoader(urls);
		Class<?> c = cLoader.loadClass(className);
		cLoader.close();
		return c;
	}

	/**
	 * 编译java文件到内存中
	 * 
	 * @param javaPath
	 * @param className 
	 * @throws IOException
	 */
	private static void compile(String javaPath, String className){
		// 使用JavaCompiler 编译java文件
		JavaCompiler jc = ToolProvider.getSystemJavaCompiler();
		StandardJavaFileManager fileManager = jc.getStandardFileManager(null, null, null);
		Iterable<? extends JavaFileObject> fileObjects = null;
		fileObjects = fileManager.getJavaFileObjects(javaPath);
		CompilationTask cTask = jc.getTask(null, fileManager, null, null, null, fileObjects);
		cTask.call();
		try {
			fileManager.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 格式化路径
	 * 
	 * @param src
	 * @param className
	 */
	private static void format(String src,String className){
		String dir = System.getProperty("user.dir");
		url = "file:/" + dir +"/"+src;
		javaPath = dir+"/"+src+className.replace(".", "/")+".java";
	}
	
}
