package com.dtdream.rdic.saas.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class IdUtils {
    private static long workerId;
    private static long sequence = 1L;

    private final static long workerIdBits  = 0xFFFF;
    private final static long sequenceBits = 0xFFFFFFFF;

    /**
     * 2015-02-09 00:00:00.000
     */
    private final static long dtDream = 1423411200000L;

    private static class VersionTs {
        public long tslast;
        public long tslastnext;
    }

    private static Map<String, VersionTs> idmap = new HashMap<String, VersionTs>();

    static {
        Random rdm = new Random(millisecond());

        long workerId = rdm.nextLong();
        while(0 == workerId){
            workerId = rdm.nextLong();
        }

        IdUtils.workerId = (workerId & workerIdBits);
    }

    public static synchronized String next() {
        long timestamp = millisecond ();

        sequence = (sequence + 1) & sequenceBits;
        if (sequence == 0) {
            sequence = 1;
        }

        return  String.format("%05d-%d-%08d", workerId, timestamp, sequence);
    }

    /**
     * 1微秒内时间戳递增
     * @return
     */
    public static synchronized long version (Class<?> generator) {
        long timestamp = millisecond();
        VersionTs ts = idmap.get(generator.getName());
        if (null == ts) {
            ts = new VersionTs();
            idmap.put(generator.getName(), ts);

        } else {
            if (timestamp <= ts.tslast) {
                timestamp = ts.tslastnext;
            }
        }

        sequence = (sequence + 1) & sequenceBits;
        if (sequence == 0) {
            sequence = 1;
        }

        ts.tslast = timestamp;
        ts.tslastnext = timestamp + 1;
        return ts.tslast;
    }

    private static long millisecond () {
        return new Date().getTime() - dtDream;
    }

    /**
     * 仿激活码生成器
     */
    public final static char[] CODE = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private static long seed = new Random(0).nextInt();
    public final static int CODE_LENGTH = CODE.length;
    public static String generate(int unit, int count, char seperator) {
        StringBuilder objCode = new StringBuilder();
        Random objRand = new Random((seed ++) + new Date().getTime());
        int iTotal = unit * count;
        while (objCode.length() < iTotal)
            objCode.append(CODE[objRand.nextInt(CODE_LENGTH)]);
        for (int i = count - 1; i > 0; -- i)
            objCode.insert(unit * i, seperator);
        return objCode.toString();
    }
}
