package com.slavic.vesna.leet.leet;

import com.slavic.vesna.leet.base.TreeNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Leet0095 {


    public static void main(String[] args) {

        List<TreeNode> treeNodes = new Leet0095().generateTrees(3);

    }

    public static void permutation(int[] arr, int s, int e) {

        if (s == e) {
            System.out.println(Arrays.toString(arr));
            return;
        }

        for (int i = s; i <= e; i++) {
            int tmp = arr[i];
            arr[i] = arr[s];
            arr[s] = tmp;
            permutation(arr, s + 1, e);
            tmp = arr[i];
            arr[i] = arr[s];
            arr[s] = tmp;
        }
    }

    public List<TreeNode> generateTrees(int n) {

        if (n < 1) return new ArrayList<>();

        return generateTrees(1,n);
    }

    public static List<TreeNode> generateTrees(int s, int e) {

        List<TreeNode> res = new ArrayList<>();
        if (s > e) {
            res.add(null);
            return res;
        }

        for (int i = s; i <= e; i++) {
            List<TreeNode> lefts = generateTrees(s, i - 1);
            List<TreeNode> rights = generateTrees(i + 1, e);

            for (TreeNode left : lefts) {
                for (TreeNode right : rights) {
                    TreeNode c = new TreeNode(i);
                    c.left = left;
                    c.right = right;
                    res.add(c);
                }
            }
        }

        return res;
    }
}
