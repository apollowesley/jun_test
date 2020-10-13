/**
 * 
 */
package com.autoscript.ui.core.processor.project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JTextArea;

import org.dom4j.DocumentException;

import com.autoscript.ui.config.AutoScriptConfig;
import com.autoscript.ui.config.AutoScriptConfigProxy;
import com.autoscript.ui.core.UIConstants;
import com.autoscript.ui.core.processor.parse.file.FileParse;
import com.autoscript.ui.core.processor.parse.file.IFileParse;
import com.autoscript.ui.helper.ClassHelper;
import com.autoscript.ui.helper.FileCtrlUtils;
import com.autoscript.ui.helper.StringHelper;
import com.autoscript.ui.helper.TemplateHelper;
import com.autoscript.ui.helper.TemplateHelperException;
import com.autoscript.ui.helper.UIPropertyHelper;
import com.autoscript.ui.helper.XMLHelper;
import com.autoscript.ui.model.extconfig.IExtConfigModel;
import com.autoscript.ui.model.extconfig.TemplateFunctionConfigModel;
import com.autoscript.ui.model.extconfig.function.IFunctionConfigModel;
import com.autoscript.ui.model.projecttree.TemplateModel;
import com.autoscript.ui.model.projecttree.TreeRootModel;
import com.autoscript.ui.model.xml.IXmlNode;

/**
 * 工程加工器实现 作者:龙色波 日期:2013-10-13
 */
public class ProjectProcessor implements IProjectProcessor {
	private JTextArea outputTextArea;

	public ProjectProcessor(JTextArea outputTextArea) {
		this.outputTextArea = outputTextArea;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.autoscript.ui.core.processor.project.IProjectProcessor#build(com.
	 * autoscript.ui.model.projecttree.TreeRootModel)
	 */
	@Override
	public void build(TreeRootModel projectModel) throws Exception {
		try {
			// 获取工作目录
			String workDir;
			workDir = getWorkDir();
			// 创建目录，如果不存在
			if (!FileCtrlUtils.isExists(workDir)) {
				FileCtrlUtils.createDir(workDir);
			}
			// 把模型的xml和模板数据以文件格式存到工作目录/模板目录
			if (outputTextArea != null) {
				outputTextArea
						.setText("--------------运行工程----------------\r\n");
			}
			addOutputMessage("把模型的xml和模板数据以文件格式存到工作目录/模板目录...");
			List<String> templateFiles = saveTemplateToFile(workDir,
					projectModel);
			// 产生中间文件
			addOutputMessage("产生中间文件...");
			List<String> interMediateFiles = buildInterMediateFile(workDir,
					templateFiles, projectModel);
			// 循环解析，并运行工作目录/中间结果目录下的文件，才是真正生成最终文件
			IFileParse fileParse = new FileParse(outputTextArea);
			addOutputMessage("产生结果文件...");
			for (int i = 0,j=0; i < projectModel.getTemplateModels().size(); i++) {
				TemplateModel templateModel = projectModel
						.getTemplateModels().get(i);
				if(templateModel.isEnable()){				  
				  fileParse.parse(interMediateFiles.get(j), templateModel);
				  j= j+1;
				}
			}
		} catch (Exception e) {
			addOutputMessage(StringHelper.exceptionToString(e));
			throw e;
		}
	}

	/**
	 * 使用freemark产生中间文件
	 * 
	 * @param workDir
	 * @param templateFiles
	 * @param projectModel
	 * @return
	 * @throws TemplateHelperException
	 */
	private List<String> buildInterMediateFile(String workDir,
			List<String> templateFiles, TreeRootModel projectModel)
			throws TemplateHelperException {
		List<String> retFiles = new ArrayList<String>();
		// 将xml转换为模型
		IXmlNode rootNode;
		try {
			rootNode = XMLHelper.xml2Model(projectModel.getXmlDataModel()
					.getXml());
		} catch (DocumentException e1) {
			throw new TemplateHelperException(e1);
		}
		String intermediatePath;
		intermediatePath = workDir + UIConstants.INTERMEDIATE_PATH
				+ File.separator;
		if (!FileCtrlUtils.isExists(intermediatePath)) {
			FileCtrlUtils.createDir(intermediatePath);
		}
		Map<String, Object> templateMap = null;
		// 循环产生中间文件
		TemplateModel templateModel;
		AutoScriptConfig config = null;
		try {
			config = AutoScriptConfigProxy.getInstance().read();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new TemplateHelperException(e.getMessage());
		}

		try {
			templateMap = fillPreDefineFun(config.getExtConfigModels());
		} catch (Exception e) {
			e.printStackTrace();
			throw new TemplateHelperException(e.getMessage());
		}
		templateMap.put(UIConstants.ROOT_NODE, rootNode);
		for (int i = 0,j=0; i < projectModel.getTemplateModels().size(); i++) {
			String intermediateFile;
			templateModel = projectModel.getTemplateModels().get(i);
			if(templateModel.isEnable()){
				intermediateFile = intermediatePath
						+ templateModel.getTemplateName()
						+ UIConstants.INTERMEDIATE_SUFFIX;
				TemplateHelper.createFileByPath(templateMap, templateFiles.get(j++),
						intermediateFile);
				addOutputMessage("生成中间文件：" + intermediateFile);
				retFiles.add(intermediateFile);
			}
		}
		return retFiles;
	}

	/**
	 * 构造预定义函数map
	 * 
	 * @param extConfigModels
	 * @return
	 * @throws Exception
	 */
	private Map<String, Object> fillPreDefineFun(
			List<IExtConfigModel> extConfigModels) throws Exception {
		TemplateFunctionConfigModel funcModel = null;
		for (IExtConfigModel extModel : extConfigModels) {
			if (extModel instanceof TemplateFunctionConfigModel) {
				funcModel = (TemplateFunctionConfigModel) extModel;
				break;
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		if (funcModel != null) {
			for (IFunctionConfigModel model : funcModel.getFunctionItems()) {
				map.put(model.getName(), ClassHelper.instanceClass(model
						.getFunctionClass()));
			}
		}
		return map;
	}

	/**
	 * 保存模板文件到模板目录
	 * 
	 * @param workDir
	 * @param projectModel
	 * @return List<String> 其他为ftl
	 * @throws Exception
	 */
	private List<String> saveTemplateToFile(String workDir,
			TreeRootModel projectModel) throws Exception {
		List<String> retFiles = new ArrayList<String>();
		String templatePath = workDir + UIConstants.TEMPLDATE_PATH
				+ File.separator;
		// 自动创建目录
		if (!FileCtrlUtils.isExists(templatePath)) {
			FileCtrlUtils.createDir(templatePath);
		}
		// 循环保存模板文件
		String templateFile;
		for (TemplateModel templateModel : projectModel.getTemplateModels()) {
			if(templateModel.isEnable()){
				templateFile = templatePath + templateModel.getTemplateName()
						+ UIConstants.TEMPLATE_SUFFIX;
				FileCtrlUtils.writeStringToFile(new File(templateFile),
						templateModel.getContentModel().getContent(), templateModel
								.getCharsetModel().getCharset());
				addOutputMessage("保存模板文件：" + templateFile);
				retFiles.add(templateFile);
			}
		}
		return retFiles;
	}

	/**
	 * 获取工作目录
	 * 
	 * @return
	 */
	private String getWorkDir() throws Exception {
		AutoScriptConfig config;
		config = AutoScriptConfigProxy.getInstance().read();
		if (StringHelper.isEmpty(config.getWorkdir())) {
			throw new Exception(UIPropertyHelper
					.getString("exception.needconfigworkdir"));
		}
		String retval;
		retval = config.getWorkdir().trim();
		if (retval.endsWith("\\") || retval.endsWith("/")) {
			return config.getWorkdir();
		} else {
			return retval + File.separator;
		}
	}

	public void addOutputMessage(String msg) {
		if (outputTextArea != null) {
			if (msg.endsWith("\r\n")) {
				outputTextArea.setText(outputTextArea.getText() + msg);
			} else {
				outputTextArea.setText(outputTextArea.getText() + msg + "\r\n");
			}
		}
	}
}
