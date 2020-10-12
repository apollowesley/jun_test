package com.foo.common.base.utils.laboratory;

import static com.foo.common.base.utils.FooUtils.getSeparateLine;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;
import org.junit.Test;

import com.foo.common.base.utils.FooUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class FileHelper {

	public static void main(String[] args) throws IOException {

		List<String> sss = Lists.newArrayList();
		for (String string : IOUtils.readLines(FileUtils
				.openInputStream(new File("/Users/Steve/Downloads/SMC.txt")))) {
			sss.add(string);
		}
		// System.out.println(getSeparateLine());
		List<String> outputs = Lists.newArrayList();
		String tmp;
		for (String string : sss) {
			if (string.endsWith(",") || string.endsWith("，")) {
				tmp = string.substring(0, string.length() - 1);
				// System.out.println(string.substring(0, string.length() - 1));
			} else {
				tmp = string;
				// System.out.println(string);
			}
			outputs.add(tmp);
		}
		FileUtils.writeLines(new File("/Users/Steve/Downloads/tmp.txt"),
				outputs, false);

		// quickSortFile();
	}

	@Test
	public void getFilesBetweenTimePeriod() {
		final DateTime currentDateTime = new DateTime();
		// Date startDateTime = currentDateTime.minusDays(1).withHourOfDay(0)
		// .withMinuteOfHour(0).withSecondOfMinute(0).toDate();
		// Date endDateTime = currentDateTime.withHourOfDay(0).withMinuteOfHour(0)
		// .withSecondOfMinute(0).minusSeconds(1).toDate();
		File myFile = new File("D:\\tmp\\jiandaClass");
		List<File> files = (List<File>) FileUtils.listFiles(myFile,
				new String[] { "png", "jpg" }, true);
		for (File file : files) {
			System.out.println(file.getAbsolutePath());
			System.out.println(
					FileUtils.isFileNewer(file, currentDateTime.toDate()));
		}
		// List<File> files = (List<File>) FileUtils.list.listFiles(myFile,
		// TrueFileFilter.TRUE, TrueFileFilter.TRUE);
	}

	/**
	 * 快速去重和排序d:\\tmp\\test\\log.txt里面的字符
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void quickSortFile() throws IOException {
		Set<String> sss = Sets.newTreeSet();
		for (String string : IOUtils.readLines(
				FileUtils.openInputStream(FooUtils.getTestLogFile()))) {
			sss.add(string);
		}
		System.out.println(getSeparateLine());
		for (String string : sss) {
			System.out.println(string);
		}
		FileUtils.writeLines(FooUtils.getTestLogFile(), sss, false);
	}
}
