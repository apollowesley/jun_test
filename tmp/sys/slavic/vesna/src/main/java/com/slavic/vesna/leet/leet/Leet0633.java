package com.slavic.vesna.leet.leet;

public class Leet0633 {

    public boolean judgeSquareSum(int c) {

        double sqrt1 = Math.sqrt(c);
        for (int i = 0; i <= sqrt1; i++) {
            int aa = c - i * i;
            double sqrt = Math.sqrt(aa);
            if (aa >= 0 && sqrt <= c && sqrt == (int) sqrt) {
                return true;
            }
        }

        return false;
    }

}
