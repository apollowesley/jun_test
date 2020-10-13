/**
 * 
 */
package com.autoscript.ui.core.component;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableColumn;

import com.autoscript.ui.helper.ImageHelper;
import com.autoscript.ui.helper.StringHelper;
import com.autoscript.ui.helper.TypeConversionHelper;
import com.autoscript.ui.helper.UIPropertyHelper;
import com.autoscript.ui.model.extconfig.XmlDataPopupMenuConfigModel;
import com.autoscript.ui.model.extconfig.menu.XmlDataPopupMenuItemConfigModel;

/**
 * Xml源数据右键菜单扩展编辑面板 
 * 作者:龙色波 
 * 日期:2013-10-16
 */
public class XmlDataPopupMenuExtPanel extends JPanel {
	/**
	 * 表格标题
	 */
	private String titles[] = new String[] {
			UIPropertyHelper.getString("table.header.xmldata.menu_name"),
			UIPropertyHelper.getString("table.header.xmldata.menu_describe"),
			UIPropertyHelper
					.getString("table.header.xmldata.menu_acceleratorKey"),
			UIPropertyHelper.getString("table.header.xmldata.menu_iconFile"),
			UIPropertyHelper
					.getString("table.header.xmldata.menu_implClassName"), };

	/**
	 * 
	 */
	private static final long serialVersionUID = 6134261030920054516L;
	private JScrollPane paramPanel = new JScrollPane();

	private JPanel buttonPanel = new JPanel();
	private JTable popupMenuConfTable;
	private BatchColumnModel paramColModel;
	private AutoSriptTableModel paramModel;
	private ImageHelper image = new ImageHelper();
	private JButton addbutton = null;
	private JButton delbutton = null;
	private boolean init= false;
	public XmlDataPopupMenuExtPanel() {

	}

	public void initLayout() {
		if(init){
			return;
		}
		init = true;
		setLayout(new BorderLayout());
		
		add(this.paramPanel, "North");
		add(this.buttonPanel, "South");

		setBorder(new EmptyBorder(1, 1, 1, 1));
		this.paramPanel.setBorder(new EmptyBorder(1, 1, 1, 1));
		this.buttonPanel.setBorder(new EmptyBorder(1, 1, 1, 1));
//		Dimension parentsize = getParent().getPreferredSize();
//		this.paramPanel.setPreferredSize(new Dimension(parentsize.width,
//				parentsize.height - 5));
		this.paramPanel.setPreferredSize(new Dimension(330,
				200));
		this.buttonPanel.setPreferredSize(new Dimension(330, 30));

		this.buttonPanel.setLayout(new FlowLayout(1));
		this.addbutton = new JButton(this.image.ADD_ICON);
		this.delbutton = new JButton(this.image.DELETE_ICON);
		this.buttonPanel.add(this.addbutton);
		this.buttonPanel.add(this.delbutton);
		this.addbutton.setToolTipText(UIPropertyHelper
				.getString("batch.addparam.label"));

		this.delbutton.setToolTipText(UIPropertyHelper
				.getString("batch.deleteparam.label"));
		this.addbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				XmlDataPopupMenuExtPanel.this.addRow();
			}
		});
		this.delbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				XmlDataPopupMenuExtPanel.this.delRow();
			}
		});
		enableButton(true);
		fillParam(new XmlDataPopupMenuConfigModel());
		//设置列头宽度
		configColumnsWidth();
	}
	/**
	 * 配置每列宽度
	 */
	private void configColumnsWidth() {
		TableColumn column = popupMenuConfTable.getColumnModel().getColumn(0);
		column.setPreferredWidth(120);
		column = popupMenuConfTable.getColumnModel().getColumn(1);
		column.setPreferredWidth(120);
		column = popupMenuConfTable.getColumnModel().getColumn(2);
		column.setPreferredWidth(140);
		column = popupMenuConfTable.getColumnModel().getColumn(3);
		column.setPreferredWidth(140);
		column = popupMenuConfTable.getColumnModel().getColumn(4);
		column.setPreferredWidth(400);
		column.setMinWidth(300);
	}
	private void addRow() {
		String[] datas = { "", "","","","" };
		this.paramModel.addRow(TypeConversionHelper.convertToVector(datas));
		this.popupMenuConfTable.updateUI();
	}

	private void delRow() {
		if (this.popupMenuConfTable.getSelectedRowCount() > 0) {
			int[] selrow = this.popupMenuConfTable.getSelectedRows();
			for (int i = selrow.length - 1; i >= 0; i--)
				this.paramModel.removeRow(selrow[i]);
			this.popupMenuConfTable.updateUI();
		}
	}

	public void fillParam( XmlDataPopupMenuConfigModel extConfigModel) {
		this.paramPanel.setViewportView(createNodeParamTable(titles,
				 extConfigModel.toArray(),true));
	}

	private JTable createNodeParamTable(String[] titles, String[][] datas,
			boolean isEdit) {
		if (this.popupMenuConfTable == null) {
			this.paramModel = new AutoSriptTableModel(titles, datas);
			this.paramColModel = new BatchColumnModel();
			this.popupMenuConfTable = new JTable(this.paramModel, this.paramColModel);

			this.popupMenuConfTable.createDefaultColumnsFromModel();
			this.paramModel.initModify();
			this.paramModel.addTableModelListener();
		} else {
			this.paramModel.setDataVector(titles, datas);
			this.paramModel.initModify();
			this.popupMenuConfTable.setModel(this.paramModel);
			this.popupMenuConfTable.setColumnModel(this.paramColModel);
		}

		if (isEdit)
			this.popupMenuConfTable.setEnabled(true);
		else {
			this.popupMenuConfTable.setEnabled(false);
		}
		return this.popupMenuConfTable;
	}

	public void enableButton(boolean bEnable) {
		this.addbutton.setEnabled(bEnable);
		this.delbutton.setEnabled(bEnable);
	}

	public AutoSriptTableModel getParamModel() {
		return this.paramModel;
	}
	/**
	 * 获取菜单项扩展配置列表
	 * @return
	 */
	public List<XmlDataPopupMenuItemConfigModel> getXmlDataPopupMenuItemConfigModels(){
		 Vector<?> vector = null;
		 vector = paramModel.getDataVector();
		 int length = vector.size();
		 List<XmlDataPopupMenuItemConfigModel> menuItems = new ArrayList<XmlDataPopupMenuItemConfigModel>();
		 for (int i = 0; i < length; i++) {
			 XmlDataPopupMenuItemConfigModel menuItem = new XmlDataPopupMenuItemConfigModel();
			 menuItem.setName((String) ((Vector<?>) vector.elementAt(i)).elementAt(0));
			 menuItem.setDescribe((String) ((Vector<?>) vector.elementAt(i)).elementAt(1));
			 String acceleratorKey = (String) ((Vector<?>) vector.elementAt(i)).elementAt(2);
			 if(!StringHelper.isEmpty(acceleratorKey)){
			    menuItem.setAcceleratorKey(acceleratorKey.charAt(0));
			 }else{
				 menuItem.setAcceleratorKey(' ');	 
			 }
			 menuItem.setIconFile((String) ((Vector<?>) vector.elementAt(i)).elementAt(3));
			 menuItem.setImplClassName((String) ((Vector<?>) vector.elementAt(i)).elementAt(4));
			 menuItems.add(menuItem);
		 }
		 return menuItems;
	}
}
