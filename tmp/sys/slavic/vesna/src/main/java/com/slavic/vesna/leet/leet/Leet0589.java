package com.slavic.vesna.leet.leet;

import com.slavic.vesna.leet.base.Node;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class Leet0589 {

    public List<Integer> preorder(Node root) {

        List<Integer> res = new ArrayList<>();
        preorder(root, res);
        return res;
    }

    public void preorder(Node root, List<Integer> res) {

        if (root == null) return;

        res.add(root.val);

        if (null != root.children && root.children.size() != 0) {
            for (Node child : root.children) {
                preorder(child, res);
            }
        }

    }

    public List<Integer> preorder2(Node root) {

        List<Integer> res = new ArrayList<>();
        Deque<Node> stack = new LinkedList<>();
        stack.offerFirst(root);

        while (!stack.isEmpty()) {
            Node node = stack.removeFirst();
            res.add(node.val);

            if (null != node.children && 0 != node.children.size()) {
                for (int i = node.children.size() - 1; i >= 0; i--) {
                    stack.offerFirst(node.children.get(i));
                }
            }
        }

        return res;
    }

}
