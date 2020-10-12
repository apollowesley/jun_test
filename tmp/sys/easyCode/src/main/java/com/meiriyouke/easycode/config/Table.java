package com.meiriyouke.easycode.config;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 表的生成配置信息
 *
 * User: liyd
 * Date: 13-11-20
 * Time: 下午6:25
 */
public class Table {

    /** 表名 */
    private String        name;

    /** 表说明 */
    private String        desc;

    /** 包含的列 */
    private List<Column>  columns;

    /** 拥有的任务 */
    private Queue<String> tasks;

    /**
     * 添加白名单任务
     * 
     * @param task
     */
    public void addTask(String task) {
        if (tasks == null) {
            tasks = new LinkedList<String>();
        }
        tasks.offer(task);
    }

    /**
     * 添加列
     * 
     * @param column
     */
    public void addColumn(Column column) {
        if (columns == null) {
            columns = new ArrayList<Column>();
        }
        columns.add(column);
    }

    public Queue<String> getTasks() {
        return tasks;
    }

    public void setTasks(Queue<String> tasks) {
        this.tasks = tasks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
