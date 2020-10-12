package com.cdh.view;


import com.cdh.model.Clazz;
import com.cdh.service.ClazzService;
import com.cdh.util.ResponseData;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import sun.swing.table.DefaultTableCellHeaderRenderer;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ClazzListView extends JFrame {

    private Container container;
    private JTextField searchTextField;
    private JTable table;
    private Integer pageIndex = 1;
    private Integer pageSize = 10;
    private ClazzService clazzService = new ClazzService();
    private ResponseData responseData;

    public ClazzListView(){
        this.setTitle("班级管理列表");
        ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("/images/logo.png"));
        setIconImage(imageIcon.getImage());
        this.setLayout(null);
        this.setBounds(220, 165, 793, 500);
        container = this.getContentPane();
        pageInfo();

        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    private void pageInfo() {
        //查询条件
        JPanel searchPanel = new JPanel();
        searchPanel.setBounds(0,0,793,60);
        searchTextField = new JTextField();
        searchTextField.setPreferredSize(new Dimension(200,30));

        ImageIcon imageIcon = new ImageIcon(ClazzListView.class.getResource("/images/search.png"));
        JButton searchBtn = new JButton("查询",imageIcon);

        imageIcon = new ImageIcon(ClazzListView.class.getResource("/images/edit.png"));
        JButton updateBtn = new JButton("修改",imageIcon);

        imageIcon = new ImageIcon(ClazzListView.class.getResource("/images/del.png"));
        JButton delBtn = new JButton("删除",imageIcon);

        searchPanel.add(searchTextField);
        searchPanel.add(searchBtn);
        searchPanel.add(updateBtn);
        searchPanel.add(delBtn);

        container.add(searchPanel);

        //表格初始化
        table=new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0,60,740,300);

        //表格数据
        responseData = clazzService.page(null,pageIndex,pageSize);
        //表格设置
        tableSetting(responseData.getPageData());

        container.add(scrollPane);

        //分页
        JPanel pagePanel = new JPanel();
        pagePanel.setBounds(0,360,740,50);
        JButton prevBtn = new JButton(new ImageIcon(Clazz.class.getResource("/images/prev.png")));
        JButton nextBtn = new JButton(new ImageIcon(Clazz.class.getResource("/images/next.png")));
        pagePanel.add(prevBtn);
        pagePanel.add(nextBtn);

        container.add(pagePanel);

        //添加事件监听
        searchBtn.addActionListener(this::searchBtnHandler);
        updateBtn.addActionListener(this::updateBtnHandler);
        delBtn.addActionListener(this::delBtnHandler);

        prevBtn.addActionListener(this::prevBtnHandler);
        nextBtn.addActionListener(this::nextBtnHandler);
    }

    private void nextBtnHandler(ActionEvent event) {
        if(pageIndex>=responseData.getPageCount()){
            return;
        }
        pageIndex++;
        String search = searchTextField.getText();
        responseData = clazzService.page(search, pageIndex, pageSize);
        tableSetting(responseData.getPageData());
    }
    //上一页
    private void prevBtnHandler(ActionEvent event) {
        if(pageIndex<=1){
            return;
        }
        pageIndex--;
        String search = searchTextField.getText();
        responseData = clazzService.page(search, pageIndex, pageSize);
        tableSetting(responseData.getPageData());
    }

    /**
     * 表格设置
     * @param tableData 动态数据
     */
    private void tableSetting(Object[][] tableData) {
        String[] tableHeader = {"班级编号","班级名称","班级描述","创建时间"};
        DefaultTableModel tableModel = new DefaultTableModel(tableData,tableHeader){
            @Override
            public boolean isCellEditable(int row, int column) { //设置指定的列不可编辑
                if(column==1 || column==2){
                    return true;
                }
                return false;
            }
        };
        table.setModel(tableModel);

        DefaultTableCellHeaderRenderer headerRenderer = new DefaultTableCellHeaderRenderer();
        headerRenderer.setHorizontalAlignment(SwingUtilities.CENTER);
        ((DefaultTableCellRenderer)table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingUtilities.CENTER);

        /* 内容居中显示*/
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(SwingUtilities.CENTER);
        table.getTableHeader().getColumnModel().getColumn(0).setCellRenderer(renderer);
        table.getTableHeader().getColumnModel().getColumn(1).setCellRenderer(renderer);
        table.getTableHeader().getColumnModel().getColumn(3).setCellRenderer(renderer);

        //表格宽度
        table.getColumnModel().getColumn(0).setPreferredWidth(90);
        table.getColumnModel().getColumn(1).setPreferredWidth(120);
        table.getColumnModel().getColumn(2).setPreferredWidth(260);
        table.getColumnModel().getColumn(3).setPreferredWidth(200);
    }

    //删除选中班级
    private void delBtnHandler(ActionEvent event) {
        int rowId = table.getSelectedRow();
        if(rowId==-1){
            JOptionPane.showMessageDialog(this,"请选择要删除的班级");
            return;
        }
        Integer claId = (Integer) table.getValueAt(rowId, 0);
        String claName = (String) table.getValueAt(rowId, 1);
        int result = JOptionPane.showConfirmDialog(this, "确定删除【" + claName + "】吗？","提示信息",JOptionPane.YES_NO_OPTION);
        if(result == JOptionPane.OK_OPTION){
            ResponseData data = clazzService.delById(claId);
            JOptionPane.showMessageDialog(this,data.getMsg());
            responseData = clazzService.page(null, pageIndex, pageSize);
            tableSetting(responseData.getPageData());
        }
    }

    //修改班级
    private void updateBtnHandler(ActionEvent event) {
        int rowId = table.getSelectedRow();
        if(rowId==-1){
            JOptionPane.showMessageDialog(this,"请选择要修改的班级");
            return;
        }
        if(table.isEditing()){
            JOptionPane.showMessageDialog(this,"还在编辑区域，请点击非编辑区域完成编辑动作！");
            return;
        }
        Integer claId = (Integer) table.getValueAt(rowId,0);
        String claName = (String) table.getValueAt(rowId, 1);
        String claInfo = (String) table.getValueAt(rowId, 2);
        ResponseData data = clazzService.update(claId, claName, claInfo);
        JOptionPane.showMessageDialog(this,data.getMsg());

        //修改完毕后刷新表格信息
        String search = searchTextField.getText();
        responseData = clazzService.page(search, pageIndex, pageSize);
        tableSetting(responseData.getPageData());
    }

    //查询班级
    private void searchBtnHandler(ActionEvent event) {
        String searchVal = searchTextField.getText();
        responseData = clazzService.page(searchVal, pageIndex, pageSize);
        tableSetting(responseData.getPageData());
    }

    public static void main(String[] args) {
        try {
            BeautyEyeLNFHelper.launchBeautyEyeLNF();
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(ClazzListView::new);
    }
}
