package com.yonge.crud.core.generator.module;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.yonge.crud.core.FreemarkerTemplateEngine;
import com.yonge.crud.core.db.model.Table;
import com.yonge.crud.core.util.XmlUtil;

public class SpringGenerator extends FreemarkerTemplateEngine {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(SpringGenerator.class);

	public SpringGenerator() {
		super();
	}

	public void createSpringFile(String daoPackageName,
			String servicePackageName, List<Table> tables,
			Map<String, Object> data, String srcBase, String packageName,
			String targetFile, String templateFile) {
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
		File file = new File(outputFile);
		if (file.exists()) {
			Map<String, String> nsMap = new HashMap<String, String>();
			nsMap.put("ns", "http://www.springframework.org/schema/beans");
			try {
				Document doc = XmlUtil.parse(file);
				for (Table table : tables) {
					String className = table.getClassName();
					if (StringUtils.isNotBlank(className)) {
						String className_firstLower = Character
								.toLowerCase(className.charAt(0))
								+ className.substring(1);
						Object obj = XmlUtil.queryNodeValue(doc,
								"//ns:beans/ns:bean[@id='"
										+ className_firstLower + "Dao']",
								XPathConstants.NODE, nsMap);
						if (obj == null) {
							// 创建dao
							Element element = doc.createElement("bean");
							element.setAttribute("id", className_firstLower
									+ "Dao");
							element.setAttribute("class",
									"com.erayt.solar2.db.DaoProxyFactoryBean");
							// 创建property元素
							Element subElement = doc.createElement("property");
							subElement.setAttribute("name", "daoInterface");
							subElement.setAttribute("value", daoPackageName
									+ "." + className + "Dao");
							element.appendChild(subElement);

							subElement = doc.createElement("property");
							subElement.setAttribute("name", "sqlMapClient");
							subElement.setAttribute("ref", "sqlMapClient");
							element.appendChild(subElement);
							doc.getDocumentElement().appendChild(element);

						} else {
							LOGGER.warn(targetFile + "中已经存在" + className
									+ "的DAO配置");
						}

						obj = XmlUtil.queryNodeValue(doc,
								"//ns:beans/ns:bean[@id='"
										+ className_firstLower + "Service']",
								XPathConstants.NODE, nsMap);
						if (obj == null) {
							// 创建service
							Element element = doc.createElement("bean");
							element.setAttribute("id", className_firstLower
									+ "Service");
							element.setAttribute("class", servicePackageName
									+ ".impl." + className + "ServiceImpl");
							doc.getDocumentElement().appendChild(element);
						} else {
							LOGGER.warn(targetFile + "中已经存在" + className
									+ "的SERVICE配置");
						}
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
		LOGGER.debug("根据输入内容[{},{},{}]得到文件名{}", new Object[] { templateFile,
				srcBase, packageName, outputFile });
		render(data, templateFile, outputFile);
	}

}
