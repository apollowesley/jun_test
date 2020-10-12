package com.rann.offer.problem39;

import com.rann.offer.node.BTreeNode;

/**
 * Created by rann on 16/3/28.
 */
public class BTreeUtil {
    /*
     * 1.输入根节点求树的深度
     */
    public int getDepth(BTreeNode root) {
        if (null == root) {
            return 0;
        }
        int left = getDepth(root.leftChild);
        int right = getDepth(root.rightChild);

        return (left > right) ? (left + 1) : (right + 1);
    }

    /*
     * 2.判断二叉树是不是平衡二叉树，如果二叉树任意节点的左右子树深度相差不超过1，就是平衡的
     */
    public boolean isBalanced(BTreeNode root) {
        if (null == root) {
            return true;
        }

        int left = getDepth(root.leftChild);
        int right = getDepth(root.rightChild);
        int diff = left - right;
        if (diff > 1 || diff < -1) {
            return false;
        }

        return isBalanced(root.leftChild) && isBalanced(root.rightChild);
    }

}
