package com.iotechn.iot.device;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2018-08-19
 * Time: 上午10:59
 */
public class CodeUtil {

    public static String generateSecretKey(){
        return generateRandomLetterAndNumber(32) + UUID.randomUUID().toString().replace("-","");
    }

    public static String generateRandomLetterAndNumber(int number){
        char[] s = new char[number];
        for(int i = 0; i < 32; i++){
            int random = (int) (System.nanoTime()%36);
            if(random < 10){
                random+=48;
            }else {
                random+=87;
            }
            char c = (char) random;
            s[i] = c;
        }
        return new String(s);
    }

    public static String getSHA256StrJava(String str){
        MessageDigest messageDigest;
        String encodeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes("UTF-8"));
            encodeStr = byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodeStr;
    }

    /**
     * 将byte转为16进制
     * @param bytes
     * @return
     */
    private static String byte2Hex(byte[] bytes){
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i=0;i<bytes.length;i++){
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length()==1){
                //1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }
}
