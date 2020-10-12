package com.meiriyouke.easycode.generator;

import org.apache.velocity.VelocityContext;

import com.meiriyouke.easycode.config.Table;
import com.meiriyouke.easycode.config.Task;

/**
 * 代码生成接口
 *
 * User: liyd
 * Date: 13-11-28
 * Time: 下午5:35
 */
public interface EasyCodeGenerator {

    /**
     * 代码生成方法
     * @param table the table
     * @param task the task
     * @param context the context             
     */
    public void doGenerate(Table table, Task task, VelocityContext context);

}
