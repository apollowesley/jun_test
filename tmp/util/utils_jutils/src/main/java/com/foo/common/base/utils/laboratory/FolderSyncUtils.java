package com.foo.common.base.utils.laboratory;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Sysnc two folders.
 * 
 * i.e:From svn to git.
 * 
 * @author Steve
 * 
 */
public class FolderSyncUtils {
	Logger logger = LoggerFactory.getLogger(FolderSyncUtils.class);

	@Test
	public void syncFolder() throws IOException {

		String srcDirPath = "D:\\zzNode\\itms3.0UI\\PLUGIN_DEV\\src\\com\\zznode\\itms\\acs\\plugin\\shtelecom\\sc\\e8c\\epon\\wband";
		String destDirPath = "D:\\programTool\\myGit\\ims\\itmsPlus_Core\\src\\main\\java\\com\\zznode\\itms\\acs\\plugin\\shtelecom\\sc\\e8c\\epon\\wband";

		File srcDir = new File(srcDirPath);
		File destDir = new File(destDirPath);

		Assert.assertTrue(srcDir.isDirectory() && destDir.isDirectory());

		logger.info("Sync start from srcDirPath:{} to destDirPath:{}",
				srcDirPath, destDirPath);

		FileUtils.copyDirectory(srcDir, destDir);

		logger.info("Sync finish");

	}
}
