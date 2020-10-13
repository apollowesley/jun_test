package common.framework.dsb.scanner;

import java.lang.annotation.Annotation;

public interface AnnotationAware {
	void annotationAware(Class targetClass, Annotation annotation);
}