package com.slavic.vesna.leet.leet;

public class LeetJ0011 {

    public int minArray(int[] numbers) {

        if (null == numbers || numbers.length == 0) return 0;

        int min = numbers[numbers.length - 1];
        for (int i = numbers.length - 1; i >= 0; i--) {
            if (numbers[i] > min) return min;
            if (numbers[i] < min) min = numbers[i];
        }
        return min;
    }

}
