package com.slavic.vesna.leet.leet;

import java.util.Arrays;

public class LeetI1010 {

    public void merge(int[] A, int m, int[] B, int n) {

        for (int i = m, j = 0; i < A.length && j < n; i++, j++) A[m] = B[j];

        Arrays.sort(A);

    }

}
