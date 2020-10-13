 package com.autoscript.ui.core.registry;
 
 import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import com.autoscript.ui.core.ToolBarButton;
import com.autoscript.ui.listener.KeyboardListener;
 
 public class ToolBarRegistry
 {
   private static ToolBarRegistry instance;
   private Map bars = new HashMap();
   private List barList = new LinkedList();
 
   public static ToolBarRegistry getInstance()
   {
     if (instance == null) {
       instance = new ToolBarRegistry();
     }
     return instance;
   }
 
   private void update(String key, Object obj) {
     int index = this.barList.indexOf(this.bars.get(key));
     this.barList.remove(index);
     this.bars.remove(key);
     this.bars.put(key, obj);
     this.barList.add(index, this.bars.get(key));
   }
 
   public void registry(String key, ImageIcon image, String tipText, Action actionClass)
   {
     if (this.bars.containsKey(key)) {
       ToolBarButton b = new ToolBarButton(image, actionClass, tipText);
       if (!this.bars.get(key).equals(b))
         update(key, b);
     }
     else {
       ToolBarButton b = new ToolBarButton(image, actionClass, tipText);
       b.setToolTipText(tipText);
       this.bars.put(key, b);
       this.barList.add(b);
     }
   }

   public void registry(String key, ImageIcon image, String tipText, Action actionClass,int shortcutKey,int ctrlKey)
   {
	 ToolBarButton b;
     if (this.bars.containsKey(key)) {
       b = new ToolBarButton(image, actionClass, tipText);
       if (!this.bars.get(key).equals(b))
         update(key, b);
     }
     else {
       b = new ToolBarButton(image, actionClass, tipText);
       b.setToolTipText(tipText);
       this.bars.put(key, b);
       this.barList.add(b);
     }
     //设置快捷键
     b.registerKeyboardAction(new KeyboardListener(actionClass), KeyStroke.getKeyStroke(shortcutKey, ctrlKey), JComponent.WHEN_IN_FOCUSED_WINDOW);  

   }
   
   public Map getToolBarRegistry() {
     return this.bars;
   }
 
   public List getToolBarList() {
     return this.barList;
   }
 
   public int size() {
     return this.bars.size();
   }
 
   public void clear() {
     this.bars.clear();
   }
 }

