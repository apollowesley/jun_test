package com.team.blogs.common.random;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 随机红包生成
 *
 * @Author: xiaokai
 * @Description:
 * @Date: 2019/7/5
 * @Version: 1.0
 */
public class RandomGiftUtils {

    private static final int MIN = 1;
    private static final int MAX = 20000;

    private static final int SCALE = 2;
    private static final RoundingMode MODE = RoundingMode.HALF_UP;

    public static Integer getBig(int bigBao, int num) {
        List<Integer> gifts = RandomGiftUtils.randomGifts(bigBao,num);
        if (gifts == null) {
            for (int i = 0; i < gifts.size(); i++) {
                Integer amount = gifts.get(i);
                return amount;
            }
        }
        return null;
    }

    /**
     * 生成随机红包(最高200元,最低1分)
     *
     * @param money 红包总金额(单位:分)
     * @param num   红包份数
     * @return
     */
    public static List<Integer> randomGifts(int money,int num) {
        checkRange(money, num);

        List<Integer> gifts;
        if (num == 1) {
            return Arrays.asList(Integer.valueOf(money));
        }

        // 刚好最大或刚好最小，则直接均分
        if ((gifts = quickFill(money, num)) != null) {
            return gifts;
        }

        gifts = new ArrayList<>(num);
        BigDecimal total = BigDecimal.valueOf(money);
        // 当前红包金额总和
        int sum = 0;
        // 当前红包允许最大金额
        int curMax = MAX;
        // 当前红包允许最小金额
        int curMin = MIN;
        // 当前红包金额
        int c;
        // 剩余红包个数
        int rn;
        // 各个红包权重
        int[] an = new int[num];
        // 权重份数
        BigDecimal share = BigDecimal.valueOf(randomInitArrayAndSum(an, num));
        // 当前红包金额占比
        BigDecimal rate;
        for (int i = 0, size = num; i < size; i++) {
            // 最后一个红包金额为总金额-已分配
            if (i == size - 1) {
                c = money - sum;
            } else {
                rn = num - i - 1;
                rate = BigDecimal.valueOf(an[i]).divide(share, SCALE, MODE);
                // 按各自权重计算红包金额
                c = total.multiply(rate).intValue();

                // 预先判断金额是否太大或太小
                curMax = money - sum - rn * MIN;
                curMax = curMax > MAX ? MAX : curMax;
                c = c > curMax ? curMax : c;

                curMin = money - sum - rn * MAX;
                curMin = curMin < MIN ? MIN : curMin;
                c = c < curMin ? curMin : c;
            }

            sum += c;
            Assert.isTrue(c >= MIN && c <= MAX, "c=" + c);
            gifts.add(c);
        }
        // 打乱红包顺序
        Collections.shuffle(gifts);
//        System.out.println(gifts);
        return gifts;
    }

    private static List<Integer> quickFill(int money, int num) {
        List<Integer> gifts = null;
        if (money == num * MIN) {
            gifts = Collections.nCopies(num, MIN);
        } else if (money == num * MAX) {
            gifts = Collections.nCopies(num, MAX);
        }
        return gifts;
    }

    private static void checkRange(int money, int num) {
        if (money < MIN || num < 1) {
            throw new IllegalArgumentException("illegal money:" + money + " or gifts num:" + num);
        }
        if (money / MAX > num) {
            throw new IllegalArgumentException("illegal money:" + money + ",too big.");
        }
        if (money / MIN < num) {
            throw new IllegalArgumentException("illegal money:" + money + ",too little.");
        }
    }

    /**
     * 求所有红包金额之和
     *
     * @param gifts
     * @return
     */
    public static Integer sumGifts(List<Integer> gifts) {
        return gifts.stream().reduce((n1, n2) -> n1 + n2).get();
    }

    /**
     * 随机初始化红包权重,并计算权重份数
     *
     * @param a
     * @param n
     * @return
     */
    public static int randomInitArrayAndSum(int[] a, int n) {
        int sum = 0;
        SecureRandom random = new SecureRandom();
        for (int i = 0, size = n; i < size; i++) {
            a[i] = random.nextInt(n);
            sum += a[i];
        }
        return sum;
    }

}
