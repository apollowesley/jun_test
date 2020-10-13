/*     */ package com.autoscript.ui.core.component;
/*     */ 
/*     */ import java.awt.Container;
/*     */ import java.awt.Frame;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import javax.swing.ComboBoxEditor;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import com.autoscript.ui.helper.UIPropertyHelper;
/*     */ 
/*     */ public class FindDialog extends JDialog
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  30 */   JPanel panel1 = new JPanel();
/*     */ 
/*  32 */   JLabel jLabel1 = new JLabel();
/*     */ 
/*  34 */   static JComboBox jComboBox1 = new JComboBox();
/*     */ 
/*  36 */   JButton jButtonOK = new JButton();
/*     */ 
/*  38 */   JButton jButtonClose = new JButton();
/*     */   private int retvalue;
/*     */   private String strFindText;
/*     */ 
/*     */   public FindDialog(Frame frame, String title, boolean modal)
/*     */   {
/*  42 */     super(frame, title, modal);
/*     */     try {
/*  44 */       jbInit();
/*  45 */       pack();
/*  46 */       setSize(410, 200);
/*  47 */       setLocation(310, 200);
/*     */     }
/*     */     catch (Exception ex) {
/*  50 */       ex.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public FindDialog() {
/*  55 */     this(null, "", false);
/*     */   }
/*     */ 
/*     */   private void jbInit() throws Exception {
/*  59 */     this.panel1.setLayout(null);
/*  60 */     this.jLabel1.setRequestFocusEnabled(true);
/*  61 */     this.jLabel1.setText(UIPropertyHelper.getString("dialog.find.findtext"));
/*  62 */     this.jLabel1.setVerticalAlignment(0);
/*  63 */     this.jLabel1.setBounds(new Rectangle(18, 63, 100, 16));
/*  64 */     jComboBox1.setBounds(new Rectangle(126, 60, 213, 22));
/*  65 */     jComboBox1.setEditable(true);
/*  66 */     this.jButtonOK.setBounds(new Rectangle(84, 127, 73, 25));
/*  67 */     this.jButtonOK.setActionCommand("jButton1");
/*  68 */     this.jButtonOK.setText(UIPropertyHelper.getString("dialog.find.findbutton"));
/*  69 */     this.jButtonClose.setBounds(new Rectangle(191, 128, 73, 25));
/*  70 */     this.jButtonClose.setText(UIPropertyHelper.getString("dialog.find.closebutton"));
/*  71 */     this.panel1.add(this.jLabel1, null);
/*  72 */     this.panel1.add(jComboBox1, null);
/*  73 */     this.panel1.add(this.jButtonClose, null);
/*  74 */     this.panel1.add(this.jButtonOK, null);
/*  75 */     getContentPane().add(this.panel1, "Center");
/*  76 */     this.jButtonOK.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent e) {
/*  79 */         FindDialog.this.retvalue = 0;
/*     */ 
/*  81 */         FindDialog.this.strFindText = ((String)FindDialog.jComboBox1.getEditor().getItem());
/*  82 */         FindDialog.this.AddFindTextToComboBox(FindDialog.this.strFindText);
/*  83 */         FindDialog.this.dispose();
/*     */       }
/*     */     });
/*  88 */     this.jButtonClose.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent e) {
/*  91 */         FindDialog.this.retvalue = -1;
/*  92 */         FindDialog.this.dispose();
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public int getRetvalue()
/*     */   {
/* 105 */     return this.retvalue;
/*     */   }
/*     */ 
/*     */   public String getFindText()
/*     */   {
/* 112 */     return this.strFindText;
/*     */   }
/*     */ 
/*     */   private void AddFindTextToComboBox(String strValue)
/*     */   {
/* 121 */     if ((strValue == null) || (strValue.trim().equals(""))) return;
/* 122 */     for (int i = 0; i < jComboBox1.getItemCount(); i++) {
/* 123 */       String cmpValue = (String)jComboBox1.getItemAt(i);
/* 124 */       if ((cmpValue != null) && 
/* 125 */         (cmpValue.trim().equals(strValue.trim()))) return;
/*     */     }
/* 127 */     jComboBox1.addItem(strValue);
/*     */   }
/*     */ }

/* Location:           H:\笔记本备份\d盘\FmisBatchUI\lib\plantix-batch-ui.jar
 * Qualified Name:     com.autoscript.ui.core.component.FindDialog
 * JD-Core Version:    0.6.0
 */