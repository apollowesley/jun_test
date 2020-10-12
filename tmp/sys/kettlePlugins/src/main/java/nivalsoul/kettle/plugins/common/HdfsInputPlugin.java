package nivalsoul.kettle.plugins.common;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import nivalsoul.kettle.plugins.step.CommonStep;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.pentaho.di.core.exception.KettleStepException;
import org.pentaho.di.core.row.RowDataUtil;
import org.pentaho.di.core.row.RowMeta;
import org.pentaho.di.core.row.ValueMetaInterface;

import java.io.*;
import java.net.URI;
import java.sql.Timestamp;
import java.util.List;

/**
 * 读取HDFS文件
 *
 * @Author 空山苦水禅人
 * @Date 2018/9/3 23:55
 */
public class HdfsInputPlugin extends CommonStepRunBase {
    private Configuration conf;
    private String hdfsUrls;
    private String fileName;
    private String fileType="";
    private String fieldSeparator;
    private String lineSeparator;
    
    @Override
    public boolean run() throws Exception {
        Object[] r = commonStep.getRow();
        if (commonStep.first) {
            data.outputRowMeta = new RowMeta();
            meta.getFields(data.outputRowMeta, commonStep.getStepname(), null, null,
                    commonStep, commonStep.getRepository(), commonStep.getMetaStore());
            commonStep.first = false;
            init();
        }
        
        if(!fileType.equals("text")){
            throw new IllegalArgumentException("now only support file type [text], but get"+ fileType);
        }
        
        //创建输出记录
        readAndOutput();

        commonStep.setOutputDone();
        return false;
    }

    private void readAndOutput() throws Exception {
        FileSystem fs = FileSystem.get(URI.create(fileName), conf);
        FSDataInputStream fsr = fs.open(new Path(fileName));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fsr));
        String lineTxt = null;
        int num = 0;
        int offsetLine = configInfo.getBooleanValue("header") ? 1 : 0;
        while ((lineTxt = bufferedReader.readLine()) != null){
            if(num ==0){
                createOutFields(lineTxt, offsetLine);
            }
            num++;
            if(offsetLine ==1 && num == 1){
                continue;
            }

            List<ValueMetaInterface> valueMetaList = data.outputRowMeta.getValueMetaList();
            Object[] outputRowData = RowDataUtil.allocateRowData( data.outputRowMeta.size() );
            String[] arr = lineTxt.split(fieldSeparator);
            for(int i=0; i<arr.length; i++) {
                String fieldType = valueMetaList.get(i).getTypeDesc().toLowerCase();
                Object value = null;
                switch (fieldType){
                    case "integer":
                        value = Long.parseLong(arr[i]);
                        break;
                    case "number":
                    case "bignumber":
                        value = Double.parseDouble(arr[i]);
                        break;
                    case "boolean":
                        value = Boolean.parseBoolean(arr[i]);
                        break;
                    case "timestamp":
                        value = Timestamp.valueOf(arr[i]);
                        break;
                    case "string":
                    case "date":
                    default:
                        value = arr[i];
                }
                outputRowData[i] = value;
            }
            commonStep.putRow(data.outputRowMeta, outputRowData);
        }
        commonStep.logBasic("read lines:"+num);
    }

    private void createOutFields(String lineTxt, int offsetLine) throws KettleStepException {
        JSONArray outputFields = meta.getOutputFields();
        String[] arr = lineTxt.split(fieldSeparator);
        if(outputFields==null || outputFields.size()==0) {
            outputFields = new JSONArray();
            //使用第一行数据或者自动字段名时，数据类型默认为string
            if(offsetLine == 0) {
                for(int i=0; i<arr.length;i++) {
                    JSONObject field = new JSONObject();
                    field.put("name", "field"+(i+1));
                    field.put("type", "string");
                    outputFields.add(field);
                }
            }else {
                for(int i=0; i<arr.length;i++) {
                    JSONObject field = new JSONObject();
                    field.put("name", arr[i]);
                    field.put("type", "string");
                    outputFields.add(field);
                }
            }
            meta.setOutputFields(outputFields);
            meta.getFields(data.outputRowMeta, commonStep.getStepname(), null, null,
                    commonStep, commonStep.getRepository(), commonStep.getMetaStore());
        }
    }

    @Override
    public void init(){
        hdfsUrls = commonStep.environmentSubstitute(configInfo.getString("hdfsUrls"));
        fileName = commonStep.environmentSubstitute(configInfo.getString("fileName"));
        fileType = configInfo.getString("fileType");
        fieldSeparator = configInfo.getString("fieldSeparator");
        lineSeparator = configInfo.getString("lineSeparator");

        conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://creditriskcluster");
        conf.set("dfs.nameservices", "creditriskcluster");
        conf.set("dfs.ha.namenodes.creditriskcluster", "nn1,nn2");
        String[] nn = hdfsUrls.split(";");
        for (int i = 0; i < nn.length; i++) {
            conf.set("dfs.namenode.rpc-address.creditriskcluster.nn"+(i+1), nn[i]);
        }
        conf.set("dfs.client.failover.proxy.provider.creditriskcluster",
                "org.apache.hadoop.hdfs.server.namenode.ha.ConfiguredFailoverProxyProvider");
        conf.set("fs.hdfs.impl", "org.apache.hadoop.hdfs.DistributedFileSystem");
    }

}
