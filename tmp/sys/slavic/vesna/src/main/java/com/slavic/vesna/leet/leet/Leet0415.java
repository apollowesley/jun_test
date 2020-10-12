package com.slavic.vesna.leet.leet;

public class Leet0415 {

    public String addStrings(String num1, String num2) {

        StringBuilder sb = new StringBuilder();

        for (int i = num1.length() - 1, j = num2.length() - 1, add = 0; i >= 0 || j >= 0 || add == 1; i--, j--) {

            int c1 = i < 0 ? 0 : num1.charAt(i) - '0';
            int c2 = j < 0 ? 0 : num2.charAt(j) - '0';
            int sum = c1 + c2 + add;
            int i1 = sum > 9 ? sum - 10 : sum;
            add = sum <= 9 ? 0 : 1;
            sb.append(i1);
        }

        return sb.reverse().toString();
    }

    public static void main(String[] args) {
        System.out.println(new Leet0415().addStrings("123", "9999"));
    }

}
