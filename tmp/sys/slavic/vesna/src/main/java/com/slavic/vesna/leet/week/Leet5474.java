package com.slavic.vesna.leet.week;

import com.slavic.vesna.leet.base.TreeNode;

public class Leet5474 {

    public int countPairs(TreeNode root, int distance) {


        return 0;
    }

    public void countPairs1(TreeNode root, int distance) {

        if (root == null) return;

        if (root.left != null) {
            countPairs1(root.left, distance);
        }


        if (root.right != null) {
            countPairs1(root.right, distance + 2);
        }
    }
}
