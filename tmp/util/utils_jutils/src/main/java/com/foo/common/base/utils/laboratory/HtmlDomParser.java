package com.foo.common.base.utils.laboratory;

import java.io.File;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class HtmlDomParser {
	public static void main(String[] args) throws Exception {

		File input = new File("D:\\tmp\\xUtils\\test.html");

		Document doc = Jsoup.parse(input, "UTF-8");

		// Elements pngs = doc.select("table#archiveResult tr:gt(0)");
		// Elements pngs = doc.select("span");

		for (Element element : doc.getAllElements()) {

			if (element.tagName().equals("span")) {
				System.out.println(element.text());
			} else if (element.tagName().equals("img")) {
				System.out.println(element.attr("src").toString());
			} else {

			}

			// System.out.println(element.select("td.name").text());
			// System.out.println(element.select("a:eq(1)").attr("href"));
		}

	}
}
