package com.yonge.crud.core.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;

import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XmlUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(XmlUtil.class);

	private static final Map<ClassLoader, DocumentBuilderFactory> DOCUMENT_BUILDER_FACTORIES = Collections
			.synchronizedMap(new WeakHashMap<ClassLoader, DocumentBuilderFactory>());

	/**
	 * 根据xpath表达式查询目标document中节点的值
	 * 
	 * @param doc
	 *            目标对象
	 * @param expression
	 *            xpath表达式
	 * @param returnType
	 *            节点值的返回类型（eg:XPathConstants.STRING）
	 * @param nsMap
	 *            如果有默认的命名空间，需设置默认的命名空间
	 * @return
	 * @throws XPathExpressionException
	 */
	public static Object queryNodeValue(final Document doc, String expression,
			QName returnType, final Map<String, String> nsMap)
			throws XPathExpressionException {
		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();

		xpath.setNamespaceContext(new NamespaceContext() {

			@SuppressWarnings("rawtypes")
			public Iterator getPrefixes(String namespaceURI) {
				throw new UnsupportedOperationException();
			}

			public String getPrefix(String namespaceURI) {
				throw new UnsupportedOperationException();
			}

			public String getNamespaceURI(String prefix) {
				String xmlns = doc.lookupNamespaceURI(prefix);
				if (StringUtils.isBlank(xmlns)) {
					xmlns = nsMap.get(prefix);
				}
				return xmlns;
			}
		});

		XPathExpression expr = xpath.compile(expression);

		return expr.evaluate(doc, returnType);
	}

	/**
	 * 将document对象写入filename文件
	 * 
	 * @param doc
	 *            目标对象
	 * @param filename
	 *            输出文件名称
	 */
	public static void writeXmlFile(Document doc, String filename) {
		try {
			// Prepare the DOM document for writing
			Source source = new DOMSource(doc);

			// Prepare the output file
			File file = new File(filename);
			Result result = new StreamResult(file);

			// Write the DOM document to the file
			Transformer xformer = TransformerFactory.newInstance()
					.newTransformer();
			xformer.setOutputProperty(OutputKeys.INDENT, "yes");
			xformer.transform(source, result);
		} catch (TransformerException e) {
			LOGGER.warn("document对象写入文件失败", e);
		}
	}

	public static DocumentBuilder getParser()
			throws ParserConfigurationException {
		return getDocumentBuilderFactory().newDocumentBuilder();
	}

	public static Document parse(InputSource is)
			throws ParserConfigurationException, SAXException, IOException {
		return getParser().parse(is);
	}

	public static Document parse(File is) throws ParserConfigurationException,
			SAXException, IOException {
		return getParser().parse(is);
	}

	public static Document parse(InputStream in)
			throws ParserConfigurationException, SAXException, IOException {
		if (in == null) {
			LOGGER.warn("XMLUtils trying to parse a null inputstream");
		}
		return getParser().parse(in);
	}

	public static Document parse(String in)
			throws ParserConfigurationException, SAXException, IOException {
		return parse(in.getBytes());
	}

	public static Document parse(byte[] in)
			throws ParserConfigurationException, SAXException, IOException {
		if (in == null) {
			LOGGER.warn("XMLUtils trying to parse a null bytes");
			return null;
		}
		return getParser().parse(new ByteArrayInputStream(in));
	}

	private static DocumentBuilderFactory getDocumentBuilderFactory() {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		if (loader == null) {
			loader = XmlUtil.class.getClassLoader();
		}
		if (loader == null) {
			return DocumentBuilderFactory.newInstance();
		}
		DocumentBuilderFactory factory = DOCUMENT_BUILDER_FACTORIES.get(loader);
		if (factory == null) {
			factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			DOCUMENT_BUILDER_FACTORIES.put(loader, factory);
		}
		return factory;
	}
}
