package com.slavic.vesna.leet.leet;

public class Leet0007 {


    // 已有结果升位，%10 得到个位数的值,追加到已有结果， 最后用强转相等判断是否溢出
    public int reverse(int x) {
        long r = 0;
        for (; x != 0; x = x / 10) r = r * 10 + (x % 10);
        return (int) r != r ? 0 : (int) r;
    }
}
