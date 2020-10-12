package com.cdh.view;

import com.cdh.model.Clazz;
import com.cdh.service.ClazzService;
import com.cdh.service.StudentService;
import com.cdh.util.ResponseData;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class AddStudentView extends JFrame{

    private Container container;
    private JComboBox stuClassComboBox;
    private JTextField stuNameTextField;
    private ButtonGroup sexGroup;
    private ButtonGroup statusGroup;
    private JRadioButton manRadioBtn,womanRadioBtn,enabledRadioBtn,disabledRadioBtn;

    private ClazzService clazzService = new ClazzService();
    private StudentService studentService = new StudentService();

    public AddStudentView(){
        this.setTitle("添加学生");
        ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("/images/logo.png"));
        setIconImage(imageIcon.getImage());
        this.setLayout(null);
        this.setBounds(400,220,400,300);
        container = this.getContentPane();
        pageInfo();

        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    private void pageInfo() {
        JPanel stuNamePanel = new JPanel();
        stuNamePanel.setBounds(-30,0,400,40);
        JLabel stuNameLabel = new JLabel("学生姓名：");
        stuNameTextField = new JTextField();
        stuNameTextField.setPreferredSize(new Dimension(200,30));
        stuNamePanel.add(stuNameLabel);
        stuNamePanel.add(stuNameTextField);

        JPanel stuClassPanel = new JPanel();
        stuClassPanel.setBounds(-30,40,400,40);
        JLabel stuClassLabel = new JLabel("学生班级：");

        List<Clazz> list = clazzService.findAll();
        if(Objects.nonNull(list) && !list.isEmpty()){
            String[] claNameItems = list.stream().map(Clazz::getClaName).toArray(String[]::new);
            stuClassComboBox = new JComboBox<>(claNameItems);
        }
        stuClassComboBox.setPreferredSize(new Dimension(200,30));
        stuClassPanel.add(stuClassLabel);
        stuClassPanel.add(stuClassComboBox);

        //状态
        JPanel statusPanel = new JPanel();
        statusPanel.setBounds(-85,80,430,40);
        JLabel statusLabel = new JLabel("状    态：");
        statusGroup = new ButtonGroup();
        enabledRadioBtn = new JRadioButton("启用",true);
        disabledRadioBtn = new JRadioButton("禁用");
        statusGroup.add(enabledRadioBtn);
        statusGroup.add(disabledRadioBtn);
        statusPanel.add(statusLabel);
        statusPanel.add(enabledRadioBtn);
        statusPanel.add(disabledRadioBtn);

        //性别
        JPanel sexPanel = new JPanel();
        sexPanel.setBounds(-110,120,460,40);
        JLabel sexLabel = new JLabel("性    别：");
        sexGroup = new ButtonGroup(); //使用组包裹，实现真正的单选
        manRadioBtn = new JRadioButton("男",true);
        womanRadioBtn = new JRadioButton("女");
        sexGroup.add(manRadioBtn);
        sexGroup.add(womanRadioBtn);
        sexPanel.add(sexLabel);
        sexPanel.add(manRadioBtn);
        sexPanel.add(womanRadioBtn);

        //按钮组
        JPanel btnPanel = new JPanel();
        btnPanel.setBounds(-30,160,400,60);
        ImageIcon addIcon = new ImageIcon(AddClazzView.class.getResource("/images/add.png"));
        JButton okBtn = new JButton("添加",addIcon);
        ImageIcon resetIcon = new ImageIcon(AddClazzView.class.getResource("/images/reset.png"));
        JButton resetBtn = new JButton("重置",resetIcon);
        btnPanel.add(okBtn);
        btnPanel.add(resetBtn);

        container.add(stuNamePanel);
        container.add(stuClassPanel);
        container.add(statusPanel);
        container.add(sexPanel);
        container.add(btnPanel);

        //添加事件监听
        okBtn.addActionListener(this::okBtnHandler);
        resetBtn.addActionListener(this::restBtnHandler);
    }
    //重置
    private void restBtnHandler(ActionEvent event) {
        stuNameTextField.setText("");
        stuClassComboBox.setSelectedIndex(0);
        manRadioBtn.setSelected(true);
        enabledRadioBtn.setSelected(true);
    }

    private void okBtnHandler(ActionEvent event) {
        String stuName = stuNameTextField.getText();
        if(StringUtils.isEmpty(stuName)){
            JOptionPane.showMessageDialog(this, "学生姓名不能为空！", "提示信息", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String stuClazz = stuClassComboBox.getSelectedItem().toString();
        String stuSex = null,stuStatus = null;
        Enumeration<AbstractButton> sexElements = sexGroup.getElements();
        while(sexElements.hasMoreElements()){
            AbstractButton nextElement = sexElements.nextElement();
            if(nextElement.isSelected()){
                stuSex = nextElement.getText();
            }
        }
        Enumeration<AbstractButton> statusElements = statusGroup.getElements();
        while (statusElements.hasMoreElements()) {
            AbstractButton nextElement = statusElements.nextElement();
            if (nextElement.isSelected()) {
                stuStatus = nextElement.getText();
            }
        }

        ResponseData responseData = studentService.save(stuName, stuClazz, stuSex, stuStatus);
        JOptionPane.showMessageDialog(this,responseData.getMsg());
        this.dispose();
    }

   /* public static void main(String[] args) {
        try {
            org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(AddStudentView::new);
    }*/
}
