/**
 * 
 */
package com.autoscript.ui.core.component;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import org.apache.log4j.Logger;

import com.autoscript.ui.config.AutoScriptConfig;
import com.autoscript.ui.config.AutoScriptConfigProxy;
import com.autoscript.ui.helper.UIPropertyHelper;
import com.autoscript.ui.listener.ExtConfigModelListListener;
import com.autoscript.ui.logger.UILogger;
import com.autoscript.ui.model.extconfig.IExtConfigModel;
import com.autoscript.ui.model.extconfig.KBConfigModel;
import com.autoscript.ui.model.extconfig.TemplateFunctionConfigModel;
import com.autoscript.ui.model.extconfig.XmlDataPopupMenuConfigModel;

/**
 * 配置对话框
 * 作者:龙色波
 * 日期:2013-10-13
 */
public class ConfigDialog extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JPanel panel1 = new JPanel();
	
	JLabel jLabel1 = new JLabel();

	private JTextField workDirTextField;

	private AutoScriptConfig autoScriptConfig;
	protected static Logger logger = UILogger.getLogger("ConfigDialog");
	
	JButton jButtonOK = new JButton();
	 
	JButton jButtonClose = new JButton();

	private JTabbedPane jtp;
	/**
	 * 扩展功能面板
	 */
	private JPanel panel2 = new JPanel();
	/**
	 * 扩展功能右边面板
	 */
	private JPanel extRightPanel = new JPanel();
	/**
	 * 记录右边扩展功能界面Map
	 */
	private Map<Integer,Component> extRightCompMap = new HashMap<Integer,Component>();

	private JList extConfigModelList;

	private JScrollPane leftPanel;

	private ExtConfigModelListListener listListener; 
	public ConfigDialog(Frame frame, String title, boolean modal) {
	  super(frame, title, modal);
	  try {
	    makeMainPanel();
	    pack();
	    setSize(874, 353);
//	    Dimension parentsize = frame.getPreferredSize();
////		this.paramPanel.setPreferredSize(new Dimension(parentsize.width,
////				parentsize.height - 5));
	    setLocation(310, 200);
//	    int x,y;
//	    x = (parentsize.width- this.getSize().width)/2;
//	    y = (parentsize.height - this.getSize().height)/2;
//	    setLocation(x,y);
	  } catch (Exception ex) {
	    ex.printStackTrace();
	    logger.error(ex.getMessage(), ex);
	    DialogMessage.showMessageDialog(frame, ex.getMessage());
	  }
	}
	
	public ConfigDialog() {
	  this(null, "", false);
	}
	private void makeMainPanel() throws Exception{
		jtp = new JTabbedPane( );
	    jtp.addTab(UIPropertyHelper.getString("dialog.config.dir"), makePathConfPanel());
	    jtp.addTab(UIPropertyHelper.getString("dialog.config.ext"), makeExtConfPanel());
	    getContentPane().add(this.jtp,"Center");
	    getContentPane().add(createButtonPanel(),"South");
	}
	/**
	 * 创建路径配置面板
	 * @return
	 * @throws Exception
	 */
	private JPanel makePathConfPanel() throws Exception {
	  try{
		  this.autoScriptConfig = AutoScriptConfigProxy.getInstance().read();
	  }catch(Exception e){
		  throw e;
	  }
	  this.panel1.setLayout(null);
	  this.jLabel1.setRequestFocusEnabled(true);
	  this.jLabel1.setText(UIPropertyHelper.getString("dialog.config.workdir"));
	  this.jLabel1.setVerticalAlignment(0);
	  this.jLabel1.setBounds(new Rectangle(18, 63, 100, 16));
	  workDirTextField = new JTextField("", 20);
	  if(autoScriptConfig!=null){
		  workDirTextField.setText(autoScriptConfig.getWorkdir());
	  }
	  workDirTextField.addFocusListener(new FocusListener(){

		@Override
		public void focusGained(FocusEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void focusLost(FocusEvent e) {
			try {
				AutoScriptConfigProxy.getInstance().read().setWorkdir(workDirTextField.getText());
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		}
		  
	  });
	  workDirTextField.setBounds(new Rectangle(84, 60, 213, 22));
	  
	  this.panel1.add(this.jLabel1, null);
	  this.panel1.add(workDirTextField, null);
	   return panel1;
	}
	/**
	 * 创建扩展配置面板
	 * @return
	 * @throws Exception
	 */
	private JPanel makeExtConfPanel() throws Exception {
		this.panel2.setLayout(new BorderLayout());
//		this.panel2.setBorder(new EmptyBorder(0, 1, 2, 1));
		try{
			  this.autoScriptConfig = AutoScriptConfigProxy.getInstance().read();
		  }catch(Exception e){
			  throw e;
		  }
		//创建扩展功能列表
		autoScriptConfig.initExtConfigModel();
		extConfigModelList = new JList(autoScriptConfig.getExtConfigModels().toArray());
        extConfigModelList.setVisibleRowCount(4);
        
        listListener = new ExtConfigModelListListener(this);
		extConfigModelList.addListSelectionListener(listListener);
		leftPanel = new JScrollPane(extConfigModelList);
		//设置单选模式
		extConfigModelList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		panel2.add(leftPanel, "West");
		extRightPanel.setLayout(new BorderLayout());
		panel2.add(extRightPanel,"Center");
		
		
		return panel2;
	}
	/**
	 * 创建按钮面板
	 * @return
	 */
	private JPanel createButtonPanel() {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1, 5));
//		buttonPanel.setBorder(new EmptyBorder(0, 1, 2, 1));
//	    this.jButtonOK.setBounds(new Rectangle(84, 127, 73, 25));
		this.jButtonOK.setActionCommand("jButton1");
//		this.jButtonClose.setBounds(new Rectangle(191, 128, 73, 25));
		this.jButtonOK.setText(UIPropertyHelper.getString("dialog.config.okbutton"));
		this.jButtonClose.setText(UIPropertyHelper.getString("dialog.config.closebutton"));
		buttonPanel.add(new JLabel("   "));
		buttonPanel.add(jButtonOK);
		buttonPanel.add(new JLabel("   "));
		buttonPanel.add(jButtonClose);
		buttonPanel.add(new JLabel("   "));
		this.jButtonOK.addActionListener(new ActionListener()
		     {
		       public void actionPerformed(ActionEvent e) {
		         try {
		        	syncConfigModel(listListener.getSelectIndex());
					AutoScriptConfigProxy.getInstance().save();
					AutoScriptConfigProxy.getInstance().resetConfig();
					AutoScriptConfigProxy.getInstance().read();
				} catch (Exception e1) {
					e1.printStackTrace();
					logger.error(e1.getMessage(), e1);
					DialogMessage.show(ConfigDialog.this,e1.getMessage(),DialogMessage.ERROR_MESSAGE);
				    return;
				}
		         ConfigDialog.this.dispose();
		       }
		     });
		     this.jButtonClose.addActionListener(new ActionListener()
		     {
		       public void actionPerformed(ActionEvent e) {
		    	   ConfigDialog.this.dispose();
		       }
		     });
		return buttonPanel;
	}

	/**
	 * 刷新右边扩展功能界面
	 * @param oldIndex
	 * @param newIndex
	 */
	public void refreshExtConfigDisp(int oldIndex, int newIndex) {
		//移去界面
		Component removeComp = extRightCompMap.get(oldIndex);
		if(removeComp!=null){
			//新界面下标必须有效,才删除老界面
			if(newIndex!=-1){
				//同步模型
				try {
					syncConfigModel(oldIndex);
				} catch (Exception e) {
					e.printStackTrace();
				    logger.error(e.getMessage(), e);
				    DialogMessage.showMessageDialog(this, e.getMessage());
				    return;
				}
				extRightPanel.remove(removeComp);
			}
		}
		//加入新界面
		if(newIndex!=-1){
			Component addComp = extRightCompMap.get(newIndex);
			if(addComp==null){
				String compName;
				IExtConfigModel extConfigModel = autoScriptConfig.getExtConfigModels().get(newIndex);
				compName = extConfigModel.toString();
				if(compName.equals(UIPropertyHelper.getString("dialog.config.xmldatapopupmenuext"))){
					addComp = new XmlDataPopupMenuExtPanel();
				}else if(compName.equals(UIPropertyHelper.getString("dialog.config.templatefunext"))){
					addComp = new TemplateFunctionExtPanel();
				}else if(compName.equals(UIPropertyHelper.getString("dialog.config.kbext"))){
					addComp = new KBExtPanel();

				}
				if(addComp!=null){
					extRightCompMap.put(newIndex, addComp);
				}
			}
			if(addComp!=null){
				Dimension parentsize = extRightPanel.getParent().getParent().getParent().getPreferredSize();
				Dimension leftPanelSize= leftPanel.getPreferredSize();
				Dimension preferredSize = new Dimension();
				preferredSize.width = parentsize.width-leftPanelSize.width;
				preferredSize.height = parentsize.height;
				extRightPanel.setPreferredSize(preferredSize);
				extRightPanel.add(addComp,"Center");
				displayChildComp(addComp,newIndex);
			}
		}
	}
	/**
	 * 显示子窗口
	 * @param addComp
	 * @param index 
	 */
	private void displayChildComp(Component addComp, int index) {
		String compName;
		IExtConfigModel extConfigModel = autoScriptConfig.getExtConfigModels().get(index);
		compName = extConfigModel.toString();
		if(compName.equals(UIPropertyHelper.getString("dialog.config.xmldatapopupmenuext"))){
			((XmlDataPopupMenuExtPanel)addComp).initLayout();
			((XmlDataPopupMenuExtPanel)addComp).fillParam((XmlDataPopupMenuConfigModel) autoScriptConfig.getExtConfigModels().get(index));
		}else if(compName.equals(UIPropertyHelper.getString("dialog.config.templatefunext"))){
			((TemplateFunctionExtPanel)addComp).initLayout();
			((TemplateFunctionExtPanel)addComp).fillParam((TemplateFunctionConfigModel) autoScriptConfig.getExtConfigModels().get(index));
		}else if(compName.equals(UIPropertyHelper.getString("dialog.config.kbext"))){
			((KBExtPanel)addComp).initLayout();
			((KBExtPanel)addComp).fillParam((KBConfigModel) autoScriptConfig.getExtConfigModels().get(index));			
		}
		
	}

	/**
	 * 同步界面数据到模型
	 * @param index  扩展模型列表索引
	 * @throws Exception 
	 */
	private void syncConfigModel(int index) throws Exception {
		if(index!=-1){
			Component addComp = extRightCompMap.get(index);
			String compName;
			IExtConfigModel extConfigModel = autoScriptConfig.getExtConfigModels().get(index);
			compName = extConfigModel.toString();
			if(compName.equals(UIPropertyHelper.getString("dialog.config.xmldatapopupmenuext"))){
				XmlDataPopupMenuExtPanel panel = (XmlDataPopupMenuExtPanel) addComp;
				XmlDataPopupMenuConfigModel model =(XmlDataPopupMenuConfigModel) extConfigModel;
				model.setMenuItems(panel.getXmlDataPopupMenuItemConfigModels());
				//验证输入有效性
				model.verify();
			}else if(compName.equals(UIPropertyHelper.getString("dialog.config.templatefunext"))){
				TemplateFunctionExtPanel panel = (TemplateFunctionExtPanel) addComp;
				TemplateFunctionConfigModel model =(TemplateFunctionConfigModel) extConfigModel;
				model.setFunctionItems(panel.getFuncConfigModels());
				//验证输入有效性
				model.verify();
			}else if(compName.equals(UIPropertyHelper.getString("dialog.config.kbext"))){
				KBExtPanel panel = (KBExtPanel) addComp;
				KBConfigModel model =(KBConfigModel) extConfigModel;
				model.setKbItems(panel.getKBItemModels());
				//验证输入有效性
				model.verify();				
			}
		}
	}

	public JList getExtConfigModelList() {
		return extConfigModelList;
	}
	
}
