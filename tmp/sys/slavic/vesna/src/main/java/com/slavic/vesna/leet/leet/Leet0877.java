package com.slavic.vesna.leet.leet;

public class Leet0877 {

    public boolean stoneGame(int[] piles) {

        int a = 0, b = 0;
        for (int i = 0; i < piles.length; i++) {
            if (i % 2 == 0) {
                a += piles[i];
            } else {
                b += piles[i];
            }
        }

        return a > b;
    }

}
