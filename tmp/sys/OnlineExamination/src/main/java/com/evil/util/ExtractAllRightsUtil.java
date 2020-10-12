package com.evil.util;

import java.io.File;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.evil.service.RightService;

public class ExtractAllRightsUtil {

	private static RightService rightService;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ApplicationContext ac = new ClassPathXmlApplicationContext(
					"applicationContext*.xml");
			rightService = (RightService) ac.getBean("rightService");
			ClassLoader classLoader = ExtractAllRightsUtil.class
					.getClassLoader();
			URL classPath = classLoader.getResource("com/evil/struts2/action");
			// System.out.println(classPath.toString());
			File file = new File(classPath.toURI());
			File[] files = file.listFiles();
			String fname = null;
			for (File f : files) {
				fname = f.getName();
				if (fname.endsWith(".class")
						&& !fname.equals("BaseAction.class")) {
					processAction(fname);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("rawtypes")
	private static void processAction(String fname) {
		try {
			String packName = "com.evil.struts2.action";
			String simpleClassName = fname.substring(0, fname.indexOf("."));
			String className = packName + "." + simpleClassName;
			Class clazz = Class.forName(className);
			Method[] method = clazz.getDeclaredMethods();
			Class retType = null;
			String mName = null;
			Class[] paramType = null;
			String url = null;
			for (Method m : method) {
				retType = m.getReturnType();
				mName = m.getName();
				paramType = m.getParameterTypes();
				if (ValidateUtil.isNull(paramType)
						&& Modifier.isPublic(m.getModifiers())) {
					if(mName.startsWith("get")||mName.startsWith("set")){
						continue;
					}
					if (mName.endsWith("execute")) {
						url = "/" + simpleClassName;
					} else 
						url = "/" + simpleClassName + "_" + mName;
					rightService.appendRightByUrl(url);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
