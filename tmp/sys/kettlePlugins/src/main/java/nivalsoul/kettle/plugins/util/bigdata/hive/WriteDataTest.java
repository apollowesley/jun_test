package nivalsoul.kettle.plugins.util.bigdata.hive;

import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.UUID;

/**
 * @Author 空山苦水禅人
 * @Date 2018/8/3 13:36
 */
@Slf4j
public class WriteDataTest {
    public static void main(String[] args) throws Exception {
        String driverName = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/test";
        String username = "root";
        String password = "root";
        Class.forName(driverName);
        Connection con = DriverManager.getConnection(url, username, password);
        String[] fields = {"id", "name", "code", "biz_name"};
        String[] types = {"integer", "string", "string", "string"};
        String tableType="orc";
        String hdfsFileName = "/tmp/hiveload/"+UUID.randomUUID()+"."+tableType;
        WriterConfig config = WriterConfig.builder()
                .hadoopUserName("hive")
                .hiveDriver("org.apache.hive.jdbc.HiveDriver")
                .hiveUrl("jdbc:hive2://10.6.1.20:10000/default")
                .hiveUser("hive").hivePassword("hive")
                .hiveTable("xuwl_orc")
                .tableType(tableType)
                .overwrite("true")
                .createTable("true")
                .fieldNames(fields)
                .fieldTypes(types)
                .hdfsUrls("10.6.1.19:8020;10.6.1.20:8020")
                .hdfsFileName(hdfsFileName)
                .fieldSeparator("\t")
                .lineSeparator("\n")
                .build();
        DataWriter dataWriter = new DataWriter(null, config);
        Statement stmt = con.createStatement();
        ResultSet result = stmt.executeQuery("select * from p2");
        ResultSetMetaData rsmd = result.getMetaData();
        int nrCols = rsmd.getColumnCount();
        for(int i = 1; i <= nrCols; i++){
            System.out.print(rsmd.getColumnName(i) + "\t");
        }
        System.out.println();
        while (result.next()) {
            Object[] row  = new Object[nrCols];
            for (int i = 1; i <= nrCols; i++) {
                row[i-1] = result.getObject((i));
                System.out.print(result.getObject(i) + "\t");
            }
            dataWriter.write(row);
            System.out.println();
        }
        dataWriter.finish();
        log.info("write data to hdfs finish!");
        con.close();
    }
}
