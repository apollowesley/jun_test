package common.framework.dsb.scanner;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

public class DefaultAnnotationScanner implements AnnotationScanner {

	private String scanDirectory = null;
	private String bypass = null;

	private Set<Class> annotationClassList = new HashSet<Class>();

	private Set<AnnotationAware> listeners = new HashSet<AnnotationAware>();

	public DefaultAnnotationScanner(String scanDirectory, String bypass) {
		super();
		this.scanDirectory = scanDirectory;
		this.bypass = bypass;
	}

	@Override
	public void start() throws Exception {
		ClassLoader clazzLoader = Thread.currentThread().getContextClassLoader();
		ResourceScanner scanner = new ResourceScanner();
		Set<String> classFiles = scanner.scanDirectory(scanDirectory, ".class", bypass);
		for (String classFilePath : classFiles) {
			String fullClassName = fileNameToClassName(classFilePath);
			try {
				Class targetClass = clazzLoader.loadClass(fullClassName);
				// check annotations
				for (Class annotationClass : annotationClassList) {
					if (targetClass.isAnnotationPresent(annotationClass)) {
						Annotation annotationObj = targetClass.getAnnotation(annotationClass);
						for (AnnotationAware listener : listeners) {
							listener.annotationAware(targetClass, annotationObj);
						}
					}
				}
			} catch (Throwable ex) {
				ex.printStackTrace(System.out);
			}
		}// for
	}

	@Override
	public void addAnnotationClass(Class annotationClass) {
		annotationClassList.add(annotationClass);
	}

	@Override
	public void close() {
	}

	@Override
	public void register(AnnotationAware annotationAware) {
		listeners.add(annotationAware);
	}

	public static void main(String[] args) {

	}

	public static String fileNameToClassName(final String filename) {
		return filename.substring(0, filename.lastIndexOf(".class")).replace('/', '.').replace('\\', '.');
	}
}
