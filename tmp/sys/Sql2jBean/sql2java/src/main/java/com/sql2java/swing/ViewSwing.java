/**
 * 
 */
package com.sql2java.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.sql2java.jdbc.JdbcUtils;
import com.sql2java.sqlbean.CreateBean;

/**
 * @author Viken
 * 
 */
public class ViewSwing {
    JFrame jf = new JFrame("sql for javabean");

    JPanel jp = new JPanel();

    JLabel l_name = new JLabel("Sql：");
    JLabel l_package = new JLabel("包名+类名：");
    JLabel radio = new JLabel("工程构建类型:");
    JLabel psLabel = new JLabel("注:包名+类名例：xxx.xxx.Test,请注意工程类型，maven 生成路径 src/main/java 非maven 则为默认src下");

    static JTextArea t_name = new JTextArea(35, 70);
    static JTextField t_package = new JTextField(30);
    JRadioButton radio1 = new JRadioButton("Maven构建", true);
    JRadioButton radio2 = new JRadioButton("普通工程");
    String planets[] = {
            "Maven构建", "普通工程"
    };

    JButton ok = new JButton("生成javabean");
    JButton cancel = new JButton("关闭");
    JComboBox combo = new JComboBox(planets);

    public ViewSwing() {

        t_name.setLineWrap(true);
        t_name.setWrapStyleWord(true);

        jp.add(l_name);
        jp.add(new JScrollPane(t_name));
        jp.add(l_package);
        jp.add(t_package);

        jp.add(combo);

        jp.add(ok);
        jp.add(cancel);
        jp.add(psLabel);

        jf.add(jp);

        // 事件OK按钮监听
        ok.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                boolean selectType = true;
                if ("Maven构建".equals(combo.getSelectedItem())) {
                    selectType = true;
                } else {
                    selectType = false;
                }

                JdbcUtils jdbc = new JdbcUtils();
                CreateBean cb = new CreateBean();
                boolean flag = cb.createJavaBean(jdbc.getConnection(), t_name.getText(), t_package.getText(),
                        selectType);
                if (flag) {
                    JOptionPane.showMessageDialog(null, "SUCCESS");
                } else {
                    JOptionPane.showMessageDialog(null, "ERROR", "提示", JOptionPane.ERROR_MESSAGE);
                }
            }

        });

        // 事件Cancel按钮监听
        cancel.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                System.out.println(combo.getSelectedItem());
                System.exit(0);
            }

        });

        // jf.setLayout(new FlowLayout(FlowLayout.LEFT));
        jf.setVisible(true);
        // jf.setResizable(false);
        jf.setSize(800, 700);
        jf.setDefaultCloseOperation(jf.EXIT_ON_CLOSE);
        jf.setLocation(500, 270);
    }
}
