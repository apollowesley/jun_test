package com.rann.offer.problem23;

import java.util.LinkedList;
import java.util.Queue;

import com.rann.offer.node.BTreeNode;

/**
 * 二叉树的层序遍历
 */
public class LevelTraverse {
    public void levelTraverse(BTreeNode root) {
        if (null == root) {
            return;
        }
        Queue<BTreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            BTreeNode node = queue.poll();
            System.out.println(node.data);
            if (null != node.leftChild) {
                queue.offer(node.leftChild);
            }
            if (null != node.rightChild) {
                queue.offer(node.rightChild);
            }
        }
    }
}
