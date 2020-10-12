package com.slavic.vesna.leet.leet;

import java.util.Arrays;

public class Leet0014 {

    public String longestCommonPrefix(String[] strs) {

        if (strs.length == 0) return "";
        StringBuilder res = new StringBuilder();
        int min = Arrays.stream(strs).mapToInt(String::length).min().getAsInt();
        for (int i = 0; i < min; i++) {
            for (int i1 = 1; i1 < strs.length; i1++) if (strs[i1].charAt(i) != strs[0].charAt(i)) return res.toString();
            res.append(strs[0].charAt(i));
        }
        return res.toString();
    }

}
