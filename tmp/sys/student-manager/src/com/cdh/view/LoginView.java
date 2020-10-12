package com.cdh.view;

import com.cdh.model.User;
import com.cdh.model.UserType;
import com.cdh.service.UserService;
import com.cdh.util.ResponseData;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Objects;

public class LoginView extends JFrame{


    private JTextField logNameField;
    private JPasswordField logPwdField ;
    private JComboBox<UserType> typeComboBox;

    //用于其他页面判断和操作的公用字段
    private UserType userType;
    private User nowUser;

    public LoginView(){

        this.setTitle("学生管理系统");
        ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("/images/logo.png"));
        setIconImage(imageIcon.getImage());
        this.setLayout(null); //绝对定位
        JPanel contextPanel = new JPanel();
        this.setContentPane(contextPanel);
        this.setBounds(450,200,400,350);
        Container container = this.getContentPane();
        //页面布局实现
        pageInfo(container);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setVisible(true);

    }

    /**
     * 页面布局
     * @param container 页面容器
     */
    private void pageInfo(Container container) {
        //登录界面标题
        JPanel titlePanel = new JPanel();
        titlePanel.setBounds(40,5,350,50);
        ImageIcon logo = new ImageIcon(LoginView.class.getResource("/images/logo.png"));
        logo.setImage(logo.getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT));
        JLabel title = new JLabel("用户登陆界面",logo,SwingConstants.CENTER);
        Font font = new Font("漫画字体",Font.BOLD,26);
        title.setFont(font);
        titlePanel.add(title);

        container.add(titlePanel);

        //用户名
        JPanel logNamePanel = new JPanel();
        logNamePanel.setBounds(0,60,400,40);
        ImageIcon logNameIcon = new ImageIcon(LoginView.class.getResource("/images/logName.png"));
        logNameIcon.setImage(logNameIcon.getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT));
        JLabel logNameLabel = new JLabel("用 户 名：",logNameIcon,SwingConstants.RIGHT);
        logNameField = new JTextField();
        logNameField.setPreferredSize(new Dimension(200,30)); //设置控件的宽度和高度
        logNamePanel.add(logNameLabel);
        logNamePanel.add(logNameField);

        container.add(logNamePanel);

        //密码
        JPanel logPwdPanel = new JPanel();
        logPwdPanel.setBounds(0,100,400,40);
        ImageIcon logPwdIcon = new ImageIcon(LoginView.class.getResource("/images/logPwd.png"));
        logPwdIcon.setImage(logPwdIcon.getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT));
        JLabel logPwdLabel = new JLabel("密    码：",logPwdIcon,SwingConstants.RIGHT);
        logPwdField = new JPasswordField();
        logPwdField.setPreferredSize(new Dimension(200,30));
        logPwdPanel.add(logPwdLabel);
        logPwdPanel.add(logPwdField);

        container.add(logPwdPanel);

        //帐号类型
        JPanel typePanel = new JPanel();
        typePanel.setBounds(0,140,400,40);
        ImageIcon typeIcon = new ImageIcon(LoginView.class.getResource("/images/type.png"));
        typeIcon.setImage(typeIcon.getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT));
        JLabel typeLabel = new JLabel("帐号类型：",typeIcon,SwingConstants.RIGHT);
        typeComboBox = new JComboBox<>(new DefaultComboBoxModel<>(new UserType[]{UserType.ADMIN,UserType.TEACHER,UserType.STUDENT}));
        typeComboBox.setPreferredSize(new Dimension(200,30));
        typePanel.add(typeLabel);
        typePanel.add(typeComboBox);

        container.add(typePanel);

        //登录注册
        JPanel btnPanel = new JPanel();
        btnPanel.setBounds(0,180,400,40);
        JButton loginBtn = new JButton("登录");
        ImageIcon loginIcon = new ImageIcon(LoginView.class.getResource("/images/login.png"));
        loginIcon.setImage(loginIcon.getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT));
        loginBtn.setIcon(loginIcon);
        JButton resetBtn = new JButton("重置");
        ImageIcon restIcon = new ImageIcon(LoginView.class.getResource("/images/reset.png"));
        restIcon.setImage(restIcon.getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT));
        resetBtn.setIcon(restIcon);
        btnPanel.add(loginBtn);
        btnPanel.add(resetBtn);
        //开启按钮监听
        resetBtn.addActionListener(this::resetVal);
        loginBtn.addActionListener(this::userLogin);
        container.add(btnPanel);
    }

    //重置表单信息
    private void resetVal(ActionEvent e) {
        logNameField.setText("");
        logPwdField.setText("");
        typeComboBox.setSelectedIndex(0);
    }
    //用户登录
    private void userLogin(ActionEvent e) {
        String logName = logNameField.getText();
        String logPwd = String.valueOf(logPwdField.getPassword());
        if(StringUtils.isEmpty(logName)){
            JOptionPane.showMessageDialog(this,"用户名不能为空！");
            return;
        }
        if(StringUtils.isEmpty(logPwd)){
            JOptionPane.showMessageDialog(this,"密码不能为空！");
            return;
        }
        userType = (UserType) typeComboBox.getSelectedItem();
        UserService userService = new UserService();
        ResponseData data = userService.login(logName, logPwd, userType.getCode());
        if(data.getCode()==1){//登陆成功！
            nowUser = (User) data.getData();
            JOptionPane.showMessageDialog(this, "欢迎【"+userType.getName()+"】："+nowUser.getLogName()+"登录本系统！");
            this.dispose();//隐藏当前层
            new MainView(userType,nowUser);
        }else{
            JOptionPane.showMessageDialog(this,data.getMsg());
        }
    }

    public static void main(String[] args) {
        try {
            org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(LoginView::new);
    }

}
