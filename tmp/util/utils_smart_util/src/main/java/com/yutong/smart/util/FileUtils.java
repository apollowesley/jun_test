package com.yutong.smart.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.io.input.BOMInputStream;
import org.apache.commons.lang.StringUtils;


public class FileUtils {

    /**
     * 取得当前系统的换行符
     * @return
     */
    public static String getLineSeparator() {
        String separator = System.getProperty("line.separator");
        return separator;
    }


    /**
     * 取得当前系统的路径分隔符
     * @return
     */
    public static String getFileSeparator() {
        String separator = System.getProperty("file.separator");
        return separator;
    }


    /**
     * 文件拷贝
     * <p>
     * 剔除“输入文件”含有BOM信息后,生成“输出文件”
     * <p>
     * 
     * @param inputFileName
     *        输入文件
     * @param outputFileName
     *        输出文件
     * @return 复制的字节数
     * @throws IOException
     * @author yuxiangtong
     */
    public static long copyRemoveBOM(String inputFileName,
        String outputFileName)
        throws IOException {
        FileInputStream inputStream = new FileInputStream(inputFileName);
        FileOutputStream outputStream = new FileOutputStream(outputFileName);

        /* 剔除BOM信息 */
        BOMInputStream bomInputStream = new BOMInputStream(inputStream, false);

        byte[] buffer = new byte[10 * 1024 * 1024]; // 10M
        long byteCount =
                IOUtils.copyLarge(bomInputStream, outputStream, buffer);

        /* 关闭输入输出流 */
        IOUtils.closeQuietly(inputStream);
        IOUtils.closeQuietly(outputStream);

        /* 处理终了 */
        return byteCount;
    }


    public static void test1(String inputFileName, Charset inputCharset,
        String outputFileName, Charset outCharset)
        throws Exception {
        FileInputStream inputStream = new FileInputStream(inputFileName);

        FileOutputStream outputStream = new FileOutputStream(outputFileName);
        LineIterator lineIterator =
                IOUtils.lineIterator(inputStream, inputCharset);
        try {
            while (lineIterator.hasNext()) {
                String line = lineIterator.nextLine();
                if (StringUtils.isNotEmpty(line) && line.charAt(0) == 65279) {
                    line = line.substring(1);
                }
                /* 排除行 */
                // if (StringUtils.contains(line, "prompt")) {
                // continue;
                // }
                line += System.getProperty("line.separator");
                IOUtils.write(line, outputStream, outCharset);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(outputStream);
            lineIterator.close();
        }
    }


    /**
     * @param basePath
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void testcreateNewFile(String basePath, String oldFileName,
        String newFileName)
        throws FileNotFoundException, IOException {
        FileInputStream inputStream =
                new FileInputStream(basePath + oldFileName);
        String sql_string = IOUtils.toString(inputStream, "utf-8");
        IOUtils.closeQuietly(inputStream);

        if (sql_string.charAt(0) == 65279) {
            sql_string = sql_string.substring(1);
        }

        OutputStream output = new FileOutputStream(basePath + newFileName);
        IOUtils.write(sql_string, output, "utf-8");
        IOUtils.closeQuietly(output);
    }
}
