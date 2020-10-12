package com.rann.offer.problem25;

import java.util.*;
import com.rann.offer.node.BTreeNode;

/**
 * 输入一个二叉树和一个整数，打印出二叉树中节点值的和为输入整数的所有路径
 * 如
 *       10
 *      /  \
 *     5   12
 *    / \
 *   4  7
 *  和为22的路径[[10,5,7],[10,12]]
 */
public class FindPath {
	public List<List<Integer>> findPath(BTreeNode root, int target) {
        List<List<Integer>> list = new ArrayList<>();
        if (null == root) {
            return list;
        }
        Stack<Integer> stack = new Stack<>();
        generate(root, target, stack, list);

        return list;
    }

    private void generate(BTreeNode root, int target, Stack<Integer> stack, List<List<Integer>> list) {
        if (null == root) {
            return;
        }
        if (null == root.leftChild && null == root.rightChild) {
            if (root.data == target) {
                ArrayList<Integer> tmpList = new ArrayList<>(stack);
                tmpList.add(root.data);
                list.add(tmpList);
            }
        } else {
            stack.push(root.data);
            generate(root.leftChild, target - root.data, stack, list);
            generate(root.rightChild, target - root.data, stack, list);
            stack.pop();
        }
    }
}
