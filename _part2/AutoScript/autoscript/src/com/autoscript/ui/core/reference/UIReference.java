package com.autoscript.ui.core.reference;

import java.awt.event.KeyEvent;

import javax.swing.Action;

import com.autoscript.ui.core.BatchUI;
import com.autoscript.ui.core.action.AboutDialogAction;
import com.autoscript.ui.core.action.AbstractUIAction;
import com.autoscript.ui.core.action.CloseProjectAction;
import com.autoscript.ui.core.action.ConfigDialogAction;
import com.autoscript.ui.core.action.CreateProjectAction;
import com.autoscript.ui.core.action.OpenProjectAction;
import com.autoscript.ui.core.action.RunProjectAction;
import com.autoscript.ui.core.action.SaveProjectAction;
import com.autoscript.ui.core.action.SearchAction;
import com.autoscript.ui.core.action.SystemExitAction;
import com.autoscript.ui.core.registry.MenuRegistry;
import com.autoscript.ui.core.registry.NavigationRegistry;
import com.autoscript.ui.core.registry.ToolBarRegistry;
import com.autoscript.ui.helper.ImageHelper;
import com.autoscript.ui.helper.UIPropertyHelper;

public class UIReference
  implements Reference
{
  private MenuRegistry menuRegistry;
  private NavigationRegistry navigationRegistry;
  private ToolBarRegistry barRegistry;
  private BatchUI ui;
  private static final ImageHelper imager = new ImageHelper();
  public UIReference(BatchUI ui)
  {
    this.ui = ui;
    this.menuRegistry = MenuRegistry.getInstance();
    this.navigationRegistry = NavigationRegistry.getInstance();
    this.barRegistry = ToolBarRegistry.getInstance();
  } 

  public void init() {
    menuRegistry();
    barRegistry();
  }

  public void destroy() {
    this.menuRegistry.clear();
    this.barRegistry.clear();
    this.navigationRegistry.clear();
  }

  private void barRegistry()
  {
	  Action action = new CreateProjectAction(ui);
	  int ctrlKey = KeyEvent.CTRL_MASK;
	  //新增工程
	  barRegistry.registry("NewProject", imager.NEWPROJECT_ICON, UIPropertyHelper.getString("menu.project.create"), action,
			  UIPropertyHelper.getMnemonic("menu.project.create.mnemonic"),ctrlKey);
	  //打开工程
	  barRegistry.registry("OpenProject", imager.OPENPROJECT_ICON, UIPropertyHelper.getString("menu.project.open"), new OpenProjectAction(ui),
			  UIPropertyHelper.getMnemonic("menu.project.open.mnemonic"),ctrlKey);
	  //保存工程
	  barRegistry.registry("SaveProject", imager.SAVEPROJECT_ICON, UIPropertyHelper.getString("menu.project.save"), new SaveProjectAction(ui),
			  UIPropertyHelper.getMnemonic("menu.project.save.mnemonic"),ctrlKey);
	  //运行工程
	  barRegistry.registry("RunProject", imager.RUNPROJECT_ICON, UIPropertyHelper.getString("menu.project.run"), new RunProjectAction(ui),
			  UIPropertyHelper.getMnemonic("menu.project.run.mnemonic"),ctrlKey);
	  //关闭工程
	  barRegistry.registry("CloseProject", imager.CLOSEPROJECT_ICON, UIPropertyHelper.getString("menu.project.close"), new CloseProjectAction(ui),
			  UIPropertyHelper.getMnemonic("menu.project.close.mnemonic"),ctrlKey);
	  //查询模板内容等
	  barRegistry.registry("Search", imager.SEARCH_ICON, UIPropertyHelper.getString("toolbar.textarea.search"), new SearchAction(ui),
			  UIPropertyHelper.getMnemonic("toolbar.textarea.search.mnemonic"),ctrlKey);
	  //配置
	  barRegistry.registry("Config", imager.CONFIG_ICON, UIPropertyHelper.getString("menu.tool.config"), new ConfigDialogAction(ui),
			  UIPropertyHelper.getMnemonic("menu.tool.config.mnemonic"),ctrlKey);
	  //关于
	  barRegistry.registry("About", imager.AUTOSCRIPT_ICON, UIPropertyHelper.getString("menu.about"), new AboutDialogAction(ui),
			  UIPropertyHelper.getMnemonic("menu.about.mnemonic"),ctrlKey);
	  //退出
	  barRegistry.registry("Exit", imager.EXIT_ICON, UIPropertyHelper.getString("menu.project.exit"), new SystemExitAction(ui),
			  UIPropertyHelper.getMnemonic("menu.project.exit.mnemonic"),ctrlKey);	  
  }

  private void menuRegistry() {
	
    this.menuRegistry.registry("projectMenu", getString("menu.project"));
    AbstractUIAction action = new CreateProjectAction(getString("menu.project.create"),null, getMnemonic("menu.project.create.mnemonic"), getString("menu.project.create.description"));
    action.setUi(ui);
	this.menuRegistry.registryItem("projectMenu", getString("menu.project.create"), getMnemonic("menu.project.create.mnemonic"), getString("menu.project.create.description"), false, action);
	action = new OpenProjectAction(getString("menu.project.open"),null, getMnemonic("menu.project.open.mnemonic"), getString("menu.project.open.description"));
    action.setUi(ui);
    this.menuRegistry.registryItem("projectMenu", getString("menu.project.open"), getMnemonic("menu.project.open.mnemonic"), getString("menu.project.open.description"), false, action);
    action = new SaveProjectAction(getString("menu.project.save"),null, getMnemonic("menu.project.save.mnemonic"), getString("menu.project.save.description"));
    action.setUi(ui);
    this.menuRegistry.registryItem("projectMenu", getString("menu.project.save"), getMnemonic("menu.project.save.mnemonic"), getString("menu.project.save.description"), false, action);
    action = new RunProjectAction(getString("menu.project.run"),null, getMnemonic("menu.project.run.mnemonic"), getString("menu.project.run.description"));
    action.setUi(ui);
    this.menuRegistry.registryItem("projectMenu", getString("menu.project.run"), getMnemonic("menu.project.run.mnemonic"), getString("menu.project.run.description"), false, action);
    action = new CloseProjectAction(getString("menu.project.close"),null, getMnemonic("menu.project.close.mnemonic"), getString("menu.project.close.description"));
    action.setUi(ui);
    this.menuRegistry.registryItem("projectMenu", getString("menu.project.close"), getMnemonic("menu.project.close.mnemonic"), getString("menu.project.close.description"), false, action);
    this.menuRegistry.registryItem("projectMenu", getString("menu.project.exit"), getMnemonic("menu.project.mnemonic"), getString("menu.project.description"), true, new SystemExitAction(this.ui));

     this.menuRegistry.registry("toolMenu", getString("menu.tool"));
     this.menuRegistry.registryItem("toolMenu", getString("menu.tool.config"), getMnemonic("menu.tool.config.mnemonic"), getString("menu.tool.config.description"), false, new ConfigDialogAction(this.ui));
/// 
///     this.menuRegistry.registry("online", getString("menu.online"));
/// 
///     this.menuRegistry.registryItem("online", getString("menu.changeactstate"), getMnemonic("menu.changeactstate.mnemonic"), getString("menu.changeactstate.description"), false, new BalanceSwitchAction(this.ui));
/// 
///     this.menuRegistry.registryItem("online", getString("menu.dbbackupbefore"), getMnemonic("menu.dbbackupbefore.mnemonic"), getString("menu.dbbackupbefore.description"), false, new DatabaseBackupAction(this.ui));
/// 
///     this.menuRegistry.registryItem("online", getString("menu.dbbackupafter"), getMnemonic("menu.dbbackupafter.mnemonic"), getString("menu.dbbackupafter.description"), false, new DatabaseBackupAction(this.ui));
/// 
///     this.menuRegistry.registryItem("online", getString("menu.openfmis"), getMnemonic("menu.openfmis.mnemonic"), getString("menu.openfmis.description"), false, new OpenFmisAction(this.ui));
/// 
///     this.menuRegistry.registryItem("online", getString("menu.tempswitch"), getMnemonic("menu.tempswitch.mnemonic"), getString("menu.tempswitch.description"), false, new TempSwitchAction(this.ui));
/// 
///     this.menuRegistry.registryItem("online", getString("menu.viewtradeinfo"), getMnemonic("menu.viewtradeinfo.mnemonic"), getString("menu.viewtradeinfo.description"), false, new ViewTradeInfoAction(this.ui));*/
/// 
    this.menuRegistry.registry("help", getString("menu.help"));
    this.menuRegistry.registryItem("help", getString("menu.about"), getMnemonic("menu.about.mnemonic"), getString("menu.about.description"), false, new AboutDialogAction(this.ui));
  }

 

  private ImageHelper getImageHelper() {
    return this.ui.getImageHelper();
  }

  private char getMnemonic(String key) {
    return getString(key).charAt(0);
  }

  private String getString(String key) {
    return UIPropertyHelper.getString(key);
  }
}

