package com.autoscript.ui.ui;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class KBPanel extends JPanel {
	private JTextField textField;

	/**
	 * Create the panel.
	 */
	public KBPanel() {
		setLayout(new GridLayout(1, 7, 0, 0));
		
		JLabel lblNewLabel = new JLabel("类型:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblNewLabel);
		
		JComboBox typeComboBox = new JComboBox();
		typeComboBox.setMaximumRowCount(1);
		add(typeComboBox);
		
		JLabel lblNewLabel_1 = new JLabel("关键字/函数：");
		add(lblNewLabel_1);
		
		JComboBox keyFunComboBox = new JComboBox();
		keyFunComboBox.setEditable(true);
		add(keyFunComboBox);
		
		JLabel lblNewLabel_2 = new JLabel("语法:");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblNewLabel_2);
		
		textField = new JTextField();
		textField.setEditable(false);
		add(textField);
		textField.setColumns(30);
		
		JLabel describeLabel = new JLabel("#####");
		describeLabel.setToolTipText("描述");
		add(describeLabel);
		
		JButton btnNewButton = new JButton("插入");
		add(btnNewButton);

	}

}
