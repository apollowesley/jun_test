package com.kiss.freemarker.templete;

import java.util.Map;

public interface TemplateHandler {
	public String getContent();
	public void setParameters(Map map);
	public void setDirectory(String dirName);
	public void setFilePath(String filePath);
	public void setEncoding(String encode);
}
