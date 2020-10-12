package cn.uncode.dal.generator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.ScrollPane;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import cn.uncode.dal.generator.support.BeanUtil;
import cn.uncode.dal.generator.support.DbUtil;
import cn.uncode.dal.generator.support.ServiceUtil;

public class GenerateRunningMain extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTextField ipFiled;
	private JTextField dbFiled;
	private JTextField dbNameFiled;
	private JTextField tabField;
	private JTextField packField;
	private JTextField dtoPackField;
	private JTextField servicePackField;
	private JTextField catField;
	private JCheckBox checkBox;
	private JTextField userField;
	private JTextField pwdField;
	private JComboBox dbBox;
	private static Integer coordY = 10;//控件的y轴坐标
	DbUtil dbutil = new DbUtil();
	BeanUtil butil = new BeanUtil();
	ServiceUtil sutil = new ServiceUtil();
	JLabel labelInfo;
	JTextArea textArea;

	public JTextArea getTextArea() {
		return textArea;
	}

	public void setTextArea(JTextArea textArea) {
		this.textArea = textArea;
	}

	private JTable jtable;
	private MyTableModel tableModel;
	HashMap dbInfoMap;
	String[] titles = { "选择", "表格名称" };

	// 配置文件信息
	Map<String, HashMap<String, String>> dbMap;

	public GenerateRunningMain() {
		setTitle("Uncode数据库生成javabean工具");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 929, 667);

		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JLabel lblIp = new JLabel("IP:");
		lblIp.setBounds(60, coordY, 80, 15);
		lblIp.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(lblIp);

		ipFiled = new JTextField();
		ipFiled.setText("192.168.1.17:3306");
		ipFiled.setBounds(146, coordY, 153, 21);
		panel.add(ipFiled);
		ipFiled.setColumns(10);
		
		JLabel mustIPLabel = new JLabel("* IP地址及端口号");
		mustIPLabel.setForeground(Color.RED);
		mustIPLabel.setBounds(307, coordY, 135, 15);
		panel.add(mustIPLabel);

		JLabel label = new JLabel("数据库:");
		label.setBounds(60, getAddCoordY(), 80, 15);
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(label);
		// String dbStyles[] = { };
		// dbBox = new JComboBox(dbStyles);
		dbBox = new JComboBox();
		dbBox.setModel(new DefaultComboBoxModel(new String[] { "mysql" }));
		dbBox.setBounds(146, coordY, 153, 21);
		dbBox.setVisible(true);
		dbBox.setMaximumRowCount(3);
		panel.add(dbBox);
		

		JLabel dbNamelabel = new JLabel("数据库名:");
		dbNamelabel.setBounds(60, getAddCoordY(), 80, 20);
		dbNamelabel.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(dbNamelabel);

		dbNameFiled = new JTextField();
		dbNameFiled.setBounds(146, coordY, 153, 21);
		dbNameFiled.setText("ksudi_star_dev");
		panel.add(dbNameFiled);
		dbNameFiled.setColumns(10);
		

		JLabel userLabel = new JLabel("用户名:");
		userLabel.setBounds(60, getAddCoordY(), 80, 20);
		userLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(userLabel);

		userField = new JTextField();
		userField.setText("root");
		userField.setBounds(145, coordY, 153, 21);
		panel.add(userField);
		userField.setColumns(10);

		JLabel pwdLabel = new JLabel("密码:");
		pwdLabel.setBounds(60, getAddCoordY(), 80, 15);
		pwdLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(pwdLabel);

		pwdField = new JTextField();
		pwdField.setText("123456");
		pwdField.setBounds(145, coordY, 154, 21);
		panel.add(pwdField);
		pwdField.setColumns(10);
		
		JLabel dtoPackLabel = new JLabel("baseDTO包:");
		dtoPackLabel.setBounds(60, getAddCoordY(), 80, 15);
		dtoPackLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(dtoPackLabel);

		dtoPackField = new JTextField();
		dtoPackField.setText("cn.uncode.dal.core.BaseDTO");
		dtoPackField.setBounds(146, coordY, 153, 21);
		panel.add(dtoPackField);
		dtoPackField.setColumns(10);

		JLabel dtoPacklabel = new JLabel("生成DTO时improt的path");
		dtoPacklabel.setForeground(Color.RED);
		dtoPacklabel.setBounds(307, coordY, 139, 15);
		panel.add(dtoPacklabel);

		JLabel packLabel = new JLabel("DTO包名:");
		packLabel.setBounds(60, getAddCoordY(), 80, 15);
		packLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(packLabel);

		packField = new JTextField();
		packField.setText("cn.uncode.dal.dto");
		packField.setBounds(146, coordY, 153, 21);
		panel.add(packField);
		packField.setColumns(10);

		JLabel mustPacklabel = new JLabel("只影响package,下同");
		mustPacklabel.setForeground(Color.RED);
		mustPacklabel.setBounds(307, coordY, 139, 15);
		panel.add(mustPacklabel);
		
		JLabel servicePackLabel = new JLabel("Service包名:");
		servicePackLabel.setBounds(60, getAddCoordY(), 80, 15);
		servicePackLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(servicePackLabel);

		servicePackField = new JTextField();
		servicePackField.setText("cn.uncode.dal.service");
		servicePackField.setBounds(146, coordY, 153, 21);
		panel.add(servicePackField);
		servicePackField.setColumns(10);

		JLabel catlogLabel = new JLabel("输出目录:");
		catlogLabel.setBounds(60, getAddCoordY(), 80, 15);
		catlogLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(catlogLabel);

		catField = new JTextField();
		catField.setText("temp");
		catField.setBounds(146, coordY, 153, 21);
		panel.add(catField);
		catField.setColumns(10);

		JLabel catlabel = new JLabel("举例：temp\\bean");
		catlabel.setForeground(Color.RED);
		catlabel.setBounds(307, coordY, 139, 15);
		panel.add(catlabel);

		JButton button = new JButton("查询");
		// 按钮增加动作执行go()方法
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				go();

			}
		});
		button.setBounds(80, 287, 110, 37);
		panel.add(button);

		JButton crButton = new JButton("生成Bean");
		// 按钮增加动作执行go()方法
		crButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createBean();
			}
		});
		crButton.setBounds(200, 287, 110, 37);
		panel.add(crButton);

		JButton crsButton = new JButton("生成Service");
		// 按钮增加动作执行go()方法
		crsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createService();
			}
		});
		crsButton.setBounds(320, 287, 110, 37);
		panel.add(crsButton);

		// 增加关闭事件监听，关闭相关操作
		this.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				close();
				System.exit(0);
			}

		});
		// 设置表格

		Object[][] tableData = {};
		tableModel = new MyTableModel(tableData, titles);
		jtable = new JTable(this.tableModel);
		JScrollPane scr = new JScrollPane(this.jtable);
		scr.setBounds(479, 13, 393, 574);
		panel.add(scr);
		// 添加标格监听事件
		jtable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = jtable.getSelectedRow();
				if (jtable.getSelectedColumn() == 0)// 如果是第一列的单元格，则返回，不响应点击
					return;
				// 列响应操作
			}
		});

		// 显示操作信息label
		labelInfo = new JLabel("");
		labelInfo.setForeground(Color.RED);
		labelInfo.setBounds(14, 317, 428, 59);
		panel.add(labelInfo);

		textArea = new JTextArea();
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setBounds(14, 397, 428, 190);
		// panel.add(textArea);

		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setBounds(14, 397, 428, 190);
		scrollPane.add(textArea);
		panel.add(scrollPane);

		// 初始化配置信息和数据库下拉列表
		dbMap = dbutil.getDbConfigMap();
		for (String key : dbMap.keySet()) {
			this.getDbBox().addItem(key);
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// 创建对象
		GenerateRunningMain dtb = new GenerateRunningMain();
		// 设置可见
		dtb.setVisible(true);
		// 点击X关闭窗口
		dtb.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 调用设置居中显示
		dtb.setSizeAndCentralizeMe(900, 650);

	}

	// 设置居中
	private void setSizeAndCentralizeMe(int width, int height) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(width, height);
		this.setLocation(screenSize.width / 2 - width / 2, screenSize.height / 2 - height / 2);
	}

	@SuppressWarnings("unchecked")
	public void createBean() {
		String sucessName = "";
		this.getLabelInfo().setText("");

		this.getTextArea().setText("");
		initInfo();

		// 生成文件夹
		File file = new File("");
		String path = file.getAbsolutePath() + File.separator + dbInfoMap.get("catName").toString();
		File newFile = new File(path);
		if (newFile.exists() == false) {
			newFile.mkdirs();
		}
		dbInfoMap.put("catName", path);
		
		if ((new File(dbInfoMap.get("catName").toString()).isDirectory()) != true) {
			this.getLabelInfo().setText("目录不存在，请重新输入");
		} else {
			if (dbInfoMap.get("packName") != null && !dbInfoMap.get("packName").toString().equals("")) {
				String catPack = dbInfoMap.get("catName").toString();
				catPack = catPack.replace(".", File.separator);
				new File(catPack).mkdirs();
				dbInfoMap.put("catName", catPack);
			}
			// 勾选
			int rowCount = this.jtable.getRowCount();
			for (int i = 0; i < rowCount; i++) {
				if (this.jtable.getValueAt(i, 0).toString().equals("true")) {
					String tabName = this.jtable.getValueAt(i, 1).toString().toLowerCase();
					List<Map<String, String>> tabFileds = dbutil.getColumnNames(dbInfoMap, tabName);
					butil.createBean(tabName, tabFileds, dbInfoMap);
					sucessName += tabName + ",\n";
				}
			}
			this.getTextArea().setText("数据库表:\n" + sucessName + "生成成功");

			try {
				Runtime.getRuntime().exec("explorer " + path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void createService() {
		String sucessName = "";
		this.getLabelInfo().setText("");
		this.getTextArea().setText("");
		initInfo();

		// 生成文件夹
		File file = new File("");
		String path = file.getAbsolutePath() + File.separator + dbInfoMap.get("catName").toString();
		File newFile = new File(path);
		if (newFile.exists() == false) {
			newFile.mkdirs();
		}
		dbInfoMap.put("catName", path);
		
		if ((new File(dbInfoMap.get("catName").toString()).isDirectory()) != true) {
			this.getLabelInfo().setText("目录不存在，请重新输入");
		} else {
			if (dbInfoMap.get("packName") != null && !dbInfoMap.get("packName").toString().equals("")) {
				String catPack = dbInfoMap.get("catName").toString();
				catPack = catPack.replace(".", File.separator);
				new File(catPack).mkdirs();
				dbInfoMap.put("catName", catPack);
			}
			// 勾选
			int rowCount = this.jtable.getRowCount();
			for (int i = 0; i < rowCount; i++) {
				if (this.jtable.getValueAt(i, 0).toString().equals("true")) {
					String tabName = this.jtable.getValueAt(i, 1).toString().toLowerCase();
					List<Map<String, String>> tabFileds = dbutil.getColumnNames(dbInfoMap, tabName);
					sutil.createService(tabName, dbInfoMap);
					sucessName += tabName + ",\n";
				}
			}
			this.getTextArea().setText("Service:\n" + sucessName + "生成成功");

			try {
				Runtime.getRuntime().exec("explorer " + path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void go() {
		this.getLabelInfo().setText("");
		this.getTextArea().setText("");
		initInfo();
		String selTableStr = dbInfoMap.get("showTable").toString();
		// 获取表名
		List<String> tableList = dbutil.getTableNames(dbInfoMap, dbInfoMap.get("dbName").toString());
		if (tableList == null) {
			int rowCount = this.getTableModel().getRowCount();
			int delInd = 0;
			while (delInd < rowCount) {
				this.getTableModel().removeRow(0);
				delInd++;
			}
			this.getLabelInfo().setText("数据库连接异常");
		} else {
			int rowCount = this.getTableModel().getRowCount();
			int delInd = 0;
			while (delInd < rowCount) {
				this.getTableModel().removeRow(0);
				delInd++;
			}
			for (String tName : tableList) {
				Object[] rowData = { new Boolean(false), tName };
				this.getTableModel().addRow(rowData);
			}

		}

	}

	public void initInfo() {
		// 读取配置文件数据库配置
		String user = this.getUserField().getText();
		String pass = this.getPwdField().getText();
		String ip = this.getIpFiled().getText();
		String database = this.getDbNameFiled().getText();
		String dbName = this.getDbBox().getSelectedItem().toString();
		String packName = this.getPackField().getText();
		String servicePackName = this.getServicePackField().getText();
		String catName = this.getCatField().getText();
		String dtoBasePath = this.getDtoPackField().getText();
		// 处理界面数据
		dbInfoMap = new HashMap();
		dbInfoMap = dbMap.get(dbName);
		dbInfoMap.put("userName", user);
		dbInfoMap.put("userpwd", pass);
		dbInfoMap.put("jdbc", dbMap.get(dbName).get("JdbcURL") + ip + dbMap.get(dbName).get("dbStr") + database);
		dbInfoMap.put("driver", dbMap.get(dbName).get("driverClassName"));
		dbInfoMap.put("dbName", database);
		dbInfoMap.put("packName", packName);
		dbInfoMap.put("servicePackName", servicePackName);
		dbInfoMap.put("catName", catName);
		dbInfoMap.put("dtoBasePath", dtoBasePath);

	}

	private void close() {
		System.out.println("关闭事件");
	}

	public JTextField getIpFiled() {
		return ipFiled;
	}

	public void setIpFiled(JTextField ipFiled) {
		this.ipFiled = ipFiled;
	}

	public JTextField getDbFiled() {
		return dbFiled;
	}

	public void setDbFiled(JTextField dbFiled) {
		this.dbFiled = dbFiled;
	}

	public JTextField getTabField() {
		return tabField;
	}

	public void setTabField(JTextField tabField) {
		this.tabField = tabField;
	}

	public JTextField getPackField() {
		return packField;
	}

	public void setPackField(JTextField packField) {
		this.packField = packField;
	}

	public JTextField getCatField() {
		return catField;
	}

	public void setCatField(JTextField catField) {
		this.catField = catField;
	}

	public JCheckBox getCheckBox() {
		return checkBox;
	}

	public void setCheckBox(JCheckBox checkBox) {
		this.checkBox = checkBox;
	}

	public JTextField getUserField() {
		return userField;
	}

	public void setUserField(JTextField userField) {
		this.userField = userField;
	}

	public JTextField getPwdField() {
		return pwdField;
	}

	public void setPwdField(JTextField pwdField) {
		this.pwdField = pwdField;
	}

	public JTextField getDbNameFiled() {
		return dbNameFiled;
	}

	public void setDbNameFiled(JTextField dbNameFiled) {
		this.dbNameFiled = dbNameFiled;
	}

	public JComboBox getDbBox() {
		return dbBox;
	}

	public void setDbBox(JComboBox dbBox) {
		this.dbBox = dbBox;
	}

	public JLabel getLabelInfo() {
		return labelInfo;
	}

	public void setLabelInfo(JLabel labelInfo) {
		this.labelInfo = labelInfo;
	}

	public JTable getJtable() {
		return jtable;
	}

	public void setJtable(JTable jtable) {
		this.jtable = jtable;
	}

	public MyTableModel getTableModel() {
		return tableModel;
	}

	public void setTableModel(MyTableModel tableModel) {
		this.tableModel = tableModel;
	}
	

	public JTextField getServicePackField() {
		return servicePackField;
	}

	public void setServicePackField(JTextField servicePackField) {
		this.servicePackField = servicePackField;
	}

	public JTextField getDtoPackField() {
		return dtoPackField;
	}

	public void setDtoPackField(JTextField dtoPackField) {
		this.dtoPackField = dtoPackField;
	}




	class MyTableModel extends DefaultTableModel {
		public MyTableModel(Object[][] data, String[] columns) {
			super(data, columns);
		}

		public boolean isCellEditable(int row, int column) { // 设置Table单元格是否可编辑
			if (column == 0)
				return true;
			return false;
		}

		public Class<?> getColumnClass(int columnIndex) {
			if (columnIndex == 0) {
				return Boolean.class;
			}
			return Object.class;
		}
	}
	private static Integer getAddCoordY(){
		coordY = coordY + 30;
		return coordY;
	}
}
