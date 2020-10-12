package com.site.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ファイル操作のユーティリティ.
 *
 * @name ファイル操作のユーティリティ
 * @file 可変
 * @author kc
 * @version 0.0.1 2008/11/18 初版作成
 */
public class FileUtils {

    /** Log4J Logger */
    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

    /**
     * デフォルトのファイルエンコーディング
     */
    private static final String DEFAULT_FILE_ENCODING = "GBK";

    /**
     * コンストラクター.
     */
    private FileUtils() {

    }

    /**
     * テキストファイルをLOADします.
     *
     * @param filepath
     * @param encoding
     * @param throwException
     * @return
     * @throws IOException
     */
    public static String loadTextfile(String filepath, String encoding,
            boolean throwException) throws IOException {

        FileInputStream in = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        String str = null;

        try {
            // 特定したテキストファイルから、各項目とリンクの値を読み込む。
            in = new FileInputStream(filepath);
            isr = new InputStreamReader(in, encoding);
            br = new BufferedReader(isr);

            StringBuilder sb = new StringBuilder();
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }

            str = sb.toString();

        } catch (IOException ioe) {
            logger.debug("ファイルのLOADに失敗しました.[" + filepath + "]", ioe);
            if (throwException)
                throw ioe;
        } finally {

            if (br != null) {
                try {
                    br.close();
                } catch (IOException ioe) {
                    logger.debug("ストリームのCLOSEに失敗しました.[" + filepath + "]", ioe);
                    if (throwException)
                        throw ioe;
                }
            }
            if (isr != null) {
                try {
                    isr.close();
                } catch (IOException ioe) {
                    logger.debug("ストリームのCLOSEに失敗しました.[" + filepath + "]", ioe);
                    if (throwException)
                        throw ioe;
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ioe) {
                    logger.debug("ストリームのCLOSEに失敗しました.[" + filepath + "]", ioe);
                    if (throwException)
                        throw ioe;
                }
            }
        }

        if (str == null)
            str = "";

        return str;
    }

    /**
     * テキストファイルをLOADします.
     *
     * @param filepath
     * @param throwException
     * @return
     * @throws IOException
     */
    public static String loadTextfile(String filepath, boolean throwException) throws IOException {

        return loadTextfile(filepath, DEFAULT_FILE_ENCODING, throwException);
    }
}
