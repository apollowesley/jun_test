package com.cdh.view;

import com.cdh.model.User;
import com.cdh.model.UserType;
import com.cdh.service.UserService;
import jdk.nashorn.internal.scripts.JO;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowEvent;
import java.util.Objects;
import java.util.Timer;

public class UpdatePwdView extends JFrame {

    private Container container;
    private UserType userType;
    private User nowUser;
    private UserService userService = new UserService();

    private JPasswordField oldPwdField;
    private JPasswordField newPwdField;
    private JPasswordField okPwdField;

    public UpdatePwdView(UserType userType,User nowUser){
        this.userType = userType;
        this.nowUser = nowUser;

        this.setTitle("修改密码");
        ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("/images/logo.png"));
        setIconImage(imageIcon.getImage());
        this.setLayout(null);
        this.setBounds(400,200,400,320);
        container = this.getContentPane();
        //页面布局实现
        pageInfo();
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    private void pageInfo() {
        JPanel updatePwdPanel = new JPanel();
        updatePwdPanel.setBounds(-30,0,400,300);

        //当前用户
        JPanel nowUserPanel = new JPanel();
        JLabel nowUserLabel = new JLabel("当前用户：");
        JTextField nowUserTextField = new JTextField();
        nowUserTextField.setPreferredSize(new Dimension(200,30)); //设置控件的宽度和高度
        nowUserTextField.setText("["+userType.getName()+"] "+nowUser.getLogName());
        nowUserTextField.setEnabled(false);
        nowUserPanel.add(nowUserLabel);
        nowUserPanel.add(nowUserTextField);

        //旧密码
        JPanel oldPwdPanel = new JPanel();
        JLabel oldPwdLabel = new JLabel("旧 密 码：");
        oldPwdField = new JPasswordField();
        oldPwdField.setPreferredSize(new Dimension(200,30)); //设置控件的宽度和高度
        oldPwdPanel.add(oldPwdLabel);
        oldPwdPanel.add(oldPwdField);

        //新密码
        JPanel newPwdPanel = new JPanel();
        JLabel newPwdLabel = new JLabel("新 密 码：");
        newPwdField = new JPasswordField();
        newPwdField.setPreferredSize(new Dimension(200,30)); //设置控件的宽度和高度
        newPwdPanel.add(newPwdLabel);
        newPwdPanel.add(newPwdField);

        //确认密码
        JPanel okPwdPanel = new JPanel();
        JLabel okPwdLabel = new JLabel("确认密码：");
        okPwdField = new JPasswordField();
        okPwdField.setPreferredSize(new Dimension(200,30)); //设置控件的宽度和高度
        okPwdPanel.add(okPwdLabel);
        okPwdPanel.add(okPwdField);

        //按钮组
        JPanel btnPanel = new JPanel();
        JButton okBtn = new JButton("确认");
        ImageIcon loginIcon = new ImageIcon(LoginView.class.getResource("/images/ok.png"));
        okBtn.setIcon(loginIcon);
        JButton resetBtn = new JButton("重置");
        ImageIcon restIcon = new ImageIcon(LoginView.class.getResource("/images/reset.png"));
        restIcon.setImage(restIcon.getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT));
        resetBtn.setIcon(restIcon);
        btnPanel.add(okBtn);
        btnPanel.add(resetBtn);

        updatePwdPanel.add(nowUserPanel);
        updatePwdPanel.add(oldPwdPanel);
        updatePwdPanel.add(newPwdPanel);
        updatePwdPanel.add(okPwdPanel);
        updatePwdPanel.add(btnPanel);

        container.add(updatePwdPanel);

        //旧密码光标离开验证是否输入正确
        oldPwdField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                oldPwdVerify();
            }
        });

        //按钮事件监听
        resetBtn.addActionListener(this::restBtnHandler);
        okBtn.addActionListener(this::updatePwdHandler);
    }

    //旧密码输入完毕后验证是否有误
    private void oldPwdVerify() {
        String oldPwdVal = String.valueOf(oldPwdField.getPassword());
        boolean flg = userService.oldPwdVerify(oldPwdVal,nowUser.getUserId());
        if(!flg){
            JOptionPane.showMessageDialog(this,"旧密码输入有误！请重新输入","信息提示",JOptionPane.ERROR_MESSAGE);
            oldPwdField.setFocusable(true);
        }
    }

    //修改密码
    private void updatePwdHandler(ActionEvent event) {
        //表单不为空
        String oldPwdVal = String.valueOf(oldPwdField.getPassword());
        String newPwdVal = String.valueOf(newPwdField.getPassword());
        String okPwdVal = String.valueOf(okPwdField.getPassword());
        if(StringUtils.isEmpty(oldPwdVal)){
            JOptionPane.showMessageDialog(this,"旧密码不能为空！");
            return;
        }
        if(StringUtils.isEmpty(newPwdVal)){
            JOptionPane.showMessageDialog(this,"新密码不能为空！");
            return;
        }

        if(!Objects.equals(newPwdVal,okPwdVal)){
            JOptionPane.showMessageDialog(this,"两次密码输入不一致！","信息提示",JOptionPane.ERROR_MESSAGE);
            okPwdField.setFocusable(true);
            return;
        }

        boolean flg = userService.updatePwdByUserId(newPwdVal,nowUser.getUserId());
        if(flg){
            //this.dispose();
            JOptionPane.showMessageDialog(this,"密码修改成功！请重新登陆");
            //new LoginView();
            return;
        }
        JOptionPane.showMessageDialog(this,"密码修改失败！");
    }

    //表单重置
    private void restBtnHandler(ActionEvent event) {
        okPwdField.setText("");
        newPwdField.setText("");
        oldPwdField.setText("");
    }

   /* public static void main(String[] args) {
        try {
            org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(UpdatePwdView::new);
    }*/

}
