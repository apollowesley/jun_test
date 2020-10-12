package com.bj58.supin.plugins.codegenerator.core.util;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Properties;

/**
 * Velocity工具类,根据模板内容生成文件
 */
public class VelocityUtil {


	public static String render(String vm, VelocityContext context) {
		String content = "";

		Template template = null;
		try {
			template = Velocity.getTemplate(vm);
			StringWriter writer = new StringWriter();
			if (template != null)
				template.merge(context, writer);
			writer.flush();
			writer.close();
			content = writer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return content;
	}

}
