package nivalsoul.kettle.plugins.util.bigdata.hive;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import nivalsoul.kettle.plugins.step.CommonStep;
import org.apache.hadoop.conf.Configuration;
import org.apache.orc.TypeDescription;
import org.apache.parquet.example.data.simple.SimpleGroupFactory;
import org.apache.parquet.schema.MessageType;
import org.apache.parquet.schema.MessageTypeParser;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @Author 空山苦水禅人
 * @Date 2018/8/3 13:47
 */
@Slf4j
public class DataWriter {
    private CommonStep step;
    private WriterConfig config;

    private MessageType parquetSchema = null;
    private TypeDescription orcSchema = null;
    private IProxy writeProxy;
    private Connection hiveCon;
    private String hiveSQL;
    private String partitionFields = "";
    private String partitionValues = "";
    
    
    public DataWriter(CommonStep step, WriterConfig config) throws Exception {
        this.step = step;
        this.config = config;
        init();
    }
    
    public void write(Object[] row) throws IOException {
        writeProxy.writeRow(row, config.getFieldNames(), config.getFieldTypes());
    }
    
    public void finish() throws Exception {
        //关闭文件流
        writeProxy.close();
        //创建hive表并导入数据
        if("true".equals(config.getCreateTable())) {
            createTableAndLoadData();
        }else{
            logInfo("***only write data to: "+config.getHdfsFileName());
        }
    }
    
    private void init() throws Exception {
        //设置hdfs文件权限
        String hadoop_user_name = config.getHadoopUserName();
        logInfo("==set the HADOOP_USER_NAME to : "+hadoop_user_name);
        System.setProperty("HADOOP_USER_NAME", hadoop_user_name);

        //create hive connection
        Class.forName(config.getHiveDriver());
        String url = config.getHiveUrl();
        logInfo("get hive connection from ["+url+"]...");
        hiveCon = DriverManager.getConnection(url , config.getHiveUser(), config.getHivePassword());
        
        //create table schema
        Configuration conf = getConf(config.getHdfsUrls());
        String hdfsFileName = config.getHdfsFileName();
        logInfo("tmp filename is:"+hdfsFileName);
        setPatitionInfo(config);
        if("orc".equals(config.getTableType())) {
            orcSchema = getOrcSchema(config.getHiveTable(), config.getFieldNames(), config.getFieldTypes());
            writeProxy = new OrcProxy(conf, hdfsFileName, orcSchema, "true".equals(config.getOverwrite()));
        }else if("parquet".equals(config.getTableType())) {
            parquetSchema = getParquetShema(config.getHiveTable(), config.getFieldNames(), config.getFieldTypes());
            SimpleGroupFactory f = new SimpleGroupFactory(parquetSchema);
            writeProxy = new ParquetProxy(conf, hdfsFileName, parquetSchema, f, "true".equals(config.getOverwrite()));
        }else if("text".equals(config.getTableType())){
            createTextTableSQL(config.getHiveTable(), config.getFieldNames(), config.getFieldTypes());
            writeProxy = new TextProxy(conf, hdfsFileName, "\t", "\n", "true".equals(config.getOverwrite()));
        }else{
            throw new IllegalArgumentException("文件类型设置不正确，只支持[orc/parquet/text]!");
        }
        writeProxy.setBlockSize(128*1024*1024); //128M
        writeProxy.setPageSize(1024*1024); //1024K
        writeProxy.start();
    }

    private void setPatitionInfo(WriterConfig config) {
        if(config.getPartitionField()!=null){
            JSONArray pfs = config.getPartitionField();
            JSONArray pvs = config.getPartitionValue();
            for(int i=0; i<pfs.size(); i++){
                JSONObject pf = pfs.getJSONObject(i);
                JSONObject pv = pvs.getJSONObject(i);
                partitionFields += pf.getString("name")+" "+pf.getString("type")+",";
                partitionValues += pv.getString("name")+"='"+pv.getString("value")+"',";
            }
            partitionFields = " partitioned by (" + partitionFields.substring(0, partitionFields.length()-1) + ") ";
            partitionValues = " partition(" + partitionValues.substring(0, partitionValues.length()-1) + ") ";
        }
    }

    /**
     * HDFS集群的master节点的ip:port，多个ip:port之间用英文;分隔
     * @param hdfsUrls 172.16.50.24:8020;172.16.50.21:8020
     * @return
     */
    private Configuration getConf(String hdfsUrls) {
        String[] urls = hdfsUrls.split(";");
        String ClusterName = "nss";
        String HADOOP_URL = "hdfs://"+ClusterName;
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", HADOOP_URL);
        conf.set("dfs.nameservices", ClusterName);
        String namenodes = "";
        for (int i=0; i<urls.length; i++) {
            namenodes += ",nn"+i;
            conf.set("dfs.namenode.rpc-address."+ClusterName+".nn"+i, urls[i]);
        }
        conf.set("dfs.ha.namenodes."+ClusterName, namenodes.substring(1));
        conf.set("dfs.client.failover.proxy.provider."+ClusterName,
                "org.apache.hadoop.hdfs.server.namenode.ha.ConfiguredFailoverProxyProvider");
        conf.set("fs.hdfs.impl", "org.apache.hadoop.hdfs.DistributedFileSystem");
        return conf;
    }

    private TypeDescription getOrcSchema(String tablename, String[] fields, String[] types) {
        hiveSQL = "create table if not exists " + tablename +"(";
        TypeDescription schema = TypeDescription.createStruct();
        for(int i=0;i<fields.length;i++){
            hiveSQL += fields[i];
            switch (types[i].toLowerCase()){
                case "long":
                case "integer":
                case "int":
                case "short":
                case "byte":
                    schema.addField(fields[i], TypeDescription.createLong());
                    hiveSQL += " bigint, ";
                    break;
                case "float":
                case "double":
                    schema.addField(fields[i], TypeDescription.createDouble());
                    hiveSQL += " double, ";
                    break;
                case "boolean":
                    schema.addField(fields[i], TypeDescription.createBoolean());
                    hiveSQL += " boolean, ";
                    break;
                case "string":
                default:
                    schema.addField(fields[i], TypeDescription.createString());
                    hiveSQL += " string, ";
            }
        }
        hiveSQL = hiveSQL.substring(0, hiveSQL.length()-2) + ") " + partitionFields + " stored AS orc";
        return schema;
    }

    private MessageType getParquetShema(String tablename, String[] fields, String[] types) {
        hiveSQL = "create table if not exists " + tablename +"(";
        StringBuffer message = new StringBuffer("message m {");
        for(int i=0;i<fields.length;i++){
            hiveSQL += fields[i];
            switch (types[i].toLowerCase()){
                case "long":
                    message.append(" required int64 ").append(fields[i]).append(";");
                    hiveSQL += " bigint, ";
                    break;
                case "integer":
                case "int":
                case "short":
                case "byte":
                    message.append(" required int32 ").append(fields[i]).append(";");
                    hiveSQL += " int, ";
                    break;
                case "float":
                case "double":
                    message.append(" required double ").append(fields[i]).append(";");
                    hiveSQL += " double, ";
                    break;
                case "boolean":
                    message.append(" required boolean ").append(fields[i]).append(";");
                    hiveSQL += " boolean, ";
                    break;
                case "string":
                default:
                    message.append(" required binary ").append(fields[i]).append(" (UTF8);");
                    hiveSQL += " string, ";
            }
        }
        message.append(" }");
        hiveSQL = hiveSQL.substring(0, hiveSQL.length()-2) + ")" + partitionFields + " stored AS parquet";

        return MessageTypeParser.parseMessageType(message.toString());
    }
    
    private void createTextTableSQL(String tablename, String[] fields, String[] types){
        hiveSQL = "create table if not exists " + tablename +"(";
        for(int i=0;i<fields.length;i++){
            hiveSQL += fields[i];
            switch (types[i].toLowerCase()){
                case "long":
                    hiveSQL += " bigint, ";
                    break;
                case "integer":
                case "int":
                case "short":
                case "byte":
                    hiveSQL += " int, ";
                    break;
                case "float":
                case "double":
                    hiveSQL += " double, ";
                    break;
                case "boolean":
                    hiveSQL += " boolean, ";
                    break;
                case "string":
                default:
                    hiveSQL += " string, ";
            }
        }
        hiveSQL = hiveSQL.substring(0, hiveSQL.length()-2) + ")" + partitionFields + 
                "ROW FORMAT DELIMITED FIELDS TERMINATED BY '" + config.getFieldSeparator()+
                "' LINES TERMINATED BY '"+config.getLineSeparator()+"' ";
    }

    private boolean createTableAndLoadData() throws Exception {
        try {
            Statement stmt = hiveCon.createStatement();
            //try to create table, if exist, continue
            String msg = "try to create table: " + config.getHiveTable() + ", ";
            try {
                logInfo("**create table sql::"+hiveSQL);
                stmt.execute(hiveSQL);
                logInfo(msg + "[created true]");
            } catch (SQLException e) {
                logInfo(msg + "[created false]");
                log.error(e.getMessage());
                throw e;
            }

            logInfo("load data to hive table: " + config.getHiveTable() + "...");
            hiveSQL = "load data inpath '"+config.getHdfsFileName()+"' ";
            if("true".equals(config.getOverwrite())){
                hiveSQL += "overwrite ";
            }
            hiveSQL += "into table "+config.getHiveTable() + partitionValues;
            try{
                logInfo("==sql=="+hiveSQL);
                stmt.execute(hiveSQL);
            }catch (Exception e) {
                logError("load data error...");
                throw e;
            }
            logInfo("load data finish!");

            hiveCon.close();

            return true;
        } catch (Exception e) {
            logError(e.getMessage());
            throw e;
        }
    }

    private void logInfo(String msg) {
        if(step!=null){
            step.logBasic(msg);
        }else{
            log.info(msg);
        }
    }
    private void logError(String msg) {
        if(step!=null){
            step.logError(msg);
        }else{
            log.error(msg);
        }
    }

}
