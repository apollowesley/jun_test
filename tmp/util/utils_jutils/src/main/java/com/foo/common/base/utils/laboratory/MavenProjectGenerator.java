package com.foo.common.base.utils.laboratory;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.codec.CharEncoding;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.foo.common.base.utils.FooUtils;
import com.google.common.collect.Lists;

/**
 * 指定需要生成的maven项目的名称，jdk的版本号，模版的位置，拷贝到的目录即可完成模版的生成
 * 
 * @author Steve
 * 
 */
public class MavenProjectGenerator {

	Logger logger = LoggerFactory.getLogger("MavenProjectGenerator");

	final String newProjectName = "spring4-mvc-template";
	// final String jdkVersion = "jdk1.5.0_22";
	// final String jdkVersion = "jdk1.6.0_43";
	final String jdkVersion = "jdk1.7.0_67";

	// Template location here
	final File mavenProjectDirectory = new File(
			"C:\\Users\\Steve\\Desktop\\emptyMavenTemplate-1.0");

	// output Directory here
	final String desPath = "C:\\Users/Steve/Desktop";

	@Test
	public void generateNewProject() throws IOException {

		String newDesDirectoryStr = FilenameUtils.concat(desPath,
				newProjectName);

		File newDesDirectory = new File(newDesDirectoryStr);

		FileUtils.copyDirectory(mavenProjectDirectory, newDesDirectory);

		List<String> dealFileNames = Lists.newArrayList();
		dealFileNames.add("pom.xml");
		dealFileNames.add(".project");
		dealFileNames.add(".classpath");

		File myFile = null;
		for (String dealFileName : dealFileNames) {
			myFile = new File(FilenameUtils.concat(newDesDirectoryStr,
					dealFileName));
			dealFile(myFile);
		}
		FooUtils.info("generateNewProject end");
	}

	public void dealFile(File myFile) throws IOException {
		FooUtils.info("deal with file:{}", myFile.getName());
		List<String> myLines = FileUtils.readLines(myFile, CharEncoding.UTF_8);
		List<String> myNewLines = Lists.newArrayList();
		for (String string : myLines) {
			if (string.contains("defaultTemplateName")) {
				string = string.replace("defaultTemplateName", newProjectName);
			}
			if (myFile.getName().equals(".classpath")
					&& string.contains("jdkVersion")) {
				string = string.replace("jdkVersion", jdkVersion);
			}
			myNewLines.add(string);
		}
		FileUtils.writeLines(myFile, myNewLines, "\n");
		FooUtils.info("deal with file,end:" + myFile.getName());
	}

	public void generateMavenProjectTemplate() {

		try {
			String filepath = "D:\\1.xml";
			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(filepath);

			// Get the root element
			// Node company = doc.getFirstChild();

			// Get the staff element , it may not working if tag has spaces, or
			// whatever weird characters in front...it's better to use
			// getElementsByTagName() to get it directly.
			// Node staff = company.getFirstChild();

			// Get the staff element by tag name directly
			Node staff = doc.getElementsByTagName("staff").item(0);

			// update staff attribute
			NamedNodeMap attr = staff.getAttributes();
			Node nodeAttr = attr.getNamedItem("id");
			nodeAttr.setTextContent("2");

			// append a new node to staff
			Element age = doc.createElement("age");
			age.appendChild(doc.createTextNode("28"));
			staff.appendChild(age);

			// loop the staff child node
			NodeList list = staff.getChildNodes();

			for (int i = 0; i < list.getLength(); i++) {

				Node node = list.item(i);

				// get the salary element, and update the value
				if ("salary".equals(node.getNodeName())) {
					node.setTextContent("2000000");
				}

				// remove firstname
				if ("firstname".equals(node.getNodeName())) {
					staff.removeChild(node);
				}

			}

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(filepath));
			transformer.transform(source, result);

			FooUtils.info("Done");

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (SAXException sae) {
			sae.printStackTrace();
		}
	}
}
