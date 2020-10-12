package com.foo.common.base.algorithms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Auto Generated By IntelliJIdea on 25/10/2016.
 */
public class LeetcodeHelper {

    final private static Logger logger = LoggerFactory.getLogger(LeetcodeHelper.class);

    /**
     * 371. Sum of Two Integers
     *
     * @see <a href="https://leetcode.com/problems/sum-of-two-integers/">Sum of Two Integers</a>
     */
    public static int getSum(int a, int b) {
//        Integer.toBinaryString(a) ^ Integer.toBinaryString(b);
        return a ^ b;
    }

    /**
     * 292. Nim Game
     *
     * @see <a href="https://leetcode.com/problems/nim-game/">Nim Game</a>
     */
    public static boolean canWinNim(int n) {
        if (n <= 3) {
            return true;
        }
        return !(n % 4 == 0);
    }

    /**
     * 136. Single Number
     *
     * @see <a href="https://leetcode.com/problems/single-number/">single-number</a>
     */
    public static int singleNumber(int[] nums) {
        //Assume that nums length is always >=3
        Arrays.sort(nums);
        for (int i = 0; i < nums.length; i += 2) {
            if (i + 1 >= nums.length) {
                return nums[i];
            }
            if (nums[i] != nums[i + 1]) {
                return nums[i];
            }

        }
        return -1;
    }

    /**
     * 463. Island Perimeter
     *
     * @see <a href="https://leetcode.com/problems/island-perimeter/">island-perimeter</a>
     */
    public static int islandPerimeter(int[][] grid) {
        int sum = 0;
        int currentValue = 0;
        for (int rowIndex = 0; rowIndex < grid.length; rowIndex++) {
            for (int columnIndex = 0; columnIndex < grid[rowIndex].length; columnIndex++) {
                currentValue = grid[rowIndex][columnIndex];
                if (currentValue == 1) {
                    sum += 4;
                    //compare left.
                    if (columnIndex > 0) {
                        if (grid[rowIndex][columnIndex - 1] == 1) {
                            sum -= 1;
                        }
                    }
                    //compare top
                    if (rowIndex > 0) {
                        if (grid[rowIndex - 1][columnIndex] == 1) {
                            sum -= 1;
                        }
                    }
                    //compare right
                    if (columnIndex + 1 < grid[rowIndex].length) {
                        if (grid[rowIndex][columnIndex + 1] == 1) {
                            sum -= 1;
                        }
                    }
                    //compare down
                    if (rowIndex + 1 < grid.length) {
                        if (grid[rowIndex + 1][columnIndex] == 1) {
                            sum -= 1;
                        }
                    }
                }
            }
        }

        return sum;
    }

    /**
     * 455. Assign Cookies
     *
     * @see <a href="https://leetcode.com/problems/assign-cookies/">assign-cookies</a>
     */
    public static int assignCookies(int[] g, int[] s) {
        Arrays.sort(g);
        Arrays.sort(s);

        int sum = 0;
        int sIndex = 0;
        out:
        for (int i = 0; i < g.length; i++) {
            for (int j = sIndex; j < s.length; j++) {
                sIndex++;
                if (g[i] <= s[j]) {
                    sum++;
                    continue out;
                }
            }
        }

        return sum;
    }


    public static int[] twoSum(int[] nums, int target) {
        final int arrayLength = nums.length;
        for (int i = 0; i < arrayLength; i++) {
            for (int j = i + 1; j < arrayLength; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        throw new RuntimeException("not find anything.");
    }


    public static List<String> fizzBuzz(int n) {
        List<String> myList = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            if (i % 3 == 0 && i % 5 == 0) {
                myList.add("FizzBuzz");
            } else {
                if (i % 3 == 0) {
                    myList.add("Fizz");
                } else if (i % 5 == 0) {
                    myList.add("Buzz");
                } else {
                    myList.add(i + "");
                }
            }
        }
        return myList;
    }


    public static String reverseString(String s) {
        char source[] = s.toCharArray();
        final int half = source.length / 2;
        char tmp;
        for (int i = 0; i < half; i++) {
            tmp = source[i];
            source[i] = source[source.length - i - 1];
            source[source.length - i - 1] = tmp;
        }
        return String.valueOf(source);
    }


}