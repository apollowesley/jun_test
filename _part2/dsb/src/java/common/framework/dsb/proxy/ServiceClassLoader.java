package common.framework.dsb.proxy;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;

/**
 * class loader to load all dynamic service class
 * 
 * @author David Yuan
 * 
 */
public class ServiceClassLoader extends URLClassLoader {
	public static final URL[] EMPTY_URL_ARRAY = {};

	public ServiceClassLoader(URL[] urls) {
		super(urls);
	}

	public ServiceClassLoader(URL[] urls, ClassLoader parent, URLStreamHandlerFactory factory) {
		super(urls, parent, factory);
	}

	public ServiceClassLoader(URL[] urls, ClassLoader parent) {
		super(urls, parent);
	}

	/**
	 * Load all dynamic service class from the given directory
	 * 
	 * @param serviceDir
	 */
	public void loadLibrary(File fileDir) {
		try {
			String[] files = fileDir.list();
			if (files == null) {
				return;
			}
			for (int i = 0; i < files.length; i++) {
				String fileName = files[i];
				File currentFile = new File(fileDir, fileName);
				if (currentFile.isDirectory()) {
					loadLibrary(currentFile);
				} else {
					addURL(currentFile.toURI().toURL());
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace(System.out);
		}
	}

	/*
	 * @see java.net.URLClassLoader#addURL(java.net.URL)
	 */
	public void addURL(URL url) {
		super.addURL(url);
	}
}
