package tangjl.async;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 压缩文件为zip格式<br>
 * 压缩后的文件目录和未压缩前保持一致
 * 
 * @author from net
 * 
 */
public class ZipCompressor {
	static final int BUFFER = 8192;

	public File compress(String zipPath, String srcPath, boolean append) {
		File zipFile = new File(zipPath);
		try {
			File file = new File(srcPath);
			if (!file.exists())
				throw new RuntimeException(srcPath + "不存在！");
			FileOutputStream fileOutputStream = new FileOutputStream(new File(zipPath), append);
			CheckedOutputStream cos = new CheckedOutputStream(fileOutputStream, new CRC32());
			ZipOutputStream out = new ZipOutputStream(cos);
			String basedir = "";
			compress(file, out, basedir);
			out.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return zipFile;
	}

	private void compress(File file, ZipOutputStream out, String basedir) {
		/* 判断是目录还是文件 */
		if (file.isDirectory()) {
			System.out.println("压缩：" + basedir + file.getName());
			this.compressDirectory(file, out, basedir);
		} else {
			System.out.println("压缩：" + basedir + file.getName());
			this.compressFile(file, out, basedir);
		}
	}

	/** 压缩一个目录 */
	private void compressDirectory(File dir, ZipOutputStream out, String basedir) {
		if (!dir.exists())
			return;

		File[] files = dir.listFiles();
		for (int i = 0; i < files.length; i++) {
			/* 递归 */
			compress(files[i], out, basedir + dir.getName() + "/");
		}
	}

	/** 压缩一个文件 */
	private void compressFile(File file, ZipOutputStream out, String basedir) {
		if (!file.exists()) {
			return;
		}
		try {
			BufferedInputStream bis = new BufferedInputStream(
					new FileInputStream(file));
			ZipEntry entry = new ZipEntry(basedir + file.getName());
			out.putNextEntry(entry);
			int count;
			byte data[] = new byte[BUFFER];
			while ((count = bis.read(data, 0, BUFFER)) != -1) {
				out.write(data, 0, count);
			}
			bis.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) {
		new ZipCompressor().compress("E:/szhzip.zip", "E:/test", false);
	}
}
