package com.autoscript.ui.jdk;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

public class ProgressFrame extends JFrame implements ActionListener {

	private JButton btn = new JButton("Start");
	private int i=0;
	private JProgressBar bar = new JProgressBar() {
		public void paint(Graphics g) {
			super.paint(g);
			System.out.println("paint");
		}
	};

	public ProgressFrame() {
		init();
	}

	private void init() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 200);
		setLocationRelativeTo(null);
		setLayout(new FlowLayout());
		add(btn);
		add(bar);
		btn.addActionListener(this);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Runnable runnable = new Runnable() {

			 

			@Override
			public void run() {
			for (i = 0; i <= 10; i++) {
			try {
			Thread.sleep(200);
			} catch (InterruptedException e1) {
			e1.printStackTrace();
			}
//			SwingUtilities.invokeLater(new Runnable() {
//				
//			@Override
//			public void run() {
//				System.out.println("SwingUtilities.invokeLater");
//			bar.setValue(i * 10);
//			}
//			});

			}
			}
			};
			
			
			Runnable runnable1 = new Runnable() {

				@Override
				public void run() {
					while(i<11){
						SwingUtilities.invokeLater(new Runnable() {
							
							@Override
							public void run() {
								System.out.println("SwingUtilities.invokeLater");
							bar.setValue(i * 10);
							}
							});
						try {
							Thread.sleep(200);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
				}
				
			};
			new Thread(runnable1).start();
			new Thread(runnable).start();
	}
	

	public static void main(String[] args) {
		new ProgressFrame();
	}

}