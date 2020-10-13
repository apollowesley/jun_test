package com.autoscript.ui.helper;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.autoscript.ui.model.xml.IXmlNode;

public class TemplateHelperTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testCreateFileByClassPath() {
		try {
			String fileName;
			fileName="H:\\mysoft\\autoscript\\test\\web.xml";
			IXmlNode node = XMLHelper.xml2Model(FileCtrlUtils.readFileToString(new File(fileName), "UTF-8"));

			String templateFile;
			templateFile ="H:\\mysoft\\autoscript\\test\\test.ftl";
			String resultPath;
			resultPath ="H:\\mysoft\\autoscript\\test\\test.txt";
			Map map = new HashMap();
			map.put("root", node);
			TemplateHelper.createFileByPath(node, templateFile, resultPath);
			assertTrue(true);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	@Test
	public void testFreeMarker() {
		try {
			String resultPath;
			resultPath ="H:\\mysoft\\autoscript\\test\\test.txt";
			String templateFile;
			templateFile ="H:\\mysoft\\autoscript\\test\\teststr.ftl";
			TemplateHelper.createFileByPath(new HashMap(), templateFile, resultPath);			
			assertTrue(true);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

}
