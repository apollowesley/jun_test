package org.duomn.naive.web.action;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

public class IndexActionTest {

	@Test
	public void test() {
		InputStream in = null;
		OutputStream out = null;
		try {
			IOUtils.copy(in, out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
