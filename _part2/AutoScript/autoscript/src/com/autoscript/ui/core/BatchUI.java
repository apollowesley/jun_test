package com.autoscript.ui.core;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.JWindow;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import com.autoscript.extinterface.xmldata.IMakeXmlData;
import com.autoscript.ui.config.AutoScriptConfig;
import com.autoscript.ui.config.AutoScriptConfigProxy;
import com.autoscript.ui.core.action.AddTemplateAction;
import com.autoscript.ui.core.action.DelTemplateAction;
import com.autoscript.ui.core.action.DisableTemplateAction;
import com.autoscript.ui.core.action.EnableTemplateAction;
import com.autoscript.ui.core.action.XmlSourceExtAction;
import com.autoscript.ui.core.cache.UICache;
import com.autoscript.ui.core.component.JFilterComboBox;
import com.autoscript.ui.core.component.UIPopupMenu;
import com.autoscript.ui.core.filter.XmlFilenameFilter;
import com.autoscript.ui.core.reference.UIReference;
import com.autoscript.ui.core.registry.MenuRegistry;
import com.autoscript.ui.core.registry.NavigationRegistry;
import com.autoscript.ui.core.registry.ToolBarRegistry;
import com.autoscript.ui.core.render.ProjectTreeCellRenderer;
import com.autoscript.ui.helper.ImageHelper;
import com.autoscript.ui.helper.SerialHelper;
import com.autoscript.ui.helper.StringHelper;
import com.autoscript.ui.helper.UIPropertyHelper;
import com.autoscript.ui.listener.ProjectTreeMousePopupListener;
import com.autoscript.ui.listener.ProjectTreeSelectListener;
import com.autoscript.ui.logger.UILogger;
import com.autoscript.ui.model.extconfig.IExtConfigModel;
import com.autoscript.ui.model.extconfig.KBConfigModel;
import com.autoscript.ui.model.extconfig.XmlDataPopupMenuConfigModel;
import com.autoscript.ui.model.extconfig.kb.IKBItemModel;
import com.autoscript.ui.model.extconfig.menu.XmlDataPopupMenuItemConfigModel;
import com.autoscript.ui.model.projecttree.ITreeModel;
import com.autoscript.ui.model.projecttree.TemplateCharsetModel;
import com.autoscript.ui.model.projecttree.TemplateContentModel;
import com.autoscript.ui.model.projecttree.TemplateModel;
import com.autoscript.ui.model.projecttree.TreeRootModel;
import com.autoscript.ui.model.projecttree.XmlDataModel;

public class BatchUI extends JPanel {
	private static final long serialVersionUID = 1L;
	private static UILogger logger = (UILogger) UILogger
			.getLogger(BatchUI.class);
	private static final ImageHelper imager = new ImageHelper();
	private MenuRegistry menuRegistry;
	private ToolBarRegistry barRegistry;
	private JFrame frame;
	private JMenuBar menuBar;
	private UICache cache;
	private JTextField statusField;
	private JTextField userField;
	private JTextField jmsField;
	private JTextField sysGenCtlField;
	private JPanel mainPane;
	private JPanel navigationPane;
	private JPanel statePane;
	private JPanel resourcePane;
	private JProgressBar progressBar;
	private JWindow splashScreen;
	private OperationModule currentModule;
	private JToolBar toolbar;
	private DefaultTreeModel treeModel;
	private JTree tree;
	private DefaultMutableTreeNode selectProjectTreeNode;
	ImageHelper image = new ImageHelper();
	private AddTemplateAction addTemplateAction;
	private DelTemplateAction delTemplateAction;
	private TreeRootModel projectModel;
	private JTextArea ta;	
	/**
	 * 工程文件名
	 */
	private String projectFileName;
	/**
	 * 工程在运行标志
	 */
	private Boolean projectRunFlag = false;
	private JTextArea outputTextArea;
	private JSplitPane parentsp;
	private JTextField syntaxTextField;
	private JComboBox typeComboBox;
	private JComboBox keyFunComboBox;
	private KBConfigModel kbConfModel;
	private JTextField kbDescribeLabel;
	private EnableTemplateAction enableTemplateAction;
	private DisableTemplateAction disableTemplateAction;
	/**
	 * 工程文档修改标志
	 */
	private Boolean projectModify = false;
	public BatchUI() {		
		try {
			this.frame = createFrame(GraphicsEnvironment
					.getLocalGraphicsEnvironment().getDefaultScreenDevice()
					.getDefaultConfiguration());


			new UIReference(this).init();

			this.menuRegistry = MenuRegistry.getInstance();
			NavigationRegistry.getInstance();
			this.barRegistry = ToolBarRegistry.getInstance();

			createSplashScreen();

			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					BatchUI.this.showSplashScreen();
				}
			});
			try {
				Thread.sleep(1000L);
			} catch (InterruptedException localInterruptedException) {
				localInterruptedException.printStackTrace();
			}
			setPreferredSize(new Dimension(570, 500));
			setLayout(new BorderLayout());
            initAction();
			initLayout();
			//添加的ComponentListener决定了任意改变界面尺寸后JSplitPane都会按比例分割。 
			this.addComponentListener(new ComponentAdapter(){
		            public void componentResized(ComponentEvent e) {
		            	parentsp.setDividerLocation(0.8);
		            }
		        }); 
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					BatchUI.this.showBatchUI();
					BatchUI.this.hideSplash();					
				}
			});
		} catch (Exception e) {
			logger.fatal("UIBuilder Failed cause " + e.getMessage(), e);
		}
	}
	/**
	 * 初始化Action
	 */
	private void initAction() {
		addTemplateAction = new AddTemplateAction(getString("menu.newtemplate"), this.image.ADD_ICON, 
						 getString("menu.newtemplate.description"), 
						  getString("menu.newtemplate.mnemonic").charAt(0));
		
		addTemplateAction.setBatchUI(this);
		delTemplateAction = new DelTemplateAction(getString("menu.deltemplate"), this.image.DELETE_ICON, 
				 getString("menu.deltemplate.description"), 
				  getString("menu.deltemplate.mnemonic").charAt(0));
		delTemplateAction.setUi(this);
		//激活模板action
		enableTemplateAction = new EnableTemplateAction(getString("menu.enabletemplate"), this.image.EDIT_ICON, 
				 getString("menu.enabletemplate.description"), 
				  getString("menu.enabletemplate.mnemonic").charAt(0));
		enableTemplateAction.setUi(this);
		//禁用模板action
		disableTemplateAction = new DisableTemplateAction(getString("menu.disabletemplate"), this.image.UNDOEDIT_ICON, 
				 getString("menu.disabletemplate.description"), 
				  getString("menu.disabletemplate.mnemonic").charAt(0));
		disableTemplateAction.setUi(this);
	}

	private void initLayout() {
		setLayout(new BorderLayout());

		JPanel northPane = new JPanel(new BorderLayout());
		northPane.add(createMenus(), "North");
		northPane.add(createToolBar(), "Center");

		add(northPane, "North");
//		add(createNavigationPane(), "West");
//		add(createStatePane(), "South");
//		add(createMainPane(), "Center");
		createNavigationPane();
		createMainPane();
		this.navigationPane.setMinimumSize(new Dimension(150, 150));
	    this.mainPane.setMinimumSize(new Dimension(150, 150));
	    JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, navigationPane, mainPane);
		add(createStatePane(), "South");
		//创建输出窗口
		JScrollPane outputPane = createOutputPane();
		parentsp = new JSplitPane(JSplitPane.VERTICAL_SPLIT, sp,outputPane );
		add(parentsp, "Center");
		
	}
	/**
	 * 创建输出信息窗口
	 * @return
	 */
	private JScrollPane createOutputPane() {
		outputTextArea = new JTextArea();
		outputTextArea.setRows(3);
		outputTextArea.setEditable(false);
		JScrollPane pane = new JScrollPane(outputTextArea);
		return pane;
	}
	private static JFrame createFrame(GraphicsConfiguration gc) {
		JFrame frame = new JFrame(gc);
		frame.setDefaultCloseOperation(0);
		WindowListener l = new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if (JOptionPane.showConfirmDialog(null, UIPropertyHelper
						.getString("system.exit"), UIPropertyHelper
						.getString("system.info"), 0) == 0)
					System.exit(0);
			}
		};
		// WindowListener l = new WindowAdapter(frame) {
		// public void windowClosing(WindowEvent e) {
		// if (JOptionPane.showConfirmDialog(BatchUI.this,
		// UIPropertyHelper.getString("system.exit"),
		// UIPropertyHelper.getString("system.info"), 0) == 0)
		// System.exit(0);
		// }
		// };
		frame.addWindowListener(l);
		return frame;
	}

	private void createSplashScreen() {
		JLabel splashLabel = new JLabel(imager.UI_SPLASH);
		this.splashScreen = new JWindow(getFrame());
		this.splashScreen.getContentPane().add(splashLabel);
		this.splashScreen.pack();
		Rectangle screenRect = getFrame().getGraphicsConfiguration()
				.getBounds();
		this.splashScreen.setLocation(screenRect.x + screenRect.width / 2
				- this.splashScreen.getSize().width / 2, screenRect.y
				+ screenRect.height / 2 - this.splashScreen.getSize().height
				/ 2);
	}

	private JPanel createMainPane() {
		this.mainPane = new JPanel(new BorderLayout());
		this.mainPane.setBorder(new EmptyBorder(0, 1, 2, 1));

		// OperationModule welcome = new WelcomePanel(this);
		// setCurrentModule(welcome);

		// this.mainPane.add(welcome, "Center");
		ta = new JTextArea();
		ta.setEditable(false);
		ta.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				  if ((e.getKeyCode() == KeyEvent.VK_SLASH)   
                          && (e.isAltDown())) {
				  }
			}
		});
		ta.getDocument().addDocumentListener(new DocumentListener(){
			 
            //不清楚這個方法监听的什么事件
            @Override
            public void changedUpdate(DocumentEvent event) {
            	//setProjectModify(true);
            }
 
            @Override
            public void insertUpdate(DocumentEvent event) {
            	//setProjectModify(true);
            }
 
            @Override
            public void removeUpdate(DocumentEvent event) {
            	//setProjectModify(true);
            }
 
        });

		JScrollPane scrollpane = new JScrollPane(ta);
		scrollpane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollpane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.mainPane.add(scrollpane, "Center");
		this.mainPane.add(createKBPane(),"North");
		return this.mainPane;
	}
	/**
	 * 创建知识库提示面板
	 * @return
	 */
	private Component createKBPane() {
		JPanel KBPane = new JPanel();
		KBPane.setLayout(new GridLayout(1, 7, 0, 0));
		 
		JLabel lblNewLabel = new JLabel("类型:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		KBPane.add(lblNewLabel);
		kbConfModel = getKBConfigModel();
		typeComboBox = new JComboBox();
		typeComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String type = (String)typeComboBox.getSelectedItem();
				//填充关键字列表
				if(keyFunComboBox!=null){
					fillKeyFunComboBox(type,keyFunComboBox,kbConfModel);
					//清空关键字组合框编辑框内容
					keyFunComboBox.getEditor().setItem("");
				}
			}
		});
		typeComboBox.setMaximumRowCount(5);
		KBPane.add(typeComboBox);
		fillKBTypeComboBox(typeComboBox,kbConfModel);
		JLabel lblNewLabel_1 = new JLabel("关键字/函数：");
		KBPane.add(lblNewLabel_1);
		
		keyFunComboBox = new JFilterComboBox();
		keyFunComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//显示语法，描述
				IKBItemModel model = (IKBItemModel)keyFunComboBox.getSelectedItem();
				if(model!=null){
					IKBItemModel kbItem = kbConfModel.getKBItem(model.getType(),model.getKeyFun());
					if(kbItem!=null){
						syntaxTextField.setText(kbItem.getSyntax());
						kbDescribeLabel.setText(kbItem.getDescribe());
					}
				}
			}
		});
		keyFunComboBox.setEditable(true);
		keyFunComboBox.setMaximumRowCount(30);
		KBPane.add(keyFunComboBox);
		
		JLabel lblNewLabel_2 = new JLabel("语法:");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.RIGHT);
		KBPane.add(lblNewLabel_2);
		
		syntaxTextField = new JTextField();
		syntaxTextField.setEditable(false);
		KBPane.add(syntaxTextField);
		syntaxTextField.setColumns(30);
		
		kbDescribeLabel = new JTextField("            ");
		kbDescribeLabel.setEditable(false);
		kbDescribeLabel.setToolTipText("描述");
		KBPane.add(kbDescribeLabel);
		
		JButton btnNewButton = new JButton("插入");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!StringHelper.isEmpty(syntaxTextField.getText())){
					if(ta!=null){
					int pos = ta.getCaretPosition();
						if(ta.isEditable()){
							ta.insert(syntaxTextField.getText(), pos);
						}
					}

				}
			}
		});
		KBPane.add(btnNewButton);

		return KBPane;
	}
	protected void fillKeyFunComboBox(String type,JComboBox keyFunComboBox2,
			KBConfigModel kbConfModel2) {
		List<IKBItemModel> keyFuns;
		
		keyFuns = kbConfModel.getAllModelByType(type);
		keyFunComboBox2.setModel(new DefaultComboBoxModel(keyFuns.toArray(new IKBItemModel[keyFuns.size()])));
		if(keyFuns.size()>0){
			keyFunComboBox2.setSelectedIndex(0);
		}
	}
	/**
	 * 填充知识库类型组合框
	 * @param typeComboBox
	 * @param kbConfModel
	 */
	private void fillKBTypeComboBox(JComboBox typeComboBox,
			KBConfigModel kbConfModel) {
		List<String> allTypes;
		
		allTypes = kbConfModel.getAllTypes();
		allTypes.add(0, UIConstants.ALL_KEY);
		typeComboBox.setModel(new DefaultComboBoxModel(allTypes.toArray(new String[allTypes.size()])));
		if(allTypes.size()>0){
			typeComboBox.setSelectedIndex(0);
		}
	}
	/**
	 * 获取知识库配置模型
	 * @return
	 * @throws FileNotFoundException 
	 */
	private KBConfigModel getKBConfigModel()  {
		AutoScriptConfig config;
		try {
			config = AutoScriptConfigProxy.getInstance().read();
			List<IExtConfigModel> extConfModels = config.getExtConfigModels();
			for(IExtConfigModel model:extConfModels){
				if(model.toString().equals(UIPropertyHelper.getString("dialog.config.kbext"))){
					return (KBConfigModel) model;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return null;
		}
		return null;
	}
	private JPanel createStatePane() {
		this.statePane = new JPanel();
		this.statePane.setBorder(new EmptyBorder(0, 0, 0, 0));
		this.statusField = new JTextField(getString("system.status.label"));
		this.statusField.setEditable(false);

		this.jmsField = new JTextField("");
		this.jmsField.setEditable(false);
		this.jmsField.setToolTipText(getString("system.jmsconfig"));

		this.sysGenCtlField = new JTextField("");
		this.sysGenCtlField.setEditable(false);
		this.sysGenCtlField.setToolTipText(getString("system.sysgenctl"));
		showSysGenCtlInfo();
		this.userField = new JTextField();
		this.userField.setEditable(false);
		this.resourcePane = new JPanel(new FlowLayout(0));
		this.resourcePane
				.setBorder(new EtchedBorder(0, Color.gray, Color.white));
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		c.fill = 1;
		this.statePane.setLayout(gridbag);
		c.weightx = 3.0D;
		gridbag.setConstraints(this.statusField, c);
		c.weightx = 2.0D;
		gridbag.setConstraints(this.jmsField, c);
		c.weightx = 2.0D;
		gridbag.setConstraints(this.sysGenCtlField, c);
		c.weightx = 0.2D;
		gridbag.setConstraints(this.resourcePane, c);
		this.progressBar = new JProgressBar();
		c.weightx = 0.5D;
		gridbag.setConstraints(this.userField, c);

		int maxMemory = new Long(Runtime.getRuntime().maxMemory()).intValue();
		this.progressBar.setMinimum(0);
		this.progressBar.setMaximum(maxMemory);
		this.progressBar.setPreferredSize(new Dimension(100, 20));
		this.progressBar.setForeground(Color.WHITE);
		this.progressBar.setStringPainted(true);

//		showProgress();

		JToggleButton gc = new JToggleButton(imager.GC_ICON);
		gc.setToolTipText(getString("button.gc.description"));
		gc.setPreferredSize(new Dimension(17, 20));
		gc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// SwingUtilities.invokeLater(new BatchUI.5(this));
			}
		});

		this.resourcePane.add(this.progressBar);
		this.resourcePane.add(gc);

		this.statePane.add(this.statusField);
		this.statePane.add(this.jmsField);
		this.statePane.add(this.sysGenCtlField);
		this.statePane.add(this.resourcePane);
		this.statePane.add(this.userField);
		return this.statePane;
	}

	private JPanel createNavigationPane() {
		this.navigationPane = new JPanel(new GridLayout(1, 1));
		this.navigationPane.setBorder(new EmptyBorder(0, 0, 0, 2));
//		 navigationPane.setPreferredSize(new Dimension(200, this.getHeight()));
		return this.navigationPane;
	}

	private JMenuBar createMenus() {
		this.menuBar = new JMenuBar();

		Iterator menuResult = this.menuRegistry.getMenuList().iterator();
		while (menuResult.hasNext()) {
			this.menuBar.add((Component) menuResult.next());
		}
		return this.menuBar;
	}

	private JToolBar createToolBar() {
		this.toolbar = new JToolBar();

		Iterator result = this.barRegistry.getToolBarList().iterator();
		while (result.hasNext()) {
			this.toolbar.add((Component) result.next());
		}

		return this.toolbar;
	}

	public void destroy() {
		
	}

	private void showProgress() {
		Thread progress = new Thread(new Runnable() {
			public void run() {
				while (true) {
					int currMemory = new Long(Runtime.getRuntime()
							.totalMemory()).intValue();
					BatchUI.this.progressBar.setValue(currMemory);
					String txt = currMemory / 1024 / 1024 + "M of "
							+ BatchUI.this.progressBar.getMaximum() / 1024
							/ 1024 + "M";
					BatchUI.this.progressBar.setString(txt);
					BatchUI.this.progressBar.updateUI();
					try {
						Thread.sleep(10000L);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		progress.setDaemon(true);
		progress.start();
	}

	public void show() {
		setVisible(true);
	}

	private void showBatchUI() {
		if (getFrame() != null) {
			JFrame f = getFrame();
			f.setTitle(getString("frame.title"));
			f.setIconImage(imager.APP_ICON.getImage());
			f.getContentPane().add(this, "Center");
			f.pack();
			Rectangle screenRect = f.getGraphicsConfiguration().getBounds();
			Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(
					f.getGraphicsConfiguration());
			int centerWidth = screenRect.width >= f.getSize().width ? screenRect.x
					+ screenRect.width / 2 - f.getSize().width / 2
					: screenRect.x;
			int centerHeight = screenRect.height >= f.getSize().height ? screenRect.y
					+ screenRect.height / 2 - f.getSize().height / 2
					: screenRect.y;
			centerHeight = centerHeight >= screenInsets.top ? centerHeight
					: screenInsets.top;
			f.setLocation(centerWidth, centerHeight);
			f.setExtendedState(6);
			f.show();
		}
	}

	public void setStatusField(JTextField statusField) {
		this.statusField = statusField;
	}

	public void setCurrentModule(OperationModule module) {
		this.currentModule = module;
	}

	private void showSplashScreen() {
		this.splashScreen.show();
	}

	private void hideSplash() {
		this.splashScreen.setVisible(false);
		this.splashScreen = null;
	}

	private String getString(String key) {
		return UIPropertyHelper.getString(key);
	}

	public OperationModule getModule(Class moduleClass) {
		Object module = null;
		module = this.cache.get(moduleClass.getName());

		if (module != null)
			return (OperationModule) module;
		try {
			module = moduleClass.getDeclaredConstructor(
					new Class[] { BatchUI.class }).newInstance(
					new Object[] { this });

			this.cache.put(moduleClass.getName(), module);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return (OperationModule) module;
	}

	public JMenuBar getMenuBar() {
		return this.menuBar;
	}

	public ImageHelper getImageHelper() {
		return imager;
	}

	public JFrame getFrame() {
		return this.frame;
	}

	public UILogger getLogger() {
		return logger;
	}

	public UICache getCache() {
		return this.cache;
	}

	public JPanel getNavigationPane() {
		return this.navigationPane;
	}

	public JPanel getMainPane() {
		return this.mainPane;
	}

	public OperationModule getCurrentModule() {
		return this.currentModule;
	}

	public JTextField getStatusField() {
		return this.statusField;
	}

	public JToolBar getToolBar() {
		return this.toolbar;
	}

	

	public JTextField getJmsField() {
		return this.jmsField;
	}

	public JTextField getSysGenCtlField() {
		return this.sysGenCtlField;
	}

	// ERROR //
	public void showSysGenCtlInfo() {
	}

	public void setEnableMenuAndNavigation(boolean bEnable) {
		getMenuBar().setEnabled(bEnable);
		getNavigationPane().setEnabled(bEnable);
	}
	/**
	 * 创建工程树
	 */
	public void createProjectTree() {
		//先删除旧树
		if(tree!=null){
			navigationPane.remove(tree);
		}else{
			projectModel = new TreeRootModel();
			//初始化相关模型
			projectModel.setXmlDataModel(new XmlDataModel());
			projectModel.setTemplateModels(new ArrayList<TemplateModel>());
		}
		
		// DefaultTreeModel can use it to build a complete tree.
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(
				projectModel);
		//
		DefaultMutableTreeNode xmldata = new DefaultMutableTreeNode(
				projectModel.getXmlDataModel()
				);
		
		// Build our tree model starting at the root node, and then make a JTree
		// out
		// of it.
		treeModel = new DefaultTreeModel(root);
		tree = new JTree(treeModel);
		ProjectTreeCellRenderer projectRender;
		projectRender = new ProjectTreeCellRenderer(image); 
		tree.setCellRenderer(projectRender);
		// Build the tree up from the nodes we created.

		root.add(xmldata);
		
		tree.expandRow(tree.getRowCount() - 1);
		//增加树选择事件
		tree.addTreeSelectionListener(new ProjectTreeSelectListener(this));
		//增加树右键菜单事件
		tree.addMouseListener(new ProjectTreeMousePopupListener(tree, createProjectTreePopupMenu(tree)));
		navigationPane.add(tree, BorderLayout.CENTER);
		navigationPane.updateUI();
		setProjectModify(true);

	}
	/**
	 * 创建工程树右键菜单 
	 * @param tree2
	 * @return
	 */
	private UIPopupMenu createProjectTreePopupMenu(JTree tree2) {
		 UIPopupMenu menu = new UIPopupMenu();
		 menu.add(addTemplateAction);
		 menu.add(delTemplateAction);
		 menu.add(enableTemplateAction);
		 menu.add(disableTemplateAction);
		 //循环增加xml源数据右键扩展菜单
		 try {
			AutoScriptConfig groupconfig = AutoScriptConfigProxy.getInstance().read();
			List<IExtConfigModel> extModels = groupconfig.getExtConfigModels();
			if(extModels!=null && extModels.size()>0){
				for(IExtConfigModel extModel:extModels){
					if(extModel instanceof XmlDataPopupMenuConfigModel){
						addXmlDataExtMenus(menu,(XmlDataPopupMenuConfigModel)extModel);
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);			
		}
		 return menu;
	}
	/**
	 * 增加xml源数据扩展菜单
	 * @param menu
	 * @param extModel
	 */
	private void addXmlDataExtMenus(UIPopupMenu menu,
			XmlDataPopupMenuConfigModel extModel) {
		List<XmlDataPopupMenuItemConfigModel> menuItems = extModel.getMenuItems();
		Icon icon;
		for(XmlDataPopupMenuItemConfigModel item:menuItems){
			if(StringHelper.isEmpty(item.getIconFile())){
				icon = null;
			}else{
				icon = ImageHelper.loadImageIcon(item.getIconFile());
			}
			XmlSourceExtAction menuAction = new XmlSourceExtAction(item.getName(),icon);
			Class<?> implClass=null;
			IMakeXmlData xmlData = null;
			try{
				implClass = Class.forName(item.getImplClassName());
				xmlData = (IMakeXmlData) implClass.newInstance();
			}catch(Throwable e){
				logger.error(e.getMessage(), e);
				xmlData = null;
				continue;
			}			
			menuAction.setMakeXmlData(xmlData);
			menuAction.setUi(this);
			menu.add(menuAction);
		}
	}
	public DefaultMutableTreeNode createTemplateTreeNode(String templateName){
		if(projectModel!=null){
			//检查模板名称是否存在
			if(projectModel.isExistTemplate(templateName)){
				JOptionPane.showMessageDialog(this.getFrame(), UIPropertyHelper.getString("system.template_name_exist", templateName),
						UIPropertyHelper.getString("system.info"), JOptionPane.OK_OPTION);
				return null;
			}
			TemplateModel templateModel = new TemplateModel(templateName);
			
			DefaultMutableTreeNode subroot = new DefaultMutableTreeNode(templateModel);
			DefaultMutableTreeNode temp_content = new DefaultMutableTreeNode(templateModel.getContentModel());
			DefaultMutableTreeNode temp_charset = new DefaultMutableTreeNode(templateModel.getCharsetModel());
			DefaultMutableTreeNode root;
			root = (DefaultMutableTreeNode) treeModel.getRoot();
			treeModel.insertNodeInto(subroot, root, root.getChildCount());
			// Or, more succinctly:
			subroot.add(temp_content);
			subroot.add(temp_charset);
			((DefaultTreeModel)tree.getModel( )).reload(root);
			//加入到工程模型
			projectModel.getTemplateModels().add(templateModel);
			return subroot;
		}else{
			return null;
		}
	}
	/**
	 * 设置被选择的工程树节点
	 * @param leadSelection
	 */
	public void setSelectProjectTreeNode(DefaultMutableTreeNode leadSelection) {
		//把旧树节点的值同步到模型
		syncTreeNode(selectProjectTreeNode);
		this.selectProjectTreeNode = leadSelection;
		initEditor(leadSelection);
		
	}
	/**
	 * 根据模型初始化
	 * @param leadSelection 
	 */
	private void initEditor(DefaultMutableTreeNode leadSelection) {
		if(leadSelection!=null){
			//获取节点类型
			ITreeModel treeModel = (ITreeModel) leadSelection.getUserObject();
			if(treeModel!=null){
				switch  (treeModel.getType()){
				//根节点，模板节点禁止编辑器
				case ITreeModel.ROOT_TYPE:
				case ITreeModel.TEMPLATE_TYPE:
					enableEditor(false);
					setEditorContent("");
					break;
				case ITreeModel.XML_DATA_TYPE:
					enableEditor(true);
					setEditorContent(((XmlDataModel)treeModel).getXml());	
					break;
				case ITreeModel.TEMPLATE_CHARSET_TYPE:
					enableEditor(true);
					setEditorContent(((TemplateCharsetModel)treeModel).getCharset());
					break;
				case ITreeModel.TEMPLATE_CONTENT_TYPE:
					enableEditor(true);
					setEditorContent(((TemplateContentModel)treeModel).getContent());
					break;
				}
				
			}
		}
		
	}
	/**
	 * 设置编辑器内容
	 * @param val
	 */
	public void setEditorContent(String val) {
		if(ta!=null){
			ta.setText(val);
		}
	}
	public void syncTreeNode(DefaultMutableTreeNode leadSelection) {
		
		if(leadSelection!=null){
			//获取节点类型
			ITreeModel treeModel = (ITreeModel) leadSelection.getUserObject();
			if(treeModel!=null){
				String editorText = getEditorText();
				if(editorText==null){
					editorText = "";
				}
				switch  (treeModel.getType()){
				//根节点，模板节点不需要更新模型
				case ITreeModel.ROOT_TYPE:
				case ITreeModel.TEMPLATE_TYPE:					
					break;
				case ITreeModel.XML_DATA_TYPE:
					if(!editorText.equals(((XmlDataModel)treeModel).getXml())){
						((XmlDataModel)treeModel).setXml(editorText);
						setProjectModify(true);
					}
					break;
				case ITreeModel.TEMPLATE_CHARSET_TYPE:
					if(!editorText.equals(((TemplateCharsetModel)treeModel).getCharset())){
						((TemplateCharsetModel)treeModel).setCharset(editorText);
						setProjectModify(true);
					}
					break;
				case ITreeModel.TEMPLATE_CONTENT_TYPE:
					if(!editorText.equals(((TemplateContentModel)treeModel).getContent())){
					 ((TemplateContentModel)treeModel).setContent(editorText);
					 setProjectModify(true);
					}
					break;
				}
				
			}
		}
		
	}
	/**
	 * 激活或者禁止编辑器
	 * @param b
	 */
	private void enableEditor(boolean b) {
          if(ta!=null){
        	  ta.setEnabled(b);
          }
		
	}
	/**
	 * 获取编辑器内容
	 * @return
	 */
	private String getEditorText() {
		if(ta!=null){
			return ta.getText();
		}else{
			return null;
		}
	}
	public TreeRootModel getProjectModel() {
		return projectModel;
	}
	public DefaultMutableTreeNode getSelectProjectTreeNode() {
		return selectProjectTreeNode;
	}
	/**
	 * 根据模型构造树
	 * @param rootModel
	 */
	public void buildProjectTree(TreeRootModel rootModel) {
		if(rootModel!=null){
			projectModel = rootModel;
			//先删除旧树
			if(tree!=null){
				navigationPane.remove(tree);
			}
			
			// DefaultTreeModel can use it to build a complete tree.
			DefaultMutableTreeNode root = new DefaultMutableTreeNode(
					projectModel);
			//
			DefaultMutableTreeNode xmldata = new DefaultMutableTreeNode(
					projectModel.getXmlDataModel()
					);
			
			// Build our tree model starting at the root node, and then make a JTree
			// out
			// of it.
			treeModel = new DefaultTreeModel(root);
			tree = new JTree(treeModel);
			ProjectTreeCellRenderer projectRender;
			projectRender = new ProjectTreeCellRenderer(this.image);
			tree.setCellRenderer(projectRender);
			// Build the tree up from the nodes we created.

			root.add(xmldata);
			//增加树选择事件
			tree.addTreeSelectionListener(new ProjectTreeSelectListener(this));
			//增加树右键菜单事件
			tree.addMouseListener(new ProjectTreeMousePopupListener(tree, createProjectTreePopupMenu(tree)));
			navigationPane.add(tree, BorderLayout.CENTER);
			for(TemplateModel tempModel:projectModel.getTemplateModels()){
				createTemplateTreeNodeFromModel(tempModel);
			}
			tree.expandRow(tree.getRowCount() - 1);
			navigationPane.updateUI();	
			ta.setEditable(true);
		}
	}
	public DefaultMutableTreeNode createTemplateTreeNodeFromModel(TemplateModel tempModel){
		if(projectModel!=null){
			DefaultMutableTreeNode subroot = new DefaultMutableTreeNode(tempModel);
			DefaultMutableTreeNode temp_content = new DefaultMutableTreeNode(tempModel.getContentModel());
			DefaultMutableTreeNode temp_charset = new DefaultMutableTreeNode(tempModel.getCharsetModel());
			DefaultMutableTreeNode root;
			root = (DefaultMutableTreeNode) treeModel.getRoot();
			treeModel.insertNodeInto(subroot, root, root.getChildCount());
			// Or, more succinctly:
			subroot.add(temp_content);
			subroot.add(temp_charset);
			return subroot;
		}else{
			return null;
		}
	}
	public String getProjectFileName() {
		return projectFileName;
	}
	public void setProjectFileName(String projectFileName) {
		this.projectFileName = projectFileName;
	}
	/**
	 * 关闭工程
	 */
	public void closeProject() {
		syncTreeNode(getSelectProjectTreeNode());
		//检查工程是否未存，未存则提示
		if(getProjectModify()){
			if (JOptionPane.showConfirmDialog(this.frame, UIPropertyHelper.getString("system.needSaveProject"), UIPropertyHelper.getString("system.info"), 0) == 0) {
				saveProject();
			}
		}
		//设置工程修改标志为false
		setProjectModify(false);
		if(!projectRunFlag){
			if(tree!=null){
				navigationPane.remove(tree);
			}
			projectFileName = "";
			selectProjectTreeNode=null;
			selectProjectTreeNode = null;
			setEditorContent("");
			ta.setEditable(false);
			navigationPane.updateUI();
		}else{
			JOptionPane.showMessageDialog(getFrame(), UIPropertyHelper.getString("system.isruning"));
		}
	}
	public Boolean getProjectRunFlag() {
		return projectRunFlag;
	}
	public void setProjectRunFlag(Boolean projectRunFlag) {
		this.projectRunFlag = projectRunFlag;
	}
	/**
	 * 删除模板
	 * @param treeNode
	 */
	public void delTemplateNode(DefaultMutableTreeNode treeNode) {  
		if(treeNode!=null){
			ITreeModel model = (ITreeModel) treeNode.getUserObject();
			if(model.getType()== ITreeModel.TEMPLATE_TYPE){
			  treeModel.removeNodeFromParent(treeNode);
			  TemplateModel templateModel;
			  TemplateModel delModel = (TemplateModel) model;
			  for(int i=0;i<projectModel.getTemplateModels().size();i++){
				  templateModel = projectModel.getTemplateModels().get(i);
				  if(templateModel.getTemplateName().equals(delModel.getTemplateName())){
					  projectModel.getTemplateModels().remove(i);
					  setProjectModify(true);
					  return;
				  }
			  }
			}
		}
		
	}
	public JTextArea getTextArea() {
		return ta;
	}
	public void clearOutputWin(){
		outputTextArea.setText("");
	}
	public void addOutputMessage(String msg){
		outputTextArea.setText(outputTextArea.getText()+msg);
	}
	public JTextArea getOutputTextArea() {
		return outputTextArea;
	}
	public JSplitPane getParentsp() {
		return parentsp;
	}
	public ImageHelper getImage() {
		return image;
	}
	public JTree getTree() {
		return tree;
	}
	/**
	 * 获取工程是否修改标志
	 * @return
	 */
	public Boolean getProjectModify() {
		return projectModify;
	}
	/**
	 * 设置工程是否修改标志
	 * @param projectModify
	 */
	public void setProjectModify(Boolean projectModify) {
		this.projectModify = projectModify;
	}
	/**
	 * 保存工程
	 */
	public void saveProject() {
		String fileName;
		//文件名为空，弹出对话框
		if(StringHelper.isEmpty(getProjectFileName())){
			FileDialog dlg;
			dlg = new FileDialog(getFrame(),UIPropertyHelper.getString("dialog.saveproject.title"),FileDialog.SAVE);
			dlg.setFilenameFilter(new XmlFilenameFilter());
			dlg.setVisible(true);
			
			fileName = dlg.getDirectory()+dlg.getFile();
		}else{
			fileName = getProjectFileName();
		}
		if(fileName!=null){
			//先同步更新模型
			syncTreeNode(getSelectProjectTreeNode());
			try {
				SerialHelper.saveToXml(fileName, getProjectModel());
				setProjectModify(false);
			} catch (FileNotFoundException e1) {
				logger.error(e1.getMessage(), e1);
				e1.printStackTrace();
			}
		}		
	}
}
