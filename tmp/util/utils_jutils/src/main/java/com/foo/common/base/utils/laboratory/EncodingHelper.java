package com.foo.common.base.utils.laboratory;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

/**
 * 编码转换，将源目标文件夹内的所有文件重新用utf-8编码，并且拷贝到新的文件夹:xxx-encoding-utf8
 * 
 * @author Steve
 *
 */
public class EncodingHelper {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Test
	public void gbk2Utf8() throws IOException {
		// 截取最末一位的文件夹名称，加上后缀，拷贝到新的文件夹
		File myDirectory = new File("d:\\tmp\\test\\encoding");

		List<String> myList = Lists.newArrayList(Splitter.on("\\").split(
				myDirectory.getAbsolutePath()));

		String ori = myList.get(myList.size() - 1);

		String des = ori + "-encoding-utf8";

		List<File> files = (List<File>) FileUtils.listFiles(myDirectory,
				TrueFileFilter.TRUE, TrueFileFilter.TRUE);

		String source = null;
		String absolutePath = null;
		for (File file : files) {
			absolutePath = file.getAbsolutePath();
			logger.info("coping file now:{}", absolutePath);
			source = FileUtils.readFileToString(file, "gbk");
			FileUtils.write(new File(absolutePath.replaceFirst(ori, des)),
					source, "utf-8");
		}
	}
}
