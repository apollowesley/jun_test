package com.foo.common.base.gson;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class GsonAnnotationExclusionStrategy implements ExclusionStrategy {
	public boolean shouldSkipClass(Class<?> clazz) {
		return clazz.getAnnotation(GsonAnnotation.class) != null;
	}

	public boolean shouldSkipField(FieldAttributes f) {
		return f.getAnnotation(GsonAnnotation.class) != null;
	}

}
