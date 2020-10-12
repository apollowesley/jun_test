package com.lijian.devutils.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;

public class BlobUtil {

    public static String readStringFromBlob(Blob blob) throws SQLException, IOException {
        if (null == blob) {
            return null;
        }

        StringBuilder sb = new StringBuilder();

        InputStream inStream = blob.getBinaryStream();
        // data是读出并需要返回的数据，类型是byte[]
        byte[] data = null;

        data = new byte[512];
        int len = -1;
        while ((len = inStream.read(data)) != -1) {
            sb.append(new String(data,0,len));
            data = new byte[8];
        }
        inStream.close();

        return sb.toString().trim();
    }

    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

}
