package com.cdh.view;

import com.cdh.service.ClazzService;
import com.cdh.util.ResponseData;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class AddClazzView extends JFrame {

    private JTextField clazzNameTextField;
    private JTextArea clazzInfoTextArea;
    private Container container;

    public AddClazzView(){
        this.setTitle("添加班级");
        ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("/images/logo.png"));
        setIconImage(imageIcon.getImage());
        this.setLayout(null);
        this.setBounds(400,250,400,300);
        container = this.getContentPane();
        //页面布局实现
        this.pageInfo();
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    private void pageInfo() {
        JPanel contextPanel = new JPanel();
        contextPanel.setBounds(0,0,350,400);

        //班级名称
        JPanel clazzNamePanel = new JPanel();
        JLabel clazzNameLabel = new JLabel("班级名称：");
        clazzNameTextField = new JTextField();
        clazzNameTextField.setPreferredSize(new Dimension(200,30));
        clazzNamePanel.add(clazzNameLabel);
        clazzNamePanel.add(clazzNameTextField);

        //班级信息
        JPanel clazzInfoPanel = new JPanel();
        JLabel clazzInfoLabel = new JLabel("班级信息：");
        clazzInfoTextArea = new JTextArea(5,24);
        clazzInfoTextArea.setBorder(new LineBorder(Color.GRAY));
        //clazzInfoTextArea.setBorder(toBack(););
        clazzInfoPanel.add(clazzInfoLabel);
        clazzInfoPanel.add(clazzInfoTextArea);

        //按钮组
        JPanel btnPanel = new JPanel();
        ImageIcon addIcon = new ImageIcon(AddClazzView.class.getResource("/images/add.png"));
        JButton okBtn = new JButton("添加",addIcon);
        ImageIcon resetIcon = new ImageIcon(AddClazzView.class.getResource("/images/reset.png"));
        JButton resetBtn = new JButton("重置",resetIcon);
        btnPanel.add(okBtn);
        btnPanel.add(resetBtn);

        contextPanel.add(clazzNamePanel);
        contextPanel.add(clazzInfoPanel);
        contextPanel.add(btnPanel);

        container.add(contextPanel);

        //按钮事件监听
        resetBtn.addActionListener(this::restBtnHandler);
        okBtn.addActionListener(this::okBtnHandler);

    }

    //添加班级
    private void okBtnHandler(ActionEvent event) {
        String clazzName = clazzNameTextField.getText();
        String clazzInfo = clazzInfoTextArea.getText();
        if(StringUtils.isEmpty(clazzName)){
            JOptionPane.showMessageDialog(this,"班级名称不能为空！");
            return;
        }
        ResponseData data = new ClazzService().save(clazzName, clazzInfo);
        this.dispose();
        JOptionPane.showMessageDialog(this,data.getMsg());
    }

    private void restBtnHandler(ActionEvent event) {
        clazzNameTextField.setText("");
        clazzInfoTextArea.setText("");
    }

    /*public static void main(String[] args) {
        try {
            org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(AddClazzView::new);
    }*/

}
