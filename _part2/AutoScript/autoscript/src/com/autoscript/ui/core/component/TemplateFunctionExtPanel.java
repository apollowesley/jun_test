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
import com.autoscript.ui.helper.TypeConversionHelper;
import com.autoscript.ui.helper.UIPropertyHelper;
import com.autoscript.ui.model.extconfig.TemplateFunctionConfigModel;
import com.autoscript.ui.model.extconfig.function.FunctionConfigModel;
import com.autoscript.ui.model.extconfig.function.IFunctionConfigModel;

/**
 * 模板函数扩展编辑面板 
 * 作者:龙色波 
 * 日期:2013-10-24
 */
public class TemplateFunctionExtPanel extends JPanel {
	/**
	 * 表格标题
	 */
	private String titles[] = new String[] {
			UIPropertyHelper.getString("table.header.function.name"),
			UIPropertyHelper.getString("table.header.function.implClassName"),
			UIPropertyHelper.getString("table.header.function.describe")};

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

	private boolean init=false;

	public TemplateFunctionExtPanel() {

	}

	public void initLayout() {
		if(init){
			return;
		}
		init = true;
		setLayout(new BorderLayout());
		add(this.paramPanel, "Center");
		add(this.buttonPanel, "South");

		setBorder(new EmptyBorder(1, 1, 1, 1));
		this.paramPanel.setBorder(new EmptyBorder(1, 1, 1, 1));
		this.buttonPanel.setBorder(new EmptyBorder(1, 1, 1, 1));
		this.paramPanel.setPreferredSize(new Dimension(330,200));
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
				TemplateFunctionExtPanel.this.addRow();
			}
		});
		this.delbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TemplateFunctionExtPanel.this.delRow();
			}
		});
		enableButton(true);
		fillParam(new TemplateFunctionConfigModel());
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
		column.setPreferredWidth(400);
		column = popupMenuConfTable.getColumnModel().getColumn(2);
		column.setPreferredWidth(200);
	}

	private void addRow() {
		String[] datas = { "", "","" };
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

	public void fillParam( TemplateFunctionConfigModel extConfigModel) {
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
	 * 获取函数扩展配置列表
	 * @return
	 */
	public List<IFunctionConfigModel> getFuncConfigModels(){
		 Vector<?> vector = null;
		 vector = paramModel.getDataVector();
		 int length = vector.size();
		 List<IFunctionConfigModel> funcItems = new ArrayList<IFunctionConfigModel>();
		 for (int i = 0; i < length; i++) {
			 FunctionConfigModel funcItem = new FunctionConfigModel();
			 funcItem.setName((String) ((Vector<?>) vector.elementAt(i)).elementAt(0));
			 funcItem.setFunctionClass(((String) ((Vector<?>) vector.elementAt(i)).elementAt(1)));
			 funcItem.setDescribe((String) ((Vector<?>) vector.elementAt(i)).elementAt(2));
			 funcItems.add(funcItem);
		 }
		 return funcItems;
	}
}
