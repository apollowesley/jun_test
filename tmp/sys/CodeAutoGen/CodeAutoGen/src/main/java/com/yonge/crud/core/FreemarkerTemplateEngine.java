package com.yonge.crud.core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

public class FreemarkerTemplateEngine {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(FreemarkerTemplateEngine.class);

	private static Configuration cfg;

	static {
		cfg = new Configuration();
		cfg.setOutputEncoding("UTF-8");
		cfg.setClassForTemplateLoading(FreemarkerTemplateEngine.class,
				"/template/");
		cfg.setObjectWrapper(new DefaultObjectWrapper());
	}

	public void render(Map<String, Object> data, String templateFile,
			File outputFile) {
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			Template temp = cfg.getTemplate(templateFile);
			fw = new FileWriter(outputFile);
			bw = new BufferedWriter(fw);
			temp.process(data, bw);
			bw.flush();
		} catch (Exception e) {
			LOGGER.warn(String.format("将数据%1$s通过FreeMarker模板%2$s生成文件%3$s失败",
					data, templateFile, outputFile), e);
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					LOGGER.warn("BufferedWriter关闭异常");
				}
			}
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					LOGGER.warn("FileWriter关闭异常");
				}
			}
		}
	}

	public void render(Map<String, Object> data, String templateFile,
			String outputFile) {
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			Template temp = cfg.getTemplate(templateFile);
			File file = new File(outputFile);
			fw = new FileWriter(file);
			bw = new BufferedWriter(fw);
			temp.process(data, bw);
			bw.flush();
		} catch (Exception e) {
			LOGGER.warn(String.format("将数据%1$s通过FreeMarker模板%2$s生成文件%3$s失败",
					data, templateFile, outputFile), e);
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					LOGGER.warn("BufferedWriter关闭异常");
				}
			}
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					LOGGER.warn("FileWriter关闭异常");
				}
			}
		}
	}

}
