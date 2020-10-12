package com.slavic.vesna.leet.traverse;

import com.slavic.vesna.leet.base.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Tree {

    public List<List<Integer>> levelOrder(TreeNode root) {

        List<List<Integer>> res = new ArrayList<>();
        levelOrder(root, res, 0);
        return res;
    }

    public void levelOrder(TreeNode root, List<List<Integer>> res, int level) {
        if (null == root) return;
        List<Integer> integers;
        if (res.size() <= level) {
            integers = new ArrayList<>();
            integers.add(root.val);
            res.add(level, integers);
        } else {
            integers = res.get(level);
            integers.add(root.val);
        }
        levelOrder(root.left, res, level + 1);
        levelOrder(root.right, res, level + 1);
    }


    public List<List<Integer>> levelOrder2(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (null == root) return res;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> resItem = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.remove();
                resItem.add(node.val);
                if (null != node.left) queue.add(node.left);
                if (null != node.right) queue.add(node.right);
            }
            res.add(resItem);
        }
        return res;
    }

}
