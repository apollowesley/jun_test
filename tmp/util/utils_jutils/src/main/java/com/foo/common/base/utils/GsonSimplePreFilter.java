package com.foo.common.base.utils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @Description: 简单gson过滤器
 * @Company: Feiynn
 * @author: Dean
 * @date: 2015年9月28日 上午10:03:15
 */
public class GsonSimplePreFilter {
	// 当includes的size >
	// 0时，属性必须在includes中才会被序列化，excludes优先于includes，全局的excludes和includes优先于过滤类中的excludes和includes
	private final Set<String> includes = Sets.newHashSet();
	private final Set<String> excludes = Sets.newHashSet();
	private final Map<String, String> translateNames = Maps.newHashMap();
	private final List<FilterClass> filterClassList = Lists.newArrayList();

	private GsonBuilder gsonBuilder;

	public GsonSimplePreFilter() {
		gsonBuilder = new GsonBuilder().disableHtmlEscaping().serializeNulls()
				.setDateFormat("yyyy-MM-dd HH:mm:ss");
	}

	public GsonSimplePreFilter(GsonBuilder gsonBuilder) {
		this.gsonBuilder = gsonBuilder;
	}

	public Gson create() {
		final int includesCount = includes.size();
		final int excludesCount = excludes.size();

		return gsonBuilder
				.addSerializationExclusionStrategy(new ExclusionStrategy() {
					@Override
					public boolean shouldSkipField(FieldAttributes attr) {
						if (excludesCount > 0
								&& excludes.contains(attr.getName())) {
							return true;
						} else {
							if (includesCount > 0
									&& !includes.contains(attr.getName())) {
								return true;
							}
						}

						for (FilterClass filterClass : filterClassList) {
							if (attr.getDeclaringClass().getName()
									.equals(filterClass.getClazz().getName())) {
								if (filterClass.getExcludes().size() > 0
										&& filterClass.getExcludes().contains(
												attr.getName())) {
									return true;
								} else {
									if (filterClass.getIncludes().size() > 0
											&& !filterClass.getIncludes()
													.contains(attr.getName())) {
										return true;
									}
								}
							}
						}

						return false;
					}

					@Override
					public boolean shouldSkipClass(Class<?> clazz) {
						return false;
					}
				}).setFieldNamingStrategy(new FieldNamingStrategy() {
					@Override
					public String translateName(Field field) {
						if (translateNames.get(field.getName()) != null) {
							return translateNames.get(field.getName());
						} else {
							for (FilterClass filterClass : filterClassList) {
								if (field
										.getDeclaringClass()
										.getName()
										.equals(filterClass.getClazz()
												.getName())) {
									if (filterClass.getTranslateNames().get(
											field.getName()) != null) {
										return filterClass.getTranslateNames()
												.get(field.getName());
									}
								}
							}
						}
						return field.getName();
					}
				}).create();
	}

	/**
	 * 需要保留的属性
	 * 
	 * @param attr
	 * @return
	 */
	public GsonSimplePreFilter setIncludes(String... attr) {
		includes.addAll(Arrays.asList(attr));
		return this;
	}

	/**
	 * 需要排除的属性
	 * 
	 * @param attr
	 * @return
	 */
	public GsonSimplePreFilter setExcludes(String... attr) {
		excludes.addAll(Arrays.asList(attr));
		return this;
	}

	/**
	 * 转换属性名称
	 * 
	 * @param old
	 *            转换前
	 * @param now
	 *            转换后
	 * @return
	 */
	public GsonSimplePreFilter setTranslateNames(String old, String now) {
		translateNames.put(old, now);
		return this;
	}

	public Set<String> getIncludes() {
		return includes;
	}

	public Set<String> getExcludes() {
		return excludes;
	}

	public Map<String, String> getTranslateNames() {
		return translateNames;
	}

	public List<FilterClass> getFilterClassList() {
		return filterClassList;
	}

	public FilterClassBuidler createBuilder() {
		return new FilterClassBuidler(this);
	}

	/**
	 * 静态内部类构造器
	 *
	 */
	public static final class FilterClassBuidler {

		private GsonSimplePreFilter gsonFilter;
		private FilterClass filterClass;

		FilterClassBuidler(GsonSimplePreFilter instant) {
			gsonFilter = instant;
		}

		/**
		 * 设置要进行过滤的类
		 * 
		 * @param clazz
		 * @return
		 */
		public FilterClassBuidler setFilterClass(Class<?> clazz) {
			filterClass = new FilterClass();
			filterClass.setClazz(clazz);
			gsonFilter.getFilterClassList().add(filterClass);
			return this;
		}

		/**
		 * 设置要过滤的类中需要被排除的属性
		 * 
		 * @param attr
		 * @return
		 */
		public FilterClassBuidler setClassExcludes(String... attr) {
			filterClass.getExcludes().addAll(Arrays.asList(attr));
			return this;
		}

		/**
		 * 设置要过滤的类中需要被包含的属性
		 * 
		 * @param attr
		 * @return
		 */
		public FilterClassBuidler setClassIncludes(String... attr) {
			filterClass.getIncludes().addAll(Arrays.asList(attr));
			return this;
		}

		/**
		 * 设置要过滤的类中需要被转换的属性
		 * 
		 * @return
		 */
		public FilterClassBuidler setClassTranslateNames(String old, String now) {
			filterClass.getTranslateNames().put(old, now);
			return this;
		}

		/**
		 * 构造为GsonSimplePreFilter
		 * 
		 * @return
		 */
		public GsonSimplePreFilter buildPreFilter() {
			return gsonFilter;
		}
	}

	public static final class FilterClass {
		Class<?> clazz;
		Set<String> includes = new HashSet<String>();
		Set<String> excludes = new HashSet<String>();
		Map<String, String> translateNames = new HashMap<String, String>();

		public Class<?> getClazz() {
			return clazz;
		}

		public void setClazz(Class<?> clazz) {
			this.clazz = clazz;
		}

		public Set<String> getIncludes() {
			return includes;
		}

		public void setIncludes(Set<String> includes) {
			this.includes = includes;
		}

		public Set<String> getExcludes() {
			return excludes;
		}

		public void setExcludes(Set<String> excludes) {
			this.excludes = excludes;
		}

		public Map<String, String> getTranslateNames() {
			return translateNames;
		}

		public void setTranslateNames(Map<String, String> translateNames) {
			this.translateNames = translateNames;
		}

	}
}
