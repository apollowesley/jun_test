/**
 * 
 */
package com.autoscript.ui.core.processor.parse.file;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JTextArea;

import com.autoscript.ui.core.UIConstants;
import com.autoscript.ui.core.processor.file.IAutoScriptFile;
import com.autoscript.ui.core.processor.file.TextFile;
import com.autoscript.ui.core.processor.parse.function.CreateTextFileParse;
import com.autoscript.ui.core.processor.parse.function.IFunctionParse;
import com.autoscript.ui.helper.FileCtrlUtils;
import com.autoscript.ui.helper.StringHelper;
import com.autoscript.ui.helper.UIPropertyHelper;
import com.autoscript.ui.model.projecttree.TemplateModel;

/**
 * 文件解析器实现类
 * 作者:龙色波
 * 日期:2013-10-15
 */
public class FileParse implements IFileParse {
	private Map<String,IAutoScriptFile> autoScriptFileMap = new HashMap<String,IAutoScriptFile>();
	private JTextArea outputTextArea;
	public FileParse(JTextArea outputTextArea) {
		this.outputTextArea = outputTextArea; 
	}
	/* (non-Javadoc)
	 * @see com.autoscript.ui.core.processor.parse.file.IFileParse#parse(java.lang.String, com.autoscript.ui.model.projecttree.TemplateModel)
	 */
	@Override
	public void parse(String fileName, TemplateModel templateModel)
			throws Exception {
		String keys[]={UIConstants.CREATE_TEXT_FILE_PREFIX,UIConstants.FUNCTION_SUFFIX,UIConstants.CLOSE_FILE};
		String fileContent;
		fileContent = FileCtrlUtils.readFileToString(new File(fileName), UIConstants.UTF_8);
		List<Integer> foundIndexs;
		int startpos=0;
		int startfunpos;
		int endfunpos;
		Integer keypos;
		IFunctionParse funParse;
		int startconentpos;
		int endconentpos;
		String funNamePrefix;
		
		while(true){			
			foundIndexs = StringHelper.findInStrArray(startpos, fileContent, keys, true);
			 keypos= foundIndexs.get(1);
			if(keypos==-1){
				//没有文件操作函数，认为是公共模板 include到其他模板
				if(startpos==0){
					return;
				}else{
					break;
				}
			}
			//第一个必须为文件创建函数前缀
			if(keypos!=0){
				throw new Exception(UIPropertyHelper.getString("exception.invalidateSyntaxInFile",fileName,keys[keypos]));
			}
			funNamePrefix = keys[keypos];
			startfunpos = foundIndexs.get(0)+keys[keypos].length();
			//第二个必须为函数结束
			startpos =startfunpos;
			foundIndexs = StringHelper.findInStrArray(startpos, fileContent, keys, true);
			keypos= foundIndexs.get(1);
			if(keypos==-1){
				throw new Exception(UIPropertyHelper.getString("exception.missFunEndMark", keys[keypos]));
			}
			endfunpos = foundIndexs.get(0);
			//解释函数参数
			funParse = new CreateTextFileParse();
			List<Object> parameters = funParse.parseFunctionParmeter(fileContent.substring(startfunpos, endfunpos));
			//记录文件内容开始地方
			startconentpos = endfunpos+keys[keypos].length();
			//忽略当前行的回车换行
			startconentpos = ignoreLineChar(fileContent, startconentpos);
			startconentpos = ignoreLineChar(fileContent, startconentpos);
			//第三个必须为文件关闭函数
			startpos =startconentpos;
			foundIndexs = StringHelper.findInStrArray(startpos, fileContent, keys, true);
			keypos= foundIndexs.get(1);
			if(keypos==-1){
				throw new Exception(UIPropertyHelper.getString("exception.missCloseFileFun"));
			}
			//记录文件内容结束地方
			endconentpos = foundIndexs.get(0);
			//开始调用写文件方法
			IAutoScriptFile autoScriptFile = makeAutoScriptFile(funNamePrefix);
			autoScriptFile.createFile((String)parameters.get(0), fileContent.substring(startconentpos, endconentpos), templateModel.getCharsetModel().getCharset(), (Boolean)parameters.get(1));
			addOutputMessage("生成文件:"+(String)parameters.get(0));
			//改变开始位置
			startpos =endconentpos+keys[keypos].length(); 
		}
	}
	/**
	 * 忽略行
	 * @param fileContent
	 * @param startconentpos
	 * @return
	 */
	private int ignoreLineChar(String fileContent, int startconentpos) {
		if(fileContent.charAt(startconentpos)=='\r'||fileContent.charAt(startconentpos)=='\n'){
			return startconentpos+1;
		}
		return startconentpos;
	}
	/**
	 * 构造
	 * @param funname  函数名
	 * @return
	 * @throws Exception 
	 */
	private IAutoScriptFile makeAutoScriptFile(String funname) throws Exception {
		IAutoScriptFile autoScriptFile = autoScriptFileMap.get(funname);
		if(autoScriptFile==null){
			if(UIConstants.CREATE_TEXT_FILE_PREFIX.equalsIgnoreCase(funname)){
				autoScriptFile = new TextFile();
				autoScriptFileMap.put(funname, autoScriptFile);
			}else
				throw new Exception(UIPropertyHelper.getString("exception.invalidateCreateFileFun",funname));
		}
		return autoScriptFile;
	}
	public void addOutputMessage(String msg){
		if(outputTextArea!=null){
		  if(msg.endsWith("\r\n")){
		   outputTextArea.setText(outputTextArea.getText()+msg);
		  }else{
			  outputTextArea.setText(outputTextArea.getText()+msg+"\r\n");  
		  }
		}
	}
}
