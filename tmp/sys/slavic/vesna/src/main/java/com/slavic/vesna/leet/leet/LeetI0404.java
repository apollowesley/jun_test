package com.slavic.vesna.leet.leet;

import com.slavic.vesna.leet.base.TreeNode;

public class LeetI0404 {

    public boolean isBalanced(TreeNode root) {
        if (root == null) return true;
        if (Math.abs(getDeep(root.left, 0) - getDeep(root.right, 0)) > 1) return false;
        return isBalanced(root.right) && isBalanced(root.left);
    }

    public int getDeep(TreeNode root, int deep) {
        if (root == null) return deep;
        return Math.max(getDeep(root.left, deep + 1), getDeep(root.right, deep + 1));
    }

}
