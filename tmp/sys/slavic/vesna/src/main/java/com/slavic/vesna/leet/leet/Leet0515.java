package com.slavic.vesna.leet.leet;

import com.slavic.vesna.leet.base.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Leet0515 {

    public List<Integer> largestValues(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (null == root) return res;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int size = queue.size(), max = Integer.MIN_VALUE;
            for (int i = 0; i < size; i++) {
                TreeNode poll = queue.remove();
                max = Math.max(poll.val, max);
                if (null != poll.left) queue.add(poll.left);
                if (null != poll.right) queue.add(poll.right);
            }
            res.add(max);
        }

        return res;
    }

}
