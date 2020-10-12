package com.jeesuite.rest.filter;

import java.lang.reflect.Method;

import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;

import com.jeesuite.rest.filter.annotation.ResponseFormat;
import com.jeesuite.rest.filter.annotation.ResponseFormat.FormatType;

/**
 * 
 * @description <br>
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @date 2016年2月25日
 */
public class DefaultFilterDynamicFeature implements DynamicFeature {

	@Override
	public void configure(ResourceInfo resourceInfo, FeatureContext context) {
		// 获取资源方法
		Method resourceMethod = resourceInfo.getResourceMethod();

		if (resourceMethod != null) {

			// 获取FormatJson注解
			ResponseFormat formatJson = resourceMethod.getAnnotation(ResponseFormat.class);

			if(formatJson == null || formatJson.type().equals(FormatType.JSON)){
				context.register(DefaultWebFilter.class);
			}

		}
	}
}
