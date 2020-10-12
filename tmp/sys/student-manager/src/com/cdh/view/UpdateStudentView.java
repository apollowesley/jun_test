package com.cdh.view;

import com.cdh.model.Clazz;
import com.cdh.model.StudentVO;
import com.cdh.service.ClazzService;
import com.cdh.service.StudentService;
import com.cdh.util.ResponseData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Enumeration;
import java.util.Objects;

public class UpdateStudentView extends JFrame {

    private Container container;
    private JComboBox claNameComboBox;
    private ButtonGroup statusBtnGroup;

    private ClazzService clazzService = new ClazzService();
    private StudentService studentService = new StudentService();
    private StudentVO studentVO;

    public UpdateStudentView(StudentVO studentVO){
        this.studentVO = studentVO;

        this.setTitle("修改学生信息");
        ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("/images/logo.png"));
        setIconImage(imageIcon.getImage());
        this.setLayout(null);
        this.setBounds(400,200,400,260);
        container = this.getContentPane();
        //页面布局实现
        pageInfo();
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    private void pageInfo() {

        //学生姓名
        JPanel stuNamePanel = new JPanel();
        stuNamePanel.setBounds(-30,0,400,40);
        JLabel stuNameLabel = new JLabel("学生姓名：");
        JTextField stuNameTextField = new JTextField(studentVO.getStuName());
        stuNameTextField.setPreferredSize(new Dimension(200,30));
        stuNameTextField.setEnabled(false);
        stuNamePanel.add(stuNameLabel);
        stuNamePanel.add(stuNameTextField);
        container.add(stuNamePanel);

        //班级名称
        JPanel claNamePanel = new JPanel();
        claNamePanel.setBounds(-30,80,400,40);
        JLabel claNameLabel = new JLabel("班级名称：");
        String[] claNameItems = clazzService.findAll().stream().map(Clazz::getClaName).toArray(String[]::new);
        claNameComboBox = new JComboBox(claNameItems);
        claNameComboBox.setPreferredSize(new Dimension(200,30));
        claNameComboBox.setSelectedItem(studentVO.getClaName());
        claNamePanel.add(claNameLabel);
        claNamePanel.add(claNameComboBox);
        container.add(claNamePanel);

        //登录状态
        JPanel statusPanel = new JPanel();
        statusPanel.setBounds(-82,40,450,40);
        JLabel statusLabel = new JLabel("登录状态：");
        statusBtnGroup = new ButtonGroup();
        boolean flg = Objects.equals("启用", studentVO.getLogStatus());
        JRadioButton enabledRadioButton = new JRadioButton("启用",flg);
        JRadioButton disabledRadioButton = new JRadioButton("禁用",!flg);

        statusBtnGroup.add(enabledRadioButton);
        statusBtnGroup.add(disabledRadioButton);
        statusPanel.add(statusLabel);
        statusPanel.add(enabledRadioButton);
        statusPanel.add(disabledRadioButton);
        container.add(statusPanel);

        //按钮组
        JPanel btnPanel = new JPanel();
        btnPanel.setBounds(-30,120,450,60);
        JButton okBtn = new JButton("确认修改");
        ImageIcon loginIcon = new ImageIcon(LoginView.class.getResource("/images/ok.png"));
        okBtn.setIcon(loginIcon);
        btnPanel.add(okBtn);

        container.add(btnPanel);

        //按钮事件监听
        okBtn.addActionListener(this::updateStudentHandler);
    }

    private void updateStudentHandler(ActionEvent event) {
        studentVO.setClaName(claNameComboBox.getSelectedItem().toString());
        Enumeration<AbstractButton> statusGroupElements = statusBtnGroup.getElements();
        while(statusGroupElements.hasMoreElements()){
            AbstractButton element = statusGroupElements.nextElement();
            if(element.isSelected()){
                studentVO.setLogStatus(element.getText());
            }
        }
        ResponseData responseData = studentService.update(studentVO);
        JOptionPane.showMessageDialog(this,responseData.getMsg());
        this.dispose();
    }

  /*  public static void main(String[] args) {
        try {
            org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(()->new UpdateStudentView(null));
    }*/

}
