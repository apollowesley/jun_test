package fun.oook.c08;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.annotation.WebListener;

/**
 * AppAttrListener
 *
 * @author ZhouYu
 * @version 1.0.0
 * @since 2020/4/11 14:42
 */
@WebListener
public class AppAttrListener implements ServletContextAttributeListener {

    @Override
    public void attributeAdded(ServletContextAttributeEvent event) {
        final String name = event.getName();
        final Object value = event.getValue();
        System.out.println("添加context属性 " + name + ": " + value);
    }

    @Override
    public void attributeRemoved(ServletContextAttributeEvent event) {
        final String name = event.getName();
        final Object value = event.getValue();
        System.out.println("移除context属性 " + name + ": " + value);
    }

    @Override
    public void attributeReplaced(ServletContextAttributeEvent event) {
        final String name = event.getName();
        final Object value = event.getValue();
        final Object source = event.getSource();
        System.out.println(source);
        System.out.println("修改context属性 " + name + ": " + value);
    }
}
