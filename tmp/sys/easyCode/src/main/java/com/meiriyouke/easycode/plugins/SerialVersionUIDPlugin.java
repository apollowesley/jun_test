package com.meiriyouke.easycode.plugins;

import com.meiriyouke.easycode.config.Table;
import com.meiriyouke.easycode.config.Task;
import org.apache.velocity.VelocityContext;

/**
 * serialVersionUID插件
 * 
 * User: liyd
 * Date: 13-12-24
 * Time: 下午8:14
 */
public class SerialVersionUIDPlugin implements EasyCodePlugin {

    /**
     * 插件执行方法
     *
     * @param table        表配置信息
     * @param task         任务配置信息
     * @param templateText 模板内容
     * @param context      velocity 上下文
     */
    @Override
    public void execute(Table table, Task task, StringBuilder templateText, VelocityContext context) {



    }
}
