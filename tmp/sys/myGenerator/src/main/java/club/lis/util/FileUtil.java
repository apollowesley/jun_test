package club.lis.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;


/**
 * @Auther: lishun
 * @Date: 2019/4/2 13:48
 * @Description:
 */
public class FileUtil {

    private Logger logger = LoggerFactory.getLogger(FileUtil.class);

    public void writeToFile(String fileName,String content) throws IOException {
        String filePath = this.getClass().getResource("/").getPath();
        filePath = filePath.substring(1,filePath.lastIndexOf("/"));
        filePath = filePath.substring(0,filePath.lastIndexOf("/"));
        File file = new File(filePath + "/" + fileName);
        if(file.exists()){
            file.deleteOnExit();
        }
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(content);
        fileWriter.flush();
    }
}
