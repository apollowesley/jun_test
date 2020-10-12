package org.nature.framework.bean;

import java.io.InputStream;
import java.io.Serializable;

public class FileData implements Serializable{
	
	private static final long serialVersionUID = 1L;

	public static String docx="application/vnd.openxmlformats-officedocument.wordprocessingml.document";
	
	public InputStream inputStream;
	public String fileName;
	public String contentType;
	public FileData(InputStream inputStream, String fileName, String contentType) {
		this.inputStream = inputStream;
		this.fileName = fileName;
		this.contentType = contentType;
	}
	
}
