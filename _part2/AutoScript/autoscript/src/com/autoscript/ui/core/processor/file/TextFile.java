/**
 * 
 */
package com.autoscript.ui.core.processor.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import com.autoscript.ui.helper.FileCtrlUtils;

/**
 * 文本文件实现类
 * 作者:龙色波
 * 日期:2013-10-14
 */
public class TextFile implements IAutoScriptFile {

	/* (non-Javadoc)
	 * @see com.autoscript.ui.core.processor.file.IAutoScriptFile#createFile(java.lang.String, java.lang.String, java.lang.String, boolean)
	 */
	@Override
	public void createFile(String fileName, String content, String charset,
			boolean append) throws Exception {
		Writer out = null;
		try{
			//如果目录不存在，则创建
			String path;
			path = FileCtrlUtils.getFilePath(fileName);
			if(!FileCtrlUtils.isExists(path)){
				FileCtrlUtils.createDir(path);
			}
			File toFile = new File(fileName);
			out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(toFile,append), charset));
			out.write(content);
		}finally{
			if(out!=null){
				try{
					out.flush();
				}catch(Exception e){
					e.printStackTrace();
				}
				out.close();
			}
		}
	}

}
