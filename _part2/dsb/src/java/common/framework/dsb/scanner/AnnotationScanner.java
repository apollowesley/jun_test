package common.framework.dsb.scanner;

public interface AnnotationScanner {

	void start() throws Exception;

	void close();

	/**
	 * 增加 annotation class
	 * 
	 * @param annotationClass
	 */
	void addAnnotationClass(Class annotationClass);

	/**
	 * 添加Annotation 监听器
	 * 
	 * @param annotationAware
	 */
	void register(AnnotationAware annotationAware);
}
