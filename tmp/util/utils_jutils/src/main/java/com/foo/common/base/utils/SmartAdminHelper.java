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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

/**
 * SmartAdmin1.5 版本的html模版到到jsp版本的转换
 * 
 * @author Steve
 *
 */
public class SmartAdminHelper {

	private static Logger logger = LoggerFactory
			.getLogger(SmartAdminHelper.class);

	private static String getResult(File file) throws IOException {
		Document doc = Jsoup.parse(file, "utf-8");

		doc.outputSettings().prettyPrint(true);

		// head里面会有一些自定义的style，需要特殊处理下
		if (doc.select("head > style").isEmpty()) {
			logger.info("there's no style in head tag,using defualt.");
			doc.select("head")
					.first()
					.html("<%@ include file=\"/templates/smartAdmin-head.jsp\"%>");
		} else {
			logger.info("there's style in head tag,add default before the first style.");
			doc.select("head > meta").remove();
			doc.select("head > title").remove();
			doc.select("head > link").remove();
			doc.select("head > style")
					.first()
					.before("<%@ include file=\"/templates/smartAdmin-head.jsp\"%>");
		}

		// 有header的地方一定是有权限的,即header,leftPanel,shotcut,page-footer这些一定是同时出现
		Elements headers = doc.select("header#header");
		Elements leftPanels = doc.select("aside#left-panel");
		if (!headers.isEmpty() && !leftPanels.isEmpty()) {
			Element header = headers.first();
			Element leftPanel = leftPanels.first();
			header.html("<%@ include file=\"/templates/smartAdmin-header.jsp\"%>");
			leftPanel
					.html("<%@ include file=\"/templates/smartAdmin-leftPanel.jsp\"%>");
			// shortcut后面引用通用js
			Element shortcut = doc.select("div#shortcut").first();
			shortcut.html("<%@ include file=\"/templates/smartAdmin-shortcut.jsp\"%>");
			shortcut.after("<%@ include file=\"/templates/smartAdmin-scripts.jsp\"%>");

			Element footer = doc.select("div.page-footer").first();
			footer.html("<%@ include file=\"/templates/smartAdmin-footer.jsp\"%>");

			// 处理scripts，过滤掉公共的script脚本
			Elements scripts = doc.select("script");
			String tmpSrc = "";
			for (Element element : scripts) {
				tmpSrc = element.attr("src").toString();
				if (tmpSrc.equals("js/smart-chat-ui/smart.chat.manager.min.js")) {
					logger.info("stop removing,break loop.");
					element.remove();
					break;
				}
				logger.info("removing script:{}", tmpSrc);
				element.remove();
			}
		}
		// 没有权限的页面目前有如下：forgotpassword.jsp,lock.jsp,login.jsp,register.jsp。在最后一个div插入公共js引用
		else {
			doc.select("div")
					.last()
					.after("<%@ include file=\"/templates/smartAdmin-no-privilege-scripts.jsp\"%>");

			Elements script = doc.select("script");
			for (Element element : script) {
				if (!element.attr("src").equals("")) {
					element.remove();
				}
			}
		}

		// GOOGLE ANALYTICS CODE总是在最后，删除该引用
		Element lastScript = doc.select("script").last();
		if (lastScript.html().contains("_trackPageview")) {
			logger.info("googleAnalyzeDetected:{}", file.getName());
			lastScript.remove();
		}

		// 删除所有注释
		logger.info("removing all comments now.");
		List<Node> listForRemove = Lists.newArrayList();
		for (Element e : doc.getAllElements()) {
			for (Node n : e.childNodes()) {
				if (n instanceof Comment) {
					listForRemove.add(n);
				}
			}
		}

		logger.info("removing nodes size of:{}", listForRemove.size());
		for (Node node : listForRemove) {
			// logger.info("removing node of:{}", node.toString());
			node.remove();
		}

		// logger.info("doc:\n{}", doc.toString());
		// logger.info("doc:\n{}",
		// StringEscapeUtils.unescapeHtml4(doc.toString()));
		return StringEscapeUtils.unescapeHtml4(doc.toString());
	}

	public static void main(String[] args) {

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
