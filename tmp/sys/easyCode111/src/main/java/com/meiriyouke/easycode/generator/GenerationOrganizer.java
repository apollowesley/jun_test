package com.meiriyouke.easycode.generator;

import java.util.List;
import java.util.Map;
import java.util.Queue;

import com.meiriyouke.easycode.utils.DBUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.velocity.VelocityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.meiriyouke.easycode.config.Column;
import com.meiriyouke.easycode.config.Table;
import com.meiriyouke.easycode.config.Task;
import com.meiriyouke.easycode.context.EasyCodeContext;
import com.meiriyouke.easycode.database.DatabaseProvider;
import com.meiriyouke.easycode.database.DatabaseProviderFactory;
import com.meiriyouke.easycode.utils.VelocityUtils;

/**
 * 代码生成业务组织者
 * 
 * User: liyd
 * Date: 13-12-6
 * Time: 下午4:01
 */
public class GenerationOrganizer {

    /** 日志对象 */
    private static final Logger LOG = LoggerFactory.getLogger(GenerationOrganizer.class);

    /**
     * 代码生成
     */
    public void codeGenerate() {

        //所有表配置信息
        Map<String, Table> tableMap = EasyCodeContext.getAllTable();

        //所有的任务配置信息
        Map<String, Task> tasks = EasyCodeContext.getAllTask();

        DBUtils.createConnection();
        for (Map.Entry<String, Table> entry : tableMap.entrySet()) {

            Table table = entry.getValue();
            if (CollectionUtils.isEmpty(table.getColumns())) {
                DatabaseProvider provider = DatabaseProviderFactory.buildProvider();
                List<Column> columnList = provider.getTableMetaData(table.getName());
                table.setColumns(columnList);
            }

            //获取包含了所有常量的velocityContext
            VelocityContext context = VelocityUtils.getVelocityContext();

            //表的任务
            Queue<String> tableTasks = table.getTasks();

            String tableTask;
            while ((tableTask = tableTasks.poll()) != null) {
                Task task = tasks.get(tableTask);
                if (task == null) {
                    LOG.info("不存在配置任务{}", tableTask);
                    continue;
                }
                EasyCodeGenerator codeGenerator = task.getClassInstance();
                codeGenerator.doGenerate(table, task, context);
            }

        }
        DBUtils.closeConnection();

    }

}
