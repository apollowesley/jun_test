package com.slavic.vesna.leet.leet;

import com.slavic.vesna.leet.base.TreeNode;

public class Leet0100 {

    public boolean isSameTree(TreeNode p, TreeNode q) {

        if (p == null && q == null) return true;
        if (p == null || q == null || p.val != q.val) return false;

        return isSameTree(p.left,q.left) && isSameTree(p.right,q.right);
    }

}
