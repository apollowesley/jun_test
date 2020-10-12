/**
 * Copyright 2017 KiWiPeach
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package cn.kiwipeach.design.structure.composite;

import java.util.LinkedList;
import java.util.List;

/**
 * Create Date: 2017/11/19
 * Description: 属性节点
 *
 * @author kiwipeach [1099501218@qq.com]
 */
public class TreeNode implements TreeNodeOperation {
    private String name;
    List<TreeNode> sonList = new LinkedList<TreeNode>();

    public TreeNode() {
    }

    public TreeNode(String name) {
        this.name = name;
    }

    @Override
    public boolean addNode(TreeNode treeNode) {
        return sonList.add(treeNode);
    }

    @Override
    public boolean removeNode(TreeNode treeNode) {
        return sonList.remove(treeNode);
    }

    @Override
    public void showNodeTree() {
    }
}
