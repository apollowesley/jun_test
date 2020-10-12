package cn.buglife.tool.e2s.export;

import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;

/**
 * Created by CrazyHarry on 2015/9/19.
 */
public class ExportUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExportUtil.class);

    private static final String DEFAULT_CHARSET = "UTF-8";

    public static void export(String path, String fileName, List<TextField> header, List<String> data) {
        File file = new File(path + "/" + fileName + ".sql");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            String sql = "insert into " + fileName + "(";
            for (int i = 0; i < header.size(); i++) {
                sql += "`" + header.get(i).getText() + "`";
                if (i != header.size() - 1) {
                    sql += ",";
                }
            }
            sql += ") values";
            out.write(sql.getBytes(DEFAULT_CHARSET));
            for (int i = 0; i < data.size(); i++) {
                String tmp = data.get(i);
                out.write(("(" + tmp + ")").getBytes(DEFAULT_CHARSET));
                if (i!=data.size()-1){
                    out.write((",\n").getBytes(DEFAULT_CHARSET));
                }
            }
            out.write((";\n").getBytes(DEFAULT_CHARSET));
            out.close();
        } catch (FileNotFoundException e) {
            LOGGER.error("文件未找到" + e.getMessage());
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("不支持的编码" + e.getMessage());
        } catch (IOException e) {
            LOGGER.error("写入文件失败" + e.getMessage());
        }
    }
}
