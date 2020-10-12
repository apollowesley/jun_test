package cn.backflow.lib.util;

import org.apache.commons.io.FilenameUtils;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * File optations
 * Created by Taeyeon on 2015/11/27.
 */
public abstract class FileUtil {

    public static String newFilename(String origname, String newname) {
        return newname + "." + FilenameUtils.getExtension(origname);
    }

    /**
     * 转移文件到指定路径 (如果目标文件已存在, 则会先执行删除)
     *
     * @param dir      要保存的路径
     * @param origname 原始文件名
     * @param newname  新文件名称(不含后缀), 为空则不更名
     * @return 最终创建的文件
     */
    public static File touch(String dir, String origname, String newname) {
        String filename = StringUtil.isBlank(newname) ? origname : newFilename(origname, newname);
        File file = new File(dir, filename);
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        return file;
    }

    /**
     * Gets image dimensions for given file
     *
     * @param image image file
     * @return dimensions of image
     * @throws IOException if the file is not a known image
     */
    public static Dimension getImageDimension(File image) throws IOException {
        int pos = image.getName().lastIndexOf(".");
        if (pos == -1)
            throw new IOException("No extension for file: " + image.getAbsolutePath());
        String suffix = image.getName().substring(pos + 1);
        Iterator<ImageReader> iter = ImageIO.getImageReadersBySuffix(suffix);
        if (iter.hasNext()) {
            ImageReader reader = iter.next();
            try {
                ImageInputStream stream = new FileImageInputStream(image);
                reader.setInput(stream);
                int width = reader.getWidth(reader.getMinIndex());
                int height = reader.getHeight(reader.getMinIndex());
                return new Dimension(width, height);
            } catch (IOException e) {
                System.err.println("Error reading: " + image.getAbsolutePath());
                e.printStackTrace();
            } finally {
                reader.dispose();
            }
        }
        throw new IOException("Not a known image file: " + image.getAbsolutePath());
    }

    /**
     * 获取单个文件的MD5值！
     */
    public static String getFileMD5(File file) {
        if (!file.isFile()) {
            return null;
        }
        MessageDigest digest;
        FileInputStream in;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5Util");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        BigInteger bigInt = new BigInteger(1, digest.digest());
        return bigInt.toString(16);
    }

    /**
     * 以指定的文件编码根据文件路径和内容创建文件
     *
     * @param filePath 文件路径
     * @param content  文本内容
     */
    public static void createFile(String filePath, String content, String charset) throws Exception {
        File file = new File(filePath);
        Writer out = new OutputStreamWriter(new FileOutputStream(file), charset);
        out.write(content);
        out.flush();
        out.close();
    }

    /**
     * 以指定编码格式读取指定的文件
     *
     * @param path    文件路径
     * @param charset 字符编码
     */
    public static String readFile(String path, String charset) throws Exception {
        return readFile(new FileInputStream(path), charset);
    }

    /**
     * 以指定编码格式读取指定的文件
     *
     * @param in      文件输入流
     * @param charset 字符集
     */
    public static String readFile(InputStream in, String charset) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(in, charset));
            StringBuilder sb = new StringBuilder("");
            String data;
            while ((data = br.readLine()) != null) {
                sb.append(data.trim()).append("\n");
            }
            return sb.toString().trim();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
            } catch (IOException ignored) {
            }
        }
        return "";
    }

    /**
     * 复制文件、文件夹
     *
     * @param source 源文件或文件夹
     * @param target 替换路径
     */
    public static void replaceFile(String source, String target) throws IOException {
        File old = new File(source); // 原文件地址
        if (!old.exists()) {
            System.out.println("源文件" + source + "不存在");
            return;
        }
        if (!old.isDirectory()) {
            File file = new File(target); // new一个新文件夹
            if (!file.exists())
                file.mkdirs(); // 判断文件夹是否存在
            File fnew = new File(target + File.separator + old.getName()); // 将文件移到新文件里
            old.renameTo(fnew);
        } else {
            // 先打包
            //ZipHelper.createZip(sourceFile, sourceFile + "_.jar");
            //ZipHelper.releaseZipToFile(sourceFile + "_.jar", replaPath + oldFile.getUsername());// 替换到目标文件夹
        }
    }

    /**
     * 复制文件、文件夹
     *
     * @param source 源文件或文件夹
     * @param target 替换路径
     */
    public static boolean copyFile(String source, String target) throws IOException {
        // 原文件地址
        File old = new File(source);
        if (!old.exists()) {
            System.out.println("源文件" + source + "不存在");
            return false;
        }
        if (!old.isDirectory()) {
            // new一个新文件夹
            File fnewpath = new File(target);
            // 判断文件夹是否存在
            if (!fnewpath.exists())
                fnewpath.mkdirs();
            // 将文件移到新文件里
            File fnew = new File(target + File.separator + old.getName());
            FileInputStream fosfrom = new FileInputStream(old);
            FileOutputStream fosto = new FileOutputStream(fnew);
            byte bt[] = new byte[1024];
            int c;
            while ((c = fosfrom.read(bt)) > 0) {
                fosto.write(bt, 0, c);
            }
            fosfrom.close();
            fosto.close();
        } else {
            // 先打包
            //ZipHelper.createZip(sourceFile, sourceFile + "_.jar");
            //ZipHelper.releaseZipToFile(sourceFile + "_.jar", replaPath + oldFile.getUsername());// 替换到目标文件夹

        }
        return true;
    }

    public static void main(String[] args) throws IOException {
        //		FileUtil.createFolders("D:\\/temp\\/uninstallPluging", "\\/SG_GUEST_MANAGER_1.0\\/uninstall");
        //		System.out.println("复制完毕");
        List<String> list = new ArrayList<>();
        list.add("d://1.txt");
        list.add("d://2.txt");
        list.add("d://基于REST风格的RBAC模型研究.pdf");
        FileUtil.files2Zip(list, "d://test.zip");
    }

    /**
     * <pre>
     * 压缩文件 eg:
     * List<String> list =new ArrayList<String>();
     * list.add("d://1.txt");
     * list.add("d://2.txt");
     * list.add("d://基于REST风格的RBAC模型研究.pdf");
     * FileUtil.files2Zip(list, "d://test.zip");
     * </pre>
     *
     * @param filelist ,要压缩的文件源 ，路径集合
     * @param zippath  ,压缩到目标地址
     */
    public static void files2Zip(List<String> filelist, String zippath) {
        File zipFile = new File(zippath); // 最终打包的压缩包
        ZipOutputStream zipStream = null;
        FileInputStream zipSource = null;
        BufferedInputStream bufferStream = null;
        try {
            zipStream = new ZipOutputStream(new FileOutputStream(zipFile));//用这个构造最终压缩包的输出流
            zipSource = null;//将源头文件格式化为输入流
            byte[] bufferArea = new byte[1024 * 10];//读写缓冲区
            int read;
            for (String s : filelist) {
                File file = new File(s);
                zipSource = new FileInputStream(file);
                ZipEntry zipEntry = new ZipEntry(file.getName()); //压缩条目不是具体独立的文件，而是压缩包文件列表中的列表项，称为条目，就像索引一样
                zipStream.putNextEntry(zipEntry); // 定位到该压缩条目位置，开始写入文件到压缩包中
                bufferStream = new BufferedInputStream(zipSource, 1024 * 10); // 输入缓冲流
                // 在任何情况下，b[0] 到 b[off] 的元素以及 b[off+len] 到 b[b.length-1] 的元素都不会受到影响。这个是官方API给出的read方法说明，经典！
                while ((read = bufferStream.read(bufferArea, 0, 1024 * 10)) != -1)
                    zipStream.write(bufferArea, 0, read);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != bufferStream)
                    bufferStream.close();
                if (null != zipStream)
                    zipStream.close();
                if (null != zipSource)
                    zipSource.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}