package nivalsoul.kettle.plugins.util.bigdata;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.*;
import java.net.URI;
import java.util.Date;
import java.util.List;

/**
 * @Author 空山苦水禅人
 * @Date 2018/7/24 14:31
 */
@Slf4j
public class HdfsUtil {
    private Configuration conf;
    
    public HdfsUtil(Configuration conf){
        this.conf = conf;
    }
    
    public HdfsUtil(String namenodes){
        conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://creditriskcluster");
        conf.set("dfs.nameservices", "creditriskcluster");
        conf.set("dfs.ha.namenodes.creditriskcluster", "nn1,nn2");
        String[] nn = namenodes.split(";");
        for (int i = 0; i < nn.length; i++) {
            conf.set("dfs.namenode.rpc-address.creditriskcluster.nn"+(i+1), nn[i]);
        }
        conf.set("dfs.client.failover.proxy.provider.creditriskcluster",
                "org.apache.hadoop.hdfs.server.namenode.ha.ConfiguredFailoverProxyProvider");
        conf.set("fs.hdfs.impl", "org.apache.hadoop.hdfs.DistributedFileSystem");
    }

    /**
     * 将传入的数据行写入HDFS文件
     * @param rows 传入的数据行
     * @param path 文件路径          
     * @return
     * @throws IOException
     */
    public void writeData2HdfsFile(List<String> rows, String path) throws IOException {
        FSDataOutputStream fos = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;
        if(rows!=null && rows.size()>0){
            try{
                Path hdfsPath = new Path(path);
                FileSystem hdfs = FileSystem.get(conf);
                fos = hdfs.create(hdfsPath);
                osw = new OutputStreamWriter(fos);
                bw = new BufferedWriter(osw);
                for(int i=0;i<rows.size();i++){
                    bw.append(rows.get(i));
                }
                bw.flush();
                log.info("write data to hdfs finish.");
            }finally {
                closeIO(bw,osw,fos);
            }
        }
    }

    /**
     * 上传文件到HDFS上去
     */
    public void uploadToHdfs(String localFile, String hdfsFile) throws IOException {
        String localSrc = "E:\\工作相关\\记录.txt";
        String dst = "/tmp/test/article06.txt";
        FileSystem fs = FileSystem.get(conf);
        long start = new Date().getTime();

       /* InputStream in = new FileInputStream(localFile);
        InputStreamReader isr = new InputStreamReader(in, "UTF-8");
        OutputStream out = fs.create(new Path(hdfsFile), true);
        IOUtils.copy(isr, out, "UTF8");*/

        //该方法更快
        FSDataOutputStream outputStream=fs.create(new Path(hdfsFile));
        String fileContent = FileUtils.readFileToString(new File(localFile), "UTF-8");
        outputStream.write(fileContent.getBytes());
        closeIO(outputStream, fs);

        long end = new Date().getTime();
        log.info("upload file use:"+(end-start));
    }

    /**
     * 从HDFS上读取文件
     * @param hdfsFile
     * @param localFile
     * @throws IOException
     */
    public void readFromHdfs(String hdfsFile, String localFile) throws IOException {
        FileSystem fs = FileSystem.get(URI.create(hdfsFile), conf);
        FSDataInputStream hdfsInStream = fs.open(new Path(hdfsFile));

        OutputStream out = new FileOutputStream(localFile);
        byte[] ioBuffer = new byte[1024];
        int readLen = hdfsInStream.read(ioBuffer);

        while (-1 != readLen) {
            out.write(ioBuffer, 0, readLen);
            readLen = hdfsInStream.read(ioBuffer);
        }
        closeIO(out, hdfsInStream, fs);
    }

    public void readHdfsFileByLine(String hdfsFile, String localFile) throws IOException {
        FileSystem fs = FileSystem.get(URI.create(hdfsFile), conf);
        FSDataInputStream fsr = fs.open(new Path(hdfsFile));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fsr));
        BufferedWriter bw = new BufferedWriter(new FileWriter(localFile));
        String lineTxt = null;
        while ((lineTxt = bufferedReader.readLine()) != null){
            bw.write(lineTxt);
            bw.newLine();
        }
        closeIO(bw, bufferedReader, fsr, fs);
    }

    public static void closeIO(Closeable... closeables){
        if(closeables != null){
            for(Closeable io : closeables){
                if(io !=null ) {
                    try {
                        io.close();
                    } catch (Exception e) {
                        io = null;
                        log.error(e.getMessage());
                    }
                }
            }
        }
    }
    
}
