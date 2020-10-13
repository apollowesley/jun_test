package common.framework.dsb.main;

import java.io.File;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.xml.bind.JAXBElement;

import common.framework.dsb.ResourceManager;
import common.framework.dsb.annotation.DSBCmd;
import common.framework.dsb.annotation.DSBService;
import common.framework.dsb.cmd.CmdAnnotationAware;
import common.framework.dsb.config.DSBConfType;
import common.framework.dsb.config.DisabledServiesType;
import common.framework.dsb.config.StartupServiesType;
import common.framework.dsb.config.SystemPropsType;
import common.framework.dsb.orm.MybatisSqlSessionProvider;
import common.framework.dsb.orm.SqlSessionProvider;
import common.framework.dsb.proxy.DefaultServiceProxy;
import common.framework.dsb.proxy.DefaultUploadHandler;
import common.framework.dsb.proxy.ServiceClassLoader;
import common.framework.dsb.proxy.ServiceProxy;
import common.framework.dsb.proxy.UploadHandler;
import common.framework.dsb.scanner.AnnotationAware;
import common.framework.dsb.scanner.AnnotationScanner;
import common.framework.dsb.scanner.DefaultAnnotationScanner;
import common.framework.dsb.server.DSBServer;
import common.framework.dsb.server.RMIDSBServer;
import common.framework.dsb.service.DefaultServiceConfig;
import common.framework.dsb.service.ServiceConfig;
import common.framework.format.StringFromat;
import common.framework.jaxb.JAXBTool;
import common.framework.log.Logger;

public class Main {
	public final static String SCHEMAED_PACKAGE_NAME = "common.framework.dsb.config";

	public static final String DATE_PATTERN = "yyyy-MM-dd";
	public static DateFormat dateformat = new SimpleDateFormat(DATE_PATTERN);

	private static int[] format = { 30, 30 };
	private static String license = null;

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		if (args == null || args.length < 1) {
			System.out.println("Parameter not enough: license(dsb.lcs file) required");
			return;
		}
		license = args[0];
		String configFlie = "config" + File.separator + "dsb-config.xml";
		String ormConfigFlie = "config" + File.separator + "mybatis-config.xml";
		DSBConfType dsbConfType = null;
		try {
			FileInputStream fin = new FileInputStream(configFlie);
			JAXBElement<DSBConfType> jaxbElement = (JAXBElement<DSBConfType>) JAXBTool.unmarshal(fin, SCHEMAED_PACKAGE_NAME, Main.class.getClassLoader());
			dsbConfType = jaxbElement.getValue();
			fin.close();
		} catch (Exception e) {
			e.printStackTrace(System.out);
			System.err.println("Failed to load config file:" + configFlie);
			System.exit(0);
		}

		String dsbServerName = dsbConfType.getDsbServerName();
		Logger.log(Logger.FUNCTION_LEVEL, "dsb.server.name=" + dsbServerName);
		System.out.println(StringFromat.format(new Object[] { "dsb.server.name:", dsbServerName }, format));

		String logDir = dsbConfType.getDsbServerLogDir();
		System.out.println(StringFromat.format(new Object[] { "dsb.server.log.dir:", logDir }, format));

		String logName = dsbConfType.getDsbServerLogName();
		System.out.println(StringFromat.format(new Object[] { "dsb.server.log.name:", logName }, format));

		int logLevel = dsbConfType.getDsbServerLogLevel();
		System.out.println(StringFromat.format(new Object[] { "dsb.server.log.level:", logLevel }, format));

		long logMaxFileSize = dsbConfType.getDsbServerLogMaxfilesize();
		if (logMaxFileSize <= 0) {
			logMaxFileSize = 2;
		}
		System.out.println(StringFromat.format(new Object[] { "dsb.server.log.maxfilesize:", logMaxFileSize + "M Bytes" }, format));
		// Logger initialize
		Logger.initialize(logDir, logName, logLevel, logMaxFileSize * 1024 * 1000);

		// check DSB license
		try {
			checkLCS(license, true);
		} catch (Exception ex) {
			ex.printStackTrace(System.out);
			System.exit(0);
		}

		// server port
		int serverPort = dsbConfType.getDsbSocketPort();
		Logger.log(Logger.FUNCTION_LEVEL, "dsb.socket.port=" + serverPort);
		System.out.println(StringFromat.format(new Object[] { "dsb.socket.port:", serverPort }, format));

		// RMI port
		int rmiPort = dsbConfType.getDsbRmiServerPort();
		Logger.log(Logger.FUNCTION_LEVEL, "dsb.rmi.server.port=" + rmiPort);
		System.out.println(StringFromat.format(new Object[] { "dsb.rmi.server.port:", rmiPort }, format));

		// service deploy directory
		String serviceDir = dsbConfType.getDsbDeployDir();
		Logger.log(Logger.FUNCTION_LEVEL, "dsb.deploy.dir=" + serviceDir);
		System.out.println(StringFromat.format(new Object[] { "dsb.deploy.dir:", serviceDir }, format));

		// annotation scanning bypass directory
		String bypassDir = dsbConfType.getDsbAnnotationBypassDir();
		Logger.log(Logger.FUNCTION_LEVEL, "dsb.annotation.bypass.dir=" + bypassDir);
		System.out.println(StringFromat.format(new Object[] { "dsb.annotation.bypass.dir:", bypassDir }, format));

		// Server default language
		String language = dsbConfType.getDsbServerLanguage();
		Logger.log(Logger.FUNCTION_LEVEL, "dsb.server.language=" + language);
		System.out.println(StringFromat.format(new Object[] { "dsb.server.language:", language }, format));

		ResourceManager.locale = new Locale(language);

		int cmdServerPort = dsbConfType.getCmdServerPort();
		Logger.log(Logger.FUNCTION_LEVEL, "cmd.server.port=" + cmdServerPort);
		System.out.println(StringFromat.format(new Object[] { "cmd.server.port:", cmdServerPort }, format));

		int cmdThreadCount = dsbConfType.getCmdThreadCount();
		Logger.log(Logger.FUNCTION_LEVEL, "cmd.thread.count=" + cmdThreadCount);
		System.out.println(StringFromat.format(new Object[] { "cmd.thread.count:", cmdThreadCount }, format));

		long cmdTimeout = dsbConfType.getCmdConnTimeout();
		Logger.log(Logger.FUNCTION_LEVEL, "cmd.conn.timeout=" + cmdTimeout);
		System.out.println(StringFromat.format(new Object[] { "cmd.conn.timeout:", cmdTimeout }, format));

		// startup services
		List<String> startupServices = new ArrayList<String>();
		StartupServiesType startupServiesType = dsbConfType.getStartupservices();
		if (startupServiesType != null) {
			int num = 0;
			for (String service : startupServiesType.getService()) {
				if (null != service && !"".equalsIgnoreCase(service.trim())) {
					num++;
					startupServices.add(service);
					Logger.log(Logger.FUNCTION_LEVEL, service);
					System.out.println(StringFromat.format(new Object[] { "startup service:", service }, format));
				}
			}
		}

		// disabled services
		List<String> disabledServies = new ArrayList<String>();
		DisabledServiesType disabledServiesType = dsbConfType.getDisabledservices();
		if (disabledServiesType != null) {
			int num = 0;
			for (String service : disabledServiesType.getService()) {
				if (null != service && !"".equalsIgnoreCase(service.trim())) {
					num++;
					disabledServies.add(service);
					Logger.log(Logger.FUNCTION_LEVEL, service);
					System.out.println(StringFromat.format(new Object[] { "disable service:", service }, format));
				}
			}
		}

		// configured system properties
		List<SystemPropsType> spts = dsbConfType.getSystemprops();
		Logger.log(Logger.FUNCTION_LEVEL, "load system properties=" + spts.size());
		for (SystemPropsType spt : spts) {
			System.setProperty(spt.getPropName(), spt.getPropValue());
			Logger.log(Logger.FUNCTION_LEVEL, spt.getPropName() + "=" + spt.getPropValue());
			System.out.println(StringFromat.format(new Object[] { "system property:", spt.getPropName() + "=" + spt.getPropValue() }, format));
		}
		printinfo(null, 80);

		// DSB kernel boot-strap classloader (dsb-x.x)
		ClassLoader currentClassLoader = Main.class.getClassLoader();
		// DSB service classloader (to load all jars in deploy-DIR)
		ServiceClassLoader newClassLoader = new ServiceClassLoader(ServiceClassLoader.EMPTY_URL_ARRAY, currentClassLoader);
		File deployables = new File(serviceDir);
		System.out.println(new Date() + " DSB deploy dir:" + deployables.getAbsolutePath());
		newClassLoader.loadLibrary(deployables);
		Thread.currentThread().setContextClassLoader(newClassLoader);

		// class annotation scanning
		final AnnotationScanner scanner = new DefaultAnnotationScanner(serviceDir, bypassDir);
		scanner.addAnnotationClass(DSBService.class);
		scanner.addAnnotationClass(DSBCmd.class);
		final ServiceConfig serviceConfig = new DefaultServiceConfig(ResourceManager.locale, serviceDir);
		final CmdAnnotationAware cmdAnnotationAware = new CmdAnnotationAware(cmdServerPort, cmdThreadCount, cmdTimeout);
		final SqlSessionProvider sqlSessionProvider = new MybatisSqlSessionProvider();
		final ServiceProxy serviceProxy = new DefaultServiceProxy(serviceConfig, sqlSessionProvider);
		final UploadHandler uploadHandler = new DefaultUploadHandler(serviceProxy, serviceConfig);
		final DSBServer dsbServer = new RMIDSBServer(serverPort, rmiPort, serviceProxy);
		final LCSChecker lcsChecker = new LCSChecker("License-checker");
		scanner.register((AnnotationAware) serviceConfig);
		scanner.register(cmdAnnotationAware);
		// system start
		try {
			System.out.println(new Date() + " DSB micro-kernel is starting.");
			Logger.log(Logger.FUNCTION_LEVEL, "DSB micro-kernel is starting.");

			// class scanner
			scanner.start();
			// command server start
			cmdAnnotationAware.start();
			// ORM
			sqlSessionProvider.start(ormConfigFlie);
			// ServiceProxy
			serviceProxy.start(startupServices, disabledServies);
			// DSBServer
			dsbServer.start();
			lcsChecker.start();
			Logger.log(Logger.FUNCTION_LEVEL, " DSB micro-kernel started.");
			System.out.println(new Date() + " DSB micro-kernel started.");
		} catch (Throwable e) {
			e.printStackTrace(System.out);
			Logger.printStackTrace(Logger.FATAL_LEVEL, "Fatal error while server startup", e);
			Logger.shutDown();
			System.exit(0);
		}

		// System shutdown
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				try {
					dsbServer.close();
					uploadHandler.close();
					serviceProxy.close();
					serviceConfig.close();
					cmdAnnotationAware.close();
				} catch (Exception e) {
				} finally {
					Logger.shutDown();
				}
			}
		});
	}

	public static void checkLCS(String license, boolean log) throws Exception {

		License lcs = new License(license);
		if (log) {
			// printinfo(null, len);
			System.out.println(StringFromat.format(new Object[] { "License name:", lcs.getName() }, format));
			System.out.println(StringFromat.format(new Object[] { "License type:", lcs.getType() }, format));
			System.out.println(StringFromat.format(new Object[] { "License MAC:", lcs.getMac() }, format));
			System.out.println(StringFromat.format(new Object[] { "License issue date:", lcs.getIssueDate() }, format));
			System.out.println(StringFromat.format(new Object[] { "License expiry date:", lcs.getExpiryDate() }, format));
			System.out.println(StringFromat.format(new Object[] { "License created on:", lcs.getCreatedOn() }, format));
			// printinfo(null, len);
			Logger.log(Logger.FUNCTION_LEVEL, license);
		}

		String interfaceInfo = CMD.runInterfaceInfo();
		if (log) {
			Logger.log(Logger.FUNCTION_LEVEL, interfaceInfo);
		}

		String type = lcs.getType();
		String expiryDateString = lcs.getExpiryDate();
		Date expiryDate = dateformat.parse(expiryDateString);
		Date currentDate = new Date();
		boolean expired = false;
		if (expiryDate.before(currentDate)) {
			expired = true;
		}
		boolean macMatched = interfaceInfo.indexOf(lcs.getMac()) >= 0;
		// Logger.log(Logger.FUNCTION_LEVEL, "macMatched:" + macMatched);
		if ("t".equalsIgnoreCase(type) || "d".equalsIgnoreCase(type)) {
			// 临时型 T license 不限 MAC 地址, 开发型 D 不限 MAC地址
			macMatched = true;
		}

		if (!macMatched) {
			System.out.println("Machine unauthorized to run DSB! please update dsb.lcs!");
			Logger.log(Logger.FUNCTION_LEVEL, "Machine unauthorized to run DSB!");
			throw new Exception("Machine unauthorized to run DSB!");
		}
		// Logger.log(Logger.FUNCTION_LEVEL, "expired:" + expired);
		if (expired) {
			System.out.println("License has been expired!");
			Logger.log(Logger.FUNCTION_LEVEL, "License has been expired!");
			Logger.log(Logger.FUNCTION_LEVEL, license);
			Logger.log(Logger.FUNCTION_LEVEL, interfaceInfo);
			throw new Exception("License has been expired!");
		}
	}

	private static void printinfo(String info, int length) {
		if (info == null) {
			StringBuffer sb = new StringBuffer("");
			for (int i = 0; i < length; i++) {
				sb.append("*");
			}
			System.out.println(sb.toString());
		} else {
			StringBuffer sb = new StringBuffer("*");
			sb.append(" ");
			sb.append(info);
			int pad = length - info.length() - 3;
			for (int i = 0; i < pad; i++) {
				sb.append(" ");
			}
			sb.append("*");
			System.out.println(sb.toString());

		}
	}// printinfo

	private static class LCSChecker extends Thread {
		private long sleepTime = 2 * 60 * 60 * 1000;

		public LCSChecker(String name) {
			super(name);
		}

		@Override
		public void run() {
			while (true) {
				try {
					sleep(sleepTime);
				} catch (InterruptedException e) {
				}
				try {
					checkLCS(license, false);
				} catch (Exception e) {
					e.printStackTrace(System.out);
					System.exit(0);
				}
			} // while
		} // run
	}// class LCSChecker
}
