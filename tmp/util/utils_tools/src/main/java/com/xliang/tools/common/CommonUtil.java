package com.xliang.tools.common;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

public class CommonUtil
{
    /**
     * 字符串md5加密
     * @param str
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String strToMD5(String str)
        throws UnsupportedEncodingException
    {
        
        try
        {
            byte[] input;
            input = str.getBytes("UTF-8");
            return bytesToHex(bytesToMD5(input));
        }
        catch (UnsupportedEncodingException e)
        {
            throw e;
        }
    }
    
    /**
     * 字节数组转换成MD5数组
     * @param input
     * @return
     */
    public static byte[] bytesToMD5(byte[] input)
    {
        byte[] buff = null;
        try
        {
            // 创建一个提供信息摘要算法的对象，初始化为md5算法对象
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算后获得字节数组
            buff = md.digest(input);
            // 把数组每一字节换成16进制连成md5字符串
            // md5str = bytesToHex(buff);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return buff;
    }
    
    /**
     * 把字节数组转成16进位制数
     * 
     * @param bytes
     * @return
     */
    public static String bytesToHex(byte[] bytes)
    {
        StringBuffer md5str = new StringBuffer();
        // 把数组每一字节换成16进制连成md5字符串
        int digital;
        for (int i = 4; i < bytes.length - 4; i++)
        {
            digital = bytes[i];
            if (digital < 0)
            {
                digital += 256;
            }
            if (digital < 16)
            {
                md5str.append("0");
            }
            md5str.append(Integer.toHexString(digital));
        }
        return md5str.toString().toUpperCase();
    }
}
