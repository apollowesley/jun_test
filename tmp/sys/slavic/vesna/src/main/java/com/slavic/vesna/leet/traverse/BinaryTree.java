package com.slavic.vesna.leet.traverse;

import com.slavic.vesna.leet.base.TreeNode;

import java.util.LinkedList;
import java.util.List;

public class BinaryTree {

    //root left right
    public void preface(TreeNode node) {
        if (null == node) return;
        System.out.println(node.val);
        preface(node.left);
        preface(node.right);
    }

    //left root right
    public void middle(TreeNode node) {
        if (null == node) return;
        middle(node.left);
        System.out.println(node.val);
        middle(node.right);
    }

    //left right root
    public void postorder(TreeNode node) {
        if (null == node) return;
        postorder(node.left);
        postorder(node.right);
        System.out.println(node.val);
    }

    //root left right
    public void prefaceLoop(TreeNode node) {
        if (null == node) return;
        LinkedList<TreeNode> stack = new LinkedList<>();
        stack.offerFirst(node);
        while (!stack.isEmpty()) {
            TreeNode treeNode = stack.removeFirst();
            System.out.println(treeNode.val);
            stack.offerFirst(treeNode.right);
            stack.offerFirst(treeNode.left);
        }
    }

    //left root right
    public void middleLoop(TreeNode node) {
        if (null == node) return;
        LinkedList<TreeNode> stack = new LinkedList<>();
        TreeNode c = node;
        while (!stack.isEmpty() || null != c) {
            while (null != c) {
                stack.offerFirst(c);
                c = c.left;
            }
            TreeNode treeNode = stack.removeFirst();
            System.out.println(treeNode.val);
            c = c.right;
        }
    }

    //root left right
    public void postorderLoop1(TreeNode root) {

        LinkedList<TreeNode> stack = new LinkedList<>();
        TreeNode c = root, visited = null;
        while (null != c || !stack.isEmpty()) {
            while (null != c) {
                stack.offerFirst(c);
                c = c.left;
            }
            c = stack.peek();
            if (c.right != null && c.right != visited) {
                c = c.right;
                stack.offerFirst(c);
                c = c.left;
            } else {
                visited = stack.removeFirst();
                System.out.println(visited.val);
                c = null;
            }
        }

    }

    //root left right ? 前序逆序
    public List<Integer> postorderLoop(TreeNode root) {
        LinkedList<Integer> linkedList = new LinkedList<>();
        if (null == root) return linkedList;
        LinkedList<TreeNode> stack = new LinkedList<>();
        stack.offerFirst(root);
        while (!stack.isEmpty()) {
            TreeNode treeNode = stack.removeFirst();
            linkedList.addFirst(treeNode.val);
            if (null != treeNode.left) stack.offerFirst(treeNode.left);
            if (null != treeNode.right) stack.offerFirst(treeNode.right);
        }
        return linkedList;
    }
}
