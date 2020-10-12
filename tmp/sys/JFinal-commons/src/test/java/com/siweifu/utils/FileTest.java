package com.siweifu.utils;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

public class FileTest {

	@Test
	public void test() throws IOException {
		File tempFile = File.createTempFile("base64", ".jpg");
		
		File tf1 = File.createTempFile("base64", ".jpg");

		System.out.println(tempFile.getPath());
		System.out.println(tf1.getPath());
	}
}
