package com.autoscript.ui.helper;

import javax.swing.ImageIcon;

import com.autoscript.ui.core.UIConstants;
import com.autoscript.ui.logger.UILogger;

public final class ImageHelper {
	private static UILogger logger = (UILogger) UILogger
			.getLogger(ImageHelper.class);

	public final ImageIcon UI_SPLASH = getResource("splash.png");



	public final ImageIcon BATCHMOINTOR = getResource("batchmonitor_32.png");

	public final ImageIcon STARTBATCH = getResource("startbatch_16.png");

	public final ImageIcon STOPBATCH = getResource("stopbatch_16.png");

	public final ImageIcon PAUSEBATCH = getResource("pausebatch_16.png");

	public final ImageIcon RESUMEBATCH = getResource("resumebatch_16.png");

	public final ImageIcon DESTROYBATCH = getResource("destroybatch_16.png");

	public final ImageIcon EDITNODES = getResource("nodeinfo_32.png");

	public final ImageIcon IMPORTNODESFILE = getResource("import_wiz.gif");

	public final ImageIcon EXPORTNODESFILE = getResource("export_wiz.gif");

	public final ImageIcon ADD_ICON = getResource("add.gif");

	public final ImageIcon SAVEALL_ICON = getResource("saveall_edit.gif");

	public final ImageIcon EDIT_ICON = getResource("edit_enable.png");

	public final ImageIcon UNDOEDIT_ICON = getResource("edit_disable.png");

	public final ImageIcon DELETE_ICON = getResource("delete.gif");

	public final ImageIcon SKIPNODE_ICON = getResource("skipnode_16.png");

	public final ImageIcon RESTARTNODE_ICON = getResource("restartnode_16.png");

	public final ImageIcon FIND_ICON = getResource("find_16.png");

	public final ImageIcon FINDNEXT_ICON = getResource("findnext_16.png");

	public final ImageIcon FINDPREV_ICON = getResource("findprev_16.png");

	public final ImageIcon TASK_ICON = getResource("task_16.png");

	public final ImageIcon GC_ICON = getResource("gc.png");

	public final ImageIcon APP_ICON = getResource("autoscripticon.png");
	/**
	 * 新建工程
	 */
	public final ImageIcon NEWPROJECT_ICON = getResource("newproject.png");
	/**
	 * 打开工程
	 */
	public final ImageIcon OPENPROJECT_ICON = getResource("openproject.png");
	/**
	 * 关闭工程
	 */
	public final ImageIcon CLOSEPROJECT_ICON = getResource("closeproject.png");
	/**
	 * 运行工程
	 */
	public final ImageIcon RUNPROJECT_ICON = getResource("runproject.png");
	/**
	 * 保存工程
	 */
	public final ImageIcon SAVEPROJECT_ICON = getResource("saveproject.png");

	/**
	 * 查询
	 */
	public final ImageIcon SEARCH_ICON = getResource("find.png");
	/**
	 * 配置
	 */
	public final ImageIcon CONFIG_ICON = getResource("config.png");
	/**
	 * 退出
	 */
	public final ImageIcon EXIT_ICON = getResource("exit.png");
	/**
	 * AutoScript
	 */
	public final ImageIcon AUTOSCRIPT_ICON = getResource("autoscript.png");
	//树图标
	/**
	 * 收缩目录启用图标:用于根节点，模板节点
	 */
	public final ImageIcon FOLDER_ENABLE_ICON = getResource("folder.jpg");
	/**
	 * 收缩目录禁用图标
	 */
	public final ImageIcon FOLDER_DISABLE_ICON = getResource("folder_gray.jpg");
	/**
	 * 展开目录启用图标:用于根节点，模板节点
	 */
	public final ImageIcon OPEN_FOLDER_ENABLE_ICON = getResource("openfolder.jpg");
	/**
	 * 展开目录禁用图标
	 */
	public final ImageIcon OPEN_FOLDER_DISABLE_ICON = getResource("openfolder_gray.jpg");
	/**
	 * 文件目录图标
	 */
	public final ImageIcon FILE_ICON = getResource("file.jpg");
	
	public static ImageIcon createImageIcon(String filename) {
		String path = UIConstants.RESOURCE_PATH + "/" + filename;
		try {
			System.out.println(path);

			return new ImageIcon(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private ImageIcon getResource(String filename) {
		String path = UIConstants.RESOURCE_PATH + "/" + filename;
		try {
			return new ImageIcon(path);
		} catch (Exception e) {
			logger.error("load image:" + path + " failed!");
		}
		return null;
	}
	/**
	 * 从绝对路径装入图标对象
	 * @param fullFileName
	 * @return
	 */
	public static ImageIcon loadImageIcon(String fullFileName) {
		try {
			return new ImageIcon(fullFileName);
		} catch (Exception e) {
			logger.error("load image:" + fullFileName + " failed!");
		}
		return null;

	}
}

/*
 * Location: H:\笔记本备份\d盘\FmisBatchUI\lib\plantix-batch-ui.jar Qualified Name:
 * com.autoscript.ui.helper.ImageHelper JD-Core Version: 0.6.0
 */