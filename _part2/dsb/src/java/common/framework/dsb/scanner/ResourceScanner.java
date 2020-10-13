package common.framework.dsb.scanner;

import java.io.File;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ResourceScanner {
	/**
	 * 扫描给定目录下以suffix为后缀名的文件路径
	 * 
	 * @param resourceDir
	 *            根目录
	 * @param suffix
	 *            目标文件后缀名
	 * @return 所有符合后缀名的文件路径
	 * @throws Exception
	 */
	public Set<String> scanDirectory(String resourceDir, String suffix, String bypass) throws Exception {
		File dirFile = new File(resourceDir);
		if (!dirFile.exists()) {
			throw new Exception("File " + resourceDir + " not found.");
		}
		Set<String> result = new HashSet<String>();
		handleDirectory(result, dirFile, null, suffix, bypass);
		return result;

	}

	/**
	 * 扫描给定文件(压缩包)内以suffix为后缀的文件路径
	 * 
	 * @param archiveFile
	 * @return
	 * @throws Exception
	 */
	public Collection<String> scanArchiveFile(File archiveFile, String suffix) throws Exception {
		if (!archiveFile.exists()) {
			throw new Exception("File " + archiveFile + " not found.");
		}
		Set<String> result = new HashSet<String>();
		handleArchive(result, archiveFile, suffix);
		return result;
	}

	private void handleDirectory(final Set<String> result, final File file, String path, String suffix, String bypass) throws Exception {
		if (file.getName().equals(bypass)) {
			return;
		}
		File[] childFiles = file.listFiles();
		if (childFiles == null) {
			return;
		}

		for (final File childFile : file.listFiles()) {
			final String newPath = (path == null) ? childFile.getName() : path + '/' + childFile.getName();
			if (childFile.isDirectory()) {
				handleDirectory(result, childFile, newPath, suffix, bypass);
			} else if (isArchiveFile(childFile.getName())) {
				handleArchive(result, childFile, suffix);
			} else {
				handleFile(result, newPath, suffix);
			}
		}
	}

	private void handleArchive(final Set<String> result, final File file, String suffix) throws Exception {

		try {
			final ZipFile zip = new ZipFile(file);
			final Enumeration<? extends ZipEntry> entries = zip.entries();
			while (entries.hasMoreElements()) {
				final ZipEntry entry = entries.nextElement();
				final String name = entry.getName();
				handleFile(result, name, suffix);
			}
		} catch (Exception ex) {
			// 不能抛出异常，否则导致DSB启动失败
			ex.printStackTrace(System.out);
		}
	}

	private void handleFile(final Set<String> result, String name, String suffix) {
		if (name.endsWith(suffix)) {
			result.add(name);
		}
	}

	private boolean isArchiveFile(String fileName) {
		return fileName.endsWith(".jar") || fileName.endsWith(".zip") || fileName.endsWith(".rar") || fileName.endsWith(".tar") || fileName.endsWith(".gz");

	}
}
