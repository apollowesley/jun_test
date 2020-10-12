package com.slavic.vesna.leet.leet;

public class Leet1025 {

    int count = 0;

    public boolean divisorGame(int N) {

        for (int i = 1; i < N;) {
            count++;
            return divisorGame(N - i);
        }

        return count % 2 != 0;
    }

    public static void main(String[] args) {
        System.out.println(new Leet1025().divisorGame(6));
    }
}
