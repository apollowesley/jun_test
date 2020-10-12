package tom.cocook;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.StandardRoot;
import org.apache.tomcat.util.scan.Constants;
import org.apache.tomcat.util.scan.StandardJarScanFilter;

import tom.net.Helper;

public class TomcatStart {
	public static void run(Class<?> clazz, String[] args) {
		try {
			tomcat_run(clazz, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static File getRootFolder(Class<?> clazz) {
		try {
			File root;
			String runningJarPath = clazz.getProtectionDomain().getCodeSource().getLocation().toURI().getPath().replaceAll("\\\\", "/");
			int lastIndexOf = runningJarPath.lastIndexOf("/target/");
			if (lastIndexOf < 0) {
				root = new File("");
			} else {
				root = new File(runningJarPath.substring(0, lastIndexOf));
			}
			System.out.println("application resolved root folder: " + root.getAbsolutePath());
			return root;
		} catch (URISyntaxException ex) {
			throw new RuntimeException(ex);
		}
	}

	public static void tomcat_run(Class<?> clazz, String args[]) throws Exception {
		int port = Helper.option(args, "-p", 80);
		File root = getRootFolder(clazz);
		System.setProperty("org.apache.catalina.startup.EXIT_ON_INIT_FAILURE", "true");
		Tomcat tomcat = new Tomcat();
		Path tempPath = Files.createTempDirectory("tomcat-base-dir");
		tomcat.setBaseDir(tempPath.toString());
		tomcat.setPort(port);
		File webContentFolder = new File(root.getAbsolutePath(), "src/main/webapp/");
		if (!webContentFolder.exists()) {
			webContentFolder = Files.createTempDirectory("default-doc-base").toFile();
		}
		StandardContext ctx = (StandardContext) tomcat.addWebapp("", webContentFolder.getAbsolutePath());

		// Set execution independent of current thread context classloader
		// (compatibility with exec:java mojo)
		ctx.setParentClassLoader(clazz.getClassLoader());

		// Disable TLD scanning by default
		if (System.getProperty(Constants.SKIP_JARS_PROPERTY) == null && System.getProperty(Constants.SKIP_JARS_PROPERTY) == null) {
			System.out.println("disabling TLD scanning");
			StandardJarScanFilter jarScanFilter = (StandardJarScanFilter) ctx.getJarScanner().getJarScanFilter();
			jarScanFilter.setTldSkip("*");
		}

		WebResourceRoot resources = new StandardRoot(ctx);
		ctx.setResources(resources);
		TomcatResources.get(resources.getContext()).addClasspathResources();

		tomcat.start();
		tomcat.getServer().await();

	}

}
