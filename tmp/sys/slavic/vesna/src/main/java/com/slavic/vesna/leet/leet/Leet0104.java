package com.slavic.vesna.leet.leet;

import com.slavic.vesna.leet.base.TreeNode;

import java.util.Deque;
import java.util.LinkedList;

public class Leet0104 {

    //栈
    public int maxDepth2(TreeNode root) {



        return 0;
    }

    //递归
    public int maxDepth(TreeNode root) {
        return maxDepth1(root,0);
    }

    public int maxDepth1(TreeNode root, int max) {
        if (root == null) return max;
        return Math.max(maxDepth1(root.left, max + 1), maxDepth1(root.right, max + 1));
    }

}
