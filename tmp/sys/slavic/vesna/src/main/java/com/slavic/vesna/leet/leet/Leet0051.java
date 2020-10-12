package com.slavic.vesna.leet.leet;

import java.util.*;
import java.util.stream.Collectors;

public class Leet0051 {

    public static void main(String[] args) {
        System.out.println(new Leet0051().solveNQueens(4));
    }

    Set<Integer> cols = new HashSet<>(), xySub = new HashSet<>(), xyAdd = new HashSet<>();

    public List<List<String>> solveNQueens(int n) {
        Map<Integer, String> kv = new HashMap<>();
        for (int i = 0; i < n; i++) {
            StringBuilder itemStr = new StringBuilder();
            for (int i1 = 0; i1 < n; i1++) itemStr.append(i1 == i ? "Q" : ".");
            kv.put(i, itemStr.toString());
        }

        List<int[]> res = new ArrayList<>();
        solveNQueens(0, n, new int[n], res);
        return res.stream().map(re -> Arrays.stream(re).mapToObj(kv::get).collect(Collectors.toList())).collect(Collectors.toList());
    }

    public void solveNQueens(int row, int n, int[] cr, List<int[]> res) {
        if (row == n) {
            res.add(Arrays.copyOf(cr, row));
            return;
        }
        for (int col = 0, add = row + col, sub = row - col; col < n; col++, add++, sub--) {
            if (xyAdd.contains(add) || xySub.contains(sub) || cols.contains(col)) continue;
            xyAdd.add(add);
            xySub.add(sub);
            cols.add(col);
            cr[row] = col;
            solveNQueens(row + 1, n, cr, res);
            xyAdd.remove(add);
            xySub.remove(sub);
            cols.remove(col);
        }
    }
}
