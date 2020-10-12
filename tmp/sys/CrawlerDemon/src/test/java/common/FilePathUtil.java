/*
 * developer spirit_demon  @ 2015.
 */

package common;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2015/8/26 0026.
 */
public class FilePathUtil {
    public static String getFile(String fileName) {

        String result = "";
        InputStream res = ClassLoader.getSystemResourceAsStream(fileName);
        try {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(res);
            byte[] buffer = new byte[res.available()];

            while (bufferedInputStream.read() > 0) {
                bufferedInputStream.read(buffer);
            }
            result = new String(buffer);
            res.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
