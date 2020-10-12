package com.foo.common.base.utils.laboratory;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.core.io.ClassPathResource;

import com.foo.common.base.utils.FooUtils;
import com.google.common.collect.Maps;

public class Poi_DocxOperation {

	public static void searchAndReplace2(String srcPath, String destPath,
			Map<String, String> map) throws Exception {

		System.out.println("begin to operation docx:" + srcPath);
		System.out.println("begin to operation docx:" + destPath);

		OPCPackage pack = POIXMLDocument.openPackage(srcPath);
		XWPFDocument doc = new XWPFDocument(pack);

		String bodyElementType = "";
		for (IBodyElement iBodyElement : doc.getBodyElements()) {
			bodyElementType = iBodyElement.getElementType().toString();
			// logger.info("deal type of:{} now", bodyElementType);
			if (bodyElementType.equals("TABLE")) {

				XWPFTable myTable = (XWPFTable) iBodyElement;
				for (XWPFTableRow row : myTable.getRows()) {

					for (XWPFTableCell cell : row.getTableCells()) {

						for (XWPFParagraph myParagraph : cell.getParagraphs()) {

							for (XWPFRun r : myParagraph.getRuns()) {
								// logger.info("test:{}", r.getText(0));
								for (String key : map.keySet()) {
									if (r.getText(0).contains(key)) {
										r.setText(map.get(key), 0);
									}
								}
							}
						}
					}
				}

			} else if (bodyElementType.equals("PARAGRAPH")) {

				XWPFParagraph myParagraph = (XWPFParagraph) iBodyElement;

				for (XWPFRun r : myParagraph.getRuns()) {

					for (String key : map.keySet()) {
						if (r.getText(0).contains(key)) {
							r.setText(map.get(key), 0);
						}
					}
				}

			} else {
				// logger.error("not dealed type of:{}", bodyElementType);
			}
		}

		FileOutputStream fos = new FileOutputStream(destPath);
		doc.write(fos);
		fos.flush();
		fos.close();
	}

	public static void main(String[] args) throws Exception {

		// logger.info("loading value map here.");

		ClassPathResource myPath = new ClassPathResource("poi.properties");

		Properties p = new Properties();
		p.load(myPath.getInputStream());

		HashMap<String, String> map = Maps.newHashMap();
		for (Object key : p.keySet()) {
			map.put(key.toString(), new String(p.getProperty(key.toString())
					.getBytes("ISO-8859-1"), "utf-8"));
		}

		String path = FilenameUtils.getFullPath(myPath.getFile()
				.getAbsolutePath());
		// if (1 == 1) {
		// return;
		// }

		// String srcPath = "c:\\Users\\Steve\\Desktop\\test.docx";
		// String destPath = "c:\\Users\\Steve\\Desktop\\tmp\\"
		// + FooUtils.generateUUID() + ".docx";
		//

		System.out.println("read directory of:" + path);
		System.out.println("read map of:" + map.toString());

		Collection<File> myFiles = FileUtils.listFiles(new File(path),
				new String[] { "docx" }, false);

		System.out.println(myFiles.size());

		for (File file : myFiles) {
			searchAndReplace2(
					FilenameUtils.concat(path, file.getName()),
					FilenameUtils.concat(path + "\\tmp",
							FooUtils.generateUUID() + ".docx"), map);
		}

	}
}
