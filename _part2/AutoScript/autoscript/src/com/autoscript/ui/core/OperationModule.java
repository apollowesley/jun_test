/*    */ package com.autoscript.ui.core;
/*    */ 
/*    */ import java.awt.BorderLayout;
/*    */ import java.awt.Container;
/*    */ import java.awt.Dimension;
/*    */ import java.awt.event.ComponentEvent;
/*    */ import java.awt.event.ComponentListener;
/*    */ import javax.swing.JFrame;
/*    */ import javax.swing.JPanel;
/*    */ import javax.swing.border.Border;
/*    */ import javax.swing.border.CompoundBorder;
/*    */ import javax.swing.border.EmptyBorder;
/*    */ import javax.swing.border.SoftBevelBorder;
/*    */ import com.autoscript.ui.helper.UIPropertyHelper;
/*    */ 
/*    */ public class OperationModule extends JPanel
/*    */   implements ComponentListener
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private int PREFERRED_WIDTH;
/*    */   private int PREFERRED_HEIGHT;
/*    */   Border loweredBorder;
/*    */   private BatchUI ui;
/*    */   private JPanel panel;
/*    */   private String moduleName;
/*    */ 
/*    */   public OperationModule(BatchUI ui)
/*    */   {
/* 32 */     this(ui, null);
/*    */   }
/*    */ 
/*    */   public OperationModule(BatchUI ui, String moduleName) {
/* 36 */     this.PREFERRED_WIDTH = 680;
/* 37 */     this.PREFERRED_HEIGHT = 600;
/* 38 */     this.loweredBorder = new CompoundBorder(new SoftBevelBorder(1), new EmptyBorder(5, 5, 5, 5));
/* 39 */     this.ui = null;
/* 40 */     this.panel = null;
/* 41 */     this.moduleName = null;
/* 42 */     this.panel = new JPanel();
/* 43 */     this.panel.setLayout(new BorderLayout());
/* 44 */     setLayout(new BorderLayout());
/* 45 */     Border border = new CompoundBorder(new SoftBevelBorder(1), new EmptyBorder(5, 5, 5, 5));
/* 46 */     setBorder(border);
/* 47 */     this.moduleName = moduleName;
/* 48 */     this.ui = ui;
/* 49 */     addComponentListener(this);
/*    */   }
/*    */ 
/*    */   public String getModuleName() {
/* 53 */     return this.moduleName;
/*    */   }
/*    */ 
/*    */   public JPanel getModulePanel() {
/* 57 */     return this.panel;
/*    */   }
/*    */ 
/*    */   public BatchUI getBatchUI() {
/* 61 */     return this.ui;
/*    */   }
/*    */ 
/*    */   public String getString(String key) {
/* 65 */     return UIPropertyHelper.getString(key);
/*    */   }
/*    */ 
/*    */   public void mainImpl() {
/* 69 */     JFrame frame = new JFrame(getName());
/* 70 */     frame.getContentPane().setLayout(new BorderLayout());
/* 71 */     frame.getContentPane().add(getModulePanel(), "Center");
/* 72 */     getModulePanel().setPreferredSize(new Dimension(this.PREFERRED_WIDTH, this.PREFERRED_HEIGHT));
/* 73 */     frame.pack();
/* 74 */     frame.show();
/*    */   }
/*    */   public void componentHidden(ComponentEvent e) {
/*    */   }
/*    */   public void componentMoved(ComponentEvent e) {
/*    */   }
/*    */   public void componentResized(ComponentEvent e) {
/*    */   }
/*    */ 
/*    */   public void componentShown(ComponentEvent e) {
/* 84 */     String newTitle = getString("frame.title") + " - " + getModuleName();
/* 85 */     getBatchUI().getFrame().setTitle(newTitle);
/*    */   }
/*    */   public void setModuleName(String value) {
/* 88 */     this.moduleName = value;
/*    */   }
/*    */ }

/* Location:           H:\笔记本备份\d盘\FmisBatchUI\lib\plantix-batch-ui.jar
 * Qualified Name:     com.autoscript.ui.core.OperationModule
 * JD-Core Version:    0.6.0
 */