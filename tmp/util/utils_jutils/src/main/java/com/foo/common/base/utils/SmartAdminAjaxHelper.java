package com.foo.common.base.utils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Comment;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;

import com.google.common.collect.Lists;

/**
 * SmartAdmin1.5 版本的html模版到到jsp版本的转换
 * 
 * @author Steve
 *
 */
public class SmartAdminAjaxHelper {

	private final static ch.qos.logback.classic.Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory
			.getLogger("com.foo");

	// private static Logger logger = LoggerFactory
	// .getLogger(SmartAdminAjaxHelper.class);

	public static Document deleteDocComments(Document doc) {
		logger.info("removing all comments,Start");
		List<Node> listForRemove = Lists.newArrayList();
		for (Element e : doc.getAllElements()) {
			for (Node n : e.childNodes()) {
				if (n instanceof Comment) {
					listForRemove.add(n);
				}
			}
		}

		logger.info("removing all comments,End for size:{}",
				listForRemove.size());
		for (Node node : listForRemove) {
			node.remove();
		}
		return doc;
	}

	private static String getResult(File file) throws IOException {
		Document doc = Jsoup.parse(file, "utf-8");

		doc.outputSettings().prettyPrint(false);

		String absPath = "{smartAdmin_home}/";

		Elements headLink = doc.select("head > link");
		if (!headLink.isEmpty()) {
			String tmpHref = "";
			for (Element element : headLink) {
				tmpHref = element.attr("href");

				if (tmpHref.startsWith("http")) {
					logger.warn("there's url,special deal please:{}", tmpHref);
					continue;
				}

				element.attr("href", absPath + element.attr("href"));
			}
		}

		// doc = deleteDocComments(doc);

		String fileHtmlStr = StringEscapeUtils.unescapeHtml4(doc.toString());
		String jspHead = "<%@ page contentType=\"text/html;charset=UTF-8\" language=\"java\"%>";
		StringBuilder sb = new StringBuilder();
		sb.append(jspHead);
		sb.append("\n");
		sb.append(fileHtmlStr);

		logger.trace("result is:\n{}", sb);

		return sb.toString();
	}

	public static void main(String[] args) {

		logger.setLevel(Level.INFO);

		String workingDir = "d:\\tmp\\smartAdmin1.5";
		String srcDir = workingDir + "\\src";
		String targetDir = workingDir + "\\target";

		List<String> errorFileNames = Lists.newArrayList();

		FileUtils.getFile(new File("d:\\tmp\\smartAdmin1.5"), "test.java");

		IOFileFilter filter = new WildcardFileFilter("*.html");

		logger.info("delete directory:{}");
		FileUtils.deleteQuietly(new File(targetDir));

		String newFileName = "";
		for (File file : FileUtils.listFiles(new File(srcDir), filter, null)) {
			newFileName = FilenameUtils.concat(targetDir,
					FilenameUtils.getBaseName(file.getName()) + ".jsp");
			logger.info("deal with file:{}", newFileName);
			try {
				FileUtils.writeStringToFile(new File(newFileName),
						getResult(file), "utf-8", false);
			} catch (Exception e) {
				errorFileNames.add(newFileName);
				e.printStackTrace();
				continue;
			}
		}
		logger.info("error files are following ones.");
		for (String string : errorFileNames) {
			logger.info(string);
		}

	}
}
