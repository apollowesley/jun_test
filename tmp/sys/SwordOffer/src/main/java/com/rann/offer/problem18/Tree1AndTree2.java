package com.rann.offer.problem18;

import com.rann.offer.node.BTreeNode;

/**
 * problem18 树的子结构
 *
 * @author lemonjing
 */
public class Tree1AndTree2 {

    /**
     * @param root1 主树
     * @param root2 目标树
     * @return
     */
    public boolean HasSubtree(BTreeNode root1, BTreeNode root2) {
        if (root1 == null || root2 == null) return false;
        boolean flag = false;
        flag = isSubTree(root1, root2);
        if (!flag) {
            flag = isSubTree(root1.leftChild, root2) || isSubTree(root1.rightChild, root2);
        }
        return flag;
    }

    private boolean isSubTree(BTreeNode root1, BTreeNode root2) {
        if (root2 == null) return true;
        if (root1 == null) return false;
        if (root1.data != root2.data) return false;
        return isSubTree(root1.leftChild, root2.leftChild) && isSubTree(root1.rightChild, root2.rightChild);
    }
}