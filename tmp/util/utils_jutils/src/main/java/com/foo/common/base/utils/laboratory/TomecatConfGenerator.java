package com.foo.common.base.utils.laboratory;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.foo.common.base.utils.FooUtils;

public class TomecatConfGenerator {
	@Test
	public void myTest() throws Exception {

		final String projectName = "rebate";
		final String projectBathPath = "D:\\programTool\\myGit\\rebate\\";
		final String tomcatConfFileName = "D:\\tmp.xml";

		DocumentBuilderFactory docFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.newDocument();

		// append resources element
		Element context = doc.createElement("Context");
		context.setAttribute("path", "/" + projectName);
		context.setAttribute("privileged", "true");
		context.setAttribute("reloadable", "false");
		context.setAttribute("docBase", projectBathPath + "src\\main\\webapp");
		doc.appendChild(context);

		// append resources element under context
		Element resources = doc.createElement("Resources");
		resources.setAttribute("className",
				"org.apache.naming.resources.VirtualDirContext");
		resources.setAttribute("extraResourcePaths", projectBathPath
				+ "target\\classes");
		context.appendChild(resources);

		// append Loader element under context
		Element loader = doc.createElement("Loader");
		loader.setAttribute("className",
				"org.apache.catalina.loader.VirtualWebappLoader");
		loader.setAttribute("virtualClasspath", projectBathPath
				+ "target\\classes" + ";" + projectBathPath + "target\\"
				+ projectName + "\\WEB-INF\\lib\\*.jar");
		context.appendChild(loader);

		// append Loader element under context
		Element jarScanner = doc.createElement("JarScanner");
		jarScanner.setAttribute("scanAllDirectories", "true");
		context.appendChild(jarScanner);

		doc.normalize();

		// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory
				.newInstance();
		Transformer transformer = transformerFactory.newTransformer();

		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(
				"{http://xml.apache.org/xslt}indent-amount", "2");
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
		transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
		transformer.setOutputProperty(OutputKeys.VERSION, "1.0");
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(tomcatConfFileName));
		transformer.transform(source, result);

		FooUtils.info("done");

	}
}
