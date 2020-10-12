package com.slavic.vesna.leet.week;

public class Leet5473 {

    public int minFlips(String target) {

        if (target == null || target.length() == 0) return 0;

        while (target.contains("11") || target.contains("00")) {
            target = target.replaceAll("11", "1").replaceAll("00", "0");
        }
        while (target.startsWith("0")) {
            target = target.substring(1);
        }

        return target.length();
    }

    public static void main(String[] args) {
        System.out.println(new Leet5473().minFlips("001011101"));
    }
}
