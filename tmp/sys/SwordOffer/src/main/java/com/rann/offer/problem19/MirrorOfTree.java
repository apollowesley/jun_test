package com.rann.offer.problem19;

import java.util.Stack;

import com.rann.offer.node.BTreeNode;

/**
 * problem19
 * 二叉树的镜像
 *
 * @author lemonjing
 */
public class MirrorOfTree {
    public BTreeNode mirror(BTreeNode root) {
        if (null == root || (root.leftChild == null && root.rightChild == null)) {
            return root;
        }
        Stack<BTreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            BTreeNode node = stack.pop();
            // 交换
            BTreeNode temp = node.leftChild;
            node.leftChild = node.rightChild;
            node.rightChild = temp;

            if (node.leftChild != null) {
                stack.push(node.leftChild);
            }
            if (node.rightChild != null) {
                stack.push(node.rightChild);
            }
        }

        return root;
    }

    public BTreeNode mirrorRecursive(BTreeNode root) {
        if (null == root || (root.leftChild == null && root.rightChild == null)) {
            return null;
        }
        // 交换
        BTreeNode temp = root.leftChild;
        root.leftChild = root.rightChild;
        root.rightChild = temp;


        mirrorRecursive(root.leftChild);
        mirrorRecursive(root.rightChild);

        return root;
    }
}
