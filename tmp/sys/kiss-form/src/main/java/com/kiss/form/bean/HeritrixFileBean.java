/**
 * HeritrixFileBean.java
 * hrrm-form
 * com.hrrm.kiss.form.bean		
 */
package com.kiss.form.bean;

/**
 * @author leyvi
 * @since 2013-8-13下午5:43:51
 */
public class HeritrixFileBean {
	/**
	 * 文件名
	 */
	private String fileName;
	/**
	 * 文件路径
	 */
	private String fileUrl;
	/**
	 * 文件大小
	 */
	private long fileSize;
	/**
	 * 文件类型
	 */
	private String fileType;

	/**
	 * 是否是文件夹
	 */
	private boolean isDirectory;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public boolean getIsDirectory() {
		return isDirectory;
	}

	public void setIsDirectory(boolean isDirectory) {
		this.isDirectory = isDirectory;
	}

}
