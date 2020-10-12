package com.slavic.veles.utils;

import org.apache.commons.codec.binary.Base64;

public class Base64Util {

    public static byte[] base64String2Bytes(String base64Str){
        return Base64.decodeBase64(base64Str);
    }

    public static String bytes2Base64String(byte[] b){
        return Base64.encodeBase64String(b);
    }
}
