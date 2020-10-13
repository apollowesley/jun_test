/*    */ package com.autoscript.ui;
/*    */ 
/*    */ import java.awt.Font;
/*    */ import javax.swing.UIManager;
/*    */ 
/*    */ public class BatchUIManager
/*    */ {
/*    */   public BatchUIManager()
/*    */   {
/*    */     try
/*    */     {
///* 11 */       UIManager.setLookAndFeel("com.jgoodies.looks.plastic.PlasticXPLookAndFeel");
///* 12 */       Font _font = new Font("新宋体", 0, 12);
///* 13 */       UIManager.put("Button.font", _font);
///* 14 */       UIManager.put("ToggleButton.font", _font);
///* 15 */       UIManager.put("RadioButton.font", _font);
///* 16 */       UIManager.put("CheckBox.font", _font);
///* 17 */       UIManager.put("ColorChooser.font", _font);
///* 18 */       UIManager.put("ToggleButton.font", _font);
///* 19 */       UIManager.put("ComboBox.font", _font);
///* 20 */       UIManager.put("ComboBoxItem.font", _font);
///* 21 */       UIManager.put("InternalFrame.titleFont", _font);
///* 22 */       UIManager.put("Label.font", _font);
///* 23 */       UIManager.put("List.font", _font);
///* 24 */       UIManager.put("MenuBar.font", _font);
///* 25 */       UIManager.put("Menu.font", _font);
///* 26 */       UIManager.put("MenuItem.font", _font);
///* 27 */       UIManager.put("RadioButtonMenuItem.font", _font);
///* 28 */       UIManager.put("CheckBoxMenuItem.font", _font);
///* 29 */       UIManager.put("PopupMenu.font", _font);
///* 30 */       UIManager.put("OptionPane.font", _font);
///* 31 */       UIManager.put("Panel.font", _font);
///* 32 */       UIManager.put("ProgressBar.font", _font);
///* 33 */       UIManager.put("ScrollPane.font", _font);
///* 34 */       UIManager.put("Viewport", _font);
///* 35 */       UIManager.put("TabbedPane.font", _font);
///* 36 */       UIManager.put("TableHeader.font", _font);
///* 37 */       UIManager.put("TextField.font", _font);
///* 38 */       UIManager.put("PasswordFiled.font", _font);
///* 39 */       UIManager.put("TextArea.font", _font);
///* 40 */       UIManager.put("TextPane.font", _font);
///* 41 */       UIManager.put("EditorPane.font", _font);
///* 42 */       UIManager.put("TitledBorder.font", _font);
///* 43 */       UIManager.put("ToolBar.font", _font);
///* 44 */       UIManager.put("ToolTip.font", _font);
///* 45 */       UIManager.put("Tree.font", _font);
///* 46 */       UIManager.put("Table.font", _font);
	         UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
/*    */     } catch (Exception e) {
/* 48 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */ }

/* Location:           H:\笔记本备份\d盘\FmisBatchUI\lib\plantix-batch-ui.jar
 * Qualified Name:     com.autoscript.ui.BatchUIManager
 * JD-Core Version:    0.6.0
 */