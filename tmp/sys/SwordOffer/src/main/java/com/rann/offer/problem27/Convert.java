package com.rann.offer.problem27;

import com.rann.offer.node.BTreeNode;

/**
 * 将二叉搜索树转换为一个排序的双向链表
 * Problem 27
 *
 * @author lemonjing
 */
public class Convert {

    // 不能作为引用传递
    private BTreeNode lastNode = null;

    public BTreeNode convert(BTreeNode root) {
        if (null == root) {
            return null;
        }
        convertNode(root);
        while (null != lastNode && lastNode.leftChild != null) {
            lastNode = lastNode.leftChild;
        }
        return lastNode;
    }

    private void convertNode(BTreeNode current) {
        if (null == current) {
            return;
        }
        if (null != current.leftChild) {
            convertNode(current.leftChild);
        }
        current.leftChild = lastNode;
        if (null != lastNode) {
            lastNode.rightChild = current;
        }
        lastNode = current;
        if (null != current.rightChild) {
            convertNode(current.rightChild);
        }
    }
}
