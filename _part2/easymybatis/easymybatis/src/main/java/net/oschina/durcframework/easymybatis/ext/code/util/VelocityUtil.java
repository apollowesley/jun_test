/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.oschina.durcframework.easymybatis.ext.code.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.log.NullLogChute;

/**
 * Velocity工具类,根据模板内容生成文件
 */
public class VelocityUtil {

	static {
		// 禁止输出日志
		Velocity.setProperty(Velocity.RUNTIME_LOG_LOGSYSTEM, new NullLogChute());
		Velocity.init();
	}

	private static String LOG_TAG = "easymybatis";
	private static String UTF8 = "UTF-8";

	public static String generate(VelocityContext context, InputStream inputStream) {
		return generate(context, inputStream, UTF8);
	}

	public static String generate(VelocityContext context, InputStream inputStream, String charset) {
		return generate(context, new InputStreamReader(inputStream));
	}

	public static String generate(VelocityContext context, Reader reader) {
		StringWriter writer = new StringWriter();
		// 不用vm文件
		Velocity.evaluate(context, writer, LOG_TAG, reader);

		try {
			writer.close();
			reader.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return writer.toString();

	}

	public static String generate(VelocityContext context, String template) {
		return generate(context, new StringReader(template));
	}
}
