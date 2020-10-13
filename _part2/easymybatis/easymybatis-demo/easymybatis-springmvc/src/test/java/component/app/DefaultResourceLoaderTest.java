package component.app;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import junit.framework.TestCase;

public class DefaultResourceLoaderTest extends TestCase {

	@Test
	public void testRun() {
		DefaultResourceLoader loader = new DefaultResourceLoader();
		Resource res = loader.getResource("/log4j.properties");
		Assert.assertTrue(res.exists());
		try {
			System.out.println(res.getURI().toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testRun2() {
		DefaultResourceLoader loader = new DefaultResourceLoader();
		Resource res = loader.getResource("classpath:/log4j.properties");
		Assert.assertTrue(res.exists());
		try {
			System.out.println(res.getURI().toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testRun3() {
		DefaultResourceLoader loader = new DefaultResourceLoader();
		Resource res = loader.getResource("file:/D:/workspace-em/easymybatis-springmvc/target/test-classes/log4j.properties");
		Assert.assertTrue(res.exists());
		try {
			System.out.println(res.getURI().toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
