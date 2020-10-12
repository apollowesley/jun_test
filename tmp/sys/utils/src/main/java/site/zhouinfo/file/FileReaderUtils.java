package site.zhouinfo.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

/**
 * utils
 * Author:      zhou
 * Create Date：2016-03-05 19:08
 */
public class FileReaderUtils {

    static String readTextA_ByClassPath = "ip.properties";
    static String readTextA_ByProjectRelativePath = "src/main/java/site/zhouinfo/file/FileReaderUtils.java";
    static String readTextB_ByProjectRelativePath = "src/main/resources/log4j.properties";

    public static void main(String[] args) throws IOException {
        File file = new File("");
        InputStream is = file.getClass().getResourceAsStream("network");
        System.out.println(FileReaderUtils.class.getResource("/").getFile());
        readTextA_ByClassPath();
        readTextA_ByProjectRelativePath();
        readTextB_ByProjectRelativePath();
    }

    /**
     * Java读取相对路径的文件
     *
     * @author leizhimin 2010-1-15 10:59:43
     */

    /**
     * 通过工程相对路径读取（包内）文件，注意不以“/”开头
     */
    public static void readTextA_ByProjectRelativePath() {
        System.out.println("-----------------readTextA_ByProjectRelativePath---------------------");
        File f = new File(readTextA_ByProjectRelativePath);
        String a = file2String(f, "utf-8");
        System.out.println(a);
    }

    /**
     * 通过工程相对路径读取（包外）文件，注意不以“/”开头
     */
    public static void readTextB_ByProjectRelativePath() {
        System.out.println("-----------------readTextB_ByProjectRelativePath---------------------");
        File f = new File(readTextB_ByProjectRelativePath);
        String b = file2String(f, "utf-8");
        System.out.println(b);
    }


    /**
     * 通过CLASSPATH读取包内文件，注意以“/”开头
     */
    public static void readTextA_ByClassPath() {
        System.out.println("-----------------readTextA_ByClassPath---------------------");
        InputStream in = FileReaderUtils.class.getClassLoader().getResourceAsStream(readTextA_ByClassPath);
        String a = stream2String(in, "utf-8");
        System.out.println(a);
    }

    /**
     * 文件转换为字符串
     *
     * @param f       文件
     * @param charset 文件的字符集
     * @return 文件内容
     */
    public static String file2String(File f, String charset) {
        String result = null;
        try {
            result = stream2String(new FileInputStream(f), charset);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 文件转换为字符串
     *
     * @param in      字节流
     * @param charset 文件的字符集
     * @return 文件内容
     */
    public static String stream2String(InputStream in, String charset) {
        StringBuffer sb = new StringBuffer();
        try {
            Reader r = new InputStreamReader(in, charset);
            int length = 0;
            for (char[] c = new char[1024]; (length = r.read(c)) != -1; ) {
                sb.append(c, 0, length);
            }
            r.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
