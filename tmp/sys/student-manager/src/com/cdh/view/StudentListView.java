package com.cdh.view;

import com.cdh.model.Clazz;
import com.cdh.model.Student;
import com.cdh.model.StudentVO;
import com.cdh.service.ClazzService;
import com.cdh.service.StudentService;
import com.cdh.util.ResponseData;
import com.sun.xml.internal.messaging.saaj.soap.JpegDataContentHandler;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Objects;

public class StudentListView extends JFrame {

    private Container container;
    private ResponseData responseData;
    private Integer pageIndex = 1;
    private final Integer pageSize = 10;
    private JTable stuInfoTable;
    private JTextField stuNameTextField;
    private JComboBox claNameComboBox;

    private ClazzService clazzService = new ClazzService();
    private StudentService studentService = new StudentService();

    public StudentListView(){
        this.setTitle("学生管理列表");
        ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("/images/logo.png"));
        setIconImage(imageIcon.getImage());
        this.setLayout(null);
        this.setBounds(250, 165, 810, 500);
        container = this.getContentPane();

        pageInfo();

        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    private void pageInfo() {
        //查询
        JPanel searchPanel = new JPanel();
        searchPanel.setBounds(-40,0,813,60);
        JLabel stuNameLabel = new JLabel("学生姓名：");
        stuNameTextField = new JTextField();
        stuNameTextField.setPreferredSize(new Dimension(100,30));
        searchPanel.add(stuNameLabel);
        searchPanel.add(stuNameTextField);

        JLabel claNameLabel = new JLabel("班级名称：");
        String[] claNameItems = clazzService.findAll().stream().map(Clazz::getClaName).toArray(String[]::new);
        claNameComboBox = new JComboBox(claNameItems);
        claNameComboBox.insertItemAt("请选择",0);
        claNameComboBox.setSelectedIndex(0);
        searchPanel.add(claNameLabel);
        searchPanel.add(claNameComboBox);

        //查询/修改/删除按钮
        ImageIcon searchIcon = new ImageIcon(this.getClass().getResource("/images/search.png"));
        JButton searchBtn = new JButton("查询",searchIcon);
        ImageIcon updateBtnIcon = new ImageIcon(this.getClass().getResource("/images/edit.png"));
        JButton updateBtn = new JButton("修改",updateBtnIcon);
        ImageIcon delBtnIcon = new ImageIcon(this.getClass().getResource("/images/del.png"));
        JButton delBtn = new JButton("删除",delBtnIcon);

        searchPanel.add(searchBtn);
        searchPanel.add(updateBtn);
        searchPanel.add(delBtn);

        //表格
        stuInfoTable = new JTable();
        //把表格存放在滚动面板中
        JScrollPane tableScrollPane = new JScrollPane(stuInfoTable);
        tableScrollPane.setBounds(0,60,755,300);

        //表格数据
        responseData = studentService.page("","",pageIndex,pageSize);
        //表格设置
        tableSetting(responseData.getPageData());

        //分页
        JPanel pagePanel = new JPanel();
        pagePanel.setBounds(-40,360,800,60);
        JButton prevBtn = new JButton(new ImageIcon(this.getClass().getResource("/images/prev.png")));
        JButton nextBtn = new JButton(new ImageIcon(this.getClass().getResource("/images/next.png")));
        pagePanel.add(prevBtn);
        pagePanel.add(nextBtn);

        container.add(searchPanel);
        container.add(tableScrollPane);
        container.add(pagePanel);

        //事件监听
        searchBtn.addActionListener(this::searchBtnHandler);
        updateBtn.addActionListener(this::updateBtnHandler);
        delBtn.addActionListener(this::delBtnHandler);

        //分页监听
        prevBtn.addActionListener(this::prevBtnHandler);
        nextBtn.addActionListener(this::nextBtnHandler);
    }

    //下一页
    private void nextBtnHandler(ActionEvent event) {
        if(pageIndex>=responseData.getPageCount()){
            return;
        }
        pageIndex++;
        clickPageCode();
    }

    private void prevBtnHandler(ActionEvent event) {
        if(pageIndex<=1){
            return;
        }
        pageIndex--;
        clickPageCode();
    }

    private void clickPageCode() {
        String stuName = stuNameTextField.getText();
        String claName = claNameComboBox.getSelectedItem().toString();
        ResponseData responseData = studentService.page(stuName, claName, pageIndex, pageSize);
        tableSetting(responseData.getPageData());
    }

    private void delBtnHandler(ActionEvent event) {
        int rowId = stuInfoTable.getSelectedRow();
        if(rowId==-1){
            JOptionPane.showMessageDialog(this,"请选择要删除的学生！！");
            return;
        }
        String stuName = (String) stuInfoTable.getValueAt(rowId,1);
        Integer stuId = (Integer) stuInfoTable.getValueAt(rowId,0);
        int result = JOptionPane.showConfirmDialog(this, "你确定要删除【"+stuName+"】吗？","提示信息",JOptionPane.YES_NO_OPTION);
        if(result == JOptionPane.OK_OPTION){
            ResponseData responseData = studentService.delById(stuId);
            JOptionPane.showMessageDialog(this,responseData.getMsg());

            //重新查询
            clickPageCode();
        }

    }

    private void updateBtnHandler(ActionEvent event) {
        int rowId = stuInfoTable.getSelectedRow();
        if(rowId==-1){
            JOptionPane.showMessageDialog(this,"请选择要修改的学生！！");
            return;
        }
        Integer stuId = (Integer) stuInfoTable.getValueAt(rowId,0);
        String stuName = (String) stuInfoTable.getValueAt(rowId,1);
        String logStatus = (String) stuInfoTable.getValueAt(rowId,3);
        String claName = (String) stuInfoTable.getValueAt(rowId,5);
        StudentVO studentVO = StudentVO.builder().stuId(stuId).stuName(stuName).claName(claName).logStatus(logStatus).build();
        new UpdateStudentView(studentVO);
    }

    private void searchBtnHandler(ActionEvent event) {
       clickPageCode();
    }

    //表格设置
    private void tableSetting(Object[][] tableData) {
        String[] tableHeader = {"学号","姓名","登录帐号","状态","性别","班级","注册时间"};
        DefaultTableModel defaultTableModel = new DefaultTableModel(tableData,tableHeader){
            @Override
            public boolean isCellEditable(int row, int column) {
               /* if(column==3 || column==5){
                    return true;
                }*/
                return false;
            }
        };
        stuInfoTable.setModel(defaultTableModel);

        //表格头信息居中显示
        ((DefaultTableCellRenderer)stuInfoTable.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingUtilities.CENTER);
        //表格内容居中显示
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(SwingUtilities.CENTER);
        for (int i = 0; i < stuInfoTable.getColumnCount() ; i++) {
            stuInfoTable.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }
    }

    public static void main(String[] args) {
        try {
            BeautyEyeLNFHelper.launchBeautyEyeLNF();
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(StudentListView::new);
    }
}
