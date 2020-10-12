package org.nature4j.framework.quartz;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.nature4j.framework.annotation.Schedule;
import org.nature4j.framework.helper.ConfigHelper;
import org.nature4j.framework.helper.IocHelper;
import org.nature4j.framework.util.ClassUtil;
import org.nature4j.framework.util.ReflectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuartzJobHelper {
	private static Logger LOGGER = LoggerFactory.getLogger(QuartzJobHelper.class);
	private static Map<String, QuartzJobBean> quartzJobMap = new HashMap<String, QuartzJobBean>();

	public static void initQuartzJob() {
		Set<Class<?>> quartzJobClassSet = ClassUtil.getClassSet(ConfigHelper.getAppBasePackage(), org.nature4j.framework.annotation.QuartzJob.class);
		for (Class<?> cls : quartzJobClassSet) {
			Object quartzJobObjet = ReflectionUtil.newInstance(cls);
			IocHelper.injectService(quartzJobObjet, cls);
			QuartzJobBean quartzJobBean = new QuartzJobBean();
			quartzJobBean.setQuartzJobObject(quartzJobObjet);
			Method[] methods = cls.getMethods();
			Map<String, QuartzMethodBean> quartzMethodMap = new HashMap<String, QuartzMethodBean>();
			for (Method method : methods) {
				method.setAccessible(true);
				if (method.isAnnotationPresent(Schedule.class)) {
					Schedule schedule = method.getAnnotation(Schedule.class);
					String corn = schedule.corn();
					if (!"".equals(corn)) {
						QuartzMethodBean quartzMethodBean = new QuartzMethodBean(method, corn);
						quartzMethodMap.put(method.getName(), quartzMethodBean);
					} else {
						LOGGER.error("Schedule's corns is not defined");
						throw new RuntimeException("Schedule's corns is not defined");
					}
				} else {
					continue;
				}
			}
			quartzJobBean.setMethods(quartzMethodMap);
			quartzJobMap.put(cls.getName(), quartzJobBean);
		}
		if (!quartzJobClassSet.isEmpty()) {
			NatureQuartzManager.initScheduler();
			NatureQuartzManager.startQuartzScheduler();
		}
	}

	public static QuartzJobBean getQuartzJobBean(String clsName) {
		return quartzJobMap.get(clsName);
	}

	public static Map<String, QuartzJobBean> getQuartzJobMap() {
		return quartzJobMap;
	}

	public static void clear() {
		if (!quartzJobMap.isEmpty()) {
			quartzJobMap.clear();
			NatureQuartzManager.stopAll();
		}
	}

}
