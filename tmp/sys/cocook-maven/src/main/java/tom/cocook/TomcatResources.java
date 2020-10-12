package tom.cocook;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import javax.naming.directory.DirContext;
import javax.servlet.ServletContext;

import org.apache.catalina.Context;
import org.apache.catalina.WebResourceRoot.ResourceSetType;
import org.apache.catalina.WebResourceSet;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.JarResourceSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tom.kit.clazz.ReflectUtil;


/**
 * Abstraction to add resources that works with both Tomcat 8 and 7.
 *
 * @author Dave Syer
 * @author Phillip Webb
 */
abstract class TomcatResources {

	Logger log = LoggerFactory.getLogger("TomcatResources");
	private final Context context;

	TomcatResources(Context context) {
		this.context = context;
	}

	/**
	 * Add resources from the classpath.
	 */
	public void addClasspathResources() {
		ClassLoader loader = getClass().getClassLoader();
		if (loader instanceof URLClassLoader) {
			for (URL url : ((URLClassLoader) loader).getURLs()) {
				String file = url.getFile();
				if (file.endsWith(".jar") || file.endsWith(".jar!/")) {
					String jar = url.toString();
//					if (!jar.startsWith("jar:")) {
//						// A jar file in the file system. Convert to Jar URL.
//						jar = "jar:" + jar + "!/";
//					}
					addJar(jar);
				}
				else if (url.toString().startsWith("file:")) {
					String dir = url.toString().substring("file:".length());
					if (new File(dir).isDirectory()) {
						addDir(dir, url);
					}
				}
				log.info("Tomcat Embed Load Resource :: "+url.toString());
			}
		}
	}

	protected final Context getContext() {
		return this.context;
	}

	/**
	 * Called to add a JAR to the resources.
	 * @param jar the URL spec for the jar
	 */
	protected abstract void addJar(String jar);

	/**
	 * Called to add a dir to the resource.
	 * @param dir the dir
	 * @param url the URL
	 */
	protected abstract void addDir(String dir, URL url);

	/**
	 * Return a {@link TomcatResources} instance for the currently running Tomcat version.
	 * @param context the tomcat context
	 * @return a {@link TomcatResources} instance.
	 */
	public static TomcatResources get(Context context) {
		try{
			Class.forName("org.apache.catalina.deploy.ErrorPage");
			return new Tomcat7Resources(context);
		}catch(Exception e){
			return new Tomcat8Resources(context);
		}
	}

	/**
	 * {@link TomcatResources} for Tomcat 7.
	 */
	private static class Tomcat7Resources extends TomcatResources {

		private final Method addResourceJarUrlMethod;

		Tomcat7Resources(Context context) {
			super(context);
			this.addResourceJarUrlMethod = ReflectUtil.findMethod(context.getClass(),
					"addResourceJarUrl", URL.class);
		}

		@Override
		protected void addJar(String jar) {
			URL url = getJarUrl(jar);
			if (url != null) {
				try {
					this.addResourceJarUrlMethod.invoke(getContext(), url);
				}
				catch (Exception ex) {
					throw new IllegalStateException(ex);
				}
			}
		}

		private URL getJarUrl(String jar) {
			try {
				return new URL(jar);
			}
			catch (MalformedURLException ex) {
				// Ignore
				return null;
			}
		}

		@Override
		protected void addDir(String dir, URL url) {
			if (getContext() instanceof ServletContext) {
				try {
					Class<?> fileDirContextClass = Class
							.forName("org.apache.naming.resources.FileDirContext");
					Method setDocBaseMethod = ReflectUtil
							.findMethod(fileDirContextClass, "setDocBase", String.class);
					Object fileDirContext = fileDirContextClass.newInstance();
					setDocBaseMethod.invoke(fileDirContext, dir);
					Method addResourcesDirContextMethod = ReflectUtil.findMethod(
							StandardContext.class, "addResourcesDirContext",
							DirContext.class);
					addResourcesDirContextMethod.invoke(getContext(), fileDirContext);
				}
				catch (Exception ex) {
					throw new IllegalStateException("Tomcat 7 reflection failed", ex);
				}
			}
		}
	}

	/**
	 * {@link TomcatResources} for Tomcat 8.
	 */
	static class Tomcat8Resources extends TomcatResources {

		Tomcat8Resources(Context context) {
			super(context);
		}

		@Override
		protected void addJar(String jar) {
			try {
				if(jar.indexOf("cocook")== -1) return;
				URL url = new URL(jar);
				WebResourceSet resourceSet = new JarResourceSet(getContext().getResources(), "/WEB-INF/classes", url.getPath(), "/");
				getContext().getResources().addPreResources(resourceSet);
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		@Override
		protected void addDir(String dir, URL url) {
			addResourceSet(url.toString());
		}

		private void addResourceSet(String resource) {
			try {
				URL url = new URL(resource);
				WebResourceSet resourceSet = new DirResourceSet(getContext().getResources(), "/WEB-INF/classes", url.getPath(), "/");
				getContext().getResources().addPreResources(resourceSet);
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
		}


	}

}
