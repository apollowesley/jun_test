package com.mkfree.hbase.example;

import org.apache.hadoop.hbase.util.Bytes;

import java.util.Iterator;
import java.util.TreeSet;

/**
 * author oyhk
 *
 * hbase 建表预分区工具
 */
public class HashChoreWoker{
    //随机取机数目
    private int baseRecord;
    //rowkey生成器
    private HashRowKeyGenerator rkGen;
    //取样时，由取样数目及region数相除所得的数量.
    private int splitKeysBase;
    //splitkeys个数
    private int splitKeysNumber;

    public HashChoreWoker(int baseRecord, int prepareRegions) {
        this.baseRecord = baseRecord;
        //实例化rowkey生成器
        rkGen = new HashRowKeyGenerator();
        splitKeysNumber = prepareRegions - 1;
        splitKeysBase = baseRecord / prepareRegions;
    }

    public byte[][] calcSplitKeys() {
        //由抽样计算出来的splitkeys结果
        byte[][] splitKeys = new byte[splitKeysNumber][];
        //使用treeset保存抽样数据，已排序过
        TreeSet<byte[]> rows = new TreeSet<byte[]>(Bytes.BYTES_COMPARATOR);
        for (int i = 0; i < baseRecord; i++) {
            rows.add(rkGen.nextId());
        }
        int pointer = 0;
        Iterator<byte[]> rowKeyIter = rows.iterator();
        int index = 0;
        while (rowKeyIter.hasNext()) {
            byte[] tempRow = rowKeyIter.next();
            rowKeyIter.remove();
            if ((pointer != 0) && (pointer % splitKeysBase == 0)) {
                if (index < splitKeysNumber) {
                    splitKeys[index] = tempRow;
                    index ++;
                }
            }
            pointer ++;
        }
        rows.clear();
        rows = null;
        return splitKeys;
    }
}