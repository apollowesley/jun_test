package com.foo.common.base.utils.laboratory;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.foo.common.base.utils.FooUtils;
import com.google.common.base.Charsets;
import com.google.common.collect.Sets;
import com.google.common.io.Files;
import com.google.common.io.LineProcessor;

public class ImagesAnalyzer {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Test
	public void dealCssFile() throws Exception {
		// new ImagesAnalyzer().replaceURL2RelativePath();
		// new ImagesAnalyzer().parseImagesFromCssFile();
		new ImagesAnalyzer().copyImageFromUrl();
	}

	public void replaceURL2RelativePath() throws IOException {
		Set<String> mySet = Sets.newHashSet();
		mySet.add("http://res.c.hjfile.cn");
		mySet.add("http://bulo.hujiang.com");
		mySet.add("http://t.hujiang.com");
		// URL url = null;
		// for (String myUrl : Files.readLines(new File(
		// "C:\\Users\\Steve\\Desktop\\cssForNewSettings.txt"),
		// Charsets.UTF_8)) {
		// }
		for (String string : mySet) {
			System.out.println(string);
		}
	}

	public void copyImageFromUrl() throws IOException {
		URL url = null;
		for (String myUrl : Files.readLines(new File(
				"C:\\Users\\Steve\\Desktop\\test.css"), Charsets.UTF_8)) {
			try {
				url = new URL(myUrl);
				FileUtils.copyURLToFile(url, new File(
						"C:\\Users\\Steve\\Desktop\\" + url.getPath()));
			} catch (IOException e) {
				System.out.println("Copy url error of:" + myUrl);
				continue;
			}
		}
	}

	public void parseImagesFromCssFile() throws Exception {

		File myDesDirectory = new File(FooUtils.getSystemDesktopPath());

		assertTrue("You should specify directory here",
				myDesDirectory.isDirectory());

		Collection<File> myFiles = FileUtils.listFiles(myDesDirectory,
				new String[] { "css" }, false);

		Set<String> imageTypeSets = Sets.newHashSet();
		imageTypeSets.add(".png");
		imageTypeSets.add(".gif");

		final Set<String> imageTypeSetsFinal = Sets.newHashSet(imageTypeSets);

		for (File file : myFiles) {
			logger.info(file.getName());

			Set<String> mySet = Files.readLines(file, Charsets.UTF_8,
					new LineProcessor<Set<String>>() {
						Set<String> list = Sets.newHashSet();

						@Override
						public boolean processLine(String line)
								throws IOException {

							for (String imageType : imageTypeSetsFinal) {
								// logger.info("Processing lines:{}", line);
								if (line.contains(imageType)
										&& line.contains("http://")) {
									list.add(line.substring(
											line.indexOf("http://"),
											line.indexOf(imageType)
													+ imageType.length()));
								}
							}

							return true;
						}

						@Override
						public Set<String> getResult() {
							return list;
						}

					});
			for (String string : mySet) {
				System.out.println(string);
			}
		}
	}
}
