package site.yaotang.xgen.orm.tools;

import org.junit.Test;

import junit.framework.TestCase;

public class XMLFactoryTest extends TestCase {

	private static final String XGENORM_DATASOURCE_XML = "/xgenorm.datasource.xml";

	@Test
	public void test1() {
		XMLFactory.readDBInfo(XMLFactoryTest.class.getResource(XGENORM_DATASOURCE_XML).getFile());
		XMLFactory.readTableInfo(XMLFactoryTest.class.getResource(XGENORM_DATASOURCE_XML).getFile());
		System.out.println(Constant.TABLEMAP);
	}

}
