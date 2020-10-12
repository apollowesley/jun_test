package com.yonge.crud.core.generator.module;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.yonge.crud.core.FreemarkerTemplateEngine;
import com.yonge.crud.core.db.model.Table;
import com.yonge.crud.core.util.XmlUtil;

public final class SqlMapConfigGenerator extends FreemarkerTemplateEngine {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(SqlMapConfigGenerator.class);

	public void createSqlMapConfigFile(String sqlmapPackageName,
			List<Table> tables, Map<String, Object> data, String srcBase,
			String packageName, String targetFile, String templateFile) {
		StringBuilder buf = new StringBuilder();
		buf.append(srcBase).append(File.separator);
		buf.append(packageName.replaceAll("\\.", File.separator
				+ File.separator));// 单个的File.separator会导致无法通过正则表达式解析
		buf.append(File.separator);
		File dbDir = new File(buf.toString());
		if (!dbDir.exists()) {
			boolean flag = dbDir.mkdirs();
			if (!flag) {
				LOGGER.warn("递归创建文件夹{}失败", dbDir);
				return;
			}
		}
		buf.append(targetFile);
		String outputFile = buf.toString();
		// 判断文件是否存在，如果存在，则在原来的基础上追加
		File file = new File(outputFile);
		if (file.exists()) {
			try {
				Document doc = XmlUtil.parse(file);
				for (Table table : tables) {
					String src = sqlmapPackageName.replace('.', '/')
							+ "/SqlMap_" + table.getName() + ".xml";
					Object obj = XmlUtil.queryNodeValue(doc,
							"//sqlMapConfig/sqlMap[@resource='" + src + "']",
							XPathConstants.NODE, null);
					if (obj == null) {
						// 创建一个新的节点
						Element element = doc.createElement("sqlMap");
						element.setAttribute("resource", src);
						doc.getDocumentElement().appendChild(element);
					} else {
						LOGGER.warn(targetFile + "中已经存在" + table.getName()
								+ "配置");
					}
				}
				// 将dom写入文件
				XmlUtil.writeXmlFile(doc, outputFile);
				return;
			} catch (ParserConfigurationException e) {
				LOGGER.warn("xml文件解析失败", e);
			} catch (SAXException e) {
				LOGGER.warn("xml文件解析失败", e);
			} catch (IOException e) {
				LOGGER.warn("xml文件解析失败", e);
			} catch (XPathExpressionException e) {
				LOGGER.warn("xpath表达式异常", e);
			}
		}
		LOGGER.debug("根据输入内容[{},{},{}]得到文件名{}", new Object[] { data,
				templateFile, srcBase, outputFile });
		render(data, templateFile, outputFile);
	}
}
