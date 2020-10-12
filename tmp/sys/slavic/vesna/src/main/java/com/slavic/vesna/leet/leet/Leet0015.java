package com.slavic.vesna.leet.leet;

import java.util.*;

public class Leet0015 {

    private static final Map<Character, List<Character>> map = new HashMap<>();

    {
        map.put('2', Arrays.asList('a', 'b', 'c'));
        map.put('3', Arrays.asList('d', 'e', 'f'));
        map.put('4', Arrays.asList('g', 'h', 'i'));
        map.put('5', Arrays.asList('j', 'k', 'l'));
        map.put('6', Arrays.asList('m', 'n', 'o'));
        map.put('7', Arrays.asList('p', 'q', 'r', 's'));
        map.put('8', Arrays.asList('t', 'u', 'v'));
        map.put('9', Arrays.asList('w', 'x', 'y', 'z'));

    }

    public List<String> letterCombinations(String digits) {
        List<String> res = new ArrayList<>();
        if (digits.length() == 0) return res;
        letterCombinations(digits.replaceAll("1","").replaceAll("0",""), 0, res, "");
        return res;
    }


    public void letterCombinations(String digits, int start, List<String> res, String item) {

        if (start == digits.length() && item.length() == digits.length()) {
            res.add(item);
            return;
        }
        for (int i = start; i < digits.length(); i++) {
            for (Character character : map.get(digits.charAt(i))) {
                StringBuilder itemBuilder = new StringBuilder(item);
                letterCombinations(digits, i + 1, res, itemBuilder.append(character).toString());
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(new Leet0015().letterCombinations("23").toString());
    }
}
