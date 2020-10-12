package com.slavic.vesna.leet.leet;

import com.slavic.vesna.leet.base.TreeNode;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class Leet0144 {

    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        preorderTraversal1(root,res);
        return res;
    }

    public void preorderTraversal1(TreeNode root, List<Integer> res) {
        if (null == root) return;
        res.add(root.val);
        if (null != root.left) preorderTraversal1(root.left,res);
        if (null != root.right) preorderTraversal1(root.right,res);
    }


    public List<Integer> preorderTraversal2(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) return res;
        Deque<TreeNode> stack = new LinkedList<>();
        stack.offerFirst(root);

        while (!stack.isEmpty()) {
            TreeNode node = stack.removeFirst();
            res.add(node.val);
            if (null != node.right) stack.offerFirst(node.right);
            if (null != node.left) stack.offerFirst(node.left);
        }

        return res;
    }


}
