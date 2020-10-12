package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * Created by Administrator on 2017-05-31.
 */
public class FileUtils {


    /**
     * 删除文件夹
     * @param dir
     * @return
     */
    public static boolean delDir(File dir) {

        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = delDir(new File(dir, children[i]));
                if (!success) {
                    return success;
                }
            }
        }
    return dir.delete();
    }

    /**
     * 文件复制
     * @param source
     * @param target
     * @return
     */
    public  static  boolean copyFile(File source,File target) throws IOException {
        FileChannel in =null;
        FileChannel out =null;
        FileInputStream inputStream =null;
        FileOutputStream outputStream =null;
        try{
            inputStream = new FileInputStream(source);
            outputStream = new FileOutputStream(target);
            in = inputStream.getChannel();
            out = outputStream.getChannel();
            in.transferTo(0,in.size(),out);
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            inputStream.close();
            outputStream.close();
            in.close();
            out.close();
        }
        return true;
    }


}
