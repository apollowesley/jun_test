package nivalsoul.kettle.plugins.util.bigdata.hive;

import com.alibaba.fastjson.JSONArray;
import lombok.Builder;
import lombok.Data;

/**
 * @Author 空山苦水禅人
 * @Date 2018/8/3 18:56
 */
@Data
@Builder
public class WriterConfig {
    private String hadoopUserName = "hive";
    private String hiveDriver = "org.apache.hive.jdbc.HiveDriver";
    private String hiveUrl;
    private String hiveUser;
    private String hivePassword;
    private String hiveTable;
    private JSONArray partitionField;
    private JSONArray partitionValue;
    private String tableType = "orc";
    private String overwrite = "true";
    private String createTable = "true";
    private String[] fieldNames;
    private String[] fieldTypes;
    private String hdfsUrls;
    private String hdfsFileName;
    //仅仅针对textfile格式
    private String fieldSeparator = "\t";
    private String lineSeparator = "\n";
}
