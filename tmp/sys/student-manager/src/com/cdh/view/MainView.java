package com.cdh.view;

import com.cdh.model.User;
import com.cdh.model.UserType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URI;

public class MainView extends JFrame {

    private ImageIcon icon;
    private UserType userType;
    private User nowUser;

    public MainView(UserType userType,User nowUser){
        this.userType = userType;
        this.nowUser = nowUser;

        this.setTitle("学生管理系统");
        ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("/images/logo.png"));
        setIconImage(imageIcon.getImage());
        this.setLayout(null); //绝对定位
        JPanel contextPanel = new JPanel();
        this.setContentPane(contextPanel);
        this.setBounds(100,100,1200,600);
        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);

        pageInfo(menuBar);

        JLabel contextLabel = new JLabel("欢迎您：【"+userType.getName()+"】"+nowUser.getLogName());
        this.add(contextLabel);

        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setVisible(true);

    }

    //管理系统主界面
    private void pageInfo(JMenuBar menuBar) {
        //系统设置
        JMenu sysSettingMenu=new JMenu("系统设置");
        icon = new ImageIcon(MainView.class.getResource("/images/setting.png"));
        icon.setImage(icon.getImage().getScaledInstance(20,20, Image.SCALE_DEFAULT));
        sysSettingMenu.setIcon(icon);

        JMenuItem updatePwdItem = new JMenuItem("修改密码");
        icon = new ImageIcon(MainView.class.getResource("/images/update_pwd.png"));
        icon.setImage(icon.getImage().getScaledInstance(20,20, Image.SCALE_DEFAULT));
        updatePwdItem.setIcon(icon);
        sysSettingMenu.add(updatePwdItem);

        sysSettingMenu.addSeparator(); //分割线

        JMenuItem outSystemItem = new JMenuItem("退出系统");
        icon = new ImageIcon(MainView.class.getResource("/images/out.png"));
        icon.setImage(icon.getImage().getScaledInstance(20,20, Image.SCALE_DEFAULT));
        outSystemItem.setIcon(icon);
        sysSettingMenu.add(outSystemItem);

        menuBar.add(sysSettingMenu);

        //学生管理
        JMenu studentManagerMenu=new JMenu("学生管理");
        icon = new ImageIcon(MainView.class.getResource("/images/student_manager.png"));
        icon.setImage(icon.getImage().getScaledInstance(20,20, Image.SCALE_DEFAULT));
        studentManagerMenu.setIcon(icon);

        JMenuItem addStudentItem = new JMenuItem("添加学生");
        icon = new ImageIcon(MainView.class.getResource("/images/add_student.png"));
        icon.setImage(icon.getImage().getScaledInstance(20,20, Image.SCALE_DEFAULT));
        addStudentItem.setIcon(icon);
        studentManagerMenu.add(addStudentItem);

        studentManagerMenu.addSeparator();//分割线

        JMenuItem studentListItem = new JMenuItem("学生列表");
        icon = new ImageIcon(MainView.class.getResource("/images/list.png"));
        icon.setImage(icon.getImage().getScaledInstance(20,20, Image.SCALE_DEFAULT));
        studentListItem.setIcon(icon);
        studentManagerMenu.add(studentListItem);

        menuBar.add(studentManagerMenu);

        //班级管理
        JMenu clazzManagerMenu=new JMenu("班级管理");
        icon = new ImageIcon(MainView.class.getResource("/images/clazz.png"));
        icon.setImage(icon.getImage().getScaledInstance(20,20, Image.SCALE_DEFAULT));
        clazzManagerMenu.setIcon(icon);

        JMenuItem addClassItem = new JMenuItem("添加班级");
        icon = new ImageIcon(MainView.class.getResource("/images/add_clazz.png"));
        icon.setImage(icon.getImage().getScaledInstance(20,20, Image.SCALE_DEFAULT));
        addClassItem.setIcon(icon);
        clazzManagerMenu.add(addClassItem);

        clazzManagerMenu.addSeparator();//分割线

        JMenuItem classListItem = new JMenuItem("班级列表");
        icon = new ImageIcon(MainView.class.getResource("/images/clazz_list.png"));
        icon.setImage(icon.getImage().getScaledInstance(20,20, Image.SCALE_DEFAULT));
        classListItem.setIcon(icon);
        clazzManagerMenu.add(classListItem);

        menuBar.add(clazzManagerMenu);

        //关于我们
        JMenu aboutMe = new JMenu("关于我们");
        icon = new ImageIcon(MainView.class.getResource("/images/about_me.png"));
        icon.setImage(icon.getImage().getScaledInstance(20,20, Image.SCALE_DEFAULT));
        aboutMe.setIcon(icon);
        menuBar.add(aboutMe);

        //事件监听处理
        //修改密码
        updatePwdItem.addActionListener(this::updatePwdHandler);
        //退出
        outSystemItem.addActionListener(this::outSystemHandler);
        //添加学生
        addStudentItem.addActionListener(e -> new AddStudentView());
        //学生列表
        studentListItem.addActionListener(e -> new StudentListView());
        //添加班级
        addClassItem.addActionListener(e -> new AddClazzView());
        //班级列表
        classListItem.addActionListener(e -> new ClazzListView());
        //关于我们
        aboutMe.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                aboutUsHandler(menuBar);
            }
        });
    }

    //退出
    private void outSystemHandler(ActionEvent event) {
        int result = JOptionPane.showConfirmDialog(this, "确定要退出吗？", "消息提示", JOptionPane.OK_CANCEL_OPTION);
        if(result == JOptionPane.OK_OPTION){
            System.exit(0);
        }
    }

    //修改密码
    private void updatePwdHandler(ActionEvent event){
        new UpdatePwdView(userType,nowUser);
    }

    //关于我们弹出层信息
    private void aboutUsHandler(JMenuBar menuBar) {
        //个性提示框
        String msg = "Java是一门面向对象编程语言，不仅吸收了C++语言的各种优点，\n" +
                     "还摒弃了C++里难以理解的多继承、指针等概念，\n" +
                     "因此Java语言具有功能强大和简单易用两个特征。\n" +
                     "Java语言作为静态面向对象编程语言的代表，极好地实现了面向对象理论，\n" +
                     "允许程序员以优雅的思维方式进行复杂的编程。";
        String[] options = {"去看看","暂时不想去"};
        icon = new ImageIcon(MainView.class.getResource("/images/logo.png"));
        icon.setImage(icon.getImage().getScaledInstance(150,150, Image.SCALE_DEFAULT));
        int result = JOptionPane.showOptionDialog(menuBar, msg, "关于我们",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
                icon,options,null);
        if(result == 0){
            try {
                //采用Java 调用系统浏览器打开制定
                Desktop.getDesktop().browse(new URI("https://www.oracle.com/java/"));
                //Runtime.getRuntime().exec("explorer https://www.oracle.com/java/");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }/*else{
            JOptionPane.showMessageDialog(this, "欢迎下次回来！");
        }*/
    }


    /*public static void main(String[] args) {
        try {
            org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(MainView::new);
    }*/

}
