package com.slavic.vesna.leet.leet;

import java.util.ArrayList;
import java.util.List;

public class Leet0022 {

    public List<String> generateParenthesis(int n) {
        List<String> res = new ArrayList<>();
        generateParenthesis(0, 0, n, "", res);
        return res;
    }

    public void generateParenthesis(int l, int r, int n, String s, List<String> res) {
        if (l == n && n == r) res.add(s);
        if (l < n) generateParenthesis(l + 1, r, n, s + "(", res);
        if (r < l) generateParenthesis(l, r + 1, n, s + ")", res);
    }

}
