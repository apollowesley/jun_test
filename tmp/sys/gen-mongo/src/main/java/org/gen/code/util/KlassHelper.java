package org.gen.code.util;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import com.google.common.reflect.ClassPath.ClassInfo;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class KlassHelper {
	static ClassPath classpath;
	static {
		try {
			classpath = ClassPath.from(KlassHelper.class.getClassLoader());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
    public static Set<Class<?>> getClasses(String pack){ 
		Set<Class<?>> classes = new LinkedHashSet<Class<?>>(); 
		ImmutableSet<ClassInfo> topLevelClassesRecursive = classpath.getTopLevelClassesRecursive(pack);
		topLevelClassesRecursive
		.forEach(k->{
			try {
				classes.add(Class.forName(k.getName()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		return classes;
    
    }

	public static Map<String, Class<?>> getQuerysClasses(
			String queryModelPackage) {
			Map<String, Class<?>> classes = new HashMap<String, Class<?>>();
			classpath.getTopLevelClassesRecursive(queryModelPackage)
			.forEach(k->{
				try {
					classes.put(k.getSimpleName(), Class.forName(k.getName()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			return classes;
	} 
}
