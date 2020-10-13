package com.autoscript.ui.helper;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.List;

import org.junit.Test;

import com.autoscript.ui.model.xml.IXmlNode;

public class XMLHelperTest {

	@Test
	public void testDom2MapString() {
		try {
			String fileName;
//			fileName="H:\\mysoft\\autoscript\\test\\web.xml";
			fileName="H:\\mysoft\\autoscript\\test\\pdmtables.xml";
			IXmlNode node = XMLHelper.xml2Model(FileCtrlUtils.readFileToString(new File(fileName), "UTF-8"));
//			List<IXmlNode> servletNameNodes = node.getChildNodesByPath("servlet.servlet_name");
			List<IXmlNode> tableNodes = node.getChildNodesByPath("Table");
			assertTrue(true);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

}
