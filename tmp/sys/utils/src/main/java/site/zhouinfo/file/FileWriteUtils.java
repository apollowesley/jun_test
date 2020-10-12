package site.zhouinfo.file;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;

/**
 * 写文件工具类
 *
 * @author zhouinfo
 *         Date 2016-08-19 11:11
 */
public class FileWriteUtils {

    /**
     * 覆盖文件内容
     *
     * @param file
     * @param str
     */
    public static void fileWriter(String file, String str) {
        try {
            FileWriter fw = new FileWriter(file);
            fw.write(str);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * A方法追加文件
     * 将内容 @param str 写进 @param file 的文件尾
     *
     * @param file
     * @param b
     * @param str
     */
    public static void fileWriterEndA(String file, boolean b, String str) {
        try {
            FileWriter fw = new FileWriter(file, b);
            fw.write(str);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * B方法追加文件：使用RandomAccessFile
     */
    public static void fileWriterEndB(String fileName, String content) {
        try {
            // 打开一个随机访问文件流，按读写方式
            RandomAccessFile randomFile = new RandomAccessFile(fileName, "rw");
            // 文件长度，字节数
            long fileLength = randomFile.length();
            //将写文件指针移到文件尾。
            randomFile.seek(fileLength);
            randomFile.writeBytes(content);
            randomFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * C方法追加文件：使用FileOutputStream，在构造FileOutputStream时，把第二个参数设为true
     *
     * @param file
     * @param conent
     */
    public static void fileWriterEndC(String file, String conent) {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file, true)));
            out.write(conent);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        fileWriterEndA("d:/zhou.txt", true, "zhou\r\n");
        fileWriterEndB("d:/zhou.txt", "zhou\r\n");
        fileWriterEndC("d:/zhou.txt", "zhou\r\n");
    }
}
