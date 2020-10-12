/**
 * Copyright 2013 ABSir's Studio
 * 
 * All right reserved
 *
 * Create on 2013-5-3 下午1:35:42
 */
package com.absir.core.util;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.absir.core.kernel.KernelCharset;
import com.absir.core.kernel.KernelLang.BreakException;
import com.absir.core.kernel.KernelLang.CallbackBreak;

/**
 * @author absir
 * 
 */
@SuppressWarnings("rawtypes")
public class UtilPackage {

	/** CLASS_SUFFIX_NAME */
	public static String CLASS_SUFFIX_NAME = ".class";

	/** CLASS_SUFFIX_LENGTH */
	public static int CLASS_SUFFIX_LENGTH = CLASS_SUFFIX_NAME.length();

	/**
	 * @param packageName
	 * @param iterator
	 * @return
	 */
	public static List<Class> findClasses(String packageName, boolean iterator) {
		final List<Class> classes = new ArrayList<Class>();
		findClasses(packageName, iterator, new CallbackBreak<String>() {

			@Override
			public void doWith(String template) throws BreakException {
				// TODO Auto-generated method stub
				try {
					Thread.currentThread().getContextClassLoader().loadClass(template);

				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		return classes;
	}

	/**
	 * @param packageName
	 * @param iterator
	 * @param callback
	 */
	public static void findClasses(String packageName, boolean iterator, final CallbackBreak<String> callback) {
		String packageDir = packageName.replace('.', '/');
		try {
			Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources(packageDir);
			while (urls.hasMoreElements()) {
				URL url = urls.nextElement();
				if ("file".equals(url.getProtocol())) {
					findClasses(packageName, URLDecoder.decode(url.getFile(), KernelCharset.DEFAULT.name()), iterator, callback);

				} else {
					findClasses(packageDir, url, iterator, callback);
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (BreakException e) {
		}
	}

	/**
	 * @param jarUrl
	 * @param iterator
	 * @param callback
	 * @throws IOException
	 * @throws BreakException
	 */
	public static void findClasses(String packageDir, URL jarUrl, boolean iterator, final CallbackBreak<String> callback) throws IOException, BreakException {
		if ("jar".equals(jarUrl.getProtocol())) {
			JarFile jarFile = ((JarURLConnection) jarUrl.openConnection()).getJarFile();
			findClasses(packageDir, jarFile, iterator, callback);
		}
	}

	/**
	 * @param jarFile
	 * @param iterator
	 * @param callback
	 * @throws IOException
	 * @throws BreakException
	 */
	public static void findClasses(String packageDir, JarFile jarFile, boolean iterator, final CallbackBreak<String> callback) throws IOException, BreakException {
		Enumeration<JarEntry> entries = jarFile.entries();
		while (entries.hasMoreElements()) {
			JarEntry jarEntity = entries.nextElement();
			String jarName = jarEntity.getName();
			if (jarName.charAt(0) == '/') {
				jarName = jarName.substring(1);
				if (packageDir == null || jarName.startsWith(packageDir)) {
					boolean isFile = jarName.lastIndexOf('/') >= 0;
					if (isFile || iterator) {
						if (!jarEntity.isDirectory() && jarName.endsWith(CLASS_SUFFIX_NAME)) {
							String classname = jarName.substring(0, jarName.length() - CLASS_SUFFIX_LENGTH);
							if (!isFile) {
								classname = classname.replace('/', '.');
							}

							callback.doWith(classname);
						}
					}
				}
			}
		}
	}

	/** CLASS_FILE_FILTER */
	public static final FileFilter CLASS_FILE_FILTER = new FileFilter() {

		@Override
		public boolean accept(File file) {
			// TODO Auto-generated method stub
			return file.getName().endsWith(CLASS_SUFFIX_NAME);
		}
	};

	/** CLASS_DIR_FILE_FILTER */
	public static final FileFilter CLASS_DIR_FILE_FILTER = new FileFilter() {

		@Override
		public boolean accept(File file) {
			// TODO Auto-generated method stub
			return file.isDirectory() || file.getName().endsWith(CLASS_SUFFIX_NAME);
		}
	};

	/**
	 * @param packageName
	 * @param packagePath
	 * @param iterator
	 * @param callback
	 * @throws BreakException
	 */
	public static void findClasses(String packageName, String packagePath, boolean iterator, final CallbackBreak<String> callback) throws BreakException {
		File packageDir = new File(packagePath);
		if (!packageDir.exists() || !packageDir.isDirectory()) {
			return;
		}

		File[] packageFiles = packageDir.listFiles(iterator ? CLASS_DIR_FILE_FILTER : CLASS_FILE_FILTER);
		try {
			for (File packageFile : packageFiles) {
				if (packageFile.isDirectory()) {
					findClasses(packageName + "." + packageFile.getName(), packageFile.getAbsolutePath(), iterator, callback);

				} else {
					callback.doWith(packageFile.getName().substring(0, packageFile.getName().length() - CLASS_SUFFIX_LENGTH));
				}
			}

		} catch (BreakException e) {
			// TODO: handle exception
			throw e;
		}
	}
}
