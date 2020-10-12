package com.slavic.vesna.leet.leet;

public class Leet1221 {

    public int balancedStringSplit(String s) {
        if (null == s || s.length() == 0) return 0;
        int count = 0, num = 0;
        for (int i = 0; i < s.length() - 1; i++) {
            if (s.charAt(i) == 'L') num++;
            if (s.charAt(i) == 'R') num--;
            if (num == 0) count++;
        }
        return count;
    }

}
