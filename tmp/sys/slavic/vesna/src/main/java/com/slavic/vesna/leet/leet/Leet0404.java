package com.slavic.vesna.leet.leet;

import com.slavic.vesna.leet.base.TreeNode;

import java.util.LinkedList;

public class Leet0404 {

    public int sumOfLeftLeaves(TreeNode root) {

        int res = 0;
        if (null == root) return res;
        LinkedList<TreeNode> stack = new LinkedList<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.poll();
            if (node.left != null) {
                stack.push(node.left);
                if (node.left.left == null && node.left.right == null) res += node.left.val;
            }
            if (null != node.right) stack.push(node.right);
        }

        return res;
    }

}
