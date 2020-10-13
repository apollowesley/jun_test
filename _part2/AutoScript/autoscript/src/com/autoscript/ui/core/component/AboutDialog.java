/*    */ package com.autoscript.ui.core.component;
/*    */ 
/*    */ import java.awt.Container;
/*    */ import java.awt.Dimension;
/*    */ import java.awt.Font;
/*    */ import java.awt.Frame;
/*    */ import java.awt.Rectangle;
/*    */ import javax.swing.JDialog;
/*    */ import javax.swing.JLabel;
/*    */ import javax.swing.JPanel;
/*    */ import com.autoscript.ui.helper.ImageHelper;
/*    */ import com.autoscript.ui.helper.UIPropertyHelper;
/*    */ 
/*    */ public class AboutDialog extends JDialog
/*    */ {
/*    */   private static final long serialVersionUID = 5274366309147424464L;
/* 17 */   JPanel panel1 = new JPanel();
/*    */ 
/* 19 */   JLabel jLabel1 = new JLabel();
/*    */ 
/* 21 */   JLabel jLabel2 = new JLabel();
/*    */ 
/* 23 */   JLabel jLabel3 = new JLabel();
/*    */ 
/*    */   public AboutDialog(Frame frame, String title, boolean modal) {
/* 26 */     super(frame, title, modal);
/*    */     try {
/* 28 */       jbInit();
/* 29 */       pack();
/* 30 */       setSize(410, 140);
/* 31 */       setLocation(310, 200);
/*    */     } catch (Exception ex) {
/* 33 */       ex.printStackTrace();
/*    */     }
/*    */   }
/*    */ 
/*    */   public AboutDialog() {
/* 38 */     this(null, "", false);
/*    */   }
/*    */ 
/*    */   private void jbInit() throws Exception {
/* 42 */     this.panel1.setLayout(null);
/* 43 */     this.jLabel1.setIconTextGap(4);
/* 44 */     ImageHelper image = new ImageHelper();
/* 45 */     this.jLabel1.setIcon(image.APP_ICON);
/* 46 */     this.jLabel1.setBounds(new Rectangle(9, 8, 80, 98));
/* 47 */     this.jLabel2.setFont(new Font("Dialog", 1, 12));
/* 48 */     this.jLabel2.setPreferredSize(new Dimension(91, 16));
/* 49 */     this.jLabel2.setRequestFocusEnabled(true);
/* 50 */     this.jLabel2.setToolTipText("");
/* 51 */     this.jLabel2.setText("AutoScript 5.05");
/* 52 */     this.jLabel2.setBounds(new Rectangle(110, 28, 292, 30));
/* 53 */     this.jLabel3.setFont(new Font("Dialog", 1, 12));
/* 54 */     this.jLabel3.setRequestFocusEnabled(true);
/* 56 */     this.jLabel3.setBounds(new Rectangle(110, 58, 279, 36));
	         this.jLabel3.setText("基于FreeMarker技术");
/* 57 */     getContentPane().add(this.panel1);
/* 58 */     this.panel1.add(this.jLabel2, null);
/* 59 */     this.panel1.add(this.jLabel1, null);
/* 60 */     this.panel1.add(this.jLabel3, null);
/*    */   }
/*    */ }

/* Location:           H:\笔记本备份\d盘\FmisBatchUI\lib\plantix-batch-ui.jar
 * Qualified Name:     com.autoscript.ui.core.component.AboutDialog
 * JD-Core Version:    0.6.0
 */