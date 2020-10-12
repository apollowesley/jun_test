package com.rann.offer.problem6;

import java.util.Arrays;

import com.rann.offer.node.BTreeNode;

/**
 * Problem6
 * 根据二叉树前序和中序重建二叉树
 *
 * @author lemonjing
 */
public class ConstructBTree {
    /**
     * 输入二叉树的前序遍历和中序遍历结果,重建二叉树并输出头节点
     * e.g.12473568,47215386
     */
    public BTreeNode construct(int[] preorder, int[] inorder) throws Exception {

        if (null == preorder || null == inorder || preorder.length <= 0 || inorder.length <= 0) {
            return null;
        }

        if (preorder.length != inorder.length) {
            throw new Exception("length is not equal, error input");
        }

        boolean flag = false;
        BTreeNode root = new BTreeNode(-1);
        for (int i = 0; i < preorder.length; i++) {
            if (inorder[i] == preorder[0]) {
                flag = true;
                root.data = inorder[i];
                root.leftChild = construct(Arrays.copyOfRange(preorder, 1, i + 1)
                        , Arrays.copyOfRange(inorder, 0, i));
                root.rightChild = construct(Arrays.copyOfRange(preorder, i + 1, preorder.length)
                        , Arrays.copyOfRange(inorder, i + 1, inorder.length));
            }
        }

        if (!flag) {
            throw new Exception("no root node, error input");
        }

        return root;
    }
}
