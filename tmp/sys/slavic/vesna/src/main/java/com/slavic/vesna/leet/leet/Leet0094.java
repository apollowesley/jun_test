package com.slavic.vesna.leet.leet;

import com.slavic.vesna.leet.base.TreeNode;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class Leet0094 {

    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        inorderTraversal1(root, res);
        return res;
    }

    public void inorderTraversal1(TreeNode parent, List<Integer> res) {

        if (parent == null) return;
        if (null != parent.left) inorderTraversal1(parent.left, res);
        res.add(parent.val);
        if (null != parent.right) inorderTraversal1(parent.right, res);
    }

    public List<Integer> inorderTraversal2(TreeNode root) {

        Deque<TreeNode> stack = new LinkedList<>();
        List<Integer> res = new ArrayList<>();
        if (null == root) return res;
        TreeNode c = root;
        while (c != null || !stack.isEmpty()) {
            while (null != c) {
                stack.offerFirst(c);
                c = c.left;
            }
            TreeNode node = stack.removeFirst();
            res.add(node.val);
            c = node.right;
        }

        return res;
    }

}
